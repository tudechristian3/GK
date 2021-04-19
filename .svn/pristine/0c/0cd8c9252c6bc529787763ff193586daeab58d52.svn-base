package com.goodkredit.myapplication.fragments.skycable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.delivery.SetupDeliveryAddressActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.AmazonCredentials;
import com.goodkredit.myapplication.bean.SkycableDictionary;
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.bean.SkycableServiceArea;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.GetS3keyResponse;
import com.goodkredit.myapplication.responses.skycable.CheckIfConfigIsAvailableResponse;
import com.goodkredit.myapplication.responses.skycable.GetSkycablePPVCustomerServiceChargeResponse;
import com.goodkredit.myapplication.responses.skycable.RegisterNewSkycableAccountResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.android.gms.maps.model.LatLng;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SkycableNewApplicationFragment extends BaseFragment implements View.OnClickListener {

//    private MaterialDialog mLoaderDialog;
//    private TextView mLoaderDialogMessage;
//    private TextView mLoaderDialogTitle;
//    private ImageView mLoaderDialogImage;
//    private TextView mLoaderDialogClose;
//    private TextView mLoaderDialogRetry;
//    private RelativeLayout buttonLayout;

    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtCity;
    private EditText edtZipCode;
    private EditText edtGender;
    private EditText edtBirthDate;
    private EditText edtMobileNo;
    private EditText edtTelephoneNumber;
    private EditText edtEmailAddress;
    private EditText edtAddress;
    private EditText edtBillingAddress;
    private EditText edtInstallationAddress;
    private CheckBox cbxBillingAddress;
    private CheckBox cbxInstallationAddress;
    private Button btnProceed;

    private String mobileno;
    private String merchantid;
    private String planid = ".";
    private double amount = 0;
    private String latitude = "";
    private String longitude = "";
    private String servicecode;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    private boolean isLoading = false;
    private DatabaseHandler mdb;
//    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private MaterialDialog mDialog;
    private TextView txvMessage;

    //MAP ICONS
    private ImageView imgAddress;
    private ImageView imgBillingAddress;
    private ImageView imgInstallationAddress;

    //MAP
    private double getlatitude = 0;
    private double getlongitude = 0;

    private Uri imageUri;

    private LinearLayout profileCameraLayout;
    private LinearLayout profileGalleryLayout;
    private LinearLayout nationalidlayout;
    private ImageView nationalIdImage;
    private ImageView camera;
    private ImageView gallery;

    private LinearLayout profileCameraLayout2;
    private LinearLayout profileGalleryLayout2;
    private LinearLayout nationalidlayout2;
    private ImageView nationalIdImage2;
    private ImageView camera2;
    private ImageView gallery2;

    private boolean isProofOfBilling = false;

    private List<String> arrPlans;

    private boolean isS3Key = false;
    private boolean isRegister = false;

    private List<SkycableDictionary> skycableDictionaries;
    private SkycableDictionary skycableDictionary = null;
    private ArrayList<SkycableServiceArea> skycableServiceAreasList;
    private SkycableServiceArea skycableServiceAreaObj;

    public Uri imageidUri = null;
    public Uri imagepobUri = null;
    private AmazonCredentials amazonCredentials = null;

    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;

    private String imageidurl = "";
    private String imagepoburl = "";
    private String imageidfilename = "";
    private String imagepobfilename = "";

    private boolean isError = false;
    private Uri fileUri = null;

    private String bucketName;

    private List<SkycableRegistration> skycableRegistrations;

    //Fields
    private TextView txvFirstName;
    private TextView txvLastName;
    private TextView txvGender;
    private TextView txvBirthDate;
    private TextView txvMobileNumber;
    private TextView txvTelephoneNumber;
    private TextView txvEmailAddress;
    private TextView txvAddress;
    private TextView txvCity;
    private TextView txvZipCode;
    private TextView txvBillingAddress;
    private TextView txvInstallationAddress;

    private ImageView imgGenderArrowRight;

    private double installationAddressLatitude = 0;
    private double installationAddressLongitude = 0;

    private LinearLayout linearPlanItemLayout;
    private TextView txvName;
    private TextView txvAmount;
    private ImageView imgPlanItem;

    private TextView txvDescription;
    private TextView txvInstallationFee;
    private TextView txvMonthlyFee;
    private TextView txvCashOut;
    private TextView txvDiscount;

    private boolean isServiceCharge = false;
    private boolean isConfigAvailable = false;

    //CONFIRM DIALOG
    private TextView txvConfirmMonthlyFee;
    private TextView txvConfirmInstallationFee;
    private TextView txvConfirmServiceCharge;
    private TextView txvConfirmDiscount;
    private TextView txvConfirmTotalAmount;
    private TextView txvConfirmInitialCashout;
    private MaterialDialog mConfirmDialog;

    private LinearLayout linearDiscountlayout;

    private String skyServiceAreaID;

    private boolean isUpload = false;
    private File idImageFile;
    private File pobImageFile;

    private File imageFile;
    private String dialogName;
    private String filePath = null;
    private boolean isID = false;
    private boolean isPOB = false;

    //UNIFIED SESSION
    private String sessionid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skycable_new_application, container, false);

        setHasOptionsMenu(true);

        init(view);

        initData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (imgAddress != null) {
            imgAddress.setEnabled(true);
        }

        if (imgBillingAddress != null) {
            imgBillingAddress.setEnabled(true);
        }

        if (imgInstallationAddress != null) {
            imgInstallationAddress.setEnabled(true);
        }

        if (btnProceed != null) {
            btnProceed.setEnabled(true);
        }

    }

//    private void setupLoaderDialog() {
//        mLoaderDialog = new MaterialDialog.Builder(getViewContext())
//                .cancelable(false)
//                .customView(R.layout.dialog_custom_animated, false)
//                .build();
//
//        View view = mLoaderDialog.getCustomView();
//        if (view != null) {
//            mLoaderDialogMessage = (TextView) view.findViewById(R.id.mLoaderDialogMessage);
//            mLoaderDialogTitle = (TextView) view.findViewById(R.id.mLoaderDialogTitle);
//            mLoaderDialogImage = (ImageView) view.findViewById(R.id.mLoaderDialogImage);
//            mLoaderDialogClose = (TextView) view.findViewById(R.id.mLoaderDialogClose);
//            mLoaderDialogClose.setOnClickListener(this);
//            mLoaderDialogRetry = (TextView) view.findViewById(R.id.mLoaderDialogRetry);
//            mLoaderDialogRetry.setVisibility(View.GONE);
//            mLoaderDialogRetry.setOnClickListener(this);
//            buttonLayout = (RelativeLayout) view.findViewById(R.id.buttonLayout);
//            buttonLayout.setVisibility(View.GONE);
//
//            mLoaderDialogTitle.setText("Processing...");
//
//            Glide.with(getViewContext())
//                    .load(R.drawable.progressloader)
//                    .into(mLoaderDialogImage);
//        }
//    }

    private void init(View view) {
        if (isAdded()) {
            //Skycable
            getActivity().setTitle("SKYCABLE");
        }

        linearPlanItemLayout = (LinearLayout) view.findViewById(R.id.linearPlanItemLayout);
        txvName = (TextView) view.findViewById(R.id.txvName);
        txvAmount = (TextView) view.findViewById(R.id.txvAmount);
        imgPlanItem = (ImageView) view.findViewById(R.id.imgPlanItem);

        imgGenderArrowRight = (ImageView) view.findViewById(R.id.imgGenderArrowRight);

        camera = (ImageView) view.findViewById(R.id.camera);
        camera.setOnClickListener(this);
        gallery = (ImageView) view.findViewById(R.id.gallery);
        gallery.setOnClickListener(this);
        profileCameraLayout = (LinearLayout) view.findViewById(R.id.profileCameraLayout);
        profileCameraLayout.setOnClickListener(this);
        profileGalleryLayout = (LinearLayout) view.findViewById(R.id.profileGalleryLayout);
        profileGalleryLayout.setOnClickListener(this);
        nationalidlayout = (LinearLayout) view.findViewById(R.id.nationalidlayout);
        nationalIdImage = (ImageView) view.findViewById(R.id.nationalIdImage);

        camera2 = (ImageView) view.findViewById(R.id.camera2);
        camera2.setOnClickListener(this);
        gallery2 = (ImageView) view.findViewById(R.id.gallery2);
        gallery2.setOnClickListener(this);
        profileCameraLayout2 = (LinearLayout) view.findViewById(R.id.profileCameraLayout2);
        profileCameraLayout2.setOnClickListener(this);
        profileGalleryLayout2 = (LinearLayout) view.findViewById(R.id.profileGalleryLayout2);
        profileGalleryLayout2.setOnClickListener(this);
        nationalidlayout2 = (LinearLayout) view.findViewById(R.id.nationalidlayout2);
        nationalIdImage2 = (ImageView) view.findViewById(R.id.nationalIdImage2);

        edtFirstName = (EditText) view.findViewById(R.id.edtFirstName);
        edtLastName = (EditText) view.findViewById(R.id.edtLastName);
        edtCity = (EditText) view.findViewById(R.id.edtCity);
        edtZipCode = (EditText) view.findViewById(R.id.edtZipCode);
        edtGender = (EditText) view.findViewById(R.id.edtGender);
        edtGender.setOnClickListener(this);
        edtBirthDate = (EditText) view.findViewById(R.id.edtBirthDate);
        edtBirthDate.setOnClickListener(this);
        edtMobileNo = (EditText) view.findViewById(R.id.edtMobileNo);
        edtTelephoneNumber = (EditText) view.findViewById(R.id.edtTelephoneNumber);
        edtEmailAddress = (EditText) view.findViewById(R.id.edtEmailAddress);
        edtAddress = (EditText) view.findViewById(R.id.edtAddress);
        edtBillingAddress = (EditText) view.findViewById(R.id.edtBillingAddress);
        edtInstallationAddress = (EditText) view.findViewById(R.id.edtInstallationAddress);
        edtInstallationAddress.setBackgroundColor(ContextCompat.getColor(getViewContext(), R.color.color_CCCCCC));
        cbxBillingAddress = (CheckBox) view.findViewById(R.id.cbxBillingAddress);
        cbxBillingAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtBillingAddress.setText(edtAddress.getText().toString().trim());
                } else {
                    edtBillingAddress.setText("");
                }
            }
        });
        cbxInstallationAddress = (CheckBox) view.findViewById(R.id.cbxInstallationAddress);
        cbxInstallationAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtInstallationAddress.setText(edtAddress.getText().toString().trim());
                } else {
                    edtInstallationAddress.setText("");
                }
            }
        });
        btnProceed = (Button) view.findViewById(R.id.btnProceed);
        btnProceed.setEnabled(true);
        btnProceed.setOnClickListener(this);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //MAP ICONS
        imgAddress = (ImageView) view.findViewById(R.id.imgAddress);
        imgAddress.setEnabled(true);
        imgAddress.setOnClickListener(this);
        imgBillingAddress = (ImageView) view.findViewById(R.id.imgBillingAddress);
        imgBillingAddress.setEnabled(true);
        imgBillingAddress.setOnClickListener(this);
        imgInstallationAddress = (ImageView) view.findViewById(R.id.imgInstallationAddress);
        imgInstallationAddress.setEnabled(true);
        imgInstallationAddress.setOnClickListener(this);

        //FIELDS
        txvFirstName = (TextView) view.findViewById(R.id.txvFirstName);
        txvLastName = (TextView) view.findViewById(R.id.txvLastName);
        txvGender = (TextView) view.findViewById(R.id.txvGender);
        txvBirthDate = (TextView) view.findViewById(R.id.txvBirthDate);
        txvMobileNumber = (TextView) view.findViewById(R.id.txvMobileNumber);
        txvTelephoneNumber = (TextView) view.findViewById(R.id.txvTelephoneNumber);
        txvEmailAddress = (TextView) view.findViewById(R.id.txvEmailAddress);
        txvAddress = (TextView) view.findViewById(R.id.txvAddress);
        txvCity = (TextView) view.findViewById(R.id.txvCity);
        txvZipCode = (TextView) view.findViewById(R.id.txvZipCode);
        txvBillingAddress = (TextView) view.findViewById(R.id.txvBillingAddress);
        txvInstallationAddress = (TextView) view.findViewById(R.id.txvInstallationAddress);

        txvDescription = (TextView) view.findViewById(R.id.txvDescription);
        txvInstallationFee = (TextView) view.findViewById(R.id.txvInstallationFee);
        txvMonthlyFee = (TextView) view.findViewById(R.id.txvMonthlyFee);
        txvCashOut = (TextView) view.findViewById(R.id.txvCashOut);
        txvDiscount = (TextView) view.findViewById(R.id.txvDiscount);

        linearDiscountlayout = (LinearLayout) view.findViewById(R.id.linearDiscountlayout);

        //SETUP DIALOGS
//        setUpStatusDialog();
//
//        setupLoaderDialog();
//
        setUpConfirmDialog();

    }

    private void initData() {
        skycableDictionary = getArguments().getParcelable("SKYDICTIONARY");
        merchantid = PreferenceUtils.getStringPreference(getViewContext(), "skycablemerchantidnewapplication");
        mdb = new DatabaseHandler(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        imageUri = null;
        arrPlans = new ArrayList<>();
        skycableDictionaries = new ArrayList<>();
        skycableRegistrations = new ArrayList<>();
        skycableServiceAreasList = new ArrayList<>();
        skycableServiceAreaObj = null;
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "skycablebillcode");

        skycableServiceAreasList = getArguments().getParcelableArrayList("SKYSERVICEAREA");

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        txvDescription.setText(CommonFunctions.replaceEscapeData(skycableDictionary.getDescription()));
        txvInstallationFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getInstallationFee())));
        txvMonthlyFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getMonthlyFee())));
        txvCashOut.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getInitialCashout())));
        setUpDiscount();

//        linearPlanItemLayout.setBackgroundColor(Color.parseColor(CommonFunctions.parseJSON(skycableDictionary.getPlanStyle(), "background-color")));
//        txvName.setTextColor(Color.parseColor(CommonFunctions.parseJSON(skycableDictionary.getPlanStyle(), "color")));
//        txvAmount.setTextColor(Color.parseColor(CommonFunctions.parseJSON(skycableDictionary.getPlanStyle(), "color")));
        txvName.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", skycableDictionary.getPlanName()));
        txvAmount.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Light.ttf", "₱" + CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getMonthlyFee()))));
        Glide.with(mContext)
                .asBitmap()
                .load(skycableDictionary.getPlanImgUrl())
                .apply(new RequestOptions()
                        .fitCenter())
                .into(new BaseTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        imgPlanItem.setImageBitmap(resource);
                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(CommonFunctions.getScreenWidth(mContext), CommonFunctions.getScreenHeight(mContext));
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        linearPlanItemLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorwhite));
                    }
                });


        double discount = (skycableDictionary.getMonthlyFee() * skycableDictionary.getDiscountPercentage()) + skycableDictionary.getDiscountBase();

        if (discount > 0) {
            linearDiscountlayout.setVisibility(View.VISIBLE);
        } else {
            linearDiscountlayout.setVisibility(View.GONE);
        }

    }

    private void setUpDiscount() {
        String discount = CommonFunctions.discountFormatter(String.valueOf((skycableDictionary.getMonthlyFee() * skycableDictionary.getDiscountPercentage()) + skycableDictionary.getDiscountBase()));
        txvDiscount.setText(!discount.contains(".") ? discount : discount.replaceAll("0*$", "").replaceAll("\\.$", ""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {

                new MaterialDialog.Builder(getViewContext())
                        .content("You are about to leave your registration.")
                        .cancelable(false)
                        .negativeText("Cancel")
                        .positiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                ((SkyCableActivity) getViewContext()).displayView(16, null);

                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {

                            }
                        })
                        .show();

                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void showGenderOptionsDialog() {
        new MaterialDialog.Builder(getViewContext())
                .title("Select Gender")
                .items(R.array.arr_gender)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/

                        edtGender.setText(text.toString().toUpperCase());

                        return true;
                    }
                })
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtGender: {
                showGenderOptionsDialog();
                break;
            }
            case R.id.edtBirthDate: {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                edtBirthDate.setText(date);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "dialog");
                break;
            }
            case R.id.btnProceed: {

                if (edtFirstName.getText().toString().trim().length() > 0 &&
                        edtLastName.getText().toString().trim().length() > 0 &&
                        edtCity.getText().toString().trim().length() > 0 &&
                        edtZipCode.getText().toString().trim().length() > 0 &&
                        edtGender.getText().toString().trim().length() > 0 &&
                        edtBirthDate.getText().toString().trim().length() > 0 &&
                        edtMobileNo.getText().toString().trim().length() > 0 &&
                        edtEmailAddress.getText().toString().trim().length() > 0 &&
                        edtAddress.getText().toString().trim().length() > 0 &&
                        edtBillingAddress.getText().toString().trim().length() > 0 &&
                        edtInstallationAddress.getText().toString().trim().length() > 0
//                        && imageidUri != null
//                        && imagepobUri != null
                        ) {

                    mobileno = getMobileNumber(edtMobileNo.getText().toString().trim());
                    if (mobileno.equals("INVALID")) {

                        txvMobileNumber.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
                        edtMobileNo.setError("Invalid Mobile Number");
                        edtMobileNo.requestFocus();

                    } else {

                        if (skycableServiceAreasList.size() == 0) {
                            showError("SKYCABLE Service Area not set. Please contact Administrator.");
                        } else {

                            LatLng latLng = getLatitudeLongitudeFromAddress(edtInstallationAddress.getText().toString().trim());

                            installationAddressLatitude = latLng.latitude;
                            installationAddressLongitude = latLng.longitude;

//                            if (checkIfAddressIsWithinRadius(installationAddressLatitude, installationAddressLongitude, Double.valueOf(skycableServiceAreaObj.getLatitude()), Double.valueOf(skycableServiceAreaObj.getLongitude()), Double.valueOf(skycableServiceAreaObj.getRadius()))) {
                            btnProceed.setEnabled(false);

                            txvInstallationAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
                            edtInstallationAddress.setError(null);

                            isRegister = false;
                            isS3Key = false;
                            isServiceCharge = false;
                            isConfigAvailable = true;
                            getSession();

//                            } else {
//
//                                txvInstallationAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
//                                edtInstallationAddress.setError("Sorry, entered installation address is not within our area of service. Please try another address.");
//                                edtInstallationAddress.requestFocus();
//
//                            }

                        }

                    }

                } else {

//                    if (imageidUri == null) {
//                        showError("Please take a picture or browse from your library for your Identification Number image.");
//                    } else if (imagepobUri == null) {
//                        showError("Please take a picture or browse from your library for your Proof of Billing image.");
//                    } else {
                    checkFieldErrors();
//                    }

                }

                break;
            }
            case R.id.txvCloseDialog: {
//                mDialog.dismiss();
                getActivity().finish();
                SkyCableActivity.start(getViewContext(), 1, null);

                break;
            }
            case R.id.imgAddress: {

                if (isAirplaneModeOn(getViewContext())) {
                    showError("Airplane mode is enabled. Please disable Airplane mode and enable GPS or Internet to proceed.");
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                        if (isGPSModeOn()) {

                            imgAddress.setEnabled(false);

                            LatLng latLng = getLatitudeLongitudeFromAddress(edtAddress.getText().toString().trim());

                            Bundle args = new Bundle();
                            args.putString("latitude", String.valueOf(latLng.latitude));
                            args.putString("longitude", String.valueOf(latLng.longitude));
                            args.putBoolean("isServiceArea", false);

                            Intent intent = new Intent(getViewContext(), SetupDeliveryAddressActivity.class);
                            intent.putExtra("index", 1);
                            intent.putExtra("args", args);
                            startActivityForResult(intent, 1);

                        } else {
                            showGPSDisabledAlertToUser();
                        }
                    } else {
                        showError(getString(R.string.generic_internet_error_message));
                    }
                }

                break;
            }
            case R.id.imgBillingAddress: {

                if (isAirplaneModeOn(getViewContext())) {
                    showError("Airplane mode is enabled. Please disable Airplane mode and enable GPS or Internet to proceed.");
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                        if (isGPSModeOn()) {

                            imgBillingAddress.setEnabled(false);

                            LatLng latLng = getLatitudeLongitudeFromAddress(edtBillingAddress.getText().toString().trim());

                            Bundle args = new Bundle();
                            args.putString("latitude", String.valueOf(latLng.latitude));
                            args.putString("longitude", String.valueOf(latLng.longitude));
                            args.putBoolean("isServiceArea", false);

                            Intent intent = new Intent(getViewContext(), SetupDeliveryAddressActivity.class);
                            intent.putExtra("index", 1);
                            intent.putExtra("args", args);
                            startActivityForResult(intent, 2);

                        } else {
                            showGPSDisabledAlertToUser();
                        }
                    } else {
                        showError(getString(R.string.generic_internet_error_message));
                    }
                }

                break;
            }
            case R.id.imgInstallationAddress: {

//                if (skycableServiceAreasList == null) {
//                    showError("Skycable Service Area not set. Please contact Administrator.");
//                } else {

                if (isAirplaneModeOn(getViewContext())) {
                    showError("Airplane mode is enabled. Please disable Airplane mode and enable GPS or Internet to proceed.");
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                        if (isGPSModeOn()) {

                            imgInstallationAddress.setEnabled(false);

                            LatLng latLng = getLatitudeLongitudeFromAddress(edtInstallationAddress.getText().toString().trim());

                            Bundle args = new Bundle();
                            args.putString("latitude", String.valueOf(latLng.latitude));
                            args.putString("longitude", String.valueOf(latLng.longitude));
                            args.putParcelableArrayList("SKYCABLEAREALIST", skycableServiceAreasList);
//                                args.putString("servicearealatitude", skycableServiceAreasList.getLatitude());
//                                args.putString("servicearealongitude", skycableServiceAreasList.getLongitude());
//                                args.putString("radius", skycableServiceAreasList.getRadius());
                            args.putBoolean("isServiceArea", true);
                            args.putBoolean("isShowMapDescription", true);

                            installationAddressLatitude = latLng.latitude;
                            installationAddressLongitude = latLng.longitude;

                            Intent intent = new Intent(getViewContext(), SetupDeliveryAddressActivity.class);
                            intent.putExtra("index", 1);
                            intent.putExtra("args", args);
                            startActivityForResult(intent, 3);

                        } else {
                            showGPSDisabledAlertToUser();
                        }
                    } else {
                        showError(getString(R.string.generic_internet_error_message));
                    }
                }

//                }
                break;
            }
            case R.id.profileCameraLayout: {
                isProofOfBilling = false;
                cameraIntent();
                break;
            }
            case R.id.profileGalleryLayout: {
                isProofOfBilling = false;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_FILE);
                break;
            }
            case R.id.camera: {
                isProofOfBilling = false;
                cameraIntent();
                break;
            }
            case R.id.gallery: {
                isProofOfBilling = false;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_FILE);
                break;
            }
            case R.id.profileCameraLayout2: {
                isProofOfBilling = true;
                cameraIntent();
                break;
            }
            case R.id.profileGalleryLayout2: {
                isProofOfBilling = true;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_FILE);
                break;
            }
            case R.id.camera2: {
                isProofOfBilling = true;
                cameraIntent();
                break;
            }
            case R.id.gallery2: {
                isProofOfBilling = true;
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_FILE);
                break;
            }
            case R.id.txvConfirmCloseDialog: {
                mConfirmDialog.dismiss();
                setUpProgressLoaderDismissDialog();
                break;
            }
            case R.id.txvConfirmProceed: {
                mConfirmDialog.dismiss();

                imageidfilename = userid + "-" + Calendar.getInstance().getTimeInMillis() + "-skycable-imageid.jpg";
                imagepobfilename = userid + "-" + Calendar.getInstance().getTimeInMillis() + "-skycable-imagepob.jpg";

                isID = true;
                isPOB = false;
                isRegister = false;
                isS3Key = false;
                isUpload = true;
                isServiceCharge = false;
                getSession();

//                isRegister = false;
//                isS3Key = true;
//                isServiceCharge = false;
//                getSession();

//                isRegister = true;
//                isS3Key = false;
//                isServiceCharge = false;
//                getSession();

                break;
            }
        }
    }

    private void checkFieldErrors() {

        if (edtFirstName.getText().toString().trim().length() == 0) {
            txvFirstName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtFirstName.setError("This field can not be blank");
            edtFirstName.requestFocus();
        } else {
            txvFirstName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtFirstName.setError(null);
        }

        if (edtLastName.getText().toString().trim().length() == 0) {
            txvLastName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtLastName.setError("This field can not be blank");
            edtLastName.requestFocus();
        } else {
            txvLastName.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtLastName.setError(null);
        }

        if (edtCity.getText().toString().trim().length() == 0) {
            txvCity.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtCity.setError("This field can not be blank");
            edtCity.requestFocus();
        } else {
            txvCity.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtCity.setError(null);
        }

        if (edtZipCode.getText().toString().trim().length() == 0) {
            txvZipCode.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtZipCode.setError("This field can not be blank");
            edtZipCode.requestFocus();
        } else {
            txvZipCode.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtZipCode.setError(null);
        }

        if (edtGender.getText().toString().trim().length() == 0) {
            txvGender.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtGender.setError("This field can not be blank");
            edtGender.requestFocus();
            imgGenderArrowRight.setVisibility(View.GONE);
        } else {
            txvGender.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtGender.setError(null);
            imgGenderArrowRight.setVisibility(View.VISIBLE);
        }

        if (edtBirthDate.getText().toString().trim().length() == 0) {
            txvBirthDate.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtBirthDate.setError("This field can not be blank");
            edtBirthDate.requestFocus();
        } else {
            txvBirthDate.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtBirthDate.setError(null);
        }

        if (edtMobileNo.getText().toString().trim().length() == 0) {
            txvMobileNumber.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtMobileNo.setError("This field can not be blank");
            edtMobileNo.requestFocus();
        } else {
            txvMobileNumber.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtMobileNo.setError(null);
        }

        if (edtEmailAddress.getText().toString().trim().length() == 0) {
            txvEmailAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtEmailAddress.setError("This field can not be blank");
            edtEmailAddress.requestFocus();
        } else if (!CommonFunctions.isValidEmail(edtEmailAddress.getText().toString().trim())) {
            txvEmailAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtEmailAddress.setError("Please input a valid email address.");
            edtEmailAddress.requestFocus();
        } else {
            txvEmailAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtEmailAddress.setError(null);
        }

        if (edtAddress.getText().toString().trim().length() == 0) {
            txvAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtAddress.setError("This field can not be blank");
            edtAddress.requestFocus();
        } else {
            txvAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtAddress.setError(null);
        }

        if (edtBillingAddress.getText().toString().trim().length() == 0) {
            txvBillingAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtBillingAddress.setError("This field can not be blank");
            edtBillingAddress.requestFocus();
        } else {
            txvBillingAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtBillingAddress.setError(null);
        }

        if (edtInstallationAddress.getText().toString().trim().length() == 0) {
            txvInstallationAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
            edtInstallationAddress.setError("This field can not be blank");
            edtInstallationAddress.requestFocus();
        } else {
            txvInstallationAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
            edtInstallationAddress.setError(null);
        }

    }

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
            setUpProgressLoader();

            if (isRegister) {
//                mLoaderDialog.show();
//                mLoaderDialogMessage.setText("Processing request, Please wait...");
                setUpProgressLoaderMessageDialog("Processing request, Please wait...");
                mTvFetching.setText("Sending request..");
            } else if (isUpload) {
//                mLoaderDialog.show();
//                mLoaderDialogMessage.setText("Uploading pictures, Please wait...");
                setUpProgressLoaderMessageDialog("Uploading pictures, Please wait...");
                mTvFetching.setText("Sending request..");
            } else if (isS3Key) {
//                mLoaderDialog.show();
//                mLoaderDialogMessage.setText("Fetching Credentials, Please wait...");
                setUpProgressLoaderMessageDialog("Fetching Credentials, Please wait...");
                mTvFetching.setText("Sending request..");
            }

            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);
            isLoading = true;

//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isRegister) {
                registerNewSkycableAccount(registerNewSkycableAccountSession);
            } else if (isS3Key) {
                getS3key(getS3keySession);
            } else if (isServiceCharge) {
                getSkycablePPVCustomerServiceCharge(getSkycablePPVCustomerServiceChargeSession);
            } else if (isConfigAvailable) {
                checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
            } else if (isUpload) {
                dialogName = "Valid I.D.";
                imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                        "/" + imageidfilename);
                createFile(getViewContext(), imageidUri, imageFile);
//                        mLoaderDialogMessage.setText("Uploading " + dialogName);
                setUpProgressLoaderMessageDialog("Uploading " + dialogName);
                mTvFetching.setText("Uploading Pictures");
                uploadFile(imageFile);
            }

        } else {
//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
            setUpProgressLoaderDismissDialog();
            btnProceed.setEnabled(true);
            mLlLoader.setVisibility(View.GONE);
            showNoInternetConnection(true);
            showError(getString(R.string.generic_internet_error_message));
        }
    }

//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//
//                    if (isRegister || isS3Key || isUpload) {
////                        if (mLoaderDialog != null) {
////                            mLoaderDialog.dismiss();
////                        }
//                        setUpProgressLoaderDismissDialog();
//                    }
//
//                    btnProceed.setEnabled(true);
//                    isS3Key = false;
//                    isRegister = false;
//                    isServiceCharge = false;
//                    isLoading = false;
//                    isConfigAvailable = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//
//                    if (isRegister || isS3Key || isUpload) {
////                        if (mLoaderDialog != null) {
////                            mLoaderDialog.dismiss();
////                        }
//                        setUpProgressLoaderDismissDialog();
//                    }
//
//                    btnProceed.setEnabled(true);
//                    isS3Key = false;
//                    isRegister = false;
//                    isServiceCharge = false;
//                    isLoading = false;
//                    isConfigAvailable = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//
//                    if (isRegister || isS3Key || isUpload) {
////                        if (mLoaderDialog != null) {
////                            mLoaderDialog.dismiss();
////                        }
//                        setUpProgressLoaderDismissDialog();
//                    }
//
//                    btnProceed.setEnabled(true);
//                    isS3Key = false;
//                    isRegister = false;
//                    isServiceCharge = false;
//                    isLoading = false;
//                    isConfigAvailable = false;
//                    mLlLoader.setVisibility(View.GONE);
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isRegister) {
//                        registerNewSkycableAccount(registerNewSkycableAccountSession);
//                    } else if (isS3Key) {
//                        getS3key(getS3keySession);
//                    } else if (isServiceCharge) {
//                        getSkycablePPVCustomerServiceCharge(getSkycablePPVCustomerServiceChargeSession);
//                    } else if (isConfigAvailable) {
//                        checkIfConfigIsAvailable(checkIfConfigIsAvailableSession);
//                    } else if (isUpload) {
//                        dialogName = "Valid I.D.";
//                        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//                                "/" + imageidfilename);
//                        createFile(getViewContext(), imageidUri, imageFile);
////                        mLoaderDialogMessage.setText("Uploading " + dialogName);
//                        setUpProgressLoaderMessageDialog("Uploading " + dialogName);
//                        mTvFetching.setText("Uploading Pictures...");
//                        uploadFile(imageFile);
//                    }
//
//                }
//            } else {
//
//                if (isRegister || isS3Key || isUpload) {
////                    if (mLoaderDialog != null) {
////                        mLoaderDialog.dismiss();
////                    }
//                    setUpProgressLoaderDismissDialog();
//                }
//
//                btnProceed.setEnabled(true);
//                isS3Key = false;
//                isRegister = false;
//                isServiceCharge = false;
//                isLoading = false;
//                isConfigAvailable = false;
//                mLlLoader.setVisibility(View.GONE);
//                showNoInternetConnection(true);
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//
//            if (isRegister) {
////                if (mLoaderDialog != null) {
////                    mLoaderDialog.dismiss();
////                }
//                setUpProgressLoaderDismissDialog();
//            }
//
//            btnProceed.setEnabled(true);
//            isLoading = false;
//            isS3Key = false;
//            isServiceCharge = false;
//            isRegister = false;
//            isConfigAvailable = false;
//            showNoInternetConnection(true);
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getS3key(Callback<GetS3keyResponse> getS3keyCallback) {
        Call<GetS3keyResponse> getS3key = RetrofitBuild.getS3keyService(getViewContext())
                .getAmzCredCall(imei,
                        CommonFunctions.getSha1Hex(imei + CommonVariables.version),
                        CommonVariables.version,
                        "GKSERVICES");
        getS3key.enqueue(getS3keyCallback);
    }

    private final Callback<GetS3keyResponse> getS3keySession = new Callback<GetS3keyResponse>() {

        @Override
        public void onResponse(Call<GetS3keyResponse> call, Response<GetS3keyResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                List<AmazonCredentials> amzlist = response.body().getAmazonCredentials();

                bucketName = response.body().getBucketName();

                if (bucketName.trim().length() > 0 && amzlist.size() > 0) {

                    amazonCredentials = amzlist.get(0);

                    imageidfilename = userid + "-" + Calendar.getInstance().getTimeInMillis() + "-skycable-imageid.jpg";
                    imagepobfilename = userid + "-" + Calendar.getInstance().getTimeInMillis() + "-skycable-imagepob.jpg";

                    imageidurl = "https://s3-us-west-1.amazonaws.com/" + bucketName + "/" + imageidfilename;
                    imagepoburl = "https://s3-us-west-1.amazonaws.com/" + bucketName + "/" + imagepobfilename;

                    isS3Key = false;

                    uploadImagetoAWS(imageidUri, imageidfilename, false, imageidurl);
                } else {
                    showError("Something went wrong during upload.");
                }

            } else {
                btnProceed.setEnabled(true);
            }
        }

        @Override
        public void onFailure(Call<GetS3keyResponse> call, Throwable t) {

//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
            setUpProgressLoaderDismissDialog();

            mLlLoader.setVisibility(View.GONE);
            btnProceed.setEnabled(true);
            isLoading = false;
            isS3Key = false;
            isRegister = false;
            showError("Error in connection. Please contact support.");
        }
    };

    private void registerNewSkycableAccount(Callback<RegisterNewSkycableAccountResponse> registerNewSkycableAccountCallback) {

        //imageidurl
        //imagepoburl
        Call<RegisterNewSkycableAccountResponse> registernewskycableaccount = RetrofitBuild.registerNewSkycableAccountService(getViewContext())
                .registerNewSkycableAccountCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        edtFirstName.getText().toString().trim(),
                        edtLastName.getText().toString().trim(),
                        edtGender.getText().toString().trim(),
                        edtBirthDate.getText().toString().trim(),
                        edtAddress.getText().toString().trim(),
                        edtCity.getText().toString().trim(),
                        longitude,
                        latitude,
                        edtZipCode.getText().toString().trim(),
                        edtBillingAddress.getText().toString().trim(),
                        edtInstallationAddress.getText().toString().trim(),
                        edtEmailAddress.getText().toString().trim(),
                        mobileno,
                        edtTelephoneNumber.getText().toString().trim().length() > 0 ? edtTelephoneNumber.getText().toString().trim() : ".",
                        skycableDictionary.getPlanID(),
                        merchantid,
                        imageidurl,
                        imagepoburl,
                        skyServiceAreaID);
        registernewskycableaccount.enqueue(registerNewSkycableAccountCallback);
    }

    private final Callback<RegisterNewSkycableAccountResponse> registerNewSkycableAccountSession = new Callback<RegisterNewSkycableAccountResponse>() {

        @Override
        public void onResponse(Call<RegisterNewSkycableAccountResponse> call, Response<RegisterNewSkycableAccountResponse> response) {

//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }


            btnProceed.setEnabled(true);
            isS3Key = false;
            isRegister = false;
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
//                    mDialog.show();
//                    txvMessage.setText(response.body().getMessage());
                    setUpProgressLoaderDismissDialog();
                    showGlobalDialogs(response.body().getMessage(), "close", ButtonTypeEnum.SINGLE,
                            true, false, DialogTypeEnum.IMAGE);

                } else {
                    setUpProgressLoaderDismissDialog();
//                    showError(response.body().getMessage());
                    setUpProgressLoaderDismissDialog();
                    showGlobalDialogs(response.body().getMessage(), "close", ButtonTypeEnum.SINGLE,
                            true, false, DialogTypeEnum.FAILED);
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<RegisterNewSkycableAccountResponse> call, Throwable t) {
//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
            setUpProgressLoaderDismissDialog();

            btnProceed.setEnabled(true);
            isS3Key = false;
            isRegister = false;
            isLoading = false;
            mLlLoader.setVisibility(View.GONE);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
        }
    };

    private void uploadImagetoAWS(final Uri imageUri, final String filename, final boolean isFinish, final String imageurl) {

        isError = false;

        if (imageUri != null) {
            filePath = null;

            if (isFinish) {
                dialogName = "Proof of Billing image";
            } else {
                dialogName = "Valid I.D.";
            }

            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "/" + filename);

            createFile(getViewContext(), imageUri, imageFile);

            @SuppressLint("StaticFieldLeak") AsyncTask<String, String, String> uploadTask = new AsyncTask<String, String, String>() {

                @Override
                protected void onPreExecute() {

//                    mLoaderDialogMessage.setText("Uploading " + dialogName);
                    setUpProgressLoaderMessageDialog("Uploading " + dialogName);
                    mTvFetching.setText("Please wait...");

                    //showProgressDialog("Uploading " + dialogName, "Please wait...");
                }

                @Override
                protected String doInBackground(String... strings) {

                    try {

                        AmazonS3Client s3Client1 = new AmazonS3Client(new BasicAWSCredentials(amazonCredentials.getAPIKey(), amazonCredentials.getAPISecretKey()));
                        PutObjectRequest por = new PutObjectRequest(
                                bucketName,
                                filename,
                                imageFile);

                        //making the object Public
                        por.setCannedAcl(CannedAccessControlList.PublicRead);
                        s3Client1.putObject(por);

                    } catch (Exception e) {
                        isError = true;
                        e.printStackTrace();
                    }

                    return imageurl;
                }

                @Override
                protected void onPostExecute(String s) {

//                    hideProgressDialog();

                    if (isFinish) {
                        if (isError) {

//                            if (mLoaderDialog != null) {
//                                mLoaderDialog.dismiss();
//                            }
                            setUpProgressLoaderDismissDialog();

                            showError("Something went wrong during upload.");
                        } else {
                            isS3Key = false;
                            isRegister = true;
                            isServiceCharge = false;
                            getSession();
                        }
                    } else {
                        if (isError) {
//                            if (mLoaderDialog != null) {
//                                mLoaderDialog.dismiss();
//                            }
                            setUpProgressLoaderDismissDialog();

                            showError("Something went wrong during upload.");
                        } else {
                            uploadImagetoAWS(imagepobUri, imagepobfilename, true, imagepoburl);
                        }
                    }
                }
            };
            uploadTask.execute((String[]) null);

        } else {

//            if (mLoaderDialog != null) {
////                mLoaderDialog.dismiss();
////            }
            setUpProgressLoaderDismissDialog();

            showError("Something went wrong during upload.");
        }

    }

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

//    private void setUpStatusDialog() {
//        mDialog = new MaterialDialog.Builder(getViewContext())
//                .cancelable(false)
//                .customView(R.layout.dialog_skycable_link_account_status, false)
//                .build();
//        View view = mDialog.getCustomView();
//
//        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvCloseDialog);
//        txvCloseDialog.setOnClickListener(this);
//        txvMessage = (TextView) view.findViewById(R.id.txvMessage);
//    }

    private LatLng getLatitudeLongitudeFromAddress(String address) {
        Geocoder geocoder = new Geocoder(getViewContext());
        GPSTracker tracker = new GPSTracker(getViewContext());

        if (address.trim().length() > 0) {
            List<Address> addresses = new ArrayList<>();
            try {
                addresses = geocoder.getFromLocationName(address, 1);
                if (addresses.size() > 0) {
                    getlatitude = addresses.get(0).getLatitude();
                    getlongitude = addresses.get(0).getLongitude();
                } else {
                    getlatitude = tracker.getLatitude();
                    getlongitude = tracker.getLongitude();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            getlatitude = tracker.getLatitude();
            getlongitude = tracker.getLongitude();
        }

        return new LatLng(getlatitude, getlongitude);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.debug("antonhttp", "FRAGMENT requestCode = " + requestCode);

        Logger.debug("antonhttp", "FRAGMENT resultCode = " + resultCode);

        Logger.debug("antonhttp", "data: " + data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE) {
                onImageResult(data.getData());
            } else if (requestCode == SKY_REQUEST_CAMERA) {
                onImageResult(fileUri);
                fileUri = null;
            } else {

                Address checkaddress = null;

                Bundle args = data.getExtras();

                if (args != null) {
                    checkaddress = args.getParcelable("MarkerAddress");
                }

                if (checkaddress != null) {
                    double checklat = checkaddress.getLatitude();
                    double checklong = checkaddress.getLongitude();

                    latitude = String.valueOf(checklat);
                    longitude = String.valueOf(checklong);

                }

                if (requestCode == 1) {
                    edtAddress.setText(getMarkerAddress(checkaddress));

                    if (checkaddress.getLocality() != null) {
                        if (checkaddress.getLocality().trim().length() > 0 && !checkaddress.getLocality().equals("null")) {
                            edtCity.setText(checkaddress.getLocality());
                        }
                    }

                    if (checkaddress.getPostalCode() != null) {
                        if (String.valueOf(checkaddress.getPostalCode()).trim().length() > 0 && !String.valueOf(checkaddress.getPostalCode()).equals("null")) {
                            edtZipCode.setText(String.valueOf(checkaddress.getPostalCode()));
                        }
                    }


                } else if (requestCode == 2) {
                    edtBillingAddress.setText(getMarkerAddress(checkaddress));
                } else if (requestCode == 3) {

                    skycableServiceAreaObj = args.getParcelable("skycableServiceAreaObj");

//                edtInstallationAddress.setText(getMarkerAddress(checkaddress));
                    edtInstallationAddress.setText(args.getString("placeAddress"));

                    LatLng latLng = getLatitudeLongitudeFromAddress(edtInstallationAddress.getText().toString().trim());

                    if (checkIfAddressIsWithinRadius(latLng.latitude, latLng.longitude, Double.valueOf(skycableServiceAreaObj.getLatitude()), Double.valueOf(skycableServiceAreaObj.getLongitude()), Double.valueOf(skycableServiceAreaObj.getRadius()))) {

                        txvInstallationAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
                        edtInstallationAddress.setError(null);
                        skyServiceAreaID = skycableServiceAreaObj.getServiceAreaID();

                    } else {

                        txvInstallationAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.colorsilver));
                        edtInstallationAddress.setError(null);
                        skyServiceAreaID = "-";
//                    txvInstallationAddress.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_FF0000));
//                    edtInstallationAddress.setError("Sorry, entered installation address is not within our area of service. Please try another address.");
//                    edtInstallationAddress.requestFocus();

                    }

                }

            }

        }

    }

    public float getDistance(double latitude, double longitude, double xlatitude, double xlongitude) {
        float distance;
        Location startpoint = new Location("startpoint");
        startpoint.setLatitude(latitude);
        startpoint.setLongitude(longitude);

        try {
            Location endpoint = new Location("newlocation");
            endpoint.setLatitude(xlatitude);
            endpoint.setLongitude(xlongitude);
            distance = startpoint.distanceTo(endpoint) / 1000;
        } catch (NumberFormatException e) {
            distance = 0;
            e.printStackTrace();
        }

        return distance;
    }

    private String getMarkerAddress(Address address) {
        String display_address = "";
        if (address != null) {

            display_address += address.getAddressLine(0) + ", ";

            for (int i = 1; i < address.getMaxAddressLineIndex(); i++) {
                display_address += address.getAddressLine(i) + ", ";
            }

            display_address = display_address.substring(0, display_address.length() - 2);
        }

        return display_address;
    }

    private void cameraIntent() {

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        fileUri = Uri.fromFile(getOutputMediaFile(1));

        Logger.debug("antonhttp", "fileUri: " + fileUri);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, SKY_REQUEST_CAMERA);

//        List<ResolveInfo> resolvedIntentActivities = getViewContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
//            String packageName = resolvedIntentInfo.activityInfo.packageName;
//
//            getViewContext().grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//
//        if (intent.resolveActivity(getViewContext().getPackageManager()) != null) {
//
//        }

    }

    private void onImageResult(Uri uri) {

        Logger.debug("antonhttp", "onImageResult uri: " + uri);

        if (isProofOfBilling) {

            imagepobUri = uri;

            profileCameraLayout2.setVisibility(View.GONE);
            profileGalleryLayout2.setVisibility(View.GONE);
            nationalidlayout2.setVisibility(View.VISIBLE);

            Glide.with(getViewContext())
                    .asBitmap()
                    .load(uri)
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            nationalIdImage2.setImageBitmap(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(CommonFunctions.getScreenWidthPixel(getViewContext()), 200);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });


        } else {

            imageidUri = uri;

            profileCameraLayout.setVisibility(View.GONE);
            profileGalleryLayout.setVisibility(View.GONE);
            nationalidlayout.setVisibility(View.VISIBLE);

            Glide.with(getViewContext())
                    .asBitmap()
                    .load(uri)
                    .apply(new RequestOptions()
                            .fitCenter())
                    .into(new BaseTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            nationalIdImage.setImageBitmap(resource);
                        }

                        @Override
                        public void getSize(SizeReadyCallback cb) {
                            cb.onSizeReady(CommonFunctions.getScreenWidthPixel(getViewContext()), 200);
                        }

                        @Override
                        public void removeCallback(SizeReadyCallback cb) {

                        }
                    });

        }

    }

    private void createFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSkycablePPVCustomerServiceCharge(Callback<GetSkycablePPVCustomerServiceChargeResponse> getSkycablePPVCustomerServiceChargeCallback) {
        Call<GetSkycablePPVCustomerServiceChargeResponse> getsycableppvsc = RetrofitBuild.getSkycablePPVCustomerServiceChargeService(getViewContext())
                .getSkycablePPVCustomerServiceChargeCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        String.valueOf(skycableDictionary.getMonthlyFee()),
                        "Registration");
        getsycableppvsc.enqueue(getSkycablePPVCustomerServiceChargeCallback);
    }

    private final Callback<GetSkycablePPVCustomerServiceChargeResponse> getSkycablePPVCustomerServiceChargeSession = new Callback<GetSkycablePPVCustomerServiceChargeResponse>() {

        @Override
        public void onResponse(Call<GetSkycablePPVCustomerServiceChargeResponse> call, Response<GetSkycablePPVCustomerServiceChargeResponse> response) {
            mLlLoader.setVisibility(View.GONE);
            ResponseBody errorBody = response.errorBody();
            isServiceCharge = false;
            btnProceed.setEnabled(true);

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    //9-13-2018
                    //UPDATES
                    // *** Remove Monthly Fee in the computation as per miss Sheila
                    //10-10-2018
                    // *** Remove Initial Cashout in Total Amount

                    mConfirmDialog.show();

                    txvConfirmMonthlyFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getMonthlyFee())));
                    txvConfirmInstallationFee.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getInstallationFee())));
                    txvConfirmServiceCharge.setText(CommonFunctions.currencyFormatter(String.valueOf(response.body().getCustomerServiceCharge())));

//                    double discount = (skycableDictionary.getMonthlyFee() * skycableDictionary.getDiscountPercentage()) + skycableDictionary.getDiscountBase();
                    double discount = skycableDictionary.getDiscountPercentage() + skycableDictionary.getDiscountBase();
                    txvConfirmDiscount.setText("(" + CommonFunctions.currencyFormatter(String.valueOf(discount)) + ")");

                    txvConfirmInitialCashout.setText(CommonFunctions.currencyFormatter(String.valueOf(skycableDictionary.getInitialCashout())));

//                    double totalamount = (skycableDictionary.getMonthlyFee() + skycableDictionary.getInstallationFee() + skycableDictionary.getInitialCashout() + Double.valueOf(response.body().getCustomerServiceCharge())) - discount;
//                    double totalamount = (skycableDictionary.getInstallationFee() + skycableDictionary.getInitialCashout() + Double.valueOf(response.body().getCustomerServiceCharge())) - discount;
                    double totalamount = (skycableDictionary.getInstallationFee() + Double.valueOf(response.body().getCustomerServiceCharge())) - discount;
                    txvConfirmTotalAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamount)));

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetSkycablePPVCustomerServiceChargeResponse> call, Throwable t) {
            isServiceCharge = false;
            mLlLoader.setVisibility(View.GONE);
            btnProceed.setEnabled(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void setUpConfirmDialog() {
        mConfirmDialog = new MaterialDialog.Builder(getViewContext())
                .cancelable(false)
                .customView(R.layout.dialog_confirm_skycable_new_application, true)
                .build();
        View view = mConfirmDialog.getCustomView();
        TextView txvCloseDialog = (TextView) view.findViewById(R.id.txvConfirmCloseDialog);
        txvCloseDialog.setOnClickListener(this);
        TextView txvProceed = (TextView) view.findViewById(R.id.txvConfirmProceed);
        txvProceed.setOnClickListener(this);

        txvConfirmMonthlyFee = (TextView) view.findViewById(R.id.txvConfirmMonthlyFee);
        txvConfirmInstallationFee = (TextView) view.findViewById(R.id.txvConfirmInstallationFee);
        txvConfirmServiceCharge = (TextView) view.findViewById(R.id.txvConfirmServiceCharge);
        txvConfirmDiscount = (TextView) view.findViewById(R.id.txvConfirmDiscount);
        txvConfirmTotalAmount = (TextView) view.findViewById(R.id.txvConfirmTotalAmount);
        txvConfirmInitialCashout = (TextView) view.findViewById(R.id.txvConfirmInitialCashout);
    }

    private void checkIfConfigIsAvailable(Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableCallback) {
        Call<CheckIfConfigIsAvailableResponse> checkconfig = RetrofitBuild.checkIfConfigIsAvailableService(getViewContext())
                .checkIfConfigIsAvailableCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        "New Application");
        checkconfig.enqueue(checkIfConfigIsAvailableCallback);
    }

    private final Callback<CheckIfConfigIsAvailableResponse> checkIfConfigIsAvailableSession = new Callback<CheckIfConfigIsAvailableResponse>() {

        @Override
        public void onResponse(Call<CheckIfConfigIsAvailableResponse> call, Response<CheckIfConfigIsAvailableResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mLlLoader.setVisibility(View.GONE);
            isConfigAvailable = false;

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    isRegister = false;
                    isS3Key = false;
                    isServiceCharge = true;
                    getSession();

                } else {
                    btnProceed.setEnabled(true);
                    showError(response.body().getMessage());
                }
            } else {
                btnProceed.setEnabled(true);
                showError();
            }

        }

        @Override
        public void onFailure(Call<CheckIfConfigIsAvailableResponse> call, Throwable t) {
            mLlLoader.setVisibility(View.GONE);
            isConfigAvailable = false;
            btnProceed.setEnabled(true);
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void uploadFile(File file) {

        RequestBody requestFile = RequestBody.create(
                MediaType.parse("image/*"), file);
        MultipartBody.Part bodyFile = MultipartBody.Part.createFormData(
                "upload_file", file.getName(), requestFile);

        RequestBody bodyBorrowerID = RequestBody.create(MediaType.parse("text/plain"), borrowerid);
        RequestBody bodyIMEI = RequestBody.create(MediaType.parse("text/plain"), imei);
        RequestBody bodyBucketName = RequestBody.create(MediaType.parse("text/plain"), CommonVariables.BUCKETNAME);
        RequestBody bodyUserID = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody bodySession = RequestBody.create(MediaType.parse("text/plain"), sessionid);
        RequestBody bodyAuthCode = RequestBody.create(MediaType.parse("text/plain"), CommonFunctions.getSha1Hex(imei + userid + sessionid));
        RequestBody bodyCommand = RequestBody.create(MediaType.parse("text/plain"), "UPLOAD PROFILE IMAGE");

        Call<GenericResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .uploadImage(
                        bodyCommand,
                        bodyFile,
                        bodyBorrowerID,
                        bodyIMEI,
                        bodyBucketName,
                        bodyUserID,
                        bodySession,
                        bodyAuthCode
                );
        call.enqueue(uploadFileCallback);

    }

    private Callback<GenericResponse> uploadFileCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {

                        if (isID) {
                            imageidurl = response.body().getData();

                            isID = false;
                            isPOB = true;
                            dialogName = "Proof of Billing image";
                            imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                    "/" + imagepobfilename);
                            createFile(getViewContext(), imagepobUri, imageFile);
//                            mLoaderDialogMessage.setText("Uploading " + dialogName);
                            setUpProgressLoaderMessageDialog("Uploading " + dialogName);
                            mTvFetching.setText("Uploading Pictures...");
                            uploadFile(imageFile);

                        } else if (isPOB) {
                            mLlLoader.setVisibility(View.GONE);
                            hideProgressDialog();
                            imagepoburl = response.body().getData();

                            isID = false;
                            isPOB = false;
                            isRegister = true;
                            isS3Key = false;
                            isUpload = false;
                            isServiceCharge = false;
                            getSession();
                        }


                    } else {
//                        if (mLoaderDialog != null) {
//                            mLoaderDialog.dismiss();
//                        }
                        setUpProgressLoaderDismissDialog();

                        mLlLoader.setVisibility(View.GONE);
                        hideProgressDialog();

                        showError(response.body().getMessage());
                    }
                } else {

//                    if (mLoaderDialog != null) {
//                        mLoaderDialog.dismiss();
//                    }
                    setUpProgressLoaderDismissDialog();

                    mLlLoader.setVisibility(View.GONE);
                    hideProgressDialog();

                    if (errBody.string().contains("!DOCTYPE html")) {
                        showReUploadDialog();
                    } else {
                        showError();
                    }
                }
            } catch (Exception e) {

//                if (mLoaderDialog != null) {
//                    mLoaderDialog.dismiss();
//                }
                setUpProgressLoaderDismissDialog();

                mLlLoader.setVisibility(View.GONE);
                hideProgressDialog();

                showError("Something went wrong during upload.");
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            if (mLoaderDialog != null) {
//                mLoaderDialog.dismiss();
//            }
            setUpProgressLoaderDismissDialog();

            mLlLoader.setVisibility(View.GONE);
//            hideProgressDialog();

            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
//            hideProgressDialog();
        }
    };

    private void showReUploadDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(getViewContext())
                .content("Failed in uploading your image. Please try again.")
                .positiveText("Try Again")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //call upload api
                        isID = true;
                        isPOB = false;
                        isRegister = false;
                        isS3Key = false;
                        isUpload = true;
                        isServiceCharge = false;
                        getSession();
                    }
                }).show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
    }

}
