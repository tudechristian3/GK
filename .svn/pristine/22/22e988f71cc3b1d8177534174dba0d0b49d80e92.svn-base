package com.goodkredit.myapplication.database.schoolmini;

import android.content.ContentValues;
import android.database.Cursor;

import com.goodkredit.myapplication.database.DBUtils;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniPaymentLogs;

import java.util.ArrayList;
import java.util.List;

public class SchoolMiniPaymentLogsDB {
    //Table Name
    public static final String TABLE_SC_PAYMENTLOGS = "table_sc_paymentlogs";

    //Values
    public static final String KEY_ID = "ID";
    public static final String KEY_DATETIMEIN = "DateTimeIN";
    public static final String KEY_DATETIMECOMPLETED = "DateTimeCompleted";
    public static final String KEY_PAYMENTTXNID = "PaymentTxnID";
    public static final String KEY_SOAID = "SOAID";
    public static final String KEY_BILLINGID = "BillingID";
    public static final String KEY_SCHOOLID = "SchoolID";
    public static final String KEY_MERCHANTID = "MerchantID";
    public static final String KEY_MERCHANTNAME = "MerchantName";
    public static final String KEY_BORROWERID = "BorrowerID";
    public static final String KEY_STUDENTID = "StudentID";
    public static final String KEY_STUDENTMOBILENUMBER = "StudentMobileNumber";
    public static final String KEY_STUDENTFIRSTNAME = "StudentFirstName";
    public static final String KEY_STUDENTLASTNAME = "StudentLastName";
    public static final String KEY_PAYMENTDETAILS = "PaymentDetails";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_CUSTOMERSERVICECHARGE = "CustomerServiceCharge";
    public static final String KEY_MERCHANTSERVICECHARGE = "MerchantServiceCharge";
    public static final String KEY_RESELLERDISCOUNT = "ResellerDiscount";
    public static final String KEY_TOTALAMOUNT = "TotalAmount";
    public static final String KEY_TRANSACTIONMEDIUM = "TransactionMedium";
    public static final String KEY_PAYMENTTYPE = "PaymentType";
    public static final String KEY_DATETIMEPAID = "DateTimePaid";
    public static final String KEY_PARTNERNETWORKID = "PartnerNetworkID";
    public static final String KEY_PARTNERNETWORKNAME = "PartnerNetworkName";
    public static final String KEY_PARTNEROUTLETID = "PartnerOutletID";
    public static final String KEY_PARTNEROUTLETNAME = "PartnerOutletName";
    public static final String KEY_PRECONSUMMATIONSESSION = "PreConsummationSession";
    public static final String KEY_STATUS = "Status";
    public static final String KEY_EXTRA1 = "Extra1";
    public static final String KEY_EXTRA2 = "Extra2";
    public static final String KEY_EXTRA3 = "Extra3";
    public static final String KEY_EXTRA4 = "Extra4";
    public static final String KEY_NOTES1 = "Notes1";
    public static final String KEY_NOTES2 = "Notes2";

    public static String CREATE_TABLE_SC_PAYMENTLOGS = DBUtils.CT_IF_NOT_EXISTS + TABLE_SC_PAYMENTLOGS + DBUtils.GENERIC_ID +
            DBUtils.DATA_TYPE_INTEGER + DBUtils.CONSTRAINT_PRIMARY_KEY +
            DBUtils.CONSTRAINT_AUTOINCREMENT + DBUtils.COMMA +
            KEY_ID + DBUtils.DATA_TYPE_INTEGER + DBUtils.COMMA +
            KEY_DATETIMEIN + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMECOMPLETED + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PAYMENTTXNID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SOAID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BILLINGID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_SCHOOLID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MERCHANTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MERCHANTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_BORROWERID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTMOBILENUMBER + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTFIRSTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STUDENTLASTNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PAYMENTDETAILS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_AMOUNT + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_CUSTOMERSERVICECHARGE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_MERCHANTSERVICECHARGE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_RESELLERDISCOUNT + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TOTALAMOUNT + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_TRANSACTIONMEDIUM + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PAYMENTTYPE + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_DATETIMEPAID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PARTNERNETWORKID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PARTNERNETWORKNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PARTNEROUTLETID + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PARTNEROUTLETNAME + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_PRECONSUMMATIONSESSION + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_STATUS + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA2 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA3 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_EXTRA4 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES1 + DBUtils.DATA_TYPE_TEXT + DBUtils.COMMA +
            KEY_NOTES2  + DBUtils.DATA_TYPE_TEXT + DBUtils.GENERIC_STATEMENT_ENDER;

    private String TRUNCATE_TABLE = DBUtils.DELETE + TABLE_SC_PAYMENTLOGS;

    public static ContentValues insert(SchoolMiniPaymentLogs data) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, data.getID());
        contentValues.put(KEY_DATETIMEIN, data.getDateTimeIN());
        contentValues.put(KEY_DATETIMECOMPLETED, data.getDateTimeCompleted());
        contentValues.put(KEY_PAYMENTTXNID, data.getPaymentTxnID());
        contentValues.put(KEY_SOAID, data.getSOAID());
        contentValues.put(KEY_BILLINGID, data.getBillingID());
        contentValues.put(KEY_SCHOOLID, data.getSchoolID());
        contentValues.put(KEY_MERCHANTID, data.getMerchantID());
        contentValues.put(KEY_MERCHANTNAME, data.getMerchantName());
        contentValues.put(KEY_BORROWERID, data.getBorrowerID());
        contentValues.put(KEY_STUDENTID, data.getStudentID());
        contentValues.put(KEY_STUDENTMOBILENUMBER, data.getStudentMobileNumber());
        contentValues.put(KEY_STUDENTFIRSTNAME, data.getStudentFirstName());
        contentValues.put(KEY_STUDENTLASTNAME, data.getStudentLastName());
        contentValues.put(KEY_PAYMENTDETAILS, data.getPaymentDetails());
        contentValues.put(KEY_AMOUNT, data.getAmount());
        contentValues.put(KEY_CUSTOMERSERVICECHARGE, data.getCustomerServiceCharge());
        contentValues.put(KEY_MERCHANTSERVICECHARGE, data.getMerchantServiceCharge());
        contentValues.put(KEY_RESELLERDISCOUNT, data.getResellerDiscount());
        contentValues.put(KEY_TOTALAMOUNT, data.getTotalAmount());
        contentValues.put(KEY_TRANSACTIONMEDIUM, data.getTransactionMedium());
        contentValues.put(KEY_PAYMENTTYPE, data.getPaymentType());
        contentValues.put(KEY_DATETIMEPAID, data.getDateTimePaid());
        contentValues.put(KEY_PARTNERNETWORKID, data.getPartnerNetworkID());
        contentValues.put(KEY_PARTNERNETWORKNAME, data.getPartnerNetworkName());
        contentValues.put(KEY_PARTNEROUTLETID, data.getPartnerOutletID());
        contentValues.put(KEY_PARTNEROUTLETNAME, data.getPartnerOutletName());
        contentValues.put(KEY_PRECONSUMMATIONSESSION, data.getPreConsummationSession());
        contentValues.put(KEY_STATUS, data.getStatus());
        contentValues.put(KEY_EXTRA1, data.getExtra1());
        contentValues.put(KEY_EXTRA2, data.getExtra2());
        contentValues.put(KEY_EXTRA3, data.getExtra3());
        contentValues.put(KEY_EXTRA4, data.getExtra4());
        contentValues.put(KEY_NOTES1, data.getNotes1());
        contentValues.put(KEY_NOTES2, data.getNotes2());

        return contentValues;
    }

    public static List<SchoolMiniPaymentLogs> getSchoolMiniPaymentLogs(Cursor cursor) {
        List<SchoolMiniPaymentLogs>  schoolminigradeslist = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(KEY_ID));
                String datetimein = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEIN));
                String datetimecompleted = cursor.getString(cursor.getColumnIndex(KEY_DATETIMECOMPLETED));
                String paymenttxnid = cursor.getString(cursor.getColumnIndex(KEY_PAYMENTTXNID));
                String soaid = cursor.getString(cursor.getColumnIndex(KEY_SOAID));
                String billingid = cursor.getString(cursor.getColumnIndex(KEY_BILLINGID));
                String schoolid = cursor.getString(cursor.getColumnIndex(KEY_SCHOOLID));
                String merchantid = cursor.getString(cursor.getColumnIndex(KEY_MERCHANTID));
                String merchantname = cursor.getString(cursor.getColumnIndex(KEY_MERCHANTNAME));
                String borrowerid = cursor.getString(cursor.getColumnIndex(KEY_BORROWERID));
                String studentid = cursor.getString(cursor.getColumnIndex(KEY_STUDENTID));
                String studentmobilenumber = cursor.getString(cursor.getColumnIndex(KEY_STUDENTMOBILENUMBER));
                String studentfirstname = cursor.getString(cursor.getColumnIndex(KEY_STUDENTFIRSTNAME));
                String studentlastname = cursor.getString(cursor.getColumnIndex(KEY_STUDENTLASTNAME));
                String paymentdetails = cursor.getString(cursor.getColumnIndex(KEY_PAYMENTDETAILS));
                double amount = cursor.getDouble(cursor.getColumnIndex(KEY_AMOUNT));
                double customerservicecharge = cursor.getDouble(cursor.getColumnIndex(KEY_CUSTOMERSERVICECHARGE));
                double merchantservicecharge = cursor.getDouble(cursor.getColumnIndex(KEY_MERCHANTSERVICECHARGE));
                double resellerdiscount = cursor.getDouble(cursor.getColumnIndex(KEY_RESELLERDISCOUNT));
                double totalamount = cursor.getDouble(cursor.getColumnIndex(KEY_TOTALAMOUNT));
                String transactionmedium = cursor.getString(cursor.getColumnIndex(KEY_TRANSACTIONMEDIUM));
                String paymenttype = cursor.getString(cursor.getColumnIndex(KEY_PAYMENTTYPE));
                String datetimepaid = cursor.getString(cursor.getColumnIndex(KEY_DATETIMEPAID));
                String partnernetworkid = cursor.getString(cursor.getColumnIndex(KEY_PARTNERNETWORKID));
                String partnernetworkname = cursor.getString(cursor.getColumnIndex(KEY_PARTNERNETWORKNAME));
                String partneroutletid = cursor.getString(cursor.getColumnIndex(KEY_PARTNEROUTLETID));
                String partneroutletname = cursor.getString(cursor.getColumnIndex(KEY_PARTNEROUTLETNAME));
                String preconsummationsession = cursor.getString(cursor.getColumnIndex(KEY_PRECONSUMMATIONSESSION));
                String status = cursor.getString(cursor.getColumnIndex(KEY_STATUS));
                String extra1 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA1));
                String extra2 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA2));
                String extra3 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA3));
                String extra4 = cursor.getString(cursor.getColumnIndex(KEY_EXTRA4));
                String notes1 = cursor.getString(cursor.getColumnIndex(KEY_NOTES1));
                String notes2 = cursor.getString(cursor.getColumnIndex(KEY_NOTES2));

                schoolminigradeslist.add(new SchoolMiniPaymentLogs(id,
                        datetimein, datetimecompleted, paymenttxnid, soaid, billingid, schoolid, merchantid,
                        merchantname, borrowerid, studentid, studentmobilenumber, studentfirstname, studentlastname, paymentdetails,
                        amount,customerservicecharge, merchantservicecharge, resellerdiscount,
                        totalamount, transactionmedium, paymenttype, datetimepaid, partnernetworkid, partnernetworkname,
                        partneroutletid, partneroutletname, preconsummationsession,
                        status, extra1, extra2, extra3, extra4, notes1, notes2
                ));

            }
        }
        return schoolminigradeslist;
    }
}
