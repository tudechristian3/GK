package com.goodkredit.myapplication.fragments.vouchers;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkservices.GKServicesRecyclerViewAdapter;
import com.goodkredit.myapplication.base.BaseFragment;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Created by Ban_Lenovo on 10/25/2017.
 */

public class GKServicesFragment extends BaseFragment {

    private List<GKService> gkServices = new ArrayList<>();
    private RecyclerView gView;
    private GKServicesRecyclerViewAdapter adapter;

    private DatabaseHandler db;

    private int PAGE;
    private int nof = 0;

    public static GKServicesFragment newInstance(int pageindex) {
        GKServicesFragment fragment = new GKServicesFragment();
        Bundle b = new Bundle();
        b.putInt("PAGE_INDEX", pageindex);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PAGE = getArguments().getInt("PAGE_INDEX") + 1;
        Logger.debug("PAGE", String.valueOf(PAGE));
    }

    @Override
    public void onResume() {
        super.onResume();
        new getGKServicesForPageTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gk_services, container, false);

        gView = view.findViewById(R.id.gks_gridView);
        gView.setLayoutManager(new GridLayoutManager(getViewContext(), 4));
        gView.setNestedScrollingEnabled(false);
        gView.setItemAnimator(new SlideInLeftAnimator());
        gView.addItemDecoration(new SpacesItemDecoration(0));

        db = new DatabaseHandler(getViewContext());

        new getGKServicesForPageTask().execute();

        return view;
    }

    private class getGKServicesForPageTask extends AsyncTask<List<GKService>, Void, List<GKService>> {

        @Override
        protected List<GKService> doInBackground(List<GKService>... lists) {
            gkServices = db.getGKServicesForPage(db, PAGE, nof);
            return gkServices;
        }

        @Override
        protected void onPostExecute(List<GKService> servicesData) {
            if (servicesData.size() > 0) {
                adapter = new GKServicesRecyclerViewAdapter(getViewContext(), servicesData, false);
                gView.setAdapter(adapter);
            }
        }
    }
}
