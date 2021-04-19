package com.goodkredit.myapplication.activities.notification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.gkstore.GKStoreDetailsActivity;
import com.goodkredit.myapplication.adapter.NotificationItem;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKNotificationsCustomValues;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.squareup.picasso.Picasso;

public class NotificationDetailsActivity extends BaseActivity implements View.OnClickListener {

    Context mcontext;
    DatabaseHandler db;
    AQuery aq;
    //CommonFunctions cf;

    TextView senderlogo;
    ImageView sendericon;
    TextView sender;
    TextView message;
    TextView date;

//    String senderval = "";
//    String messageval = "";
//    String dateval = "";
//    String senderimageval = "";
//    String firstval = "";
//    String txnnoval = "";

    private NotificationItem notif;

    //for product layout
    private LinearLayout flash_sale_layout, notificationDetailsLayout, notificationProductContainer;
    private TextView flashMessage, tailmessage;
    private Button flashGoToStoreBtn;
    private String subjectType;

    private TextView productname, productdesc, actualprice;
    private ImageView productLogo;

    GKNotificationsCustomValues customValues;

    ProgressDialog progressDialog;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    private String imei;
    private String userid ;
    private String borrowerid ;
    private String sessionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
        mcontext = this;

        //set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Initialize database
        db = new DatabaseHandler(getViewContext());

        notif = new NotificationItem();

        senderlogo = findViewById(R.id.senderlogo);
        sendericon = findViewById(R.id.sendericon);
        sender = findViewById(R.id.sender);
        message = findViewById(R.id.message);
        date = findViewById(R.id.date);
        try {

            aq = new AQuery(this);

            notif = (NotificationItem) getIntent().getSerializableExtra("NOTIFICATION_OBJECT");

//            Logger.debug("roneldayanan","DATA : "+new Gson().toJson(notif));

            productname = findViewById(R.id.notificationProductnamefull);
            productdesc = findViewById(R.id.notificationProductdescfull);
            actualprice = findViewById(R.id.notificationActualpricefull);
            productLogo = findViewById(R.id.notificationProductLogoPicfull);
            tailmessage = findViewById(R.id.notification_tv_tailmessage);


            notificationDetailsLayout = findViewById(R.id.notification_details_layout);
            notificationProductContainer = findViewById(R.id.notificationProductLogoContainerfull);


            //for flash sale layout
            flash_sale_layout = findViewById(R.id.flash_sale_layout);
            flashMessage = findViewById(R.id.notification_tv_flashSale);
            flashGoToStoreBtn = findViewById(R.id.btn_notification_gotostore);

            //listener
            flashGoToStoreBtn.setOnClickListener(this);

            subjectType = getIntent().getStringExtra("subject");
            Logger.debug("roneldayanan","Subject Type: "+ subjectType);

            if (subjectType.equals("GKSTORE PROMO") || subjectType.equals("GKCOOPSTORE")) {
//                    Logger.debug("roneldayanan","Subject Type: "+ subjectType);
                try {
                    String[] messages = notif.getMessage().split("::::");
//                        Logger.debug("roneldayanan","Length: "+messages.length);
//
//                        for(String m : messages){
//                            Logger.debug("roneldayanan","MESSAGES: "+m+" \n");
//                        }
                    if (messages.length == 1) {
                        notificationProductContainer.setVisibility(View.GONE);
                        flash_sale_layout.setVisibility(View.GONE);
                        notificationDetailsLayout.setVisibility(View.VISIBLE);

                        message.setText(notif.getMessage());
                        sender.setText(CommonFunctions.replaceEscapeData(notif.getSender()));
                        date.setText(notif.getDate());

                        db.updateNotificationStatus(db, "1", notif.getTxnNo());

                        if (!notif.getSenderImage().equals("") && notif.getSenderImage().toLowerCase().contains("http")) {
                            aq.id(sendericon).image(notif.getSenderImage(), false, true);
                            senderlogo.setVisibility(View.GONE);
                            sendericon.setVisibility(View.VISIBLE);
                        } else {
                            String initials = String.valueOf(notif.getSender().charAt(0));

                            senderlogo.setVisibility(View.VISIBLE);
                            sendericon.setVisibility(View.GONE);
                            senderlogo.setBackgroundResource(R.color.colortoolbar);
                            senderlogo.setText(initials);
                        }
                    } else {

                        progressDialog = new ProgressDialog(this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Preparing details... Please Wait for a moment...");
                        progressDialog.show();

                        notificationProductContainer.setVisibility(View.VISIBLE);
                        flash_sale_layout.setVisibility(View.VISIBLE);
                        notificationDetailsLayout.setVisibility(View.GONE);

                        String prodLogo = CommonFunctions.parseXML(messages[1], "productlogo");
                        String prodId = CommonFunctions.parseXML(messages[1], "productid");
                        String prodPrice = CommonFunctions.parseXML(messages[1], "productprice");
                        String prodDesc = CommonFunctions.parseXML(messages[1], "productdesc");
                        String prodName = CommonFunctions.parseXML(messages[1], "productname");
                        String storeid = CommonFunctions.parseXML(messages[1], "storeid");
                        String merchantid = CommonFunctions.parseXML(messages[1], "merchantid");


//                            Logger.debug("roneldayanan","productlogo: "+prodLogo);
//                            Logger.debug("roneldayanan","productid: "+prodId);
//                            Logger.debug("roneldayanan","productprice: "+prodPrice);
//                            Logger.debug("roneldayanan","productdesc: "+prodDesc);
//                            Logger.debug("roneldayanan","productname: "+prodName);
//                            Logger.debug("roneldayanan","StoreID:"+storeid);
//                            Logger.debug("roneldayanan","MerchantID: "+merchantid);

                        customValues = new GKNotificationsCustomValues();
                        customValues.setMerchantid(merchantid);
                        customValues.setStoreid(storeid);
                        customValues.setProductid(prodId);
                        customValues.setProductname(prodName);
                        customValues.setProductdesc(prodDesc);
                        customValues.setProductlogo(prodLogo);

                        Logger.debug("roneldayanan","Tail Message: "+messages[2]);
                        tailmessage.setText(CommonFunctions.replaceEscapeData(messages[2]));

                        flashMessage.setText(messages[0]);
                        aq.id(productLogo).image(prodLogo, false, true);
                        productname.setText(prodName);
                        productdesc.setText(prodDesc);
                        actualprice.setText("₱".concat(CommonFunctions.currencyFormatter(prodPrice)));

                        //new HttpAsyncTask2().execute(CommonVariables.GETBILLERS);
                        getBillers();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

                notificationProductContainer.setVisibility(View.GONE);
                flash_sale_layout.setVisibility(View.GONE);
                notificationDetailsLayout.setVisibility(View.VISIBLE);

                message.setText(CommonFunctions.replaceEscapeData(notif.getMessage()));
                sender.setText(CommonFunctions.replaceEscapeData(notif.getSender()));
                date.setText(notif.getDate());

                db.updateNotificationStatus(db, "1", notif.getTxnNo());

                if (!notif.getSenderImage().equals("") && notif.getSenderImage().toLowerCase().contains("http")) {
                    aq.id(sendericon).image(notif.getSenderImage(), false, true);
                    senderlogo.setVisibility(View.GONE);
                    sendericon.setVisibility(View.VISIBLE);
                } else {
                    String initials = String.valueOf(notif.getSender().charAt(0));

                    senderlogo.setVisibility(View.VISIBLE);
                    sendericon.setVisibility(View.GONE);
                    senderlogo.setBackgroundResource(R.color.colortoolbar);
                    senderlogo.setText(initials);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_notification_gotostore) {
            Intent intent = new Intent(this, GKStoreDetailsActivity.class);
            intent.putExtra("NOTIF_VALUES", new Gson().toJson(customValues));
            startActivity(intent);
            finish();
        }
    }

    private class HttpAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String json;


            //COMMON, REGISTRATION
            String imei = PreferenceUtils.getImeiId(getApplicationContext());
            String userid = PreferenceUtils.getUserId(getApplicationContext());
            String borrowerid = PreferenceUtils.getBorrowerId(getApplicationContext());
            String sessionid = PreferenceUtils.getSessionID(getApplicationContext());

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
                JSONObject parentObj = new JSONObject(result);
                String data = parentObj.getString("data");
                String message = parentObj.getString("message");
                String status = parentObj.getString("status");

                if (status.equals("000")) {
                    JSONArray jsonArr = new JSONArray(data);
                    db.deleteBillers(db, "FALSE");
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
                        String customerscclass = obj.getString("CustomerSCClass");
                        String notes = obj.getString("Notes1");
                        String groupcategory = obj.getString("GroupCategory");
                        String categorylogo = obj.getString("CategoryLogo");

                        customValues.setServicecode(servicecode);

                        db.insertBiller(db, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "FALSE", ".", ".", notes, groupcategory, categorylogo);
                        db.close();

                    }
                } else if (status.equals("003")) {
                    showAutoLogoutDialog(message);

                } else {
                    showErrorGlobalDialogs(message);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
        }
    }

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void getCommonData(){
        //COMMON, REGISTRATION
         imei = PreferenceUtils.getImeiId(getApplicationContext());
         userid = PreferenceUtils.getUserId(getApplicationContext());
         borrowerid = PreferenceUtils.getBorrowerId(getApplicationContext());
         sessionid = PreferenceUtils.getSessionID(getApplicationContext());
    }

    private void getBillers(){
        try{

            getCommonData();

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
                progressDialog.dismiss();
                showNoInternetToast();
            }

        }catch (Exception e){
            progressDialog.dismiss();
            showError();
            e.printStackTrace();
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

            progressDialog.dismiss();

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
                            db.deleteBillers(db, "FALSE");
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
                                String customerscclass = obj.getString("CustomerSCClass");
                                String notes = obj.getString("Notes1");
                                String groupcategory = obj.getString("GroupCategory");
                                String categorylogo = obj.getString("CategoryLogo");

                                customValues.setServicecode(servicecode);

                                db.insertBiller(db, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "FALSE", ".", ".", notes, groupcategory, categorylogo);
                                db.close();

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
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            progressDialog.dismiss();
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
        }
    };

}
