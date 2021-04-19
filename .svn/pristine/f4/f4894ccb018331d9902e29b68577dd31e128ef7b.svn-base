package com.goodkredit.myapplication.common;

import android.os.Build;

import com.goodkredit.myapplication.BuildConfig;


/**
 * Created by user on 8/2/2016.
 */

public class CommonVariables {

    //Current Version
    public static final String version = "90";

    //THE DEV MEISTER
    public static final String DEV_MEISTER = "GODV";
//    public static final String DEV_MEISTER = "BANANA";

//---------------DEV PORT REFERENCES-------------------------
//  public static final String link = "http://172.16.16.100:8089/"; DEV
//  public static final String link = "http://172.16.16.111:9093/"; ANTON
//  public static final String link = "http://172.16.16.145:9090/"; BAN
//  public static final String link = "http://172.16.16.112:9092/"; KRIS
//  public static final String link = "http://172.16.16.112:9096/"; KRIS
//  public static final String link = "http://172.16.16.191:9094/"; VANESSA
//  public static final String link = "http://172.16.16.248:8096/"; RONEL
//  public static final String link = "http://172.16.16.192:8097/"; KENJI
//  public static String link = "http://c4bf3372.ngrok.io/";
//   public static String securedLink = "https://05b7a068.ngrok.io/";

    //NEW APPROACH
    public static final String link = BuildConfig.OLD_LINK;
    public static final String securedLink = BuildConfig.SECURED_LINK;
    public static final String link1 = BuildConfig.PUSH_NOTIFICATION_LINK;
    public static final String s3link = BuildConfig.S3_LINK;
    public static final boolean isDebugMode = Boolean.parseBoolean(BuildConfig.DEBUG_MODE);
    public static final String BUCKETNAME = BuildConfig.BUCKET_NAME;
    public static final String SUPPORT_BUCKETNAME = BuildConfig.SUPPORT_BUCKET_NAME;
    public static final String FREENETKEY = BuildConfig.FREE_NET_KEY;
    public static final String SPONSORTERMSANDCONDITIONURL = BuildConfig.SPONSOR_TERMS_AND_CONDITION_URL;
    public static final boolean isHttpLogs = Boolean.parseBoolean(BuildConfig.HTTP_LOGS);
    public static final boolean isEncryptOrDecrypt = Boolean.parseBoolean(BuildConfig.ENCRYPT);
    public static final String TERMSGUARANTORSIGNUP = BuildConfig.TERMS_GUARANTOR_SIGN_UP;

    //public static final String VERIFICATIONURL = link + "api/verifymobileV2";
    public static final String GETGUARANTORURL = link + "api/getGuarantorList";
    public static final String GETSETTLEMENTHISTORY = link + "api/getSettleHistory";
    public static final String GET_PURCHASES_TRANSACTIONS = link + "api/getPrepaidTransaction";
    public static final String GETCONSUMMATIONTRANSACTION = link + "api/getConsummation";
    public static final String UPDATEPASSWORDURL = link + "api/updatePassword";
    public static final String GETVOUCHERS = link + "api/getVoucherV2";
    public static final String TRANSFERVOUCHER = link + "api/transferVoucher";
    public static final String TRANSFERVALIDATESMSEMAL = link + "api/validateSMSEMAIL";
    public static final String CHECKVERIFICATIONCODE = link + "api/checkVerificationCode";
    public static final String GENERATENEWPIN = link + "api/generateNewPIN";
    public static final String SAVEPROFILEPICURL = link + "api/updateProfilePic";
    public static final String PREPURCHASE = link + "api/prePurchase";
    public static final String UPDATENOTIFICATIONSTATUS = link + "api/updateNotificationStatus";
    public static final String GET_HELP_TOPIC = link + "api/getHelpTopic";
    public static final String GET_FAQ = link + "api/getHelpFAQ";
    public static final String DELETEBORROWEBILLER = link + "api/deleteBorrowerBiller";
    public static final String PROCCESSRETAILERLOADING = link + "api/processConsummationSmartRetailerLoadingV2";
    public static final String CHECKRETAILERLOADINGTXNSTAT = link + "api/checkRetailerLoadingTransactionStatus";
    public static final String GETSMARTRETAILERDISCOUNT = link + "api/getSmartRetailerDiscount";
    public static final String GETAMZCRED = link + "api/getAmzCred";

    //for push notification
    public static String SOCKETID = "";
    public static final String UNSUBSCRIBEPUSH = link1 + "delete/";
    public static final String REGISTERPUSH = link1;

    public static final String SEND_SUPPORT_MESSAGE = link1 + "sendsupportmessage";
    public static final String GET_SUPPORT_MESSAGES_THREADS = link1 + "fetchSupportMessageThreads";
    public static final String GET_SUPPORT_MESSAGES_CONVERSATION = link1 + "fetchSupportMessageConversation";
    public static final String REOPEN_THREAD = link1 + "openClosedTicket";

    public static String TRANSFERAUTHENTICATION = ""; //this is used when do transfer of voucher, holder for the authentication value

    //push
    public static final int PERMISSION_REQUEST_CODE = 1;
    public static final String REGISTRATION_PROCESS = "registration";
    public static final String MESSAGE_RECEIVED = "message_received";

    //new voucher received
    public static boolean ISNEWVOUCHER = false;

    //list query indicators
    public static boolean VOUCHERISFIRSTLOAD = false;
    public static String BORROWINGSISFIRSTLOAD = "true";
    public static boolean CONSUMMATIONISFIRSTLOAD = true;
    public static String TRANSFERREDFIRSTLOAD = "true";
    public static String PAIDBILLISFIRSTLOAD = "true";
    public static String UNPAINBILLISFIRSTLOAD = "true";
    public static String MERCHANTISFIRSTLOAD = "true";
    public static String GUARANTORSISFIRSTLOAD = "true";
    public static String NOTIFICATIONFIRSTLOAD = "true";
    public static boolean MY_SPONSORS_ISFIRSTLOAD = true;
    public static boolean SUPPORT_MESSAGES_ISFIRSTLOAD = true;
    public static boolean GROUP_SUPPORT_MESSAGES_ISFIRSTLOAD = true;
    public static boolean HELP_TOPIC_ISFIRSTLOAD = true;
    public static boolean FAQ_ISFIRSTLOAD = true;
    public static boolean PURCHASES_TRANSACTIONS_ISFIRTLOAD = true;
    public static boolean BILLERSFIRSTLOAD = true;
    public static boolean BILLS_FIRSTLOAD = true;
    public static boolean PAYMENTS_FIRSTLOAD = true;
    public static String PROCESSNOTIFICATIONINDICATOR = "";
    public static int notifCount = 0;

    public static void clearVariables() {

        TRANSFERAUTHENTICATION = "";
        VOUCHERISFIRSTLOAD = true;
        BORROWINGSISFIRSTLOAD = "true";
        CONSUMMATIONISFIRSTLOAD = true;
        TRANSFERREDFIRSTLOAD = "true";
        PAIDBILLISFIRSTLOAD = "true";
        UNPAINBILLISFIRSTLOAD = "true";
        MERCHANTISFIRSTLOAD = "true";
        GUARANTORSISFIRSTLOAD = "true";
        NOTIFICATIONFIRSTLOAD = "true";
        MY_SPONSORS_ISFIRSTLOAD = true;
        SUPPORT_MESSAGES_ISFIRSTLOAD = true;
        HELP_TOPIC_ISFIRSTLOAD = true;
        FAQ_ISFIRSTLOAD = true;
        GROUP_SUPPORT_MESSAGES_ISFIRSTLOAD = true;
        PURCHASES_TRANSACTIONS_ISFIRTLOAD = true;
        BILLS_FIRSTLOAD = true;
        PAYMENTS_FIRSTLOAD = true;
        notifCount = 0;
        PROCESSNOTIFICATIONINDICATOR = "";
    }

    //DEVICE and USER
    public static final String usertype = "BORROWER";
    public static final String devicetype = "ANDROID";

    //PLACES API KEY
    public static String placesAPIKey = "AIzaSyASnOCtIJeeIz37NyJfpbV7-lvOrJPo1mo";

    //REFERRAL GENERATION TYPE
    public static String gkphreferral = "GKPH_REFERRAL";
    public static String gkearnreferral = "GKEARN_REFERRAL";

    //FOR BETA TESTING GK EARN
    public static boolean GKEARNBETATEST = false;

}
