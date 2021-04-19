package com.goodkredit.myapplication.activities.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;

import java.util.Objects;

import ph.com.voyagerinnovation.freenet.applink.FreenetSdk;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionEvent;
import ph.com.voyagerinnovation.freenet.applink.FreenetSdkConnectionListener;


/**
 * Created by Ban_Lenovo on 12/21/2016.
 */

public class TermsAndConditions extends BaseActivity implements FreenetSdkConnectionListener {

    Button accept;
    WebView wv;
    Toolbar toolbar;
    Intent getIntent;
    LinearLayout acceptLayout;
    String source = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setContentView(R.layout.activity_terms_conditions);
        try {
            wv =  (WebView) findViewById(R.id.webView);
            accept = (Button) findViewById(R.id.acceptButton);
            toolbar = (Toolbar) findViewById(R.id.termsToolbar);
            acceptLayout = (LinearLayout) findViewById(R.id.acceptLayout);
            setSupportActionBar(toolbar);
            getIntent = getIntent();
            Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#FFFFFF'> Terms & Conditions </font>"));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if (getIntent.hasExtra("callFromSettings")) {
                source = getIntent.getStringExtra("callFromSettings");
            }
            if (source.toString().contentEquals("1")) {
                acceptLayout.setVisibility(View.GONE);
            }

            wv.loadUrl("file:///android_asset/termsandconditions.html");

            if (isFreeModeEnabled()) {
                FreenetSdk.registerConnectionListener(this);
                activateFreeNet();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        accept.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick(View v) {
                                          if (isFreeModeEnabled()) {
                                              deactivateFreeNet();
                                              FreenetSdk.unregisterConnectionListener(TermsAndConditions.this);
                                          }
                                          setResult(RESULT_OK);
                                          finish();
                                      }
                                  }

        );
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            if (isFreeModeEnabled()) {
                deactivateFreeNet();
                FreenetSdk.unregisterConnectionListener(this);
            }
           onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (source.toString().contentEquals("1")) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }else{
            //showAcceptDialog();
            showAcceptDialogNew();
        }

    }

    private void showAcceptDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Accept Terms & Conditions")
                .setMessage("Do you accept terms & conditions?")
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isFreeModeEnabled()) {
                            deactivateFreeNet();
                            FreenetSdk.unregisterConnectionListener(TermsAndConditions.this);
                        }
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .show();
    }

    private void showAcceptDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Accept Terms & Conditions","Do you accept terms & conditions?",
                "Cancel", "Accept", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View btndoubleone = dialog.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View btndoubletwo = dialog.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (isFreeModeEnabled()) {
                    deactivateFreeNet();
                    FreenetSdk.unregisterConnectionListener(TermsAndConditions.this);
                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void onConnectionChange(FreenetSdkConnectionEvent freenetSdkConnectionEvent) {
        Logger.debug("getStatus", "STATUS: " + String.valueOf(freenetSdkConnectionEvent.getStatus()));
        Logger.debug("getErrorDescription", "Error Desc: " + String.valueOf(freenetSdkConnectionEvent.getErrorDescription()));
        Logger.debug("getErrorCode", "Error Code: " + String.valueOf(freenetSdkConnectionEvent.getErrorCode()));
        Logger.debug("toString", "toString: " + String.valueOf(freenetSdkConnectionEvent.toString()));

        if (freenetSdkConnectionEvent.getStatus() == 1) {
        } else if (freenetSdkConnectionEvent.getStatus() == 0) {
        } else {
            switch (freenetSdkConnectionEvent.getErrorCode()) {
                case 4022:
                    showToast("Please check network configuration on your device.", GlobalToastEnum.NOTICE);
                    break;
                case 4020:
                    showToast("Unsupported network settings on the device.", GlobalToastEnum.NOTICE);
                    break;
                case 4023:
                    showToast("Android version of the device is not supported", GlobalToastEnum.NOTICE);
                    break;
                case 4001:
                case 4002:
                case 4011:
                    showToast("Internal error occurred. (Params)", GlobalToastEnum.ERROR);
                    break;
                case 4012:
                    showToast("Internal error occurred. (Expired) ", GlobalToastEnum.ERROR);
                    break;
                case 4021:
                    showToast("Internal error occurred. (SDK_Exception) ", GlobalToastEnum.ERROR);
                    break;
            }
        }
    }

    public static void start(Context context, String source) {
        Intent intent = new Intent(context, TermsAndConditions.class);
        intent.putExtra("callFromSettings", source);
        context.startActivity(intent);
    }

}
