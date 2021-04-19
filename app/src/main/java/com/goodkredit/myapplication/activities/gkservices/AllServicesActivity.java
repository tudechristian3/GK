package com.goodkredit.myapplication.activities.gkservices;

//package com.goodkredit.myapplication.activities.gkservices;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.billspayment.BillsPaymentBillerItem;
import com.goodkredit.myapplication.adapter.gkservices.AllServicesItemAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.Event;
import com.goodkredit.myapplication.model.EventItem;
import com.goodkredit.myapplication.model.HeaderItem;
import com.goodkredit.myapplication.model.ListItem;
import com.goodkredit.myapplication.model.SectionOrRow;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetGoodKreditServicesResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllServicesActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView recyclerView;


    private DatabaseHandler mdb;
    private String userid;
    private String imei;
    private String borrowerid;
    private String sessionid;

    private List<GKService> services;
    private SectionOrRow sectionOrRows;

    //swipe refresh
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //no internet connection
    private RelativeLayout nointernetconnection;

    private Button refreshnointernetbtn;

    //AUTOLOGOUT
    private boolean autoLogoutisShowing = false;


    //category
    private ArrayList<String> categoryList;
    private List<ListItem> items ;


    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String validateIndex;
    private String validateAuthenticationid;
    private String validateKeyEncryption;
    private String valiadateParam;

    private String serviceIndex;
    private String serviceAuth;
    private String serviceKey;
    private String serviceParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_services);
        init();
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        validatesession();
    }

    private void initData(){
        imei = PreferenceUtils.getImeiId(getApplicationContext());
        userid = PreferenceUtils.getUserId(getApplicationContext());
        borrowerid = PreferenceUtils.getBorrowerId(getApplicationContext());
        sessionid = PreferenceUtils.getSessionID(getApplicationContext());
        services = new ArrayList<>();
        items = new ArrayList<>();
        categoryList= new ArrayList<>();

        new LongFirstOperationTask().execute();
        if (getAllBillers("").size() == 0) {
            //verifySessionBiller();
            //new HttpAsyncTask2().execute(CommonVariables.GETBILLERS);
            getBillers();
        }

    }

    public ArrayList<BillsPaymentBillerItem> getAllBillers(String searchbillername) {
        ArrayList<BillsPaymentBillerItem> arrayList = new ArrayList<>();
        try {

            Cursor c = mdb.getBillers(mdb, "FALSE", searchbillername);
            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    String billercode = c.getString(c.getColumnIndex("BillerCode"));
                    String serviceprvbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                    String billername = c.getString(c.getColumnIndex("BillerName"));
                    String category = c.getString(c.getColumnIndex("Category"));
                    String logourl = c.getString(c.getColumnIndex("LogoURL"));
                    arrayList.add(new BillsPaymentBillerItem(logourl, billercode, billername, category, "", serviceprvbillercode));


                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return arrayList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refreshnointernet:
                    onRefresh();
                break;
        }
    }

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
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
                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");

                if (status.equals("000")) {
                    JSONArray jsonArr = new JSONArray(data);
                    mdb.deleteBillers(mdb, "FALSE");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject obj = jsonArr.getJSONObject(i);

                        String serviceprovider = obj.getString("ServiceProviderBillerCode");
                        String billercode = obj.getString("BillerCode");
                        String billername = obj.getString("BillerName");
                        String billerdesc = obj.getString("BillerDescription");
                        String category = obj.getString("Category");
                        String billerinfo = obj.getString("BillerInfo");
                        String logourl = obj.getString("BillerLogoURL");
                        String servicecode = obj.getString("ServiceCode");
                        String customerscclass = obj.getString("CustomerSCClass");
                        String notes = obj.getString("Notes1");
                        String groupcategory = obj.getString("GroupCategory");
                        String categorylogo = obj.getString("CategoryLogo");

                        mdb.insertBiller(mdb, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "FALSE", ".", ".", notes, groupcategory, categorylogo);
                    }
                } else if (status.equals("003")) {
                    showAutoLogoutDialog(message);
                } else {
                    showErrorGlobalDialogs(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerViewSection);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Objects.requireNonNull(recyclerView.getItemAnimator()).setAddDuration(1500);
        recyclerView.getItemAnimator().setMoveDuration(1500);

        //swipe refresh
        mSwipeRefreshLayout = findViewById(R.id.allservices_swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);


        nointernetconnection = findViewById(R.id.nointernetconnection_allservices);
        refreshnointernetbtn = findViewById(R.id.refreshnointernet);
        refreshnointernetbtn.setOnClickListener(this);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Services");

        mdb = new DatabaseHandler(getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_search,menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.clearFocus();

//        // Detect SearchView icon clicks
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.requestFocus();
                }
            });
//        // Detect SearchView close
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }
            });

//        // Get data in SearchView
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String searchText) {
                    //searchServices(searchText);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String searchText) {
                    searchServices(searchText);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void searchServices(String searchText) {

       Logger.debug("okhttp","SEARCH : "+searchText);

        if (!searchText.isEmpty()) {
                List<GKService> services = mdb.getGKServicesForKeyword(mdb,searchText);

            Logger.debug("okhttp","SEARCH : "+new Gson().toJson(services));

            if (services.size() > 0) {
                items = new ArrayList<>();
                Map<String, List<Event>> events = toMap(loadEvents(services));
                for (String category : events.keySet()) {
                    HeaderItem header = new HeaderItem(category);
                    items.add(header);
                    for (Event event : Objects.requireNonNull(events.get(category))) {
                        EventItem item = new EventItem(event);
                        items.add(item);
                    }
                }

                LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_to_left);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                recyclerView.setAdapter(new AllServicesItemAdapter(items,AllServicesActivity.this));
            }
        }else{
            items = new ArrayList<>();
            List<GKService> services1 = mdb.getGKServicesForKeyword(mdb,"");
            if (services1.size() > 0) {
                Map<String, List<Event>> events = toMap(loadEvents(services1));
                for (String category : events.keySet()) {
                    HeaderItem header = new HeaderItem(category);
                    items.add(header);
                    for (Event event : Objects.requireNonNull(events.get(category))) {
                        EventItem item = new EventItem(event);
                        items.add(item);
                    }
                }
                LayoutAnimationController controller1 =
                        AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);
                recyclerView.setLayoutAnimation(controller1);
                recyclerView.scheduleLayoutAnimation();
                recyclerView.setAdapter(new AllServicesItemAdapter(items,AllServicesActivity.this));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        categoryList.clear();
        getSession();
        validatesession();
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getApplicationContext()) > 0) {
           // getGoodKreditServices();
            getGoodKreditServicesV3();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            //recyclerView.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }
    private void validatesession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            validateSession(validateSessionCallback);
        } else{
            showNoInternetToast();
        }
    }
    private void validateSession (Callback<GenericResponse> validateSessionCallback) {
        Call<GenericResponse> validatesession = RetrofitBuild.getAccountAPIService(getViewContext())
                .validateSessionCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        "ANDROID");

        validatesession.enqueue(validateSessionCallback);
    }

    private final Callback<GenericResponse> validateSessionCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            try{
                if(errorBody == null){
                    if(response.body().getStatus().equals("000")){
                        //do nothing here
                    } else{
                        autoLogoutisShowing = true;
                        checkIfActivityIsBeingFinished(response.body().getMessage());
                    }
                } else {
                    autoLogoutisShowing = true;
                    checkIfActivityIsBeingFinished("Invalid Session");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            t.printStackTrace();
        }
    };

    private class LongFirstOperationTask extends AsyncTask<List<GKService>, Void, List<GKService>> {

        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {
            return mdb.getGKServices(mdb);
        }

        @Override
        protected void onPostExecute(List<GKService> services) {
            if (services.size() > 0) {
                updateData();
            } else {
                getSession();
            }
        }
    }

    private void getGoodKreditServices() {
        Call<GetGoodKreditServicesResponse> call = RetrofitBuild.getGKSerivcesAPIService(getViewContext())
                .getGoodKreditServices(
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        borrowerid
                );

        call.enqueue(getGoodKreditServicesCallback);
    }
    private Callback<GetGoodKreditServicesResponse> getGoodKreditServicesCallback = new Callback<GetGoodKreditServicesResponse>() {
        @Override
        public void onResponse(Call<GetGoodKreditServicesResponse> call, Response<GetGoodKreditServicesResponse> response) {
            try {
                mSwipeRefreshLayout.setRefreshing(false);

                ResponseBody errBody = response.errorBody();
                if (errBody == null) {

                    if(response.body().getStatus().equals("000")){

                        if (response.body().getGkServiceList().size() > 0) {

                            new insertGKServicesTask().execute(response.body().getGkServiceList());
                            updateData();

                        }

                    } else if(response.body().getStatus().equals("003")){
                        if(!autoLogoutisShowing) {
                            checkIfActivityIsBeingFinished(response.body().getMessage());
                        }
                    } else {
                        if(!autoLogoutisShowing) {
                            showErrorGlobalDialogs(response.body().getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetGoodKreditServicesResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            t.toString();
            t.printStackTrace();
            t.getLocalizedMessage();
        }
    };

    private void checkIfActivityIsBeingFinished(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(!Objects.requireNonNull(isFinishing())) {
                showAutoLogoutDialog(message);
            }
        } else {
            if(isFinishing()){
                showAutoLogoutDialog(message);
            }
        }
    }

    private class insertGKServicesTask extends AsyncTask<List<GKService>, Void, List<GKService>> {

        @Override
        protected void onPreExecute() {
            if (mdb != null) {
                mdb.truncateTable(mdb, DatabaseHandler.GK_SERVICES);
            }
        }

        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {

            if (mdb != null) {
                for (GKService gkService : lists[0]) {
                    mdb.insertGKServices(mdb, gkService);
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<GKService> services) {
            super.onPostExecute(services);

        }
    }

    private void updateData() {
        new getGoodAppsServicesTask().execute();
    }


    private List<GKService> getGoodAppsServices(List<GKService> gkServices) {
        return new ArrayList<>(gkServices);
    }

    private class getGoodAppsServicesTask extends AsyncTask<List<GKService>, Void, List<GKService>> {

        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {
            services = mdb.getGKServices(mdb);
            return getGoodAppsServices(services);
        }

        @Override
        protected void onPostExecute(List<GKService> servicesData) {
            if (servicesData.size() > 0) {
                items = new ArrayList<>();

                Map<String, List<Event>> events = toMap(loadEvents(servicesData));
                for (String category : events.keySet()) {
                    HeaderItem header = new HeaderItem(category);
                    items.add(header);
                    for (Event event : Objects.requireNonNull(events.get(category))) {
                        EventItem item = new EventItem(event);
                        items.add(item);
                        Logger.debug("okhttp","ITEM : "+ new Gson().toJson(item));
                    }
                }
                Drawable mDivider = ContextCompat.getDrawable(getViewContext(),R.drawable.divider);
                DividerItemDecoration vItemDecoration = new DividerItemDecoration(getViewContext(),DividerItemDecoration.VERTICAL);
                // Set the drawable on it
                assert mDivider != null;
                vItemDecoration.setDrawable(mDivider);

                recyclerView.addItemDecoration(vItemDecoration);
                recyclerView.setAdapter(new AllServicesItemAdapter(items,AllServicesActivity.this));
            }
        }
    }

    @NonNull
    private List<Event> loadEvents(List<GKService> services) {
        ArrayList<Event> events = new ArrayList<>();
         try{
             for (GKService service : services) {
                 if(!service.getCategory().equals("MAIN")){
                     events.add(new Event(service,service.getCategory()));
                 }
             }
         }catch (NullPointerException e){
             e.printStackTrace();
         }
        return events;
    }
    @NonNull
    private Map<String, List<Event>> toMap(@NonNull List<Event> events) {
        Map<String, List<Event>> map = new TreeMap<>();
         try{
             for (Event event : events) {
                 List<Event> value = map.get(event.getCategory());
                 if (value == null ) {
                     value = new ArrayList<>();
                     map.put(event.getCategory(), value);
                 }
                 value.add(event);
             }

         }catch (NullPointerException e){
             e.printStackTrace();
         }
        return map;
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
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getBillersV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getBillersObject(getBillersSession);

            }else{
                showNoInternetToast();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getBillersObject(Callback<GenericResponse> getBillersCallback) {
        Call<GenericResponse> getBillers = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getBillers(
                        authenticationid, sessionid, param
                );
        getBillers.enqueue(getBillersCallback);
    }
    private final Callback<GenericResponse> getBillersSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

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
                           mdb.deleteBillers(mdb, "FALSE");
                           for (int i = 0; i < jsonArr.length(); i++) {
                               JSONObject obj = jsonArr.getJSONObject(i);

                               String serviceprovider = obj.getString("ServiceProviderBillerCode");
                               String billercode = obj.getString("BillerCode");
                               String billername = obj.getString("BillerName");
                               String billerdesc = obj.getString("BillerDescription");
                               String category = obj.getString("Category");
                               String billerinfo = obj.getString("BillerInfo");
                               String logourl = obj.getString("BillerLogoURL");
                               String servicecode = obj.getString("ServiceCode");
                               String customerscclass = obj.getString("CustomerSCClass");
                               String notes = obj.getString("Notes1");
                               String groupcategory = obj.getString("GroupCategory");
                               String categorylogo = obj.getString("CategoryLogo");

                               mdb.insertBiller(mdb, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "FALSE", ".", ".", notes, groupcategory, categorylogo);
                           }
                       }catch (JSONException e){
                           e.printStackTrace();
                       }
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    }else if(response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(decryptedMessage);
                    }else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };


    //VALIDATE SESSION V2
    private void validateSessionV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("devicetype",CommonVariables.devicetype);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        validateIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", validateIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        validateAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        validateKeyEncryption = CommonFunctions.getSha1Hex(validateAuthenticationid + sessionid + "validateSessionV2");
        valiadateParam= CommonFunctions.encryptAES256CBC(validateKeyEncryption, String.valueOf(paramJson));


        validateSessionV2Object(validateSessionV2Callback);

    }
    private void validateSessionV2Object(Callback<GenericResponse> validateSessionCallback) {
        Call<GenericResponse> validatesession = RetrofitBuilder.getCommonV2API(getViewContext())
                .validateSessionV2(validateAuthenticationid,sessionid,valiadateParam);
        validatesession.enqueue(validateSessionCallback);
    }
    private final Callback<GenericResponse>validateSessionV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mSwipeRefreshLayout.setRefreshing(false);
            try {
                if (errorBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(validateKeyEncryption,response.body().getMessage());
                    assert response.body() != null;
                    if (response.body().getStatus().equals("000")) {
                        updateData();
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        autoLogoutisShowing = true;
                        checkIfActivityIsBeingFinished(getString(R.string.logoutmessage));
                    }else {
                        showError(message);
                    }
                } else {
                    autoLogoutisShowing = true;
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                mSwipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            t.printStackTrace();
        }
    };


    private void getGoodKreditServicesV3(){

        //Please refer to corresponding parameters
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();

        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("devicetype", CommonVariables.devicetype);

        //depends on the authentication type
        LinkedHashMap indexAuthMapObject= CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        serviceIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", serviceIndex);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        //refer to API
        serviceAuth = CommonFunctions.parseJSON(jsonString, "authenticationid");
        serviceKey = CommonFunctions.getSha1Hex(serviceAuth + sessionid + "getGoodKreditServicesV3");
        serviceParam = CommonFunctions.encryptAES256CBC(serviceKey, String.valueOf(paramJson));

        getGoodKreditServicesV3Object(getGoodKreditServicesV3Callback);


    }
    private void getGoodKreditServicesV3Object(Callback<GenericResponse> genericResponseCallback){
        //should check this always
        Call<GenericResponse> call = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getGoodKreditServicesV2(serviceAuth,sessionid,serviceParam);
        call.enqueue(genericResponseCallback);
    }
    private final Callback<GenericResponse>  getGoodKreditServicesV3Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();
            mSwipeRefreshLayout.setRefreshing(false);

            if(errorBody == null){
                String message = CommonFunctions.decryptAES256CBC(serviceKey,response.body().getMessage());
                if(response.body().getStatus().equals("000")){
                    String data = CommonFunctions.decryptAES256CBC(serviceKey,response.body().getData());

                    String services = CommonFunctions.parseJSON(data,"services");
                    String badge = CommonFunctions.parseJSON(data,"badge");

                    Logger.debug("okhttp","SERVICES : "+ services);

                    List<GKService> gkServiceList = new Gson().fromJson(services,new TypeToken<List<GKService>>(){}.getType());
                    if (gkServiceList.size() > 0) {
                        new insertGKServicesTask().execute(gkServiceList);
                        updateData();
                    }
                }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else{
                    showErrorToast(message);
                }

            }else{
                showErrorToast();
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            t.printStackTrace();
            showErrorToast();
        }
    };



}
