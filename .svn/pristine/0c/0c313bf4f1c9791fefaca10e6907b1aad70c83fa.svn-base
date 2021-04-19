package com.goodkredit.myapplication.adapter;

import java.io.Serializable;

/**
 * Created by user on 10/5/2016.
 */
public class NotificationItem implements Serializable {
    private String senderlogo;
    private String sender;
    private String message;
    private String date;
    private String senderimage;
    private String status;
    private String txnno;

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

    private String subjectType;


    public NotificationItem() {
        super();
    }

    public NotificationItem(String senderlogo, String sender, String message, String date, String senderimage, String status, String txnno,String subjectType) {
        this.senderlogo = senderlogo;
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.senderimage = senderimage;
        this.status = status;
        this.txnno = txnno;
        this.subjectType = subjectType;
    }

    public String getSenderLogo() {
        return senderlogo;
    }

    public void setSenderLogo(String senderlogo) {
        this.senderlogo = senderlogo;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        String strMsg = "";
        if (message.contains("You are given up to")) {
            strMsg = message.replace("[", "").replace("]", "");
        } else {
            strMsg = message;
        }
        return strMsg;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSenderImage() {
        return senderimage;
    }

    public void setSenderImage(String senderimage) {
        this.senderimage = senderimage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTxnNo() {
        return txnno;
    }

    public void setTxnNo(String txnno) {
        this.txnno = txnno;
    }
}
