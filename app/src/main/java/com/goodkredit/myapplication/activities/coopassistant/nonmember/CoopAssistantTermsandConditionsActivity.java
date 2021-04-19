package com.goodkredit.myapplication.activities.coopassistant.nonmember;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantPaymentOptionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantInformation;

import hk.ids.gws.android.sclick.SClick;

public class CoopAssistantTermsandConditionsActivity extends BaseActivity implements View.OnClickListener {
    private WebView webView;
    private String urlLink;

    private ProgressBar progressBar;

    private LinearLayout btn_action_container;
    private TextView btn_action;

    public static CoopAssistantTermsandConditionsActivity coopAssistantTermsandConditionsActivity;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_coopassistant_termsandconditions);

            coopAssistantTermsandConditionsActivity = this;

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);

        btn_action_container = findViewById(R.id.btn_action_container);
        btn_action_container.setVisibility(View.VISIBLE);
        btn_action = findViewById(R.id.btn_action);
        btn_action.setText("Agree");
        btn_action.setOnClickListener(this);
    }

    private void initData() {
        setupToolbarWithTitle("Terms and Conditions");

        Bundle args = new Bundle();
        args = getIntent().getBundleExtra("args");
        urlLink = args.getString(CoopAssistantInformation.KEY_COOPTERMS);

        setWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        loadWebView();
    }

    private void loadWebView() {
        String loadURL = "https://docs.google.com/gview?embedded=true&url=" + urlLink;
        webView.loadUrl(loadURL);


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setProgress(20);
            }

            //OPENS THE ORIGINAL PAGE TO YOUR DEFAULT BROWSER
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith(".pdf")) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                if (view.getContentHeight() == 0) view.loadUrl(url);
            }

//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//                    webView.loadUrl(loadURL);
//                }
//            }
//
//            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
//                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//                    webView.loadUrl(loadURL);
//                }
//                handler.proceed();
//            }

        });

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, CoopAssistantTermsandConditionsActivity.class);
        intent.putExtra("args", args);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;

                Bundle args = new Bundle();
                args = getIntent().getBundleExtra("args");
                //args.putString("FROM", CoopAssistantInformation.KEY_COOPTERMS);

                CoopAssistantApplyMemberActivity.start(getViewContext(), args);

                break;
            }
        }
    }
}
