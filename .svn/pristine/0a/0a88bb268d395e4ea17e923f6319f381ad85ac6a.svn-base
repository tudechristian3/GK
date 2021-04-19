package com.goodkredit.myapplication.fragments.transactions;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.transactions.BorrowingsRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Borrowings extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    //Declaration
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;

    private BorrowingsRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    static String borrowerid = "";
    static String userid = "";
    static String imei = "";
    static String sessionidval = "";
    static String dateregistered = "";
    ArrayList<Transaction> borrowingList;
    static Calendar c;
    static int year;
    static int month;
    static int registrationyear;
    static int registrationmonth;
    static int currentyear = 0;//make this not changeable for (filter condition)
    static int currentmonth = 0; //make this not changeable for (filter condition)


    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isViewShown = false;
    public static Boolean isFirstload = false;

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

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_borrowings, null);

        try {
            isFirstload = true;

            //Set context
            mcontext = getActivity();

            //initialized local database
            db = new DatabaseHandler(getActivity());

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            //initialize date
            c = Calendar.getInstance();
            currentyear = c.get(Calendar.YEAR);
            currentmonth = c.get(Calendar.MONTH) + 1;
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;

             Logger.debug("CURYEAR", "currentyear" + currentyear);

            //initialize button list footer
            btnAddMore = new Button(getActivity());
            btnAddMore.setBackgroundResource(R.color.colorwhite);
            btnAddMore.setTextColor(getResources().getColor(R.color.buttons));
            btnAddMore.setTypeface(Typeface.DEFAULT_BOLD);

            //initialize element for view archive in empty screen
            viewarchiveemptyscreen = rootView.findViewById(R.id.viewarchiveemptyscreen);
            filteroption = rootView.findViewById(R.id.filteroption);
            emptyvoucherfilter = rootView.findViewById(R.id.emptyvoucherfilter);

            //initialize elements
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

            borrowingList = new ArrayList<>();
            recyclerView = rootView.findViewById(R.id.recycler_view_borrowings);

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

                if (!isViewShown) {
                    if (CommonVariables.BORROWINGSISFIRSTLOAD.equals("true")) {
                        verifySession();
                    }

                    isFirstload = false;
                }
            }
            cursor.close();

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


            /**************
             * TRIGGERS
             * ***********/

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
                        ((BaseActivity) getActivity()).showToast("Please select a date", GlobalToastEnum.WARNING);
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

            //Cancel dialog
            popcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
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
                    CommonVariables.BORROWINGSISFIRSTLOAD = "true";
                    verifySession();
                    dialog.dismiss();
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

            //Scroll to view filter option or view archive
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
//                @Override
//                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//
//                    if (borrowingList.size() > 0) {
//
//                        if (listView.getFooterViewsCount() <= 0) {
//                            listView.addFooterView(btnAddMore);
//                        }
//
//                        if (ismonthselected == true && isyearselected == true) {
//                            btnAddMore.setText("FILTER OPTIONS");
//                        } else {
//                            btnAddMore.setText("VIEW ARCHIVE");
//                        }
//
//                        btnAddMore.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (btnAddMore.getText().equals("FILTER OPTIONS")) {
//                                    filterwrap.setVisibility(View.GONE);
//                                    optionwrap.setVisibility(View.VISIBLE);
//                                } else {
//                                    filterwrap.setVisibility(View.VISIBLE);
//                                    optionwrap.setVisibility(View.GONE);
//                                    spinType.setSelection(0); //set to default value of spinner
//                                    spinType1.setSelection(0);//set to default value of spinner
//                                }
//
//                                dialog.show();
//                            }
//                        });
//                    }
//
//                }
//            });


//            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem,
//                                     int visibleItemCount, int totalItemCount) {
//                    // TODO Auto-generated method stub
//
//                    if (borrowingList.size() > 0) {
//                        if (listView.getFooterViewsCount() <= 0) {
//                            listView.addFooterView(btnAddMore);
//                        }
//                        if (ismonthselected == true && isyearselected == true) {
//                            btnAddMore.setText("FILTER OPTIONS");
//                        } else {
//                            btnAddMore.setText("VIEW ARCHIVE");
//                        }
//
//                        btnAddMore.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                if (btnAddMore.getText().equals("FILTER OPTIONS")) {
//                                    filterwrap.setVisibility(View.GONE);
//                                    optionwrap.setVisibility(View.VISIBLE);
//                                } else {
//                                    filterwrap.setVisibility(View.VISIBLE);
//                                    optionwrap.setVisibility(View.GONE);
//                                    spinType.setSelection(0); //set to default value of spinner
//                                    spinType1.setSelection(0);//set to default value of spinner
//                                }
//
//                                dialog.show();
//                            }
//                        });
//                    }
//
//
//                }
//
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        return rootView;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        try {
            if (isVisibleToUser) {//dosomething when the fragment is visible

                preloadLocalBorrowings();
                if (getView() != null) {
                    if (isFirstload == true) {

                        if (CommonVariables.BORROWINGSISFIRSTLOAD.equals("true")) {
                            verifySession();
                        }
                        isViewShown = true;
                        isFirstload = false;
                    }
                }
            } else {//dosomething else.}
                isViewShown = false;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        try {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            ismonthselected = false;
            isyearselected = false;
            swipeRefreshLayout.setRefreshing(true);
            verifySession();

        } catch (Exception e) {

        }

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

                preloadLocalBorrowings();
            }
            cursor.close();
            ((BaseActivity) getActivity()).setupOverlay();
            ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
        } catch (Exception e) {
        } finally {
        }
    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }

    /*---------------
     * FUNCTIONS
     * --------------*/


    //pre load local data
    private void preloadLocalBorrowings() {

        try {
            //preload local borrowings
            borrowingList.clear();
            Cursor c = db.getBorrowings(db);
            if (c.getCount() > 0) {
                c.moveToFirst();
                emptyvoucher.setVisibility(View.GONE);
                emptyvoucherfilter.setVisibility(View.GONE);
                do {
                    String transactionno = c.getString(c.getColumnIndex("transactionno"));
                    String datetimecompleted = c.getString(c.getColumnIndex("datetimecompleted"));
                    String totalvoucheramount = c.getString(c.getColumnIndex("totalvoucheramount"));
                    String totalnumvoucher = c.getString(c.getColumnIndex("totalnumvouchers"));

                    borrowingList.add(new Transaction(transactionno, datetimecompleted, totalvoucheramount, totalnumvoucher));

                } while (c.moveToNext());

                //Update the data in the list
                updateListAddapter();


            } else {

                if (CommonVariables.BORROWINGSISFIRSTLOAD.equals("false")) {

                    if (ismonthselected == true && isyearselected == true) {
                        viewarchiveemptyscreen.setText("FILTER OPTIONS");
                        emptyvoucherfilter.setVisibility(View.VISIBLE);
                        emptyvoucher.setVisibility(View.GONE);
                    } else {
                        viewarchiveemptyscreen.setText("VIEW ARCHIVE");
                        emptyvoucher.setVisibility(View.VISIBLE);
                        emptyvoucherfilter.setVisibility(View.GONE);
                    }
                }
            }
            c.close();

        } catch (Exception e) {
        } finally {
        }


    }

    //create session
    private void verifySession() {

        try {
            int status = CommonFunctions.getConnectivityStatus(getActivity());
            if (status == 0) { //no connection
                showNoInternetConnection();
                inableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {

                ((BaseActivity) getActivity()).setupOverlay();
                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
                mLoaderTimer.cancel();
                mLoaderTimer.start();
                mTvFetching.setText("Fetching borrowings.");
                mTvWait.setText("Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                //call AsynTask to perform network operation on separate thread
                //new HttpAsyncTask().execute(CommonVariables.GETBORROWINGSTRANSACTION);
                getBorrowings();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), BorrowingsDetails.class);
        intent.putExtra("ID", borrowingList.get(position).getTransactionNumber());
        intent.putExtra("DATE", borrowingList.get(position).getDateCompleted());
        startActivity(intent);
    }

    //process request
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
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("year", year);
                jsonObject.accumulate("month", month);
                jsonObject.accumulate("authcode", authcode);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
                //cf.hideDialog();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            try {
                if (result == null) {
                    showNoInternetConnection();
                    inableRefresh();
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showNoInternetConnection();
                    inableRefresh();
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    processList(result);
                }
            } catch (Exception e) {
            }

        }
    }

    //process result of request
    private void processList(String result) {

        try {
            //hide no internet indicator
            hideNoInternetConnection();

            //clear  list first
            borrowingList.clear();

            JSONArray jsonArr = new JSONArray(result);
            db.deleteBorrowings(db); //delete local data before inserting

            if (jsonArr.length() > 0) {
                emptyvoucher.setVisibility(View.GONE);
                emptyvoucherfilter.setVisibility(View.GONE);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String transactionno = jsonObj.getString("TransactionNo");
                    String datecompleted = jsonObj.getString("DateTimeCompleted");
                    String totalvoucheramount = jsonObj.getString("TotalVoucherAmount");
                    String totalnumofvoucher = jsonObj.getString("TotalNoOfVouchers");
                    DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
                    totalvoucheramount = formatter.format(Double.parseDouble(totalvoucheramount));

                    //insert local
                    db.insertBorrowings(db, transactionno, datecompleted, totalvoucheramount, totalnumofvoucher);
                    borrowingList.add(new Transaction(transactionno, datecompleted, totalvoucheramount, totalnumofvoucher));

                }

                CommonVariables.BORROWINGSISFIRSTLOAD = "false"; //setting to false so that it will not reload again
                //update data in list
                updateListAddapter();


            } else {
                if (ismonthselected == true && isyearselected == true) {
                    viewarchiveemptyscreen.setText("FILTER OPTIONS");
                    emptyvoucherfilter.setVisibility(View.VISIBLE);
                    emptyvoucher.setVisibility(View.GONE);
                } else {
                    viewarchiveemptyscreen.setText("VIEW ARCHIVE");
                    emptyvoucher.setVisibility(View.VISIBLE);
                    emptyvoucherfilter.setVisibility(View.GONE);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Update result in the list
    private void updateListAddapter() {
        try {
            // updating UI from Background Thread
            (getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Logger.debug("OPENTAB", "BORROWINGUDAPTER");
                    recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
                    recyclerView.setNestedScrollingEnabled(false);
//                    adapter = new BorrowingsRecyclerAdapter(getViewContext(), borrowingList, Borrowings.this);
                    recyclerView.setAdapter(adapter);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //disable refresh button
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }

    //inable refresh button
    private void inableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

    }

    //show page with no internection connection indicator
    private void showNoInternetConnection() {
        try {
            Cursor c = db.getBorrowings(db);
            if (c.getCount() <= 0) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hide page with no internection connection indicator
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
        int newyear = registrationyear;
        for (int i = registrationyear; i <= currentyear; i++) {
            xVals.add(Integer.toString(newyear));
            newyear = registrationyear + 1;
        }

        return xVals;
    }


    /*
     * SECURITY UPDATE
     *  AS OF
     * OCTOBER 2019
     * */

    private void getBorrowings() {
        try {
            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(year));
                parameters.put("month", String.valueOf(month));

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getBorrowingsV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getBorrowingsObject(getBorrowingsSessionV2);

            } else {
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                showNoInternetConnection();
            }
        } catch (Exception e) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
        }
    }

    private void getBorrowingsObject(Callback<GenericResponse> getBorrowingsObject) {
        Call<GenericResponse> getBorrowings = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getBorrowings(
                        authenticationid, sessionid, param
                );
        getBorrowings.enqueue(getBorrowingsObject);
    }

    private final Callback<GenericResponse> getBorrowingsSessionV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        processList(decryptedData);
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };
}
