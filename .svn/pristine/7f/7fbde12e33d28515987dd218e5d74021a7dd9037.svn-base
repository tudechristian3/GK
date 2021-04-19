package com.goodkredit.myapplication.activities.merchants;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.goodkredit.myapplication.R;
import com.goodkredit.myapplication.adapter.AllMerchantsAdapter;
import com.goodkredit.myapplication.base.BaseActivity;
import com.goodkredit.myapplication.bean.Merchant;
import com.goodkredit.myapplication.utilities.GPSTracker;
import com.goodkredit.myapplication.utilities.GlobalDialogs;
import com.goodkredit.myapplication.database.DatabaseHandler;
import com.goodkredit.myapplication.enums.ButtonTypeEnum;
import com.goodkredit.myapplication.enums.DialogTypeEnum;

import java.util.ArrayList;

/**
 * Created by Ban_Lenovo on 4/7/2017.
 */

public class FullMerchantGridActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView fullMerchantGridView;
    private AllMerchantsAdapter adapter;

    private ArrayList<Merchant> currentList;

    private DatabaseHandler db;

    private double longitude;
    private double latitude;
    private double endlat;
    private double endlong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_merchant_grid);
        db = new DatabaseHandler(this);

        currentList = new ArrayList<>();

        int flag = getIntent().getIntExtra("FLAG", -1);

        //initialize GPS for near merchants
        GPSTracker gpsTracker = new GPSTracker(getViewContext());
        longitude = gpsTracker.getLongitude();
        latitude = gpsTracker.getLatitude();

        switch (flag) {
            case 0:
                setupToolbarWithTitle("All Merchants");
                adapter = new AllMerchantsAdapter(this, getAllMerchants());
                currentList = getAllMerchants();
                break;
            case 1:
                setupToolbarWithTitle("Featured Merchants");
                adapter = new AllMerchantsAdapter(this, getFeaturedAllMerchants());
                currentList = getFeaturedAllMerchants();
                break;
            case 2:
                setupToolbarWithTitle("Nearest Merchants");
                adapter = new AllMerchantsAdapter(this, getNearestAllMerchants());
                currentList = getNearestAllMerchants();
                break;
            default:
                //showErrorDialog();
                showErrorDialogNew();
                break;
        }
        fullMerchantGridView = (GridView) findViewById(R.id.fullMerchantGridView);
        fullMerchantGridView.setAdapter(adapter);
        fullMerchantGridView.setOnItemClickListener(this);
    }

    public static void start(Context ctx, int flag) {
        Intent intent = new Intent(ctx, FullMerchantGridActivity.class);
        intent.putExtra("FLAG", flag);
        ctx.startActivity(intent);
    }

    private ArrayList<Merchant> getAllMerchants() {
        ArrayList<Merchant> arrayList = new ArrayList<>();
        try {
            Cursor cur = db.getMerchants(db);
            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex("merchantid"));
                    String mName = cur.getString(cur.getColumnIndex("merchantname"));
                    String street = cur.getString(cur.getColumnIndex("streetaddress"));
                    String city = cur.getString(cur.getColumnIndex("city"));
                    String province = cur.getString(cur.getColumnIndex("province"));
                    String country = cur.getString(cur.getColumnIndex("country"));
                    String telephone = cur.getString(cur.getColumnIndex("telno"));
                    String mobile = cur.getString(cur.getColumnIndex("mobileno"));
                    String nofOutlets = cur.getString(cur.getColumnIndex("noofbranches"));
                    String logo = cur.getString(cur.getColumnIndex("merchantlogo"));
                    String isfeatured = cur.getString(cur.getColumnIndex("isfeatured"));
                    String featureaddspath = cur.getString(cur.getColumnIndex("featuredaddspath"));
                    String longitudev = cur.getString(cur.getColumnIndex("longitude"));
                    String latitudev = cur.getString(cur.getColumnIndex("latitude"));
                    String merchantgroup = cur.getString(cur.getColumnIndex("merchantgroup"));

                    String zip = cur.getString(cur.getColumnIndex("zip"));
                    String email = cur.getString(cur.getColumnIndex("email"));
                    String natureofbusiness = cur.getString(cur.getColumnIndex("natureofbusiness"));

//                    arrayList.add(new Merchant(id, null, mName, null, logo, street, province, country, telephone, mobile, nofOutlets, city, longitudev, latitudev, isfeatured, featureaddspath, merchantgroup));
                    arrayList.add(new Merchant(id, null, mName, null, logo, street, province, country, telephone, mobile, nofOutlets, city, longitudev, latitudev, isfeatured, featureaddspath, merchantgroup,zip, email, natureofbusiness));
                }
            } else {
            }
            cur.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return arrayList;
    }

    private ArrayList<Merchant> getNearestAllMerchants() {
        ArrayList<Merchant> arrayList = new ArrayList<>();
        try {
            Cursor cur = db.getMerchants(db);
            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex("merchantid"));
                    String mName = cur.getString(cur.getColumnIndex("merchantname"));
                    String street = cur.getString(cur.getColumnIndex("streetaddress"));
                    String city = cur.getString(cur.getColumnIndex("city"));
                    String province = cur.getString(cur.getColumnIndex("province"));
                    String country = cur.getString(cur.getColumnIndex("country"));
                    String telephone = cur.getString(cur.getColumnIndex("telno"));
                    String mobile = cur.getString(cur.getColumnIndex("mobileno"));
                    String nofOutlets = cur.getString(cur.getColumnIndex("noofbranches"));
                    String logo = cur.getString(cur.getColumnIndex("merchantlogo"));
                    String isfeatured = cur.getString(cur.getColumnIndex("isfeatured"));
                    String featureaddspath = cur.getString(cur.getColumnIndex("featuredaddspath"));
                    String longitudev = cur.getString(cur.getColumnIndex("longitude"));
                    String latitudev = cur.getString(cur.getColumnIndex("latitude"));
                    String merchantgroup = cur.getString(cur.getColumnIndex("merchantgroup"));

                    String zip = cur.getString(cur.getColumnIndex("zip"));
                    String email = cur.getString(cur.getColumnIndex("email"));
                    String natureofbusiness = cur.getString(cur.getColumnIndex("natureofbusiness"));

                    if (!longitudev.equals(".") || !latitudev.equals(".")) {
                        endlat = Double.parseDouble(latitudev);
                        endlong = Double.parseDouble(longitudev);
                        double distance = (int) distance(latitude, longitude, endlat, endlong);

                        if (distance <= 2) {
//                            arrayList.add(new Merchant(id, null, mName, null, logo, street, province, country, telephone, mobile, nofOutlets, city, longitudev, latitudev, isfeatured, featureaddspath, merchantgroup));
                            arrayList.add(new Merchant(id, null, mName, null,
                                    logo, street, province, country,
                                    telephone, mobile, nofOutlets, city,
                                    longitudev, latitudev, isfeatured, featureaddspath, merchantgroup,
                                    zip, email, natureofbusiness));
                        }
                    }
                }
            } else {

            }
            cur.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return arrayList;
    }

    private ArrayList<Merchant> getFeaturedAllMerchants() {
        ArrayList<Merchant> arrayList = new ArrayList<>();
        try {
            Cursor cur = db.getFeaturedMerchants(db, "1");
            if (cur != null && cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex("merchantid"));
                    String mName = cur.getString(cur.getColumnIndex("merchantname"));
                    String street = cur.getString(cur.getColumnIndex("streetaddress"));
                    String city = cur.getString(cur.getColumnIndex("city"));
                    String province = cur.getString(cur.getColumnIndex("province"));
                    String country = cur.getString(cur.getColumnIndex("country"));
                    String telephone = cur.getString(cur.getColumnIndex("telno"));
                    String mobile = cur.getString(cur.getColumnIndex("mobileno"));
                    String nofOutlets = cur.getString(cur.getColumnIndex("noofbranches"));
                    String logo = cur.getString(cur.getColumnIndex("merchantlogo"));
                    String isfeatured = cur.getString(cur.getColumnIndex("isfeatured"));
                    String featureaddspath = cur.getString(cur.getColumnIndex("featuredaddspath"));
                    String longitudev = cur.getString(cur.getColumnIndex("longitude"));
                    String latitudev = cur.getString(cur.getColumnIndex("latitude"));
                    String merchantgroup = cur.getString(cur.getColumnIndex("merchantgroup"));

                    String zip = cur.getString(cur.getColumnIndex("zip"));
                    String email = cur.getString(cur.getColumnIndex("email"));
                    String natureofbusiness = cur.getString(cur.getColumnIndex("natureofbusiness"));

//                    arrayList.add(new Merchant(id, null, mName, null, logo, street, province, country, telephone, mobile, nofOutlets, city, longitudev, latitudev, isfeatured, featureaddspath, merchantgroup));
                    arrayList.add(new Merchant(id, null, mName, null, logo, street, province, country, telephone, mobile, nofOutlets, city, longitudev, latitudev, isfeatured, featureaddspath, merchantgroup,zip, email, natureofbusiness));
                }
                cur.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //get distance between point in KM
    private double distance(double latitude, double longitude, double finalLatit, double finalLongit) {
        double d = 0;
        try {
            double R = 6371; // km
            double dlong = toRadians(finalLongit - longitude);
            double dlat = toRadians(finalLatit - latitude);

            double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                    Math.cos(toRadians(latitude)) * Math.cos(toRadians(finalLatit)) *
                            Math.sin(dlong / 2) * Math.sin(dlong / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            d = R * c;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d;
    }

    public double toRadians(double deg) {
        return deg * (Math.PI / 180);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        OutletsActivity.start(getViewContext(), currentList.get(position));
        MerchantDetailsActivity.start(getViewContext(), currentList.get(position));
    }

    private void showErrorDialog() {
        new MaterialDialog.Builder(getViewContext())
                .content("Something went wrong. (Error)")
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }

    private void showErrorDialogNew() {
        GlobalDialogs dialog = new GlobalDialogs(getViewContext());

        dialog.createDialog("ERROR", "Something went wrong. (Error)",
                "OK", "", ButtonTypeEnum.SINGLE,
                false, false, DialogTypeEnum.FAILED);

        dialog.hideCloseImageButton();

        View closebtn = dialog.btnCloseImage();
        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        View singlebtn = dialog.btnSingle();
        singlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

    }
}
