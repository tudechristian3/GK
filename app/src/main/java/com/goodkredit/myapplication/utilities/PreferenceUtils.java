package com.goodkredit.myapplication.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.androidquery.util.Common;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.PrepaidRequest;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.model.coopassistant.CoopRequestInfo;
import com.goodkredit.myapplication.model.GKConfigurations;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantInformation;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantLoanType;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantAccounts;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMemberCredits;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantMembers;
import com.goodkredit.myapplication.model.coopassistant.CoopAssistantMembershipType;
import com.goodkredit.myapplication.model.gkearn.GKEarnStockistPackage;
import com.goodkredit.myapplication.model.gkearn.GKEarnSubscribers;
import com.goodkredit.myapplication.model.prepaidload.PreloadedSignature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ban_Lenovo on 2/28/2017.
 */

public class PreferenceUtils {

    private static final String APP_ID = "gkapp";
    private static final String APP_INTRO_STATUS = "appIntroStatus";
    public static final String KEY_BORROWER_ID = "borroweridval";
    public static final String KEY_BORROWER_NAME = "borrowernameval";
    public static final String KEY_USER_ID = "userid";
    public static final String KEY_USER_MOBILE = "usermobile";
    public static final String KEY_IMEI = "imei";
    public static final String KEY_FREE_MODE = "freeMode";
    public static final String KEY_CURRENT_SERVICES_VERSION = "service_version";
    public static final String KEY_IS_SUBS_RESELLER = "reseller";
    public static final String KEY_IS_REFER_RESSELER_PROMO = "resellerpromo";
    public static final String KEY_IS_REFER_FRIEND_PROMO = "friendpromo";
    public static final String KEY_IS_EXPLORE_VIEWED = "exploreviewed";
    public static final String KEY_LAST_KNOWN_LONGITUDE = "longitude";
    public static final String KEY_LAST_KNOWN_LATITUDE = "latitude";
    //MERCHANT
    public static final String KEY_MERCHANTNAME = "merchantname";
    //GKNEGOSYO
    public static final String KEY_DISTRIBUTORID = "distributorid";
    public static final String KEY_RESELLER = "resellerid";
    public static final String KEY_REDIRECTNEGOSYO = "redirectnegosyo";
    //SCHOOL MINI
    public static final String KEY_SCSERVICECODE = "scservicecode";
    public static final String KEY_SCMERCHANTID = "scmerchantid";
    public static final String KEY_SCMERCHANTNAME = "scmerchantname";
    public static final String KEY_SCMERCHANTSTATUS = "scmerchantstatus";
    //SCHOOL MINI (PAY NOW)
    public static final String KEY_PASSEDSOAID = "passedsoaid";
    public static final String KEY_PASSEDSCHOOLYEAR = "passedschoolyear";
    public static final String KEY_PASSEDSEMESTER = "passedsemester";
    public static final String KEY_PASSEDSEMESTRALFEE = "passedsemestralfee";
    //PAY VIA QR
    public static final String KEY_PVSOURCE = "pvsource";
    public static final String KEY_PVBARCODE = "pvbarcode";
    //MDB
    public static final String KEY_MDPTYPE = "mdptype";

    public static final String KEY_GK_ADS_DISALLOW_POP_UP = "disallowpopup";

    //SKYCABLE
    public static final String KEY_SKY_BUTTON = "skybutton";

    //GKSTORE
    public static final String KEY_STORE_FROM = "storefrom";

    //VOTES
    public static final String KEY_VOTES_FROM = "votesfrom";

    //PROFILE NOT COMPLETE
    public static final String KEY_PROFILENOTCOMPLETE = "profilenotcomplete";

    //SESSION
    public static final String KEY_SESSIONID = "sessionid";

	//FAIRCHILD
    public static final String KEY_FAIRCHILD_FROM = "fairchildfrom";
	
    public static final String KEY_PROMO_FAIR_STATUS = "promo_fair_status";

    //VERSION CODE
    public static final String KEY_VERSION_CODE = "versioncode";

    public static final String KEY_PRELOADED_SIGNATURE = "preloadedsignature";

    //GK COOP
    public static final String KEY_GKCOOPMEMBERCREDITS = "gkcoopmembercredits";

    //GK EARN
    public static final String KEY_GKEARNBUYVOUCHER = "gkearnbuyvoucher";
    public static final String KEY_GKEARNSELECTEDPACKAGE = "gkearnselectedpackage";
    public static final String KEY_GKEARNSTOCKISTSERVICECODE = "gkearnstockistservicecode";
    public static final String KEY_GKEARNSTOCKISTMERCHANTID = "gkearnstockistmerchantid";

    //RFID
    public static final String KEY_RFID_MERCHANTID = "rfidmerchantid";
    public static final String KEY_RFID_SERVICECODE = "rfidservicecode";

    //BUY VOUCHER
    public static final String KEY_BUYVOUCHER_SERVICETYPE = "servicetype";


    //FROM - Temporary Value for Register
    public static final String KEY_FROM = "FROM";

    //-------------COMMON PREFRENCES---------------
    public static void saveStringPreference(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(value));
        editor.apply();
    }

    public static String getStringPreference(Context context, String key) {
        String value = "";
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //value = prefs.getString(key, "");
        String encryptedvalue = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        value = CommonFunctions.decodeToBase64(encryptedvalue);
        return value;
    }

    public static void saveBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putBoolean(key, value);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(String.valueOf(value)));
        editor.apply();
    }

    public static boolean getBooleanPreference(Context context, String key) {
        boolean value = false;
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //value = prefs.getBoolean(key, false);
        String strencryptedvalue = prefs.getString(CommonFunctions.encodeToBase64(key),
                CommonFunctions.encodeToBase64(String.valueOf(value)));
        String strvalue = CommonFunctions.decodeToBase64(strencryptedvalue);
        value = Boolean.parseBoolean(strvalue);
        return value;
    }

    public static void saveStringListPreference(Context context, String key, List<String> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<String> getStringListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    public static void saveIntegerPreference(Context context, String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putBoolean(key, value);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(String.valueOf(value)));
        editor.apply();
    }

    public static int getIntegerPreference(Context context, String key) {
        int value = 0;
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //value = prefs.getBoolean(key, false);
        String strencryptedvalue = prefs.getString(CommonFunctions.encodeToBase64(key),
                CommonFunctions.encodeToBase64(String.valueOf(value)));
        String strvalue = CommonFunctions.decodeToBase64(strencryptedvalue);
        value = Integer.parseInt(strvalue);
        return value;
    }

    //-------------CLEAR PREFRENCES------------
    public static void clearPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static void removePreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(CommonFunctions.encodeToBase64(key));
        editor.apply();
    }

    //------------CUSTOM PREFERENCES------------
    public static void saveBorrowerID(Context context, String borrowerID) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(KEY_BORROWER_ID, borrowerID);
        editor.putString(CommonFunctions.encodeToBase64(KEY_BORROWER_ID), CommonFunctions.encodeToBase64(borrowerID));
        editor.apply();
    }

    public static String getBorrowerId(Context context) {
        return getStringPreference(context, KEY_BORROWER_ID);
    }

    public static void saveUserID(Context context, String userID) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(KEY_USER_ID, userID);
        editor.putString(CommonFunctions.encodeToBase64(KEY_USER_ID), CommonFunctions.encodeToBase64(userID));
        editor.apply();
    }

    public static String getUserId(Context context) {
        return getStringPreference(context, KEY_USER_ID);
    }

    public static void saveUserMobile(Context context, String userMobile) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(KEY_USER_MOBILE, userMobile);
        editor.putString(CommonFunctions.encodeToBase64(KEY_USER_MOBILE), CommonFunctions.encodeToBase64(userMobile));
        editor.apply();
    }

    public static String getKeyUserMobile(Context context) {
        return getStringPreference(context, KEY_USER_MOBILE);
    }

    public static void saveImeiID(Context context, String userID) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(KEY_IMEI, userID);
        editor.putString(CommonFunctions.encodeToBase64(KEY_IMEI), CommonFunctions.encodeToBase64(userID));
        editor.apply();
    }

    public static String getImeiId(Context context) {
        return getStringPreference(context, KEY_IMEI);
    }

    public static void saveAppIntroStatus(Context context, boolean isShownOnce) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putBoolean(APP_INTRO_STATUS, isShownOnce);
        editor.putString(CommonFunctions.encodeToBase64(APP_INTRO_STATUS),
                CommonFunctions.encodeToBase64(String.valueOf(isShownOnce)));
        editor.apply();
    }

    public static boolean getSavedAppIntroStatus(Context context) {
        return getBooleanPreference(context, APP_INTRO_STATUS);
    }

    public static void saveIsFreeMode(Context context, boolean isFreeMode) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putBoolean(KEY_FREE_MODE, isFreeMode);
        editor.putString(CommonFunctions.encodeToBase64(KEY_FREE_MODE),
                CommonFunctions.encodeToBase64(String.valueOf(isFreeMode)));
        editor.apply();
    }

    public static boolean getIsFreeMode(Context context) {
        return getBooleanPreference(context, KEY_FREE_MODE);
    }

    public static void savePrepaidRequestArrayListPreference(Context context, String key, ArrayList<PrepaidRequest> arrList) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<PrepaidRequest> getPrepaidRequestArrayList(Context context, String key) {
        String value = "";
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<PrepaidRequest>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //SESSION ID
    public static void saveSessionID(Context context, String userID) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(KEY_SESSIONID, userID);
        editor.putString(CommonFunctions.encodeToBase64(KEY_SESSIONID), CommonFunctions.encodeToBase64(userID));
        editor.apply();
    }

    public static String getSessionID(Context context) {
        return getStringPreference(context, KEY_SESSIONID);
    }

    //VERSION CODE
    public static void saveGKVersionCodePreference(Context context, int key) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putString(KEY_VERSION_CODE, key);
        editor.putString(CommonFunctions.encodeToBase64(KEY_VERSION_CODE), (CommonFunctions
                .encodeToBase64(String.valueOf(key))));
        editor.apply();
    }

    public static int getGKVersionCode(Context context) {
        return getIntegerPreference(context, KEY_VERSION_CODE);
    }

    //GK SERVICES
    public static void saveGKServicesPreference(Context context, String key, GKService service){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(service);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static GKService getGKServicesPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<GKService>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //PRELOADED DATA
    public static void savePreloadedSignatureListPreference(Context context, String key, List<PreloadedSignature> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<PreloadedSignature> getPreloadedSignaturePreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<PreloadedSignature>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP INFORMATION
    public static void saveCoopInformationListPreference(Context context, String key, List<CoopAssistantInformation> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key),CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<CoopAssistantInformation> getCoopInformationListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<CoopAssistantInformation>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP MEMBERS
    public static void saveCoopMembersListPreference(Context context, String key, List<CoopAssistantMembers> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key),CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<CoopAssistantMembers> getCoopMembersListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<CoopAssistantMembers>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP SERVICES
    public static void saveCoopMenusListPreference(Context context, String key, List<GKConfigurations> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<GKConfigurations> getCoopMenusListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<GKConfigurations>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP MEMBERSHIP
    public static void saveCoopMemberShipTypeListPreference(Context context, String key, List<CoopAssistantMembershipType> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<CoopAssistantMembershipType> getCoopMemberShipTypeListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<CoopAssistantMembershipType>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP LOAN
    public static void saveCoopAssistantLoanTypeListPreference(Context context, String key, List<CoopAssistantLoanType> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<CoopAssistantLoanType> getCoopAssistantLoanTypeListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<CoopAssistantLoanType>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP ACCOUNTS
    public static void saveCoopAccountsListPreference(Context context, String key, List<CoopAssistantAccounts> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<CoopAssistantAccounts> getCoopAccountsListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<CoopAssistantAccounts>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP REQUEST
    public static void saveCoopRequestInfoListPreference(Context context, String key, List<CoopRequestInfo> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<CoopRequestInfo> getCoopRequestInfoListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<CoopRequestInfo>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //COOP MEMBER CREDITS
    public static void saveCoopMemberCreditsListPreference(Context context, String key, List<CoopAssistantMemberCredits> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<CoopAssistantMemberCredits> getCoopMemberCreditsListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<CoopAssistantMemberCredits>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //GK EARN SUBSCRIBERS
    public static void saveGKEarnSubscribersListPreference(Context context, String key, List<GKEarnSubscribers> arrayList){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(arrayList);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static ArrayList<GKEarnSubscribers> getGKEarnSubscribersListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<ArrayList<GKEarnSubscribers>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    //GK EARN PACKAGE SELECTED
    public static void saveGKEarnSelectedPackagePreference(Context context, String key, GKEarnStockistPackage data){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = new Gson().toJson(data);
        //editor.putString(key, json);
        editor.putString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(json));
        editor.apply();
    }

    public static GKEarnStockistPackage getGKEarnSelectedPackagePreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        //String json = prefs.getString(key, null);
        String encryptedjson = prefs.getString(CommonFunctions.encodeToBase64(key), CommonFunctions.encodeToBase64(""));
        String json = CommonFunctions.decodeToBase64(encryptedjson);
        Type type = new TypeToken<GKEarnStockistPackage>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

   /*|********************SECURITY UPDATE AS OF OCT. 14,2019*********************************|*/
   //SAVE SHA1 OF OTP
   public static void saveListPreference(Context context, String key, List<String> arrayList){
       SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = prefs.edit();
       String json = new Gson().toJson(arrayList);
       editor.putString(key, json);
       editor.apply();
   }

    public static ArrayList<String> getListPreference(Context context, String key){
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    public static void saveFromValue(Context context, String from) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CommonFunctions.encodeToBase64(KEY_FROM), CommonFunctions.encodeToBase64(from));
        editor.apply();
    }

    public static String getFromValue(Context context) {
        return getStringPreference(context, KEY_FROM);
    }


}
