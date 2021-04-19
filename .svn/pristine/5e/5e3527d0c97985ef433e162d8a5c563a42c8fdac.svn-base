package com.goodkredit.myapplication.fragments.projectcoop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.adapter.projectcoop.ProjectCoopP2SDigitsRecyclerAdapter;
import com.goodkredit.myapplication.adapter.projectcoop.ProjectCoopP2SResultsRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.projectcoop.GetGameP2SResultsResponse;
import com.goodkredit.myapplication.responses.projectcoop.GetGameProductsResponse;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectCoopPlayToSaveFragment extends BaseFragment implements View.OnClickListener {
    //    private TextView txvMechanicsNotes;
    private TextView txvPlayToSave;
    private TextView txvPlayTheGame;
    private RecyclerView recyclerView;
    private ProjectCoopP2SResultsRecyclerAdapter mAdapter;
    private TextView txvResult;

    private RecyclerView recyclerViewDigits;
    private ProjectCoopP2SDigitsRecyclerAdapter mDigitsAdapter;

    private ArrayList<String> p2sArr;
    private ArrayList<String> p2sShakeArr;
    private ArrayList<String> p2sShakeArrResult;

//    private SensorManager mSensorManager;
//    private Sensor mAccelerometer;
//    private ShakeDetector mShakeDetector;

    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private boolean isInsert = false;

    private TextView txvPreviousEntries;

    private WebView webView;

    private EditText edtResultDate;
    private TextView txvResultDate;

    private String selectedDay;

    private boolean isResults = false;

    private LinearLayout linearResultsLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_coop_play_to_save, container, false);

        setHasOptionsMenu(true);

        init(view);

        getSession();

        return view;
    }

    private void init(final View view) {

        initData();

        linearResultsLayout = (LinearLayout) view.findViewById(R.id.linearResultsLayout);

        txvResultDate = (TextView) view.findViewById(R.id.txvResultDate);
        txvResultDate.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Result"));
        edtResultDate = (EditText) view.findViewById(R.id.edtResultDate);
        String date = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + ":" + String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1) + ":" + String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        edtResultDate.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.getCPMPCDate(date)));
        edtResultDate.setOnClickListener(this);

        txvPreviousEntries = (TextView) view.findViewById(R.id.txvPreviousEntries);
        txvPreviousEntries.setOnClickListener(this);

        recyclerViewDigits = (RecyclerView) view.findViewById(R.id.recyclerViewDigits);
        recyclerViewDigits.setLayoutManager(new LinearLayoutManager(getViewContext()));
        //recyclerViewDigits.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerViewDigits.setNestedScrollingEnabled(false);
        mDigitsAdapter = new ProjectCoopP2SDigitsRecyclerAdapter(getViewContext());
        recyclerViewDigits.setAdapter(mDigitsAdapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        //recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new ProjectCoopP2SResultsRecyclerAdapter(getViewContext(), ProjectCoopPlayToSaveFragment.this);
        recyclerView.setAdapter(mAdapter);

        txvResult = (TextView) view.findViewById(R.id.txvResult);
        txvResult.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", "NO RESULTS YET"));
//        txvMechanicsNotes = (TextView) view.findViewById(R.id.txvMechanicsNotes);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            Spanned spannedHtml = Html.fromHtml(getString(R.string.str_coop_p2s_mechanics), Html.FROM_HTML_MODE_COMPACT);
////            txvMechanicsNotes.setText(spannedHtml);
//        } else {
//            Spanned spannedHtml = Html.fromHtml(getString(R.string.str_coop_p2s_mechanics));
////            txvMechanicsNotes.setText(spannedHtml);
//        }

        txvPlayToSave = (TextView) view.findViewById(R.id.txvPlayToSave);
        txvPlayToSave.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Light.ttf", "PLAY TO SAVE"));
        txvPlayTheGame = (TextView) view.findViewById(R.id.txvPlayTheGame);
        txvPlayTheGame.setText(CommonFunctions.setTypeFace(getViewContext(), "fonts/Roboto-Bold.ttf", "Play the Game"));

        webView = (WebView) view.findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/cpmpcp2smechanics.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Logger.debug("antonhttp", "URL: " + Uri.parse(url).getHost());

                if (Uri.parse(url).getHost().contains("www.mycoop.ph") || Uri.parse(url).getHost().startsWith("http://") || Uri.parse(url).getHost().startsWith("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }

            }

        });

        // ShakeDetector initialization
//        mSensorManager = (SensorManager) getViewContext().getSystemService(Context.SENSOR_SERVICE);
//        mAccelerometer = mSensorManager
//                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        mShakeDetector = new ShakeDetector();
//        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
//
//            @Override
//            public void onShake(int count) {
//                /*
//                 * The following method, "handleShakeEvent(count):" is a stub //
//                 * method you would use to setup whatever you want done once the
//                 * device has been shook.
//                 */
////                handleShakeEvent(view, count);
//            }
//        });
    }

//    private void handleShakeEvent(View view, int count) {
//        Logger.debug("antonhttp", "SHAKING!!!!");
//        Logger.debug("antonhttp", "p2sShakeArr: " + p2sShakeArr);
//        Collections.shuffle(p2sShakeArr);
//        // get selected radio button from radioGroup
//        int selectedId = radioDigits.getCheckedRadioButtonId();
//        // find the radiobutton by returned id
//        RadioButton radioButton = (RadioButton) view.findViewById(selectedId);
//        int limit = 0;
//        if (radioButton.getText().equals("3 Digits")) {
//            limit = 3;
//        } else if (radioButton.getText().equals("6 Digits")) {
//            limit = 6;
//        }
//
//        p2sShakeArrResult.clear();
//        for (int i = 0; i < limit; i++) {
//            p2sShakeArrResult.add(p2sShakeArr.get(i));
//        }
//
//        Logger.debug("antonhttp", "p2sShakeArrResult: " + p2sShakeArrResult);
//
//        if (limit == 3) {
//            p2sArr3Digit();
//            edtp2s1.setText(p2sShakeArrResult.get(0));
//            edtp2s2.setText(p2sShakeArrResult.get(1));
//            edtp2s3.setText(p2sShakeArrResult.get(2));
//        } else {
//            p2sArr6Digit();
//            edtp2s1.setText(p2sShakeArrResult.get(0));
//            edtp2s2.setText(p2sShakeArrResult.get(1));
//            edtp2s3.setText(p2sShakeArrResult.get(2));
//            edtp2s4.setText(p2sShakeArrResult.get(3));
//            edtp2s5.setText(p2sShakeArrResult.get(4));
//            edtp2s6.setText(p2sShakeArrResult.get(5));
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
//        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
//        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    private void initData() {
        p2sArr = new ArrayList<>();
        p2sShakeArr = new ArrayList<>();
        p2sShakeArrResult = new ArrayList<>();
        selectedDay = ".";

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home: {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ((ProjectCoopActivity) getViewContext()).displayView(1, null);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txvGoodLuck: {
                boolean isProceed = true;
                for (String number : p2sArr) {
                    Logger.debug("antonhttp", "number: " + number);
                    if (number.equals("0")) {
                        isProceed = false;
                        break;
                    }
                }

                if (!isProceed) {
                    showError("Invalid Combination");
                } else {
                    isInsert = true;
                    getSession();
                }

                break;
            }
            case R.id.txvPreviousEntries: {
                ((ProjectCoopActivity) getViewContext()).displayView(4, null);
                break;
            }
            case R.id.edtResultDate: {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                String month = "";
                                String day = "";

                                int mm = monthOfYear + 1;
                                int dd = dayOfMonth;
                                if (mm <= 9) {
                                    month = "0".concat(String.valueOf(mm));
                                } else {
                                    month = String.valueOf(mm);
                                }

                                if (dd <= 9) {
                                    day = "0".concat(String.valueOf(dd));
                                } else {
                                    day = String.valueOf(dd);
                                }

                                String date = year + ":" + month + ":" + day;

                                selectedDay = date;

                                mAdapter.clear();

                                isResults = true;
                                getSession();

                                edtResultDate.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.getCPMPCDate(date)));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.show(getActivity().getFragmentManager(), "dialog");
                break;
            }
        }
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {
//
//            Call<String> call = RetrofitBuild.getCommonApiService(getContext())
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//
//        } else {
//            isResults = false;
//            isInsert = false;
//            showError(getString(R.string.generic_internet_error_message));
//        }
//
//    }
//
//    private final Callback<String> callsession = new Callback<String>() {
//
//        @Override
//        public void onResponse(Call<String> call, Response<String> response) {
//            String responseData = response.body().toString();
//            if (!responseData.isEmpty()) {
//                if (responseData.equals("001")) {
//                    isInsert = false;
//                    isResults = false;
//                    showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                } else if (responseData.equals("error")) {
//                    isInsert = false;
//                    isResults = false;
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isInsert = false;
//                    isResults = false;
//                    showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isInsert) {
////                        prePurchase(prePurchaseSession);
////                        insertEntryNumberP2S(insertEntryNumberP2SSession);
//                    } else if (isResults) {
//                        getGameP2SResults(getGameP2SResultsSession);
//                    } else {
//                        getGameProducts(getGameProductsSession);
//                        getGameP2SResults(getGameP2SResultsSession);
//                    }
//                }
//            } else {
//                isResults = false;
//                isInsert = false;
//                showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            isResults = false;
//            isInsert = false;
//            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(getContext()) > 0) {

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isInsert) {
//          prePurchase(prePurchaseSession);
//          insertEntryNumberP2S(insertEntryNumberP2SSession);
            } else if (isResults) {
                getGameP2SResults(getGameP2SResultsSession);
            } else {
                getGameProducts(getGameProductsSession);
                getGameP2SResults(getGameP2SResultsSession);
            }

        } else {
            isResults = false;
            isInsert = false;
            showNoInternetToast();
        }

    }

    private void getGameProducts(Callback<GetGameProductsResponse> getGameProductsCallback) {
        Call<GetGameProductsResponse> getgameproducts = RetrofitBuild.getGameProductsService(getViewContext())
                .getGameProductsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode);
        getgameproducts.enqueue(getGameProductsCallback);
    }

    private final Callback<GetGameProductsResponse> getGameProductsSession = new Callback<GetGameProductsResponse>() {

        @Override
        public void onResponse(Call<GetGameProductsResponse> call, Response<GetGameProductsResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    mDigitsAdapter.clear();
                    mDigitsAdapter.update(response.body().getGameProductList());

                } else {
                    showError(response.body().getMessage());
                }
            } else {
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetGameProductsResponse> call, Throwable t) {
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void getGameP2SResults(Callback<GetGameP2SResultsResponse> GetGameP2SResultsCallback) {
        Call<GetGameP2SResultsResponse> getgamep2sresults = RetrofitBuild.getGameP2SResultsService(getViewContext())
                .getGameP2SResultsCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        selectedDay);
        getgamep2sresults.enqueue(GetGameP2SResultsCallback);
    }

    private final Callback<GetGameP2SResultsResponse> getGameP2SResultsSession = new Callback<GetGameP2SResultsResponse>() {

        @Override
        public void onResponse(Call<GetGameP2SResultsResponse> call, Response<GetGameP2SResultsResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    if (response.body().getGameResultList().size() > 0) {
                        txvResult.setVisibility(View.GONE);
                        mAdapter.clear();
                        mAdapter.update(response.body().getGameResultList());
                        linearResultsLayout.setVisibility(View.GONE);
                    } else {
                        linearResultsLayout.setVisibility(View.VISIBLE);
                        mAdapter.clear();
                        txvResult.setVisibility(View.VISIBLE);
                    }

                } else {
                    isResults = false;
                    showError(response.body().getMessage());
                }
            } else {
                isResults = false;
                showError();
            }

        }

        @Override
        public void onFailure(Call<GetGameP2SResultsResponse> call, Throwable t) {
            isResults = false;
            showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    public void isResultsEmpty(boolean isEmpty, String selectedDay) {

        Logger.debug("antonhttp", "isEmpty: " + String.valueOf(isEmpty));

        if (isEmpty) {
            txvResult.setVisibility(View.VISIBLE);
            linearResultsLayout.setVisibility(View.VISIBLE);
            edtResultDate.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.getCPMPCDate(selectedDay)));
        } else {
            txvResult.setVisibility(View.GONE);
            linearResultsLayout.setVisibility(View.GONE);
        }
    }

}
