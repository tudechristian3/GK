package com.goodkredit.myapplication.activities.publicsponsor;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.V2SubscriberDetails;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.goodkredit.myapplication.model.publicsponsor.VerifySponsorData;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.JsonToXml;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterToPublicSponsorActivity extends BaseActivity implements View.OnClickListener {

    private PublicSponsor sponsor;
    private V2SubscriberDetails subscriberdetails;
    private VerifySponsorData verifysponsordata;

    private ImageView img_camera;
    private ImageView img_profpic;
    private EditText txv_mobileno;
    private EditText edt_lastname;
    private EditText edt_firstname;
    private EditText edt_middlename;
    private EditText edt_email;
    private EditText edt_streetaddress;
    private EditText edt_city;
    private EditText edt_message;
    private EditText edt_sponsorid;
    private EditText edt_gender;

    private TextView txv_sponsorid;
    private Button btn_register;

    //spinner
    private ArrayAdapter<String> spinTypeAdapter;
    private String[] selecttype;

    //STRING DATA
    private String lastname;
    private String firstname;
    private String middlename;
    private String email;
    private String streetaddress;
    private String city;
    private String message;
    private String gender;

    private DatabaseHandler mdb;
    private String imei;
    private String userid;
    private String borrowerid;
    private String authcode;
//    private String sessionid;
    private String sponsorcode;

    private String sponsorNotes1;

    LinearLayout linearLayout;

    List<Button> buttonslist = new ArrayList<Button>();
    List<EditText> inputslist = new ArrayList<EditText>();
    List<Spinner> spinnerlist = new ArrayList<Spinner>();
    List<TextView> textviewlist = new ArrayList<TextView>();
    final HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
    JSONArray params = new JSONArray();
    JSONArray custominfoarr;

    JSONArray passedarr = new JSONArray();
    private JsonToXml jsonToXml;
    private String formattedXML;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    public final int REQ_GALLERY = 111;
    public final int REQ_CAMERA = 222;

    private File imageFile;
    public Uri fileUri = null;
    String imageurl = "";

    public ProfileLogo target;

    private Uri imageUri;
    private boolean isProfilePicChanged = false;
    private String picurl;

    //UNIFIED SESSION
    private String sessionid = "";

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String param;
    private String keyEncryption;

    private String updateIndex;
    private String updateAuthenticationid;
    private String updateKeyEncryption;
    private String updateParam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_public_sponsor_registration);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        init();
        initData();
    }

    private void init() {

        checkPermissions();
        setupToolbar();
        target = new ProfileLogo();
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        mLlLoader = (RelativeLayout) findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) findViewById(R.id.fetching);
        mTvWait = (TextView) findViewById(R.id.wait);

        img_camera = (ImageView) findViewById(R.id.profile_edit_profpic);
        img_profpic = (ImageView) findViewById(R.id.circleImageView);
        txv_mobileno = (EditText) findViewById(R.id.mobile);
        edt_lastname = (EditText) findViewById(R.id.lastname);
        edt_firstname = (EditText) findViewById(R.id.firstname);
        edt_middlename = (EditText) findViewById(R.id.middlename);
        edt_email = (EditText) findViewById(R.id.email);
        edt_streetaddress = (EditText) findViewById(R.id.streetaddress);
        edt_city = (EditText) findViewById(R.id.city);
        edt_message = (EditText) findViewById(R.id.message);
        edt_sponsorid = (EditText) findViewById(R.id.guarantoridval);
        edt_gender = (EditText) findViewById(R.id.gender);

        txv_sponsorid = (TextView) findViewById(R.id.guarantoridlbl);
        btn_register = (Button) findViewById(R.id.btn_register);

        linearLayout = (LinearLayout) findViewById(R.id.layout_regtosponsorxmldetails);

        img_camera.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        edt_gender.setOnClickListener(this);
    }

    private void initData() {
        sponsor = getIntent().getParcelableExtra(PublicSponsor.KEY_PUBLICSPONSOR);
        subscriberdetails = getIntent().getParcelableExtra(V2SubscriberDetails.KEY_V2SUBSCRIBERDETAILS);
        verifysponsordata = getIntent().getParcelableExtra(VerifySponsorData.KEY_VERIFYSPONSORDATA);

        mdb = new DatabaseHandler(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        imei = PreferenceUtils.getImeiId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sponsorcode = verifysponsordata.getGuarantorID();

        //UNIFIED SESSION
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        populateData(subscriberdetails);
//        parseData();
    }

    private void parseData() {
        try{
            sponsorNotes1 = sponsor.getNotes1();
            String count = CommonFunctions.parseXML(sponsorNotes1, "count");

            for(int i = 0; i<Integer.parseInt(count); i++){
                String value = CommonFunctions.parseXML(sponsorNotes1, String.valueOf(i));
                String field = CommonFunctions.parseXML(value, String.valueOf("mobile"));


                if(!field.equals("NONE")){
                    String[] fieldarr = field.split(":::");
                    if(fieldarr.length > 0){
                        String description = fieldarr[0];
                        String name = fieldarr[1];
                        String datatype = fieldarr[2];
                        String mandatory = fieldarr[3];
                        String inputtype = fieldarr[4];

                        //set xthis for the submit checking of values
                        JSONObject obj = new JSONObject();
                        obj.put("name", name);
                        obj.put("mandatory", mandatory);
                        obj.put("description", description);
                        obj.put("datatype", datatype);
                        params.put(obj);

                        //1. Create title (description)
                        if (!mandatory.equals("NO")) {
                            description = description + " * ";
                        }

                        if (inputtype.contains("SELECT")) {

                            //1.
//                            createTextView(name, description, mandatory);
                            //2.
//                            createSpinner(name, inputtype, mandatory);

                        } else if (inputtype.contains("CALENDAR")) {

                            //1.
//                            createTextView(name, description, mandatory);

                            //2.Add button
//                            createButton(name, mandatory);


                        } else {//TEXT VIEW

                            //1.
                            Logger.debug("vanhttp", "name: " + name);
                            Logger.debug("vanhttp", "desc: " + description);
                            Logger.debug("vanhttp", "mand: " + mandatory);
                            createTextView(name, description, mandatory);

                            //2.Add inputs
                            createEditText(name, datatype, mandatory);

                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void createTextView(String name, String description, String mandatory){
        try{
            JSONObject textviewtag = new JSONObject();
            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutparams.setMargins(0, 40, 0, 10);
            TextView textview = new TextView(getViewContext());
            textview.setText(description);
            textview.setTextSize(12);
            textview.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_default_textview));
            textview.setTextAppearance(getViewContext(), R.style.roboto_regular);
            textview.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            textviewtag.put("name", name);
            textviewtag.put("mandatory", mandatory);
            textview.setTag(textviewtag);
            textviewlist.add(textview);
            linearLayout.addView(textview, layoutparams);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                textview.setVisibility(View.VISIBLE);
            } else {
                textview.setVisibility(View.GONE);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createEditText(String name, String datatype, String mandatory) {
        try {
            JSONObject edittexttag = new JSONObject();
            EditText editText = new EditText(this);
            editText.setPadding(30, 30, 30, 30);
            editText.setTextSize(18);
            edittexttag.put("name", name);
            edittexttag.put("mandatory", mandatory);
            editText.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            editText.setTag(edittexttag);
            editText.setBackgroundResource(R.drawable.border);
            if (datatype.toUpperCase().equals("MONEY") || datatype.toUpperCase().equals("NUMBER")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                editText.setFilters(new InputFilter[]{
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence cs, int start,
                                                       int end, Spanned spanned, int dStart, int dEnd) {
                                // TODO Auto-generated method stub
                                if (cs.equals("")) { // for backspace
                                    return cs;
                                }
                                if (cs.toString().matches("[.0-9]+")) {
                                    return cs;
                                }
                                return "";
                            }
                        }
                });
            } else if (datatype.toUpperCase().equals("VARCHAR")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setFilters(new InputFilter[]{
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence cs, int start,
                                                       int end, Spanned spanned, int dStart, int dEnd) {
                                // TODO Auto-generated method stub
                                if (cs.equals("")) { // for backspace
                                    return cs;
                                }
                                if (cs.toString().matches("[a-zA-Z 0-9]+")) {
                                    return cs;
                                }
                                return "";
                            }
                        }
                });
            }

            linearLayout.addView(editText);
            inputslist.add(editText);


            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                editText.setVisibility(View.VISIBLE);
            } else {
                editText.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void populateData(V2SubscriberDetails data) {

        try{
            String profilepic = data.getProfilePicURL();
            String lastname = data.getLastName();
            String firstname = data.getFirstName();
            String middlename = data.getMiddleName();
            String gender = data.getGender();
            String email = data.getEmailAddress();
            String streetaddress = data.getStreetAddress();
            String city = data.getCity();
            String mobileno = data.getMobileNo();

            txv_mobileno.setText(mobileno);

            Glide.with(getViewContext())
                    .load(profilepic)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.emptyprofilepic))
                    .into(img_profpic);

            if(lastname.equals(".") || lastname.isEmpty()){
                edt_lastname.setText("");
            } else{
                edt_lastname.setText(lastname);
                edt_lastname.setSelection(edt_lastname.getText().length());
            }

            if(firstname.equals(".") || firstname.isEmpty()){
                edt_firstname.setText("");
            } else{
                edt_firstname.setText(firstname);
            }

            if(middlename.equals(".") || middlename.isEmpty()){
                edt_middlename.setText("");
            } else{
                edt_middlename.setText(middlename);
            }

            if(gender.equals(".") || gender.isEmpty()){
                edt_gender.setText("");
            } else{
                edt_gender.setText(gender);
            }

            if(email.equals(".") || email.isEmpty()){
                edt_email.setText("");
            } else{
                edt_email.setText(email);
            }

            if(streetaddress.equals(".") || streetaddress.isEmpty()){
                edt_streetaddress.setText("");
            } else{
                edt_streetaddress.setText(streetaddress);
            }

            if(city.equals(".") || city.isEmpty()){
                edt_city.setText("");
            } else{
                edt_city.setText(city);
            }

            txv_sponsorid.setVisibility(View.VISIBLE);
            edt_sponsorid.setVisibility(View.VISIBLE);
            edt_sponsorid.setText(sponsor.getGuarantorID());
        } catch (Exception e){
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

        switch (v.getId()){
            case R.id.btnSelectPhoto: {
                break;
            }

            case R.id.profile_edit_profpic: {
                showUpdateProfPicConfirmation();
                break;
            }

            case R.id.gender: {
                showGenderOptionsDialog();
                break;
            }

            case R.id.btn_register: {

                try{
                    //GET STRING
                    lastname = edt_lastname.getText().toString();
                    firstname = edt_firstname.getText().toString();
                    middlename = edt_middlename.getText().toString();
                    gender = edt_gender.getText().toString();
                    email = edt_email.getText().toString();
                    streetaddress = edt_streetaddress.getText().toString();
                    city = edt_city.getText().toString();
                    message = edt_message.getText().toString();

                    if(isProfilePicChanged){
                        picurl = imageurl;
                    } else{
                        picurl = subscriberdetails.getProfilePicURL();
                    }

                    //please include isvalid>0
                    if(!(lastname.trim().equals("") || firstname.trim().equals("") || middlename.trim().equals("") || gender.trim().equals("")
                            || email.trim().equals("") || streetaddress.trim().equals("") || city.trim().equals("") || message.trim().equals(""))){

                        getSession();

                    } else{
                        showToast("Please fill all mandatory fields.", GlobalToastEnum.WARNING); }

                    int isvalid = 0;


//                    int mobileinvalid = 0;
//                    int emailisinvalid = 0;
//                    custominfoarr = new JSONArray();

                    for(int i = 0; i<params.length(); i++){
                        JSONObject custominfo = new JSONObject();

                        JSONObject custom = params.getJSONObject(i);
                        String inputname = custom.getString("name");
                        String ismandatory = custom.getString("mandatory");
                        String description = custom.getString("description");
                        String datatype = custom.getString("datatype");

                        //get the data in the input text
                        for (EditText inputdata : inputslist) {
                            String f = inputdata.getTag().toString();
                            JSONObject jresponse = new JSONObject(f);
                            String name = jresponse.getString("name");

                            if(name.equals(inputname)){
                                String inputres = inputdata.getText().toString();

                                //place data in the object
                                custominfo.put("name", inputname);
                                custominfo.put("description", description.toUpperCase());
                                custominfo.put("value", inputres);
                                custominfo.put("datatype", datatype);
                                custominfoarr.put(custominfo);

                                JSONObject jsonObject = null;
                                for(int x = 0, count = passedarr.length(); x < count; x++){
                                    jsonObject = passedarr.getJSONObject(x);
                                }
                                jsonToXml = new JsonToXml.Builder(jsonObject).build();
                                formattedXML = jsonToXml.toString();

                                Logger.debug("vanhttp", "jsonToXml" + jsonToXml);
                                Logger.debug("vanhttp", "formattedXML" + formattedXML);


                                //if mandatory, need not empty
                                if (!ismandatory.equals("NO") && inputdata.getVisibility() == View.VISIBLE) {
                                    if (inputres.trim().length() == 0) {
                                        isvalid = isvalid + 1;
                                    }
                                }
                            }
                        }

//                            if (isvalid > 0) {
//                                showToast("Fields with * are mandatory", GlobalToastEnum.WARNING);
//                            } else{
//
//                            }
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

                break;
            }
        }
    }

    private void showGenderOptionsDialog() {
        new MaterialDialog.Builder(this)
                .title("Select Gender")
                .items(R.array.arr_gender)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        /**
                         * If you use alwaysCallSingleChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected radio button to actually be selected.
                         **/
                        edt_gender.setText(text.toString().toUpperCase());
                        return true;
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GALLERY) {
            if (resultCode == RESULT_OK) {
                cropImage(data.getData());
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageUri = result.getUri();
                setDisplayPicture(imageUri);
                imageFile = new File(imageUri.getPath());
                getUploadSession();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == REQ_CAMERA) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    cropImage(fileUri);
                    fileUri = null;
                } else {
                    cropImage(data.getData());
                }
            }
        }
    }

    private void cropImage(Uri uri) {
        if (uri != null) {
            CropImage.activity(uri)
                    .setActivityTitle("Crop Image")
                    .setAutoZoomEnabled(true)
                    .setBorderCornerColor(R.color.zxing_transparent)
                    .setBorderCornerThickness(0)
                    .setActivityMenuIconColor(R.color.colorwhite)
                    .setAllowRotation(true)
                    .setAllowFlipping(true)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setFixAspectRatio(true)
                    .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                    .start(this);
        }
    }

    private void setDisplayPicture(Uri uri) {
        img_profpic.setImageBitmap(getRoundedShape(getImageBitmap(uri)));
    }

    public Bitmap getImageBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public Bitmap getRoundedShape(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                    bitmap.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    private class ProfileLogo implements Target {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
            img_profpic.setImageBitmap(getRoundedShape(bitmap));
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }


        @Override
        public void onPrepareLoad(Drawable drawable) {
        }

    }


//    private void getUploadSession() {
//
//        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
//                .getRegisteredSession(imei, userid);
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            call.enqueue(sessionUploadCallback);
//            showProgressDialog(getViewContext(), "Uploading image", "Please wait...");
//        } else {
//            hideProgressDialog();
//            showError(getString(R.string.generic_internet_error_message));
//        }
//    }
//
//    private Callback<String> sessionUploadCallback = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                    mLlLoader.setVisibility(View.GONE);
//                    hideProgressDialog();
//                } else if (responseData.equals("error")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    mLlLoader.setVisibility(View.GONE);
//                    hideProgressDialog();
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    mLlLoader.setVisibility(View.GONE);
//                    hideProgressDialog();
//                } else {
//                    sessionid = response.body();
//                    uploadFile(imageFile);
//                }
//            } else {
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                mLlLoader.setVisibility(View.GONE);
//                hideProgressDialog();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            mLlLoader.setVisibility(View.GONE);
//            hideProgressDialog();
//        }
//    };

    private void getUploadSession() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog(getViewContext(), "Uploading image", "Please wait...");
            uploadFile(imageFile);
        } else {
            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            showNoInternetToast();
        }
    }

    @SuppressLint("InlinedApi")
    private void checkPermissions() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getViewContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1234);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showUpdateProfPicConfirmation() {
        new MaterialDialog.Builder(getViewContext())
                .title("Select Mode")
                .items(R.array.str_action_profile_picture)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which == 0) {
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            fileUri = getOutputMediaFileUri(1);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, REQ_CAMERA);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            RegisterToPublicSponsorActivity.this.startActivityForResult(intent, REQ_GALLERY);
                        }
                        return true;
                    }
                })
                .show();
    }

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
                        imageurl = response.body().getData();
                        getSessionUpdateProfPicLink();

                        isProfilePicChanged = true;
                    } else {
                        showError(response.body().getMessage());
                    }
                } else {
                    if (errBody.string().contains("!DOCTYPE html")) {
                        showReUploadDialog();
                    } else {
                        showError();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
        }
    };


    private void getSessionUpdateProfPicLink() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //updateProfPicLink();
            updateProfilePicV2();
        } else {
            mLlLoader.setVisibility(View.GONE);
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void updateProfPicLink() {
        Call<String> call = RetrofitBuild.getCommonApiService(getViewContext())
                .updateProfilePic(
                        imei,
                        userid,
                        borrowerid,
                        V2Utils.getSha1Hex(imei + userid + sessionid),
                        sessionid,
                        imageurl);

        call.enqueue(updateProfPicLinkCallback);
    }

    private Callback<String> updateProfPicLinkCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            hideProgressDialog();
            if (response.body().toString().equals("000")) {
                mdb.updateProfilePic(mdb, imageurl, subscriberdetails.getMobileNo());
            } else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            hideProgressDialog();
            showError();
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
                        getUploadSession();
                    }
                }).show();

        V2Utils.overrideFonts(getViewContext(), V2Utils.ROBOTO_REGULAR, dialog.getView());
    }


    private void getSession() {
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){
            setUpProgressLoader();
            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
            //registerToSponsorV2(registerToSponsorV2Session);
            registerToSponsorV2Security();

        } else{
            setUpProgressLoaderDismissDialog();
            showError(getString(R.string.generic_internet_error_message));
        }
    }

    private void registerToSponsorV2(Callback<GenericResponse> registerToSponsorV2Callback) {
        Call<GenericResponse> registertosponsor = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .registerToSponsorV2Call(imei,
                        userid,
                        borrowerid,
                        authcode,
                        sessionid,
                        lastname,
                        firstname,
                        middlename,
                        gender,
                        email,
                        streetaddress,
                        city,
                        sponsorcode,
                        message,
                        subscriberdetails.getProfilePicURL(),
                        "ANDROID");

        registertosponsor.enqueue(registerToSponsorV2Callback);
    }
    private final Callback<GenericResponse> registerToSponsorV2Session = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            setUpProgressLoaderDismissDialog();
            if (errorBody == null) {

                if (response.body().getStatus().equals("000")) {
                    Logger.debug("vanhttp", "profile pic updated: " + subscriberdetails.getProfilePicURL());
                    showGlobalDialogs("You have successfully sent your registration request. " +
                                    "You will be notified once your sponsor confirmed your request.",
                            "close", ButtonTypeEnum.SINGLE,
                            false, false, DialogTypeEnum.SUCCESS);
                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showGlobalDialogs(response.body().getMessage(), "retry",
                        ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     * */

    private void registerToSponsorV2Security(){
        if(CommonFunctions.getConnectivityStatus(getViewContext()) > 0){

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("lastname", lastname);
            parameters.put("firstname", firstname);
            parameters.put("middlename", middlename);
            parameters.put("gender", gender);
            parameters.put("emailaddress", email);
            parameters.put("streetaddress", streetaddress);
            parameters.put("city", city);
            parameters.put("sponsorcode", sponsorcode);
            parameters.put("message", message);

            if(subscriberdetails.getProfilePicURL() == null){
                parameters.put("picurl",".");
            }else{
                parameters.put("picurl",subscriberdetails.getProfilePicURL());
            }
            parameters.put("devicetype", CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "registerToSponsorV2");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            registerToSponsorV2SecurityObject(registerToSponsorV2Callback);

        }else{
            setUpProgressLoaderDismissDialog();
            showNoInternetToast();
        }
    }
    private void registerToSponsorV2SecurityObject(Callback<GenericResponse> registerToSponsorV2Callback) {
        Call<GenericResponse> registertosponsor = RetrofitBuilder.getSubscriberV2APIService(getViewContext())
                .registerToSponsorV2(authenticationid,sessionid,param);

        registertosponsor.enqueue(registerToSponsorV2Callback);
    }
    private final Callback<GenericResponse> registerToSponsorV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            setUpProgressLoaderDismissDialog();
            if (errorBody == null) {
                String message =CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String data =CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    //Logger.debug("okhttp", "profile pic updated: " + subscriberdetails.getProfilePicURL());
                    showGlobalDialogs("You have successfully sent your registration request. " +
                                    "You will be notified once your sponsor confirmed your request.",
                            "close", ButtonTypeEnum.SINGLE,
                            false, false, DialogTypeEnum.SUCCESS);

                } else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showError(message);
                }
            } else {
                showGlobalDialogs("Something went wrong. Please try again.", "retry",
                        ButtonTypeEnum.SINGLE, false, false, DialogTypeEnum.FAILED);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            setUpProgressLoaderDismissDialog();
            hideProgressDialog();
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    //update profile picture
    private void updateProfilePicV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("profilepicurl", imageurl);

        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        updateIndex = CommonFunctions.parseJSON(jsonString, "index");
        updateAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        parameters.put("index", updateIndex);

        String paramJson = new Gson().toJson(parameters, Map.class);
        updateKeyEncryption = CommonFunctions.getSha1Hex(updateAuthenticationid + sessionid + "updateProfilePicV2");
        updateParam = CommonFunctions.encryptAES256CBC(updateKeyEncryption, String.valueOf(paramJson));

        updateProfilePicV2Object(updateProfilePicV2Callback);

    }
    private void updateProfilePicV2Object(Callback<GenericResponse> updateProfilePicCallback) {
        Call<GenericResponse> call = RetrofitBuilder.getAccountV2API(getViewContext())
                .updateProfilePicV2(
                        updateAuthenticationid, sessionid, updateParam);
        call.enqueue(updateProfilePicCallback);
    }
    private Callback<GenericResponse> updateProfilePicV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            if(response.errorBody() == null){
                String message = CommonFunctions.decryptAES256CBC(updateKeyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    mdb.updateProfilePic(mdb, imageurl, subscriberdetails.getMobileNo());
                } else if (response.body().getStatus().equals("003")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    showErrorGlobalDialogs(message);
                }
            }else {
                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };


}
