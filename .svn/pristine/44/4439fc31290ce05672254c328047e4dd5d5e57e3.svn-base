package com.goodkredit.myapplication.activities.billspayment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.billspayment.BillsPaymentCategoriesShowAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.BillsPaymentCategories;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by GoodApps on 14/02/2018.
 */

public class ViewAllBillersByCategoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener  {
    private RecyclerView recycler_view_all_billers;

    private AQuery aq;
    private DatabaseHandler mdb;

    CommonFunctions cf;
    CommonVariables cv;

    private BillsPaymentCategoriesShowAdapter mAdapter;

    private ArrayList<BillsPaymentCategories> mlistData;

    private EditText search;
    public static Boolean isFirstload = false;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout emptyvoucher;

    private BillsPaymentCategories billsPaymentCategories;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_billers_category);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();

        init();
        initData();

    }

    private void init() {
        recycler_view_all_billers = findViewById(R.id.recycler_view_all_billers);

        search = findViewById(R.id.edtSearch);

        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        emptyvoucher = findViewById(R.id.emptyvoucher);
    }

    private void initData() {
        aq = new AQuery(this);

        //initialized local database
        mdb = new DatabaseHandler(this);

        isFirstload = true;

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        session = PreferenceUtils.getSessionID(getViewContext());

        Bundle args = getIntent().getBundleExtra("args");
        billsPaymentCategories = (BillsPaymentCategories) args.getSerializable("mcategory");

        if (billsPaymentCategories != null) {
            setTitle(CommonFunctions.replaceEscapeData(capitalizeWord(billsPaymentCategories.getCategory())));
        }

        recycler_view_all_billers.setNestedScrollingEnabled(false);
        recycler_view_all_billers.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recycler_view_all_billers.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.recycler_divider)));
        mAdapter = new BillsPaymentCategoriesShowAdapter(getViewContext());
        recycler_view_all_billers.setAdapter(mAdapter);
        mAdapter.updateDataBillsList(getAllBillersViewAll("", billsPaymentCategories.getCategory()));

        mlistData = new ArrayList<>();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (count == 0) {
                    populateBillers();
                } else {
                    showSearchBiller();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(mlistData.isEmpty()) {
            if (!session.isEmpty()) {
                mTvFetching.setText("Fetching billers.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);
                //new HttpAsyncTask().execute(CommonVariables.GETBILLERS);
                getBillers();
            } else {
                hideProgressDialog();
                showError();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    private void populateBillers() {
        mlistData.clear();
        try {
            if (!getAllBillersViewAll("",billsPaymentCategories.getCategory()).isEmpty()) {
                mAdapter.updateDataBillsList(getAllBillersViewAll("", billsPaymentCategories.getCategory()));
            } else {
                emptyvoucher.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {

        }
    }

    public void showSearchBiller() {
        try {
            if (search.getText().length() > 0) {
                mlistData.clear();
                mAdapter.updateDataBillsList(getAllBillersViewAll(search.getText().toString(), billsPaymentCategories.getCategory()));
            }
        } catch (Exception e) {

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                hideKeyboard();
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        try {
            search.setText("");
            hideKeyboard();
            mLlLoader.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(true);
            if (!session.isEmpty()) {
                mTvFetching.setText("Fetching billers.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);
                //new HttpAsyncTask().execute(CommonVariables.GETBILLERS);
                getBillers();

            } else {
                hideProgressDialog();
                showError();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + session);
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", session);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);
        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {

                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");

                if (status.equals("000")) {
                    JSONArray jsonArr = new JSONArray(data);
                    if (jsonArr.length() > 0) {
                        emptyvoucher.setVisibility(View.GONE);
                    } else {
                        emptyvoucher.setVisibility(View.VISIBLE);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private ArrayList<BillsPaymentCategories> getAllBillersViewAll(String searchval, String category) {
        ArrayList<BillsPaymentCategories> billsPaymentGroupCategories = new ArrayList<>();
        try {
            Cursor c = mdb.getBillersCatViewAll(mdb, searchval, category);

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    String billercode = c.getString(c.getColumnIndex("BillerCode"));
                    String serviceprvbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                    String billername = c.getString(c.getColumnIndex("BillerName"));
                    String billerscategory = c.getString(c.getColumnIndex("Category"));
                    String logourl = c.getString(c.getColumnIndex("LogoURL"));
                    String categoryGroup = c.getString(c.getColumnIndex("GroupCategory"));
                    String categorylogo = c.getString(c.getColumnIndex("CategoryLogo"));
                    if (!category.equals("") && !category.isEmpty() && !category.equals("Null") && !category.equals("null")) {
                        billsPaymentGroupCategories.add(new BillsPaymentCategories(logourl, billercode, billername, billerscategory, "", serviceprvbillercode, categoryGroup, categorylogo));
                    }
                }
            }
            c.close();
            mlistData = billsPaymentGroupCategories;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return billsPaymentGroupCategories;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, ViewAllBillersByCategoryActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getBillers(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                LinkedHashMap<String ,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, session);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + session + "getBillersV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getBillersObject(getBillersSession);

            }else{
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                showNoInternetToast();
            }

        }catch (Exception e){
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showError();
            e.printStackTrace();
        }
    }

    private void getBillersObject(Callback<GenericResponse> getBillersCallback) {
        Call<GenericResponse> getBillers = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getBillers(
                        authenticationid, session, param
                );
        getBillers.enqueue(getBillersCallback);
    }

    private final Callback<GenericResponse> getBillersSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        try{
                            JSONArray jsonArr = new JSONArray(decryptedData);
                            if (jsonArr.length() > 0) {
                                emptyvoucher.setVisibility(View.GONE);
                            } else {
                                emptyvoucher.setVisibility(View.VISIBLE);
                            }
                        }catch (NullPointerException | JSONException e){
                            e.printStackTrace();
                        }
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showError();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    };

}
