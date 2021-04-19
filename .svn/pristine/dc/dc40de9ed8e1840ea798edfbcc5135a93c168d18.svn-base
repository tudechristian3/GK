package com.goodkredit.myapplication.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeVerificationSMSBroadcastListener extends BroadcastReceiver {

    private static final String TAG = CodeVerificationSMSBroadcastListener.class.getSimpleName();

    private Listener listener;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    smsSender = smsMessage.getDisplayOriginatingAddress();
                    smsBody = smsMessage.getMessageBody();
                }
            } else {
                Bundle smsBundle = intent.getExtras();
                if (smsBundle != null) {
                    Object[] pdus = (Object[]) smsBundle.get("pdus");
                    if (pdus == null) {
                        // Display some error to the user
                        Log.e(TAG, "SmsBundle had no pdus key");
                        return;
                    }
                    SmsMessage[] messages = new SmsMessage[pdus.length];
                    for (int i = 0; i < messages.length; i++) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        smsBody = messages[i].getMessageBody();
                    }
                }
            }
            if (smsBody.contains("Your GoodKredit verification code"))
                if (listener != null) {
                    smsBody = smsBody.replace(".", "");
                    List<String> message = new ArrayList<>(Arrays.asList(smsBody.split(" ")));
                    listener.onTextReceived(message.get(message.size() - 1));
                }
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onTextReceived(String text);
    }
}