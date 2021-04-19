package com.goodkredit.myapplication.fragments.vouchers;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.activities.vouchers.AvailableVoucherDetails;
import com.goodkredit.myapplication.activities.vouchers.GroupedVouchersActivity;
import com.goodkredit.myapplication.adapter.vouchers.GroupDetailsGridViewAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 5/24/2017.
 */

public class GroupVoucherDetailsFragment2 extends BaseFragment implements AdapterView.OnItemClickListener {

    private ImageView mImgVoucherImage;
    private TextView mTvVoucherName;
    private TextView mTvNofVoucher;
    private TextView mTvTotalAmount;

    private AQuery aq;
    private DatabaseHandler db;

    private Voucher mVoucher;
    private ArrayList<Voucher> mVoucherArrayList;

    private GridView mGridView;
    private GroupDetailsGridViewAdapter mGridAdapter;

    public static GroupVoucherDetailsFragment2 newInstance() {
        GroupVoucherDetailsFragment2 fragment = new GroupVoucherDetailsFragment2();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grouped_voucher_details2, null);

        aq = new AQuery(getContext());
        db = new DatabaseHandler(getContext());

        mVoucher = ((GroupedVouchersActivity) getActivity()).getVoucher();
        mVoucherArrayList = db.getVouchersFromGroupedVouchers(db, mVoucher.getGroupVoucherCode());

        mImgVoucherImage = (ImageView) view.findViewById(R.id.gvd_voucherImage);
        mTvVoucherName = (TextView) view.findViewById(R.id.gvd_groupName);
        mTvNofVoucher = (TextView) view.findViewById(R.id.gvd_nofVoucher);
        mTvTotalAmount = (TextView) view.findViewById(R.id.gvd_totalAmount);

        mGridView = (GridView) view.findViewById(R.id.gridView);
        mGridAdapter = new GroupDetailsGridViewAdapter(getContext(), mVoucherArrayList);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(this);

        String voucherword = "";

        aq.id(mImgVoucherImage).image(CommonVariables.s3link+"group-vouchers.png", false, true);
        mTvVoucherName.setText(mVoucher.getGroupName());

        if ((mVoucherArrayList.size() == 1))
            voucherword = " Voucher";
        else
            voucherword = " Vouchers";

        mTvNofVoucher.setText(String.valueOf(mVoucherArrayList.size()) + voucherword);
        mTvTotalAmount.setText(getTotal(mVoucherArrayList));

        return view;
    }

    private String getTotal(ArrayList<Voucher> arrayList) {
        double subtotal = 0;
        if (!arrayList.isEmpty())
            for (Voucher voucher : arrayList) {
                subtotal += voucher.getRemainingBalance();
            }
        return String.valueOf(subtotal);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), AvailableVoucherDetails.class);
        intent.putExtra("VOUCHER_OBJECT", mVoucherArrayList.get(position));
        getContext().startActivity(intent);
    }
}
