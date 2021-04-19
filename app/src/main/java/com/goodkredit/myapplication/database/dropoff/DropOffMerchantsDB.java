package com.goodkredit.myapplication.database.dropoff;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.dropoff.DropOffMerchants;

import java.util.ArrayList;
import java.util.List;

public class DropOffMerchantsDB {

    public static String TABLE_NAME = "DropOffMerchants";

    public static String ID = "ID";
    public static String MerchantID = "MerchantID";
    public static String MerchantName = "MerchantName";
    public static String MerchantType = "MerchantType";
    public static String MerchantGroup = "MerchantGroup";
    public static String MerchantStatus = "MerchantStatus";
    public static String StreetAddress = "StreetAddress";
    public static String City = "City";
    public static String Province = "Province";
    public static String Zip = "Zip";
    public static String Country = "Country";
    public static String Longitude = "Longitude";
    public static String Latitude = "Latitude";
    public static String Representative = "Representative";
    public static String AuthorisedEmailAddress = "AuthorisedEmailAddress";
    public static String AuthorisedTelNo = "AuthorisedTelNo";
    public static String AuthorisedMobileNo = "AuthorisedMobileNo";
    public static String Fax = "Fax";
    public static String NatureOfBusiness = "NatureOfBusiness";
    public static String TIN = "TIN";
    public static String OrganizationType = "OrganizationType";
    public static String NoOfBranches = "NoOfBranches";
    public static String DateAdded = "DateAdded";
    public static String AddedBy = "AddedBy";
    public static String LastUpdated = "LastUpdated";
    public static String LastUpdatedBy = "LastUpdatedBy";
    public static String MerchantLogo = "MerchantLogo";
    public static String isFeatured = "isFeatured";
    public static String FeatureAddsPath = "FeatureAddsPath";
    public static String Extra1 = "Extra1";
    public static String Extra2 = "Extra2";
    public static String Extra3 = "Extra3";
    public static String Extra4 = "Extra4";
    public static String Notes1 = "Notes1";
    public static String Notes2 = "Notes2";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS + TABLE_NAME + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            MerchantID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantGroup + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantStatus + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            StreetAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            City + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Province + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Zip + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            Country + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Longitude + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Latitude + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Representative + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AuthorisedEmailAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AuthorisedTelNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AuthorisedMobileNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Fax + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            NatureOfBusiness + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            OrganizationType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            NoOfBranches + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            DateAdded + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AddedBy + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            LastUpdated + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            LastUpdatedBy + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            MerchantLogo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            isFeatured + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            FeatureAddsPath + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra1+ DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(DropOffMerchants data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(MerchantID, data.getMerchantID());
        contentValues.put(MerchantName, data.getMerchantName());
        contentValues.put(MerchantType, data.getMerchantType());
        contentValues.put(MerchantGroup, data.getMerchantGroup());
        contentValues.put(MerchantStatus, data.getMerchantStatus());
        contentValues.put(StreetAddress, data.getStreetAddress());
        contentValues.put(City, data.getCity());
        contentValues.put(Zip, data.getZip());
        contentValues.put(Country, data.getCountry());
        contentValues.put(Longitude, data.getLongitude());
        contentValues.put(Latitude, data.getLatitude());
        contentValues.put(Representative, data.getRepresentative());
        contentValues.put(AuthorisedEmailAddress, data.getAuthorisedEmailAddress());
        contentValues.put(AuthorisedTelNo, data.getAuthorisedTelNo());
        contentValues.put(AuthorisedMobileNo, data.getAuthorisedMobileNo());
        contentValues.put(Fax, data.getFax());
        contentValues.put(NatureOfBusiness, data.getNatureOfBusiness());
        contentValues.put(TIN, data.getTIN());
        contentValues.put(OrganizationType, data.getOrganizationType());
        contentValues.put(NoOfBranches, data.getNoOfBranches());
        contentValues.put(DateAdded, data.getDateAdded());
        contentValues.put(AddedBy, data.getAddedBy());
        contentValues.put(LastUpdated, data.getLastUpdated());
        contentValues.put(LastUpdatedBy, data.getLastUpdatedBy());
        contentValues.put(MerchantLogo, data.getMerchantLogo());
        contentValues.put(isFeatured, data.getIsFeatured());
        contentValues.put(FeatureAddsPath, data.getFeaturedAddsPath());
        contentValues.put(Extra1, data.getExtra1());
        contentValues.put(Extra2, data.getExtra2());
        contentValues.put(Extra3, data.getExtra3());
        contentValues.put(Extra4, data.getExtra4());
        contentValues.put(Notes1, data.getNotes1());
        contentValues.put(Notes2, data.getNotes2());
        return contentValues;
    }

    public static List<DropOffMerchants> getDropOffMerchants (Cursor cursor){
        List<DropOffMerchants> completedrequest = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String merchantid = cursor.getString(cursor.getColumnIndex(MerchantID));
                String merchantname = cursor.getString(cursor.getColumnIndex(MerchantName));
                String merchanttype = cursor.getString(cursor.getColumnIndex(MerchantType));
                String merchantgroup = cursor.getString(cursor.getColumnIndex(MerchantGroup));
                String merchantstatus = cursor.getString(cursor.getColumnIndex(MerchantStatus));
                String streetaddress = cursor.getString(cursor.getColumnIndex(StreetAddress));
                String city = cursor.getString(cursor.getColumnIndex(City));
                String province = cursor.getString(cursor.getColumnIndex(Province));
                int zip = cursor.getInt(cursor.getColumnIndex(Zip));
                String country = cursor.getString(cursor.getColumnIndex(Country));
                String longitude = cursor.getString(cursor.getColumnIndex(Longitude));
                String latitude = cursor.getString(cursor.getColumnIndex(Latitude));
                String representative = cursor.getString(cursor.getColumnIndex(Representative));
                String authorisedemailaddress = cursor.getString(cursor.getColumnIndex(AuthorisedEmailAddress));
                String authorisedtelno = cursor.getString(cursor.getColumnIndex(AuthorisedTelNo));
                String authorisedmobileno = cursor.getString(cursor.getColumnIndex(AuthorisedMobileNo));
                String fax = cursor.getString(cursor.getColumnIndex(Fax));
                String natureofbusiness = cursor.getString(cursor.getColumnIndex(NatureOfBusiness));
                String tin = cursor.getString(cursor.getColumnIndex(TIN));
                String organizationtype = cursor.getString(cursor.getColumnIndex(OrganizationType));
                int noofbranches = cursor.getInt(cursor.getColumnIndex(NoOfBranches));
                String dateadded = cursor.getString(cursor.getColumnIndex(DateAdded));
                String addedby = cursor.getString(cursor.getColumnIndex(AddedBy));
                String lastupdated = cursor.getString(cursor.getColumnIndex(LastUpdated));
                String lastupdatedby = cursor.getString(cursor.getColumnIndex(LastUpdatedBy));
                String merchantlogo = cursor.getString(cursor.getColumnIndex(MerchantLogo));
                String isfeatured = cursor.getString(cursor.getColumnIndex(isFeatured));
                String featuredaddspath = cursor.getString(cursor.getColumnIndex(FeatureAddsPath));
                String extra1 = cursor.getString(cursor.getColumnIndex(Extra1));
                String extra2 = cursor.getString(cursor.getColumnIndex(Extra2));
                String extra3 = cursor.getString(cursor.getColumnIndex(Extra3));
                String extra4 = cursor.getString(cursor.getColumnIndex(Extra4));
                String notes1 = cursor.getString(cursor.getColumnIndex(Notes1));
                String notes2 = cursor.getString(cursor.getColumnIndex(Notes2));

                completedrequest.add(new DropOffMerchants(
                        id,
                        merchantid,
                        merchantname,
                        merchanttype,
                        merchantgroup,
                        merchantstatus,
                        streetaddress,
                        city,
                        province,
                        zip,
                        country,
                        longitude,
                        latitude,
                        representative,
                        authorisedemailaddress,
                        authorisedtelno,
                        authorisedmobileno,
                        fax,
                        natureofbusiness,
                        tin,
                        organizationtype,
                        noofbranches,
                        dateadded,
                        addedby,
                        lastupdated,
                        lastupdatedby,
                        merchantlogo,
                        isfeatured,
                        featuredaddspath,
                        extra1,
                        extra2,
                        extra3,
                        extra4,
                        notes1,
                        notes2
                ));
            }
        }
        return completedrequest;
    }
}
