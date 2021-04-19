package com.goodkredit.myapplication.activities.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class CountryCodeListActivity extends BaseActivity {

    //Declaration
    private DatabaseHandler db;
    private CommonFunctions cf;
    private CommonVariables cv;
    private Context mcontext;
    private ListView listView;
    private EditText search;
    private String countrylistarry;
    private String[] countrylistarr;

    private String borrowerid = "";
    private ArrayList<HashMap<String, String>> List = new ArrayList<>();
    final ArrayList<HashMap<String, String>> searchCountry = new ArrayList<HashMap<String, String>>();
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Hashmap for ListView
        List = new ArrayList<HashMap<String, String>>();
        listView = (ListView) findViewById(R.id.countrylist);
        search = (EditText) findViewById(R.id.edtSearch);

        //load country
        loadCountry();


        //SEARCH IN THE LIST
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (count == 0) {
                    showCountryList();
                } else {
                    try {
                        searchCountry.clear();

                        for (int i = 0; i < countrylistarr.length; i++) {
                            String[] g = countrylistarr[i].split(",");

                            String countryprefix = g[0];
                            String countrycode = g[1];
                            String country = g[2];

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put("COUNTRY", country);
                            map.put("COUNTRYPREFIX", countryprefix);
                            map.put("COUNTRYCODE", countrycode);

                            //get the text in the EditText
                            String searchString = search.getText().toString();
                            int textLength = searchString.length();
                            if (searchString.equalsIgnoreCase(country.substring(0, textLength))) {
                                searchCountry.add(map);
                            }
                        }


                        showSearchCommunity();

                    } catch (Exception e) {
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        //CLICK ON THE LIST
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //final int myPosition = position;
                listView.setEnabled(false);

                String countryval = ((TextView) view.findViewById(R.id.country)).getText().toString();
                String countrycodeval = ((TextView) view.findViewById(R.id.code)).getText().toString();
                String countryprefix = ((TextView) view.findViewById(R.id.prefix)).getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("Country", countryval);
                returnIntent.putExtra("CountryCode", countrycodeval);
                returnIntent.putExtra("CountryPrefix", countryprefix);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

    /*---------------
  * FUNCTIONS
  * --------------*/

    private void loadCountry() {
        searchCountry.clear();
        String[] rl = this.getResources().getStringArray(R.array.CountryCodes);
        countrylistarr = this.getResources().getStringArray(R.array.CountryCodes);
        for (int i = 0; i < rl.length; i++) {
            String[] g = rl[i].split(",");

            String countryprefix = g[0];
            String countrycode = g[1];
            String country = g[2];

            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();

            // adding each child node to HashMap key => value
            map.put("COUNTRY", country);
            map.put("COUNTRYPREFIX", countryprefix);
            map.put("COUNTRYCODE", countrycode);

            // adding HashList to ArrayList
            List.add(map);
        }

        showCountryList();
    }

    public void showCountryList() {
        ListAdapter adapter = new SimpleAdapter(getBaseContext(),
                List, R.layout.activity_country_list_item, new String[]{
                "COUNTRY", "COUNTRYCODE", "COUNTRYPREFIX"},
                new int[]{R.id.country, R.id.code, R.id.prefix});

        listView.setAdapter(adapter);
    }

    public void showSearchCommunity() {
        ListAdapter adapter = new SimpleAdapter(getBaseContext(),
                searchCountry, R.layout.activity_country_list_item, new String[]{
                "COUNTRY", "COUNTRYCODE", "COUNTRYPREFIX"},
                new int[]{R.id.country, R.id.code, R.id.prefix});

        listView.setAdapter(adapter);

    }

}
