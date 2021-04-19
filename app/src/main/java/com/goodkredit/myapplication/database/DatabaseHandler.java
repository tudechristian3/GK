package com.goodkredit.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.goodkredit.myapplication.bean.AppVersion;
import com.goodkredit.myapplication.bean.BillsPaymentCategories;
import com.goodkredit.myapplication.bean.BillsPaymentLogs;
import com.goodkredit.myapplication.bean.Branches;
import com.goodkredit.myapplication.bean.GKService;
import com.goodkredit.myapplication.bean.GKServiceBadge;
import com.goodkredit.myapplication.bean.GKStoreMerchants;
import com.goodkredit.myapplication.bean.GKStoreProducts;
import com.goodkredit.myapplication.bean.Merchants;
import com.goodkredit.myapplication.bean.Outlets;
import com.goodkredit.myapplication.bean.ParamountCharges;
import com.goodkredit.myapplication.bean.ParamountPolicyType;
import com.goodkredit.myapplication.bean.ParamountQueue;
import com.goodkredit.myapplication.bean.ParamountVehicleSeriesMaker;
import com.goodkredit.myapplication.bean.ParamountVouchers;
import com.goodkredit.myapplication.bean.PaymentChannels;
import com.goodkredit.myapplication.bean.PaymentSummary;
import com.goodkredit.myapplication.bean.Petron;
import com.goodkredit.myapplication.bean.PrepaidLogs;
import com.goodkredit.myapplication.bean.PrepaidVoucherTransaction;
import com.goodkredit.myapplication.bean.Promotions;
import com.goodkredit.myapplication.bean.PublicSponsorPromos;
import com.goodkredit.myapplication.bean.SkycableAccounts;
import com.goodkredit.myapplication.bean.SkycableConversation;
import com.goodkredit.myapplication.bean.SkycablePPV;
import com.goodkredit.myapplication.bean.SkycablePPVHistory;
import com.goodkredit.myapplication.bean.SkycableRegistration;
import com.goodkredit.myapplication.bean.SkycableSOA;
import com.goodkredit.myapplication.bean.Sponsor;
import com.goodkredit.myapplication.bean.SubscriberBillSummary;
import com.goodkredit.myapplication.bean.Transaction;
import com.goodkredit.myapplication.bean.TransactionDetails;
import com.goodkredit.myapplication.bean.TransactionsVouchers;
import com.goodkredit.myapplication.bean.V2VirtualVoucherRequestQueue;
import com.goodkredit.myapplication.bean.Voucher;
import com.goodkredit.myapplication.bean.VoucherRestriction;
import com.goodkredit.myapplication.bean.VoucherSummary;
import com.goodkredit.myapplication.bean.dropoff.DropOffOrder;
import com.goodkredit.myapplication.common.CommonFunctions;
import com.goodkredit.myapplication.database.Otp.OtpDBHelper;
import com.goodkredit.myapplication.database.coopassistant.CoopMiniBulletinDB;
import com.goodkredit.myapplication.database.coopassistant.CoopMiniSupportConversationDB;
import com.goodkredit.myapplication.database.dropoff.DropOffMerchantsDB;
import com.goodkredit.myapplication.database.dropoff.PaymentRequestCompletedDB;
import com.goodkredit.myapplication.database.dropoff.PaymentRequestPendingDB;
import com.goodkredit.myapplication.database.egame.EGameProductsDBHelper;
import com.goodkredit.myapplication.database.fairchild.FairChildMembersDB;
import com.goodkredit.myapplication.database.freesms.FreeSMSHistoryDBHelper;
import com.goodkredit.myapplication.database.gkearn.GKEarnConversionsPointsDB;
import com.goodkredit.myapplication.database.gknegosyo.GKNegosyoDistributorsDBHelper;
import com.goodkredit.myapplication.database.mdp.MDPBulletinDB;
import com.goodkredit.myapplication.database.mdp.MDPSupportConversationDB;
import com.goodkredit.myapplication.database.publicsponsor.PublicSponsorDBHelper;
import com.goodkredit.myapplication.database.rfid.RFIDDBHelper;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniBulletinDB;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniDtrDB;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniGradesDB;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniPaymentLogsDB;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniStudentDB;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniSupportConversationDB;
import com.goodkredit.myapplication.database.schoolmini.SchoolMiniTuitionFeeDB;
import com.goodkredit.myapplication.database.votes.VotesParticipantsDB;
import com.goodkredit.myapplication.database.votes.VotesPostEventDB;
import com.goodkredit.myapplication.model.CSBRedemption;
import com.goodkredit.myapplication.model.FetchStoreOrderList;
import com.goodkredit.myapplication.model.MedPadalaTransaction;
import com.goodkredit.myapplication.model.MedPadalaVoucherUsed;
import com.goodkredit.myapplication.model.OtpModel.BorrowerOtp;
import com.goodkredit.myapplication.model.RedeemedPromo;
import com.goodkredit.myapplication.model.SupportConversation;
import com.goodkredit.myapplication.model.V2Subscriber;
import com.goodkredit.myapplication.model.dropoff.DropOffMerchants;
import com.goodkredit.myapplication.model.dropoff.PaymentRequest;
import com.goodkredit.myapplication.model.egame.EGameProducts;
import com.goodkredit.myapplication.model.fairchild.FairChildMembers;
import com.goodkredit.myapplication.model.freesms.FreeSMSHistory;
import com.goodkredit.myapplication.model.gkearn.GKEarnConversionPoints;
import com.goodkredit.myapplication.model.gknegosyo.Distributor;
import com.goodkredit.myapplication.model.mdp.MDPBulletin;
import com.goodkredit.myapplication.model.publicsponsor.PublicSponsor;
import com.goodkredit.myapplication.model.GenericBulletin;
import com.goodkredit.myapplication.model.rfid.RFIDInfo;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDtr;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniGrades;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPaymentLogs;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniTuitionFee;
import com.goodkredit.myapplication.model.v2Models.OutletsV2;
import com.goodkredit.myapplication.model.v2Models.TransactionModel;
import com.goodkredit.myapplication.model.votes.VotesParticipants;
import com.goodkredit.myapplication.model.votes.VotesPostEvent;
import com.goodkredit.myapplication.responses.support.GetFAQResponseData;
import com.goodkredit.myapplication.responses.support.GetHelpTopicsResponseData;
import com.goodkredit.myapplication.responses.support.GetSupportConversationResponseData;
import com.goodkredit.myapplication.responses.support.GetSupportMessagesResponseData;
import com.goodkredit.myapplication.utilities.Logger;
import com.goodkredit.myapplication.utilities.PreferenceUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ann on 8/2/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Version
    public static final int database_version = 88;
    //DataBase Name
    public static final String DATABASE_NAME = "goodkreditborrowerapp";

    //Table Name
    public static final String ACCOUNTS = "accounts";
    public static final String ACCOUNTWALLET = "accountwallet";
    public static final String VOUCHERS = "vouchers";
    public static final String VOUCHERRESTRICTIONS = "voucherrestrictions";
    public static final String MERCHANTS = "merchants";
    public static final String GUARANTORS = "guarantors";
    public static final String BORROWINGS = "borrowings";
    public static final String CONSUMMATIONTRANSACTION = "consummationtransaction";
    public static final String CONSUMMATIONTRANSACTIONDETAILS = "consummationtransactiondetails";
    public static final String TRANSFERREDTRANSACTION = "transferredtransaction";
    public static final String UNPAIDBILLS = "unpaidbills";
    public static final String PAIDBILLS = "paidbills";
    public static final String NETWORKPREFIX = "networkprefix";
    public static final String GLOBEPRODUCT = "globeproduct";
    public static final String SMARTPRODUCT = "smartproduct";
    public static final String SUNPRODUCT = "sunproduct";
    public static final String NOTIFICATIONS = "notifications";
    public static final String INDICATORS = "indicators";
    public static final String HAS_AGREED = "agreed";
    public static final String VOUCHERSUMMARY = "vouchersummary";
    public static final String MY_SPONSORS = "new_account_wallet_info";
    private static final String OUTLETS = "outlets";
    private static final String SUPPORT_MESSAGES_CONVERSATION = "support_messages_conversation";
    private static final String SUPPORT_MESSAGES_THREADS = "support_messages_threads";
    private static final String HELP_TOPIC = "help_topic";
    private static final String FAQ = "faq";
    public static final String PURCHASES = "purchases";
    private static final String BILLERS = "billers";
    private static final String MYBILLERS = "mybillers";
    private static final String SUBSCIRBER_BILL = "subscriberbill";
    private static final String SUBSCIRBER_PAYMENT = "subscriberpayment";
    public static final String PREPAID_LOGS = "prepaid_logs";
    public static final String BILLS_PAYMENT_LOGS = "bills_payment_logs";
    public static final String RECEIVED_VOUCHERS = "received_vouchers";
    public static final String TRANSFERRED_VOUCHERS = "transferred_vouchers";
    public static final String BORROWING_VOUCHER_DETAILS = "borrowing_voucher_details";
    public static final String PURCHASES_VOUCHER_DETAILS = "purchases_voucher_details";
    public static final String RETAILER_RELOADING_LOGS = "retailer_reloading_logs";
    public static final String LOADWALLET_PREFIX = "loadwalletprefix";
    public static final String GK_SERVICES = "gk_services";
    public static final String APP_VERSION = "appversion";
    public static final String PAYMENT_CHANNELS = "payment_channels";
    public static final String PAYMENT_BRANCHES = "payment_branches";
    public static final String PROMOTIONS = "promotions";
    public static final String VIRTUAL_VOUCHER_REQUEST = "virtual_voucher_request";
    public static final String CSB_REDEMPTION_HISTORY = "csb_redemption_history";
    public static final String PARAMOUNT_YEAR_MODEL = "paramount_year_model";
    public static final String PARAMOUNT_POLICY_TYPE = "paramount_policy_type";
    public static final String PARAMOUNT_CHARGES = "paramount_charges";
    public static final String PARAMOUNT_QUEUE = "paramount_queue";
    public static final String PARAMOUNT_HISTORY = "paramount_history";
    public static final String PARAMOUNT_PAYMENT_VOUCHERS = "paramount_payment_vouchers";
    public static final String PARAMOUNT_VEHICLE_MAKERS = "paramount_vehicle_makers";
    public static final String REDEEMED_PROMO_HISTORY = "redeemed_promo_history";
    public static final String GK_STORE_PRODUCTS = "merchant_virtual_store_products";
    public static final String GK_STORE_MERCHANTS = "merchant_virtual_store";

    //SKYCABLE
    public static final String SKYCABLE_PPV_CATALOGS = "sky_ppv_catalogs";
    public static final String SKYCABLE_PPV_ACCOUNTS = "sky_ppv_accounts";
    public static final String SKYCABLE_PPV_HISTORY = "sky_ppv_history";
    public static final String SKYCABLE_SOA = "sky_soa";
    public static final String SKYCABLE_REGISTRATIONS = "skycable_registrations";
    public static final String SKYCABLE_REGISTRATIONS_FORPAYMENT = "skycable_registrations_forpayment";
    public static final String SKYCABLE_SUPPORT_CONVERSATIONS = "skycable_support_conversations";
    public static final String SKYCABLE_PAYMENT_VOUCHERS = "skycable_payment_vouchers";
//    public static final String SKYCABLE_PPV_SUBSCRIPTION_QUEUE = "sky_ppv_subscription_queue";

    //DROPOFF
    public static final String DROPOFF_ORDERS_PENDING = "dropoff_orders_pending";
    public static final String DROPOFF_ORDERS_COMPLETED = "dropoff_orders_completed";
    public static final String PUBLIC_SPONSOR_PROMOS = "public_sponsor_promos";

    //COOP
    public static final String PROJECT_COOP_SUPPORT_CONVERSATIONS = "project_coop_support_conversations";

    //MAIN MENU - ADDED Oct. 01,2019
    public static final String GKSERVICES_MENU = "gkservices_mainmenu";

    //OTP Local DB
    public static final String BORROWER_OTP = "borrower_otp";

    private final Context mContext;

    public String CREATE_ACCOUNTS = "CREATE TABLE IF NOT EXISTS " + ACCOUNTS + " " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username text, " +
            "email text, " +
            "mobile text, " +
            "status text, " +
            "authentication text, " +
            "password text, " +
            "borrowerid text," +
            "guarantorid text, " +
            "firstname text," +
            "middlename text, " +
            "lastname text, " +
            "streetaddress text, " +
            "city text, " +
            "country text, " +
            "dateregistration text, " +
            "profilepic text, " +
            "gender text, " +
            "guarantorregistrationstatus text, " +
            "imei text, " +
            "notes1 text)";

    public String CREATE_ACCOUNTWALLET = "CREATE TABLE IF NOT EXISTS " + ACCOUNTWALLET + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, creditlimit TEXT, availablecredits TEXT, usedcredits TEXT, outstandingbalance TEXT, currentbillingbalance TEXT, overduebillingbalance TEXT, advances TEXT, currencyid TEXT)";

    public String CREATE_VOUCHERS = "CREATE TABLE IF NOT EXISTS " + VOUCHERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "serialno text, productid text, amount text, remainingamount text, vouchercode text, voucherpin text, " +
            "lastupdated text, barcode text, GroupBarCode text, GroupName text, GroupVoucherCode text, GroupVoucherPIN text, GroupBalance  text," +
            "GroupVoucherSession text, isPrepaid text, isTransferable text, PayToTheOrderOf text, Particulars text, ReleaseTxnNo text, IssuedBy text," +
            " ApprovedBy text, DateTimeIssued text, DateTimeApproved text, RFIDNumber text, RFIDCardNumber text, RFIDPIN text, LastAddedDateTime text, LastDeductedDateTime text)";

    public String CREATE_VOUCHERRESTRICTIONS = "CREATE TABLE IF NOT EXISTS " + VOUCHERRESTRICTIONS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, productid text, restrictiontype text, restrictedto text)";

    public String CREATE_MERCHANTS = "CREATE TABLE IF NOT EXISTS " + MERCHANTS +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "merchantid text, " +
            "merchantname text," +
            "merchantlogo text, " +
            "merchanttype text, " +
            "merchantgroup text, " +
            "merchantstatus text, " +
            "streetaddress text, " +
            "city text, " +
            "province text, " +
            "zip text, " +
            "country text, " +
            "longitude text, " +
            "latitude text, " +
            "representative text, " +
            "email text, " +
            "telno text, " +
            "mobileno text, " +
            "fax text, " +
            "natureofbusiness text, " +
            "tin text, " +
            "organizationtype text," +
            "noofbranches text," +
            "dateadded text, " +
            "isfeatured text, " +
            "featuredaddspath text, " +
            "OrganizationLogo text)";

    public String CREATE_GUARANTORS = "CREATE TABLE IF NOT EXISTS " + GUARANTORS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, guarantorid text, guarantorname text, guarantortype text, guarantorgroup text, guarantorstatus text, billingaddress text, streetaddress text,city text, province text,country text, representative text, email text, telno text, mobileno text, fax text, natureofbusiness text, tin text, organizationtype text, registrationdate text, notes1 text, longitude text,latitude text)";
    public String CREATE_BORROWINGS = "CREATE TABLE IF NOT EXISTS " + BORROWINGS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, transactionno text, datetimecompleted text, totalvoucheramount text, totalnumvouchers text)";
    public String CREATE_CONSUMMATIONTRANSACTION = "CREATE TABLE IF NOT EXISTS " + CONSUMMATIONTRANSACTION + " (id INTEGER PRIMARY KEY AUTOINCREMENT, MerchantLogo text, transactionno text, merchantid text, merchantname text, merchantbranch text, productid text, voucherserial text, vouchercode text, voucherdenoms text, amountconsumed text, merchantservicecharge text, datecompleted text, merchantreferencecode text, MerchantBranchName text, Extra3 text, Status text)";
    public String CREATE_TRANSFERREDTRANSACTION = "CREATE TABLE IF NOT EXISTS " + TRANSFERREDTRANSACTION + " (id INTEGER PRIMARY KEY AUTOINCREMENT, transactionno text, transfertype text, sourceborrowerid text, sourceborrowername text, recipientborrowerid text, recipientborrowername text, recipientmobileno text, recipientemail text, serialno text, vouchercode text, denoms text, datetimein text, Extra3 text)";
    public String CREATE_UNPAIDBILLS = "CREATE TABLE IF NOT EXISTS " + UNPAIDBILLS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, billingid text, guarantorid text, guarantorname text, statementdate text, duedate text, amount text, advancepayment text, partialpayment text, commission text, netamount text, duebalance text, cutoverperiod text)";
    public String CREATE_PAIDBILLS = "CREATE TABLE IF NOT EXISTS " + PAIDBILLS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, billingid text, guarantorid text, guarantorname text, statementdate text, duedate text, amount text, advancepayment text, partialpayment text, commission text, netamount text, duebalance text, cutoverperiod text, datetimepaid text, paymenttxnno text)";
    public String CREATE_NETWORK_PREFIX = "CREATE TABLE IF NOT EXISTS " + NETWORKPREFIX + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, prefix INTEGER, brand TEXT)";
    public String CREATE_GLOBE_PRODUCT = "CREATE TABLE IF NOT EXISTS " + GLOBEPRODUCT + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, networkid TEXT, minamount TEXT,maxamount TEXT,productcode TEXT,discount TEXT,discountclass TEXT,denomtype TEXT)";
    public String CREATE_SMART_PRODUCT = "CREATE TABLE IF NOT EXISTS " + SMARTPRODUCT + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,networkid TEXT,keyword TEXT, amount TEXT,txntype TEXT,description TEXT ,discountclass TEXT,discount TEXT, buddy TEXT,tnt TEXT,bro TEXT)";
    public String CREATE_SUN_PRODUCT = "CREATE TABLE IF NOT EXISTS " + SUNPRODUCT + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,networkid TEXT ,elpkeyword TEXT,amount TEXT,txntype TEXT,description TEXT,discountclass TEXT,discount TEXT)";
    public String CREATE_NOTIFICATION = "CREATE TABLE IF NOT EXISTS " + NOTIFICATIONS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, txnno TEXT, subject TEXT, status TEXT, sender TEXT,sendericon TEXT,message TEXT,date TEXT,extra1 TEXT,extra2 TEXT)";
    public String CREATE_INDICATORS = "CREATE TABLE IF NOT EXISTS " + INDICATORS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, indicator1 INTEGER, indicator2 TEXT, indicator3 TEXT, indicator4 TEXT, indicator5 TEXT, indicator6 TEXT)";
    public String CREATE_AGREED = "CREATE TABLE IF NOT EXISTS " + HAS_AGREED + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, accepted text)";
    public String CREATE_VOUCHERSUMMARY = "CREATE TABLE IF NOT EXISTS " + VOUCHERSUMMARY + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, productid text,totalvoucher text,totaldenom text,totalbalance text,rfidnumber text,rfidcardnumber text,rfidpin text)";
    public String CREATE_NEW_WALLET_INFO = "CREATE TABLE IF NOT EXISTS " + MY_SPONSORS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, _id text, borrowerid text, currencyid text,creditlimit text,availablecredits text,usedcredits text,outstandingbalance text,currentbillingbalance text,overduebillingbalance text,advances text,lastadvancepaymenttxnno text,lastadvancepaymentdatetime text," +
            "ledgerbeginningbalance text,ledgerrunningbalance text,currentvoucherinventory text,status text,extra1 text,extra2 text,extra3 text,extra4 text" +
            ", notes1 text, notes2 text,guarantorid text, guarantorname text, guarantorpreix text, gstreetaddress text, gcity text, gprovince text, gcountry text,gzip text,gemail text, " +
            "gmobile text,gtelno text, subguarantorid text, subguarantorname text, subguarantorstreetaddress text, subguarantorcity text, subguarantorprovince text, subguarantorcountry text,szip text, subguarantormobile text, subguarantortelno text, subguarantoremail text)";
    private String CREATE_OUTLETS = "CREATE TABLE IF NOT EXISTS " + OUTLETS +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "outletBranchName text, " +
            "outletStreetAddress text, " +
            "outletCity text," +
            "outletProvince text, " +
            "outletCountry text, " +
            "outletTelephone text," +
            "outletMobile text, " +
            "outletMerchantID text,  " +
            "outletBranchID text," +
            "outletAuthorisedRepresentative text," +
            " outletZip text, " +
            "outletLongitude text, " +
            "outletLatitude text, " +
            "outletAuthorisedEmailAddress text, " +
            "outletFax text, " +
            "outletDateAdded text, " +
            "outletStatus text," +
            "outletMerchantGroup text )";

    private String CREATE_SUPPORT_MESSAGES_THREADS = "CREATE TABLE IF NOT EXISTS " + SUPPORT_MESSAGES_THREADS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ID text," +
            "DateTimeIN text," +
            "DateTimeCompleted text," +
            "ThreadID text," +
            "HelpTopic text," +
            "Subject text," +
            "SupportUserID text," +
            "MobileNo text," +
            "GKBorrowerID text," +
            "GKBorrowerName text," +
            "GKIMEI text," +
            "GKEmailAddress text," +
            "NotificationStatus text," +
            "Priority text," +
            "Logo text," +
            "Status text," +
            "Extra1 text," +
            "Extra2 text," +
            "Extra3 text," +
            "Notes1 text," +
            "Notes2 text)";

    private String CREATE_SUPPORT_MESSAGES_CONVERSATION = "CREATE TABLE IF NOT EXISTS " + SUPPORT_MESSAGES_CONVERSATION +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ID text," +
            "DateTimeIN text," +
            "ThreadID text," +
            "ReplySupportUserID text," +
            "ReplySupportUserName text," +
            "ThreadType text," +
            "Message text)";

    private String CREATE_HELP_TOPIC = "CREATE TABLE IF NOT EXISTS " + HELP_TOPIC + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id text, helptopic text,category text, " +
            "priority text, supportdepartment text, extra1 text, extra2 text, extra3 text, notes1 text, notes2 text, logo text, description text)";

    private String CREATE_FAQ = "CREATE TABLE IF NOT EXISTS " + FAQ + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id text, helptopic text, faqid text, question text, answer text," +
            "extra1 text, extra2 text, extra3 text, notes1 text, notes2 text)";

    private String CREATE_PURCHASES_TABLE = "CREATE TABLE IF NOT EXISTS " + PURCHASES +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID text, " +
            "DateTimeIN text, " +
            "DateTimeCompleted text, " +
            "TransactionNo text, " +
            "BorrowingType text, " +
            "GuarantorID text, " +
            "GuarantorName text, " +
            "SubGuarantorID text, " +
            "SubGuarantorName text, " +
            "BorrowerID text, " +
            "BorrowerName text," +
            "TotalNoOfVouchers text, " +
            "TotalVoucherAmount text, " +
            "isThruSubGuarantor text, " +
            "GuarantorPreNoOfVouchers text, " +
            "GuarantorPostNoOfVouchers text, " +
            "SubGuarantorPreNoOfVouchers text, " +
            "SubGuarantorPostNoOfVouchers text, " +
            "BorrowerPreNoOfVouchers text, " +
            "BorrowerPostNoOfVouchers text, " +
            "TransactionMedium text, " +
            "IMEI text, " +
            "UserID text, " +
            "ScheduleProcessID text, " +
            "BorrowScheduleID text, " +
            "Status text, " +
            "VoucherGenerationID text, " +
            "PaymentTxnNo text)";


    private String CREATE_BILLER_TABLE = "CREATE TABLE IF NOT EXISTS " + BILLERS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ServiceProviderBillerCode text, " +
            "BillerCode text, " +
            "BillerName text, " +
            "BillerDescription text, " +
            "ServiceCode text, " +
            "Category text, " +
            "BillerInfo text, " +
            "CustomerSCClass text, " +
            "LogoURL text," +
            "Notes, " +
            "GroupCategory text, " +
            "CategoryLogo text)";


    private String CREATE_MYBILLERS_TABLE = "CREATE TABLE IF NOT EXISTS " + MYBILLERS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ServiceProviderBillerCode text, " +
            "BillerCode text, " +
            "BillerName text, " +
            "BillerDescription text, " +
            "ServiceCode text, " +
            "Category text, " +
            "BillerInfo text, " +
            "CustomerSCClass text, " +
            "LogoURL text," +
            "AccountNo text," +
            "OtherInfo text," +
            "Notes)";


    private String CREATE_SUBCRIBERBILL_TABLE = "CREATE TABLE IF NOT EXISTS " + SUBSCIRBER_BILL +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "DateTimeIN text, " +
            "GuarantorID text, " +
            "GuarantorName text, " +
            "SubGuarantorID text, " +
            "BorrowerID text, " +
            "BorrowerName text," +
            "BillingID text," +
            "StatementDate text," +
            "DueDate text," +
            "Amount integer, " +
            "PeriodFrom text, " +
            "PeriodTo text, " +
            "isNotified text," +
            "Status text, " +
            "Extra1 text, " +
            "Extra2 text, " +
            "Extra3 text, " +
            "Extra4 text, " +
            "Notes1 text, " +
            "Notes2 text)";

    private String CREATE_SUBCRIBERPAYMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + SUBSCIRBER_PAYMENT +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "DateTimeIN text, " +
            "PaymentTxnNo text, " +
            "AccountType text, " +
            "AccountID text, " +
            "AccountName text, " +
            "Amount integer, " +
            "isAdvancePayment text, " +
            "CollectedBy text, " +
            "CollectedByUser text," +
            "CollectionMedium text," +
            "PaymentOption text," +
            "Status text, " +
            "Extra1 text, " +
            "Extra2 text, " +
            "Extra3 text, " +
            "Extra4 text, " +
            "Notes1 text, " +
            "Notes2 text)";

    private String CREATE_CONSUMMATION_TRANSACTION_DETAILS = "CREATE TABLE IF NOT EXISTS " + CONSUMMATIONTRANSACTIONDETAILS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "TransactionDetailsType text, " +
            "TransactionNo text, " +
            "MobileTarget text, " +
            "ProductCode text, " +
            "Amount integer, " +
            "DateTimeCompleted text, " +
            "BillerTransactionNo text, " +
            "GKTransactionNo text, " +
            "BillerName text, " +
            "AccountNo text," +
            "AccountName text," +
            "TotalAmountPaid integer," +
            "Discount integer," +
            "NetAmount integer," +
            "AmountPaid integer)";

    private String CREATE_PREPAID_LOGS = "CREATE TABLE IF NOT EXISTS " + PREPAID_LOGS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "DateTimeCompleted text, " +
            "TransactionNo text, " +
            "MobileTarget text, " +
            "ProductCode text, " +
            "TxnStatus text, " +
            "DenomType text, " +
            "Network text, " +
            "Amount text)";

    private String CREATE_RETAILER_RELOADING_LOGS = "CREATE TABLE IF NOT EXISTS " + RETAILER_RELOADING_LOGS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "DateTimeCompleted text, " +
            "TransactionNo text, " +
            "MobileTarget text, " +
            "ProductCode text, " +
            "TxnStatus text, " +
            "DenomType text, " +
            "Network text, " +
            "Discount integer, " +
            "NetAmount integer, " +
            "Amount text)";

    private String CREATE_BILLS_PAYMENT_LOGS = "CREATE TABLE IF NOT EXISTS " + BILLS_PAYMENT_LOGS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "DateTimeCompleted text, " +
            "BillerTransactionNo text, " +
            "GKTransactionNo text, " +
            "BillerName text, " +
            "AccountNo text," +
            "AccountName text," +
            "TxnStatus text," +
            "ServiceProviderBillerCode text, " +
            "BillerCode text, " +
            "BillerInfo text, " +
            "ServiceCode text, " +
            "CustomerSCClass text, " +
            "BillDetails text, " +
            "BillerLogoURL text, " +
            "OtherCharges integer, " +
            "CustomerServiceCharge integer, " +
            "Extra2 text, " +
            "TotalAmountPaid integer, " +
            "AmountPaid integer)";

    private String CREATE_RECEIVED_VOUCHERS = "CREATE TABLE IF NOT EXISTS " + RECEIVED_VOUCHERS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "DateTimeCompleted text, " +
            "TransferType text, " +
            "SourceBorrowerID text, " +
            "SourceBorrowerName text, " +
            "RecipientBorrowerID text," +
            "RecipientBorrowerName text," +
            "RecipientMobileNo text," +
            "RecipientEmailAddress text," +
            "VoucherSerialNo text," +
            "VoucherCode text," +
            "VoucherDenoms text," +
            "ProcessID text," +
            "Status text," +
            "TransferTxnNo text)";

    private String CREATE_TRANSFERRED_VOUCHERS = "CREATE TABLE IF NOT EXISTS " + TRANSFERRED_VOUCHERS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "DateTimeCompleted text, " +
            "TransferType text, " +
            "SourceBorrowerID text, " +
            "SourceBorrowerName text, " +
            "RecipientBorrowerID text," +
            "RecipientBorrowerName text," +
            "RecipientMobileNo text," +
            "RecipientEmailAddress text," +
            "VoucherSerialNo text," +
            "VoucherCode text," +
            "VoucherDenoms text," +
            "ProcessID text," +
            "Status text," +
            "TransferTxnNo text)";

    private String CREATE_BORROWING_VOUCHER_DETAILS = "CREATE TABLE IF NOT EXISTS " + BORROWING_VOUCHER_DETAILS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "TransactionNo text, " +
            "ProductID text, " +
            "VoucherDenom text, " +
            "VoucherCode text, " +
            "VoucherSerialNo text)";

    private String CREATE_PURCHASES_VOUCHER_DETAILS = "CREATE TABLE IF NOT EXISTS " + PURCHASES_VOUCHER_DETAILS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "TransactionNo text, " +
            "ProductID text, " +
            "VoucherDenom text, " +
            "VoucherCode text, " +
            "VoucherSerialNo text)";

    private String CREATE_GK_SERVICES = "CREATE TABLE IF NOT EXISTS " + GK_SERVICES +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "ServiceCode text, " +
            "ServiceName text, " +
            "Type text, " +
            "Logo text, " +
            "MerchantID text, " +
            "GKStoreID text, " +
            "ServiceType text, " +
            "GKStoreStatus text, " +
            "GuarantorID text, " +
            "Category text,"+
            "Color text,"+
            "Rank integer ,"+
            "Status text)";

    private String CREATE_APP_VERSION = "CREATE TABLE IF NOT EXISTS " + APP_VERSION +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "appversion text, " +
            "serviceversion text)";


    public String CREATE_LOADWALLET_PREFIX = "CREATE TABLE IF NOT EXISTS " + LOADWALLET_PREFIX + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, prefix INTEGER, brand TEXT)";

    private String CREATE_PAYMENT_CHANNELS = "CREATE TABLE IF NOT EXISTS " + PAYMENT_CHANNELS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "NetworkID text, " +
            "NetworkName text, " +
            "NetworkType text, " +
            "NetworkGroup text, " +
            "NetworkStatus text, " +
            "StreetAddress text, " +
            "City text, " +
            "Province text, " +
            "Zip integer, " +
            "Country text, " +
            "Longitude text, " +
            "Latitude text, " +
            "Representative text, " +
            "AuthorisedEmailAddress text, " +
            "AuthorisedTelNo text, " +
            "AuthorisedMobileNo text, " +
            "Fax text, " +
            "NatureOfBusiness text, " +
            "TIN text, " +
            "OrganizationType text, " +
            "NoOfBranches integer, " +
            "DateTimeAdded text, " +
            "AddedBy text, " +
            "Logo text)";

    //payment_branches
    private String CREATE_PAYMENT_BRANCHES = "CREATE TABLE IF NOT EXISTS " + PAYMENT_BRANCHES +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "NetworkID text, " +
            "OutletID text, " +
            "OutletName text, " +
            "AuthorisedRepresentative text, " +
            "StreetAddress text, " +
            "City text, " +
            "Province text, " +
            "Zip integer, " +
            "Country text, " +
            "Longitude text, " +
            "Latitude text, " +
            "AuthorisedEmailAddress text, " +
            "AuthorisedTelNo text, " +
            "AuthorisedMobileNo text, " +
            "Fax text, " +
            "DateTimeAdded text, " +
            "DateTimeUpdated text, " +
            "Status text, " +
            "CalculatedDistance text)";

    private String CREATE_PROMOTIONS = "CREATE TABLE IF NOT EXISTS " + PROMOTIONS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "MerchantID text, " +
            "PromoLink text)";

    private String CREATE_VIRTUAL_VOUCHER_REQUEST_TABLE = "CREATE TABLE IF NOT EXISTS " + VIRTUAL_VOUCHER_REQUEST +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeRequested text, " +
            "DateTimeCompleted text, " +
            "VoucherGenerationID text, " +
            "PaymentTxnNo text, " +
            "PartnerReferenceNo text, " +
            "BorrowingTxnNo text, " +
            "ProcessType text, " +
            "GuarantorID text, " +
            "GuarantorName text, " +
            "GuarantorPrefix text, " +
            "SubGuarantorID text," +
            "SubGuarantorName text, " +
            "BorrowerID text, " +
            "BorrowerName text, " +
            "BorrowerMobileNumber text, " +
            "TotalAmount INTEGER, " +
            "TotalNoOfVouchers INTEGER, " +
            "XMLDetails text, " +
            "Medium text, " +
            "IMEI text, " +
            "PartnerNetworkID text, " +
            "PartnerOutletID text, " +
            "DateTimePartnerPaid text, " +
            "Status text)";


    private String CREATE_CSB_REDEMPTION_HISTORY = "CREATE TABLE IF NOT EXISTS " + CSB_REDEMPTION_HISTORY +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "SendDetails text, " +
            "ReceiveDetails text, " +
            "ResultCode text, " +
            "ResultDescription text, " +
            "DateTimeCompleted text)";

    private String CREATE_REDEEMED_PROMO_HISTORY = "CREATE TABLE IF NOT EXISTS " + REDEEMED_PROMO_HISTORY +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeRedeem text, " +
            "PromoID text, " +
            "GuarantorID text, " +
            "BorrowerID text, " +
            "MobileNumber text, " +
            "IMEI text, " +
            "XMLDetails text, " +
            "Status text, " +
            "Extra1 text, " +
            "Extra2 text, " +
            "Notes1 text, " +
            "Notes2 text, " +
            "PromoName text, " +
            "PromoLogo text" +
            ")";


    /*
    GK SERVICES MAIN MENU
    */
    private String CREATE_GKSERVICES_MENU = "CREATE TABLE IF NOT EXISTS " + GKSERVICES_MENU +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "ServiceCode text, " +
            "ServiceName text, " +
            "Type text, " +
            "Logo text, " +
            "MerchantID text, " +
            "GKStoreID text, " +
            "ServiceType text, " +
            "GKStoreStatus text, " +
            "GuarantorID text, " +
            "Category text,"+
            "Color text,"+
            "Rank integer,"+
            "Status text)";


    /*
    * BORROWER OTP
    * */
    private String CREATE_BORROWER_OTP = "CREATE TABLE IF NOT EXISTS " + BORROWER_OTP +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " UserID text," +
            " OTP text )";

    //***********************
    //PARAMOUNT
    //***********************

    private String CREATE_PARAMOUNT_YEAR_MODEL = "CREATE TABLE IF NOT EXISTS " + PARAMOUNT_YEAR_MODEL +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "YearModel text)";

    private String CREATE_PARAMOUNT_POLICY_TYPE = "CREATE TABLE IF NOT EXISTS " + PARAMOUNT_POLICY_TYPE +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "PolicyTypeID text, " +
            "PolicyTypeName text)";

    private String CREATE_PARAMOUNT_CHARGES = "CREATE TABLE IF NOT EXISTS " + PARAMOUNT_CHARGES +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ChargeID text, " +
            "PolicyTypeID text, " +
            "PolicyTypeName text, " +
            "VehicleTypeID text, " +
            "ApplicationType text, " +
            "VehicleDescription text, " +
            "Amount integer, " +
            "OtherCharges integer, " +
            "Status text)";

    private String CREATE_PARAMOUNT_QUEUE = "CREATE TABLE IF NOT EXISTS " + PARAMOUNT_QUEUE +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "RequestID text, " +
            "VehicleID text, " +
            "VehicleTypeID text, " +
            "ParamountProductID text, " +
            "ParamountProductName text, " +
            "Price integer, " +
            "OtherCharges integer, " +
            "AmountPaid integer, " +
            "Renewal text, " +
            "PolicyTypeID text, " +
            "PolicyType text, " +
            "LTOMVType text, " +
            "YearModel text, " +
            "VehicleMaker text, " +
            "Series text, " +
            "Color text, " +
            "MVFileNumber text, " +
            "PlateNumber text, " +
            "EngineMotorNumber text, " +
            "Serial text, " +
            "Type text, " +
            "CorporateName text, " +
            "FirstName text, " +
            "MiddleName text, " +
            "LastName text, " +
            "HouseNumber text, " +
            "StreetName text, " +
            "BuildingName text, " +
            "Barangay text, " +
            "Province text, " +
            "Municipality text, " +
            "MobileNumber text, " +
            "TelephoneNo text, " +
            "Zipcode text, " +
            "AuthNo text, " +
            "COCNo text, " +
            "EmailAddress text)";

    private String CREATE_PARAMOUNT_HISTORY = "CREATE TABLE IF NOT EXISTS " + PARAMOUNT_HISTORY +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "RequestID text, " +
            "ParamountProductID text, " +
            "ParamountProductName text, " +
            "Price integer, " +
            "OtherCharges integer, " +
            "AmountPaid integer, " +
            "Renewal text, " +
            "PolicyTypeID text, " +
            "PolicyType text, " +
            "LTOMVType text, " +
            "YearModel text, " +
            "VehicleMaker text, " +
            "Series text, " +
            "Color text, " +
            "MVFileNumber text, " +
            "PlateNumber text, " +
            "EngineMotorNumber text, " +
            "Serial text, " +
            "Type text, " +
            "CorporateName text, " +
            "FirstName text, " +
            "MiddleName text, " +
            "LastName text, " +
            "HouseNumber text, " +
            "StreetName text, " +
            "BuildingName text, " +
            "Barangay text, " +
            "Province text, " +
            "Municipality text, " +
            "MobileNumber text, " +
            "TelephoneNo text, " +
            "Zipcode text, " +
            "DateTimeCompleted text, " +
            "TransactionNo text, " +
            "RegistrationID text, " +
            "TxnStatus text, " +
            "AuthNo text, " +
            "COCNo text, " +
            "EmailAddress text)";


    //PARAMOUNT_PAYMENT_VOUCHERS
    private String CREATE_PARAMOUNT_PAYMENT_VOUCHERS = "CREATE TABLE IF NOT EXISTS " + PARAMOUNT_PAYMENT_VOUCHERS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MerchantTransactionNo text, " +
            "TransactionNo text, " +
            "VoucherSeriesNo text, " +
            "VoucherCode text, " +
            "AmountConsumed integer, " +
            "ProductID text, " +
            "Extra3 text, " +
            "VoucherDenom integer)";

    private String CREATE_PARAMOUNT_VEHICLE_MAKERS = "CREATE TABLE IF NOT EXISTS " + PARAMOUNT_VEHICLE_MAKERS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "Maker text, " +
            "VehicleID text, " +
            "VehicleTypeID text, " +
            "Series text)";

    //SKYCABLE_PAYMENT_VOUCHERS
    private String CREATE_SKYCABLE_PAYMENT_VOUCHERS = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_PAYMENT_VOUCHERS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "MerchantTransactionNo text, " +
            "TransactionNo text, " +
            "VoucherSeriesNo text, " +
            "VoucherCode text, " +
            "AmountConsumed integer, " +
            "ProductID text, " +
            "Extra3 text, " +
            "VoucherDenom integer)";

    //***********************

    //SKYCABLE
    private String CREATE_SKYCABLE_PPV_CATALOGS = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_PPV_CATALOGS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "AddedBy text, " +
            "Currency text, " +
            "DateTimeIN text, " +
            "ShowingDateFrom text, " +
            "ShowingDateTo text, " +
            "DiscountPercentage integer, " +
            "DiscountedRate integer, " +
            "GrossRate integer, " +
            "ImageURL text, " +
            "PPVDescription text, " +
            "PPVName text, " +
            "PPVID text, " +
            "Status text, " +
            "SubscriptionCount integer)";

    //SKYCABLE_PPV_ACCOUNTS
    private String CREATE_SKYCABLE_PPV_ACCOUNTS = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_PPV_ACCOUNTS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeIN text, " +
            "SkyCableAccountNo text, " +
            "SkyCableAccountName text, " +
            "MobileNo text, " +
            "Source text, " +
            "Status text)";

    //SKYCABLE_PPV_HISTORY
    private String CREATE_SKYCABLE_PPV_HISTORY = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_PPV_HISTORY +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeIN text, " +
            "DateTimeCompleted text, " +
            "SubscriptionTxnID text, " +
            "PPVID text, " +
            "PPVName text, " +
            "PPVDescription text, " +
            "ImageURL text, " +
            "ShowingDateFrom text, " +
            "ShowingDateTo text, " +
            "SkyCableAccountNo text, " +
            "BorrowerID text, " +
            "BorrowerName text, " +
            "CustomerFirstName text, " +
            "CustomerLastName text, " +
            "CustomerMobileNumber text, " +
            "CustomerEmailAddress text, " +
            "CustomerAddress text, " +
            "City text, " +
            "TxnMedium text, " +
            "Amount text, " +
            "CustomerServiceCharge text, " +
            "TotalAmountPaid text, " +
            "GKPaymentReferenceNo text, " +
            "MerchantID text, " +
            "Longitude text, " +
            "Latitude text, " +
            "ActedBy text, " +
            "Remarks text, " +
            "Status text)";

    //SKYCABLE_SOA
    private String CREATE_SKYCABLE_SOA = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_SOA +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeIN text, " +
            "BillingID text, " +
            "SkyCableAccountNo text, " +
            "SkyCableAccountName text, " +
            "MobileNo text, " +
            "DueFromLastBill integer, " +
            "CurrentCharges integer, " +
            "TotalCharges integer, " +
            "DueDate text, " +
            "SOAURL text, " +
            "UploadedBy text, " +
            "Status text)";

    //SKYCABLE_REGISTRATIONS
    private String CREATE_SKYCABLE_REGISTRATIONS = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_REGISTRATIONS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "PlanName text, " +
            "PlanType text, " +
            "PlanImgUrl text, " +
            "PlanStyle text, " +
            "Description text, " +
            "DateTimeIN text, " +
            "DateTimeCompleted text, " +
            "RegistrationID text, " +
            "BorrowerID text, " +
            "FirstName text, " +
            "LastName text, " +
            "Gender text, " +
            "Birthdate text, " +
            "Address text, " +
            "City text, " +
            "Longitude text, " +
            "Latitude text, " +
            "ZIPCode text, " +
            "BillingAddress text, " +
            "InstallationAddress text, " +
            "EmailAddress text, " +
            "MobileNumber text, " +
            "TelephoneNumber text, " +
            "PlanID text, " +
            "DiscountBase integer, " +
            "DiscountPercentage integer, " +
            "InstallationFee integer, " +
            "MonthlyFee integer, " +
            "InitialCashout integer, " +
            "ServiceCharge integer, " +
            "TotalAmountPaid integer, " +
            "GKPaymentReferenceNo text, " +
            "AccountNo text, " +
            "MerchantID text, " +
            "Status text, " +
            "Notes1 text)";

    //SKYCABLE_REGISTRATIONS_FORPAYMENT
    private String CREATE_SKYCABLE_REGISTRATIONS_FORPAYMENT = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_REGISTRATIONS_FORPAYMENT +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "PlanName text, " +
            "PlanType text, " +
            "PlanImgUrl text, " +
            "PlanStyle text, " +
            "Description text, " +
            "DateTimeIN text, " +
            "DateTimeCompleted text, " +
            "RegistrationID text, " +
            "BorrowerID text, " +
            "FirstName text, " +
            "LastName text, " +
            "Gender text, " +
            "Birthdate text, " +
            "Address text, " +
            "City text, " +
            "Longitude text, " +
            "Latitude text, " +
            "ZIPCode text, " +
            "BillingAddress text, " +
            "InstallationAddress text, " +
            "EmailAddress text, " +
            "MobileNumber text, " +
            "TelephoneNumber text, " +
            "PlanID text, " +
            "DiscountBase integer, " +
            "DiscountPercentage integer, " +
            "InstallationFee integer, " +
            "MonthlyFee integer, " +
            "InitialCashout integer, " +
            "ServiceCharge integer, " +
            "TotalAmountPaid integer, " +
            "GKPaymentReferenceNo text, " +
            "AccountNo text, " +
            "MerchantID text, " +
            "Status text, " +
            "Notes1 text)";


    private String CREATE_PUBLIC_SPONSOR_PROMO = "CREATE TABLE IF NOT EXISTS " + PUBLIC_SPONSOR_PROMOS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT,GuarantorID text,PromoLink text,Title integer,Description text)";

    //SKYCABLE_SUPPORT_CONVERSATIONS
    private String CREATE_SKYCABLE_SUPPORT_CONVERSATIONS = "CREATE TABLE IF NOT EXISTS " + SKYCABLE_SUPPORT_CONVERSATIONS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeIN text, " +
            "ThreadID text, " +
            "ReplySupportUserID integer, " +
            "ReplySupportName text, " +
            "ThreadType text, " +
            "Message text)";

    //PROJECT_COOP_SUPPORT_CONVERSATIONS
    private String CREATE_PROJECT_COOP_SUPPORT_CONVERSATIONS = "CREATE TABLE IF NOT EXISTS " + PROJECT_COOP_SUPPORT_CONVERSATIONS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeIN text, " +
            "ThreadID text, " +
            "ReplySupportUserID integer, " +
            "ReplySupportName text, " +
            "ThreadType text, " +
            "Message text)";

    //DROPOFF_ORDERS_PENDING
    private String CREATE_DROPOFF_ORDERS_PENDING = "CREATE TABLE IF NOT EXISTS " + DROPOFF_ORDERS_PENDING +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeIN text, " +
            "DateTimeCompleted text, " +
            "GKTxnNo text, " +
            "MerchantID text, " +
            "MerchantName text, " +
            "MerchantBranchID text, " +
            "MerchantBranchName text, " +
            "BorrowerID text, " +
            "CustomerMobileNumber text, " +
            "CustomerFirstName text, " +
            "CustomerLastName text, " +
            "CustomerAddress text, " +
            "CustomerEmailAddress text, " +
            "Amount integer, " +
            "MerchantServiceCharge integer, " +
            "CustomerServiceCharge integer, " +
            "OtherCharge integer, " +
            "ResellerDiscount integer, " +
            "TotalAmount integer, " +
            "TransactionMedium text, " +
            "ProcessID text, " +
            "PaymentReferenceNo text, " +
            "PaymentType text, " +
            "BillingID text, " +
            "PartnerNetworkID text, " +
            "PartnerNetworkName text, " +
            "PartnerOutletID text, " +
            "PartnerOutletName text, " +
            "DateTimePaid text, " +
            "PreConsummationSession text, " +
            "DropOffPurpose text, " +
            "DropOffNotes text, " +
            "Status text, " +
            "Extra1 text)";

    private String CREATE_DROPOFF_ORDERS_COMPLETED = "CREATE TABLE IF NOT EXISTS " + DROPOFF_ORDERS_COMPLETED +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "DateTimeIN text, " +
            "DateTimeCompleted text, " +
            "GKTxnNo text, " +
            "MerchantID text, " +
            "MerchantName text, " +
            "MerchantBranchID text, " +
            "MerchantBranchName text, " +
            "BorrowerID text, " +
            "CustomerMobileNumber text, " +
            "CustomerFirstName text, " +
            "CustomerLastName text, " +
            "CustomerAddress text, " +
            "CustomerEmailAddress text, " +
            "Amount integer, " +
            "MerchantServiceCharge integer, " +
            "CustomerServiceCharge integer, " +
            "OtherCharge integer, " +
            "ResellerDiscount integer, " +
            "TotalAmount integer, " +
            "TransactionMedium text, " +
            "ProcessID text, " +
            "PaymentReferenceNo text, " +
            "PaymentType text, " +
            "BillingID text, " +
            "PartnerNetworkID text, " +
            "PartnerNetworkName text, " +
            "PartnerOutletID text, " +
            "PartnerOutletName text, " +
            "DateTimePaid text, " +
            "PreConsummationSession text, " +
            "DropOffPurpose text, " +
            "DropOffNotes text, " +
            "Status text, " +
            "Extra1 text)";

    public List<DropOffOrder> getDropOffOrderPending(DatabaseHandler db) {
        List<DropOffOrder> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + DROPOFF_ORDERS_PENDING;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                String GKTxnNo = c.getString(c.getColumnIndex("GKTxnNo"));
                String merchantID = c.getString(c.getColumnIndex("MerchantID"));
                String merchantName = c.getString(c.getColumnIndex("MerchantName"));
                String merchantBranchID = c.getString(c.getColumnIndex("MerchantBranchID"));
                String merchantBranchName = c.getString(c.getColumnIndex("MerchantBranchName"));
                String borrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                String customerMobileNumber = c.getString(c.getColumnIndex("CustomerMobileNumber"));
                String customerFirstName = c.getString(c.getColumnIndex("CustomerFirstName"));
                String customerLastName = c.getString(c.getColumnIndex("CustomerLastName"));
                String customerAddress = c.getString(c.getColumnIndex("CustomerAddress"));
                String customerEmailAddress = c.getString(c.getColumnIndex("CustomerEmailAddress"));
                double amount = c.getDouble(c.getColumnIndex("Amount"));
                double merchantServiceCharge = c.getDouble(c.getColumnIndex("MerchantServiceCharge"));
                double customerServiceCharge = c.getDouble(c.getColumnIndex("CustomerServiceCharge"));
                double otherCharge = c.getDouble(c.getColumnIndex("OtherCharge"));
                double resellerDiscount = c.getDouble(c.getColumnIndex("ResellerDiscount"));
                double totalAmount = c.getDouble(c.getColumnIndex("TotalAmount"));
                String transactionMedium = c.getString(c.getColumnIndex("TransactionMedium"));
                String processID = c.getString(c.getColumnIndex("ProcessID"));
                String paymentReferenceNo = c.getString(c.getColumnIndex("PaymentReferenceNo"));
                String paymentType = c.getString(c.getColumnIndex("PaymentType"));
                String billingID = c.getString(c.getColumnIndex("BillingID"));
                String partnerNetworkID = c.getString(c.getColumnIndex("PartnerNetworkID"));
                String partnerNetworkName = c.getString(c.getColumnIndex("PartnerNetworkName"));
                String partnerOutletID = c.getString(c.getColumnIndex("PartnerOutletID"));
                String partnerOutletName = c.getString(c.getColumnIndex("PartnerOutletName"));
                String dateTimePaid = c.getString(c.getColumnIndex("DateTimePaid"));
                String preConsummationSession = c.getString(c.getColumnIndex("PreConsummationSession"));
                String dropOffPurpose = c.getString(c.getColumnIndex("DropOffPurpose"));
                String dropOffNotes = c.getString(c.getColumnIndex("DropOffNotes"));
                String status = c.getString(c.getColumnIndex("Status"));
                String extra1 = c.getString(c.getColumnIndex("Extra1"));

                arrList.add(new DropOffOrder(dateTimeIN, dateTimeCompleted, GKTxnNo, merchantID, merchantName, merchantBranchID, merchantBranchName, borrowerID, customerMobileNumber, customerFirstName, customerLastName, customerAddress, customerEmailAddress, amount, merchantServiceCharge, customerServiceCharge, otherCharge, resellerDiscount, totalAmount, transactionMedium, processID, paymentReferenceNo, paymentType, billingID, partnerNetworkID, partnerNetworkName, partnerOutletID, partnerOutletName, dateTimePaid, preConsummationSession, dropOffPurpose, dropOffNotes, status, extra1));

            }
        }
        return arrList;
    }

    public List<DropOffOrder> getDropOffOrderCompleted(DatabaseHandler db) {
        List<DropOffOrder> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + DROPOFF_ORDERS_COMPLETED;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                String GKTxnNo = c.getString(c.getColumnIndex("GKTxnNo"));
                String merchantID = c.getString(c.getColumnIndex("MerchantID"));
                String merchantName = c.getString(c.getColumnIndex("MerchantName"));
                String merchantBranchID = c.getString(c.getColumnIndex("MerchantBranchID"));
                String merchantBranchName = c.getString(c.getColumnIndex("MerchantBranchName"));
                String borrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                String customerMobileNumber = c.getString(c.getColumnIndex("CustomerMobileNumber"));
                String customerFirstName = c.getString(c.getColumnIndex("CustomerFirstName"));
                String customerLastName = c.getString(c.getColumnIndex("CustomerLastName"));
                String customerAddress = c.getString(c.getColumnIndex("CustomerAddress"));
                String customerEmailAddress = c.getString(c.getColumnIndex("CustomerEmailAddress"));
                double amount = c.getDouble(c.getColumnIndex("Amount"));
                double merchantServiceCharge = c.getDouble(c.getColumnIndex("MerchantServiceCharge"));
                double customerServiceCharge = c.getDouble(c.getColumnIndex("CustomerServiceCharge"));
                double otherCharge = c.getDouble(c.getColumnIndex("OtherCharge"));
                double resellerDiscount = c.getDouble(c.getColumnIndex("ResellerDiscount"));
                double totalAmount = c.getDouble(c.getColumnIndex("TotalAmount"));
                String transactionMedium = c.getString(c.getColumnIndex("TransactionMedium"));
                String processID = c.getString(c.getColumnIndex("ProcessID"));
                String paymentReferenceNo = c.getString(c.getColumnIndex("PaymentReferenceNo"));
                String paymentType = c.getString(c.getColumnIndex("PaymentType"));
                String billingID = c.getString(c.getColumnIndex("BillingID"));
                String partnerNetworkID = c.getString(c.getColumnIndex("PartnerNetworkID"));
                String partnerNetworkName = c.getString(c.getColumnIndex("PartnerNetworkName"));
                String partnerOutletID = c.getString(c.getColumnIndex("PartnerOutletID"));
                String partnerOutletName = c.getString(c.getColumnIndex("PartnerOutletName"));
                String dateTimePaid = c.getString(c.getColumnIndex("DateTimePaid"));
                String preConsummationSession = c.getString(c.getColumnIndex("PreConsummationSession"));
                String dropOffPurpose = c.getString(c.getColumnIndex("DropOffPurpose"));
                String dropOffNotes = c.getString(c.getColumnIndex("DropOffNotes"));
                String status = c.getString(c.getColumnIndex("Status"));
                String extra1 = c.getString(c.getColumnIndex("Extra1"));

                arrList.add(new DropOffOrder(dateTimeIN, dateTimeCompleted, GKTxnNo, merchantID, merchantName, merchantBranchID, merchantBranchName, borrowerID, customerMobileNumber, customerFirstName, customerLastName, customerAddress, customerEmailAddress, amount, merchantServiceCharge, customerServiceCharge, otherCharge, resellerDiscount, totalAmount, transactionMedium, processID, paymentReferenceNo, paymentType, billingID, partnerNetworkID, partnerNetworkName, partnerOutletID, partnerOutletName, dateTimePaid, preConsummationSession, dropOffPurpose, dropOffNotes, status, extra1));

            }
        }
        return arrList;
    }

    public void insertDropOffOrderPending(DatabaseHandler db, DropOffOrder dropOffOrder) {
        SQLiteDatabase sql = db.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("DateTimeIN", dropOffOrder.getDateTimeIN());
        value.put("DateTimeCompleted", dropOffOrder.getDateTimeCompleted());
        value.put("GKTxnNo", dropOffOrder.getGKTxnNo());
        value.put("MerchantID", dropOffOrder.getMerchantID());
        value.put("MerchantName", dropOffOrder.getMerchantName());
        value.put("MerchantBranchID", dropOffOrder.getMerchantBranchID());
        value.put("MerchantBranchName", dropOffOrder.getMerchantBranchName());
        value.put("BorrowerID", dropOffOrder.getBorrowerID());
        value.put("CustomerMobileNumber", dropOffOrder.getCustomerMobileNumber());
        value.put("CustomerFirstName", dropOffOrder.getCustomerFirstName());
        value.put("CustomerLastName", dropOffOrder.getCustomerLastName());
        value.put("CustomerAddress", dropOffOrder.getCustomerAddress());
        value.put("CustomerEmailAddress", dropOffOrder.getCustomerEmailAddress());
        value.put("Amount", dropOffOrder.getAmount());
        value.put("MerchantServiceCharge", dropOffOrder.getMerchantServiceCharge());
        value.put("CustomerServiceCharge", dropOffOrder.getCustomerServiceCharge());
        value.put("OtherCharge", dropOffOrder.getOtherCharge());
        value.put("ResellerDiscount", dropOffOrder.getResellerDiscount());
        value.put("TotalAmount", dropOffOrder.getTotalAmount());
        value.put("TransactionMedium", dropOffOrder.getTransactionMedium());
        value.put("ProcessID", dropOffOrder.getProcessID());
        value.put("PaymentReferenceNo", dropOffOrder.getPaymentReferenceNo());
        value.put("PaymentType", dropOffOrder.getPaymentType());
        value.put("BillingID", dropOffOrder.getBillingID());
        value.put("PartnerNetworkID", dropOffOrder.getPartnerNetworkID());
        value.put("PartnerNetworkName", dropOffOrder.getPartnerNetworkName());
        value.put("PartnerOutletID", dropOffOrder.getPartnerOutletID());
        value.put("PartnerOutletName", dropOffOrder.getPartnerOutletName());
        value.put("DateTimePaid", dropOffOrder.getDateTimePaid());
        value.put("PreConsummationSession", dropOffOrder.getPreConsummationSession());
        value.put("DropOffPurpose", dropOffOrder.getDropOffPurpose());
        value.put("DropOffNotes", dropOffOrder.getDropOffNotes());
        value.put("Status", dropOffOrder.getStatus());
        value.put("Extra1", dropOffOrder.getExtra1());

        sql.insert(DROPOFF_ORDERS_PENDING, null, value);
    }

    public void insertDropOffOrderCompleted(DatabaseHandler db, DropOffOrder dropOffOrder) {
        SQLiteDatabase sql = db.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("DateTimeIN", dropOffOrder.getDateTimeIN());
        value.put("DateTimeCompleted", dropOffOrder.getDateTimeCompleted());
        value.put("GKTxnNo", dropOffOrder.getGKTxnNo());
        value.put("MerchantID", dropOffOrder.getMerchantID());
        value.put("MerchantName", dropOffOrder.getMerchantName());
        value.put("MerchantBranchID", dropOffOrder.getMerchantBranchID());
        value.put("MerchantBranchName", dropOffOrder.getMerchantBranchName());
        value.put("BorrowerID", dropOffOrder.getBorrowerID());
        value.put("CustomerMobileNumber", dropOffOrder.getCustomerMobileNumber());
        value.put("CustomerFirstName", dropOffOrder.getCustomerFirstName());
        value.put("CustomerLastName", dropOffOrder.getCustomerLastName());
        value.put("CustomerAddress", dropOffOrder.getCustomerAddress());
        value.put("CustomerEmailAddress", dropOffOrder.getCustomerEmailAddress());
        value.put("Amount", dropOffOrder.getAmount());
        value.put("MerchantServiceCharge", dropOffOrder.getMerchantServiceCharge());
        value.put("CustomerServiceCharge", dropOffOrder.getCustomerServiceCharge());
        value.put("OtherCharge", dropOffOrder.getOtherCharge());
        value.put("ResellerDiscount", dropOffOrder.getResellerDiscount());
        value.put("TotalAmount", dropOffOrder.getTotalAmount());
        value.put("TransactionMedium", dropOffOrder.getTransactionMedium());
        value.put("ProcessID", dropOffOrder.getProcessID());
        value.put("PaymentReferenceNo", dropOffOrder.getPaymentReferenceNo());
        value.put("PaymentType", dropOffOrder.getPaymentType());
        value.put("BillingID", dropOffOrder.getBillingID());
        value.put("PartnerNetworkID", dropOffOrder.getPartnerNetworkID());
        value.put("PartnerNetworkName", dropOffOrder.getPartnerNetworkName());
        value.put("PartnerOutletID", dropOffOrder.getPartnerOutletID());
        value.put("PartnerOutletName", dropOffOrder.getPartnerOutletName());
        value.put("DateTimePaid", dropOffOrder.getDateTimePaid());
        value.put("PreConsummationSession", dropOffOrder.getPreConsummationSession());
        value.put("DropOffPurpose", dropOffOrder.getDropOffPurpose());
        value.put("DropOffNotes", dropOffOrder.getDropOffNotes());
        value.put("Status", dropOffOrder.getStatus());
        value.put("Extra1", dropOffOrder.getExtra1());

        sql.insert(DROPOFF_ORDERS_COMPLETED, null, value);
    }

    public List<SkycablePPV> getSkycablePpvCatalogs(DatabaseHandler db) {
        List<SkycablePPV> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + SKYCABLE_PPV_CATALOGS;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String showingDateFrom = c.getString(c.getColumnIndex("ShowingDateFrom"));
                String showingDateTo = c.getString(c.getColumnIndex("ShowingDateTo"));
                String PPVID = c.getString(c.getColumnIndex("PPVID"));
                String PPVName = c.getString(c.getColumnIndex("PPVName"));
                String PPVDescription = c.getString(c.getColumnIndex("PPVDescription"));
                String imageURL = c.getString(c.getColumnIndex("ImageURL"));
                String currency = c.getString(c.getColumnIndex("Currency"));
                double discountPercentage = c.getDouble(c.getColumnIndex("DiscountPercentage"));
                double grossRate = c.getDouble(c.getColumnIndex("GrossRate"));
                double discountedRate = c.getDouble(c.getColumnIndex("DiscountedRate"));
                String addedBy = c.getString(c.getColumnIndex("AddedBy"));
                double subscriptionCount = c.getDouble(c.getColumnIndex("SubscriptionCount"));
                String status = c.getString(c.getColumnIndex("Status"));

                arrList.add(new SkycablePPV(dateTimeIN, showingDateFrom, showingDateTo, PPVID, PPVName, PPVDescription, imageURL, currency, discountPercentage, grossRate, discountedRate, addedBy, subscriptionCount, status));

            }
        }
        return arrList;
    }

    //SKYCABLE_PPV_ACCOUNTS
    public List<SkycableAccounts> getSkycableAccounts(DatabaseHandler db) {
        List<SkycableAccounts> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + SKYCABLE_PPV_ACCOUNTS;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String skyCableAccountNo = c.getString(c.getColumnIndex("SkyCableAccountNo"));
                String skyCableAccountName = c.getString(c.getColumnIndex("SkyCableAccountName"));
                String mobileNo = c.getString(c.getColumnIndex("MobileNo"));
                String source = c.getString(c.getColumnIndex("Source"));
                String status = c.getString(c.getColumnIndex("Status"));

                arrList.add(new SkycableAccounts(dateTimeIN, skyCableAccountNo, skyCableAccountName, mobileNo, source, status));

            }
        }
        return arrList;
    }

    //SKYCABLE_PPV_HISTORY
    public List<SkycablePPVHistory> getSkycablePPVHistory(DatabaseHandler db) {
        List<SkycablePPVHistory> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + SKYCABLE_PPV_HISTORY;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                String PPVName = c.getString(c.getColumnIndex("PPVName"));
                String PPVDescription = c.getString(c.getColumnIndex("PPVDescription"));
                String imageURL = c.getString(c.getColumnIndex("ImageURL"));
                String showingDateFrom = c.getString(c.getColumnIndex("ShowingDateFrom"));
                String showingDateTo = c.getString(c.getColumnIndex("ShowingDateTo"));
                String subscriptionTxnID = c.getString(c.getColumnIndex("SubscriptionTxnID"));
                String PPVID = c.getString(c.getColumnIndex("PPVID"));
                String skyCableAccountNo = c.getString(c.getColumnIndex("SkyCableAccountNo"));
                String borrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                String borrowerName = c.getString(c.getColumnIndex("BorrowerName"));
                String customerFirstName = c.getString(c.getColumnIndex("CustomerFirstName"));
                String customerLastName = c.getString(c.getColumnIndex("CustomerLastName"));
                String customerMobileNumber = c.getString(c.getColumnIndex("CustomerMobileNumber"));
                String customerEmailAddress = c.getString(c.getColumnIndex("CustomerEmailAddress"));
                String customerAddress = c.getString(c.getColumnIndex("CustomerAddress"));
                String city = c.getString(c.getColumnIndex("City"));
                String txnMedium = c.getString(c.getColumnIndex("TxnMedium"));
                double amount = c.getDouble(c.getColumnIndex("Amount"));
                double customerServiceCharge = c.getDouble(c.getColumnIndex("CustomerServiceCharge"));
                double totalAmountPaid = c.getDouble(c.getColumnIndex("TotalAmountPaid"));
                String GKPaymentReferenceNo = c.getString(c.getColumnIndex("GKPaymentReferenceNo"));
                String merchantID = c.getString(c.getColumnIndex("MerchantID"));
                String longitude = c.getString(c.getColumnIndex("Longitude"));
                String latitude = c.getString(c.getColumnIndex("Latitude"));
                String actedBy = c.getString(c.getColumnIndex("ActedBy"));
                String remarks = c.getString(c.getColumnIndex("Remarks"));
                String status = c.getString(c.getColumnIndex("Status"));

                arrList.add(new SkycablePPVHistory(dateTimeIN, dateTimeCompleted, PPVName, PPVDescription, imageURL, showingDateFrom, showingDateTo, subscriptionTxnID, PPVID, skyCableAccountNo, borrowerID, borrowerName, customerFirstName, customerLastName, customerMobileNumber, customerEmailAddress, customerAddress, city, txnMedium, amount, customerServiceCharge, totalAmountPaid, GKPaymentReferenceNo, merchantID, longitude, latitude, actedBy, remarks, status));

            }
        }
        return arrList;
    }

    //SKYCABLE_SOA
    public List<SkycableSOA> getSkycableSOA(DatabaseHandler db) {
        List<SkycableSOA> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + SKYCABLE_SOA;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String billingID = c.getString(c.getColumnIndex("BillingID"));
                String skycableAccountNo = c.getString(c.getColumnIndex("SkyCableAccountNo"));
                String skycableAccountName = c.getString(c.getColumnIndex("SkyCableAccountName"));
                String mobileNo = c.getString(c.getColumnIndex("MobileNo"));
                double dueFromLastBill = c.getDouble(c.getColumnIndex("DueFromLastBill"));
                double currentCharges = c.getDouble(c.getColumnIndex("CurrentCharges"));
                double totalCharges = c.getDouble(c.getColumnIndex("TotalCharges"));
                String dueDate = c.getString(c.getColumnIndex("DueDate"));
                String SOAURL = c.getString(c.getColumnIndex("SOAURL"));
                String uploadedBy = c.getString(c.getColumnIndex("UploadedBy"));
                String status = c.getString(c.getColumnIndex("Status"));

                arrList.add(new SkycableSOA(dateTimeIN, billingID, skycableAccountNo, skycableAccountName, mobileNo, dueFromLastBill, currentCharges, totalCharges, dueDate, SOAURL, uploadedBy, status));

            }
        }
        return arrList;
    }

    //SKYCABLE_REGISTRATIONS
    public List<SkycableRegistration> getSkycableRegistrations(DatabaseHandler db) {
        List<SkycableRegistration> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + SKYCABLE_REGISTRATIONS;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String planName = c.getString(c.getColumnIndex("PlanName"));
                String planType = c.getString(c.getColumnIndex("PlanType"));
                String planImgUrl = c.getString(c.getColumnIndex("PlanImgUrl"));
                String planStyle = c.getString(c.getColumnIndex("PlanStyle"));
                String description = c.getString(c.getColumnIndex("Description"));
                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                String registrationID = c.getString(c.getColumnIndex("RegistrationID"));
                String borrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                String firstName = c.getString(c.getColumnIndex("FirstName"));
                String lastName = c.getString(c.getColumnIndex("LastName"));
                String gender = c.getString(c.getColumnIndex("Gender"));
                String birthdate = c.getString(c.getColumnIndex("Birthdate"));
                String address = c.getString(c.getColumnIndex("Address"));
                String city = c.getString(c.getColumnIndex("City"));
                String longitude = c.getString(c.getColumnIndex("Longitude"));
                String latitude = c.getString(c.getColumnIndex("Latitude"));
                String ZIPCode = c.getString(c.getColumnIndex("ZIPCode"));
                String billingAddress = c.getString(c.getColumnIndex("BillingAddress"));
                String installationAddress = c.getString(c.getColumnIndex("InstallationAddress"));
                String emailAddress = c.getString(c.getColumnIndex("EmailAddress"));
                String mobileNumber = c.getString(c.getColumnIndex("MobileNumber"));
                String telephoneNumber = c.getString(c.getColumnIndex("TelephoneNumber"));
                String planID = c.getString(c.getColumnIndex("PlanID"));
                double discountBase = c.getDouble(c.getColumnIndex("DiscountBase"));
                double discountPercentage = c.getDouble(c.getColumnIndex("DiscountPercentage"));
                double installationFee = c.getDouble(c.getColumnIndex("InstallationFee"));
                double monthlyFee = c.getDouble(c.getColumnIndex("MonthlyFee"));
                double initialCashout = c.getDouble(c.getColumnIndex("InitialCashout"));
                double serviceCharge = c.getDouble(c.getColumnIndex("ServiceCharge"));
                double totalAmountPaid = c.getDouble(c.getColumnIndex("TotalAmountPaid"));
                String GKPaymentReferenceNo = c.getString(c.getColumnIndex("GKPaymentReferenceNo"));
                String accountNo = c.getString(c.getColumnIndex("AccountNo"));
                String merchantID = c.getString(c.getColumnIndex("MerchantID"));
                String status = c.getString(c.getColumnIndex("Status"));
                String notes1 = c.getString(c.getColumnIndex("Notes1"));

                arrList.add(new SkycableRegistration(planName, planType, planImgUrl, planStyle, description, dateTimeIN, dateTimeCompleted, registrationID, borrowerID, firstName, lastName, gender, birthdate, address, city, longitude, latitude, ZIPCode, billingAddress, installationAddress, emailAddress, mobileNumber, telephoneNumber, planID, discountBase, discountPercentage, installationFee, monthlyFee, initialCashout, serviceCharge, totalAmountPaid, GKPaymentReferenceNo, accountNo, merchantID, status, notes1));

            }
        }
        return arrList;
    }

    //SKYCABLE_REGISTRATIONS_FORPAYMENT
    public List<SkycableRegistration> getSkycableRegistrationsForPayment(DatabaseHandler db) {
        List<SkycableRegistration> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + SKYCABLE_REGISTRATIONS_FORPAYMENT;
        Cursor c = sql.rawQuery(strsql, new String[]{});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String planName = c.getString(c.getColumnIndex("PlanName"));
                String planType = c.getString(c.getColumnIndex("PlanType"));
                String planImgUrl = c.getString(c.getColumnIndex("PlanImgUrl"));
                String planStyle = c.getString(c.getColumnIndex("PlanStyle"));
                String description = c.getString(c.getColumnIndex("Description"));
                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                String registrationID = c.getString(c.getColumnIndex("RegistrationID"));
                String borrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                String firstName = c.getString(c.getColumnIndex("FirstName"));
                String lastName = c.getString(c.getColumnIndex("LastName"));
                String gender = c.getString(c.getColumnIndex("Gender"));
                String birthdate = c.getString(c.getColumnIndex("Birthdate"));
                String address = c.getString(c.getColumnIndex("Address"));
                String city = c.getString(c.getColumnIndex("City"));
                String longitude = c.getString(c.getColumnIndex("Longitude"));
                String latitude = c.getString(c.getColumnIndex("Latitude"));
                String ZIPCode = c.getString(c.getColumnIndex("ZIPCode"));
                String billingAddress = c.getString(c.getColumnIndex("BillingAddress"));
                String installationAddress = c.getString(c.getColumnIndex("InstallationAddress"));
                String emailAddress = c.getString(c.getColumnIndex("EmailAddress"));
                String mobileNumber = c.getString(c.getColumnIndex("MobileNumber"));
                String telephoneNumber = c.getString(c.getColumnIndex("TelephoneNumber"));
                String planID = c.getString(c.getColumnIndex("PlanID"));
                double discountBase = c.getDouble(c.getColumnIndex("DiscountBase"));
                double discountPercentage = c.getDouble(c.getColumnIndex("DiscountPercentage"));
                double installationFee = c.getDouble(c.getColumnIndex("InstallationFee"));
                double monthlyFee = c.getDouble(c.getColumnIndex("MonthlyFee"));
                double initialCashout = c.getDouble(c.getColumnIndex("InitialCashout"));
                double serviceCharge = c.getDouble(c.getColumnIndex("ServiceCharge"));
                double totalAmountPaid = c.getDouble(c.getColumnIndex("TotalAmountPaid"));
                String GKPaymentReferenceNo = c.getString(c.getColumnIndex("GKPaymentReferenceNo"));
                String accountNo = c.getString(c.getColumnIndex("AccountNo"));
                String merchantID = c.getString(c.getColumnIndex("MerchantID"));
                String status = c.getString(c.getColumnIndex("Status"));
                String notes1 = c.getString(c.getColumnIndex("Notes1"));

                arrList.add(new SkycableRegistration(planName, planType, planImgUrl, planStyle, description, dateTimeIN, dateTimeCompleted, registrationID, borrowerID, firstName, lastName, gender, birthdate, address, city, longitude, latitude, ZIPCode, billingAddress, installationAddress, emailAddress, mobileNumber, telephoneNumber, planID, discountBase, discountPercentage, installationFee, monthlyFee, initialCashout, serviceCharge, totalAmountPaid, GKPaymentReferenceNo, accountNo, merchantID, status, notes1));

            }
        }
        return arrList;
    }

    //SKYCABLE_SUPPORT_CONVERSATIONS
    public List<SkycableConversation> getSkycableConversations(DatabaseHandler db, String threadid) {
        List<SkycableConversation> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + SKYCABLE_SUPPORT_CONVERSATIONS + " WHERE ThreadID=? ORDER BY DateTimeIN ASC";
        Cursor c = sql.rawQuery(strsql, new String[]{threadid});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String threadID = c.getString(c.getColumnIndex("ThreadID"));
                String replySupportUserID = c.getString(c.getColumnIndex("ReplySupportUserID"));
                String replySupportName = c.getString(c.getColumnIndex("ReplySupportName"));
                String threadType = c.getString(c.getColumnIndex("ThreadType"));
                String message = c.getString(c.getColumnIndex("Message"));


                arrList.add(new SkycableConversation(dateTimeIN, threadID, replySupportUserID, replySupportName, threadType, message));

            }
        }
        return arrList;
    }

    //PROJECT_COOP_SUPPORT_CONVERSATIONS
    public List<SkycableConversation> getProjectCoopConversations(DatabaseHandler db, String threadid) {
        List<SkycableConversation> arrList = new ArrayList<>();
        SQLiteDatabase sql = db.getReadableDatabase();

        String strsql = "SELECT * FROM " + PROJECT_COOP_SUPPORT_CONVERSATIONS + " WHERE ThreadID=? ORDER BY DateTimeIN ASC";
        Cursor c = sql.rawQuery(strsql, new String[]{threadid});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String dateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                String threadID = c.getString(c.getColumnIndex("ThreadID"));
                String replySupportUserID = c.getString(c.getColumnIndex("ReplySupportUserID"));
                String replySupportName = c.getString(c.getColumnIndex("ReplySupportName"));
                String threadType = c.getString(c.getColumnIndex("ThreadType"));
                String message = c.getString(c.getColumnIndex("Message"));


                arrList.add(new SkycableConversation(dateTimeIN, threadID, replySupportUserID, replySupportName, threadType, message));

            }
        }
        return arrList;
    }

    public void insertSkycableVouchers(DatabaseHandler db, ParamountVouchers paramountVouchers) {
        SQLiteDatabase sql = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MerchantTransactionNo", paramountVouchers.getMerchantTransactionNo());
        values.put("TransactionNo", paramountVouchers.getTransactionNo());
        values.put("VoucherSeriesNo", paramountVouchers.getVoucherSeriesNo());
        values.put("VoucherCode", paramountVouchers.getVoucherCode());
        values.put("AmountConsumed", paramountVouchers.getAmountConsumed());
        values.put("ProductID", paramountVouchers.getProductID());
        values.put("VoucherDenom", paramountVouchers.getVoucherDenom());
        values.put("Extra3", paramountVouchers.getExtra3());

        sql.insert(SKYCABLE_PAYMENT_VOUCHERS, null, values);
    }

    public List<ParamountVouchers> getSkycablePaymentVouchers(DatabaseHandler db, String merchanttxnno) {
        List<ParamountVouchers> arrVouchers = new ArrayList<>();

        SQLiteDatabase sql = db.getReadableDatabase();
        String strsql = "SELECT * FROM " + SKYCABLE_PAYMENT_VOUCHERS + " WHERE MerchantTransactionNo=?";
        Cursor c = sql.rawQuery(strsql, new String[]{merchanttxnno});
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                String merchantTransactionNo = c.getString(c.getColumnIndex("MerchantTransactionNo"));
                String transactionNo = c.getString(c.getColumnIndex("TransactionNo"));
                String voucherSeriesNo = c.getString(c.getColumnIndex("VoucherSeriesNo"));
                String voucherCode = c.getString(c.getColumnIndex("VoucherCode"));
                double amountConsumed = c.getDouble(c.getColumnIndex("AmountConsumed"));
                String productID = c.getString(c.getColumnIndex("ProductID"));
                double voucherDenom = c.getDouble(c.getColumnIndex("VoucherDenom"));
                String extra3 = c.getString(c.getColumnIndex("Extra3"));

                arrVouchers.add(new ParamountVouchers(merchantTransactionNo, transactionNo, voucherSeriesNo, voucherCode, amountConsumed, productID, voucherDenom, extra3));
            }
        }

        return arrVouchers;
    }

    //***********************

    private String CREATE_GK_STORE_PRODUCTS = "CREATE TABLE IF NOT EXISTS " + GK_STORE_PRODUCTS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "MerchantID text, " +
            "GKStoreID text, " +
            "ProductID text, " +
            "ProductName text, " +
            "ProductDesc text, " +
            "GrossPrice text, " +
            "DiscountPercentage text, " +
            "ActualPrice text, " +
            "ProductImageURL text, " +
            "isPromo text, " +
            "PromoDetails text, " +
            "isSelected integer)";


    private String CREATE_GK_STORE_MERCHANTS = "CREATE TABLE IF NOT EXISTS " + GK_STORE_MERCHANTS +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "ID integer, " +
            "MerchantID text, " +
            "GKStoreID text, " +
            "GKStoreName text, " +
            "GKStoreDesc text, " +
            "GKStoreCategory text, " +
            "GKStoreRepresentative text, " +
            "GKStoreMobileNo text, " +
            "GKStoreTelNo text, " +
            "GKStoreEmailAddress text, " +
            "GKStoreMerchantChargeClass text, " +
            "GKStoreCustomerChargeClass text, " +
            "GKStoreLogoURL text, " +
            "GKStoreBannerURL text, " +
            "KYCOtherInfo text, " +
            "NoOfProducts text, " +
            "NoOfOrders text, " +
            "GKStoreNotes text, " +
            "DateTimeAdded text, " +
            "Status text, " +
            "Extra1 text, " +
            "Extra2 text, " +
            "Extra3 text, " +
            "Extra4 text, " +
            "Notes1 text, " +
            "Notes2 text)";

    //***********************
    //CONSTRUCTOR
    //********* **************
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        this.mContext = context;
    }

    //************************
    //INHERITED METHODS
    //************************

    @Override
    public void onCreate(SQLiteDatabase db) {
        //DROP ALL THE TABLES IF EXIST
        dropAllTables(db);

        //CREATE ALL TABLES IF DOESNT EXIST
        createAllTables(db);
    }

    private void dropAllTables(SQLiteDatabase db) {

        String d1 = DBUtils.DROP + ACCOUNTS;
        String d2 = DBUtils.DROP + VOUCHERS;
        String d3 = DBUtils.DROP + MERCHANTS;
        String d4 = DBUtils.DROP + GUARANTORS;
        String d5 = DBUtils.DROP + BORROWINGS;

        String d6 = DBUtils.DROP + CONSUMMATIONTRANSACTION;
        String d7 = DBUtils.DROP + UNPAIDBILLS;
        String d8 = DBUtils.DROP + PAIDBILLS;
        String d9 = DBUtils.DROP + NETWORKPREFIX;
        String d10 = DBUtils.DROP + GLOBEPRODUCT;

        String d11 = DBUtils.DROP + SMARTPRODUCT;
        String d12 = DBUtils.DROP + SUNPRODUCT;
        String d13 = DBUtils.DROP + VOUCHERRESTRICTIONS;
        String d14 = DBUtils.DROP + NOTIFICATIONS;
        String d15 = DBUtils.DROP + TRANSFERREDTRANSACTION;

        String d16 = DBUtils.DROP + INDICATORS;
        String d17 = DBUtils.DROP + ACCOUNTWALLET;
        String d18 = DBUtils.DROP + HAS_AGREED;
        String d19 = DBUtils.DROP + VOUCHERSUMMARY;
        String d20 = DBUtils.DROP + OUTLETS;

        String d21 = DBUtils.DROP + SUPPORT_MESSAGES_CONVERSATION;
        String d22 = DBUtils.DROP + SUPPORT_MESSAGES_THREADS;
        String d23 = DBUtils.DROP + HELP_TOPIC;
        String d24 = DBUtils.DROP + FAQ;
        String d25 = DBUtils.DROP + PURCHASES;

        String d26 = DBUtils.DROP + BILLERS;
        String d27 = DBUtils.DROP + MYBILLERS;
        String d28 = DBUtils.DROP + SUBSCIRBER_BILL;
        String d29 = DBUtils.DROP + SUBSCIRBER_PAYMENT;
        String d30 = DBUtils.DROP + CONSUMMATIONTRANSACTIONDETAILS;

        String d31 = DBUtils.DROP + PREPAID_LOGS;
        String d32 = DBUtils.DROP + BILLS_PAYMENT_LOGS;
        String d33 = DBUtils.DROP + RECEIVED_VOUCHERS;
        String d34 = DBUtils.DROP + TRANSFERRED_VOUCHERS;
        String d35 = DBUtils.DROP + BORROWING_VOUCHER_DETAILS;

        String d36 = DBUtils.DROP + PURCHASES_VOUCHER_DETAILS;
        String d37 = DBUtils.DROP + LOADWALLET_PREFIX;
        String d38 = DBUtils.DROP + RETAILER_RELOADING_LOGS;
        String d39 = DBUtils.DROP + GK_SERVICES;
        String d40 = DBUtils.DROP + APP_VERSION;

        String d41 = DBUtils.DROP + PAYMENT_CHANNELS;
        String d42 = DBUtils.DROP + PAYMENT_BRANCHES;
        String d43 = DBUtils.DROP + PROMOTIONS;
        String d44 = DBUtils.DROP + VIRTUAL_VOUCHER_REQUEST;
        String d45 = DBUtils.DROP + PARAMOUNT_POLICY_TYPE;

        String d46 = DBUtils.DROP + PARAMOUNT_CHARGES;
        String d47 = DBUtils.DROP + PARAMOUNT_QUEUE;
        String d48 = DBUtils.DROP + PARAMOUNT_HISTORY;
        String d49 = DBUtils.DROP + PARAMOUNT_PAYMENT_VOUCHERS;
        String d50 = DBUtils.DROP + CSB_REDEMPTION_HISTORY;

        String d51 = DBUtils.DROP + GK_STORE_PRODUCTS;
        String d52 = DBUtils.DROP + GK_STORE_MERCHANTS;
        String d53 = DBUtils.DROP + MedPadalaTransactionsDBHelper.TABLE_NAME;
        String d54 = DBUtils.DROP + GKStoreHistoryDBHelper.TABLE_NAME;
        String d55 = DBUtils.DROP + PUBLIC_SPONSOR_PROMOS;

        String d56 = DBUtils.DROP + SKYCABLE_REGISTRATIONS_FORPAYMENT;
        String d57 = DBUtils.DROP + SKYCABLE_REGISTRATIONS;
        String d58 = DBUtils.DROP + GKServicesBadgeDBHelper.TABLE_NAME;
        String d59 = DBUtils.DROP + SKYCABLE_PAYMENT_VOUCHERS;
        String d60 = DBUtils.DROP + SKYCABLE_SUPPORT_CONVERSATIONS;

        String d61 = DBUtils.DROP + SKYCABLE_SOA;
        String d62 = DBUtils.DROP + GKSRecentlyUsedDBHelper.TABLE_NAME;
        String d63 = DBUtils.DROP + SKYCABLE_PPV_CATALOGS;
        String d64 = DBUtils.DROP + SKYCABLE_PPV_ACCOUNTS;
        String d65 = DBUtils.DROP + SKYCABLE_PPV_HISTORY;


        String d66 = DBUtils.DROP + REDEEMED_PROMO_HISTORY;
        String d67 = DBUtils.DROP + PARAMOUNT_YEAR_MODEL;
        String d68 = DBUtils.DROP + MedPadalaTransactionsVoucherDetailsDBHelper.TABLE_NAME;
        String d69 = DBUtils.DROP + PROJECT_COOP_SUPPORT_CONVERSATIONS;
        String d70 = DBUtils.DROP + PARAMOUNT_VEHICLE_MAKERS;
        String d71 = DBUtils.DROP + SchoolMiniGradesDB.TABLE_GRADES;
        String d72 = DBUtils.DROP + SchoolMiniStudentDB.TABLE_STUDENTS;
        String d73 = DBUtils.DROP + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE;
        String d74 = DBUtils.DROP + SchoolMiniBulletinDB.TABLE_SCHOOL_BULLETIN;
        String d75 = DBUtils.DROP + SchoolMiniSupportConversationDB.TABLE_SC_SUPPORT_CONVERSATIONS;

        String d76 = DBUtils.DROP + SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS;
        String d77 = DBUtils.DROP + PaymentRequestPendingDB.TABLE_NAME;
        String d78 = DBUtils.DROP + PaymentRequestCompletedDB.TABLE_NAME;
        String d79 = DBUtils.DROP + MDPBulletinDB.TABLE_NAME;
        String d80 = DBUtils.DROP + MDPSupportConversationDB.TABLE_NAME;

        String d81 = DBUtils.DROP + DropOffMerchantsDB.TABLE_NAME;
        String d82 = DBUtils.DROP + DROPOFF_ORDERS_PENDING;
        String d83 = DBUtils.DROP + DROPOFF_ORDERS_COMPLETED;
        String d84 = DBUtils.DROP + PublicSponsorDBHelper.TABLE_NAME;
        String d85 = DBUtils.DROP + FreeSMSHistoryDBHelper.TABLE_NAME;

        String d86 = DBUtils.DROP + GKNegosyoDistributorsDBHelper.TABLE_NAME;
        String d87 = DBUtils.DROP + VotesPostEventDB.TABLE_VOTESEVENTS;
        String d88 = DBUtils.DROP + VotesParticipantsDB.TABLE_PARTICIPANTS;
        String d89 = DBUtils.DROP + FairChildMembersDB.TABLE_FAIRCHILDMEMBERS;
        String d90 = DBUtils.DROP + CoopMiniBulletinDB.TABLE_COOP_BULLETIN;

        String d91 = DBUtils.DROP + RFIDDBHelper.TABLE_NAME;
        String d92 = DBUtils.DROP + CoopMiniSupportConversationDB.TABLE_COOP_SUPPORT_CONVERSATIONS;
        String d93 = DBUtils.DROP + SchoolMiniDtrDB.TABLE_SCHOOL_DTR;
        String d94 = DBUtils.DROP + GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS;
        String d95 = DBUtils.DROP + MainMenuDBHelper.TABLE_NAME;

        String d96 = DBUtils.DROP + EGameProductsDBHelper.TABLE_NAME;
        String d97 = DBUtils.DROP + GKSERVICES_MENU;

        db.execSQL(d1);
        db.execSQL(d2);
        db.execSQL(d3);
        db.execSQL(d4);
        db.execSQL(d5);
        db.execSQL(d6);
        db.execSQL(d7);
        db.execSQL(d8);
        db.execSQL(d9);
        db.execSQL(d10);
        db.execSQL(d11);
        db.execSQL(d12);
        db.execSQL(d13);
        db.execSQL(d14);
        db.execSQL(d15);
        db.execSQL(d16);
        db.execSQL(d17);
        db.execSQL(d18);
        db.execSQL(d19);
        db.execSQL(d20);
        db.execSQL(d21);
        db.execSQL(d22);
        db.execSQL(d23);
        db.execSQL(d24);
        db.execSQL(d25);
        db.execSQL(d26);
        db.execSQL(d27);
        db.execSQL(d28);
        db.execSQL(d29);
        db.execSQL(d30);
        db.execSQL(d31);
        db.execSQL(d32);
        db.execSQL(d33);
        db.execSQL(d34);
        db.execSQL(d35);
        db.execSQL(d36);
        db.execSQL(d37);
        db.execSQL(d38);
        db.execSQL(d39);
        db.execSQL(d40);
        db.execSQL(d41);
        db.execSQL(d42);
        db.execSQL(d43);
        db.execSQL(d44);
        db.execSQL(d45);
        db.execSQL(d46);
        db.execSQL(d47);
        db.execSQL(d48);
        db.execSQL(d49);
        db.execSQL(d50);
        db.execSQL(d51);
        db.execSQL(d52);
        db.execSQL(d53);
        db.execSQL(d54);
        db.execSQL(d55);
        db.execSQL(d56);
        db.execSQL(d57);
        db.execSQL(d58);
        db.execSQL(d59);
        db.execSQL(d60);
        db.execSQL(d61);
        db.execSQL(d62);
        db.execSQL(d63);
        db.execSQL(d64);
        db.execSQL(d65);
        db.execSQL(d66);
        db.execSQL(d67);
        db.execSQL(d68);
        db.execSQL(d69);
        db.execSQL(d70);
        db.execSQL(d71);
        db.execSQL(d72);
        db.execSQL(d73);
        db.execSQL(d74);
        db.execSQL(d75);
        db.execSQL(d76);
        db.execSQL(d77);
        db.execSQL(d78);
        db.execSQL(d79);
        db.execSQL(d80);
        db.execSQL(d81);
        db.execSQL(d82);
        db.execSQL(d83);
        db.execSQL(d84);
        db.execSQL(d85);
        db.execSQL(d86);
        db.execSQL(d87);
        db.execSQL(d88);
        db.execSQL(d89);
        db.execSQL(d90);
        db.execSQL(d91);
        db.execSQL(d92);
        db.execSQL(d93);
        db.execSQL(d94);
        db.execSQL(d95);
        db.execSQL(d96);
        db.execSQL(d97);


    }

    private void createAllTables(SQLiteDatabase db) {
        //07/08/2019: Currently at 94 Tables.
        db.execSQL(CREATE_ACCOUNTS);
        db.execSQL(CREATE_VOUCHERS);
        db.execSQL(CREATE_MERCHANTS);
        db.execSQL(CREATE_GUARANTORS);
        db.execSQL(CREATE_BORROWINGS);

        db.execSQL(CREATE_CONSUMMATIONTRANSACTION);
        db.execSQL(CREATE_UNPAIDBILLS);
        db.execSQL(CREATE_PAIDBILLS);
        db.execSQL(CREATE_NETWORK_PREFIX);
        db.execSQL(CREATE_GLOBE_PRODUCT);

        db.execSQL(CREATE_SMART_PRODUCT);
        db.execSQL(CREATE_SUN_PRODUCT);
        db.execSQL(CREATE_VOUCHERRESTRICTIONS);
        db.execSQL(CREATE_NOTIFICATION);
        db.execSQL(CREATE_TRANSFERREDTRANSACTION);

        db.execSQL(CREATE_INDICATORS);
        db.execSQL(CREATE_ACCOUNTWALLET);
        db.execSQL(CREATE_AGREED);
        db.execSQL(CREATE_VOUCHERSUMMARY);
        db.execSQL(CREATE_OUTLETS);

        db.execSQL(CREATE_SUPPORT_MESSAGES_CONVERSATION);
        db.execSQL(CREATE_SUPPORT_MESSAGES_THREADS);
        db.execSQL(CREATE_HELP_TOPIC);
        db.execSQL(CREATE_FAQ);
        db.execSQL(CREATE_PURCHASES_TABLE);

        db.execSQL(CREATE_BILLER_TABLE);
        db.execSQL(CREATE_MYBILLERS_TABLE);
        db.execSQL(CREATE_SUBCRIBERBILL_TABLE);
        db.execSQL(CREATE_SUBCRIBERPAYMENT_TABLE);
        db.execSQL(CREATE_CONSUMMATION_TRANSACTION_DETAILS);

        db.execSQL(CREATE_PREPAID_LOGS);
        db.execSQL(CREATE_BILLS_PAYMENT_LOGS);
        db.execSQL(CREATE_RECEIVED_VOUCHERS);
        db.execSQL(CREATE_TRANSFERRED_VOUCHERS);
        db.execSQL(CREATE_BORROWING_VOUCHER_DETAILS);

        db.execSQL(CREATE_PURCHASES_VOUCHER_DETAILS);
        db.execSQL(CREATE_LOADWALLET_PREFIX);
        db.execSQL(CREATE_RETAILER_RELOADING_LOGS);
        db.execSQL(CREATE_GK_SERVICES);
        db.execSQL(CREATE_APP_VERSION);

        db.execSQL(CREATE_PAYMENT_CHANNELS);
        db.execSQL(CREATE_PAYMENT_BRANCHES);
        db.execSQL(CREATE_PROMOTIONS);
        db.execSQL(CREATE_VIRTUAL_VOUCHER_REQUEST_TABLE);
        db.execSQL(CREATE_PARAMOUNT_POLICY_TYPE);

        db.execSQL(CREATE_PARAMOUNT_CHARGES);
        db.execSQL(CREATE_PARAMOUNT_QUEUE);
        db.execSQL(CREATE_PARAMOUNT_HISTORY);
        db.execSQL(CREATE_PARAMOUNT_PAYMENT_VOUCHERS);
        db.execSQL(CREATE_CSB_REDEMPTION_HISTORY);

        db.execSQL(CREATE_GK_STORE_PRODUCTS);
        db.execSQL(CREATE_GK_STORE_MERCHANTS);
        db.execSQL(MedPadalaTransactionsDBHelper.CREATE_STATEMENT);
        db.execSQL(GKStoreHistoryDBHelper.CREATE_STATEMENT);
        db.execSQL(CREATE_PUBLIC_SPONSOR_PROMO);

        db.execSQL(CREATE_SKYCABLE_REGISTRATIONS_FORPAYMENT);
        db.execSQL(CREATE_SKYCABLE_REGISTRATIONS);
        db.execSQL(GKServicesBadgeDBHelper.CREATE_STATEMENT);
        db.execSQL(CREATE_SKYCABLE_PAYMENT_VOUCHERS);
        db.execSQL(CREATE_SKYCABLE_SUPPORT_CONVERSATIONS);

        db.execSQL(CREATE_SKYCABLE_SOA);
        db.execSQL(GKSRecentlyUsedDBHelper.CREATE_STATEMENT);
        db.execSQL(CREATE_SKYCABLE_PPV_CATALOGS);
        db.execSQL(CREATE_SKYCABLE_PPV_ACCOUNTS);
        db.execSQL(CREATE_SKYCABLE_PPV_HISTORY);

        db.execSQL(CREATE_REDEEMED_PROMO_HISTORY);
        db.execSQL(CREATE_PARAMOUNT_YEAR_MODEL);
        db.execSQL(MedPadalaTransactionsVoucherDetailsDBHelper.CREATE_STATEMENT);
        db.execSQL(CREATE_PROJECT_COOP_SUPPORT_CONVERSATIONS);
        db.execSQL(CREATE_PARAMOUNT_VEHICLE_MAKERS);

        db.execSQL(SchoolMiniGradesDB.CREATE_TABLE_GRADES);
        db.execSQL(SchoolMiniStudentDB.CREATE_TABLE_STUDENTS);
        db.execSQL(SchoolMiniTuitionFeeDB.CREATE_TABLE_TUITIONFEE);
        db.execSQL(SchoolMiniBulletinDB.CREATE_TABLE_BULLETIN);
        db.execSQL(SchoolMiniSupportConversationDB.CREATE_TABLE_SC_SUPPORT_CONVERSATIONS);

        db.execSQL(SchoolMiniPaymentLogsDB.CREATE_TABLE_SC_PAYMENTLOGS);
        db.execSQL(PaymentRequestPendingDB.CREATE_STATEMENT);
        db.execSQL(PaymentRequestCompletedDB.CREATE_STATEMENT);
        db.execSQL(MDPBulletinDB.CREATE_STATEMENT);
        db.execSQL(MDPSupportConversationDB.CREATE_STATEMENT);

        db.execSQL(DropOffMerchantsDB.CREATE_STATEMENT);
        db.execSQL(CREATE_DROPOFF_ORDERS_PENDING);
        db.execSQL(CREATE_DROPOFF_ORDERS_COMPLETED);
        db.execSQL(PublicSponsorDBHelper.CREATE_STATEMENT);
        db.execSQL(FreeSMSHistoryDBHelper.CREATE_STATEMENT);

        db.execSQL(GKNegosyoDistributorsDBHelper.CREATE_STATEMENT);
        db.execSQL(VotesPostEventDB.CREATE_TABLE_VOTESEVENTS);
        db.execSQL(VotesParticipantsDB.CREATE_TABLE_VOTESEVENTS);
        db.execSQL(FairChildMembersDB.CREATE_TABLE_FAIRCHILDMEMBERS);
        db.execSQL(CoopMiniBulletinDB.CREATE_TABLE_BULLETIN);

        db.execSQL(RFIDDBHelper.CREATE_STATEMENT);
        db.execSQL(CoopMiniSupportConversationDB.CREATE_TABLE_COOP_SUPPORT_CONVERSATIONS);
        db.execSQL(SchoolMiniDtrDB.CREATE_TABLE_DTR);
        db.execSQL(GKEarnConversionsPointsDB.CREATE_TABLE_GKEARN_CONVERSIONS);

        //new db added as of OCT. 01,2019
        db.execSQL(CREATE_GKSERVICES_MENU);

        //new for otp local as of OCTOBER 2019
        db.execSQL(OtpDBHelper.CREATE_TABLE_OTP);

        db.execSQL(EGameProductsDBHelper.CREATE_STATEMENT);


    }

    private void deleteAllTables(SQLiteDatabase db) {
        String d1 = DBUtils.DELETE + ACCOUNTS + "";
        String d2 = DBUtils.DELETE + VOUCHERS + "";
        String d3 = DBUtils.DELETE + MERCHANTS + "";
        String d4 = DBUtils.DELETE + GUARANTORS + "";
        String d5 = DBUtils.DELETE + BORROWINGS + "";

        String d6 = DBUtils.DELETE + CONSUMMATIONTRANSACTION + "";
        String d7 = DBUtils.DELETE + UNPAIDBILLS + "";
        String d8 = DBUtils.DELETE + PAIDBILLS + "";
        String d9 = DBUtils.DELETE + NETWORKPREFIX + "";
        String d10 = DBUtils.DELETE + GLOBEPRODUCT + "";

        String d11 = DBUtils.DELETE + SMARTPRODUCT + "";
        String d12 = DBUtils.DELETE + SUNPRODUCT + "";
        String d13 = DBUtils.DELETE + VOUCHERRESTRICTIONS + "";
        String d14 = DBUtils.DELETE + NOTIFICATIONS + "";
        String d15 = DBUtils.DELETE + TRANSFERREDTRANSACTION + "";

        String d16 = DBUtils.DELETE + INDICATORS + "";
        String d17 = DBUtils.DELETE + ACCOUNTWALLET + "";
        String d18 = DBUtils.DELETE + HAS_AGREED + "";
        String d19 = DBUtils.DELETE + VOUCHERSUMMARY + "";
        String d20 = DBUtils.DELETE + OUTLETS + "";

        String d21 = DBUtils.DELETE + SUPPORT_MESSAGES_CONVERSATION + "";
        String d22 = DBUtils.DELETE + SUPPORT_MESSAGES_THREADS + "";
        String d23 = DBUtils.DELETE + HELP_TOPIC + "";
        String d24 = DBUtils.DELETE + FAQ + "";
        String d25 = DBUtils.DELETE + PURCHASES + "";

        String d26 = DBUtils.DELETE + BILLERS + "";
        String d27 = DBUtils.DELETE + MYBILLERS + "";
        String d28 = DBUtils.DELETE + SUBSCIRBER_BILL + "";
        String d29 = DBUtils.DELETE + SUBSCIRBER_PAYMENT + "";
        String d30 = DBUtils.DELETE + CONSUMMATIONTRANSACTIONDETAILS + "";

        String d31 = DBUtils.DELETE + PREPAID_LOGS + "";
        String d32 = DBUtils.DELETE + BILLS_PAYMENT_LOGS + "";
        String d33 = DBUtils.DELETE + RECEIVED_VOUCHERS + "";
        String d34 = DBUtils.DELETE + TRANSFERRED_VOUCHERS + "";
        String d35 = DBUtils.DELETE + BORROWING_VOUCHER_DETAILS + "";

        String d36 = DBUtils.DELETE + PURCHASES_VOUCHER_DETAILS + "";
        String d37 = DBUtils.DELETE + LOADWALLET_PREFIX + "";
        String d38 = DBUtils.DELETE + RETAILER_RELOADING_LOGS + "";
        String d39 = DBUtils.DELETE + GK_SERVICES + "";
        String d40 = DBUtils.DELETE + APP_VERSION + "";

        String d41 = DBUtils.DELETE + PAYMENT_CHANNELS + "";
        String d42 = DBUtils.DELETE + PAYMENT_BRANCHES + "";
        String d43 = DBUtils.DELETE + PROMOTIONS + "";
        String d44 = DBUtils.DELETE + VIRTUAL_VOUCHER_REQUEST + "";
        String d45 = DBUtils.DELETE + PARAMOUNT_POLICY_TYPE + "";

        String d46 = DBUtils.DELETE + PARAMOUNT_CHARGES + "";
        String d47 = DBUtils.DELETE + PARAMOUNT_QUEUE + "";
        String d48 = DBUtils.DELETE + PARAMOUNT_HISTORY + "";
        String d49 = DBUtils.DELETE + PARAMOUNT_PAYMENT_VOUCHERS + "";
        String d50 = DBUtils.DELETE + CSB_REDEMPTION_HISTORY + "";

        String d51 = DBUtils.DELETE + GK_STORE_PRODUCTS + "";
        String d52 = DBUtils.DELETE + GK_STORE_MERCHANTS + "";
        String d53 = DBUtils.DELETE + MedPadalaTransactionsDBHelper.TABLE_NAME;
        String d54 = DBUtils.DELETE + GKStoreHistoryDBHelper.TABLE_NAME;
        String d55 = DBUtils.DELETE + PUBLIC_SPONSOR_PROMOS + "";

        String d56 = DBUtils.DELETE + SKYCABLE_REGISTRATIONS_FORPAYMENT + "";
        String d57 = DBUtils.DELETE + SKYCABLE_REGISTRATIONS + "";
        String d58 = DBUtils.DELETE + GKServicesBadgeDBHelper.TABLE_NAME;
        String d59 = DBUtils.DELETE + SKYCABLE_PAYMENT_VOUCHERS + "";
        String d60 = DBUtils.DELETE + SKYCABLE_SUPPORT_CONVERSATIONS + "";

        String d61 = DBUtils.DELETE + SKYCABLE_SOA + "";
        String d62 = DBUtils.DELETE + GKSRecentlyUsedDBHelper.TABLE_NAME;
        String d63 = DBUtils.DELETE + SKYCABLE_PPV_CATALOGS + "";
        String d64 = DBUtils.DELETE + SKYCABLE_PPV_ACCOUNTS + "";
        String d65 = DBUtils.DELETE + SKYCABLE_PPV_HISTORY + "";

        String d66 = DBUtils.DELETE + REDEEMED_PROMO_HISTORY + "";
        String d67 = DBUtils.DELETE + PARAMOUNT_YEAR_MODEL + "";
        String d68 = DBUtils.DELETE + MedPadalaTransactionsVoucherDetailsDBHelper.TABLE_NAME;
        String d69 = DBUtils.DELETE + PROJECT_COOP_SUPPORT_CONVERSATIONS + "";
        String d70 = DBUtils.DELETE + PARAMOUNT_VEHICLE_MAKERS + "";

        String d71 = DBUtils.DELETE + SchoolMiniGradesDB.TABLE_GRADES;
        String d72 = DBUtils.DELETE + SchoolMiniStudentDB.TABLE_STUDENTS;
        String d73 = DBUtils.DELETE + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE;
        String d74 = DBUtils.DELETE + SchoolMiniBulletinDB.TABLE_SCHOOL_BULLETIN;
        String d75 = DBUtils.DELETE + SchoolMiniSupportConversationDB.TABLE_SC_SUPPORT_CONVERSATIONS;

        String d76 = DBUtils.DELETE + SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS;
        String d77 = DBUtils.DELETE + PaymentRequestPendingDB.TABLE_NAME;
        String d78 = DBUtils.DELETE + PaymentRequestCompletedDB.TABLE_NAME;
        String d79 = DBUtils.DELETE + MDPBulletinDB.TABLE_NAME;
        String d80 = DBUtils.DELETE + MDPSupportConversationDB.TABLE_NAME;

        String d81 = DBUtils.DELETE + DropOffMerchantsDB.TABLE_NAME;
        String d82 = DBUtils.DELETE + DROPOFF_ORDERS_PENDING + "";
        String d83 = DBUtils.DELETE + DROPOFF_ORDERS_COMPLETED + "";
        String d84 = DBUtils.DELETE + PublicSponsorDBHelper.TABLE_NAME;
        String d85 = DBUtils.DELETE + FreeSMSHistoryDBHelper.TABLE_NAME;

        String d86 = DBUtils.DELETE + GKNegosyoDistributorsDBHelper.TABLE_NAME;
        String d87 = DBUtils.DELETE + VotesPostEventDB.TABLE_VOTESEVENTS;
        String d88 = DBUtils.DELETE + VotesParticipantsDB.TABLE_PARTICIPANTS;
        String d89 = DBUtils.DELETE + FairChildMembersDB.TABLE_FAIRCHILDMEMBERS;
        String d90 = DBUtils.DELETE + CoopMiniBulletinDB.TABLE_COOP_BULLETIN;

        String d91 = DBUtils.DELETE + RFIDDBHelper.TABLE_NAME;
        String d92 = DBUtils.DELETE + CoopMiniSupportConversationDB.TABLE_COOP_SUPPORT_CONVERSATIONS;
        String d93 = DBUtils.DELETE + SchoolMiniDtrDB.TABLE_SCHOOL_DTR;
        String d94 = DBUtils.DELETE + GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS;

        //Added new on Oct. 01,2019
        String d95 = DBUtils.DELETE + MainMenuDBHelper.TABLE_NAME;
        String d96 = DBUtils.DELETE + EGameProductsDBHelper.TABLE_NAME;

        db.execSQL(d1);
        db.execSQL(d2);
        db.execSQL(d3);
        db.execSQL(d4);
        db.execSQL(d5);
        db.execSQL(d6);
        db.execSQL(d7);
        db.execSQL(d8);
        db.execSQL(d9);
        db.execSQL(d10);
        db.execSQL(d11);
        db.execSQL(d12);
        db.execSQL(d13);
        db.execSQL(d14);
        db.execSQL(d15);
        db.execSQL(d16);
        db.execSQL(d17);
        db.execSQL(d18);
        db.execSQL(d19);
        db.execSQL(d20);
        db.execSQL(d21);
        db.execSQL(d22);
        db.execSQL(d23);
        db.execSQL(d24);
        db.execSQL(d25);
        db.execSQL(d26);
        db.execSQL(d27);
        db.execSQL(d28);
        db.execSQL(d29);
        db.execSQL(d30);
        db.execSQL(d31);
        db.execSQL(d32);
        db.execSQL(d33);
        db.execSQL(d34);
        db.execSQL(d35);
        db.execSQL(d36);
        db.execSQL(d37);
        db.execSQL(d38);
        db.execSQL(d39);
        db.execSQL(d40);
        db.execSQL(d41);
        db.execSQL(d42);
        db.execSQL(d43);
        db.execSQL(d44);
        db.execSQL(d45);
        db.execSQL(d46);
        db.execSQL(d47);
        db.execSQL(d48);
        db.execSQL(d49);
        db.execSQL(d50);
        db.execSQL(d51);
        db.execSQL(d52);
        db.execSQL(d53);
        db.execSQL(d54);
        db.execSQL(d55);
        db.execSQL(d56);
        db.execSQL(d57);
        db.execSQL(d58);
        db.execSQL(d59);
        db.execSQL(d60);
        db.execSQL(d61);
        db.execSQL(d62);
        db.execSQL(d63);
        db.execSQL(d64);
        db.execSQL(d65);
        db.execSQL(d66);
        db.execSQL(d67);
        db.execSQL(d68);
        db.execSQL(d69);
        db.execSQL(d70);
        db.execSQL(d71);
        db.execSQL(d72);
        db.execSQL(d73);
        db.execSQL(d74);
        db.execSQL(d75);
        db.execSQL(d76);
        db.execSQL(d77);
        db.execSQL(d78);
        db.execSQL(d79);
        db.execSQL(d80);
        db.execSQL(d81);
        db.execSQL(d82);
        db.execSQL(d83);
        db.execSQL(d84);
        db.execSQL(d85);
        db.execSQL(d86);
        db.execSQL(d87);
        db.execSQL(d88);
        db.execSQL(d89);
        db.execSQL(d90);
        db.execSQL(d91);
        db.execSQL(d92);
        db.execSQL(d93);
        db.execSQL(d94);
        db.execSQL(d95);
        db.execSQL(d96);

    }

    public void Logout(DatabaseHandler db) {
        if(db != null) {
            //DROP ALL THE TABLES
            dropAllTables(openLogoutDB(db));

            //CREATE ALL TABLES IF DOESNT EXIST
            createAllTables(openLogoutDB(db));

            //DELETE ALL TABLES
            deleteAllTables(openLogoutDB(db));

            db.close();
        }
    }

    private SQLiteDatabase openLogoutDB(DatabaseHandler db) {
        return db.getWritableDatabase();
    }

    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < database_version) {
            checkVersionTable(db, oldVersion);
        }
    }

    //UPDATE THIS FROM TIME TO TIME.
    //NEVER REMOVED THIS.
    private void checkVersionTable(SQLiteDatabase db, int oldVersion) {

        if(oldVersion < 87) {

            PreferenceUtils.clearPreference(mContext);

            //DROP ALL THE TABLES IF EXIST
            dropAllTables(db);
            //CREATE ALL TABLES IF DOESNT EXIST
            createAllTables(db);

            //1/20/2020
            db.execSQL(DBUtils.DROP + VIRTUAL_VOUCHER_REQUEST);
            db.execSQL(CREATE_VIRTUAL_VOUCHER_REQUEST_TABLE);

            //1/20/2020
            db.execSQL(DBUtils.DROP + VIRTUAL_VOUCHER_REQUEST);
            db.execSQL(CREATE_VIRTUAL_VOUCHER_REQUEST_TABLE);
        }else{
            //DROP ALL THE TABLES IF EXIST
            dropAllTables(db);
            //CREATE ALL TABLES IF DOESNT EXIST
            createAllTables(db);

            //1/20/2020
            db.execSQL(DBUtils.DROP + VIRTUAL_VOUCHER_REQUEST);
            db.execSQL(CREATE_VIRTUAL_VOUCHER_REQUEST_TABLE);

            //1/20/2020
            db.execSQL(DBUtils.DROP + VIRTUAL_VOUCHER_REQUEST);
            db.execSQL(CREATE_VIRTUAL_VOUCHER_REQUEST_TABLE);
        }
    }

            //GKS Badge
            public void insertGKSBadge (DatabaseHandler db, GKServiceBadge data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(GKServicesBadgeDBHelper.TABLE_NAME, null, GKServicesBadgeDBHelper.insert(data));
            }

            public void trucateGKSBadge (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL(GKServicesBadgeDBHelper.TRUNCATE_TABLE);
            }


            public List<GKServiceBadge> getGKSBadge (DatabaseHandler db){
                List<GKServiceBadge> badges;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKServicesBadgeDBHelper.TABLE_NAME, null);
                badges = GKServicesBadgeDBHelper.getBorrowerGKSBadges(cursor);
                return badges;
            }

            public int getBadgeForServiceCode (DatabaseHandler db, String serviceCode){
                int nof = 0;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKServicesBadgeDBHelper.TABLE_NAME + " WHERE ServiceCode = ? LIMIT 1", new String[]{serviceCode});
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    nof = cursor.getInt(cursor.getColumnIndex(GKServicesBadgeDBHelper.TotalBadge));
                }
                return nof;
            }

            //GKS_RECENTLY_USED
            //GKS_RECENTLY_USED
            public void insertRecentlyUsedGKS (DatabaseHandler db, GKService data){
                SQLiteDatabase sql = db.getWritableDatabase();
                if (!data.getServiceCode().equals("BUYPREPAIDLOAD") && !data.getServiceCode().equals("PAYBILLS")
                        && !data.getServiceCode().equals("BUY_VOUCHER") && !data.getServiceCode().equals("GKNEGOSYO")
                        && !data.getServiceCode().equals("PAYVIAQR") && !data.getServiceCode().equals("PROMO")
                        && !data.getServiceCode().equals("DROPOFF") && !data.getServiceCode().equals("FREESMS")
                        && !data.getServiceCode().equals("REFER_A_FRIEND") && !data.getServiceCode().equals("WHATS_NEW")
                        && !data.getServiceCode().equals("GKEARN")) {
                    sql.insert(GKSRecentlyUsedDBHelper.TABLE_NAME, null, GKSRecentlyUsedDBHelper.insert(data));
                }
            }

            public void trucateRecentlyUsedGKS (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL(GKSRecentlyUsedDBHelper.TRUNCATE_TABLE);
            }

            public void deleteRecordS (DatabaseHandler db, String serviceCode){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL(GKSRecentlyUsedDBHelper.deleteRecord(serviceCode));
            }

            public List<GKService> getRecentlyUsedGKS (DatabaseHandler db){
                List<GKService> transactions;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKSRecentlyUsedDBHelper.TABLE_NAME + " AS A" +
                        " INNER JOIN " + GK_SERVICES + " AS B ON A.ServiceCode = B.ServiceCode" +
                        " ORDER BY _id DESC", null);
                transactions = GKSRecentlyUsedDBHelper.getRecentlyUsedGKS(cursor);
                return transactions;
            }

            //MEDPADALA_TRANSACTION
            public void insertMedPadalaTransaction (DatabaseHandler db, MedPadalaTransaction data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(MedPadalaTransactionsDBHelper.TABLE_NAME, null, MedPadalaTransactionsDBHelper.insert(data));
            }

            public void trucateMedPadalaTable (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL(DBUtils.DELETE + MedPadalaTransactionsDBHelper.TABLE_NAME);
            }

            public List<MedPadalaTransaction> getAllMedPadalaTransactions (DatabaseHandler db){
                List<MedPadalaTransaction> transactions;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + MedPadalaTransactionsDBHelper.TABLE_NAME + " ORDER BY " + MedPadalaTransactionsDBHelper.DateTimeCompleted + " DESC", null);
                transactions = MedPadalaTransactionsDBHelper.getAllMedPadalaTransactions(cursor);
                return transactions;
            }

            //MEDPADALA_VOUCHER_USED_PER_TRANSACTION
            public void insertMedPadalaTransactionVoucherUsed (DatabaseHandler
            db, MedPadalaVoucherUsed data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(MedPadalaTransactionsVoucherDetailsDBHelper.TABLE_NAME, null, MedPadalaTransactionsVoucherDetailsDBHelper.insert(data));
            }

            public void removeRowsWithTransactionNo (DatabaseHandler db, String transactionNumber){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.rawQuery("DELETE FROM " + MedPadalaTransactionsVoucherDetailsDBHelper.TABLE_NAME + " WHERE " + MedPadalaTransactionsVoucherDetailsDBHelper.TransactionNo + " = " + transactionNumber, null);
            }

            public List<MedPadalaVoucherUsed> getRowsWithTransactionNo (DatabaseHandler db, String
            transactionNumber){
                List<MedPadalaVoucherUsed> vouchers;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + MedPadalaTransactionsVoucherDetailsDBHelper.TABLE_NAME + " WHERE " + MedPadalaTransactionsVoucherDetailsDBHelper.TransactionNo + "=" + transactionNumber, null);
                vouchers = MedPadalaTransactionsVoucherDetailsDBHelper.getAllMedPadalaTransactionVouchersUsed(cursor);
                return vouchers;
            }

            //GKSTOREHISTORY TRANSACTIONS
            public void insertGKStoreHistory (DatabaseHandler db, FetchStoreOrderList data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(GKStoreHistoryDBHelper.TABLE_NAME, null, GKStoreHistoryDBHelper.insert(data));
            }

            public void deleteGKStoreHistory (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL(DBUtils.DELETE + GKStoreHistoryDBHelper.TABLE_NAME);
            }

            public List<FetchStoreOrderList> getAllGKStoreTransactionsStatus (DatabaseHandler db){
                List<FetchStoreOrderList> transactions = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKStoreHistoryDBHelper.TABLE_NAME
                        + " ORDER BY " + GKStoreHistoryDBHelper.Status + " ASC", null);
                transactions = GKStoreHistoryDBHelper.getAllGKStoreTransactions(cursor);
                cursor.close();
                return transactions;
            }

            public List<FetchStoreOrderList> getAllGKStoreTransactionsHeaderStatus (DatabaseHandler
            db){
                List<FetchStoreOrderList> transactions = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKStoreHistoryDBHelper.TABLE_NAME
                                + " GROUP BY " + GKStoreHistoryDBHelper.Status
                                + " ORDER BY "
                                + " CASE " + GKStoreHistoryDBHelper.Status
                                + " WHEN " + "'FOR PAYMENT'" + " THEN " + 0
                                + " WHEN " + "'FOR APPROVAL'" + " THEN " + 1
                                + " WHEN " + "'FOR CANCELLATION'" + " THEN " + 2
                                + " WHEN " + "'PENDING'" + " THEN " + 3
                                + " WHEN " + "'CANCELLED'" + " THEN " + 4
                                + " WHEN " + "'DECLINED'" + " THEN " + 5
                                + " ELSE " + GKStoreHistoryDBHelper.Status
                                + " END "
                        , null);

                //+ " ORDER BY " + GKStoreHistoryDBHelper.Status + " = " + "'FOR PAYMENT'" + " DESC"
                //+ ", "
                //+ GKStoreHistoryDBHelper.Status + " ASC"
                //ORDER BY FIELD(`Status`, 'FOR PAYMENT', 'FOR APPROVAL', 'PENDING', 'CANCELLED', 'DECLINED') ASC
                transactions = GKStoreHistoryDBHelper.getAllGKStoreTransactions(cursor);
                cursor.close();
                return transactions;
            }

            public List<FetchStoreOrderList> getAllGKStoreTransactionsChildStatus (DatabaseHandler
            db, String status){
                List<FetchStoreOrderList> transactions = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKStoreHistoryDBHelper.TABLE_NAME
                        + " WHERE Status" + "=?"
                        + " ORDER BY " + GKStoreHistoryDBHelper.Status + " ASC ", new String[]{status});
                transactions = GKStoreHistoryDBHelper.getAllGKStoreTransactions(cursor);
                cursor.close();
                return transactions;
            }

            public List<FetchStoreOrderList> getAllTransactionActionNeeded (DatabaseHandler db){
                List<FetchStoreOrderList> transactions = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKStoreHistoryDBHelper.TABLE_NAME
                        + " WHERE " + GKStoreHistoryDBHelper.Status + "= 'ON PROCESS'" + " OR "
                        + GKStoreHistoryDBHelper.Status + "= 'PENDING'"
                        + " ORDER BY " + GKStoreHistoryDBHelper.DateTimeIN + " DESC", null);
                transactions = GKStoreHistoryDBHelper.getAllGKStoreTransactions(cursor);
                return transactions;
            }

            //region REGISTRATION PROCESS
            public Cursor isLogin (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT status,borrowerid From " + ACCOUNTS + " LIMIT 1", null);
                return c;
            }

            //REDEEMED_PROMO_HISTORY
            public void insertPromoHistory (DatabaseHandler db, RedeemedPromo data){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("DateTimeRedeem", data.getDateTimeRedeem());
                contentValues.put("PromoID", data.getPromoID());
                contentValues.put("GuarantorID", data.getGuarantorID());
                contentValues.put("BorrowerID", data.getBorrowerID());
                contentValues.put("MobileNumber", data.getMobileNumber());
                contentValues.put("IMEI", data.getIMEI());
                contentValues.put("XMLDetails", data.getXMLDetails());
                contentValues.put("Status", data.getStatus());
                contentValues.put("Extra1", data.getExtra1());
                contentValues.put("Extra2", data.getExtra2());
                contentValues.put("Notes1", data.getNotes1());
                contentValues.put("Notes2", data.getNotes2());
                contentValues.put("PromoName", data.getPromoName());
                contentValues.put("PromoLogo", data.getPromoLogo());
                sql.insert(REDEEMED_PROMO_HISTORY, null, contentValues);
            }

            public void deletePromoHistory (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + REDEEMED_PROMO_HISTORY + "");
            }

            public List<RedeemedPromo> getPromoHistory (DatabaseHandler db){
                List<RedeemedPromo> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + REDEEMED_PROMO_HISTORY, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String DateTimeRedeem = cursor.getString(cursor.getColumnIndex("DateTimeRedeem"));
                        String PromoID = cursor.getString(cursor.getColumnIndex("PromoID"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String BorrowerID = cursor.getString(cursor.getColumnIndex("BorrowerID"));
                        String MobileNumber = cursor.getString(cursor.getColumnIndex("MobileNumber"));
                        String IMEI = cursor.getString(cursor.getColumnIndex("IMEI"));
                        String XMLDetails = cursor.getString(cursor.getColumnIndex("XMLDetails"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Extra1 = cursor.getString(cursor.getColumnIndex("Extra1"));
                        String Extra2 = cursor.getString(cursor.getColumnIndex("Extra2"));
                        String Notes1 = cursor.getString(cursor.getColumnIndex("Notes1"));
                        String Notes2 = cursor.getString(cursor.getColumnIndex("Notes2"));
                        String PromoName = cursor.getString(cursor.getColumnIndex("PromoName"));
                        String PromoLogo = cursor.getString(cursor.getColumnIndex("PromoLogo"));

                        arrList.add(new RedeemedPromo(DateTimeRedeem, PromoID, GuarantorID,
                                BorrowerID, MobileNumber, IMEI, XMLDetails, Status, Extra1, Extra2, Notes1, Notes2,
                                PromoName, PromoLogo));
                    }
                }
                return arrList;
            }

            //CSB_REDEMPTION_HISTORY
            public void insertCSBRedemption (DatabaseHandler db, CSBRedemption data){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("SendDetails", data.getStrSendDetails());
                contentValues.put("ReceiveDetails", data.getStrReceiveDetails());
                contentValues.put("ResultCode", data.getResultCode());
                contentValues.put("ResultDescription", data.getResultDescription());
                contentValues.put("DateTimeCompleted", data.getDateTimeCompleted());
                sql.insert(CSB_REDEMPTION_HISTORY, null, contentValues);
            }

            public void deleteCSBRedemption (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + CSB_REDEMPTION_HISTORY + "");
            }

            public List<CSBRedemption> getCSBRedemptions (DatabaseHandler db){
                List<CSBRedemption> redemptions = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + CSB_REDEMPTION_HISTORY + " ORDER BY 'DateTimeCompleted' ASC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String SendDetails = cursor.getString(cursor.getColumnIndex("SendDetails"));
                        String ReceiveDetails = cursor.getString(cursor.getColumnIndex("ReceiveDetails"));
                        String ResultCode = cursor.getString(cursor.getColumnIndex("ResultCode"));
                        String ResultDescription = cursor.getString(cursor.getColumnIndex("ResultDescription"));
                        String DateTimeCompleted = cursor.getString(cursor.getColumnIndex("DateTimeCompleted"));

                        redemptions.add(new CSBRedemption(SendDetails, ReceiveDetails, ResultCode, ResultDescription, DateTimeCompleted));
                    }
                }
                return redemptions;
            }

            //APP VERSION
            public void insertAppVersion (DatabaseHandler db, AppVersion data){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("appversion", data.getAppversion());
                contentValues.put("serviceversion", data.getServiceversion());
                sql.insert(APP_VERSION, null, contentValues);
            }

            public void deleteAppVersion (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + APP_VERSION + "");
            }

            public AppVersion getAppVersion (DatabaseHandler db){
                AppVersion version = new AppVersion("", "");
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + APP_VERSION, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String appversion = cursor.getString(cursor.getColumnIndex("appversion"));
                        String serviceversion = cursor.getString(cursor.getColumnIndex("serviceversion"));

                        version = new AppVersion(appversion, serviceversion);
                    }
                }

                return version;
            }

            //GK Services
            public void insertGKServices (DatabaseHandler db, GKService data){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", data.getID());
                contentValues.put("ServiceCode", data.getServiceCode());
                contentValues.put("ServiceName", data.getServiceName());
                contentValues.put("Type", data.getType());
                contentValues.put("Status", data.getStatus());
                contentValues.put("Logo", data.getLogo());
                contentValues.put("MerchantID", data.getMerchantID());
                contentValues.put("GKStoreID", data.getGKStoreID());
                contentValues.put("ServiceType", data.getServiceType());
                contentValues.put("GKStoreStatus", data.getGKStoreStatus());
                contentValues.put("GuarantorID", data.getGuarantorID());
                contentValues.put("Category", data.getCategory());
                contentValues.put("Color", data.getColor());
                contentValues.put("Rank", data.getRank() == 0 ? 2020 : data.getRank());
                sql.insert(GK_SERVICES, null, contentValues);
            }

            public void deleteGKServices (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + GK_SERVICES + "");
            }

            public List<GKService> getGKServices (DatabaseHandler db){
                List<GKService> services = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GK_SERVICES + " ORDER BY RANK  ASC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                        String Type = cursor.getString(cursor.getColumnIndex("Type"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                        String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                        String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                        String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                        String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String Category = cursor.getString(cursor.getColumnIndex("Category"));
                        String Color = cursor.getString(cursor.getColumnIndex("Color"));
                        int Rank = cursor.getInt(cursor.getColumnIndex("Rank"));
                        services.add(new GKService(ID, ServiceCode, ServiceName, Type, Status, Logo,
                                MerchantID, GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,Rank));
                    }
                }
                cursor.close();
                return services;
            }

            public List<GKService> getGKServicesForKeyword (DatabaseHandler db, String keyword){
                List<GKService> services = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GK_SERVICES +
                        " WHERE  ServiceCode!='BUYPREPAIDLOAD' " +
                        "AND ServiceCode!='PAYBILLS' " +
                        "AND ServiceCode!='BUY_VOUCHER' " +
                        "AND ServiceCode!='GKNEGOSYO'  " +
                        "AND ServiceCode!='PAYVIAQR' " +
                        "AND ServiceCode!='PROMO' " +
                        "AND ServiceCode!='SENDMONEY' " +
                        "AND ServiceCode!='REFER_A_FRIEND' " +
                        "AND ServiceCode!='WHATS_NEW' " +
                        "AND ServiceCode!='DROPOFF' " +
                        "AND ServiceCode!='FREESMS' " +
                        "AND ServiceCode!='GKEARN' " +
                        " AND ServiceName   LIKE '%" + keyword + "%' ORDER BY  ID  ASC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                        String Type = cursor.getString(cursor.getColumnIndex("Type"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                        String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                        String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                        String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                        String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String Category = cursor.getString(cursor.getColumnIndex("Category"));
                        String Color = cursor.getString(cursor.getColumnIndex("Color"));
                        int Rank =  cursor.getInt(cursor.getColumnIndex("Rank"));
                        services.add(new GKService(ID, ServiceCode, ServiceName, Type,
                                Status, Logo, MerchantID, GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,Rank));
                    }
                }
                cursor.close();
                return services;
            }

            //NEWLY ADDED - getAllGKServicesBYKeyword
            public List<GKService> getAllGKServicesBYKeyword (DatabaseHandler db, String keyword){
                List<GKService> services = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GK_SERVICES +
                        " WHERE ServiceName   LIKE '%" + keyword + "%' ORDER BY  ID  ASC", null);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                        String Type = cursor.getString(cursor.getColumnIndex("Type"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                        String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                        String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                        String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                        String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String Category = cursor.getString(cursor.getColumnIndex("Category"));
                        String Color = cursor.getString(cursor.getColumnIndex("Color"));
                        int rank = cursor.getInt(cursor.getColumnIndex("Rank"));
                        services.add(new GKService(ID, ServiceCode, ServiceName, Type,
                                Status, Logo, MerchantID, GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,rank));
                    }
                }
                cursor.close();
                return services;
            }


            public List<GKService> getGKServicesForPage (DatabaseHandler db,int page,
            int nofItemsPerPage){
                List<GKService> services = new ArrayList<>();
                try {
                    SQLiteDatabase sql = db.getWritableDatabase();
                    Cursor cursor = sql.rawQuery("SELECT * FROM " + GK_SERVICES + " " +
                            " WHERE  ServiceCode!='BUYPREPAIDLOAD' " +
                            "AND ServiceCode!='PAYBILLS' " +
                            "AND ServiceCode!='BUY_VOUCHER' " +
                            "AND ServiceCode!='GKNEGOSYO'  " +
                            "AND ServiceCode!='PAYVIAQR' " +
                            "AND ServiceCode!='PROMO' " +
                            "AND ServiceCode!='SENDMONEY' " +
                            "AND ServiceCode!='REFER_A_FRIEND' " +
                            "AND ServiceCode!='WHATS_NEW' " +
                            "AND ServiceCode!='DROPOFF' " +
                            "AND ServiceCode!='FREESMS' " +
                            "AND ServiceCode!='GKEARN' ", null);

                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                            String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                            String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                            String Type = cursor.getString(cursor.getColumnIndex("Type"));
                            String Status = cursor.getString(cursor.getColumnIndex("Status"));
                            String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                            String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                            String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                            String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                            String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                            String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                            String Category = cursor.getString(cursor.getColumnIndex("Category"));
                            String Color = cursor.getString(cursor.getColumnIndex("Color"));
                            int Rank = cursor.getInt(cursor.getColumnIndex("Rank"));
                            services.add(new GKService(ID, ServiceCode, ServiceName, Type, Status, Logo, MerchantID,
                                    GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,Rank));
                        }
                    }


                    switch (page) {
                        case 1: {
                            if ((services.size()) < 12) {
                                services = services.subList(0, services.size());
                            } else {
                                services = services.subList(0, 12);
                            }

                            break;
                        }
                        case 2: {
                            if ((services.size()) > 12 && (services.size()) < 24) {
                                services = services.subList(12, services.size());
                            } else {
                                services = services.subList(12, 24);
                            }
                            break;
                        }
                        case 3: {
                            if ((services.size()) > 24 && (services.size()) < 36) {
                                services = services.subList(24, services.size());
                            } else {
                                services = services.subList(24, 36);
                            }
                            break;
                        }
                        case 4: {
                            if ((services.size()) > 36 && (services.size()) < 48) {
                                services = services.subList(36, services.size());
                            } else {
                                services = services.subList(36, 48);
                            }
                            break;
                        }
                        case 5: {
                            if ((services.size()) > 48 && (services.size()) < 60) {
                                services = services.subList(48, services.size());
                            } else {
                                services = services.subList(48, 60);
                            }
                            break;
                        }

                        default: {
                            break;
                        }
                    }

                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    e.getLocalizedMessage();
                }
                return services;
            }

            //COUNT FOR EXCLUDED SERVICES
            public int getCountGKServicesCount (DatabaseHandler db){
                int count = 0;

                try {
                    SQLiteDatabase sql = db.getWritableDatabase();
                    Cursor cursor = sql.rawQuery("SELECT COUNT(*) FROM " + GK_SERVICES + " " +
                            " WHERE ( ServiceCode='BUYPREPAIDLOAD' " +
                            "OR ServiceCode='PAYBILLS' " +
                            "OR ServiceCode='BUY_VOUCHER' " +
                            "OR ServiceCode='GKNEGOSYO'  " +
                            "OR ServiceCode='PAYVIAQR' " +
                            "OR ServiceCode='PROMO' " +
                            "OR ServiceCode='SENDMONEY' " +
                            "OR ServiceCode='REFER_A_FRIEND' " +
                            "OR ServiceCode='WHATS_NEW' " +
                            "OR ServiceCode='DROPOFF' " +
                            "OR ServiceCode='FREESMS' " +
                            "OR ServiceCode='GKEARN' )" +
                            "AND Status='ACTIVE' ", null);

                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        count = cursor.getInt(0);
                    }

                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return count;
            }

            //SubscriberPayment

            public void insertSubscriberPaymentSummary (DatabaseHandler db, PaymentSummary data){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", data.getID());
                contentValues.put("DateTimeIN", data.getDateTimeIN());
                contentValues.put("PaymentTxnNo", data.getPaymentTxnNo());
                contentValues.put("AccountType", data.getAccountType());
                contentValues.put("AccountID", data.getAccountID());
                contentValues.put("AccountName", data.getAccountName());
                contentValues.put("Amount", data.getAmount());
                contentValues.put("isAdvancePayment", data.getIsAdvancePayment());
                contentValues.put("CollectedBy", data.getCollectedBy());
                contentValues.put("CollectedByUser", data.getCollectedByUser());
                contentValues.put("CollectionMedium", data.getCollectionMedium());
                contentValues.put("PaymentOption", data.getPaymentOption());
                contentValues.put("Status", data.getStatus());
                contentValues.put("Extra1", data.getExtra1());
                contentValues.put("Extra2", data.getExtra2());
                contentValues.put("Extra3", data.getExtra3());
                contentValues.put("Extra4", data.getExtra4());
                contentValues.put("Notes1", data.getNotes1());
                contentValues.put("Notes2", data.getNotes2());

                sql.insert(SUBSCIRBER_PAYMENT, null, contentValues);
            }

            public void deleteSubscriberPaymentSummary (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + SUBSCIRBER_PAYMENT + "");
            }

            public List<PaymentSummary> getSubscriberPayments (DatabaseHandler db){
                List<PaymentSummary> payments = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SUBSCIRBER_PAYMENT + " ORDER BY DateTimeIN DESC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String DateTimeIN = cursor.getString(cursor.getColumnIndex("DateTimeIN"));
                        String PaymentTxnNo = cursor.getString(cursor.getColumnIndex("PaymentTxnNo"));
                        String AccountType = cursor.getString(cursor.getColumnIndex("AccountType"));
                        String AccountID = cursor.getString(cursor.getColumnIndex("AccountID"));
                        String AccountName = cursor.getString(cursor.getColumnIndex("AccountName"));
                        double Amount = cursor.getDouble(cursor.getColumnIndex("Amount"));
                        String isAdvancePayment = cursor.getString(cursor.getColumnIndex("isAdvancePayment"));
                        String CollectedBy = cursor.getString(cursor.getColumnIndex("CollectedBy"));
                        String CollectedByUser = cursor.getString(cursor.getColumnIndex("CollectedByUser"));
                        String CollectionMedium = cursor.getString(cursor.getColumnIndex("CollectionMedium"));
                        String PaymentOption = cursor.getString(cursor.getColumnIndex("PaymentOption"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Extra1 = cursor.getString(cursor.getColumnIndex("Extra1"));
                        String Extra2 = cursor.getString(cursor.getColumnIndex("Extra2"));
                        String Extra3 = cursor.getString(cursor.getColumnIndex("Extra3"));
                        String Extra4 = cursor.getString(cursor.getColumnIndex("Extra4"));
                        String Notes1 = cursor.getString(cursor.getColumnIndex("Notes1"));
                        String Notes2 = cursor.getString(cursor.getColumnIndex("Notes2"));

                        payments.add(new PaymentSummary(ID, DateTimeIN, PaymentTxnNo, AccountType, AccountID, AccountName, Amount, isAdvancePayment, CollectedBy, CollectedByUser,
                                CollectionMedium, PaymentOption, Status, Extra1, Extra2, Extra3, Extra4, Notes1, Notes2));
                    }
                }
                return payments;
            }

            //SubscriberBill
            public void insertSubscriberBillSummary (DatabaseHandler db, SubscriberBillSummary data)
            {

                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("ID", data.getID());
                contentValues.put("DateTimeIN", data.getDateTimeIN());
                contentValues.put("GuarantorID", data.getGuarantorID());
                contentValues.put("GuarantorName", data.getGuarantorName());
                contentValues.put("SubGuarantorID", data.getSubGuarantorID());
                contentValues.put("BorrowerID", data.getBorrowerID());
                contentValues.put("BorrowerName", data.getBorrowerName());
                contentValues.put("BillingID", data.getBillingID());
                contentValues.put("StatementDate", data.getStatementDate());
                contentValues.put("DueDate", data.getDueDate());
                contentValues.put("Amount", data.getAmount());
                contentValues.put("PeriodFrom", data.getPeriodFrom());
                contentValues.put("PeriodTo", data.getPeriodTo());
                contentValues.put("isNotified", data.getIsNotified());
                contentValues.put("Extra1", data.getExtra1());
                contentValues.put("Extra2", data.getExtra2());
                contentValues.put("Extra3", data.getExtra3());
                contentValues.put("Extra4", data.getExtra4());
                contentValues.put("Notes1", data.getNotes1());
                contentValues.put("Notes2", data.getNotes2());

                sql.insert(SUBSCIRBER_BILL, null, contentValues);
            }

            public void deleteSubscriberBillSummary (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + SUBSCIRBER_BILL + "");
            }

            public List<SubscriberBillSummary> getSubscriberBills (DatabaseHandler db){
                List<SubscriberBillSummary> bills = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SUBSCIRBER_BILL + " ORDER BY DueDate DESC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String DateTimeIN = cursor.getString(cursor.getColumnIndex("DateTimeIN"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String GuarantorName = cursor.getString(cursor.getColumnIndex("GuarantorName"));
                        String SubGuarantorID = cursor.getString(cursor.getColumnIndex("SubGuarantorID"));
                        String BorrowerID = cursor.getString(cursor.getColumnIndex("BorrowerID"));
                        String BorrowerName = cursor.getString(cursor.getColumnIndex("BorrowerName"));
                        String BillingID = cursor.getString(cursor.getColumnIndex("BillingID"));
                        String StatementDate = cursor.getString(cursor.getColumnIndex("StatementDate"));
                        String DueDate = cursor.getString(cursor.getColumnIndex("DueDate"));
                        double Amount = cursor.getDouble(cursor.getColumnIndex("Amount"));
                        String PeriodFrom = cursor.getString(cursor.getColumnIndex("PeriodFrom"));
                        String PeriodTo = cursor.getString(cursor.getColumnIndex("PeriodTo"));
                        String isNotified = cursor.getString(cursor.getColumnIndex("isNotified"));
                        String Extra1 = cursor.getString(cursor.getColumnIndex("Extra1"));
                        String Extra2 = cursor.getString(cursor.getColumnIndex("Extra2"));
                        String Extra3 = cursor.getString(cursor.getColumnIndex("Extra3"));
                        String Extra4 = cursor.getString(cursor.getColumnIndex("Extra4"));
                        String Notes1 = cursor.getString(cursor.getColumnIndex("Notes1"));
                        String Notes2 = cursor.getString(cursor.getColumnIndex("Notes2"));

                        bills.add(new SubscriberBillSummary(ID, DateTimeIN, GuarantorID, GuarantorName, SubGuarantorID, BorrowerID, BorrowerName,
                                BillingID, StatementDate, DueDate, Amount, PeriodFrom, PeriodTo, isNotified,
                                Extra1, Extra2, Extra3, Extra4, Notes1, Notes2));
                    }
                }
                return bills;
            }

            public ArrayList<SubscriberBillSummary> getSubscriberBilling (DatabaseHandler db, String
            Status){
                ArrayList<SubscriberBillSummary> arrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor cursor;
                if (Status.equals("CURRENT")) {
                    cursor = sql.rawQuery("SELECT * FROM " + SUBSCIRBER_BILL + " ORDER BY DueDate DESC LIMIT 1", null);
                } else {
                    cursor = sql.rawQuery("SELECT * FROM " + SUBSCIRBER_BILL + " ORDER BY DueDate DESC LIMIT 1,100", null);
                }


                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String DateTimeIN = cursor.getString(cursor.getColumnIndex("DateTimeIN"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String GuarantorName = cursor.getString(cursor.getColumnIndex("GuarantorName"));
                        String SubGuarantorID = cursor.getString(cursor.getColumnIndex("SubGuarantorID"));
                        String BorrowerID = cursor.getString(cursor.getColumnIndex("BorrowerID"));
                        String BorrowerName = cursor.getString(cursor.getColumnIndex("BorrowerName"));
                        String BillingID = cursor.getString(cursor.getColumnIndex("BillingID"));
                        String StatementDate = cursor.getString(cursor.getColumnIndex("StatementDate"));
                        String DueDate = cursor.getString(cursor.getColumnIndex("DueDate"));
                        double Amount = cursor.getDouble(cursor.getColumnIndex("Amount"));
                        String PeriodFrom = cursor.getString(cursor.getColumnIndex("PeriodFrom"));
                        String PeriodTo = cursor.getString(cursor.getColumnIndex("PeriodTo"));
                        String isNotified = cursor.getString(cursor.getColumnIndex("isNotified"));
                        String Extra1 = cursor.getString(cursor.getColumnIndex("Extra1"));
                        String Extra2 = cursor.getString(cursor.getColumnIndex("Extra2"));
                        String Extra3 = cursor.getString(cursor.getColumnIndex("Extra3"));
                        String Extra4 = cursor.getString(cursor.getColumnIndex("Extra4"));
                        String Notes1 = cursor.getString(cursor.getColumnIndex("Notes1"));
                        String Notes2 = cursor.getString(cursor.getColumnIndex("Notes2"));

                        arrayList.add(new SubscriberBillSummary(ID, DateTimeIN, GuarantorID, GuarantorName, SubGuarantorID, BorrowerID, BorrowerName,
                                BillingID, StatementDate, DueDate, Amount, PeriodFrom, PeriodTo, isNotified,
                                Extra1, Extra2, Extra3, Extra4, Notes1, Notes2));
                    }
                }
                cursor.close();
                return arrayList;
            }

            //FAQ
            public void deleteFAQ (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + FAQ + "");
            }

            public void insertFAQ (DatabaseHandler db, GetFAQResponseData data){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("id", data.getID());
                value.put("helptopic", data.getHelpTopic());
                value.put("faqid", data.getFAQID());
                value.put("question", CommonFunctions.replaceEscapeData(data.getQuestion()));
                value.put("answer", CommonFunctions.replaceEscapeData(data.getAnswer()));
                value.put("extra1", data.getExtra1());
                value.put("extra2", data.getExtra2());
                value.put("extra3", data.getExtra3());
                value.put("notes1", data.getNotes1());
                value.put("notes2", data.getNotes2());

                sql.insert(FAQ, null, value);
            }

            public ArrayList<GetFAQResponseData> getFAQResponseData (DatabaseHandler db, String
            mHelptopic){
                ArrayList<GetFAQResponseData> arrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * From " + FAQ + " WHERE helptopic = ?", new String[]{mHelptopic});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(cursor.getColumnIndex("id"));
                        String helptopic = cursor.getString(cursor.getColumnIndex("helptopic"));
                        String faqid = cursor.getString(cursor.getColumnIndex("faqid"));
                        String question = cursor.getString(cursor.getColumnIndex("question"));
                        String answer = cursor.getString(cursor.getColumnIndex("answer"));
                        String extra1 = cursor.getString(cursor.getColumnIndex("extra1"));
                        String extra2 = cursor.getString(cursor.getColumnIndex("extra2"));
                        String extra3 = cursor.getString(cursor.getColumnIndex("extra3"));
                        String notes1 = cursor.getString(cursor.getColumnIndex("notes1"));
                        String notes2 = cursor.getString(cursor.getColumnIndex("notes2"));

                        arrayList.add(new GetFAQResponseData(id, helptopic, faqid, question, answer, extra1, extra2, extra3, notes1, notes2));
                    }
                }
                return arrayList;
            }

            //topics
            public void deleteHelpTopic (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + HELP_TOPIC + "");
            }

            public String getStringFromTopicTable (DatabaseHandler db, String query){
                String str = ".";
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + HELP_TOPIC + " WHERE helptopic = ? LIMIT 1", new String[]{query});
                cursor.moveToFirst();
                str = cursor.getString(cursor.getColumnIndex("priority"));
                return str;
            }

            public ArrayList<GetHelpTopicsResponseData> getHelpTopicsResponseData (DatabaseHandler
            db){
                ArrayList<GetHelpTopicsResponseData> arrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * From " + HELP_TOPIC + "", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String id = cursor.getString(cursor.getColumnIndex("id"));
                        String helptopic = cursor.getString(cursor.getColumnIndex("helptopic"));
                        String category = cursor.getString(cursor.getColumnIndex("category"));
                        String priority = cursor.getString(cursor.getColumnIndex("priority"));
                        String supportdepartment = cursor.getString(cursor.getColumnIndex("supportdepartment"));
                        String extra1 = cursor.getString(cursor.getColumnIndex("extra1"));
                        String extra2 = cursor.getString(cursor.getColumnIndex("extra2"));
                        String extra3 = cursor.getString(cursor.getColumnIndex("extra3"));
                        String notes1 = cursor.getString(cursor.getColumnIndex("notes1"));
                        String notes2 = cursor.getString(cursor.getColumnIndex("notes2"));
                        String logo = cursor.getString(cursor.getColumnIndex("logo"));
                        String description = cursor.getString(cursor.getColumnIndex("description"));

                        arrayList.add(new GetHelpTopicsResponseData(id, helptopic, category, priority, supportdepartment, extra1, extra2, extra3, notes1, notes2, logo, description));
                    }
                }
                return arrayList;
            }

            public void insertHelpTopics (DatabaseHandler db, GetHelpTopicsResponseData data){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("id", data.getID());
                value.put("helptopic", data.getHelpTopic());
                value.put("category", data.getCategory());
                value.put("priority", data.getPriority());
                value.put("supportdepartment", data.getSupportDepartment());
                value.put("extra1", data.getExtra1());
                value.put("extra2", data.getExtra2());
                value.put("extra3", data.getExtra3());
                value.put("notes1", data.getNotes1());
                value.put("notes2", data.getNotes2());
                value.put("logo", data.getLogo());
                value.put("description", CommonFunctions.replaceEscapeData(data.getDescription()));
                sql.insert(HELP_TOPIC, null, value);
            }

            //threads
            public void insertSupportMessagesThreads (DatabaseHandler
            db, GetSupportMessagesResponseData data){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("ID", data.getID());
                value.put("DateTimeIN", data.getDateTimeIN());
                value.put("DateTimeCompleted", data.getDateTimeCompleted());
                value.put("ThreadID", data.getThreadID());
                value.put("HelpTopic", data.getHelpTopic());
                value.put("Subject", data.getSubject());
                value.put("SupportUserID", data.getSupportUserID());
                value.put("MobileNo", data.getMobileNo());
                value.put("GKBorrowerID", data.getGKBorrowerID());
                value.put("GKBorrowerName", data.getGKBorrowerName());
                value.put("GKIMEI", data.getGKIMEI());
                value.put("GKEmailAddress", data.getGKEmailAddress());
                value.put("Status", data.getStatus());
                value.put("NotificationStatus", data.getNotificationStatus());
                value.put("Priority", data.getPriority());
                value.put("Logo", data.getLogo());
                sql.insert(SUPPORT_MESSAGES_THREADS, null, value);
            }

            public void updateSupportMessagesThreadsReadStatus (DatabaseHandler db, String
            readStatus, String threadid){
                SQLiteDatabase sql = db.getWritableDatabase();

                sql.execSQL("UPDATE " + SUPPORT_MESSAGES_THREADS + " SET NotificationStatus =  ? WHERE ThreadID = ? ", new String[]{readStatus, threadid});
            }

            public void deleteSupportMessagesThreads (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(SUPPORT_MESSAGES_THREADS, null, null);
            }

            public void updateSupportMessageThreadStatus (DatabaseHandler db, String threadid){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.rawQuery("UPDATE " + SUPPORT_MESSAGES_THREADS + " SET Status = ? WHERE ThreadID = ?", new String[]{"OPEN", threadid});
            }

            public ArrayList<GetSupportMessagesResponseData> getAllMessagesThreads (DatabaseHandler
            db, String status){
                ArrayList<GetSupportMessagesResponseData> arrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * From " + SUPPORT_MESSAGES_THREADS + " WHERE Status = ?  ORDER BY DateTimeIN DESC", new String[]{status});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String ID = cursor.getString(cursor.getColumnIndex("ID"));
                        String DateTimeIN = cursor.getString(cursor.getColumnIndex("DateTimeIN"));
                        String DateTimeCompleted = cursor.getString(cursor.getColumnIndex("DateTimeCompleted"));
                        String ThreadID = cursor.getString(cursor.getColumnIndex("ThreadID"));
                        String HelpTopic = cursor.getString(cursor.getColumnIndex("HelpTopic"));
                        String Subject = cursor.getString(cursor.getColumnIndex("Subject"));
                        String SupportUserID = cursor.getString(cursor.getColumnIndex("SupportUserID"));
                        String MobileNo = cursor.getString(cursor.getColumnIndex("MobileNo"));
                        String GKBorrowerID = cursor.getString(cursor.getColumnIndex("GKBorrowerID"));
                        String GKBorrowerName = cursor.getString(cursor.getColumnIndex("GKBorrowerName"));
                        String GKIMEI = cursor.getString(cursor.getColumnIndex("GKIMEI"));
                        String GKEmailAddress = cursor.getString(cursor.getColumnIndex("GKEmailAddress"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String NotificationStatus = cursor.getString(cursor.getColumnIndex("NotificationStatus"));
                        String Priority = cursor.getString(cursor.getColumnIndex("Priority"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));

                        arrayList.add(new GetSupportMessagesResponseData(ID, DateTimeIN, DateTimeCompleted,
                                ThreadID, HelpTopic, Subject, SupportUserID, MobileNo, GKBorrowerID,
                                GKBorrowerName, GKIMEI, GKEmailAddress, NotificationStatus, Status, Priority, Logo));
                    }
                }
                cursor.close();
                return arrayList;
            }

            //conversation
            public void deleteMessagesConversation (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(SUPPORT_MESSAGES_CONVERSATION, null, null);
            }

            public void deleteMessagesConversationsInThread (DatabaseHandler db, String threadid){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(SUPPORT_MESSAGES_CONVERSATION, " ThreadID = ?", new String[]{threadid});
            }

            public void insertMessagesConversation (DatabaseHandler
            db, GetSupportConversationResponseData data){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues value = new ContentValues();
                value.put("ID", data.getID());
                value.put("DateTimeIN", CommonFunctions.dateFormatForSQLITE(data.getDateTimeIn()));
                value.put("ThreadID", data.getThreadID());
                value.put("ReplySupportUserID", data.getReplySupportUserID());
                value.put("ReplySupportUserName", data.getReplySupportName());
                value.put("ThreadType", data.getThreadType());
                value.put("Message", data.getMessage());
                sql.insert(SUPPORT_MESSAGES_CONVERSATION, null, value);
            }

            public boolean alreadyExist (DatabaseHandler db, String message, String threadid){
                boolean bool = false;
                for (GetSupportConversationResponseData payload : getConversation(db, threadid)) {
                    if (payload.getMessage().equals(message)) {
                        bool = true;
                        break;
                    }
                }
                return bool;
            }

            public ArrayList<GetSupportConversationResponseData> getAllMessagesConversation
            (DatabaseHandler db){
                ArrayList<GetSupportConversationResponseData> arrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * From " + SUPPORT_MESSAGES_CONVERSATION + " ORDER BY datetimein DESC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String ID = cursor.getString(cursor.getColumnIndex("ID"));
                        String DateTimeIN = cursor.getString(cursor.getColumnIndex("DateTimeIN"));
                        String ThreadID = cursor.getString(cursor.getColumnIndex("ThreadID"));
                        String ReplySupportUserID = cursor.getString(cursor.getColumnIndex("ReplySupportUserID"));
                        String ReplySupportUserName = cursor.getString(cursor.getColumnIndex("ReplySupportUserName"));
                        String ThreadType = cursor.getString(cursor.getColumnIndex("ThreadType"));
                        String Messages = cursor.getString(cursor.getColumnIndex("Message"));

                        arrayList.add(new GetSupportConversationResponseData(ID, DateTimeIN, ThreadID, ReplySupportUserID, ReplySupportUserName, ThreadType, Messages));
                    }
                }
                cursor.close();
                return arrayList;
            }

            public ArrayList<GetSupportConversationResponseData> getConversation (DatabaseHandler
            db, String threadid){
                ArrayList<GetSupportConversationResponseData> arrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * From " + SUPPORT_MESSAGES_CONVERSATION + " WHERE ThreadID = ?  ORDER BY datetime(DateTimeIN) ASC", new String[]{threadid});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String ID = cursor.getString(cursor.getColumnIndex("ID"));
                        String DateTimeIN = cursor.getString(cursor.getColumnIndex("DateTimeIN"));
                        String ThreadID = cursor.getString(cursor.getColumnIndex("ThreadID"));
                        String ReplySupportUserID = cursor.getString(cursor.getColumnIndex("ReplySupportUserID"));
                        String ReplySupportUserName = cursor.getString(cursor.getColumnIndex("ReplySupportUserName"));
                        String ThreadType = cursor.getString(cursor.getColumnIndex("ThreadType"));
                        String Messages = cursor.getString(cursor.getColumnIndex("Message"));

                        arrayList.add(new GetSupportConversationResponseData(ID, DateTimeIN, ThreadID, ReplySupportUserID, ReplySupportUserName, ThreadType, Messages));
                    }
                }
                cursor.close();
                return arrayList;
            }

            //outlets
            public void deleteOutlets (DatabaseHandler db, String merchantGroup){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(OUTLETS, "outletMerchantGroup = ?", new String[]{merchantGroup});
            }

            public void insertOutlets (DatabaseHandler db, OutletsV2 outlet){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("outletBranchName", outlet.getOutletBranchName());
                value.put("outletStreetAddress", outlet.getOutletStreetAddress());
                value.put("outletCity", outlet.getOutletCity());
                value.put("outletProvince", outlet.getOutletProvince());
                value.put("outletCountry", outlet.getOutletCountry());
                value.put("outletTelephone", outlet.getOutletTelephone());
                value.put("outletMobile", outlet.getOutletMobile());
                value.put("outletMerchantID", outlet.getOutletMerchantID());
                value.put("outletBranchID", outlet.getOutletBranchID());
                value.put("outletAuthorisedRepresentative", outlet.getOutletAuthorisedRepresentative());
                value.put("outletZip", outlet.getOutletZip());
                value.put("outletLongitude", outlet.getOutletLongitude());
                value.put("outletLatitude", outlet.getOutletLatitude());
                value.put("outletAuthorisedEmailAddress", outlet.getOutletAuthorisedEmailAddress());
                value.put("outletFax", outlet.getOutletFax());
                value.put("outletDateAdded", outlet.getOutletDateAdded());
                value.put("outletStatus", outlet.getOutletStatus());
                value.put("outletMerchantGroup", outlet.getOutletMerchantGroup());

                sql.insert(OUTLETS, null, value);
            }

            public ArrayList<Outlets> getOutlets (String merchantGroup){
                ArrayList<Outlets> outletsArrayList = new ArrayList<>();
                try {
                    outletsArrayList.clear();
                    SQLiteDatabase sql = getReadableDatabase();
                    Cursor cursor = sql.rawQuery("SELECT * FROM " + OUTLETS + " WHERE outletMerchantGroup = ?", new String[]{merchantGroup});
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            String outletBranchName = cursor.getString(cursor.getColumnIndex("outletBranchName"));
                            String outletStreetAddress = cursor.getString(cursor.getColumnIndex("outletStreetAddress"));
                            String outletCity = cursor.getString(cursor.getColumnIndex("outletCity"));
                            String outletProvince = cursor.getString(cursor.getColumnIndex("outletProvince"));
                            String outletCountry = cursor.getString(cursor.getColumnIndex("outletCountry"));
                            String outletTelephone = cursor.getString(cursor.getColumnIndex("outletTelephone"));
                            String outletMobile = cursor.getString(cursor.getColumnIndex("outletMobile"));
                            String outletMerchantID = cursor.getString(cursor.getColumnIndex("outletMerchantID"));
                            String outletBranchID = cursor.getString(cursor.getColumnIndex("outletBranchID"));
                            String outletAuthorisedRepresentative = cursor.getString(cursor.getColumnIndex("outletAuthorisedRepresentative"));
                            String outletZip = cursor.getString(cursor.getColumnIndex("outletZip"));
                            String outletLongitude = cursor.getString(cursor.getColumnIndex("outletLongitude"));
                            String outletLatitude = cursor.getString(cursor.getColumnIndex("outletLatitude"));
                            String outletAuthorisedEmailAddress = cursor.getString(cursor.getColumnIndex("outletAuthorisedEmailAddress"));
                            String outletFax = cursor.getString(cursor.getColumnIndex("outletFax"));
                            String outletDateAdded = cursor.getString(cursor.getColumnIndex("outletDateAdded"));
                            String outletStatus = cursor.getString(cursor.getColumnIndex("outletStatus"));
                            String outletMerchantGroup = cursor.getString(cursor.getColumnIndex("outletMerchantGroup"));

                            outletsArrayList.add(new Outlets(outletBranchName, outletStreetAddress, outletCity, outletProvince,
                                    outletCountry, outletTelephone, outletMobile, outletMerchantID, outletBranchID,
                                    outletAuthorisedRepresentative, outletZip, outletLongitude, outletLatitude,
                                    outletAuthorisedEmailAddress, outletFax, outletDateAdded, outletStatus, outletMerchantGroup));
                        }
                    }
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return outletsArrayList;
            }

            //GET Branches
            public int getNoOfBranchesperMerchantGroup (String merchantgroup){
                int count = 0;

                try {
                    SQLiteDatabase sql = getReadableDatabase();
                    Cursor cursor = sql.rawQuery("SELECT SUM(NoOfBranches) AS BranchesTotal FROM " + MERCHANTS
                                    + " WHERE MerchantGroup" + "=?"
                                    + " GROUP BY MerchantGroup"
                            , new String[]{merchantgroup});

                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            count = cursor.getInt(cursor.getColumnIndex("BranchesTotal"));
                            Logger.debug("cbhttp", "count: " + count);
                        }
                    }
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return count;
            }

            //Promotion
            public void deletePromotion (DatabaseHandler db, String merchantID){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(PROMOTIONS, "MerchantID = ?", new String[]{merchantID});
            }

            public void insertPromotion (DatabaseHandler db, Promotions promotion){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("MerchantID", promotion.getMerchantID());
                value.put("PromoLink", promotion.getPromoLink());

                sql.insert(PROMOTIONS, null, value);
            }

            public List<Promotions> getPromotion (DatabaseHandler db, String merchantID){

                List<Promotions> promotionsArrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + PROMOTIONS + " WHERE MerchantID " + "=?";

                //Cursor c = sql.rawQuery(strsql, null);
                Cursor c = sql.rawQuery(strsql, new String[]{merchantID});

                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String MerchantID = c.getString(c.getColumnIndex("MerchantID"));
                        String PromoLink = c.getString(c.getColumnIndex("PromoLink"));

                        promotionsArrayList.add(new Promotions(MerchantID, PromoLink));
                    }
                }

                c.close();
                db.close();
                return promotionsArrayList;
            }

            //GK STORE PRODUCTS
            public void deleteGKStoreProducts (DatabaseHandler db, String merchantid){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(GK_STORE_PRODUCTS, "MerchantID = ?", new String[]{merchantid});
            }

            public void insertGKStoreProducts (DatabaseHandler db, GKStoreProducts gkstore){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("MerchantID", gkstore.getMerchantID());
                value.put("GKStoreID", gkstore.getStoreID());
                value.put("ProductID", gkstore.getProductID());
                value.put("ProductName", gkstore.getProductName());
                value.put("ProductDesc", gkstore.getProductDesc());
                value.put("GrossPrice", gkstore.getGrossPrice());
                value.put("DiscountPercentage", gkstore.getDiscountPercentage());
                value.put("ActualPrice", gkstore.getActualPrice());
                value.put("ProductImageURL", gkstore.getProductImageURL());
                value.put("isPromo", gkstore.getisPromo());
                value.put("PromoDetails", gkstore.getPromoDetails());
                value.put("isSelected", gkstore.getIsSelected());

                sql.insert(GK_STORE_PRODUCTS, null, value);
            }

            public List<GKStoreProducts> getGKStoreProducts (DatabaseHandler db, String merchantid){

                List<GKStoreProducts> gkStoreProductsProductsArrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + GK_STORE_PRODUCTS + " WHERE MerchantID " + "=?";
                Cursor c = sql.rawQuery(strsql, new String[]{merchantid});

                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String MerchantID = c.getString(c.getColumnIndex("MerchantID"));
                        String StoreID = c.getString(c.getColumnIndex("GKStoreID"));
                        String ProductID = c.getString(c.getColumnIndex("ProductID"));
                        String ProductName = c.getString(c.getColumnIndex("ProductName"));
                        String ProductDesc = c.getString(c.getColumnIndex("ProductDesc"));
                        double GrossPrice = c.getDouble(c.getColumnIndex("GrossPrice"));
                        double DiscountPercentage = c.getDouble(c.getColumnIndex("DiscountPercentage"));
                        double ActualPrice = c.getDouble(c.getColumnIndex("ActualPrice"));
                        String ProductImageURL = c.getString(c.getColumnIndex("ProductImageURL"));
                        int isPromo = c.getInt(c.getColumnIndex("isPromo"));
                        String PromoDetails = c.getString(c.getColumnIndex("PromoDetails"));
                        gkStoreProductsProductsArrayList.add(new GKStoreProducts(MerchantID, StoreID, ProductID, ProductName, ProductDesc, GrossPrice, DiscountPercentage, ActualPrice, ProductImageURL, isPromo, PromoDetails));
                    }
                }
                c.close();
                db.close();
                return gkStoreProductsProductsArrayList;
            }

            public List<GKStoreProducts> getGKStoreProductKeyword (DatabaseHandler db, String
            merchantid, String keyword){
                List<GKStoreProducts> gkstoreProducts = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + GK_STORE_PRODUCTS
                        + " WHERE MerchantID " + "=?"
                        + " AND "
                        + " ProductName LIKE '%" + keyword + "%' ORDER BY isSelected DESC, ProductName ASC", new String[]{merchantid});

                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String MerchantID = c.getString(c.getColumnIndex("MerchantID"));
                        String StoreID = c.getString(c.getColumnIndex("GKStoreID"));
                        String ProductID = c.getString(c.getColumnIndex("ProductID"));
                        String ProductName = c.getString(c.getColumnIndex("ProductName"));
                        String ProductDesc = c.getString(c.getColumnIndex("ProductDesc"));
                        double GrossPrice = c.getDouble(c.getColumnIndex("GrossPrice"));
                        double DiscountPercentage = c.getDouble(c.getColumnIndex("DiscountPercentage"));
                        double ActualPrice = c.getDouble(c.getColumnIndex("ActualPrice"));
                        String ProductImageURL = c.getString(c.getColumnIndex("ProductImageURL"));
                        int isPromo = c.getInt(c.getColumnIndex("isPromo"));
                        String PromoDetails = c.getString(c.getColumnIndex("PromoDetails"));
                        int isSelected = c.getInt(c.getColumnIndex("isSelected"));

                        gkstoreProducts.add(new GKStoreProducts(MerchantID, StoreID, ProductID, ProductName, ProductDesc, GrossPrice, DiscountPercentage, ActualPrice, ProductImageURL, isPromo, PromoDetails, isSelected));

                    }
                }
                c.close();
                db.close();
                return gkstoreProducts;
            }


            public void updateGKStoreisSelected (DatabaseHandler db,int isSelected, String productid)
            {
                List<GKStoreProducts> gkstoreProducts = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("isSelected", isSelected);
                sql.update(GK_STORE_PRODUCTS, cv, "ProductID='" + productid + "'", null);
            }

            //GK STORE MERCHANTS
            public void deleteGKStoreMerchants (DatabaseHandler db, String merchantid){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.delete(GK_STORE_MERCHANTS, "MerchantID = ?", new String[]{merchantid});
            }

            public void insertGKStoreMerchants (DatabaseHandler db, GKStoreMerchants gkstore){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("MerchantID", gkstore.getMerchantID());
                value.put("GKStoreID", gkstore.getStoreID());
                value.put("GKStoreName", gkstore.getGKStoreName());
                value.put("GKStoreDesc", gkstore.getGKStoreDesc());
                value.put("GKStoreCategory", gkstore.getGKStoreCategory());
                value.put("GKStoreRepresentative", gkstore.getGKStoreRepresentative());
                value.put("GKStoreMobileNo", gkstore.getGKStoreMobileNo());
                value.put("GKStoreTelNo", gkstore.getGKStoreTelNo());
                value.put("GKStoreEmailAddress", gkstore.getGKStoreEmailAddress());
                value.put("GKStoreMerchantChargeClass", gkstore.getGKStoreMerchantChargeClass());
                value.put("GKStoreCustomerChargeClass", gkstore.getGKStoreCustomerChargeClass());
                value.put("GKStoreLogoURL", gkstore.getGKStoreLogoURL());
                value.put("GKStoreBannerURL", gkstore.getGKStoreBannerURL());
                value.put("KYCOtherInfo", gkstore.getKYCOtherInfo());
                value.put("NoOfProducts", gkstore.getNoOfProducts());
                value.put("NoOfOrders", gkstore.getNoOfOrders());
                value.put("GKStoreNotes", gkstore.getGKStoreNotes());
                value.put("DateTimeAdded", gkstore.getDateTimeAdded());
                value.put("Status", gkstore.getStatus());
                value.put("Extra1", gkstore.getExtra1());
                value.put("Extra2", gkstore.getExtra2());
                value.put("Extra3", gkstore.getExtra3());
                value.put("Extra4", gkstore.getExtra4());
                value.put("Extra4", gkstore.getExtra4());
                value.put("Notes1", gkstore.getNotes1());
                value.put("Notes2", gkstore.getNotes2());

                sql.insert(GK_STORE_MERCHANTS, null, value);
            }

            public GKStoreMerchants getGKStoreMerchants (DatabaseHandler db, String merchantid){
                GKStoreMerchants gkStoreMerchants = new GKStoreMerchants("", "", "", "",
                        "", "", "", "", "",
                        "", "", "", "",
                        "", 0, 0, "", "", "", "",
                        "", "", "", "", "");

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + GK_STORE_MERCHANTS + " WHERE MerchantID " + "=?";

                Cursor c = sql.rawQuery(strsql, new String[]{merchantid});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String MerchantID = c.getString(c.getColumnIndex("MerchantID"));
                        String GKStoreID = c.getString(c.getColumnIndex("GKStoreID"));
                        String GKStoreName = c.getString(c.getColumnIndex("GKStoreName"));
                        String GKStoreDesc = c.getString(c.getColumnIndex("GKStoreDesc"));
                        String GKStoreCategory = c.getString(c.getColumnIndex("GKStoreCategory"));
                        String GKStoreRepresentative = c.getString(c.getColumnIndex("GKStoreRepresentative"));
                        String GKStoreMobileNo = c.getString(c.getColumnIndex("GKStoreMobileNo"));
                        String GKStoreTelNo = c.getString(c.getColumnIndex("GKStoreTelNo"));
                        String GKStoreEmailAddress = c.getString(c.getColumnIndex("GKStoreEmailAddress"));
                        String GKStoreMerchantChargeClass = c.getString(c.getColumnIndex("GKStoreMerchantChargeClass"));
                        String GKStoreCustomerChargeClass = c.getString(c.getColumnIndex("GKStoreCustomerChargeClass"));
                        String GKStoreLogoURL = c.getString(c.getColumnIndex("GKStoreLogoURL"));
                        String GKStoreBannerURL = c.getString(c.getColumnIndex("GKStoreBannerURL"));
                        String KYCOtherInfo = c.getString(c.getColumnIndex("KYCOtherInfo"));
                        int NoOfProducts = c.getInt(c.getColumnIndex("NoOfProducts"));
                        int NoOfOrders = c.getInt(c.getColumnIndex("NoOfOrders"));
                        String GKStoreNotes = c.getString(c.getColumnIndex("GKStoreNotes"));
                        String DateTimeAdded = c.getString(c.getColumnIndex("DateTimeAdded"));
                        String Status = c.getString(c.getColumnIndex("Status"));
                        String Extra1 = c.getString(c.getColumnIndex("Extra1"));
                        String Extra2 = c.getString(c.getColumnIndex("Extra2"));
                        String Extra3 = c.getString(c.getColumnIndex("Extra3"));
                        String Extra4 = c.getString(c.getColumnIndex("Extra4"));
                        String Notes1 = c.getString(c.getColumnIndex("Notes1"));
                        String Notes2 = c.getString(c.getColumnIndex("Notes2"));

                        gkStoreMerchants = new GKStoreMerchants(MerchantID, GKStoreID, GKStoreName, GKStoreDesc, GKStoreCategory,
                                GKStoreRepresentative, GKStoreMobileNo, GKStoreTelNo, GKStoreEmailAddress, GKStoreMerchantChargeClass,
                                GKStoreCustomerChargeClass, GKStoreLogoURL, GKStoreBannerURL, KYCOtherInfo, NoOfProducts,
                                NoOfOrders, GKStoreNotes, DateTimeAdded, Status, Extra1, Extra2, Extra3, Extra4, Notes1, Notes2
                        );
                    }
                }
                c.close();
                db.close();
                return gkStoreMerchants;
            }

            //GET GKSTORE TIME
            public Cursor getGKStoreInformation (DatabaseHandler db, String merchantid){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + GK_STORE_MERCHANTS + " WHERE MerchantID " + "=?" + " LIMIT 1", new String[]{merchantid});
                return c;
            }

            public void insertNewWalletInfo (DatabaseHandler db, String borrowerid, String
            currencyid, String creditlimit, String availablecredits,
                    String usedcredits, String outstandingbalance, String
            currentbillingbalance, String overduebillingbalance,
                    String advances, String lastadvancepaymenttxnno, String
            lastadvancepaymentdatetime, String ledgerbeginningbalance,
                    String ledgerrunningbalance, String currentvoucherinventory, String
            status, String extra1, String extra2, String extra3,
                    String extra4, String notes1, String notes2, String guarantorid, String
            guarantorname, String guarantorprefix,
                    String guarantorstreetaddress, String guarantorcity, String
            guarantorprovince, String guarantorcountry, String gzip, String guarantoremail,
                    String guarantormobile, String guarantortelno, String subguarantorid, String
            subguarantorname, String subguarantorstreetaddress,
                    String subguarantorcity, String subguarantorprovince, String
            subguarantorcountry, String szip, String subguarantormobile, String subguarantortelno,
                    String subguarantoremail){

                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("borrowerid", borrowerid);
                value.put("currencyid", currencyid);
                value.put("creditlimit", creditlimit);
                value.put("availablecredits", availablecredits);
                value.put("usedcredits", usedcredits);
                value.put("outstandingbalance", outstandingbalance);
                value.put("currentbillingbalance", currentbillingbalance);
                value.put("overduebillingbalance", overduebillingbalance);
                value.put("advances", advances);
                value.put("lastadvancepaymenttxnno", lastadvancepaymenttxnno);
                value.put("lastadvancepaymentdatetime", lastadvancepaymentdatetime);
                value.put("ledgerbeginningbalance", ledgerbeginningbalance);
                value.put("ledgerrunningbalance", ledgerrunningbalance);
                value.put("currentvoucherinventory", currentvoucherinventory);
                value.put("status", status);
                value.put("extra1", extra1);
                value.put("extra2", extra2);
                value.put("extra3", extra3);
                value.put("extra4", extra4);
                value.put("notes1", notes1);
                value.put("notes2", notes2);
                value.put("guarantorid", guarantorid);
                value.put("guarantorname", guarantorname);
                value.put("guarantorpreix", guarantorprefix);
                value.put("gstreetaddress", guarantorstreetaddress);
                value.put("gcity", guarantorcity);
                value.put("gprovince", guarantorprovince);
                value.put("gcountry", guarantorcountry);
                value.put("gzip", gzip);
                value.put("gemail", guarantoremail);
                value.put("gmobile", guarantormobile);
                value.put("gtelno", guarantortelno);
                value.put("subguarantorid", subguarantorid);
                value.put("subguarantorname", subguarantorname);
                value.put("subguarantorstreetaddress", subguarantorstreetaddress);
                value.put("subguarantorcity", subguarantorcity);
                value.put("subguarantorprovince", subguarantorprovince);
                value.put("subguarantorcountry", subguarantorcountry);
                value.put("szip", szip);
                value.put("subguarantormobile", subguarantormobile);
                value.put("subguarantortelno", subguarantortelno);
                value.put("subguarantoremail", subguarantoremail);
                // inserting row
                sql.insert(MY_SPONSORS, null, value);
            }

            public ArrayList<Sponsor> getSponsors (String queryStatus){
                ArrayList<Sponsor> sponsorList = new ArrayList<>();
                try {
                    sponsorList.clear();
                    SQLiteDatabase sql = getReadableDatabase();
                    Cursor cursor = sql.rawQuery("SELECT * FROM " + MY_SPONSORS + " WHERE status = ?", new String[]{queryStatus});
                    if (cursor.getCount() > 0) {
                        while (cursor.moveToNext()) {
                            String id = cursor.getString(cursor.getColumnIndex("guarantorid"));
                            String name = cursor.getString(cursor.getColumnIndex("subguarantorname"));

                            String street = "";
                            String city = "";
                            String country = "";
                            String province = "";
                            String zip = "";
                            String email = "";
                            String telno = "";
                            String mobileno = "";
                            String notes1 = "";
                            String sponsorlogo = "";
                            String websitelink = "";
                            String description = "";
                            String longitude = "";
                            String latitude = "";

                            if (name == null || name.equals("") || name.equals("null")) {
                                name = cursor.getString(cursor.getColumnIndex("guarantorname"));
                                street = cursor.getString(cursor.getColumnIndex("gstreetaddress"));
                                city = cursor.getString(cursor.getColumnIndex("gcity"));
                                province = cursor.getString(cursor.getColumnIndex("gprovince"));
                                zip = cursor.getString(cursor.getColumnIndex("gzip"));
                                longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                                latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                                notes1 = cursor.getString(cursor.getColumnIndex("notes1"));
                                sponsorlogo = CommonFunctions.parseXML(notes1, "logo");
                                websitelink = CommonFunctions.parseXML(notes1, "website");
                                description = CommonFunctions.parseXML(notes1, "about");

                            } else {
                                name = cursor.getString(cursor.getColumnIndex("subguarantorname"));
                                street = cursor.getString(cursor.getColumnIndex("subguarantorstreetaddress"));
                                city = cursor.getString(cursor.getColumnIndex("subguarantorcity"));
                                province = cursor.getString(cursor.getColumnIndex("subguarantorprovince"));
                                zip = cursor.getString(cursor.getColumnIndex("szip"));
                            }
                            sponsorList.add(new Sponsor(id, name, street, city, province, country, zip, email, telno, mobileno, sponsorlogo, websitelink, description, longitude, latitude));
                        }
                    }
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return sponsorList;
            }

            public void truncateTable (DatabaseHandler db, String tableName){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + tableName + "");
            }

            public void truncateSponsorTable (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + MY_SPONSORS + "");
            }

            public void insertAccountInfoInLocal (DatabaseHandler db, String Mobile, String
            CountryCode, String Country,
                    String auth, String imei, String stat){

                SQLiteDatabase sql = db.getWritableDatabase();

                String d = "DELETE FROM " + ACCOUNTS + "";
                sql.execSQL(d);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();

                ContentValues value = new ContentValues();
                String mobilenew = CountryCode + Mobile;
                value.put("mobile", mobilenew);
                value.put("status", stat);
                value.put("country", Country);
                value.put("authentication", auth);
                value.put("dateregistration", dateFormat.format(date));
                value.put("imei", imei);

                // inserting row
                sql.insert(ACCOUNTS, null, value);


            }

            public void insertAccountInfoTeeCee (DatabaseHandler db, String teeCee){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();

                // sql.execSQL("DELETE FROM " + HAS_AGREED + "");

                cv.put("accepted", teeCee);
                Log.i("DB INS", "INSERT" + teeCee);
                sql.insert(HAS_AGREED, null, cv);

            }

            public Cursor getTeeCee (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + HAS_AGREED + " LIMIT 1", null);
                return c;
            }

            public void updateGuarantorStatus (DatabaseHandler db, String status, String userid){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("guarantorregistrationstatus", status);
                sql.update(ACCOUNTS, cv, "mobile='" + userid + "'", null);
            }

            public Cursor getAccountInformation (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + ACCOUNTS + " LIMIT 1", null);
                return c;
            }

            public String getStringAccountInformationInColumn (DatabaseHandler db, String columnName)
            {
                Cursor cursor = getAccountInformation(db);
                cursor.moveToFirst();
                String infoInColumn = ".";
                infoInColumn = cursor.getString(cursor.getColumnIndex(columnName));
                cursor.close();
                return infoInColumn;
            }

            public String getAccountProfilePic (DatabaseHandler db){
                String url = "";
                try {
                    Cursor cursor = db.getAccountInformation(db);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        url = cursor.getString(cursor.getColumnIndex("profilepic"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return url;
            }

            public String getAccountSubscriberName (DatabaseHandler db){
                String subsName = "";
                try {
                    Cursor cursor = db.getAccountInformation(db);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        subsName = cursor.getString(cursor.getColumnIndex("firstname")) + " " + cursor.getString(cursor.getColumnIndex("lastname"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return subsName;
            }

            public String hasGuarantorStatus () {
                String status = "";
                SQLiteDatabase sql = getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT guarantorregistrationstatus FROM " + ACCOUNTS + " LIMIT 1", null);

                Logger.debug("ANNCC", "GUARANTORSTATUS" + cursor.getCount());
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        status = cursor.getString(cursor.getColumnIndex("guarantorregistrationstatus"));
                    }
                }
                cursor.close();
                return status;
            }

            public void updateAccount (DatabaseHandler db, String borrowerid, String status, String
            mobileno){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("borrowerid", borrowerid);
                cv.put("status", status);
                sql.update(ACCOUNTS, cv, "mobile='" + mobileno + "'", null);

            }


            public void v2UpdateProfile (DatabaseHandler db,
                    String lastname,
                    String firstname,
                    String middlename,
                    String gender,
                    String email,
                    String streetAddress,
                    String city, String userid){

                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("firstname", firstname);
                cv.put("middlename", middlename);
                cv.put("lastname", lastname);
                cv.put("streetaddress", streetAddress);
                cv.put("city", city);
                cv.put("email", email);
                cv.put("gender", gender);

                Logger.debug("QURY", "v2UpdateProfile: " + String.valueOf(cv));

                sql.update(ACCOUNTS, cv, "mobile='" + userid + "'", null);

            }

            public void updateProfilePic (DatabaseHandler db, String profilepath, String mobileno){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("profilepic", profilepath);
                sql.update(ACCOUNTS, cv, "mobile='" + mobileno + "'", null);

            }

            public void setGuarantorID (DatabaseHandler db, String guarantorid, String mobileno){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("guarantorid", guarantorid);
                sql.update(ACCOUNTS, cv, "mobile='" + mobileno + "'", null);

            }

            public void updateProfile (DatabaseHandler db, String mobileno, String
            firstnameval, String middlenameval, String lastnameval, String streetaddressval, String
            cityval, String email, String status, String profileurl, String gender, String
            guarantorid){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("firstname", firstnameval);
                cv.put("middlename", middlenameval);
                cv.put("lastname", lastnameval);
                cv.put("streetaddress", streetaddressval);
                cv.put("city", cityval);
                cv.put("email", email);
                cv.put("status", status);
                cv.put("profilepic", profileurl);
                cv.put("gender", gender);
                cv.put("guarantorid", guarantorid);
                sql.update(ACCOUNTS, cv, "mobile='" + mobileno + "'", null);
            }

            public void updateAccountStatus (DatabaseHandler db, String status, String mobileno){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("status", status);
                sql.update(ACCOUNTS, cv, "mobile='" + mobileno + "'", null);

            }

            public void insertAccountDetails (DatabaseHandler db, String borrowerid, String
            mobileno, String firstname,
                    String middlename, String lastname, String email, String streetaddress,
                    String city, String dateregistered, String country, String guarantorid,
                    String gender, String profilepic, String guarantorregistrationstatus, String
            imei){

                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + ACCOUNTS + "";
                sql.execSQL(d);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                ContentValues value = new ContentValues();
                value.put("borrowerid", borrowerid);
                value.put("mobile", mobileno);
                value.put("status", "LOGIN");
                value.put("firstname", firstname);
                value.put("middlename", middlename);
                value.put("lastname", lastname);
                value.put("email", email);
                value.put("streetaddress", streetaddress);
                value.put("city", city);
                value.put("country", country);
                value.put("guarantorid", guarantorid);
                value.put("dateregistration", dateregistered);
                value.put("gender", gender);
                value.put("profilepic", profilepic);
                value.put("guarantorregistrationstatus", guarantorregistrationstatus);
                value.put("imei", imei);


                // inserting row
                sql.insert(ACCOUNTS, null, value);

            }

            public void updateRegistrationStatus (DatabaseHandler db, String
            guarantorregistrationstatus, String borrowerid){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("guarantorregistrationstatus", guarantorregistrationstatus);
                sql.update(ACCOUNTS, cv, "borrowerid='" + borrowerid + "'", null);

                Logger.debug("ANNCC", "UPDATEHERE: " + guarantorregistrationstatus + borrowerid);

            }

            public void deleteGuarantorCode (DatabaseHandler db, String borrowerid){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("guarantorid", "");
                sql.update(ACCOUNTS, cv, "borrowerid='" + borrowerid + "'", null);

            }

            public void insertAccountWallet (DatabaseHandler db, String creditlimit, String
            availablecredits, String usedcredits, String outstandingbalance, String
            currentbillingbalance, String overduebillingbalance, String advances, String currencyid)
            {
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + ACCOUNTWALLET + "";
                sql.execSQL(d);

                ContentValues value = new ContentValues();
                value.put("creditlimit", creditlimit);
                value.put("availablecredits", availablecredits);
                value.put("usedcredits", usedcredits);
                value.put("outstandingbalance", outstandingbalance);
                value.put("currentbillingbalance", currentbillingbalance);
                value.put("overduebillingbalance", overduebillingbalance);
                value.put("advances", advances);
                value.put("currencyid", currencyid);

                // inserting row
                sql.insert(ACCOUNTWALLET, null, value);

            }

            public Cursor getAccountWallet (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + ACCOUNTWALLET + "", null);
                return c;
            }

            //end region

            //region VOUCHERS
            public void insertVouchers (DatabaseHandler db, Voucher voucher){
                SQLiteDatabase sql = db.getWritableDatabase();
                String miliseconds = "";
                long time = System.currentTimeMillis();
                miliseconds = Long.toString(time);

                ContentValues value = new ContentValues();
                value.put("productid", voucher.getProductID());
                value.put("serialno", voucher.getVoucherSerialNo());
                value.put("amount", String.valueOf(voucher.getVoucherDenom()));

                if (voucher.getRemainingBalance() % 1 == 0)
                    value.put("remainingamount", String.valueOf((int) voucher.getRemainingBalance()));
                else
                    value.put("remainingamount", String.valueOf((double) voucher.getRemainingBalance()));

                value.put("vouchercode", voucher.getVoucherCode());
                value.put("voucherpin", voucher.getVoucherPIN());
                value.put("lastupdated", miliseconds);
                value.put("barcode", voucher.getBarCode());
                value.put("GroupBarCode", voucher.getGroupBarCode());
                value.put("GroupName", voucher.getGroupName());
                value.put("GroupVoucherCode", voucher.getGroupVoucherCode());
                value.put("GroupVoucherPIN", voucher.getGroupVoucherPIN());
                value.put("GroupBalance", voucher.getGroupBalance());
                value.put("GroupVoucherSession", voucher.getGroupVoucherSession());
                value.put("isPrepaid", voucher.getExtra3());
                value.put("isTransferable", voucher.getIsTransferable());
                value.put("PayToTheOrderOf", voucher.getPayToTheOrderOf());
                value.put("Particulars", voucher.getParticulars());
                value.put("ReleaseTxnNo", voucher.getReleaseTxnNo());
                value.put("IssuedBy", voucher.getIssuedBy());
                value.put("ApprovedBy", voucher.getApprovedBy());
                value.put("DateTimeIssued", voucher.getDateTimeIssued());
                value.put("DateTimeApproved", voucher.getDateTimeApproved());
                value.put("RFIDNumber", voucher.getRFIDNumber());
                value.put("RFIDCardNumber", voucher.getRFIDCardNumber());
                value.put("LastAddedDateTime", voucher.getLastAddedDateTime());
                value.put("LastDeductedDateTime", voucher.getLastDeductedDateTime());

                if (voucher.getProductID().contains("RFID")) {
                    if (voucher.getRFIDNumber() != null || voucher.getRFIDCardNumber() != null) {
                        sql.insert(VOUCHERS, null, value);
                    }
                } else {
                    sql.insert(VOUCHERS, null, value);
                }
            }

            public void deleteVoucher (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + VOUCHERS + "";
                sql.execSQL(d);

            }

            public Cursor getVouchers (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERS + "", null);
                return c;
            }

            public ArrayList<Voucher> getVouchersFromGroupedVouchers (DatabaseHandler db, String
            groupVoucherCode){
                ArrayList<Voucher> arrayList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor cursor = sql.rawQuery("SELECT * From " + VOUCHERS + " WHERE GroupVoucherCode = ? ORDER BY id DESC ", new String[]{groupVoucherCode});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String productid = cursor.getString(cursor.getColumnIndex("productid"));
                        String serialno = cursor.getString(cursor.getColumnIndex("serialno"));
                        String amount = cursor.getString(cursor.getColumnIndex("amount"));
                        String remainingamount = cursor.getString(cursor.getColumnIndex("remainingamount"));
                        String vouchercode = cursor.getString(cursor.getColumnIndex("vouchercode"));
                        String voucherpin = cursor.getString(cursor.getColumnIndex("voucherpin"));
                        String lastupdated = cursor.getString(cursor.getColumnIndex("lastupdated"));
                        String barcode = cursor.getString(cursor.getColumnIndex("barcode"));
                        String GroupBarCode = cursor.getString(cursor.getColumnIndex("GroupBarCode"));
                        String GroupName = cursor.getString(cursor.getColumnIndex("GroupName"));
                        String GroupVoucherCode = cursor.getString(cursor.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = cursor.getString(cursor.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = cursor.getString(cursor.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = cursor.getString(cursor.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = cursor.getString(cursor.getColumnIndex("isPrepaid"));
                        String isTransferable = cursor.getString(cursor.getColumnIndex("isTransferable"));

                        if (!GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, isPrepaid, isTransferable));
                    }
                }
                return arrayList;
            }

            public ArrayList<Voucher> getAllVouchers (DatabaseHandler db){

                ArrayList<Voucher> arrayList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor cursor = sql.rawQuery("SELECT * From " + VOUCHERS + " GROUP BY GroupVoucherCode ORDER BY id DESC ", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String productid = cursor.getString(cursor.getColumnIndex("productid"));
                        String serialno = cursor.getString(cursor.getColumnIndex("serialno"));
                        String amount = cursor.getString(cursor.getColumnIndex("amount"));
                        String remainingamount = cursor.getString(cursor.getColumnIndex("remainingamount"));
                        String vouchercode = cursor.getString(cursor.getColumnIndex("vouchercode"));
                        String voucherpin = cursor.getString(cursor.getColumnIndex("voucherpin"));
                        String lastupdated = cursor.getString(cursor.getColumnIndex("lastupdated"));
                        String barcode = cursor.getString(cursor.getColumnIndex("barcode"));
                        String GroupBarCode = cursor.getString(cursor.getColumnIndex("GroupBarCode"));
                        String GroupName = cursor.getString(cursor.getColumnIndex("GroupName"));
                        String GroupVoucherCode = cursor.getString(cursor.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = cursor.getString(cursor.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = cursor.getString(cursor.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = cursor.getString(cursor.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = cursor.getString(cursor.getColumnIndex("isPrepaid"));
                        String isTransferable = cursor.getString(cursor.getColumnIndex("isTransferable"));

                        if (!GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, ".", isTransferable));
                    }
                }

                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERS + "" + " WHERE ProductID NOT LIKE '%CHEQUE%'" + " AND " + " ProductID NOT LIKE '%RFID%'", null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String productid = c.getString(c.getColumnIndex("productid"));
                        String serialno = c.getString(c.getColumnIndex("serialno"));
                        String amount = c.getString(c.getColumnIndex("amount"));
                        String remainingamount = c.getString(c.getColumnIndex("remainingamount"));
                        String vouchercode = c.getString(c.getColumnIndex("vouchercode"));
                        String voucherpin = c.getString(c.getColumnIndex("voucherpin"));
                        String lastupdated = c.getString(c.getColumnIndex("lastupdated"));
                        String barcode = c.getString(c.getColumnIndex("barcode"));
                        String GroupBarCode = c.getString(c.getColumnIndex("GroupBarCode"));
                        String GroupName = c.getString(c.getColumnIndex("GroupName"));
                        String GroupVoucherCode = c.getString(c.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = c.getString(c.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = c.getString(c.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = c.getString(c.getColumnIndex("isPrepaid"));
                        String isTransferable = c.getString(c.getColumnIndex("isTransferable"));

                        if (GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, isPrepaid, isTransferable));
                    }
                }
                c.close();
                return arrayList;
            }

            public ArrayList<Voucher> getSortedVouchers (DatabaseHandler db, String selected, String
            option){

                ArrayList<Voucher> arrayList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * From " + VOUCHERS + " WHERE ProductID NOT LIKE '%CHEQUE%'" + " AND " + " ProductID NOT LIKE '%RFID%'"
                        + " GROUP BY GroupVoucherCode ORDER BY id " + option, null);

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String productid = cursor.getString(cursor.getColumnIndex("productid"));
                        String serialno = cursor.getString(cursor.getColumnIndex("serialno"));
                        String amount = cursor.getString(cursor.getColumnIndex("amount"));
                        String remainingamount = cursor.getString(cursor.getColumnIndex("remainingamount"));
                        String vouchercode = cursor.getString(cursor.getColumnIndex("vouchercode"));
                        String voucherpin = cursor.getString(cursor.getColumnIndex("voucherpin"));
                        String lastupdated = cursor.getString(cursor.getColumnIndex("lastupdated"));
                        String barcode = cursor.getString(cursor.getColumnIndex("barcode"));
                        String GroupBarCode = cursor.getString(cursor.getColumnIndex("GroupBarCode"));
                        String GroupName = cursor.getString(cursor.getColumnIndex("GroupName"));
                        String GroupVoucherCode = cursor.getString(cursor.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = cursor.getString(cursor.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = cursor.getString(cursor.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = cursor.getString(cursor.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = cursor.getString(cursor.getColumnIndex("isPrepaid"));
                        String isTransferable = cursor.getString(cursor.getColumnIndex("isTransferable"));

                        if (!GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, isPrepaid, isTransferable));
                    }
                }

                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERS + " WHERE ProductID NOT LIKE '%CHEQUE%'" + " AND " + " ProductID NOT LIKE '%RFID%'"
                        + " ORDER BY CAST(" + selected + " AS INTEGER) " + option + "  ", null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String productid = c.getString(c.getColumnIndex("productid"));
                        String serialno = c.getString(c.getColumnIndex("serialno"));
                        String amount = c.getString(c.getColumnIndex("amount"));
                        String remainingamount = c.getString(c.getColumnIndex("remainingamount"));
                        String vouchercode = c.getString(c.getColumnIndex("vouchercode"));
                        String voucherpin = c.getString(c.getColumnIndex("voucherpin"));
                        String lastupdated = c.getString(c.getColumnIndex("lastupdated"));
                        String barcode = c.getString(c.getColumnIndex("barcode"));
                        String GroupBarCode = c.getString(c.getColumnIndex("GroupBarCode"));
                        String GroupName = c.getString(c.getColumnIndex("GroupName"));
                        String GroupVoucherCode = c.getString(c.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = c.getString(c.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = c.getString(c.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = c.getString(c.getColumnIndex("isPrepaid"));
                        String isTransferable = c.getString(c.getColumnIndex("isTransferable"));

                        if (GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, isPrepaid, isTransferable));
                    }
                }
                c.close();
                return arrayList;
            }

            public ArrayList<Voucher> getIndividualVouchers (DatabaseHandler db){

                ArrayList<Voucher> arrayList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERS + "" + " WHERE ProductID NOT LIKE '%CHEQUE%'" + " AND " + " ProductID NOT LIKE '%RFID%'", null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String productid = c.getString(c.getColumnIndex("productid"));
                        String serialno = c.getString(c.getColumnIndex("serialno"));
                        String amount = c.getString(c.getColumnIndex("amount"));
                        String remainingamount = c.getString(c.getColumnIndex("remainingamount"));
                        String vouchercode = c.getString(c.getColumnIndex("vouchercode"));
                        String voucherpin = c.getString(c.getColumnIndex("voucherpin"));
                        String lastupdated = c.getString(c.getColumnIndex("lastupdated"));
                        String barcode = c.getString(c.getColumnIndex("barcode"));
                        String GroupBarCode = c.getString(c.getColumnIndex("GroupBarCode"));
                        String GroupName = c.getString(c.getColumnIndex("GroupName"));
                        String GroupVoucherCode = c.getString(c.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = c.getString(c.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = c.getString(c.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = c.getString(c.getColumnIndex("isPrepaid"));
                        String isTransferable = c.getString(c.getColumnIndex("isTransferable"));

                        if (GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, isPrepaid, isTransferable));
                    }
                }
                c.close();
                return arrayList;
            }

            public ArrayList<Voucher> getGroupedVouchers (DatabaseHandler db){

                ArrayList<Voucher> arrayList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor cursor = sql.rawQuery("SELECT * From " + VOUCHERS + " GROUP BY GroupVoucherCode ORDER BY id DESC ", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String productid = cursor.getString(cursor.getColumnIndex("productid"));
                        String serialno = cursor.getString(cursor.getColumnIndex("serialno"));
                        String amount = cursor.getString(cursor.getColumnIndex("amount"));
                        String remainingamount = cursor.getString(cursor.getColumnIndex("remainingamount"));
                        String vouchercode = cursor.getString(cursor.getColumnIndex("vouchercode"));
                        String voucherpin = cursor.getString(cursor.getColumnIndex("voucherpin"));
                        String lastupdated = cursor.getString(cursor.getColumnIndex("lastupdated"));
                        String barcode = cursor.getString(cursor.getColumnIndex("barcode"));
                        String GroupBarCode = cursor.getString(cursor.getColumnIndex("GroupBarCode"));
                        String GroupName = cursor.getString(cursor.getColumnIndex("GroupName"));
                        String GroupVoucherCode = cursor.getString(cursor.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = cursor.getString(cursor.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = cursor.getString(cursor.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = cursor.getString(cursor.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = cursor.getString(cursor.getColumnIndex("isPrepaid"));
                        String isTransferable = cursor.getString(cursor.getColumnIndex("isTransferable"));

                        if (!GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, isPrepaid, isTransferable));
                    }
                }
                cursor.close();
                return arrayList;
            }

            public ArrayList<Voucher> getVouchersInAGroup (DatabaseHandler db, String
            groupVoucherCode){

                ArrayList<Voucher> arrayList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor cursor = sql.rawQuery("SELECT * From " + VOUCHERS + " WHERE GroupVoucherCode = ?  ORDER BY id DESC ", new String[]{groupVoucherCode});

                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String productid = cursor.getString(cursor.getColumnIndex("productid"));
                        String serialno = cursor.getString(cursor.getColumnIndex("serialno"));
                        String amount = cursor.getString(cursor.getColumnIndex("amount"));
                        String remainingamount = cursor.getString(cursor.getColumnIndex("remainingamount"));
                        String vouchercode = cursor.getString(cursor.getColumnIndex("vouchercode"));
                        String voucherpin = cursor.getString(cursor.getColumnIndex("voucherpin"));
                        String lastupdated = cursor.getString(cursor.getColumnIndex("lastupdated"));
                        String barcode = cursor.getString(cursor.getColumnIndex("barcode"));
                        String GroupBarCode = cursor.getString(cursor.getColumnIndex("GroupBarCode"));
                        String GroupName = cursor.getString(cursor.getColumnIndex("GroupName"));
                        String GroupVoucherCode = cursor.getString(cursor.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = cursor.getString(cursor.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = cursor.getString(cursor.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = cursor.getString(cursor.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = cursor.getString(cursor.getColumnIndex("isPrepaid"));
                        String isTransferable = cursor.getString(cursor.getColumnIndex("isTransferable"));

                        if (!GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode, voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance, GroupVoucherSession, isPrepaid, isTransferable));
                    }
                }
                cursor.close();
                return arrayList;
            }

//    public Cursor getSortedVouchers(DatabaseHandler db, String sortfield, String sortoption) {
//        SQLiteDatabase sql = db.getReadableDatabase();
//
//        Cursor c = sql.rawQuery("SELECT * From " + VOUCHERS + " ORDER BY CAST(" + sortfield + " AS INTEGER) " + sortoption + "  ", null);
//
//        return c;
//    }

            public Cursor getSpecificVoucher (DatabaseHandler db, String serialno){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERS + " WHERE serialno = '" + serialno.trim() + "' LIMIT 1", null);
                return c;
            }

            public void insertVoucherRestrictions (DatabaseHandler db, VoucherRestriction
            restriction){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("productid", restriction.getProductID());
                value.put("restrictiontype", restriction.getRestrictionType());
                value.put("restrictedto", restriction.getRestrictedTo());

                // inserting row
                sql.insert(VOUCHERRESTRICTIONS, null, value);

            }

            public void deleteVoucherRestriction (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + VOUCHERRESTRICTIONS + "";
                sql.execSQL(d);

            }

            public Cursor getVoucherRestriction (DatabaseHandler db, String productid){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERRESTRICTIONS + " WHERE productid = '" + productid + "'", null);
                return c;
            }

            public void updateVoucherPIN (DatabaseHandler db, String voucherpin, String serialno){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("voucherpin", voucherpin);
                sql.update(VOUCHERS, cv, "serialno='" + serialno.trim() + "'", null);

            }

            public void updateGroupVoucherPIN (DatabaseHandler db, String voucherpin, String
            groupVoucherCode){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("voucherpin", voucherpin);
                sql.update(VOUCHERS, cv, "serialno='" + groupVoucherCode.trim() + "'", null);

            }

            public void updateParticularVoucher (DatabaseHandler db, String voucherpin, String
            vouchercode, String balance, String barcode){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("voucherpin", voucherpin);
                cv.put("remainingamount", balance);
                cv.put("barcode", barcode);
                sql.update(VOUCHERS, cv, "vouchercode='" + vouchercode.trim() + "'", null);

            }

            public void insertVouchersSummary (DatabaseHandler db, VoucherSummary summary){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("productid", summary.getProductID());
                value.put("totalvoucher", String.valueOf(summary.getTotalNumber()));
                value.put("totaldenom", String.valueOf(summary.getTotalDenom()));
                value.put("totalbalance", String.valueOf(summary.getTotalBalance()));
                value.put("rfidnumber", summary.getRFIDNumber());
                value.put("rfidcardnumber", summary.getRFIDCardNumber());
                value.put("rfidpin", summary.getRFIDPIN());

                // inserting row
                if (summary.getProductID().contains("RFID")) {
                    if (summary.getRFIDNumber() != null || summary.getRFIDCardNumber() != null) {
                        sql.insert(VOUCHERSUMMARY, null, value);
                    }
                } else {
                    sql.insert(VOUCHERSUMMARY, null, value);
                }
            }

            public void deleteVoucherSummary (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + VOUCHERSUMMARY + "";
                sql.execSQL(d);

            }

            public Cursor getVoucherSummary (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERSUMMARY + "", null);
                return c;
            }
            //end region

            //region LIST INSERTION

            //insert merchants

//    public void insertMerchants(DatabaseHandler db, String merchantid, String merchantname, String merchantlogo, String merchanttype, String merchantgroup, String merchantstatus, String streetaddress, String city, String province, String zip, String country, String longitude, String latitude, String representative, String email, String telno, String mobileno, String fax, String natureofbusiness, String tin, String organizationtype, String noofbranches, String dateadded, String isfeatured, String featuredaddspath) {
//        SQLiteDatabase sql = db.getWritableDatabase();
//        ContentValues value = new ContentValues();
//        value.put("merchantid", merchantid);
//        value.put("merchantname", merchantname);
//        value.put("merchantlogo", merchantlogo);
//        value.put("merchanttype", merchanttype);
//        value.put("merchantgroup", merchantgroup);
//        value.put("merchantstatus", merchantstatus);
//        value.put("streetaddress", streetaddress);
//        value.put("city", city);
//        value.put("province", province);
//        value.put("zip", zip);
//        value.put("country", country);
//        value.put("longitude", longitude);
//        value.put("latitude", latitude);
//        value.put("representative", representative);
//        value.put("email", email);
//        value.put("telno", telno);
//        value.put("mobileno", mobileno);
//        value.put("fax", fax);
//        value.put("natureofbusiness", natureofbusiness);
//        value.put("tin", tin);
//        value.put("organizationtype", organizationtype);
//        value.put("noofbranches", noofbranches);
//        value.put("isfeatured", isfeatured);
//        value.put("featuredaddspath", featuredaddspath);
//
//
//         inserting row
//        sql.insert(MERCHANTS, null, value);


//    }

            //PAYOUT ONE AND RFID
            public ArrayList<Voucher> getChequeVoucher (DatabaseHandler db){

                ArrayList<Voucher> arrayList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor c = sql.rawQuery("SELECT * From " + VOUCHERS + " WHERE ProductID LIKE '%CHEQUE%'"
                        + " OR " + " ProductID LIKE '%RFID%'" + "ORDER BY id ASC", null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String productid = c.getString(c.getColumnIndex("productid"));
                        String serialno = c.getString(c.getColumnIndex("serialno"));
                        String amount = c.getString(c.getColumnIndex("amount"));
                        String remainingamount = c.getString(c.getColumnIndex("remainingamount"));
                        String vouchercode = c.getString(c.getColumnIndex("vouchercode"));
                        String voucherpin = c.getString(c.getColumnIndex("voucherpin"));
                        String lastupdated = c.getString(c.getColumnIndex("lastupdated"));
                        String barcode = c.getString(c.getColumnIndex("barcode"));
                        String GroupBarCode = c.getString(c.getColumnIndex("GroupBarCode"));
                        String GroupName = c.getString(c.getColumnIndex("GroupName"));
                        String GroupVoucherCode = c.getString(c.getColumnIndex("GroupVoucherCode"));
                        String GroupVoucherPIN = c.getString(c.getColumnIndex("GroupVoucherPIN"));
                        String GroupBalance = c.getString(c.getColumnIndex("GroupBalance"));
                        String GroupVoucherSession = c.getString(c.getColumnIndex("GroupVoucherSession"));
                        String isPrepaid = c.getString(c.getColumnIndex("isPrepaid"));
                        String isTransferable = c.getString(c.getColumnIndex("isTransferable"));
                        String PayToTheOrderOf = c.getString(c.getColumnIndex("PayToTheOrderOf"));
                        String Particulars = c.getString(c.getColumnIndex("Particulars"));
                        String ReleaseTxnNo = c.getString(c.getColumnIndex("ReleaseTxnNo"));
                        String IssuedBy = c.getString(c.getColumnIndex("IssuedBy"));
                        String ApprovedBy = c.getString(c.getColumnIndex("ApprovedBy"));
                        String DateTimeIssued = c.getString(c.getColumnIndex("DateTimeIssued"));
                        String DateTimeApproved = c.getString(c.getColumnIndex("DateTimeApproved"));
                        String rfidNumber = c.getString(c.getColumnIndex("RFIDNumber"));
                        String rfidCardNumber = c.getString(c.getColumnIndex("RFIDCardNumber"));
                        String rfidPin = c.getString(c.getColumnIndex("RFIDPIN"));
                        String lastAddedDateTime = c.getString(c.getColumnIndex("LastAddedDateTime"));
                        String lastDeductedDateTime = c.getString(c.getColumnIndex("LastDeductedDateTime"));

                        if (GroupVoucherCode.equals("."))
                            arrayList.add(new Voucher(productid, serialno, Double.parseDouble(amount), Double.parseDouble(remainingamount), vouchercode,
                                    voucherpin, lastupdated, barcode, GroupBarCode, GroupName, GroupVoucherCode, GroupVoucherPIN, GroupBalance,
                                    GroupVoucherSession, isPrepaid, isTransferable, PayToTheOrderOf, Particulars, ReleaseTxnNo, IssuedBy, ApprovedBy,
                                    DateTimeIssued, DateTimeApproved, rfidNumber, rfidCardNumber, rfidPin, lastAddedDateTime, lastDeductedDateTime));
                    }
                }
                c.close();
                return arrayList;
            }

            public void insertMerchants (DatabaseHandler db, String merchantid, String
            merchantname, String merchantlogo, String merchanttype, String merchantgroup, String
            merchantstatus, String streetaddress, String city, String province, String zip, String
            country, String longitude, String latitude, String representative, String email, String
            telno, String mobileno, String fax, String natureofbusiness, String tin, String
            organizationtype, String noofbranches, String dateadded, String isfeatured, String
            featuredaddspath, String OrganizationLogo){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues value = new ContentValues();
                value.put("merchantid", merchantid);
                value.put("merchantname", merchantname);
                value.put("merchantlogo", merchantlogo);
                value.put("merchanttype", merchanttype);
                value.put("merchantgroup", merchantgroup);
                value.put("merchantstatus", merchantstatus);
                value.put("streetaddress", streetaddress);
                value.put("city", city);
                value.put("province", province);
                value.put("zip", zip);
                value.put("country", country);
                value.put("longitude", longitude);
                value.put("latitude", latitude);
                value.put("representative", representative);
                value.put("email", email);
                value.put("telno", telno);
                value.put("mobileno", mobileno);
                value.put("fax", fax);
                value.put("natureofbusiness", natureofbusiness);
                value.put("tin", tin);
                value.put("organizationtype", organizationtype);
                value.put("noofbranches", noofbranches);
                value.put("isfeatured", isfeatured);
                value.put("featuredaddspath", featuredaddspath);
                value.put("OrganizationLogo", OrganizationLogo);


                // inserting row
                sql.insert(MERCHANTS, null, value);

            }

            //delete Merchants
            public void deleteMerchants (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + MERCHANTS + "";
                sql.execSQL(d);

            }

            //insert Guarantor List
            public void insertGuarantors (DatabaseHandler db, String guarantorid, String
            guarantorname, String guarantortype, String guarantorgroup, String
            guarantorstatus, String billingaddress, String streetaddress, String city, String
            province, String country, String representative, String email, String telno, String
            mobileno, String fax, String natureofbusiness, String tin, String
            organizationtype, String registrationdate, String notes1, String longitude, String
            latitude){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("guarantorid", guarantorid);
                value.put("guarantorname", guarantorname);
                value.put("guarantortype", guarantortype);
                value.put("guarantorgroup", guarantorgroup);
                value.put("guarantorstatus", guarantorstatus);
                value.put("billingaddress", billingaddress);
                value.put("streetaddress", streetaddress);
                value.put("city", city);
                value.put("province", province);
                value.put("country", country);
                value.put("representative", representative);
                value.put("email", email);
                value.put("telno", telno);
                value.put("mobileno", mobileno);
                value.put("telno", telno);
                value.put("mobileno", mobileno);
                value.put("fax", fax);
                value.put("natureofbusiness", natureofbusiness);
                value.put("tin", tin);
                value.put("organizationtype", organizationtype);
                value.put("registrationdate", registrationdate);
                value.put("notes1", notes1);
                value.put("longitude", longitude);
                value.put("latitude", latitude);

                // inserting row
                sql.insert(GUARANTORS, null, value);

            }

            //delete Guarantors
            public void deleteGuarantors (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + GUARANTORS + "";
                sql.execSQL(d);

            }

            //insert borrowings transaction
            public void insertBorrowings (DatabaseHandler db, String transactionno, String
            datetimecompleted, String totalvoucheramount, String totalnumvoucher){
                SQLiteDatabase sql = db.getWritableDatabase();


                ContentValues value = new ContentValues();
                value.put("transactionno", transactionno);
                value.put("datetimecompleted", datetimecompleted);
                value.put("totalvoucheramount", totalvoucheramount);
                value.put("totalnumvouchers", totalnumvoucher);

                // inserting row
                sql.insert(BORROWINGS, null, value);

            }

            //insert borrowings transaction
            public void insertBorrowingsData (DatabaseHandler db, Transaction details){
                SQLiteDatabase sql = db.getWritableDatabase();


                ContentValues value = new ContentValues();
                value.put("transactionno", details.getTransactionNumber());
                value.put("datetimecompleted", details.getDateCompleted());
                value.put("totalvoucheramount", details.getTotalVoucherAmount());
                value.put("totalnumvouchers", details.getNumOfVoucher());

                // inserting row
                sql.insert(BORROWINGS, null, value);

            }

            //delete borrowings
            public void deleteBorrowings (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + BORROWINGS + "";
                sql.execSQL(d);

            }

            //insert COnsummation Transaction
            public void insertConsummationTransaction (DatabaseHandler db, String
            transactionno, String merchantid, String merchantname, String merchantbranch,
                    String productid, String voucherserial, String vouchercode, String
            voucherdenoms, String amountconsumed,
                    String merchantservicecharge, String datecompleted, String
            merchantReferenceCode, String MerchantBranchName,
                    String Extra3, String MerchantLogo, String Status){

                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("transactionno", transactionno);
                value.put("merchantid", merchantid);
                value.put("merchantname", merchantname);
                value.put("merchantbranch", merchantbranch);
                value.put("productid", productid);
                value.put("voucherserial", voucherserial);
                value.put("vouchercode", vouchercode);
                value.put("voucherdenoms", voucherdenoms);
                value.put("amountconsumed", amountconsumed);
                value.put("merchantservicecharge", merchantservicecharge);
                value.put("datecompleted", datecompleted);
                value.put("merchantreferencecode", merchantReferenceCode);
                value.put("MerchantBranchName", MerchantBranchName);
                value.put("MerchantLogo", MerchantLogo);
                value.put("Extra3", Extra3);
                value.put("Status", Status);

                // inserting row
                sql.insert(CONSUMMATIONTRANSACTION, null, value);

            }

            //delete consummation
            public void deleteConsummation (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + CONSUMMATIONTRANSACTION + "";
                sql.execSQL(d);

            }

            //inser transfered
            public void insertTransferredTransaction (DatabaseHandler db, String
            transactionno, String transfertype, String sourceborrowerid, String
            sourceborrowername, String recipientborrowerid, String recipientborrowername, String
            recipientmobileno, String recipientemail, String serialno, String vouchercode, String
            denoms, String datetimein, String Extra3){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("transactionno", transactionno);
                value.put("transfertype", transfertype);
                value.put("sourceborrowerid", sourceborrowerid);
                value.put("sourceborrowername", sourceborrowername);
                value.put("recipientborrowerid", recipientborrowerid);
                value.put("recipientborrowername", recipientborrowername);
                value.put("recipientmobileno", recipientmobileno);
                value.put("recipientemail", recipientemail);
                value.put("serialno", serialno);
                value.put("vouchercode", vouchercode);
                value.put("denoms", denoms);
                value.put("datetimein", datetimein);
                value.put("Extra3", Extra3);

                // inserting row
                sql.insert(TRANSFERREDTRANSACTION, null, value);

            }

            //delete transferred transaction
            public void deleteTransferredTransaction (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + TRANSFERREDTRANSACTION + "";
                sql.execSQL(d);

            }

            //insert unpaid bills
            public void insertUnpaidBills (DatabaseHandler db, String billingid, String
            guarantorid, String guarantorname, String statementdate, String duedate, String
            amount, String advancepayment, String partialpayment, String commission, String
            netamount, String duebalance, String cutoverperiod){
                SQLiteDatabase sql = db.getWritableDatabase();


                ContentValues value = new ContentValues();
                value.put("billingid", billingid);
                value.put("guarantorid", guarantorid);
                value.put("guarantorname", guarantorname);
                value.put("statementdate", statementdate);
                value.put("duedate", duedate);
                value.put("amount", amount);
                value.put("advancepayment", advancepayment);
                value.put("partialpayment", partialpayment);
                value.put("commission", commission);
                value.put("netamount", netamount);
                value.put("duebalance", duebalance);
                value.put("cutoverperiod", cutoverperiod);

                // inserting row
                sql.insert(UNPAIDBILLS, null, value);

            }

            //delete unpaid bills
            public void deleteUnpaidBills (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + UNPAIDBILLS + "";
                sql.execSQL(d);

            }

            //insert paid bills
            public void insertPaidBills (DatabaseHandler db, String billingid, String
            guarantorid, String guarantorname, String statementdate, String duedate, String
            amount, String advancepayment, String partialpayment, String commission, String
            netamount, String duebalance, String cutoverperiod, String datetimepaid, String
            paymenttxnno){
                SQLiteDatabase sql = db.getWritableDatabase();


                ContentValues value = new ContentValues();
                value.put("billingid", billingid);
                value.put("guarantorid", guarantorid);
                value.put("guarantorname", guarantorname);
                value.put("statementdate", statementdate);
                value.put("duedate", duedate);
                value.put("amount", amount);
                value.put("advancepayment", advancepayment);
                value.put("partialpayment", partialpayment);
                value.put("commission", commission);
                value.put("netamount", netamount);
                value.put("duebalance", duebalance);
                value.put("cutoverperiod", cutoverperiod);
                value.put("datetimepaid", datetimepaid);
                value.put("paymenttxnno", paymenttxnno);

                // inserting row
                sql.insert(PAIDBILLS, null, value);

            }

            //delete paid bills
            public void deletePaidBills (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + PAIDBILLS + "";
                sql.execSQL(d);

            }
            //end region

            //get List
            public Cursor getDetails (DatabaseHandler db, String tablename, String id, String field)
            {
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + tablename + " WHERE " + field + " = '" + id + "' LIMIT 1", null);

                return c;
            }

            //get borrowings
            public Cursor getBorrowings (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + BORROWINGS + "", null);
                return c;
            }

            //getting all transaction grouped by merchant txn no, amount consumed is totaled
            public Cursor getMerchantTransactions (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT COALESCE(SUM(amountconsumed), 0) as totalamountconsume , merchantreferencecode, datecompleted, merchantname, transactionno, MerchantBranchName, Extra3, MerchantLogo, merchantid, Status From " + CONSUMMATIONTRANSACTION + " GROUP BY merchantreferencecode, merchantid ORDER BY datecompleted DESC", null);
                return c;
            }

            //getting all transactions details
            public List<TransactionDetails> getTransactionDetails (DatabaseHandler db, String txnno)
            {
                List<TransactionDetails> transactions = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + CONSUMMATIONTRANSACTIONDETAILS + " WHERE " + " TransactionNo " + "=?" + " OR " + " GKTransactionNo " + "=?" + " LIMIT 1";
                Cursor c = sql.rawQuery(strsql, new String[]{txnno, txnno});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String transactionNo = c.getString(c.getColumnIndex("TransactionNo"));
                        String mobileTarget = c.getString(c.getColumnIndex("MobileTarget"));
                        String productCode = c.getString(c.getColumnIndex("ProductCode"));
                        double amount = c.getDouble(c.getColumnIndex("Amount"));
                        String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String billerTransactionNo = c.getString(c.getColumnIndex("BillerTransactionNo"));
                        String GKTransactionNo = c.getString(c.getColumnIndex("GKTransactionNo"));
                        String billerName = c.getString(c.getColumnIndex("BillerName"));
                        String accountNo = c.getString(c.getColumnIndex("AccountNo"));
                        String accountName = c.getString(c.getColumnIndex("AccountName"));
                        double amountPaid = c.getDouble(c.getColumnIndex("AmountPaid"));
                        double totalamountPaid = c.getDouble(c.getColumnIndex("TotalAmountPaid"));
                        double discount = c.getDouble(c.getColumnIndex("Discount"));
                        double netamount = c.getDouble(c.getColumnIndex("NetAmount"));

                        transactions.add(new TransactionDetails(transactionNo, mobileTarget, productCode, amount, dateTimeCompleted, billerTransactionNo, GKTransactionNo, billerName, accountNo, accountName, amountPaid, totalamountPaid, discount, netamount));
                    }
                }
                c.close();
                db.close();
                return transactions;
            }

            //check if transaction details exist
            public String isTxnDetailsExist (DatabaseHandler db, String txnno){
                String type = null;
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT TransactionDetailsType FROM " + CONSUMMATIONTRANSACTIONDETAILS + " WHERE " + " TransactionNo " + "=?" + " OR " + " GKTransactionNo " + "=?" + " LIMIT 1";
                Cursor cursor = sql.rawQuery(strsql, new String[]{txnno, txnno});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        type = cursor.getString(cursor.getColumnIndex("TransactionDetailsType"));
                    }
                }
                cursor.close();
                db.close();
                return type;
            }

            //get consummations
            //getting all transactions given merchant txn no
            public Cursor getVouchersUsedTransaction (DatabaseHandler db, String
            merchanttxnno, String merchantid){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + CONSUMMATIONTRANSACTION + " WHERE merchantreferencecode ='" + merchanttxnno + "' AND merchantid = '" + merchantid + "' ", null);
                return c;
            }

            //get transfered
            public Cursor getTransferredTransaction (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + TRANSFERREDTRANSACTION + "", null);
                return c;
            }

            //get unpaid bills
            public Cursor getUnpaidbills (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + UNPAIDBILLS + "", null);
                return c;
            }

            //get paid bills
            public Cursor getPaidBills (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + PAIDBILLS + " ORDER BY datetimepaid ASC", null);
                return c;
            }

            //get guarantors
            public Cursor getGuarantors (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + GUARANTORS + "", null);
                return c;
            }

            //get Merchants
            public Cursor getMerchants (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + MERCHANTS + "", null);
                return c;
            }

            //get Merchants
            public Cursor getFeaturedMerchants (DatabaseHandler db, String isFeatured){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + MERCHANTS + " WHERE isfeatured = " + isFeatured, null);
                return c;
            }

            //region LOAD PRODUCTS
            public void saveLoadWalletPrefix (DatabaseHandler db, JSONArray myarray){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.beginTransaction();

                try {

                    ContentValues cv = new ContentValues();

                    JSONObject myobj;

                    for (int i = 0; i < myarray.length(); i++) {

                        try {
                            myobj = myarray.getJSONObject(i);

                            cv.put("prefix", myobj.getString("Prefix"));
                            cv.put("brand", myobj.getString("Brand"));

                            sql.insert(LOADWALLET_PREFIX, null, cv);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    sql.setTransactionSuccessful();

                } finally {
                    sql.endTransaction();
                }
            }

            public void DeleteLoadWalletPrefix (DatabaseHandler db){

                SQLiteDatabase sql = db.getWritableDatabase();

                sql.execSQL("DELETE FROM " + LOADWALLET_PREFIX + "");
                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + LOADWALLET_PREFIX + "'");

            }

            public String getLoadwalletPrefix (DatabaseHandler db, String prfix){

                String myresult = "";
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + LOADWALLET_PREFIX + " Where prefix = " + prfix + " LIMIT 1", null);
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    do {
                        myresult = c.getString(c.getColumnIndex("brand"));
                    } while (c.moveToNext());
                }

                c.close();
                return myresult;
            }

            public Cursor getLoadwalletPrefixCount (DatabaseHandler db){

                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + LOADWALLET_PREFIX + " ", null);
                return c;
            }

            public void saveSunProductCode (DatabaseHandler db, JSONArray myarray, String PartnerID)
            {

                SQLiteDatabase sql = db.getWritableDatabase();
                sql.beginTransaction();

                try {

                    ContentValues cv = new ContentValues();

                    JSONObject myobj;

                    for (int i = 0; i < myarray.length(); i++) {

                        try {

                            myobj = myarray.getJSONObject(i);

                            cv.put("networkid", PartnerID);
                            cv.put("elpkeyword", myobj.getString("Keyword"));
                            cv.put("amount", myobj.getString("Amount"));
                            cv.put("txntype", myobj.getString("Type"));
                            cv.put("description", myobj.getString("Description"));
                            cv.put("discountclass", "");
                            cv.put("discount", ".");

                            sql.insert(SUNPRODUCT, null, cv);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    sql.setTransactionSuccessful();

                } finally {
                    sql.endTransaction();
                }

            }

            public void saveGlobeProductCode (DatabaseHandler db, JSONArray myarray, String
            PartnerID){

                SQLiteDatabase sql = db.getWritableDatabase();
                sql.beginTransaction();

                try {

                    ContentValues cv = new ContentValues();

                    JSONObject myobj;

                    for (int i = 0; i < myarray.length(); i++) {


                        try {

                            myobj = myarray.getJSONObject(i);

                            // cv.put("networkid", PartnerID);
                            cv.put("minamount", myobj.getString("MinAmnt"));
                            cv.put("maxamount", myobj.getString("MaxAmnt"));
                            cv.put("discount", ".");
                            cv.put("denomtype", myobj.getString("DenomType"));
                            cv.put("productcode", myobj.getString("ProductCode"));
                            cv.put("networkid", myobj.getString("Network"));


                            sql.insert(GLOBEPRODUCT, null, cv);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Logger.debug("LOADPRODUCTERROR", "error" + e);
                        }

                    }
                    sql.setTransactionSuccessful();

                } finally {
                    sql.endTransaction();
                }


            }

            public void saveSmartProductCode (DatabaseHandler db, JSONArray myarray, String
            PartnerID){

                SQLiteDatabase sql = db.getWritableDatabase();
                sql.beginTransaction();

                try {

                    ContentValues cv = new ContentValues();

                    JSONObject myobj;

                    for (int i = 0; i < myarray.length(); i++) {

                        try {

                            myobj = myarray.getJSONObject(i);

                            // cv.put("networkid", PartnerID);
                            cv.put("networkid", myobj.getString("Network"));
                            cv.put("keyword", myobj.getString("Keyword"));
                            cv.put("amount", myobj.getString("Amount"));
                            cv.put("txntype", myobj.getString("Type"));
                            cv.put("description", myobj.getString("Description"));
                            cv.put("discount", ".");
                            cv.put("buddy", ".");
                            cv.put("tnt", ".");
                            cv.put("bro", ".");

                            sql.insert(SMARTPRODUCT, null, cv);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    sql.setTransactionSuccessful();

                } finally {
                    sql.endTransaction();
                }


            }

            public void saveNetPrefix (DatabaseHandler db, JSONArray myarray){


                SQLiteDatabase sql = db.getWritableDatabase();
                sql.beginTransaction();

                try {

                    ContentValues cv = new ContentValues();

                    JSONObject myobj;

                    for (int i = 0; i < myarray.length(); i++) {

                        try {

                            myobj = myarray.getJSONObject(i);

                            cv.put("prefix", myobj.getString("Prefix"));
                            cv.put("brand", myobj.getString("Brand"));

                            sql.insert(NETWORKPREFIX, null, cv);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    sql.setTransactionSuccessful();

                } finally {
                    sql.endTransaction();
                }

            }

            public void deleteProdCode (DatabaseHandler db){

                SQLiteDatabase sql = db.getWritableDatabase();

                sql.execSQL("DELETE FROM " + SUNPRODUCT + "");
                sql.execSQL("DELETE FROM " + SMARTPRODUCT + "");
                sql.execSQL("DELETE FROM " + GLOBEPRODUCT + "");

                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + SUNPRODUCT + "'");
                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + SMARTPRODUCT + "'");
                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + GLOBEPRODUCT + "'");

            }

            public void DeletePrefix (DatabaseHandler db){

                SQLiteDatabase sql = db.getWritableDatabase();

                sql.execSQL("DELETE FROM " + NETWORKPREFIX + "");
                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + NETWORKPREFIX + "'");


            }

            public void DeleteGlobeProduct (DatabaseHandler db){

                SQLiteDatabase sql = db.getWritableDatabase();

                sql.execSQL("DELETE FROM " + GLOBEPRODUCT + "");
                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + GLOBEPRODUCT + "'");

            }

            public void DeleteSmartProduct (DatabaseHandler db){

                SQLiteDatabase sql = db.getWritableDatabase();

                sql.execSQL("DELETE FROM " + SMARTPRODUCT + "");
                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + SMARTPRODUCT + "'");

            }

            public void DeleteSunProduct (DatabaseHandler db){

                SQLiteDatabase sql = db.getWritableDatabase();

                sql.execSQL("DELETE FROM " + SUNPRODUCT + "");
                sql.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
                        + SMARTPRODUCT + "'");

            }

            public String getNetworkPrefix (DatabaseHandler db, String prfix){

                String myresult = "";

                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor c = sql.rawQuery("SELECT * FROM " + NETWORKPREFIX + " Where prefix = " + prfix + " LIMIT 1", null);


                Logger.debug("Query", "SELECT * FROM " + NETWORKPREFIX + " Where prefix = " + prfix + "");


                if (c.getCount() > 0) {
                    c.moveToFirst();
                    do {
                        myresult = c.getString(c.getColumnIndex("brand"));
                    } while (c.moveToNext());
                }


                c.close();


                return myresult;


            }

            public Cursor getProduct (DatabaseHandler db, String brand){

                String myquery = "";
                SQLiteDatabase sql = db.getReadableDatabase();

                if (brand.contains("TNT") || brand.contains("SMART")) {

                    if (brand.equalsIgnoreCase("SMART") || brand.equalsIgnoreCase("TNT")) {
                        myquery = "SELECT _id,description,keyword,discount,amount FROM " + SMARTPRODUCT + " WHERE networkid LIKE '%" + brand.trim() + "%' Order By _id ASC";
                    } else {
                        myquery = "SELECT _id,description,keyword,discount,amount FROM " + SMARTPRODUCT + " Order By _id ASC";

                    }

                }

                if (brand.equalsIgnoreCase("SUN")) {
                    myquery = "SELECT _id,description,elpkeyword as keyword,discount,amount FROM " + SUNPRODUCT + " Order By _id ASC";
                }

                if (brand.equalsIgnoreCase("GLOBE") || brand.equalsIgnoreCase("TM") || brand.equalsIgnoreCase("ABS") || brand.equalsIgnoreCase("CHERRY")) {
                    myquery = "SELECT _id,productcode as keyword, productcode as description ,discount,maxamount as amount FROM " + GLOBEPRODUCT + " Where denomtype ='SPECIAL' AND networkid='" + brand.trim() + "' Order By _id ASC";
                }


                Logger.debug("Query", myquery);

                Cursor c = sql.rawQuery(myquery, null);


                return c;

            }

            public Cursor getSearchProduct (DatabaseHandler db, String brand, String searchkey){

                String myquery = "";
                SQLiteDatabase sql = db.getReadableDatabase();

                if (brand.contains("TNT") || brand.contains("SMART")) {

                    if (brand.equalsIgnoreCase("SMART") || brand.equalsIgnoreCase("TNT")) {
                        myquery = "SELECT _id,description,keyword,discount,amount FROM " + SMARTPRODUCT + " WHERE description Like '%" + searchkey + "%' AND networkid LIKE '%" + brand.trim() + "%'  Order By description ASC";
                    } else {
                        myquery = "SELECT _id,description,keyword,discount,amount FROM " + SMARTPRODUCT + "  Where  description Like '%" + searchkey + "%' Order By description ASC";

                    }
                }

                if (brand.equalsIgnoreCase("SUN")) {
                    myquery = "SELECT _id,description,elpkeyword as keyword,discount,amount FROM " + SUNPRODUCT + " Where description Like '%" + searchkey + "%' Order By description ASC";
                }

                if (brand.equalsIgnoreCase("GLOBE") || brand.equalsIgnoreCase("TM") || brand.equalsIgnoreCase("ABS") || brand.equalsIgnoreCase("CHERRY")) {
                    myquery = "SELECT _id,productcode as keyword, productcode as description ,discount,maxamount as amount FROM " + GLOBEPRODUCT + " Where denomtype ='SPECIAL' AND keyword Like '%" + searchkey + "%' Order By productcode ASC";
                }

                Logger.debug("Query", myquery);

                Cursor c = sql.rawQuery(myquery, null);


                return c;

            }

            public String isValidAmount (DatabaseHandler db, String type, String amount, String
            network){
                SQLiteDatabase sql = db.getReadableDatabase();
                String result = "0";
                String tablename = "";
                if (network.trim().equals("ABS") || network.trim().equals("GLOBE") || network.trim().equals("TM") || network.trim().equals("CHERRY")) {
                    tablename = "GLOBE";
                }

                String myquery = "Select * FROM " + tablename.toLowerCase() + "product WHERE productcode='" + type
                        + "' AND CAST(minamount AS INTEGER)<='"
                        + Integer.parseInt(amount) + "' AND CAST(maxamount AS INTEGER)>='"
                        + Integer.parseInt(amount) + "' AND networkid='" + network + "'";


                Cursor c = sql.rawQuery(myquery, null);
                if (c.getCount() > 0) {
                    result = "1";
                }
                c.close();

                return result;
            }

            public String getProductCodeDesc (DatabaseHandler db, String brand, String productcode){
                String productdesc = "";
                String myquery = "";
                SQLiteDatabase sql = db.getReadableDatabase();

                if (brand.contains("TNT") || brand.contains("SMART")) {
                    myquery = "SELECT description FROM " + SMARTPRODUCT + " WHERE keyword='" + productcode + "' LIMIT  1";
                }

                if (brand.equalsIgnoreCase("SUN")) {
                    myquery = "SELECT description FROM " + SUNPRODUCT + "  WHERE elpkeyword = '" + productcode + "' LIMIT 1 ";
                }

                if (brand.equalsIgnoreCase("GLOBE") || brand.equalsIgnoreCase("TM") || brand.equalsIgnoreCase("ABS") || brand.equalsIgnoreCase("CHERRY")) {
                    myquery = "SELECT productcode as description FROM " + GLOBEPRODUCT + " Where productcode ='" + productcode + "' LIMIT 1";
                }


                Cursor c = sql.rawQuery(myquery, null);

                if (c.getCount() > 0) {
                    c.moveToFirst();
                    do {
                        productdesc = c.getString(c.getColumnIndex("description"));

                    } while (c.moveToNext());
                }
                return productdesc;
            }

            //end Region

            //Region Notification
            public void insertNotification (DatabaseHandler db, String txnno, String subject, String
            status, String from, String fromicon, String message, String date){
                SQLiteDatabase sql = db.getWritableDatabase();
                Log.v("INSERT NOTI", "NOTI");
                String d = "DELETE FROM " + NOTIFICATIONS + " WHERE txnno = '" + txnno + "'";
                sql.execSQL(d);

                ContentValues value = new ContentValues();
                value.put("txnno", txnno);
                value.put("subject", subject);
                value.put("status", status);
                value.put("sender", from);
                value.put("sendericon", fromicon);
                value.put("message", message);
                value.put("date", date);


                // inserting row
                sql.insert(NOTIFICATIONS, null, value);


            }

            public void deleteNotifications (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + NOTIFICATIONS + "";
                sql.execSQL(d);

            }

            public Cursor getNotifications (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + NOTIFICATIONS + " ORDER  BY _id ASC", null);
                return c;
            }

            public void updateNotificationStatus (DatabaseHandler db, String status, String txnno){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put("status", status);
                sql.update(NOTIFICATIONS, cv, "txnno='" + txnno + "'", null);

            }

            public int getNofUnreadNotifications (DatabaseHandler db){
                int nof = 0;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + NOTIFICATIONS + " WHERE status=0", null);
                nof = cursor.getCount();
                return nof;
            }
            //end Region

            // Region Indicators
            public void saveIndicators (DatabaseHandler db, String indicator1){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + INDICATORS + "";
                sql.execSQL(d);

                ContentValues value = new ContentValues();
                value.put("indicator1", indicator1);

                // inserting row
                sql.insert(INDICATORS, null, value);

            }

            public Cursor getIndicators (DatabaseHandler db){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + INDICATORS + " Order By _id DESC", null);
                return c;
            }

            //Prepaid Voucher Transactions / Purchases
            public void insertPrepaidVoucherTransaction (DatabaseHandler
            db, PrepaidVoucherTransaction trans){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("ID", String.valueOf(trans.getID()));
                values.put("DateTimeIN", trans.getDateTimeIN());
                values.put("DateTimeCompleted", trans.getDateTimeCompleted());
                values.put("TransactionNo", trans.getTransactionNo());
                values.put("BorrowingType", trans.getBorrowingType());
                values.put("GuarantorID", trans.getGuarantorID());
                values.put("GuarantorName", trans.getGuarantorName());
                values.put("SubGuarantorID", trans.getSubGuarantorID());
                values.put("SubGuarantorName", trans.getSubGuarantorName());
                values.put("BorrowerID", trans.getBorrowerID());
                values.put("BorrowerName", trans.getBorrowerName());
                values.put("TotalNoOfVouchers", String.valueOf(trans.getTotalNoOfVouchers()));
                values.put("TotalVoucherAmount", String.valueOf(trans.getTotalVoucherAmount()));
                values.put("isThruSubGuarantor", String.valueOf(trans.getIsThruSubGuarantor()));
                values.put("GuarantorPreNoOfVouchers", String.valueOf(trans.getGuarantorPreNoOfVouchers()));
                values.put("GuarantorPostNoOfVouchers", String.valueOf(trans.getGuarantorPostNoOfVouchers()));
                values.put("SubGuarantorPreNoOfVouchers", String.valueOf(trans.getSubGuarantorPreNoOfVouchers()));
                values.put("SubGuarantorPostNoOfVouchers", String.valueOf(trans.getSubGuarantorPostNoOfVouchers()));
                values.put("BorrowerPreNoOfVouchers", String.valueOf(trans.getBorrowerPreNoOfVouchers()));
                values.put("BorrowerPostNoOfVouchers", String.valueOf(trans.getBorrowerPostNoOfVouchers()));
                values.put("TransactionMedium", trans.getTransactionMedium());
                values.put("IMEI", trans.getIMEI());
                values.put("UserID", trans.getUserID());
                values.put("ScheduleProcessID", trans.getScheduleProcessID());
                values.put("BorrowScheduleID", trans.getBorrowScheduleID());
                values.put("Status", trans.getStatus());
                values.put("VoucherGenerationID", trans.getVoucherGenerationID());
                values.put("PaymentTxnNo", trans.getPaymentTxnNo());

                sql.insert(PURCHASES, null, values);
            }

            public void clearPrepaidVoucherTransaction (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + PURCHASES + "";
                sql.execSQL(d);
            }

            public ArrayList<PrepaidVoucherTransaction> getAllPrepaidVoucherTransactions
            (DatabaseHandler db){
                ArrayList<PrepaidVoucherTransaction> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * From " + PURCHASES + "", null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String ID = c.getString(c.getColumnIndex("ID"));
                        String DateTimeIN = c.getString(c.getColumnIndex("DateTimeIN"));
                        String DateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String TransactionNo = c.getString(c.getColumnIndex("TransactionNo"));
                        String BorrowingType = c.getString(c.getColumnIndex("BorrowingType"));
                        String GuarantorID = c.getString(c.getColumnIndex("GuarantorID"));
                        String GuarantorName = c.getString(c.getColumnIndex("GuarantorName"));
                        String SubGuarantorID = c.getString(c.getColumnIndex("SubGuarantorID"));
                        String SubGuarantorName = c.getString(c.getColumnIndex("SubGuarantorName"));
                        String BorrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                        String BorrowerName = c.getString(c.getColumnIndex("BorrowerName"));
                        String TotalNoOfVouchers = c.getString(c.getColumnIndex("TotalNoOfVouchers"));
                        String TotalVoucherAmount = c.getString(c.getColumnIndex("TotalVoucherAmount"));
                        String isThruSubGuarantor = c.getString(c.getColumnIndex("isThruSubGuarantor"));
                        String GuarantorPreNoOfVouchers = c.getString(c.getColumnIndex("GuarantorPreNoOfVouchers"));
                        String GuarantorPostNoOfVouchers = c.getString(c.getColumnIndex("GuarantorPostNoOfVouchers"));
                        String SubGuarantorPreNoOfVouchers = c.getString(c.getColumnIndex("SubGuarantorPreNoOfVouchers"));
                        String SubGuarantorPostNoOfVouchers = c.getString(c.getColumnIndex("SubGuarantorPostNoOfVouchers"));
                        String BorrowerPreNoOfVouchers = c.getString(c.getColumnIndex("BorrowerPreNoOfVouchers"));
                        String BorrowerPostNoOfVouchers = c.getString(c.getColumnIndex("BorrowerPostNoOfVouchers"));
                        String TransactionMedium = c.getString(c.getColumnIndex("TransactionMedium"));
                        String IMEI = c.getString(c.getColumnIndex("IMEI"));
                        String UserID = c.getString(c.getColumnIndex("UserID"));
                        String ScheduleProcessID = c.getString(c.getColumnIndex("ScheduleProcessID"));
                        String BorrowScheduleID = c.getString(c.getColumnIndex("BorrowScheduleID"));
                        String Status = c.getString(c.getColumnIndex("Status"));
                        String VoucherGenerationID = c.getString(c.getColumnIndex("VoucherGenerationID"));
                        String PaymentTxnNo = c.getString(c.getColumnIndex("PaymentTxnNo"));

                        arrList.add(new PrepaidVoucherTransaction(Integer.parseInt(ID),
                                DateTimeIN,
                                DateTimeCompleted,
                                TransactionNo,
                                BorrowingType,
                                GuarantorID,
                                GuarantorName,
                                SubGuarantorID,
                                SubGuarantorName,
                                BorrowerID,
                                BorrowerName,
                                Integer.parseInt(TotalNoOfVouchers),
                                Double.parseDouble(TotalVoucherAmount),
                                Integer.parseInt(isThruSubGuarantor),
                                Integer.parseInt(GuarantorPreNoOfVouchers),
                                Integer.parseInt(GuarantorPostNoOfVouchers),
                                Integer.parseInt(SubGuarantorPreNoOfVouchers),
                                Integer.parseInt(SubGuarantorPostNoOfVouchers),
                                Integer.parseInt(BorrowerPreNoOfVouchers),
                                Integer.parseInt(BorrowerPostNoOfVouchers),
                                TransactionMedium,
                                IMEI,
                                UserID,
                                ScheduleProcessID,
                                BorrowScheduleID,
                                Status,
                                VoucherGenerationID,
                                PaymentTxnNo));
                    }
                }
                return arrList;
            }
            //end Region


            //#REGION BILLSPAYMENT
            public void insertBiller (DatabaseHandler db, String serviceprovider, String
            billercode, String billername, String billerdesc, String servicecode, String
            category, String billerinfo, String logo, String customerscclass, String
            ismybiller, String accountno, String otherinfo, String notes, String
            groupcategory, String categorylogo){

                try {

                    SQLiteDatabase sql = db.getWritableDatabase();
                    ContentValues value = new ContentValues();

                    value.put("ServiceProviderBillerCode", serviceprovider);
                    value.put("BillerCode", billercode);
                    value.put("BillerName", billername);
                    value.put("BillerDescription", billerdesc);
                    value.put("Category", category);
                    value.put("BillerInfo", billerinfo);
                    value.put("LogoURL", logo);
                    value.put("ServiceCode", servicecode);
                    value.put("CustomerSCClass", customerscclass);
                    value.put("Notes", notes);

                    // inserting row
                    if (ismybiller.equals("TRUE")) {
                        value.put("AccountNo", accountno);
                        value.put("OtherInfo", otherinfo);
                        sql.insert(MYBILLERS, null, value);
                    } else {
                        value.put("GroupCategory", groupcategory);
                        value.put("CategoryLogo", categorylogo);
                        sql.insert(BILLERS, null, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            public Cursor getBillers (DatabaseHandler db, String ismybiller, String searchval){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c;
                if (ismybiller.equals("TRUE")) {
                    if (searchval.equals("")) {
                        c = sql.rawQuery("SELECT * From " + MYBILLERS + " ORDER BY _id ASC", null);
                    } else {
                        c = sql.rawQuery("SELECT * From " + MYBILLERS + " WHERE BillerName Like '%" + searchval + "%' ORDER BY _id ASC", null);
                    }

                } else {
                    if (searchval.equals("")) {
                        c = sql.rawQuery("SELECT * From " + BILLERS + " ORDER BY _id ASC", null);
                    } else {
                        c = sql.rawQuery("SELECT * From " + BILLERS + " WHERE BillerName Like '%" + searchval + "%' ORDER BY _id ASC", null);
                    }

                }

                return c;
            }

            public Cursor getBillsGroupCategory (DatabaseHandler db, String searchval){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c;

                if (searchval.equals("")) {

                    c = sql.rawQuery("SELECT DISTINCT * FROM " + BILLERS + " WHERE GroupCategory NOT NULL || GroupCategory != 'null' "
                            + "GROUP BY GroupCategory ORDER BY GroupCategory ", null);

                } else {

                    c = sql.rawQuery("SELECT DISTINCT * FROM " + BILLERS + " WHERE Category Like '%" + searchval + "%'"
                            + "GROUP BY Category ORDER BY Category ", null);

                }

                return c;
            }


            public Cursor getBillersSubCategory (DatabaseHandler db, String searchval, String
            groupcategory){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c;

                if (searchval.equals("")) {
                    c = sql.rawQuery("SELECT DISTINCT * FROM " + BILLERS + " WHERE length(GroupCategory) > 0 "
                            + "AND GroupCategory = ?"
                            + "GROUP BY Category ORDER BY Category ASC ", new String[]{groupcategory});
                } else {
                    c = sql.rawQuery("SELECT DISTINCT * FROM " + BILLERS + " WHERE Category Like '%" + searchval + "%'"
                            + "AND GroupCategory = ?"
                            + "GROUP BY Category ORDER BY Category ", new String[]{groupcategory});
                }

                return c;
            }

            public Cursor getBillersCatViewAll (DatabaseHandler db, String searchval, String
            category){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c;

                if (searchval.equals("")) {
                    c = sql.rawQuery("SELECT DISTINCT * FROM " + BILLERS + " WHERE length(Category) > 0 "
                            + "AND Category = ?"
                            + "ORDER BY Category ASC ", new String[]{category});
                } else {
                    c = sql.rawQuery("SELECT DISTINCT * FROM " + BILLERS + " WHERE BillerName Like '%" + searchval + "%'"
                            + "AND Category = ?"
                            + "ORDER BY Category ASC", new String[]{category});
                }

                return c;
            }

            //GET BILLERS MERCHANT
            public List<BillsPaymentCategories> getBillersCatBillsBelongs (DatabaseHandler
            db, String category){
                List<BillsPaymentCategories> Billerlist = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();

                String strsql = "SELECT * FROM " + BILLERS
                        + " WHERE Category = ?"
                        + " ORDER BY Category ASC";

                Cursor c = sql.rawQuery(strsql, new String[]{category});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String logourl = c.getString(c.getColumnIndex("LogoURL"));
                        String billercode = c.getString(c.getColumnIndex("BillerCode"));
                        String billername = c.getString(c.getColumnIndex("BillerName"));
                        String billerscategory = c.getString(c.getColumnIndex("Category"));
                        String serviceprvbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                        String categoryGroup = c.getString(c.getColumnIndex("GroupCategory"));
                        String categorylogo = c.getString(c.getColumnIndex("CategoryLogo"));

                        Billerlist.add(new BillsPaymentCategories(logourl, billercode, billername, billerscategory, "", serviceprvbillercode, categoryGroup, categorylogo));
                    }
                }
                c.close();
                db.close();

                return Billerlist;
            }


            public void deleteBillers (DatabaseHandler db, String ismybiller){
                SQLiteDatabase sql = db.getWritableDatabase();

                if (ismybiller.equals("TRUE")) {
                    String d = "DELETE FROM " + MYBILLERS + " ";
                    sql.execSQL(d);
                } else {
                    String d = "DELETE FROM " + BILLERS + " ";
                    sql.execSQL(d);
                }

            }

            public Cursor getBillerInfo (DatabaseHandler db, String billercode, String
            ismybiller, String accountno){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c;

                if (ismybiller.equals("TRUE")) {
                    c = sql.rawQuery("SELECT * From " + MYBILLERS + " WHERE BillerCode='" + billercode + "' AND AccountNo='" + accountno + "' ORDER BY _id ASC", null);
                } else {
                    c = sql.rawQuery("SELECT * From " + BILLERS + " WHERE BillerCode='" + billercode + "' ORDER BY _id ASC", null);
                }
                return c;
            }

            public Cursor getSingleBiller (DatabaseHandler db, String billercode){
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + BILLERS + " WHERE BillerCode='" + billercode + "' ORDER BY _id ASC", null);

                return cursor;
            }

            public boolean validateBiller (DatabaseHandler db, String billercode){
                boolean result = true;
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c;
                c = sql.rawQuery("SELECT * From " + BILLERS + " WHERE BillerCode='" + billercode + "' LIMIT 1 ", null);

                if (c.getCount() > 0) {
                    result = true;
                } else {
                    result = false;
                }
                return result;
            }


            //insert voucher details
            public void insertVoucherDetails (DatabaseHandler db, String billingid, String
            guarantorid, String guarantorname, String statementdate, String duedate, String
            amount, String advancepayment, String partialpayment, String commission, String
            netamount, String duebalance, String cutoverperiod, String datetimepaid, String
            paymenttxnno){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("billingid", billingid);
                value.put("guarantorid", guarantorid);
                value.put("guarantorname", guarantorname);
                value.put("statementdate", statementdate);
                value.put("duedate", duedate);
                value.put("amount", amount);
                value.put("advancepayment", advancepayment);
                value.put("partialpayment", partialpayment);
                value.put("commission", commission);
                value.put("netamount", netamount);
                value.put("duebalance", duebalance);
                value.put("cutoverperiod", cutoverperiod);
                value.put("datetimepaid", datetimepaid);
                value.put("paymenttxnno", paymenttxnno);

                // inserting row
                sql.insert(PAIDBILLS, null, value);
            }

            //insert prepaid logs
            public void insertPrepaidLogs (DatabaseHandler db, PrepaidLogs details){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("TransactionNo", details.getTransactionNo());
                value.put("MobileTarget", details.getMobileTarget());
                value.put("ProductCode", details.getProductCode());
                value.put("Amount", details.getAmount());
                value.put("DateTimeCompleted", details.getDateTimeCompleted());
                value.put("TxnStatus", details.getTxnStatus());
                value.put("DenomType", details.getDenomType());
                value.put("Network", details.getNetwork());

                // inserting row
                sql.insert(PREPAID_LOGS, null, value);
            }

            //insert retailer reloading logs
            public void insertRetailerReloadingLogs (DatabaseHandler db, PrepaidLogs details){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("TransactionNo", details.getTransactionNo());
                value.put("MobileTarget", details.getMobileTarget());
                value.put("ProductCode", details.getProductCode());
                value.put("Amount", details.getAmount());
                value.put("DateTimeCompleted", details.getDateTimeCompleted());
                value.put("TxnStatus", details.getTxnStatus());
                value.put("DenomType", details.getDenomType());
                value.put("Network", details.getNetwork());
                value.put("Discount", details.getDiscount());
                value.put("NetAmount", details.getNetAmount());

                // inserting row
                sql.insert(RETAILER_RELOADING_LOGS, null, value);
            }

            //insert billspayent logs
            public void insertBillsPaymentLogs (DatabaseHandler db, BillsPaymentLogs details){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("DateTimeCompleted", details.getDateTimeCompleted());
                value.put("BillerTransactionNo", details.getBillerTransactionNo());
                value.put("GKTransactionNo", details.getGKTransactionNo());
                value.put("BillerName", details.getBillerName());
                value.put("AccountNo", details.getAccountNo());
                value.put("AccountName", details.getAccountName());
                value.put("AmountPaid", details.getAmountPaid());
                value.put("TxnStatus", details.getTxnStatus());
                value.put("ServiceProviderBillerCode", details.getServiceProviderBillerCode());
                value.put("BillerCode", details.getBillerCode());
                value.put("BillerInfo", details.getBillerInfo());
                value.put("ServiceCode", details.getServiceCode());
                value.put("CustomerSCClass", details.getCustomerSCClass());
                value.put("BillDetails", details.getBillDetails());
                value.put("BillerLogoURL", details.getBillerLogoURL());
                value.put("OtherCharges", details.getOtherCharges());
                value.put("CustomerServiceCharge", details.getCustomerServiceCharge());
                value.put("Extra2", details.getExtra2());
                value.put("TotalAmountPaid", details.getTotalAmountPaid());

                // inserting row
                sql.insert(BILLS_PAYMENT_LOGS, null, value);
            }

            //getting all prepaid logs details
            public List<PrepaidLogs> getPrepaidLogsDetails (DatabaseHandler db){
                List<PrepaidLogs> logs = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + PREPAID_LOGS + " ORDER BY DateTimeCompleted DESC ", new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String transactionNo = c.getString(c.getColumnIndex("TransactionNo"));
                        String mobileTarget = c.getString(c.getColumnIndex("MobileTarget"));
                        String productCode = c.getString(c.getColumnIndex("ProductCode"));
                        String amount = c.getString(c.getColumnIndex("Amount"));
                        String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String txnStatus = c.getString(c.getColumnIndex("TxnStatus"));
                        String denomType = c.getString(c.getColumnIndex("DenomType"));
                        String network = c.getString(c.getColumnIndex("Network"));

                        logs.add(new PrepaidLogs(dateTimeCompleted, transactionNo, mobileTarget, productCode, amount, txnStatus, denomType, network));
                    }
                }
                c.close();
                db.close();
                return logs;
            }

            //getting all retailer reloading logs details
            public List<PrepaidLogs> getRetailerReloadingLogsDetails (DatabaseHandler db){
                List<PrepaidLogs> logs = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + RETAILER_RELOADING_LOGS + " ORDER BY DateTimeCompleted DESC ", new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String transactionNo = c.getString(c.getColumnIndex("TransactionNo"));
                        String mobileTarget = c.getString(c.getColumnIndex("MobileTarget"));
                        String productCode = c.getString(c.getColumnIndex("ProductCode"));
                        String amount = c.getString(c.getColumnIndex("Amount"));
                        String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String txnStatus = c.getString(c.getColumnIndex("TxnStatus"));
                        String denomType = c.getString(c.getColumnIndex("DenomType"));
                        String network = c.getString(c.getColumnIndex("Network"));
                        double discount = c.getDouble(c.getColumnIndex("Discount"));
                        double netAmount = c.getDouble(c.getColumnIndex("NetAmount"));

                        logs.add(new PrepaidLogs(dateTimeCompleted, transactionNo, mobileTarget, productCode, amount, txnStatus, denomType, network, discount, netAmount));
                    }
                }
                c.close();
                db.close();
                return logs;
            }

            //getting all bills payment logs details
            public List<BillsPaymentLogs> getBillsPaymentLogsDetails (DatabaseHandler db){
                List<BillsPaymentLogs> logs = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + BILLS_PAYMENT_LOGS + " ORDER BY DateTimeCompleted DESC ", new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String gktransactionno = c.getString(c.getColumnIndex("GKTransactionNo"));
                        String billertransactionno = c.getString(c.getColumnIndex("BillerTransactionNo"));
                        String billername = c.getString(c.getColumnIndex("BillerName"));
                        String accountno = c.getString(c.getColumnIndex("AccountNo"));
                        String accountname = c.getString(c.getColumnIndex("AccountName"));
                        double amountpaid = c.getDouble(c.getColumnIndex("AmountPaid"));
                        String datetimecompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String txnstatus = c.getString(c.getColumnIndex("TxnStatus"));
                        String serviceproviderbillercode = c.getString(c.getColumnIndex("ServiceProviderBillerCode"));
                        String billercode = c.getString(c.getColumnIndex("BillerCode"));
                        String servicecode = c.getString(c.getColumnIndex("ServiceCode"));
                        String billerinfo = c.getString(c.getColumnIndex("BillerInfo"));
                        String customerscclass = c.getString(c.getColumnIndex("CustomerSCClass"));
                        String billdetails = c.getString(c.getColumnIndex("BillDetails"));
                        String billerlogourl = c.getString(c.getColumnIndex("BillerLogoURL"));
                        double otherCharges = c.getDouble(c.getColumnIndex("OtherCharges"));
                        double customerServiceCharge = c.getDouble(c.getColumnIndex("CustomerServiceCharge"));
                        String extra2 = c.getString(c.getColumnIndex("Extra2"));
                        double totalAmountPaid = c.getDouble(c.getColumnIndex("TotalAmountPaid"));

                        logs.add(new BillsPaymentLogs(gktransactionno, billertransactionno, billername, accountno, accountname, amountpaid, datetimecompleted, txnstatus, serviceproviderbillercode, billercode, servicecode, billerinfo, customerscclass, billdetails, billerlogourl, otherCharges, customerServiceCharge, extra2, totalAmountPaid));
                    }
                }
                c.close();
                db.close();
                return logs;
            }

            //insert Consummation Transaction Details
            public void insertConsummationTransactionDetails (DatabaseHandler db, TransactionDetails
            details, String type){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("TransactionNo", details.getTransactionNo());
                value.put("MobileTarget", details.getMobileTarget());
                value.put("ProductCode", details.getProductCode());
                value.put("Amount", details.getAmount());
                value.put("DateTimeCompleted", details.getDateTimeCompleted());
                value.put("BillerTransactionNo", details.getBillerTransactionNo());
                value.put("GKTransactionNo", details.getGKTransactionNo());
                value.put("BillerName", details.getBillerName());
                value.put("AccountNo", details.getAccountNo());
                value.put("AccountName", details.getAccountName());
                value.put("AmountPaid", details.getAmountPaid());
                value.put("TotalAmountPaid", details.getTotalAmountPaid());
                value.put("Discount", details.getDiscount());
                value.put("NetAmount", details.getNetAmount());
                value.put("TransactionDetailsType", type);

                // inserting row
                sql.insert(CONSUMMATIONTRANSACTIONDETAILS, null, value);
            }

            //insert received vouchers
            public void insertReceivedVouchers (DatabaseHandler db, TransactionsVouchers details){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("DateTimeCompleted", details.getDateTimeCompleted());
                value.put("TransferType", details.getTransferType());
                value.put("SourceBorrowerID", details.getSourceBorrowerID());
                value.put("SourceBorrowerName", details.getSourceBorrowerName());
                value.put("RecipientBorrowerID", details.getRecipientBorrowerID());
                value.put("RecipientBorrowerName", details.getRecipientBorrowerName());
                value.put("RecipientMobileNo", details.getRecipientMobileNo());
                value.put("RecipientEmailAddress", details.getRecipientEmailAddress());
                value.put("VoucherSerialNo", details.getVoucherSerialNo());
                value.put("VoucherCode", details.getVoucherCode());
                value.put("VoucherDenoms", details.getVoucherDenoms());
                value.put("ProcessID", details.getProcessID());
                value.put("Status", details.getStatus());
                value.put("TransferTxnNo", details.getTransferTxn());

                // inserting row
                sql.insert(RECEIVED_VOUCHERS, null, value);
            }

            public void insertTransferredVoucheres (DatabaseHandler db, TransactionModel details){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("DateTimeCompleted", details.getDateTimeCompleted());
                value.put("TransferType", details.getTransferType());
                value.put("SourceBorrowerID", details.getSourceBorrowerID());
                value.put("SourceBorrowerName", details.getSourceBorrowerName());
                value.put("RecipientBorrowerID", details.getRecipientBorrowerID());
                value.put("RecipientBorrowerName", details.getRecipientBorrowerName());
                value.put("RecipientMobileNo", details.getRecipientMobileNo());
                value.put("RecipientEmailAddress", details.getRecipientEmailAddress());
                value.put("VoucherSerialNo", details.getVoucherSerialNo());
                value.put("VoucherCode", details.getVoucherCode());
                value.put("VoucherDenoms", details.getVoucherDenoms());
                value.put("ProcessID", details.getProcessID());
                value.put("Status", details.getStatus());
                value.put("TransferTxnNo", details.getTransferTxnNo());

                // inserting row
                sql.insert(TRANSFERRED_VOUCHERS, null, value);
            }

            //getting all received vouchers
            public List<TransactionModel> getAllReceivedVouchers (DatabaseHandler db, String
            borrowerid){
                List<TransactionModel> vouchers = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + RECEIVED_VOUCHERS + " WHERE " + " RecipientBorrowerID " + "=?" + " ORDER BY DateTimeCompleted DESC ";
                Cursor c = sql.rawQuery(strsql, new String[]{borrowerid});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String datetimecompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String transfertype = c.getString(c.getColumnIndex("TransferType"));
                        String sourceborrowerid = c.getString(c.getColumnIndex("SourceBorrowerID"));
                        String sourceborrowername = c.getString(c.getColumnIndex("SourceBorrowerName"));
                        String recborrowerid = c.getString(c.getColumnIndex("RecipientBorrowerID"));
                        String recborrowername = c.getString(c.getColumnIndex("RecipientBorrowerName"));
                        String mobileno = c.getString(c.getColumnIndex("RecipientMobileNo"));
                        String emailaddress = c.getString(c.getColumnIndex("RecipientEmailAddress"));
                        String voucherserialno = c.getString(c.getColumnIndex("VoucherSerialNo"));
                        String vouchercode = c.getString(c.getColumnIndex("VoucherCode"));
                        String voucherdenoms = c.getString(c.getColumnIndex("VoucherDenoms"));
                        String processid = c.getString(c.getColumnIndex("ProcessID"));
                        String status = c.getString(c.getColumnIndex("Status"));
                        String transfertxnno = c.getString(c.getColumnIndex("TransferTxnNo"));

                        vouchers.add(new TransactionModel(datetimecompleted, transfertxnno, transfertype, sourceborrowerid, sourceborrowername,
                                recborrowerid, recborrowername, mobileno, emailaddress, voucherserialno, vouchercode, voucherdenoms,
                                processid, status));
                    }
                }
                c.close();
                db.close();
                return vouchers;
            }

            //getting all received vouchers
            public List<TransactionModel> getAllTransferredVouchers (DatabaseHandler db, String
            borrowerid){
                List<TransactionModel> vouchers = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + TRANSFERRED_VOUCHERS + " WHERE " + " SourceBorrowerID " + "=?" + " ORDER BY DateTimeCompleted DESC ";
                Cursor c = sql.rawQuery(strsql, new String[]{borrowerid});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String datetimecompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String transfertype = c.getString(c.getColumnIndex("TransferType"));
                        String sourceborrowerid = c.getString(c.getColumnIndex("SourceBorrowerID"));
                        String sourceborrowername = c.getString(c.getColumnIndex("SourceBorrowerName"));
                        String recborrowerid = c.getString(c.getColumnIndex("RecipientBorrowerID"));
                        String recborrowername = c.getString(c.getColumnIndex("RecipientBorrowerName"));
                        String mobileno = c.getString(c.getColumnIndex("RecipientMobileNo"));
                        String emailaddress = c.getString(c.getColumnIndex("RecipientEmailAddress"));
                        String voucherserialno = c.getString(c.getColumnIndex("VoucherSerialNo"));
                        String vouchercode = c.getString(c.getColumnIndex("VoucherCode"));
                        String voucherdenoms = c.getString(c.getColumnIndex("VoucherDenoms"));
                        String processid = c.getString(c.getColumnIndex("ProcessID"));
                        String status = c.getString(c.getColumnIndex("Status"));
                        String tranfertxn = c.getString(c.getColumnIndex("TransferTxnNo"));

                        vouchers.add(new TransactionModel(datetimecompleted, tranfertxn, transfertype, sourceborrowerid, sourceborrowername,
                                recborrowerid, recborrowername, mobileno, emailaddress, voucherserialno, vouchercode, voucherdenoms,
                                processid, status));
                    }
                }
                c.close();
                db.close();
                return vouchers;
            }

            //getting all borrowings data
            public List<Transaction> getBorrowingsData (DatabaseHandler db){
                List<Transaction> vouchers = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + BORROWINGS + " ORDER BY DateTimeCompleted DESC ", new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String transactionno = c.getString(c.getColumnIndex("transactionno"));
                        String datetimecompleted = c.getString(c.getColumnIndex("datetimecompleted"));
                        String totalvoucheramount = c.getString(c.getColumnIndex("totalvoucheramount"));
                        String totalnumvoucher = c.getString(c.getColumnIndex("totalnumvouchers"));

                        vouchers.add(new Transaction(transactionno, datetimecompleted, totalvoucheramount, totalnumvoucher));
                    }
                }
                c.close();
                db.close();
                return vouchers;
            }

            //getting all borrowings voucher details data
            public List<Voucher> getBorrowingVoucherDetailsData (DatabaseHandler db, String
            transactionno){
                List<Voucher> vouchers = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + BORROWING_VOUCHER_DETAILS + " WHERE " + " TransactionNo " + "=?";
                Cursor c = sql.rawQuery(strsql, new String[]{transactionno});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String productid = c.getString(c.getColumnIndex("ProductID"));
                        String voucherdenom = c.getString(c.getColumnIndex("VoucherDenom"));
                        String vouchercode = c.getString(c.getColumnIndex("VoucherCode"));
                        String voucherserialno = c.getString(c.getColumnIndex("VoucherSerialNo"));

                        vouchers.add(new Voucher(productid, vouchercode, Double.parseDouble(voucherdenom), voucherserialno));
                    }
                }
                c.close();
                db.close();
                return vouchers;
            }

            //getting all borrowings voucher details data
            public List<Voucher> getPurchasesVoucherDetailsData (DatabaseHandler db, String
            transactionno){
                List<Voucher> vouchers = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + PURCHASES_VOUCHER_DETAILS + " WHERE " + " TransactionNo " + "=?";
                Cursor c = sql.rawQuery(strsql, new String[]{transactionno});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String productid = c.getString(c.getColumnIndex("ProductID"));
                        String voucherdenom = c.getString(c.getColumnIndex("VoucherDenom"));
                        String vouchercode = c.getString(c.getColumnIndex("VoucherCode"));
                        String voucherserialno = c.getString(c.getColumnIndex("VoucherSerialNo"));

                        vouchers.add(new Voucher(productid, vouchercode, Double.parseDouble(voucherdenom), voucherserialno));
                    }
                }
                c.close();
                db.close();
                return vouchers;
            }

            public void insertBorrowingsVoucherDetailsData (DatabaseHandler db, Voucher details){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("ProductID", details.getProductID());
                value.put("VoucherDenom", details.getVoucherDenom());
                value.put("VoucherCode", details.getVoucherCode());
                value.put("VoucherSerialNo", details.getVoucherSerialNo());
                value.put("TransactionNo", details.getTransactionNo());

                // inserting row
                sql.insert(BORROWING_VOUCHER_DETAILS, null, value);
            }

            public void insertPurchasesVoucherDetailsData (DatabaseHandler db, Voucher details){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("ProductID", details.getProductID());
                value.put("VoucherDenom", details.getVoucherDenom());
                value.put("VoucherCode", details.getVoucherCode());
                value.put("VoucherSerialNo", details.getVoucherSerialNo());
                value.put("TransactionNo", details.getTransactionNo());

                // inserting row
                sql.insert(PURCHASES_VOUCHER_DETAILS, null, value);
            }

            //end Region

            //get email address
            public String getEmail (DatabaseHandler db){
                String email = "";
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT email FROM " + ACCOUNTS + " LIMIT 1", new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        email = c.getString(c.getColumnIndex("email"));
                    }
                }
                c.close();
                db.close();
                return email;
            }

            //get currency id
            public String getCurrencyID (DatabaseHandler db){
                String currencyid = "";
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT currencyid FROM " + ACCOUNTWALLET + " LIMIT 1", new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        currencyid = c.getString(c.getColumnIndex("currencyid"));
                    }
                }
                c.close();
                db.close();
                return currencyid;
            }

            //BAN
            public V2Subscriber getSubscriber (DatabaseHandler db){
                V2Subscriber subscriber = null;
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + ACCOUNTS + " LIMIT 1", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String fullname = cursor.getString(cursor.getColumnIndex("firstname")) + " " + cursor.getString(cursor.getColumnIndex("lastname"));
                        String mobileNumber = cursor.getString(cursor.getColumnIndex("mobile"));
                        String emailAddress = cursor.getString(cursor.getColumnIndex("email"));
                        String streetAddress = cursor.getString(cursor.getColumnIndex("streetaddress"));
                        String city = cursor.getString(cursor.getColumnIndex("city"));
                        String country = cursor.getString(cursor.getColumnIndex("country"));
                        String guarantorid = cursor.getString(cursor.getColumnIndex("guarantorid"));
                        String profilepic = cursor.getString(cursor.getColumnIndex("profilepic"));
                        String firstName = cursor.getString(cursor.getColumnIndex("firstname"));
                        String lastName = cursor.getString(cursor.getColumnIndex("lastname"));
                        String middleName = cursor.getString(cursor.getColumnIndex("middlename"));
                        String gender = cursor.getString(cursor.getColumnIndex("gender"));
                        String notes1 = cursor.getString(cursor.getColumnIndex("notes1"));


                        subscriber = new V2Subscriber(fullname, mobileNumber, emailAddress, streetAddress, city, country, guarantorid, profilepic, firstName, lastName, middleName, gender, notes1);

                        Logger.debug("okhttp", "SUBS: " + new Gson().toJson(subscriber));
                    }
                }
                return subscriber;
            }

            //GET ALL MERCHANTS
            public List<Merchants> getAllMerchants (DatabaseHandler db){
                List<Merchants> merchantsList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + MERCHANTS;
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String merchantID = c.getString(c.getColumnIndex("merchantid"));
                        String merchantName = c.getString(c.getColumnIndex("merchantname"));
                        String merchantType = c.getString(c.getColumnIndex("merchanttype"));
                        String merchantGroup = c.getString(c.getColumnIndex("merchantgroup"));
                        String merchantStatus = c.getString(c.getColumnIndex("merchantstatus"));
                        String streetAddress = c.getString(c.getColumnIndex("streetaddress"));
                        String city = c.getString(c.getColumnIndex("city"));
                        String province = c.getString(c.getColumnIndex("province"));
                        String zip = c.getString(c.getColumnIndex("zip"));
                        String country = c.getString(c.getColumnIndex("country"));
                        String longitude = c.getString(c.getColumnIndex("longitude"));
                        String latitude = c.getString(c.getColumnIndex("latitude"));
                        String representative = c.getString(c.getColumnIndex("representative"));
                        String authorisedEmailAddress = c.getString(c.getColumnIndex("email"));
                        String authorisedTelNo = c.getString(c.getColumnIndex("telno"));
                        String authorisedMobileNo = c.getString(c.getColumnIndex("mobileno"));
                        String fax = c.getString(c.getColumnIndex("fax"));
                        String natureOfBusiness = c.getString(c.getColumnIndex("natureofbusiness"));
                        String TIN = c.getString(c.getColumnIndex("tin"));
                        String organizationType = c.getString(c.getColumnIndex("organizationtype"));
                        String noOfBranches = c.getString(c.getColumnIndex("noofbranches"));
                        String merchantLogo = c.getString(c.getColumnIndex("merchantlogo"));
                        String isFeatured = c.getString(c.getColumnIndex("isfeatured"));
                        String featureAddsPath = c.getString(c.getColumnIndex("featuredaddspath"));
                        String OrganizationLogo = c.getString(c.getColumnIndex("OrganizationLogo"));

                        merchantsList.add(new Merchants(merchantID, merchantName, merchantType, merchantGroup, merchantStatus, streetAddress, city, province, zip, country, longitude, latitude, representative, authorisedEmailAddress, authorisedTelNo, authorisedMobileNo, fax, natureOfBusiness, TIN, organizationType, noOfBranches, merchantLogo, isFeatured, featureAddsPath, OrganizationLogo));
                    }

                }
                c.close();
                db.close();

                return merchantsList;
            }

            //ORGANIZATION LIST
            public List<Merchants> getOrganizationList (DatabaseHandler db){
                List<Merchants> merchantsList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + MERCHANTS + " WHERE organizationtype <> '.' GROUP BY UPPER(organizationtype) ORDER BY organizationtype ASC";
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String merchantID = c.getString(c.getColumnIndex("merchantid"));
                        String merchantName = c.getString(c.getColumnIndex("merchantname"));
                        String merchantType = c.getString(c.getColumnIndex("merchanttype"));
                        String merchantGroup = c.getString(c.getColumnIndex("merchantgroup"));
                        String merchantStatus = c.getString(c.getColumnIndex("merchantstatus"));
                        String streetAddress = c.getString(c.getColumnIndex("streetaddress"));
                        String city = c.getString(c.getColumnIndex("city"));
                        String province = c.getString(c.getColumnIndex("province"));
                        String zip = c.getString(c.getColumnIndex("zip"));
                        String country = c.getString(c.getColumnIndex("country"));
                        String longitude = c.getString(c.getColumnIndex("longitude"));
                        String latitude = c.getString(c.getColumnIndex("latitude"));
                        String representative = c.getString(c.getColumnIndex("representative"));
                        String authorisedEmailAddress = c.getString(c.getColumnIndex("email"));
                        String authorisedTelNo = c.getString(c.getColumnIndex("telno"));
                        String authorisedMobileNo = c.getString(c.getColumnIndex("mobileno"));
                        String fax = c.getString(c.getColumnIndex("fax"));
                        String natureOfBusiness = c.getString(c.getColumnIndex("natureofbusiness"));
                        String TIN = c.getString(c.getColumnIndex("tin"));
                        String organizationType = c.getString(c.getColumnIndex("organizationtype"));
                        String noOfBranches = c.getString(c.getColumnIndex("noofbranches"));
                        String merchantLogo = c.getString(c.getColumnIndex("merchantlogo"));
                        String isFeatured = c.getString(c.getColumnIndex("isfeatured"));
                        String featureAddsPath = c.getString(c.getColumnIndex("featuredaddspath"));
                        String OrganizationLogo = c.getString(c.getColumnIndex("OrganizationLogo"));

                        merchantsList.add(new Merchants(merchantID, merchantName, merchantType, merchantGroup, merchantStatus, streetAddress, city, province, zip, country, longitude, latitude, representative, authorisedEmailAddress, authorisedTelNo, authorisedMobileNo, fax, natureOfBusiness, TIN, organizationType, noOfBranches, merchantLogo, isFeatured, featureAddsPath, OrganizationLogo));
                    }
                }
                c.close();
                db.close();

                return merchantsList;
            }

            //GET FEATURED MERCHANTS
            public List<Merchants> getFeaturedMerchants (DatabaseHandler db){
                List<Merchants> merchantsList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + MERCHANTS + " WHERE isfeatured = '1'";
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String merchantID = c.getString(c.getColumnIndex("merchantid"));
                        String merchantName = c.getString(c.getColumnIndex("merchantname"));
                        String merchantType = c.getString(c.getColumnIndex("merchanttype"));
                        String merchantGroup = c.getString(c.getColumnIndex("merchantgroup"));
                        String merchantStatus = c.getString(c.getColumnIndex("merchantstatus"));
                        String streetAddress = c.getString(c.getColumnIndex("streetaddress"));
                        String city = c.getString(c.getColumnIndex("city"));
                        String province = c.getString(c.getColumnIndex("province"));
                        String zip = c.getString(c.getColumnIndex("zip"));
                        String country = c.getString(c.getColumnIndex("country"));
                        String longitude = c.getString(c.getColumnIndex("longitude"));
                        String latitude = c.getString(c.getColumnIndex("latitude"));
                        String representative = c.getString(c.getColumnIndex("representative"));
                        String authorisedEmailAddress = c.getString(c.getColumnIndex("email"));
                        String authorisedTelNo = c.getString(c.getColumnIndex("telno"));
                        String authorisedMobileNo = c.getString(c.getColumnIndex("mobileno"));
                        String fax = c.getString(c.getColumnIndex("fax"));
                        String natureOfBusiness = c.getString(c.getColumnIndex("natureofbusiness"));
                        String TIN = c.getString(c.getColumnIndex("tin"));
                        String organizationType = c.getString(c.getColumnIndex("organizationtype"));
                        String noOfBranches = c.getString(c.getColumnIndex("noofbranches"));
                        String merchantLogo = c.getString(c.getColumnIndex("merchantlogo"));
                        String isFeatured = c.getString(c.getColumnIndex("isfeatured"));
                        String featureAddsPath = c.getString(c.getColumnIndex("featuredaddspath"));
                        String OrganizationLogo = c.getString(c.getColumnIndex("OrganizationLogo"));

                        merchantsList.add(new Merchants(merchantID, merchantName, merchantType, merchantGroup, merchantStatus, streetAddress, city, province, zip, country, longitude, latitude, representative, authorisedEmailAddress, authorisedTelNo, authorisedMobileNo, fax, natureOfBusiness, TIN, organizationType, noOfBranches, merchantLogo, isFeatured, featureAddsPath, OrganizationLogo));
                    }
                }
                c.close();
                db.close();

                return merchantsList;
            }

            //GET MERCHANTS BY TYPE
            public List<Merchants> getMerchantsByOrganiationType (DatabaseHandler db, String orgType)
            {
                List<Merchants> merchantsList = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String whereclause = "";
                if (orgType.toUpperCase().equals("OTHERS")) {
                    whereclause = " WHERE organizationtype = '.' OR UPPER(organizationtype) =  UPPER(?) ";
                } else {
                    whereclause = " WHERE UPPER(organizationtype) =  UPPER(?) ";
                }
                String strsql = "SELECT * FROM " + MERCHANTS + whereclause + " ORDER BY organizationtype ASC";
                Cursor c = sql.rawQuery(strsql, new String[]{orgType});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String merchantID = c.getString(c.getColumnIndex("merchantid"));
                        String merchantName = c.getString(c.getColumnIndex("merchantname"));
                        String merchantType = c.getString(c.getColumnIndex("merchanttype"));
                        String merchantGroup = c.getString(c.getColumnIndex("merchantgroup"));
                        String merchantStatus = c.getString(c.getColumnIndex("merchantstatus"));
                        String streetAddress = c.getString(c.getColumnIndex("streetaddress"));
                        String city = c.getString(c.getColumnIndex("city"));
                        String province = c.getString(c.getColumnIndex("province"));
                        String zip = c.getString(c.getColumnIndex("zip"));
                        String country = c.getString(c.getColumnIndex("country"));
                        String longitude = c.getString(c.getColumnIndex("longitude"));
                        String latitude = c.getString(c.getColumnIndex("latitude"));
                        String representative = c.getString(c.getColumnIndex("representative"));
                        String authorisedEmailAddress = c.getString(c.getColumnIndex("email"));
                        String authorisedTelNo = c.getString(c.getColumnIndex("telno"));
                        String authorisedMobileNo = c.getString(c.getColumnIndex("mobileno"));
                        String fax = c.getString(c.getColumnIndex("fax"));
                        String natureOfBusiness = c.getString(c.getColumnIndex("natureofbusiness"));
                        String TIN = c.getString(c.getColumnIndex("tin"));
                        String organizationType = c.getString(c.getColumnIndex("organizationtype"));
                        String noOfBranches = c.getString(c.getColumnIndex("noofbranches"));
                        String merchantLogo = c.getString(c.getColumnIndex("merchantlogo"));
                        String isFeatured = c.getString(c.getColumnIndex("isfeatured"));
                        String featureAddsPath = c.getString(c.getColumnIndex("featuredaddspath"));
                        String OrganizationLogo = c.getString(c.getColumnIndex("OrganizationLogo"));

                        merchantsList.add(new Merchants(merchantID, merchantName, merchantType, merchantGroup, merchantStatus, streetAddress, city, province, zip, country, longitude, latitude, representative, authorisedEmailAddress, authorisedTelNo, authorisedMobileNo, fax, natureOfBusiness, TIN, organizationType, noOfBranches, merchantLogo, isFeatured, featureAddsPath, OrganizationLogo));
                    }
                }
                c.close();
                db.close();

                return merchantsList;
            }

            public void insertParamountYearModel (DatabaseHandler db, String year){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("YearModel", year);

                sql.insert(PARAMOUNT_YEAR_MODEL, null, values);
            }

            public List<String> getYearModel (DatabaseHandler db){
                List<String> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + PARAMOUNT_YEAR_MODEL + " ORDER BY YearModel DESC ", null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String yearModel = c.getString(c.getColumnIndex("YearModel"));

                        arrList.add(yearModel);
                    }
                }
                return arrList;
            }

            //SKYCABLE
            public void insertSkycablePpvCatalogs (DatabaseHandler db, SkycablePPV skycablePPV){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("AddedBy", skycablePPV.getAddedBy());
                values.put("Currency", skycablePPV.getCurrency());
                values.put("DateTimeIN", skycablePPV.getDateTimeIN());
                values.put("ShowingDateFrom", skycablePPV.getShowingDateFrom());
                values.put("ShowingDateTo", skycablePPV.getShowingDateTo());
                values.put("DiscountPercentage", skycablePPV.getDiscountPercentage());
                values.put("DiscountedRate", skycablePPV.getDiscountedRate());
                values.put("GrossRate", skycablePPV.getGrossRate());
                values.put("ImageURL", skycablePPV.getImageURL());
                values.put("PPVDescription", skycablePPV.getPPVDescription());
                values.put("PPVID", skycablePPV.getPPVID());
                values.put("PPVName", skycablePPV.getPPVName());
                values.put("Status", skycablePPV.getStatus());
                values.put("SubscriptionCount", skycablePPV.getSubscriptionCount());

                sql.insert(SKYCABLE_PPV_CATALOGS, null, values);
            }

            public void insertSkycableAccounts (DatabaseHandler db, SkycableAccounts
            skycableAccounts){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("DateTimeIN", skycableAccounts.getDateTimeIN());
                values.put("SkyCableAccountNo", skycableAccounts.getSkyCableAccountNo());
                values.put("SkyCableAccountName", skycableAccounts.getSkyCableAccountName());
                values.put("MobileNo", skycableAccounts.getMobileNo());
                values.put("Source", skycableAccounts.getSource());
                values.put("Status", skycableAccounts.getStatus());

                sql.insert(SKYCABLE_PPV_ACCOUNTS, null, values);
            }

            //getSkycablePPVHistory
            public void insertSkycablePPVHistory (DatabaseHandler db, SkycablePPVHistory
            skycablePPVHistory){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("DateTimeIN", skycablePPVHistory.getDateTimeIN());
                values.put("DateTimeCompleted", skycablePPVHistory.getDateTimeCompleted());
                values.put("SubscriptionTxnID", skycablePPVHistory.getSubscriptionTxnID());
                values.put("PPVID", skycablePPVHistory.getPPVID());
                values.put("PPVName", skycablePPVHistory.getPPVName());
                values.put("PPVDescription", skycablePPVHistory.getPPVDescription());
                values.put("ImageURL", skycablePPVHistory.getImageURL());
                values.put("ShowingDateFrom", skycablePPVHistory.getShowingDateFrom());
                values.put("ShowingDateTo", skycablePPVHistory.getShowingDateTo());
                values.put("SkyCableAccountNo", skycablePPVHistory.getSkyCableAccountNo());
                values.put("BorrowerID", skycablePPVHistory.getBorrowerID());
                values.put("BorrowerName", skycablePPVHistory.getBorrowerName());
                values.put("CustomerFirstName", skycablePPVHistory.getCustomerFirstName());
                values.put("CustomerLastName", skycablePPVHistory.getCustomerLastName());
                values.put("CustomerMobileNumber", skycablePPVHistory.getCustomerMobileNumber());
                values.put("CustomerEmailAddress", skycablePPVHistory.getCustomerEmailAddress());
                values.put("CustomerAddress", skycablePPVHistory.getCustomerAddress());
                values.put("City", skycablePPVHistory.getCity());
                values.put("TxnMedium", skycablePPVHistory.getTxnMedium());
                values.put("Amount", skycablePPVHistory.getAmount());
                values.put("CustomerServiceCharge", skycablePPVHistory.getCustomerServiceCharge());
                values.put("TotalAmountPaid", skycablePPVHistory.getTotalAmountPaid());
                values.put("GKPaymentReferenceNo", skycablePPVHistory.getGKPaymentReferenceNo());
                values.put("MerchantID", skycablePPVHistory.getMerchantID());
                values.put("Longitude", skycablePPVHistory.getLongitude());
                values.put("Latitude", skycablePPVHistory.getLatitude());
                values.put("ActedBy", skycablePPVHistory.getActedBy());
                values.put("Remarks", skycablePPVHistory.getRemarks());
                values.put("Status", skycablePPVHistory.getStatus());

                sql.insert(SKYCABLE_PPV_HISTORY, null, values);
            }

            //SKY_SOA
            public void insertSkycableSOA (DatabaseHandler db, SkycableSOA skycableSOA){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("DateTimeIN", skycableSOA.getDateTimeIN());
                values.put("BillingID", skycableSOA.getBillingID());
                values.put("SkyCableAccountNo", skycableSOA.getSkycableAccountNo());
                values.put("SkyCableAccountName", skycableSOA.getSkycableAccountName());
                values.put("MobileNo", skycableSOA.getMobileNo());
                values.put("DueFromLastBill", skycableSOA.getDueFromLastBill());
                values.put("CurrentCharges", skycableSOA.getCurrentCharges());
                values.put("TotalCharges", skycableSOA.getTotalCharges());
                values.put("DueDate", skycableSOA.getDueDate());
                values.put("SOAURL", skycableSOA.getSOAURL());
                values.put("UploadedBy", skycableSOA.getUploadedBy());
                values.put("Status", skycableSOA.getStatus());

                sql.insert(SKYCABLE_SOA, null, values);
            }

            //SKYCABLE_REGISTRATIONS
            public void insertSkycableRegistrations (DatabaseHandler db, SkycableRegistration
            skycableRegistration){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("PlanName", skycableRegistration.getPlanName());
                values.put("PlanType", skycableRegistration.getPlanType());
                values.put("PlanImgUrl", skycableRegistration.getPlanImgUrl());
                values.put("PlanStyle", skycableRegistration.getPlanStyle());
                values.put("Description", skycableRegistration.getDescription());
                values.put("DateTimeIN", skycableRegistration.getDateTimeIN());
                values.put("DateTimeCompleted", skycableRegistration.getDateTimeCompleted());
                values.put("RegistrationID", skycableRegistration.getRegistrationID());
                values.put("BorrowerID", skycableRegistration.getBorrowerID());
                values.put("FirstName", skycableRegistration.getFirstName());
                values.put("LastName", skycableRegistration.getLastName());
                values.put("Gender", skycableRegistration.getGender());
                values.put("Birthdate", skycableRegistration.getBirthdate());
                values.put("Address", skycableRegistration.getAddress());
                values.put("City", skycableRegistration.getCity());
                values.put("Longitude", skycableRegistration.getLongitude());
                values.put("Latitude", skycableRegistration.getLatitude());
                values.put("ZIPCode", skycableRegistration.getZIPCode());
                values.put("BillingAddress", skycableRegistration.getBillingAddress());
                values.put("InstallationAddress", skycableRegistration.getInstallationAddress());
                values.put("EmailAddress", skycableRegistration.getEmailAddress());
                values.put("MobileNumber", skycableRegistration.getMobileNumber());
                values.put("TelephoneNumber", skycableRegistration.getTelephoneNumber());
                values.put("PlanID", skycableRegistration.getPlanID());
                values.put("DiscountBase", skycableRegistration.getDiscountBase());
                values.put("DiscountPercentage", skycableRegistration.getDiscountPercentage());
                values.put("InstallationFee", skycableRegistration.getInstallationFee());
                values.put("MonthlyFee", skycableRegistration.getMonthlyFee());
                values.put("InitialCashout", skycableRegistration.getInitialCashout());
                values.put("ServiceCharge", skycableRegistration.getServiceCharge());
                values.put("TotalAmountPaid", skycableRegistration.getTotalAmountPaid());
                values.put("GKPaymentReferenceNo", skycableRegistration.getGKPaymentReferenceNo());
                values.put("AccountNo", skycableRegistration.getAccountNo());
                values.put("MerchantID", skycableRegistration.getMerchantID());
                values.put("Status", skycableRegistration.getStatus());
                values.put("Notes1", skycableRegistration.getNotes1());

                sql.insert(SKYCABLE_REGISTRATIONS, null, values);
            }

            public void insertSkycableRegistrationsForPayment (DatabaseHandler
            db, SkycableRegistration skycableRegistration){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("PlanName", skycableRegistration.getPlanName());
                values.put("PlanType", skycableRegistration.getPlanType());
                values.put("PlanImgUrl", skycableRegistration.getPlanImgUrl());
                values.put("PlanStyle", skycableRegistration.getPlanStyle());
                values.put("Description", skycableRegistration.getDescription());
                values.put("DateTimeIN", skycableRegistration.getDateTimeIN());
                values.put("DateTimeCompleted", skycableRegistration.getDateTimeCompleted());
                values.put("RegistrationID", skycableRegistration.getRegistrationID());
                values.put("BorrowerID", skycableRegistration.getBorrowerID());
                values.put("FirstName", skycableRegistration.getFirstName());
                values.put("LastName", skycableRegistration.getLastName());
                values.put("Gender", skycableRegistration.getGender());
                values.put("Birthdate", skycableRegistration.getBirthdate());
                values.put("Address", skycableRegistration.getAddress());
                values.put("City", skycableRegistration.getCity());
                values.put("Longitude", skycableRegistration.getLongitude());
                values.put("Latitude", skycableRegistration.getLatitude());
                values.put("ZIPCode", skycableRegistration.getZIPCode());
                values.put("BillingAddress", skycableRegistration.getBillingAddress());
                values.put("InstallationAddress", skycableRegistration.getInstallationAddress());
                values.put("EmailAddress", skycableRegistration.getEmailAddress());
                values.put("MobileNumber", skycableRegistration.getMobileNumber());
                values.put("TelephoneNumber", skycableRegistration.getTelephoneNumber());
                values.put("PlanID", skycableRegistration.getPlanID());
                values.put("DiscountBase", skycableRegistration.getDiscountBase());
                values.put("DiscountPercentage", skycableRegistration.getDiscountPercentage());
                values.put("InstallationFee", skycableRegistration.getInstallationFee());
                values.put("MonthlyFee", skycableRegistration.getMonthlyFee());
                values.put("InitialCashout", skycableRegistration.getInitialCashout());
                values.put("ServiceCharge", skycableRegistration.getServiceCharge());
                values.put("TotalAmountPaid", skycableRegistration.getTotalAmountPaid());
                values.put("GKPaymentReferenceNo", skycableRegistration.getGKPaymentReferenceNo());
                values.put("AccountNo", skycableRegistration.getAccountNo());
                values.put("MerchantID", skycableRegistration.getMerchantID());
                values.put("Status", skycableRegistration.getStatus());
                values.put("Notes1", skycableRegistration.getNotes1());

                sql.insert(SKYCABLE_REGISTRATIONS_FORPAYMENT, null, values);
            }

            public void insertSkycableConversations (DatabaseHandler db, SkycableConversation
            skycableConversation){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("DateTimeIN", skycableConversation.getDateTimeIN());
                values.put("ThreadID", skycableConversation.getThreadID());
                values.put("ReplySupportUserID", skycableConversation.getReplySupportUserID());
                values.put("ReplySupportName", skycableConversation.getReplySupportName());
                values.put("ThreadType", skycableConversation.getThreadType());
                values.put("Message", skycableConversation.getMessage());

                sql.insert(SKYCABLE_SUPPORT_CONVERSATIONS, null, values);
            }

            public void insertProjectCoopConversations (DatabaseHandler db, SkycableConversation
            skycableConversation){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put("DateTimeIN", skycableConversation.getDateTimeIN());
                values.put("ThreadID", skycableConversation.getThreadID());
                values.put("ReplySupportUserID", skycableConversation.getReplySupportUserID());
                values.put("ReplySupportName", skycableConversation.getReplySupportName());
                values.put("ThreadType", skycableConversation.getThreadType());
                values.put("Message", skycableConversation.getMessage());

                sql.insert(PROJECT_COOP_SUPPORT_CONVERSATIONS, null, values);
            }

            //============================================

            //PARAMOUNT POLICY TYPE
            public void insertParamountPolicyType (DatabaseHandler db, ParamountPolicyType pmnt){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("PolicyTypeID", pmnt.getPolicyTypeID());
                values.put("PolicyTypeName", pmnt.getPolicyTypeName());

                sql.insert(PARAMOUNT_POLICY_TYPE, null, values);
            }

            //PARAMOUNT LTO MV TYPE
            public void insertParamountLTOMVType (DatabaseHandler db, ParamountCharges pmnt){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("ChargeID", pmnt.getChargeID());
                values.put("PolicyTypeID", pmnt.getPolicyTypeID());
                values.put("PolicyTypeName", pmnt.getPolicyTypeName());
                values.put("VehicleTypeID", pmnt.getVehicleTypeID());
                values.put("ApplicationType", pmnt.getApplicationType());
                values.put("VehicleDescription", pmnt.getVehicleDescription());
                values.put("Amount", pmnt.getAmount());
                values.put("OtherCharges", pmnt.getOtherCharges());
                values.put("Status", pmnt.getStatus());

                sql.insert(PARAMOUNT_CHARGES, null, values);
            }

            public List<ParamountPolicyType> getParamountPolicyType (DatabaseHandler db){
                List<ParamountPolicyType> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + PARAMOUNT_POLICY_TYPE, null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String policyTypeID = c.getString(c.getColumnIndex("PolicyTypeID"));
                        String policyTypeName = c.getString(c.getColumnIndex("PolicyTypeName"));

                        arrList.add(new ParamountPolicyType(policyTypeID, policyTypeName));
                    }
                }
                return arrList;
            }

            public List<ParamountCharges> getParamountLTOMVType (DatabaseHandler db){
                List<ParamountCharges> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + PARAMOUNT_CHARGES, null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String chargeID = c.getString(c.getColumnIndex("ChargeID"));
                        String policyTypeID = c.getString(c.getColumnIndex("PolicyTypeID"));
                        String policyTypeName = c.getString(c.getColumnIndex("PolicyTypeName"));
                        String vehicleTypeID = c.getString(c.getColumnIndex("VehicleTypeID"));
                        String applicationType = c.getString(c.getColumnIndex("ApplicationType"));
                        String vehicleDescription = c.getString(c.getColumnIndex("VehicleDescription"));
                        double amount = c.getDouble(c.getColumnIndex("Amount"));
                        double otherCharges = c.getDouble(c.getColumnIndex("OtherCharges"));
                        String status = c.getString(c.getColumnIndex("Status"));

                        arrList.add(new ParamountCharges(chargeID, policyTypeID, policyTypeName, vehicleTypeID, applicationType, vehicleDescription, amount, otherCharges, status));
                    }
                }
                return arrList;
            }

            public List<ParamountCharges> getParamountLTOMVTypeByID (DatabaseHandler db, String
            policytypeid){
                List<ParamountCharges> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT DISTINCT * FROM " + PARAMOUNT_CHARGES + " WHERE PolicyTypeID=?";
                Cursor c = sql.rawQuery(strsql, new String[]{policytypeid});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String chargeID = c.getString(c.getColumnIndex("ChargeID"));
                        String policyTypeID = c.getString(c.getColumnIndex("PolicyTypeID"));
                        String policyTypeName = c.getString(c.getColumnIndex("PolicyTypeName"));
                        String vehicleTypeID = c.getString(c.getColumnIndex("VehicleTypeID"));
                        String applicationType = c.getString(c.getColumnIndex("ApplicationType"));
                        String vehicleDescription = c.getString(c.getColumnIndex("VehicleDescription"));
                        double amount = c.getDouble(c.getColumnIndex("Amount"));
                        double otherCharges = c.getDouble(c.getColumnIndex("OtherCharges"));
                        String status = c.getString(c.getColumnIndex("Status"));

                        arrList.add(new ParamountCharges(chargeID, policyTypeID, policyTypeName, vehicleTypeID, applicationType, vehicleDescription, amount, otherCharges, status));
                    }
                }
                return arrList;
            }

            public List<String> getVehicleTypesByID (DatabaseHandler db, String policytypeid){
                List<String> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT VehicleDescription FROM " + PARAMOUNT_CHARGES + " WHERE PolicyTypeID=?";
                Cursor c = sql.rawQuery(strsql, new String[]{policytypeid});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String vehicleDescription = c.getString(c.getColumnIndex("VehicleDescription"));

                        arrList.add(vehicleDescription);

                    }
                }
                return arrList;
            }

            public List<String> getVehicleTypes (DatabaseHandler db, String policytypeid){
                List<String> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();

                String strsql = policytypeid.length() > 0 ? "SELECT VehicleDescription FROM " + PARAMOUNT_CHARGES + " WHERE PolicyTypeID=?" : "SELECT VehicleDescription FROM " + PARAMOUNT_CHARGES;

                Cursor c = sql.rawQuery(strsql, policytypeid.length() > 0 ? new String[]{policytypeid} : new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String vehicleDescription = c.getString(c.getColumnIndex("VehicleDescription"));

                        arrList.add(vehicleDescription);

                    }
                }
                return arrList;
            }

            public String getVehicleTypeID (DatabaseHandler db, String description){
                String vehicletypeID = "";
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT VehicleTypeID FROM " + PARAMOUNT_CHARGES + " WHERE VehicleDescription=? LIMIT 1";
                Cursor c = sql.rawQuery(strsql, new String[]{description});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        vehicletypeID = c.getString(c.getColumnIndex("VehicleTypeID"));

                    }
                }
                return vehicletypeID;
            }

            public String getPolicyType (DatabaseHandler db, String name){
                String pname = "";
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT PolicyTypeName FROM " + PARAMOUNT_CHARGES + " WHERE VehicleDescription=? LIMIT 1";
                Cursor c = sql.rawQuery(strsql, new String[]{name});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        pname = c.getString(c.getColumnIndex("PolicyTypeName"));

                    }
                }
                return pname;
            }

            //ADD DATA ON PARAMOUNT_QUEUE
            public void insertParamountQueue (DatabaseHandler db, ParamountQueue paramountQueue){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("RequestID", paramountQueue.getRequestID());
                values.put("ParamountProductID", paramountQueue.getVehicleTypeID());
                values.put("ParamountProductName", paramountQueue.getVehicleDescription());
                values.put("Price", paramountQueue.getPrice());
                values.put("OtherCharges", paramountQueue.getOtherCharges());
                values.put("AmountPaid", paramountQueue.getAmountPaid());
                values.put("Renewal", paramountQueue.getRenewal());
                values.put("PolicyTypeID", paramountQueue.getPolicyTypeID());
                values.put("PolicyType", paramountQueue.getPolicyType());
                values.put("LTOMVType", paramountQueue.getLTOMVType());
                values.put("YearModel", paramountQueue.getYearModel());
                values.put("VehicleMaker", paramountQueue.getVehicleMaker());
                values.put("Series", paramountQueue.getSeries());
                values.put("Color", paramountQueue.getColor());
                values.put("MVFileNumber", paramountQueue.getMVFileNumber());
                values.put("PlateNumber", paramountQueue.getPlateNumber());
                values.put("EngineMotorNumber", paramountQueue.getEngineMotorNumber());
                values.put("Serial", paramountQueue.getSerial());
                values.put("Type", paramountQueue.getType());
                values.put("CorporateName", paramountQueue.getCorporateName());
                values.put("FirstName", paramountQueue.getFirstName());
                values.put("MiddleName", paramountQueue.getMiddleName());
                values.put("LastName", paramountQueue.getLastName());
                values.put("HouseNumber", paramountQueue.getHouseNumber());
                values.put("StreetName", paramountQueue.getStreetName());
                values.put("BuildingName", paramountQueue.getBuildingName());
                values.put("Barangay", paramountQueue.getBarangay());
                values.put("Province", paramountQueue.getProvince());
                values.put("Municipality", paramountQueue.getMunicipality());
                values.put("MobileNumber", paramountQueue.getMobileNumber());
                values.put("TelephoneNo", paramountQueue.getTelephoneNo());
                values.put("Zipcode", paramountQueue.getZipcode());
                values.put("EmailAddress", paramountQueue.getEmailAddress());
                values.put("AuthNo", paramountQueue.getAuthNo());
                values.put("COCNo", paramountQueue.getCOCNo());

                sql.insert(PARAMOUNT_QUEUE, null, values);
            }

            public void insertParamountHistory (DatabaseHandler db, ParamountQueue paramountQueue){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("RequestID", paramountQueue.getRequestID());
                values.put("ParamountProductID", paramountQueue.getVehicleTypeID());
                values.put("ParamountProductName", paramountQueue.getVehicleDescription());
                values.put("Price", paramountQueue.getPrice());
                values.put("OtherCharges", paramountQueue.getOtherCharges());
                values.put("AmountPaid", paramountQueue.getAmountPaid());
                values.put("Renewal", paramountQueue.getRenewal());
                values.put("PolicyTypeID", paramountQueue.getPolicyTypeID());
                values.put("PolicyType", paramountQueue.getPolicyType());
                values.put("LTOMVType", paramountQueue.getLTOMVType());
                values.put("YearModel", paramountQueue.getYearModel());
                values.put("VehicleMaker", paramountQueue.getVehicleMaker());
                values.put("Series", paramountQueue.getSeries());
                values.put("Color", paramountQueue.getColor());
                values.put("MVFileNumber", paramountQueue.getMVFileNumber());
                values.put("PlateNumber", paramountQueue.getPlateNumber());
                values.put("EngineMotorNumber", paramountQueue.getEngineMotorNumber());
                values.put("Serial", paramountQueue.getSerial());
                values.put("Type", paramountQueue.getType());
                values.put("CorporateName", paramountQueue.getCorporateName());
                values.put("FirstName", paramountQueue.getFirstName());
                values.put("MiddleName", paramountQueue.getMiddleName());
                values.put("LastName", paramountQueue.getLastName());
                values.put("HouseNumber", paramountQueue.getHouseNumber());
                values.put("StreetName", paramountQueue.getStreetName());
                values.put("BuildingName", paramountQueue.getBuildingName());
                values.put("Barangay", paramountQueue.getBarangay());
                values.put("Province", paramountQueue.getProvince());
                values.put("Municipality", paramountQueue.getMunicipality());
                values.put("MobileNumber", paramountQueue.getMobileNumber());
                values.put("TelephoneNo", paramountQueue.getTelephoneNo());
                values.put("Zipcode", paramountQueue.getZipcode());
                values.put("EmailAddress", paramountQueue.getEmailAddress());
                values.put("DateTimeCompleted", paramountQueue.getDateTimeCompleted());
                values.put("TransactionNo", paramountQueue.getTransactionNo());
                values.put("RegistrationID", paramountQueue.getRegistrationID());
                values.put("TxnStatus", paramountQueue.getTxnStatus());
                values.put("AuthNo", paramountQueue.getAuthNo());
                values.put("COCNo", paramountQueue.getCOCNo());

                sql.insert(PARAMOUNT_HISTORY, null, values);
            }

            public void insertParamountVouchers (DatabaseHandler db, ParamountVouchers
            paramountVouchers){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("MerchantTransactionNo", paramountVouchers.getMerchantTransactionNo());
                values.put("TransactionNo", paramountVouchers.getTransactionNo());
                values.put("VoucherSeriesNo", paramountVouchers.getVoucherSeriesNo());
                values.put("VoucherCode", paramountVouchers.getVoucherCode());
                values.put("AmountConsumed", paramountVouchers.getAmountConsumed());
                values.put("ProductID", paramountVouchers.getProductID());
                values.put("VoucherDenom", paramountVouchers.getVoucherDenom());
                values.put("Extra3", paramountVouchers.getExtra3());

                sql.insert(PARAMOUNT_PAYMENT_VOUCHERS, null, values);
            }

            public List<ParamountVouchers> getParamountPaymentVouchers (DatabaseHandler db, String
            merchanttxnno){
                List<ParamountVouchers> arrVouchers = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + PARAMOUNT_PAYMENT_VOUCHERS + " WHERE MerchantTransactionNo=?";
                Cursor c = sql.rawQuery(strsql, new String[]{merchanttxnno});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String merchantTransactionNo = c.getString(c.getColumnIndex("MerchantTransactionNo"));
                        String transactionNo = c.getString(c.getColumnIndex("TransactionNo"));
                        String voucherSeriesNo = c.getString(c.getColumnIndex("VoucherSeriesNo"));
                        String voucherCode = c.getString(c.getColumnIndex("VoucherCode"));
                        double amountConsumed = c.getDouble(c.getColumnIndex("AmountConsumed"));
                        String productID = c.getString(c.getColumnIndex("ProductID"));
                        double voucherDenom = c.getDouble(c.getColumnIndex("VoucherDenom"));
                        String extra3 = c.getString(c.getColumnIndex("Extra3"));

                        arrVouchers.add(new ParamountVouchers(merchantTransactionNo, transactionNo, voucherSeriesNo, voucherCode, amountConsumed, productID, voucherDenom, extra3));
                    }
                }

                return arrVouchers;
            }

            //PARAMOUNT_VEHICLE_MAKERS
            public void insertParamountVehicleMaker (DatabaseHandler db, ParamountVehicleSeriesMaker
            paramountVehicleSeriesMaker){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("Maker", paramountVehicleSeriesMaker.getMaker());
                values.put("VehicleID", paramountVehicleSeriesMaker.getVehicleID());
                values.put("VehicleTypeID", paramountVehicleSeriesMaker.getVehicleTypeID());
                values.put("Series", paramountVehicleSeriesMaker.getSeries());

                sql.insert(PARAMOUNT_VEHICLE_MAKERS, null, values);
            }

            public List<ParamountVehicleSeriesMaker> getVehicleMakerSeries (DatabaseHandler
            db, String maker){
                List<ParamountVehicleSeriesMaker> arrMakerSeries = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT DISTINCT VehicleID,Series FROM " + PARAMOUNT_VEHICLE_MAKERS + " WHERE Maker=? ORDER BY Series ASC";
                Cursor c = sql.rawQuery(strsql, new String[]{maker});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String vehicleID = c.getString(c.getColumnIndex("VehicleID"));
                        String series = c.getString(c.getColumnIndex("Series"));

                        arrMakerSeries.add(new ParamountVehicleSeriesMaker(vehicleID, maker, ".", series));
                    }
                }

                return arrMakerSeries;
            }

            public boolean isMakerExist (DatabaseHandler db, String maker){
                List<ParamountVehicleSeriesMaker> arrMakers = new ArrayList<>();
                String mMaker = "";

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT Count(Maker) as CountMaker FROM " + PARAMOUNT_VEHICLE_MAKERS + " WHERE Maker=? LIMIT 1";
                Cursor c = sql.rawQuery(strsql, new String[]{maker});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        mMaker = c.getString(c.getColumnIndex("CountMaker"));

                    }
                }

                return Integer.valueOf(mMaker) > 0;
            }

            public List<ParamountVehicleSeriesMaker> getVehicleMakers (DatabaseHandler db){
                List<ParamountVehicleSeriesMaker> arrMakers = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT DISTINCT * FROM " + PARAMOUNT_VEHICLE_MAKERS + " ORDER BY Maker ASC";
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String vehicleID = c.getString(c.getColumnIndex("VehicleID"));
                        String maker = c.getString(c.getColumnIndex("Maker"));
                        String vehicleTypeID = c.getString(c.getColumnIndex("VehicleTypeID"));
                        String series = c.getString(c.getColumnIndex("Series"));

                        arrMakers.add(new ParamountVehicleSeriesMaker(vehicleID, maker, vehicleTypeID, series));
                    }
                }

                return arrMakers;
            }

            public void updateParamountQueue (DatabaseHandler db, String strsql, String[]whereArgs){
                SQLiteDatabase sql = db.getWritableDatabase();
                //String strsql = "UPDATE " + PARAMOUNT_QUEUE + " SET YearModel=?, VehicleMaker=?, Series=?, Color=? WHERE RequestID=?";
                sql.execSQL(strsql, whereArgs);
            }

//    public void updateParamountQueue(DatabaseHandler db, ParamountQueue paramountQueue) {
//        SQLiteDatabase sql = db.getWritableDatabase();
//        String strsql = "UPDATE " + PARAMOUNT_QUEUE + " SET YearModel=?, VehicleMaker=?, Series=?, Color=? WHERE RequestID=?";
//        sql.execSQL(strsql, new String[]{paramountQueue.getYearModel(), paramountQueue.getVehicleMaker(), paramountQueue.getSeries(), paramountQueue.getColor(), paramountQueue.getRequestID()});
//    }

            public ParamountQueue getParamountQueue (DatabaseHandler db){
                ParamountQueue paramountQueue = null;
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + PARAMOUNT_QUEUE + " LIMIT 1";
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String requestID = c.getString(c.getColumnIndex("RequestID"));
                        String paramountProductID = c.getString(c.getColumnIndex("ParamountProductID"));
                        String paramountProductName = c.getString(c.getColumnIndex("ParamountProductName"));
                        double price = c.getDouble(c.getColumnIndex("Price"));
                        double otherCharges = c.getDouble(c.getColumnIndex("OtherCharges"));
                        double amountPaid = c.getDouble(c.getColumnIndex("AmountPaid"));
                        String renewal = c.getString(c.getColumnIndex("Renewal"));
                        String policyTypeID = c.getString(c.getColumnIndex("PolicyTypeID"));
                        String policyType = c.getString(c.getColumnIndex("PolicyType"));
                        String LTOMVType = c.getString(c.getColumnIndex("LTOMVType"));
                        String yearModel = c.getString(c.getColumnIndex("YearModel"));
                        String vehicleMaker = c.getString(c.getColumnIndex("VehicleMaker"));
                        String series = c.getString(c.getColumnIndex("Series"));
                        String color = c.getString(c.getColumnIndex("Color"));
                        String MVFileNumber = c.getString(c.getColumnIndex("MVFileNumber"));
                        String plateNumber = c.getString(c.getColumnIndex("PlateNumber"));
                        String engineMotorNumber = c.getString(c.getColumnIndex("EngineMotorNumber"));
                        String serial = c.getString(c.getColumnIndex("Serial"));
                        String type = c.getString(c.getColumnIndex("Type"));
                        String corporateName = c.getString(c.getColumnIndex("CorporateName"));
                        String firstName = c.getString(c.getColumnIndex("FirstName"));
                        String middleName = c.getString(c.getColumnIndex("MiddleName"));
                        String lastName = c.getString(c.getColumnIndex("LastName"));
                        String houseNumber = c.getString(c.getColumnIndex("HouseNumber"));
                        String streetName = c.getString(c.getColumnIndex("StreetName"));
                        String buildingName = c.getString(c.getColumnIndex("BuildingName"));
                        String barangay = c.getString(c.getColumnIndex("Barangay"));
                        String province = c.getString(c.getColumnIndex("Province"));
                        String municipality = c.getString(c.getColumnIndex("Municipality"));
                        String mobileNumber = c.getString(c.getColumnIndex("MobileNumber"));
                        String telephoneNo = c.getString(c.getColumnIndex("TelephoneNo"));
                        String emailAddress = c.getString(c.getColumnIndex("EmailAddress"));
                        String zipcode = c.getString(c.getColumnIndex("Zipcode"));
                        String vehicleID = c.getString(c.getColumnIndex("VehicleID"));
                        String authNo = c.getString(c.getColumnIndex("AuthNo"));
                        String COCNo = c.getString(c.getColumnIndex("COCNo"));

                        paramountQueue = new ParamountQueue(requestID,
                                paramountProductID,
                                paramountProductName,
                                price,
                                otherCharges,
                                amountPaid,
                                renewal,
                                policyTypeID,
                                policyType,
                                LTOMVType,
                                yearModel,
                                vehicleMaker,
                                series,
                                color,
                                MVFileNumber,
                                plateNumber,
                                engineMotorNumber,
                                serial,
                                type,
                                corporateName,
                                firstName,
                                middleName,
                                lastName,
                                houseNumber,
                                streetName,
                                buildingName,
                                barangay,
                                province,
                                municipality,
                                mobileNumber,
                                telephoneNo,
                                emailAddress,
                                zipcode,
                                vehicleID,
                                authNo,
                                COCNo);
                    }
                }
                return paramountQueue;
            }

            public List<ParamountQueue> getParamountHistory (DatabaseHandler db){
                List<ParamountQueue> arrhistory = new ArrayList<>();

                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + PARAMOUNT_HISTORY;
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String requestID = c.getString(c.getColumnIndex("RequestID"));
                        String paramountProductID = c.getString(c.getColumnIndex("ParamountProductID"));
                        String paramountProductName = c.getString(c.getColumnIndex("ParamountProductName"));
                        double price = c.getDouble(c.getColumnIndex("Price"));
                        double otherCharges = c.getDouble(c.getColumnIndex("OtherCharges"));
                        double amountPaid = c.getDouble(c.getColumnIndex("AmountPaid"));
                        String renewal = c.getString(c.getColumnIndex("Renewal"));
                        String policyTypeID = c.getString(c.getColumnIndex("PolicyTypeID"));
                        String policyType = c.getString(c.getColumnIndex("PolicyType"));
                        String LTOMVType = c.getString(c.getColumnIndex("LTOMVType"));
                        String yearModel = c.getString(c.getColumnIndex("YearModel"));
                        String vehicleMaker = c.getString(c.getColumnIndex("VehicleMaker"));
                        String series = c.getString(c.getColumnIndex("Series"));
                        String color = c.getString(c.getColumnIndex("Color"));
                        String MVFileNumber = c.getString(c.getColumnIndex("MVFileNumber"));
                        String plateNumber = c.getString(c.getColumnIndex("PlateNumber"));
                        String engineMotorNumber = c.getString(c.getColumnIndex("EngineMotorNumber"));
                        String serial = c.getString(c.getColumnIndex("Serial"));
                        String type = c.getString(c.getColumnIndex("Type"));
                        String corporateName = c.getString(c.getColumnIndex("CorporateName"));
                        String firstName = c.getString(c.getColumnIndex("FirstName"));
                        String middleName = c.getString(c.getColumnIndex("MiddleName"));
                        String lastName = c.getString(c.getColumnIndex("LastName"));
                        String houseNumber = c.getString(c.getColumnIndex("HouseNumber"));
                        String streetName = c.getString(c.getColumnIndex("StreetName"));
                        String buildingName = c.getString(c.getColumnIndex("BuildingName"));
                        String barangay = c.getString(c.getColumnIndex("Barangay"));
                        String province = c.getString(c.getColumnIndex("Province"));
                        String municipality = c.getString(c.getColumnIndex("Municipality"));
                        String mobileNumber = c.getString(c.getColumnIndex("MobileNumber"));
                        String telephoneNo = c.getString(c.getColumnIndex("TelephoneNo"));
                        String emailAddress = c.getString(c.getColumnIndex("EmailAddress"));
                        String zipcode = c.getString(c.getColumnIndex("Zipcode"));

                        String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String transactionNo = c.getString(c.getColumnIndex("TransactionNo"));
                        String registrationID = c.getString(c.getColumnIndex("RegistrationID"));
                        String txnStatus = c.getString(c.getColumnIndex("TxnStatus"));

                        String authNo = c.getString(c.getColumnIndex("AuthNo"));
                        String COCNo = c.getString(c.getColumnIndex("COCNo"));

                        arrhistory.add(new ParamountQueue(requestID,
                                paramountProductID,
                                paramountProductName,
                                price,
                                otherCharges,
                                amountPaid,
                                renewal,
                                policyTypeID,
                                policyType,
                                LTOMVType,
                                yearModel,
                                vehicleMaker,
                                series,
                                color,
                                MVFileNumber,
                                plateNumber,
                                engineMotorNumber,
                                serial,
                                type,
                                corporateName,
                                firstName,
                                middleName,
                                lastName,
                                houseNumber,
                                streetName,
                                buildingName,
                                barangay,
                                province,
                                municipality,
                                mobileNumber,
                                telephoneNo,
                                emailAddress,
                                zipcode,
                                dateTimeCompleted,
                                transactionNo,
                                registrationID,
                                txnStatus,
                                authNo,
                                COCNo));
                    }
                }

                return arrhistory;
            }

            public String getGuarantorID (DatabaseHandler db){
                String guarantorID = "";
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT guarantorid FROM " + ACCOUNTS + " LIMIT 1;";
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        guarantorID = c.getString(c.getColumnIndex("guarantorid"));


                    }
                }
                return guarantorID;
            }

            //payment_channels
            public void insertPaymentChannels (DatabaseHandler db, PaymentChannels paymentChannels){
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("NetworkID", paymentChannels.getNetworkID());
                value.put("NetworkName", paymentChannels.getNetworkName());
                value.put("NetworkType", paymentChannels.getNetworkType());
                value.put("NetworkGroup", paymentChannels.getNetworkGroup());
                value.put("NetworkStatus", paymentChannels.getNetworkStatus());
                value.put("StreetAddress", paymentChannels.getStreetAddress());
                value.put("City", paymentChannels.getCity());
                value.put("Province", paymentChannels.getProvince());
                value.put("Zip", paymentChannels.getZip());
                value.put("Country", paymentChannels.getCountry());
                value.put("Longitude", paymentChannels.getLongitude());
                value.put("Latitude", paymentChannels.getLatitude());
                value.put("Representative", paymentChannels.getRepresentative());
                value.put("AuthorisedEmailAddress", paymentChannels.getAuthorisedEmailAddress());
                value.put("AuthorisedTelNo", paymentChannels.getAuthorisedTelNo());
                value.put("AuthorisedMobileNo", paymentChannels.getAuthorisedMobileNo());
                value.put("Fax", paymentChannels.getFax());
                value.put("NatureOfBusiness", paymentChannels.getNatureOfBusiness());
                value.put("TIN", paymentChannels.getTIN());
                value.put("OrganizationType", paymentChannels.getOrganizationType());
                value.put("NoOfBranches", paymentChannels.getNoOfBranches());
                value.put("DateTimeAdded", paymentChannels.getDateTimeAdded());
                value.put("AddedBy", paymentChannels.getAddedBy());
                value.put("Logo", paymentChannels.getLogo());

                // inserting row
                sql.insert(PAYMENT_CHANNELS, null, value);
            }

            //payment_channels
            public List<PaymentChannels> getPaymentChannels (DatabaseHandler db){
                List<PaymentChannels> paymentChannels = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + PAYMENT_CHANNELS, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String networkID = c.getString(c.getColumnIndex("NetworkID"));
                        String networkName = c.getString(c.getColumnIndex("NetworkName"));
                        String networkType = c.getString(c.getColumnIndex("NetworkType"));
                        String networkGroup = c.getString(c.getColumnIndex("NetworkGroup"));
                        String networkStatus = c.getString(c.getColumnIndex("NetworkStatus"));
                        String streetAddress = c.getString(c.getColumnIndex("StreetAddress"));
                        String city = c.getString(c.getColumnIndex("City"));
                        String province = c.getString(c.getColumnIndex("Province"));
                        String zip = c.getString(c.getColumnIndex("Zip"));
                        String country = c.getString(c.getColumnIndex("Country"));
                        String longitude = c.getString(c.getColumnIndex("Longitude"));
                        String latitude = c.getString(c.getColumnIndex("Latitude"));
                        String representative = c.getString(c.getColumnIndex("Representative"));
                        String authorisedEmailAddress = c.getString(c.getColumnIndex("AuthorisedEmailAddress"));
                        String authorisedTelNo = c.getString(c.getColumnIndex("AuthorisedTelNo"));
                        String authorisedMobileNo = c.getString(c.getColumnIndex("AuthorisedMobileNo"));
                        String fax = c.getString(c.getColumnIndex("Fax"));
                        String natureOfBusiness = c.getString(c.getColumnIndex("NatureOfBusiness"));
                        String TIN = c.getString(c.getColumnIndex("TIN"));
                        String organizationType = c.getString(c.getColumnIndex("OrganizationType"));
                        String noOfBranches = c.getString(c.getColumnIndex("NoOfBranches"));
                        String dateTimeAdded = c.getString(c.getColumnIndex("DateTimeAdded"));
                        String addedBy = c.getString(c.getColumnIndex("AddedBy"));
                        String logo = c.getString(c.getColumnIndex("Logo"));

                        paymentChannels.add(new PaymentChannels(networkID, networkName, networkType, networkGroup, networkStatus, streetAddress, city, province, zip, country, longitude, latitude, representative, authorisedEmailAddress, authorisedTelNo, authorisedMobileNo, fax, natureOfBusiness, TIN, organizationType, noOfBranches, dateTimeAdded, addedBy, logo));
                    }
                }
                c.close();
                db.close();
                return paymentChannels;
            }

            //payment_channels
            public void insertPaymentBranches (DatabaseHandler db, Branches branches,double distance)
            {
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues value = new ContentValues();
                value.put("NetworkID", branches.getNetworkID());
                value.put("OutletID", branches.getOutletID());
                value.put("OutletName", branches.getOutletName());
                value.put("AuthorisedRepresentative", branches.getAuthorisedRepresentative());
                value.put("StreetAddress", branches.getStreetAddress());
                value.put("City", branches.getCity());
                value.put("Province", branches.getProvince());
                value.put("Zip", branches.getZip());
                value.put("Country", branches.getCountry());
                value.put("Longitude", branches.getLongitude());
                value.put("Latitude", branches.getLatitude());
                value.put("AuthorisedEmailAddress", branches.getAuthorisedEmailAddress());
                value.put("AuthorisedTelNo", branches.getAuthorisedTelNo());
                value.put("AuthorisedMobileNo", branches.getAuthorisedMobileNo());
                value.put("Fax", branches.getFax());
                value.put("DateTimeAdded", branches.getDateTimeAdded());
                value.put("DateTimeUpdated", branches.getDateTimeUpdated());
                value.put("Status", branches.getStatus());
                value.put("CalculatedDistance", distance);

                // inserting row
                sql.insert(PAYMENT_BRANCHES, null, value);
            }

            //payment_branches
            public List<Branches> getPaymentBranches (DatabaseHandler db, String networkId){
                List<Branches> branches = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT * FROM " + PAYMENT_BRANCHES + " WHERE NetworkID " + "=?" + " ORDER BY CalculatedDistance ASC";
                Cursor c = sql.rawQuery(strsql, new String[]{networkId});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String networkID = c.getString(c.getColumnIndex("NetworkID"));
                        String outletID = c.getString(c.getColumnIndex("OutletID"));
                        String outletName = c.getString(c.getColumnIndex("OutletName"));
                        String authorisedRepresentative = c.getString(c.getColumnIndex("AuthorisedRepresentative"));
                        String streetAddress = c.getString(c.getColumnIndex("StreetAddress"));
                        String city = c.getString(c.getColumnIndex("City"));
                        String province = c.getString(c.getColumnIndex("Province"));
                        int zip = c.getInt(c.getColumnIndex("Zip"));
                        String country = c.getString(c.getColumnIndex("Country"));
                        String longitude = c.getString(c.getColumnIndex("Longitude"));
                        String latitude = c.getString(c.getColumnIndex("Latitude"));
                        String authorisedEmailAddress = c.getString(c.getColumnIndex("AuthorisedEmailAddress"));
                        String authorisedTelNo = c.getString(c.getColumnIndex("AuthorisedTelNo"));
                        String authorisedMobileNo = c.getString(c.getColumnIndex("AuthorisedMobileNo"));
                        String fax = c.getString(c.getColumnIndex("Fax"));
                        String dateTimeAdded = c.getString(c.getColumnIndex("DateTimeAdded"));
                        String dateTimeUpdated = c.getString(c.getColumnIndex("DateTimeUpdated"));
                        String status = c.getString(c.getColumnIndex("Status"));
                        double calculatedDistance = c.getDouble(c.getColumnIndex("CalculatedDistance"));

                        branches.add(new Branches(networkID, outletID, outletName, authorisedRepresentative, streetAddress, city, province, zip, country, longitude, latitude, authorisedEmailAddress, authorisedTelNo, authorisedMobileNo, fax, dateTimeAdded, dateTimeUpdated, status, calculatedDistance));
                    }
                }
                c.close();
                db.close();
                return branches;
            }

            public boolean isPaymentBranchExist (DatabaseHandler db, String networkId){
                SQLiteDatabase sql = db.getReadableDatabase();
                boolean isExist = false;

                String myquery = "SELECT NetworkID FROM payment_branches WHERE NetworkID =? LIMIT 1";
                Cursor c = sql.rawQuery(myquery, new String[]{networkId});
                if (c.getCount() > 0) {
                    isExist = true;
                }

                c.close();
                db.close();
                return isExist;
            }

            //Virtual Voucher Request
            public void insertVirtualVoucherRequestQueue (DatabaseHandler
            db, V2VirtualVoucherRequestQueue rqst){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("DateTimeRequested", rqst.getDateTimeRequested());
                values.put("DateTimeCompleted", rqst.getDateTimeCompleted());
                values.put("VoucherGenerationID", rqst.getVoucherGenerationID());
                values.put("PaymentTxnNo", rqst.getPaymentTxnNo());
                values.put("PartnerReferenceNo", rqst.getPartnerReferenceNo());
                values.put("BorrowingTxnNo", rqst.getBorrowingTxnNo());
                values.put("ProcessType", rqst.getProcessType());
                values.put("GuarantorID", rqst.getGuarantorID());
                values.put("GuarantorName", rqst.getGuarantorName());
                values.put("GuarantorPrefix", rqst.getGuarantorPrefix());
                values.put("SubGuarantorID", rqst.getSubGuarantorID());
                values.put("SubGuarantorName", rqst.getSubGuarantorName());
                values.put("BorrowerID", rqst.getBorrowerID());
                values.put("BorrowerName", rqst.getBorrowerName());
                values.put("BorrowerMobileNumber", rqst.getBorrowerMobileNumber());
                values.put("TotalAmount", rqst.getTotalAmount());
                values.put("TotalNoOfVouchers", rqst.getTotalNoOfVouchers());
                values.put("XMLDetails", rqst.getXMLDetails());
                values.put("Medium", rqst.getMedium());
                values.put("IMEI", rqst.getIMEI());
                values.put("PartnerNetworkID", rqst.getPartnerNetworkID());
                values.put("PartnerOutletID", rqst.getPartnerOutletID());
                values.put("DateTimePartnerPaid", rqst.getDateTimePartnerPaid());
                values.put("Status", rqst.getStatus());

                sql.insert(VIRTUAL_VOUCHER_REQUEST, null, values);
            }

            public List<V2VirtualVoucherRequestQueue> getVirtualVoucherRequestQueue (DatabaseHandler
            db){
                List<V2VirtualVoucherRequestQueue> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();
                Cursor c = sql.rawQuery("SELECT * FROM " + VIRTUAL_VOUCHER_REQUEST + " ORDER BY DateTimeRequested DESC ", null);
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String dateTimeRequested = c.getString(c.getColumnIndex("DateTimeRequested"));
                        String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String voucherGenerationID = c.getString(c.getColumnIndex("VoucherGenerationID"));
                        String paymentTxnNo = c.getString(c.getColumnIndex("PaymentTxnNo"));
                        String partnerReferenceNo = c.getString(c.getColumnIndex("PartnerReferenceNo"));
                        String borrowingTxnNo = c.getString(c.getColumnIndex("BorrowingTxnNo"));
                        String processType = c.getString(c.getColumnIndex("ProcessType"));
                        String guarantorID = c.getString(c.getColumnIndex("GuarantorID"));
                        String guarantorName = c.getString(c.getColumnIndex("GuarantorName"));
                        String guarantorPrefix = c.getString(c.getColumnIndex("GuarantorPrefix"));
                        String subGuarantorID = c.getString(c.getColumnIndex("SubGuarantorID"));
                        String subGuarantorName = c.getString(c.getColumnIndex("SubGuarantorName"));
                        String borrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                        String borrowerName = c.getString(c.getColumnIndex("BorrowerName"));
                        String borrowerMobileNumber = c.getString(c.getColumnIndex("BorrowerMobileNumber"));
                        double totalAmount = c.getDouble(c.getColumnIndex("TotalAmount"));
                        int totalNoOfVouchers = c.getInt(c.getColumnIndex("TotalNoOfVouchers"));
                        String XMLDetails = c.getString(c.getColumnIndex("XMLDetails"));
                        String medium = c.getString(c.getColumnIndex("Medium"));
                        String IMEI = c.getString(c.getColumnIndex("IMEI"));
                        String partnerNetworkID = c.getString(c.getColumnIndex("PartnerNetworkID"));
                        String partnerOutletID = c.getString(c.getColumnIndex("PartnerOutletID"));
                        String dateTimePartnerPaid = c.getString(c.getColumnIndex("DateTimePartnerPaid"));
                        String status = c.getString(c.getColumnIndex("Status"));

                        arrList.add(new V2VirtualVoucherRequestQueue(dateTimeRequested,
                                dateTimeCompleted,
                                voucherGenerationID,
                                paymentTxnNo,
                                partnerReferenceNo,
                                borrowingTxnNo,
                                processType,
                                guarantorID,
                                guarantorName,
                                guarantorPrefix,
                                subGuarantorID,
                                subGuarantorName,
                                borrowerID,
                                borrowerName,
                                borrowerMobileNumber,
                                totalAmount,
                                totalNoOfVouchers,
                                XMLDetails,
                                medium,
                                IMEI,
                                partnerNetworkID,
                                partnerOutletID,
                                dateTimePartnerPaid,
                                status));
                    }
                }
                return arrList;
            }


            public List<V2VirtualVoucherRequestQueue> getVirtualVoucherRequestQueueByStatus
            (DatabaseHandler db){
                List<V2VirtualVoucherRequestQueue> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();

                Cursor c = sql.rawQuery("SELECT * FROM " + VIRTUAL_VOUCHER_REQUEST + " ORDER BY Status DESC ", null);

                if (c.getCount() > 0) {
                    while (c.moveToNext()) {
                        String dateTimeRequested = c.getString(c.getColumnIndex("DateTimeRequested"));
                        String dateTimeCompleted = c.getString(c.getColumnIndex("DateTimeCompleted"));
                        String voucherGenerationID = c.getString(c.getColumnIndex("VoucherGenerationID"));
                        String paymentTxnNo = c.getString(c.getColumnIndex("PaymentTxnNo"));
                        String partnerReferenceNo = c.getString(c.getColumnIndex("PartnerReferenceNo"));
                        String borrowingTxnNo = c.getString(c.getColumnIndex("BorrowingTxnNo"));
                        String processType = c.getString(c.getColumnIndex("ProcessType"));
                        String guarantorID = c.getString(c.getColumnIndex("GuarantorID"));
                        String guarantorName = c.getString(c.getColumnIndex("GuarantorName"));
                        String guarantorPrefix = c.getString(c.getColumnIndex("GuarantorPrefix"));
                        String subGuarantorID = c.getString(c.getColumnIndex("SubGuarantorID"));
                        String subGuarantorName = c.getString(c.getColumnIndex("SubGuarantorName"));
                        String borrowerID = c.getString(c.getColumnIndex("BorrowerID"));
                        String borrowerName = c.getString(c.getColumnIndex("BorrowerName"));
                        String borrowerMobileNumber = c.getString(c.getColumnIndex("BorrowerMobileNumber"));
                        double totalAmount = c.getDouble(c.getColumnIndex("TotalAmount"));
                        int totalNoOfVouchers = c.getInt(c.getColumnIndex("TotalNoOfVouchers"));
                        String XMLDetails = c.getString(c.getColumnIndex("XMLDetails"));
                        String medium = c.getString(c.getColumnIndex("Medium"));
                        String IMEI = c.getString(c.getColumnIndex("IMEI"));
                        String partnerNetworkID = c.getString(c.getColumnIndex("PartnerNetworkID"));
                        String partnerOutletID = c.getString(c.getColumnIndex("PartnerOutletID"));
                        String dateTimePartnerPaid = c.getString(c.getColumnIndex("DateTimePartnerPaid"));
                        String status = c.getString(c.getColumnIndex("Status"));

                        arrList.add(new V2VirtualVoucherRequestQueue(dateTimeRequested,
                                dateTimeCompleted,
                                voucherGenerationID,
                                paymentTxnNo,
                                partnerReferenceNo,
                                borrowingTxnNo,
                                processType,
                                guarantorID,
                                guarantorName,
                                guarantorPrefix,
                                subGuarantorID,
                                subGuarantorName,
                                borrowerID,
                                borrowerName,
                                borrowerMobileNumber,
                                totalAmount,
                                totalNoOfVouchers,
                                XMLDetails,
                                medium,
                                IMEI,
                                partnerNetworkID,
                                partnerOutletID,
                                dateTimePartnerPaid,
                                status));
                    }
                }
                return arrList;
            }

            public Petron getBorrowerInfo (DatabaseHandler db){
                Petron borrowerinfo = null;
                SQLiteDatabase sql = db.getReadableDatabase();
                String strsql = "SELECT guarantorid,borrowerid,mobile,firstname,middlename,lastname,email FROM " + ACCOUNTS + " LIMIT 1;";
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String guarantorID = c.getString(c.getColumnIndex("guarantorid"));
                        String borrowerID = c.getString(c.getColumnIndex("borrowerid"));
                        String borrowerMobileNo = c.getString(c.getColumnIndex("mobile"));
                        String name = c.getString(c.getColumnIndex("firstname")).concat(" ");
                        name = name.concat(c.getString(c.getColumnIndex("middlename")) != null ? c.getString(c.getColumnIndex("middlename")) : "");
                        String borrowerName = name.concat(" ").concat(c.getString(c.getColumnIndex("lastname")));
                        String borrowerEmailAddress = c.getString(c.getColumnIndex("email"));
                        String userID = c.getString(c.getColumnIndex("mobile"));

                        borrowerinfo = new Petron(guarantorID, borrowerID, borrowerMobileNo, borrowerName, borrowerEmailAddress, userID);
                    }
                }
                return borrowerinfo;
            }

            public void insertPublicSponsorPromos (DatabaseHandler db, PublicSponsorPromos pspromo){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("GuarantorID", pspromo.getGuarantorID());
                values.put("PromoLink", pspromo.getPSPromoImage());
                values.put("Title", pspromo.getPSPromoTitle());
                values.put("Description", pspromo.getPSPromoDescription());


                sql.insert(PUBLIC_SPONSOR_PROMOS, null, values);
            }

            public List<PublicSponsorPromos> getPublicSponsorPromoDB (DatabaseHandler db){
                List<PublicSponsorPromos> arrList = new ArrayList<>();
                SQLiteDatabase sql = db.getReadableDatabase();

                String strsql = "SELECT * FROM " + PUBLIC_SPONSOR_PROMOS;
                Cursor c = sql.rawQuery(strsql, new String[]{});
                if (c.getCount() > 0) {
                    while (c.moveToNext()) {

                        String guarantorID = c.getString(c.getColumnIndex("GuarantorID"));
                        String promolink = c.getString(c.getColumnIndex("PromoLink"));
                        String title = c.getString(c.getColumnIndex("Title"));
                        String desc = c.getString(c.getColumnIndex("Description"));

                        arrList.add(new PublicSponsorPromos(guarantorID, promolink, title, desc));

                    }
                }
                return arrList;
            }

            // ------------- School Mini (STUDENTS) --------------------
            public void insertSchoolMiniStudents (DatabaseHandler db, SchoolMiniStudents data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(SchoolMiniStudentDB.TABLE_STUDENTS, null, SchoolMiniStudentDB.insert(data));
                db.close();
            }

            public ArrayList<SchoolMiniStudents> getSchoolMiniStudents (DatabaseHandler db){
                ArrayList<SchoolMiniStudents> schoolministudentslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniStudentDB.TABLE_STUDENTS, null);
                schoolministudentslist = SchoolMiniStudentDB.getSchoolMiniStudents(cursor);
                db.close();
                return schoolministudentslist;
            }

            public ArrayList<SchoolMiniStudents> getSchoolMiniStudentsByKeyWord (DatabaseHandler
            db, String keyword){
                ArrayList<SchoolMiniStudents> schoolministudentslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();

                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniStudentDB.TABLE_STUDENTS
                        + " WHERE StudentID LIKE '%" + keyword + "%' "
                        + " OR "
                        + " FirstName LIKE '%" + keyword + "%' "
                        + " OR "
                        + " MiddleName LIKE '%" + keyword + "%' "
                        + " OR "
                        + " LastName LIKE '%" + keyword + "%' ", null);

                schoolministudentslist = SchoolMiniStudentDB.getSchoolMiniStudents(cursor);
                db.close();
                return schoolministudentslist;
            }

            // ------------- School Mini (GRADES) --------------------
            public void insertSchoolMiniGrades (DatabaseHandler db, SchoolMiniGrades data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(SchoolMiniGradesDB.TABLE_GRADES, null, SchoolMiniGradesDB.insert(data));
                db.close();
            }
            //GET SCHOOL GRADES
            public List<SchoolMiniGrades> getSchoolMiniGrades (DatabaseHandler db){
                List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniGradesDB.TABLE_GRADES, null);
                schoolminigradeslist = SchoolMiniGradesDB.getSchoolMiniGrades(cursor);
                db.close();
                return schoolminigradeslist;
            }
            //GET SCHOOL GRADES BY SCHOOL YEAR
            public List<SchoolMiniGrades> getSchoolMiniGradesGroupBySchoolYear (DatabaseHandler
            db, String studentid){
                List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniGradesDB.TABLE_GRADES
                        + " WHERE StudentID" + "=?"
                        + " GROUP BY SchoolYear ORDER BY SchoolYear DESC", new String[]{studentid});
                schoolminigradeslist = SchoolMiniGradesDB.getSchoolMiniGrades(cursor);
                db.close();
                return schoolminigradeslist;
            }
            //GET SCHOOL GRADES GROUP BY SEMESTER
            public List<SchoolMiniGrades> getSchoolMiniGradesGroupBySemester (DatabaseHandler
            db, String studentid, String schoolyear){
                List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniGradesDB.TABLE_GRADES
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " GROUP BY UPPER(Semester)", new String[]{studentid, schoolyear});
                schoolminigradeslist = SchoolMiniGradesDB.getSchoolMiniGrades(cursor);
                db.close();
                return schoolminigradeslist;
            }
            //GET SCHOOL GRADES GROUP BY EXAM TERM
            public List<SchoolMiniGrades> getSchoolMiniGradesGroupByExamTerm (DatabaseHandler
            db, String studentid, String schoolyear){
                List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniGradesDB.TABLE_GRADES
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " GROUP BY UPPER(ExamTerm) ORDER BY DateTimeIN ASC", new String[]{studentid, schoolyear});
                schoolminigradeslist = SchoolMiniGradesDB.getSchoolMiniGrades(cursor);

                db.close();
                return schoolminigradeslist;
            }
            //GET SCHOOL GRADES BY SEMESTER GROUP BY EXAM TERM
            public List<SchoolMiniGrades> getSchoolMiniGradesBySemesterGroupByExamTermChild
            (DatabaseHandler db, String studentid, String schoolyear, String semester){
                List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniGradesDB.TABLE_GRADES
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " AND "
                        + " Semester" + "=?"
                        + " GROUP BY UPPER(ExamTerm) ORDER BY DateTimeIN ASC", new String[]{studentid, schoolyear, semester});
                schoolminigradeslist = SchoolMiniGradesDB.getSchoolMiniGrades(cursor);

                db.close();
                return schoolminigradeslist;
            }
            //GET SCHOOL GRADES BY EXAM TERM
            public List<SchoolMiniGrades> getSchoolMiniGradesByExamTermChild (DatabaseHandler
            db, String studentid, String schoolyear, String examterm){
                List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniGradesDB.TABLE_GRADES
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " AND "
                        + " ExamTerm" + "=?"
                        + " ORDER BY ExamTerm ASC", new String[]{studentid, schoolyear, examterm});
                schoolminigradeslist = SchoolMiniGradesDB.getSchoolMiniGrades(cursor);
                db.close();
                return schoolminigradeslist;
            }

            //GET SCHOOL GRADES SEMESTER AND EXAM TERM
            public List<SchoolMiniGrades> getSchoolMiniGradesBySemesterandExamTermChild
            (DatabaseHandler db, String studentid, String schoolyear, String semester, String
            examterm){
                List<SchoolMiniGrades> schoolminigradeslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniGradesDB.TABLE_GRADES
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " AND "
                        + " Semester" + "=?"
                        + " AND "
                        + " ExamTerm" + "=?"
                        + " ORDER BY DateTimeIN ASC", new String[]{studentid, schoolyear, semester, examterm});
                schoolminigradeslist = SchoolMiniGradesDB.getSchoolMiniGrades(cursor);

                db.close();
                return schoolminigradeslist;
            }


            // ------------- School Mini (TuitionFee) --------------------
            public void insertSchoolMiniTuitionFee (DatabaseHandler db, SchoolMiniTuitionFee data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE, null, SchoolMiniTuitionFeeDB.insert(data));
                db.close();
            }

            public List<SchoolMiniTuitionFee> getSchoolMiniTuitionFee (DatabaseHandler db){
                List<SchoolMiniTuitionFee> schoolminituitionfeelist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE, null);
                schoolminituitionfeelist = SchoolMiniTuitionFeeDB.getSchoolMiniTuitionFee(cursor);
                db.close();
                return schoolminituitionfeelist;
            }

            public List<SchoolMiniTuitionFee> getSchoolMiniTuitionFeeGroupBySchoolYear
            (DatabaseHandler db, String studentid){
                List<SchoolMiniTuitionFee> schoolminituitionfeelist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE
                        + " WHERE StudentID" + "=?"
                        + " GROUP BY SchoolYear ORDER BY SchoolYear DESC", new String[]{studentid});
                schoolminituitionfeelist = SchoolMiniTuitionFeeDB.getSchoolMiniTuitionFee(cursor);
                db.close();
                return schoolminituitionfeelist;
            }

            public List<SchoolMiniTuitionFee> getSchoolMiniTuitionFeeGroupByDateTimeIN
            (DatabaseHandler db, String studentid){
                List<SchoolMiniTuitionFee> schoolminituitionfeelist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE
                        + " WHERE StudentID" + "=?"
                        + " GROUP BY strftime('%Y', DateTimeIn)  ORDER BY strftime('%Y', DateTimeIn) DESC", new String[]{studentid});
                schoolminituitionfeelist = SchoolMiniTuitionFeeDB.getSchoolMiniTuitionFee(cursor);
                db.close();
                return schoolminituitionfeelist;
            }

            public List<SchoolMiniTuitionFee> getSchoolMiniTuitionFeeGroupBySemester
            (DatabaseHandler db, String studentid, String schoolyear){
                List<SchoolMiniTuitionFee> schoolminituitionfeelist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " GROUP BY UPPER(Semester)", new String[]{studentid, schoolyear});
                schoolminituitionfeelist = SchoolMiniTuitionFeeDB.getSchoolMiniTuitionFee(cursor);
                db.close();
                return schoolminituitionfeelist;
            }

            public List<SchoolMiniTuitionFee> getSchoolMiniTuitionFeeGroupByExamTerm
            (DatabaseHandler db, String studentid, String schoolyear){
                List<SchoolMiniTuitionFee> schoolminituitionfeelist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " GROUP BY UPPER(ExamTerm) ORDER BY DateTimeIN ASC", new String[]{studentid, schoolyear});
                schoolminituitionfeelist = SchoolMiniTuitionFeeDB.getSchoolMiniTuitionFee(cursor);
                db.close();
                return schoolminituitionfeelist;
            }

            public List<SchoolMiniTuitionFee> getSchoolMiniTuitionFeeChild (DatabaseHandler
            db, String studentid, String schoolyear, String semester){
                List<SchoolMiniTuitionFee> schoolminituitionfeelist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniTuitionFeeDB.TABLE_TUITIONFEE
                        + " WHERE StudentID" + "=?"
                        + " AND "
                        + " SchoolYear" + "=?"
                        + " AND "
                        + " Semester" + "=?"
                        + " ORDER BY Semester ASC", new String[]{studentid, schoolyear, semester});
                schoolminituitionfeelist = SchoolMiniTuitionFeeDB.getSchoolMiniTuitionFee(cursor);
                db.close();
                return schoolminituitionfeelist;
            }


            // ------------- School Mini (BULLETIN) --------------------
            public void insertSchoolMiniBulletin (DatabaseHandler db, GenericBulletin data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(SchoolMiniBulletinDB.TABLE_SCHOOL_BULLETIN, null, SchoolMiniBulletinDB.insert(data));
                db.close();
            }

            public List<GenericBulletin> getSchoolMiniBulletin (DatabaseHandler db){
                List<GenericBulletin> schoolminibulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniBulletinDB.TABLE_SCHOOL_BULLETIN, null);
                schoolminibulletinlist = SchoolMiniBulletinDB.getSchoolMiniBulletin(cursor);
                db.close();
                return schoolminibulletinlist;
            }

            public List<GenericBulletin> getSchoolMiniBulletinGroupByDate (DatabaseHandler db){
                List<GenericBulletin> schoolminibulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniBulletinDB.TABLE_SCHOOL_BULLETIN
                        + " WHERE Status" + "= 'ACTIVE'"
                        + " GROUP BY date(DateTimeIN) ORDER BY DateTimeIN DESC", null);
                schoolminibulletinlist = SchoolMiniBulletinDB.getSchoolMiniBulletin(cursor);
                db.close();
                return schoolminibulletinlist;
            }

            public List<GenericBulletin> getSchoolMiniBulletinGroupByDAY (DatabaseHandler db, String
            datetime){
                List<GenericBulletin> schoolminibulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniBulletinDB.TABLE_SCHOOL_BULLETIN
                        + " WHERE Status" + "= 'ACTIVE'"
                        + " AND "
                        + " strftime('%Y-%m-%d', DateTimeIN) " + "=?"
                        + " ORDER BY DateTimeIN DESC", new String[]{datetime});

                schoolminibulletinlist = SchoolMiniBulletinDB.getSchoolMiniBulletin(cursor);
                db.close();
                return schoolminibulletinlist;
            }

            // ------------- School Mini (CONVERSATION) --------------------
            public void insertSchoolMiniConversation (DatabaseHandler db, SupportConversation data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(SchoolMiniSupportConversationDB.TABLE_SC_SUPPORT_CONVERSATIONS, null, SchoolMiniSupportConversationDB.insert(data));
                db.close();
            }

            public List<SupportConversation> getSchoolConversation (DatabaseHandler db, String
            threadid){
                List<SupportConversation> supportconversationlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniSupportConversationDB.TABLE_SC_SUPPORT_CONVERSATIONS
                                + " WHERE ThreadID" + "=?"
                                + " ORDER BY DateTimeIN ASC"
                        , new String[]{threadid});
                supportconversationlist = SchoolMiniSupportConversationDB.getSupportConversation(cursor);
                db.close();
                return supportconversationlist;
            }

            // ------------- School Mini (PAYMENT LOGS) --------------------
            public void insertSchoolMiniPaymentLogs (DatabaseHandler db, SchoolMiniPaymentLogs data)
            {
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS, null, SchoolMiniPaymentLogsDB.insert(data));
                db.close();
            }

            public List<SchoolMiniPaymentLogs> getSchoolMiniPaymentLogs (DatabaseHandler db, String
            soaid){
                List<SchoolMiniPaymentLogs> schoolMiniPaymentLogsList = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniPaymentLogsDB.TABLE_SC_PAYMENTLOGS
                                + " WHERE SOAID " + "=?"
                                + " ORDER BY DateTimeIN DESC"
                        , new String[]{soaid});
                schoolMiniPaymentLogsList = SchoolMiniPaymentLogsDB.getSchoolMiniPaymentLogs(cursor);
                db.close();
                return schoolMiniPaymentLogsList;
            }

            public GKService getGkServicesData (DatabaseHandler db, String serviceCode){

                GKService gkService = null;
                SQLiteDatabase sql = db.getReadableDatabase();

                String strsql = "SELECT * FROM " + GK_SERVICES + " WHERE ServiceCode=? LIMIT 1";
                Cursor cursor = sql.rawQuery(strsql, new String[]{serviceCode});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {

                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                        String Type = cursor.getString(cursor.getColumnIndex("Type"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                        String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                        String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                        String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                        String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String Category = cursor.getString(cursor.getColumnIndex("Category"));
                        String Color = cursor.getString(cursor.getColumnIndex("Color"));
                        int Rank = cursor.getInt(cursor.getColumnIndex("Rank"));

                        gkService = new GKService(ID, ServiceCode, ServiceName, Type, Status, Logo, MerchantID, GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,Rank);

                    }
                }

                return gkService;
            }


            // ------------- School Mini (DTR) --------------------
            public void insertSchoolMiniDtr (DatabaseHandler db, SchoolMiniDtr data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(SchoolMiniDtrDB.TABLE_SCHOOL_DTR, null, SchoolMiniDtrDB.insert(data));
                db.close();
            }

            public List<SchoolMiniDtr> getSchoolMiniDtr (DatabaseHandler db){
                List<SchoolMiniDtr> list = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniDtrDB.TABLE_SCHOOL_DTR, null);
                list = SchoolMiniDtrDB.getSchoolMiniDtr(cursor);
                db.close();
                return list;
            }

            public List<SchoolMiniDtr> getSchoolMiniDtrGroupByDate (DatabaseHandler db){
                List<SchoolMiniDtr> list = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniDtrDB.TABLE_SCHOOL_DTR
                        + " GROUP BY date(DateTimeIN) ORDER BY DateTimeIN DESC", null);
                list = SchoolMiniDtrDB.getSchoolMiniDtr(cursor);
                db.close();
                return list;
            }

            public List<SchoolMiniDtr> getSchoolMiniDtrGroupByDAY (DatabaseHandler db, String
            datetime){
                List<SchoolMiniDtr> list = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + SchoolMiniDtrDB.TABLE_SCHOOL_DTR
                        + " WHERE strftime('%Y-%m-%d', DateTimeIN) =?"
                        + " ORDER BY DateTimeIN DESC", new String[]{datetime});
                list = SchoolMiniDtrDB.getSchoolMiniDtr(cursor);
                db.close();
                return list;
            }


            //-------------------------------------VOTES ------------------------------------------

            //----------------- VOTE POST EVENT------------------
            public void insertVotesPostEvent (DatabaseHandler db, VotesPostEvent data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(VotesPostEventDB.TABLE_VOTESEVENTS, null, VotesPostEventDB.insert(data));
                db.close();
            }

            public List<VotesPostEvent> getVotesPostEvent (DatabaseHandler db){
                List<VotesPostEvent> votesposteventlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + VotesPostEventDB.TABLE_VOTESEVENTS +
                                " ORDER BY DateTimeIN DESC",
                        null);
                votesposteventlist = VotesPostEventDB.getPostVotesEvent(cursor);
                db.close();
                return votesposteventlist;
            }

            public List<VotesPostEvent> getVotesPostEventByKeyWord (DatabaseHandler db, String
            keyword){
                List<VotesPostEvent> votesposteventlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();

                Cursor cursor = sql.rawQuery("SELECT * FROM " + VotesPostEventDB.TABLE_VOTESEVENTS
                                + " WHERE EventName LIKE '%" + keyword + "%' "
                                + " ORDER BY DateTimeIN DESC",
                        null);

                votesposteventlist = VotesPostEventDB.getPostVotesEvent(cursor);
                db.close();
                return votesposteventlist;
            }

            //----------------- PARTICIPANTS ------------------
            public void insertVotesParticipants (DatabaseHandler db, VotesParticipants data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(VotesParticipantsDB.TABLE_PARTICIPANTS, null, VotesParticipantsDB.insert(data));
                db.close();
            }

            public List<VotesParticipants> getVotesParticipants (DatabaseHandler db){
                List<VotesParticipants> getparticipantslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + VotesParticipantsDB.TABLE_PARTICIPANTS
                                + " WHERE ParticipantNumber" + " > 0"
                                + " ORDER BY ParticipantNumber ASC "
                        , null);
                getparticipantslist = VotesParticipantsDB.getParticipants(cursor);
                db.close();
                return getparticipantslist;
            }

            public List<VotesParticipants> getVotesParticipantsByVotes (DatabaseHandler db){
                List<VotesParticipants> getparticipantslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + VotesParticipantsDB.TABLE_PARTICIPANTS
                                + " WHERE ParticipantNumber" + " > 0"
                                + " ORDER BY CurrentNoVote DESC "
                        , null);
                getparticipantslist = VotesParticipantsDB.getParticipants(cursor);
                db.close();
                return getparticipantslist;
            }

            public List<VotesParticipants> getDonateParticipant (DatabaseHandler db){
                List<VotesParticipants> getparticipantslist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + VotesParticipantsDB.TABLE_PARTICIPANTS
                                + " WHERE ParticipantNumber" + " = 0"
                                + " ORDER BY ParticipantNumber ASC "
                                + " LIMIT 1"
                        , null);
                getparticipantslist = VotesParticipantsDB.getParticipants(cursor);
                db.close();
                return getparticipantslist;
            }

            //-----------------------------------FAIR CHILD------------------------------------------
            public void insertFairChildMembers (DatabaseHandler db, FairChildMembers data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(FairChildMembersDB.TABLE_FAIRCHILDMEMBERS, null, FairChildMembersDB.insert(data));
                db.close();
            }

            public List<FairChildMembers> getFairChildMembers (DatabaseHandler db){
                List<FairChildMembers> fairChildMembersList = new ArrayList<>();

                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + FairChildMembersDB.TABLE_FAIRCHILDMEMBERS +
                                " ORDER BY ParticipantID DESC",
                        null);
                fairChildMembersList = FairChildMembersDB.getFairChildMembers(cursor);

                db.close();
                return fairChildMembersList;
            }

            public List<FairChildMembers> getFairChildBoardOfDirectors (DatabaseHandler db){
                List<FairChildMembers> boardofdirectorslist = new ArrayList<>();

                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + FairChildMembersDB.TABLE_FAIRCHILDMEMBERS +
                        " WHERE " + FairChildMembersDB.KEY_POSITION + " = 'BOARD OF DIRECTORS' " + " ORDER BY " +
                        FairChildMembersDB.KEY_PARTICIPANTID + " ASC", null);
                boardofdirectorslist = FairChildMembersDB.getFairChildMembers(cursor);

                db.close();
                return boardofdirectorslist;
            }

            public List<FairChildMembers> getFairChildAuditCom (DatabaseHandler db){
                List<FairChildMembers> auditlist = new ArrayList<>();

                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + FairChildMembersDB.TABLE_FAIRCHILDMEMBERS +
                        " WHERE " + FairChildMembersDB.KEY_POSITION + " = 'AUDIT COMMITTEE' " + " ORDER BY " +
                        FairChildMembersDB.KEY_PARTICIPANTID + " ASC", null);
                auditlist = FairChildMembersDB.getFairChildMembers(cursor);

                db.close();
                return auditlist;
            }

            public List<FairChildMembers> getFairChildElectionCom (DatabaseHandler db){
                List<FairChildMembers> electionlist = new ArrayList<>();

                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + FairChildMembersDB.TABLE_FAIRCHILDMEMBERS +
                        " WHERE " + FairChildMembersDB.KEY_POSITION + " = 'ELECTION COMMITTEE' " + " ORDER BY " +
                        FairChildMembersDB.KEY_PARTICIPANTID + " ASC", null);
                electionlist = FairChildMembersDB.getFairChildMembers(cursor);

                db.close();
                return electionlist;
            }

            //DROPOFF PAYMENTREQUEST PENDING
            public void insertPaymentRequestPending (DatabaseHandler db, PaymentRequest data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(PaymentRequestPendingDB.TABLE_NAME, null, PaymentRequestPendingDB.insert(data));
            }

            public List<PaymentRequest> getPaymentRequestPending (DatabaseHandler db){
                List<PaymentRequest> pendingrequest;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + PaymentRequestPendingDB.TABLE_NAME, null);
                pendingrequest = PaymentRequestPendingDB.getPaymentRequestPending(cursor);
                return pendingrequest;
            }

            public List<PaymentRequest> getPaymentRequestPendingDetails (DatabaseHandler db, String
            ordertxnid){
                List<PaymentRequest> prpending;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + PaymentRequestPendingDB.TABLE_NAME +
                        " WHERE " + PaymentRequestPendingDB.OrderTxnID + "=?", new String[]{ordertxnid});

                prpending = PaymentRequestPendingDB.getPaymentRequestPending(cursor);
                return prpending;
            }

            //DROPOFF PAYMENTREQUEST COMPLETED
            public void insertPaymentRequestCompleted (DatabaseHandler db, PaymentRequest data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(PaymentRequestCompletedDB.TABLE_NAME, null, PaymentRequestCompletedDB.insert(data));
            }

            public List<PaymentRequest> getPaymentRequestCompleted (DatabaseHandler db){
                List<PaymentRequest> completedrequest;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + PaymentRequestCompletedDB.TABLE_NAME, null);
                completedrequest = PaymentRequestCompletedDB.getPaymentRequestCompleted(cursor);
                return completedrequest;
            }

            public List<PaymentRequest> getPaymentRequestCompletedDetails (DatabaseHandler
            db, String ordertxnid){
                List<PaymentRequest> prcompleted;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + PaymentRequestCompletedDB.TABLE_NAME +
                        " WHERE " + PaymentRequestCompletedDB.OrderTxnID + "=?", new String[]{ordertxnid});
                prcompleted = PaymentRequestCompletedDB.getPaymentRequestCompleted(cursor);
                return prcompleted;
            }

            //MDP BULLETIN
            public void insertMDPBulletin (DatabaseHandler db, MDPBulletin data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(MDPBulletinDB.TABLE_NAME, null, MDPBulletinDB.insert(data));
            }

            public List<MDPBulletin> getMDPBulletin (DatabaseHandler db){
                List<MDPBulletin> mdpbulletin;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + MDPBulletinDB.TABLE_NAME, null);
                mdpbulletin = MDPBulletinDB.getMDPBulletin(cursor);
                return mdpbulletin;
            }

            public List<MDPBulletin> getMDPBulletinGroupByDate (DatabaseHandler db){
                List<MDPBulletin> mdpbulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + MDPBulletinDB.TABLE_NAME
                        + " WHERE Status" + "= 'ACTIVE'"
                        + " GROUP BY date(DateTimeIN) ORDER BY DateTimeIN DESC", null);
                mdpbulletinlist = MDPBulletinDB.getMDPBulletin(cursor);
                db.close();
                return mdpbulletinlist;
            }

            public List<MDPBulletin> getMDPBulletinGroupByDay (DatabaseHandler db, String datetime){
                List<MDPBulletin> mdpbulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + MDPBulletinDB.TABLE_NAME
                        + " WHERE Status" + "= 'ACTIVE'"
                        + " AND "
                        + " strftime('%Y-%m-%d', DateTimeIN) " + "=?"
                        + " ORDER BY DateTimeIN DESC", new String[]{datetime});

                mdpbulletinlist = MDPBulletinDB.getMDPBulletin(cursor);
                db.close();
                return mdpbulletinlist;
            }

            //MDPSUPPORTCONVERSATION
            public void insertMDPSupportConversation (DatabaseHandler db, SupportConversation data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(MDPSupportConversationDB.TABLE_NAME, null, MDPSupportConversationDB.insert(data));
                db.close();
            }

            public List<SupportConversation> getMDPSupportConversation (DatabaseHandler db, String
            threadid){
                List<SupportConversation> supportconversationlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + MDPSupportConversationDB.TABLE_NAME
                                + " WHERE ThreadID" + "=?"
                                + " ORDER BY DateTimeIN ASC"
                        , new String[]{threadid});
                supportconversationlist = MDPSupportConversationDB.getMDPSupportConversation(cursor);
                db.close();
                return supportconversationlist;
            }

            //DROP OFF MERCHANTS
            public void insertDropOffMerchants (DatabaseHandler db, DropOffMerchants data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(DropOffMerchantsDB.TABLE_NAME, null, DropOffMerchantsDB.insert(data));
            }

            public List<DropOffMerchants> getDropOffMerchants (DatabaseHandler db){
                List<DropOffMerchants> dropoffmerchants;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + DropOffMerchantsDB.TABLE_NAME, null);
                dropoffmerchants = DropOffMerchantsDB.getDropOffMerchants(cursor);
                return dropoffmerchants;
            }

            //PUBLIC SPONSOR
            public void insertPublicSponsors (DatabaseHandler db, PublicSponsor data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(PublicSponsorDBHelper.TABLE_NAME, null, PublicSponsorDBHelper.insert(data));
            }

            public List<PublicSponsor> getPublicSponsors (DatabaseHandler db){
                List<PublicSponsor> publicsponsor;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + PublicSponsorDBHelper.TABLE_NAME, null);
                publicsponsor = PublicSponsorDBHelper.getPublicSponsors(cursor);
                return publicsponsor;
            }

            //FREESMS
            public void insertFreeSMSHistory (DatabaseHandler db, FreeSMSHistory data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(FreeSMSHistoryDBHelper.TABLE_NAME, null, FreeSMSHistoryDBHelper.insert(data));
            }

            public List<FreeSMSHistory> getFreeSMSHistory (DatabaseHandler db){
                List<FreeSMSHistory> freesmshistory;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + FreeSMSHistoryDBHelper.TABLE_NAME, null);
                freesmshistory = FreeSMSHistoryDBHelper.getFreeSMSHistory(cursor);
                return freesmshistory;
            }

            //GKNegosyoDistributor
            public void insertDistributor (DatabaseHandler db, Distributor data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(GKNegosyoDistributorsDBHelper.TABLE_NAME, null, GKNegosyoDistributorsDBHelper.insert(data));
            }

            public List<Distributor> getDistributors (DatabaseHandler db, String keyword){
                List<Distributor> distributors;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKNegosyoDistributorsDBHelper.TABLE_NAME + " " +
                        "WHERE " + GKNegosyoDistributorsDBHelper.DistributorID + " LIKE '%" + keyword + "%' " +
                        "OR " + GKNegosyoDistributorsDBHelper.FirstName + " LIKE '%" + keyword + "%' " +
                        "OR " + GKNegosyoDistributorsDBHelper.LastName + " LIKE '%" + keyword + "%' ", null);
                distributors = GKNegosyoDistributorsDBHelper.getDistributors(cursor);
                return distributors;
            }


            // COOP ASSISTANT
            public void insertCoopAssistantBulletin (DatabaseHandler db, GenericBulletin data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(CoopMiniBulletinDB.TABLE_COOP_BULLETIN, null, CoopMiniBulletinDB.insert(data));
                db.close();
            }

            public List<GenericBulletin> getCoopAssistantBulletin (DatabaseHandler db){
                List<GenericBulletin> bulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + CoopMiniBulletinDB.TABLE_COOP_BULLETIN, null);
                bulletinlist = CoopMiniBulletinDB.getCoopMiniBulletin(cursor);
                db.close();
                return bulletinlist;
            }

            //COOP BULLETIN
            public List<GenericBulletin> getCoopAssistantMiniBulletinGroupByDate (DatabaseHandler db)
            {
                List<GenericBulletin> bulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + CoopMiniBulletinDB.TABLE_COOP_BULLETIN
                        + " WHERE Status" + "= 'ACTIVE'"
                        + " GROUP BY date(DateTimeIN) ORDER BY DateTimeIN DESC", null);
                bulletinlist = CoopMiniBulletinDB.getCoopMiniBulletin(cursor);
                db.close();
                return bulletinlist;
            }

            public List<GenericBulletin> getCoopAssistantMiniBulletinGroupByDatePublic
            (DatabaseHandler db){
                List<GenericBulletin> bulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + CoopMiniBulletinDB.TABLE_COOP_BULLETIN
                        + " WHERE Status" + "= 'ACTIVE' AND isPrivate = 0"
                        + " GROUP BY date(DateTimeIN) ORDER BY DateTimeIN DESC", null);
                bulletinlist = CoopMiniBulletinDB.getCoopMiniBulletin(cursor);
                db.close();
                return bulletinlist;
            }

            public List<GenericBulletin> getCoopAssistantMiniBulletinGroupByDAY (DatabaseHandler
            db, String datetime){
                List<GenericBulletin> bulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + CoopMiniBulletinDB.TABLE_COOP_BULLETIN
                        + " WHERE Status" + "= 'ACTIVE'"
                        + " AND "
                        + " strftime('%Y-%m-%d', DateTimeIN) " + "=?"
                        + " ORDER BY DateTimeIN DESC", new String[]{datetime});

                bulletinlist = CoopMiniBulletinDB.getCoopMiniBulletin(cursor);
                db.close();
                return bulletinlist;
            }

            public List<GenericBulletin> getCoopAssistantMiniBulletinGroupByDAYPublic
            (DatabaseHandler db, String datetime){
                List<GenericBulletin> bulletinlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + CoopMiniBulletinDB.TABLE_COOP_BULLETIN
                        + " WHERE Status" + "= 'ACTIVE'"
                        + " AND "
                        + " strftime('%Y-%m-%d', DateTimeIN) " + "=?"
                        + " AND isPrivate = 0"
                        + " ORDER BY DateTimeIN DESC", new String[]{datetime});

                bulletinlist = CoopMiniBulletinDB.getCoopMiniBulletin(cursor);
                db.close();
                return bulletinlist;
            }

            //COOP SUPPORT
            public void insertCoopAssistantConversation (DatabaseHandler db, SupportConversation
            data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(CoopMiniSupportConversationDB.TABLE_COOP_SUPPORT_CONVERSATIONS, null, CoopMiniSupportConversationDB.insert(data));
                db.close();
            }

            public List<SupportConversation> getCoopAssistantConversation (DatabaseHandler
            db, String threadid){
                List<SupportConversation> supportconversationlist = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + CoopMiniSupportConversationDB.TABLE_COOP_SUPPORT_CONVERSATIONS
                                + " WHERE ThreadID" + "=?"
                                + " ORDER BY DateTimeIN ASC"
                        , new String[]{threadid});
                supportconversationlist = CoopMiniSupportConversationDB.getSupportConversation(cursor);
                db.close();
                return supportconversationlist;
            }

            //RFID INFORMATION
            public void insertRFIDInfo (DatabaseHandler db, RFIDInfo data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(RFIDDBHelper.TABLE_NAME, null, RFIDDBHelper.insert(data));
            }

            public List<RFIDInfo> getActiveRFID (DatabaseHandler db){
                List<RFIDInfo> rfid;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + RFIDDBHelper.TABLE_NAME + " WHERE " + RFIDDBHelper.Status + " = 'ACTIVE'", null);
                rfid = RFIDDBHelper.getRFID(cursor);
                return rfid;
            }

            public List<RFIDInfo> getInActiveRFID (DatabaseHandler db){
                List<RFIDInfo> rfid;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + RFIDDBHelper.TABLE_NAME + " WHERE " + RFIDDBHelper.Status + " = 'INACTIVE' OR " + RFIDDBHelper.Status + "= 'BLOCKED'", null);
                rfid = RFIDDBHelper.getRFID(cursor);
                return rfid;
            }

            //GK EARN
            public void insertGKEarnConversions (DatabaseHandler db, GKEarnConversionPoints data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS,
                        null, GKEarnConversionsPointsDB.insert(data));
                db.close();
            }

            public List<GKEarnConversionPoints> getGKEarnConversionsPoints (DatabaseHandler db){
                List<GKEarnConversionPoints> list = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKEarnConversionsPointsDB.TABLE_GKEARN_CONVERSIONS
                                + " ORDER BY DateTimeIN DESC"
                        , null);
                list = GKEarnConversionsPointsDB.getCoopGKEarnConversionPoints(cursor);
                db.close();
                return list;
            }

            public boolean serviceExists (String serviceCode){
                SQLiteDatabase db = getWritableDatabase();
                String selectString = "SELECT * FROM " + MainMenuDBHelper.TABLE_NAME + " WHERE " + MainMenuDBHelper.ServiceCode + " =?";

                // Add the String you are searching by here.
                // Put it in an array to avoid an unrecognized token error
                Cursor cursor = db.rawQuery(selectString, new String[]{serviceCode});

                boolean hasObject = false;
                if (cursor.moveToFirst()) {
                    hasObject = true;

                    //region if you had multiple records to check for, use this region.

                    int count = 0;
                    while (cursor.moveToNext()) {
                        count++;
                    }
                }

                cursor.close();
                db.close();
                return hasObject;
            }

            public boolean deleteServiceinMenu (String servicecode)
            {
                SQLiteDatabase db = getWritableDatabase();
                return db.delete(MainMenuDBHelper.TABLE_NAME, MainMenuDBHelper.ServiceCode + "=?", new String[]{servicecode}) > 0;
            }

            //GKSERVICES_MAINMENU
            public void insertGKSERVICES_MAINMENU (DatabaseHandler db, GKService data){
                SQLiteDatabase sql = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("ServiceCode", data.getServiceCode());
                contentValues.put("ServiceName", data.getServiceName());
                contentValues.put("Type", data.getType());
                contentValues.put("Status", data.getStatus());
                contentValues.put("Logo", data.getLogo());
                contentValues.put("MerchantID", data.getMerchantID());
                contentValues.put("GKStoreID", data.getGKStoreID());
                contentValues.put("ServiceType", data.getServiceType());
                contentValues.put("GKStoreStatus", data.getGKStoreStatus());
                contentValues.put("GuarantorID", data.getGuarantorID());
                contentValues.put("Category", data.getCategory());
                contentValues.put("Color", data.getColor());
                contentValues.put("Rank",data.getRank());
                sql.insert(GKSERVICES_MENU, null, contentValues);
            }

            public void truncateGKSERVICES_MAINMENU (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL(MainMenuDBHelper.TRUNCATE_TABLE);
            }

            public void deleteMainMenu (DatabaseHandler db, String serviceCode){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL(MainMenuDBHelper.deleteMainMenuRecord(serviceCode));
            }

            public List<GKService> getGKSERVICES_MAINMENU (DatabaseHandler db){
                List<GKService> services = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GKSERVICES_MENU + " ORDER BY ID  ASC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                        String Type = cursor.getString(cursor.getColumnIndex("Type"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                        String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                        String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                        String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                        String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String Category = cursor.getString(cursor.getColumnIndex("Category"));
                        String Color = cursor.getString(cursor.getColumnIndex("Color"));
                        int Rank = cursor.getInt(cursor.getColumnIndex("Rank"));

                        services.add(new GKService(ID, ServiceCode, ServiceName, Type, Status, Logo,
                                MerchantID, GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,Rank));
                    }
                }
                cursor.close();
                return services;
            }
            public List<GKService> getGKSERVICES_MAINMENUBYKeyWord (DatabaseHandler db, String
            category){
                List<GKService> services = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GK_SERVICES + " WHERE " + MainMenuDBHelper.Category + " =?", new String[]{category});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                        String Type = cursor.getString(cursor.getColumnIndex("Type"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                        String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                        String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                        String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                        String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String Category = cursor.getString(cursor.getColumnIndex("Category"));
                        String Color = cursor.getString(cursor.getColumnIndex("Color"));
                        int Rank = cursor.getInt(cursor.getColumnIndex("Rank"));

                        services.add(new GKService(ID, ServiceCode, ServiceName, Type, Status, Logo,
                                MerchantID, GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,Rank));
                    }
                }
                cursor.close();
                return services;
            }

            public List<GKService> getGKSERVICES_MAINMENUUsingKeyWord (DatabaseHandler db, String
            category, String servicename){
                List<GKService> services = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + GK_SERVICES + " " +
                        "WHERE " + MainMenuDBHelper.Category + " = ? " +
                        "AND " + MainMenuDBHelper.ServiceName + " LIKE '%" + servicename + "%' ", new String[]{category});
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int ID = cursor.getInt(cursor.getColumnIndex("ID"));
                        String ServiceCode = cursor.getString(cursor.getColumnIndex("ServiceCode"));
                        String ServiceName = cursor.getString(cursor.getColumnIndex("ServiceName"));
                        String Type = cursor.getString(cursor.getColumnIndex("Type"));
                        String Status = cursor.getString(cursor.getColumnIndex("Status"));
                        String Logo = cursor.getString(cursor.getColumnIndex("Logo"));
                        String MerchantID = cursor.getString(cursor.getColumnIndex("MerchantID"));
                        String GKStoreID = cursor.getString(cursor.getColumnIndex("GKStoreID"));
                        String ServiceType = cursor.getString(cursor.getColumnIndex("ServiceType"));
                        String GKStoreStatus = cursor.getString(cursor.getColumnIndex("GKStoreStatus"));
                        String GuarantorID = cursor.getString(cursor.getColumnIndex("GuarantorID"));
                        String Category = cursor.getString(cursor.getColumnIndex("Category"));
                        String Color = cursor.getString(cursor.getColumnIndex("Color"));
                        int Rank = cursor.getInt(cursor.getColumnIndex("Rank"));
                        services.add(new GKService(ID, ServiceCode, ServiceName, Type, Status, Logo,
                                MerchantID, GKStoreID, ServiceType, GKStoreStatus, GuarantorID, Category, Color,Rank));
                    }
                }
                cursor.close();
                return services;
            }

            //for local db otp
            //for local DB
            public void insertBorrowersOTP (DatabaseHandler db, BorrowerOtp data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(OtpDBHelper.TABLE_OTP, null, OtpDBHelper.insert(data));
                db.close();
            }

            public List<BorrowerOtp> getBorrowersOTP (DatabaseHandler db){
                List<BorrowerOtp> borrowerOtps = new ArrayList<>();
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + OtpDBHelper.TABLE_OTP, null);
                borrowerOtps = OtpDBHelper.getBorrowersOTP(cursor);
                Logger.debug("errnsasss", "decrypteddata " + new Gson().toJson(borrowerOtps));
                return borrowerOtps;
            }

            public Cursor getData (DatabaseHandler dB, String user_id){
                SQLiteDatabase db = dB.getReadableDatabase();
                return db.rawQuery("SELECT * FROM '" + OtpDBHelper.TABLE_OTP + "' WHERE UserID= ?", new String[]{user_id});
            }

            public Cursor getOTPData (DatabaseHandler dB, String user_id, String otp){
                SQLiteDatabase db = dB.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM '" + OtpDBHelper.TABLE_OTP + "' WHERE '" + OtpDBHelper.KEY_USERID + "'=? AND '" + OtpDBHelper.KEY_OTP + "' = ?;", new String[]{user_id, otp});
                return cursor;
            }

            public void truncateTable (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                String d = "DELETE FROM " + OtpDBHelper.TABLE_OTP + "";
                sql.execSQL(d);
            }
            public void updateOTP (DatabaseHandler db, String usernames, String otp){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("UPDATE " + OtpDBHelper.TABLE_OTP + " SET Otp= '" + otp + "' WHERE UserID = ?;", new String[]{usernames});
            }

            //EGAME
            public void insertEGameProducts (DatabaseHandler db, EGameProducts data){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.insert(EGameProductsDBHelper.TABLE_NAME, null, EGameProductsDBHelper.insert(data));
            }

            public List<EGameProducts> getSearchedEGameProducts (DatabaseHandler db, String
            productcode){
                List<EGameProducts> egameproducts;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + EGameProductsDBHelper.TABLE_NAME +
                        " WHERE " + EGameProductsDBHelper.ProductCode + " LIKE '%" + productcode + "%'", null);
                egameproducts = EGameProductsDBHelper.getEGameProducts(cursor);
                return egameproducts;
            }

            public List<EGameProducts> getEGameProducts (DatabaseHandler db,int limit){
                List<EGameProducts> egameproducts;
                SQLiteDatabase sql = db.getWritableDatabase();
                Cursor cursor = sql.rawQuery("SELECT * FROM " + EGameProductsDBHelper.TABLE_NAME + " LIMIT " + limit, null);
                egameproducts = EGameProductsDBHelper.getEGameProducts(cursor);
                return egameproducts;
            }

            public void deleteEGameProducts (DatabaseHandler db){
                SQLiteDatabase sql = db.getWritableDatabase();
                sql.execSQL("DELETE FROM " + EGameProductsDBHelper.TABLE_NAME + "");
            }
        }
