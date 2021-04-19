package com.goodkredit.myapplication.activities.gkservices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.gkservices.AllServicesItemAdapter;
import com.goodkredit.myapplication.adapter.gkservices.GKServicesRecyclerViewAdapter;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.model.Event;
import com.goodkredit.myapplication.model.EventItem;
import com.goodkredit.myapplication.model.HeaderItem;
import com.goodkredit.myapplication.utilities.Logger;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GKSpecificServicesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Intent intent;

    private DatabaseHandler mDb;
    private String title;

    private RecyclerView recyclerView;
    private GKServicesRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gkspecific_services);

        intent = getIntent();
        init();

    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);
        mDb = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerViewSpecific);


        recyclerView.setLayoutManager(new GridLayoutManager(this, CommonFunctions.calculateNoOfColumns(getApplicationContext(), "SERVICES")));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.getItemAnimator().setAddDuration(1500);
        recyclerView.getItemAnimator().setMoveDuration(1500);


        recyclerViewAdapter = new GKServicesRecyclerViewAdapter(this, true);
        recyclerView.setAdapter(recyclerViewAdapter);

        //get intent extra
        title = intent.getStringExtra("category");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);

        List<GKService> gkServices = mDb.getGKSERVICES_MAINMENUBYKeyWord(mDb, title);

        Logger.debug("okhttp","DATAAA: "+new Gson().toJson(gkServices));

        if(gkServices.size() > 0){
            recyclerViewAdapter.updateData(gkServices);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.menu_search,menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.clearFocus();

//        // Detect SearchView icon clicks
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.requestFocus();
                }
            });
//        // Detect SearchView close
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }
            });

//        // Get data in SearchView
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String searchText) {
                    //searchServices(searchText);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String searchText) {
                    searchServices(searchText);
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private void searchServices(String searchText) {
        Logger.debug("okhttp","SEARCH : "+searchText);
        if (!searchText.isEmpty()) {
            List<GKService> services = mDb.getGKSERVICES_MAINMENUUsingKeyWord(mDb,title,searchText);
            Logger.debug("okhttp","SEARCH : "+new Gson().toJson(services));

            if (services.size() > 0) {
                LayoutAnimationController controller =
                        AnimationUtils.loadLayoutAnimation(this, R.anim.layout_to_left);
                recyclerViewAdapter = new GKServicesRecyclerViewAdapter(this,true);
                recyclerView.setLayoutAnimation(controller);
                recyclerView.scheduleLayoutAnimation();
                recyclerViewAdapter.updateData(services);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        }else{
            List<GKService> services1 = mDb.getGKSERVICES_MAINMENUUsingKeyWord(mDb,title,"");
            if (services1.size() > 0) {
                LayoutAnimationController controller1 =
                        AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation);
                recyclerViewAdapter = new GKServicesRecyclerViewAdapter(this,true);
                recyclerView.setLayoutAnimation(controller1);
                recyclerView.scheduleLayoutAnimation();
                recyclerViewAdapter.updateData(services1);
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        }
    }

}
