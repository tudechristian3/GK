package com.goodkredit.myapplication.adapter.projectcoop;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.projectcoop.ProjectCoopActivity;
import com.goodkredit.myapplication.bean.projectcoop.GameProduct;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.responses.projectcoop.GetGameServiceChargeResponse;
import com.goodkredit.myapplication.responses.projectcoop.ValidateEntryP2SCutoverResponse;
import com.goodkredit.myapplication.utilities.InputFilterMinMax;
import com.goodkredit.myapplication.utilities.RetrofitBuild;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectCoopP2SDigitsRecyclerAdapter extends RecyclerView.Adapter<ProjectCoopP2SDigitsRecyclerAdapter.MyViewHolder> {
    private Context mContext;
    private List<GameProduct> mGridData;
    private ArrayList<String> p2sArr;
    private String sessionid;
    private String authcode;
    private String userid;
    private String imei;
    private String borrowerid;
    private double amount = 0;
    private double serviceCharge = 0;
    private double totalAmount = 0;
    private String chargeclass;
    private GameProduct gameProduct;
    private String winningCombination = "";
    private String winningName = "";
    private String winningAccountNumber = "";
    private boolean isCutoverCall = false;
    private boolean isInsertCall = false;
    public boolean isClickable = false;
    private List<Integer> edtIds;
    private Set<Integer> digitHashSet;

    public ProjectCoopP2SDigitsRecyclerAdapter(Context context) {
        mContext = context;
        mGridData = new ArrayList<>();
        p2sArr = new ArrayList<>();
        imei = PreferenceUtils.getImeiId(mContext);
        userid = PreferenceUtils.getUserId(mContext);
        borrowerid = PreferenceUtils.getBorrowerId(mContext);
        sessionid = PreferenceUtils.getSessionID(mContext);
        edtIds = new ArrayList<>();
        digitHashSet = new HashSet<>();
    }

    public void update(List<GameProduct> data) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_project_coop_play_to_save_digits_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final GameProduct gameProduct = mGridData.get(position);

        holder.txvProductDescription.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", gameProduct.getProductDesc()));
        holder.p2sInstructions.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Regular.ttf", "Fill in the " + String.valueOf(gameProduct.getDigit()) + " circles with your " + (gameProduct.getIsUnique() == 1 ? "unique " : "") + "combination numbers."));
        holder.txvGoodLuck.setText(CommonFunctions.setTypeFace(mContext, "fonts/Roboto-Bold.ttf", "Good luck!"));

        for (int i = 0; i < gameProduct.getDigit(); i++) {
            final EditText editText = new EditText(mContext);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                int id = editText.generateViewId();
                editText.setId(id);
                edtIds.add(id);
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            if (i > 0) {
                lp.setMargins(10, 0, 0, 0);
            }

            editText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
            editText.setGravity(Gravity.CENTER);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2), new InputFilterMinMax(gameProduct.getMinNumber(), gameProduct.getMaxNumber())});
            editText.setPadding(10, 10, 10, 10);
            editText.setBackgroundResource(R.drawable.cpmpc_p2s_circle);
            editText.setTextColor(ContextCompat.getColor(mContext, R.color.colorblack));
            editText.setTextSize(16);
            editText.setLayoutParams(lp);
            editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    digitHashSet.clear();
                    for (int i = 0; i < holder.linearDynamicDigits.getChildCount(); i++) {
                        View tempView = holder.linearDynamicDigits.getChildAt(i);
                        if (tempView instanceof EditText) {
                            String assembledString = ((EditText) tempView).getText().toString();
                            if (assembledString.length() > 0) {

                                if (gameProduct.getIsUnique() == 1) {

                                    if (!digitHashSet.add(Integer.valueOf(assembledString))) {
                                        ((EditText) tempView).setBackgroundResource(R.drawable.cpmpc_p2s_circle_red);
                                        ((EditText) tempView).setTextColor(ContextCompat.getColor(mContext, R.color.colorwhite));
                                        ((ProjectCoopActivity) mContext).showToast("Invalid Combination. Please review your entered combination.", GlobalToastEnum.WARNING);
                                    } else {
                                        ((EditText) tempView).setBackgroundResource(R.drawable.cpmpc_p2s_circle);
                                        ((EditText) tempView).setTextColor(ContextCompat.getColor(mContext, R.color.colorblack));
                                    }

                                }

                            }
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (s.toString().length() > 0) {

                        if (gameProduct.getMaxNumber() > 9) {
                            if (s.toString().length() == 2) {
                                EditText edt = (EditText) editText.focusSearch(View.FOCUS_RIGHT);
                                if (edt != null) {
                                    edt.requestFocus();
                                } else {
                                    ((ProjectCoopActivity) mContext).hideSoftKeyboard();
                                }
                            }
                        } else {
                            EditText edt = (EditText) editText.focusSearch(View.FOCUS_RIGHT);
                            if (edt != null) {
                                edt.requestFocus();
                            } else {
                                ((ProjectCoopActivity) mContext).hideSoftKeyboard();
                            }
                        }

                    }

                }
            });

            holder.linearDynamicDigits.addView(editText);
        }
    }

    @Override
    public int getItemCount() {
        return mGridData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout linearProductDescription;
        private LinearLayout linearDigits;
        private TextView txvProductDescription;
        private ImageView imgAccordion;
        private TextView p2sInstructions;
        private TextView txvGoodLuck;
        private LinearLayout linearDynamicDigits;
        private EditText edtName;
        private EditText edtAccountNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            linearProductDescription = (LinearLayout) itemView.findViewById(R.id.linearProductDescription);
            linearProductDescription.setOnClickListener(this);
            linearDigits = (LinearLayout) itemView.findViewById(R.id.linearDigits);
            linearDigits.setOnClickListener(this);
            txvProductDescription = (TextView) itemView.findViewById(R.id.txvProductDescription);
            imgAccordion = (ImageView) itemView.findViewById(R.id.imgAccordion);
            p2sInstructions = (TextView) itemView.findViewById(R.id.p2sInstructions);
            txvGoodLuck = (TextView) itemView.findViewById(R.id.txvGoodLuck);
            txvGoodLuck.setOnClickListener(this);
            linearDynamicDigits = (LinearLayout) itemView.findViewById(R.id.linearDynamicDigits);
            edtName = (EditText) itemView.findViewById(R.id.edtName);
            edtAccountNumber = (EditText) itemView.findViewById(R.id.edtAccountNumber);
        }

        @Override
        public void onClick(View v) {

            gameProduct = mGridData.get(getAdapterPosition());

            switch (v.getId()) {
                case R.id.linearProductDescription: {
                    if (imgAccordion.getRotation() == 270) {
                        imgAccordion.animate().rotation(90).start();
                        fadeOutAnimation(linearDigits, 1000);
                    } else {
                        fadeInAnimation(linearDigits, 1000);
                        imgAccordion.animate().rotation(270).start();
                    }

                    break;
                }
                case R.id.txvGoodLuck: {
                    boolean isProceed = true;
                    String assembledString = "";
                    View tempView;
                    Set<Integer> appeared = new HashSet<>();
                    p2sArr.clear();
                    winningCombination = "";

                    for (int i = 0; i < linearDynamicDigits.getChildCount(); i++) {
                        tempView = linearDynamicDigits.getChildAt(i);
                        if (tempView instanceof EditText) {
                            assembledString = ((EditText) tempView).getText().toString();
                            if (assembledString.length() > 0) {

                                if (gameProduct.getIsUnique() == 1) {

                                    if (!appeared.add(Integer.valueOf(assembledString))) {
                                        isProceed = false;
                                        break;
                                    } else {
                                        p2sArr.add(assembledString);
                                    }

                                } else {

                                    p2sArr.add(assembledString);

                                }

                            } else {
                                isProceed = false;
                                break;
                            }
                        }
                    }

                    if (isProceed) {
                        if (isFieldsValid(edtName.getText().toString().trim(), edtAccountNumber.getText().toString().trim())) {
                            amount = gameProduct.getPrice();
                            chargeclass = gameProduct.getServiceChargeClass();
                            winningCombination = convertArrayListToString(p2sArr, "-", "", "");
                            winningName = edtName.getText().toString().trim();
                            winningAccountNumber = edtAccountNumber.getText().toString().trim();
                            isCutoverCall = true;
                            isInsertCall = false;
                            getSession();
                        } else {
                            ((ProjectCoopActivity) mContext).showError("Please complete the necessary fields required.");
                        }
                    } else {
                        ((ProjectCoopActivity) mContext).showError("Invalid Combination. Please review your entered combination.");
                    }
                    break;
                }
            }
        }
    }

    private String convertArrayListToString(ArrayList<String> stringArrayList, String delim, String prefix, String suffix) {
        if (stringArrayList.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = stringArrayList.iterator();
        while (it.hasNext()) {
            sb.append(prefix).append(it.next()).append(suffix);
            if (it.hasNext()) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

//    private void getSession() {
//        if (CommonFunctions.getConnectivityStatus(mContext) > 0) {
//
//            ((ProjectCoopActivity) mContext).showProgressDialog(mContext, "", "Validating Entry. Please wait...");
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
//                    isCutoverCall = false;
//                    isInsertCall = false;
//                    ((ProjectCoopActivity) mContext).showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
//                    ((ProjectCoopActivity) mContext).hideProgressDialog();
//                } else if (responseData.equals("error")) {
//                    isCutoverCall = false;
//                    isInsertCall = false;
//                    ((ProjectCoopActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    ((ProjectCoopActivity) mContext).hideProgressDialog();
//                } else if (responseData.contains("<!DOCTYPE html>")) {
//                    isCutoverCall = false;
//                    isInsertCall = false;
//                    ((ProjectCoopActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//                    ((ProjectCoopActivity) mContext).hideProgressDialog();
//                } else {
//                    sessionid = response.body().toString();
//                    authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);
//
//                    if (isCutoverCall) {
//                        validateEntryP2SCutover(validateEntryP2SCutoverSession);
//                    } else {
//                        getGameServiceCharge(getGameServiceChargeSession);
//                    }
//
//                }
//            } else {
//                isCutoverCall = false;
//                isInsertCall = false;
//                ((ProjectCoopActivity) mContext).hideProgressDialog();
//                ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//            }
//        }
//
//        @Override
//        public void onFailure(Call<String> call, Throwable t) {
//            isCutoverCall = false;
//            isInsertCall = false;
//            ((ProjectCoopActivity) mContext).hideProgressDialog();
//            ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
//        }
//    };

    private void getSession() {
        if (CommonFunctions.getConnectivityStatus(mContext) > 0) {

            ((ProjectCoopActivity) mContext).showProgressDialog(mContext, "", "Validating Entry. Please wait...");

            authcode = CommonFunctions.getSha1Hex(imei + userid + sessionid);

            if (isCutoverCall) {
                validateEntryP2SCutover(validateEntryP2SCutoverSession);
            } else {
                getGameServiceCharge(getGameServiceChargeSession);
            }
        } else {
            isCutoverCall = false;
            isInsertCall = false;
            ((ProjectCoopActivity) mContext).hideProgressDialog();
            ((ProjectCoopActivity) mContext).showNoInternetToast();
        }

    }

    private void getGameServiceCharge(Callback<GetGameServiceChargeResponse> getGameServiceChargeCallback) {
        Call<GetGameServiceChargeResponse> getgameservicecharge = RetrofitBuild.getGameServiceChargeService(mContext)
                .getGameServiceChargeCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode,
                        chargeclass,
                        String.valueOf(amount));
        getgameservicecharge.enqueue(getGameServiceChargeCallback);
    }

    private final Callback<GetGameServiceChargeResponse> getGameServiceChargeSession = new Callback<GetGameServiceChargeResponse>() {

        @Override
        public void onResponse(Call<GetGameServiceChargeResponse> call, Response<GetGameServiceChargeResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    serviceCharge = response.body().getServiceCharge();
                    totalAmount = amount + serviceCharge;
                    prePurchase(prePurchaseSession);

                } else {
                    ((ProjectCoopActivity) mContext).hideProgressDialog();
                    ((ProjectCoopActivity) mContext).showError(response.body().getMessage());
                }
            } else {
                ((ProjectCoopActivity) mContext).hideProgressDialog();
                ((ProjectCoopActivity) mContext).showError();
            }

        }

        @Override
        public void onFailure(Call<GetGameServiceChargeResponse> call, Throwable t) {
            ((ProjectCoopActivity) mContext).hideProgressDialog();
            ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
        }
    };

    private void validateEntryP2SCutover(Callback<ValidateEntryP2SCutoverResponse> validateEntryP2SCutoverCallback) {
        Call<ValidateEntryP2SCutoverResponse> validateentry = RetrofitBuild.validateEntryP2SCutoverService(mContext)
                .validateEntryP2SCutoverCall(sessionid,
                        imei,
                        userid,
                        borrowerid,
                        authcode);
        validateentry.enqueue(validateEntryP2SCutoverCallback);
    }

    private final Callback<ValidateEntryP2SCutoverResponse> validateEntryP2SCutoverSession = new Callback<ValidateEntryP2SCutoverResponse>() {

        @Override
        public void onResponse(Call<ValidateEntryP2SCutoverResponse> call, Response<ValidateEntryP2SCutoverResponse> response) {
            ResponseBody errorBody = response.errorBody();

            if (errorBody == null) {
                if (response.body().getStatus().equals("000")) {

                    isCutoverCall = false;
                    isInsertCall = true;
                    getSession();

                } else {
                    isCutoverCall = false;
                    isInsertCall = false;
                    ((ProjectCoopActivity) mContext).showError(response.body().getMessage());
                    ((ProjectCoopActivity) mContext).hideProgressDialog();
                }
            } else {
                isCutoverCall = false;
                isInsertCall = false;
                ((ProjectCoopActivity) mContext).hideProgressDialog();
                ((ProjectCoopActivity) mContext).showError();
            }

        }

        @Override
        public void onFailure(Call<ValidateEntryP2SCutoverResponse> call, Throwable t) {
            isCutoverCall = false;
            isInsertCall = false;
            ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            ((ProjectCoopActivity) mContext).hideProgressDialog();
        }
    };

    private void prePurchase(Callback<String> prePurchaseCallback) {
        Call<String> prepurchase = RetrofitBuild.prePurchaseService(mContext)
                .prePurchaseCall(borrowerid,
                        String.valueOf(totalAmount),
                        userid,
                        imei,
                        sessionid,
                        authcode);
        prepurchase.enqueue(prePurchaseCallback);
    }

    private final Callback<String> prePurchaseSession = new Callback<String>() {

        @Override
        public void onResponse(Call<String> call, Response<String> response) {
            ResponseBody errorBody = response.errorBody();
            if (errorBody == null) {

                String responseData = response.body().toString();

                if (!responseData.isEmpty()) {
                    if (responseData.equals("001")) {
                        CommonFunctions.hideDialog();
                        ((ProjectCoopActivity) mContext).showToast("Session: Invalid session.", GlobalToastEnum.NOTICE);
                        ((ProjectCoopActivity) mContext).hideProgressDialog();
                    } else if (responseData.equals("error")) {
                        CommonFunctions.hideDialog();
                        ((ProjectCoopActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                        ((ProjectCoopActivity) mContext).hideProgressDialog();
                    } else if (responseData.contains("<!DOCTYPE html>")) {
                        CommonFunctions.hideDialog();
                        ((ProjectCoopActivity) mContext).showToast("Session: Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                        ((ProjectCoopActivity) mContext).hideProgressDialog();
                    } else {
                        if (responseData.length() > 0) {

                            Intent intent = new Intent(mContext, Payment.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("AMOUNT", String.valueOf(amount));
                            intent.putExtra("SERVICECHARGE", String.valueOf(serviceCharge));
                            intent.putExtra("AMOUNTPAID", String.valueOf(totalAmount));
                            intent.putExtra("VOUCHERSESSION", responseData);
                            intent.putExtra("GAMEP2SOBJ", gameProduct);
                            intent.putExtra("PROJECTCOOPP2S", "true");
                            intent.putExtra("P2SCOMBINATION", winningCombination);
                            intent.putExtra("P2SNAME", winningName);
                            intent.putExtra("P2SACCOUNTNUMBER", winningAccountNumber);
                            ((ProjectCoopActivity) mContext).startActivity(intent);
                            ((ProjectCoopActivity) mContext).hideProgressDialog();

                        } else {
                            ((ProjectCoopActivity) mContext).showError("Invalid Voucher Session.");
                            ((ProjectCoopActivity) mContext).hideProgressDialog();
                        }
                    }
                } else {
                    CommonFunctions.hideDialog();
                    ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
                    ((ProjectCoopActivity) mContext).hideProgressDialog();
                }

            }

        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            CommonFunctions.hideDialog();
            ((ProjectCoopActivity) mContext).showToast("Something went wrong. Please try again.", GlobalToastEnum.NOTICE);
            ((ProjectCoopActivity) mContext).hideProgressDialog();
        }
    };

    private boolean isFieldsValid(String name, String accountNumber) {
        return name.length() > 0 && accountNumber.length() > 0;
    }

    private static void fadeInAnimation(final View view, long animationDuration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(animationDuration);
        view.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.setVisibility(View.VISIBLE);
    }

    private static void fadeOutAnimation(final View view, long animationDuration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(animationDuration);
        fadeOut.setDuration(animationDuration);
        view.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.setVisibility(View.GONE);
    }
}
