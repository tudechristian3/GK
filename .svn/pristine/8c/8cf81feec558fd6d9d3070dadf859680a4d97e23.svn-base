package com.goodkredit.myapplication.activities.gkservices;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.claudiodegio.msv.OnSearchViewListener;
import com.claudiodegio.msv.SuggestionMaterialSearchView;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkservices.GKServicesSearchResultAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.RecyclerViewListItemDecorator;
import com.goodkredit.myapplication.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 10/27/2017.
 */

public class GKServicesSearchActivity extends BaseActivity {

    private SuggestionMaterialSearchView searchView;

    private RecyclerView rv_search_result;
    private GKServicesSearchResultAdapter mAdapter;

    private List<GKService> gkServices = new ArrayList<>();

    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_gk_services);
        init();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, GKServicesSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchView != null)
            searchView.showSearch();
    }

    private void init() {

        db = new DatabaseHandler(getViewContext());

        mAdapter = new GKServicesSearchResultAdapter(getViewContext(), gkServices);

        rv_search_result = (RecyclerView) findViewById(R.id.rv_search_result);
        rv_search_result.setLayoutManager(new LinearLayoutManager(getViewContext()));
        rv_search_result.addItemDecoration(new RecyclerViewListItemDecorator(ContextCompat.getDrawable(getViewContext(), R.drawable.divider_white), false, false));
        rv_search_result.setAdapter(mAdapter);

        searchView = (SuggestionMaterialSearchView) findViewById(R.id.sv);
        searchView.setOnSearchViewListener(new OnSearchViewListener() {
            @Override
            public void onSearchViewShown() {
                mAdapter.update(db.getGKServicesForKeyword(db, ""));
            }

            @Override
            public void onSearchViewClosed() {
                onBackPressed();
            }

            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public void onQueryTextChange(String s) {
                mAdapter.update(db.getGKServicesForKeyword(db, s));
//                db.getGKServicesForKeyword(db,s);
            }
        });

        searchView.showSearch();
    }
}
