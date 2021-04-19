package com.goodkredit.myapplication.activities.gkstore;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkstore.history.GKStoreHeaderHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.FetchStoreOrderList;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.gkstore.FetchStoreOrderListResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

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
import se.emilsjolander.stickylistheaders.ExpandableStickyListHeadersListView;

public class GkStoreHistoryActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {
    private DatabaseHandler mdb;
    private Toolbar toolbar;

    //HISTORY
    private List<FetchStoreOrderList> fetchorderlist = new ArrayList<>();
    private String merchantid = "";
    private String storeid = "";
    private String borrowername = "";
    private String strmerchantlat = "";
    private String strmerchantlong = "";
    private String strmerchantaddress = "";
    private String servicecode = "";
    private String sessionid = "";

    private RecyclerView gkHistoryLV;
    private GKStoreHeaderHistoryAdapter gkStoreHistoryLVAdapter;

    //VIEW ARCHIVE
    private MaterialDialog mFilterOptionDialog;
    private List<String> MONTHS = new ArrayList<>();
    private List<String> YEARS = new ArrayList<>();
    private int MIN_MONTH = 1;
    private int MIN_YEAR = 2018;
    private int MAX_MONTH = 12;
    private int MAX_YEAR = 2018;
    private MaterialEditText edtMonth;
    private MaterialEditText edtYear;

    private MaterialDialog mDialog;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner monthspinType;
    private Spinner yearspinType;
    private TextView popfilter;
    private TextView popcancel;
    private boolean isyearselected = false;
    private boolean ismonthselected = false;

    //DATE
    private String passedyear = ".";
    private String passedmonth = ".";
    private int year = 0;
    private int month = 0;
    private int currentyear = 0;
    private int currentmonth = 0;
    private int registrationyear;
    private int registrationmonth;
    private String dateregistered = "";
    private Button mBtnMore;

    //View Archieve and View Filter Switch
    private LinearLayout view_more_container;
    private TextView view_more;

    //LOADER
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //EMPTY
    private RelativeLayout emptyLayout;
    private TextView txv_empty_content;
    private ImageView imv_empty_box;
    private ImageView imv_empty_refresh;
    private ImageView imv_empty_box_watermark;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //SCROLLLIMIT
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private int ordhislimit = 0;
    private NestedScrollView scrollmaincontainer;

    //GLOBAL DIALOGS
    private GlobalDialogs mGlobalDialogs;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_store_history);

        init();
        initData();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);

        scrollmaincontainer = findViewById(R.id.scrollmaincontainer);

        //swipe refresh
        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //loader
        mLlLoader = findViewById(R.id.loaderLayout);
        mTvFetching = findViewById(R.id.fetching);
        mTvWait = findViewById(R.id.wait);

        //NO INTERNET
        nointernetconnection = findViewById(R.id.nointernetconnection);
        refreshnointernet = findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //EMPTY
        emptyLayout = findViewById(R.id.emptyLayout);
        txv_empty_content = findViewById(R.id.txv_empty_content);
        txv_empty_content.setText(V2Utils.setTypeFace(getViewContext(), V2Utils.ROBOTO_ITALIC, "No Order History yet."));
        imv_empty_box = findViewById(R.id.imv_empty_box);
        imv_empty_refresh = findViewById(R.id.imv_empty_refresh);
        imv_empty_refresh.setVisibility(View.GONE);
        imv_empty_box_watermark = findViewById(R.id.imv_empty_box_watermark);

        view_more_container = findViewById(R.id.view_more_container);
        view_more = findViewById(R.id.view_more);
        view_more_container.setOnClickListener(this);
    }

    private void initData() {
        setupToolbar();

        mdb = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        Bundle b = getIntent().getExtras();
        storeid = b.getString("GKSTOREID");
        merchantid = b.getString("GKSTOREMERCHANTID");
        borrowername = b.getString("GKSTOREBORROWERNAME");
        strmerchantlat = b.getString("GKSTOREMERCHANTLAT");
        strmerchantlong = b.getString("GKSTOREMERCHANTLONG");
        strmerchantaddress = b.getString("GKSTOREMERCHANTADD");
        servicecode = b.getString("GKSTORESERVICECODE");

        merchantDetailsSavetoPref();

        setTitle("Order History");

        //ListView for Order History
        gkHistoryLV = findViewById(R.id.gkstorehistorylistview);
        gkHistoryLV.setLayoutManager(new LinearLayoutManager(getViewContext()));
        gkHistoryLV.setNestedScrollingEnabled(false);
        gkStoreHistoryLVAdapter = new GKStoreHeaderHistoryAdapter(getViewContext(), fetchorderlist);
        gkHistoryLV.setAdapter(gkStoreHistoryLVAdapter);

        //initialize date
        passedyear = ".";
        passedmonth = ".";
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        //get store information
        Cursor cursor = mdb.getGKStoreInformation(mdb, merchantid);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                dateregistered = cursor.getString(cursor.getColumnIndex("DateTimeAdded"));

                String CurrentString = dateregistered;
                String[] separated = CurrentString.split("-");
                registrationyear = Integer.parseInt(separated[0]);
                registrationmonth = Integer.parseInt(separated[1]);

            } while (cursor.moveToNext());
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

        MIN_YEAR = registrationyear;
        MIN_MONTH = registrationmonth;
        MAX_YEAR = currentyear;
        MAX_MONTH = currentmonth;

        scrollmaincontainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
                    isbottomscroll = true;

                    if (isloadmore) {
                        if (!isfirstload) {
                            ordhislimit = ordhislimit + 10;
                        }
                        getSession();
                    }
                }
                else {
                    if (mdb.getAllGKStoreTransactionsHeaderStatus(mdb).size() > 0) {
                        view_more_container.setVisibility(View.VISIBLE);
                        if (isyearselected && ismonthselected) {
                            view_more.setText("FILTER OPTIONS");
                        } else {
                            view_more.setText("VIEW ARCHIVE");
                        }
                    }
                    if (nestedScrollViewAtTop(scrollmaincontainer)) {
                        mSwipeRefreshLayout.setEnabled(true);
                    } else {
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                }

            }
        });

//        if (fetchorderlist.isEmpty()) {
//            getSession();
//        }
    }

    private void setNestedScrollingDisabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gkHistoryLV.setNestedScrollingEnabled(false);
        } else {
            ViewCompat.setNestedScrollingEnabled(gkHistoryLV, false);
        }
    }

    //Checks if StickyListView is At top
    private boolean stickylistViewAtTop(ExpandableStickyListHeadersListView stickyListView) {
        return stickyListView.getHeaderViewsCount() == 0 || stickyListView.getHeaderOverlap(0) == 0;
    }

    private boolean nestedScrollViewAtTop(NestedScrollView nestedScrollView) {
        return nestedScrollView.getChildCount() == 0 || nestedScrollView.getChildAt(0).getTop() == 0;
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);
            refreshnointernet.setVisibility(View.GONE);

            isLoading = true;

            mTvFetching.setText("Fetching order history.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            //fetchStoreOrderList(fetchStoreSession);
            fetchStoreOrdersListV3();
        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            refreshnointernet.setVisibility(View.VISIBLE);
            showWarningGlobalDialogs("Seems you are not connected to the internet. " +
                    "Please check your connection and try again. Thank you.");
        }
    }

    private void fetchStoreOrderList(Callback<FetchStoreOrderListResponse> fetchStoreOrderListResponseCallback) {
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        Call<FetchStoreOrderListResponse> fetchstoreorderlist = RetrofitBuild.getGKStoreService(getViewContext())
                .getGkStoreHistory(sessionid,
                        imei,
                        authcode,
                        userid,
                        borrowerid,
                        merchantid,
                        storeid,
                        ordhislimit,
                        passedyear,
                        passedmonth
                );

        fetchstoreorderlist.enqueue(fetchStoreOrderListResponseCallback);
    }

    private final Callback<FetchStoreOrderListResponse> fetchStoreSession = new Callback<FetchStoreOrderListResponse>() {

        @Override
        public void onResponse(Call<FetchStoreOrderListResponse> call, Response<FetchStoreOrderListResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                //000 - list of process
                if (response.body().getStatus().equals("000")) {
                    mLlLoader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);

                    isloadmore = response.body().getGkStoreHistory().size() > 0;

                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.deleteGKStoreHistory(mdb);
                    }

                    List<FetchStoreOrderList> fetchorderlist = response.body().getGkStoreHistory();
                    if (fetchorderlist.size() > 0) {
                        for (FetchStoreOrderList fetchorder : fetchorderlist) {
                            mdb.insertGKStoreHistory(mdb, fetchorder);
                        }
                    }

                    checkhistorylist(mdb.getAllGKStoreTransactionsHeaderStatus(mdb));

                } else {
                    mLlLoader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<FetchStoreOrderListResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            CommonFunctions.hideDialog();
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void showViewArchiveDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(true)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = dialog.findViewById(R.id.filterwrap);
        optionwrap = dialog.findViewById(R.id.optionwrap);
        editsearches = dialog.findViewById(R.id.editsearches);
        clearsearch = dialog.findViewById(R.id.clearsearch);
        monthspinType = dialog.findViewById(R.id.month);
        yearspinType = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);

        filterwrap.setVisibility(View.VISIBLE);
        optionwrap.setVisibility(View.GONE);

        createYearSpinnerAddapter();
        createMonthSpinnerAddapter();

        yearspinType.setOnItemSelectedListener(yearItemListener);
        monthspinType.setOnItemSelectedListener(monthItemListener);

        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);
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

    private void showFilterOptionDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.pop_filtering, false)
                .cancelable(true)
                .backgroundColorRes(R.color.zxing_transparent)
                .show();

        mDialog.getWindow().setBackgroundDrawableResource(R.color.zxing_transparent);

        View dialog = mDialog.getCustomView();

        filterwrap = dialog.findViewById(R.id.filterwrap);
        optionwrap = dialog.findViewById(R.id.optionwrap);
        editsearches = dialog.findViewById(R.id.editsearches);
        clearsearch = dialog.findViewById(R.id.clearsearch);
        yearspinType = dialog.findViewById(R.id.year);
        popfilter = dialog.findViewById(R.id.filter);
        popcancel = dialog.findViewById(R.id.cancel);
        monthspinType = dialog.findViewById(R.id.month);

        createYearSpinnerAddapter();
        createYearSpinnerAddapter();

        filterwrap.setVisibility(View.GONE);
        optionwrap.setVisibility(View.VISIBLE);

        yearspinType.setOnItemSelectedListener(yearItemListener);
        monthspinType.setOnItemSelectedListener(monthItemListener);

        clearsearch.setOnClickListener(this);
        editsearches.setOnClickListener(this);
        popfilter.setOnClickListener(this);
        popcancel.setOnClickListener(this);
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
                    editsearches.setPadding(20,20,20,20);
                    editsearches.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_blue));
                    editsearches.setOnClickListener(edtisearchListener);
                } else if (tag.equals("CLEAR SEARCHES")) {
                    clearsearch = (TextView) textView;
                    clearsearch.setPadding(20,20,20,20);
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

    private void editSearchOption() {
        ordhislimit = 0;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        showViewArchiveDialogNew();
    }

    private void clearSearchOption() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        isfirstload = false;
        ordhislimit = 0;
        view_more.setText("VIEW ARCHIVE");
        isyearselected = false;
        ismonthselected = false;
        isbottomscroll = false;
        isloadmore = false;
        passedyear = ".";
        passedmonth = ".";
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        merchantDetailsSavetoPref();
        getSession();
    }

    //FILTER OPTIONS
    private void filterOptions() {
        if (isyearselected && ismonthselected) {
            if (mdb != null) {
                mdb.deleteGKStoreHistory(mdb);

                if (gkStoreHistoryLVAdapter != null) {
                    gkStoreHistoryLVAdapter.clear();
                }
                view_more_container.setVisibility(View.GONE);
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                ordhislimit = 0;
                if (view_more.getText().equals("VIEW ARCHIVE")) {
                    view_more.setText("FILTER OPTIONS");
                }
                getSession();
            }
        } else {
            showToast("Please select a date.", GlobalToastEnum.WARNING);
        }
    }

    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayAdapter<String> monthadapter;
            ArrayList<String> spinmonthlist = new ArrayList<String>();
            spinmonthlist = monthlist();
            monthadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
            monthadapter.setDropDownViewResource(R.layout.spinner_arrow);
            monthspinType.setAdapter(monthadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            yearspinType.setAdapter(yearadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private AdapterView.OnItemSelectedListener yearItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);
                    createMonthSpinnerAddapter();
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

    private AdapterView.OnItemSelectedListener monthItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    String monthstring = parent.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;
                    if (month <= 9) {
                        passedmonth = "0" + month;
                    } else {
                        passedmonth = String.valueOf(month);
                    }

                    ismonthselected = month > 0;
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

    private AdapterView.OnItemSelectedListener yearItemListenerNew = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);

                    ArrayList<String> finalMonthList = new ArrayList<>();
                    finalMonthList = monthlist();
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getViewContext(), android.R.layout.simple_spinner_dropdown_item, finalMonthList);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.dialog_global_messages_spinner_arrow);
                    monthspinType.setAdapter(spinnerArrayAdapter);

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
                    if (month <= 9) {
                        passedmonth = "0" + month;
                    } else {
                        passedmonth = String.valueOf(month);
                    }

                    ismonthselected = month > 0;
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

    private View.OnClickListener edtisearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mGlobalDialogs != null) {
                editSearchOption();
            }
        }
    };

    private View.OnClickListener clearSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mGlobalDialogs != null) {
                clearSearchOption();
            }
        }
    };

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

    private void checkhistorylist(List<FetchStoreOrderList> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            view_more_container.setVisibility(View.VISIBLE);

            gkHistoryLV.setVisibility(View.VISIBLE);
            gkStoreHistoryLVAdapter.updateData(data);

            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            view_more_container.setVisibility(View.VISIBLE);

            gkHistoryLV.setVisibility(View.GONE);
            gkStoreHistoryLVAdapter.updateData(data);

            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    private void merchantDetailsSavetoPref() {
        PreferenceUtils.removePreference(getViewContext(), "GKSTOREBORROWERNAME");
        PreferenceUtils.removePreference(getViewContext(), "GKSTOREMERCHANTLAT");
        PreferenceUtils.removePreference(getViewContext(), "GKSTOREMERCHANTLONG");
        PreferenceUtils.removePreference(getViewContext(), "GKSTOREMERCHANTADD");
        PreferenceUtils.removePreference(getViewContext(), "GKSTORESERVICECODE");

        PreferenceUtils.saveStringPreference(getViewContext(), "GKSTOREBORROWERNAME", borrowername);
        PreferenceUtils.saveStringPreference(getViewContext(), "GKSTOREMERCHANTLAT", strmerchantlat);
        PreferenceUtils.saveStringPreference(getViewContext(), "GKSTOREMERCHANTLONG", strmerchantlong);
        PreferenceUtils.saveStringPreference(getViewContext(), "GKSTOREMERCHANTADD", strmerchantaddress);
        PreferenceUtils.saveStringPreference(getViewContext(), "GKSTORESERVICECODE", servicecode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewarchive: {
                ordhislimit = 0;
                //showViewArchiveDialog();
                showViewArchiveDialogNew();
                break;
            }
            case R.id.cancel: {
                mDialog.dismiss();
                break;
            }
            case R.id.filter: {
                if (isyearselected && ismonthselected) {
                    if (mdb != null) {
                        mdb.deleteGKStoreHistory(mdb);

                        if (gkStoreHistoryLVAdapter != null) {
                            gkStoreHistoryLVAdapter.clear();
                        }
                        view_more_container.setVisibility(View.GONE);
                        year = Calendar.getInstance().get(Calendar.YEAR);
                        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                        ordhislimit = 0;
                        if (view_more.getText().equals("VIEW ARCHIVE")) {
                            view_more.setText("FILTER OPTIONS");
                        }
                        getSession();
                        mDialog.dismiss();
                    }
                } else {
                    showToast("Please select a date.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refresh: {
                ordhislimit = 0;
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                passedyear = ".";
                passedmonth = ".";
                getSession();
                break;
            }
            case R.id.view_more_container: {
                ordhislimit = 0;
                if (view_more.getText().equals("VIEW ARCHIVE"))
                    //showViewArchiveDialog();
                    showViewArchiveDialogNew();
                else
                    //showFilterOptionDialog();
                    showFilterOptionDialogNew();
                break;
            }
            case R.id.editsearches: {
                ordhislimit = 0;
                filterwrap.setVisibility(View.VISIBLE);
                optionwrap.setVisibility(View.GONE);
                break;
            }
            case R.id.clearsearch: {
                ordhislimit = 0;
                view_more.setText("VIEW ARCHIVE");
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                passedyear = ".";
                passedmonth = ".";
                isyearselected = false;
                ismonthselected = false;
                mDialog.dismiss();
                break;
            }

            case R.id.refreshnointernet: {
                ordhislimit = 0;
                getSession();
                break;
            }
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
    public void onResume() {
        if (mdb != null) {
            mdb.deleteGKStoreHistory(mdb);
            if (gkStoreHistoryLVAdapter != null) {
                gkStoreHistoryLVAdapter.clear();
            }
            view_more_container.setVisibility(View.GONE);
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            mSwipeRefreshLayout.setRefreshing(true);
            isfirstload = false;
            ordhislimit = 0;
            isbottomscroll = false;
            isloadmore = false;
            getSession();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, GkStoreHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            mdb.deleteGKStoreHistory(mdb);
            if (gkStoreHistoryLVAdapter != null) {
                gkStoreHistoryLVAdapter.clear();
            }
            view_more_container.setVisibility(View.GONE);
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            mSwipeRefreshLayout.setRefreshing(true);
            isfirstload = false;
            ordhislimit = 0;
            view_more.setText("VIEW ARCHIVE");
            isyearselected = false;
            ismonthselected = false;
            isbottomscroll = false;
            isloadmore = false;
            passedyear = ".";
            passedmonth = ".";
            merchantDetailsSavetoPref();
            getSession();
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * JANUARY 2020
     * */

    private void fetchStoreOrdersListV3() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("merchantid", merchantid);
            parameters.put("storeid", storeid);
            parameters.put("recordyear", passedyear);
            parameters.put("recordmonth", passedmonth);
            parameters.put("limit", String.valueOf(ordhislimit));
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index =CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "fetchStoreOrdersListV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            fetchStoreOrdersListV3Object();

        } else {
            showNoInternetToast();
        }

    }

    private void fetchStoreOrdersListV3Object(){
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .fetchStoreOrdersListV3(
                        authenticationid, sessionid, param
                );
        call.enqueue(fetchStoreOrdersListV3CallBack);
    }

    private final Callback<GenericResponse> fetchStoreOrdersListV3CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {

                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            isLoading = false;
                            isfirstload = false;

                            if (!isbottomscroll) {
                                mdb.deleteGKStoreHistory(mdb);
                            }

                            List<FetchStoreOrderList> fetchorderlist = new Gson().fromJson(decryptedData, new TypeToken<List<FetchStoreOrderList>>(){}.getType());

                            if (fetchorderlist.size() > 0) {
                                isloadmore = true;
                                for (FetchStoreOrderList fetchorder : fetchorderlist) {
                                    mdb.insertGKStoreHistory(mdb, fetchorder);
                                }
                            } else {
                                isloadmore = false;
                            }
                            checkhistorylist(mdb.getAllGKStoreTransactionsHeaderStatus(mdb));
                        }

                    }  else {
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorGlobalDialogs(decryptedMessage);
                        }

                        List<FetchStoreOrderList> fetchorderlist = new ArrayList<>();
                        checkhistorylist(fetchorderlist);
                    }
                } else {
                    showErrorToast();
                }
            } catch (Exception e){
                e.printStackTrace();
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showErrorToast();
        }
    };
}
