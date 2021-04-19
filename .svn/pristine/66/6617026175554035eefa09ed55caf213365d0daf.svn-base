package com.goodkredit.myapplication.fragments.paramount;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceActivity;
import com.goodkredit.myapplication.adapter.paramount.ParamountVehicleDialogAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.bean.ParamountVehicleSeriesMaker;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.paramount.GetVehicleMakerResponse;
import com.goodkredit.myapplication.responses.paramount.GetVehicleSeriesResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 11/29/2017.
 */

public class VehicleTypeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private DatabaseHandler mdb;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private String currentyear;
    private String requestID;
    private String vehicleID;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<String> arrYears;
    private List<ParamountVehicleSeriesMaker> arrMakers;
    private List<ParamountVehicleSeriesMaker> arrSeries;

    private EditText edtYearModel;
    private EditText edtMaker;
    private EditText edtSeries;
    private EditText edtColor;

    private String maker;

    private boolean isSeriescall = false;
    private boolean isSearchedCall = false;
    private String searchKeyword = "";

    private NestedScrollView nested_scroll_dialog;

    private SearchView searchView;
    private MaterialDialog mParamountDialog;
    private TextView txvDialogTitle;
    private ParamountVehicleDialogAdapter mParamountDialogAdapter;

    private boolean isMaker = false;
    private boolean isSeries = false;

    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private int limit = 0;

    private ProgressBar progressBar;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_type, container, false);

        setHasOptionsMenu(true);

//        try {

        init(view);

        initData();

//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

        isloadmore = false;
        isfirstload = true;
        isLoading = false;

        if (mdb != null) {

            ParamountQueue paramountQueue = mdb.getParamountQueue(mdb);

            if (paramountQueue != null) {

                if (paramountQueue.getYearModel() != null) {
                    edtYearModel.setText(paramountQueue.getYearModel());
                }

                if (paramountQueue.getColor() != null) {
                    edtColor.setText(paramountQueue.getColor());
                }

                if (paramountQueue.getVehicleMaker() != null) {
                    maker = paramountQueue.getVehicleMaker();
                }

                if (paramountQueue.getVehicleMaker() != null) {
                    edtMaker.setText(paramountQueue.getVehicleMaker());
                }

                if (paramountQueue.getSeries() != null) {
                    edtSeries.setText(paramountQueue.getSeries());
                    edtSeries.setEnabled(true);
                }

            }

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                hideKeyboard(getViewContext());

                String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET VehicleID=?, YearModel=?, VehicleMaker=?, Series=?, Color=? WHERE RequestID=?";
                String[] whereArgs = new String[]{vehicleID,
                        edtYearModel.getText().toString(),
                        edtMaker.getText().toString(),
                        edtSeries.getText().toString(),
                        edtColor.getText().toString().trim(),
                        requestID};

                mdb.updateParamountQueue(mdb, strsql, whereArgs);

                Bundle args = new Bundle();
                args.putString("currentyear", currentyear);
                args.putString("generatedid", requestID);

                ((ParamountInsuranceActivity) getViewContext()).displayView(1, args);

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(View view) {
        view.findViewById(R.id.btnNext).setOnClickListener(this);
        edtMaker = (EditText) view.findViewById(R.id.edtMaker);
        edtMaker.setOnClickListener(this);
        edtSeries = (EditText) view.findViewById(R.id.edtSeries);
        edtSeries.setOnClickListener(this);
        edtYearModel = (EditText) view.findViewById(R.id.edtYearModel);
        edtYearModel.setOnClickListener(this);
        edtColor = (EditText) view.findViewById(R.id.edtColor);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        arrMakers = new ArrayList<>();
        arrSeries = new ArrayList<>();
        arrYears = new ArrayList<>();
        arrYears = mdb.getYearModel(mdb);

        currentyear = getArguments().getString("currentyear");
        requestID = getArguments().getString("generatedid");

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        setUpMakerDialog();

        getSession();
    }

    private void setUpYearsArray() {
        try {
            arrYears.clear();

            if (currentyear == null) {

                currentyear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

            }

            for (int i = Integer.parseInt(currentyear); i >= 1960; i--) {

                arrYears.add(Integer.toString(i));

            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setUpMakerDialog() {
        mParamountDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_vehicle_types, false)
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        isMaker = false;
                        isSeries = false;
                    }
                })
                .build();
        View view = mParamountDialog.getCustomView();

        progressBar = (ProgressBar) view.findViewById(R.id.progress);

        txvDialogTitle = (TextView) view.findViewById(R.id.txvDialogTitle);
        txvDialogTitle.setText("Maker");

        searchView = (SearchView) view.findViewById(R.id.searchbox);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setOnSearchClickListener(this);

        RecyclerView recyclerViewDialog = (RecyclerView) view.findViewById(R.id.recycler_view_dialog_vehicle_types);
        recyclerViewDialog.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerViewDialog.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerViewDialog.setNestedScrollingEnabled(false);
        mParamountDialogAdapter = new ParamountVehicleDialogAdapter(getViewContext());
        recyclerViewDialog.setAdapter(mParamountDialogAdapter);
        mParamountDialogAdapter.clear();

        mParamountDialogAdapter.setOnItemClickListener(new ParamountVehicleDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, ParamountVehicleSeriesMaker paramountVehicleSeriesMaker) {

                if (isMaker) {

                    maker = paramountVehicleSeriesMaker.getMaker();
                    edtMaker.setText(maker);

                    edtSeries.setEnabled(true);
                    edtSeries.setText("");

                    arrSeries.clear();

                    limit = 0;
                    isSeriescall = true;
                    isSearchedCall = false;
                    getSession();

                } else if (isSeries) {

                    edtSeries.setText(paramountVehicleSeriesMaker.getSeries());
                    vehicleID = paramountVehicleSeriesMaker.getVehicleID();

                }

                mParamountDialog.dismiss();

            }
        });

        nested_scroll_dialog = (NestedScrollView) view.findViewById(R.id.nested_scroll);

        nested_scroll_dialog.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    //Logger.debug("antonhttp", "Scroll DOWN");
                    mSwipeRefreshLayout.setEnabled(false);
                }
                if (scrollY < oldScrollY) {
                    //Logger.debug("antonhttp", "Scroll UP");
                    mSwipeRefreshLayout.setEnabled(false);
                }

                if (scrollY == 0) {
                    //Logger.debug("antonhttp", "TOP SCROLL");
                    mSwipeRefreshLayout.setEnabled(true);
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    mSwipeRefreshLayout.setEnabled(false);

                    Logger.debug("antonhttp", "PROGRESSIVE LOADING CALLED!");

                    isbottomscroll = true;
                    if (isloadmore) {

                        if (!isfirstload) {
                            limit = limit + 30;
                        }

                        new Thread(new Runnable() {
                            public void run() {
                                // a potentially  time consuming task

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        getSession();

                                    }

                                });

                            }
                        }).start();

                    }

                }
            }
        });

    }

    private void openYearModelDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                .title("Year Model")
                .items(arrYears)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                        edtYearModel.setText(text.toString());
                    }
                })
                .show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
    }

    private void openMakerDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title("Maker")
                .items(arrMakers)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                        maker = text.toString();
                        edtMaker.setText(maker);
                        edtSeries.setEnabled(true);
                        edtSeries.setText("");
                        isSeriescall = true;
                        getSession();
                    }
                })
                .show();
    }

    private void openSeriesDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title("Series")
                .items(arrSeries)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                        edtSeries.setText(text.toString());
                    }
                })
                .show();
    }

    @Override
    public void onRefresh() {

        mSwipeRefreshLayout.setRefreshing(true);
        mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_VEHICLE_MAKERS);

        isSearchedCall = false;
        isSeriescall = false;
        isfirstload = false;
        isbottomscroll = false;
        isMaker = false;
        isSeries = false;
        limit = 0;
        getSession();

    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching vehicles.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.requestFocus();

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isSearchedCall) {
                if (isMaker) {
                    getSearchedVehicleMakerV2(getSearchedVehicleMakerV2Session);
                } else if (isSeries) {
                    getSearchedVehicleSeriesV2(getSearchedVehicleSeriesV2Session);
                }
            } else {
                if (!isSeriescall) {
                    getVehicleMakerV2(getVehicleMakerV2Session);
                } else {
                    getVehicleSeriesV2(getVehicleSeriesV2Session);
                }
            }

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }

    }

//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    progressBar.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    progressBar.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    progressBar.setVisibility(View.GONE);
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isSearchedCall) {
//                        if (isMaker) {
//                            getSearchedVehicleMakerV2(getSearchedVehicleMakerV2Session);
//                        } else if (isSeries) {
//                            getSearchedVehicleSeriesV2(getSearchedVehicleSeriesV2Session);
//                        }
//                    } else {
//                        if (!isSeriescall) {
//                            getVehicleMakerV2(getVehicleMakerV2Session);
//                        } else {
//                            getVehicleSeriesV2(getVehicleSeriesV2Session);
//                        }
//                    }
//
//                }
//            } else {
//                progressBar.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setRefreshing(false);
//                hideProgressDialog();
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            progressBar.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setRefreshing(false);
//            hideProgressDialog();
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getVehicleSeries(Callback<GetVehicleSeriesResponse> getVehicleSeriesCallback) {
        Call<GetVehicleSeriesResponse> getvehicleseries = RetrofitBuild.getVehicleSeriesService(getViewContext())
                .getVehicleSeriesCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        maker);
        getvehicleseries.enqueue(getVehicleSeriesCallback);
    }

    private final Callback<GetVehicleSeriesResponse> getVehicleSeriesSession = new Callback<GetVehicleSeriesResponse>() {

        @Override
        public void onResponse(Call<GetVehicleSeriesResponse> call, Response<GetVehicleSeriesResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    arrSeries.clear();

                    for (ParamountVehicleSeriesMaker series : response.body().getVehicleSeries()) {
                        if (!arrSeries.contains(series)) {
                            arrSeries.add(series);
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
        public void onFailure(Call<GetVehicleSeriesResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getVehicleSeriesV2(Callback<GetVehicleSeriesResponse> getVehicleSeriesCallback) {
        Call<GetVehicleSeriesResponse> getvehicleseries = RetrofitBuild.getVehicleSeriesService(getViewContext())
                .getVehicleSeriesV2Call(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        maker,
                        String.valueOf(limit));
        getvehicleseries.enqueue(getVehicleSeriesCallback);
    }

    private final Callback<GetVehicleSeriesResponse> getVehicleSeriesV2Session = new Callback<GetVehicleSeriesResponse>() {

        @Override
        public void onResponse(Call<GetVehicleSeriesResponse> call, Response<GetVehicleSeriesResponse> response) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    //arrSeries.clear();

                    List<ParamountVehicleSeriesMaker> paramountVehicleSeries = response.body().getVehicleSeries();

                    isloadmore = paramountVehicleSeries.size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    for (ParamountVehicleSeriesMaker series : paramountVehicleSeries) {
                        arrSeries.add(series);
                    }

                    mParamountDialogAdapter.clear();
                    mParamountDialogAdapter.setDialogData(arrSeries, false);

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetVehicleSeriesResponse> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getSearchedVehicleSeriesV2(Callback<GetVehicleSeriesResponse> getVehicleSeriesCallback) {
        Call<GetVehicleSeriesResponse> getvehicleseries = RetrofitBuild.getVehicleSeriesService(getViewContext())
                .getSearchedVehicleSeriesV2Call(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        maker,
                        searchKeyword,
                        String.valueOf(limit));
        getvehicleseries.enqueue(getVehicleSeriesCallback);
    }

    private final Callback<GetVehicleSeriesResponse> getSearchedVehicleSeriesV2Session = new Callback<GetVehicleSeriesResponse>() {

        @Override
        public void onResponse(Call<GetVehicleSeriesResponse> call, Response<GetVehicleSeriesResponse> response) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    List<ParamountVehicleSeriesMaker> paramountVehicleSeries = response.body().getVehicleSeries();

                    isloadmore = paramountVehicleSeries.size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    if (paramountVehicleSeries.size() > 0) {
                        for (ParamountVehicleSeriesMaker series : paramountVehicleSeries) {
                            if (!arrSeries.contains(series)) {
                                arrSeries.add(series);
                            }
                        }

                        mParamountDialogAdapter.clear();
                        mParamountDialogAdapter.setDialogData(arrSeries, false);
                    }

                    if (limit == 0) {
                        if (arrSeries.size() == 0) {
                            mParamountDialogAdapter.clear();
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
        public void onFailure(Call<GetVehicleSeriesResponse> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getVehicleMaker(Callback<GetVehicleMakerResponse> getVehicleMakerCallback) {
        Call<GetVehicleMakerResponse> getvehiclemaker = RetrofitBuild.getVehicleMakerService(getViewContext())
                .getVehicleMakerCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode);
        getvehiclemaker.enqueue(getVehicleMakerCallback);
    }

    private final Callback<GetVehicleMakerResponse> getVehicleMakerSession = new Callback<GetVehicleMakerResponse>() {

        @Override
        public void onResponse(Call<GetVehicleMakerResponse> call, Response<GetVehicleMakerResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                if (response.body().getStatus().equals("000")) {

                    arrMakers.clear();

                    for (ParamountVehicleSeriesMaker maker : response.body().getVehicleMaker()) {
                        arrMakers.add(maker);
                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetVehicleMakerResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getVehicleMakerV2(Callback<GetVehicleMakerResponse> getVehicleMakerCallback) {
        Call<GetVehicleMakerResponse> getvehiclemaker = RetrofitBuild.getVehicleMakerService(getViewContext())
                .getVehicleMakerV2Call(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(limit));
        getvehiclemaker.enqueue(getVehicleMakerCallback);
    }

    private final Callback<GetVehicleMakerResponse> getVehicleMakerV2Session = new Callback<GetVehicleMakerResponse>() {

        @Override
        public void onResponse(Call<GetVehicleMakerResponse> call, Response<GetVehicleMakerResponse> response) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                if (response.body().getStatus().equals("000")) {

                    List<ParamountVehicleSeriesMaker> paramountVehicleSeriesMakers = response.body().getVehicleMaker();

                    isloadmore = paramountVehicleSeriesMakers.size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_HISTORY);
                    }

                    if (paramountVehicleSeriesMakers.size() > 0) {
                        for (ParamountVehicleSeriesMaker paramountVehicleSeriesMaker : paramountVehicleSeriesMakers) {

                            if (!mdb.isMakerExist(mdb, paramountVehicleSeriesMaker.getMaker())) {
                                mdb.insertParamountVehicleMaker(mdb, paramountVehicleSeriesMaker);
                            }

                        }
                    }

                    arrMakers.clear();
                    arrMakers = mdb.getVehicleMakers(mdb);

                    mParamountDialogAdapter.clear();
                    mParamountDialogAdapter.setDialogData(arrMakers, true);

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetVehicleMakerResponse> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getSearchedVehicleMakerV2(Callback<GetVehicleMakerResponse> getVehicleMakerCallback) {
        Call<GetVehicleMakerResponse> getvehiclemaker = RetrofitBuild.getVehicleMakerService(getViewContext())
                .getSearchedVehicleMakerV2Call(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        searchKeyword,
                        String.valueOf(limit));
        getvehiclemaker.enqueue(getVehicleMakerCallback);
    }

    private final Callback<GetVehicleMakerResponse> getSearchedVehicleMakerV2Session = new Callback<GetVehicleMakerResponse>() {

        @Override
        public void onResponse(Call<GetVehicleMakerResponse> call, Response<GetVehicleMakerResponse> response) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                if (response.body().getStatus().equals("000")) {

                    List<ParamountVehicleSeriesMaker> paramountVehicleSeriesMakers = response.body().getVehicleMaker();

                    isloadmore = paramountVehicleSeriesMakers.size() > 0;
                    isLoading = false;
                    isfirstload = false;

                    if (isSearchedCall) {
                        if (paramountVehicleSeriesMakers.size() < 30) {
                            isloadmore = false;
                        }
                    }

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, DatabaseHandler.SKYCABLE_PPV_HISTORY);
                    }

                    if (paramountVehicleSeriesMakers.size() > 0) {
                        for (ParamountVehicleSeriesMaker paramountVehicleSeriesMaker : paramountVehicleSeriesMakers) {

                            if (!mdb.isMakerExist(mdb, paramountVehicleSeriesMaker.getMaker())) {
                                mdb.insertParamountVehicleMaker(mdb, paramountVehicleSeriesMaker);
                            }

                        }
                    }

                    arrMakers.clear();
                    arrMakers = mdb.getVehicleMakers(mdb);

                    mParamountDialogAdapter.clear();
                    mParamountDialogAdapter.setDialogData(response.body().getVehicleMaker(), true);

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetVehicleMakerResponse> call, Throwable t) {
            progressBar.setVisibility(View.GONE);
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        isSearchedCall = true;
        searchKeyword = query;
        isSeriescall = false;
        isfirstload = false;
        isbottomscroll = false;
        isloadmore = false;
        limit = 0;

        if (isSeries) {
            arrSeries.clear();
        }

        getSession();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //queryText(newText);
        return false;
    }

    private void queryText(String query) {
        if (mParamountDialogAdapter != null) {
            if (query.length() > 0) {

                mParamountDialogAdapter.filter(query);

            } else {

                if (isMaker) {
                    mParamountDialogAdapter.clear();
                    mParamountDialogAdapter.setDialogData(arrMakers, true);
                } else if (isSeries) {
                    mParamountDialogAdapter.clear();
                    mParamountDialogAdapter.setDialogData(arrSeries, false);
                }

            }
        }
    }

    @Override
    public boolean onClose() {
        txvDialogTitle.setVisibility(View.VISIBLE);
        isSearchedCall = false;
        if (isMaker) {
            isSeriescall = false;
            limit = getLimit(arrMakers.size(), 30);
            arrMakers.clear();
            arrMakers = mdb.getVehicleMakers(mdb);
            mParamountDialogAdapter.clear();
            mParamountDialogAdapter.setDialogData(arrMakers, true);
            isMaker = true;
            isSeries = false;
        } else {
            isSeriescall = true;
            arrSeries.clear();
            isfirstload = false;
            isbottomscroll = false;
            isMaker = false;
            isSeries = true;
            limit = 0;
            getSession();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext: {

                if (edtYearModel.getText().toString().trim().length() > 0 &&
                        edtMaker.getText().toString().trim().length() > 0 &&
                        edtSeries.getText().toString().trim().length() > 0 &&
                        edtColor.getText().toString().trim().length() > 0) {

                    String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET VehicleID=?, YearModel=?, VehicleMaker=?, Series=?, Color=? WHERE RequestID=?";

                    String[] whereArgs = new String[]{vehicleID,
                            edtYearModel.getText().toString(),
                            edtMaker.getText().toString(),
                            edtSeries.getText().toString(),
                            edtColor.getText().toString().trim(),
                            requestID};

                    mdb.updateParamountQueue(mdb, strsql, whereArgs);

                    Bundle args = new Bundle();
                    args.putString("currentyear", currentyear);
                    args.putString("generatedid", requestID);

                    ((ParamountInsuranceActivity) getViewContext()).displayView(3, args);

                } else {

                    showError("Please input all required fields.");

                }

                break;
            }
            case R.id.edtMaker: {

                searchKeyword = "";

                if (mParamountDialog != null) {

                    limit = 0;

                    isSeriescall = false;

                    isMaker = true;

                    mParamountDialogAdapter.clear();

                    mParamountDialogAdapter.setDialogData(arrMakers, true);

                    mParamountDialog.show();

                    txvDialogTitle.setText("Maker");

                    searchView.setQuery("", false);

                    searchView.clearFocus();

                }

//                openMakerDialog();
                break;
            }
            case R.id.edtSeries: {

                searchKeyword = "";

                limit = 0;

                if (arrSeries.size() == 0) {
                    isSeriescall = true;
                    getSession();
                }

                if (mParamountDialog != null) {

                    isSeries = true;

                    mParamountDialogAdapter.clear();

                    mParamountDialogAdapter.setDialogData(arrSeries, false);

                    mParamountDialog.show();

                    txvDialogTitle.setText("Series");

                    searchView.setQuery("", false);

                    searchView.clearFocus();

                }

                break;
            }
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
            case R.id.edtYearModel: {

                if (arrYears.size() > 0) {

                    openYearModelDialog();

                } else {

                    showError("Invalid years data. Please refresh the page.");

                }

                break;
            }
            case R.id.searchbox: {

                isfirstload = true;
                limit = 0;
                searchKeyword = "";
                txvDialogTitle.setVisibility(View.GONE);

                break;
            }
        }
    }

    private class TestAsync extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            //Start showing progress bar here ...


            Logger.debug("antonhttp", "PRE EXECUTE");
        }

        protected Void doInBackground(Void... arg0) {
            // Do long running operation here ...

            Logger.debug("antonhttp", "DO BACKGROUND");

            if (mParamountDialog != null) {

                limit = 0;

                isSeriescall = false;

                isMaker = true;

                mParamountDialogAdapter.clear();

                mParamountDialogAdapter.setDialogData(arrMakers, true);

                mParamountDialog.show();

                txvDialogTitle.setText("Maker");

                searchView.setQuery("", false);

                searchView.clearFocus();

            }

            return null;
        }

        protected void onPostExecute(Void result) {
            // Start displaying data in your table view and dismiss the progress  bar

            Logger.debug("antonhttp", "POST EXECUTE");

        }
    }


}
