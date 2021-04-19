package com.goodkredit.myapplication.fragments.transactions;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.ConsumedAdapter;
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
import com.goodkredit.myapplication.utilities.V2Utils;
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
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


public class Consummated extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    //Declaration
    private DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;

    StickyListHeadersListView listView;
    static RelativeLayout emptyvoucher;
    static ImageView refresh;
    static RelativeLayout nointernetconnection;
    static ImageView refreshnointernet;
    static ImageView refreshdisabled;
    static ImageView refreshdisabled1;

    static String borrowerid = "";
    static String imei = "";
    static String sessionidval = "";
    static String userid = "";
    static String dateregistered = "";
    private ArrayList<Transaction> arrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    static Calendar c;
    static int year;
    static int month;
    static int registrationyear;
    static int registrationmonth;
    static int currentyear = 0;//make this not changeable for (filter condition)
    static int currentmonth = 0; //make this not changeable for (filter condition)

    static Button btnAddMore;
    static Dialog dialog = null;
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

    private ConsumedAdapter adapter;
    private boolean isRefresh = false;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY UPDATE
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    public static Consummated newInstance(String value) {
        Consummated fragment = new Consummated();
        Bundle b = new Bundle();
        b.putString("title", value);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        //inflate view
        rootView = inflater.inflate(R.layout.fragment_consummated, null);

        try {

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

            //initialize button list footer
            btnAddMore = new Button(getActivity());
            btnAddMore.setBackgroundResource(R.color.colorwhite);
            btnAddMore.setTextColor(getResources().getColor(R.color.buttons));
            btnAddMore.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_BOLD));

            //initialize element for view archive in empty screen
            viewarchiveemptyscreen = rootView.findViewById(R.id.viewarchiveemptyscreen);
            filteroption = rootView.findViewById(R.id.filteroption);
            emptyvoucherfilter = rootView.findViewById(R.id.emptyvoucherfilter);

            //initialize elements of the fragment
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
            arrayList = new ArrayList<>();
            listView = rootView.findViewById(R.id.consumedListView);
            listView.setOnItemClickListener(this);

            adapter = new ConsumedAdapter(getContext(), getAllMerchantTransaction());
            listView.setAdapter(adapter);

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
            cursor.close();

            //create a Filter confirmation Dialog
            dialog = new Dialog(new ContextThemeWrapper(getContext(), android.R.style.Theme_Holo_Light));
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
            createYearSpinnerAdapter();

            /***********
             * TRIGGERS
             * **********/

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
                    if (isyearselected && ismonthselected) {
                        isRefresh = true;
                        verifySession();
                        dialog.hide();
                    } else {
                        showToast("Please select a date.", GlobalToastEnum.NOTICE);
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

            //click the view archive button on the empty screen
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

            //click the filter option button in empty screen with filter result
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

                    if (arrayList.size() > 0) {
                        if (listView.getFooterViewsCount() <= 0) {
                            listView.addFooterView(btnAddMore);
                        }
                        if (ismonthselected && isyearselected) {
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
                    isRefresh = true;
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
                    isRefresh = true;
                    verifySession();
                }
            });


            if (CommonVariables.CONSUMMATIONISFIRSTLOAD) {
                verifySession();
            } else {
                preloadLocalConsummated();
                adapter.updateList(getAllMerchantTransaction());
            }
            //  db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onRefresh() {
        try {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;
            ismonthselected = false;
            isyearselected = false;
            swipeRefreshLayout.setRefreshing(true);
            isRefresh = true;
            verifySession();
        } catch (Exception e) {
            e.printStackTrace();
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

    /*---------------
     * FUNCTIONS
     * --------------*/
    //load local data
    private void preloadLocalConsummated() {
        try {
            //preload consummated
            if (arrayList.isEmpty()) {
                if (!CommonVariables.CONSUMMATIONISFIRSTLOAD) {
                    if (ismonthselected && isyearselected) {
                        viewarchiveemptyscreen.setText("FILTER OPTIONS");
                        emptyvoucherfilter.setVisibility(View.VISIBLE);
                        emptyvoucher.setVisibility(View.GONE);
                    } else {
                        viewarchiveemptyscreen.setText("VIEW ARCHIVE");
                        emptyvoucher.setVisibility(View.VISIBLE);
                        emptyvoucherfilter.setVisibility(View.GONE);
                    }
                }
            } else {
                adapter.updateList(getAllMerchantTransaction());
                emptyvoucherfilter.setVisibility(View.GONE);
                emptyvoucher.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //create session
    private void verifySession() {
        try {
            int status = CommonFunctions.getConnectivityStatus(getViewContext());
            if (status == 0) { //no connection

                showNoInternetConnection();
                enableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                mLoaderTimer.cancel();
                mLoaderTimer.start();
                ((BaseActivity) getActivity()).setupOverlay();
                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));

                mTvFetching.setText("Fetching consummations");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                //call AsynTask to perform network operation on separate thread
                // new HttpAsyncTask().execute(CommonVariables.GETCONSUMMATIONTRANSACTION);

                //with security
                getConsummation();
            }
        } catch (Exception e) {
            CommonFunctions.hideDialog();
            //mLlLoader.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ConsummatedDetails.class);
        intent.putExtra("TRANSACTION_OBJECT", arrayList.get(position));
        startActivity(intent);

    }

    //send request
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
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("year", year);
                jsonObject.accumulate("month", month);

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

            mLlLoader.setVisibility(View.GONE);
            try {
                swipeRefreshLayout.setRefreshing(false);
                if (result == null) {
                    showNoInternetConnection();
                    enableRefresh();
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    showNoInternetConnection();
                    enableRefresh();
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    showNoInternetConnection();
                    enableRefresh();
                    showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("003")) {
                    showNoInternetConnection();
                    enableRefresh();
                    showToast("Invalid Authentication.", GlobalToastEnum.NOTICE);
                } else if (result.equals("error")) {
                    showNoInternetConnection();
                    enableRefresh();
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showNoInternetConnection();
                    enableRefresh();
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    processList(result);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //process result
    private void processList(String result) {

        try {
            //hide no internet indicator
            hideNoInternetConnection();

            //clear  list first
            arrayList.clear();
            db.deleteConsummation(db);
            JSONArray jsonArr = new JSONArray(result);

            if (jsonArr.length() > 0) {
                emptyvoucher.setVisibility(View.GONE);
                emptyvoucherfilter.setVisibility(View.GONE);
                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String transactionno = jsonObj.getString("TransactionNo");
                    String merchantid = jsonObj.getString("MerchantID");
                    String merchantname = jsonObj.getString("MerchantName");
                    String merchantbranch = jsonObj.getString("MerchantBranchID");
                    String productid = jsonObj.getString("ProductID");
                    String voucherserial = jsonObj.getString("VoucherSeriesNo");
                    String vouchercode = jsonObj.getString("VoucherCode");
                    String voucherdenoms = jsonObj.getString("VoucherDenom");
                    String amountconsumed = jsonObj.getString("AmountConsumed");
                    String merchantservicecharge = jsonObj.getString("MerchantServiceCharge");
                    String datecompleted = jsonObj.getString("DateTimeCompleted");
                    String merchantReferenceCode = jsonObj.getString("MerchantTransactionNo");
                    String MerchantBranchName = jsonObj.getString("MerchantBranchName");
                    String Extra3 = jsonObj.getString("Extra3");
                    String MerchantLogo = jsonObj.getString("MerchantLogo");
                    String status = jsonObj.getString("Status");


                    Logger.debug("okhttp", "Status " + status);
                    Logger.debug("okhttp", "JSON " + jsonObj.toString());

                    db.insertConsummationTransaction(db, transactionno, merchantid, merchantname, merchantbranch, productid, voucherserial, vouchercode, voucherdenoms, amountconsumed, merchantservicecharge, datecompleted, merchantReferenceCode, MerchantBranchName, Extra3, MerchantLogo, status);
                }
                CommonVariables.CONSUMMATIONISFIRSTLOAD = false;

                if (CommonVariables.CONSUMMATIONISFIRSTLOAD) {
                    CommonVariables.CONSUMMATIONISFIRSTLOAD = false;
                    adapter.updateList(getAllMerchantTransaction());
                } else {
                    adapter.updateList(getAllMerchantTransaction());
                }

                if (isRefresh) {
                    isRefresh = false;
                    adapter.updateList(getAllMerchantTransaction());
                } else {
                    adapter.updateList(getAllMerchantTransaction());
                }

            } else {

                if (ismonthselected && isyearselected) {
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

    //disable refresh button on empty screen
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //enable refresh button
    private void enableRefresh() {
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
            Cursor c = db.getMerchantTransactions(db);
            if (c.getCount() <= 0) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //hide no internet connection indicator
    private void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            enableRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayAdapter<String> monthadapter;
            ArrayList<String> spinmonthlist = new ArrayList<String>();
            spinmonthlist = monthlist();
            monthadapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
            monthadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType.setAdapter(monthadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //create spinner for year list
    private void createYearSpinnerAdapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            spinType1.setAdapter(yearadapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //make the number month to month name
    private ArrayList<String> monthlist() {


        int[] months = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ArrayList<String> xVals = new ArrayList<>();
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

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            xVals.add(Integer.toString(i));
        }
        return xVals;
    }


    private ArrayList<Transaction> getAllMerchantTransaction() {
        ArrayList<Transaction> arrList = new ArrayList<>();
        try {
            Cursor c = db.getMerchantTransactions(db);
            while (c.moveToNext()) {
                String merchantreferencecode = c.getString(c.getColumnIndex("merchantreferencecode"));
                String datecompleted = c.getString(c.getColumnIndex("datecompleted"));
                String totalamountconsume = c.getString(c.getColumnIndex("totalamountconsume"));
                String merchantname = c.getString(c.getColumnIndex("merchantname"));
                String transactionno = c.getString(c.getColumnIndex("transactionno"));
                String MerchantBranchName = c.getString(c.getColumnIndex("MerchantBranchName"));
                String Extra3 = c.getString(c.getColumnIndex("Extra3"));
                String MerchantLogo = c.getString(c.getColumnIndex("MerchantLogo"));
                String MerchantID = c.getString(c.getColumnIndex("merchantid"));
                String status = c.getString(c.getColumnIndex("Status"));

                DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
                totalamountconsume = formatter.format(Double.parseDouble(totalamountconsume));

                arrList.add(new Transaction(merchantreferencecode, datecompleted, totalamountconsume, merchantname, transactionno, MerchantBranchName, Extra3, MerchantLogo, MerchantID, status));
            }
            arrayList = arrList;
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrList;
    }


    /*
    * SECURITY UPDATE
    * AS OF
    * OCTOBER 2019
    * */

    private void getConsummation(){

        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            try{
                //PARAMETERS
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(year));
                parameters.put("month",String.valueOf(month));
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(mcontext,parameters, sessionid);

                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);

                index = CommonFunctions.parseJSON(jsonString, "index");
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                parameters.put("index", index);

                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                //AUTHENTICATIONID
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid +sessionid+ "getConsummationV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                //getting callback from server
                getConsummationObject(getConsummationCallback);

            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            showNoInternetConnection();

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private void getConsummationObject (Callback<GenericResponse> getConsummationObjectCallback){
        Call<GenericResponse> getConsummationCallback = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getConsummation(authenticationid,
                        sessionid,
                        param);
        getConsummationCallback.enqueue(getConsummationObjectCallback);
    }

    private final Callback<GenericResponse> getConsummationCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errorBody = response.errorBody();

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            if(errorBody == null){

                String message = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                switch (response.body().getStatus()){
                    case "000":
                        String data = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());

                        if(data.equalsIgnoreCase("error") || message.equalsIgnoreCase("error")){
                            showErrorToast();
                        }else{
                            processList(data);
                        }

                        break;
                    default:
                        if(message.equalsIgnoreCase("error")){
                            showErrorToast("Something went wrong. Please try again.");
                        }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        }
                        else{
                            showErrorToast(message);
                        }
                        break;
                }

            }else{
                assert response.body() != null;
                showErrorToast(response.body().getMessage());
            }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {

            t.printStackTrace();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        }
    };

}
