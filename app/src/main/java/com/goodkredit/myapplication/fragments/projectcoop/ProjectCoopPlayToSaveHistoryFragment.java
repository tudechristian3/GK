package com.goodkredit.myapplication.fragments.projectcoop;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.adapter.projectcoop.ProjectCoopPlayToSaveHistoryStickyHeaderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.projectcoop.GameHistory;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.projectcoop.GetGamePreviousEntriesResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class ProjectCoopPlayToSaveHistoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemClickListener {

    //    private RecyclerView recyclerView;
//    private ProjectCoopPlayToSaveHistoryRecyclerAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private int limit = 0;
    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;
    private boolean isLoading = false;

    //VIEW ARCHIVE
    private LinearLayout viewarchivelayoutv2;
    private TextView viewarchivev2;
    private LinearLayout viewarchivelayout;
    private TextView viewarchive;
    private MaterialDialog mViewArchiveDialog;
    private MaterialDialog mFilterOptionDialog;
    private List<String> MONTHS = new ArrayList<>();
    private List<String> YEARS = new ArrayList<>();
    private int MIN_MONTH = 1;
    private int MIN_YEAR = 2018;
    private MaterialEditText edtMonth;
    private MaterialEditText edtYear;
    private int year = 0;
    private int month = 0;

    private List<GameHistory> gameHistoryList;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private StickyListHeadersListView stickyListHeadersListView;
    private ProjectCoopPlayToSaveHistoryStickyHeaderAdapter mStickyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_coop_play_to_save_history, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        //ListView for Order History
        stickyListHeadersListView = (StickyListHeadersListView) view.findViewById(R.id.stickyListHeaderListView);
        stickyListHeadersListView.setOnItemClickListener(this);
        mStickyAdapter = new ProjectCoopPlayToSaveHistoryStickyHeaderAdapter(getViewContext());
        stickyListHeadersListView.setAdapter(mStickyAdapter);

//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
//        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
//        recyclerView.setNestedScrollingEnabled(false);
//        mAdapter = new ProjectCoopPlayToSaveHistoryRecyclerAdapter(getViewContext());
//        recyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //VIEW ARCHIVE
        viewarchivelayoutv2 = (LinearLayout) view.findViewById(R.id.viewarchivelayoutv2);
        viewarchivev2 = (TextView) view.findViewById(R.id.viewarchivev2);
        viewarchivev2.setOnClickListener(this);
        viewarchivev2.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));
        viewarchivelayout = (LinearLayout) view.findViewById(R.id.viewarchivelayout);
        viewarchive = (TextView) view.findViewById(R.id.viewarchive);
        viewarchive.setOnClickListener(this);
        viewarchive.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", getString(R.string.string_view_archive)));

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);


        //SET UP ARCHIVE DIALOG
        setUpViewArchiveDialog();
        setUpFilterOptions();
    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        gameHistoryList = new ArrayList<>();

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        getSession();

        stickyListHeadersListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (stickyListHeadersListView.getLastVisiblePosition() - stickyListHeadersListView.getHeaderViewsCount() -
                        stickyListHeadersListView.getFooterViewsCount()) >= (stickyListHeadersListView.getCount() - 1)) {

                    //BOTTOM SCROLL

                    mSwipeRefreshLayout.setEnabled(false);

                    isbottomscroll = true;

                    if (isloadmore) {
                        if (!isfirstload) {
                            limit = limit + 30;
                        }
                        getSession();
                    }

                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (firstVisibleItem == 0 && StickylistViewAtTop(stickyListHeadersListView)) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }

            }
        });
    }

    //Checks if StickyListView is At top
    private boolean StickylistViewAtTop(StickyListHeadersListView stickyListView) {
        return stickyListView.getChildCount() == 0 || stickyListView.getChildAt(0).getTop() == 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ((ProjectCoopActivity) getViewContext()).displayView(2, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//
//        } else {
//            showNoInternetConnection(true);
//            mSwipeRefreshLayout.setRefreshing(false);
//            showError(getString(R.string.generic_internet_error_message));
//        }
//
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    getGamePreviousEntries(getGamePreviousEntriesSession);
//                }
//            } else {
//                mSwipeRefreshLayout.setRefreshing(false);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            mSwipeRefreshLayout.setRefreshing(false);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getGamePreviousEntries(getGamePreviousEntriesSession);
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
        }

    }

    private void getGamePreviousEntries(Callback<GetGamePreviousEntriesResponse> getGamePreviousEntriesCallback) {
        Call<GetGamePreviousEntriesResponse> getgamepreviousentries = RetrofitBuild.getGamePreviousEntriesService(getViewContext())
                .getGamePreviousEntriesCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(limit),
                        String.valueOf(year),
                        String.valueOf(month));
        getgamepreviousentries.enqueue(getGamePreviousEntriesCallback);
    }

    private final Callback<GetGamePreviousEntriesResponse> getGamePreviousEntriesSession = new Callback<GetGamePreviousEntriesResponse>() {

        @Override
        public void onResponse(Call<GetGamePreviousEntriesResponse> call, Response<GetGamePreviousEntriesResponse> response) {
            ResponseBody errorBody = response.errorBody();

            mSwipeRefreshLayout.setRefreshing(false);

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    isloadmore = response.body().getGameHistoryList().size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    if (response.body().getGameHistoryList().size() > 0) {

                        gameHistoryList.addAll(response.body().getGameHistoryList());
                        //                    mAdapter.update(response.body().getGameHistoryList());
                        mStickyAdapter.update(response.body().getGameHistoryList());
                        emptyLayout.setVisibility(View.GONE);
                        viewarchivelayoutv2.setVisibility(View.GONE);
                        viewarchivelayout.setVisibility(View.VISIBLE);

                    }

                    if (gameHistoryList.size() == 0) {

                        emptyLayout.setVisibility(View.VISIBLE);
                        viewarchivelayoutv2.setVisibility(View.VISIBLE);
                        viewarchivelayout.setVisibility(View.GONE);

                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetGamePreviousEntriesResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public void onRefresh() {

        viewarchivelayoutv2.setVisibility(View.GONE);
        viewarchivelayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(true);

        gameHistoryList.clear();
//        mAdapter.clear();
        mStickyAdapter.clear();

        isfirstload = false;
        limit = 0;
        isbottomscroll = false;
        isloadmore = true;
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        getSession();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewarchivev2: {
                if (viewarchivev2.getText().toString().equals(getString(R.string.string_view_archive))) {

                    mViewArchiveDialog.show();

                } else if (viewarchivev2.getText().toString().equals(getString(R.string.string_filter_options))) {

                    mFilterOptionDialog.show();

                }
                break;
            }
            case R.id.viewarchive: {
                if (viewarchive.getText().toString().equals(getString(R.string.string_view_archive))) {

                    mViewArchiveDialog.show();

                } else if (viewarchive.getText().toString().equals(getString(R.string.string_filter_options))) {

                    mFilterOptionDialog.show();

                }
                break;
            }
            case R.id.edtYear: {
                new MaterialDialog.Builder(getViewContext())
                        .title("Select Year")
                        .items(YEARS)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                year = Integer.parseInt(text.toString());
                                MONTHS.clear();
                                int minMonth = year == MIN_YEAR ? MIN_MONTH : 1;
                                MONTHS = setUpMonth(minMonth);
                                edtYear.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", text.toString()));
                            }
                        })
                        .show();
                break;
            }
            case R.id.edtMonth: {
                new MaterialDialog.Builder(getViewContext())
                        .title("Select Month")
                        .items(MONTHS)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                month = Integer.valueOf(CommonFunctions.getMonthNumber(text.toString()));
                                edtMonth.setText(text.toString());
                            }
                        })
                        .show();
                break;
            }
            case R.id.txvDateClose: {
                mViewArchiveDialog.dismiss();
                break;
            }
            case R.id.txvFilter: {
                if (!edtMonth.getText().toString().isEmpty() &&
                        !edtYear.getText().toString().isEmpty()) {
                    mViewArchiveDialog.dismiss();

                    limit = 0;

                    if (viewarchivev2.getText().toString().equals(getString(R.string.string_view_archive))) {
                        viewarchivev2.setText(getString(R.string.string_filter_options));
                    }

                    if (viewarchive.getText().toString().equals(getString(R.string.string_view_archive))) {
                        viewarchive.setText(getString(R.string.string_filter_options));
                    }

                    viewarchivelayoutv2.setVisibility(View.GONE);
                    viewarchivelayout.setVisibility(View.GONE);

                    gameHistoryList.clear();
//                    mAdapter.clear();
                    mStickyAdapter.clear();

                    getSession();

                }
                break;
            }
            case R.id.txvEdtSearches: {
                mFilterOptionDialog.dismiss();
                mViewArchiveDialog.show();
                break;
            }
            case R.id.txvClearSearches: {

                edtMonth.setText("");
                edtYear.setText("");

                mFilterOptionDialog.dismiss();
                viewarchivelayoutv2.setVisibility(View.GONE);
                viewarchivelayout.setVisibility(View.GONE);

                gameHistoryList.clear();
//                mAdapter.clear();
                mStickyAdapter.clear();

                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;

                getSession();

                viewarchive.setText(getString(R.string.string_view_archive));
                viewarchivev2.setText(getString(R.string.string_view_archive));

                break;
            }
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
            case R.id.refresh: {
                getSession();
                break;
            }
        }
    }

    private void setUpViewArchiveDialog() {
        mViewArchiveDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_date_view_archive, false)
                .build();

        //SET UP MONTH
        MONTHS = setUpMonth(MIN_MONTH);

        //SET UP YEAR
        YEARS = setUpYear(MIN_YEAR);

        View view = mViewArchiveDialog.getCustomView();
        TextView txvDateClose = (TextView) view.findViewById(R.id.txvDateClose);
        txvDateClose.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "CANCEL"));
        txvDateClose.setOnClickListener(this);
        TextView txvFilter = (TextView) view.findViewById(R.id.txvFilter);
        txvFilter.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "FILTER"));
        txvFilter.setOnClickListener(this);

        edtMonth = (MaterialEditText) view.findViewById(R.id.edtMonth);
        edtMonth.setVisibility(View.VISIBLE);
        edtMonth.setOnClickListener(this);
        edtYear = (MaterialEditText) view.findViewById(R.id.edtYear);
        edtYear.setOnClickListener(this);
    }

    private void setUpFilterOptions() {
        mFilterOptionDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_date_filter_options, false)
                .build();
        View view = mFilterOptionDialog.getCustomView();
        TextView txvEdtSearches = (TextView) view.findViewById(R.id.txvEdtSearches);
        txvEdtSearches.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "EDIT SEARCHES"));
        txvEdtSearches.setOnClickListener(this);
        TextView txvClearSearches = (TextView) view.findViewById(R.id.txvClearSearches);
        txvClearSearches.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Regular.ttf", "CLEAR SEARCHES"));
        txvClearSearches.setOnClickListener(this);
    }

    private List<String> setUpYear(int minYear) {
        List<String> mYears = new ArrayList<>();
        int currYear = Calendar.getInstance().get(Calendar.YEAR);

        int numYears = currYear - minYear;

        for (int i = 0; i <= numYears; i++) {
            mYears.add(String.valueOf(minYear));
            minYear = minYear + 1;
        }

        return mYears;
    }

    private List<String> setUpMonth(int minMonth) {
        //==================================
        // DateFormatSymbols().getMonths()
        // - Gets months strings.
        // e.g ( ["January","February","March","April","May","June","July","August","September","October","November","December"] )
        //==================================

        String[] months = new DateFormatSymbols().getMonths();
        List<String> mMonths = new ArrayList<>();
        mMonths.addAll(Arrays.asList(months).subList(minMonth - 1, months.length));

        return mMonths;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        GameHistory gameHistory = gameHistoryList.get(position);

        Bundle args = new Bundle();
        args.putParcelable("GAMEP2SOBJ", gameHistory);
        args.putString("AMOUNT", String.valueOf(gameHistory.getAmount()));
        args.putString("SERVICECHARGE", String.valueOf(gameHistory.getServiceCharge()));
        args.putString("AMOUNTPAID", String.valueOf(gameHistory.getAmountPaid()));
        args.putString("TYPE", "HISTORY");
        args.putString("P2SCOMBINATION", String.valueOf(gameHistory.getEntryNumber()));
        args.putString("P2SNAME", gameHistory.getName());
        args.putString("P2SACCOUNTNUMBER", gameHistory.getAccountID());

        ((ProjectCoopActivity) getViewContext()).displayView(3, args);

    }
}
