package com.goodkredit.myapplication.fragments.transactions;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.transactions.OthersRecyclerAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;

import java.util.ArrayList;

/**
 * Created by User-PC on 7/28/2017.
 */

public class OthersFragment extends BaseFragment {

    private static final String KEY_TRANSACTIONS_FRAGMENT = "title";
    private ArrayList<String> mGridData;
    private OthersRecyclerAdapter mOthersAdapter;

    public static OthersFragment newInstance(String value) {
        OthersFragment fragment = new OthersFragment();
        Bundle b = new Bundle();
        b.putString(KEY_TRANSACTIONS_FRAGMENT, value);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transactions_others, container, false);

        mGridData = new ArrayList<>();
        mGridData.add("Borrowings from Sponsor");
        mGridData.add("Purchases of Prepaid Vouchers");
        mGridData.add("History of Transferred Vouchers");
        mGridData.add("History of Received Vouchers");
        mGridData.add("Logs of Prepaid Load");
        mGridData.add("Logs of Bills Payment");
        mGridData.add("Logs of Smart Retailer Reloading");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_transactions_others);
        recyclerView.setLayoutManager(new LinearLayoutManager(getViewContext()));
        recyclerView.setNestedScrollingEnabled(false);
        mOthersAdapter = new OthersRecyclerAdapter(getViewContext(), mGridData);
        recyclerView.addItemDecoration(new RecyclerViewListItemDecorator(getContext(), null));
        recyclerView.setAdapter(mOthersAdapter);

        return view;
    }
}
