package com.goodkredit.myapplication.activities.publicsponsor;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;

public class PublicSponsorWebViewCostumizedActivity extends BaseActivity {

    private PublicSponsor publicSponsor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_costumized_sponsor);

        setupToolbar();

        publicSponsor = getIntent().getParcelableExtra(PublicSponsor.KEY_PUBLICSPONSOR);

        String sponsorid = publicSponsor.getGuarantorID();
        String borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        String mobileno = PreferenceUtils.getUserId(getViewContext());

        String requestformurl = CommonFunctions.replaceEscapeData(CommonFunctions.parseXML(publicSponsor.getNotes1(), "requestformurl"));

        String shaurl = CommonFunctions.getSha1Hex("60a54d645173cf7c2cd53a403cb717b7de449e72"
                + sponsorid + borrowerid + mobileno);
        String newurl =  requestformurl + shaurl +"/" + sponsorid + "/"
                + borrowerid + "/" + mobileno;

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(newurl);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            closeDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeDialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getViewContext());
        builder1.setMessage("Are you sure you want to leave this page?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Leave",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                });
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        /*
         * without call to super onBackPress() will not be called when
         * keyCode == KeyEvent.KEYCODE_BACK
         */
        return super.onKeyUp(keyCode, event);
    }
    @Override
    public void onBackPressed() {
        closeDialog();
    }
}