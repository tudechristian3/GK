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
import com.goodkredit.myapplication.adapter.TransferredAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.model.v2Models.TransactionModel;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

/*
* ALGO:
* 1. Check network status, internet connection
* 2. Check session
* 3. Get voucher send http request
* 4. Process return request
* 5. localize voucher
* */
public class Transferred extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;

    private TransferredAdapter adapter;
    private StickyListHeadersListView listView;
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
    ArrayList<Transaction> arrayList;
    private SwipeRefreshLayout swipeRefreshLayout;

    static Calendar c;
    static int year;
    static int month;
    static int registrationyear;
    static int registrationmonth;
    static int currentyear = 0;//make this not changeable for (filter condition)
    static int currentmonth = 0; //make this not changeable for (filter condition)

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
    private boolean isViewShown = false;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    View v;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_transfered, null);

        try {
            //Set context
            mcontext = getActivity();
            db = new DatabaseHandler(getActivity());
            //initialize date
            c = Calendar.getInstance();
            currentyear = c.get(Calendar.YEAR);
            currentmonth = c.get(Calendar.MONTH) + 1;
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1;

            //UNIFIED SESSION
            sessionid = PreferenceUtils.getSessionID(getViewContext());

            //initialize button list footer
            btnAddMore = new Button(getActivity());
            btnAddMore.setBackgroundResource(R.color.colorwhite);
            btnAddMore.setTextColor(getResources().getColor(R.color.buttons));
            btnAddMore.setTypeface(Typeface.DEFAULT_BOLD);

            //initialize element for view archive in empty screen
            viewarchiveemptyscreen = v.findViewById(R.id.viewarchiveemptyscreen);
            filteroption = v.findViewById(R.id.filteroption);
            emptyvoucherfilter = v.findViewById(R.id.emptyvoucherfilter);

            //initialize element in fragment
            swipeRefreshLayout = v.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(this);
            emptyvoucher = v.findViewById(R.id.emptyvoucher);
            refresh = v.findViewById(R.id.refresh);
            nointernetconnection = v.findViewById(R.id.nointernetconnection);
            refreshnointernet = v.findViewById(R.id.refreshnointernet);
            refreshdisabled = v.findViewById(R.id.refreshdisabled);
            refreshdisabled1 = v.findViewById(R.id.refreshdisabled1);

            mLlLoader = v.findViewById(R.id.loaderLayout);
            mTvFetching = v.findViewById(R.id.fetching);
            mTvWait = v.findViewById(R.id.wait);


            // Hashmap for ListView
            arrayList = new ArrayList<>();
            listView = v.findViewById(R.id.transferredList);
            listView.setOnItemClickListener(this);
            listView.addFooterView(btnAddMore, null, true);

            final ImageView backgroundOne = v.findViewById(R.id.background_one);
            final ImageView backgroundTwo = v.findViewById(R.id.background_two);

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


            /*****************
             * TIGGERS
             * ****************/

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
                    CommonVariables.TRANSFERREDFIRSTLOAD = "true";
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

            // Scroll to view filter option or view archive
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
                            listView.addFooterView(btnAddMore, null, true);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUserTrasfered) { //this is to know what tab is open
        super.setUserVisibleHint(isVisibleToUserTrasfered);
        if (isVisibleToUserTrasfered) {
            Logger.debug("OPENTAB", "TANSFERRED");
            //initialized local database
            if (CommonVariables.TRANSFERREDFIRSTLOAD.equals("true")) {
                verifySession();
            }
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

        } catch (Exception ignored) {}

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

                preLoadLocalData();

            }
            cursor.close();

            ((BaseActivity) getActivity()).setupOverlay();
            ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // db.close();
        }

    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }


    /****************
     * FUNCTIONS
     *************/
    //preload data from local database
    private void preLoadLocalData() {

        try {
            //preload consummated
            arrayList.clear();
            Cursor c = db.getTransferredTransaction(db);
            if (c.getCount() > 0) {
                c.moveToFirst();
                emptyvoucher.setVisibility(View.GONE);
                emptyvoucherfilter.setVisibility(View.GONE);
                do {
                    String transfertype = c.getString(c.getColumnIndex("transfertype"));
                    String datetimein = c.getString(c.getColumnIndex("datetimein"));
                    String denoms = c.getString(c.getColumnIndex("denoms"));
                    String transactionno = c.getString(c.getColumnIndex("transactionno"));
                    String sourceborrowername = c.getString(c.getColumnIndex("sourceborrowername"));
                    String recipientborrowername = c.getString(c.getColumnIndex("recipientborrowername"));
                    String voucherCode = c.getString(c.getColumnIndex("vouchercode"));
                    String Extra3 = c.getString(c.getColumnIndex("Extra3"));
                    String mobileNum = c.getString(c.getColumnIndex("recipientmobileno"));
                    String email = c.getString(c.getColumnIndex("recipientemail"));
                    String status = c.getString(c.getColumnIndex("Status"));

                    Logger.debug("MARY ANN","PRELOADSTATUS"+status);

                    DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
                    denoms = formatter.format(Double.parseDouble(denoms));

                    arrayList.add(new Transaction(denoms, datetimein, sourceborrowername, recipientborrowername, transactionno, transfertype, voucherCode, "", Extra3, mobileNum, email,status));

                } while (c.moveToNext());

                //update data in list
                updateListAdapter();


            } else {


                if (CommonVariables.TRANSFERREDFIRSTLOAD.equals("false")) {
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
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    //2. create session
    private void verifySession() {
        try {
            // cf.showDialog(mcontext, "", "Fetching Transferred Transaction. Please wait ...", false);
            int status = CommonFunctions.getConnectivityStatus(getActivity());
            if (status == 0) { //no connection
                showNoInternetConnection();
                inableRefresh();
                swipeRefreshLayout.setRefreshing(false);

                CommonFunctions.hideDialog();
                showToast("No internet connection.", GlobalToastEnum.NOTICE);
            } else {
                mLoaderTimer.cancel();
                mLoaderTimer.start();
                mTvFetching.setText("Fetching transferred transaction.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);

                ((BaseActivity) getActivity()).setupOverlay();
                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));

                //call AsynTask to perform network operation on separate thread
                //new HttpAsyncTask().execute(cv.GETTRANSFEREDTRANSACTION);
                getTransferedVoucherV2();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), TransferredDetails.class);
        intent.putExtra("TRANSFERRED_OBJECT", arrayList.get(position));
        startActivity(intent);
    }

    //3.
    //Downloading data asynchronously, send request
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

        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            if (result.equals("001")) {
                showNoInternetConnection();
                inableRefresh();

                showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
            } else if (result.equals("002")) {
                showNoInternetConnection();
                inableRefresh();

                showToast("Invalid Session.", GlobalToastEnum.NOTICE);
            } else if (result.equals("003")) {
                showNoInternetConnection();
                inableRefresh();

                showToast("Invalid Guarantor ID.", GlobalToastEnum.NOTICE);
            } else if (result.equals("error")) {
                showNoInternetConnection();
                inableRefresh();

                showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
            } else if (result.contains("<!DOCTYPE html>")) {
                showNoInternetConnection();
                inableRefresh();
                showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
            } else {
                processResult(result);
            }
        }
    }

    //5. process request result
    private void processResult(String result) {
        try {

            Logger.debug("okhttp","DATA : "+result);

            hideNoInternetConnection();

            CommonVariables.TRANSFERREDFIRSTLOAD = "false"; //set to false so that it wont load again

            //clear  list first
            arrayList.clear();
            db.deleteTransferredTransaction(db); //delete local data before inserting


            ArrayList<TransactionModel> transactions = new ArrayList<>();
            transactions = new Gson().fromJson(result, new TypeToken<ArrayList<TransactionModel>>(){}.getType());

            if(transactions.size() > 0){

                emptyvoucher.setVisibility(View.GONE);
                emptyvoucherfilter.setVisibility(View.GONE);

                for(TransactionModel transaction : transactions){

                        String transactionno = transaction.getTransferTxnNo();
                        String transfertype = transaction.getTransferType();
                        String sourceborrowerid = transaction.getSourceBorrowerID();
                        String sourceborrowername = transaction.getSourceBorrowerName();
                        String recipientborrowerid = transaction.getRecipientBorrowerID();
                        String recipientborrowername = transaction.getRecipientBorrowerName();
                        String recipientmobile = transaction.getRecipientMobileNo();
                        String recipientemail = transaction.getRecipientEmailAddress();
                        String serialno = transaction.getVoucherSerialNo();
                        String vouchercode = transaction.getVoucherCode();
                        String denoms = transaction.getVoucherDenoms();
                        String date = transaction.getDateTimeCompleted();
                        String Extra3 = transaction.getExtra3();
                        String status = transaction.getStatus();

                        //insert local
                        db.insertTransferredTransaction(db, transactionno, transfertype, sourceborrowerid, sourceborrowername, recipientborrowerid, recipientborrowername, recipientmobile, recipientemail, serialno, vouchercode, denoms, date, Extra3);

                        DecimalFormat formatter = new DecimalFormat("#,###,##0.00");

                        arrayList.add(new Transaction(formatter.format(Double.parseDouble(denoms)), date, sourceborrowername, recipientborrowername, transactionno, transfertype, vouchercode, "", Extra3, recipientmobile, recipientemail,status));
                    }
                    updateListAdapter();
                }else {
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

        } catch (Exception ignored) {

        }
    }

    //update data in the list
    private void updateListAdapter() {

        try {
            adapter = new TransferredAdapter(getContext(), arrayList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //disable refresh from empty screen
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }

    //enable refresh button from empty screen
    public void inableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

    }

    //show no internet connection page
    public void showNoInternetConnection() {
        try {
            Cursor c = db.getNotifications(db);
            if (c.getCount() <= 0) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // db.close();
        }
    }

    //hide no internet connection page
    public void hideNoInternetConnection() {
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
     * AS OF
     * OCTOBER 2019
     * */

    private void getTransferedVoucherV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("year", String.valueOf(year));
                parameters.put("month", String.valueOf(month));
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getTransferedVoucherV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getTransferredVouchersV2Object(getTransferredVouchersV2Session);

            } else {
                showNoInternetToast();
                CommonFunctions.hideDialog();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }

        } catch (Exception e) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            e.printStackTrace();
            showErrorToast();
        }

    }

    private void getTransferredVouchersV2Object(Callback<GenericResponse> getReceivedVouchersCallback) {
        Call<GenericResponse> getreceivedvouchers = RetrofitBuilder.getTransactionsV2APIService(getViewContext())
                .getTransferedVoucher(authenticationid,
                        sessionid,
                        param);
        getreceivedvouchers.enqueue(getReceivedVouchersCallback);
    }

    private final Callback<GenericResponse> getTransferredVouchersV2Session = new Callback<GenericResponse>() {

        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);

            ResponseBody errorBody = response.errorBody();

            inableRefresh();

            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());

                switch (response.body().getStatus()) {

                    case "000":

                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedData.equalsIgnoreCase("error") || decryptedMessage.equalsIgnoreCase("error")) {
                            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                        } else {
                            processResult(decryptedData);
                        }
                        break;

                    default:

                        if (decryptedMessage.equalsIgnoreCase("error")) {
                            showErrorToast();
                        }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorToast(decryptedMessage);
                        }
                        break;
                }


            } else {
                CommonFunctions.hideDialog();
                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            inableRefresh();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

}
