package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.nonmember.CoopAssistantPMESActivity;

public class CoopAssistantViewPMESPDF extends Fragment {

    private WebView webView;
    private String urlLink = "";
    private String loadURL = "";
    private CardView view_view_presentation;
    private ProgressBar progressBar;

    public static CoopAssistantViewPMESPDF newInstance(String link){
        CoopAssistantViewPMESPDF fragment = new CoopAssistantViewPMESPDF();
        Bundle args = new Bundle();
        args.putString("link", link);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_coop_assistant_view_p_d, container, false);

        if(getArguments() != null){
            urlLink = getArguments().getString("link");
        }

        init(view);

        return view;
    }


    private void init(View view) {
        webView = view.findViewById(R.id.webView);
        progressBar = view.findViewById(R.id.progressBar);

        view_view_presentation = view.findViewById(R.id.layout_viewpresentation);
        view_view_presentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CoopAssistantPMESActivity)requireContext()).viewpresentation();
            }
        });

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
        // String loadURL = "https://drive.google.com/viewerng/viewer?embedded=true&url=" + urlLink;
        // String loadURL = "https://docs.google.com/viewer?url=" + urlLink;

        loadURL = "https://docs.google.com/gview?embedded=true&url=" + urlLink;
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
                if (view.getContentHeight() == 0) {
                    view.loadUrl(url);
                }
            }

//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//                    view.loadUrl(loadURL);
//                }
//            }
//
//            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
//                if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//                    view.loadUrl(loadURL);
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

}