package com.goodkredit.myapplication.fragments.paramount;

import android.annotation.SuppressLint;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.paramount.ParamountInsuranceActivity;
import com.goodkredit.myapplication.activities.paramount.ViewParamountHistoryActivity;
import com.goodkredit.myapplication.adapter.paramount.ParamountDialogAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.ParamountCharges;
import com.goodkredit.myapplication.bean.ParamountPolicyType;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.paramount.GetParamountChargesResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountPolicyTypeResponse;
import com.goodkredit.myapplication.responses.paramount.GetParamountVehicleTypeResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User-PC on 11/29/2017.
 */

public class PolicyTypeFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private EditText edtPolicyType;
    private EditText edtLtoMvType;
    private EditText edtRenewal;

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
    private String currentyear = "";
    private String requestID = "";
    private String vehicleTypeID;
    private String vehicleDescription;
    private String policytypeid = "";
    private String applicationtype = "";

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<String> arrPolicy;
    private List<String> arrLto;
    private List<String> arrApplicationType;

    private TextView txvAmount;

    private double Price = 0;
    private double AmountPaid = 0;
    private double OtherCharges = 0;

    private List<ParamountPolicyType> arrListPolicy;

//    private RadioGroup radioGroup;
//    private RadioButton radioButton;

    private View view;

    private boolean isParamountCharge = false;
    private boolean isVehicleType = false;

    private SearchView searchView;
    private MaterialDialog mParamountDialog;
    private TextView txvDialogTitle;
    private ParamountDialogAdapter mParamountDialogAdapter;

    private boolean isloadmore;
    private boolean isbottomscroll;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private int limit = 0;

    private TextView btn_view_history;

    private NestedScrollView nested_scroll;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_policy_type, container, false);

        setHasOptionsMenu(true);

        try {

            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        view.findViewById(R.id.btnNext).setOnClickListener(this);
        edtPolicyType = (EditText) view.findViewById(R.id.edtPolicyType);
        edtPolicyType.setOnClickListener(this);
        edtLtoMvType = (EditText) view.findViewById(R.id.edtLtoMvType);
        edtLtoMvType.setOnClickListener(this);
        edtRenewal = (EditText) view.findViewById(R.id.edtRenewal);
        edtRenewal.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        txvAmount = (TextView) view.findViewById(R.id.txvAmount);

        btn_view_history = (TextView) view.findViewById(R.id.btn_view_history);
        btn_view_history.setOnClickListener(this);

    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        arrPolicy = new ArrayList<>();
        arrLto = new ArrayList<>();
        arrApplicationType = new ArrayList<>();
        arrListPolicy = new ArrayList<>();
        limit = getLimit(mdb.getVehicleTypes(mdb, "").size(), 30);

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

        setUpYearsArray();

        setUpVehicleTypesDialog();

        getSession();

    }

    private void setUpYearsArray() {
        try {

            if (mdb.getYearModel(mdb).size() == 0) {

                currentyear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

                if (currentyear.length() > 0) {

                    for (int i = Integer.parseInt(currentyear); i >= 1960; i--) {

                        mdb.insertParamountYearModel(mdb, Integer.toString(i));

                    }

                } else {

                    Logger.debug("antonhttp", "YearModel: NO YEAR");

                }

            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setUpVehicleTypesDialog() {
        mParamountDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(true)
                .customView(R.layout.dialog_vehicle_types, false)
                .build();
        View view = mParamountDialog.getCustomView();

        txvDialogTitle = (TextView) view.findViewById(R.id.txvDialogTitle);
        txvDialogTitle.setText("LTO MV TYPES");

        searchView = (SearchView) view.findViewById(R.id.searchbox);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.setOnSearchClickListener(this);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_dialog_vehicle_types);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mParamountDialogAdapter = new ParamountDialogAdapter(getViewContext());
        recyclerView.setAdapter(mParamountDialogAdapter);

        mParamountDialogAdapter.setOnItemClickListener(new ParamountDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, String name) {
                vehicleTypeID = mdb.getVehicleTypeID(mdb, name);
                edtLtoMvType.setText(name);
                edtPolicyType.setText(mdb.getPolicyType(mdb, name));

                mParamountDialog.dismiss();

                if (edtRenewal.getText().toString().length() > 0 &&
                        edtLtoMvType.getText().toString().length() > 0) {
                    isParamountCharge = true;
                    getSession();
                }
            }
        });

        nested_scroll = (NestedScrollView) view.findViewById(R.id.nested_scroll);
        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll DOWN");
                }

                if (scrollY < oldScrollY) {
//                    Logger.debug("antonhttp", "Scroll UP");
                }

                if (scrollY == 0) {
//                    Logger.debug("antonhttp", "TOP SCROLL");
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
//                    Logger.debug("antonhttp", "======BOTTOM SCROLL=======");
//                    isbottomscroll = true;
//                    if (isloadmore) {
//                        if (!isfirstload) {
//                            limit = limit + 30;
//                        }
//
//                        isVehicleType = true;
//                        getSession();
//                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        currentyear = "";
        requestID = "";

        if (getArguments() != null) {
            currentyear = getArguments().getString("currentyear");
            requestID = getArguments().getString("generatedid");
        }

        if (mdb != null) {

            ParamountQueue paramountQueue = mdb.getParamountQueue(mdb);

            if (paramountQueue != null) {

                if (paramountQueue.getLTOMVType().length() > 0) {

                    if (paramountQueue.getRenewal() != null) {
                        applicationtype = paramountQueue.getRenewal();
                    }

                    if (paramountQueue.getPolicyTypeID() != null) {
                        policytypeid = paramountQueue.getPolicyTypeID();
                    }

                    if (paramountQueue.getVehicleTypeID() != null) {
                        vehicleTypeID = paramountQueue.getVehicleTypeID();
                    }

                    if (paramountQueue.getVehicleDescription() != null) {
                        vehicleDescription = paramountQueue.getVehicleDescription();
                    }

                }

                if(paramountQueue.getRenewal() != null){
                    edtRenewal.setText(paramountQueue.getRenewal());
                }

                if(paramountQueue.getPolicyType() != null){
                    edtPolicyType.setText(paramountQueue.getPolicyType());
                }

                if(paramountQueue.getLTOMVType() != null){
                    edtLtoMvType.setText(paramountQueue.getLTOMVType());
                }

                Price = paramountQueue.getPrice();
                OtherCharges = paramountQueue.getOtherCharges();
                AmountPaid = paramountQueue.getAmountPaid();

                txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(AmountPaid)));
            }

        }

    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            if (isParamountCharge) {
                mTvFetching.setText("Fetching lto mv types..");
            } else {
                mTvFetching.setText("Fetching policy types.");
            }

            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            isLoading = true;

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isVehicleType) {
                getParamountVehicleType(getParamountVehicleTypeSession);
            } else if (isParamountCharge) {
                getParamountCharges(getParamountChargeSession);
            } else {
                getParamountPolicyType(getParamountPolicyTypeSession);
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
//                    isLoading = false;
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    isLoading = false;
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isLoading = false;
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    hideProgressDialog();
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isVehicleType) {
//                        getParamountVehicleType(getParamountVehicleTypeSession);
//                    } else if (isParamountCharge) {
//                        getParamountCharges(getParamountChargeSession);
//                    } else {
//                        getParamountPolicyType(getParamountPolicyTypeSession);
//                    }
//                }
//            } else {
//                isLoading = false;
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
//            mSwipeRefreshLayout.setRefreshing(false);
//            hideProgressDialog();
//            mLlLoader.setVisibility(View.GONE);
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getParamountPolicyType(Callback<GetParamountPolicyTypeResponse> getParamountPolicyTypeCallback) {
        Call<GetParamountPolicyTypeResponse> getparamountpolicytype = RetrofitBuild.getParamountPolicyTypeService(getViewContext())
                .getParamountPolicyTypeCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode);
        getparamountpolicytype.enqueue(getParamountPolicyTypeCallback);
    }

    private final Callback<GetParamountPolicyTypeResponse> getParamountPolicyTypeSession = new Callback<GetParamountPolicyTypeResponse>() {

        @Override
        public void onResponse(Call<GetParamountPolicyTypeResponse> call, Response<GetParamountPolicyTypeResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    isloadmore = response.body().getLtoMVTypes().size() > 0;
                    showNoInternetConnection(false);
                    isLoading = false;
                    isfirstload = false;

                    if (mdb != null) {

                        arrListPolicy.clear();
                        arrPolicy.clear();
                        mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_POLICY_TYPE);
                        arrListPolicy = response.body().getPolicyRequest();
                        for (ParamountPolicyType policy : arrListPolicy) {
                            mdb.insertParamountPolicyType(mdb, policy);
                            arrPolicy.add(policy.getPolicyTypeName());
                        }

                        currentyear = response.body().getCurrentyear();

                        arrApplicationType.clear();
                        for (ParamountCharges applicationType : response.body().getApplicationTypes()) {
                            arrApplicationType.add(applicationType.getApplicationType());
                        }

                        arrLto.clear();

                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_CHARGES);
                        }

                        List<ParamountCharges> arrListLto = response.body().getLtoMVTypes();
                        for (ParamountCharges lto : arrListLto) {
                            mdb.insertParamountLTOMVType(mdb, lto);
                        }

                        arrLto = mdb.getVehicleTypes(mdb, policytypeid);

                        if (mParamountDialogAdapter != null && !isfirstload) {
                            mParamountDialogAdapter.setDialogData(arrLto);
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
        public void onFailure(Call<GetParamountPolicyTypeResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getParamountVehicleType(Callback<GetParamountVehicleTypeResponse> getParamountVehicleTypeCallback) {
        Call<GetParamountVehicleTypeResponse> getparamountvehicletypes = RetrofitBuild.getParamountPolicyTypeService(getViewContext())
                .getParamountVehicleTypeCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(limit));
        getparamountvehicletypes.enqueue(getParamountVehicleTypeCallback);
    }

    private final Callback<GetParamountVehicleTypeResponse> getParamountVehicleTypeSession = new Callback<GetParamountVehicleTypeResponse>() {

        @Override
        public void onResponse(Call<GetParamountVehicleTypeResponse> call, Response<GetParamountVehicleTypeResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    isVehicleType = false;
                    isloadmore = response.body().getLtoMVTypes().size() > 0;
                    showNoInternetConnection(false);
                    isLoading = false;
                    isfirstload = false;

                    if (mdb != null) {

                        arrLto.clear();

                        if (!isbottomscroll) {
                            mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_CHARGES);
                        }

                        List<ParamountCharges> arrListLto = response.body().getLtoMVTypes();
                        for (ParamountCharges lto : arrListLto) {
                            mdb.insertParamountLTOMVType(mdb, lto);
                        }

                        arrLto = mdb.getVehicleTypes(mdb, policytypeid);

                        if (mParamountDialogAdapter != null && !isfirstload) {
                            mParamountDialogAdapter.setDialogData(arrLto);
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
        public void onFailure(Call<GetParamountVehicleTypeResponse> call, Throwable t) {
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getParamountCharges(Callback<GetParamountChargesResponse> getParamountChargesCallback) {
        Call<GetParamountChargesResponse> getparamountcharges = RetrofitBuild.getParamountChargeService(getViewContext())
                .getParamountChargesCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        vehicleTypeID,
                        applicationtype);
        getparamountcharges.enqueue(getParamountChargesCallback);
    }

    private final Callback<GetParamountChargesResponse> getParamountChargeSession = new Callback<GetParamountChargesResponse>() {

        @Override
        public void onResponse(Call<GetParamountChargesResponse> call, Response<GetParamountChargesResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    showNoInternetConnection(false);

                    if (mdb != null) {

                        isParamountCharge = false;

                        List<ParamountCharges> paramountCharges = response.body().getLtoRequest();
                        ParamountCharges charge = paramountCharges.get(0);

                        Price = charge.getAmount();
                        OtherCharges = charge.getOtherCharges();
                        AmountPaid = charge.getAmount() + charge.getOtherCharges();
                        vehicleTypeID = charge.getVehicleTypeID();
                        vehicleDescription = charge.getVehicleDescription();

                        txvAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(AmountPaid)));

                        if (requestID != null) {

                            String strsql = "UPDATE " + DatabaseHandler.PARAMOUNT_QUEUE + " SET YearModel=?, VehicleMaker=?, Series=?, Color=? WHERE RequestID=?";

                            String[] whereArgs = new String[]{"",
                                    "",
                                    "",
                                    "",
                                    requestID};

                            mdb.updateParamountQueue(mdb, strsql, whereArgs);

                        }

                        nested_scroll.fullScroll(View.FOCUS_DOWN);

                    }

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetParamountChargesResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openRenewalDialog() {
        if (mdb != null) {
            MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                    .title("Term Condition")
                    .items(arrApplicationType)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {
                            applicationtype = text.toString();
                            edtRenewal.setText(text.toString());
                            txvAmount.setText("0.00");

                            if (edtRenewal.getText().toString().length() > 0 &&
                                    edtLtoMvType.getText().toString().length() > 0) {
                                isParamountCharge = true;
                                getSession();
                            }

                        }
                    })
                    .show();

            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
        }
    }

    private void openPolicyTypeDialog() {
        if (mdb != null) {
            MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                    .title("Policy Type")
                    .items(arrPolicy)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View view, int position, CharSequence text) {

                            ParamountPolicyType policyType = mdb.getParamountPolicyType(mdb).get(position);
                            policytypeid = policyType.getPolicyTypeID();
                            edtPolicyType.setText(text.toString());
                            edtLtoMvType.setText("");
                            txvAmount.setText("00.00");

                            arrLto.clear();

                            List<ParamountCharges> arrListLto = mdb.getParamountLTOMVTypeByID(mdb, policytypeid);

                            if (arrListLto.size() > 0) {

                                arrLto = mdb.getVehicleTypesByID(mdb, policytypeid);

                                if (mParamountDialogAdapter != null) {

                                    mParamountDialogAdapter.clear();

                                    mParamountDialogAdapter.setDialogData(arrLto);

                                }

                            }

                        }
                    })
                    .show();

            V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        Price = 0;
        OtherCharges = 0;
        //AmountPaid = 0;
        currentyear = "";
        isParamountCharge = false;
        isVehicleType = false;
        isfirstload = false;
        limit = 0;
        isbottomscroll = false;
        isloadmore = false;

        if (mdb != null) {
            mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_CHARGES);
        }

        getSession();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_view_history: {

                ViewParamountHistoryActivity.start(getViewContext());

                break;
            }
            case R.id.refreshnointernet: {

                getSession();

                break;
            }
            case R.id.edtPolicyType: {
                if (arrPolicy.size() > 0) {

                    openPolicyTypeDialog();

                } else {

                    showError("No Policy types found. Please refresh the page.");

                }
                break;
            }
            case R.id.edtLtoMvType: {

                if (arrLto.size() > 0) {

                    if (mParamountDialog != null) {

                        mParamountDialogAdapter.clear();

                        Logger.debug("antonhttp", "arrLto: " + new Gson().toJson(arrLto));

                        mParamountDialogAdapter.setDialogData(arrLto);

                        mParamountDialog.show();

                        searchView.setQuery("", false);

                        searchView.clearFocus();

                    }

                } else {

                    showError("No LTO MV types found. Please refresh the page.");

                }

                break;
            }
            case R.id.edtRenewal: {
                if (arrApplicationType.size() > 0) {

                    openRenewalDialog();

                } else {

                    showError("No Term Condition data found. Please refresh the page.");

                }
                break;
            }
            case R.id.searchbox: {

                txvDialogTitle.setVisibility(View.GONE);

                break;
            }
            case R.id.btnNext: {

                if (AmountPaid == 0) {

                    showError("Invalid Amount");

                } else {

                    if (edtRenewal.getText().toString().length() > 0 &&
                            edtPolicyType.getText().toString().length() > 0 &&
                            edtLtoMvType.getText().toString().length() > 0) {

                        if (requestID == null) {
                            Random random = new Random();
                            requestID = String.format("%04d", random.nextInt(10000));
                        }

                        ParamountQueue paramountQueue = mdb.getParamountQueue(mdb);

                        mdb.truncateTable(mdb, DatabaseHandler.PARAMOUNT_QUEUE);

                        if (paramountQueue != null) {
                            mdb.insertParamountQueue(mdb, new ParamountQueue(requestID,
                                    vehicleTypeID,
                                    edtLtoMvType.getText().toString(),
                                    Price,
                                    OtherCharges,
                                    AmountPaid,
                                    edtRenewal.getText().toString(),
                                    policytypeid,
                                    edtPolicyType.getText().toString(),
                                    edtLtoMvType.getText().toString(),
                                    paramountQueue.getYearModel(),
                                    paramountQueue.getVehicleMaker(),
                                    paramountQueue.getSeries(),
                                    paramountQueue.getColor(),
                                    paramountQueue.getMVFileNumber(),
                                    paramountQueue.getPlateNumber(),
                                    paramountQueue.getEngineMotorNumber(),
                                    paramountQueue.getSerial(),
                                    paramountQueue.getType(),
                                    paramountQueue.getCorporateName(),
                                    paramountQueue.getFirstName(),
                                    paramountQueue.getMiddleName(),
                                    paramountQueue.getLastName(),
                                    paramountQueue.getHouseNumber(),
                                    paramountQueue.getStreetName(),
                                    paramountQueue.getBuildingName(),
                                    paramountQueue.getBarangay(),
                                    paramountQueue.getProvince(),
                                    paramountQueue.getMunicipality(),
                                    paramountQueue.getMobileNumber(),
                                    paramountQueue.getTelephoneNo(),
                                    paramountQueue.getEmailAddress(),
                                    paramountQueue.getZipcode(),
                                    paramountQueue.getVehicleID(),
                                    ".",
                                    "."));
                        } else {
                            mdb.insertParamountQueue(mdb, new ParamountQueue(requestID,
                                    Price,
                                    OtherCharges,
                                    AmountPaid,
                                    policytypeid,
                                    edtPolicyType.getText().toString(),
                                    edtLtoMvType.getText().toString(),
                                    edtRenewal.getText().toString(),
                                    vehicleTypeID,
                                    edtLtoMvType.getText().toString()));
                        }

                        Bundle args = new Bundle();
                        args.putString("currentyear", currentyear);
                        args.putString("generatedid", requestID);

                        ((ParamountInsuranceActivity) getViewContext()).displayView(2, args);

                    } else {

                        showError("Please input all required fields.");

                    }

                }

                break;
            }
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        queryText(newText);
        return false;
    }

    private void queryText(String query) {
        if (mParamountDialogAdapter != null) {
            if (query.length() > 0) {

                mParamountDialogAdapter.filter(query);

            } else {

                arrLto.clear();
                arrLto = mdb.getVehicleTypes(mdb, policytypeid);
                mParamountDialogAdapter.clear();
                mParamountDialogAdapter.setDialogData(arrLto);

            }
        }
    }

    @Override
    public boolean onClose() {
        txvDialogTitle.setVisibility(View.VISIBLE);
        return false;
    }
}
