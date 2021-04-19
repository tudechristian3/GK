package com.goodkredit.myapplication.activities.settings;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.goodkredit.myapplication.BuildConfig;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvVersionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        setupToolbar();

        findViewById(R.id.fb).setOnClickListener(this);
        findViewById(R.id.twitter).setOnClickListener(this);
        findViewById(R.id.instagram).setOnClickListener(this);
        findViewById(R.id.gplus).setOnClickListener(this);
        findViewById(R.id.youtube).setOnClickListener(this);

        mTvVersionName = findViewById(R.id.tv_version_name);
        String vName = "Version " + BuildConfig.VERSION_NAME;
        mTvVersionName.setText(vName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb: {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(getViewContext());
                facebookIntent.setData(Uri.parse(facebookUrl));
                facebookIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(facebookIntent);
                break;
            }
            case R.id.twitter: {
                launchTwitter();
                break;
            }
            case R.id.instagram: {
                launchInstagram();
                break;
            }
            case R.id.gplus: {
                launchGooglePlus();
                break;
            }
            case R.id.youtube: {
                launchYoutube();
                break;
            }
        }
    }

    //fb

    public static String FACEBOOK_URL = "https://www.facebook.com/GoodKredit-GK-1287167851333303/";
    public static String FACEBOOK_PAGE_ID = "1287167851333303";

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

    //twitter

    public void launchTwitter() {

        Intent intent = null;
        try {
            // get the Twitter app if possible
            this.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=GoodKredit_PH"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/GoodKredit_PH"));
        }
        this.startActivity(intent);

    }

    //youtube

    public void launchYoutube() {
        Intent intent = null;
        try {
            this.getPackageManager().getPackageInfo("com.google.android.youtube.player", 0);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube.player");
            intent.setData(Uri.parse("https://www.youtube.com/channel/UCihHEkoZgDEtv8WMmHSM0Uw"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCihHEkoZgDEtv8WMmHSM0Uw"));
        }
        this.startActivity(intent);
    }

    //google plus

    public void launchGooglePlus() {
        Intent intent = null;
        try {
            this.getPackageManager().getPackageInfo("com.google.android.gms.plus", 0);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.gms.plus");
            intent.putExtra("customAppUri", "116801348193380080877");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/116801348193380080877"));
        }
        this.startActivity(intent);
    }

    //instagram
    public void launchInstagram() {
        Intent intent = null;
        try {
            this.getPackageManager().getPackageInfo("com.instagram.android", 0);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.instagram.android");
            intent.setData(Uri.parse("https://www.instagram.com/_u/goodkredit_official/"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            e.printStackTrace();
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/goodkredit_official/"));
        }
        this.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

}
