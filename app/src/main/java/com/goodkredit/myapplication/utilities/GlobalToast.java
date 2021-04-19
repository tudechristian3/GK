package com.goodkredit.myapplication.utilities;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.enums.GlobalToastEnum;

public class GlobalToast {
    private static Toast currentToast;

    public static void displayToast(Context context, String message, GlobalToastEnum globalToastEnum) {
        //CUSTOM TOAST
        customToast(context, message, globalToastEnum);
    }

    private static void customToast(Context context, String message, GlobalToastEnum globalToastEnum) {
        // Avoid creating a queue of toasts
        if (currentToast != null) {
            // Dismiss the current showing Toast
            currentToast.cancel();
        }
        //Retrieve the layout Inflater
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Assign the custom layout to view
        View layout = inflater.inflate(R.layout.global_toast, null, false);
        //Return the application context
        currentToast = new Toast(context.getApplicationContext());
        //Set toast gravity to top
        currentToast.setGravity(Gravity.TOP, 0, 120);
        //Set toast duration
        currentToast.setDuration(Toast.LENGTH_LONG);
        //Set the custom layout to Toast
        currentToast.setView(layout);

        LinearLayout toastmaincontainer = (LinearLayout) layout.findViewById(R.id.toastmaincontainer) ;
        //Get the TextView for the message of the Toast
        TextView toastmessage = (TextView) layout.findViewById(R.id.toastmessage);
        ImageView icon = (ImageView) layout.findViewById(R.id.icon);

        switch (globalToastEnum) {
            case WARNING:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_warning_24));
                layout.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_toast_warn_background));
                break;
            case ERROR:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_error_24));
                layout.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_toast_error_background));
                break;

            case SUCCESS:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_check_circle_24));
                layout.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_toast_success_background));
                break;
            case INFORMATION:
            case NOTICE:
            default:
                icon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_info_24));
                layout.setBackground(ContextCompat.getDrawable(context, R.drawable.custom_toast_info_background));
                break;
        }

        //Set the custom text for the message of the Toast
        toastmessage.setText(message);
        //Display toast
        currentToast.show();
        // Check if the layout is visible - just to be sure
        if (layout != null) {
            // Touch listener for the layout
            // This will listen for any touch event on the screen
            layout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // No need to check the event, just dismiss the toast if it is showing
                    if (currentToast != null) {
                        currentToast.cancel();
                        // we return True if the listener has consumed the event
                        return true;
                    }
                    return false;
                }
            });
        }
    }

}
