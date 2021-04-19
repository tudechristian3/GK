package com.goodkredit.myapplication.database.gknegosyo;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.gknegosyo.Distributor;

import java.util.ArrayList;
import java.util.List;

public class GKNegosyoDistributorsDBHelper {

    public static String TABLE_NAME = "gknegosyodistributors";

    public static String DistributorID = "DistributorID";
    public static String LastName = "LastName";
    public static String FirstName = "FirstName";
    public static String Longitude = "Longitude";
    public static String Latitude = "Latitude";
    public static String AuthorizedMobile = "AuthorizedMobile";
    public static String AuthorizedEmail = "AuthorizedEmail";
    public static String AuthorizedTelNo = "AuthorizedTelNo";
    public static String StreetAddress = "StreetAddress";
    public static String City = "City";
    public static String Province = "Province";
    public static String ServiceArea = "ServiceArea";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS +
            TABLE_NAME +
            DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            DistributorID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            LastName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            FirstName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Longitude + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Latitude + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AuthorizedMobile + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AuthorizedEmail + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AuthorizedTelNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            StreetAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            City + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Province + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            ServiceArea + DBUtils.DATA_TYPE_INTEGER + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(Distributor data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DistributorID, data.getDistributorID());
        contentValues.put(LastName, data.getLastName());
        contentValues.put(FirstName, data.getFirstName());
        contentValues.put(Longitude, data.getLongitude());
        contentValues.put(Latitude, data.getLatitude());
        contentValues.put(AuthorizedMobile, data.getAuthorizedMobile());
        contentValues.put(AuthorizedEmail, data.getAuthorizedEmail());
        contentValues.put(AuthorizedTelNo, data.getAuthorizedTelNo());
        contentValues.put(StreetAddress, data.getStreetAddress());
        contentValues.put(City, data.getCity());
        contentValues.put(Province, data.getProvince());
        contentValues.put(ServiceArea, data.getServiceArea());
        return contentValues;
    }

    public static List<Distributor> getDistributors(Cursor cursor) {
        List<Distributor> distributors = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String strDistributorID = cursor.getString(cursor.getColumnIndex(DistributorID));
                String strLastName = cursor.getString(cursor.getColumnIndex(LastName));
                String strFirstName = cursor.getString(cursor.getColumnIndex(FirstName));
                String strLongitude = cursor.getString(cursor.getColumnIndex(Longitude));
                String strLatitude = cursor.getString(cursor.getColumnIndex(Latitude));
                String strAuthorizedMobile = cursor.getString(cursor.getColumnIndex(AuthorizedMobile));
                String strAuthorizedEmail = cursor.getString(cursor.getColumnIndex(AuthorizedEmail));
                String strAuthorizedTelNo = cursor.getString(cursor.getColumnIndex(AuthorizedTelNo));
                String strStreetAddress = cursor.getString(cursor.getColumnIndex(StreetAddress));
                String strCity = cursor.getString(cursor.getColumnIndex(City));
                String stProvince = cursor.getString(cursor.getColumnIndex(Province));
                double doubleServiceArea = cursor.getDouble(cursor.getColumnIndex(ServiceArea));

                distributors.add(new Distributor(
                        strDistributorID,
                        strLastName,
                        strFirstName,
                        strLongitude,
                        strLatitude,
                        strAuthorizedMobile,
                        strAuthorizedEmail,
                        strAuthorizedTelNo,
                        strStreetAddress,
                        strCity,
                        stProvince,
                        doubleServiceArea
                ));
            }
        }
        return distributors;
    }
}
