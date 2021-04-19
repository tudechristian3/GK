package com.goodkredit.myapplication.activities.egame;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.egame.EGameTransactionsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.egame.EGameTransactions;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EGameTransactionsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private RecyclerView rv_egame_transactions;
    private List<EGameTransactions> mGridData = new ArrayList<>();
    private EGameTransactionsAdapter mAdapter;

    //PARAMETERS
    private String sessionid;
    private String imei;
    private String userid;
    private String borrowerid;

    //VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private SwipeRefreshLayout swipe_container;
    private NestedScrollView nested_scroll;
    private RelativeLayout emptyvoucher;
    private TextView txv_nodata;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //VIEW ARCHIVE
    private LinearLayout btn_view_archive;
    private TextView txv_view_archive;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner monthspinType;
    private Spinner yearspinType;
    private boolean isyearselected = false;
    private boolean ismonthselected = false;
    private int MIN_YEAR = 1995;
    private int MAX_YEAR = 2060;
    private int MIN_MONTH = 1;
    private int MAX_MONTH = 12;
    private int year = 0;
    private int month = 0;
    private int currentmonth = 0;
    private int currentyear = 0;
    private int registrationyear = 0;
    private int registrationmonth = 0;

    //SCROLLLIMIT
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private int limit = 0;

    //GLOBAL DIALOGS
    private GlobalDialogs mGlobalDialogs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egame_transactions);

        init();
        initData();
    }

    private void init() {
        setupToolbarWithTitle("E-Games Transactions");

        rv_egame_transactions = (RecyclerView) findViewById(R.id.rv_egame_transactions);
        rv_egame_transactions.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_egame_transactions.setLayoutManager(layoutManager);
        mAdapter = new EGameTransactionsAdapter(mGridData, getViewContext());
        rv_egame_transactions.setAdapter(mAdapter);

        swipe_container = findViewById(R.id.swipe_container);
        swipe_container.setOnRefreshListener(this);
        nested_scroll = findViewById(R.id.nested_scroll);
        emptyvoucher = (RelativeLayout) findViewById(R.id.emptyvoucher);
        refresh = (ImageView) findViewById(R.id.refresh);
        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);

        txv_nodata = findViewById(R.id.txv_nodata);
        refresh.setOnClickListener(this);
        refreshnointernet.setOnClickListener(this);

        //ARCHIVE
        btn_view_archive = findViewById(R.id.btn_view_archive);
        btn_view_archive.setOnClickListener(this);
        txv_view_archive = findViewById(R.id.txv_view_archive);
    }

    private void initData() {
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        nested_scroll.setOnScrollChangeListener(scrollOnChangedListener);

        registrationyear = 2019;
        registrationmonth = 1;
        MIN_YEAR = registrationyear;
        MIN_MONTH = registrationmonth;
        MAX_YEAR = currentyear;
        MAX_MONTH = currentmonth;

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
                if (CacheManager.getInstance().getEGameTransactions().size() > 0) {
                    btn_view_archive.setVisibility(View.VISIBLE);
                    if (isyearselected && ismonthselected) {
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
            getEGameTransactionsV2();
        } else {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getEGameTransactionsV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(year));
                parameters.put("month", convertMonthSingleDigits(month));
                parameters.put("limit", String.valueOf(limit));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getEGameTransactionsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getEGameTransactionsObject(getEGameTransactionsSession);
            } else {
                hideProgressDialog();
                showNoInternetConnection(true);
                swipe_container.setRefreshing(false);
            }
        } catch (Exception e) {
            hideProgressDialog();
            swipe_container.setRefreshing(false);
            e.printStackTrace();
        }
    }

    private void getEGameTransactionsObject(Callback<GenericResponse> getEgameProductsCallback) {
        Call<GenericResponse> getegameproducts = RetrofitBuilder.geteGameAPI(getViewContext())
                .getEGameTransactionsV2Call(
                        authenticationid, sessionid, param
                );
        getegameproducts.enqueue(getEgameProductsCallback);
    }

    private final Callback<GenericResponse> getEGameTransactionsSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                swipe_container.setRefreshing(false);
                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            isfirstload = false;

                            if (!isbottomscroll) {
                                CacheManager.getInstance().removeEGameTransactions();
                            }

                            List<EGameTransactions> eGameTransactionsList = new Gson().fromJson(decryptedData, new TypeToken<List<EGameTransactions>>() {}.getType());

                            if(eGameTransactionsList.size() > 0) {
                                isloadmore = true;
                                CacheManager.getInstance().saveEGameTransactions(eGameTransactionsList);
                            } else {
                                isloadmore = false;
                            }

                            updateList(CacheManager.getInstance().getEGameTransactions());
                        }
                    } else {
                        if (response.body().getStatus().equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            showErrorGlobalDialogs("No Records Found.");
                        }

                        List<EGameTransactions> data = new ArrayList<>();
                        updateList(data);
                    }
                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                hideProgressDialog();
                swipe_container.setRefreshing(false);
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            swipe_container.setRefreshing(false);
            showErrorToast();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onRefresh() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        swipe_container.setRefreshing(true);
        isfirstload = false;
        limit = 0;
        txv_view_archive.setText("View Archive");
        isyearselected = false;
        ismonthselected = false;
        isbottomscroll = false;
        isloadmore = false;
        callMainAPI();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh: {
                swipe_container.setRefreshing(true);
                onRefresh();
                break;
            }

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

        ArrayList<String> spinmonthlist = new ArrayList<>();
        spinmonthlist = monthlist();


        LinearLayout yearContainer = mGlobalDialogs.setContentSpinner(spinyearlist, LinearLayout.HORIZONTAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));

        final int countyear = yearContainer.getChildCount();
        for (int i = 0; i < countyear; i++) {
            View spinnerView = yearContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                yearspinType = (Spinner) spinnerView;
                yearspinType.setOnItemSelectedListener(yearItemListenerNew);
            }
        }

        LinearLayout monthContainer = mGlobalDialogs.setContentSpinner(spinmonthlist, LinearLayout.HORIZONTAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));

        final int countmonth = monthContainer.getChildCount();
        for (int i = 0; i < countmonth; i++) {
            View spinnerView = monthContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                monthspinType = (Spinner) spinnerView;
                monthspinType.setOnItemSelectedListener(monthItemListenerNew);
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

    private AdapterView.OnItemSelectedListener monthItemListenerNew = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    String monthstring = parent.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;

                    if (month > 0) {
                        ismonthselected = true;
                    } else {
                        ismonthselected = false;
                    }
                } else {
                    ismonthselected = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            ismonthselected = false;
        }
    };

    //FILTER OPTIONS
    private void filterOptions() {
        if (isyearselected && ismonthselected) {
            CacheManager.getInstance().removeEGameTransactions();

            if (mAdapter != null) {
                mAdapter.clear();
            }

            btn_view_archive.setVisibility(View.GONE);

            if (txv_view_archive.getText().equals("View Archive")) {
                txv_view_archive.setText("Filter Options");
            }

            limit = 0;

            callMainAPI();
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

    //make the number month to month name
    private ArrayList<String> monthlist() {
        String[] months = new DateFormatSymbols().getMonths();


        ArrayList<String> mMonths = new ArrayList<String>();
        mMonths.add("Select Month");

        int max = 0;
        for (int i = 0; i < months.length; i++) {
            if(registrationyear == year && year != currentyear) {
                max = max + 1;
            }
            else if (year != currentyear) {
                max = max + 1;
            } else {
                if (i < MAX_MONTH) {
                    max = max + 1;
                } else {
                    break;
                }
            }
        }


        if(registrationyear == year) {
            mMonths.addAll(Arrays.asList(months).subList(MIN_MONTH - 1, max));
        } else {
            mMonths.addAll(Arrays.asList(months).subList(0, max));
        }
        return mMonths;
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
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        swipe_container.setRefreshing(true);
        isfirstload = false;
        limit = 0;
        txv_view_archive.setText("View Archive");
        isyearselected = false;
        ismonthselected = false;
        isbottomscroll = false;
        isloadmore = false;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        callMainAPI();
    }

    private String convertMonthSingleDigits(int value) {
        String strmonth = ".";
        if(value < 10){
            strmonth = "0" + value;
        } else {
            strmonth = String.valueOf(value);
        }

        return strmonth;
    }

    private void updateList(List<EGameTransactions> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            txv_nodata.setVisibility(View.GONE);
            emptyvoucher.setVisibility(View.GONE);
            mAdapter.updateList(data);
            mAdapter.notifyDataSetChanged();
            btn_view_archive.setVisibility(View.VISIBLE);
        } else {
            txv_nodata.setText("No E-Games transactions yet.");
            txv_nodata.setVisibility(View.VISIBLE);
            emptyvoucher.setVisibility(View.VISIBLE);
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            btn_view_archive.setVisibility(View.GONE);
        }
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyvoucher.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

}
