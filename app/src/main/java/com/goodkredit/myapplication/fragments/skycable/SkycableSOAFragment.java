package com.goodkredit.myapplication.fragments.skycable;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.adapter.skycable.ViewSkycableSOAAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableAccounts;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.responses.skycable.CallLinkSkycableAccountResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycableSOAResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycableSOAFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView txvSoa;
    private DatabaseHandler mdb;

    private boolean isLoading = false;
    private String skycableaccountno;
    private String skycableaccountname;
    private String mobileno;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private int limit = 0;

    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //DIALOG
    private MaterialDialog mDialog;
    private TextView txvMessage;

    private boolean isCallLink = false;

    private NestedScrollView nested_scroll;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private ViewSkycableSOAAdapter mAdapter;

    private List<SkycableAccounts> linkAccounts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_soa, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    private void init(View view) {
        if (isAdded()) {
//            Skycable
            getActivity().setTitle("SKYCABLE");
        }

        //swipe refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(getViewContext(), 2));
//        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new ViewSkycableSOAAdapter(getViewContext());
        recyclerView.setAdapter(mAdapter);

        txvSoa = (TextView) view.findViewById(R.id.txvSoa);
        txvSoa.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "STATEMENT OF ACCOUNT"));

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        nested_scroll = (NestedScrollView) view.findViewById(R.id.nested_scroll);
        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll DOWN");
                    mSwipeRefreshLayout.setEnabled(false);
                }
                if (scrollY < oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll UP");
                    mSwipeRefreshLayout.setEnabled(false);
                }

                if (scrollY == 0) {
//                    Logger.debug("antonhttp", "TOP SCROLL");
                    mSwipeRefreshLayout.setEnabled(true);
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Logger.debug("antonhttp", "======BOTTOM SCROLL=======");
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
        });

        //SETUP DIALOGS
        setUpStatusDialog();
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        V2Subscriber v2Subscriber = mdb.getSubscriber(mdb);
        mobileno = v2Subscriber.getMobileNumber();

        checkIfAccountExist();

        linkAccounts = new ArrayList<>();
        linkAccounts = mdb.getSkycableAccounts(mdb);
//
//        if (linkAccounts.size() == 0) {
//
//            new MaterialDialog.Builder(getViewContext())
//                    .title("Notice")
//                    .content("Your mobile number is not registered in any SKYCABLE account. Please link your account.")
//                    .cancelable(false)
//                    .negativeText("CANCEL")
//                    .positiveText("LINK")
//                    .negativeColor(getResources().getColor(R.color.color_C0C0C0))
//                    .positiveColor(getResources().getColor(R.color.color_24BDD9))
//                    .onPositive(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                            ((SkyCableActivity) getViewContext()).displayView(7, null);
//                        }
//                    })
//                    .dismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//
//                        }
//                    })
//                    .show();
//
//            //mobileno = linkAccounts.get(0).getMobileNo();
////            skycableaccountname = linkAccounts.get(0).getSkyCableAccountName();
////            skycableaccountno = linkAccounts.get(0).getSkyCableAccountNo();
//
//        }


//        if (linkAccounts.get(0).getStatus().equals("FORAPPROVAL")) {
//            isCallLink = true;
//        } else {
//            isCallLink = false;
//        }

        isCallLink = false;
        getSession();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_link, menu);
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//            if (isCallLink) {
//                mTvFetching.setText("Linking Account..");
//            } else {
//                mTvFetching.setText("Fetching SOA..");
//            }
//            mTvWait.setText(" Please wait...");
//            mLlLoader.setVisibility(View.VISIBLE);
//
//            isLoading = true;
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//        } else {
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showError(getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isLoading = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isCallLink) {
//                        linkSkycableAccount(linkSkycableAccountSession);
//                    } else {
//                        callSkycableSOA(getSkycableSOASession);
//                    }
//
//                }
//            } else {
//                isLoading = false;
//                mLlLoader.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setRefreshing(false);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            isLoading = true;

            if (isCallLink) {
                mTvFetching.setText("Linking Account..");
                linkSkycableAccount(linkSkycableAccountSession);
            } else {
                mTvFetching.setText("Fetching SOA..");
                callSkycableSOA(getSkycableSOASession);
            }
        } else {
            isLoading = false;
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void linkSkycableAccount(Callback<CallLinkSkycableAccountResponse> linkSkycableAccountCallback) {
        Call<CallLinkSkycableAccountResponse> linkskycableaccount = RetrofitBuild.callLinkSkycableAccountService(getViewContext())
                .callLinkSkycableAccountCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        skycableaccountno,
                        skycableaccountname,
                        mobileno);
        linkskycableaccount.enqueue(linkSkycableAccountCallback);
    }

    private final Callback<CallLinkSkycableAccountResponse> linkSkycableAccountSession = new Callback<CallLinkSkycableAccountResponse>() {

        @Override
        public void onResponse(Call<CallLinkSkycableAccountResponse> call, Response<CallLinkSkycableAccountResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    List<SkycableAccounts> skycableAccountslist = response.body().getSkycableAccounts();
                    List<SkycableSOA> skycableSOAList = response.body().getSkycableSOAList();

                    if (skycableAccountslist.get(0).getStatus().equals("ACTIVE")) {
                        isCallLink = false;
                    }

                    if (mDialog != null) {
                        mDialog.show();
                        txvMessage.setText(response.body().getMessage());
                    }

                    if (mdb != null) {
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_ACCOUNTS);
                        for (SkycableAccounts skycableAccounts : skycableAccountslist) {
                            mdb.insertSkycableAccounts(mdb, skycableAccounts);
                        }

                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_SOA);
                        for (SkycableSOA skycableSOA : skycableSOAList) {
                            mdb.insertSkycableSOA(mdb, skycableSOA);
                        }
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<CallLinkSkycableAccountResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void callSkycableSOA(Callback<GetSkycableSOAResponse> getSkycableSOACallback) {
        Call<GetSkycableSOAResponse> getskycablesoa = RetrofitBuild.getSkycableSOAService(getViewContext())
                .getSkycableSOACall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        mobileno,
                        String.valueOf(limit));
        getskycablesoa.enqueue(getSkycableSOACallback);
    }

    private final Callback<GetSkycableSOAResponse> getSkycableSOASession = new Callback<GetSkycableSOAResponse>() {

        @Override
        public void onResponse(Call<GetSkycableSOAResponse> call, Response<GetSkycableSOAResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    showNoInternetConnection(false);
                    isloadmore = response.body().getSkycableSOAList().size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_SOA);
                    }

                    List<SkycableSOA> skycableSOAList = response.body().getSkycableSOAList();
                    if (skycableSOAList.size() > 0) {
                        if (mdb != null) {
                            for (SkycableSOA skycableSOA : skycableSOAList) {
                                mdb.insertSkycableSOA(mdb, skycableSOA);
                            }
                        }
                    }

                    updateList(mdb.getSkycableSOA(mdb));

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycableSOAResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void updateList(List<SkycableSOA> data) {
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);

            mAdapter.update(data);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            mSwipeRefreshLayout.setVisibility(View.GONE);

            mAdapter.clear();
        }
    }

    private void checkIfAccountExist() {
        if (mdb != null) {
            if (mdb.getSkycableAccounts(mdb).size() > 0) {
                refresh.setVisibility(View.VISIBLE);
            } else {
                refresh.setVisibility(View.GONE);
            }
            updateList(mdb.getSkycableSOA(mdb));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                ((SkyCableActivity) getViewContext()).displayView(1, null);
                return true;
            }
            case R.id.link: {

                ((SkyCableActivity) getViewContext()).displayView(7, null);

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshnointernet: {
//                if (linkAccounts.get(0).getStatus().equals("ACTIVE")) {
//                    isCallLink = false;
//                } else {
//                    isCallLink = true;
//                }

                isCallLink = false;
                getSession();
                break;
            }
            case R.id.txvCloseDialog: {
                mDialog.dismiss();
                break;
            }
            case R.id.refresh: {
//                if (linkAccounts.get(0).getStatus().equals("ACTIVE")) {
//                    isCallLink = false;
//                } else {
//                    isCallLink = true;
//                }

                isCallLink = false;
                getSession();
                break;
            }
        }
    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            if (mAdapter != null) {
                mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_SOA);
                mAdapter.clear();
            }

            mSwipeRefreshLayout.setRefreshing(true);

            isfirstload = false;
            limit = 0;
            isbottomscroll = false;
            isloadmore = true;
            getSession();
        }
    }

    private void setUpStatusDialog() {
        mDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_skycable_link_account_status, false)
                .build();
        View view = mDialog.getCustomView();

        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        txvMessage = (TextView) view.findViewById(R.id.txvMessage);

    }
}
