package com.goodkredit.myapplication.fragments.uno;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.prepaidrequest.VoucherPrepaidRequestActivity;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.fragments.prepaidrequest.VirtualVoucherProductFragment;
import com.goodkredit.myapplication.model.UnoPointsConversion;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.uno.GetUNOPointsConversionResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnoRewardsFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mNonMemberLayout;
    private RelativeLayout mFaileConnectLayout;
    private LinearLayout mMemberLayout;

    private TextView tvPoints;
    private TextView tv_conversion;

    private String sessionid = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static UnoRewardsFragment newInstance() {
        UnoRewardsFragment fragment = new UnoRewardsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uno_rewards, container, false);

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        tvPoints = (TextView) view.findViewById(R.id.tv_points);
        tv_conversion = (TextView) view.findViewById(R.id.tv_conversion);
        mNonMemberLayout = (RelativeLayout) view.findViewById(R.id.non_member_layout);
        mFaileConnectLayout = (RelativeLayout) view.findViewById(R.id.failed_to_connect);
        mMemberLayout = (LinearLayout) view.findViewById(R.id.points_layout);

        view.findViewById(R.id.refresh_points).setOnClickListener(this);
        view.findViewById(R.id.btn_convert_to_voucher).setOnClickListener(this);
        view.findViewById(R.id.btn_view_history).setOnClickListener(this);

        getSession();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh_points: {
                getSession();
//                getUNOPointsConversion();
                break;
            }
            case R.id.btn_convert_to_voucher: {
                if (mPoints > 0) {
                    Logger.debug("OKHttp", "mPoints" + String.valueOf(mPoints));
                    VoucherPrepaidRequestActivity.start(getViewContext(), 0, VirtualVoucherProductFragment.BY_UNO_REWARDS, mPoints);
                } else
                    showError("You do not have any points to convert. Thank you.");
                break;
            }
            case R.id.btn_view_history: {
                ViewOthersTransactionsActivity.start(getViewContext(), 8);
                break;
            }
        }
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) == 0) {
//            showError("Please check your network connection and try again. Thank you.");
//        } else {
//            showProgressDialog("", "Please wait...");
//            createSession(getSessionCallback);
//            getUNOPointsConversion();
//        }
//    }
//
//    private Callback<String> getSessionCallback = new Callback<String>() {
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            try {
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    if (!response.body().isEmpty()
//                            && !response.body().contains("<!DOCTYPE html>")
//                            && !response.body().equals("001")
//                            && !response.body().equals("002")
//                            && !response.body().equals("003")
//                            && !response.body().equals("004")
//                            && !response.body().equals("error")) {
//                        sessionid = response.body();
//                        getUnoMemberPoints();
//                    } else {
//                        hideProgressDialog();
//                        showError();
//                    }
//                } else {
//                    hideProgressDialog();
//                    showError();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            hideProgressDialog();
//            showError();
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("", "Please wait...");
            getUnoMemberPoints();
            getUNOPointsConversion();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getUnoMemberPoints() {
        Call<GenericResponse> call = RetrofitBuild.getUnoRewardsAPIService(getViewContext())
                .getUnoMemberPoints(
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        userid,
                        borrowerid,
                        sessionid
                );
        call.enqueue(getUnoMemberPointsCallback);
    }

    private Callback<GenericResponse> getUnoMemberPointsCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            try {
                if (response.body().getStatus().equals("000")) {
                    mNonMemberLayout.setVisibility(View.GONE);
                    mFaileConnectLayout.setVisibility(View.GONE);
                    mMemberLayout.setVisibility(View.VISIBLE);
                    setTvPoints(response.body().getData());
                    mPoints = Double.parseDouble(response.body().getData());
                } else if (response.body().getStatus().equals("005")) {
                    mNonMemberLayout.setVisibility(View.VISIBLE);
                    mFaileConnectLayout.setVisibility(View.GONE);
                    mMemberLayout.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            mNonMemberLayout.setVisibility(View.GONE);
            mFaileConnectLayout.setVisibility(View.VISIBLE);
            mMemberLayout.setVisibility(View.GONE);
        }
    };

    private void getUNOPointsConversion() {
        Call<GetUNOPointsConversionResponse> call = RetrofitBuild.getUnoRewardsAPIService(getViewContext())
                .getUNOPointsConversion(
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid),
                        userid,
                        borrowerid
                );
        call.enqueue(getUNOPointsConversionCallback);
    }

    private Callback<GetUNOPointsConversionResponse> getUNOPointsConversionCallback = new Callback<GetUNOPointsConversionResponse>() {
        @Override
        public void onResponse(Call<GetUNOPointsConversionResponse> call, Response<GetUNOPointsConversionResponse> response) {
            try {
                if (response.body().getStatus().equals("000")) {
                    setTvConversion(response.body().getData());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetUNOPointsConversionResponse> call, Throwable t) {
            t.printStackTrace();
            showNoInternetToast();
        }
    };

    private void setTvPoints(String data) {
        try {
            tvPoints.setText("");
            tvPoints.setText(CommonFunctions.pointsFormatter(data));
        } catch (Exception e) {
            tvPoints.setText("0");
            e.printStackTrace();
        }
    }

    private void setTvConversion(UnoPointsConversion data) {
        try {
            tv_conversion.setText("");
            String point = "";
            if (Double.parseDouble(data.getPoints()) > 1)
                point = data.getPoints() + " points";
            else
                point = data.getPoints() + " point";

            tv_conversion.setVisibility(View.VISIBLE);
            String str = point + " = ₱" + CommonFunctions.currencyFormatter(String.valueOf(data.getConversionAmount()));
            tv_conversion.setText(str);
        } catch (Exception e) {
            tv_conversion.setVisibility(View.INVISIBLE);
            e.printStackTrace();
        }
    }

    private double mPoints = 0;
}
