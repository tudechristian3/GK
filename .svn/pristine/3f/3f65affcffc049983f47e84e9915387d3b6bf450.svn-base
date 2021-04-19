package com.goodkredit.myapplication.adapter.projectcoop;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.bean.projectcoop.GameResult;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.fragments.projectcoop.ProjectCoopPlayToSaveFragment;
import com.goodkredit.myapplication.responses.projectcoop.GetGameP2SResultsResponse;
import com.goodkredit.myapplication.utilities.InputFilterMinMax;
import com.goodkredit.myapplication.utilities.RetrofitBuild;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectCoopP2SResultsRecyclerAdapter extends RecyclerView.Adapter<ProjectCoopP2SResultsRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<GameResult> mGridData;
    private String selectedDay = "";

    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;

    private ProjectCoopPlayToSaveFragment mFragment;

    public ProjectCoopP2SResultsRecyclerAdapter(Context context, ProjectCoopPlayToSaveFragment fragment) {
        mContext = context;
        mGridData = new ArrayList<>();
        imei = PreferenceUtils.getImeiId(mContext);
        userid = PreferenceUtils.getUserId(mContext);
        borrowerid = PreferenceUtils.getBorrowerId(mContext);
        sessionid = PreferenceUtils.getSessionID(mContext);
        mFragment = fragment;
    }

    public void update(List<GameResult> data) {
        int startPos = mGridData.size() + 1;
        mGridData.clear();
        mGridData.addAll(data);
        notifyItemRangeInserted(startPos, data.size());
    }

    public void clear() {
        int startPos = mGridData.size();
        mGridData.clear();
        notifyItemRangeRemoved(0, startPos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_project_coop_play_to_save_result_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GameResult gameResult = mGridData.get(position);
        String[] result = gameResult.getWinningNumber().split("-");

        holder.linearDynamicDigits.removeAllViews();
        for (int i = 0; i < gameResult.getWinningCombination(); i++) {
            EditText editText = new EditText(mContext);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                editText.setId(editText.generateViewId());
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            if (i > 0) {
                lp.setMargins(10, 0, 0, 0);
            }

            editText.setBackgroundResource(R.drawable.cpmpc_p2s_circle);
            editText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
            editText.setGravity(Gravity.CENTER);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setEnabled(false);
            editText.setText(result[i]);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
            editText.setPadding(10, 10, 10, 10);
            editText.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack));
            editText.setTextSize(16);
            editText.setFocusable(false);
            editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "44")});
            editText.setLayoutParams(lp);
            holder.linearDynamicDigits.addView(editText);
        }

        holder.txvResultLabel.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Result"));
        holder.txvResultValue.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.getDateFromDateTime(CommonFunctions.convertDate(gameResult.getDrawDateTime()))));

        holder.txvJackpotLabel.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Jackpot Prize"));
        holder.txvJackpotValue.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.currencyFormatter(String.valueOf(gameResult.getJackpotPrice()))));
        holder.txvNextJackpotLabel.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Next Jackpot Prize"));
        holder.txvNextJackpotValue.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.currencyFormatter(String.valueOf(gameResult.getNextJackpotPrice()))));

        int winningcombi = gameResult.getWinningCombination();
        try{
            if(winningcombi == 3){
                holder.layout5hits.setVisibility(View.GONE);
                holder.layout3hits.setVisibility(View.GONE);

                if(gameResult.getThreeHitsPrize() != null){
                    if(!gameResult.getThreeHitsPrize().equals(".")){
                        holder.layoutrambolito.setVisibility(View.VISIBLE);
                        holder.txvRambolitoLabel.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Rambolito Prize"));
                        holder.txvRambolitoValue.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.currencyFormatter(String.valueOf(gameResult.getThreeHitsPrize()))));
                    } else{
                        holder.layoutrambolito.setVisibility(View.GONE);
                    }
                } else{
                    holder.layoutrambolito.setVisibility(View.GONE);
                }

            } else if(winningcombi == 6){
                holder.layoutrambolito.setVisibility(View.GONE);

                if(gameResult.getFiveHitsPrize() != null && gameResult.getThreeHitsPrize() != null){
                    if(!gameResult.getFiveHitsPrize().equals(".") && !gameResult.getThreeHitsPrize().equals(".")){
                        holder.layout5hits.setVisibility(View.VISIBLE);
                        holder.layout3hits.setVisibility(View.VISIBLE);
                        holder.txv5hitsLabel.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Consolation Prize (5 hits)"));
                        holder.txv5hitsValue.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.currencyFormatter(String.valueOf(gameResult.getFiveHitsPrize()))));
                        holder.txv3hitsLabel.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Consolation Prize (4 hits)"));
                        holder.txv3hitsValue.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", CommonFunctions.currencyFormatter(String.valueOf(gameResult.getThreeHitsPrize()))));
                    } else{
                        holder.layout5hits.setVisibility(View.GONE);
                        holder.layout3hits.setVisibility(View.GONE);
                    }
                } else{
                    holder.layout5hits.setVisibility(View.GONE);
                    holder.layout3hits.setVisibility(View.GONE);
                }

            } else{
                holder.layoutrambolito.setVisibility(View.GONE);
                holder.layout5hits.setVisibility(View.GONE);
                holder.layout3hits.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txvResultLabel;
        private TextView txvResultValue;
        private TextView txvJackpotLabel;
        private TextView txvJackpotValue;
        private TextView txvNextJackpotLabel;
        private TextView txvNextJackpotValue;

        private TextView txv5hitsLabel;
        private TextView txv5hitsValue;
        private TextView txv3hitsLabel;
        private TextView txv3hitsValue;
        private TextView txvRambolitoLabel;
        private TextView txvRambolitoValue;
        private LinearLayout layout5hits;
        private LinearLayout layout3hits;
        private LinearLayout layoutrambolito;

        private LinearLayout linearDynamicDigits;
        private LinearLayout linearResultsDate;

        public MyViewHolder(View itemView) {
            super(itemView);
            txvResultLabel = (TextView) itemView.findViewById(R.id.txvResultLabel);
            txvResultValue = (TextView) itemView.findViewById(R.id.txvResultValue);
            txvJackpotLabel = (TextView) itemView.findViewById(R.id.txvJackpotLabel);
            txvJackpotValue = (TextView) itemView.findViewById(R.id.txvJackpotValue);
            txvNextJackpotLabel = (TextView) itemView.findViewById(R.id.txvNextJackpotLabel);
            txvNextJackpotValue = (TextView) itemView.findViewById(R.id.txvNextJackpotValue);

            txv5hitsLabel = (TextView) itemView.findViewById(R.id.txv5hitsLabel);
            txv5hitsValue = (TextView) itemView.findViewById(R.id.txv5hitsValue);
            txv3hitsLabel = (TextView) itemView.findViewById(R.id.txv3hitsLabel);
            txv3hitsValue = (TextView) itemView.findViewById(R.id.txv3hitsValue);
            txvRambolitoLabel = (TextView) itemView.findViewById(R.id.txvRambolitoLabel);
            txvRambolitoValue = (TextView) itemView.findViewById(R.id.txvRambolitoValue);
            layout5hits = (LinearLayout) itemView.findViewById(R.id.layout5hits);
            layout3hits = (LinearLayout) itemView.findViewById(R.id.layout3hits);
            layoutrambolito = (LinearLayout) itemView.findViewById(R.id.layoutrambolito);

            linearDynamicDigits = (LinearLayout) itemView.findViewById(R.id.linearDynamicDigits);
            linearResultsDate = (LinearLayout) itemView.findViewById(R.id.linearResultsDate);
            linearResultsDate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linearResultsDate: {
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

                                    getSession();

                                    //edtResultDate.setText(CommonFunctions.setTypeFace(mContext, "fonts/robotobold.ttf", CommonFunctions.getCPMPCDate(date)));
                                }
                            },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.setMaxDate(now);
                    dpd.show(((ProjectCoopActivity) mContext).getFragmentManager(), "dialog");
                    break;
                }
            }
        }
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(mContext) > 0) {
//
//            Call<String> call = RetrofitBuild.getCommonApiService(mContext)
//                    .getRegisteredSession(imei, userid);
//
//            call.enqueue(callsession);
//
//        } else {
//            ((ProjectCoopActivity) mContext).showError(((ProjectCoopActivity) mContext).getString(R.string.generic_internet_error_message));
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
//                    ((ProjectCoopActivity) mContext).showToast("Session: Invalid session.", GlobalToastEnum.ERROR);
//                } else if (responseData.equals("error")) {
//                    ((ProjectCoopActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    ((ProjectCoopActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    getGameP2SResults(getGameP2SResultsSession);
//                }
//            } else {
//                ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.ERROR);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(mContext) > 0) {

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            getGameP2SResults(getGameP2SResultsSession);
        } else {
            ((ProjectCoopActivity) mContext).showNoInternetToast();
        }
    }

    private void getGameP2SResults(Callback<GetGameP2SResultsResponse> GetGameP2SResultsCallback) {
        Call<GetGameP2SResultsResponse> getgamep2sresults = RetrofitBuild.getGameP2SResultsService(mContext)
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
                        clear();
                        mFragment.isResultsEmpty(false, selectedDay);
                        update(response.body().getGameResultList());
                    } else {
                        mFragment.isResultsEmpty(true, selectedDay);
                        clear();
                    }

                } else {
                    ((ProjectCoopActivity) mContext).showError(response.body().getMessage());
                }
            } else {
                ((ProjectCoopActivity) mContext).showError();
            }

        }

        @Override
        public void onFailure(Call<GetGameP2SResultsResponse> call, Throwable t) {
            ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };
}
