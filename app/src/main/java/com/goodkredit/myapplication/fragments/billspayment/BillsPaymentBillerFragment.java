package com.goodkredit.myapplication.fragments.billspayment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
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

public class BillsPaymentBillerFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    //Declaration
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;
    ListView listView;

    //COMMON
    String sessionid = "";
    String imei ="";
    String borrowerid ="";
    String userid ="";


    private boolean isViewShown = false;
    public static Boolean isFirstload = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    private BillsPaymentBillerAdapter mlistAdapter;
    private ArrayList<BillsPaymentBillerItem> mlistData;
    private BillsPaymentBillerItem itemToDetails = null;
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;
    private EditText search;

    private CountDownTimer mLoaderTimer;

    static RelativeLayout emptyvoucher;
    static ImageView refresh;
    static RelativeLayout nointernetconnection;
    static ImageView refreshnointernet;
    static ImageView refreshdisabled;
    static ImageView refreshdisabled1;

    //GKSERVICE (ServiceCode)
    private String servicecode;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_bills_payment_biller, null);

        try{
            //initialize elements
            isFirstload = true;
            db = new DatabaseHandler(getActivity());
            listView = rootView.findViewById(R.id.billerlist);
            search = rootView.findViewById(R.id.edtSearch);

            //SERVICE CODE
            servicecode = getArguments().getString("ServiceCode");

            //array data
            itemToDetails = new BillsPaymentBillerItem();
            mlistData = new ArrayList<>();
            mlistAdapter = new BillsPaymentBillerAdapter(getActivity(), R.layout.fragment_bills_payment_biller_item, getAllBillers(""));
            listView.setAdapter(mlistAdapter);

            //refresh
            swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(this);
            emptyvoucher = rootView.findViewById(R.id.emptyvoucher);
            refresh = rootView.findViewById(R.id.refresh);
            nointernetconnection = rootView.findViewById(R.id.nointernetconnection);
            refreshnointernet = rootView.findViewById(R.id.refreshnointernet);
            refreshdisabled = rootView.findViewById(R.id.refreshdisabled);
            refreshdisabled1 = rootView.findViewById(R.id.refreshdisabled1);


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

                if (!isViewShown) {
                    if (CommonVariables.BILLERSFIRSTLOAD) {
                        verifySession("");
                    }

                    isFirstload = false;
                }
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
                        populateBillers();
                    } else {
                        showSearchBiller();
                    }

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    showSearchBiller();
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

                    Intent intent = new Intent(getActivity(), BillsPaymentBillerDetailsActivity.class);
                    intent.putExtra("BILLCODE", billcode);
                    intent.putExtra("SPBILLCODE", spbillcode);
                    intent.putExtra("SPBILLERACCOUNTNO", "");
                    intent.putExtra("BILLNAME", billname);
                    intent.putExtra("ServiceCode", servicecode);
                    intent.putExtra("FROM","BILLERS");
                    startActivity(intent);


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
                    disableRefresh();
                    verifySession("");
                }
            });


        } catch (Exception e){
            e.printStackTrace();
        }

        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        try {
            if (isVisibleToUser) {//dosomething when the fragment is visible

                //populateBillers();
                if (search.getText().length() > 0) {
                    showSearchBiller();
                } else {
                    populateBillers();
                }
                if (getView() != null) {
                    if (isFirstload) {

                        if (CommonVariables.BILLERSFIRSTLOAD) {
                            verifySession("");
                        }
                        isViewShown = true;
                        isFirstload = false;
                    }
                }
            } else {//dosomething else.}
                isViewShown = false;
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

    private void populateBillers() {
        mlistData.clear();
        try {
            if (!getAllBillers("").isEmpty()) {
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

    private void verifySession(final String flag) {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            mLoaderTimer.cancel();
            mLoaderTimer.start();

            mTvFetching.setText("Fetching billers.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            //new HttpAsyncTask().execute(CommonVariables.GETBILLERS);
            getBillers();

        } else {
            showNoInternetConnection();
            enableRefresh();
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
                swipeRefreshLayout.setRefreshing(false);
                hideNoInternetConnection();

                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status =  parentObj.getString("status");

                if(status.equals("000")){
                    JSONArray jsonArr = new JSONArray(data);
                    db.deleteBillers(db,"FALSE");
                    if(jsonArr.length()>0){
                        emptyvoucher.setVisibility(View.GONE);
                    }else{
                        emptyvoucher.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject obj = jsonArr.getJSONObject(i);

                        String serviceprovider = obj.getString("ServiceProviderBillerCode");
                        String billercode = obj.getString("BillerCode");
                        String billername = obj.getString("BillerName");
                        String billerdesc = obj.getString("BillerDescription");
                        String category = obj.getString("Category");
                        String billerinfo = obj.getString("BillerInfo");
                        String logourl = obj.getString("BillerLogoURL");
                        String servicecode = obj.getString("ServiceCode");
                        String customerscclass= obj.getString("CustomerSCClass");
                        String notes =obj.getString("Notes1");
                        String groupcategory = obj.getString("GroupCategory");
                        String categorylogo = obj.getString("CategoryLogo");

                        db.insertBiller(db, serviceprovider, billercode, billername,billerdesc, servicecode, category, billerinfo, logourl,customerscclass,"FALSE",".",".",notes,groupcategory,categorylogo);

                    }
                }else{
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

    private ArrayList<BillsPaymentBillerItem> getAllBillers(String searchbillername) {
        ArrayList<BillsPaymentBillerItem> arrayList = new ArrayList<>();
        try {

            Cursor c = db.getBillers(db,"FALSE",searchbillername);
            if (c.getCount() > 0) {
                c.moveToFirst();

                do {
                    String billercode = c.getString(c.getColumnIndex("BillerCode"));
                    String serviceprvbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                    String billername = c.getString(c.getColumnIndex("BillerName"));
                    String category = c.getString(c.getColumnIndex("Category"));
                    String logourl = c.getString(c.getColumnIndex("LogoURL"));
                    arrayList.add(new BillsPaymentBillerItem(logourl,billercode, billername,category,"",serviceprvbillercode));


                } while (c.moveToNext());
            }
            c.close();
            mlistData = arrayList;

        } catch (Exception e) {
            e.printStackTrace();

        }
        return arrayList;
    }


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

    private void showNoInternetConnection() {
        try {
            Cursor c = db.getUnpaidbills(db);
            if (c.getCount() <= 0) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //hide no internet connection
    private void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            enableRefresh();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getBillers(){
        try{

            if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

                LinkedHashMap<String ,String> parameters = new LinkedHashMap<>();
                parameters.put("imei",imei);
                parameters.put("userid",userid);
                parameters.put("borrowerid",borrowerid);
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getBillersV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getBillersObject(getBillersSession);

            }else{
                showNoInternetConnection();
                enableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                showNoInternetToast();
            }

        }catch (Exception e){
            e.printStackTrace();
            enableRefresh();
            swipeRefreshLayout.setRefreshing(false);
            showErrorToast();
        }
    }

    private void getBillersObject(Callback<GenericResponse> getBillersCallback) {
        Call<GenericResponse> getBillers = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getBillers(
                        authenticationid, sessionid, param
                );
        getBillers.enqueue(getBillersCallback);
    }
    private final Callback<GenericResponse> getBillersSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            mLlLoader.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            hideNoInternetConnection();

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else {
                        try{
                            JSONArray jsonArr = new JSONArray(decryptedData);
                            db.deleteBillers(db,"FALSE");
                            if(jsonArr.length()>0){
                                emptyvoucher.setVisibility(View.GONE);
                            }else{
                                emptyvoucher.setVisibility(View.VISIBLE);
                            }
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject obj = jsonArr.getJSONObject(i);

                                String serviceprovider = obj.getString("ServiceProviderBillerCode");
                                String billercode = obj.getString("BillerCode");
                                String billername = obj.getString("BillerName");
                                String billerdesc = obj.getString("BillerDescription");
                                String category = obj.getString("Category");
                                String billerinfo = obj.getString("BillerInfo");
                                String logourl = obj.getString("BillerLogoURL");
                                String servicecode = obj.getString("ServiceCode");
                                String customerscclass= obj.getString("CustomerSCClass");
                                String notes =obj.getString("Notes1");
                                String groupcategory = obj.getString("GroupCategory");
                                String categorylogo = obj.getString("CategoryLogo");

                                db.insertBiller(db, serviceprovider, billercode, billername,billerdesc, servicecode, category, billerinfo, logourl,customerscclass,"FALSE",".",".",notes,groupcategory,categorylogo);
                            }

                            if (search.getText().length() > 0) {
                                mlistAdapter.update(getAllBillers(search.getText().toString()));
                            } else {
                                mlistAdapter.update(getAllBillers(""));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    }else if(response.body().getStatus().equals("003")){
                        showAutoLogoutDialog(decryptedMessage);
                    }else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
                enableRefresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            enableRefresh();
            swipeRefreshLayout.setRefreshing(false);
        }
    };

}
