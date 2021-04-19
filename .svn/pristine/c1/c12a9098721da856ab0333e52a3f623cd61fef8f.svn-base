package com.goodkredit.myapplication.activities.gkstore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.androidquery.AQuery;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkstore.ViewGKStoreOrderDetailsHistoryAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.FetchStoreOrderDetails;
import com.goodkredit.myapplication.model.FetchStoreOrderList;
import com.goodkredit.myapplication.model.GKStoreImage;
import com.goodkredit.myapplication.responses.gkstore.GetGKStoreCancelOrderResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GKStoreHistoryDetailsActivity extends BaseActivity implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private AQuery aq;
    private DatabaseHandler mdb;
    private Toolbar toolbar;

    private String sessionid;

    //Image Logo
    private List<GKStoreImage> fetchorderimagelist = new ArrayList<>();
    //Single Photo
    private LinearLayout singlephotolayout;
    private ImageView single_photo_one;

    //Double Photo
    private LinearLayout doublephotolayout;
    private ImageView dbl_photo_one;
    private ImageView dbl_photo_two;

    //Multi PHoto
    private LinearLayout multiphotolayout;
    private ImageView multi_photo_one;
    private ImageView multi_photo_two;
    private ImageView multi_photo_three;
    private ImageView multi_photo_four;

    //ORDER DETAILS
    private TextView txtOrderTxnID;
    private TextView txtCustomerName;
    private TextView txtMobileNo;
    private TextView lblAddress;
    private TextView txtAddress;
    private TextView txtDeliveryType;
    private LinearLayout deliv_charge_container;
    private TextView txtDeliveryCharge;
    private TextView txtTotalAmount;
    private TextView txtDateTime;
    private TextView txtStatus;

    //PRODUCT DETAILS
    private RecyclerView recycler_view_gkstore_historydetails;
    private ViewGKStoreOrderDetailsHistoryAdapter adapter_history_details;
    private List<FetchStoreOrderDetails> fetchorderlist = new ArrayList<>();

    //REFRESH
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //PASSED DATA
    private Intent intent;
    private FetchStoreOrderList obj_fetchorder;

    private String strordertxnID = "";
    private String strcustomerfirstname = "";
    private String strcustomerlastname = "";
    private String strmobileno = "";
    private String straddress = "";
    private String strdatetime = "";
    private String strgrossamount = "";
    private String strtotalamount = "";
    private String strStatus = "";
    private String strcustomerservicecharge = "";
    private String strdeliverytype = "";
    private String strorderDetails = "";
    private String strservicecode = "";
    private String stryear = "";
    private String strmonth = "";
    private String strgkstoreid = "";
    private String strmessagefrompartner = "";
    private String strcustomeremailadd = "";
    private String strremarks = "";
    private String strmerchantid = "";
    private String strlatitude = "";
    private String strlongitude = "";
    private String vouchersession = "";
    private String borrowername = "";
    private String strgkstoredeliverytype = "";
    private String strmerchantlat = "";
    private String strmerchantlong = "";
    private String strmerchantaddress = "";
    private String strdeliverycharge = "";
    private String strorderdetails = "";
    private String strotherdetails = "";

    //DISCOUNT
    private double discount = 0.00;
    private String strdiscount = "";
    private String discountmessage = "";
    private int hasdiscount = 0;

    //LOADER
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private MaterialDialog mDialog = null;

    //PHILCARE
    private LinearLayout linear_message_container;
    private TextView edt_partner_message;
    private LinearLayout btn_send_email_container;
    private Button btn_send_email;
    private MaterialDialog emailDialog;

    //STEP ORDER
    private LinearLayout btn_pay_now_container;
    private Button btn_pay_now;
    private Button btn_pay_now_decline;
    private boolean isPayNow = false;

    //FOR DIALOGS
    private Dialog dialog = null;

    //REQUEST CANCEL
    private String strstatusrequest = "";

    //DECLINE REMARKS
    private String declineremarks = "";

    //DECLINE/CANCEL ORDERS ORDER
    private LinearLayout btn_order_container;
    private Button btn_cancel_order;
    private boolean is_order_status = false;

    private LinearLayout btn_decline_order_container;
    private Button btn_decline_order;

    private EditText dialogEdt;
    private String dialogString = "";

    private String cancelOrderTxnNo = "";

    //SPECIAL INSTRUCTIONS
    private LinearLayout txt_special_instructions_container;
    private TextView txt_special_instructions;

    //REMARKS
    private LinearLayout txt_remarks_container;
    private TextView txt_remarks;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gk_store_history_details);

        init();

        initData();
    }

    private void init() {
        //loader
        mLlLoader = (RelativeLayout) findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) findViewById(R.id.fetching);
        mTvWait = (TextView) findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = (RelativeLayout) findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //IMAGE LOGO
        singlephotolayout = (LinearLayout) findViewById(R.id.singlephotolayout);
        single_photo_one = (ImageView) findViewById(R.id.single_photo_one);

        //Double Photo
        doublephotolayout = (LinearLayout) findViewById(R.id.doublephotolayout);
        dbl_photo_one = (ImageView) findViewById(R.id.dbl_photo_one);
        dbl_photo_two = (ImageView) findViewById(R.id.dbl_photo_two);

        //Multi Photo
        multiphotolayout = (LinearLayout) findViewById(R.id.multiphotolayout);
        multi_photo_one = (ImageView) findViewById(R.id.multi_photo_one);
        multi_photo_two = (ImageView) findViewById(R.id.multi_photo_two);
        multi_photo_three = (ImageView) findViewById(R.id.multi_photo_three);
        multi_photo_four = (ImageView) findViewById(R.id.multi_photo_four);

        //ORDER DETAILS
        txtOrderTxnID = (TextView) findViewById(R.id.txtOrderTxnID);
        txtCustomerName = (TextView) findViewById(R.id.txtCustomerName);
        txtMobileNo = (TextView) findViewById(R.id.txtMobileNo);

        lblAddress = (TextView) findViewById(R.id.lblAddress);
        txtAddress = (TextView) findViewById(R.id.txtAddress);

        txtDeliveryType = (TextView) findViewById(R.id.txtDeliveryType);

        deliv_charge_container = (LinearLayout) findViewById(R.id.deliv_charge_container);
        txtDeliveryCharge = (TextView) findViewById(R.id.txtDeliveryCharge);

        txtTotalAmount = (TextView) findViewById(R.id.txtTotalAmount);
        txtDateTime = (TextView) findViewById(R.id.txtDateTime);
        txtStatus = (TextView) findViewById(R.id.txtStatus);

        //PRODUCT DETAILS
        recycler_view_gkstore_historydetails = (RecyclerView) findViewById(R.id.recycler_view_gkstore_historydetails);

        //CANCEL ORDERS
        btn_order_container = (LinearLayout) findViewById(R.id.btn_order_container);
        btn_cancel_order = (Button) findViewById(R.id.btn_cancel_order);
        btn_cancel_order.setOnClickListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(false);

        //PHILCARE
        linear_message_container = (LinearLayout) findViewById(R.id.linear_message_container);
        edt_partner_message = (TextView) findViewById(R.id.edt_partner_message);
        btn_send_email_container = (LinearLayout) findViewById(R.id.btn_send_email_container);
        btn_send_email = (Button) findViewById(R.id.btn_send_email);
        btn_send_email.setOnClickListener(this);

        //STEP ORDER
        btn_pay_now_container = (LinearLayout) findViewById(R.id.btn_pay_now_container);
        btn_pay_now = (Button) findViewById(R.id.btn_pay_now);
        btn_pay_now.setOnClickListener(this);
        btn_pay_now_decline = (Button) findViewById(R.id.btn_pay_now_decline);
        btn_pay_now_decline.setOnClickListener(this);

        //DECLINE ORDER
        btn_decline_order_container = (LinearLayout) findViewById(R.id.btn_decline_order_container);
        btn_decline_order = (Button) findViewById(R.id.btn_decline_order);
        btn_decline_order.setOnClickListener(this);

        //SPECIAL INSTRUCTIONS
        txt_special_instructions_container = (LinearLayout) findViewById(R.id.txt_special_instructions_container);
        txt_special_instructions = (TextView) findViewById(R.id.txt_special_instructions);

        //REMARKS
        txt_remarks_container = (LinearLayout) findViewById(R.id.txt_remarks_container);
        txt_remarks = (TextView) findViewById(R.id.txt_remarks);
    }

    private void initData() {
        setupToolbar();
        setTitle("Order History Details");

        aq = new AQuery(this);
        mdb = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        //Getting the values of the passed intent
        Bundle b = getIntent().getExtras();

        strordertxnID = b.getString("ordertxnID");
        strcustomerfirstname = b.getString("firstname");
        strcustomerlastname = b.getString("lastname");
        strmobileno = b.getString("mobileno");
        strdeliverytype = b.getString("deliverytype");
        straddress = b.getString("address");
        strdatetime = b.getString("datetime");
        strgrossamount = b.getString("grossamount");
        strtotalamount = b.getString("totalamount");
        strStatus = b.getString("status");
        strcustomerservicecharge = b.getString("customerservicecharge");
        strdiscount = b.getString("resellerdiscount");
        strorderDetails = b.getString("orderDetails");
        strservicecode = b.getString("servicecode");
        strgkstoreid = b.getString("gkstoreid");
        strmessagefrompartner = b.getString("messagefrompartner");
        strcustomeremailadd = b.getString("customeremailadd");
        strremarks = b.getString("remarks");
        strmerchantid = b.getString("merchantid");
        strlatitude = b.getString("latitude");
        strlongitude = b.getString("longitude");
        borrowername = b.getString("GKSTOREBORROWERNAME");
        strgkstoredeliverytype = b.getString("GKSTOREDELIVERYTYPE");
        strmerchantlat = b.getString("GKSTOREMERCHANTLAT");
        strmerchantlong = b.getString("GKSTOREMERCHANTLONG");
        strmerchantaddress = b.getString("GKSTOREMERCHANTADD");
        strdeliverycharge = b.getString("GKSTOREMERCHANTSC");
        strorderdetails = b.getString("ORDERDETAILS");
        strotherdetails = b.getString("CUSTOMEROTHERDETAILS");

        stryear = CommonFunctions.getYearFromDate(strdatetime);
        strmonth = CommonFunctions.getMonthFromDate(strdatetime);

        //SETTING OF VALUES
        txtOrderTxnID.setText(strordertxnID);
        txtCustomerName.setText(strcustomerfirstname + " " + strcustomerlastname);
        txtMobileNo.setText(strmobileno);
        txtTotalAmount.setText("₱" + CommonFunctions.currencyFormatter(strtotalamount));
        txtStatus.setText(strStatus);
        if (strStatus.equals("COMPLETED")) {
            txtStatus.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_4A90E2));
        } else if (strStatus.equals("FOR CANCELLATION") || strStatus.equals("CANCELLED") || strStatus.equals("FAILED")) {
            txtStatus.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorred));
        } else {
            txtStatus.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_757575));
        }
        txtDateTime.setText(CommonFunctions.convertDate(strdatetime));
        txtDeliveryType.setText(strdeliverytype);

        //Checks if delivery Type either PickUp or Delivery
        String deliveryType = strdeliverytype;
        switch (deliveryType) {
            case "PHILCARE":
                lblAddress.setText("Store Address");
                deliv_charge_container.setVisibility(View.GONE);
                break;
            case "PICKUP":
                lblAddress.setText("Store Address");
                deliv_charge_container.setVisibility(View.GONE);
                break;
            default:
                lblAddress.setText("Delivery Address");
                deliv_charge_container.setVisibility(View.VISIBLE);
                txtDeliveryCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(strcustomerservicecharge)));
                break;
        }

        txtAddress.setText(CommonFunctions.replaceEscapeData(straddress));

        String orderdetails = strorderDetails;

        //FOR ORDER DETAILS
        try {
            convertStringtoListOrderDetails(orderdetails);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //DISPLAYING IMAGES FROM ORDERLAYOUT
        try {
            convertStringtoListGKStoreImage(orderdetails);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpImageLayout();
        scrollonTop();

        recycler_view_gkstore_historydetails.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recycler_view_gkstore_historydetails.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null));
        recycler_view_gkstore_historydetails.setNestedScrollingEnabled(false);
        adapter_history_details = new ViewGKStoreOrderDetailsHistoryAdapter(getViewContext());
        recycler_view_gkstore_historydetails.setAdapter(adapter_history_details);
        adapter_history_details.updateHistoryDetails(fetchorderlist);

        String remarks = CommonFunctions.parseXML(strremarks, "remarks");
        String declineremarks = CommonFunctions.parseXML(strremarks, "declineremarks");

        if (strStatus.equals("PENDING") || strStatus.equals("ON PROCESS")) {
            //btn_order_container.setVisibility(View.GONE);
            btn_order_container.setVisibility(View.GONE);
            btn_pay_now_container.setVisibility(View.GONE);
            btn_decline_order_container.setVisibility(View.GONE);
            txt_special_instructions_container.setVisibility(View.VISIBLE);
            txt_special_instructions.setText(remarks);
        } else if (strStatus.equals("FOR PAYMENT")) {
            btn_order_container.setVisibility(View.GONE);
            btn_pay_now_container.setVisibility(View.VISIBLE);
            btn_decline_order_container.setVisibility(View.GONE);
            txt_special_instructions_container.setVisibility(View.VISIBLE);
            txt_special_instructions.setText(remarks);
        } else if (strStatus.equals("FOR APPROVAL")) {
            btn_order_container.setVisibility(View.GONE);
            btn_pay_now_container.setVisibility(View.GONE);
            btn_decline_order_container.setVisibility(View.VISIBLE);
            txt_special_instructions_container.setVisibility(View.VISIBLE);
            txt_special_instructions.setText(remarks);
        }  else if (strStatus.equals("CANCELLED") || strStatus.equals("DECLINED")) {
            btn_pay_now_container.setVisibility(View.GONE);
            btn_order_container.setVisibility(View.GONE);
            btn_decline_order_container.setVisibility(View.GONE);
            txt_remarks_container.setVisibility(View.VISIBLE);
            txt_remarks.setText(declineremarks);
        } else {
            btn_pay_now_container.setVisibility(View.GONE);
            btn_order_container.setVisibility(View.GONE);
            btn_decline_order_container.setVisibility(View.GONE);
        }

        //PHILCARE
        if (strgkstoreid.equals("PHILCARE")) {
            if (strmessagefrompartner.equals("") || strmessagefrompartner == null || strmessagefrompartner.equals("null")) {
                linear_message_container.setVisibility(View.GONE);
            } else {
                linear_message_container.setVisibility(View.VISIBLE);
                edt_partner_message.setText(strmessagefrompartner);
                if (strStatus.equals("COMPLETED")) {
                    btn_send_email_container.setVisibility(View.GONE);
                } else {
                    btn_send_email_container.setVisibility(View.GONE);
                }
            }
        } else {
            linear_message_container.setVisibility(View.GONE);
        }
    }

    //Converts the String to List for Order Details
    private void convertStringtoListOrderDetails(String orderdetails) throws JSONException {
        ArrayList<FetchStoreOrderDetails> fetchtempoorderlist = new ArrayList<>();
        fetchtempoorderlist = new Gson().fromJson(orderdetails, new TypeToken<List<FetchStoreOrderDetails>>() {
        }.getType());
        fetchorderlist = fetchtempoorderlist;
    }

    //Converts the String to List for Order Details then convert and add to GKStoreImage Object
    private void convertStringtoListGKStoreImage(String orderdetails) throws JSONException {
        ArrayList<FetchStoreOrderDetails> fetchorderlist = new ArrayList<>();

        List<GKStoreImage> photoobject = new ArrayList<>();

        fetchorderlist = new Gson().fromJson(orderdetails, new TypeToken<List<FetchStoreOrderDetails>>() {
        }.getType());

        for (FetchStoreOrderDetails gkstoreHistoryList : fetchorderlist) {
            String productimage = gkstoreHistoryList.getProductImage();
            photoobject.add(new GKStoreImage(productimage));
        }

        fetchorderimagelist = photoobject;
    }

    private void setUpImageLayout() {
        int total_items = fetchorderimagelist.size();

        if (total_items == 1) {
            singlephotolayout.setVisibility(View.VISIBLE);
            doublephotolayout.setVisibility(View.GONE);
            multiphotolayout.setVisibility(View.GONE);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(single_photo_one);
        } else if (total_items == 2) {
            singlephotolayout.setVisibility(View.GONE);
            doublephotolayout.setVisibility(View.VISIBLE);
            multiphotolayout.setVisibility(View.GONE);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(dbl_photo_one);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(1).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(dbl_photo_two);
        } else if (total_items == 3) {
            singlephotolayout.setVisibility(View.GONE);
            doublephotolayout.setVisibility(View.VISIBLE);
            multiphotolayout.setVisibility(View.GONE);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(dbl_photo_one);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(1).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(dbl_photo_two);
        } else if (total_items > 3) {
            singlephotolayout.setVisibility(View.GONE);
            doublephotolayout.setVisibility(View.GONE);
            multiphotolayout.setVisibility(View.VISIBLE);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(0).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(multi_photo_one);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(1).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(multi_photo_two);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(2).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(multi_photo_three);
            Glide.with(getViewContext())
                    .load(fetchorderimagelist.get(3).getImageUrl())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(multi_photo_four);
        } else if (total_items == 0) {
            singlephotolayout.setVisibility(View.VISIBLE);
            doublephotolayout.setVisibility(View.GONE);
            multiphotolayout.setVisibility(View.GONE);
            Glide.with(getViewContext())
                    .load("")
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .override(250, 250)
                            .fitCenter())
                    .into(single_photo_one);
        }
    }

    private void orderCancelDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Order");
        alertDialog.setMessage("Are you sure you want to cancel your order?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                is_order_status = true;
                getSession();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void orderCancelDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //TEXTVIEW
        LinearLayout textViewMainContainer = dialog.getTextViewMessageContainer();
        textViewMainContainer.setPadding(15, 15, 15, 0);

        List<String> declineList = new ArrayList<>();
        declineList.add("Reason for Cancellation:");

        LinearLayout textViewContainer = dialog.setContentTextView(declineList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(15, 0, 15, 15);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMVARCHAR));

        LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER,
                        R.drawable.border, 0, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setSingleLine(false);
                editItem.setLines(4);
                editItem.setMinLines(3);
                editItem.setMaxLines(5);
                editItem.setVerticalScrollBarEnabled(true);
                editItem.setBackgroundResource(R.drawable.border);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    dialogEdt = editItem;
                    dialogEdt.addTextChangedListener(dialogTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtnone = dialog.btnDoubleOne();
        dblbtnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtntwo = dialog.btnDoubleTwo();
        dblbtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                is_order_status = true;
                if (!dialogString.trim().equals("")) {
                    declineremarks = dialogString;
                } else {
                    declineremarks = ".";
                }
                getSession();
            }
        });
    }

    private void orderSuccessCancellation() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Your order has been succesfully cancelled.",
                "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void sendToEmail() {
        emailDialog = new MaterialDialog.Builder(getViewContext())
                .autoDismiss(false)
                .cancelable(false)
                .title("Email")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Add email address", strcustomeremailadd, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        strcustomeremailadd = input.toString();
                    }
                })
                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (strcustomeremailadd.length() > 0) {
                        } else {
                            showToast("Please input an email address", GlobalToastEnum.WARNING);
                        }
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void sendToEmailNew() {
        emailDialog = new MaterialDialog.Builder(getViewContext())
                .autoDismiss(false)
                .cancelable(false)
                .title("Email")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input("Add email address", strcustomeremailadd, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        strcustomeremailadd = input.toString();
                    }
                })
                .positiveText("Send")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (strcustomeremailadd.length() > 0) {
                        } else {
                            showToast("Please input an email address", GlobalToastEnum.WARNING);
                        }
                    }
                }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void checkPayments() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Payment");
        alertDialog.setMessage("Will now proceed to payments. Would you like to proceed?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                isPayNow = true;
                getSession();
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void checkPaymentsNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("Payment", "Will now proceed to payments. Would you like to continue?",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.NOTICE);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View cancelbtn = dialog.btnDoubleOne();
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View okbtn = dialog.btnDoubleTwo();
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;
                dialog.dismiss();
                isPayNow = true;
                getSession();
            }
        });
    }

    private void orderDeclineOrder() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Cancel", "OK", ButtonTypeEnum.DOUBLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //TEXTVIEW
        LinearLayout textViewMainContainer = dialog.getTextViewMessageContainer();
        textViewMainContainer.setPadding(15, 15, 15, 0);

        List<String> declineList = new ArrayList<>();
        declineList.add("Reason for Cancellation:");

        LinearLayout textViewContainer = dialog.setContentTextView(declineList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(15, 0, 15, 15);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMVARCHAR));

        LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.CENTER,
                        R.drawable.border, 0, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setSingleLine(false);
                editItem.setLines(4);
                editItem.setMinLines(3);
                editItem.setMaxLines(5);
                editItem.setVerticalScrollBarEnabled(true);
                editItem.setBackgroundResource(R.drawable.border);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    dialogEdt = editItem;
                    dialogEdt.addTextChangedListener(dialogTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtnone = dialog.btnDoubleOne();
        dblbtnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View dblbtntwo = dialog.btnDoubleTwo();
        dblbtntwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                is_order_status = true;
                if (!dialogString.trim().equals("")) {
                    declineremarks = dialogString;
                } else {
                    declineremarks = ".";
                }
                getSession();
            }
        });
    }

    private void orderSuccessDecline() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Your order request has been succesfully cancelled.",
                "Close", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.NOTICE);

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
    }

    private void scrollonTop() {
        final NestedScrollView main = (NestedScrollView) findViewById(R.id.scrollmaincontainer);
        main.post(new Runnable() {
            public void run() {
                main.smoothScrollTo(0, 0);
            }
        });
    }

    private void checkActionType() {

    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    //PROCEEDING TO PAYMENTS
    private void proceedtoPayments(final double totalamountcheck) {
        CommonFunctions.hideDialog();
        PreferenceUtils.saveStringPreference(getViewContext(), PreferenceUtils.KEY_STORE_FROM, "HISTORY");
        PreferenceUtils.saveStringPreference(getViewContext(), "gkstoretxnno", strordertxnID);

        Intent intent = new Intent(getBaseContext(), Payment.class);
        intent.putExtra("GKSTOREFIRSTNAME", strcustomerfirstname);
        intent.putExtra("GKSTORELASTNAME", strcustomerlastname);
        intent.putExtra("GKSTOREMOBILENO", strmobileno);
        intent.putExtra("GKSTOREEMAILADD", strcustomeremailadd);
        intent.putExtra("GKSTORECUSTOMERADD", straddress);
        intent.putExtra("VOUCHERSESSION", vouchersession);
        intent.putExtra("AMOUNT", String.valueOf(totalamountcheck));
        intent.putExtra("DISCOUNT", strdiscount);
        intent.putExtra("MERCHANTID", strmerchantid);
        intent.putExtra("GKSTOREID", strgkstoreid);
        intent.putExtra("LATITUDE", strlatitude);
        intent.putExtra("LONGITUDE", strlongitude);
        intent.putExtra("GKSTOREBORROWERNAME", borrowername);
        intent.putExtra("GKSTOREDELIVERYTYPE", strgkstoredeliverytype);
        intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
        intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
        intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);
        intent.putExtra("GKSTOREMERCHANTSC", strdeliverycharge);
        intent.putExtra("GKSTOREREMARKS", strremarks);
        intent.putExtra("GROSSPRICE", strgrossamount);
        intent.putExtra("GKSERVICECODE", strservicecode);
        intent.putExtra("GKHASDISCOUNT", hasdiscount);
        intent.putExtra("ORDERDETAILS", strorderdetails);
        intent.putExtra("CUSTOMEROTHERDETAILS", strotherdetails);

        startActivity(intent);
    }

    //----------------------------------API------------------------------------------
    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);

            mTvFetching.setText("Fetching order history.");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (is_order_status) {
                //gkstoreCancelOrder(gkStoreCancelOrderSession);
                gkstoreCancelOrderV2();
            } else if (isPayNow) {
                //prePurchase(prePurchaseSession);
                prePurchaseV3();
            }

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            showNoInternetToast();
        }
    }

    private void gkstoreCancelOrder(Callback<GetGKStoreCancelOrderResponse> getGKStoreCancelOrderResponseCallback) {
        Call<GetGKStoreCancelOrderResponse> gkstorecancelorder = RetrofitBuild.getGKStoreService(getViewContext())
                .getGkStoreCancelOrder(sessionid,
                        imei,
                        userid,
                        authcode,
                        borrowerid,
                        strordertxnID,
                        strservicecode,
                        stryear,
                        strmonth,
                        strstatusrequest,
                        declineremarks,
                        strmerchantid,
                        strgkstoreid
                );

        gkstorecancelorder.enqueue(getGKStoreCancelOrderResponseCallback);
    }

    private final Callback<GetGKStoreCancelOrderResponse> gkStoreCancelOrderSession = new Callback<GetGKStoreCancelOrderResponse>() {

        @Override
        public void onResponse(Call<GetGKStoreCancelOrderResponse> call, Response<GetGKStoreCancelOrderResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                //000 - list of process
                if (response.body().getStatus().equals("000")) {
                    mLlLoader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    is_order_status = false;
                    if (strstatusrequest.equals("FOR CANCELLATION")) {
                        orderSuccessCancellation();
                    } else if (strstatusrequest.equals("CANCELLED")) {
                        orderSuccessDecline();
                    } else if (strstatusrequest.equals("FOR PAYMENT")) {
                        orderSuccessDecline();
                    }
                } else {
                    mLlLoader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);
                    showErrorGlobalDialogs(response.body().getMessage());
                }
            } else {
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GetGKStoreCancelOrderResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            CommonFunctions.hideDialog();
        }
    };

    private void prePurchase(Callback<String> prePurchaseCallback) {
        //Limits the two decimal places
        String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
        strtotalamount = valuecheck;

        Call<String> prepurchase = RetrofitBuild.prePurchaseService(getViewContext())
                .prePurchaseCall(borrowerid,
                        strtotalamount,
                        userid,
                        imei,
                        sessionid,
                        authcode);
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            try {
                ResponseBody errorBody = response.errorBody();
                if (errorBody == null) {

                    vouchersession = response.body().toString();

                    if (!vouchersession.isEmpty()) {
                        if (vouchersession.equals("001")) {
                            CommonFunctions.hideDialog();
                            showToast("Session: Invalid session.", GlobalToastEnum.ERROR);
                        } else if (vouchersession.equals("error")) {
                            CommonFunctions.hideDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                        } else if (vouchersession.contains("<!DOCTYPE html>")) {
                            CommonFunctions.hideDialog();
                            showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                        } else {
                            if (vouchersession.length() > 0) {
                                double discount = Double.parseDouble(strdiscount);
                                double totalamountcheck = Double.parseDouble(strtotalamount);
                                if (discount > 0) {
                                    hasdiscount = 1;
                                } else {
                                    hasdiscount = 0;
                                }

                                proceedtoPayments(totalamountcheck);
                            } else {
                                showWarningGlobalDialogs("Invalid Voucher Session.");
                            }
                        }
                    } else {
                        CommonFunctions.hideDialog();
                        showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            CommonFunctions.hideDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

    //---------------------------------------OTHERS -----------------------------------
    private TextWatcher dialogTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dialogString = s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //Intent Start
    public static void start(Context context, String ordertxnID, String firstname, String lastname, String mobileno,
                             String deliverytype, String address, String datetime, String grossamount, String totalamount,
                             String status, String customerservicecharge, String resellerdiscount, String orderDetails,
                             String servicecode, String gkstoreid, String messagefrompartner, String customeremailadd,
                             String remarks, String merchantid, String latitude, String longitude, String borrowername,
                             String strgkstoredeliverytype, String strmerchantlat, String strmerchantlong,
                             String strmerchantaddress,
                             String strdeliverycharge, String orderdetails, String otherdetails) {

        Intent intent = new Intent(context, GKStoreHistoryDetailsActivity.class);
        intent.putExtra("ordertxnID", ordertxnID);
        intent.putExtra("firstname", firstname);
        intent.putExtra("lastname", lastname);
        intent.putExtra("mobileno", mobileno);
        intent.putExtra("deliverytype", deliverytype);
        intent.putExtra("address", address);
        intent.putExtra("datetime", datetime);
        intent.putExtra("grossamount", grossamount);
        intent.putExtra("totalamount", totalamount);
        intent.putExtra("status", status);
        intent.putExtra("customerservicecharge", customerservicecharge);
        intent.putExtra("resellerdiscount", resellerdiscount);
        intent.putExtra("orderDetails", orderDetails);
        intent.putExtra("servicecode", servicecode);
        intent.putExtra("gkstoreid", gkstoreid);
        intent.putExtra("messagefrompartner", messagefrompartner);
        intent.putExtra("customeremailadd", customeremailadd);
        intent.putExtra("remarks", remarks);
        intent.putExtra("merchantid", merchantid);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("GKSTOREBORROWERNAME", borrowername);
        intent.putExtra("GKSTOREDELIVERYTYPE", strgkstoredeliverytype);
        intent.putExtra("GKSTOREMERCHANTLAT", strmerchantlat);
        intent.putExtra("GKSTOREMERCHANTLONG", strmerchantlong);
        intent.putExtra("GKSTOREMERCHANTADD", strmerchantaddress);
        intent.putExtra("GKSTOREMERCHANTSC", strdeliverycharge);
        intent.putExtra("ORDERDETAILS", orderdetails);
        intent.putExtra("CUSTOMEROTHERDETAILS", otherdetails);

        context.startActivity(intent);
    }

    //Intent Start
    public static void start(Context context, FetchStoreOrderList fetchstoreorderlist) {
        Bundle args = new Bundle();
        args.putParcelable("ORDERLIST", fetchstoreorderlist);

        Intent intent = new Intent(context, GKStoreHistoryDetailsActivity.class);
        intent.putExtras(args);

        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        getSession();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                getSession();
                break;
            }
            case R.id.refreshnointernet: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                getSession();
                break;
            }

            case R.id.btn_cancel_order: {
                //orderCancelDialog();
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                strstatusrequest = "FOR CANCELLATION";
                orderCancelDialogNew();
                break;
            }

            case R.id.btn_send_email: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                //sendToEmail();
                break;
            }

            case R.id.btn_pay_now: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                //checkPayments();
                //checkPaymentsNew();
                isPayNow = true;
                getSession();
                break;
            }

            case R.id.btn_decline_order: {
                //orderCancelDialog();
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                strstatusrequest = "CANCELLED";
                orderDeclineOrder();
                break;
            }

            case R.id.btn_pay_now_decline: {
                //orderCancelDialog();
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                strstatusrequest = "FOR PAYMENT";
                orderDeclineOrder();
                break;
            }
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * JANUARY 2020
     * */
    private void prePurchaseV3() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            String valuecheck = CommonFunctions.totalamountlimiter(String.valueOf(strtotalamount));
            strtotalamount = valuecheck;

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("amountpurchase", strtotalamount);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index =CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "prePurchaseV3");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            prePurchaseV3Object();

        } else {
            showNoInternetToast();
        }
    }

    private void prePurchaseV3Object(){
        Call<GenericResponse> call = RetrofitBuilder.getCommonV2API(getViewContext())
                .prePurchaseV3(
                        authenticationid, sessionid, param
                );
        call.enqueue(prePurchaseV3Callback);
    }

    private final Callback<GenericResponse> prePurchaseV3Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            vouchersession = CommonFunctions.parseJSON(decryptedData, "value");
                            if (vouchersession.length() > 0) {
                                double discount = Double.parseDouble(strdiscount);
                                double totalamountcheck = Double.parseDouble(strtotalamount);
                                if (discount > 0) {
                                    hasdiscount = 1;
                                } else {
                                    hasdiscount = 0;
                                }
                                proceedtoPayments(totalamountcheck);
                            } else {
                                showWarningGlobalDialogs("Invalid Voucher Session.");
                            }
                        }

                    }  else {
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorGlobalDialogs(decryptedMessage);
                        }
                    }
                } else {
                    showErrorToast();
                }
            } catch (Exception e){
                e.printStackTrace();
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorToast();
        }
    };

    private void gkstoreCancelOrderV2() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("ordertxnno", strordertxnID);
            parameters.put("servicecode", strservicecode);
            parameters.put("passedyear", stryear);
            parameters.put("passedmonth", strmonth);
            parameters.put("statusrequest", strstatusrequest);
            parameters.put("declineremarks", declineremarks);
            parameters.put("merchantid", strmerchantid);
            parameters.put("storeid", strgkstoreid);
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index =CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "gkstoreCancelOrderV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            gkstoreCancelOrderV2Object();

        } else {
            showNoInternetToast();
        }
    }

    private void gkstoreCancelOrderV2Object(){
        Call<GenericResponse> call = RetrofitBuilder.getGKStoreV2API(getViewContext())
                .gkstoreCancelOrderV2(
                        authenticationid, sessionid, param
                );
        call.enqueue(gkstoreCancelOrderV2CallBack);
    }

    private final Callback<GenericResponse> gkstoreCancelOrderV2CallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                ResponseBody errorBody = response.errorBody();

                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);

                if (errorBody == null) {
                    String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                        String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                        if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                            showErrorToast();
                        } else {
                            is_order_status = false;
                            if (strstatusrequest.equals("FOR CANCELLATION")) {
                                orderSuccessCancellation();
                            } else if (strstatusrequest.equals("CANCELLED")) {
                                orderSuccessDecline();
                            } else if (strstatusrequest.equals("FOR PAYMENT")) {
                                orderSuccessDecline();
                            }
                        }

                    }  else {
                        if (response.body().getStatus().equals("error")) {
                            showErrorToast();
                        } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        } else {
                            showErrorGlobalDialogs(decryptedMessage);
                        }
                    }
                } else {
                    showErrorToast();
                }
            } catch (Exception e){
                e.printStackTrace();
                mLlLoader.setVisibility(View.GONE);
                mSwipeRefreshLayout.setRefreshing(false);
                showErrorToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showErrorToast();
        }
    };

}
