package com.goodkredit.myapplication.fragments.generateqr;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.goodkredit.myapplication.utilities.RetrofitBuilder;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogGenerateQRFragment extends DialogFragment {


    public static String TAG = "DialogGenerateQRFragment";
    public String imei = "";
    public String userid = "";
    public String devicetype = "";
    public String index = "";
    public String sessionid = "";

    private String authenticationid;
    private String keyEncryption;
    private String param;

    public String session = "";
    public String authcode = "";
    public String borrowerid = "";
    public String guarantorid = "";

    static String mID = null;
    static String bID = null;
    static String secKey = null;





    private ImageView qrcodegenerate,qrImgClose;
    private TextView btnGenerateQrCode;
    private ProgressBar progressCirular;

//    @Override
//    public void onStart() {
//        super.onStart();
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
//                WindowManager.LayoutParams.WRAP_CONTENT);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getData();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_view_qr, container, false);

        //init
        qrcodegenerate = v.findViewById(R.id.qrcodegenerate);
        qrImgClose  = v.findViewById(R.id.qrImgClose);
        btnGenerateQrCode = v.findViewById(R.id.btnGenerateQrCode);
        progressCirular = v.findViewById(R.id.progressCirular);

        qrImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnGenerateQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressCirular.setVisibility(View.VISIBLE);
                qrcodegenerate.setVisibility(View.GONE);
                validateQRPartialV2();
            }
        });



        validateQRPartialV2();

        return v;
    }


    private void getData() {
        sessionid = PreferenceUtils.getSessionID(getContext());
        imei = PreferenceUtils.getImeiId(getContext());
        userid = PreferenceUtils.getUserId(getContext());
        borrowerid = PreferenceUtils.getBorrowerId(getContext());
        session = PreferenceUtils.getSessionID(getContext());
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



}
