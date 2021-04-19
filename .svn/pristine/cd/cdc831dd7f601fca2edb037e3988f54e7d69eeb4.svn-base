package com.goodkredit.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GoodApps on 08/02/2018.
 */

public class ProcessGKStoreOrder implements Parcelable {

    private String Borrowerid;
    private String Borrowername;
    private String Merchantid;
    private String Storeid;
    private String Firstname;
    private String Lastname;
    private String Mobileno;
    private String Emailaddress;
    private String Addresss;
    private String Otherdetails;
    private String Orderdetails;
    private String Productid;
    private int Quantity;
    private String vouchersession;

    protected ProcessGKStoreOrder(Parcel in) {
        Borrowerid = in.readString();
        Borrowername = in.readString();
        Merchantid = in.readString();
        Storeid = in.readString();
        Firstname = in.readString();
        Lastname = in.readString();
        Mobileno = in.readString();
        Emailaddress = in.readString();
        Addresss = in.readString();
        Otherdetails = in.readString();
        Orderdetails = in.readString();
        Productid = in.readString();
        Quantity = in.readInt();
        vouchersession = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Borrowerid);
        dest.writeString(Borrowername);
        dest.writeString(Merchantid);
        dest.writeString(Storeid);
        dest.writeString(Firstname);
        dest.writeString(Lastname);
        dest.writeString(Mobileno);
        dest.writeString(Emailaddress);
        dest.writeString(Addresss);
        dest.writeString(Otherdetails);
        dest.writeString(Orderdetails);
        dest.writeString(Productid);
        dest.writeInt(Quantity);
        dest.writeString(vouchersession);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProcessGKStoreOrder> CREATOR = new Creator<ProcessGKStoreOrder>() {
        @Override
        public ProcessGKStoreOrder createFromParcel(Parcel in) {
            return new ProcessGKStoreOrder(in);
        }

        @Override
        public ProcessGKStoreOrder[] newArray(int size) {
            return new ProcessGKStoreOrder[size];
        }
    };

    public String getBorrowerid() {
        return Borrowerid;
    }

    public void setBorrowerid(String borrowerid) {
        Borrowerid = borrowerid;
    }

    public String getBorrowername() {
        return Borrowername;
    }

    public void setBorrowername(String borrowername) {
        Borrowername = borrowername;
    }

    public String getMerchantid() {
        return Merchantid;
    }

    public void setMerchantid(String merchantid) {
        Merchantid = merchantid;
    }

    public String getStoreid() {
        return Storeid;
    }

    public void setStoreid(String storeid) {
        Storeid = storeid;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public void setMobileno(String mobileno) {
        Mobileno = mobileno;
    }

    public String getEmailaddress() {
        return Emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        Emailaddress = emailaddress;
    }

    public String getAddresss() {
        return Addresss;
    }

    public void setAddresss(String addresss) {
        Addresss = addresss;
    }

    public String getOtherdetails() {
        return Otherdetails;
    }

    public void setOtherdetails(String otherdetails) {
        Otherdetails = otherdetails;
    }

    public String getOrderdetails() {
        return Orderdetails;
    }

    public void setOrderdetails(String orderdetails) {
        Orderdetails = orderdetails;
    }

    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getVouchersession() {
        return vouchersession;
    }

    public void setVouchersession(String vouchersession) {
        this.vouchersession = vouchersession;
    }
}
