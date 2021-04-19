package com.goodkredit.myapplication.fragments.billspayment;


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.billspayment.BillsPaymentCategoriesRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
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
 * Created by GoodApps on 07/02/2018.
 */
public class BillsPaymentCategoriesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recycler_view_billers;
    private BillsPaymentCategoriesRecyclerAdapter mAdapter;

    private AQuery aq;

    private DatabaseHandler db;

    //COMMMON
    private String sessionid = "";
    private String imei = "";
    private String borrowerid = "";
    private String userid = "";

    private ArrayList<BillsPaymentCategories> mlistData;
    private boolean isViewShown = false;

    public static Boolean isFirstload = false;
    private EditText search;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //GKSERVICE (ServiceCode)
    private String servicecode;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bills_categories, container, false);

        try {

            init(view);
            initData();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;

    }

    private void init(View view) {
        //set up organization recycler list
        recycler_view_billers = view.findViewById(R.id.recycler_view_billers);
        search = view.findViewById(R.id.edtSearch);

        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        emptyvoucher = view.findViewById(R.id.emptyvoucher);
    }

    private void initData() {

        aq = new AQuery(getActivity());
        //initialized local database
        db = new DatabaseHandler(getActivity());

        isFirstload = true;

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        recycler_view_billers.setNestedScrollingEnabled(false);
        recycler_view_billers.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recycler_view_billers.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.recycler_divider)));
        mAdapter = new BillsPaymentCategoriesRecyclerAdapter(getViewContext(), BillsPaymentCategoriesFragment.this);
        recycler_view_billers.setAdapter(mAdapter);
        mAdapter.setOrganizationList(getAllBillersGC(""));

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
                showSearchBiller();
            }

            @Override
            public void afterTextChanged(Editable s) {
                showSearchBiller();
            }
        });

        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));

            } while (cursor.moveToNext());

            if (!isViewShown) {
                if (CommonVariables.BILLERSFIRSTLOAD) {
                    getSession();
                }
                isFirstload = false;
            }
        }
        cursor.close();

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        try {
            if (isVisibleToUser) {
                if (search.getText().length() > 0) {
                    showSearchBiller();
                } else {
                    populateBillers();
                }
                if (getView() != null) {
                    if (isFirstload) {
                        if (CommonVariables.BILLERSFIRSTLOAD) {
                            getSession();
                        }
                        isViewShown = true;
                        isFirstload = false;
                    }
                }
            } else {
                isViewShown = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateBillers() {
        mlistData.clear();
        try {
            if (!getAllBillersGC("").isEmpty()) {
                mAdapter.setOrganizationList(getAllBillersGC(""));
                mAdapter.setOrganizationList(getAllBillersGC(""), getAllBillersCategory("", ""), "");

            } else {
                emptyvoucher.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSearchBiller() {
        try {
            if (search.getText().length() > 0) {
                mlistData.clear();
                mAdapter.setOrganizationList(getAllBillersGC(search.getText().toString()), getAllBillersCategory(search.getText().toString(), search.getText().toString()), search.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void getSession() {
//        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            createSession(getSessionCallback);
//        } else {
//            showNoInternetToast();
//        }
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            ResponseBody errBody = response.errorBody();
//
//            if (errBody == null) {
//                sessionid = response.body();
//                if (!sessionid.isEmpty()) {
//                    mTvFetching.setText("Fetching billers.");
//                    mTvWait.setText(" Please wait...");
//                    mLlLoader.setVisibility(View.VISIBLE);
//                    new HttpAsyncTask().execute(CommonVariables.GETBILLERS);
//                } else {
//                    hideProgressDialog();
//                    showError();
//                    mLlLoader.setVisibility(View.GONE);
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            } else {
//                hideProgressDialog();
//                showError();
//                mLlLoader.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showError();
//            mLlLoader.setVisibility(View.GONE);
//            swipeRefreshLayout.setRefreshing(false);
//        }
//    };

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mTvFetching.setText("Fetching billers.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            //new HttpAsyncTask().execute(CommonVariables.GETBILLERS);
            getBillers();
        } else {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showNoInternetToast();
        }
    }

    @Override
    public void onRefresh() {
        try {
            mLlLoader.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(true);
            hideKeyboard();
            getSession();
//            search.setText("");
//            mAdapter.setOrganizationList(getAllBillersGC(""));
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

                if (search.getText().length() > 0) {
                    mAdapter.setOrganizationList(getAllBillersGC(search.getText().toString()), getAllBillersCategory(search.getText().toString(), search.getText().toString()), search.getText().toString());
                } else {
                    mAdapter.setOrganizationList(getAllBillersGC(""));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<BillsPaymentCategories> getAllBillersGC(String searchval) {
        ArrayList<BillsPaymentCategories> billsPaymentGroupCategories = new ArrayList<>();
        try {
            Cursor c = db.getBillsGroupCategory(db, searchval);

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    String billercode = c.getString(c.getColumnIndex("BillerCode"));
                    String serviceprvbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                    String billername = c.getString(c.getColumnIndex("BillerName"));
                    String category = c.getString(c.getColumnIndex("Category"));
                    String logourl = c.getString(c.getColumnIndex("LogoURL"));
                    String categoryGroup = c.getString(c.getColumnIndex("GroupCategory"));
                    String categorylogo = c.getString(c.getColumnIndex("CategoryLogo"));
                    if (!categoryGroup.equals("") && !categoryGroup.isEmpty() && !categoryGroup.equals("Null") && !categoryGroup.equals("null")) {
                        billsPaymentGroupCategories.add(new BillsPaymentCategories(logourl, billercode, billername, category, "", serviceprvbillercode, categoryGroup, categorylogo));
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

    public ArrayList<BillsPaymentCategories> getAllBillersCategory(String searchval, String groupcategory) {
        ArrayList<BillsPaymentCategories> billsPaymentCategories = new ArrayList<>();
        try {
            Cursor c = db.getBillersSubCategory(db, searchval, groupcategory);

            if (c.getCount() > 0) {
                while (c.moveToNext()) {
                    String billercode = c.getString(c.getColumnIndex("BillerCode"));
                    String serviceprvbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                    String billername = c.getString(c.getColumnIndex("BillerName"));
                    String billerscategory = c.getString(c.getColumnIndex("Category"));
                    String logourl = c.getString(c.getColumnIndex("LogoURL"));
                    String categoryGroup = c.getString(c.getColumnIndex("GroupCategory"));
                    String categorylogo = c.getString(c.getColumnIndex("CategoryLogo"));
                    if (!categoryGroup.equals("") && !categoryGroup.isEmpty() && !categoryGroup.equals("Null") && !categoryGroup.equals("null")) {
                        billsPaymentCategories.add(new BillsPaymentCategories(logourl, billercode, billername, billerscategory, "",serviceprvbillercode, categoryGroup, categorylogo));
                    }
                }
            }
            c.close();
            mlistData = billsPaymentCategories;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return billsPaymentCategories;
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
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
                        authenticationid, sessionid, param
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

                            if (search.getText().length() > 0) {
                                mAdapter.setOrganizationList(getAllBillersGC(search.getText().toString()), getAllBillersCategory(search.getText().toString(), search.getText().toString()), search.getText().toString());
                            } else {
                                mAdapter.setOrganizationList(getAllBillersGC(""));
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
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };

}

