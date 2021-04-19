package com.goodkredit.myapplication.activities.notification;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.NotificationItem;
import com.goodkredit.myapplication.adapter.NotificationListAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Notifications;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    //Declaration
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;
    ListView listView;

    LinearLayout notificationrow;
    RelativeLayout emptynotification;
    ImageView refresh;
    static RelativeLayout nointernetconnection;
    static Button refreshnointernet;
    static ImageView refreshdisabled;
   // static ImageView refreshdisabled1;

    static String borrowerid = "";
    static String dateregistered = "";

    private NotificationListAdapter mlistAdapter;
    private ArrayList<NotificationItem> mlistData;
    String sessionidval = "";
    String userid = "";
    String imei = "";
    String borroweridval = "";

    static Calendar c;
    static int year;
    static int month;
    static int registrationyear;
    static int registrationmonth;
    static int currentyear = 0;//make this not changeable for (filter condition)
    static int currentmonth = 0; //make this not changeable for (filter condition)
    private SwipeRefreshLayout swipeRefreshLayout;

    static Button btnAddMore;
    static Dialog dialog;
    static Spinner spinType;
    static Spinner spinType1;
    static TextView popfilter;
    static TextView popcancel;
    static TextView viewarchiveemptyscreen;
    static RelativeLayout emptyvoucherfilter;
    static TextView filteroption;

    //dialog spin selected indicator
    static Boolean ismonthselected = false;
    static Boolean isyearselected = false;
    static ScrollView filterwrap;
    static LinearLayout optionwrap;
    static TextView editsearches;
    static TextView clearsearch;

    private static final String READ_NOTIF = "READ_NOTIF";

    private String flaglag = "";
    private String txnno = "";
    private NotificationItem itemToDetails = null;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    int currentlimit = 0;
    boolean isloadmore = true;

    //UNIFIED SESSION
    private String sessionid = "";
    private String subjectType;

    //NEW VARIABLES FOR SECURITY UPDATE
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        try {

            //set toolbar
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            db = new DatabaseHandler(this);
            mcontext = this;

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            //initialize date
            c = Calendar.getInstance();
            currentyear = c.get(Calendar.YEAR);
            currentmonth = c.get(Calendar.MONTH) + 1;
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;

            itemToDetails = new NotificationItem();

            //initialize button list footer
            btnAddMore = new Button(this);
            btnAddMore.setBackgroundResource(R.color.colorwhite);
            btnAddMore.setTextColor(getResources().getColor(R.color.buttons));
            btnAddMore.setTypeface(Typeface.DEFAULT_BOLD);


            //initialize element for view archive in empty screen
            viewarchiveemptyscreen = findViewById(R.id.viewarchiveemptyscreen);
            filteroption = findViewById(R.id.filteroption);
            emptyvoucherfilter = findViewById(R.id.emptyvoucherfilter);

            //Initialize with empty data
            listView = findViewById(R.id.notificationlist);
            emptynotification = findViewById(R.id.emptyvoucher);
            refresh = findViewById(R.id.refresh);
            nointernetconnection = findViewById(R.id.nointernetconnection);
            refreshnointernet = findViewById(R.id.refreshnointernet);
            refreshdisabled = findViewById(R.id.refreshdisabled);
            //refreshdisabled1 = (ImageView) findViewById(R.id.refreshdisabled1);

            mLlLoader = findViewById(R.id.loaderLayout);
            mTvFetching = findViewById(R.id.fetching);
            mTvWait = findViewById(R.id.wait);

            //initialize elements
            swipeRefreshLayout = findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(this);

            mlistData = new ArrayList<>();
            mlistAdapter = new NotificationListAdapter(this, R.layout.activity_notifications_item, getALlNotifications());
            listView.setAdapter(mlistAdapter);

            final ImageView backgroundOne = findViewById(R.id.background_one);
            final ImageView backgroundTwo = findViewById(R.id.background_two);

            final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());
            animator.setDuration(750);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final float progress = (float) animation.getAnimatedValue();
                    final float width = backgroundOne.getWidth();
                    final float translationX = width * progress;
                    backgroundOne.setTranslationX(translationX);
                    backgroundTwo.setTranslationX(translationX - width);
                }
            });
            animator.start();

            mLoaderTimer = new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    mLlLoader.setVisibility(View.GONE);
                }
            };


            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                    dateregistered = cursor.getString(cursor.getColumnIndex("dateregistration"));

                    String CurrentString = dateregistered;
                    String[] separated = CurrentString.split("-");
                    registrationyear = Integer.parseInt(separated[0]);
                    registrationmonth = Integer.parseInt(separated[1]);
                } while (cursor.moveToNext());
            }
            cursor.close();
            populateNotification();

            if (CommonVariables.NOTIFICATIONFIRSTLOAD.equals("true")) {
                verifySession("");
            }


            //create a Filter confirmation
            dialog = new Dialog(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light));
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pop_filtering);
            filterwrap = dialog.findViewById(R.id.filterwrap);
            optionwrap = dialog.findViewById(R.id.optionwrap);
            editsearches = dialog.findViewById(R.id.editsearches);
            clearsearch = dialog.findViewById(R.id.clearsearch);
            spinType = dialog.findViewById(R.id.month);
            spinType1 = dialog.findViewById(R.id.year);
            popfilter = dialog.findViewById(R.id.filter);
            popcancel = dialog.findViewById(R.id.cancel);


            //setting list in spinner
            //Month
            createMonthSpinnerAddapter();
            //Year
            createYearSpinnerAddapter();

            /****
             * TRIGGERS
             * */

            //Spinner for MONTH
            spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                protected Adapter initializedAdapter = null;

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        String monthstring = parentView.getItemAtPosition(position).toString();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                        month = cal.get(Calendar.MONTH) + 1;
                        ismonthselected = month > 0;
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    ismonthselected = false;
                }
            });

            //Spinner for YEAR
            spinType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                protected Adapter initializedAdapter = null;

                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String spinyear = parentView.getItemAtPosition(position).toString();
                    if (!spinyear.equals("Select Year")) {
                        year = Integer.parseInt(parentView.getItemAtPosition(position).toString());
                        createMonthSpinnerAddapter();
                        isyearselected = true;
                    } else {
                        isyearselected = false;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    isyearselected = false;
                }
            });

            //Filter in Dialog
            popfilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isyearselected == true && ismonthselected == true) {
                        currentlimit = 0;
                        verifySession("");
                        dialog.hide();
                    } else {
                        showToast("Please select a date.", GlobalToastEnum.WARNING);
                    }

                }
            });

            //Cancel dialog
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.hide();
                }

            });


            //Edit Searches
            editsearches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterwrap.setVisibility(View.VISIBLE);
                    optionwrap.setVisibility(View.GONE);
                }

            });

            //clear searches
            clearsearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH) + 1;
                    ismonthselected = false;
                    isyearselected = false;
                    currentlimit = 0;
                    verifySession("");
                    dialog.hide();
                }

            });

            viewarchiveemptyscreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (viewarchiveemptyscreen.getText().toString().equals("FILTER OPTIONS")) {
                        filterwrap.setVisibility(View.GONE);
                        optionwrap.setVisibility(View.VISIBLE);
                    } else {
                        filterwrap.setVisibility(View.VISIBLE);
                        optionwrap.setVisibility(View.GONE);
                    }
                    filterwrap.setVisibility(View.VISIBLE);
                    optionwrap.setVisibility(View.GONE);
                    spinType.setSelection(0); //set to default value of spinner
                    spinType1.setSelection(0);//set to default value of spinner
                    dialog.show();
                }

            });

            filteroption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    filterwrap.setVisibility(View.GONE);
                    optionwrap.setVisibility(View.VISIBLE);
                    spinType.setSelection(0); //set to default value of spinner
                    spinType1.setSelection(0);//set to default value of spinner
                    dialog.show();
                }

            });

            //REFRESH
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH) + 1;
                    ismonthselected = false;
                    isyearselected = false;
                    disableRefresh();
                    currentlimit = 0;
                    verifySession("");
                }
            });

            //refresh in no internet connection indicator
            refreshnointernet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH) + 1;
                    ismonthselected = false;
                    isyearselected = false;
                    disableRefresh();
                    currentlimit = 0;
                    verifySession("");
                }
            });

            //Scroll to view filter option or view archive
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                    View views = listView.getChildAt(listView.getChildCount() - 1);
                    int diff = (views.getBottom() - (listView.getHeight() + listView.getScrollY()));


                    // if diff is zero, then the bottom has been reached
                    if (diff == 0) {
                        if (isloadmore == true) {
                            currentlimit = currentlimit + 30;
                            Logger.debug("LOADMORE", "LOAD" + currentlimit);
                            verifySession("");
                        }


                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub

                    if (mlistData.size() > 0) {
                        if (listView.getFooterViewsCount() <= 0) {
                            listView.addFooterView(btnAddMore);
                        }
                        if (ismonthselected == true && isyearselected == true) {
                            btnAddMore.setText("FILTER OPTIONS");
                        } else {
                            btnAddMore.setText("VIEW ARCHIVE");
                        }

                        btnAddMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (btnAddMore.getText().equals("FILTER OPTIONS")) {
                                    filterwrap.setVisibility(View.GONE);
                                    optionwrap.setVisibility(View.VISIBLE);
                                } else {
                                    filterwrap.setVisibility(View.VISIBLE);
                                    optionwrap.setVisibility(View.GONE);
                                    spinType.setSelection(0); //set to default value of spinner
                                    spinType1.setSelection(0);//set to default value of spinner
                                }
                                dialog.show();
                            }
                        });
                    }


                }

            });

            //CLICK ON THE LIST
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    try {
                        txnno = mlistData.get(position).getTxnNo();
                        itemToDetails = mlistData.get(position);
                        subjectType = mlistData.get(position).getSubjectType();

                        if (itemToDetails.getStatus().equals("0")) {
                            verifySession(READ_NOTIF);
                        } else {
                            openDetails();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

            });


            if (CommonVariables.NOTIFICATIONFIRSTLOAD.equals("false")) {
//                createSession(getSessionCallback);
               // getAllNotifcationsV2();
                getNotificationsSecurityV2();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub

            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            ismonthselected = false;
            isyearselected = false;
            currentlimit = 0;

            swipeRefreshLayout.setRefreshing(true);
            verifySession("");


    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                    dateregistered = cursor.getString(cursor.getColumnIndex("dateregistration"));

                    String CurrentString = dateregistered;
                    String[] separated = CurrentString.split("-");
                    registrationyear = Integer.parseInt(separated[0]);
                    registrationmonth = Integer.parseInt(separated[1]);
                } while (cursor.moveToNext());
                populateNotification();
            }
            cursor.close();
        } catch (Exception e) {
            Logger.debug("okhttp", "ERROR: " + e.getMessage());
        } finally {
//            year = c.get(Calendar.YEAR);
//            month = c.get(Calendar.MONTH) + 1;
//            ismonthselected = false;
//            isyearselected = false;
//            currentlimit = 0;
//
//            swipeRefreshLayout.setRefreshing(true);
//            verifySession("");
        }
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

    /*---------------
     * FUNCTIONS
     * --------------*/

    private void populateNotification() {
        mlistData.clear();
        try {
            if (!getALlNotifications().isEmpty()) {
                mlistAdapter.update(getALlNotifications());
            } else {
                if (CommonVariables.NOTIFICATIONFIRSTLOAD.equals("false")) {
                    if (ismonthselected == true && isyearselected == true) {
                        viewarchiveemptyscreen.setText("FILTER OPTIONS");
                        emptyvoucherfilter.setVisibility(View.VISIBLE);
                        emptynotification.setVisibility(View.GONE);
                    } else {
                        viewarchiveemptyscreen.setText("VIEW ARCHIVE");
                        emptynotification.setVisibility(View.VISIBLE);
                        emptyvoucherfilter.setVisibility(View.GONE);
                    }

                } else {
                    //emptynotification.setVisibility(View.VISIBLE);
                }

            }
        } catch (Exception e) {

        }
    }

    private void verifySession(final String flag) {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) <= 0) { //no connection
                showNoInternetConnection();
                inableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                CommonFunctions.hideDialog();
            } else {
                mLoaderTimer.cancel();
                mLoaderTimer.start();

                mTvFetching.setText("Fetching notifications.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                //call AsynTask to perform network operation on separate thread
                if (flag.equals(READ_NOTIF)) {
                    new HttpAsyncTask().execute(CommonVariables.UPDATENOTIFICATIONSTATUS);
                    flaglag = READ_NOTIF;
                } else {
                    //new HttpAsyncTask().execute(cv.GETNOTIFICATIONS);
                    getNotificationsSecurityV2();
                    flaglag = "";
                }

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
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            try {

                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                if (flaglag == READ_NOTIF) {
                    jsonObject.accumulate("txnno", txnno);
                }
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("year", year);
                jsonObject.accumulate("month", month);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("result", currentlimit);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                CommonFunctions.hideDialog();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            try {


                if (result == null) {
                    showNoInternetConnection();
                    inableRefresh();
                } else if (result == "001") {
                    showToast("Invalid entry.", GlobalToastEnum.NOTICE);
                } else if (result == "002") {
                    showToast("Invalid session.", GlobalToastEnum.NOTICE);
                } else if (result == "003") {
                    showToast("Invalid authentication.", GlobalToastEnum.NOTICE);
                } else if (result == "error") {
                    showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    if (flaglag == READ_NOTIF) {
                        openDetails();
                    } else {
                        processList(result);
                    }
                }
            } catch (Exception e) {

            }

        }
    }

    private void openDetails() {
        try {
            Intent intent = new Intent(this, NotificationDetailsActivity.class);
            intent.putExtra("NOTIFICATION_OBJECT", itemToDetails);
            intent.putExtra("subject", subjectType);
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void processList(String result) {
        Log.d("roneldayanan","NOTIF : "+ result);
        try {
            swipeRefreshLayout.setRefreshing(false);
            hideNoInternetConnection();

            if (currentlimit == 0) {
                //clear  list first
                mlistData.clear();
                db.deleteNotifications(db);
            }

            JSONArray jsonArr = new JSONArray(result);
            if (jsonArr.length() > 0) {
                emptynotification.setVisibility(View.GONE);
                isloadmore = true;
            } else {
                isloadmore = false;
                emptynotification.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                String message = jsonObj.getString("Message");
                String status = jsonObj.getString("NotificationStatus");

                Logger.debug("roneldayanan", "|DATA: " + message);

                JSONObject mainObject = new JSONObject(message);
                JSONObject payload = mainObject.getJSONObject("payload");
                String txnno = payload.getString("txnno");
                String subject = payload.getString("subject");
                String sender = payload.getString("sender");
                String sendericon = payload.getString("senderlogo");
                String curmessage = payload.getString("message");
                String date = payload.getString("date");
                curmessage = curmessage.replace('[', ' ');
                curmessage = curmessage.replace(']', ' ');

                //for notificationdetails purpose
                if (!subject.equals("SUPPORTMESSAGE"))
                    db.insertNotification(db, txnno, subject, status, sender, sendericon, curmessage, date);
            }

            CommonVariables.NOTIFICATIONFIRSTLOAD = "false";
            mlistAdapter.update(getALlNotifications());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }

    private void inableRefresh() {
        refreshdisabled.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        refreshnointernet.setVisibility(View.VISIBLE);
    }

    public void showNoInternetConnection() {
        swipeRefreshLayout.setVisibility(View.GONE);
        emptynotification.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        nointernetconnection.setVisibility(View.VISIBLE);
    }

    public void hideNoInternetConnection() {
        inableRefresh();
        nointernetconnection.setVisibility(View.GONE);
        emptyvoucherfilter.setVisibility(View.GONE);
        emptynotification.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayAdapter<String> monthadapter;
            ArrayList<String> spinmonthlist = new ArrayList<String>();
            spinmonthlist = monthlist();
            monthadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
            monthadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType.setAdapter(monthadapter);

        } catch (Exception e) {
        }

    }

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType1.setAdapter(yearadapter);
        } catch (Exception e) {
        }

    }

    //make the number month to month name
    private ArrayList<String> monthlist() {

        int[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Select Month");

        for (int i = 0; i < months.length; i++) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            cal.set(Calendar.MONTH, months[i]);
            String month_name = month_date.format(cal.getTime());
            if (registrationyear == year && year != currentyear) { //meaning we need to filter the month range of the borrower
                if (i >= registrationmonth - 1 && (registrationyear - year) == 0) {
                    xVals.add(month_name);
                }
            } else if (year == currentyear) {
                if (i <= currentmonth - 1) {
                    xVals.add(month_name);
                }
            } else {
                xVals.add(month_name);
            }
        }

        return xVals;
    }

    //create year list
    private ArrayList<String> yearList() {

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            xVals.add(Integer.toString(i));
        }

        return xVals;
    }

    private ArrayList<NotificationItem> getALlNotifications() {
        ArrayList<NotificationItem> arrayList = new ArrayList<>();
        try {
            String senderLogo = "";
            Cursor c = db.getNotifications(db);
            Logger.debug("CUR SIZE", String.valueOf(c.getCount()));
            if (c.getCount() > 0) {
                c.moveToFirst();
                emptynotification.setVisibility(View.GONE);
                emptyvoucherfilter.setVisibility(View.GONE);
                do {
                    String txnno = c.getString(c.getColumnIndex("txnno"));
                    String status = c.getString(c.getColumnIndex("status"));
                    String sender = c.getString(c.getColumnIndex("sender"));
                    String sendericon = c.getString(c.getColumnIndex("sendericon"));
                    String message = c.getString(c.getColumnIndex("message"));
                    String date = c.getString(c.getColumnIndex("date"));
                    String subject = c.getString(c.getColumnIndex("subject"));
                    String firstleter = "";
                    if (sender.length() > 1) {
                        firstleter = sender.substring(0, 1);
                    } else {
                        firstleter = sender;
                    }

                    if (!sendericon.equals("") && sendericon.toLowerCase().contains("http")) {
                        senderLogo = sendericon;
                    } else {
                        senderLogo = firstleter;
                    }
                    arrayList.add(new NotificationItem(senderLogo, sender, message, date, sendericon, status, txnno,subject));

                } while (c.moveToNext());
            }
            c.close();
            mlistData = arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, NotificationsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    private Callback<String> getSessionCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (!response.body().isEmpty()
                            && !response.body().contains("<!DOCTYPE html>")
                            && !response.body().equals("001")
                            && !response.body().equals("002")
                            && !response.body().equals("003")
                            && !response.body().equals("004")
                            && !response.body().equals("error")) {

                        session = response.body();
                        getAllNotifcationsV2();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable throwable) {

        }
    };

    private void getAllNotifcationsV2() {
        Call<String> call = RetrofitBuild.getNotificationAPIService(getViewContext())
                .getNotifications(
                        sessionid,
                        imei,
                        userid,
                        borrowerid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
                        String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1),
                        "0"
                );

        call.enqueue(getAllNotifcationsV2Callback);
    }

    private Callback<String> getAllNotifcationsV2Callback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    JSONArray jsonArr = new JSONArray(response.body());
                    if (jsonArr.length() > 0) {
                        db.deleteNotifications(db);
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);
                            String message = jsonObj.getString("Message");
                            String status = jsonObj.getString("NotificationStatus");

                            JSONObject mainObject = new JSONObject(message);
                            JSONObject payload = mainObject.getJSONObject("payload");
                            String txnno = payload.getString("txnno");
                            String subject = payload.getString("subject");
                            String sender = payload.getString("sender");
                            String sendericon = payload.getString("senderlogo");
                            String curmessage = payload.getString("message");
                            String date = payload.getString("date");
                            curmessage = curmessage.replace('[', ' ');
                            curmessage = curmessage.replace(']', ' ');

                            if (!subject.equals("SUPPORTMESSAGE"))
                                db.insertNotification(db, txnno, subject, status, sender, sendericon, curmessage, date);
                        }
                    }
                }
                mlistAdapter.update(getALlNotifications());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable throwable) {
            Logger.debug(TAG, "onFailure: " + throwable.getLocalizedMessage());
        }
    };


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getNotificationsSecurityV2(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();

            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("year", String.valueOf(year));
            parameters.put("month", String.valueOf(month));
            parameters.put("devicetype",CommonVariables.devicetype);
            parameters.put("limit", String.valueOf(currentlimit));

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getNotificationsV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getNotificationsV2SecurityObject(getNotificationsV2SecurityCallback);

        }else{
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection();
            inableRefresh();
        }
    }

    private void getNotificationsV2SecurityObject(Callback<GenericResponse> notifications){
        Call<GenericResponse> call = RetrofitBuilder.getNotificationsV2API(getViewContext())
                .getNotificationsV2(
                        authenticationid,sessionid,param
                );

        call.enqueue(notifications);
    }

    private Callback<GenericResponse> getNotificationsV2SecurityCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                switch (response.body().getStatus()){
                    case "000":
                        String decrypteddata = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                        processList(decrypteddata);
                        break;
                    case "003": case "002":
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        break;
                    default:
                         showErrorToast(CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage()));
                        break;
                }
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable throwable) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            Logger.debug(TAG, "onFailure: " + throwable.getLocalizedMessage());
        }
    };
}
