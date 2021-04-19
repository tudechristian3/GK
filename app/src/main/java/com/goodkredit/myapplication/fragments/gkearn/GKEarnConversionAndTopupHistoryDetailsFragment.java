package com.goodkredit.myapplication.fragments.gkearn;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkearn.GKEarnHomeActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.gkearn.GKEarnConversionPoints;
import com.goodkredit.myapplication.model.gkearn.GKEarnTopUpHistory;

public class GKEarnConversionAndTopupHistoryDetailsFragment extends BaseFragment {
    private TextView txv_header_title;
    private TextView txv_datetime;
    private TextView txv_transactionno;
    private TextView txv_amount;
    private TextView txv_outlet;
    private TextView txv_custsercharge;
    private TextView txv_status;
    //MODEL
    private GKEarnTopUpHistory gkEarnTopUpHistory = null;
    private GKEarnConversionPoints gkEarnConversionPoints = null;
    private String str_from = "";
    private LinearLayout layout_outlet;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gkearn_conversion_and_topup_history_details, container, false);
        try {
            init(view);
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    private void init(View view) {
        txv_header_title = view.findViewById(R.id.txv_header_title);
        txv_datetime = view.findViewById(R.id.txv_datetime);
        txv_transactionno = view.findViewById(R.id.txv_transactionno);
        txv_amount = view.findViewById(R.id.txv_amount);
        txv_outlet = view.findViewById(R.id.txv_outlet);
        txv_custsercharge = view.findViewById(R.id.txv_custsercharge);
        txv_status = view.findViewById(R.id.txv_status);
        layout_outlet = view.findViewById(R.id.layout_outlet);
    }
    private void initData() {
        if(getArguments() != null){
            str_from = getArguments().getString(GKEarnHomeActivity.GKEARN_KEY_TOPUP_OR_CONVERSION_TO_DETAILS);
            gkEarnConversionPoints = getArguments().getParcelable(GKEarnHomeActivity.GKEARN_KEY_PARCELABLE_CONVERSION);
            gkEarnTopUpHistory = getArguments().getParcelable(GKEarnHomeActivity.GKEARN_KEY_PARCELABLE_TOPUP);
        }
        if(str_from.equals(GKEarnHomeActivity.GKEARN_VALUE_CONVERSION_TO_DETAILS)){
            layout_outlet.setVisibility(View.GONE);
            txv_header_title.setText("CONVERSION DETAILS");
            txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(gkEarnConversionPoints.getDateTimeIN())));
            txv_transactionno.setText(CommonFunctions.replaceEscapeData(gkEarnConversionPoints.getConversionTxnNo()));
            txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(gkEarnConversionPoints.getTotalVoucherAmount())));
            txv_custsercharge.setText(CommonFunctions.currencyFormatter(String.valueOf(gkEarnConversionPoints.getTotalSC())));
            txv_status.setText(CommonFunctions.replaceEscapeData(gkEarnConversionPoints.getStatus()));
        } else{
            layout_outlet.setVisibility(View.VISIBLE);
            txv_header_title.setText("TOP-UP DETAILS");
            txv_datetime.setText(CommonFunctions.getDateTimeFromDateTime(CommonFunctions.convertDate(gkEarnTopUpHistory.getDateTimeIN())));
            txv_transactionno.setText(CommonFunctions.replaceEscapeData(gkEarnTopUpHistory.getTopupTransactionNo()));
            txv_amount.setText(CommonFunctions.currencyFormatter(String.valueOf(gkEarnTopUpHistory.getAmount())));
            txv_custsercharge.setText(CommonFunctions.currencyFormatter(String.valueOf(gkEarnTopUpHistory.getServiceCharge())));
            txv_status.setText(CommonFunctions.replaceEscapeData(gkEarnTopUpHistory.getStatus()));

            if(gkEarnTopUpHistory.getPartnerOutletName().equals(".")){
                txv_outlet.setText("N/A");
            } else{
                txv_outlet.setText(CommonFunctions.replaceEscapeData(gkEarnTopUpHistory.getPartnerOutletName()));
            }
        }
    }
}