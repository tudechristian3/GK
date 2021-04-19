package com.goodkredit.myapplication.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.MainActivity;
import com.goodkredit.myapplication.activities.coopassistant.CoopAssistantHomeActivity;
import com.goodkredit.myapplication.activities.gknegosyo.GKNegosyoRedirectionActivity;
import com.goodkredit.myapplication.activities.gkstore.GkStoreHistoryActivity;
import com.goodkredit.myapplication.activities.skycable.SkyCableActivity;
import com.goodkredit.myapplication.activities.transactions.ViewOthersTransactionsActivity;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.common.Payment;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//--------------------------GLOBAL DIALOGS  Created on: (12/05/2018) - mm/dd/yy ---------------------
//Updated on: 11/18/2019

//PARAMETERS:
//messagetitle -> Refers to content for the title of the dialog. (TOP)
//messagecontent -> Content of the message of the dialog.(MIDDLE)
//btnOneMsg -> Refers to the content for the button.(FIRST)
//btnTwoMsg -> Refers to the content for the button.(SECOND)
//buttonTypeEnum -> Refers to type of button that will display on the bottom that contains a text message. (E.G SINGLE, DOUBLE)
//isGKNegosyoModal -> Refers to the GK Negosyo Modal (I Want This).
//ishtmlcode -  Refers whether the message content will be able to read html code format or not.
//dialogTypeEnum -> Refers to the type of dialog that will display. (E.G SUCCESS, FAILED and etc).

//TERMS:
//GLOBAL TEXT BUTTONS -> Refers to the button that will display on the bottom of the Dialog.
//CONTENT VIEW -> Refers to the content that will display in the middle of the Dialog.
//TITLE -> Refers to the header that will display on the top of the Dialog.
//CLOSE IMAGE BUTTON -> Refers to the image icon (X) that will display on the upper right of the dialog.


//NOTES:
//1. THIS DOES NOT INCLUDE ALL TYPES OF DIALOGS AND THE NUMBER OF GLOBAL TEXT BUTTONS THAT WILL
//// DISPLAY IS LIMITED TO TWO.
//2. IF YOU WANT TO CHANGE THE CONTENT VIEW, YOU CAN CHECK SET CONTENT VIEW BELOW FOR MORE DETAILS.
//3. IN SETTING THE CONTENT VIEW (RADIO, TEXTVIEW AND ETC) WITH AN ORIENTATION (HORIZONTAL)
//IS ENCOURAGE TO LIMIT TO TWO FOR NOW. IRREGULAR DISPLAY IS EXPECTED.
//4. TITLE IS SET TO HIDDEN BY DEFAULT. (dialog.showContentTitle(); to show the Title).

//The things listed here is subject to change. Please be advised.


//--------------------SAMPLE (COMMON) -----------------------

//DEFAULT ACTION:

//     GlobalDialogs dialog = new GlobalDialogs(getViewContext());
//
//     dialog.createDialog("","A Quick Brown Fox Jumps Over" +
//     "The Lazy Dog.", "Close", "",ButtonTypeEnum.SINGLE,
//     false, false, DialogTypeEnum.NOTICE);
//
//     dialog.defaultDialogActions();

//CUSTOM ACTION (SINGLE):

//    GlobalDialogs dialog = new GlobalDialogs(getViewContext());
//
//    dialog.createDialog("","A Quick Brown Fox Jumps Over" +
//                    "The Lazy Dog.", "Close", "",ButtonTypeEnum.SINGLE,
//            false, false, DialogTypeEnum.NOTICE);

//    View closebtn = dialog.btnCloseImage();
//    closebtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            dialog.dismiss();
//        }
//    });
//    View singlebtn = dialog.btnSingle();
//    singlebtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            dialog.dismiss();
//
//        }
//    });

//CUSTOM ACTION (DOUBLE):

//    GlobalDialogs dialog = new GlobalDialogs(getViewContext());
//
//    dialog.createDialog("","A Quick Brown Fox Jumps Over" +
//    "The Lazy Dog.", "Close", "Next", ButtonTypeEnum.DOUBLE,
//    false, false, DialogTypeEnum.NOTICE);
//
//    View closebtn = dialog.btnCloseImage();
//    closebtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            dialog.dismiss();
//        }
//    });

//    View btndoubleone = dialog.btnDoubleOne();
//    btndoubleone.setOnClickListener(new View.OnClickListener() {
//    @Override
//        public void onClick(View view) {
//            dialog.dismiss();
//
//        }
//    });
//
//    View btndoubletwo = dialog.btnDoubleTwo();
//    btndoubletwo.setOnClickListener(new View.OnClickListener() {
//    @Override
//        public void onClick(View view) {
//            dialog.dismiss();
//
//        }
//    });


public class GlobalDialogs extends Dialog {
    //GLOBAL DIALOG
    private Dialog mGlobalDialog = null;

    private TextView mGlobalTitle;
    private LinearLayout mGlobalContentViewContainer;
    private ImageView mGlobalContentImageView;
    private TextView mGlobalContentView;
    private FrameLayout mGlobalPaymentLogoContainer;
    private RelativeLayout mDialogTitleLogoContainer;
    private ImageView mGlobalTitleDialogLogo;
    private LinearLayout mGlobalCloseBtn;

    private String strmessagetitle = "";
    private String strmessagecontent = "";
    private String strbuttonmessage = "";
    private String strbuttonmessagetwo = "";
    private ButtonTypeEnum typebuttonTypeEnum;
    private boolean boolisGKNegosyoModal = false;
    private boolean boolishtmlcode = false;
    private DialogTypeEnum typedialogTypeEnum;
    private boolean boolredirects = false;

    //GLOBAL BUTTON
    private LinearLayout btn_global;
    private TextView txv_btn_global;
    private LinearLayout btn_global_double_container;
    private TextView txv_btn_global_double;
    private TextView txv_btn_global_double_two;

    //GLOBAL QRCODE
    private LinearLayout layout_qrcode_container;
    private TableRow approvalcodewrap;
    private ImageView leftarrow;
    private ImageView successBarcode;
    private ImageView successQRCode;
    private ImageView rightarrow;

    private LinearLayout layout_values_container;
    private TextView barcodeValue;
    private TextView merchantRefCodeLabel;
    private TextView dialogNote;

    //GK NEGOSYO
    private LinearLayout linearGkNegosyoLayout;
    private TextView txvGkNegosyoDescription;
    private TextView txvGkNegosyoRedirection;

    //GLOBAL SKYCABLE
    private String strskybtnaction = "";

    private Fragment globalfragment = null;

    private Context mContext;

    //GLOBAL TEXTVIEW
    private LinearLayout layout_txt_maincontainer;
    private LinearLayout layout_txt_container;

    //GLOBAL DOUBLE TEXTVIEW
    private LinearLayout layout_txtdbl_maincontainer;
    private LinearLayout layout_txtdbl_container;

    //GLOBAL RADIOBUTTON
    private LinearLayout rg_options_container;
    private LinearLayout rg_options;

    //GLOBAL EDITTEXT
    private LinearLayout layout_edttxt_maincontainer;
    private LinearLayout layout_edttxt_container;

    //GLOBAL SPINNER
    private LinearLayout layout_spinner_maincontainer;
    private LinearLayout layout_spinner_container;

    //GLOBAL CUSTOM
    private LinearLayout layout_custom_maincontainer;
    private LinearLayout layout_custom_container;

    //for gkstore
    private LinearLayout layout_custom_maincontainer2;
    private LinearLayout layout_custom_container1,layout_custom_container2;


    public GlobalDialogs(@NonNull Context context) {
        super(context);
        if (context instanceof Activity) {
            setOwnerActivity((Activity) context);
        }
        mContext = context;
    }

    //-----------DEFAULT/RECOMMENDED------------
    public void createDialog(String messagetitle, String messagecontent, String btnOneMsg, String btnTwoMsg,
                             ButtonTypeEnum buttonTypeEnum, boolean isGKNegosyoModal, boolean ishtmlcode,
                             DialogTypeEnum dialogTypeEnum) {

        strmessagetitle = messagetitle;
        strmessagecontent = messagecontent;
        strbuttonmessage = btnOneMsg;
        strbuttonmessagetwo = btnTwoMsg;
        typebuttonTypeEnum = buttonTypeEnum;
        boolisGKNegosyoModal = isGKNegosyoModal;
        boolishtmlcode = ishtmlcode;
        typedialogTypeEnum = dialogTypeEnum;

        globalDialogsMain();
    }

    private void globalDialogsMain() {
        try {
            if (mGlobalDialog != null) {
                mGlobalDialog.dismiss();
                mGlobalDialog = null;
                checkIfActivityIsBeingFinished();
            } else {
                checkIfActivityIsBeingFinished();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
            e.getMessage();
        }
    }

    private void checkIfActivityIsBeingFinished() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(!Objects.requireNonNull(getOwnerActivity()).isFinishing()) {
                displayGlobalDialog();
            }
        } else {
            if(!Objects.requireNonNull(getOwnerActivity()).isFinishing()){
                displayGlobalDialog();
            }
        }
    }

    private void displayGlobalDialog() {
        mGlobalDialog = new Dialog(mContext);
        mGlobalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(mGlobalDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } else {
            mGlobalDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        mGlobalDialog.setContentView(R.layout.dialog_global_messages);
        mGlobalDialog.setCancelable(false);

        //CUSTOM FIELDS
        //TITLE
        mGlobalTitle = (TextView) mGlobalDialog.findViewById(R.id.txv_title);
        mGlobalTitle.setAllCaps(false);
        mGlobalTitle.setVisibility(View.GONE);
        //CONTENT
        mGlobalContentViewContainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_contentview_container);
        mGlobalContentViewContainer.setVisibility(View.VISIBLE);
        //IMAGE CONTENT
        mGlobalContentImageView = (ImageView) mGlobalDialog.findViewById(R.id.img_content_logo);
        mGlobalContentImageView.setVisibility(View.GONE);
        //TITLE LOGO
        mDialogTitleLogoContainer = (RelativeLayout) mGlobalDialog.findViewById(R.id.layout_titlelogo_container);
        mGlobalTitleDialogLogo = (ImageView) mGlobalDialog.findViewById(R.id.img_title_logo);
        //PAYMENT LOGO
        mGlobalPaymentLogoContainer = (FrameLayout) mGlobalDialog.findViewById(R.id.layout_payment_logo_container);
        mGlobalPaymentLogoContainer.setVisibility(View.GONE);

        //CLOSE ICON BUTTON
        mGlobalCloseBtn = (LinearLayout) mGlobalDialog.findViewById(R.id.btn_close_container);
        mGlobalCloseBtn.setVisibility(View.VISIBLE);

        //BUTTON GLOBAL CONTAINER (SINGLE AND DOUBLE)
        btn_global = (LinearLayout) mGlobalDialog.findViewById(R.id.btn_global);
        btn_global_double_container = (LinearLayout) mGlobalDialog.findViewById(R.id.btn_global_double_container);

        //BUTTON TEXT
        txv_btn_global = (TextView) mGlobalDialog.findViewById(R.id.txv_btn_global);
        txv_btn_global_double = (TextView) mGlobalDialog.findViewById(R.id.txv_btn_global_double);
        txv_btn_global_double_two = (TextView) mGlobalDialog.findViewById(R.id.txv_btn_global_double_two);

        //TEXTVIEW CONTENT
        layout_txt_maincontainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_txt_maincontainer);
        layout_txt_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_txt_container);

        //DOUBLE TEXTVIEW CONTENT
        layout_txtdbl_maincontainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_txtdbl_maincontainer);
        layout_txtdbl_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_txtdbl_container);

        //RADIO LAYOUT CONTENT
        rg_options_container = (LinearLayout) mGlobalDialog.findViewById(R.id.rg_options_container);
        rg_options = (LinearLayout) mGlobalDialog.findViewById(R.id.rg_options);

        //EDITTEXT CONTENT
        layout_edttxt_maincontainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_edttxt_maincontainer);
        layout_edttxt_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_edttxt_container);

        //SPINNER CONTENT
        layout_spinner_maincontainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_spinner_maincontainer);
        layout_spinner_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_spinner_container);

        //CUSTOM CONTENT
        layout_custom_maincontainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_custom_maincontainer);
        layout_custom_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_custom_container);

        layout_custom_maincontainer = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_custom_maincontainer);
        layout_custom_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_custom_container);

        switch (typedialogTypeEnum) {
            case SUCCESS:
                globalSuccess();
                break;
            case FAILED:
                globalFailed();
                break;
            case ONPROCESS:
                globalOnProcess();
                break;
            case TIMEOUT:
                globalTimeOut();
                break;
            case QRCODE:
                globalQRCode();
                break;
            case IMAGE:
                globalImage();
                break;
            case TEXT:
                globalText();
                break;
            case NOTICE:
                globalNotice();
                break;
            case WARNING:
                globalWarning();
                break;
            case TEXTVIEW:
                globalCustomContent();
                break;
            case DOUBLETEXTVIEW:
                globalCustomContent();
                break;
            case RADIO:
                globalCustomContent();
                break;
            case EDITTEXT:
                globalCustomContent();
                break;
            case SPINNER:
                globalCustomContent();
                break;
            case CUSTOMCONTENT:
                globalCustomContent();
                break;
        }

        globalGKNegosyoModal();
        globalButtonsView();
        mGlobalDialog.show();
    }

    //SUCCESS
    private void globalSuccess() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            mGlobalTitle.setText("SUCCESS");
        } else {
            mGlobalTitle.setText(strmessagetitle);
        }
        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_success_green));
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
        mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_success);
        //CUSTOM BUTTONS
        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_success_green));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_success_green));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_success_lightgreen));
    }

    //FAILED
    private void globalFailed() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            mGlobalTitle.setText("FAILED");
        } else {
            mGlobalTitle.setText(strmessagetitle);
        }
        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_error_red));
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
        mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_error);
        //CUSTOM BUTTONS
        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_error_red));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_error_red));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_error_lightred));
    }

    //ON PROCESS
    private void globalOnProcess() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            mGlobalTitle.setText("ON PROCESS");
        } else {
            mGlobalTitle.setText(strmessagetitle);
        }
        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_yellow));
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
        mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_onprocess);
        //CUSTOM BUTTONS
        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_yellow));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_yellow));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_lightyellow));
    }

    //TIMEOUT
    private void globalTimeOut() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            mGlobalTitle.setText("TIMEOUT");
        } else {
            mGlobalTitle.setText(strmessagetitle);
        }
        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_timeout_orange));
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
        mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_timeout);
        //CUSTOM BUTTONS
        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_timeout_orange));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_timeout_orange));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_timeout_lightorange));
    }

    //QR CODE
    private void globalQRCode() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            mGlobalTitle.setText("TRANSACTION COMPLETE");
        } else {
            mGlobalTitle.setText(strmessagetitle);
        }
        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_qrcode_darkblue));
        mGlobalContentViewContainer.setVisibility(View.GONE);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.GONE);
        //QRLAYOUT
        globalQRCodeLayout(strmessagecontent);
        //CLOSE
        mGlobalCloseBtn.setVisibility(View.GONE);
        //CUSTOM BUTTONS
        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_qrcode_darkblue));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_qrcode_darkblue));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_qrcode_lightblue));
    }

    @SuppressLint("ClickableViewAccessibility")
    private void globalQRCodeLayout(String messagecontent) {
        try {
            layout_qrcode_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_qrcode_container);
            approvalcodewrap = (TableRow) mGlobalDialog.findViewById(R.id.approvalcodewrap);
            leftarrow = (ImageView) mGlobalDialog.findViewById(R.id.leftarrow);
            successBarcode = (ImageView) mGlobalDialog.findViewById(R.id.successBarcode);
            successQRCode = (ImageView) mGlobalDialog.findViewById(R.id.successQRCode);
            rightarrow = (ImageView) mGlobalDialog.findViewById(R.id.rightarrow);

            layout_values_container = (LinearLayout) mGlobalDialog.findViewById(R.id.layout_values_container);
            barcodeValue = (TextView) mGlobalDialog.findViewById(R.id.barcodeValue);
            merchantRefCodeLabel = (TextView) mGlobalDialog.findViewById(R.id.barcodeValue);
            dialogNote = (TextView) mGlobalDialog.findViewById(R.id.dialogNote);

            dialogNote.setText("Present this QR code/barcode to the merchant for your payment verification.");
            barcodeValue.setText(messagecontent);

            // barcode image
            Bitmap bitmap = null;
            try {
                bitmap = encodeAsBitmap(messagecontent, BarcodeFormat.CODE_128, 600, 300);
                successBarcode.setImageBitmap(bitmap);

                bitmap = encodeAsBitmap(messagecontent, BarcodeFormat.QR_CODE, 600, 300);
                successQRCode.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
                e.getLocalizedMessage();
                e.getMessage();
            }

            layout_qrcode_container.setVisibility(View.VISIBLE);
            approvalcodewrap.setVisibility(View.VISIBLE);
            successQRCode.setVisibility(View.VISIBLE);
            barcodeValue.setVisibility(View.VISIBLE);

            layout_values_container.setVisibility(View.VISIBLE);

            //slide left and right to reverse view of the barcode or QR code
            successQRCode.setOnTouchListener(new OnSwipeTouchListener() {
                public void onSwipeRight() {
                    successQRCode.setVisibility(View.GONE);
                    successBarcode.setVisibility(View.VISIBLE);
                }

                public void onSwipeLeft() {
                    successQRCode.setVisibility(View.GONE);
                    successBarcode.setVisibility(View.VISIBLE);
                }
            });

            successBarcode.setOnTouchListener(new OnSwipeTouchListener() {
                public void onSwipeRight() {
                    successQRCode.setVisibility(View.VISIBLE);
                    successBarcode.setVisibility(View.GONE);
                }

                public void onSwipeLeft() {
                    successQRCode.setVisibility(View.VISIBLE);
                    successBarcode.setVisibility(View.GONE);
                }

            });

            leftarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (successQRCode.getVisibility() == View.VISIBLE) {
                        successQRCode.setVisibility(View.GONE);
                        successBarcode.setVisibility(View.VISIBLE);
                    } else {
                        successQRCode.setVisibility(View.VISIBLE);
                        successBarcode.setVisibility(View.GONE);
                    }

                }

            });

            rightarrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (successQRCode.getVisibility() == View.VISIBLE) {
                        successQRCode.setVisibility(View.GONE);
                        successBarcode.setVisibility(View.VISIBLE);
                    } else {
                        successQRCode.setVisibility(View.VISIBLE);
                        successBarcode.setVisibility(View.GONE);
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
            e.getMessage();
        }
    }

    //IMAGE
    private void globalImage() {
        //TITLE AND CONTENT
        mGlobalTitle.setVisibility(View.GONE);
        mGlobalContentViewContainer.setVisibility(View.GONE);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.GONE);
        //CONTENT LOGO
        mGlobalContentImageView.setVisibility(View.VISIBLE);
        globalContentImageLogo();
    }

    private void globalContentImageLogo() {
        if (mContext.getClass().equals(SkyCableActivity.class)) {
            Glide.with(mContext)
                    .load(R.drawable.skycable_header)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(mGlobalContentImageView);

            txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_image_darkblue));
            txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_image_darkblue));
            txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_image_lightblue));
        } else {
            Glide.with(mContext)
                    .load(R.drawable.goodkredit_logo)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(mGlobalContentImageView);
            txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_image_darkgray));
            txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_image_darkgray));
            txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_image_lightgray));
        }
    }

    //TEXT
    private void globalText() {
        //TITLE AND CONTENT
        mGlobalTitle.setVisibility(View.GONE);
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.GONE);
        //CUSTOM BUTTONS
        globalContentText();
    }

    private void globalContentText() {
        if (mContext.getClass().equals(SkyCableActivity.class)) {
            //PAYMENT LOGO
            mGlobalPaymentLogoContainer.setVisibility(View.VISIBLE);

            mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_text_darkblue));

            txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_text_lightblue));
            txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_text_lightblue));
            txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_text_darkblue));

        } else {
            mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_text_darkgray));

            txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_text_lightgray));
            txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_text_lightgray));
            txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_text_darkgray));
        }
    }

    //NOTICE
    private void globalNotice() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            mGlobalTitle.setText("NOTICE");
        } else {
            mGlobalTitle.setText(strmessagetitle);
        }

        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
        mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_notice);

        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_information_lightblue));
    }

    private void globalContentNotice() {
        if (mContext.getClass().equals(SkyCableActivity.class)) {
            //PAYMENT LOGO
            mGlobalPaymentLogoContainer.setVisibility(View.VISIBLE);
            txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_information_lightblue));
        } else {
            txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
            txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_information_lightblue));
        }
    }

    //WARNING
    private void globalWarning() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            mGlobalTitle.setText("WARNING");
        } else {
            mGlobalTitle.setText(strmessagetitle);
        }
        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_yellow));
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
        mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_warning);

        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_yellow));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_yellow));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_onprocess_lightyellow));
    }

    //CUSTOM CONTENT
    private void globalCustomContent() {
        //TITLE AND CONTENT
        if (strmessagetitle.trim().equals("")) {
            if(typedialogTypeEnum == DialogTypeEnum.RADIO) {
                mGlobalTitle.setText("Options");
            } else {
                mGlobalTitle.setText("Notice");
            }

        } else {
            mGlobalTitle.setText(strmessagetitle);
        }
        mGlobalTitle.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
        globalContentWithHTMLCode(boolishtmlcode, strmessagecontent);
        mGlobalContentViewContainer.setVisibility(View.GONE);

        //TITLE LOGO
        mDialogTitleLogoContainer.setVisibility(View.VISIBLE);
        mGlobalTitleDialogLogo.setImageResource(R.drawable.dialog_global_notice);

        txv_btn_global.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
        txv_btn_global_double.setTextColor(mContext.getResources().getColor(R.color.color_information_blue));
        txv_btn_global_double_two.setTextColor(mContext.getResources().getColor(R.color.color_information_lightblue));
    }

    //CHECKS IF THE CONTENT WILL BE DISPLAYED WITH HTML FORMAT OR NOT
    private void globalContentWithHTMLCode(boolean ishtmlcode, String message) {
        mGlobalContentView = (TextView) mGlobalDialog.findViewById(R.id.txv_contentview);
        if (ishtmlcode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mGlobalContentView.setText(Html.fromHtml(message, Html.FROM_HTML_MODE_COMPACT));
            } else {
                mGlobalContentView.setText(Html.fromHtml(message));
            }
        } else {
            mGlobalContentView.setText(message);
        }
    }

    //GK NEGOSYO MODAL
    private void globalGKNegosyoModal() {
        linearGkNegosyoLayout = (LinearLayout) mGlobalDialog.findViewById(R.id.linearGkNegosyoLayout);

        if (boolisGKNegosyoModal) {
            linearGkNegosyoLayout.setVisibility(View.VISIBLE);
            txvGkNegosyoDescription = (TextView) mGlobalDialog.findViewById(R.id.txvGkNegosyoDescription);
            txvGkNegosyoDescription.setText(CommonFunctions.setTypeFace(mContext, "fonts/RobotoCondensed-Regular.ttf", mContext.getResources().getString(R.string.str_gk_negosyo_prompt)));
            txvGkNegosyoRedirection = (TextView) mGlobalDialog.findViewById(R.id.txvGkNegosyoRedirection);
            txvGkNegosyoRedirection.setText(CommonFunctions.setTypeFace(mContext, "fonts/RobotoCondensed-Regular.ttf", "I WANT THIS!"));

            final DatabaseHandler mdb = new DatabaseHandler(mContext);
            txvGkNegosyoRedirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GKNegosyoRedirectionActivity.start(mContext, mdb.getGkServicesData(mdb, "GKNEGOSYO"));
                }
            });
        } else {
            linearGkNegosyoLayout.setVisibility(View.GONE);
        }
    }

    //GLOBAL BUTTON
    private void globalButtonsView() {
        switch (typebuttonTypeEnum) {
            case SINGLE:
                //CONTAINER
                btn_global.setVisibility(View.VISIBLE);
                btn_global_double_container.setVisibility(View.GONE);

                if (strbuttonmessage.trim().equals("")) {
                    txv_btn_global.setText("Close");
                } else {
                    txv_btn_global.setText(strbuttonmessage);
                }
                break;

            case DOUBLE:
                //CONTAINER
                btn_global.setVisibility(View.GONE);
                btn_global_double_container.setVisibility(View.VISIBLE);

                if (strbuttonmessage.trim().equals("")) {
                    txv_btn_global_double.setVisibility(View.GONE);
                    txv_btn_global_double_two.setVisibility(View.GONE);
                } else {
                    txv_btn_global_double.setText(strbuttonmessage);
                    txv_btn_global_double_two.setText(strbuttonmessagetwo);
                }


                break;
        }
    }

    //--------------------COMMON ACTIONS ----------------------

    //DEFAULT ACTION FOR DIALOG ACTION BUTTONS
    public void defaultDialogActions() {

        try {
            if (mGlobalDialog != null)
                //CLOSE ICON BUTTON
                mGlobalCloseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGlobalDialog.dismiss();
                    }
                });

            //BUTTON ACTION
            switch (typebuttonTypeEnum) {
                case SINGLE:
                    btn_global.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mGlobalDialog.dismiss();
                        }
                    });
                    break;

                case DOUBLE:
                    txv_btn_global_double.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mGlobalDialog.dismiss();
                        }
                    });

                    txv_btn_global_double_two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mGlobalDialog.dismiss();
                        }
                    });
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO HIDE THE CLOSE BUTTON
    public void hideCloseImageButton() {
        try {
            if (mGlobalDialog != null)
                //CLOSE ICON BUTTON
                mGlobalCloseBtn.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO SHOW THE CLOSE BUTTON
    public void showCloseImageButton() {
        try {
            if (mGlobalDialog != null)
                //CLOSE ICON BUTTON
                mGlobalCloseBtn.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO HIDE THE TEXT BUTTONS (SINGLE AND DOUBLE)
    public void hideTextButtonAction() {
        try {
            if (mGlobalDialog != null)
                //BUTTON ACTION
                switch (typebuttonTypeEnum) {
                    case SINGLE:
                        btn_global.setVisibility(View.GONE);
                        break;
                    case DOUBLE:
                        txv_btn_global_double.setVisibility(View.GONE);
                        txv_btn_global_double_two.setVisibility(View.GONE);
                        break;
                }
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO SHOW THE TEXT BUTTONS (SINGLE AND DOUBLE)
    public void showTextButtonAction() {
        try {
            if (mGlobalDialog != null)
                //BUTTON ACTION
                switch (typebuttonTypeEnum) {
                    case SINGLE:
                        btn_global.setVisibility(View.VISIBLE);
                        break;
                    case DOUBLE:
                        txv_btn_global_double.setVisibility(View.VISIBLE);
                        txv_btn_global_double_two.setVisibility(View.VISIBLE);
                        break;
                }
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO HIDE THE CONTENT MESSAGE
    public void hideContentMessage() {
        try {
            if (mGlobalDialog != null)
                //CLOSE ICON BUTTON
                mGlobalContentViewContainer.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO SHOW THE CONTENT MESSAGE
    public void showContentMessage() {
        try {
            if (mGlobalDialog != null)
                //CLOSE ICON BUTTON
                mGlobalContentViewContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO HIDE THE TITLE
    public void hideContentTitle() {
        try {
            if (mGlobalDialog != null)
                //CLOSE ICON BUTTON
                mGlobalTitle.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO SHOW THE TITLE
    public void showContentTitle() {
        try {
            if (mGlobalDialog != null)
                //CLOSE ICON BUTTON
                mGlobalTitle.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    //IF YOU WANT TO HIDE THE TITLE
    public void hideContentImageTitle(){
        try {
            if (mGlobalTitleDialogLogo != null)
                //CLOSE ICON BUTTON
                mGlobalTitleDialogLogo.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }


    //--------------------CUSTOM ACTIONS FOR TEXT BUTTONS----------------------
    //If you prefer to customize your action on your local activities/fragments.

    //CLOSE BUTTON ON TOP.
    public View btnCloseImage() {
        return mGlobalCloseBtn;
    }

    //BOTTOM TXT BUTTON (SINGLE)
    public View btnSingle() {
        return btn_global;
    }

    //BOTTOM TXT BTN (DOUBLE) --> (FIRST)
    public View btnDoubleOne() {
        return txv_btn_global_double;
    }

    //BOTTOM TXT BTN (DOUBLE) --> (SECOND)
    public View btnDoubleTwo() {
        return txv_btn_global_double_two;
    }


    //------------------CUSTOM PARAMS-----------------

    //DEFAULT LAYOUT PARAMS
    public LinearLayout.LayoutParams defaultLayoutParams() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f
        );
    }

    //------------------CUSTOM LAYOUTS----------------------
    //Returns specific views so that you can use it on your local class.

    //DIALOG
    public Dialog getDialogView() {
        return mGlobalDialog;
    }

    //TITLE
    public TextView getTitleView() {
        return mGlobalTitle;
    }

    //TITLE IMAGE LOGO
    public ImageView getTitleImageLogo() { return mGlobalTitleDialogLogo;}

    //CLOSE IMAGE BUTTON
    public LinearLayout getCloseImageBtn() {
        return mGlobalCloseBtn;
    }

    //CONTAINER FOR SINGLE BUTTON
    public LinearLayout getSingleBtnContainer() {
        return btn_global;
    }

    //BOTTOM TXT (SINGLE)
    public TextView getTxtBtnSingle() {
        return txv_btn_global;
    }

    //CONTAINER FOR DOUBLE BUTTON
    public LinearLayout getDoubleBtnContainer() {
        return btn_global_double_container;
    }

    //BOTTOM TXT BTN (DOUBLE) --> (FIRST)
    public TextView getTxtBtnDoubleOne() {
        return txv_btn_global_double;
    }

    //BOTTOM TXT BTN (DOUBLE) --> (SECOND)
    public TextView getTxtBtnDoubleTwo() {
        return txv_btn_global_double_two;
    }

    //MESSAGE CONTENT
    public LinearLayout getContentMessage() {
        return mGlobalContentViewContainer;
    }

    //IMAGE LOGO
    public ImageView getContentImageView() {
        return mGlobalContentImageView;
    }


    //CUSTOM CONTENT VIEW
    public LinearLayout getTextViewMessageContainer() {
        return layout_txt_maincontainer;
    }

    public LinearLayout getDoubleTextViewMessageContainer() {
        return layout_txtdbl_maincontainer;
    }

    public LinearLayout getRadioMessageContainer() {
        return rg_options_container;
    }

    public LinearLayout getEditTextMessageContainer() {
        return layout_edttxt_maincontainer;
    }

    public LinearLayout getCustomMessageContainer() {
        return layout_custom_maincontainer;
    }

    public LinearLayout getSpinnerMessageContainer() {
        return layout_spinner_maincontainer;
    }

    //------------------------CUSTOM SET CONTENT VIEW-----------------------

    //A custom layout that will replace the current CONTENT VIEW.

    //---------------------------TEXTVIEW-----------------------------------

    //HOW IT WORKS:
    //dialog.setContentTextView(YOURLISTSTRINGHERE, LinearLayoutOrientation, GlobalDialogsObject);

    //SAMPLE USE:
    // dialog.setContentTextView(textViewContext, LinearLayout.VERTICAL,
    // new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER));

    public LinearLayout setContentTextView(List<String> strArrTextView, int linearLayoutGroupOrientation,
                                           GlobalDialogsObject globalDialogsObject) {

        layout_txt_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = createCustomTextView(strArrTextView, linearLayoutGroupOrientation, globalDialogsObject);

        layout_txt_container.setOrientation(LinearLayout.VERTICAL);
        layout_txt_container.addView(linearLayout);

        return linearLayout;
    }

    //------------------------DOUBLE TEXTVIEW--------------------

    //TITLE ->> LABEL (START)
    //CONTENT -->> DATA YOU WANT TO DISPLAY (END)

    //HOW IT WORKS:
    //dialog.setContentDoubleTextView(LISTSTRINGHEREFORTITLE, GlobalDialogsObjectfortitle,
    //LISTSTRINGHEREFORCONTENT, GlobalDialogsObjectforcontent);

    //SAMPLE USE:
    //dialog.setContentDoubleTextView(textViewContext, new GlobalDialogsObject(R.color.color_908F90, 16, Gravity.START),
    //contentStringlist, new GlobalDialogsObject(R.color.color_23A8F6, 12, Gravity.END));

    public LinearLayout setContentDoubleTextView(List<String> strtitleTextView, GlobalDialogsObject titleGlobalDialogs,
                                                 List<String> strcontentTextView, GlobalDialogsObject contentGlobalDialogs) {

        layout_txtdbl_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = createCustomDoubleTextView(strtitleTextView, titleGlobalDialogs, strcontentTextView, contentGlobalDialogs);

        layout_txtdbl_container.setOrientation(LinearLayout.VERTICAL);
        layout_txtdbl_container.addView(linearLayout);

        return linearLayout;
    }

    //------------------------EDITTEXT --------------------

    //HOW IT WORKS:
    //dialog.setContentEditText(YOURDATTYPESTRINGHERE, LinearLayoutOrientation, GlobalDialogsObject);

    //SAMPLE USE:
    //LinearLayout editTextContainer = dialog.setContentEditText(editTextDataType,
    //        LinearLayout.VERTICAL,
    //        new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.CENTER,
    //                R.drawable.underline, 12, "New Group"));

    public LinearLayout setContentEditText(List<String> strArrEditTextDataType, int linearLayoutGroupOrientation,
                                           GlobalDialogsObject globalDialogsObject) {

        layout_edttxt_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = createCustomEditText(strArrEditTextDataType, linearLayoutGroupOrientation, globalDialogsObject);

        layout_edttxt_container.setOrientation(LinearLayout.VERTICAL);
        layout_edttxt_container.addView(linearLayout);

        return linearLayout;
    }

    //--------------------RADIO-----------------------

    //HOW IT WORKS:
    //dialog.setContentRadio(YOURLISTSTRINGHERE, RadioGroupOrientation, GlobalDialogsObject);

    //SAMPLE USE:
    //RadioGroup radgroup = dialog.setContentRadio(radiocontent, RadioGroup.VERTICAL,
    //new GlobalDialogsObject(R.color.colorTextGrey, 25, 0));

    public LinearLayout setContentRadio(List<String> strArrRadio, int radioGroupOrientation,
                                      GlobalDialogsObject globalDialogsObject) {

        rg_options_container.setVisibility(View.VISIBLE);

        LinearLayout radGroupContainer = createCustomRadio(strArrRadio, radioGroupOrientation, globalDialogsObject);

        rg_options.setOrientation(LinearLayout.VERTICAL);
        rg_options.addView(radGroupContainer);

        return radGroupContainer;
    }

    public LinearLayout setContentSpinner(ArrayList<String> strArrSpinner, int linearLayoutOrientation,
                                          GlobalDialogsObject globalDialogsObject) {

        layout_spinner_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout spinnerContainer = createCustomMultiSpinner(strArrSpinner, linearLayoutOrientation, globalDialogsObject);

        layout_spinner_container.setOrientation(linearLayoutOrientation);
        layout_spinner_container.addView(spinnerContainer);

        return spinnerContainer;
    }


    //------------------IF YOU WANT TO GROUP MULTI DIFFERENT TYPES OF VIEWS ON A SINGLE LAYOUT -------------------

    public LinearLayout setContentCustomMultiTextView(List<String> strArrTextView, int linearLayoutGroupOrientation,
                                           GlobalDialogsObject globalDialogsObject) {

        layout_custom_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = createCustomTextView(strArrTextView, linearLayoutGroupOrientation, globalDialogsObject);

        layout_custom_container.setOrientation(LinearLayout.VERTICAL);
        layout_custom_container.addView(linearLayout);

        return linearLayout;
    }

    public List<LinearLayout> setContentCustomDoubleLinear(List<String> strArrTextView, int linearLayoutGroupOrientation,
                                                      GlobalDialogsObject globalDialogsObject) {

        layout_custom_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout linearLayout1 = createCustomTextView(Collections.singletonList(strArrTextView.get(0)), linearLayoutGroupOrientation, globalDialogsObject);
        LinearLayout linearLayout2 = createCustomTextView(Collections.singletonList(strArrTextView.get(1)), linearLayoutGroupOrientation, globalDialogsObject);
        layout_custom_container.setOrientation(LinearLayout.VERTICAL);
        layout_custom_container.addView(linearLayout1);
        layout_custom_container.addView(linearLayout2);

        List<LinearLayout> linearLayouts = new ArrayList<>();
        linearLayouts.add(linearLayout1);
        linearLayouts.add(linearLayout2);


        return linearLayouts;
    }


    public LinearLayout setContentCustomMultiDoubleTextView(List<String> strtitleTextView, GlobalDialogsObject titleGlobalDialogs,
                                                 List<String> strcontentTextView, GlobalDialogsObject contentGlobalDialogs) {

        layout_custom_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = createCustomDoubleTextView(strtitleTextView, titleGlobalDialogs, strcontentTextView, contentGlobalDialogs);

        layout_custom_maincontainer.setOrientation(LinearLayout.VERTICAL);
        layout_custom_maincontainer.addView(linearLayout);

        return linearLayout;
    }

    public LinearLayout setContentCustomMultiEditText(List<String> strArrEditTextDataType, int linearLayoutGroupOrientation,
                                           GlobalDialogsObject globalDialogsObject) {

        layout_custom_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout linearLayout = createCustomEditText(strArrEditTextDataType, linearLayoutGroupOrientation, globalDialogsObject);

        layout_custom_container.setOrientation(LinearLayout.VERTICAL);
        layout_custom_container.addView(linearLayout);

        return linearLayout;
    }

    public LinearLayout setContentCustomMultiRadio(List<String> strArrRadio, int radioGroupOrientation,
                                      GlobalDialogsObject globalDialogsObject) {
        layout_custom_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout radGroupContainer = createCustomRadio(strArrRadio, radioGroupOrientation, globalDialogsObject);

        layout_custom_maincontainer.setOrientation(LinearLayout.VERTICAL);
        layout_custom_maincontainer.addView(radGroupContainer);

        return radGroupContainer;
    }

    public LinearLayout setContentCustomMultiSpinner(ArrayList<String> strArrSpinner, int linearLayoutOrientation,
                                          GlobalDialogsObject globalDialogsObject) {

        layout_custom_maincontainer.setVisibility(View.VISIBLE);

        LinearLayout spinnerContainer = createCustomMultiSpinner(strArrSpinner, linearLayoutOrientation, globalDialogsObject);

        layout_custom_maincontainer.setOrientation(linearLayoutOrientation);
        layout_custom_maincontainer.addView(spinnerContainer);

        return spinnerContainer;
    }


    //------------------------------CREATING THE VIEWS--------------------------

    //CREATE TEXTVIEW
    private LinearLayout createCustomTextView(List<String> strArrTextView, int linearLayoutGroupOrientation,
                                              GlobalDialogsObject globalDialogsObject) {

        String globalTypeFace = globalDialogsObject.getTypeface();
        int globalColor = globalDialogsObject.getColorID();
        int globalTextSize = globalDialogsObject.getTextSize();
        int globalGravity = globalDialogsObject.getGravity();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
        layoutParams.setMargins(10, 10, 10, 10);

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(linearLayoutGroupOrientation);

        for (int i = 0; i < strArrTextView.size(); i++) {
            TextView textView = new TextView(mContext);

            //TYPEFACE
            if(globalTypeFace != null) {
                if(!globalTypeFace.equals("")) {
                    textView.setTypeface(V2Utils.setFontInput(mContext, globalTypeFace));
                } else {
                    textView.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                }
            } else {
                textView.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
            }


            //COLORS
            if (globalColor > 0) {
                textView.setTextColor(ContextCompat.getColor(mContext, globalColor));
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));
            }

            //TEXTSIZE
            if (globalTextSize > 0) {
                textView.setTextSize(globalTextSize);
            } else {
                textView.setTextSize(16);
            }

            //GRAVITY
            if (globalGravity > 0) {
                textView.setGravity(globalGravity);
            } else {
                textView.setGravity(Gravity.CENTER);
            }

            textView.setPadding(10, 10, 10, 10);
            textView.setText(strArrTextView.get(i));
            textView.setTag(String.valueOf(strArrTextView.get(i)));
            textView.setId(i);
            linearLayout.addView(textView, layoutParams);
        }

        return  linearLayout;
    }

    //CREATE 2 COLUMNS OF TEXTVIEW
    private LinearLayout createCustomDoubleTextView(List<String> strtitleTextView, GlobalDialogsObject titleGlobalDialogs,
                                                    List<String> strcontentTextView, GlobalDialogsObject contentGlobalDialogs) {

        String titleglobalTypeFace = titleGlobalDialogs.getTypeface();
        int titleglobalColor = titleGlobalDialogs.getColorID();
        int titleglobalTextSize = titleGlobalDialogs.getTextSize();
        int titleglobalGravity = titleGlobalDialogs.getGravity();

        String contentglobalTypeFace = contentGlobalDialogs.getTypeface();
        int contentglobalColor = contentGlobalDialogs.getColorID();
        int contentglobalTextSize = contentGlobalDialogs.getTextSize();
        int contentglobalGravity = contentGlobalDialogs.getGravity();


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

        layoutParams.setMargins(10, 10, 10, 10);
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < strtitleTextView.size(); i++) {
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1.0f
                    );

            LinearLayout containerLayout = new LinearLayout(mContext);
            containerLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView titleTxtView = new TextView(mContext);

            //TYPEFACE
            if(titleglobalTypeFace != null) {
                if(!titleglobalTypeFace.equals("")) {
                    titleTxtView.setTypeface(V2Utils.setFontInput(mContext, titleglobalTypeFace));
                } else {
                    titleTxtView.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                }
            } else {
                titleTxtView.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
            }


            //COLORS
            if (titleglobalColor > 0) {
                titleTxtView.setTextColor(ContextCompat.getColor(mContext, titleglobalColor));
            } else {
                titleTxtView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));
            }

            //TEXTSIZE
            if (titleglobalTextSize > 0) {
                titleTxtView.setTextSize(titleglobalTextSize);
            } else {
                titleTxtView.setTextSize(16);
            }

            //GRAVITY
            if (titleglobalGravity > 0) {
                titleTxtView.setGravity(titleglobalGravity);
            } else {
                titleTxtView.setGravity(Gravity.START);
            }

            titleTxtView.setPadding(10, 10, 10, 10);

            if (checkDecimal(strtitleTextView.get(i))) {
                titleTxtView.setText(CommonFunctions.currencyFormatter(strtitleTextView.get(i)));
            } else {
                titleTxtView.setText(strtitleTextView.get(i));
            }

            titleTxtView.setTag(String.valueOf("TITLE " + strtitleTextView.get(i)));
            titleTxtView.setId(i);
            titleTxtView.setLayoutParams(containerParams);
            containerLayout.addView(titleTxtView, containerParams);


            if (i < strcontentTextView.size()) {
                TextView contentTxtView = new TextView(mContext);

                //TYPEFACE
                if(contentglobalTypeFace != null) {
                    if(!contentglobalTypeFace.equals("")) {
                        contentTxtView.setTypeface(V2Utils.setFontInput(mContext, contentglobalTypeFace));
                    } else {
                        contentTxtView.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                    }
                } else {
                    contentTxtView.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                }

                //COLORS
                if (contentglobalColor > 0) {
                    contentTxtView.setTextColor(ContextCompat.getColor(mContext, contentglobalColor));
                } else {
                    contentTxtView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));
                }

                //TEXTSIZE
                if (contentglobalTextSize > 0) {
                    contentTxtView.setTextSize(contentglobalTextSize);
                } else {
                    contentTxtView.setTextSize(16);
                }

                //GRAVITY
                if (contentglobalGravity > 0) {
                    contentTxtView.setGravity(contentglobalGravity);
                } else {
                    contentTxtView.setGravity(Gravity.START);
                }

                contentTxtView.setPadding(10, 10, 10, 10);

                if (checkDecimal(strcontentTextView.get(i))) {
                    contentTxtView.setText(CommonFunctions.currencyFormatter(strcontentTextView.get(i)));
                } else {
                    contentTxtView.setText(strcontentTextView.get(i));
                }

                contentTxtView.setTag(String.valueOf("CONTENT " + strtitleTextView.get(i) + strcontentTextView.get(i)));
                contentTxtView.setId(i);
                contentTxtView.setLayoutParams(containerParams);
                containerLayout.addView(contentTxtView, containerParams);
            }

            linearLayout.addView(containerLayout, layoutParams);
        }

        return linearLayout;
    }

    //CREATE EDITTEXT
    private LinearLayout createCustomEditText(List<String> strArrEditTextDataType, int linearLayoutGroupOrientation,
                                              GlobalDialogsObject globalDialogsObject) {

        String globalTypeFace = globalDialogsObject.getTypeface();
        int globalColor = globalDialogsObject.getColorID();
        int globalTextSize = globalDialogsObject.getTextSize();
        int globalGravity = globalDialogsObject.getGravity();
        int globalBackground = globalDialogsObject.getBackground();
        int globalMaxLength = globalDialogsObject.getMaxLength();
        String globalHint = globalDialogsObject.getHint();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
        layoutParams.setMargins(10, 10, 10, 10);

        LinearLayout editTextContainer = new LinearLayout(mContext);
        editTextContainer.setOrientation(linearLayoutGroupOrientation);

        for (int i = 0; i < strArrEditTextDataType.size(); i++) {
            EditText editText = new EditText(mContext);
            editText.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));

            editText.setPadding(10, 10, 10, 10);

            //TYPEFACE
            if(globalTypeFace != null) {
                if(!globalTypeFace.equals("")) {
                    editText.setTypeface(V2Utils.setFontInput(mContext, globalTypeFace));
                } else {
                    editText.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                }
            } else {
                editText.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
            }

            //COLORS
            if (globalColor > 0) {
                editText.setTextColor(ContextCompat.getColor(mContext, globalColor));
            } else {
                editText.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));
            }

            //TEXTSIZE
            if (globalTextSize > 0) {
                editText.setTextSize(globalTextSize);
            } else {
                editText.setTextSize(16);
            }

            //GRAVITY
            if (globalGravity > 0) {
                editText.setGravity(globalGravity);
            } else {
                editText.setGravity(Gravity.CENTER);
            }

            //BACKGROUND
            if (globalBackground > 0) {
                editText.setBackgroundResource(globalBackground);
            } else {
                editText.setBackgroundResource(R.drawable.underline);
            }
            //HINT
            if (globalHint.equals("")) {
                editText.setHint("");

            } else {
                editText.setHint(globalHint);
            }

            editText.setSingleLine();

            editText.setTag("TAG " + i);
            editText.setId(i);

            String datatype = String.valueOf(strArrEditTextDataType.get(i));

            Logger.debug("checkhttp","MAX LENGTH" + globalMaxLength);

            String regex = "";

            if (datatype.toUpperCase().equals("MONEY") || datatype.toUpperCase().equals("NUMBER")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                regex = "[.0-9]+";

            } else if (datatype.toUpperCase().equals("VARCHAR")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                regex = "[a-zA-Z 0-9]+";

            } else if (datatype.toUpperCase().equals("CUSTOMVARCHAR")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                regex = "[a-zA-Z 0-9 , .]+";
            } else if (datatype.toUpperCase().equals("CUSTOMNUMBER")) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER);
                regex = "[0-9]+";
            }

            String finalRegex = regex;

            if (globalMaxLength > 0) {
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(globalMaxLength),
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence cs, int start,
                                                       int end, Spanned spanned, int dStart, int dEnd) {
                                // TODO Auto-generated method stub
                                if (cs.equals("")) { // for backspace
                                    return cs;
                                }
                                if (cs.toString().matches(finalRegex)) {
                                    return cs;
                                }
                                return "";
                            }
                        }
                });
            } else {
                editText.setFilters(new InputFilter[]{
                        new InputFilter() {
                            @Override
                            public CharSequence filter(CharSequence cs, int start,
                                                       int end, Spanned spanned, int dStart, int dEnd) {
                                // TODO Auto-generated method stub
                                if (cs.equals("")) { // for backspace
                                    return cs;
                                }
                                if (cs.toString().matches(finalRegex)) {
                                    return cs;
                                }
                                return "";
                            }
                        }
                });

            }




            editTextContainer.addView(editText, layoutParams);
        }

        return editTextContainer;
    }
    //CREATE RADIO
    private LinearLayout createCustomRadio(List<String> strArrRadio, int radioGroupOrientation,
                                           GlobalDialogsObject globalDialogsObject) {

        String globalTypeFace = globalDialogsObject.getTypeface();
        int globalColor = globalDialogsObject.getColorID();
        int globalTextSize = globalDialogsObject.getTextSize();
        int globalGravity = globalDialogsObject.getGravity();

        //LINEARLAYOUT CONTAINER FOR RAD GROUP
        LinearLayout radGroupContainer = new LinearLayout(mContext);
        radGroupContainer.setOrientation(LinearLayout.VERTICAL);

        //RADIO GROUP
        RadioGroup radGroup = new RadioGroup(mContext);
        radGroup.setOrientation(radioGroupOrientation);
        radGroup.setWeightSum(1.0f);

        for (int i = 0; i < strArrRadio.size(); i++) {
            LinearLayout.LayoutParams layoutParams;

            if (radioGroupOrientation == RadioGroup.VERTICAL) {
                layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );
            } else {
                layoutParams = new LinearLayout.LayoutParams
                        (LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1.0f
                        );
            }

            layoutParams.setMargins(5, 5, 5, 5);

            RadioButton radBtn = new RadioButton(mContext);
            radBtn.setLayoutParams(layoutParams);
            radBtn.setButtonDrawable(R.drawable.custom_radio_background);

            //TYPEFACE
            if(globalTypeFace != null) {
                if(!globalTypeFace.equals("")) {
                    radBtn.setTypeface(V2Utils.setFontInput(mContext, globalTypeFace));
                } else {
                    radBtn.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
                }
            } else {
                radBtn.setTypeface(V2Utils.setFontInput(mContext, V2Utils.ROBOTO_REGULAR));
            }

            //ALPHA
            radBtn.setAlpha(0.7f);

            //TEXTSIZE
            if (globalTextSize > 0) {
                radBtn.setTextSize(globalTextSize);
            } else {
                radBtn.setTextSize(16);
            }

            //COLORS
            if (globalColor > 0) {
                radBtn.setTextColor(ContextCompat.getColor(mContext, globalColor));
            } else {
                radBtn.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextGrey));
            }

            //GRAVITY
            if (globalGravity > 0) {
                radBtn.setGravity(globalGravity);
            } else {
                radBtn.setGravity(Gravity.START);
            }

            radBtn.setPadding(5, 5, 5, 5);
            radBtn.setText(strArrRadio.get(i));
            radBtn.setTag(String.valueOf(strArrRadio.get(i)));
            radBtn.setId(i);

            radGroup.addView(radBtn);
        }

        radGroupContainer.addView(radGroup);

        return radGroupContainer;
    }
    //CREATE SPINNER
    private LinearLayout createCustomMultiSpinner(ArrayList<String> strArrSpinner, int linearLayoutOrientation,
                                                  GlobalDialogsObject globalDialogsObject) {

        int globalColor = globalDialogsObject.getColorID();
        int globalTextSize = globalDialogsObject.getTextSize();
        int globalGravity = globalDialogsObject.getGravity();
        int globalBackground = globalDialogsObject.getBackground();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1.0f
                );

        layoutParams.setMargins(10, 10, 10, 10);

        LinearLayout spinnerContainer = new LinearLayout(mContext);
        spinnerContainer.setOrientation(linearLayoutOrientation);

        Spinner spinner = new Spinner(mContext);
        spinner.setBackground(ContextCompat.getDrawable(mContext, R.drawable.underline));
        spinner.setPadding(5, 5, 5, 5);

        for (int i = 0; i < strArrSpinner.size(); i++) {
            spinner.setTag("TAG " + i);
            spinner.setId(i);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (mContext, android.R.layout.simple_spinner_dropdown_item, strArrSpinner);

            spinnerArrayAdapter.setDropDownViewResource(R.layout.dialog_global_messages_spinner_arrow);
            spinner.setAdapter(spinnerArrayAdapter);
            spinner.setLayoutParams(layoutParams);
        }

        spinnerContainer.addView(spinner, layoutParams);

        return spinnerContainer;
    }

    //------------------------OTHER IMPORTANT NOTES---------------------

    //--------------IF YOU WANT TO SET AN ONCLICK ON THE TEXTVIEW----------
    //    int count = gkstore_custom_layout.getChildCount();
    //        for(int i=0; i<count; i++) {
    //        View view = gkstore_custom_layout.getChildAt(i);
    //        view.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                String tag = String.valueOf(view.getTag());
    //                if (tag.equals("YES")) {
    //                    Logger.debug("checkhttp","YES");
    //                } else if (tag.equals("Hello")) {
    //                    Logger.debug("checkhttp","Hello");
    //                }
    //            }
    //        });
    //    }

    //--------------IF YOU WANT TO CHANGE STYLE OF THE VIEW LOCALLY----------
    //    int count = linearLayout.getChildCount();
    //        for(int i = 0; i<count; i++) {
    //        View view = linearLayout.getChildAt(i);
    //        if (view instanceof TextView) {
    //            TextView textView = (TextView) view;
    //            textView.setTextColor(ContextCompat.getColor(getViewContext(), R.color.color_error_red));
    //            textView.setGravity(Gravity.START);
    //            textView.setTextSize(24f);
    //        }
    //    }


    //----------IF YOU WANT TO GET EDITEXT TEXT--------
    //NOTE: ON THIS CASE, I only need one editext and one string.

    //String newgroupname = '';
    //final int count = editTextContainer.getChildCount();
    //                for (int i = 0; i < count; i++) {
    //    View editView = editTextContainer.getChildAt(i);
    //    if (editView instanceof EditText) {
    //        EditText editItem = (EditText) editView;
    //        String taggroup = editItem.getTag().toString();
    //        if(taggroup.equals("TAG 0")) {
    //            newgroupname = editItem.getText().toString();
    //        }
    //    }
    //}

    //-----------------------------RADIOGROUP---------------------------
//      RadioGroup radGroup = new RadioGroup(mContext);
//
//        int count = radGroupContainer.getChildCount();
//        for (int i = 0; i < count; i++) {
//            View view = radGroupContainer.getChildAt(i);
//            if (view instanceof RadioGroup) {
//                radGroup = (RadioGroup) view;
//            }
//        }
    //-----------------------GROUP LISTENER-------------------------
    //    radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    //        @Override
    //        public void onCheckedChanged(RadioGroup group, int checkedId) {
    //            String strtext = "You selected: ";
    //            strtext += " " + checkedId;
    //
    //            if(checkedId == 0) {
    //                showToast(strtext + "PICK UP", GlobalToastEnum.NOTICE);
    //            }
    //            else if(checkedId == 1) {
    //                showToast(strtext + "DELIVERY", GlobalToastEnum.NOTICE);
    //            }
    //        }
    //     });

    //--------------IF YOU WANT TO SET A LISTENER TO EACH RAD BUTTON INSTEAD OF USING RAD GROUP---------------
    //  int count = radgroup.getChildCount();
    //        for (int i=0;i<count;i++) {
    //            View radBtnView  = radgroup.getChildAt(i);
    //            radBtnView.setOnClickListener(new View.OnClickListener() {
    //                @Override
    //                public void onClick(View view) {
    //                    String tag = String.valueOf(view.getTag());
    //                    if (tag.equals("Pick Up")) {
    //                        showToast("NI PICK UP KO", GlobalToastEnum.NOTICE);
    //                    } else if (tag.equals("For Delivery")) {
    //                        showToast("NI DELIVER KO", GlobalToastEnum.NOTICE);
    //                    }
    //                }
    //            });
    //        }


    //-----------------------------CUSTOM REDIRECTION--------------------

    //RETURNS TO MAIN ACTIVITY
    public void proceedtoMainActivity() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    //PROCEED TO COOP HOME
    public void proceedtoCoopHome() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        CoopAssistantHomeActivity.start(mContext, 0, null);
    }

    // RETURNS TO BEFORE PAYMENTS (DRAG AND DROP VOUCHER)
    public void returntoBeforePayments() {
        final Handler handlerstatus = new Handler();
        handlerstatus.postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
                CommonVariables.VOUCHERISFIRSTLOAD = true;
                Payment.paymentfinishActivity.finish();
                finish();
            }
        }, 1000);
    }

    //RETURNS TO TRANSACTION LOGS OF BILLSPAYMENT
    public void returntoLogsBillsPaymentFragment() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        ViewOthersTransactionsActivity.start(mContext, 5);
    }

    //RETURNS TO TRANSACTION LOGS OF PREPAID FRAGMENT
    public void returntoLogsPrepaidFragment() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        ViewOthersTransactionsActivity.start(mContext, 4);
    }

    //RETURNS TO TRANSACTION LOGS OF SMART WALLET FRAGMENT
    public void returntoLogsRetailerFragment() {
        CommonVariables.PROCESSNOTIFICATIONINDICATOR = "";
        CommonVariables.VOUCHERISFIRSTLOAD = true;
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
        ViewOthersTransactionsActivity.start(mContext, 6);
    }

    //RETURS TO SKYCABLE HOMEFRAGMENT OR MAINACTIVITY
    public void returntoSkyCableHomeFragment() {
        if (strskybtnaction.equals("GOTOHISTORY")) {
            proceedtoMainActivity();
        } else {
            SkyCableActivity.skycablefinishActivity.finish();
            finish();
            SkyCableActivity.start(mContext, 1, null);
        }
    }

    //RETURNS TO SKYCABLE PAYPERVIEWHISTORY
    public void returntoSkycablePayPerViewHistoryFragment() {
        SkyCableActivity.skycablefinishActivity.finish();
        finish();
        Intent intent = new Intent(mContext, SkyCableActivity.class);
        intent.putExtra("index", 8);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    //REDIRECTS TO GKSTOREHISTORY (ACTIVITY)
    public void proceedtoHistoryActivity() {
        String merchantid = PreferenceUtils.getStringPreference(mContext, "gkstmerchantid");
        String storeid = PreferenceUtils.getStringPreference(mContext, "gkststoreid");
        String servicecode = PreferenceUtils.getStringPreference(mContext, "gkstserviecode");

        Intent intent = new Intent(mContext, GkStoreHistoryActivity.class);
        intent.putExtra("GKSTOREMERCHANTID", merchantid);
        intent.putExtra("GKSTOREID", storeid);
        intent.putExtra("GKSTORESERVICECODE", servicecode);
        mContext.startActivity(intent);
    }

    //RETURNS TO MAIN ACTIVITY
    public void proceedToAddingOfBanks() {

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    //-------------OTHERS---------------
    public void dismiss() {
        if (mGlobalDialog != null && mGlobalDialog.isShowing()) {
            mGlobalDialog.dismiss();
            mGlobalDialog = null;
        }
    }

    public void finish() {
        ((BaseActivity) mContext).finish();
    }

    public void dismissProgressDialog() {
        ((BaseActivity) mContext).hideProgressDialog();
        ((BaseActivity) mContext).setUpProgressLoaderDismissDialog();
    }

    //BARCODE
    private Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {

        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    //CHECK IF STRING MATCHES DECIMAL NUMBER
    private boolean checkDecimal(String message) {
        boolean isDecimal = false;
        if (message.matches("\\d*\\.?\\d+")) {
            isDecimal = true;
        }
        return isDecimal;
    }

}


