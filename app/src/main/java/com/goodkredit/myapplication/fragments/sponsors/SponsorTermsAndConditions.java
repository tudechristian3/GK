package com.goodkredit.myapplication.fragments.sponsors;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Sponsor;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.goodkredit.myapplication.utilities.Logger;

public class SponsorTermsAndConditions extends BaseActivity {

    WebView wv;
//    Sponsor sponsor;
    PublicSponsor sponsor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_terms_and_conditions);
        setupToolbar();

//        sponsor = (Sponsor) getIntent().getSerializableExtra("SPONSOR_OBJECT");
        sponsor = getIntent().getParcelableExtra(PublicSponsor.KEY_PUBLICSPONSOR);
        String shaurl = CommonFunctions.getSha1Hex("GKSPONSORAGREEMENT" + sponsor.getGuarantorID());
        String newurl = CommonVariables.SPONSORTERMSANDCONDITIONURL + shaurl + "=" + sponsor.getGuarantorID() + "";

        wv = (WebView) findViewById(R.id.webView);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(newurl);

        Logger.debug("vanhttp","termsurl: " + newurl);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
