package com.goodkredit.myapplication.fragments.soa;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.fragment.app.ListFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class UnpaidBills extends ListFragment implements SwipeRefreshLayout.OnRefreshListener {
    //Declaration
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;

    ListAdapter adapter;
    ListView listView;
    static RelativeLayout emptyvoucher;
    static ImageView refresh;
    static RelativeLayout nointernetconnection;
    static ImageView refreshnointernet;
    static ImageView refreshdisabled;
    static ImageView refreshdisabled1;

    static String borrowerid = "";
    static String userid = "";
    static String imei = "";
    static String sessionidval = "";

    ArrayList<HashMap<String, String>> List;
    // ALL JSON node names

    private static final String TAG_TRANSACTIONNO = "transactionno";
    private static final String TAG_DATE = "date";
    private static final String TAG_TOTALAMOUNT = "totalamount";
    private SwipeRefreshLayout swipeRefreshLayout;
    public static Boolean isFirstload = false;
    private boolean isViewShown = false;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private CountDownTimer mLoaderTimer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            isFirstload = true;

            //inflate view
            rootView = inflater.inflate(R.layout.fragment_unpaid_bills, null);

            //Set context
            mcontext = getActivity();

            //initialized local database
            db = new DatabaseHandler(getActivity());


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
                } while (cursor.moveToNext());

                if (!isViewShown) {
                    if (CommonVariables.PAIDBILLISFIRSTLOAD.equals("true")) {
//                        verifySession();
                    }

                    isFirstload = false;
                }
            }
            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


        /***********
         * TRIGGERS
         * */


        //REFRESH
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableRefresh();
//                verifySession();
            }
        });

        //refresh in no internet connection indicator
        refreshnointernet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableRefresh();
//                verifySession();
            }
        });

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
                } while (cursor.moveToNext());
                preloadLocalUnpaidBills();
            }
            cursor.close();
            ((BaseActivity) getActivity()).setupOverlay();
            ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
        } catch (Exception e) {
        }


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { //this is to know what tab is open
        super.setUserVisibleHint(isVisibleToUser);

        try {
            if (isVisibleToUser) {
                if (getView() != null) {

                    preloadLocalUnpaidBills();

                    if (isFirstload == true) {
                        if (CommonVariables.UNPAINBILLISFIRSTLOAD.equals("true")) {
//                            verifySession();
                        }
                        isViewShown = true;
                        isFirstload = false;
                    }
                }
            } else {
                isViewShown = false;
            }
        } catch (Exception e) {
        }


    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
//        verifySession();

    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }

    /*---------------
   * FUNCTIONS
   * --------------*/

    private void preloadLocalUnpaidBills() {

        try {
            //preload unpaid bills
            List.clear();
            Cursor c = db.getUnpaidbills(db);
            if (c.getCount() > 0) {
                c.moveToFirst();
                emptyvoucher.setVisibility(View.GONE);
                do {
                    String billingid = c.getString(c.getColumnIndex("billingid"));
                    String duedate = c.getString(c.getColumnIndex("duedate"));
                    String netamount = c.getString(c.getColumnIndex("netamount"));


                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

                    // adding each child node to HashMap key => value
                    map.put(TAG_TRANSACTIONNO, billingid);
                    map.put(TAG_DATE, "Payment due on " + CommonFunctions.convertDate1(duedate));
                    map.put(TAG_TOTALAMOUNT, formatter.format(Double.parseDouble(netamount)));

                    // adding HashList to ArrayList
                    List.add(map);
                } while (c.moveToNext());

                updateDataList();

            } else {
                if (CommonVariables.UNPAINBILLISFIRSTLOAD.equals("false")) {
                    emptyvoucher.setVisibility(View.VISIBLE);
                }
            }
            c.close();

        } catch (Exception e) {
        }

    }

//    private void verifySession() {
//        try {
//            int status = CommonFunctions.getConnectivityStatus(getActivity());
//            if (status == 0) { //no connection
//                showNoInternetConnection();
//                inableRefresh();
//                swipeRefreshLayout.setRefreshing(false);
//                ((BaseActivity) getActivity()).showToast("No internet connection.", GlobalToastEnum.NOTICE);
//            } else {
//
//                ((BaseActivity) getActivity()).setupOverlay();
//                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
////                cf.showDialog(mcontext, "", "Fetching Unpaid Bills. Please wait ...", false);
//                mLoaderTimer.cancel();
//                mLoaderTimer.start();
//                mTvFetching.setText("Fetching unpaid bills.");
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
//                            swipeRefreshLayout.setRefreshing(false);
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//
//                        } else if (data.equals("001")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            swipeRefreshLayout.setRefreshing(false);
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("002")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            swipeRefreshLayout.setRefreshing(false);
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Invalid User", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("error")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            swipeRefreshLayout.setRefreshing(false);
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Something went wrong with your connection. Please check.", GlobalToastEnum.NOTICE);
//                        } else if (data.contains("<!DOCTYPE html>")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            swipeRefreshLayout.setRefreshing(false);
//
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            ((BaseActivity) getActivity()).showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                        } else {
//                            sessionidval = data;
//                            //call AsynTask to perform network operation on separate thread
//
//                            new HttpAsyncTask().execute(CommonVariables.GETSETTLEMENTQUEUE);
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
//    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";

            try {


                String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionidval);
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("sessionid", sessionidval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
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
                    ((BaseActivity) getActivity()).showToast("Invalid Entry.", GlobalToastEnum.NOTICE);

                } else if (result.equals("002")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
                } else if (result.equals("error")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showNoInternetConnection();
                    inableRefresh();
                    ((BaseActivity) getActivity()).showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    processList(result);
                }
            } catch (Exception e) {
            }


        }
    }

    private void processList(String result) {

        try {
            hideNoInternetConnection();

            //clear  list first
            List.clear();

            JSONArray jsonArr = new JSONArray(result);
            db.deleteUnpaidBills(db); //delete local data before inserting

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
                    String periodfrom = jsonObj.getString("PeriodFrom");
                    String periodto = jsonObj.getString("PeriodTo");


                    String cutoverperiod = CommonFunctions.getDateShortenFromDateTime(CommonFunctions.convertDate(periodfrom)) + " - " + CommonFunctions.getDateShortenFromDateTime(CommonFunctions.convertDate(periodto));

                    db.insertUnpaidBills(db, billingid, guarantorid, guarantorname, statementdate, duedate, amount, advancepayment, partialpay, commission, netamount, duebalance, cutoverperiod);

                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();
                    DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

                    // adding each child node to HashMap key => value
                    map.put(TAG_TRANSACTIONNO, billingid);
                    map.put(TAG_DATE, "Payment due on " + CommonFunctions.convertDate1(duedate));
                    map.put(TAG_TOTALAMOUNT, formatter.format(Double.parseDouble(netamount)));

                    // adding HashList to ArrayList
                    List.add(map);
                }
                CommonVariables.UNPAINBILLISFIRSTLOAD = "false"; //set to false so that it wont load again
                updateDataList();

            } else {
                emptyvoucher.setVisibility(View.VISIBLE);
            }


        } catch (Exception e) {

        }
    }

    private void updateDataList() {

        try {
            // updating UI from Background Thread
            (getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    // Updating parsed JSON data into ListView
                    adapter = new SimpleAdapter(getActivity(), List, R.layout.fragment_unpaid_bills_item, new String[]{
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

                            String label = "Billing ID, Sponsor ID, Sponsor Name,Statement Date, Due Date, Amount, Partial Payment, Net Amount, Cut Over Period";
                            String value = "billingid,guarantorid,guarantorname,statementdate,duedate,amount,partialpayment,netamount,cutoverperiod";
                            Intent intent = new Intent(getActivity(), SettlementDetailsActivity.class);
                            intent.putExtra("ID", billingidselected);
                            intent.putExtra("TABLE", "unpaidbills");
                            intent.putExtra("LABEL", label);
                            intent.putExtra("VALUE", value);
                            intent.putExtra("FIELD", "billingid");
                            intent.putExtra("TITLE", "Unpaid Bill Details");
                            startActivity(intent);
                        }
                    });

                }
            });
        } catch (Exception e) {
        }

    }

    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }

    private void inableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

    }

    private void showNoInternetConnection() {
        try {
            Cursor c = db.getUnpaidbills(db);
            if (c.getCount() <= 0) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
            c.close();

        } catch (Exception e) {
        }

    }

    private void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            inableRefresh();
        } catch (Exception e) {
        }

    }
}
