package com.goodkredit.myapplication.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.bean.loadmessenger.LoadTransaction;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.Objects;

public class ViewLoadTxnDialogFragment extends BottomSheetDialogFragment {

    LoadTransaction loadTransaction = null;

    //
    ImageView closeImg;

    TextView view_txn_no;
    TextView view_ref_no;
    TextView view_txn_network;
    TextView view_txn_mobile;
    TextView view_txn_productcode;
    TextView view_txn_producttype;
    TextView view_txn_amount;
    TextView view_txn_datecompleted;
    TextView view_txn_name;
    TextView view_txn_id;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.view_txn_bottomsheet, container, false);

        if(getArguments() != null){
            loadTransaction = new Gson().fromJson(getArguments().getString("loadInfo"),LoadTransaction.class);
            Logger.debug("okhttp","LOAD INFO : "+ new Gson().toJson(loadTransaction));
        }

        init(v);
        initdata();
        return v;
    }


    private void init(View v) {
         view_txn_no = v.findViewById(R.id.view_txn_no);
         view_ref_no  = v.findViewById(R.id.view_ref_no);
         view_txn_network =  v.findViewById(R.id.view_txn_network);
         view_txn_mobile =  v.findViewById(R.id.view_txn_mobile);
         view_txn_productcode =  v.findViewById(R.id.view_txn_productcode);
         view_txn_producttype =  v.findViewById(R.id.view_txn_producttype);
         view_txn_amount =  v.findViewById(R.id.view_txn_amount);
         view_txn_datecompleted =  v.findViewById(R.id.view_txn_datecompleted);
         view_txn_name =  v.findViewById(R.id.view_txn_name);
         view_txn_id =  v.findViewById(R.id.view_txn_id);
        closeImg = v.findViewById(R.id.closeImg);

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(getDialog() != null){
                  getDialog().dismiss();
              }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initdata() {
        view_txn_no.setText("Transaction #: "+ (loadTransaction.getTransactionNo() == null ? ".": loadTransaction.getTransactionNo()));
        view_ref_no.setText("GK Ref #: "+ (loadTransaction.getProviderTxnNo() == null ? "." : loadTransaction.getProviderTxnNo()) );
        view_txn_network.setText("Network: "+loadTransaction.getNetwork());
        view_txn_mobile.setText("Mobile #: "+loadTransaction.getMobileTarget());
        view_txn_productcode.setText("Product Code : "+loadTransaction.getProductCode());
        view_txn_producttype.setText("Product Type: "+loadTransaction.getDenomType());
        view_txn_amount.setText("Load Amount: "+CommonFunctions.currencyFormatter(loadTransaction.getAmount()));
        view_txn_datecompleted.setText("Date & Time: "+ CommonFunctions.convertDate(loadTransaction.getDateTimeCompleted()));
        view_txn_name.setText(loadTransaction.getFBName());
        view_txn_id.setText("("+loadTransaction.getTransactionNo()+")");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) requireView().getParent()).setBackgroundColor(Color.TRANSPARENT);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog=(BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dia) {
                BottomSheetDialog dialog = (BottomSheetDialog) dia;
                FrameLayout bottomSheet =  dialog .findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setSkipCollapsed(true);
                BottomSheetBehavior.from(bottomSheet).setHideable(true);
            }
        });
        return bottomSheetDialog;
    }

}
