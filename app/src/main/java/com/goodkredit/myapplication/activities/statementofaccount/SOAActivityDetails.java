package com.goodkredit.myapplication.activities.statementofaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Ban_Lenovo on 7/25/2017.
 */

public class SOAActivityDetails extends BaseActivity {

    private WebView webView;

    private String BASE_URL = "https://admin.goodkredit.com/#/soabilling/3/";


    private SubscriberBillSummary bill;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        setupToolbar();

        if(CommonVariables.link.contains("staging")){
            BASE_URL = "http://staging-admin.goodkredit.com/#/soabilling/3/";
        }else{
            BASE_URL = "https://admin.goodkredit.com/#/soabilling/3/";
        }

        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        bill = getIntent().getParcelableExtra("OBJ");

        webView = (WebView) findViewById(R.id.webView);
        WebSettings ws = webView.getSettings();

        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            try {

                Logger.debug(TAG, "Enabling HTML5-Features");
                Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
                m1.invoke(ws, Boolean.TRUE);

                Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", new Class[]{Boolean.TYPE});
                m2.invoke(ws, Boolean.TRUE);

                Method m3 = WebSettings.class.getMethod("setDatabasePath", new Class[]{String.class});
                m3.invoke(ws, "/data/data/" + getPackageName() + "/databases/");

                Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", new Class[]{Long.TYPE});
                m4.invoke(ws, 1024 * 1024 * 8);

                Method m5 = WebSettings.class.getMethod("setAppCachePath", new Class[]{String.class});
                m5.invoke(ws, "/data/data/" + getPackageName() + "/cache/");

                Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", new Class[]{Boolean.TYPE});
                m6.invoke(ws, Boolean.TRUE);

                final ZoomButtonsController zoom_control =
                        (ZoomButtonsController) webView.getClass().getMethod("getZoomButtonsController").invoke(webView, (Object) null);
                zoom_control.getContainer().setVisibility(View.GONE);


                Logger.debug(TAG, "Enabled HTML5-Features");
            } catch (NoSuchMethodException e) {
                Log.e(TAG, "Reflection fail", e);
            } catch (InvocationTargetException e) {
                Log.e(TAG, "Reflection fail", e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "Reflection fail", e);
            } catch (Exception e) {
                Log.e(TAG, "Reflection fail", e);
            }
        }

        webView.loadUrl(

                linkBuilder(bill.getBillingID(),
                        CommonFunctions.getYearFromDate(bill.getStatementDate()),
                        CommonFunctions.getMonthFromDate(bill.getStatementDate())));
    }

    public static void start(Context context, SubscriberBillSummary bill) {
        Intent intent = new Intent(context, SOAActivityDetails.class);
        intent.putExtra("OBJ", bill);
        context.startActivity(intent);
    }

    private String linkBuilder(String billID, String year, String month) {
        String url = "";
        authcode = CommonFunctions.getSha1Hex(billID + borrowerid);
        url = BASE_URL + billID + "/" + String.valueOf(year) + "/" + String.valueOf(month) + "/" + authcode;
        log(url);
        return url;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
