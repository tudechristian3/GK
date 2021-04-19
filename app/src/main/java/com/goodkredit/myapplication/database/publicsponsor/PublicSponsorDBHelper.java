package com.goodkredit.myapplication.database.publicsponsor;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;

import java.util.ArrayList;
import java.util.List;

public class PublicSponsorDBHelper {

    public static String TABLE_NAME = "PublicSponsors";

    public static String ID = "ID";
    public static String GuarantorID = "GuarantorID";
    public static String GuarantorName = "GuarantorName";
    public static String GuarantorPrefix = "GuarantorPrefix";
    public static String GuarantorType = "GuarantorType";
    public static String GuarantorGroup = "GuarantorGroup";
    public static String GuarantorStatus = "GuarantorStatus";
    public static String BillingAddress = "BillingAddress";
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
    public static String AdditionalTelNo = "AdditionalTelNo";
    public static String AuthorisedMobileNo = "AuthorisedMobileNo";
    public static String AdditionalMobileNo = "AdditionalMobileNo";
    public static String Fax = "Fax";
    public static String NatureOfBusiness = "NatureOfBusiness";
    public static String TIN = "TIN";
    public static String OrganizationType = "OrganizationType";
    public static String isPublic = "isPublic";
    public static String DateAdded = "DateAdded";
    public static String AddedBy = "AddedBy";
    public static String LastUpdated = "LastUpdated";
    public static String LastUpdatedBy = "LastUpdatedBy";
    public static String Extra1 = "Extra1";
    public static String Extra2 = "Extra2";
    public static String Extra3 = "Extra3";
    public static String Extra4 = "Extra4";
    public static String Notes1 = "Notes1";
    public static String Notes2 = "Notes2";

    public static String CREATE_STATEMENT = DBUtils.CT_IF_NOT_EXISTS + TABLE_NAME + DBUtils.GENERIC_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY + DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            GuarantorID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorName + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorPrefix + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorGroup + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            GuarantorStatus + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            BillingAddress + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
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
            AdditionalTelNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AuthorisedMobileNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AdditionalMobileNo + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Fax + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            NatureOfBusiness + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            TIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            OrganizationType + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            isPublic + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            DateAdded + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            AddedBy + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            LastUpdated + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            LastUpdatedBy + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra1+ DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Extra4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            Notes2 + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    public static String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_NAME;

    public static ContentValues insert(PublicSponsor data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID, data.getID());
        contentValues.put(GuarantorID, data.getGuarantorID());
        contentValues.put(GuarantorName, data.getGuarantorName());
        contentValues.put(GuarantorPrefix, data.getGuarantorPrefix());
        contentValues.put(GuarantorType, data.getGuarantorType());
        contentValues.put(GuarantorGroup, data.getGuarantorGroup());
        contentValues.put(GuarantorStatus, data.getGuarantorStatus());
        contentValues.put(BillingAddress, data.getBillingAddress());
        contentValues.put(StreetAddress, data.getStreetAddress());
        contentValues.put(City, data.getCity());
        contentValues.put(Province, data.getProvince());
        contentValues.put(Zip, data.getZip());
        contentValues.put(Country, data.getCountry());
        contentValues.put(Longitude, data.getLongitude());
        contentValues.put(Latitude, data.getLatitude());
        contentValues.put(Representative, data.getRepresentative());
        contentValues.put(AuthorisedEmailAddress, data.getAuthorisedEmailAddress());
        contentValues.put(AuthorisedTelNo, data.getAuthorisedTelNo());
        contentValues.put(AdditionalTelNo, data.getAdditionalTelNo());
        contentValues.put(AuthorisedMobileNo, data.getAuthorisedMobileNo());
        contentValues.put(AdditionalMobileNo, data.getAdditionalMobileNo());
        contentValues.put(Fax, data.getFax());
        contentValues.put(NatureOfBusiness, data.getNatureOfBusiness());
        contentValues.put(TIN, data.getTIN());
        contentValues.put(OrganizationType, data.getOrganizationType());
        contentValues.put(isPublic, data.getIsPublic());
        contentValues.put(DateAdded, data.getDateAdded());
        contentValues.put(AddedBy, data.getAddedBy());
        contentValues.put(LastUpdated, data.getLastUpdated());
        contentValues.put(LastUpdatedBy, data.getLastUpdatedBy());
        contentValues.put(Extra1, data.getExtra1());
        contentValues.put(Extra2, data.getExtra2());
        contentValues.put(Extra3, data.getExtra3());
        contentValues.put(Extra4, data.getExtra4());
        contentValues.put(Notes1, data.getNotes1());
        contentValues.put(Notes2, data.getNotes2());
        return contentValues;
    }

    public static List<PublicSponsor> getPublicSponsors (Cursor cursor){
        List<PublicSponsor> publicsponsors = new ArrayList<>();
        if(cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(ID));
                String guarantorid = cursor.getString(cursor.getColumnIndex(GuarantorID));
                String guarantorname = cursor.getString(cursor.getColumnIndex(GuarantorName));
                String guarantorprefix = cursor.getString(cursor.getColumnIndex(GuarantorPrefix));
                String guarantortype = cursor.getString(cursor.getColumnIndex(GuarantorType));
                String guarantorgroup = cursor.getString(cursor.getColumnIndex(GuarantorGroup));
                String guarantorstatus = cursor.getString(cursor.getColumnIndex(GuarantorStatus));
                String billingaddress = cursor.getString(cursor.getColumnIndex(BillingAddress));
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
                String additionaltelno = cursor.getString(cursor.getColumnIndex(AdditionalTelNo));
                String authorisedmobileno = cursor.getString(cursor.getColumnIndex(AuthorisedMobileNo));
                String additionalmobileno = cursor.getString(cursor.getColumnIndex(AdditionalMobileNo));
                String fax = cursor.getString(cursor.getColumnIndex(Fax));
                String natureofbusiness = cursor.getString(cursor.getColumnIndex(NatureOfBusiness));
                String tin = cursor.getString(cursor.getColumnIndex(TIN));
                String organizationtype = cursor.getString(cursor.getColumnIndex(OrganizationType));
                String ispublic = cursor.getString(cursor.getColumnIndex(isPublic));
                String dateadded = cursor.getString(cursor.getColumnIndex(DateAdded));
                String addedby = cursor.getString(cursor.getColumnIndex(AddedBy));
                String lastupdated = cursor.getString(cursor.getColumnIndex(LastUpdated));
                String lastupdatedby = cursor.getString(cursor.getColumnIndex(LastUpdatedBy));
                String extra1 = cursor.getString(cursor.getColumnIndex(Extra1));
                String extra2 = cursor.getString(cursor.getColumnIndex(Extra2));
                String extra3 = cursor.getString(cursor.getColumnIndex(Extra3));
                String extra4 = cursor.getString(cursor.getColumnIndex(Extra4));
                String notes1 = cursor.getString(cursor.getColumnIndex(Notes1));
                String notes2 = cursor.getString(cursor.getColumnIndex(Notes2));

                publicsponsors.add(new PublicSponsor(
                        id,
                        guarantorid,
                        guarantorname,
                        guarantorprefix,
                        guarantortype,
                        guarantorgroup,
                        guarantorstatus,
                        billingaddress,
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
                        additionaltelno,
                        authorisedmobileno,
                        additionalmobileno,
                        fax,
                        natureofbusiness,
                        tin,
                        organizationtype,
                        ispublic,
                        dateadded,
                        addedby,
                        lastupdated,
                        lastupdatedby,
                        extra1,
                        extra2,
                        extra3,
                        extra4,
                        notes1,
                        notes2
                ));
            }
        }
        return publicsponsors;
    }
}
