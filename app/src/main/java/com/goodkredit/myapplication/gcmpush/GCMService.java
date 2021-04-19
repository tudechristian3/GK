package com.goodkredit.myapplication.gcmpush;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.notification.NotificationsActivity;
import com.goodkredit.myapplication.activities.settings.SupportSendMessageActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;

import org.json.JSONObject;

public class GCMService extends GcmListenerService {
    CommonVariables cv;
    DatabaseHandler db;
    Gson gson;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Intent intent = new Intent(cv.MESSAGE_RECEIVED);
        intent.putExtra("message", message);
        LocalBroadcastManager.getInstance(GCMService.this).sendBroadcast(intent);

        db = new DatabaseHandler(this);
        gson = new Gson();
        sendNotification(message);
    }

    private void sendNotification(String message) {

        Logger.debug("OkHttp", "payload---" + message);

        //parse message
        try {

            JSONObject mainObject = new JSONObject(message);
            JSONObject payload = mainObject.getJSONObject("payload");
            String subject = payload.getString("subject");
            String curmessage = payload.getString("message");

            curmessage = curmessage.replace('[', ' ');
            curmessage = curmessage.replace(']', ' ');
            curmessage = CommonFunctions.replaceEscapeData(curmessage);

            if(curmessage.contains("::::")){
                String[] messages = curmessage.split("::::");
                curmessage = messages[0];
            }

            PendingIntent pendingIntent;
            Intent intent = null;

            if (subject.equals("RELEASEVOUCHER") || subject.equals("TRANSFERVOUCHER")) {
                if(!curmessage.contains("You have successfully transferred")){
                    if(CommonFunctions.isAppRunning(getApplicationContext(), "com.goodkredit.myapplication")){
                        CommonVariables.ISNEWVOUCHER = true;
                    }
                }
            }

            Logger.debug("vanhttp", "push subject: ===" + subject);
            if (subject.equals("SUPPORTMESSAGE") || subject.equals("COOPSUPPORTMESSAGE")) {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setAction(cv.MESSAGE_RECEIVED);
                intent.putExtra("FROMPUSH", "FROMPUSH");

            } else {
                intent = new Intent(this, NotificationsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.setAction(cv.MESSAGE_RECEIVED);
                intent.putExtra("message", message);
            }

            int notificationid = 1;
            String channelId = "1";

            pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationManager notificationManagerOreo = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                NotificationChannel mChannel = notificationManagerOreo.getNotificationChannel(channelId);
                if(mChannel == null) {
                    mChannel = new NotificationChannel(channelId, subject, importance);
                    mChannel.setDescription(curmessage);
                    notificationManagerOreo.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.appicon)
                        .setContentTitle(subject)
                        .setContentText(curmessage)
                        .setAutoCancel(true)
                        .setChannelId(channelId)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(notificationid, notificationBuilder.build());
            } else {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.appicon)
                        .setContentTitle(subject)
                        .setContentText(curmessage)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, notificationBuilder.build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
