package com.goodkredit.myapplication.activities.fairchild;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

public class FairChildDeadEndActivity extends BaseActivity {
    //LOCAL DB
    private DatabaseHandler mdb;

    //COMMON
    private String imei;
    private String userid;
    private String borrowerid;
    private String sessionid;
    private String authcode;
    private String devicetype;
    private String limit;

    private ImageView imv_logo;
    private TextView txv_attendance_lbl;
    private TextView txv_attendance_desc;

    private Bundle args;
    private String strvalue;

    public static void start(Context context, Bundle args) {
        Intent intent = new Intent(context, FairChildDeadEndActivity.class);
        intent.putExtra("args",args);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fairchild_deadend);

            setupToolbar();

            //All Initializations
            init();

            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //-----------------METHODS----------------------------
    private void init() {
        imv_logo = (ImageView) findViewById(R.id.imv_logo);
        txv_attendance_lbl = (TextView) findViewById(R.id.txv_attendance_lbl);
        txv_attendance_desc = (TextView) findViewById(R.id.txv_attendance_desc);
    }

    private void initData() {
        //COMMON
        mdb = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());

        //SETUP TOOLBAR TITLE
        setUpToolBarTitle();

        //SET LOGO DETAILS
        setLogoDetails();
    }

    private void setLogoDetails() {

        Intent intent = getIntent();

        args = getIntent().getBundleExtra("args");


        strvalue = args.getString("FROM");


        String strdescription = "";

        if(strvalue != null) {
            if(strvalue.equals("WHITELIST")) {
                Glide.with(getViewContext())
                        .load(R.drawable.fairchild_attendancerecorded)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .fitCenter())
                        .into(imv_logo);

                txv_attendance_lbl.setText("Attendance Recorded");

                strdescription = "Welcome to FCCMPC General Assembly 2019! " +
                        "Your attendance has been recorded and you get <b> FREE GoodKredit voucher(s). </b>" +
                        "Please proceed to your respective bleachers to witness the proceedings. " +
                        "Thank you for your presence!";

            }else if(strvalue.equals("NOTYETSTARTED")){
                Glide.with(getViewContext())
                        .load(R.drawable.fairchildvotingnotstarted)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .fitCenter())
                        .into(imv_logo);

                txv_attendance_lbl.setText("Voting has not yet started");
                txv_attendance_lbl.setTextColor(getResources().getColor(R.color.color_FAD46C));

                strdescription = "Voting will officially start shortly. Thank you for your patience.";
            }else if(strvalue.equals("ALREADYENDED")){
                Glide.with(getViewContext())
                        .load(R.drawable.fairchildvotingended)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .fitCenter())
                        .into(imv_logo);

                txv_attendance_lbl.setText("Voting has ended");
                txv_attendance_lbl.setTextColor(getResources().getColor(R.color.color_F46355));


                strdescription = "Sorry, voting has ended! We'll see the results anytime soon.";
            } else if(strvalue.equals("ALREADYVOTED")){
                Glide.with(getViewContext())
                        .load(R.drawable.fairchildareadyvoted)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .fitCenter())
                        .into(imv_logo);

                txv_attendance_lbl.setText("You Already Voted");

                strdescription = "You have already voted your chosen candidate! Please know that you can only vote once. Thank you!";
            } else if(strvalue.equals("JUSTVOTED")){
                Glide.with(getViewContext())
                        .load(R.drawable.fairchildareadyvoted)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .fitCenter())
                        .into(imv_logo);
                txv_attendance_lbl.setText("Your vote has been cast. Thank you for your active participation.");
            } else if(strvalue.equals("NOTAMEMBER")){
                Glide.with(getViewContext())
                        .load(R.drawable.fairchildnotamember)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                .fitCenter())
                        .into(imv_logo);

                txv_attendance_lbl.setText("Not a Member");
                txv_attendance_lbl.setTextColor(getResources().getColor(R.color.color_FAD46C));

                strdescription = "Sorry, this is open only to members of FCCMPC. Please approach any FCCMPC personnel if you find this message an error. Thank you!";
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            txv_attendance_desc.setText(Html.fromHtml(strdescription, Html.FROM_HTML_MODE_COMPACT));
        } else {
            txv_attendance_desc.setText(Html.fromHtml(strdescription));
        }
    }

    private void setUpToolBarTitle() {
        String title = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceName");

        if(!title.trim().equals("")) {
            setTitle(CommonFunctions.replaceEscapeData(title));
        } else {
            setTitle("FAIR CHILD");
        }
    }

    //-------------------OTHERS------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        super.onBackPressed();
    }
}
