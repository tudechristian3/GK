package com.goodkredit.myapplication.fragments.billspayment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.account.WelcomePageActivity;
import com.goodkredit.myapplication.activities.billspayment.BillsPaymentBillerDetailsActivity;
import com.goodkredit.myapplication.adapter.billspayment.BillsPaymentBillerAdapter;
import com.goodkredit.myapplication.adapter.billspayment.BillsPaymentBillerItem;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BillsPaymentBorrowerBillerList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    //Declaration
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;
    ListView listView;

    String sessionid = "";
    String imei = "";
    String borrowerid = "";
    String userid = "";
    public static Boolean isFirstload = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private BillsPaymentBillerAdapter mlistAdapter;
    private ArrayList<BillsPaymentBillerItem> mlistData;
    private BillsPaymentBillerItem itemToDetails = null;

    private RelativeLayout emptyvoucher;
    private ImageView refresh;
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;
    private ImageView refreshdisabled;
    private ImageView refreshdisabled1;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;
    private EditText search;

    private CountDownTimer mLoaderTimer;
    private ExpandableListView listview;
    ExpandableListView elv;

    //GKSERVICE (ServiceCode)
    private String servicecode;

    //NEW VARIABLES FOR SECURITY
    private String param;
    private String keyEncryption;
    private String authenticationid;
    private String index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_bills_payment_borrower_biller_list, null);

        try {
            //initialize elements
            isFirstload = true;
            db = new DatabaseHandler(getActivity());
            listView = rootView.findViewById(R.id.billerborrowerlistlist);
            search = rootView.findViewById(R.id.edtSearch);

            //SERVICE CODE
            servicecode = getArguments().getString("ServiceCode");

            //refresh
            swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(this);
            emptyvoucher = rootView.findViewById(R.id.emptyvoucher);
            refresh = rootView.findViewById(R.id.refresh);
            nointernetconnection = rootView.findViewById(R.id.nointernetconnection);
            refreshnointernet = rootView.findViewById(R.id.refreshnointernet);
            refreshdisabled = rootView.findViewById(R.id.refreshdisabled);
            refreshdisabled1 = rootView.findViewById(R.id.refreshdisabled1);

            //array data
            itemToDetails = new BillsPaymentBillerItem();
            mlistData = new ArrayList<>();
            mlistAdapter = new BillsPaymentBillerAdapter(getActivity(), R.layout.fragment_bills_payment_borrowerbiller_item, getAllBillers(""));
            listView.setAdapter(mlistAdapter);


            //loader
            mLlLoader = rootView.findViewById(R.id.loaderLayout);
            mTvFetching = rootView.findViewById(R.id.fetching);
            mTvWait = rootView.findViewById(R.id.wait);
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

            sessionid = PreferenceUtils.getSessionID(getViewContext());
            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));

                } while (cursor.moveToNext());
            }
            cursor.close();


            /********************
             * TIRGGERS
             * ******************/

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {
                    if (count == 0) {
                        populateBorrowerBillers();
                    } else {
                        showSearchBiller();
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    showSearchBiller();
                }
            });


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    String billcode = mlistData.get(position).getBiller();
                    String spbillcode = ((TextView) view.findViewById(R.id.billercode)).getText().toString();
                    String billname = ((TextView) view.findViewById(R.id.biller)).getText().toString();
                    String account = ((TextView) view.findViewById(R.id.accountno)).getText().toString();

                    if (db.validateBiller(db, billcode)) {
                        Intent intent = new Intent(getActivity(), BillsPaymentBillerDetailsActivity.class);
                        intent.putExtra("BILLCODE", billcode);
                        intent.putExtra("SPBILLCODE", spbillcode);
                        intent.putExtra("SPBILLERACCOUNTNO", account);
                        intent.putExtra("BILLNAME", billname);
                        intent.putExtra("ServiceCode", servicecode);
                        intent.putExtra("FROM", "BORROWERBILLERS");
                        startActivity(intent);
                    } else {
                        showAlertDialog(spbillcode, billname);
                    }

                }

            });

            //REFRESH
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disableRefresh();
                    verifySession("");
                }
            });

            //refresh in no internet connection indicator
            refreshnointernet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    verifySession("");
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }

        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { //this is to know what tab is open
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {
                if (getView() != null) {
                    if (search.getText().length() > 0) {
                        showSearchBiller();
                    } else {
                        populateBorrowerBillers();
                    }

                    if (isFirstload == true) {
                        verifySession("");
                        isFirstload = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        try {
            swipeRefreshLayout.setRefreshing(true);
            verifySession("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**********************
     * FUNCTIONS
     ********************/

    private void populateBorrowerBillers() {
        mlistData.clear();
        try {
            if (!getAllBillers("").isEmpty()) {
                emptyvoucher.setVisibility(View.GONE);
                mlistAdapter.update(getAllBillers(""));
            } else {

                emptyvoucher.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSearchBiller() {
        try {

            if (search.getText().length() > 0) {
                mlistData.clear();
                mlistAdapter.update(getAllBillers(search.getText().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void verifySession(final String flag) {
//        try {
//            int status = CommonFunctions.getConnectivityStatus(getActivity());
//            if (status == 0) { //no connection
//                showNoInternetConnection();
//                inableRefresh();
//                swipeRefreshLayout.setRefreshing(false);
//                showToast("No internet connection.", GlobalToastEnum.NOTICE);
//            } else {
//
//                mLoaderTimer.cancel();
//                mLoaderTimer.start();
//
//                mTvFetching.setText("Fetching billers.");
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
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("001")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("002")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("Invalid Borrower. Please check..", GlobalToastEnum.NOTICE);
//                        } else if (data.equals("error")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("Invalid Borrower. Please check..", GlobalToastEnum.NOTICE);
//                        } else if (data.contains("<!DOCTYPE html>")) {
//                            showNoInternetConnection();
//                            inableRefresh();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                        } else {
//                            sessionid = data;
//
//                            if(flag.equals("DELETEBORROWERBILLER")){
//
//                                new HttpAsyncTask1().execute(CommonVariables.DELETEBORROWEBILLER);
//                            }else{
//                                //call AsynTask to perform network operation on separate thread
//                                new HttpAsyncTask().execute(CommonVariables.GETBORROWERBILLERLIST);
//                            }
//                        }
//                    }
//
//                });
//                newsession.execute(CommonVariables.CREATESESSION, imei, userid);
//            }
//        } catch (Exception e) {
//            Logger.debug("ERROR",e.toString());
//        }
//    }

    private void verifySession(final String flag) {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching billers.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            if (flag.equals("DELETEBORROWERBILLER")) {
                new HttpAsyncTask1().execute(CommonVariables.DELETEBORROWEBILLER);
            } else {
                //new HttpAsyncTask().execute(CommonVariables.GETBORROWERBILLERLIST);
                getBorrowerBillerlist();
            }
        } else {
            showNoInternetConnection();
            inableRefresh();
            swipeRefreshLayout.setRefreshing(false);
            showNoInternetToast();
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
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {

                mLlLoader.setVisibility(View.GONE);
                hideNoInternetConnection();
                swipeRefreshLayout.setRefreshing(false);

                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");

                if (status.equals("000")) {
                    JSONArray jsonArr = new JSONArray(data);
                    db.deleteBillers(db, "TRUE");
                    if (jsonArr.length() > 0) {
                        emptyvoucher.setVisibility(View.GONE);
                    }
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject obj = jsonArr.getJSONObject(i);

                        String serviceprovider = obj.getString("ServiceProviderBillerCode");
                        String billercode = obj.getString("BillerCode");
                        String billername = obj.getString("BillerName");
                        String category = obj.getString("BillerCategory");
                        String billerinfo = obj.getString("BillerInfo");
                        String logourl = obj.getString("BillerLogoURL");
                        String accountno = obj.getString("AccountReference");
                        String otherinfo = obj.getString("XMLDetails");
                        String billerdesc = obj.getString("BillerDescription");
                        String servicecode = obj.getString("ServiceCode");
                        String customerscclass = obj.getString("CustomerSCClass");
                        String notes = obj.getString("Notes1");


                        db.insertBiller(db, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "TRUE", accountno, otherinfo, notes, ".", ".");

                    }
                } else {
                    showToast("" + message, GlobalToastEnum.NOTICE);
                }

                if (search.getText().length() > 0) {
                    mlistAdapter.update(getAllBillers(search.getText().toString()));
                } else {
                    mlistAdapter.update(getAllBillers(""));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private ArrayList<BillsPaymentBillerItem> getAllBillers(String searchval) {
        ArrayList<BillsPaymentBillerItem> arrayList = new ArrayList<>();
        try {

            Cursor c = db.getBillers(db, "TRUE", searchval);


            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    String billercode = c.getString(c.getColumnIndex("BillerCode"));
                    String spbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                    String billername = c.getString(c.getColumnIndex("BillerName"));
                    String category = c.getString(c.getColumnIndex("Category"));
                    String logourl = c.getString(c.getColumnIndex("LogoURL"));
                    String accountno = c.getString(c.getColumnIndex("AccountNo"));

                    arrayList.add(new BillsPaymentBillerItem(logourl, billercode, billername, category, accountno, spbillercode));

                } while (c.moveToNext());
            }
            c.close();
            mlistData = arrayList;


        } catch (Exception e) {
            e.printStackTrace();

        }
        return arrayList;
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
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
                jsonObject.accumulate("authcode", authcode);
                jsonObject.accumulate("borrowerid", borrowerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("sessionid", sessionid);
                //convert JSONObject to JSON to String
                json = jsonObject.toString();


            } catch (Exception e) {
                e.printStackTrace();
                json = null;
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // 2. onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            try {

                mLlLoader.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);

                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");

                if (status.equals("000")) {
                    JSONArray jsonArr = new JSONArray(data);
                    db.deleteBillers(db, "TRUE");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject obj = jsonArr.getJSONObject(i);

                        String serviceprovider = obj.getString("ServiceProviderBillerCode");
                        String billercode = obj.getString("BillerCode");
                        String billername = obj.getString("BillerName");
                        String category = obj.getString("BillerCategory");
                        String billerinfo = obj.getString("BillerInfo");
                        String logourl = obj.getString("BillerLogoURL");
                        String accountno = obj.getString("AccountReference");
                        String otherinfo = obj.getString("XMLDetails");
                        String billerdesc = obj.getString("BillerDescription");
                        String servicecode = obj.getString("ServiceCode");
                        String customerscclass = obj.getString("CustomerSCClass");
                        String notes = obj.getString("Notes1");

                        db.insertBiller(db, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "TRUE", accountno, otherinfo, notes, ".", ".");

                    }
                } else {
                    showToast("" + message, GlobalToastEnum.NOTICE);
                }

                if (search.getText().length() > 0) {
                    mlistAdapter.update(getAllBillers(search.getText().toString()));
                } else {
                    mlistAdapter.update(getAllBillers(""));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

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
            e.printStackTrace();
        }

    }

    private void showAlertDialog(final String spbillcode, final String billname) {
        try {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("VALIDATION");
            alertDialog.setMessage("Sorry this record is no longer supported due to some parameter changes. To continue tap 'OK' ");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), BillsPaymentBillerDetailsActivity.class);
                    intent.putExtra("BILLCODE", spbillcode);
                    intent.putExtra("SPBILLCODE", spbillcode);
                    intent.putExtra("BILLNAME", billname);
                    intent.putExtra("FROM", "BILLERS");
                    startActivity(intent);

                    //background delete of borrower biller
                    verifySession("DELETEBORROWERBILLER");

                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    private void getBorrowerBillerlist() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);

            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");
            parameters.put("index", index);

            final String paramJson = new Gson().toJson(parameters, Map.class);

            authenticationid = CommonFunctions.parseJSON(String.valueOf(jsonString), "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getBorrowerBillerList");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            getBorrowerBillerListObject(getBillersListSession);

        } else {
            showNoInternetToast();
        }
    }

    private void getBorrowerBillerListObject(Callback<GenericResponse> borrowerBillers) {
        Call<GenericResponse> borrowerBillerList = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getBorrowerBillerList(authenticationid,
                        sessionid,
                        param);

        borrowerBillerList.enqueue(borrowerBillers);
    }

    private final Callback<GenericResponse> getBillersListSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();

            mLlLoader.setVisibility(View.GONE);
            hideNoInternetConnection();
            swipeRefreshLayout.setRefreshing(false);

            if (errorBody == null) {
                String decryptedmessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                switch (response.body().getStatus()) {
                    case "000":
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());
                        if (decryptedmessage.equals("error") || decryptedData.equals("error")) {
                            showErrorGlobalDialogs();
                        } else {
                            try {
                                JSONArray jsonArr = new JSONArray(decryptedData);
                                db.deleteBillers(db, "TRUE");
                                if (jsonArr.length() > 0) {
                                    emptyvoucher.setVisibility(View.GONE);
                                }
                                for (int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject obj = jsonArr.getJSONObject(i);
                                    String serviceprovider = obj.getString("ServiceProviderBillerCode");
                                    String billercode = obj.getString("BillerCode");
                                    String billername = obj.getString("BillerName");
                                    String category = obj.getString("BillerCategory");
                                    String billerinfo = obj.getString("BillerInfo");
                                    String logourl = obj.getString("BillerLogoURL");
                                    String accountno = obj.getString("AccountReference");
                                    String otherinfo = obj.getString("XMLDetails");
                                    String billerdesc = obj.getString("BillerDescription");
                                    String servicecode = obj.getString("ServiceCode");
                                    String customerscclass = obj.getString("CustomerSCClass");
                                    String notes = obj.getString("Notes1");


                                    db.insertBiller(db, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "TRUE", accountno, otherinfo, notes, ".", ".");

                                }

                                if (search.getText().length() > 0) {
                                    mlistAdapter.update(getAllBillers(search.getText().toString()));
                                } else {
                                    mlistAdapter.update(getAllBillers(""));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    default:
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast("Something went wrong. Please try again.");
                        }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorToast(decryptedmessage);
                        }
                        break;
                }
            } else {
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            hideNoInternetConnection();
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast("Oops! We failed to connect to the service. Please check your internet connection and try again.");
        }
    };


}

