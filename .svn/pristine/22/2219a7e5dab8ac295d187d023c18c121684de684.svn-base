package com.goodkredit.myapplication.fragments.gkstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkstore.GkStoreHistoryActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;


public class GKStoreAgentFragment extends BaseFragment {

    private String BASE_URL = "";

    private LinearLayout btn_view_history_container;
    private LinearLayout btn_view_agents_container;

    private String merchantid;
    private String storeid;
    private String servicecode;
    private String currentdate;

    private String metrogasurl = "";

    private String signature = "";
    private String authentication = "";
    private String mobileNo = "";

    private String borrowername = "";
    private String strmerchantlat = "";
    private String strmerchantlong = "";
    private String strmerchantaddress = "";


//    staging : http://staging-metrogas.goodkredit.com/#/login
//    dev : http://dev.metrogas.goodkredit.com/#/bills/paid

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gkstore_agent, container, false);
        try {

            init(view);

            initData();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        return view;
    }

    private void init(View view) {
        btn_view_history_container = view.findViewById(R.id.btn_view_history_container);
        btn_view_agents_container = view.findViewById(R.id.btn_view_agents_container);

    }

    private void initData() {
        if (getArguments() != null) {
            merchantid = getArguments().getString("GKSTOREMERCHANTID");
            storeid = getArguments().getString("GKSTOREID");
            servicecode = getArguments().getString("GKSTORESERVICECODE");
            currentdate = getArguments().getString("CURRENTDATE");
            borrowername = getArguments().getString("GKSTOREBORROWERNAME");
            strmerchantlat = getArguments().getString("GKSTOREMERCHANTLAT");
            strmerchantlong = getArguments().getString("GKSTOREMERCHANTLONG");
            strmerchantaddress = getArguments().getString("GKSTOREMERCHANTADD");

            signature = CommonFunctions.getSha1Hex("goodkreditmetrogas");
            authentication = CommonFunctions.getSha1Hex("goodkredit" + CommonFunctions.getDateFromDateTimeDDMMYYFormat(CommonFunctions.convertDateDDMMYYFormat(currentdate)));
            mobileNo = CommonFunctions.getSha1Hex(PreferenceUtils.getUserId(getViewContext()));

            btn_view_history_container.setOnClickListener(historyListener);
            btn_view_agents_container.setOnClickListener(agentListener);
        }
    }

//   ---------------------------------------- ALL LISTENERS AND WATCHERS ----------------------------------------

    //ORDER HISTORY
    private View.OnClickListener historyListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            view.setEnabled(false);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                    Intent intent = new Intent(getActivity(), GkStoreHistoryActivity.class);
                    intent.putExtra("GKSTOREMERCHANTID", merchantid);
                    intent.putExtra("GKSTOREID", storeid);
                    intent.putExtra("GKSTORESERVICECODE", servicecode);
                    intent.putExtra("GKSTOREBORROWERNAME", borrowername);
                    intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
                    intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
                    intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);
                    startActivity(intent);
                }
            }, 500);
        }
    };

    private View.OnClickListener agentListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {

            view.setEnabled(false);

            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setEnabled(true);
                    if (CommonVariables.isDebugMode) {
//                      metrogasurl = "http://dev.metrogas.goodkredit.com/#/login";
                      BASE_URL = "http://dev.metrogas.goodkredit.com/#/";
                      metrogasurl = metroGasURLBuilder(BASE_URL);

                    } else if (CommonVariables.link.contains("staging")) {
//                        metrogasurl = "http://staging-metrogas.goodkredit.com/#/login";
                        BASE_URL = "http://staging-metrogas.goodkredit.com/#/";
                        metrogasurl = metroGasURLBuilder(BASE_URL);
                    } else {
//                        metrogasurl = "https://metrogas.goodkredit.com/#/login";
                        BASE_URL = "https://metrogas.goodkredit.com/#/";
                        metrogasurl = metroGasURLBuilder(BASE_URL);
                    }
                    Logger.debug("checkurl", "URL: " + metrogasurl);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(metrogasurl));
                    startActivity(browserIntent);
                }
            }, 500);
        }
    };

    private String metroGasURLBuilder(String url) {
        url = BASE_URL + signature + "/" + authentication + "/" + mobileNo;
        log(url);
        return url;
    }


}
