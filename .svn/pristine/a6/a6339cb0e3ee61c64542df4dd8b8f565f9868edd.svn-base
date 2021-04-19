package com.goodkredit.myapplication.fragments.coopassistant.nonmember;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.activities.coopassistant.nonmember.CoopAssistantApplyMemberActivity;
import com.goodkredit.myapplication.activities.coopassistant.nonmember.CoopAssistantPMESActivity;
import com.goodkredit.myapplication.activities.coopassistant.nonmember.CoopAssistantTermsandConditionsActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.interfaces.onBackPressedCustom;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantInformation;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantMembershipType;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.V2Utils;
import com.shuhart.stepview.StepView;

import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import hk.ids.gws.android.sclick.SClick;

public class CoopAssistantApplicationFormFragment extends BaseFragment implements View.OnClickListener, onBackPressedCustom {
    //COMMON
    private String sessionid = "";
    private String imei = "";
    private String userid = "";
    private String borrowerid = "";
    private String authcode = "";

    //DATABASE HANDLER
    private DatabaseHandler mdb;

    //DELAY ONCLICKS
    private long mLastClickTime = 0;

    private ArrayList<CoopAssistantMembershipType> coopAssistantMembershipList;
    private String kycotherinfo = "";

    //STATIC FIELDS
    private NestedScrollView home_body;
    private StepView stepView;
    private LinearLayout layout_newapplication_fields;
    private LinearLayout surname_container;
    private EditText edt_surname;
    private LinearLayout givenname_container;
    private EditText edt_givenname;
    private LinearLayout middlename_container;
    private EditText edt_middlename;
    private LinearLayout mobileno_container;
    private EditText edt_mobilenumber;
    private LinearLayout emailadddress_container;
    private EditText edt_emailaddress;
    private LinearLayout selection_container;
    private LinearLayout gender_container;
    private Spinner spn_gender;
    private String str_gender_spinner = "";
    private LinearLayout birthdate_container;
    private TextView txv_cal_birthdate;
    private String str_cal_birthdate = "";
    private LinearLayout civilstatus_container;
    private Spinner spn_civilstatus;
    private LinearLayout nationality_container;
    private EditText edt_nationality;
    private LinearLayout hometownaddress_container;
    private EditText edt_hometown;
    private LinearLayout currentaddress_container;
    private EditText edt_currentadd;
    private LinearLayout occupation_container;
    private EditText edt_occupation;
    private LinearLayout referredbyname_container;
    private EditText edt_referredbyname;
    private LinearLayout btn_action_container;
    private TextView btn_action;

    //DYNAMIC FIELDS
    private LinearLayout layout_custom_fields_page2;
    private LinearLayout layout_custom_fields_page3;

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
    private JSONArray checkPage2JSONArray = new JSONArray();

    private JSONArray kycWithoutUploadingJSONArray = new JSONArray();
    private JSONArray kycUploadingJSONArray = new JSONArray();

    //ERROR TEXTVIEW
    private List<TextView> errorStaticTxtViewList = new ArrayList<TextView>();
    private List<TextView> errorOtherDetailsTextViewList = new ArrayList<TextView>();

    private List<String> steps = new ArrayList<>();
    private int currentStep = 0;
    private int nextStep = 0;

    //TERMS AND CONDITIONS & PMES
    //COOP INFORMATION
    private List<CoopAssistantInformation> coopInformationList = new ArrayList<>();
    private String coopid = "";
    private String coopname = "";
    private String coopTermsandConditions = "";
    private String coopPMES = "";

    private String links = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coopassistant_application_form, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        home_body = view.findViewById(R.id.home_body);

        stepView = view.findViewById(R.id.step_view);

        layout_newapplication_fields = view.findViewById(R.id.layout_newapplication_fields);

        surname_container = view.findViewById(R.id.surname_container);
        edt_surname = view.findViewById(R.id.edt_surname);

        givenname_container = view.findViewById(R.id.givenname_container);
        edt_givenname = view.findViewById(R.id.edt_givenname);

        middlename_container = view.findViewById(R.id.middlename_container);
        edt_middlename = view.findViewById(R.id.edt_middlename);

        mobileno_container = view.findViewById(R.id.mobileno_container);
        edt_mobilenumber = view.findViewById(R.id.edt_mobilenumber);

        emailadddress_container = view.findViewById(R.id.emailadddress_container);
        edt_emailaddress = view.findViewById(R.id.edt_emailaddress);

        selection_container = view.findViewById(R.id.selection_container);

        gender_container = view.findViewById(R.id.gender_container);
        spn_gender = view.findViewById(R.id.spn_gender);

        birthdate_container = view.findViewById(R.id.birthdate_container);
        txv_cal_birthdate = view.findViewById(R.id.txv_cal_birthdate);

        civilstatus_container = view.findViewById(R.id.civilstatus_container);
        spn_civilstatus = view.findViewById(R.id.spn_civilstatus);

        nationality_container = view.findViewById(R.id.nationality_container);
        edt_nationality = view.findViewById(R.id.edt_nationality);

        hometownaddress_container = view.findViewById(R.id.hometownaddress_container);
        edt_hometown = view.findViewById(R.id.edt_hometown);

        currentaddress_container = view.findViewById(R.id.currentaddress_container);
        edt_currentadd = view.findViewById(R.id.edt_currentadd);

        occupation_container = view.findViewById(R.id.occupation_container);
        edt_occupation = view.findViewById(R.id.edt_occupation);

        referredbyname_container = view.findViewById(R.id.referredbyname_container);
        edt_referredbyname = view.findViewById(R.id.edt_referredbyname);

        layout_custom_fields_page2 = view.findViewById(R.id.layout_custom_fields_page2);
        layout_custom_fields_page3 = view.findViewById(R.id.layout_custom_fields_page3);

        btn_action_container = view.findViewById(R.id.btn_action_container);
        btn_action_container.setVisibility(View.VISIBLE);
        btn_action = view.findViewById(R.id.btn_action);
        btn_action.setOnClickListener(this);
    }

    private void initData() {
        //COMMON, REGISTRATION
        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        if (getArguments() != null) {
            coopAssistantMembershipList = getArguments().getParcelableArrayList("MemberShipSelected");

            CoopAssistantMembershipType coopAssistantMembershipType = null;

            for (CoopAssistantMembershipType membershipType : coopAssistantMembershipList) {
                coopAssistantMembershipType = membershipType;
            }

            if (coopAssistantMembershipType != null) {
                kycotherinfo = coopAssistantMembershipType.getKYCOtherInfo();
            }
        }

        getCoopInformation();

        addItemsOnSpinner();

        createDatePicker();

        setStaticErrorTextViewForStaticFields();

        addXMLDetails();

        autoSetBorrowerInformationtoStaticFields();

        setStaticFieldsOnlyForDisabledViews();
    }

    //COOP INFORMATION
    private void getCoopInformation() {
        coopInformationList = PreferenceUtils.getCoopInformationListPreference(getViewContext(), CoopAssistantInformation.KEY_COOPINFORMATION);

        if (coopInformationList != null) {
            if (coopInformationList.size() > 0) {
                for (CoopAssistantInformation information : coopInformationList) {
                    coopTermsandConditions = information.getTermsAndConditions();
                    coopPMES = information.getPMES();
                    links = information.getNotes1();
                }
            }
        }
    }

    private void addItemsOnSpinner() {
        try {
            String name = "";

            //GENDER
            List<String> spnGenderList = new ArrayList<>();
            spnGenderList.add("Select Gender");
            spnGenderList.add("Male");
            spnGenderList.add("Female");

            ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(getViewContext(),
                    R.layout.activity_custom_spinner, spnGenderList) {

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

            genderAdapter.setDropDownViewResource(R.layout.activity_custom_spinner);

            spn_gender.setAdapter(genderAdapter);

            spn_gender.setTag("Gender");

            spn_gender.setOnItemSelectedListener(spn_listener);

            //CIVIL STATUS
            List<String> spnCivilStatus = new ArrayList<>();
            spnCivilStatus.add("Select Civil Status");
            spnCivilStatus.add("Single");
            spnCivilStatus.add("Married");
            spnCivilStatus.add("Divorced");
            spnCivilStatus.add("Annulled");
            spnCivilStatus.add("Separated");
            spnCivilStatus.add("Widowed");

            ArrayAdapter<String> civilStatusAdapter = new ArrayAdapter<String>(getViewContext(),
                    R.layout.activity_custom_spinner, spnCivilStatus) {

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

            civilStatusAdapter.setDropDownViewResource(R.layout.activity_custom_spinner);

            spn_civilstatus.setAdapter(civilStatusAdapter);

            spn_civilstatus.setTag("Civil Status");

            spn_civilstatus.setOnItemSelectedListener(spn_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AdapterView.OnItemSelectedListener spn_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View view, int position, long id) {
            try {
                String strSpinner = parentView.getItemAtPosition(position).toString();

                try {
                    if (errorStaticTxtViewList.size() > 0) {
                        for (TextView errordata : errorStaticTxtViewList) {

                            String strErrorTag = errordata.getTag().toString();
                            JSONObject errorjresponse = new JSONObject(strErrorTag);
                            String errorname = errorjresponse.getString("name");

                            String spnerrorname = parentView.getTag().toString();

                            if (errorname.equals(spnerrorname)) {
                                parentView.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                errordata.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //Set Date to 18 years ago from current date
    private void setDateTo18YearsAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
    }

    private void createDatePicker() {
        txv_cal_birthdate.setOnClickListener(datepickerListener);
    }

    private View.OnClickListener datepickerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar now = Calendar.getInstance();

            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            String date = setDateOnly(year, monthOfYear, dayOfMonth);

                            str_cal_birthdate = date;

                            txv_cal_birthdate.setText(date);

                            try {
                                if (errorStaticTxtViewList.size() > 0) {
                                    for (TextView errordata : errorStaticTxtViewList) {
                                        String strErrorTag = errordata.getTag().toString();
                                        JSONObject errorjresponse = new JSONObject(strErrorTag);
                                        String errorname = errorjresponse.getString("name");
                                        if (errorname.equals("Birth Date")) {
                                            txv_cal_birthdate.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                                            errordata.setVisibility(View.GONE);
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    now.get(Calendar.YEAR) - 18,
                    1,
                    1
            );

            dpd.setMaxDate(now);
            dpd.show(((CoopAssistantHomeActivity) mContext).getFragmentManager(), "dialog");
        }
    };

    private String setDateOnly(int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";

        int mm = monthOfYear + 1;
        if (mm <= 9) {
            month = "0".concat(String.valueOf(mm));
        } else {
            month = String.valueOf(mm);
        }

        if (dayOfMonth <= 9) {
            day = "0".concat(String.valueOf(dayOfMonth));
        } else {
            day = String.valueOf(dayOfMonth);
        }

        return year + "-" + month + "-" + day;
    }

    private void setStaticErrorTextViewForStaticFields() {
        edt_surname.setTag("Surname");
        edt_givenname.setTag("Given Name");
        //edt_middlename.setTag("Middle Name");
        edt_mobilenumber.setTag("Mobile Number");
        edt_emailaddress.setTag("Email Address");
        spn_gender.setTag("Gender");
        txv_cal_birthdate.setTag("Birth Date");
        spn_civilstatus.setTag("Civil Status");
        edt_nationality.setTag("Nationality");
        edt_hometown.setTag("Home Town Address");
        edt_currentadd.setTag("Current Address");
        edt_occupation.setTag("Occupation");
        //edt_referredbyname.setTag("Referrer Name");

        createErrorTextView(edt_surname.getTag().toString(), "YES", 1, edt_surname.getTag().toString());
        createErrorTextView(edt_givenname.getTag().toString(), "YES", 1, edt_givenname.getTag().toString());
        //createErrorTextView(edt_middlename.getTag().toString(), "YES", 1, edt_middlename.getTag().toString());
        createErrorTextView(edt_mobilenumber.getTag().toString(), "YES", 1, edt_mobilenumber.getTag().toString());
        createErrorTextView(edt_emailaddress.getTag().toString(), "YES", 1, edt_emailaddress.getTag().toString());
        createErrorTextView(spn_gender.getTag().toString(), "YES", 1, spn_gender.getTag().toString());
        createErrorTextView(txv_cal_birthdate.getTag().toString(), "YES", 1, txv_cal_birthdate.getTag().toString());
        createErrorTextView(spn_civilstatus.getTag().toString(), "YES", 1, spn_civilstatus.getTag().toString());
        createErrorTextView(edt_nationality.getTag().toString(), "YES", 1, edt_nationality.getTag().toString());
        createErrorTextView(edt_hometown.getTag().toString(), "YES", 1, edt_hometown.getTag().toString());
        createErrorTextView(edt_currentadd.getTag().toString(), "YES", 1, edt_currentadd.getTag().toString());
        createErrorTextView(edt_occupation.getTag().toString(), "YES", 1, edt_occupation.getTag().toString());
        //createErrorTextView(edt_referredbyname.getTag().toString(), "YES", 1, edt_referredbyname.getTag().toString());

        try {
            setStaticEdtTextErrorTextWatcher();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //SET THE STATIC FIELDS FOR INFORMATION
    private void autoSetBorrowerInformationtoStaticFields() {
        V2Subscriber mSubscriber = mdb.getSubscriber(mdb);

        if (mSubscriber != null) {
            edt_surname.setText(mSubscriber.getLastName());
            edt_givenname.setText(mSubscriber.getFirstName());
            edt_middlename.setText(mSubscriber.getMiddleName());
            edt_emailaddress.setText(mSubscriber.getEmailAddress());
            edt_mobilenumber.setText(mSubscriber.getMobileNumber());

            String gender = mSubscriber.getGender();

            if (gender.toUpperCase().equals("MALE")) {
                spn_gender.setSelection(1);
            } else if (gender.toUpperCase().equals("FEMALE")) {
                spn_gender.setSelection(2);
            } else {
                spn_gender.setSelection(0);
            }

            String currentaddress = CommonFunctions.replaceEscapeData(mSubscriber.getStreetAddress())
                    + ", " + CommonFunctions.replaceEscapeData(mSubscriber.getCity()
                    + ", " + CommonFunctions.replaceEscapeData(mSubscriber.getCountry())

            );

            edt_currentadd.setText(currentaddress);
        }

        autoFillFormForDevelopment();
    }

    private void autoFillFormForDevelopment() {
        if (CommonVariables.isDebugMode) {
            txv_cal_birthdate.setText("1990-05-23");
            spn_civilstatus.setSelection(1);
            edt_nationality.setText("Japanese");
            edt_hometown.setText("Tokyo Japan");
            edt_occupation.setText("Journalist");
            //edt_referredbyname.setText("Jon Snow");
        }
    }

    //ADD LISTENER TO OTHER DETAILS/PASSED XML
    private void setStaticEdtTextErrorTextWatcher() throws JSONException {

        for (TextView errordata : errorStaticTxtViewList) {
            String strErrorTag = errordata.getTag().toString();
            JSONObject errorjresponse = new JSONObject(strErrorTag);
            String errorname = errorjresponse.getString("name");

            switch (errorname) {
                case "Surname":
                    edt_surname.addTextChangedListener(new TextWatcher(edt_surname, errordata));
                    break;
                case "Given Name":
                    edt_givenname.addTextChangedListener(new TextWatcher(edt_givenname, errordata));
                    break;
                case "Middle Name":
                    //edt_middlename.addTextChangedListener(new TextWatcher(edt_middlename, errordata));
                    break;
                case "Mobile Number":
                    edt_mobilenumber.addTextChangedListener(new TextWatcher(edt_mobilenumber, errordata));
                    break;
                case "Email Address":
                    edt_emailaddress.addTextChangedListener(new TextWatcher(edt_emailaddress, errordata));
                    break;
                case "Nationality":
                    edt_nationality.addTextChangedListener(new TextWatcher(edt_nationality, errordata));
                    break;
                case "Home Town Address":
                    edt_hometown.addTextChangedListener(new TextWatcher(edt_hometown, errordata));
                    break;
                case "Current Address":
                    edt_currentadd.addTextChangedListener(new TextWatcher(edt_currentadd, errordata));
                    break;
                case "Occupation":
                    edt_occupation.addTextChangedListener(new TextWatcher(edt_occupation, errordata));
                    break;
//                case "Referrer Name":
//                    edt_referredbyname.addTextChangedListener(new TextWatcher(edt_referredbyname, errordata));
//                    break;
            }
        }
    }

    //THIS IS FOR STATIC FIELDS THAT WILL OVERRIDE THE TEXTWATCHER BORDER
    private void setStaticFieldsOnlyForDisabledViews() {
        edt_mobilenumber.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_disabled));
    }

    private void addXMLDetails() {
        try {

            steps = new ArrayList<>();

            String count = CommonFunctions.parseXML(kycotherinfo, "count");
            int page = 1;

            if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                if (Integer.parseInt(count) > 0) {
                    steps.add("");
                    for (int i = 1; i <= Integer.parseInt(count); i++) {
                        String field = CommonFunctions.parseXML(kycotherinfo, String.valueOf(i));
                        String[] fieldarr = field.split(":::");

                        if (i == 1) {
                            steps.add("");
                            page = 2;
                        } else if (i == 11) {
                            steps.add("");
                            page = 3;
                        }

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

                                    if (i <= 10) {
                                        checkPage2JSONArray.put(obj);
                                    }

                                    //1. Create TITLE
                                    if (!mandatory.equals("NO")) {
                                        name = name + "*";
                                    }

                                    if (inputtype.contains("SELECT")) {
                                        createTextView(name, mandatory, page);

                                        createSpinner(name, inputtype, mandatory, page);

                                        createErrorTextView(name, mandatory, page, "");

                                    } else if (inputtype.contains("CALENDAR")) {

                                        createTextView(name, mandatory, page);

                                        createCalendarView(name, mandatory, page);

                                        createErrorTextView(name, mandatory, page, "");

                                    } else if (inputtype.contains("UPLOAD")) {

                                        createTextView(name, mandatory, page);

                                        createUploadView(name, mandatory, page);

                                        createErrorTextView(name, mandatory, page, "");

                                    } else {//TEXT VIEW
                                        createTextView(name, mandatory, page);

                                        createEditText(name, datatype, mandatory, page);

                                        createErrorTextView(name, mandatory, page, "");
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

            stepView.setSteps(steps);

            stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
                @Override
                public void onStepClick(int step) {
//                    currentStep = step;
//                    stepView.go(currentStep, true);
//                    setStepPage();
                    if (currentStep > step) {
                        currentStep--;
                        stepView.done(false);
                        stepView.go(currentStep, true);
                        setStepPage();
                    } else if (currentStep < step) {
                        validateInformationBeforeProceeding();
                    }
                }
            });

            if (steps.size() > 0) {
                btn_action.setText("NEXT");
            } else {
                btn_action.setText("PROCEED");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //CREATES THE TEXTVIEW (XML DETAILS)
    private void createTextView(String name, String mandatory, int page) {
        try {

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

            JSONObject txtviewtag = new JSONObject();
            txtviewtag.put("name", name);
            txtviewtag.put("mandatory", mandatory);
            txtName.setTag(txtviewtag);

            txtViewList.add(txtName);

            if (mandatory.equals("YES") || mandatory.equals("NO")) {
                txtName.setVisibility(View.VISIBLE);
            } else {
                txtName.setVisibility(View.GONE);
            }

            if (page == 2) {
                layout_custom_fields_page2.addView(txtName, layoutParams);
            } else if (page == 3) {
                layout_custom_fields_page3.addView(txtName, layoutParams);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE EDITEXT (XML DETAILS)
    private void createEditText(String name, String datatype, String mandatory, int page) {
        try {

            EditText edtView = new EditText(getViewContext());
            edtView.setTypeface(V2Utils.setFontInput(getViewContext(), V2Utils.ROBOTO_REGULAR));

            int paddingall = CommonFunctions.converttoDP(getViewContext(), 10);

            edtView.setPadding(paddingall, paddingall, paddingall, paddingall);
            String gray = "#49494A";
            edtView.setTextColor(Color.parseColor(gray));
            edtView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));
            edtView.setTextSize(15);
            edtView.setSingleLine();

            JSONObject edittexttag = new JSONObject();
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

            if (page == 2) {
                layout_custom_fields_page2.addView(edtView);
            } else if (page == 3) {
                layout_custom_fields_page3.addView(edtView);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE SPINNER (XML DETAILS)
    private void createSpinner(String name, String inputtype, String mandatory, int page) {
        //2. Create spinner
        try {

            ArrayList<String> spinnerArray = new ArrayList<String>();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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

            JSONObject spinnertag = new JSONObject();
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

            if (page == 2) {
                layout_custom_fields_page2.addView(spinnercontainer, layoutParams);
            } else if (page == 3) {
                layout_custom_fields_page3.addView(spinnercontainer, layoutParams);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //CREATES THE BUTTON (XML DETAILS)
    private void createCalendarView(String name, String mandatory, int page) {
        try {

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                    .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            final LinearLayout txtbuttoncontainer = new LinearLayout(getViewContext());
            txtbuttoncontainer.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
            TextView txtbtn = new TextView(getViewContext());

            int paddingall = CommonFunctions.converttoDP(getViewContext(), 10);

            txtbtn.setPadding(paddingall, paddingall, paddingall, paddingall);
            txtbtn.setFocusable(false);

            JSONObject txtbtntag = new JSONObject();
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

                                        String date = setDateOnly(year, monthOfYear, dayOfMonth);

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

            if (page == 2) {
                layout_custom_fields_page2.addView(txtbuttoncontainer);
            } else if (page == 3) {
                layout_custom_fields_page3.addView(txtbuttoncontainer);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //CREATES THE VIEW FOR UPLOAD (XML DETAILS)
    private void createUploadView(String name, String mandatory, int page) {
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

            if (page == 2) {
                layout_custom_fields_page2.addView(containerLayout, layoutParams);
            } else if (page == 3) {
                layout_custom_fields_page3.addView(containerLayout, layoutParams);
            }

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
                    Picasso.with(getViewContext()).load(uri)
                            .fit()
                            .centerInside()
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

    private void createErrorTextView(String name, String mandatory, int page, String staticFieldsTag) {
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

            if (page == 1) {
                if (!staticFieldsTag.trim().equals("")) {
                    errorStaticTxtViewList.add(txtView);

                    switch (staticFieldsTag) {
                        case "Surname":
                            surname_container.addView(txtView);
                            break;
                        case "Given Name":
                            givenname_container.addView(txtView);
                            break;
                        case "Middle Name":
                            //middlename_container.addView(txtView);
                            break;
                        case "Mobile Number":
                            mobileno_container.addView(txtView);
                            break;
                        case "Email Address":
                            emailadddress_container.addView(txtView);
                            break;
                        case "Gender":
                            gender_container.addView(txtView);
                            break;
                        case "Birth Date":
                            birthdate_container.addView(txtView);
                            break;
                        case "Civil Status":
                            civilstatus_container.addView(txtView);
                            break;
                        case "Nationality":
                            nationality_container.addView(txtView);
                            break;
                        case "Home Town Address":
                            hometownaddress_container.addView(txtView);
                            break;
                        case "Current Address":
                            currentaddress_container.addView(txtView);
                            break;
                        case "Occupation":
                            occupation_container.addView(txtView);
                            break;
                        case "Referrer Name":
                            referredbyname_container.addView(txtView);
                            break;
                    }
                }
            }
            if (page == 2) {
                errorOtherDetailsTextViewList.add(txtView);
                layout_custom_fields_page2.addView(txtView);
            } else if (page == 3) {
                errorOtherDetailsTextViewList.add(txtView);
                layout_custom_fields_page3.addView(txtView);
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
                        //DEFAULT VALUES
                        default:
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
    }

    private void validateInformationBeforeProceeding() {
        try {
            int notvalid = 0;

            //STRING MESSAGES
            notvalid = validateStaticFields();

            if (steps.size() > 0) {
                notvalid = notvalid + validateOtherDetails();

                if (btn_action.getText().equals("NEXT")) {
                    if (notvalid > 0) {
                        showWarningGlobalDialogs("There's something wrong with the entered fields. Please try again.");
                        hideKeyboard(getViewContext());
                    } else {
                        if (currentStep < stepView.getStepCount() - 1) {
                            currentStep++;
                            stepView.go(currentStep, true);
                        } else {
                            stepView.done(true);
                        }

                        setStepPage();
                    }
                } else {
                    if (notvalid > 0) {
                        showWarningGlobalDialogs("There's something wrong with the entered fields. Please try again.");
                        hideKeyboard(getViewContext());
                    } else {
                        proceedToNextPage();
                    }
                }
            } else {
                if (notvalid > 0) {
                    showWarningGlobalDialogs("There's something wrong with the entered fields. Please try again.");
                    hideKeyboard(getViewContext());
                } else {
                    proceedToNextPage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void proceedToNextPage() {
        Bundle args = new Bundle();
        args.putString("LastName", edt_surname.getText().toString());
        args.putString("FirstName", edt_givenname.getText().toString());
        args.putString("MiddleName", edt_middlename.getText().toString().trim().equals("") ? "." : edt_middlename.getText().toString());
        args.putString("MobileNumber", edt_mobilenumber.getText().toString());
        args.putString("EmailAddress", edt_emailaddress.getText().toString());
        args.putString("Gender", spn_gender.getSelectedItem().toString());
        args.putString("Birthdate", txv_cal_birthdate.getText().toString());
        args.putString("CivilStatus", spn_civilstatus.getSelectedItem().toString());
        args.putString("Nationality", edt_nationality.getText().toString());
        args.putString("HomeTown", edt_hometown.getText().toString());
        args.putString("CurrentAddress", edt_currentadd.getText().toString());
        args.putString("Occupation", edt_occupation.getText().toString());
        args.putString("ReferredbyName", edt_referredbyname.getText().toString());
        args.putParcelableArrayList("MemberShipSelected", coopAssistantMembershipList);
        args.putString("kycWithoutUploading", kycWithoutUploadingJSONArray.toString());
        args.putString("kycUploading", kycUploadingJSONArray.toString());

        if (coopPMES != null) {
            if (coopTermsandConditions != null) {
                if (!coopPMES.trim().isEmpty() && !coopPMES.trim().equals(".")) {
                    args.putString(CoopAssistantInformation.KEY_COOPPMES, coopPMES);
                    args.putString(CoopAssistantInformation.KEY_COOPTERMS, coopTermsandConditions);
                    args.putString("links", links == null ? "" : links);
                    CoopAssistantPMESActivity.start(getViewContext(), args);
                } else if (!coopTermsandConditions.isEmpty() && !coopTermsandConditions.equals(".")) {
                    args.putString(CoopAssistantInformation.KEY_COOPTERMS, coopTermsandConditions);
                    CoopAssistantTermsandConditionsActivity.start(getViewContext(), args);
                } else {
                    CoopAssistantApplyMemberActivity.start(getViewContext(), args);
                }
            } else {
                if (!coopPMES.trim().isEmpty() && !coopPMES.trim().equals(".")) {
                    args.putString(CoopAssistantInformation.KEY_COOPPMES, coopPMES);
                    args.putString(CoopAssistantInformation.KEY_COOPTERMS, coopTermsandConditions);
                    CoopAssistantPMESActivity.start(getViewContext(), args);
                } else {
                    CoopAssistantApplyMemberActivity.start(getViewContext(), args);
                }
            }
        } else {
            if (coopTermsandConditions != null) {
                args.putString(CoopAssistantInformation.KEY_COOPTERMS, coopTermsandConditions);
                CoopAssistantTermsandConditionsActivity.start(getViewContext(), args);
            } else {
                CoopAssistantApplyMemberActivity.start(getViewContext(), args);
            }
        }
    }

    private int validateStaticFields() {
        int validityOfError = 0;

        try {
            for (TextView errordata : errorStaticTxtViewList) {
                String strErrorTag = errordata.getTag().toString();
                JSONObject errorjresponse = new JSONObject(strErrorTag);
                String errorname = errorjresponse.getString("name");

                switch (errorname) {
                    case "Surname":
                        if (edt_surname.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_surname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_surname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Given Name":
                        if (edt_givenname.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_givenname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_givenname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Middle Name":
                        if (edt_middlename.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_middlename.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_middlename.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Mobile Number":
                        if (getMobileNumber(edt_mobilenumber.getText().toString().trim()).equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_mobilenumber.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else if (getMobileNumber(edt_mobilenumber.getText().toString().trim()).equals("INVALID")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_mobilenumber.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_mobilenumber.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_disabled));
                        }

                        break;
                    case "Email Address":
                        if (edt_emailaddress.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_emailaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else if (!CommonFunctions.isValidEmail(edt_emailaddress.getText().toString().trim())) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_emailaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_emailaddress.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Gender":
                        if (spn_gender.getSelectedItem().toString().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            spn_gender.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else if (spn_gender.getSelectedItem().toString().contains("Select")) {
                            errordata.setVisibility(View.VISIBLE);
                            spn_gender.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            spn_gender.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Birth Date":
                        if (txv_cal_birthdate.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            txv_cal_birthdate.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else if ((!isValidBirthDate(txv_cal_birthdate.getText().toString().trim()))) {
                            errordata.setVisibility(View.VISIBLE);
                            txv_cal_birthdate.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            txv_cal_birthdate.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Civil Status":
                        if (spn_civilstatus.getSelectedItem().toString().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            spn_civilstatus.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else if (spn_civilstatus.getSelectedItem().toString().contains("Select")) {
                            errordata.setVisibility(View.VISIBLE);
                            spn_civilstatus.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            spn_civilstatus.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Nationality":
                        if (edt_nationality.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_nationality.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_nationality.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Home Town Address":
                        if (edt_hometown.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_hometown.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_hometown.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Current Address":
                        if (edt_currentadd.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_currentadd.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_currentadd.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
                    case "Occupation":
                        if (edt_occupation.getText().toString().trim().equals("")) {
                            errordata.setVisibility(View.VISIBLE);
                            edt_occupation.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
                            validityOfError = validityOfError + 1;
                        } else {
                            errordata.setVisibility(View.GONE);
                            edt_occupation.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
                        }
                        break;
//                    case "Referrer Name":
//                        if (edt_referredbyname.getText().toString().trim().equals("")) {
//                            errordata.setVisibility(View.VISIBLE);
//                            edt_referredbyname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border_red));
//                            validityOfError = validityOfError + 1;
//                        } else {
//                            errordata.setVisibility(View.GONE);
//                            edt_referredbyname.setBackground(ContextCompat.getDrawable(getViewContext(), R.drawable.border));
//                        }
//                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return validityOfError;
    }

    private boolean isValidBirthDate(String strCalendar) {
        boolean isValid = false;

        try {
            int year = 0;
            int day = 0;
            int month = 0;
            int age = 0;

            Calendar calender = Calendar.getInstance();
            SimpleDateFormat formateDate = new SimpleDateFormat("yyyy-MM-dd");
            formateDate.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = formateDate.parse(strCalendar);
            calender.setTimeInMillis(date.getTime());
            year = calender.get(Calendar.YEAR);
            month = calender.get(Calendar.MONTH);
            day = calender.get(Calendar.DAY_OF_MONTH);

            age = CommonFunctions.getAgeFromCalendar(year, month, day);

            if (errorStaticTxtViewList.size() > 0) {
                for (TextView errordata : errorStaticTxtViewList) {
                    String strErrorTag = errordata.getTag().toString();
                    JSONObject errorjresponse = new JSONObject(strErrorTag);
                    String errorname = errorjresponse.getString("name");

                    if (errorname.equals("Birth Date")) {
                        if (age >= 18) {
                            errordata.setText("Invalid " + errorname + ".");
                            isValid = true;
                        } else {
                            errordata.setText("Applicant age must be 18 years old or above.");
                            isValid = false;
                        }
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    private int validateOtherDetails() {
        int validityOfError = 0;

        try {
            JSONArray validateJSONArray = new JSONArray();

            kycWithoutUploadingJSONArray = new JSONArray();

            kycUploadingJSONArray = new JSONArray();

            if (steps.size() > 0) {
                if (otherDetailsJSONArray.length() <= 10) {
                    if (currentStep > 0) {
                        validateJSONArray = otherDetailsJSONArray;
                    }
                } else if (otherDetailsJSONArray.length() >= 11) {
                    if (currentStep <= 0) {
                        validateJSONArray = new JSONArray();
                    } else if (currentStep == 1) {
                        validateJSONArray = checkPage2JSONArray;
                    } else {
                        validateJSONArray = otherDetailsJSONArray;
                    }
                }
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

                                    if (!CommonFunctions.stringContainsNumber(inputres) || CommonFunctions.stringContainsTwoDots(inputres)) {
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
                        if (struri != null && !struri.equals("")) {
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

    private void setStepPage() {
        try {
            if (otherDetailsJSONArray.length() <= 10) {
                if (currentStep == 0) {
                    layout_newapplication_fields.setVisibility(View.VISIBLE);
                    layout_custom_fields_page2.setVisibility(View.GONE);
                    layout_custom_fields_page3.setVisibility(View.GONE);
                    btn_action.setText("NEXT");
                } else {
                    layout_newapplication_fields.setVisibility(View.GONE);
                    layout_custom_fields_page2.setVisibility(View.VISIBLE);
                    layout_custom_fields_page3.setVisibility(View.GONE);
                    btn_action.setText("PROCEED");
                }
            } else if (otherDetailsJSONArray.length() >= 11) {
                if (currentStep == 0) {
                    layout_newapplication_fields.setVisibility(View.VISIBLE);
                    layout_custom_fields_page2.setVisibility(View.GONE);
                    layout_custom_fields_page3.setVisibility(View.GONE);
                    btn_action.setText("NEXT");
                } else if (currentStep == 1) {
                    layout_newapplication_fields.setVisibility(View.GONE);
                    layout_custom_fields_page2.setVisibility(View.VISIBLE);
                    layout_custom_fields_page3.setVisibility(View.GONE);
                    btn_action.setText("NEXT");
                } else {
                    layout_newapplication_fields.setVisibility(View.GONE);
                    layout_custom_fields_page2.setVisibility(View.GONE);
                    layout_custom_fields_page3.setVisibility(View.VISIBLE);
                    btn_action.setText("PROCEED");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onBackPressed() {
        boolean returntoPreviousPage;

        if (currentStep == 0) {
            returntoPreviousPage = true;
        } else {
            currentStep--;
            stepView.done(false);
            stepView.go(currentStep, true);
            setStepPage();
            returntoPreviousPage = false;
        }

        return returntoPreviousPage;
    }

}
