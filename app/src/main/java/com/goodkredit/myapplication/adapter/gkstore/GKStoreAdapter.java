package com.goodkredit.myapplication.adapter.gkstore;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.view.CustomNestedScrollView;
import com.goodkredit.myapplication.activities.gkstore.GKStoreDetailsActivity;
import com.goodkredit.myapplication.activities.gkstore.GKStoreFullScreenActivity;
import com.goodkredit.myapplication.utilities.InputFilterMinMax_GKStore;
import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by GoodApps on 04/12/2017.
 */

public class GKStoreAdapter extends RecyclerView.Adapter<GKStoreAdapter.MyViewHolder> {
    private Context mContext;
    private List<GKStoreProducts> gkStoreProducts = new ArrayList<>();
    private List<GKStoreProducts> passedGKStoreProducts = new ArrayList<>();
    private MaterialDialog materialdialog = null;
    private Dialog quantitydialog = null;
    private NumberPicker np;
    private int getlastposition = 0;
    private LinearLayoutManager mLayoutManager;
    private ViewGroup getParent;
    private DatabaseHandler mdb;
    GKStoreProducts getGKStore;
    GKStoreProducts selectedProduct;

    private MyViewHolder viewHolders;

    //for dialog components
    LinearLayout dialogProductlogocontainerfull;
    RelativeLayout dialogLogoproductcontainerfull;
    SliderLayout dialogProductmultiimagesslider;
    PagerIndicator dialogProdDetail_custom_indicator;
    ImageView dialogProductLogoPicfull;
    ImageView dialogProductdiscountimagefull;
    TextView dialogProductshowmoreimage;
    RelativeLayout dialogDetailscontainerfull;
    TextView dialogProductnamefull;
    TextView dialogActualpricefull;
    TextView dialogProductdescfull;
    LinearLayout dialogItemcontainer;
    LinearLayout dialogQuantitycontainer;
    LinearLayout dialogOrderdetailsarrowcontainer;
    TextView dialogStorequantitynumberpickdialog;
    TextView dialogAmount;
    LinearLayout dialogAmountcontainer;
    LinearLayout dialogGkstoreordercontainer;
    TextView dialogGkstoreordertxt;
    ImageView product_details_layout_closeImg;
    boolean dialogIselected = false;
    private String productNameToShow = "";
    boolean isShowed = false;


    AlertDialog alertDialog = null;
    AlertDialog.Builder dialogBuilder = null;


    public GKStoreAdapter(Context context, List<GKStoreProducts> arrayList) {
        mContext = context;
        mdb = new DatabaseHandler(mContext);
        gkStoreProducts = arrayList;
    }

    private Context getContext() {
        return mContext;
    }

    public void updateData(List<GKStoreProducts> arraylist) {
        gkStoreProducts.clear();
        gkStoreProducts = arraylist;
        notifyDataSetChanged();

        if (passedGKStoreProducts.size() > 0) {
            //V1
            //productOrders();

            //V1 - ALTERNATIVE
            checkPassedOrders(0);

            ((GKStoreDetailsActivity) mContext).scrollonTop();
        }
    }
    //FOR LOOP
    private void productOrders() {
        for (int i = 0; i < passedGKStoreProducts.size(); i++) {
            GKStoreProducts passedgkstoreref = passedGKStoreProducts.get(i);
            String checkproductname = passedgkstoreref.getProductName();

            for (int y = 0; y < gkStoreProducts.size(); y++) {
                GKStoreProducts gkstoreref = gkStoreProducts.get(y);
                String gkstoreproductname = gkstoreref.getProductName();

                if (checkproductname.equals(gkstoreproductname)) {
                    gkStoreProducts.remove(y);
                    gkStoreProducts.add(i, gkstoreref);
                    notifyItemMoved(y, i);
                    break;
                }
            }
        }
    }

    //RECURSIVE FUNCTION (KAY GA KAPOY KOS AKONG KINABUHI :D)
    private void checkPassedOrders(int passedcount) {
        if (passedcount < passedGKStoreProducts.size()) {
            GKStoreProducts passedgkstoreref = passedGKStoreProducts.get(passedcount);
            String checkproductname = passedgkstoreref.getProductName();
            checkProductOrders(passedcount, 0, checkproductname);
        }
    }

    private void checkProductOrders(int passedcount, int count, String checkproductname) {
        if (count < gkStoreProducts.size()) {
            GKStoreProducts gkstoreref = gkStoreProducts.get(count);
            String gkstoreproductname = gkstoreref.getProductName();
            if (checkproductname.equals(gkstoreproductname)) {
                gkStoreProducts.remove(count);
                gkStoreProducts.add(passedcount, gkstoreref);
                notifyItemMoved(count, passedcount);
                checkPassedOrders(passedcount + 1);
            } else {
                checkProductOrders(passedcount, count + 1, checkproductname);
            }
        }
    }

    public void clear() {
        gkStoreProducts.clear();
        notifyDataSetChanged();
    }

    public void fullclear() {
        gkStoreProducts.clear();
        passedGKStoreProducts.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return gkStoreProducts.size();
    }

    //Order
    private View.OnClickListener orderlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final GKStoreAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();
                makeGKStoreOrder(holder);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //make order
    private void makeGKStoreOrder(MyViewHolder holder) {
        if (!holder.iselected) {
            addGKStoreOrder(holder);
        } else {
            removeGKStoreOrder(holder);
        }
    }

    //make order in dialog
    private void makeGKStoreOrderDialog(MyViewHolder holder) {
        if (!dialogIselected) {
            addGKStoreOrderDialog(holder);
        } else {
            removeGKStoreOrderDialog(holder);
        }
    }

    //add and remove order with dialog
    private void addGKStoreOrderDialog(MyViewHolder holder) {

        GKStoreProducts gkstoreref = selectedProduct;
        String storequantity = String.valueOf(dialogStorequantitynumberpickdialog.getText());
        String amount = String.valueOf(dialogAmount.getText());

        if (amount.contains(",")) {
            amount = amount.replace(",", "");
        }

        dialogIselected = true;
        holder.iselected = dialogIselected;

        dialogGkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorred));
        dialogGkstoreordertxt.setText("REMOVE");

        holder.gkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorred));
        holder.gkstoreordertxt.setText("REMOVE");

        int checkquantity = Integer.parseInt(storequantity);


        //ANIMATION
        animationforGKStoreOrder(holder);

        String productid = gkstoreref.getProductID();
        String productname = gkstoreref.getProductName();
        double actualprice = gkstoreref.getActualPrice();
        int isSelected = gkstoreref.getIsSelected();

        String getXMLPics = gkstoreref.getProductImageURL();
        String productpic = "";
        String count = parseXML(getXMLPics, "count");
        if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
            productpic = parseXML(getXMLPics, String.valueOf(0));
        }

        //Checks if quantity is 0 or not
        if (checkquantity <= 0) {
            dialogStorequantitynumberpickdialog.setText("1");
            holder.storequantitynumberpickdialog.setText("1");

            dialogAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getActualPrice())));
            holder.amount.setText(CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getActualPrice())));

            String quantitystrpassed = String.valueOf(dialogStorequantitynumberpickdialog.getText());
            double qty = Double.parseDouble(quantitystrpassed);
            int quantity = (int) qty;

            mdb.updateGKStoreisSelected(mdb, 1, productid);

            passedGKStoreProducts.add(new GKStoreProducts(productid, productname, quantity, actualprice, productpic, isSelected));
            ((GKStoreDetailsActivity) mContext).orderDetails(passedGKStoreProducts);

        } else {
            int quantity = Integer.parseInt(storequantity);

            mdb.updateGKStoreisSelected(mdb, 1, productid);

            passedGKStoreProducts.add(new GKStoreProducts(productid, productname, quantity, actualprice, productpic, isSelected));
            ((GKStoreDetailsActivity) mContext).orderDetails(passedGKStoreProducts);
        }
        //SCROLLING
        checkForGKStoreScrolling(holder);

        //Moving the added item to the very top of the list.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GKStoreProducts gkstoreswitchplace = gkStoreProducts.get(holder.getAdapterPosition());
                gkStoreProducts.remove(holder.getAdapterPosition());
                gkStoreProducts.add(0, gkstoreswitchplace);
                notifyItemMoved(holder.getAdapterPosition(), 0);
                ((GKStoreDetailsActivity) mContext).scrolltoPositionAdapter(0, holder.item_gk_store, holder.checkstatus);
            }
        }, 500);

        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
            isShowed = false;
        }

    }

    private void removeGKStoreOrderDialog(MyViewHolder holder) {


        GKStoreProducts gkstoreref = selectedProduct;
        String storequantity = String.valueOf(dialogStorequantitynumberpickdialog.getText());
        String amount = String.valueOf(dialogAmount.getText());

        dialogIselected = false;
        holder.iselected = dialogIselected;

        dialogGkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
        dialogGkstoreordertxt.setText("MAKE ORDER");

        holder.gkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
        holder.gkstoreordertxt.setText("MAKE ORDER");

        if (amount.contains(",")) {
            amount = amount.replace(",", "");
        }

        //ANIMATION
        animationforGKStoreOrder(holder);

        //Setting the values
        int quantity = 0;
        double price = gkstoreref.getActualPrice();
        String storequantstr = String.valueOf(quantity);


        dialogStorequantitynumberpickdialog.setText(storequantstr);
        holder.storequantitynumberpickdialog.setText(storequantstr);

        dialogAmount.setText("0.00");
        holder.amount.setText("0.00");

        String productid = gkstoreref.getProductID();

        //Set the List to selected
        mdb.updateGKStoreisSelected(mdb, 0, productid);

        //Adding the item to the bottom of the list.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = gkStoreProducts.size();
                GKStoreProducts gkstoreswitchplace = gkStoreProducts.get(holder.getAdapterPosition());
                gkStoreProducts.remove(holder.getAdapterPosition());
                gkStoreProducts.add(size - 1, gkstoreswitchplace);
                notifyItemMoved(holder.getAdapterPosition(), size - 1);
            }
        }, 500);

        //Remove the item from the selected list
        for (GKStoreProducts passedGkStoretest : passedGKStoreProducts) {
            if (passedGkStoretest.getProductID().equals(productid)) {
                passedGKStoreProducts.remove(passedGkStoretest);
                ((GKStoreDetailsActivity) mContext).orderDetails(passedGKStoreProducts);
                break;
            }
        }

        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
            isShowed = false;
        }

    }

    private void addGKStoreOrder(MyViewHolder holder) {
        GKStoreProducts gkstoreref = gkStoreProducts.get(holder.getAdapterPosition());
        String storequantity = String.valueOf(holder.storequantitynumberpickdialog.getText());
        String amount = String.valueOf(holder.amount.getText());

        if (amount.contains(",")) {
            amount = amount.replace(",", "");
        }

        holder.iselected = true;
        holder.gkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorred));
        holder.gkstoreordertxt.setText("REMOVE");
        int checkquantity = Integer.parseInt(storequantity);

        //ANIMATION
        animationforGKStoreOrder(holder);

        String productid = gkstoreref.getProductID();
        String productname = gkstoreref.getProductName();
        double actualprice = gkstoreref.getActualPrice();
        int isSelected = gkstoreref.getIsSelected();

        String getXMLPics = gkstoreref.getProductImageURL();
        String productpic = "";
        String count = parseXML(getXMLPics, "count");
        if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
            productpic = parseXML(getXMLPics, String.valueOf(0));
        }

        //Checks if quantity is 0 or not
        if (checkquantity <= 0) {
            holder.storequantitynumberpickdialog.setText("1");
            holder.amount.setText(CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getActualPrice())));

            String quantitystrpassed = String.valueOf(holder.storequantitynumberpickdialog.getText());
            double qty = Double.parseDouble(quantitystrpassed);
            int quantity = (int) qty;

            mdb.updateGKStoreisSelected(mdb, 1, productid);

            passedGKStoreProducts.add(new GKStoreProducts(productid, productname, quantity, actualprice, productpic, isSelected));
            ((GKStoreDetailsActivity) mContext).orderDetails(passedGKStoreProducts);

        } else {
            int quantity = Integer.parseInt(storequantity);

            mdb.updateGKStoreisSelected(mdb, 1, productid);

            passedGKStoreProducts.add(new GKStoreProducts(productid, productname, quantity, actualprice, productpic, isSelected));
            ((GKStoreDetailsActivity) mContext).orderDetails(passedGKStoreProducts);
        }

        //SCROLLING
        checkForGKStoreScrolling(holder);

        //Moving the added item to the very top of the list.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GKStoreProducts gkstoreswitchplace = gkStoreProducts.get(holder.getAdapterPosition());
                gkStoreProducts.remove(holder.getAdapterPosition());
                gkStoreProducts.add(0, gkstoreswitchplace);
                notifyItemMoved(holder.getAdapterPosition(), 0);
                ((GKStoreDetailsActivity) mContext).scrolltoPositionAdapter(0, holder.item_gk_store, holder.checkstatus);
            }
        }, 500);
    }

    private void removeGKStoreOrder(MyViewHolder holder) {
        GKStoreProducts gkstoreref = gkStoreProducts.get(holder.getAdapterPosition());
        String storequantity = String.valueOf(holder.storequantitynumberpickdialog.getText());
        String amount = String.valueOf(holder.amount.getText());

        holder.iselected = false;
        holder.gkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
        holder.gkstoreordertxt.setText("MAKE ORDER");

        if (amount.contains(",")) {
            amount = amount.replace(",", "");
        }

        //ANIMATION
        animationforGKStoreOrder(holder);

        //Setting the values
        int quantity = 0;
        double price = gkstoreref.getActualPrice();
        String storequantstr = String.valueOf(quantity);
        holder.storequantitynumberpickdialog.setText(storequantstr);
        holder.amount.setText("0.00");
        String productid = gkstoreref.getProductID();

        //Set the List to selected
        mdb.updateGKStoreisSelected(mdb, 0, productid);

        //Adding the item to the bottom of the list.
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int size = gkStoreProducts.size();
                GKStoreProducts gkstoreswitchplace = gkStoreProducts.get(holder.getAdapterPosition());
                gkStoreProducts.remove(holder.getAdapterPosition());
                gkStoreProducts.add(size - 1, gkstoreswitchplace);
                notifyItemMoved(holder.getAdapterPosition(), size - 1);
            }
        }, 500);

        //Remove the item from the selected list
        for (GKStoreProducts passedGkStoretest : passedGKStoreProducts) {
            if (passedGkStoretest.getProductID().equals(productid)) {
                passedGKStoreProducts.remove(passedGkStoretest);
                ((GKStoreDetailsActivity) mContext).orderDetails(passedGKStoreProducts);
                break;
            }
        }
    }

    private void animationforGKStoreOrder(MyViewHolder holder) {
        //Goes to Original State before adding.
        if (holder.productlogocontainerfull.getVisibility() == View.VISIBLE) {
            holder.productlogocontainerfull.setVisibility(View.GONE);
            final Animation fade_in = AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_in);
            holder.productlogocontainer.startAnimation(fade_in);
            fade_in.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    holder.productlogocontainerfull.setEnabled(false);
                    holder.productlogocontainer.setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.productlogocontainerfull.setEnabled(true);
                    holder.productlogocontainer.setEnabled(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            holder.productlogocontainer.setVisibility(View.VISIBLE);
        }
    }

    private void checkForGKStoreScrolling(MyViewHolder holder) {
        //FOR SCROLLING....
        final int size = passedGKStoreProducts.size();
        CustomNestedScrollView nestedScrollView = ((GKStoreDetailsActivity) mContext).scrollmaincontainer;
        nestedScrollView.setEnableScrolling(false);
        CustomNestedScrollView main = ((GKStoreDetailsActivity) mContext).scrollmaincontainer;
        final Rect scrollBounds = new Rect();
        main.getHitRect(scrollBounds);
        main.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (holder.item_gk_store != null) {
                    if (holder.item_gk_store.getLocalVisibleRect(scrollBounds)) {
                        if (!holder.item_gk_store.getLocalVisibleRect(scrollBounds)
                                || scrollBounds.height() < holder.item_gk_store.getHeight()) {
                            holder.checkstatus = true;
                        } else {
                            holder.checkstatus = true;
                        }
                    } else {
                        holder.checkstatus = false;
                    }
                }
            }
        });

        main.setOnScrollChangeListener(((GKStoreDetailsActivity) mContext).scrollOnChangedListener);
    }

    //Logo
    private class ImageViewPagerEX implements ViewPagerEx.OnPageChangeListener {

        private MyViewHolder myViewHolder;
        private int slidercount = 0;

        private ImageViewPagerEX(MyViewHolder myViewHolder, int slidercount) {
            this.myViewHolder = myViewHolder;
            this.slidercount = slidercount;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //Number Picker
    private class NumberPickerEditTextWatcher implements TextWatcher {
        private EditText numberpicker_input;
        private NumberPicker np;


        private NumberPickerEditTextWatcher(EditText numberpicker_input, NumberPicker np) {
            this.numberpicker_input = numberpicker_input;
            this.np = np;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0) {
                if (numberpicker_input != null && np != null) {
                    String getValue = String.valueOf(s.toString());
                    int quantity = Integer.parseInt(getValue);
                    if (quantity >= 1 && quantity <= 100) {
                        np.setValue(quantity);
                    }
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                if (numberpicker_input != null && np != null) {
                    String getValue = String.valueOf(s.toString());
                    int quantity = Integer.parseInt(getValue);
                    numberpicker_input.setSelectAllOnFocus(false);
                    if (quantity < 1) {
                        numberpicker_input.setText("1");
                        np.setValue(1);
                    }
                    if (quantity > 100) {
                        numberpicker_input.setText("100");
                        np.setValue(100);
                    }
                }
            }
        }
    }

    private View.OnClickListener numberpickerlistenerdialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MyViewHolder holder = (MyViewHolder) v.getTag();
            createNumberPickerDialog(holder);
        }
    };

    private void createNumberPickerDialog(MyViewHolder holder) {
        if (quantitydialog == null) {
            String quantitystrpassed = String.valueOf(holder.storequantitynumberpickdialog.getText());
            int quantitypassed = Integer.parseInt(quantitystrpassed);

            quantitydialog = new Dialog(mContext);
            quantitydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            quantitydialog.setContentView(R.layout.numberpickerdialog);
            quantitydialog.setCancelable(false);


            Button setbtn = (Button) quantitydialog.findViewById(R.id.setbtn);
            LinearLayout cancelbtncontainer = (LinearLayout) quantitydialog.findViewById(R.id.cancelbtncontainer);
            final EditText numberpicker_input = (EditText) quantitydialog.findViewById(R.id.numberpicker_input);
            //Works if the range starts from 1.
            numberpicker_input.setFilters(new InputFilter[]{new InputFilterMinMax_GKStore("1", "100")});

            np = (NumberPicker) quantitydialog.findViewById(R.id.numberPicker1);
            np.setMaxValue(100);
            np.setMinValue(1);
            np.setWrapSelectorWheel(false);
            np.setValue(quantitypassed);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                np.setNestedScrollingEnabled(false);
            }

            numberpicker_input.setText(String.valueOf(quantitypassed));

            np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    numberpicker_input.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(numberpicker_input.getWindowToken(), 0);
                }
            });


            np.setTag(holder);
            np.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    numberpicker_input.setVisibility(View.VISIBLE);
                    numberpicker_input.setText(String.valueOf(np.getValue()));
                    String getValue = String.valueOf(numberpicker_input.getText());
                    int quantity = Integer.parseInt(getValue);
                    np.setValue(quantity);
                    boolean yes = numberpicker_input.hasFocus();
                    numberpicker_input.setSelection(numberpicker_input.getText().length());
                    numberpicker_input.requestFocus();

                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(numberpicker_input, InputMethodManager.SHOW_IMPLICIT);
                }
            });

            numberpicker_input.addTextChangedListener(new NumberPickerEditTextWatcher(numberpicker_input, np));

            np.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                    if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_FLING
                            || scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
                            || scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                        numberpicker_input.clearFocus();
                        numberpicker_input.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(numberpicker_input.getWindowToken(), 0);
                        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                    }
                }
            });

            setbtn.setTag(holder);
            setbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GKStoreProducts gkstoreref = gkStoreProducts.get(holder.getAdapterPosition());
                    holder.storequantitynumberpickdialog.setText(String.valueOf(np.getValue()));

                    double qty = Double.parseDouble(String.valueOf(np.getValue()));
                    double price = gkstoreref.getActualPrice();
                    holder.amount.setText(CommonFunctions.currencyFormatter(String.valueOf(price * qty)));

                    int quantity = (int) qty;
                    double amountdouble = price * qty;

                    gkstoreref.setQuantity(quantity);
                    updateOrderData(gkstoreref);
                    quantitydialog.dismiss();
                    quantitydialog = null;
                }
            });
            cancelbtncontainer.setTag(holder);
            cancelbtncontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantitydialog.dismiss();
                    quantitydialog = null;
                }
            });

            quantitydialog.show();

        }
    }

    private void createNumberPickerinDialog(MyViewHolder holder) {
        if (quantitydialog == null) {
            String quantitystrpassed = String.valueOf(dialogStorequantitynumberpickdialog.getText());
            int quantitypassed = Integer.parseInt(quantitystrpassed);

            quantitydialog = new Dialog(mContext);
            quantitydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            quantitydialog.setContentView(R.layout.numberpickerdialog);
            quantitydialog.setCancelable(false);


            Button setbtn = (Button) quantitydialog.findViewById(R.id.setbtn);
            LinearLayout cancelbtncontainer = (LinearLayout) quantitydialog.findViewById(R.id.cancelbtncontainer);
            final EditText numberpicker_input = (EditText) quantitydialog.findViewById(R.id.numberpicker_input);
            //Works if the range starts from 1.
            numberpicker_input.setFilters(new InputFilter[]{new InputFilterMinMax_GKStore("1", "100")});

            np = (NumberPicker) quantitydialog.findViewById(R.id.numberPicker1);
            np.setMaxValue(100);
            np.setMinValue(1);
            np.setWrapSelectorWheel(false);
            np.setValue(quantitypassed);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                np.setNestedScrollingEnabled(false);
            }

            numberpicker_input.setText(String.valueOf(quantitypassed));

            np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    numberpicker_input.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(numberpicker_input.getWindowToken(), 0);
                }
            });

            np.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    numberpicker_input.setVisibility(View.VISIBLE);
                    numberpicker_input.setText(String.valueOf(np.getValue()));
                    String getValue = String.valueOf(numberpicker_input.getText());
                    int quantity = Integer.parseInt(getValue);
                    np.setValue(quantity);
                    boolean yes = numberpicker_input.hasFocus();
                    numberpicker_input.setSelection(numberpicker_input.getText().length());
                    numberpicker_input.requestFocus();

                    InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(numberpicker_input, InputMethodManager.SHOW_IMPLICIT);
                }
            });

            numberpicker_input.addTextChangedListener(new NumberPickerEditTextWatcher(numberpicker_input, np));

            np.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker numberPicker, int scrollState) {
                    if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_FLING
                            || scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
                            || scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
                        numberpicker_input.clearFocus();
                        numberpicker_input.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(numberpicker_input.getWindowToken(), 0);
                        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                    }
                }
            });

            setbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.storequantitynumberpickdialog.setText(String.valueOf(np.getValue()));
                    dialogStorequantitynumberpickdialog.setText(String.valueOf(np.getValue()));

                    double qty = Double.parseDouble(String.valueOf(np.getValue()));
                    double price = selectedProduct.getActualPrice();
                    dialogAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(price * qty)));
                    holder.amount.setText(CommonFunctions.currencyFormatter(String.valueOf(price * qty)));

                    int quantity = (int) qty;
                    double amountdouble = price * qty;

                    selectedProduct.setQuantity(quantity);
                    updateOrderData(selectedProduct);
                    quantitydialog.dismiss();
                    quantitydialog = null;
                }
            });
            cancelbtncontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quantitydialog.dismiss();
                    quantitydialog = null;
                }
            });

            quantitydialog.show();

        }
    }

    private void updateOrderData(GKStoreProducts gkStoreProducts) {

        for (GKStoreProducts gkstore : passedGKStoreProducts) {
            if (gkstore.getProductID().equals(gkStoreProducts.getProductID())) {
                gkstore.setQuantity(gkStoreProducts.getQuantity());
                ((GKStoreDetailsActivity) mContext).orderDetails(passedGKStoreProducts);
                return;
            }
        }

    }

    //FullScreenListener
    private View.OnClickListener fullscreenlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MyViewHolder holder = (MyViewHolder) v.getTag();
            GKStoreProducts gkstoreref = gkStoreProducts.get(holder.getAdapterPosition());
            String checkdata = gkstoreref.getProductImageURL();
            ArrayList<String> multiimage = new ArrayList<>();

            try {
                String count = parseXML(checkdata, "count");
                if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                    for (int i = 0; i < Integer.parseInt(count); i++) {
                        String multiimageurl = parseXML(checkdata, String.valueOf(i));
                        multiimage.add(multiimageurl);
                    }
                }

                Intent intent = new Intent(mContext, GKStoreFullScreenActivity.class);
                intent.putStringArrayListExtra("GKSTOREMULTIIMAGE", multiimage);
                mContext.startActivity(intent);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    //Parse the XML Details
    private String parseXML(String data, String nametag) {
        String result = "";
        int startpoint;
        int endpoint;

        //getting the starting point
        startpoint = data.indexOf("<" + nametag + ">");
        //getting the endpoint
        endpoint = data.indexOf("</" + nametag + ">");
        if (startpoint == -1 || endpoint == -1) {
            //return empty
            result = "NONE";
        } else {
            int starttaglen = nametag.length() + 2;
            //returning the extracted data
            result = data.substring(startpoint + starttaglen, endpoint);
        }

        return result;
    }

    //Convert XML Details
    private void convertXMLDetails(MyViewHolder holder) throws JSONException {
        GKStoreProducts gkstoreref = gkStoreProducts.get(holder.getAdapterPosition());
        String checkdata = gkstoreref.getProductImageURL();
        ArrayList<String> multiimage = new ArrayList<>();

        try {
            String count = parseXML(checkdata, "count");
            if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                for (int i = 0; i < Integer.parseInt(count); i++) {
                    String multiimageurl = parseXML(checkdata, String.valueOf(i));
                    multiimage.add(multiimageurl);
                    if (i <= 0) {
                        //Normal Picture
                        Glide.with(mContext)
                                .load(multiimageurl)
                                .apply(new RequestOptions()
                                        .fitCenter()
                                        .placeholder(R.drawable.ic_gkstore_products_default)
                                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                )
                                .into(holder.mLogoPic);
                        //BigSize Picture
                        Glide.with(mContext)
                                .load(multiimageurl)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.ic_gkstore_products_default)
                                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                )
                                .into(holder.mLogoPicfull);
                    }
                }
            }
            gkstoremultiimageslider(holder, multiimage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //for dialog view
    //Convert XML Details
    private void convertXMLDetailsForDialog(View view, GKStoreProducts storeProducts) throws JSONException {
        GKStoreProducts gkstoreref = storeProducts;
        String checkdata = gkstoreref.getProductImageURL();
        ArrayList<String> multiimage = new ArrayList<>();

        try {
            String count = parseXML(checkdata, "count");
            if (!count.equals("") && !count.equals(".") && !count.equals("NONE")) {
                for (int i = 0; i < Integer.parseInt(count); i++) {
                    String multiimageurl = parseXML(checkdata, String.valueOf(i));
                    multiimage.add(multiimageurl);
                    if (i <= 0) {

                        //BigSize Picture
                        Glide.with(mContext)
                                .load(multiimageurl)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.ic_gkstore_products_default)
                                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                )
                                .into(dialogProductLogoPicfull);
                    }
                }
            }
            gkstoremultiimagesliderDialog(multiimage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Images
    private void gkstoremultiimageslider(MyViewHolder holder, ArrayList<String> checkmultilist) {
        holder.mSlider.removeAllSliders();

        if (!checkmultilist.isEmpty()) {
            for (String imgpath : checkmultilist) {
                DefaultSliderView textSliderView = new DefaultSliderView(mContext);
                // initialize a SliderLayout
                textSliderView
                        .image(imgpath)
                        .setScaleType(BaseSliderView.ScaleType.CenterInside);

                holder.mSlider.addSlider(textSliderView);
                holder.mSlider.setCustomIndicator(holder.mPagerIndicator);
            }
            holder.mSlider.stopAutoCycle();

            int slidercount = checkmultilist.size() - 1;
            holder.mSlider.addOnPageChangeListener(new ImageViewPagerEX(holder, slidercount));
            if (slidercount <= 0) {
                holder.mSlider.setVisibility(View.GONE);
                holder.mPagerIndicator.setVisibility(View.GONE);
                holder.mLogoPicfull.setVisibility(View.VISIBLE);
            } else {
                holder.mSlider.setVisibility(View.VISIBLE);
                holder.mPagerIndicator.setVisibility(View.VISIBLE);
                holder.mLogoPicfull.setVisibility(View.GONE);
            }

        } else {
            holder.mSlider.setVisibility(View.GONE);
            holder.mPagerIndicator.setVisibility(View.GONE);
            holder.mLogoPicfull.setVisibility(View.VISIBLE);

        }
    }

    //Images
    private void gkstoremultiimagesliderDialog(ArrayList<String> checkmultilist) {
        dialogProductmultiimagesslider.removeAllSliders();

        if (!checkmultilist.isEmpty()) {
            for (String imgpath : checkmultilist) {
                DefaultSliderView textSliderView = new DefaultSliderView(mContext);
                // initialize a SliderLayout
                textSliderView
                        .image(imgpath)
                        .setScaleType(BaseSliderView.ScaleType.CenterInside);

                dialogProductmultiimagesslider.addSlider(textSliderView);
                dialogProductmultiimagesslider.setCustomIndicator(dialogProdDetail_custom_indicator);
            }
            dialogProductmultiimagesslider.stopAutoCycle();

            int slidercount = checkmultilist.size() - 1;
            dialogProductmultiimagesslider.addOnPageChangeListener(new ImageViewPagerEXDialog(slidercount));
            if (slidercount <= 0) {
                dialogProductmultiimagesslider.setVisibility(View.GONE);
                dialogProdDetail_custom_indicator.setVisibility(View.GONE);
                dialogProductLogoPicfull.setVisibility(View.VISIBLE);
            } else {
                dialogProductmultiimagesslider.setVisibility(View.VISIBLE);
                dialogProdDetail_custom_indicator.setVisibility(View.VISIBLE);
                dialogProductLogoPicfull.setVisibility(View.GONE);
            }

        } else {
            dialogProductmultiimagesslider.setVisibility(View.GONE);
            dialogProdDetail_custom_indicator.setVisibility(View.GONE);
            dialogProductLogoPicfull.setVisibility(View.VISIBLE);

        }
    }

    //Logo
    private class ImageViewPagerEXDialog implements ViewPagerEx.OnPageChangeListener {

        private int slidercount = 0;

        private ImageViewPagerEXDialog(int slidercount) {
            this.slidercount = slidercount;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void applyColor(SpannableStringBuilder ssb, int start, int end, int color) {
        // Create a span that will make the text red
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(
                color);
        // Apply the color span
        ssb.setSpan(
                colorSpan,            // the span to add
                start,                // the start of the span (inclusive)
                end,                  // the end of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder
        // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
        // text is added in later
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(GKStoreAdapter.MyViewHolder holder, int position) {
        try {
            GKStoreProducts gkstoreref = gkStoreProducts.get(position);

            //Setting Pictures pure URL (Normal)
            Glide.with(mContext)
                    .load(gkstoreref.getProductImageURL())
                    .apply(new RequestOptions()
                            .fitCenter()
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(holder.mLogoPic);

            //Setting Picture pure URL (Big)
            Glide.with(mContext)
                    .load(gkstoreref.getProductImageURL())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(holder.mLogoPicfull);

            //Setting Multi Pictures
            try {
                convertXMLDetails(holder);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            holder.productname.setText(CommonFunctions.replaceEscapeData(gkstoreref.getProductName()));
            holder.productdesc.setText(CommonFunctions.replaceEscapeData(gkstoreref.getProductDesc()));


            //Setting the Make Order and Remove again for search.
            if (passedGKStoreProducts.isEmpty()) {
                holder.gkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
                holder.gkstoreordertxt.setText("MAKE ORDER");
                holder.storequantitynumberpickdialog.setText("0");
                holder.amount.setText("0.00");
            } else {
                holder.gkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
                holder.gkstoreordertxt.setText("MAKE ORDER");
                holder.storequantitynumberpickdialog.setText("0");
                holder.amount.setText("0.00");

                String gkstoreproductname = gkstoreref.getProductName();
                for (GKStoreProducts passedGkStoreProducts : passedGKStoreProducts) {
                    String checkproductname = passedGkStoreProducts.getProductName();
                    int quantity = passedGkStoreProducts.getQuantity();
                    double checkquantity = Double.parseDouble(String.valueOf(quantity));
                    double checkprice = passedGkStoreProducts.getActualPrice();

                    if (checkproductname.equals(gkstoreproductname)) {
                        holder.iselected = true;
                        holder.gkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorred));
                        holder.gkstoreordertxt.setText("REMOVE");
                        holder.storequantitynumberpickdialog.setText(String.valueOf(quantity));
                        holder.amount.setText(CommonFunctions.currencyFormatter(String.valueOf(checkprice * checkquantity)));
                        break;
                    }
                }
            }

            if (gkstoreref.getGrossPrice() == gkstoreref.getActualPrice()) {
                holder.discountimage.setVisibility(View.GONE);
                holder.discountimagefull.setVisibility(View.GONE);

                holder.actualprice.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getActualPrice())));
                holder.actualpricefull.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getActualPrice())));
            } else {
                String strproductname = "";
                SpannableStringBuilder ssb = new SpannableStringBuilder(strproductname);

                String strgrossprice = CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getGrossPrice()));
                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                ssb.append("");
                ssb.append("₱");
                ssb.append(strgrossprice);
                ssb.setSpan(strikethroughSpan,
                        ssb.length() - strgrossprice.length(),
                        ssb.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                String actualprice = CommonFunctions.currencyFormatter(String.valueOf(gkstoreref.getActualPrice()));
                ssb.append("  ");
                ssb.append(actualprice);
                applyColor(ssb, ssb.length() - actualprice.length(), ssb.length(), ContextCompat.getColor(getContext(), R.color.color_F83832));

                holder.discountimage.setVisibility(View.VISIBLE);
                holder.discountimagefull.setVisibility(View.VISIBLE);
                holder.actualprice.setText(ssb, TextView.BufferType.EDITABLE);
                holder.actualpricefull.setText(ssb, TextView.BufferType.EDITABLE);
            }

            holder.productnamefull.setText(CommonFunctions.replaceEscapeData(gkstoreref.getProductName()));

            if (gkstoreref.getProductDesc().contains("http")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    holder.productdescfull.setText(Html.fromHtml(CommonFunctions.replaceEscapeData(gkstoreref.getProductDesc()), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    holder.productdescfull.setText(Html.fromHtml(CommonFunctions.replaceEscapeData(gkstoreref.getProductDesc())));
                }
                //holder.productdescfull.setMovementMethod(LinkMovementMethod.getInstance());
                Linkify.addLinks(holder.productdescfull, Linkify.WEB_URLS);
            } else {
                holder.productdescfull.setText(CommonFunctions.replaceEscapeData(gkstoreref.getProductDesc()));
            }

            holder.gkstoreordercontainer.setOnClickListener(orderlistener);
            holder.gkstoreordercontainer.setTag(holder);

            holder.orderdetailsarrowcontainer.setOnClickListener(numberpickerlistenerdialog);
            holder.orderdetailsarrowcontainer.setTag(holder);

            holder.productlogocontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showDialog(holder, gkstoreref);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

//            holder.logocontainerfull.setOnClickListener(fullscreenlistener);
//            holder.logocontainerfull.setTag(holder);

            //Checks if store is offline
            boolean checkGKStoreStatus = ((GKStoreDetailsActivity) mContext).checkGKStoreStatus();
            if (checkGKStoreStatus) {
                holder.itemcontainer.setVisibility(View.GONE);
            }


            if (gkstoreref.getProductName().equals(productNameToShow)) {
                showDialog(holder, gkstoreref);
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void showDialog(MyViewHolder holder, GKStoreProducts getStoreProducts) {

        if(!isShowed){
            dialogBuilder = new AlertDialog.Builder(mContext);

            View layoutView = LayoutInflater.from(getContext()).inflate(R.layout.product_details, null);
            View toolbarView = LayoutInflater.from(getContext()).inflate(R.layout.product_details_layout, null);

            dialogBuilder.setCustomTitle(toolbarView);
            dialogBuilder.setView(layoutView);
            dialogBuilder.setCancelable(false);
            alertDialog = dialogBuilder.create();

            //init views
            dialogProductlogocontainerfull = layoutView.findViewById(R.id.productlogocontainerfull);
            dialogLogoproductcontainerfull = layoutView.findViewById(R.id.logoproductcontainerfull);
            dialogProductmultiimagesslider = layoutView.findViewById(R.id.productmultiimagesslider);
            dialogProdDetail_custom_indicator = layoutView.findViewById(R.id.prodDetail_custom_indicator2);
            dialogProductLogoPicfull = layoutView.findViewById(R.id.mProductLogoPicfull);
            dialogProductdiscountimagefull = layoutView.findViewById(R.id.productdiscountimagefull);
            dialogProductshowmoreimage = layoutView.findViewById(R.id.productshowmoreimage);
            dialogDetailscontainerfull = layoutView.findViewById(R.id.detailscontainerfull);
            dialogProductnamefull = layoutView.findViewById(R.id.productnamefull);
            dialogActualpricefull = layoutView.findViewById(R.id.actualpricefull);
            dialogProductdescfull = layoutView.findViewById(R.id.productdescfull);
            dialogItemcontainer = layoutView.findViewById(R.id.itemcontainer);
            dialogQuantitycontainer = layoutView.findViewById(R.id.quantitycontainer);
            dialogOrderdetailsarrowcontainer = layoutView.findViewById(R.id.orderdetailsarrowcontainer);
            dialogStorequantitynumberpickdialog = layoutView.findViewById(R.id.storequantitynumberpickdialog);
            dialogAmountcontainer = layoutView.findViewById(R.id.amountcontainer);
            dialogAmount = layoutView.findViewById(R.id.amount);
            dialogGkstoreordercontainer = layoutView.findViewById(R.id.gkstoreordercontainer);
            dialogGkstoreordertxt = layoutView.findViewById(R.id.gkstoreordertxt);
            product_details_layout_closeImg = toolbarView.findViewById(R.id.product_details_layout_closeImg);
            dialogIselected = false;

            selectedProduct = getStoreProducts;

            Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.show();

            if(alertDialog.isShowing()){
                isShowed = true;
            }


            //Setting Picture pure URL (Big)
            Glide.with(mContext)
                    .load(getStoreProducts.getProductImageURL())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_gkstore_products_default)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    )
                    .into(dialogProductLogoPicfull);

            //Setting Multi Pictures
            try {
                convertXMLDetailsForDialog(dialogProductLogoPicfull, getStoreProducts);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialogProductnamefull.setText(CommonFunctions.replaceEscapeData(getStoreProducts.getProductName()));
            dialogProductdescfull.setText(CommonFunctions.replaceEscapeData(getStoreProducts.getProductDesc()));

            //Setting the Make Order and Remove again for search.
            if (passedGKStoreProducts.isEmpty()) {
                dialogGkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
                dialogGkstoreordertxt.setText("MAKE ORDER");
                dialogStorequantitynumberpickdialog.setText("0");
                dialogAmount.setText("0.00");
            } else {
                dialogGkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4A90E2));
                dialogGkstoreordertxt.setText("MAKE ORDER");
                dialogStorequantitynumberpickdialog.setText("0");
                dialogAmount.setText("0.00");

                String gkstoreproductname = getStoreProducts.getProductName();

                for (GKStoreProducts passedGkStoreProducts : passedGKStoreProducts) {
                    String checkproductname = passedGkStoreProducts.getProductName();
                    int quantity = passedGkStoreProducts.getQuantity();
                    double checkquantity = Double.parseDouble(String.valueOf(quantity));
                    double checkprice = passedGkStoreProducts.getActualPrice();

                    if (checkproductname.equals(gkstoreproductname)) {
                        dialogIselected = true;
                        dialogGkstoreordercontainer.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorred));
                        dialogGkstoreordertxt.setText("REMOVE");
                        dialogStorequantitynumberpickdialog.setText(String.valueOf(quantity));
                        dialogAmount.setText(CommonFunctions.currencyFormatter(String.valueOf(checkprice * checkquantity)));
                        break;
                    }
                }
            }

            if (getStoreProducts.getGrossPrice() == getStoreProducts.getActualPrice()) {
                dialogProductdiscountimagefull.setVisibility(View.GONE);
                dialogActualpricefull.setText("₱" + CommonFunctions.currencyFormatter(String.valueOf(getStoreProducts.getActualPrice())));
            } else {
                String strproductname = "";
                SpannableStringBuilder ssb = new SpannableStringBuilder(strproductname);

                String strgrossprice = CommonFunctions.currencyFormatter(String.valueOf(getStoreProducts.getGrossPrice()));
                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                ssb.append("");
                ssb.append("₱");
                ssb.append(strgrossprice);
                ssb.setSpan(strikethroughSpan,
                        ssb.length() - strgrossprice.length(),
                        ssb.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                String actualprice = CommonFunctions.currencyFormatter(String.valueOf(getStoreProducts.getActualPrice()));
                ssb.append("  ");
                ssb.append(actualprice);
                applyColor(ssb, ssb.length() - actualprice.length(), ssb.length(), ContextCompat.getColor(mContext, R.color.color_F83832));


                dialogProductdiscountimagefull.setVisibility(View.VISIBLE);
                dialogActualpricefull.setText(ssb, TextView.BufferType.EDITABLE);
            }

            dialogProductnamefull.setText(CommonFunctions.replaceEscapeData(getStoreProducts.getProductName()));

            if (getStoreProducts.getProductDesc().contains("http")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    dialogProductdescfull.setText(Html.fromHtml(CommonFunctions.replaceEscapeData(getStoreProducts.getProductDesc()), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    dialogProductdescfull.setText(Html.fromHtml(CommonFunctions.replaceEscapeData(getStoreProducts.getProductDesc())));
                }
                //holder.productdescfull.setMovementMethod(LinkMovementMethod.getInstance());
                Linkify.addLinks(dialogProductdescfull, Linkify.WEB_URLS);
            } else {
                dialogProductdescfull.setText(CommonFunctions.replaceEscapeData(getStoreProducts.getProductDesc()));
            }


            //listeners
            dialogGkstoreordercontainer.setTag(holder);
            dialogGkstoreordercontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        final GKStoreAdapter.MyViewHolder holder = (MyViewHolder) v.getTag();
                        makeGKStoreOrderDialog(holder);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            dialogOrderdetailsarrowcontainer.setTag(holder);
            dialogOrderdetailsarrowcontainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createNumberPickerinDialog(holder);
                }
            });

            product_details_layout_closeImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                    isShowed = false;
                }
            });
        }

    }

    @NotNull
    @Override
    public GKStoreAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gk_store, parent, false);
        getParent = parent;
        MyViewHolder holder = new MyViewHolder(itemView);
        itemView.setTag(holder);
        return holder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView amount;
        LinearLayout gkstoreordercontainer;
        TextView gkstoreordertxt;
        boolean iselected = false;
        LinearLayout productlogocontainer;
        ImageView mLogoPic;
        TextView productname;
        TextView actualprice;
        TextView productdesc;
        LinearLayout productlogocontainerfull;
        ImageView mLogoPicfull;
        TextView productnamefull;
        TextView actualpricefull;
        TextView productdescfull;
        NumberPicker storequantity;
        LinearLayout orderdetailsarrowcontainer;
        TextView storequantitynumberpickdialog;
        LinearLayout item_gk_store;
        boolean checkstatus = false;
        ImageView discountimage;
        ImageView discountimagefull;

        //MultiImages
//        List<String> multiimage = new ArrayList<>();
        RelativeLayout detailscontainerfull;
        LinearLayout itemcontainer;
        RelativeLayout logocontainerfull;
        SliderLayout mSlider;
        PagerIndicator mPagerIndicator;

        ImageView pdarrow;

        public MyViewHolder(View itemView) {
            super(itemView);

            amount = (TextView) itemView.findViewById(R.id.amount);


            mLogoPic = (ImageView) itemView.findViewById(R.id.mLogoPic);
            productname = (TextView) itemView.findViewById(R.id.productname);
            actualprice = (TextView) itemView.findViewById(R.id.actualprice);
            productdesc = (TextView) itemView.findViewById(R.id.productdesc);

            mLogoPicfull = (ImageView) itemView.findViewById(R.id.mProductLogoPicfull);
            productnamefull = (TextView) itemView.findViewById(R.id.productnamefull);
            actualpricefull = (TextView) itemView.findViewById(R.id.actualpricefull);
            productdescfull = (TextView) itemView.findViewById(R.id.productdescfull);

            productlogocontainer = (LinearLayout) itemView.findViewById(R.id.productlogocontainer);
            productlogocontainerfull = (LinearLayout) itemView.findViewById(R.id.productlogocontainerfull);
//            productlogocontainerfull.setOnClickListener(this);

            //Multi Images.
            detailscontainerfull = (RelativeLayout) itemView.findViewById(R.id.detailscontainerfull);
            detailscontainerfull.setOnClickListener(this);
            logocontainerfull = (RelativeLayout) itemView.findViewById(R.id.logoproductcontainerfull);
            logocontainerfull.setOnClickListener(this);

            gkstoreordercontainer = (LinearLayout) itemView.findViewById(R.id.gkstoreordercontainer);
            gkstoreordertxt = (TextView) itemView.findViewById(R.id.gkstoreordertxt);

            storequantitynumberpickdialog = (TextView) itemView.findViewById(R.id.storequantitynumberpickdialog);
            orderdetailsarrowcontainer = (LinearLayout) itemView.findViewById(R.id.orderdetailsarrowcontainer);

            item_gk_store = (LinearLayout) itemView.findViewById(R.id.item_gk_store);

            discountimage = (ImageView) itemView.findViewById(R.id.discountimage);
            discountimagefull = (ImageView) itemView.findViewById(R.id.productdiscountimagefull);

            itemcontainer = (LinearLayout) itemView.findViewById(R.id.itemcontainer);

            //Slider
            mSlider = (SliderLayout) itemView.findViewById(R.id.productmultiimagesslider);
            mPagerIndicator = (PagerIndicator) itemView.findViewById(R.id.prodDetail_custom_indicator2);

            pdarrow = (ImageView) itemView.findViewById(R.id.pdarrow);

            productnamefull.setOnClickListener(this);
            actualpricefull.setOnClickListener(this);
            pdarrow.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //Big Picture

                case R.id.productnamefull:
                case R.id.actualpricefull:
                case R.id.pdarrow: {

                    productlogocontainerfull.setVisibility(View.GONE);
                    final Animation fade_in = AnimationUtils.loadAnimation(mContext,
                            R.anim.fade_in);
                    productlogocontainer.startAnimation(fade_in);
                    fade_in.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            productlogocontainerfull.setEnabled(false);
                            productlogocontainer.setEnabled(false);
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            productlogocontainerfull.setEnabled(true);
                            productlogocontainer.setEnabled(true);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    productlogocontainer.setVisibility(View.VISIBLE);

                    break;
                }

//                case R.id.logocontainerfull: {
//                    Intent intent = new Intent(mContext, GKStoreFullScreenActivity.class);
//                    intent.putExtra("GKSTOREMULTIIMAGE", String.valueOf(multiimage));
//                    mContext.startActivity(intent);
//                    break;
//                }

            }
        }
    }

    // Filter Class
    public void filter(String charText) {
        productNameToShow = charText;
    }


}
