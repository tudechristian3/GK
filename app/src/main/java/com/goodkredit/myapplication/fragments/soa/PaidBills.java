package com.goodkredit.myapplication.fragments.soa;

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
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.CreateSession;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class PaidBills extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    //Declaration
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;


    ListAdapter adapter;
    ListView listView;
    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;


    private String borrowerid = "";
    private String userid = "";
    private String imei = "";
    private String sessionidval = "";
    private String dateregistered = "";
    private ArrayList<HashMap<String, String>> List;
    // ALL JSON node names

    private static final String TAG_TRANSACTIONNO = "transactionno";
    private static final String TAG_DATE = "date";
    private static final String TAG_TOTALAMOUNT = "totalamount";
    private SwipeRefreshLayout swipeRefreshLayout;
    public static Boolean isFirstload = false;
    private boolean isViewShown = false;


    private Calendar c;
    private int year;
    private int month;
    private int registrationyear;
    private int registrationmonth;
    private int currentyear = 0;//make this not changeable for (filter condition)
    private int currentmonth = 0; //make this not changeable for (filter condition)

    private Button btnAddMore;
    private Dialog dialog;
    private Spinner spinType;
    private Spinner spinType1;
    private TextView popfilter;
    private TextView popcancel;
    private TextView viewarchiveemptyscreen;
    private RelativeLayout emptyvoucherfilter;
    private TextView filteroption;

    //dialog spin selected indicator
    private Boolean ismonthselected = false;
    private Boolean isyearselected = false;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private CountDownTimer mLoaderTimer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;

        isFirstload = true;

        //inflate view
        rootView = inflater.inflate(R.layout.fragment_paid_bills, null);

        //Set context
        mcontext = getActivity();

        //initialized local database
        db = new DatabaseHandler(getActivity());

        try {
            //initialize date
            c = Calendar.getInstance();
            currentyear = c.get(Calendar.YEAR);
            currentmonth = c.get(Calendar.MONTH) + 1;
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;

            //initialize button list footer
            btnAddMore = new Button(getActivity());
            btnAddMore.setBackgroundResource(R.color.colorwhite);
            btnAddMore.setTextColor(getResources().getColor(R.color.buttons));
            btnAddMore.setTypeface(Typeface.DEFAULT_BOLD);

            //initialize element for view archive in empty screen
            viewarchiveemptyscreen = rootView.findViewById(R.id.viewarchiveemptyscreen);
            filteroption = rootView.findViewById(R.id.filteroption);
            emptyvoucherfilter = rootView.findViewById(R.id.emptyvoucherfilter);

            //initialize elements in fragment
            swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(this);
            emptyvoucher = rootView.findViewById(R.id.emptyvoucher);
            refresh = rootView.findViewById(R.id.refresh);
            nointernetconnection = rootView.findViewById(R.id.nointernetconnection);
            refreshnointernet = rootView.findViewById(R.id.refreshnointernet);
            refreshdisabled = rootView.findViewById(R.id.refreshdisabled);
            refreshdisabled1 = rootView.findViewById(R.id.refreshdisabled1);

            mLlLoader = rootView.findViewById(R.id.loaderLayout);
            mTvFetching = rootView.findViewById(R.id.fetching);
            mTvWait = rootView.findViewById(R.id.wait);

            // Hashmap for ListView
            List = new ArrayList<HashMap<String, String>>();
            listView = rootView.findViewById(android.R.id.list);

            final ImageView backgroundOne = rootView.findViewById(R.id.background_one);
            final ImageView backgroundTwo = rootView.findViewById(R.id.background_two);

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

            //get account information
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

            sessionidval = PreferenceUtils.getSessionID(getContext());
            // cursor.close();


            //create a Filter confirmation
            dialog = new Dialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light));
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


            /***********
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
                        verifySession();
                        dialog.hide();
                    } else {
                        ((BaseActivity) getActivity()).showToast("Please select a date.", GlobalToastEnum.WARNING);
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
                    verifySession();
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

            //Scroll to view filter option or view archive
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    // TODO Auto-generated method stub

                    if (List.size() > 0) {
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


            //REFRESH
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    year = c.get(Calendar.YEAR);
                    month = c.get(Calendar.MONTH) + 1;
                    ismonthselected = false;
                    isyearselected = false;
                    disableRefresh();
                    verifySession();
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
                    verifySession();
                }
            });

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }

        return rootView;

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
                preloadLocalPaidBills();
            }
            cursor.close();
            ((BaseActivity) getActivity()).setupOverlay();
            ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { //this is to know what tab is open
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {
                if (getView() != null) {
                    preloadLocalPaidBills();

                    if (isFirstload == true) {
                        verifySession();
                        isFirstload = false;
                    }
                }
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onRefresh() {
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        ismonthselected = false;
        isyearselected = false;
        swipeRefreshLayout.setRefreshing(true);
        verifySession();

    }

    /*---------------
   * FUNCTIONS
   * --------------*/

    //preload local data
    private void preloadLocalPaidBills() {

        try {
            //preload paid bills
            List.clear();
            Cursor c = db.getPaidBills(db);
            if (c.getCount() > 0) {
                c.moveToFirst();
                emptyvoucher.setVisibility(View.GONE);
                do {
                    String billingid = c.getString(c.getColumnIndex("billingid"));
                    String duedate = c.getString(c.getColumnIndex("duedate"));
                    String netamount = c.getString(c.getColumnIndex("netamount"));
                    String datetimepaid = c.getString(c.getColumnIndex("datetimepaid"));
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

                    // adding each child node to HashMap key => value
                    map.put(TAG_TRANSACTIONNO, billingid);
                    map.put(TAG_DATE, "Payment made on " + CommonFunctions.convertDate1(datetimepaid));
                    map.put(TAG_TOTALAMOUNT, formatter.format(Double.parseDouble(netamount)));

                    // adding HashList to ArrayList
                    List.add(map);
                } while (c.moveToNext());

                updateDataList();

            } else {
                if (CommonVariables.PAIDBILLISFIRSTLOAD.equals("false")) {
                    emptyvoucher.setVisibility(View.VISIBLE);
                }
            }
            //  c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //create session
//    private void verifySession() {
//
//        try {
//            int status = CommonFunctions.getConnectivityStatus(getActivity());
//            if (status == 0) { //no connection
//                showNoInternetConnection();
//                inableRefresh();
//                swipeRefreshLayout.setRefreshing(false);
//                ((BaseActivity) getActivity()).showToast("No internet connection.", GlobalToastEnum.NOTICE);
//            } else {
//                ((BaseActivity) getActivity()).setupOverlay();
//                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
//
//                mLoaderTimer.cancel();
//                mLoaderTimer.start();
//
//                mTvFetching.setText("Fetching paid bills.");
//                mTvWait.setText(" Please wait...");
//                mLlLoader.setVisibility(View.VISIBLE);
//
//                CreateSession newsession = new CreateSession(getActivity());
//                newsession.setQueryListener(new CreateSession.QueryListener() {
//                    @SuppressWarnings("unchecked")
//                    public void QuerySuccessFul(String data) {
//                        if (data == null) {
//                            showNoInternetConnection();
//                            inableRefresh();
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("001")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("002")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Invalid Borrower. Please check..", GlobalToastEnum.WARNING);
//                        } else if (data.equals("error")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                        } else if (data.contains("<!DOCTYPE html>")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                        } else {
//                            sessionidval = data;
//                            //call AsynTask to perform network operation on separate thread
//                            new HttpAsyncTask().execute(CommonVariables.GETSETTLEMENTHISTORY);
//                        }
//                    }
//
//                });
//                newsession.execute(CommonVariables.CREATESESSION, imei, userid);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void verifySession() {

        try {
            int status = CommonFunctions.getConnectivityStatus(getActivity());
            if (status == 0) { //no connection
                showNoInternetConnection();
                inableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                ((BaseActivity) getActivity()).showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                ((BaseActivity) getActivity()).setupOverlay();
                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));

                mLoaderTimer.cancel();
                mLoaderTimer.start();

                mTvFetching.setText("Fetching paid bills.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                //call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute(CommonVariables.GETSETTLEMENTHISTORY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //send request to server
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionidval);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("sessionid", sessionidval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("year", year);
                jsonObject.accumulate("month", month);
                jsonObject.accumulate("authcode", authcode);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                CommonFunctions.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {
                CommonFunctions.hideDialog();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                if (result == null) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("Invalid Entry.", GlobalToastEnum.ERROR);
                } else if (result.equals("002")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("Invalid Session.", GlobalToastEnum.ERROR);
                } else if (result.equals("003")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("Invalid Authentication.", GlobalToastEnum.ERROR);
                } else if (result.equals("error")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("No internet connection. Please try again.", GlobalToastEnum.WARNING);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("Connection is slow. Please try again.", GlobalToastEnum.WARNING);
                } else {
                    processList(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //process request
    private void processList(String result) {

        try {
            hideNoInternetConnection();
            //clear  list first
            List.clear();

            JSONArray jsonArr = new JSONArray(result);

            Logger.debug("DATAPAID", result);

            db.deletePaidBills(db); //delete local data before inserting

            if (jsonArr.length() > 0) {
                emptyvoucher.setVisibility(View.GONE);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String billingid = jsonObj.getString("BillingID");
                    String guarantorid = jsonObj.getString("GuarantorID");
                    String guarantorname = jsonObj.getString("GuarantorName");
                    String statementdate = jsonObj.getString("StatementDate");
                    String duedate = jsonObj.getString("DueDate");
                    String amount = jsonObj.getString("Amount");
                    String advancepayment = jsonObj.getString("AdvancePaymentAmount");
                    String partialpay = jsonObj.getString("PartialPaymentAmount");
                    String commission = jsonObj.getString("Commission");
                    String netamount = jsonObj.getString("NetAmount");
                    String duebalance = jsonObj.getString("DueBalance");
                    String datetimepaid = jsonObj.getString("DateTimePaid");
                    String paymenttxnno = jsonObj.getString("PaymentTxnNo");
                    String periodfrom = jsonObj.getString("PeriodFrom");
                    String periodto = jsonObj.getString("PeriodTo");
                    String cutoverperiod = CommonFunctions.getDateShortenFromDateTime(CommonFunctions.convertDate(periodfrom)) + " - " + CommonFunctions.getDateShortenFromDateTime(CommonFunctions.convertDate(periodto));

                    db.insertPaidBills(db, billingid, guarantorid, guarantorname, statementdate, duedate, amount, advancepayment, partialpay, commission, netamount, duebalance, cutoverperiod, datetimepaid, paymenttxnno);

                    DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_TRANSACTIONNO, billingid);
                    map.put(TAG_DATE, "Payment made on " + CommonFunctions.convertDate1(datetimepaid));
                    map.put(TAG_TOTALAMOUNT, formatter.format(Double.parseDouble(netamount)));

                    // adding HashList to ArrayList
                    List.add(map);
                }

                CommonVariables.PAIDBILLISFIRSTLOAD = "false"; //set to false so that it wont load again
                updateDataList();

            } else {
                emptyvoucher.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //update data in the list
    private void updateDataList() {
        try {

            // updating UI from Background Thread
            (getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // Updating parsed JSON data into ListView
                    adapter = new SimpleAdapter(getActivity(), List, R.layout.fragment_paid_bills_item, new String[]{
                            TAG_TRANSACTIONNO, TAG_DATE, TAG_TOTALAMOUNT},
                            new int[]{
                                    R.id.transactionno,
                                    R.id.date,
                                    R.id.totalamount});
                    // updating listview

                    setListAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String billingidselected = ((TextView) view.findViewById(R.id.transactionno)).getText().toString();

                            String label = "Billing ID,Sponsor ID,Sponsor Name,Statement Date,Due Date,Partial Payment,Net Amount,Cut Over Period,Payment Date,Payment Transaction Number";
                            String value = "billingid,guarantorid,guarantorname,statementdate,duedate,partialpayment,netamount,cutoverperiod,datetimepaid,paymenttxnno";
                            Intent intent = new Intent(getActivity(), SettlementDetailsActivity.class);
                            intent.putExtra("ID", billingidselected);
                            intent.putExtra("TABLE", "paidbills");
                            intent.putExtra("LABEL", label);
                            intent.putExtra("VALUE", value);
                            intent.putExtra("FIELD", "billingid");
                            intent.putExtra("TITLE", "Paid Bill Details");
                            startActivity(intent);
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //disable refresh button on empty screen
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }

    //enable refresh button on empty screen
    private void inableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //show no internet connection indicator
    private void showNoInternetConnection() {
        try {
            Cursor c = db.getPaidBills(db);
            if (c.getCount() <= 0) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
            //  c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hide no internet connection
    private void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            inableRefresh();
        } catch (Exception e) {
        }

    }

    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayAdapter<String> monthadapter;
            ArrayList<String> spinmonthlist = new ArrayList<String>();
            spinmonthlist = monthlist();
            monthadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
            monthadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType.setAdapter(monthadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, yearList());
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
}
