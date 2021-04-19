package com.goodkredit.myapplication.fragments.skycable;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

public class SkycableSOADetailsFragment extends BaseFragment implements View.OnClickListener {

    private SkycableSOA skycableSOA = null;
    private TextView txvDateTime;
    private TextView txvBillingID;
    private TextView txvAccountName;
    private TextView txvAccountNumber;
    private TextView txvDueFromLastBill;
    private TextView txvCurrentCharges;
    private TextView txvTotalCharges;
    private TextView txvSoa;
    private Button btnNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_soa_details, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();


        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ((SkyCableActivity) getViewContext()).displayView(3, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(View view) {
        btnNext = (Button) view.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        txvDateTime = (TextView) view.findViewById(R.id.txvDateTime);
        txvBillingID = (TextView) view.findViewById(R.id.txvBillingID);
        txvAccountName = (TextView) view.findViewById(R.id.txvAccountName);
        txvAccountNumber = (TextView) view.findViewById(R.id.txvAccountNumber);
        txvDueFromLastBill = (TextView) view.findViewById(R.id.txvDueFromLastBill);
        txvCurrentCharges = (TextView) view.findViewById(R.id.txvCurrentCharges);
        txvTotalCharges = (TextView) view.findViewById(R.id.txvTotalCharges);
        txvSoa = (TextView) view.findViewById(R.id.txvSoa);
        txvSoa.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "STATEMENT OF ACCOUNT"));
    }

    private void initData() {
        skycableSOA = getArguments().getParcelable("skycableSOA");
        txvDateTime.setText(CommonFunctions.convertDate(skycableSOA.getDateTimeIN()));
        txvBillingID.setText(skycableSOA.getBillingID());
        txvAccountName.setText(skycableSOA.getSkycableAccountName());
        txvAccountNumber.setText(skycableSOA.getSkycableAccountNo());
        txvDueFromLastBill.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableSOA.getDueFromLastBill())));
        txvCurrentCharges.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableSOA.getCurrentCharges())));
        txvTotalCharges.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableSOA.getTotalCharges())));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext: {
                Intent intent = new Intent(getViewContext(), BillsPaymentBillerDetailsActivity.class);
                intent.putExtra("BILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode"));
                intent.putExtra("SPBILLCODE", PreferenceUtils.getStringPreference(getViewContext(), "skycablespbillcode"));
                intent.putExtra("SPBILLERACCOUNTNO", "");
                intent.putExtra("BILLNAME", PreferenceUtils.getStringPreference(getViewContext(), "skycablebillname"));
                intent.putExtra("FROM", "BILLERS");
                intent.putExtra("skycableSOA", skycableSOA);
                getViewContext().startActivity(intent);
                break;
            }
        }
    }
}
