package com.goodkredit.myapplication.activities.coopassistant.member;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.goodkredit.myapplication.adapter.coopassistant.member.CoopAssistantTransactionHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.decoration.DividerItemDecoration;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMemberCredits;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantTransactionHistory;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.coopassistant.CoopAssistantTransactionHistoryResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantTransactionHistoryActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
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

    //CREDITS
    private List<CoopAssistantMemberCredits> coopMemberCreditsList = new ArrayList<>();

    //CREDITS HISTORY
    private RecyclerView rv_transactionhistory;
    private CoopAssistantTransactionHistoryAdapter transactionHistoryAdapter;

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
    private int month;
    private int year;
    private int currentmonth;
    private int currentyear;
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

        //CREDITS
        coopMemberCreditsList = PreferenceUtils.getCoopMemberCreditsListPreference(getViewContext(), PreferenceUtils.KEY_GKCOOPMEMBERCREDITS);

        //CREDITS HISTORY
        rv_transactionhistory.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_transactionhistory.setNestedScrollingEnabled(false);
        rv_transactionhistory.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getViewContext(), R.drawable.recycler_divider)));
        transactionHistoryAdapter = new CoopAssistantTransactionHistoryAdapter(getViewContext());
        rv_transactionhistory.setAdapter(transactionHistoryAdapter);

        nested_scroll.setOnScrollChangeListener(scrollOnChangedListener);

        Calendar c = Calendar.getInstance();
        passedyear = ".";
        passedmonth = ".";
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        currentyear = c.get(Calendar.YEAR);
        currentmonth = c.get(Calendar.MONTH) + 1;

        getDateRegisteredOfCredits();

        callMainAPI();
    }

    private void getDateRegisteredOfCredits() {
        if(coopMemberCreditsList != null) {
            if(coopMemberCreditsList.size() > 0) {
                for(CoopAssistantMemberCredits coopAssistantMemberCredits : coopMemberCreditsList) {
                    dateregistered = coopAssistantMemberCredits.getDateTimeIN();
                }

                String CurrentString = dateregistered;
                String[] separated = CurrentString.split("-");
                registrationyear = Integer.parseInt(separated[0]);
                registrationmonth = Integer.parseInt(separated[1]);

                MIN_YEAR = registrationyear;
                MIN_MONTH = registrationmonth;
                MAX_YEAR = currentyear;
                MAX_MONTH = currentmonth;
            }
        }
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
                if (CacheManager.getInstance().getCoopAssistantTransactionHistory().size() > 0) {
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
            getCoopMemberTransactionHistory();
        } else {
            swipe_container.setRefreshing(false);
            hideProgressDialog();
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void getCoopMemberTransactionHistory() {
        Call<CoopAssistantTransactionHistoryResponse> call = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .getCoopMemberTransactionHistory(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        servicecode,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        passedyear,
                        limit,
                        CommonVariables.devicetype
                );

        call.enqueue(getCoopMemberTransactionHistoryCallBack);
    }

    private final Callback<CoopAssistantTransactionHistoryResponse> getCoopMemberTransactionHistoryCallBack
            = new Callback<CoopAssistantTransactionHistoryResponse>() {
        @Override
        public void onResponse(Call<CoopAssistantTransactionHistoryResponse> call, Response<CoopAssistantTransactionHistoryResponse> response) {
            try {
                hideProgressDialog();
                swipe_container.setRefreshing(false);

                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        if (response.body().getData().size() > 0) {
                            isloadmore = true;
                        } else {
                            isloadmore = false;
                        }

                        isfirstload = false;

                        if (!isbottomscroll) {
                            CacheManager.getInstance().removeCoopAssistantTransactionHistory();
                        }

                        List<CoopAssistantTransactionHistory> list = response.body().getData();

                        if (list.size() > 0) {
                            CacheManager.getInstance().saveCoopAssistantTransactionHistory(list);
                        }

                        checkhistorylist(CacheManager.getInstance().getCoopAssistantTransactionHistory());

                    } else if (response.body().getStatus().equals("104")) {
                        showAutoLogoutDialog(response.body().getMessage());
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }

                } else {
                    showErrorGlobalDialogs();
                }
            } catch (Exception e) {
                e.printStackTrace();
                hideProgressDialog();
                swipe_container.setRefreshing(false);
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<CoopAssistantTransactionHistoryResponse> call, Throwable t) {
            t.printStackTrace();
            hideProgressDialog();
            swipe_container.setRefreshing(false);
            showNoInternetToast();
        }
    };

    private void checkhistorylist(List<CoopAssistantTransactionHistory> data) {
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
                CacheManager.getInstance().removeCoopAssistantTransactionHistory();

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
        swipe_container.setRefreshing(true);
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
        Intent intent = new Intent(context, CoopAssistantTransactionHistoryActivity.class);
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
