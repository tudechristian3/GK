package com.goodkredit.myapplication.fragments.services;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.VoucherActivity;
import com.goodkredit.myapplication.adapter.billspayment.BillsPaymentBillerItem;
import com.goodkredit.myapplication.adapter.gkservices.GKServicesRecyclerViewAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.GKServiceBadge;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.enums.LayoutVisibilityEnum;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private DatabaseHandler mdb;
    private String userid;
    private String imei;
    private String borrowerid;
    private String sessionid;

    //loader
    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    //swipe refresh
    private SwipeRefreshLayout mSwipeRefreshLayout;

    //no internet connection
    private RelativeLayout nointernetconnection;
    private Button refreshnointernetBtn;

    private List<GKService> gkServices;
    RecyclerView rv_goodapps_gks;
    GKServicesRecyclerViewAdapter mGoodAppsAdapter;

    //FABHeartbeat
    private static FrameLayout fab_bg;
    private ObjectAnimator scaleDown;

    private RecyclerView gView;
    private GKServicesRecyclerViewAdapter mGKSAdapter;

    private View view, view2;
    private List<GKService> services;
    private int mSize = 0;

    public static boolean isActive = false;

    //AUTOLOGOUT
    private boolean autoLogoutisShowing = false;
    private boolean isSearch = false;

    //NEW VARIABLES FOR SECURITY
    private String index;
    private String authenticationid;
    private String keyEncryption;
    private String param;

    //for session
    private String sessionindex;
    private String sessionauth;
    private String sessionkey;
    private String sessionparam;

    private String getBillersIndex;
    private String billerAuthentication;
    private String billerKeyEncryption;
    private String billerParam;

    private String menuIndex;
    private String menuAuthenticatioid;
    private String menuKeyEncryption;
    private String menuParam;

    private String getGKIndex;
    private String getGKAuthenticationid;
    private String getGKKeyEncryption;
    private String getGKParam;

    //Shimmering effect
    private ShimmerFrameLayout mShimmerViewContainer;
    private LinearLayout skeleton_services;

    private int counter = 0;
    private List<GKService> gkServiceList = new ArrayList<>();
    private List<GKServiceBadge> gkServiceBadges = new ArrayList<>();

    private MenuItem menus;

    private ImageView qrcodegenerate,qrImgClose;
    private TextView btnGenerateQrCode;

    private MaterialDialog mPickUpDialog;
    private ProgressBar progressCirular;


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        try {
            super.onCreateOptionsMenu(menu, inflater);
            setUpMenu(menu);

            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.clearFocus();

            // Detect SearchView icon clicks
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setItemsVisibility(menu, searchItem, false);
                    searchView.requestFocus();
                }
            });
            // Detect SearchView close
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    setUpMenu(menu);
                    return false;
                }
            });

            // Get data in SearchView
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String searchText) {
                    //                searchServices(searchText);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String searchText) {
                    isSearch = true;
                    searchServices(searchText);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchServices(String searchText) {
        if (searchText.trim().length() > 0) {
            view.findViewById(R.id.gks_search_results).setVisibility(View.VISIBLE);
            view.findViewById(R.id.gks_main).setVisibility(View.GONE);
            LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_to_left);
            gView.setLayoutAnimation(controller);
            gView.scheduleLayoutAnimation();
            gView.setAdapter(new GKServicesRecyclerViewAdapter(getViewContext(), mdb.getAllGKServicesBYKeyword(mdb, searchText.trim()), true));
        } else {
            view.findViewById(R.id.gks_search_results).setVisibility(View.GONE);
            view.findViewById(R.id.gks_main).setVisibility(View.VISIBLE);
            LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_to_left);
            gView.setLayoutAnimation(controller);
            gView.scheduleLayoutAnimation();
            gView.setAdapter(new GKServicesRecyclerViewAdapter(getViewContext(), mdb.getGKServicesForKeyword(mdb, ""), false));
        }
    }

    private void setUpMenu(Menu menu) {
        menu.findItem(R.id.action_notification_0).setVisible(false);
        menu.findItem(R.id.action_notification_1).setVisible(false);
        menu.findItem(R.id.action_notification_2).setVisible(false);
        menu.findItem(R.id.action_notification_3).setVisible(false);
        menu.findItem(R.id.action_notification_4).setVisible(false);
        menu.findItem(R.id.action_notification_5).setVisible(false);
        menu.findItem(R.id.action_notification_6).setVisible(false);
        menu.findItem(R.id.action_notification_7).setVisible(false);
        menu.findItem(R.id.action_notification_8).setVisible(false);
        menu.findItem(R.id.action_notification_9).setVisible(false);
        menu.findItem(R.id.action_notification_9plus).setVisible(false);

        switch (CommonVariables.notifCount) {
            case 0: {
                menu.findItem(R.id.action_notification_0).setVisible(true);
                break;
            }
            case 1: {
                menu.findItem(R.id.action_notification_1).setVisible(true);
                break;
            }
            case 2: {
                menu.findItem(R.id.action_notification_2).setVisible(true);
                break;
            }
            case 3: {
                menu.findItem(R.id.action_notification_3).setVisible(true);
                break;
            }
            case 4: {
                menu.findItem(R.id.action_notification_4).setVisible(true);
                break;
            }
            case 5: {
                menu.findItem(R.id.action_notification_5).setVisible(true);
                break;
            }
            case 6: {
                menu.findItem(R.id.action_notification_6).setVisible(true);
                break;
            }
            case 7: {
                menu.findItem(R.id.action_notification_7).setVisible(true);
                break;
            }
            case 8: {
                menu.findItem(R.id.action_notification_8).setVisible(true);
                break;
            }
            case 9: {
                menu.findItem(R.id.action_notification_9).setVisible(true);
                break;
            }
            default: {
                menu.findItem(R.id.action_notification_9plus).setVisible(true);
                break;
            }
        }

        menu.findItem(R.id.action_process_queue).setVisible(true);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.generate_qr).setVisible(true);


    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_services, container, false);
            view2 = inflater.inflate(R.layout.layout_view_qr, container, false);
            setHasOptionsMenu(true);
            mdb = new DatabaseHandler(getViewContext());

            init(view);
            initData();

        return view;
    }





    private void init(View view) {
        FloatingActionButton fabBtnVoucher = view.findViewById(R.id.fabBtnVoucher);
        fabBtnVoucher.setOnClickListener(this);

        fab_bg = view.findViewById(R.id.fab_bg);
        scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                fab_bg,
                PropertyValuesHolder.ofFloat("scaleX", 1.5f),
                PropertyValuesHolder.ofFloat("scaleY", 1.5f));
        scaleDown.setDuration(850);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
        fab_bg.setVisibility(View.GONE);

        //swipe refresh
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setEnabled(true);

        //loader
        mLlLoader = view.findViewById(R.id.loaderLayout);
        mTvFetching = view.findViewById(R.id.fetching);
        mTvWait = view.findViewById(R.id.wait);

        //no internet connection
        nointernetconnection = view.findViewById(R.id.nointernetconnection);
        refreshnointernetBtn = view.findViewById(R.id.refreshnointernet);
        refreshnointernetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        skeleton_services = view.findViewById(R.id.skeleton_services);
        skeleton_services.setVisibility(View.VISIBLE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        setupGKMenuServices(view);

    }

    private void initData() {

        imei = PreferenceUtils.getImeiId(getViewContext());
        userid = PreferenceUtils.getUserId(getViewContext());
        borrowerid = PreferenceUtils.getBorrowerId(getViewContext());
        sessionid = PreferenceUtils.getSessionID(getViewContext());

        services = new ArrayList<>();
        gkServices = new ArrayList<>();
        gkServiceList = new ArrayList<>();
        gkServiceBadges = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new GetBillersRunnable());

    }

//    private void validatesession() {
//
//        showNoInternetConnection(false);
//        mShimmerViewContainer.setVisibility(View.VISIBLE);
//        mShimmerViewContainer.startShimmer();
//
//        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
//            validateSession(validateSessionCallback);
//        } else {
//            mShimmerViewContainer.stopShimmer();
//            visibilityType(LayoutVisibilityEnum.NOINTERNET);
//            //showNoInternetToast();
//        }
//    }

    private void validateSession(Callback<GenericResponse> validateSessionCallback) {
        Call<GenericResponse> validatesession = RetrofitBuild.getAccountAPIService(getViewContext())
                .validateSessionCall(sessionid,
                        imei,
                        borrowerid,
                        userid,
                        "ANDROID");

        validatesession.enqueue(validateSessionCallback);
    }

    private final Callback<GenericResponse> validateSessionCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mSwipeRefreshLayout.setRefreshing(false);
            try {
                if (errorBody == null) {

                    assert response.body() != null;

                    if (response.body().getStatus().equals("000")) {
                        updateData();
                    } else {
                        autoLogoutisShowing = true;
                        checkIfActivityIsBeingFinished(response.body().getMessage());
                    }
                } else {
                    autoLogoutisShowing = true;
                    checkIfActivityIsBeingFinished("Invalid Session");
                }
            } catch (Exception e) {
                mShimmerViewContainer.stopShimmer();
                mSwipeRefreshLayout.setRefreshing(false);
                visibilityType(LayoutVisibilityEnum.NOINTERNET);
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
            t.printStackTrace();
        }
    };

    @Override
    public void onRefresh() {
 		mSwipeRefreshLayout.setRefreshing(true);
        getSession();
    }

    public class LongFirstOperationTask extends AsyncTask<List<GKService>, Void, List<GKService>> {

        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {
            return mdb.getGKSERVICES_MAINMENU(mdb);
        }

        @Override
        protected void onPostExecute(List<GKService> services) {
            if (isAdded()) {
                if (services.size() > 0) {
                    updateData();
                } else {
                    getSession();
                }
            }
        }
    }

    private void getSession() {
        visibilityType(LayoutVisibilityEnum.SHIMMERING);
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
            //getGoodKreditServices();
            counter += 1;
            getGoodkreditServicesV2();
            Logger.debug("okhttp","COUNTTTTTTTTTTTT: "+counter);
        } else {
		    mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }
    }

//    private void getGoodKreditServices() {
//        Call<GetGoodKreditServicesResponse> call = RetrofitBuild.getGKSerivcesAPIService(getViewContext())
//                .getGoodKreditServices(
//                        imei,
//                        userid,
//                        CommonFunctions.getSha1Hex(imei + userid + sessionid),
//                        sessionid,
//                        borrowerid
//                );
//
//        call.enqueue(getGoodKreditServicesCallback);
//    }
//
//    private Callback<GetGoodKreditServicesResponse> getGoodKreditServicesCallback = new Callback<GetGoodKreditServicesResponse>() {
//        @Override
//        public void onResponse(Call<GetGoodKreditServicesResponse> call, Response<GetGoodKreditServicesResponse> response) {
//            mShimmerViewContainer.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            mSwipeRefreshLayout.setRefreshing(false);
//            ResponseBody errBody = response.errorBody();
//            if (errBody == null) {
//                if (response.body().getStatus().equals("000")) {
//                    if (response.body().getGkServiceList().size() > 0) {
//                        new insertGKServicesTask().execute(response.body().getGkServiceList());
//                        new insertGKSBadgeTask().execute(response.body().getGkServiceBadges());
//                        updateData();
//                        getBorrowerMenuServices();
//                    }
//
//                } else if (response.body().getStatus().equals("003")) {
//                    if (!autoLogoutisShowing) {
//                        checkIfActivityIsBeingFinished(response.body().getMessage());
//                    }
//                } else {
//                    if (!autoLogoutisShowing) {
//                        showErrorGlobalDialogs(response.body().getMessage());
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GetGoodKreditServicesResponse> call, Throwable t) {
//            mShimmerViewContainer.stopShimmer();
//            mShimmerViewContainer.setVisibility(View.GONE);
//            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//            mSwipeRefreshLayout.setRefreshing(false);
//            t.printStackTrace();
//        }
//    };
//
//    //Newly added as of Oct. 10,2019
//    private void getBorrowerMenuServices() {
//        String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//        Call<GetGoodKreditServicesResponse> call = RetrofitBuild.getGKSerivcesAPIService(getViewContext())
//                .getGKServicesMenu(
//                        imei,
//                        userid,
//                        authcode,
//                        sessionid,
//                        borrowerid
//                );
//
//        call.enqueue(getGKServicesMenuCallback);
//    }
//
//    private Callback<GetGoodKreditServicesResponse> getGKServicesMenuCallback = new Callback<GetGoodKreditServicesResponse>() {
//        @Override
//        public void onResponse(Call<GetGoodKreditServicesResponse> call, Response<GetGoodKreditServicesResponse> response) {
//            try {
//                mSwipeRefreshLayout.setRefreshing(false);
//                ResponseBody errBody = response.errorBody();
//                if (errBody == null) {
//                    if (response.body().getStatus().equals("000")) {
//                        if (response.body().getGkServiceList().size() > 0) {
//                            new insertGKServicesMenuTask().execute(response.body().getGkServiceList());
//                            updateData();
//                        }
//
//                    } else if (response.body().getStatus().equals("003")) {
//                        if (!autoLogoutisShowing) {
//                            checkIfActivityIsBeingFinished(response.body().getMessage());
//                        }
//                    } else {
//                        if (!autoLogoutisShowing) {
//                            showErrorGlobalDialogs(response.body().getMessage());
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<GetGoodKreditServicesResponse> call, Throwable t) {
//            mSwipeRefreshLayout.setRefreshing(false);
//            mLlLoader.setVisibility(View.GONE);
//
//            t.printStackTrace();
//        }
//    };

    private class insertGKServicesMenuTask extends AsyncTask<List<GKService>, Void, List<GKService>> {


        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {
            if(lists.length > 0){
                if (mdb != null) {
                    for (GKService gkService : lists[0]) {
                        if(!gkService.getCategory().equals("MAIN")){
                            mdb.insertGKSERVICES_MAINMENU(mdb, gkService);
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<GKService> services) {
            updateData();
        }
    }

    /*******************************************/

    private class insertGKServicesTask extends AsyncTask<List<GKService>, Void, List<GKService>> {

        @Override
        protected void onPreExecute() {
            if (mdb != null) {
                mdb.truncateTable(mdb, DatabaseHandler.GK_SERVICES);
                mdb.truncateTable(mdb, DatabaseHandler.GKSERVICES_MENU);
            }
        }

        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {

            if (mdb != null) {
                for (GKService gkService : lists[0]) {
                    mdb.insertGKServices(mdb, gkService);
                    if (gkService.getCategory().equals("MAIN")) {
                        mdb.insertGKSERVICES_MAINMENU(mdb, gkService);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<GKService> services) {
            super.onPostExecute(services);
        }
    }

    private class insertGKSBadgeTask extends AsyncTask<List<GKServiceBadge>, Void, List<GKServiceBadge>> {

        @Override
        protected void onPreExecute() {
            if (mdb != null) {
                mdb.trucateGKSBadge(mdb);
            }
        }

        @Override
        protected List<GKServiceBadge> doInBackground(List<GKServiceBadge>... lists) {
            if (mdb != null) {
                for (GKServiceBadge badge : lists[0]) {
                    mdb.insertGKSBadge(mdb, badge);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<GKServiceBadge> services) {
            super.onPostExecute(services);
        }
    }

    private void updateData() {
        new getGoodAppsServicesTask().execute();
    }

    private class getGoodAppsServicesTask extends AsyncTask<List<GKService>, Void, List<GKService>> {

        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {
            services = mdb.getGKSERVICES_MAINMENU(mdb);
            mSize = services.size();
            return getGoodAppsServices(services);
        }
        @Override
        protected void onPostExecute(List<GKService> servicesData) {
            if (servicesData.size() > 0) {
                mGoodAppsAdapter = new GKServicesRecyclerViewAdapter(getViewContext(), false);
                mGoodAppsAdapter.updateData(servicesData);
                rv_goodapps_gks.setAdapter(mGoodAppsAdapter);
                visibilityType(LayoutVisibilityEnum.HASDATA);
            }

        }
    }

    private void setupGKMenuServices(View view) {

        rv_goodapps_gks = view.findViewById(R.id.rv_goodapps_gks);
        rv_goodapps_gks.setLayoutManager(new GridLayoutManager(getViewContext(), CommonFunctions.calculateNoOfColumns(getViewContext(), "SERVICES")));
        rv_goodapps_gks.setNestedScrollingEnabled(false);

        rv_goodapps_gks.setItemAnimator(new DefaultItemAnimator());
        Objects.requireNonNull(rv_goodapps_gks.getItemAnimator()).setAddDuration(1500);
        rv_goodapps_gks.getItemAnimator().setMoveDuration(1500);

        mGoodAppsAdapter = new GKServicesRecyclerViewAdapter(getViewContext(), false);
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);
        rv_goodapps_gks.setLayoutAnimation(controller);
        rv_goodapps_gks.scheduleLayoutAnimation();
        rv_goodapps_gks.setAdapter(mGoodAppsAdapter);


        gView = view.findViewById(R.id.gks_gridView);
        gView.setLayoutManager(new GridLayoutManager(getViewContext(), CommonFunctions.calculateNoOfColumns(getViewContext(), "SERVICES")));
        gView.setNestedScrollingEnabled(false);

        gView.setItemAnimator(new DefaultItemAnimator());
        Objects.requireNonNull(gView.getItemAnimator()).setAddDuration(1500);
        gView.getItemAnimator().setMoveDuration(1500);

        mGKSAdapter = new GKServicesRecyclerViewAdapter(getViewContext(), false);
        LayoutAnimationController controller1 =
                AnimationUtils.loadLayoutAnimation(getViewContext(), R.anim.layout_animation);
        gView.setLayoutAnimation(controller1);
        gView.scheduleLayoutAnimation();
        gView.setAdapter(mGKSAdapter);

    }

    //EXCLUDED SERVICES
    private List<GKService> getGoodAppsServices(List<GKService> gkServices) {

        GKService allApps = new GKService(2020,
                "ALLSERVICES",
                "All Services",
                "APP",
                "ACTIVE",
                "",
                ".",
                ".",
                ".",
                ".",
                ".",
                "MAIN",
                "#24BDD9",
                2020);
        gkServices.add(0, allApps);

        return new ArrayList<>(gkServices);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabBtnVoucher: {
                CommonVariables.ISNEWVOUCHER = false;
                Intent intent = new Intent(getViewContext(), VoucherActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hasNewVoucher();
        validateSessionV2();
    }
    public ArrayList<BillsPaymentBillerItem> getAllBillers(String searchbillername) {
        ArrayList<BillsPaymentBillerItem> arrayList = new ArrayList<>();
        try {

            Cursor c = mdb.getBillers(mdb, "FALSE", searchbillername);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    String billercode = c.getString(c.getColumnIndex("BillerCode"));
                    String serviceprvbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                    String billername = c.getString(c.getColumnIndex("BillerName"));
                    String category = c.getString(c.getColumnIndex("Category"));
                    String logourl = c.getString(c.getColumnIndex("LogoURL"));
                    arrayList.add(new BillsPaymentBillerItem(logourl, billercode, billername, category, "", serviceprvbillercode));

                } while (c.moveToNext());
            }
            c.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return arrayList;
    }

    @Override
    public void onPause() {
        isActive = false;
        super.onPause();
        Objects.requireNonNull(getActivity()).overridePendingTransition(0, 0);
    }

    public static void hasNewVoucher() {
        if (CommonVariables.ISNEWVOUCHER) {
            fab_bg.setVisibility(View.VISIBLE);
        } else {
            fab_bg.setVisibility(View.GONE);
        }
    }

    private void checkIfActivityIsBeingFinished(String message) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Objects.requireNonNull(getActivity()).isFinishing()) {
               if(!autoLogoutisShowing){
                    showAutoLogoutDialog(message);
                }
            }
        } else {
            if (!Objects.requireNonNull(getActivity()).isFinishing()) {
                if(!autoLogoutisShowing){
                    showAutoLogoutDialog(message);
                }
            }
        }
    }


    /**
     * SECURITY UPDATE
     * AS OF
     * OCTOBER 2019
     */

    /**********GET SERVICES************/
    private void getGoodkreditServicesV2() {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {
                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                getGKIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", getGKIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                getGKAuthenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
                getGKKeyEncryption = CommonFunctions.getSha1Hex(getGKAuthenticationid + sessionid + "getGoodKreditServicesV3");
                getGKParam = CommonFunctions.encryptAES256CBC(getGKKeyEncryption, String.valueOf(paramJson));

                getGoodKreditServicesV2Object(getGoodKreditServicesCallbackV2);

            } else {
                visibilityType(LayoutVisibilityEnum.NOINTERNET);
            }


    }
    private void getGoodKreditServicesV2Object(Callback<GenericResponse> getGoodkreditServicesCallback) {
        Call<GenericResponse> getServices = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getGoodKreditServicesV2(
                        getGKAuthenticationid, sessionid, getGKParam
                );

        getServices.enqueue(getGoodkreditServicesCallback);
    }
    private Callback<GenericResponse> getGoodKreditServicesCallbackV2 = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            mSwipeRefreshLayout.setRefreshing(false);
            ResponseBody errBody = response.errorBody();
            if (errBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(getGKKeyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {

                    String decrypteData = CommonFunctions.decryptAES256CBC(getGKKeyEncryption, response.body().getData());

                    String services = CommonFunctions.parseJSON(decrypteData, "services");
                    String badge = CommonFunctions.parseJSON(decrypteData, "badge");

                    Logger.debug("okhttp","DATA ::::: "+decrypteData);

                    gkServiceList = new Gson().fromJson(services, new TypeToken<List<GKService>>() {
                    }.getType());
                    gkServiceBadges = new Gson().fromJson(badge, new TypeToken<List<GKServiceBadge>>() {
                    }.getType());

                    if (gkServiceList.size() > 0) {
                        new insertGKServicesTask().execute(gkServiceList);
                        new insertGKSBadgeTask().execute(gkServiceBadges);
                        //getBorrowerMenuServices();
                    }
                    getGKServicesMenuV2();

                }else if (response.body().getStatus().equals("003") ||response.body().getStatus().equals("002")) {
                    showAutoLogoutDialog(getString(R.string.logoutmessage));
                }else {
                    if (!autoLogoutisShowing) {
                        showErrorGlobalDialogs(decryptedMessage);
                    }
                }
            }else{
                showErrorGlobalDialogs();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
            t.printStackTrace();
        }
    };


    /**********GET BILLERS************/
    private void getBillers() {

            if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

                LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
                parameters.put("imei", imei);
                parameters.put("userid", userid);
                parameters.put("borrowerid", borrowerid);
                parameters.put("devicetype",CommonVariables.devicetype);

                LinkedHashMap indexAuthMapObject;
                indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
                String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
                getBillersIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

                parameters.put("index", getBillersIndex);
                String paramJson = new Gson().toJson(parameters, Map.class);

                //ENCRYPTION
                billerAuthentication = CommonFunctions.parseJSON(jsonString, "authenticationid");
                billerKeyEncryption = CommonFunctions.getSha1Hex(billerAuthentication + sessionid + "getBillersV2");
                billerParam = CommonFunctions.encryptAES256CBC(billerKeyEncryption, String.valueOf(paramJson));

                getBillersObject(getBillersSession);

            } else {
                visibilityType(LayoutVisibilityEnum.NOINTERNET);
                //showNoInternetToast();
            }

    }
    private void getBillersObject(Callback<GenericResponse> getBillersCallback) {
        Call<GenericResponse> getBillers = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getBillers(
                        billerAuthentication, sessionid, billerParam
                );
        getBillers.enqueue(getBillersCallback);
    }
    private final Callback<GenericResponse> getBillersSession = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {
                String decryptedMessage = CommonFunctions.decryptAES256CBC(billerKeyEncryption, response.body().getMessage());
                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(billerKeyEncryption, response.body().getData());
                    if (decryptedMessage.equalsIgnoreCase("error") || decryptedData.equalsIgnoreCase("error")) {
                        showErrorToast();
                        visibilityType(LayoutVisibilityEnum.NOINTERNET);
                    } else {
                        try {
                            JSONArray jsonArr = new JSONArray(decryptedData);
                            mdb.deleteBillers(mdb, "FALSE");
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

                                mdb.insertBiller(mdb, serviceprovider, billercode, billername, billerdesc, servicecode, category, billerinfo, logourl, customerscclass, "FALSE", ".", ".", notes, groupcategory, categorylogo);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    if (response.body().getStatus().equalsIgnoreCase("error")) {
                        showErrorToast();
                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                        if (!autoLogoutisShowing) {
                            showAutoLogoutDialog(getString(R.string.logoutmessage));
                        }
                    } else {
                        showToast(decryptedMessage, GlobalToastEnum.WARNING);
                    }
                }
            } else {
                showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
                visibilityType(LayoutVisibilityEnum.NOINTERNET);
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            showToast("Something went wrong on your connection. Please check.", GlobalToastEnum.NOTICE);
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }
    };

    /**********GET ADDED MENU SERVICES************/
    private void getGKServicesMenuV2(){
        if (CommonFunctions.getConnectivityStatus(getViewContext()) > 0) {

            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("userid", userid);
            parameters.put("borrowerid", borrowerid);
            parameters.put("devicetype",CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            menuIndex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", menuIndex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            menuAuthenticatioid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            menuKeyEncryption = CommonFunctions.getSha1Hex(menuAuthenticatioid + sessionid + "getGKServicesMenuV3");
            menuParam = CommonFunctions.encryptAES256CBC(menuKeyEncryption, String.valueOf(paramJson));

            getGKServicesMenuV2Object(getGKServicesMenuV2Callback);

        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
            //showNoInternetToast();
        }
    }
    private void getGKServicesMenuV2Object(Callback<GenericResponse> getGKServicesMenu) {
        Call<GenericResponse> call = RetrofitBuilder.getGkServiceV2API(getViewContext())
                .getGKServicesMenuV2(
                        menuAuthenticatioid, sessionid, menuParam
                );
        call.enqueue(getGKServicesMenu);
    }
    private Callback<GenericResponse> getGKServicesMenuV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

                mSwipeRefreshLayout.setRefreshing(false);
                ResponseBody errBody = response.errorBody();
                if (errBody == null) {
                    String message  = CommonFunctions.decryptAES256CBC(menuKeyEncryption,response.body().getMessage());
                    if (response.body().getStatus().equals("000")) {
                         String data  = CommonFunctions.decryptAES256CBC(menuKeyEncryption,response.body().getData());

                         String service = CommonFunctions.parseJSON(data,"services");
                         String badge = CommonFunctions.parseJSON(data,"badge");

                         gkServiceList =  new Gson().fromJson(service,new TypeToken<List<GKService>>(){}.getType());
                         //gkServiceBadges = new Gson().fromJson(badge,new TypeToken<List<GKServiceBadge>>(){}.getType());
                        Logger.debug("okhttp","DATA : "+ data);
                        new insertGKServicesMenuTask().execute(gkServiceList);

                    } else if (response.body().getStatus().equals("003") || response.body().getStatus().equals("002")) {
                          if(!autoLogoutisShowing){
                              showAutoLogoutDialog(getString(R.string.logoutmessage));
                          }
                    } else {
                        visibilityType(LayoutVisibilityEnum.NOINTERNET);
                        if (!autoLogoutisShowing) {
                            showErrorGlobalDialogs(message);
                        }
                    }
                }else{
                    visibilityType(LayoutVisibilityEnum.NOINTERNET);
                    showErrorGlobalDialogs();
                }

        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
            showErrorGlobalDialogs();
            t.printStackTrace();
        }
    };

    //VALIDATE SESSION V2
    private void validateSessionV2(){

        visibilityType(LayoutVisibilityEnum.SHIMMERING);

        if(CommonFunctions.getConnectivityStatus(getViewContext())> 0){
            LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
            parameters.put("imei", imei);
            parameters.put("borrowerid", borrowerid);
            parameters.put("userid", userid);
            parameters.put("devicetype",CommonVariables.devicetype);

            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getViewContext(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            sessionindex = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", sessionindex);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            sessionauth = CommonFunctions.parseJSON(jsonString, "authenticationid");
            sessionkey = CommonFunctions.getSha1Hex(sessionauth + sessionid + "validateSessionV2");
            sessionparam = CommonFunctions.encryptAES256CBC(sessionkey, String.valueOf(paramJson));

            validateSessionV2Object(validateSessionV2Callback);
        }else{
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
        }


    }
    private void validateSessionV2Object(Callback<GenericResponse> validateSessionCallback) {
        Call<GenericResponse> validatesession = RetrofitBuilder.getCommonV2API(getViewContext())
                .validateSessionV2(sessionauth,sessionid,sessionparam);
        validatesession.enqueue(validateSessionCallback);
    }
    private final Callback<GenericResponse>validateSessionV2Callback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
            ResponseBody errorBody = response.errorBody();
            mSwipeRefreshLayout.setRefreshing(false);
                if (errorBody == null) {
                    String message = CommonFunctions.decryptAES256CBC(sessionkey,response.body().getMessage());
                    assert response.body() != null;
                    if (response.body().getStatus().equals("000")) {
                        updateData();
                    }else if(response.body().getStatus().equals("003") || response.body().getStatus().equals("002")){
                        showAutoLogoutDialog(getString(R.string.logoutmessage));
                        autoLogoutisShowing = true;
                    }else {
                        showError(message);
                    }
                } else {
                     showErrorGlobalDialogs();
                }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
            mShimmerViewContainer.stopShimmer();
            mSwipeRefreshLayout.setRefreshing(false);
            visibilityType(LayoutVisibilityEnum.NOINTERNET);
            t.printStackTrace();
        }
    };


    /************************/
    class GetBillersRunnable implements Runnable{

        @Override
        public void run() {
            Logger.debug("roneldayanan","GET BILLERS RUNNABLE RUNNING");
            new LongFirstOperationTask().execute();
            if (getAllBillers("").size() == 0) {
                //new HttpAsyncTask2().execute(CommonVariables.GETBILLERS);
                getBillers();
            }
        }
    }

    //layouts
    private void visibilityType(LayoutVisibilityEnum visibilityEnum){
       switch (visibilityEnum){
           case HASDATA:
               mShimmerViewContainer.setVisibility(View.GONE);
               nointernetconnection.setVisibility(View.GONE);
               mSwipeRefreshLayout.setVisibility(View.VISIBLE);
               break;
           case NOINTERNET:
               if (mdb.getGKServices(mdb).size() > 0) {
                   updateData();
               }else{
                   mShimmerViewContainer.setVisibility(View.GONE);
                   nointernetconnection.setVisibility(View.VISIBLE);
                   mSwipeRefreshLayout.setVisibility(View.GONE);
               }

               break;
           case SHIMMERING:
               mShimmerViewContainer.setVisibility(View.VISIBLE);
               nointernetconnection.setVisibility(View.GONE);
               mSwipeRefreshLayout.setVisibility(View.GONE);
               mShimmerViewContainer.startShimmer();
               break;
       }
    }

    private void validateQRPartialV2() {
        if(CommonFunctions.getConnectivityStatus(getActivity()) > 0) {

            LinkedHashMap<String,String> parameters = new LinkedHashMap<>();
            parameters.put("imei",imei);
            parameters.put("userid",userid);
            parameters.put("borrowerid",borrowerid);
            parameters.put("devicetype", CommonVariables.devicetype);


            LinkedHashMap indexAuthMapObject;
            indexAuthMapObject = CommonFunctions.getIndexAndAuthIDWithSession(getActivity(), parameters, sessionid);
            String jsonString = new Gson().toJson(indexAuthMapObject, Map.class);
            index = CommonFunctions.parseJSON(String.valueOf(jsonString), "index");

            parameters.put("index", index);
            String paramJson = new Gson().toJson(parameters, Map.class);

            //ENCRYPTION
            authenticationid = CommonFunctions.parseJSON(jsonString, "authenticationid");
            keyEncryption = CommonFunctions.getSha1Hex(authenticationid + sessionid + "generateBorrowersQRCode");
            param = CommonFunctions.encryptAES256CBC(keyEncryption, String.valueOf(paramJson));

            String checker = new Gson().toJson(parameters);

            validateQRCode(validateQRCodeCallback);

        } else {
//            hideProgressDialog();
//            showNoInternetToast();
//            final Handler scanBCHandler = new Handler();
//            scanBCHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    bc.resume();
//                    scanQRCode();
//                }
//            }, 1000);
            Toast.makeText(getContext(), "qwe", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateQRCode(Callback<GenericResponse> validateQRPartial) {
        Call<GenericResponse> call = RetrofitBuilder.getPayByQRCodeV2API(getContext())
                .generateBorrowersQRCode(
                        authenticationid,sessionid,param
                );
        call.enqueue(validateQRPartial);
    }

    private Callback<GenericResponse> validateQRCodeCallback = new Callback<GenericResponse>() {
        @Override
        public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {

            ResponseBody errBody = response.errorBody();
            if (errBody == null) {

                String decryptedMessage = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getMessage());

                if (response.body().getStatus().equals("000")) {
                    String decryptedData = CommonFunctions.decryptAES256CBC(keyEncryption,response.body().getData());
                    QRCodeWriter writer = new QRCodeWriter();
                    try {
                        BitMatrix bitMatrix = writer.encode(decryptedData, BarcodeFormat.QR_CODE, 300, 300);
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();

                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }
                        progressCirular.setVisibility(View.GONE);
                        qrcodegenerate.setVisibility(View.VISIBLE);
                        qrcodegenerate.setImageBitmap(bmp);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                } else {
                    final Handler scanBCHandler = new Handler();
//                    scanBCHandler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            bc.resume();
//                            scanQRCode();
//                        }
//                    }, 1000);
//                    hideProgressDialog();
//                    showError(decryptedMessage);
                }
            } else {
                final Handler scanBCHandler = new Handler();
//                scanBCHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        bc.resume();
//                        scanQRCode();
//                    }
//                }, 1000);
//                hideProgressDialog();
//                showError();
            }
        }

        @Override
        public void onFailure(Call<GenericResponse> call, Throwable t) {
//            final Handler scanBCHandler = new Handler();
//            scanBCHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    bc.resume();
//                    scanQRCode();
//                }
//            }, 1000);
//            hideProgressDialog();
//            showError();
            Toast.makeText(getContext(), "qwe", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        this.menus = item;
        int id = menus.getItemId();
        if(id == R.id.generate_qr){

            mPickUpDialog = new MaterialDialog.Builder(getViewContext())
                    .cancelable(true)
                    .customView(R.layout.layout_view_qr, false)
                    .build();
                View view = mPickUpDialog.getCustomView();
                qrcodegenerate = view.findViewById(R.id.qrcodegenerate);
                btnGenerateQrCode = view.findViewById(R.id.btnGenerateQrCode);
                qrImgClose = view.findViewById(R.id.qrImgClose);
                progressCirular = view.findViewById(R.id.progressCirular);
                validateQRPartialV2();

                view.findViewById(R.id.qrImgClose).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPickUpDialog.dismiss();
                    }
                });

                view.findViewById(R.id.btnGenerateQrCode).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressCirular.setVisibility(View.VISIBLE);
                        qrcodegenerate.setVisibility(View.GONE);
                        validateQRPartialV2();
                    }
                });

            mPickUpDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }
}
