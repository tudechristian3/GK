package com.goodkredit.myapplication.fragments;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.settings.SupportSendMessageActivity;
import com.goodkredit.myapplication.adapter.settings.SupportHelpTopicAdapter;
import com.goodkredit.myapplication.adapter.settings.SupportHistoryAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.support.GetHelpTopicsResponse;
import com.goodkredit.myapplication.responses.support.GetHelpTopicsResponseData;
import com.goodkredit.myapplication.responses.support.GetSupportConversationResponse;
import com.goodkredit.myapplication.responses.support.GetSupportConversationResponseData;
import com.goodkredit.myapplication.responses.support.GetSupportMessagesResponse;
import com.goodkredit.myapplication.responses.support.GetSupportMessagesResponseData;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ban_Lenovo on 8/3/2017.
 */

public class V2SupportFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ExpandableListView.OnChildClickListener {

    private AQuery aq;
    private DatabaseHandler db;
    private ProgressDialog mProgressDialog;

    private Gson gson;
    private ExpandableListView listView;
    private SupportHistoryAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SupportHelpTopicAdapter mAdapterHelpTopic;

    private RelativeLayout emptyLayout;

    private Button fab;

    private ImageView mXBtn;

    private LinearLayout slideView;

    public static boolean isActive = false;
    public static SupportHistoryAdapter staticAdapter;
    public static SwipeRefreshLayout staticRefreshLayout;

    private ArrayList<String> title;
    private HashMap<String, ArrayList<GetSupportMessagesResponseData>> details;

    private boolean isTopicOpen = false;

    private RelativeLayout rootV;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_support_history, container, false);
        try {
            aq = new AQuery(getViewContext());
            db = new DatabaseHandler(getViewContext());
            gson = new Gson();
            details = new HashMap<>();
            title = new ArrayList<>();
            title.clear();
            details.clear();

            mProgressDialog = new ProgressDialog(getViewContext());
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setInverseBackgroundForced(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("Fetching messages\nPlease wait...");

            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.supportRefresh);
            refreshLayout.setOnRefreshListener(this);

            listView = (ExpandableListView) view.findViewById(R.id.supportMessagesList);
            listView.setOnGroupClickListener(null);
            listView.setOnChildClickListener(this);

            recyclerView = (RecyclerView) view.findViewById(R.id.helpTopicList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
            recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getViewContext(), null, false, true));
            mAdapterHelpTopic = new SupportHelpTopicAdapter(getViewContext(), db.getHelpTopicsResponseData(db));
            recyclerView.setAdapter(mAdapterHelpTopic);

            if (!db.getAllMessagesThreads(db, "OPEN").isEmpty()) {
                title.add("OPEN");
                details.put("OPEN", db.getAllMessagesThreads(db, "OPEN"));
            }
            if (!db.getAllMessagesThreads(db, "CLOSED").isEmpty()) {
                title.add("CLOSED");
                details.put("CLOSED", db.getAllMessagesThreads(db, "CLOSED"));
            }
            adapter = new SupportHistoryAdapter(getViewContext(), title, details);
            listView.setAdapter(adapter);

            staticAdapter = adapter;
            staticRefreshLayout = refreshLayout;

            emptyLayout = (RelativeLayout) view.findViewById(R.id.emptyList);

            final Animation slide_down = AnimationUtils.loadAnimation(getViewContext(),
                    R.anim.slide_down);

            final Animation slide_up = AnimationUtils.loadAnimation(getViewContext(),
                    R.anim.slide_up);

            slideView = (LinearLayout) view.findViewById(R.id.slideView);

            rootV = (RelativeLayout) view.findViewById(R.id.rootView);

            mLlLoader = (RelativeLayout) view.findViewById(R.id.loaderLayout);
            mTvFetching = (TextView) view.findViewById(R.id.fetching);
            mTvWait = (TextView) view.findViewById(R.id.wait);

            fab = (Button) view.findViewById(R.id.supportFab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slideView.bringToFront();
                    slideView.setVisibility(View.VISIBLE);
                    isTopicOpen = true;
                    slideView.startAnimation(slide_up);
                }
            });

            mXBtn = (ImageView) view.findViewById(R.id.xbutton);

            mXBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isTopicOpen = false;
                    slideView.startAnimation(slide_down);
                    slideView.setVisibility(View.GONE);
                }
            });
            rootV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isTopicOpen)
                        mXBtn.performClick();
                }
            });

            final ImageView backgroundOne = (ImageView) view.findViewById(R.id.background_one);
            final ImageView backgroundTwo = (ImageView) view.findViewById(R.id.background_two);

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onRefresh() {
        fetchSupportMessageThreads();
    }

    @Override
    public void onPause() {
        isActive = false;
        super.onPause();
    }

    @Override
    public void onStop() {
        mLoaderTimer.cancel();
        super.onStop();
    }


    @Override
    public void onResume() {
        isActive = true;
        mXBtn.performClick();
        fetchSupportMessageThreads();
        super.onResume();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        if (title.size() == 1) {
            if (title.get(0).equals("OPEN")) {
                GetSupportMessagesResponseData data = db.getAllMessagesThreads(db, "OPEN").get(childPosition);
                db.updateSupportMessagesThreadsReadStatus(db, "1", data.getThreadID());
                title.clear();
                details.clear();

                if (!db.getAllMessagesThreads(db, "OPEN").isEmpty()) {
                    title.add("OPEN");
                    details.put("OPEN", db.getAllMessagesThreads(db, "OPEN"));
                }
                if (!db.getAllMessagesThreads(db, "CLOSED").isEmpty()) {
                    title.add("CLOSED");
                    details.put("CLOSED", db.getAllMessagesThreads(db, "CLOSED"));
                }

                adapter.update(title, details);

                fetchFullThread(data);
            } else {
                GetSupportMessagesResponseData data = db.getAllMessagesThreads(db, "CLOSED").get(childPosition);
                fetchFullThread(data);
            }
        } else if (title.size() == 2) {
            if (groupPosition == 0) {
                GetSupportMessagesResponseData data = db.getAllMessagesThreads(db, "OPEN").get(childPosition);
                db.updateSupportMessagesThreadsReadStatus(db, "1", data.getThreadID());
                title.clear();
                details.clear();

                if (!db.getAllMessagesThreads(db, "OPEN").isEmpty()) {
                    title.add("OPEN");
                    details.put("OPEN", db.getAllMessagesThreads(db, "OPEN"));
                }
                if (!db.getAllMessagesThreads(db, "CLOSED").isEmpty()) {
                    title.add("CLOSED");
                    details.put("CLOSED", db.getAllMessagesThreads(db, "CLOSED"));
                }

                adapter.update(title, details);
                fetchFullThread(data);
            } else {
                GetSupportMessagesResponseData data = db.getAllMessagesThreads(db, "CLOSED").get(childPosition);
                fetchFullThread(data);
            }
        }
        return false;
    }

    private void fetchSupportMessageThreads() {

        mTvFetching.setText("Fetching messages.");
        mTvWait.setText(" Please wait...");
        mLlLoader.setVisibility(View.VISIBLE);
        mLoaderTimer.cancel();
        mLoaderTimer.start();

        Map<String, Object> params = new HashMap<>();
        params.put("userid", PreferenceUtils.getUserId(getViewContext()));
        params.put("usermobile", PreferenceUtils.getUserId(getViewContext()));
        params.put("imei", PreferenceUtils.getImeiId(getViewContext()));
        params.put("borrowerid", PreferenceUtils.getBorrowerId(getViewContext()));


        aq.ajax(CommonVariables.GET_SUPPORT_MESSAGES_THREADS, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {

                String json = String.valueOf(object);

                if (object != null) {
                    GetSupportMessagesResponse response = gson.fromJson(json, GetSupportMessagesResponse.class);
                    if (response.getStatus().equals("000")) {
                        //insert all data to db

                        if (!response.getData().isEmpty()) {
                            adapter.clearData();
                            db.deleteSupportMessagesThreads(db);
                            for (GetSupportMessagesResponseData data : response.getData()) {
                                db.insertSupportMessagesThreads(db, data);
                            }
                            CommonVariables.GROUP_SUPPORT_MESSAGES_ISFIRSTLOAD = false;
                            //implement showing has data, hide no data
                            emptyLayout.setVisibility(View.GONE);
                        } else {
                            emptyLayout.setVisibility(View.VISIBLE);
                        }

                    } else {
                        showToast(response.getMesssage(), GlobalToastEnum.NOTICE);
                    }
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) != 0)
                        showToast("Connection maybe slow. Please try again.", GlobalToastEnum.NOTICE);
                    else
                        showToast("No internet connection. Please check.", GlobalToastEnum.NOTICE);
                    if (CommonVariables.GROUP_SUPPORT_MESSAGES_ISFIRSTLOAD = true)
                        emptyLayout.setVisibility(View.VISIBLE);
                    else
                        emptyLayout.setVisibility(View.GONE);
                }

                title.clear();
                details.clear();

                if (!db.getAllMessagesThreads(db, "OPEN").isEmpty()) {
                    title.add("OPEN");
                    details.put("OPEN", db.getAllMessagesThreads(db, "OPEN"));
                }
                if (!db.getAllMessagesThreads(db, "CLOSED").isEmpty()) {
                    title.add("CLOSED");
                    details.put("CLOSED", db.getAllMessagesThreads(db, "CLOSED"));
                }
                adapter.update(title, details);
                fetchHelpTopics();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void fetchHelpTopics() {

        Map<String, Object> params = new HashMap<>();
        params.put("imei", PreferenceUtils.getImeiId(getViewContext()));
        params.put("borrowerid", PreferenceUtils.getBorrowerId(getViewContext()));
        params.put("userid", PreferenceUtils.getUserId(getViewContext()));

        ProgressDialog mProgressDialog = new ProgressDialog(getViewContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setInverseBackgroundForced(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("Fetching topics\nPlease wait...");

        aq.ajax(CommonVariables.GET_HELP_TOPIC, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                if (object != null) {
                    String json = String.valueOf(object);
                    GetHelpTopicsResponse response = gson.fromJson(json, GetHelpTopicsResponse.class);
                    if (response.getStatus().equals("000")) {
                        //insert to db
                        if (!response.getData().isEmpty()) {
                            db.deleteHelpTopic(db);
                            for (GetHelpTopicsResponseData data : response.getData()) {
                                db.insertHelpTopics(db, data);
                            }
                            CommonVariables.HELP_TOPIC_ISFIRSTLOAD = false;
                        } else {
                            showToast(response.getMesssage(), GlobalToastEnum.NOTICE);
                        }
                    } else {
                        if (CommonFunctions.getConnectivityStatus(getViewContext()) != 0) {
                            showToast("Connection maybe slow. Please try again", GlobalToastEnum.NOTICE);
                        } else {
                            showToast("No internet connection. Please check", GlobalToastEnum.NOTICE);
                        }
                    }
                    mAdapterHelpTopic.clearData();
                    mAdapterHelpTopic.update(db.getHelpTopicsResponseData(db));
                    refreshLayout.setRefreshing(false);
                    mLlLoader.setVisibility(View.GONE);
                }
            }
        });
    }

    private void fetchFullThread(final GetSupportMessagesResponseData obj) {

        final String thread = obj.getThreadID();
        final String topic = obj.getHelpTopic();

        Map<String, Object> params = new HashMap<>();
        params.put("userid", PreferenceUtils.getUserId(getViewContext()));
        params.put("usermobile", PreferenceUtils.getUserId(getViewContext()));
        params.put("imei", PreferenceUtils.getImeiId(getViewContext()));
        params.put("borrowerid", PreferenceUtils.getBorrowerId(getViewContext()));
        params.put("threadid", thread);
        params.put("year", Calendar.getInstance().get(Calendar.YEAR));

        aq.progress(mProgressDialog).ajax(CommonVariables.GET_SUPPORT_MESSAGES_CONVERSATION, params, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                String json = String.valueOf(object);
                if (object != null) {
                    Logger.debug("HISTORY", json);
                    GetSupportConversationResponse response = gson.fromJson(json, GetSupportConversationResponse.class);
                    if (response.getStatus().equals("000")) {
                        //insert all data to db
                        if (!response.getData().isEmpty()) {
                            db.deleteMessagesConversationsInThread(db, thread);
                            for (GetSupportConversationResponseData data : response.getData()) {
                                db.insertMessagesConversation(db, data);
                            }
                            SupportSendMessageActivity.start(getViewContext(), ".", obj.getHelpTopic(), obj.getThreadID(), obj);
                        }
                    } else {
                        showToast("" + response.getMessage(), GlobalToastEnum.NOTICE);
                    }
                } else {
                    if (CommonFunctions.getConnectivityStatus(getViewContext()) != 0) {
                        showToast("Connection maybe slow. Please try again.", GlobalToastEnum.NOTICE);
                    }
                    else {
                        showToast("No internet connection. Please check.", GlobalToastEnum.NOTICE);
                    }

                }
            }
        });

    }
}
