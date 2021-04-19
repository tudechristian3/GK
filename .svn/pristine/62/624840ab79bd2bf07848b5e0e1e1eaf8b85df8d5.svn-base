package com.goodkredit.myapplication.activities.mdp;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.mdp.MDPBulletinHeaderAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.database.mdp.MDPBulletinDB;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.mdp.MDPBulletin;
import com.goodkredit.myapplication.responses.mdp.GetMDPBulletinResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MDPBulletinActivity extends BaseActivity {

    private List<MDPBulletin> mbulletin = new ArrayList<>();

    private DatabaseHandler mdb;
    private String sessionid;
    private String imei;
    private String userid;
    private String authcode;
    private String borrowerid;
    private String schoolid;
    private String servicecode;

    private LinearLayout header_container;
    private LinearLayout rv_bulletin_container;
    private RecyclerView rv_bulletin;
    private MDPBulletinHeaderAdapter bulletinheaderadapter;

    //NO RESULT
    private LinearLayout noresult;
    private TextView txv_noresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdp_bulletin);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        try {
            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setupToolbar();

        //BULLETIN
        header_container = (LinearLayout) findViewById(R.id.header_container);
        rv_bulletin_container = (LinearLayout) findViewById(R.id.rv_bulletin_container);
        rv_bulletin = (RecyclerView) findViewById(R.id.rv_mdp_bulletin);

        //NO RESULT
        noresult = (LinearLayout) findViewById(R.id.noresult);
        txv_noresult = (TextView) findViewById(R.id.txv_noresult);
    }

    private void initData() {
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        schoolid = ".";
        servicecode = "MDP";

        mdb = new DatabaseHandler(getViewContext());
        rv_bulletin.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_bulletin.setNestedScrollingEnabled(false);

        bulletinheaderadapter = new MDPBulletinHeaderAdapter(getViewContext(), this);
        rv_bulletin.setAdapter(bulletinheaderadapter);

        getSession();
    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Fetching MDP Bulletin.", "Please wait...");
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            getMDPBulletin(getMDPBulletinSession);
        } else {
            hideProgressDialog();
            showToast(getString(R.string.generic_internet_error_message), GlobalToastEnum.WARNING);
        }
    }

    //GETMDPBULLETINDETAILS
    private void getMDPBulletin(Callback<GetMDPBulletinResponse> getMDPBulletinCallback) {
        Call<GetMDPBulletinResponse> getmdpbulletin = RetrofitBuild.getMDPAPIService(getViewContext())
                .getMDPBulletinCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        schoolid,
                        servicecode
                );

        getmdpbulletin.enqueue(getMDPBulletinCallback);
    }

    private final Callback<GetMDPBulletinResponse> getMDPBulletinSession = new
            Callback<GetMDPBulletinResponse>() {

                @Override
                public void onResponse(Call<GetMDPBulletinResponse> call, Response<GetMDPBulletinResponse> response) {
                    try {
                        hideProgressDialog();
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody == null) {
                            if (response.body().getStatus().equals("000")) {
                                mdb.truncateTable(mdb, MDPBulletinDB.TABLE_NAME);

                                mbulletin = response.body().getMDPBulletinList();

                                for (MDPBulletin mdpbulletin : mbulletin) {
                                    mdb.insertMDPBulletin(mdb, mdpbulletin);
                                }

                                if (mdb.getMDPBulletin(mdb).size() > 0) {
                                    header_container.setVisibility(View.VISIBLE);
                                    rv_bulletin_container.setVisibility(View.VISIBLE);
                                    bulletinheaderadapter.updateDataHeader(mdb.getMDPBulletinGroupByDate(mdb));
                                }
                                else {
                                    header_container.setVisibility(View.GONE);
                                    rv_bulletin_container.setVisibility(View.GONE);
                                    noresult.setVisibility(View.VISIBLE);
                                    txv_noresult.setText("NO BULLETIN POSTED YET");
                                }

                            } else {
                                showError(response.body().getMessage());
                            }
                        } else {
                            showError();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetMDPBulletinResponse> call, Throwable t) {
                    showError();
                    hideProgressDialog();
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
