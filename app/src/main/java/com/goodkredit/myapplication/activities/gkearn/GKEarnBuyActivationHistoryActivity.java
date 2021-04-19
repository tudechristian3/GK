package com.goodkredit.myapplication.activities.gkearn;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkearn.GKEarnBuyActivationHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKEarnBuyActivationHistoryActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";
    private String servicecode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    private SwipeRefreshLayout swipe_container;
    private NestedScrollView nested_scroll;
    private LinearLayout nested_scroll_container;

    //VOUCHER REQUEST QUEUE
    private List<V2VirtualVoucherRequestQueue> mVirtualVoucherRequestList = new ArrayList<>();;

    //CREDITS HISTORY
    private RecyclerView rv_transactionhistory;
    private GKEarnBuyActivationHistoryAdapter transactionHistoryAdapter;

    //EMPTY
    private RelativeLayout emptyLayout;
    private TextView txv_empty_content;
    private ImageView imv_empty_box;
    private ImageView imv_empty_refresh;
    private ImageView imv_empty_box_watermark;

    //NO INTERNET CONNECTION
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //SCROLLLIMIT
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private int limit = 0;

    //DATE
    private int MIN_YEAR = 1995;
    private int MAX_YEAR = 2060;
    private int MIN_MONTH = 1;
    private int MAX_MONTH = 12;
    private String passedmonth = ".";
    private String passedyear = ".";
    private int month = 0;
    private int year = 0;
    private int currentmonth = 0;
    private int currentyear = 0;
    private int registrationmonth;
    private int registrationyear;
    private String dateregistered = "";

    //VIEW ARCHIVE
    private LinearLayout btn_view_archive;
    private TextView txv_view_archive;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner yearspinType;
    private boolean isyearselected = false;

    //GLOBAL DIALOGS
    private GlobalDialogs mGlobalDialogs;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coopassistant_transactionhistory);

        try {

            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        nested_scroll = findViewById(R.id.nested_scroll);
        nested_scroll_container = findViewById(R.id.nested_scroll_container);

        //EMPTY
        emptyLayout = findViewById(R.id.emptyLayout);
        txv_empty_content = findViewById(R.id.txv_empty_content);
        txv_empty_content.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_ITALIC, "No transaction history yet"));
        imv_empty_box = findViewById(R.id.imv_empty_box);
        imv_empty_refresh = findViewById(R.id.imv_empty_refresh);
        imv_empty_refresh.setVisibility(View.GONE);
        imv_empty_box_watermark = findViewById(R.id.imv_empty_box_watermark);

        //NO INTERNET CONNECTION
        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //TRANSACTION  HISTORY
        rv_transactionhistory = findViewById(R.id.rv_transactionhistory);

        //ARCHIVE
        btn_view_archive = findViewById(R.id.btn_view_archive);
        btn_view_archive.setOnClickListener(this);
        btn_view_archive.setBackgroundColor(getResources().getColor(R.color.color_gkearn_blue));
        txv_view_archive = findViewById(R.id.txv_view_archive);
    }

    private void initData() {
        //set toolbar
        setupToolbar();

        getSupportActionBar().setTitle("Transaction History");

        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");
        mdb = new DatabaseHandler(getViewContext());

        //HISTORY
        rv_transactionhistory.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_transactionhistory.setNestedScrollingEnabled(false);
        rv_transactionhistory.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.recycler_divider)));
        transactionHistoryAdapter = new GKEarnBuyActivationHistoryAdapter(getViewContext());
        rv_transactionhistory.setAdapter(transactionHistoryAdapter);

        nested_scroll.setOnScrollChangeListener(scrollOnChangedListener);

        Calendar c = Calendar.getInstance();
        passedyear = ".";
        year = Calendar.getInstance().get(Calendar.YEAR);
        currentyear = Calendar.getInstance().get(Calendar.YEAR);

        registrationyear = 2019;
        MIN_YEAR = registrationyear;
        MAX_YEAR = currentyear;

        callMainAPI();
    }

    private NestedScrollView.OnScrollChangeListener scrollOnChangedListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                isbottomscroll = true;

                if (isloadmore) {
                    if (!isfirstload) {
                        limit = limit + 30;
                    }
                    callMainAPI();
                }

            } else {
                if (mdb.getVirtualVoucherRequestQueueByStatus(mdb).size() > 0) {
                    btn_view_archive.setVisibility(View.VISIBLE);
                    if (isyearselected) {
                        txv_view_archive.setText("Filter Options");
                    } else {
                        txv_view_archive.setText("View Archive");
                    }
                }

                if (nestedScrollViewAtTop(nested_scroll)) {
                    swipe_container.setEnabled(true);
                } else {
                    swipe_container.setEnabled(false);
                }
            }
        }
    };

    private boolean nestedScrollViewAtTop(NestedScrollView nestedScrollView) {
        return nestedScrollView.getChildCount() == 0 || nestedScrollView.getChildAt(0).getTop() == 0;
    }

    //API
    private void callMainAPI() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Fetching transaction history", "Please wait...");
            getProcessQueueV2();
        } else {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * JANUARY 2020
     * */

    private void getProcessQueueV2(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext())  > 0){

                LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("year",passedyear);
                parameters.put("limit", String.valueOf(limit));
                parameters.put("from","GKEARNACTIVATION");
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getProcessQueue");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getProcessQueueObjectV2();

            } else {
                swipe_container.setRefreshing(false);
                hideProgressDialog();
                showNoInternetConnection(true);
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getProcessQueueObjectV2() {
        Call<GenericResponse> call = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getProcessQueueV2(authenticationid,sessionid,param);

        call.enqueue(processQueueCallbackV2);
    }


    private Callback<GenericResponse> processQueueCallbackV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();
            hideProgressDialog();
            swipe_container.setRefreshing(false);

            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                    String loaddata = CommonFunctions.parseJSON(data,"loaddata");
                    String billspaydata =  CommonFunctions.parseJSON(data,"billspaydata");
                    String smartreloadqueue = CommonFunctions.parseJSON(data,"smartreloadqueue");
                    String buyvoucherqueue = CommonFunctions.parseJSON(data,"buyvoucherqueue");

                    //success
                    mVirtualVoucherRequestList.clear();
                    Type type = new TypeToken<List<V2VirtualVoucherRequestQueue>>(){}.getType();
                    mVirtualVoucherRequestList = new Gson().fromJson(buyvoucherqueue,type);

                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);
                    }

                    if (mVirtualVoucherRequestList.size() > 0) {
                        isloadmore = true;
                        for (V2VirtualVoucherRequestQueue request : mVirtualVoucherRequestList) {
                            mdb.insertVirtualVoucherRequestQueue(mdb, request);
                        }
                    } else {
                        isloadmore = false;
                    }

                    checkhistorylist(mdb.getVirtualVoucherRequestQueueByStatus(mdb));

                } else {
                    if(response.body().getStatus().equals("error")){
                        showErrorToast();
                    }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    }else{
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                hideProgressDialog();
                swipe_container.setRefreshing(false);
                showNoInternetToast();
            }
        }
        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            swipe_container.setRefreshing(false);
            showNoInternetToast();
        }
    };

    private void checkhistorylist(List<V2VirtualVoucherRequestQueue> data) {
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            rv_transactionhistory.setVisibility(View.VISIBLE);
            transactionHistoryAdapter.updateData(data);
            btn_view_archive.setVisibility(View.VISIBLE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            rv_transactionhistory.setVisibility(View.GONE);
            btn_view_archive.setVisibility(View.GONE);
            swipe_container.bringToFront();
            swipe_container.invalidate();
        }
    }

    private void showViewArchiveDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("View Archive", "",
                "CANCEL", "FILTER", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.SPINNER);

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> spinyearlist = new ArrayList<String>();
        spinyearlist = yearList();

        LinearLayout yearContainer = mGlobalDialogs.setContentSpinner(spinyearlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER));

        final int countyear = yearContainer.getChildCount();
        for (int i = 0; i < countyear; i++) {
            View spinnerView = yearContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                yearspinType = (Spinner) spinnerView;
                yearspinType.setOnItemSelectedListener(yearItemListenerNew);
            }
        }

        View btndoubleone = mGlobalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        View btndoubletwo = mGlobalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
                filterOptions();
            }
        });
    }

    private void showFilterOptionDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("Filter Options", "",
                "CLOSE", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TEXTVIEW);

        mGlobalDialogs.hideTextButtonAction();

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> textviewlist = new ArrayList<String>();
        textviewlist.add("EDIT SEARCHES");
        textviewlist.add("CLEAR SEARCHES");

        LinearLayout textViewContainer = mGlobalDialogs.setContentTextView(textviewlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 18, 0));

        final int count = textViewContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View textView = textViewContainer.getChildAt(i);

            if (textView instanceof TextView) {
                String tag = String.valueOf(textView.getTag());

                if (tag.equals("EDIT SEARCHES")) {
                    editsearches = (TextView) textView;
                    editsearches.setPadding(20, 20, 20, 20);
                    editsearches.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_blue));
                    editsearches.setOnClickListener(editsearchListener);
                } else if (tag.equals("CLEAR SEARCHES")) {
                    clearsearch = (TextView) textView;
                    clearsearch.setPadding(20, 20, 20, 20);
                    clearsearch.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_lightblue));
                    clearsearch.setOnClickListener(clearSearchListener);
                }
            }
        }

        View singlebtn = mGlobalDialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });
    }

    private AdapterView.OnItemSelectedListener yearItemListenerNew = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);
                    isyearselected = true;
                } else {
                    isyearselected = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            isyearselected = false;
        }
    };

    //FILTER OPTIONS
    private void filterOptions() {
        if (isyearselected) {
            if (mdb != null) {
                mdb.truncateTable(mdb, DatabaseHandler.VIRTUAL_VOUCHER_REQUEST);

                if (transactionHistoryAdapter != null) {
                    transactionHistoryAdapter.clear();
                }

                btn_view_archive.setVisibility(View.GONE);
                year = Calendar.getInstance().get(Calendar.YEAR);
                limit = 0;
                if (txv_view_archive.getText().equals("View Archive")) {
                    txv_view_archive.setText("Filter Options");
                }

                callMainAPI();

            }
        } else {
            showToast("Please select a date.", GlobalToastEnum.WARNING);
        }
    }

    //create year list
    private ArrayList<String> yearList() {
        ArrayList<String> mYear = new ArrayList<String>();
        mYear.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            mYear.add(Integer.toString(i));
        }

        return mYear;
    }

    private View.OnClickListener editsearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                editSearchOption();
            }
        }
    };

    private View.OnClickListener clearSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mGlobalDialogs != null) {
                clearSearchOption();
            }
        }
    };

    private void editSearchOption() {
        limit = 0;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        showViewArchiveDialogNew();
    }

    private void clearSearchOption() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        isfirstload = false;
        limit = 0;
        txv_view_archive.setText("View Archive");
        isyearselected = false;
        isbottomscroll = false;
        isloadmore = false;
        passedyear = ".";
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;

        callMainAPI();
    }

    //OTHERS
    public static void start(Context context) {
        Intent intent = new Intent(context, GKEarnBuyActivationHistoryActivity.class);
        context.startActivity(intent);
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view_archive: {
                limit = 0;
                if (txv_view_archive.getText().toString().equals("View Archive"))
                    showViewArchiveDialogNew();
                else
                    showFilterOptionDialogNew();
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        btn_view_archive.setVisibility(View.GONE);
        year = Calendar.getInstance().get(Calendar.YEAR);
        swipe_container.setRefreshing(true);
        isfirstload = false;
        limit = 0;
        txv_view_archive.setText("View Archive");
        isyearselected = false;
        isbottomscroll = false;
        isloadmore = false;
        passedyear = ".";
        if (transactionHistoryAdapter != null) {
            transactionHistoryAdapter.clear();
        }
        callMainAPI();
    }



}
