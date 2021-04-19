package com.goodkredit.myapplication.fragments.coopassistant.member;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.util.IOUtils;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantLoanType;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.NumberTextWatcherForThousand;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import hk.ids.gws.android.sclick.SClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoopAssistantLoanFormFragment extends BaseFragment implements View.OnClickListener {

    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //ADDED PARAMETERS
    private String servicecode = "";
    private String loanid = "";
    //    private String amount = "";
    private String kycotherinfo = "";
    private String loantype = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    private LinearLayout coop_desiredamount_container;
    private EditText edt_desiredamount;
    private String desiredamount;

    private LinearLayout btn_action_container;
    private TextView btn_action;

    private ArrayList<CoopAssistantLoanType> coopAssistantLoanList;

    //DYNAMIC FIELDS
    private LinearLayout layout_custom_fields;

    private List<TextView> txtViewList = new ArrayList<TextView>();
    private List<EditText> editViewList = new ArrayList<>();
    private List<TextView> txtViewCalendarList = new ArrayList<TextView>();

    private List<LinearLayout> spnViewListContainer = new ArrayList<LinearLayout>();
    private List<Spinner> spnViewList = new ArrayList<Spinner>();
    //private final HashMap<Integer, String> spnMap = new HashMap<Integer, String>();
    private SparseArray spnMap = new SparseArray();

    private List<ImageView> imgViewList = new ArrayList<>();
    private Uri fileUri = null;
    private Uri imageidUri = null;
    private String strFromThisImageView = "";

    private JSONArray otherDetailsJSONArray = new JSONArray();

    private JSONArray kycWithoutUploadingJSONArray = new JSONArray();
    private JSONArray kycUploadingJSONArray = new JSONArray();

    private String str_kycWithoutUploading = "";
    private String str_kycUploading = "";
    private String strKycOtherInfoToBeSubmited = "";

    //ERROR TEXTVIEW
    private List<TextView> errorStaticTxtViewList = new ArrayList<TextView>();
    private List<TextView> errorOtherDetailsTextViewList = new ArrayList<TextView>();

    //RESELLER
    private String resellerid = "";
    private String distributorid = "";
    private boolean checkIfReseller = false;

    //UPLOAD
    private File imageFile;
    private String imageidfilename = "";
    private List<MultipartBody.Part> bodyParts = new ArrayList<>();

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_loan_form, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {

        coop_desiredamount_container = view.findViewById(R.id.coop_desiredamount_container);
        edt_desiredamount = view.findViewById(R.id.edt_coop_desiredamount);
        //edt_desiredamount.addTextChangedListener(new NumberTextWatcherForThousand(edt_desiredamount));

        layout_custom_fields = view.findViewById(R.id.layout_custom_fields);

        btn_action_container = view.findViewById(R.id.btn_action_container);
        btn_action_container.setVisibility(View.VISIBLE);
        btn_action = view.findViewById(R.id.btn_action);
        btn_action.setText("SUBMIT");
        btn_action.setOnClickListener(this);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
        servicecode = PreferenceUtils.getStringPreference(getViewContext(), "GKServiceCode");

        authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

        distributorid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_DISTRIBUTORID);
        resellerid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_RESELLER);

        mdb = new DatabaseHandler(getViewContext());

        if (getArguments() != null) {
            coopAssistantLoanList = getArguments().getParcelableArrayList("LoanTypeSelected");


            CoopAssistantLoanType coopAssistantLoanType = null;

            for (CoopAssistantLoanType loanType : coopAssistantLoanList) {
                coopAssistantLoanType = loanType;

            }

            if (coopAssistantLoanType != null) {
                kycotherinfo = coopAssistantLoanType.getKYCOtherInfo();
                loanid = coopAssistantLoanType.getLoanID();
                loantype = coopAssistantLoanType.getName();

                getActivity().setTitle(CommonFunctions.replaceEscapeData(coopAssistantLoanType.getName()));

            }

        }

        setStaticErrorTextViewForStaticFields();

        addXMLDetails();
    }

    private void addXMLDetails() {
        try {

            String count = CommonFunctions.parseXML(kycotherinfo, "count");

            Logger.debug("checkhttp","VALUE");

            if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {

                layout_custom_fields.setVisibility(View.VISIBLE);

                if (Integer.parseInt(count) > 0) {
                    for (int i = 1; i <= Integer.parseInt(count); i++) {
                        String field = CommonFunctions.parseXML(kycotherinfo, String.valueOf(i));
                        String[] fieldarr = field.split(":::");

                        try {
                            if (!field.equals("NONE") && !field.equals("")) {
                                if (fieldarr.length > 0) {
                                    String name = fieldarr[0];
                                    String description = fieldarr[1];
                                    String datatype = fieldarr[2];
                                    String mandatory = fieldarr[3];
                                    String inputtype = fieldarr[4];

                                    mandatory = mandatory.toUpperCase();

                                    //set this for the submit checking of values
                                    JSONObject obj = new JSONObject();
                                    obj.put("name", name);
                                    obj.put("description", description);
                                    obj.put("mandatory", mandatory);
                                    obj.put("datatype", datatype);
                                    otherDetailsJSONArray.put(obj);

                                    //1. Create TITLE
                                    if (!mandatory.equals("NO")) {
                                        name = name + "*";
                                    }

                                    if (inputtype.contains("SELECT")) {

                                        createTextView(name, mandatory);

                                        createSpinner(name, inputtype, mandatory);

                                        createErrorTextView(name, mandatory, "");

                                    } else if (inputtype.contains("CALENDAR")) {

                                        createTextView(name, mandatory);

                                        createCalendarView(name, mandatory);

                                        createErrorTextView(name, mandatory, "");

                                    } else if (inputtype.contains("UPLOAD")) {

                                        createTextView(name, mandatory);

                                        createUploadView(name, mandatory);

                                        createErrorTextView(name, mandatory, "");
                                    } else {//TEXT VIEW
                                        createTextView(name, mandatory);

                                        createEditText(name, datatype, mandatory);

                                        createErrorTextView(name, mandatory, "");
                                    }

                                }
                            }
                        } catch (Error e) {
                            e.printStackTrace();
                        }
                    }

                    otherDetailsAddTextWatcher();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //CREATES THE TEXTVIEW (XML DETAILS)
    private void createTextView(String name, String mandatory) {
        try {
            JSONObject txtviewtag = new JSONObject();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            int marginleft = CommonFunctions.converttoDP(getViewContext(), 0);
            int margintop = CommonFunctions.converttoDP(getViewContext(), 15);
            int marginright = CommonFunctions.converttoDP(getViewContext(), 0);
            int marginbottom = CommonFunctions.converttoDP(getViewContext(), 0);

            layoutParams.setMargins(marginleft, margintop, marginright, marginbottom);

            TextView txtName = new TextView(getViewContext());
            txtName.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtName.setTextColor(ContextCompat.getColor(mContext, R.color.coopassist_green));
            txtName.setPadding(10, 10, 10, 10);
            txtName.setTextSize(16);
            txtName.setText(name);
            txtviewtag.put("name", name);
            txtviewtag.put("mandatory", mandatory);
            txtName.setTag(txtviewtag);
            txtViewList.add(txtName);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                txtName.setVisibility(View.VISIBLE);
            } else {
                txtName.setVisibility(View.GONE);
            }

            layout_custom_fields.addView(txtName, layoutParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE EDITEXT (XML DETAILS)
    private void createEditText(String name, String datatype, String mandatory) {
        try {
            JSONObject edittexttag = new JSONObject();

            EditText edtView = new EditText(getViewContext());
            edtView.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));

            int paddingall = CommonFunctions.converttoDP(getViewContext(), 10);

            edtView.setPadding(paddingall, paddingall, paddingall, paddingall);
            String gray = "#49494A";
            edtView.setTextColor(Color.parseColor(gray));
            edtView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));
            edtView.setTextSize(15);
            edtView.setSingleLine();
            edittexttag.put("name", name);
            edittexttag.put("mandatory", mandatory);
            edittexttag.put("datatype", datatype);
            edtView.setTag(edittexttag);
            edtView.setBackgroundResource(R.drawable.border);
            if (datatype.toUpperCase().equals("MONEY") || datatype.toUpperCase().equals("NUMBER")) {
                edtView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                edtView.setFilters(new InputFilter[]{
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
                edtView.setInputType(InputType.TYPE_CLASS_TEXT);
                edtView.setFilters(new InputFilter[]{
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

            editViewList.add(edtView);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                edtView.setVisibility(View.VISIBLE);
            } else {
                edtView.setVisibility(View.GONE);
            }

            layout_custom_fields.addView(edtView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE SPINNER (XML DETAILS)
    private void createSpinner(String name, String inputtype, String mandatory) {
        //2. Create spinner
        try {

            ArrayList<String> spinnerArray = new ArrayList<String>();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            JSONObject spinnertag = new JSONObject();
            if (name.contains("*")) {
                name = name.replace("*", "");
            }
            spinnerArray.add("Select " + name);

            String[] selectoption = inputtype.split(":");
            if (selectoption.length > 0) {
                for (int x = 1; x < selectoption.length; x++) {

                    String[] value = selectoption[x].split("-");
                    String desc = value[1];
                    String id = value[0];

                    spinnerArray.add(desc);
                    spnMap.put(x, id);
                }
            }

            final LinearLayout spinnercontainer = new LinearLayout(getViewContext());
            spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            final Spinner spinner = new Spinner(getViewContext());


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getViewContext(),
                    R.layout.activity_custom_spinner, spinnerArray) {

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {

                    View view = super.getDropDownView(position, convertView, parent);
                    if (position == 0) {
                        ((TextView) view.findViewById(R.id.customSpinner)).setTextColor(getResources().getColor(R.color.colorsilver));
                        ((TextView) view.findViewById(R.id.customSpinner)).setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    } else {
                        ((TextView) view.findViewById(R.id.customSpinner)).setTextColor(getResources().getColor(R.color.colorTextGrey));
                        ((TextView) view.findViewById(R.id.customSpinner)).setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
                    }
                    return view;
                }
            };

            spinnerArrayAdapter.setDropDownViewResource(R.layout.activity_custom_spinner);

            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setSelection(0);

            spinnertag.put("name", name);
            spinnertag.put("mandatory", mandatory);
            spinner.setTag(spinnertag);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    try {
                        if (position > 0) {
                            String selectedoptn = spinner.getSelectedItem().toString();
                            //String checkid = spnMap.get(spinner.getSelectedItemPosition() + 1);
                            String checkid = String.valueOf(spnMap.get(spinner.getSelectedItemPosition() + 1));

                            for (EditText inputdata : editViewList) {
                                String f = inputdata.getTag().toString();
                                JSONObject jresponse = new JSONObject(f);
                                String md = jresponse.getString("mandatory");

                                if (md.equals("YES") || md.equals("NO") || md.equals(checkid)) {
                                    inputdata.setVisibility(View.VISIBLE);

                                } else {
                                    inputdata.setVisibility(View.GONE);
                                    inputdata.setText("");
                                }
                            }

                            for (TextView tv : txtViewList) {
                                String tg = tv.getTag().toString();
                                JSONObject jresponse = new JSONObject(tg);
                                String md = jresponse.getString("mandatory");

                                if (md.equals("YES") || md.equals("NO") || md.equals(checkid)) {
                                    tv.setVisibility(View.VISIBLE);
                                } else {
                                    tv.setVisibility(View.GONE);
                                }

                            }

                            for (TextView b : txtViewCalendarList) {
                                String tg = b.getTag().toString();
                                JSONObject jresponse = new JSONObject(tg);
                                String md = jresponse.getString("mandatory");

                                if (md.equals("YES") || md.equals("NO") || md.equals(checkid)) {
                                    b.setVisibility(View.VISIBLE);
                                } else {
                                    b.setVisibility(View.GONE);
                                    b.setText("");
                                }
                            }

                            if (errorOtherDetailsTextViewList.size() > 0) {
                                for (TextView errordata : errorOtherDetailsTextViewList) {

                                    String strErrorTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(strErrorTag);
                                    String errorname = errorjresponse.getString("name");

                                    String spnErrorTag = parentView.getTag().toString();
                                    JSONObject spnErrorResponse = new JSONObject(spnErrorTag);
                                    String spnerrorname = spnErrorResponse.getString("name");

                                    if (errorname.equals(spnerrorname)) {
                                        parentView.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                        errordata.setVisibility(View.GONE);
                                        break;
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }

            });

            spnViewList.add(spinner);
            spinnercontainer.addView(spinner, layoutParams);

            JSONObject spinnercontainertag = new JSONObject();
            spinnercontainertag.put("name", name);
            spinnercontainertag.put("mandatory", mandatory);
            spinnercontainer.setTag(spinnercontainertag);

            spnViewListContainer.add(spinnercontainer);

            layout_custom_fields.addView(spinnercontainer, layoutParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE BUTTON (XML DETAILS)
    private void createCalendarView(String name, String mandatory) {
        try {
            JSONObject txtbtntag = new JSONObject();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final LinearLayout txtbuttoncontainer = new LinearLayout(getViewContext());
            txtbuttoncontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            TextView txtbtn = new TextView(getViewContext());

            int paddingall = CommonFunctions.converttoDP(getViewContext(), 10);

            txtbtn.setPadding(paddingall, paddingall, paddingall, paddingall);
            txtbtn.setFocusable(false);
            txtbtntag.put("name", name);
            txtbtntag.put("mandatory", mandatory);
            txtbtn.setTag(txtbtntag);
            txtbtn.setGravity(Gravity.CENTER_VERTICAL);

            txtbtn.setTextSize(14);
            txtbtn.setTextColor(getResources().getColor(R.color.colorTextGrey));
            txtbtn.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtbuttoncontainer.addView(txtbtn, layoutParams);


            txtbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = v.getTag().toString();

                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                    try {
                                        String date = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

                                        for (TextView txtView : txtViewCalendarList) {

                                            String txtViewTag = txtView.getTag().toString();
                                            JSONObject jresponse = new JSONObject(txtViewTag);
                                            String str_txtView = jresponse.getString("name");

                                            String dpViewTag = view.getTag().toString();
                                            JSONObject jres = new JSONObject(dpViewTag);
                                            String str_dpView = jres.getString("name");

                                            if (str_txtView.equals(str_dpView)) {
                                                if (errorOtherDetailsTextViewList.size() > 0) {
                                                    for (TextView errordata : errorOtherDetailsTextViewList) {
                                                        String strErrorTag = errordata.getTag().toString();
                                                        JSONObject errorjresponse = new JSONObject(strErrorTag);
                                                        String errorname = errorjresponse.getString("name");

                                                        String calendarname = str_txtView;

                                                        if (calendarname.contains("*")) {
                                                            calendarname = str_txtView.replace("*", "");
                                                        }

                                                        if (errorname.equals(calendarname)) {
                                                            txtView.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                                            errordata.setVisibility(View.GONE);
                                                            txtView.setText(date);
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    txtView.setText(date);
                                                    break;
                                                }
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(((CoopAssistantHomeActivity) mContext).getFragmentManager(), tag);
                }
            });

            //add to the list of button for me to find it, once i need to set value on it.
            txtViewCalendarList.add(txtbtn);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                txtbtn.setVisibility(View.VISIBLE);
            } else {
                txtbtn.setVisibility(View.GONE);
            }

            layout_custom_fields.addView(txtbuttoncontainer);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //CREATES THE VIEW FOR UPLOAD (XML DETAILS)
    private void createUploadView(String name, String mandatory) {
        try {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            final LinearLayout containerLayout = new LinearLayout(getViewContext());
            containerLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams imvlogoparams = new LinearLayout.LayoutParams(100, 100);

            final ImageView imv_logo = new ImageView(getViewContext());
            imv_logo.setPadding(15, 15, 15, 15);

            JSONObject imgViewTag = new JSONObject();
            imgViewTag.put("name", name);
            imgViewTag.put("mandatory", mandatory);
            imgViewTag.put("uri", "");
            imv_logo.setTag(imgViewTag);

            Picasso.with(getViewContext())
                    .load(R.drawable.generic_placeholder_gk_background)
                    .fit()
                    .centerInside()
                    .into(imv_logo);

            containerLayout.addView(imv_logo, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    CommonFunctions.converttoDP(getViewContext(), 150),
                    (float) 1.0));


            LinearLayout buttonLayout = new LinearLayout(getViewContext());
            buttonLayout.setOrientation(LinearLayout.VERTICAL);
            buttonLayout.setGravity(Gravity.CENTER_VERTICAL);
            buttonLayout.setPadding(0, 0, 15, 0);

            LinearLayout.LayoutParams imageButtonsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            ImageView imv_camera = new ImageView(getViewContext());
            imv_camera.setPadding(10, 20, 10, 20);
            imv_camera.setMinimumWidth(CommonFunctions.converttoDP(getViewContext(), 40));
            imv_camera.setMaxWidth(CommonFunctions.converttoDP(getViewContext(), 60));
            imv_camera.setMinimumHeight(CommonFunctions.converttoDP(getViewContext(), 40));
            imv_camera.setMaxHeight(CommonFunctions.converttoDP(getViewContext(), 60));

            Picasso.with(getViewContext())
                    .load(R.drawable.ic_profile_camera)
                    .fit()
                    .into(imv_camera);

            imv_camera.setLayoutParams(imageButtonsParams);
            imv_camera.setTag(imgViewTag);

            imv_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String strTag = view.getTag().toString();
                        JSONObject jsonObject = new JSONObject(strTag);
                        strFromThisImageView = jsonObject.getString("name");

                        cameraIntent();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            buttonLayout.addView(imv_camera, imageButtonsParams);

            ImageView imv_gallery = new ImageView(getViewContext());
            imv_gallery.setPadding(10, 20, 10, 20);
            imv_gallery.setMinimumWidth(CommonFunctions.converttoDP(getViewContext(), 40));
            imv_gallery.setMaxWidth(CommonFunctions.converttoDP(getViewContext(), 60));
            imv_gallery.setMinimumHeight(CommonFunctions.converttoDP(getViewContext(), 40));
            imv_gallery.setMaxHeight(CommonFunctions.converttoDP(getViewContext(), 60));

            Picasso.with(getViewContext())
                    .load(R.drawable.ic_profile_gallery)
                    .fit()
                    .into(imv_gallery);

            imv_gallery.setLayoutParams(imageButtonsParams);
            imv_gallery.setTag(imgViewTag);

            imv_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String strTag = view.getTag().toString();
                        JSONObject jsonObject = new JSONObject(strTag);
                        strFromThisImageView = jsonObject.getString("name");

                        galleryIntent();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            buttonLayout.addView(imv_gallery, imageButtonsParams);

            containerLayout.addView(buttonLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
            );


            imgViewList.add(imv_logo);


            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                containerLayout.setVisibility(View.VISIBLE);
            } else {
                containerLayout.setVisibility(View.GONE);
            }

            layout_custom_fields.addView(containerLayout, layoutParams);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cameraIntent() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        fileUri = Uri.fromFile(getOutputMediaFile(1));

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_FILE);
    }

    private void onImageResult(Uri uri) {
        try {

            for (ImageView imgView : imgViewList) {
                String imgViewTag = imgView.getTag().toString();
                JSONObject jresponse = new JSONObject(imgViewTag);
                String name = jresponse.getString("name");
                String mandatory = jresponse.getString("mandatory");

                if (name.equals(strFromThisImageView)) {
                    Picasso.with(getViewContext())
                            .load(uri)
                            .fit()
                            .into(imgView);

                    if (errorOtherDetailsTextViewList.size() > 0) {
                        for (TextView errordata : errorOtherDetailsTextViewList) {

                            String strErrorTag = errordata.getTag().toString();
                            JSONObject errorjresponse = new JSONObject(strErrorTag);
                            String errorname = errorjresponse.getString("name");


                            String uploadname = name;

                            if (uploadname.contains("*")) {
                                uploadname = uploadname.replace("*", "");
                            }


                            if (errorname.equals(uploadname)) {

                                errordata.setVisibility(View.GONE);
                                imgView.setBackground(ContextCompat.getDrawable(getViewContext(), R.color.colorwhite));

                                JSONObject resetimgViewTag = new JSONObject();
                                resetimgViewTag.put("name", name);
                                resetimgViewTag.put("mandatory", mandatory);
                                resetimgViewTag.put("uri", uri.toString());

                                imgView.setTag(null);
                                imgView.setTag(resetimgViewTag);

                                strFromThisImageView = "";
                                break;
                            }
                        }
                    } else {
                        JSONObject resetimgViewTag = new JSONObject();
                        resetimgViewTag.put("name", name);
                        resetimgViewTag.put("mandatory", mandatory);
                        resetimgViewTag.put("uri", uri.toString());

                        imgView.setTag(null);
                        imgView.setTag(resetimgViewTag);

                        strFromThisImageView = "";

                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //STATIC FIELDS
    private void setStaticErrorTextViewForStaticFields() {
        edt_desiredamount.setTag("Amount");

        createErrorTextView(edt_desiredamount.getTag().toString(), "YES",  edt_desiredamount.getTag().toString());

        try {
            setStaticEdtTextErrorTextWatcher();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setStaticEdtTextErrorTextWatcher() throws JSONException {
        for (TextView errordata : errorStaticTxtViewList) {
            String strErrorTag = errordata.getTag().toString();
            JSONObject errorjresponse = new JSONObject(strErrorTag);
            String errorname = errorjresponse.getString("name");

            if ("Amount".equals(errorname)) {
                edt_desiredamount.addTextChangedListener(new TextWatcher(edt_desiredamount, errordata));
            }
        }
    }

    //ERROR TEXTVIEW
    private void createErrorTextView(String name, String mandatory, String staticFieldsTag) {
        try {

            JSONObject txtviewtag = new JSONObject();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            int marginleft = 0;
            int margintop = 10;
            int marginright = 0;
            int marginbottom = 0;

            marginleft = CommonFunctions.converttoDP(getViewContext(), marginleft);
            margintop = CommonFunctions.converttoDP(getViewContext(), margintop);
            marginright = CommonFunctions.converttoDP(getViewContext(), marginright);
            marginbottom = CommonFunctions.converttoDP(getViewContext(), marginbottom);

            layoutParams.setMargins(marginleft, margintop, marginright, marginbottom);

            TextView txtView = new TextView(getViewContext());
            txtView.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));
            txtView.setTextColor(ContextCompat.getColor(mContext, R.color.color_FF0000));
            if (name.contains("*")) {
                name = name.replace("*", "");
            }
            txtView.setText("Invalid " + name + ".");
            txtView.setTextSize(14);
            txtView.setPadding(10, 10, 10, 10);
            txtviewtag.put("name", name);
            txtviewtag.put("mandatory", mandatory);
            txtView.setTag(txtviewtag);

            if (!staticFieldsTag.trim().equals("")) {
                errorStaticTxtViewList.add(txtView);
                if ("Amount".equals(staticFieldsTag)) {
                    coop_desiredamount_container.addView(txtView);
                }
            } else {
                errorOtherDetailsTextViewList.add(txtView);
                layout_custom_fields.addView(txtView);
            }

            txtView.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //ADD LISTENER TO OTHER DETAILS/PASSED XML
    private void otherDetailsAddTextWatcher() throws JSONException {
        //OTHER DETAILS
        for (int i = 0; i < otherDetailsJSONArray.length(); i++) {
            JSONObject c = otherDetailsJSONArray.getJSONObject(i);
            String inputname = c.getString("name");
            String ismandatory = c.getString("mandatory");

            //Check Stuff
            ismandatory = ismandatory.toUpperCase();

            //get the data in the input text
            for (EditText inputdata : editViewList) {
                String f = inputdata.getTag().toString();
                JSONObject jresponse = new JSONObject(f);
                String name = jresponse.getString("name");

                if (name.contains("*")) {
                    name = name.replace("*", "");
                }

                if (name.equals(inputname)) {
                    String inputres = inputdata.getText().toString();

                    //if mandatory, need not empty
                    if (!ismandatory.equals("NO") && inputdata.getVisibility() == View.VISIBLE) {
                        if (inputres.trim().length() == 0) {
                            for (TextView errordata : errorOtherDetailsTextViewList) {
                                String errorgetTag = errordata.getTag().toString();
                                JSONObject errorjresponse = new JSONObject(errorgetTag);
                                String errorname = errorjresponse.getString("name");
                                if (errorname.equals(name)) {
                                    inputdata.addTextChangedListener(new TextWatcher(inputdata, errordata));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //TEXTWATCHER
    private class TextWatcher implements android.text.TextWatcher {
        private EditText editText;
        private TextView errorTextView;

        private TextWatcher(EditText editText, TextView errorTextView) {
            this.editText = editText;
            this.errorTextView = errorTextView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.length() > 0) {
                if (editText != null) {

                }
            } else {
                if (errorTextView != null) {
                    errorTextView.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            int checktype = editText.getInputType();

            if (s.length() > 0) {
                if (editText != null) {
                    switch (checktype) {
                        //CHECK IF EMAIL (ADDED ONE FOR EMAIL TO MATCH THE GETINPUT TYPE)
                        case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + 1:
                            if (!CommonFunctions.isValidEmail(editText.getText().toString())) {
                                errorTextView.setVisibility(View.VISIBLE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            } else {
                                errorTextView.setVisibility(View.GONE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                            }
                            break;
                        //CHECK IF INPUT TYPE IS NUMBER
                        case InputType.TYPE_CLASS_NUMBER:
                            String strcheckmobilenumber = getMobileNumber(editText.getText().toString().trim());

                            if (strcheckmobilenumber.equals("INVALID")) {
                                errorTextView.setVisibility(View.VISIBLE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            } else {
                                errorTextView.setVisibility(View.GONE);
                                editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                            }
                            break;

                        case InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL:
                            errorTextView.setVisibility(View.GONE);
                            editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                            Logger.debug("checkhttp","I AM DECIMAL YEAH");
                            formatTypeNumber(editText.getText().toString());
                            break;

                        //DEFAULT VALUES
                        default:
                            Logger.debug("checkhttp","DEFAULT");
                            errorTextView.setVisibility(View.GONE);
                            editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                            break;
                    }
                }
            } else {
                if (errorTextView != null) {
                    errorTextView.setVisibility(View.VISIBLE);
                    editText.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                }
            }
        }

        private void formatTypeNumber(String value) {
            editText.removeTextChangedListener(this);
            if (!value.equals("")) {
                if(value.startsWith(".")){
                    editText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    editText.setText("");
                }

                String str = editText.getText().toString().replaceAll(",", "");
                editText.setText(getDecimalFormattedString(str));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
        }
    }

    private String getDecimalFormattedString(String value) {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }

    private void validateInformationBeforeProceeding() {
        try {
            int notvalid = 0;

            //CHECK FOR DESIRED AMOUNT
            desiredamount = NumberTextWatcherForThousand.trimCommaOfString(edt_desiredamount.getText().toString().trim());

            if (desiredamount.equals("")) {
                notvalid = notvalid + 1;
            }

            //STATIC FIELDS
            notvalid = notvalid + validateStaticFields();

            //OTHER DETAILS FIELDS
            notvalid = notvalid + validateOtherDetails();

            if (notvalid > 0) {
                showWarningGlobalDialogs("There's something wrong with the entered fields. Please try again.");
                hideKeyboard(getViewContext());
            } else {
                //convertKYCInfoJSONTOXML();
                str_kycUploading = kycUploadingJSONArray.toString();
                str_kycWithoutUploading = kycWithoutUploadingJSONArray.toString();

                if (!str_kycUploading.trim().equals("") &&
                        !str_kycUploading.trim().equals("[]")) {
                    showConfirmationDialog();
                } else {
                    strKycOtherInfoToBeSubmited = str_kycWithoutUploading;

                    if (strKycOtherInfoToBeSubmited.equals("[]")) {
                        strKycOtherInfoToBeSubmited = ".";
                    } else {
                        strKycOtherInfoToBeSubmited = convertKYCInfoJSONTOXML(strKycOtherInfoToBeSubmited);
                    }

                    showConfirmationDialog();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int validateStaticFields() {
        int validityOfError = 0;

        try {
            for (TextView errordata : errorStaticTxtViewList) {
                String strErrorTag = errordata.getTag().toString();
                JSONObject errorjresponse = new JSONObject(strErrorTag);
                String errorname = errorjresponse.getString("name");

                if ("Amount".equals(errorname)) {
                    if (edt_desiredamount.getText().toString().trim().equals("")) {
                        errordata.setVisibility(View.VISIBLE);
                        edt_desiredamount.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                        validityOfError = validityOfError + 1;
                    } else {
                        errordata.setVisibility(View.GONE);
                        edt_desiredamount.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return validityOfError;
    }

    private int validateOtherDetails() {
        int validityOfError = 0;

        try {
            JSONArray validateJSONArray = new JSONArray();

            kycWithoutUploadingJSONArray = new JSONArray();

            kycUploadingJSONArray = new JSONArray();

            if (otherDetailsJSONArray.length() <= 10) {
                validateJSONArray = otherDetailsJSONArray;
            } else if (otherDetailsJSONArray.length() >= 11) {
                validateJSONArray = otherDetailsJSONArray;
            }

            for (int i = 0; i < validateJSONArray.length(); i++) {

                JSONObject otherDetailsJsonObj = new JSONObject();

                JSONObject c = validateJSONArray.getJSONObject(i);
                String inputname = c.getString("name");
                String ismandatory = c.getString("mandatory");

                ismandatory = ismandatory.toUpperCase();

                //EDITTEXT
                for (EditText inputdata : editViewList) {
                    String f = inputdata.getTag().toString();
                    JSONObject jresponse = new JSONObject(f);
                    String name = jresponse.getString("name");
                    String datatype = jresponse.getString("datatype");

                    if (name.contains("*")) {
                        name = name.replace("*", "");
                    }

                    if (name.equals(inputname)) {
                        String inputres = inputdata.getText().toString();


                        if (inputres.trim().length() > 0) {
                            //place data in the object
                            otherDetailsJsonObj.put("name", inputname);
                            otherDetailsJsonObj.put("value", inputres);
                            kycWithoutUploadingJSONArray.put(otherDetailsJsonObj);
                        }

                        //if mandatory, need not empty
                        if (!ismandatory.equals("NO") && inputdata.getVisibility() == View.VISIBLE) {
                            if (inputres.trim().length() == 0) {
                                validityOfError = validityOfError + 1;

                                for (TextView errordata : errorOtherDetailsTextViewList) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.VISIBLE);
                                        inputdata.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                        break;
                                    }
                                }
                            } else {

                                if (datatype.toUpperCase().equals("MONEY") || datatype.toUpperCase().equals("NUMBER")) {

                                    if (!CommonFunctions.stringContainsNumber(inputres)
                                            || CommonFunctions.stringContainsTwoDots(inputres)) {
                                        validityOfError = validityOfError + 1;

                                        for (TextView errordata : errorOtherDetailsTextViewList) {
                                            String errorgetTag = errordata.getTag().toString();
                                            JSONObject errorjresponse = new JSONObject(errorgetTag);
                                            String errorname = errorjresponse.getString("name");
                                            if (errorname.equals(name)) {
                                                errordata.setVisibility(View.VISIBLE);
                                                inputdata.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                                break;
                                            }
                                        }
                                    } else {
                                        for (TextView errordata : errorOtherDetailsTextViewList) {
                                            String errorgetTag = errordata.getTag().toString();
                                            JSONObject errorjresponse = new JSONObject(errorgetTag);
                                            String errorname = errorjresponse.getString("name");

                                            if (errorname.equals(name)) {
                                                errordata.setVisibility(View.GONE);
                                                inputdata.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                                break;
                                            }
                                        }
                                    }
                                } else {
                                    for (TextView errordata : errorOtherDetailsTextViewList) {
                                        String errorgetTag = errordata.getTag().toString();
                                        JSONObject errorjresponse = new JSONObject(errorgetTag);
                                        String errorname = errorjresponse.getString("name");
                                        if (errorname.equals(name)) {
                                            errordata.setVisibility(View.GONE);
                                            inputdata.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                            break;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }

                //SPINNER
                for (LinearLayout spinnercontainer : spnViewListContainer) {
                    String strspinCont = spinnercontainer.getTag().toString();
                    JSONObject spinContResponse = new JSONObject(strspinCont);
                    String spincontname = spinContResponse.getString("name");

                    if (spincontname.contains("*")) {
                        spincontname = spincontname.replace("*", "");
                    }

                    if (spincontname.equals(inputname)) {
                        final int count = spinnercontainer.getChildCount();
                        for (int spincount = 0; spincount < count; spincount++) {
                            View spinView = spinnercontainer.getChildAt(spincount);
                            if (spinView instanceof Spinner) {
                                Spinner selectedval = (Spinner) spinView;

                                String f = selectedval.getTag().toString();
                                JSONObject jresponse = new JSONObject(f);
                                String name = jresponse.getString("name");

                                if (name.contains("*")) {
                                    name = name.replace("*", "");
                                }

                                if (name.equals(inputname)) {
                                    String selectedoptn = selectedval.getSelectedItem().toString();
                                    String id = String.valueOf(spnMap.get(selectedval.getSelectedItemPosition() + 1));

                                    if (selectedoptn.contains("*")) {
                                        selectedoptn = selectedoptn.replace("*", "");
                                    }

                                    if (!selectedoptn.contains("Select")) {
                                        otherDetailsJsonObj.put("name", inputname);
                                        //gkstoreres.put("value", id + "-" + selectedoptn);
                                        otherDetailsJsonObj.put("value", selectedoptn);
                                        kycWithoutUploadingJSONArray.put(otherDetailsJsonObj);

                                        //if mandatory, need not empty
                                        if (!ismandatory.equals("NO") && selectedval.getVisibility() == View.VISIBLE) {
                                            if (selectedoptn.equals("")) {
                                                validityOfError = validityOfError + 1;

                                                for (TextView errordata : errorOtherDetailsTextViewList) {
                                                    String errorgetTag = errordata.getTag().toString();
                                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                                    String errorname = errorjresponse.getString("name");
                                                    if (errorname.equals(name)) {
                                                        errordata.setVisibility(View.VISIBLE);
                                                        spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                                        break;
                                                    }
                                                }
                                            } else {
                                                for (TextView errordata : errorOtherDetailsTextViewList) {
                                                    String errorgetTag = errordata.getTag().toString();
                                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                                    String errorname = errorjresponse.getString("name");
                                                    if (errorname.equals(name)) {
                                                        errordata.setVisibility(View.GONE);
                                                        spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (!ismandatory.equals("NO") && selectedval.getVisibility() == View.VISIBLE) {
                                            validityOfError = validityOfError + 1;

                                            for (TextView errordata : errorOtherDetailsTextViewList) {
                                                String errorgetTag = errordata.getTag().toString();
                                                JSONObject errorjresponse = new JSONObject(errorgetTag);
                                                String errorname = errorjresponse.getString("name");
                                                if (errorname.equals(name)) {
                                                    errordata.setVisibility(View.VISIBLE);
                                                    spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                                    break;
                                                }
                                            }
                                        } else {
                                            for (TextView errordata : errorOtherDetailsTextViewList) {
                                                String errorgetTag = errordata.getTag().toString();
                                                JSONObject errorjresponse = new JSONObject(errorgetTag);
                                                String errorname = errorjresponse.getString("name");
                                                if (errorname.equals(name)) {
                                                    errordata.setVisibility(View.GONE);
                                                    spinnercontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        break;
                    }
                }

                //CALENDAR
                for (TextView b : txtViewCalendarList) {
                    String f = b.getTag().toString();
                    JSONObject jresponse = new JSONObject(f);
                    String name = jresponse.getString("name");

                    if (name.contains("*")) {
                        name = name.replace("*", "");
                    }

                    if (name.equals(inputname)) {
                        String buttonres = b.getText().toString();

                        if (!buttonres.equals("")) {
                            //place data in the object
                            otherDetailsJsonObj.put("name", inputname);
                            otherDetailsJsonObj.put("value", buttonres);
                            kycWithoutUploadingJSONArray.put(otherDetailsJsonObj);
                        }

                        //if mandatory, need not empty
                        if (!ismandatory.equals("NO") && b.getVisibility() == View.VISIBLE) {
                            if (buttonres.equals("")) {
                                validityOfError = validityOfError + 1;

                                for (TextView errordata : errorOtherDetailsTextViewList) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.VISIBLE);
                                        b.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                        break;
                                    }
                                }
                            } else {
                                for (TextView errordata : errorOtherDetailsTextViewList) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.GONE);
                                        b.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                //UPLOAD
                for (ImageView imgView : imgViewList) {
                    String imgViewTag = imgView.getTag().toString();
                    JSONObject jresponse = new JSONObject(imgViewTag);
                    String name = jresponse.getString("name");
                    String struri = jresponse.getString("uri");

                    if (name.contains("*")) {
                        name = name.replace("*", "");
                    }

                    if (name.equals(inputname)) {

                        if (struri != null && !struri.trim().equals("")) {
                            //place data in the object
                            otherDetailsJsonObj.put("name", inputname);
                            otherDetailsJsonObj.put("value", struri);
                            kycUploadingJSONArray.put(otherDetailsJsonObj);
                        }


                        //if mandatory, need not empty
                        if (!ismandatory.equals("NO") && imgView.getVisibility() == View.VISIBLE) {

                            if (struri == null || struri.equals("")) {
                                validityOfError = validityOfError + 1;

                                for (TextView errordata : errorOtherDetailsTextViewList) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");
                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.VISIBLE);
                                        imgView.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                                        break;
                                    }
                                }

                            } else {
                                for (TextView errordata : errorOtherDetailsTextViewList) {
                                    String errorgetTag = errordata.getTag().toString();
                                    JSONObject errorjresponse = new JSONObject(errorgetTag);
                                    String errorname = errorjresponse.getString("name");

                                    if (errorname.equals(name)) {
                                        errordata.setVisibility(View.GONE);
                                        imgView.setBackground(ContextCompat.getDrawable(getViewContext(), R.color.colorwhite));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return validityOfError;
    }

    //CONVERT THE JSON INTO XML (REMARKS)
    private void convertKYCInfoJSONTOXML() {
        try {
            if (kycWithoutUploadingJSONArray.length() > 0) {
                String countTag = "";
                StringBuilder numberCountTag = new StringBuilder();

                for (int i = 0; i < kycWithoutUploadingJSONArray.length(); i++) {

                    JSONObject jsonObject = kycWithoutUploadingJSONArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String value = jsonObject.getString("value");
                    int count = i + 1;

                    numberCountTag.append("<").append(count).append(">");

                    numberCountTag.append(name).append(":::").append(value);

                    numberCountTag.append("</").append(count).append(">").append("\n");

                    countTag = "<count>" + count + "</count>" + "\n";
                }

                strKycOtherInfoToBeSubmited = countTag + numberCountTag.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String setUpUploadingInfo(String str_kycWithoutUploading, String str_kycUploading) {
        String str_upload = "";

        try {
            if (str_kycUploading != null) {
                if (!str_kycUploading.trim().equals("") &&
                        !str_kycUploading.trim().equals("[]")) {

                    JSONArray jsonKycInfo = new JSONArray();

                    JSONArray jsonKycWithoutUpload = new JSONArray(str_kycWithoutUploading);

                    if (jsonKycWithoutUpload.length() > 0) {
                        for (int i = 0; i < jsonKycWithoutUpload.length(); i++) {
                            JSONObject jsonObject = jsonKycWithoutUpload.getJSONObject(i);
                            jsonKycInfo.put(jsonObject);
                        }
                    }

                    JSONArray jsonKycUpload = new JSONArray(str_kycUploading);

                    if (jsonKycUpload.length() > 0) {
                        for (int i = 0; i < jsonKycUpload.length(); i++) {
                            JSONObject jsonObject = jsonKycUpload.getJSONObject(i);
                            jsonKycInfo.put(jsonObject);
                        }
                    }

                    if (jsonKycInfo.length() > 0) {
                        str_upload = jsonKycInfo.toString();
                    }

                } else {
                    str_upload = str_kycWithoutUploading;
                }
            } else {
                str_upload = str_kycWithoutUploading;
            }

            if (str_upload.equals("[]")) {
                str_upload = ".";
            }

            str_upload = convertKYCInfoJSONTOXML(str_upload);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return str_upload;
    }

    //CONVERT THE KYC INFO
    private String convertKYCInfoJSONTOXML(String kycinfo) {
        try {

            if (kycinfo != null) {
                if (!kycinfo.trim().equals("") &&
                        !kycinfo.trim().equals("NONE") &&
                        !kycinfo.trim().equals(".")) {

                    JSONArray otherDetailsJSONArray = new JSONArray(kycinfo);

                    if (otherDetailsJSONArray.length() > 0) {
                        String countTag = "";
                        StringBuilder numberCountTag = new StringBuilder();

                        for (int i = 0; i < otherDetailsJSONArray.length(); i++) {

                            JSONObject jsonObject = otherDetailsJSONArray.getJSONObject(i);
                            String name = jsonObject.getString("name");
                            String value = jsonObject.getString("value");
                            int count = i + 1;

                            numberCountTag.append("<").append(count).append(">");

                            numberCountTag.append(name).append(":::").append(value);

                            numberCountTag.append("</").append(count).append(">").append("\n");

                            countTag = "<count>" + count + "</count>" + "\n";
                        }

                        kycinfo = countTag + numberCountTag.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kycinfo;
    }

    //DIALOG FOR DISCOUNT (NEW)
    private void showConfirmationDialog() {
        GlobalDialogs globalDialogs = new GlobalDialogs(getViewContext());

        globalDialogs.createDialog("Confirm Loan Request", "Confirm Loan Request",
                "CANCEL", "CONFIRM", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.DOUBLETEXTVIEW);

        globalDialogs.showContentTitle();

        View closebtn = globalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
            }
        });

        List<String> titleList = new ArrayList<>();
        titleList.add("Loan Type");
        titleList.add("Desired Amount");

        List<String> contentList = new ArrayList<>();
        contentList.add(CommonFunctions.replaceEscapeData(loantype));
        contentList.add(CommonFunctions.currencyFormatter(desiredamount));


        LinearLayout linearLayout = globalDialogs.setContentDoubleTextView(
                titleList,
                new GlobalDialogsObject(R.color.color_908F90, 18, Gravity.START),
                contentList,
                new GlobalDialogsObject(R.color.color_23A8F6, 18, Gravity.END)
        );

        View btndoubleone = globalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalDialogs.dismiss();
                hideProgressDialog();
                setUpProgressLoaderDismissDialog();
            }
        });

        View btndoubletwo = globalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!str_kycUploading.trim().equals("") &&
                        !str_kycUploading.trim().equals("[]")) {
                    setuploadPhotostoAWS(str_kycUploading);
                } else {
                    callMainApi();
                }

                globalDialogs.dismiss();
            }
        });
    }

    //---------------------------API---------------------------------
    private void callMainApi() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Processing request", "Please wait...");
            //applyCoopLoan(applyCoopLoanSession);
            applyCoopLoanV2();
        } else {
            showErrorGlobalDialogs();
        }
    }

    private void applyCoopLoan(Callback<GenericResponse> applyCoopLoanCallback) {
        Call<GenericResponse> applycooploan = RetrofitBuild.getCoopAssistantAPI(getViewContext())
                .applyCoopLoanCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        servicecode,
                        loanid,
                        desiredamount,
                        strKycOtherInfoToBeSubmited,
                        CommonVariables.devicetype);
        applycooploan.enqueue(applyCoopLoanCallback);
    }

    private final Callback<GenericResponse> applyCoopLoanSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {
                    try {
                        if (distributorid.equals("") || distributorid.equals(".")
                                || resellerid.equals("") || resellerid.equals(".")) {
                            checkIfReseller = false;
                        } else {
                            checkIfReseller = true;
                        }

                        showConfirmSuccessDialog(response.body().getMessage());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (response.body().getStatus().equals("104")) {
                    showAutoLogoutDialog(response.body().getMessage());
                } else {
                    showErrorGlobalDialogs(response.body().getMessage());

                }

            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showErrorGlobalDialogs();
        }
    };

    private void showConfirmSuccessDialog(String message) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        boolean isGKNegosyoModal;
        if (checkIfReseller) {
            isGKNegosyoModal = false;
        } else {
            isGKNegosyoModal = true;
        }

        dialog.createDialog("SUCCESS", message,
                "Close", "", ButtonTypeEnum.SINGLE,
                isGKNegosyoModal, false, DialogTypeEnum.SUCCESS);

        dialog.showContentTitle();

        dialog.hideCloseImageButton();

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dialog.proceedtoCoopHome();
            }
        });
    }

    //-----------------------------UPLOAD-----------------------------------
    private void setuploadPhotostoAWS(String str_upload) {
        try {
            JSONArray uploadDetailsJSONArray = new JSONArray(str_upload);

            if (bodyParts != null) {
                if (bodyParts.size() > 0) {
                    bodyParts.clear();
                }
            }

            if (uploadDetailsJSONArray.length() > 0) {
                for (int i = 0; i < uploadDetailsJSONArray.length(); i++) {
                    JSONObject jsonObj = uploadDetailsJSONArray.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String value = jsonObj.getString("value");

                    if (value.contains("file") || value.contains("content")) {
                        Uri photoUri = Uri.parse(value);

                        bodyParts.add(prepareFilePart("upload_album", CommonFunctions.removeSpecialCharacters(name), photoUri));
                    }
                }

                callUploadAPI();

            }

        } catch (Exception e) {
            e.printStackTrace();
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

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, String uriName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri

        //File file = new File(fileUri.getPath());

        //coopid-cooplogo-timestamp.file extention

        String filename = userid + "-" + uriName + "-" + Calendar.getInstance().getTimeInMillis() + "-coop-imageid.jpg";

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "/" + filename);

        createFile(getViewContext(), fileUri, file);

        file = CommonFunctions.compressImage(getViewContext(),file);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private void callUploadAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showProgressDialog("Uploading image", "Please wait...");
            uploadAlbum();
        } else {
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void uploadAlbum() {
        RequestBody bodyBorrowerID = RequestBody.create(MediaType.parse("text/plain"), borrowerid);
        RequestBody bodyIMEI = RequestBody.create(MediaType.parse("text/plain"), imei);
        RequestBody bodyBucketName = RequestBody.create(MediaType.parse("text/plain"), CommonVariables.BUCKETNAME);
        RequestBody bodyUserID = RequestBody.create(MediaType.parse("text/plain"), userid);
        RequestBody bodySession = RequestBody.create(MediaType.parse("text/plain"), sessionid);
        RequestBody bodyAuthCode = RequestBody.create(MediaType.parse("text/plain"), CommonFunctions.getSha1Hex(imei + userid + sessionid));
        RequestBody bodyCommand = RequestBody.create(MediaType.parse("text/plain"), "UPLOAD COOP IMAGE");

        Call<GenericResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .uploadAlbum(
                        bodyCommand,
                        bodyParts,
                        bodyBorrowerID,
                        bodyIMEI,
                        bodyBucketName,
                        bodyUserID,
                        bodySession,
                        bodyAuthCode
                );
        call.enqueue(uploadAlbumCallBack);

    }

    private Callback<GenericResponse> uploadAlbumCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            try {
                hideProgressDialog();
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        String imageurl = response.body().getData();
                        replaceFileToUploadedAWS(imageurl);
                    } else if (response.body().getStatus().equals("004")) {
                        showReUploadDialogNew(str_kycUploading);
                    } else {
                        showErrorGlobalDialogs(response.body().getMessage());
                    }

                } else {
                    if (errBody.string().contains("!DOCTYPE html")) {
                        showReUploadDialogNew(str_kycUploading);
                    } else {
                        showErrorGlobalDialogs();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            hideProgressDialog();
        }
    };

    private void replaceFileToUploadedAWS(String imageurl) {
        try {
            JSONArray uploadArray = new JSONArray(imageurl);

            JSONArray kycArray = new JSONArray(str_kycUploading);

            JSONArray finalArray = new JSONArray();

            if (uploadArray.length() > 0) {
                for (int i = 0; i < uploadArray.length(); i++) {
                    JSONObject uploadObject = uploadArray.getJSONObject(i);
                    String uploadname = uploadObject.getString("name");
                    String uploadvalue = uploadObject.getString("value");

                    for (int y = 0; y < kycArray.length(); y++) {
                        JSONObject kycObject = kycArray.getJSONObject(y);
                        String kycname = kycObject.getString("name");
                        String kycvalue = kycObject.getString("value");

                        if (uploadname.contains(CommonFunctions.removeSpecialCharacters(kycname))) {
                            JSONObject finalObject = new JSONObject();
                            finalObject.put("name", kycname);
                            finalObject.put("value", uploadvalue);
                            finalArray.put(finalObject);
                            break;
                        }
                    }
                }

                String str_kycAfterAWSUpload = finalArray.toString();

                strKycOtherInfoToBeSubmited = setUpUploadingInfo(str_kycWithoutUploading, str_kycAfterAWSUpload);

                callMainApi();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showReUploadDialogNew(String str_upload) {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "Failed in uploading your images. Please try again.",
                "Cancel", "Try Again", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.FAILED);

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View btndoubleone = dialog.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        View btndoubletwo = dialog.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                setuploadPhotostoAWS(str_upload);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                onImageResult(fileUri);
                fileUri = null;
            } else if (requestCode == SELECT_FILE) {
                onImageResult(data.getData());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_action: {
                if (!SClick.check(SClick.BUTTON_CLICK)) return;

                validateInformationBeforeProceeding();
            }
        }
    }


    /**************
     * SECURITY UPDATE *
     * AS OF           *
     * FEBRUARY 2020    *
     * *****************/

    private void applyCoopLoanV2(){

        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        parameters.put("imei", imei);
        parameters.put("userid", userid);
        parameters.put("borrowerid", borrowerid);
        parameters.put("servicecode", servicecode);
        parameters.put("loanid",loanid);
        parameters.put("amount",desiredamount);
        parameters.put("kycotherinfo", strKycOtherInfoToBeSubmited);
        parameters.put("devicetype", CommonVariables.devicetype);


        LinkedHashMap indexAuthMapObject;
        indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(),parameters, sessionid);
        String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
        index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

        parameters.put("index", index);
        String paramJson = new Gson().toJson(parameters, Map.class);

        //ENCRYPTION
        authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
        keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "applyCoopLoanV2");
        param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

        applyCoopLoanV2Object(applyCoopLoanV2Callback);

    }

    private void applyCoopLoanV2Object(Callback<GenericResponse> genericResponseCallback){
        Call<GenericResponse> call = RetrofitBuilder.getCoopAssistantV2API(getViewContext())
                .applyCoopLoanV2(authenticationid,sessionid,param);
        call.enqueue(genericResponseCallback);
    }

    private final Callback<GenericResponse> applyCoopLoanV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            hideProgressDialog();
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String message  = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                        if (distributorid.equals("") || distributorid.equals(".")
                                || resellerid.equals("") || resellerid.equals(".")) {
                            checkIfReseller = false;
                        } else {
                            checkIfReseller = true;
                        }

                        showConfirmSuccessDialog(message);

                } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                } else {
                    showErrorGlobalDialogs(message);
                }

            } else {
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showErrorGlobalDialogs();
        }
    };



}
