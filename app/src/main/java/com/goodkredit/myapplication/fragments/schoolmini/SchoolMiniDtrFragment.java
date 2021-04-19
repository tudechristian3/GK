package com.goodkredit.myapplication.fragments.schoolmini;

import com.goodkredit.myapplication.adapter.schoolmini.dtr.SchoolMiniDtrHeaderAdapter;
import com.goodkredit.myapplication.base.BaseFragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniDtrDB;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDtr;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniDtrResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SchoolMiniDtrFragment extends BaseFragment implements View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private DatabaseHandler mdb;

    private String sessionid = "";

    private List<SchoolMiniDtr> schoolMiniDtrList = new ArrayList<>();

    //SCHOOL
    private String schoolid = "";
    private String studentid = "";
    private String course = "";
    private String yearlevel = "";
    private String firstname = "";
    private String middlename = "";
    private String lastname = "";
    private String mobilenumber = "";
    private String emailaddress = "";
    private String datetimeuploaded = "";

    private RecyclerView rv_school_dtr;
    private SchoolMiniDtrHeaderAdapter schoolMiniDtrHeaderAdapter;

    //VIEW ARCHIVE
    private LinearLayout viewarchivelayout;
    private TextView viewarchive;
    private LinearLayout viewarchivelayoutv2;
    private TextView viewarchivev2;
    private MaterialDialog mFilterOptionDialog;
    private List<String> MONTHS = new ArrayList<>();
    private List<String> YEARS = new ArrayList<>();
    private int MIN_MONTH = 1;
    private int MIN_YEAR = 2018;
    private int MAX_MONTH = 12;
    private int MAX_YEAR = 2018;
    private MaterialEditText edtMonth;
    private MaterialEditText edtYear;

    private MaterialDialog mDialog;
    private ScrollView filterwrap;
    private LinearLayout optionwrap;
    private TextView editsearches;
    private TextView clearsearch;
    private Spinner monthspinType;
    private Spinner yearspinType;
    private TextView popfilter;
    private TextView popcancel;
    private boolean isyearselected = false;
    private boolean ismonthselected = false;

    //FILTER
    private RelativeLayout emptyvoucherfilter;
    private TextView filteroption;

    //DATE
    private String passedyear = ".";
    private String passedmonth = ".";
    private int year = 0;
    private int month = 0;
    private int currentyear = 0;
    private int currentmonth = 0;
    private int registrationyear;
    private int registrationmonth;
    private String dateregistered = "";
    private Button mBtnMore;

    //View Archieve and View Filter Switch
    private LinearLayout view_more_container;
    private TextView view_more;

    //LOADER
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //empty layout
    private RelativeLayout emptyLayout;
    private TextView textView11;
    private ImageView refresh;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private ImageView refreshnointernet;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //SCROLLLIMIT
    private boolean isloadmore = false;
    private boolean isbottomscroll = false;
    private boolean isfirstload = true;
    private boolean isLoading = false;
    private int ordhislimit = 0;
    private NestedScrollView scrollmaincontainer;

    //GLOBAL DIALOGS
    private GlobalDialogs mGlobalDialogs;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schoolmini_dtr, container, false);
        try {
            init(view);
            initData();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void init(View view) {
        scrollmaincontainer = (NestedScrollView) view.findViewById(R.id.scrollmaincontainer);

        //SCHOOL RV
        rv_school_dtr = (RecyclerView) view.findViewById(R.id.rv_school_dtr);

        //swipe refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //loader
        mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
        mTvFetching = (TextView) view.findViewById(R.id.fetching);
        mTvWait = (TextView) view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = (RelativeLayout) view.findViewById(R.id.nointernetconnection);
        refreshnointernet = (ImageView) view.findViewById(R.id.refreshnointernet);
        refreshnointernet.setOnClickListener(this);

        //Filter Option
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        emptyvoucherfilter = (RelativeLayout) view.findViewById(R.id.emptyvoucherfilter);
        filteroption = (TextView) view.findViewById(R.id.filteroption);
        filteroption.setOnClickListener(this);

        //empty layout
        emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyLayout);
        textView11 = (TextView) view.findViewById(R.id.textView11);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        view_more_container = (LinearLayout) view.findViewById(R.id.view_more_container);
        view_more = (TextView) view.findViewById(R.id.view_more);
        view_more_container.setOnClickListener(this);
    }

    private void initData() {
        mdb = new DatabaseHandler(getViewContext());

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        mdb = new DatabaseHandler(getViewContext());

        schoolid = PreferenceUtils.getStringPreference(getViewContext(), PreferenceUtils.KEY_SCSERVICECODE);

        if (getArguments() != null) {

            studentid = getArguments().getString("STUDENTID");
            course = getArguments().getString("COURSE");
            yearlevel = getArguments().getString("YEARLEVEL");
            firstname = getArguments().getString("FIRSTNAME");
            middlename = getArguments().getString("MIDDLENAME");
            lastname = getArguments().getString("LASTNAME");
            mobilenumber = getArguments().getString("MOBILENUMBER");
            emailaddress = getArguments().getString("EMAILADDRESS");
            datetimeuploaded = getArguments().getString("DATETIMEUPLOAD");


            rv_school_dtr.setLayoutManager(new LinearLayoutManager(getViewContext()));
            rv_school_dtr.setNestedScrollingEnabled(false);

            schoolMiniDtrHeaderAdapter = new SchoolMiniDtrHeaderAdapter(getViewContext(), SchoolMiniDtrFragment.this);
            rv_school_dtr.setAdapter(schoolMiniDtrHeaderAdapter);

            //DATES
            setUpDates();

            //SCROLL VIEW
            setUpNestedScrollView();
        }
    }

    private void setUpDates() {
        //initialize date
        passedyear = ".";
        passedmonth = ".";
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        currentyear = Calendar.getInstance().get(Calendar.YEAR);
        currentmonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        String CurrentString = datetimeuploaded;
        String[] separated = CurrentString.split("-");
        registrationyear = Integer.parseInt(separated[0]);
        registrationmonth = Integer.parseInt(separated[1]);

        //LOADER
        mLoaderTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLlLoader.setVisibility(View.GONE);
            }
        };

        //SETTING UP THE MIN AND MAX MONTH AND YEAR
        MIN_YEAR = registrationyear;
        MIN_MONTH = registrationmonth;
        MAX_YEAR = currentyear;
        MAX_MONTH = currentmonth;
    }

    private void setUpNestedScrollView() {
        scrollmaincontainer.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == ( v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight() )) {
                    isbottomscroll = true;

                    if (isloadmore) {
                        if (!isfirstload) {
                            ordhislimit = ordhislimit + 10;
                        }
                        callMainAPI();
                    }
                }
                else {
                    if (mdb.getSchoolMiniDtrGroupByDate(mdb).size() > 0) {
                        view_more_container.setVisibility(View.VISIBLE);
                        if (isyearselected && ismonthselected) {
                            view_more.setText("FILTER OPTIONS");
                        } else {
                            view_more.setText("VIEW ARCHIVE");
                        }
                    }
                    if (nestedScrollViewAtTop(scrollmaincontainer)) {
                        mSwipeRefreshLayout.setEnabled(true);
                    } else {
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                }

            }
        });

        if (schoolMiniDtrList.isEmpty()) {
            callMainAPI();
        }
    }

    private boolean nestedScrollViewAtTop(NestedScrollView nestedScrollView) {
        return nestedScrollView.getChildCount() == 0 || nestedScrollView.getChildAt(0).getTop() == 0;
    }

    private void callMainAPI() {
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            showNoInternetConnection(false);
            refreshnointernet.setVisibility(View.GONE);

            isLoading = true;

            mTvFetching.setText("Fetching student records...");
            mTvWait.setText(" Please wait...");
            mLlLoader.setVisibility(View.VISIBLE);

            //getStudentDtrRecord();
            getStudentDtrRecordV2();

        } else {
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
            showNoInternetConnection(true);
            refreshnointernet.setVisibility(View.VISIBLE);
            showNoInternetToast();
        }
    }

    private void getStudentDtrRecord() {
        Call<SchoolMiniDtrResponse> list = RetrofitBuild.getSchoolAPIService(getViewContext())
                .getStudentDtrRecord(sessionid,
                        imei,
                        userid,
                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
                        borrowerid,
                        schoolid,
                        studentid,
                        ordhislimit,
                        passedmonth,
                        passedyear
                );

        list.enqueue(getStudentDtrRecordCallBack);
    }

    private final Callback<SchoolMiniDtrResponse> getStudentDtrRecordCallBack = new Callback<SchoolMiniDtrResponse>() {

        @Override
        public void onResponse(Call<SchoolMiniDtrResponse> call, Response<SchoolMiniDtrResponse> response) {

            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                //000 - list of process
                if (response.body().getStatus().equals("000")) {
                    mLlLoader.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(false);

                    if (response.body().getList().size() > 0) {
                        isloadmore = true;
                    } else {
                        isloadmore = false;
                    }

                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, SchoolMiniDtrDB.TABLE_SCHOOL_DTR);
                    }

                    List<SchoolMiniDtr> schoolDtrList = response.body().getList();
                    if (schoolDtrList.size() > 0) {
                        for (SchoolMiniDtr schoolMiniDtr : schoolDtrList) {
                            mdb.insertSchoolMiniDtr(mdb, schoolMiniDtr);
                        }
                    }

                    checkSchoolList(mdb.getSchoolMiniDtrGroupByDate(mdb));

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
        public void onFailure(Call<SchoolMiniDtrResponse> call, Throwable t) {
            showErrorGlobalDialogs();
            CommonFunctions.hideDialog();
        }
    };

    private void showNoInternetConnection(boolean isShow) {
        if (isShow) {
            emptyLayout.setVisibility(View.GONE);
            nointernetconnection.setVisibility(View.VISIBLE);
        } else {
            nointernetconnection.setVisibility(View.GONE);
        }
    }

    private void showViewArchiveDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("View Archive", "",
                "CANCEL", "FILTER", ButtonTypeEnum.DOUBLE,
                false, false, DialogTypeEnum.SPINNER);

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> spinyearlist = new ArrayList<String>();
        spinyearlist = yearList();

        ArrayList<String> spinmonthlist = new ArrayList<>();
        spinmonthlist = monthlist();


        LinearLayout yearContainer = mGlobalDialogs.setContentSpinner(spinyearlist, LinearLayout.HORIZONTAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));

        final int countyear = yearContainer.getChildCount();
        for (int i = 0; i < countyear; i++) {
            View spinnerView = yearContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                yearspinType = (Spinner) spinnerView;
                yearspinType.setOnItemSelectedListener(yearItemListenerNew);
            }
        }

        LinearLayout monthContainer = mGlobalDialogs.setContentSpinner(spinmonthlist, LinearLayout.HORIZONTAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, 0));

        final int countmonth = monthContainer.getChildCount();
        for (int i = 0; i < countmonth; i++) {
            View spinnerView = monthContainer.getChildAt(i);
            if (spinnerView instanceof Spinner) {
                monthspinType = (Spinner) spinnerView;
                monthspinType.setOnItemSelectedListener(monthItemListenerNew);
            }
        }

        View btndoubleone = mGlobalDialogs.btnDoubleOne();
        btndoubleone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        View btndoubletwo = mGlobalDialogs.btnDoubleTwo();
        btndoubletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
                filterOptions();
            }
        });
    }

    private void showFilterOptionDialogNew() {
        mGlobalDialogs = new GlobalDialogs(getViewContext());

        mGlobalDialogs.createDialog("Filter Options", "",
                "CLOSE", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.TEXTVIEW);

        mGlobalDialogs.hideTextButtonAction();

        View closebtn = mGlobalDialogs.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });

        ArrayList<String> textviewlist = new ArrayList<String>();
        textviewlist.add("EDIT SEARCHES");
        textviewlist.add("CLEAR SEARCHES");

        LinearLayout textViewContainer = mGlobalDialogs.setContentTextView(textviewlist, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 18, 0));

        final int count = textViewContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View textView = textViewContainer.getChildAt(i);

            if (textView instanceof TextView) {
                String tag = String.valueOf(textView.getTag());

                if (tag.equals("EDIT SEARCHES")) {
                    editsearches = (TextView) textView;
                    editsearches.setPadding(20,20,20,20);
                    editsearches.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_blue));
                    editsearches.setOnClickListener(edtisearchListener);
                } else if (tag.equals("CLEAR SEARCHES")) {
                    clearsearch = (TextView) textView;
                    clearsearch.setPadding(20,20,20,20);
                    clearsearch.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_information_lightblue));
                    clearsearch.setOnClickListener(clearSearchListener);
                }
            }
        }

        View singlebtn = mGlobalDialogs.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGlobalDialogs.dismiss();
                mGlobalDialogs = null;
            }
        });
    }

    private void editSearchOption() {
        ordhislimit = 0;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
        showViewArchiveDialogNew();
    }

    private void clearSearchOption() {
        ordhislimit = 0;
        view_more.setText("VIEW ARCHIVE");
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        passedyear = ".";
        passedmonth = ".";
        isyearselected = false;
        ismonthselected = false;
        mGlobalDialogs.dismiss();
        mGlobalDialogs = null;
    }

    //FILTER OPTIONS
    private void filterOptions() {
        if (isyearselected && ismonthselected) {
            if (mdb != null) {
                mdb.truncateTable(mdb, SchoolMiniDtrDB.TABLE_SCHOOL_DTR);

                if (schoolMiniDtrHeaderAdapter != null) {
                    schoolMiniDtrHeaderAdapter.clear();
                }
                view_more_container.setVisibility(View.GONE);
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                ordhislimit = 0;
                if (view_more.getText().equals("VIEW ARCHIVE")) {
                    view_more.setText("FILTER OPTIONS");
                }
                callMainAPI();
            }
        } else {
            showToast("Please select a date.", GlobalToastEnum.WARNING);
        }
    }

    //create spinner for month list
    private void createMonthSpinnerAddapter() {
        try {
            ArrayAdapter<String> monthadapter;
            ArrayList<String> spinmonthlist = new ArrayList<String>();
            spinmonthlist = monthlist();
            monthadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, spinmonthlist);
            monthadapter.setDropDownViewResource(R.layout.spinner_arrow);
            monthspinType.setAdapter(monthadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //create spinner for year list
    private void createYearSpinnerAddapter() {
        try {
            ArrayAdapter<String> yearadapter;
            yearadapter = new ArrayAdapter<String>(getViewContext(), android.R.layout.simple_spinner_dropdown_item, yearList());
            yearadapter.setDropDownViewResource(R.layout.spinner_arrow);
            yearspinType.setAdapter(yearadapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private AdapterView.OnItemSelectedListener yearItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);
                    createMonthSpinnerAddapter();
                    isyearselected = true;
                } else {
                    isyearselected = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            isyearselected = false;
        }
    };

    private AdapterView.OnItemSelectedListener monthItemListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    String monthstring = parent.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;
                    if (month <= 9) {
                        passedmonth = "0" + String.valueOf(month);
                    } else {
                        passedmonth = String.valueOf(month);
                    }

                    if (month > 0) {
                        ismonthselected = true;
                    } else {
                        ismonthselected = false;
                    }
                } else {
                    ismonthselected = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            ismonthselected = false;
        }
    };

    private AdapterView.OnItemSelectedListener yearItemListenerNew = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                String spinyear = parent.getItemAtPosition(position).toString();
                if (!spinyear.equals("Select Year")) {
                    year = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    passedyear = String.valueOf(year);

                    ArrayList<String> finalMonthList = new ArrayList<>();
                    finalMonthList = monthlist();
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getViewContext(), android.R.layout.simple_spinner_dropdown_item, finalMonthList);
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.dialog_global_messages_spinner_arrow);
                    monthspinType.setAdapter(spinnerArrayAdapter);

                    isyearselected = true;
                } else {
                    isyearselected = false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            isyearselected = false;
        }
    };

    private AdapterView.OnItemSelectedListener monthItemListenerNew = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            try {
                if (position > 0) {
                    String monthstring = parent.getItemAtPosition(position).toString();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new SimpleDateFormat("MMM").parse(monthstring));
                    month = cal.get(Calendar.MONTH) + 1;
                    if (month <= 9) {
                        passedmonth = "0" + String.valueOf(month);
                    } else {
                        passedmonth = String.valueOf(month);
                    }

                    if (month > 0) {
                        ismonthselected = true;
                    } else {
                        ismonthselected = false;
                    }
                } else {
                    ismonthselected = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            ismonthselected = false;
        }
    };

    private View.OnClickListener edtisearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mGlobalDialogs != null) {
                editSearchOption();
            }
        }
    };

    private View.OnClickListener clearSearchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mGlobalDialogs != null) {
                clearSearchOption();
            }
        }
    };

    //create year list
    private ArrayList<String> yearList() {

        ArrayList<String> mYear = new ArrayList<String>();
        mYear.add("Select Year");
        for (int i = registrationyear; i <= currentyear; i++) {
            mYear.add(Integer.toString(i));
        }

        return mYear;
    }

    //make the number month to month name
    private ArrayList<String> monthlist() {
        String[] months = new DateFormatSymbols().getMonths();


        ArrayList<String> mMonths = new ArrayList<String>();
        mMonths.add("Select Month");

        int max = 0;
        for (int i = 0; i < months.length; i++) {
            if(registrationyear == year && year != currentyear) {
                max = max + 1;
            }
            else if (year != currentyear) {
                max = max + 1;
            } else {
                if (i < MAX_MONTH) {
                    max = max + 1;
                } else {
                    break;
                }
            }
        }


        if(registrationyear == year) {
            mMonths.addAll(Arrays.asList(months).subList(MIN_MONTH - 1, max));
        } else {
            mMonths.addAll(Arrays.asList(months).subList(0, max));
        }
        return mMonths;
    }

    private void checkSchoolList(List<SchoolMiniDtr> data) {
        showNoInternetConnection(false);
        if (data.size() > 0) {
            emptyLayout.setVisibility(View.GONE);
            view_more_container.setVisibility(View.VISIBLE);

            rv_school_dtr.setVisibility(View.VISIBLE);
            schoolMiniDtrHeaderAdapter.updateData(data);

            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setEnabled(true);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            rv_school_dtr.setVisibility(View.GONE);
            view_more_container.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.bringToFront();
            mSwipeRefreshLayout.invalidate();
            mSwipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onRefresh() {
        if (mdb != null) {
            mdb.truncateTable(mdb, SchoolMiniDtrDB.TABLE_SCHOOL_DTR);
            if (schoolMiniDtrHeaderAdapter != null) {
                schoolMiniDtrHeaderAdapter.clear();
            }
            view_more_container.setVisibility(View.GONE);
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            mSwipeRefreshLayout.setRefreshing(true);
            isfirstload = false;
            ordhislimit = 0;
            view_more.setText("VIEW ARCHIVE");
            isyearselected = false;
            ismonthselected = false;
            isbottomscroll = false;
            isloadmore = false;
            passedyear = ".";
            passedmonth = ".";
            callMainAPI();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.viewarchive: {
                ordhislimit = 0;
                //showViewArchiveDialog();
                showViewArchiveDialogNew();
                break;
            }
            case R.id.cancel: {
                mDialog.dismiss();
                break;
            }
            case R.id.filter: {
                if (isyearselected && ismonthselected) {
                    if (mdb != null) {
                        mdb.truncateTable(mdb, SchoolMiniDtrDB.TABLE_SCHOOL_DTR);

                        if (schoolMiniDtrHeaderAdapter != null) {
                            schoolMiniDtrHeaderAdapter.clear();
                        }
                        view_more_container.setVisibility(View.GONE);
                        year = Calendar.getInstance().get(Calendar.YEAR);
                        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                        ordhislimit = 0;
                        if (view_more.getText().equals("VIEW ARCHIVE")) {
                            view_more.setText("FILTER OPTIONS");
                        }
                        callMainAPI();
                        mDialog.dismiss();
                    }
                } else {
                    showToast("Please select a date.", GlobalToastEnum.WARNING);
                }
                break;
            }
            case R.id.refresh: {
                ordhislimit = 0;
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                passedyear = ".";
                passedmonth = ".";
                callMainAPI();
                break;
            }
            case R.id.view_more_container: {
                ordhislimit = 0;
                if (view_more.getText().equals("VIEW ARCHIVE"))
                    //showViewArchiveDialog();
                    showViewArchiveDialogNew();
                else
                    //showFilterOptionDialog();
                    showFilterOptionDialogNew();
                break;
            }
            case R.id.editsearches: {
                ordhislimit = 0;
                filterwrap.setVisibility(View.VISIBLE);
                optionwrap.setVisibility(View.GONE);
                break;
            }
            case R.id.clearsearch: {
                ordhislimit = 0;
                view_more.setText("VIEW ARCHIVE");
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                passedyear = ".";
                passedmonth = ".";
                isyearselected = false;
                ismonthselected = false;
                mDialog.dismiss();
                break;
            }
            case R.id.filteroption: {
                ordhislimit = 0;
                //showFilterOptionDialog();
                showFilterOptionDialogNew();
                break;
            }
            case R.id.refreshnointernet: {
                ordhislimit = 0;
                callMainAPI();
                break;
            }
        }
    }

    /*
     * SECURITY UPDATE
     * AS OF
     * FEBRUARY 2020
     * */
    private void getStudentDtrRecordV2() {
        try {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("schoolid", schoolid);
                parameters.put("studentid", studentid);
                parameters.put("limit", String.valueOf(ordhislimit));
                parameters.put("recordmonth", passedmonth);
                parameters.put("recordyear", passedyear);
                parameters.put("devicetype", CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", index);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "getStudentDtrRecordV2");
                param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

                getStudentDtrRecordV2Object();

            } else {
                hideProgressDialog();
                showNoInternetToast();
            }

        } catch (Exception e) {
            e.printStackTrace();
            hideProgressDialog();
            showNoInternetToast();
        }
    }

    private void getStudentDtrRecordV2Object() {
        Call<GenericResponse> call = RetrofitBuilder.getSchoolV2API(getViewContext())
                .getStudentDtrRecordV2(authenticationid, sessionid, param);

        call.enqueue(getStudentDtrRecordV2ObjectCallBack);
    }

    private Callback<GenericResponse> getStudentDtrRecordV2ObjectCallBack = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errBody = response.errorBody();

            hideProgressDialog();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);


            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String data = CommonFunctions.decryptAES256CBC(keyEncryption, response.body().getData());

                    isLoading = false;
                    isfirstload = false;

                    if (!isbottomscroll) {
                        mdb.truncateTable(mdb, SchoolMiniDtrDB.TABLE_SCHOOL_DTR);
                    }

                    List<SchoolMiniDtr> schoolDtrList = new Gson().fromJson(data, new TypeToken<List<SchoolMiniDtr>>(){}.getType());
                    if (schoolDtrList.size() > 0) {
                        isloadmore = true;
                        for (SchoolMiniDtr schoolMiniDtr : schoolDtrList) {
                            mdb.insertSchoolMiniDtr(mdb, schoolMiniDtr);
                        }
                    } else {
                        isloadmore = false;
                    }

                    checkSchoolList(mdb.getSchoolMiniDtrGroupByDate(mdb));

                } else {
                    if (response.body().getStatus().equals("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                    } else {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            } else {
                showNoInternetToast();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            hideProgressDialog();
            showNoInternetToast();
            mLlLoader.setVisibility(View.GONE);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };
}
