package com.goodkredit.myapplication.activities.loadmessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.loadmessenger.ConnectedAccountsAdapter;
import com.goodkredit.myapplication.adapter.loadmessenger.OnBottomReachedListener;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.dropoff.DropOffOrder;
import com.goodkredit.myapplication.bean.loadmessenger.BorrowerFB;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.LayoutVisibilityEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.EndlessRecyclerViewScrollListener;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.view.CustomNestedScrollView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectedAccountsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;

    RecyclerView rvLMConnected;
    EditText etLMSearch;
    NestedScrollView connected_nested;
    ConnectedAccountsAdapter adapter = null;
    SwipeRefreshLayout swipeRefresh_lm;
    LinearLayout layout_lm_empty;
    //
    private String index;
    private String authID;
    private String param;
    private String key;

    private int limit = 0;
    private boolean isLoadMore = false;

    //
    private DatabaseHandler db;

    //
    ArrayList<BorrowerFB> fbArrayList = new ArrayList<>();
    private boolean isbottomscroll =false;
    private boolean isloadmore = false;
    private boolean isfirstload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_accounts);

        init();
        loadData();


    }

    private void loadData() {
        imei = CommonFunctions.getImei(getViewContext());
        session = PreferenceUtils.getSessionID(getViewContext());


        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));

            } while (cursor.moveToNext());
        }
        cursor.close();


        //call network
       requestDataFromServer();
    }

    private void requestDataFromServer() {
        layoutVisibility(LayoutVisibilityEnum.HIDE);
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            swipeRefresh_lm.setRefreshing(true);
            getConnectedAccounts();
        }else{
            swipeRefresh_lm.setRefreshing(false);
            showNoInternetToast();
        }
    }


    private void init() {

        db = new DatabaseHandler(getViewContext());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Connected Accounts");


        rvLMConnected = findViewById(R.id.rv_lm_connectedacc);
        etLMSearch = findViewById(R.id.et_lm_search);
        connected_nested = findViewById(R.id.connected_nested);

        connected_nested.setOnScrollChangeListener(scrollOnChangedListener);

        etLMSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if(!etLMSearch.getText().toString().isEmpty()){
                        isLoadMore = false;
                        limit = 0;
                        adapter = null;
                        fbArrayList = new ArrayList<>();
                        performSearch();
                    }else{
                        showErrorToast("Invalid Input");
                    }
                    return true;
                }
                return false;
            }
        });

        swipeRefresh_lm = findViewById(R.id.swipeRefresh_lm);
        layout_lm_empty = findViewById(R.id.layout_lm_empty);

        //rv
        rvLMConnected.setLayoutManager(new LinearLayoutManager(this));
        rvLMConnected.addOnScrollListener(new EndlessRecyclerViewScrollListener(new LinearLayoutManager(getViewContext())) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(getViewContext(), "LAST", Toast.LENGTH_SHORT).show();
            }
        });

        //listener
        swipeRefresh_lm.setOnRefreshListener(this);

    }

    private void performSearch() {
        layoutVisibility(LayoutVisibilityEnum.HIDE);
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            swipeRefresh_lm.setRefreshing(true);
            hideSoftKeyboard();
            searchAccount();
        }else{
            swipeRefresh_lm.setRefreshing(false);
            showNoInternetToast();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRefresh() {
        swipeRefresh_lm.setRefreshing(true);
        isLoadMore = false;
        limit = 0;
        adapter = null;
        fbArrayList = new ArrayList<>();
        requestDataFromServer();
    }

    private void layoutVisibility(LayoutVisibilityEnum visibilityEnum){
       switch (visibilityEnum){
           case HASDATA:
               connected_nested.setVisibility(View.VISIBLE);
               layout_lm_empty.setVisibility(View.GONE);
           break;
           case NODATA:
               connected_nested.setVisibility(View.GONE);
               layout_lm_empty.setVisibility(View.VISIBLE);
               break;
           case HIDE:
               connected_nested.setVisibility(View.GONE);
               layout_lm_empty.setVisibility(View.GONE);
               break;
       }
    }

    //API
    private void getConnectedAccounts(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("limit",String.valueOf(limit));
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, session);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        authID = CommonFunctions.parseJSON(jsonString, "authenticationid");
        key = CommonFunctions.getSha1Hex(authID + session + "getConnectedAccounts");
        param = CommonFunctions.encryptAES256CBC(key, String.valueOf(paramJson));

       if(isLoadMore){
           getConnectedAccountsObject(getConnectedAccountsMoreCallback);
       }else{
           getConnectedAccountsObject(getConnectedAccountsCallback);
       }

    }
    private void getConnectedAccountsObject(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getLoadMessengerV2API(getViewContext())
                .getConnectedAccounts(authID,session,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getConnectedAccountsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            swipeRefresh_lm.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(key,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(key,response.body().getData());
                    fbArrayList = new Gson().fromJson(data,new TypeToken<ArrayList<BorrowerFB>>(){}.getType());
                    if(fbArrayList.size() > 0 && fbArrayList != null){
                        layoutVisibility(LayoutVisibilityEnum.HASDATA);
                        isLoadMore = true;
                        adapter = new ConnectedAccountsAdapter(fbArrayList);
                        rvLMConnected.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        isLoadMore = false;
                        layoutVisibility(LayoutVisibilityEnum.NODATA);
                    }

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                    layoutVisibility(LayoutVisibilityEnum.NODATA);
                }

            }else{
                showErrorGlobalDialogs();
                layoutVisibility(LayoutVisibilityEnum.NODATA);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            layoutVisibility(LayoutVisibilityEnum.NODATA);
            showErrorToast();
            t.printStackTrace();
            swipeRefresh_lm.setRefreshing(false);
       }
    };

    private final Callback<GenericResponse>  getConnectedAccountsMoreCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            swipeRefresh_lm.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(key,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(key,response.body().getData());
                    ArrayList<BorrowerFB> borrowerFBArrayList = new Gson().fromJson(data,new TypeToken<ArrayList<BorrowerFB>>(){}.getType());
                    if(fbArrayList.size() > 0 && fbArrayList != null){
                        isLoadMore = true;
                        fbArrayList.addAll(borrowerFBArrayList);
                    }else{
                        isLoadMore = false;
                    }
                    adapter = new ConnectedAccountsAdapter(fbArrayList);
                    rvLMConnected.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                }

            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            swipeRefresh_lm.setRefreshing(false);
            showErrorToast();
            t.printStackTrace();
        }
    };

    //search
    private void searchAccount(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("fbname",etLMSearch.getText().toString());
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, session);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        authID = CommonFunctions.parseJSON(jsonString, "authenticationid");
        key = CommonFunctions.getSha1Hex(authID + session + "searchAccount");
        param = CommonFunctions.encryptAES256CBC(key, String.valueOf(paramJson));

        searchAccountObject(searchAccountCallback);

    }
    private void searchAccountObject(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getLoadMessengerV2API(getViewContext())
                .searchAccount(authID,session,param);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  searchAccountCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(@NotNull Call<GenericResponse> call, Response<GenericResponse> response) {
            swipeRefresh_lm.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            etLMSearch.setText("");
            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(key,response.body().getMessage());
                if(response.body().getStatus().equals("000")){

                    String data = CommonFunctions.decryptAES256CBC(key,response.body().getData());
                    fbArrayList = new Gson().fromJson(data,new TypeToken<ArrayList<BorrowerFB>>(){}.getType());
                    if(fbArrayList.size() > 0 && fbArrayList != null){
                        layoutVisibility(LayoutVisibilityEnum.HASDATA);
                        isLoadMore = true;
                        adapter = new ConnectedAccountsAdapter(fbArrayList);
                        rvLMConnected.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        isLoadMore = false;
                        layoutVisibility(LayoutVisibilityEnum.NODATA);
                    }

                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorGlobalDialogs(message);
                    layoutVisibility(LayoutVisibilityEnum.NODATA);
                }

            }else{
                showErrorGlobalDialogs();
                layoutVisibility(LayoutVisibilityEnum.NODATA);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            layoutVisibility(LayoutVisibilityEnum.NODATA);
            showErrorToast();
            t.printStackTrace();
            swipeRefresh_lm.setRefreshing(false);
        }
    };

    public CustomNestedScrollView.OnScrollChangeListener scrollOnChangedListener = new CustomNestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                isbottomscroll = true;
                if (isloadmore) {
                    if (!isfirstload) {
                        limit = limit + 30;
                    }
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                        requestDataFromServer();
                    } else {
                      showNoInternetToast();
                    }
                }
            }
        }
    };

}