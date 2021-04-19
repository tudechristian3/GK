package com.goodkredit.myapplication.activities.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;

import org.json.JSONObject;

/**
 * Created by Ban_Lenovo on 7/28/2017.
 */

public class NotificationProcessSuccessActivity extends BaseActivity {

    private static final String FAILED_NOTE = "Please try again.";
    private static final String SUCCESS_NOTE = "Thank you for using GoodKredit.";

    private static final String LOAD_S_TITLE = "SUCCESSFUL LOAD";
    private static final String LOAD_F_TITLE = "FAILED LOAD";
    private static final String BILLSPAY_S_TITLE = "SUCCESSFUL BILLS PAYMENT";
    private static final String BILLSPAY_F_TITLE = "FAILED BILLS PAYMENT";

    private MaterialDialog dialog;
    private String payload;

    private String subject;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_notification_success);
        init();
        showSuccessDialog();
    }

    private void init() {
        try {
            payload = getIntent().getStringExtra("PAYLOAD");

            JSONObject mainObject = new JSONObject(payload);
            JSONObject payloadObject = mainObject.getJSONObject("payload");
            subject = payloadObject.getString("subject");
            message = payloadObject.getString("message");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSuccessDialog() {
        dialog = new MaterialDialog.Builder(getViewContext())
                .customView(R.layout.dialog_notification_success, false)
                .cancelable(false)
                .show();

        View view = dialog.getCustomView();
        TextView tvTitle = (TextView) view.findViewById(R.id.successTitle);
        TextView tvMessage = (TextView) view.findViewById(R.id.successMessage);
        TextView tvNote = (TextView) view.findViewById(R.id.notes);

        if (subject.contains("SUCCESS")) {
            if (subject.contains("PREPAID LOAD")) {
                tvTitle.setText(LOAD_S_TITLE);
            } else {
                tvTitle.setText(BILLSPAY_S_TITLE);
            }
            tvMessage.setText(message);
            tvNote.setText(SUCCESS_NOTE);
            view.findViewById(R.id.closeDialog).setOnClickListener(closeListener);
            view.findViewById(R.id.negativeButton).setVisibility(View.GONE);
            view.findViewById(R.id.positiveButton).setVisibility(View.GONE);
        } else {
            tvTitle.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorAccent2));
            if (subject.contains("PREPAID LOAD")) {
                tvTitle.setText(LOAD_F_TITLE);

            } else {
                tvTitle.setText(BILLSPAY_F_TITLE);
            }
            tvMessage.setText(message);
            tvNote.setText(FAILED_NOTE);
            view.findViewById(R.id.closeDialog).setVisibility(View.GONE);
            view.findViewById(R.id.negativeButton).setOnClickListener(cancelListener);
            view.findViewById(R.id.positiveButton).setOnClickListener(retryListener);
        }

    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener retryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            if (subject.contains("PREPAID LOAD")) {
                ViewOthersTransactionsActivity.start(getViewContext(), 4);
            }else{
                ViewOthersTransactionsActivity.start(getViewContext(), 5);
            }
        }
    };

    private View.OnClickListener closeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    public static void start(Context context, String payload) {
        Intent intent = new Intent(context, NotificationProcessSuccessActivity.class);
        intent.putExtra("PAYLOAD", payload);
        context.startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
