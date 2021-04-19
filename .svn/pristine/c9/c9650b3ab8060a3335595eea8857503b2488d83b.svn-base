package com.goodkredit.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.activities.SplashScreenActivity;
import com.goodkredit.myapplication.activities.account.GKAppIntroActivity;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.account.GuarantorVerificationActivity;
import com.goodkredit.myapplication.activities.settings.AboutActivity;
import com.goodkredit.myapplication.activities.settings.ChangePasswordActivity;
import com.goodkredit.myapplication.activities.settings.TermsAndConditions;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.V2SubscriberDetails;
import com.goodkredit.myapplication.responses.profile.GetSubscriberDetailsResponse;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ban_Lenovo on 8/3/2017.
 */

public class V2SettingsFragment extends BaseFragment implements View.OnClickListener {


    private DatabaseHandler db;
    private String guarantoridstatus = "";

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
        View view = inflater.inflate(R.layout.activity_settings, container, false);

        db = new DatabaseHandler(getViewContext());
        //get account information
        Cursor cursor = db.getAccountInformation(db);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                borrowerid = cursor.getString(cursor.getColumnIndex("borrowerid"));
                imei = cursor.getString(cursor.getColumnIndex("imei"));
                userid = cursor.getString(cursor.getColumnIndex("mobile"));
                guarantoridstatus = cursor.getString(cursor.getColumnIndex("guarantorregistrationstatus"));

            } while (cursor.moveToNext());
        }
        cursor.close();

        if(guarantoridstatus == null || guarantoridstatus.equals("")){
            getSubscribersProfile();
            view.findViewById(R.id.registerguarantor).setVisibility(View.VISIBLE);
        }else{
            if (guarantoridstatus.equals("APPROVED")) {
                view.findViewById(R.id.registerguarantor).setVisibility(View.GONE);
            } else {
                getSubscribersProfile();
                view.findViewById(R.id.registerguarantor).setVisibility(View.VISIBLE);
            }
        }


        view.findViewById(R.id.changepassbtn).setOnClickListener(this);
        view.findViewById(R.id.registerguarantorbtn).setOnClickListener(this);
        view.findViewById(R.id.termsconditionbtn).setOnClickListener(this);
        view.findViewById(R.id.aboutbtn).setOnClickListener(this);
        view.findViewById(R.id.quicktourbtn).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changepassbtn: {
                ChangePasswordActivity.start(getViewContext());
                break;
            }
            case R.id.registerguarantorbtn: {
                GuarantorVerificationActivity.start(getViewContext(), "SETTINGS");
                break;
            }
            case R.id.termsconditionbtn: {
                TermsAndConditions.start(getViewContext(), "1");
                break;
            }
            case R.id.aboutbtn: {
                AboutActivity.start(getViewContext());
                break;
            }
            case R.id.quicktourbtn: {
                Intent intent = new Intent(getViewContext(), GKAppIntroActivity.class);
                intent.putExtra("SOURCE", "MAIN");
                startActivity(intent);
                break;

            }
        }
    }

    public static void triggerRebirth(Context context, Class myClass) {
        Intent intent = new Intent(context, myClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        Runtime.getRuntime().exit(0);
    }

    private void getSubscribersProfile() {
        Call<GetSubscriberDetailsResponse> call = RetrofitBuild.getSubscriberAPIService(getViewContext())
                .getSubscribersProfile(
                        imei,
                        CommonFunctions.getSha1Hex(imei + userid),
                        userid,
                        borrowerid
                );

        call.enqueue(getSubscribersProfileCallback);
    }

    private Callback<GetSubscriberDetailsResponse> getSubscribersProfileCallback = new Callback<GetSubscriberDetailsResponse>() {
        @Override
        public void onResponse(Call<GetSubscriberDetailsResponse> call, Response<GetSubscriberDetailsResponse> response) {
            try {

                ResponseBody errBody = response.errorBody();

                if (errBody == null) {
                    if (response.body().getStatus().equals("000")) {
                        V2SubscriberDetails subDetails = response.body().getSubscriberDetails();
                        String guarantorApprovalStatus = subDetails.getGuarantorApprovalStatus();
                        db.updateGuarantorStatus(db, guarantorApprovalStatus, userid);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(Call<GetSubscriberDetailsResponse> call, Throwable throwable) {
        }
    };
}
