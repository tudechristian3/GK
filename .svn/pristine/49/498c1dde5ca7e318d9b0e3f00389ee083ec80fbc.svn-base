package com.goodkredit.myapplication.activities.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.CreateSession;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.PreferenceUtils;

import org.json.JSONObject;

public class ChangePassword extends BaseActivity {

    //Declaration

    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;
    TextView oldpassword;
    TextView newpassword;
    TextView confpassword;

    static String oldpasswordval = "";
    static String newpasswordval = "";
    static String confpasswordval = "";
    static String borroweridval = "";
    static String mobileval = "";
    static String sessionidval = "";
    static String imei = "";
    static String userid = "";

    // Progress Dialog
    ProgressDialog pDialog;

    //UNIFIED SESSION
    private String sessionid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mcontext = this;

        //Initialize database
        db = new DatabaseHandler(this);

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //inflate view
        setContentView(R.layout.activity_change_password);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);

        //set toolbar
        setupToolbar();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }


    public void changePassword(View view) {
        try {
            //1.
            int status = cf.getConnectivityStatus(this);
            if (status == 0) { //no connection
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else { //has connection proceed

                //2. get input
                oldpassword = (TextView) findViewById(R.id.oldpassword);
                newpassword = (TextView) findViewById(R.id.newpassword);
                confpassword = (TextView) findViewById(R.id.confpass);

                oldpasswordval = oldpassword.getText().toString();
                newpasswordval = newpassword.getText().toString();
                confpasswordval = confpassword.getText().toString();


                //Validate input
                if (oldpasswordval.equals("") || newpasswordval.equals("") || confpasswordval.equals("")) {
                    showToast("All fields are mandatory", GlobalToastEnum.WARNING);
                } else if (!newpasswordval.equals(confpasswordval)) {
                    showToast("New password did not match or empty", GlobalToastEnum.WARNING);
                } else if (oldpasswordval.equals(newpasswordval)) {
                    showToast("New password must not be equal to the old password", GlobalToastEnum.WARNING);
                } else {
                    //get account information
                    Cursor cursor = db.getAccountInformation(db);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            borroweridval = cursor.getString(cursor.getColumnIndex("borrowerid"));
                            mobileval = cursor.getString(cursor.getColumnIndex("mobile"));
                            userid = cursor.getString(cursor.getColumnIndex("mobile"));
                            imei = cursor.getString(cursor.getColumnIndex("imei"));
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                    //3.
                    verifySession();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /*---------------
  * FUNCTIONS
  * --------------*/

    public void verifySession() {
        int status = cf.getConnectivityStatus(this);
        if (status == 0) { //no connection
            showToast("No internet connection.", GlobalToastEnum.NOTICE);
        } else {
            cf.showDialog(mcontext, "", "Changing Password. Please wait ...", false);

//            CreateSession newsession = new CreateSession(this);
//            newsession.setQueryListener(new CreateSession.QueryListener() {
//                @SuppressWarnings("unchecked")
//                public void QuerySuccessFul(String data) {
//                    if (data == null) {
//                        cf.hideDialog();
//                        showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                    } else if (data.equals("001")) {
//                        cf.hideDialog();
//                        showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                    } else if (data.equals("002")) {
//                        cf.hideDialog();
//                        showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                    } else if (data.equals("error")) {
//                        cf.hideDialog();
//                        showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                    } else if (data.contains("<!DOCTYPE html>")) {
//                        cf.hideDialog();
//                        showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                    } else {
//                        sessionidval = data;
//                        //call AsynTask to perform network operation on separate thread
//                        new HttpAsyncTask().execute(cv.UPDATEPASSWORDURL);
//                    }
//                }
//
//            });
//            newsession.execute(cv.CREATESESSION, imei, userid);

            //call AsynTask to perform network operation on separate thread
            new HttpAsyncTask().execute(cv.UPDATEPASSWORDURL);
        }

    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = cf.getSha1Hex(imei + userid + sessionid);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borroweridval);
                jsonObject.accumulate("username", mobileval);
                jsonObject.accumulate("newpassword", newpasswordval);
                jsonObject.accumulate("oldpassword", oldpasswordval);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", session);
                jsonObject.accumulate("authcode", authcode);


                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                cf.hideDialog();
            }

            return cf.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            cf.hideDialog();
            if (result == null) {
                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                processResult(result);
            }
        }
    }

    public void processResult(String result) {

        try {
            if (result.equals("000")) {
                showToast("Password was successfully changed.", GlobalToastEnum.INFORMATION);
                oldpassword.setText("");
                newpassword.setText("");
                confpassword.setText("");
            } else if (result.equals("001")) {
                showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
            } else if (result.equals("002")) {
                showToast("Invalid Session.", GlobalToastEnum.NOTICE);
            } else if (result.equals("003")) {
                showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
            } else if (result.equals("004")) {
                showToast("Old password did not match.", GlobalToastEnum.WARNING);
            } else if (result.contains("<!DOCTYPE html>")) {
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ChangePassword.class);
        context.startActivity(intent);
    }
}
