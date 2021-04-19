package com.goodkredit.myapplication.activities.schoolmini;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.ZoomButtonsController;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SchoolMiniBulletinWebView extends BaseActivity {
    private WebView webView;
    private String redirectionlink;

    private ProgressBar progressBar;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schoolmini_bulletin_webview);
        setupToolbarWithTitle("Bulletin");

        webView = (WebView) findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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

        Bundle bundle = getIntent().getBundleExtra("args");
        if (bundle != null) {
            redirectionlink = bundle.getString("redirectionlink");
            if (redirectionlink != null) {
                webView.loadUrl(redirectionlink);
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

                // Force links and redirects to open in the WebView instead of in a browser
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                        progressBar.setProgress(20);
                    }
                });
            }
            else {
                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            }
        }
    }

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, SchoolMiniBulletinWebView.class);
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
}
