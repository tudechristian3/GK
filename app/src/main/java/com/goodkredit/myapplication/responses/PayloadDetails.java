package com.goodkredit.myapplication.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ban_Lenovo on 3/2/2017.
 */

public class PayloadDetails {

    @SerializedName("txnno")
    @Expose
    private String txnno;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("sender")
    @Expose
    private String sender;
    @SerializedName("senderlogo")
    @Expose
    private String senderLogo;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("borrowerid")
    @Expose
    private String borrowerid;
    @SerializedName("threadid")
    @Expose
    private String threadid;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("usermobile")
    @Expose
    private String usermobile;
    @SerializedName("userimei")
    @Expose
    private String userimei;
    @SerializedName("useremail")
    @Expose
    private String useremail;
    @SerializedName("supportdepartment")
    @Expose
    private String supportdepartment;
    @SerializedName("helptopic")
    @Expose
    private String helptopic;
    @SerializedName("supportid")
    @Expose
    private String supportid;
    @SerializedName("supportname")
    @Expose
    private String supportname;

    public String getBorrowerid() {
        return borrowerid;
    }

    public String getThreadid() {
        return threadid;
    }

    public String getUsername() {
        return username;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public String getUserimei() {
        return userimei;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getSupportdepartment() {
        return supportdepartment;
    }

    public String getHelptopic() {
        return helptopic;
    }

    public String getSupportid() {
        return supportid;
    }

    public String getSupportname() {
        return supportname;
    }

    public String getTxnno() {
        return txnno;
    }

    public String getSubject() {
        return subject;
    }

    public String getSender() {
        return sender;
    }

    public String getSenderLogo() {
        return senderLogo;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
