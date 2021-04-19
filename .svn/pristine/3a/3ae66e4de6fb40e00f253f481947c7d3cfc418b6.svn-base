package com.goodkredit.myapplication.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.SponsorAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.Sponsor;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.CreateSession;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.sponsors.SponsorDetails;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
* ALGO:
* 1. Check Enternet connection
* 2. Verify Session
* 3. Fetch Guarantors
* 4. Process Result
* */
public class PublicSponsorsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    //Declaration
    DatabaseHandler db;
    CommonFunctions cf;
    CommonVariables cv;
    Context mcontext;
    View rootView;
    ListView listView;
    static RelativeLayout emptyvoucher;
    static ImageView refresh;
    static RelativeLayout nointernetconnection;
    static ImageView refreshnointernet;
    static ImageView refreshdisabled;
    static ImageView refreshdisabled1;
    static Button loadmore;

    static String borrowerid = "";
    static String sessionidval = "";
    static String imei = "";
    static String userid = "";
    static String isloadmore = "false";

    private SwipeRefreshLayout swipeRefreshLayout;

    static String FULLYLOADED = "false";

    int currentlimit = 0;
    static Button btnAddMore;

    private ArrayList<Sponsor> arrayList;
    private SponsorAdapter adapter;

    private RelativeLayout mLlLoader;
    private TextView mTvFetching;
    private TextView mTvWait;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
        menu.findItem(R.id.action_process_queue).setVisible(false);
        menu.findItem(R.id.sortitem).setVisible(false);
        menu.findItem(R.id.summary).setVisible(false);
        menu.findItem(R.id.group_voucher).setVisible(false);
        menu.findItem(R.id.action_search).setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //inflate view
        rootView = inflater.inflate(R.layout.fragment_guarantors, container, false);

        //Set context
        mcontext = getActivity();

        try {
            //initialized local database
            db = new DatabaseHandler(getActivity());

            btnAddMore = new Button(getActivity());
            btnAddMore.setBackgroundResource(R.color.colorwhite);
            btnAddMore.setTextColor(getResources().getColor(R.color.buttons));
            btnAddMore.setTypeface(Typeface.DEFAULT_BOLD);

            //initialize refresh
            swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
            swipeRefreshLayout.setOnRefreshListener(this);
            emptyvoucher = rootView.findViewById(R.id.emptyvoucher);
            refresh = rootView.findViewById(R.id.refresh);
            nointernetconnection = rootView.findViewById(R.id.nointernetconnection);
            refreshnointernet = rootView.findViewById(R.id.refreshnointernet);
            refreshdisabled = rootView.findViewById(R.id.refreshdisabled);
            refreshdisabled1 = rootView.findViewById(R.id.refreshdisabled1);
            loadmore = rootView.findViewById(R.id.viewarchive);

            mLlLoader = rootView.findViewById(R.id.loaderLayout);
            mTvFetching = rootView.findViewById(R.id.fetching);
            mTvWait = rootView.findViewById(R.id.wait);


            // Hashmap for ListView
            arrayList = new ArrayList<>();
            listView = rootView.findViewById(R.id.list);
            adapter = new SponsorAdapter(this.getContext(), getAllPublicGuarantors());
            listView.setAdapter(adapter);

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

            //get account information
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());

                //1.
                int status = CommonFunctions.getConnectivityStatus(getActivity());
                if (status == 0) { //no connection
                    showNoInternetConnection();
                    showToast("No internet connection.", GlobalToastEnum.NOTICE);
                } else { //has connection proceed
                    //2.
                    if (CommonVariables.GUARANTORSISFIRSTLOAD.equals("true")) {
                        verifySession();
                    }

                }

            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        /***********
         * TRIGGERS
         * */


        //REFRESH
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentlimit = 0;
                isloadmore = "false";
                disableRefresh();
                verifySession();
            }
        });

        //refresh in no internet connection indicator
        refreshnointernet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentlimit = 0;
                isloadmore = "false";
                disableRefresh();
                verifySession();
            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

                // We take the last son in the scrollview
                View views = listView.getChildAt(listView.getChildCount() - 1);
                int diff = (views.getBottom() - (listView.getHeight() + listView.getScrollY()));


                btnAddMore.setText("Load More");


                // if diff is zero, then the bottom has been reached
                if (diff == 0) {
                    // do stuff
                    if (listView.getFooterViewsCount() <= 0) {
                        listView.addFooterView(btnAddMore);
                    }
                }

                btnAddMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        currentlimit = currentlimit + 30;
                        isloadmore = "true";
                        verifySession();
                    }
                });
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                if (view.getLastVisiblePosition() == (totalItemCount - 1)) {

                }
            }

        });


        return rootView;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (isVisibleToUser) {//dosomething when the fragment is visible
                if (getView() != null) {
                    preloadGuarantors();
                }
            }
        } catch (Exception e) {
        }


    }

    @Override
    public void onRefresh() {
        try {
            isloadmore = "false";
            currentlimit = 0;
            swipeRefreshLayout.setRefreshing(true);
            verifySession();

        } catch (Exception e) {
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Cursor cursor = db.getAccountInformation(db);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                    userid = cursor.getString(cursor.getColumnIndex("mobile"));
                    imei = cursor.getString(cursor.getColumnIndex("imei"));
                } while (cursor.moveToNext());
                preloadGuarantors();
            }
            cursor.close();
            ((BaseActivity) getActivity()).setupOverlay();
            ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }



    /*---------------
   * FUNCTIONS
   * --------------*/

    //pre load local data
    private void preloadGuarantors() {
        //preload local

        try {
            arrayList.clear();
            Cursor c = db.getGuarantors(db);
            if (c.getCount() > 0) {
                emptyvoucher.setVisibility(View.GONE);
                updateDataList();

            } else {
                if (CommonVariables.GUARANTORSISFIRSTLOAD.equals("false")) {
                    emptyvoucher.setVisibility(View.VISIBLE);
                }
            }
            c.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }


    }

//    //2.create session
//    private void verifySession() {
//
//        try {
//
//            FULLYLOADED = "true";
//            int status = CommonFunctions.getConnectivityStatus(getActivity());
//            if (status == 0) { //no connection
//
//                //show the no connection indicator
//                showNoInternetConnection();
//                inableRefresh();
//                swipeRefreshLayout.setRefreshing(false);
//                showToast("No internet connection.", GlobalToastEnum.NOTICE);
//
//            } else {
//
//                mLoaderTimer.cancel();
//                mLoaderTimer.start();
//
//                mTvFetching.setText("Fetching public sponsors.");
//                mTvWait.setText(" Please wait...");
//                mLlLoader.setVisibility(View.VISIBLE);
//                ((BaseActivity) getActivity()).setupOverlay();
//                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));
//                CreateSession newsession = new CreateSession(getActivity());
//                newsession.setQueryListener(new CreateSession.QueryListener() {
//                    @SuppressWarnings("unchecked")
//                    public void QuerySuccessFul(String data) {
//                        if (data == null) {
//                            mLlLoader.setVisibility(View.GONE);
//                            CommonFunctions.hideDialog();
//                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            //show the no connection indicator
//                            showNoInternetConnection();
//                            inableRefresh();
//                        } else if (data.equals("001")) {
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("Invalid Entry. Please check..", GlobalToastEnum.NOTICE);
//                            //show the no connection indicator
//                            showNoInternetConnection();
//                        } else if (data.equals("002")) {
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
//                            //show the no connection indicator
//                            showNoInternetConnection();
//                        } else if (data.equals("error")) {
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("Something went wrong with your internet connection. Please check.", GlobalToastEnum.NOTICE);
//                            //show the no connection indicator
//                            showNoInternetConnection();
//                            inableRefresh();
//                        } else if (data.contains("<!DOCTYPE html>")) {
//                            CommonFunctions.hideDialog();
//                            mLlLoader.setVisibility(View.GONE);
//                            swipeRefreshLayout.setRefreshing(false);
//                            showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
//                            showNoInternetConnection();
//                            inableRefresh();
//                        } else {
//                            sessionidval = data;
//                            //call AsynTask to perform network operation on separate thread
//                            new HttpAsyncTask().execute(CommonVariables.GETGUARANTORURL);
//                        }
//                    }
//
//                });
//                newsession.execute(CommonVariables.CREATESESSION, imei, userid);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //2.create session
    private void verifySession() {

        try {

            FULLYLOADED = "true";
            int status = CommonFunctions.getConnectivityStatus(getActivity());
            if (status == 0) { //no connection

                //show the no connection indicator
                showNoInternetConnection();
                inableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                showToast("No internet connection.", GlobalToastEnum.NOTICE);

            } else {

                mLoaderTimer.cancel();
                mLoaderTimer.start();

                mTvFetching.setText("Fetching public sponsors.");
                mTvWait.setText(" Please wait...");
                mLlLoader.setVisibility(View.VISIBLE);
                ((BaseActivity) getActivity()).setupOverlay();
                ((BaseActivity) getActivity()).setOverlayGUI(PreferenceUtils.getIsFreeMode(getContext()));

                //call AsynTask to perform network operation on separate thread
                new HttpAsyncTask().execute(CommonVariables.GETGUARANTORURL);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.send request to server
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            String json = "";
            String authcode = CommonFunctions.getSha1Hex(imei + userid + sessionidval);
            try {
                // build jsonObject
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("result", currentlimit);
                jsonObject.accumulate("sessionid", sessionidval);
                jsonObject.accumulate("imei", imei);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("authcode", authcode);

                //convert JSONObject to JSON to String
                json = jsonObject.toString();

            } catch (Exception e) {
                json = null;
                CommonFunctions.hideDialog();
                e.printStackTrace();
            }

            return CommonFunctions.POST(urls[0], json);

        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            CommonFunctions.hideDialog();
            mLlLoader.setVisibility(View.GONE);
            try {

                inableRefresh();
                swipeRefreshLayout.setRefreshing(false);
                if (result == null) {
                    showNoInternetConnection();
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.equals("001")) {
                    //show the no connection indicator
                    showNoInternetConnection();
                    showToast("Invalid Entry.", GlobalToastEnum.NOTICE);
                } else if (result.equals("002")) {
                    //show the no connection indicator
                    showNoInternetConnection();
                    showToast("Invalid Session.", GlobalToastEnum.NOTICE);
                } else if (result.equals("error")) {
                    //show the no connection indicator
                    showNoInternetConnection();
                    showToast("No internet connection. Please try again.", GlobalToastEnum.NOTICE);
                } else if (result.contains("<!DOCTYPE html>")) {
                    showNoInternetConnection();
                    showToast("Connection is slow. Please try again.", GlobalToastEnum.NOTICE);
                } else {
                    //4.
                    processList(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //process request result
    private void processList(String result) {

        try {

            hideNoInternetConnection();
            JSONArray jsonArr = new JSONArray(result);

            //clear  list first
            if (isloadmore.equals("false")) {
                arrayList.clear();
                if (jsonArr.length() > 0) {
                    emptyvoucher.setVisibility(View.GONE);
                } else {
                    emptyvoucher.setVisibility(View.VISIBLE);
                }
                db.deleteGuarantors(db); //delete local before inserting

            } else {
                if (jsonArr.length() <= 0) {
                    btnAddMore.setVisibility(View.GONE);
                    isloadmore = "false";
                }
            }


            if (jsonArr.length() > 0) {

                for (int i = 0; i < jsonArr.length(); i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    String guarantorid = jsonObj.getString("GuarantorID");
                    String guarantorname = jsonObj.getString("GuarantorName");
                    String guarantortype = jsonObj.getString("GuarantorType");
                    String guarantorgroup = jsonObj.getString("GuarantorGroup");
                    String guarantorstatus = jsonObj.getString("GuarantorStatus");
                    String billingaddress = jsonObj.getString("BillingAddress");
                    String streetaddress = jsonObj.getString("StreetAddress");
                    String city = jsonObj.getString("City");
                    String province = jsonObj.getString("Province");
                    String country = jsonObj.getString("Country");
                    String representative = jsonObj.getString("Representative");
                    String email = jsonObj.getString("AuthorisedEmailAddress");
                    String telno = jsonObj.getString("AuthorisedTelNo");
                    String mobileno = jsonObj.getString("AuthorisedMobileNo");
                    String fax = jsonObj.getString("Fax");
                    String natureofbusiness = jsonObj.getString("NatureOfBusiness");
                    String tin = jsonObj.getString("TIN");
                    String organizationtype = jsonObj.getString("OrganizationType");
                    String registrationdate = jsonObj.getString("DateAdded");
                    String longitude =  jsonObj.getString("Longitude");
                    String latitude = jsonObj.getString("Latitude");
                    String notes1 = jsonObj.getString("Notes1");

                    Logger.debug("ANNCCC"," NOTES1"+ notes1);


                    db.insertGuarantors(db, guarantorid, guarantorname, guarantortype, guarantorgroup, guarantorstatus, billingaddress, streetaddress, city, province, country, representative, email, telno, mobileno, fax, natureofbusiness, tin, organizationtype, registrationdate,notes1,longitude,latitude);

                }

                CommonVariables.GUARANTORSISFIRSTLOAD = "false"; //set to false so that it wont load again
                updateDataList();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDataList() {

        adapter.update(getAllPublicGuarantors());

        int lastViewedPosition = listView.getFirstVisiblePosition(); //get the last position of list

        View v = listView.getChildAt(0); //get offset of first visible view
        int topOffset = (v == null) ? 0 : v.getTop();
        listView.setSelectionFromTop(lastViewedPosition, topOffset);//set the data with changing the current scroll position


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), SponsorDetails.class);
                intent.putExtra("SPONSOR_OBJECT", arrayList.get(position));
                startActivity(intent);
            }
        });


    }

    //disable refresh button in empty screen
    private void disableRefresh() {
        try {
            refreshdisabled.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            refreshdisabled1.setVisibility(View.VISIBLE);
            refreshnointernet.setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }

    //enable refresh button in empty screen
    private void inableRefresh() {
        try {
            refreshdisabled.setVisibility(View.GONE);
            refresh.setVisibility(View.VISIBLE);
            refreshdisabled1.setVisibility(View.GONE);
            refreshnointernet.setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }

    }

    //show no internet connection page
    private void showNoInternetConnection() {
        try {
            if (CommonVariables.GUARANTORSISFIRSTLOAD.equals("true")) {
                nointernetconnection.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }

    }

    //hide no internet connection page
    private void hideNoInternetConnection() {
        try {
            nointernetconnection.setVisibility(View.GONE);
            inableRefresh();
        } catch (Exception e) {
        }

    }

    private ArrayList<Sponsor> getAllPublicGuarantors() {
        ArrayList<Sponsor> arrayList = new ArrayList<>();
        try {
            Cursor mCur = db.getGuarantors(db);
            if (mCur != null && mCur.getCount() > 0)
                while (mCur.moveToNext()) {
                    String sponsorID = mCur.getString(mCur.getColumnIndex("guarantorid"));
                    String sponsorName = mCur.getString(mCur.getColumnIndex("guarantorname"));
                    String street = mCur.getString(mCur.getColumnIndex("streetaddress"));
                    String city = mCur.getString(mCur.getColumnIndex("city"));
                    String province = mCur.getString(mCur.getColumnIndex("province"));
                    String country = mCur.getString(mCur.getColumnIndex("country"));
                    String email = mCur.getString(mCur.getColumnIndex("email"));
                    String telephone = mCur.getString(mCur.getColumnIndex("telno"));
                    String mobile = mCur.getString(mCur.getColumnIndex("mobileno"));
                    String zip = "";
                    String longitude = mCur.getString(mCur.getColumnIndex("longitude"));
                    String latitude = mCur.getString(mCur.getColumnIndex("latitude"));
                    String notes1 =  mCur.getString(mCur.getColumnIndex("notes1"));
                    String sponsorlogo = CommonFunctions.parseXML(notes1,"logo");
                    String websitelink =  CommonFunctions.parseXML(notes1,"website");
                    String description =  CommonFunctions.parseXML(notes1,"about");

                    arrayList.add(new Sponsor(sponsorID, sponsorName, street, city, province, country, zip, email, telephone, mobile,sponsorlogo,websitelink,description,longitude,latitude));
                }

            this.arrayList = arrayList;
            mCur.close();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return arrayList;
    }
}
