package com.goodkredit.myapplication.activities.voting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;
import com.goodkredit.myapplication.enums.GlobalDialogsEditText;
import com.goodkredit.myapplication.enums.GlobalToastEnum;
import com.goodkredit.myapplication.model.globaldialogs.GlobalDialogsObject;
import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.goodkredit.myapplication.utilities.PercentageCropImageView;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import hk.ids.gws.android.sclick.SClick;

public class VotesParticipantDetailsActivity extends BaseActivity implements View.OnClickListener {

    private VotesParticipants participants = null;

//    private SliderLayout imageslider;
//    private PagerIndicator pagerindicator;

    private TextView txv_candidate_no;
    private TextView txv_candidate_name;
    private TextView txv_candidate_place;
    private TextView txv_votes;
    private FrameLayout btn_vote;
    private ImageView btn_close;
//    private ImageView img_container;

    private VotesPostEvent votesPostObject = null;

    //VOTES
    private String eventid = "";
    private String eventname = "";
    private String participantid = "";
    private int participantnumber = 0;
    private String participantname = "";
    private String pricepervote = "";
    private String votemaxtype = "";
    private String maxvote = "";
    private double totalamount = 0.0;

    //VOTES (PAYMENT OPTION)
    private EditText edtNumberofVotes;
    private String edtNumberofVotesString = "";

    private TextView txvAmounttoPay;
    private boolean checkIfPaymenthasPending = false;

    //CAROUSEL VIEW
    private CarouselView carouselView;
    private String xmlpictures;
    private String count;
    private ArrayList<String> multiimage = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    public static VotesParticipantDetailsActivity votesParticipantDetailsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_votes_participant_details);

            votesParticipantDetailsActivity = this;

            init();
            initData();

        } catch (Exception e) {
            e.printStackTrace();
            e.getLocalizedMessage();
        }
    }

    private void init() {

        txv_candidate_no = (TextView) findViewById(R.id.txv_candidate_no);
        txv_candidate_name = (TextView) findViewById(R.id.txv_candidate_name);
        txv_candidate_place = (TextView) findViewById(R.id.txv_candidate_place);
        txv_votes = (TextView) findViewById(R.id.txv_votes);
        btn_vote = (FrameLayout) findViewById(R.id.btn_vote);
        btn_close = (ImageView) findViewById(R.id.btn_close);
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        votesPostObject = getIntent().getParcelableExtra("VotesPostObject");

        getVotesObject();

        btn_vote.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }

    private void initData() {
        try{
            participants = getIntent().getParcelableExtra(VotesParticipants.KEY_VOTESPARTICIPANTS);

            switch (participants.getMiddleName()) {
                case "":
                    txv_candidate_name.setText(CommonFunctions.replaceEscapeData(participants.getFirstName()
                            + " " + participants.getLastName()));
                    break;
                case ".":
                    txv_candidate_name.setText(CommonFunctions.replaceEscapeData(participants.getFirstName()
                            + " " + participants.getLastName()));
                    break;
                default:
                    txv_candidate_name.setText(CommonFunctions.replaceEscapeData(participants.getFirstName() + " "
                            + participants.getMiddleName() + " " + participants.getLastName()));
                    break;
            }

            txv_candidate_no.setText("Candidate #".concat(String.valueOf(participants.getParticipantNumber())));
            txv_candidate_place.setText(CommonFunctions.replaceEscapeData(participants.getAddress()));
            txv_votes.setText(String.valueOf(participants.getCurrentNoVote()));

            xmlpictures = participants.getImageXML();
            count = CommonFunctions.parseXML(xmlpictures, "count");

            carouselView.setPageCount(Integer.parseInt(count));
//            carouselView.setImageListener(imageListener);
            carouselView.setViewListener(viewListener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            if(!count.equals("") && !count.equals(".") && !count.equals("NONE")){

                if(count.equals("1")){

                    String singleimageurl = CommonFunctions.parseXML(xmlpictures, "0");

                    Glide.with(getViewContext())
                            .load(singleimageurl)
                            .centerCrop()
                            .into(imageView);
                } else{

                    for(int i = 0; i<Integer.parseInt(count); i++){
                        String multiimageurl = CommonFunctions.parseXML(xmlpictures, String.valueOf(i));
                        multiimage.add(multiimageurl);

                    }

                    Glide.with(getViewContext())
                            .load(multiimage.get(position))
                            .centerCrop()
                            .into(imageView);

                }
            }
        }
    };

    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customview = getLayoutInflater().inflate(R.layout.custom_carousel_image, null);

            PercentageCropImageView img_custom_carousel = (PercentageCropImageView) customview.findViewById(R.id.img_custom_carousel);

            if(!count.equals("") && !count.equals(".") && !count.equals("NONE")){

                if(count.equals("1")){

                    String singleimageurl = CommonFunctions.parseXML(xmlpictures, "0");

                    Glide.with(getViewContext())
                            .load(singleimageurl)
                            .into(img_custom_carousel);

                    img_custom_carousel.setCropYCenterOffsetPct(0.1f);
                } else{

                    for(int i = 0; i<Integer.parseInt(count); i++){
                        String multiimageurl = CommonFunctions.parseXML(xmlpictures, String.valueOf(i));
                        multiimage.add(multiimageurl);

                    }

                    Glide.with(getViewContext())
                            .load(multiimage.get(position))
                            .into(img_custom_carousel);

                    img_custom_carousel.setCropYCenterOffsetPct(0.1f);
                }
            }

            return customview;
        }
    };

    private void getVotesObject() {
        eventid = votesPostObject.getEventID();
        eventname = votesPostObject.getEventName();

        String xmldetails = votesPostObject.getXMLDetails();
        pricepervote = CommonFunctions.parseXML(xmldetails, "pricepervote");
        votemaxtype = CommonFunctions.parseXML(xmldetails, "votemaxtype");
        maxvote = CommonFunctions.parseXML(xmldetails, "maxvote");
    }

    private void showVotesToPayValue() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("", "",
                "Proceed", "", ButtonTypeEnum.SINGLE,
                false, true, DialogTypeEnum.CUSTOMCONTENT);

        //VOTES LABEL
        LinearLayout txtVotesMainLblContainer = dialog.getTextViewMessageContainer();
        txtVotesMainLblContainer.setPadding(15, 15, 15, 0);

        List<String> votesLblList = new ArrayList<>();
        String voteslbl = "1 vote = " + pricepervote + " Php";
        votesLblList.add(voteslbl);

        dialog.setContentCustomMultiTextView(votesLblList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_045C84, 16, Gravity.TOP | Gravity.CENTER));

        //NUMBER OF VOTES
        //TEXTVIEW
        LinearLayout txtNumberOfVotesContainer = dialog.getTextViewMessageContainer();
        txtNumberOfVotesContainer.setPadding(35, 20, 35, 0);

        List<String> numberOfVotesList = new ArrayList<>();
        numberOfVotesList.add("Number of Votes: ");

        dialog.setContentCustomMultiTextView(numberOfVotesList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));

        //EDITTEXT
        LinearLayout editTextMainContainer = dialog.getEditTextMessageContainer();
        editTextMainContainer.setPadding(35, 0, 35, 20);

        List<String> editTextDataType = new ArrayList<>();
        editTextDataType.add(String.valueOf(GlobalDialogsEditText.CUSTOMNUMBER));

        LinearLayout editTextContainer = dialog.setContentCustomMultiEditText(editTextDataType,
                LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.colorTextGrey, 16, Gravity.TOP | Gravity.START,
                        R.drawable.border, 19, ""));

        final int count = editTextContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View editView = editTextContainer.getChildAt(i);
            if (editView instanceof EditText) {
                EditText editItem = (EditText) editView;
                editItem.setPadding(20, 20, 20, 20);
                String taggroup = editItem.getTag().toString();
                if (taggroup.equals("TAG 0")) {
                    edtNumberofVotes = editItem;
                }
            }
        }

        //AMOUNT TO PAY
        //TEXTVIEW
        LinearLayout txtAmountToPayLblContainer = dialog.getTextViewMessageContainer();
        txtAmountToPayLblContainer.setPadding(35, 20, 35, 0);

        List<String> amountToPayLblList = new ArrayList<>();
        amountToPayLblList.add("Amount to Pay: ");

        LinearLayout amountToPayLblViewContainer = dialog.setContentCustomMultiTextView(amountToPayLblList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));

        //TEXTVIEW
        LinearLayout txtAmountToPayContainer = dialog.getTextViewMessageContainer();
        txtAmountToPayContainer.setPadding(35, 20, 35, 0);

        List<String> amountToPayList = new ArrayList<>();
        amountToPayList.add("");

        LinearLayout amounttoPayContainer = dialog.setContentCustomMultiTextView(amountToPayList, LinearLayout.VERTICAL,
                new GlobalDialogsObject(R.color.color_878787, 16, Gravity.TOP | Gravity.START));


        final int amountopaycount = amounttoPayContainer.getChildCount();
        for (int i = 0; i < amountopaycount; i++) {
            View textView = amounttoPayContainer.getChildAt(i);
            if (textView instanceof TextView) {
                TextView textViewItem = (TextView) textView;
                textViewItem.setPadding(20, 20, 20, 20);
                textViewItem.setBackgroundResource(R.drawable.border_disabled);
                String taggroup = textViewItem.getTag().toString();
                if (taggroup.equals("")) {
                    txvAmounttoPay = textViewItem;
                    edtNumberofVotes.addTextChangedListener(numberofVotesTextWatcher);
                }
            }
        }

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                hideProgressDialog();

            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkamount = String.valueOf(edtNumberofVotes.getText());
                if (checkamount.trim().equals("") || checkamount.trim().equals(".")) {
                    showToast("Please input a value.", GlobalToastEnum.WARNING);
                } else {
                    dialog.dismiss();
                    Intent intent = new Intent(getViewContext(), VotesPaymentOptionActivity.class);
                    intent.putExtra(VotesParticipants.KEY_VOTESPARTICIPANTS, participants);
                    intent.putExtra("VotesPostObject", votesPostObject);
                    intent.putExtra("VoteAmount", String.valueOf(totalamount));
                    getViewContext().startActivity(intent);
                }
            }
        });
    }

    private TextWatcher numberofVotesTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edtNumberofVotesString = s.toString();
            if(txvAmounttoPay != null) {
                if(!edtNumberofVotesString.trim().equals("") ) {
                    Double numberofvote = Double.parseDouble(edtNumberofVotesString);
                    Double price = Double.parseDouble(pricepervote);
                    totalamount =  numberofvote * price;
                    txvAmounttoPay.setText(CommonFunctions.currencyFormatter(String.valueOf(totalamount)));
                } else {
                    txvAmounttoPay.setText("");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_close: {
                finish();
                break;
            }

            case R.id.btn_vote: {
                if (!SClick.check(SClick.BUTTON_CLICK, 2000)) return;
                //showVotesToPayValue();
                Intent intent = new Intent(getViewContext(), VotesPaymentOptionActivity.class);
                intent.putExtra(VotesParticipants.KEY_VOTESPARTICIPANTS, participants);
                intent.putExtra("VotesPostObject", votesPostObject);
                getViewContext().startActivity(intent);
                break;
            }
        }
    }
}
