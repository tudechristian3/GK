package com.goodkredit.myapplication.activities.account;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.database.DatabaseHandler;

import java.text.DecimalFormat;

public class WelcomePageActivity extends BaseActivity {

    DatabaseHandler db;
    String mobileval = "";
    String subject = "";
    String others = "";
    RelativeLayout welcomewrap;
    RelativeLayout approvewrap;
    RelativeLayout declinewrap;
    ImageView close;
    ImageView close1;
    ImageView close2;
    TextView credits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        db = new DatabaseHandler(this);

        welcomewrap = (RelativeLayout) findViewById(R.id.welcomewrap);
        approvewrap = (RelativeLayout) findViewById(R.id.approvewrap);
        declinewrap = (RelativeLayout) findViewById(R.id.declinewrap);
        credits = (TextView) findViewById(R.id.credits);

        //   close = (ImageView) findViewById(R.id.close);
        close1 = (ImageView) findViewById(R.id.close1);
        close2 = (ImageView) findViewById(R.id.close2);

        //get local data to populate in the profile
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
            } while (cursor.moveToNext());
        }
        cursor.close();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            others = extras.getString("OTHERS");
            subject = extras.getString("SUBJECT");
        }

        //check what is the subject to show
        if (subject.equals("APPROVEREQUEST")) {
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            credits.setText(formatter.format(Double.parseDouble(others)));
            approvewrap.setVisibility(View.VISIBLE);
            welcomewrap.setVisibility(View.GONE);
            declinewrap.setVisibility(View.GONE);
        } else if (subject.equals("DECLINEREQUEST")) {
            declinewrap.setVisibility(View.VISIBLE);
            approvewrap.setVisibility(View.GONE);
            welcomewrap.setVisibility(View.GONE);

        } else {
            welcomewrap.setVisibility(View.VISIBLE);
            declinewrap.setVisibility(View.GONE);
            approvewrap.setVisibility(View.GONE);

        }

        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void openQuickTour(View view) {
        db.updateAccountStatus(db, "LOGIN", mobileval);
        Intent intent = new Intent(this, GKAppIntroActivity.class);
        intent.putExtra("SOURCE", "WELCOMEPAGE");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
