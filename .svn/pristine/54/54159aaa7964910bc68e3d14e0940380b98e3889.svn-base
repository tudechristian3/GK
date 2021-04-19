package com.goodkredit.myapplication.gcmpush;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;

import com.goodkredit.myapplication.common.CommonVariables;

/**
 * Created by user on 10/4/2016.
 */
public class MainGCM {

    Context mContext;
    CommonVariables cv;

    // Constructor
    public MainGCM(Context context) {
        this.mContext = context;
    }


    public void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(mContext);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(cv.REGISTRATION_PROCESS);
        intentFilter.addAction(cv.MESSAGE_RECEIVED);
        bManager.registerReceiver(broadcastReceiver, intentFilter);

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(cv.REGISTRATION_PROCESS)) {

                String result = intent.getStringExtra("result");
                String message = intent.getStringExtra("message");

            } else if (intent.getAction().equals(cv.MESSAGE_RECEIVED)) {

                String message = intent.getStringExtra("message");
                showAlertDialog(message);

            }
        }
    };

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("GCM Message Received !");
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_PHONE_STATE);
        if (result == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            return false;
        }
    }


}

