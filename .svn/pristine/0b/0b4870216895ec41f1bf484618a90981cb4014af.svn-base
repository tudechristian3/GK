package com.goodkredit.myapplication.fragments.transactions;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import com.goodkredit.myapplication.activities.transactions.PurchasesTransactionDetailsActivity;
import com.goodkredit.myapplication.adapter.transactions.PurchasesTransactionsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GetPrepaidVoucherResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by Ban_Lenovo on 5/25/2017.
 */

public class PurchasesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //Declaration
    private Gson gson;
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;

    private StickyListHeadersListView listView;
    private PurchasesTransactionsAdapter mAdapter;

    private SwipeRefreshLayout swipeLayout;

    private RelativeLayout emptyvoucher;

    private Dialog dialog;

    private ImageView refresh;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private TextView viewarchiveemptyscreen;
    private RelativeLayout emptyvoucherfilter;

    private String borrowerid = "";
    private String userid = "";
    private String imei = "";
    private String sessionidval = "";
    private String dateregistered = "";

    private Spinner spinType;
    private Spinner spinType1;
    private TextView popfilter;
    private TextView popcancel;
    private TextView filteroption;

    //dialog spin selected indicator
    private Boolean ismonthselected = false;
    private Boolean isyearselected = false;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;

    private Button btnAddMore;

    private Calendar c;
    private int year;
    private int month;
    private int registrationyear;
    private int registrationmonth;
    private int currentyear = 0;//make this not changeable for (filter condition)
    private int currentmonth = 0; //make this not changeable for (filter condition)

    //UNIFIED SESSION
    private String sessionid = "";

    private RelativeLayout nointernetconnection;

    public static PurchasesFragment newInstance() {
        PurchasesFragment fragment = new PurchasesFragment();
        return fragment;
    }

    private void preloadPurchases() {
        mAdapter.clearData();
        if (db.getAllPrepaidVoucherTransactions(db).size() > 0) {
            emptyvoucher.setVisibility(View.GONE);
            emptyvoucherfilter.setVisibility(View.GONE);
            mAdapter.update(db.getAllPrepaidVoucherTransactions(db));
        } else {
           // if (cv.BORROWINGSISFIRSTLOAD.equals("false")) {

                if (ismonthselected == true && isyearselected == true) {
                    viewarchiveemptyscreen.setText("FILTER OPTIONS");
                    emptyvoucherfilter.setVisibility(View.VISIBLE);
                    emptyvoucher.setVisibility(View.GONE);
                } else {
                    viewarchiveemptyscreen.setText("VIEW ARCHIVE");
                    emptyvoucher.setVisibility(View.VISIBLE);
                    emptyvoucherfilter.setVisibility(View.GONE);
                }
           // }
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {
                preloadPurchases();
                if (cv.PURCHASES_TRANSACTIONS_ISFIRTLOAD) {
                    verifySession();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales, null);

        db = new DatabaseHandler(getContext());
        gson = new Gson();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        emptyvoucher = (RelativeLayout) view.findViewById(R.id.emptyvoucher);

        viewarchiveemptyscreen = (TextView) view.findViewById(R.id.viewarchiveemptyscreen);
        filteroption = (TextView) view.findViewById(R.id.filteroption);
        emptyvoucherfilter = (RelativeLayout) view.findViewById(R.id.emptyvoucherfilter);

        //initialize button list footer
        btnAddMore = new Button(getActivity());
        btnAddMore.setBackgroundResource(R.color.colorwhite);
        btnAddMore.setTextColor(getResources().getColor(R.color.buttons));
        btnAddMore.setTypeface(Typeface.DEFAULT_BOLD);

        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshdisabled = (ImageView) view.findViewById(R.id.refreshdisabled);
        refreshdisabled1 = (ImageView) view.findViewById(R.id.refreshdisabled1);
        refresh = (ImageView) view.findViewById(R.id.refresh);

        mAdapter = new PurchasesTransactionsAdapter(getContext(), db.getAllPrepaidVoucherTransactions(db));
        listView = (StickyListHeadersListView) view.findViewById(R.id.purchasesListView);
        listView.setAdapter(mAdapter);
        swipeLayout.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);

        final ImageView backgroundOne = (ImageView) view.findViewById(R.id.background_one);
        final ImageView backgroundTwo = (ImageView) view.findViewById(R.id.background_two);

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

        c = Calendar.getInstance();
        currentyear = c.get(Calendar.YEAR);
        currentmonth = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;

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

        dialog = new Dialog(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_filtering);
        filterwrap = (ScrollView) dialog.findViewById(R.id.filterwrap);
        optionwrap = (LinearLayout) dialog.findViewById(R.id.optionwrap);
        editsearches = (TextView) dialog.findViewById(R.id.editsearches);
        clearsearch = (TextView) dialog.findViewById(R.id.clearsearch);
        spinType = (Spinner) dialog.findViewById(R.id.month);
        spinType1 = (Spinner) dialog.findViewById(R.id.year);
        popfilter = (TextView) dialog.findViewById(R.id.filter);
        popcancel = (TextView) dialog.findViewById(R.id.cancel);

        createMonthSpinnerAddapter();
        createYearSpinnerAddapter();

        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            protected Adapter initializedAdapter = null;

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                try {
                    String monthstring = parentView.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;
                    if (month > 0) {
                        ismonthselected = true;
                    } else {
                        ismonthselected = false;
                    }
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
                    dialog.dismiss();
                } else {
                    showToast("Please select a date.", GlobalToastEnum.WARNING);
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
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub

                if (db.getAllPrepaidVoucherTransactions(db).size() > 0) {
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

        preloadPurchases();

        return view;
    }

    //create session
    private void verifySession() {

        try {
            int status = cf.getConnectivityStatus(getActivity());
            if (status == 0) { //no connection
                showNoInternetConnection();
                enableRefresh();
                swipeLayout.setRefreshing(false);
                showToast("No internet connection.", GlobalToastEnum.WARNING);
            } else {

                ((BaseActivity) getActivity()).setupOverlay();
                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));

                mLoaderTimer.cancel();
                mLoaderTimer.start();
                mTvFetching.setText("Fetching purchases.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                new HttpAsyncTask().execute(cv.GET_PURCHASES_TRANSACTIONS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRefresh() {
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        ismonthselected = false;
        isyearselected = false;
        swipeLayout.setRefreshing(true);
        verifySession();
    }

    @Override
    public void onResume() {
        ((BaseActivity) getActivity()).setupOverlay();
        ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PurchasesTransactionDetailsActivity.start(getContext(), db.getAllPrepaidVoucherTransactions(db).get(position));
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
            String authcode = cf.getSha1Hex(imei + userid + sessionid);

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                jsonObject.accumulate("year", year);
                jsonObject.accumulate("month", month);
                jsonObject.accumulate("authcode", authcode);

                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                e.printStackTrace();
            }

            return cf.POST(urls[0], json);

        }

        @Override
        protected void onPostExecute(String result) {
            mLlLoader.setVisibility(View.GONE);
            swipeLayout.setRefreshing(false);
            try {
                if (result == null) {
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

    private void processList(String result) {

        cv.PURCHASES_TRANSACTIONS_ISFIRTLOAD = false;
        hideNoInternetConnection();

        try {

            GetPrepaidVoucherResponse response = gson.fromJson(result, GetPrepaidVoucherResponse.class);

            if (response.getStatus().equals("000")) {
                db.clearPrepaidVoucherTransaction(db);
                mAdapter.clearData();
                for (PrepaidVoucherTransaction trans : response.getData()) {
                    db.insertPrepaidVoucherTransaction(db, trans);
                }
                mAdapter.update(db.getAllPrepaidVoucherTransactions(db));

                if (response.getData().size() <= 0) {
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
                showToast(response.getMessage(), GlobalToastEnum.NOTICE);
            }

            swipeLayout.setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
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

    private void hideNoInternetConnection() {
        nointernetconnection.setVisibility(View.GONE);
        enableRefresh();
    }

    private void enableRefresh() {
        refreshdisabled.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        refreshdisabled1.setVisibility(View.GONE);
        refreshnointernet.setVisibility(View.VISIBLE);
    }

    private void disableRefresh() {
        refreshdisabled.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.GONE);
        refreshdisabled1.setVisibility(View.VISIBLE);
        refreshnointernet.setVisibility(View.GONE);
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
}
