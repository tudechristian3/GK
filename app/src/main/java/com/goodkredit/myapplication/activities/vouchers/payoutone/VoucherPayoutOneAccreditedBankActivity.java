package com.goodkredit.myapplication.activities.vouchers.payoutone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.vouchers.payoutone.VoucherPayoutOneAccreditedBanksAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.vouchers.AccreditedBanks;
import com.goodkredit.myapplication.responses.vouchers.AccreditedBanksResponse;
import com.goodkredit.myapplication.utilities.CacheManager;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherPayoutOneAccreditedBankActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView rv_accreditedbanks;
    private VoucherPayoutOneAccreditedBanksAdapter mAdapter;
    private List<AccreditedBanks> mGridData;

    private ArrayList<AccreditedBanks> passedAccreditedBanks;
    private String bankcode;
    private String bankname;

    private String imei;
    private String sessionid;
    private String userid;
    private String borrowerid;
    private String authcode;

    private TextView txv_nodata;
    private RelativeLayout layout_nointernet;
    private ImageView img_refreshnointerner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_payoutone_accredited_banks);

        setupToolbar();

        init();
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_payoutone_proceed, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        rv_accreditedbanks = findViewById(R.id.rv_accreditedbanks);

        txv_nodata = findViewById(R.id.txv_nodata);
        layout_nointernet = findViewById(R.id.nointernetconnection);
        img_refreshnointerner = findViewById(R.id.refreshnointernet);

        img_refreshnointerner.setOnClickListener(this);

    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        mAdapter = new VoucherPayoutOneAccreditedBanksAdapter(getViewContext(), VoucherPayoutOneAccreditedBankActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getViewContext());
        rv_accreditedbanks.setNestedScrollingEnabled(false);
        rv_accreditedbanks.addItemDecoration(new DividerItemDecoration(getViewContext(), LinearLayoutManager.VERTICAL));
        rv_accreditedbanks.setLayoutManager(layoutManager);
        rv_accreditedbanks.setAdapter(mAdapter);

        rv_accreditedbanks.setVisibility(View.VISIBLE);
        txv_nodata.setVisibility(View.GONE);

        getSession();
    }

    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            getAccreditedBanks(getAccreditedBanksSession);
        } else{
            showNoInternetConnection(true);
        }

    }

    private void getAccreditedBanks (Callback<AccreditedBanksResponse> getAccreditBanksCallback ){
        Call<AccreditedBanksResponse> getaccreditedbanks = RetrofitBuild.getVoucherV3Service(getViewContext())
                .getAccreditedBanks(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        CommonVariables.devicetype);
        getaccreditedbanks.enqueue(getAccreditBanksCallback);
    }

    private final Callback<AccreditedBanksResponse> getAccreditedBanksSession = new Callback<AccreditedBanksResponse>() {
        @Override
        public void onResponse(Call<AccreditedBanksResponse> call, Response<AccreditedBanksResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if(errorBody == null){
                if(response.body().getStatus().equals("000")){

                    try{
                        CacheManager.getInstance().saveAccreditedBanks(response.body().getAccreditedBanksList());
                        mGridData = CacheManager.getInstance().getAccreditedBanks();

                        if(mGridData.size() > 0){
                            mAdapter.updateList(mGridData);
                        } else{
                            rv_accreditedbanks.setVisibility(View.GONE);
                            txv_nodata.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else if(response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else{
                    showErrorGlobalDialogs(response.body().getMessage());

                }

            } else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<AccreditedBanksResponse> call, Throwable t) {
            showErrorGlobalDialogs();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.payoutone_proceed) {
            proceedToAddingOfBankDetails();
        }
        return super.onOptionsItemSelected(item);
    }

    private void proceedToAddingOfBankDetails(){
        if (!SClick.check(SClick.BUTTON_CLICK)) return;

        if(passedAccreditedBanks != null){
            if(passedAccreditedBanks.size() > 0){
                try{
                    for(AccreditedBanks accreditedBanks : passedAccreditedBanks){
                        bankcode = accreditedBanks.getBankCode();
                        bankname = accreditedBanks.getBank();
                    }

                    Intent intent = new Intent(getViewContext(), VoucherPayoutOneAddBankAccountActivity.class);
                    intent.putExtra(AccreditedBanks.KEY_ACCREDITEDBANKS_BANKCODE, bankcode);
                    intent.putExtra(AccreditedBanks.KEY_ACCREDITEDBANKS_BANKNAME, bankname);
                    startActivityForResult(intent, 100);

                }catch (Exception e){
                    e.printStackTrace();
                }
            } else{
                showToast("Please select a bank.", GlobalToastEnum.WARNING);
            }
        } else {
            showToast("Please select a bank.", GlobalToastEnum.WARNING);
        }
    }

    //CALLS DURING THE SELECTION
    public void selectAccreditedBanks (ArrayList<AccreditedBanks> accreditedBanks) {
        Collections.reverse(accreditedBanks);
        passedAccreditedBanks = accreditedBanks;
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    //show no internet connection page
    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            layout_nointernet.setVisibility(View.VISIBLE);
        } else {
            layout_nointernet.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.refreshnointernet: {
                getSession();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Intent intent = new Intent(getViewContext(), VoucherPayoutOneAccreditedBankActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
