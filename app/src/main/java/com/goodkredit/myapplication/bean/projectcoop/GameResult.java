package com.goodkredit.myapplication.bean.projectcoop;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameResult {
    @SerializedName("DateTimeIN")
    @Expose
    private String DateTimeIN;
    @SerializedName("DateTimeCompleted")
    @Expose
    private String DateTimeCompleted;
    @SerializedName("TransactionNo")
    @Expose
    private String TransactionNo;
    @SerializedName("WinningNumber")
    @Expose
    private String WinningNumber;
    @SerializedName("WinningCombination")
    @Expose
    private int WinningCombination;
    @SerializedName("CutOverID")
    @Expose
    private String CutOverID;
    @SerializedName("DrawDateTime")
    @Expose
    private String DrawDateTime;
    @SerializedName("JackpotPrice")
    @Expose
    private double JackpotPrice;
    @SerializedName("NextJackpotPrice")
    @Expose
    private double NextJackpotPrice;
    @SerializedName("ThreeHitsPrize")
    @Expose
    private String ThreeHitsPrize;
    @SerializedName("FiveHitsPrize")
    @Expose
    private String FiveHitsPrize;

    public GameResult(String dateTimeIN, String dateTimeCompleted, String transactionNo, String winningNumber,
                      int winningCombination, String cutOverID, String drawDateTime, double jackpotPrice,
                      double nextJackpotPrice, String threeHitsPrize, String fiveHitsPrize) {
        DateTimeIN = dateTimeIN;
        DateTimeCompleted = dateTimeCompleted;
        TransactionNo = transactionNo;
        WinningNumber = winningNumber;
        WinningCombination = winningCombination;
        CutOverID = cutOverID;
        DrawDateTime = drawDateTime;
        JackpotPrice = jackpotPrice;
        NextJackpotPrice = nextJackpotPrice;
        ThreeHitsPrize = threeHitsPrize;
        FiveHitsPrize = fiveHitsPrize;
    }

    public String getDateTimeIN() {
        return DateTimeIN;
    }

    public String getDateTimeCompleted() {
        return DateTimeCompleted;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public String getWinningNumber() {
        return WinningNumber;
    }

    public int getWinningCombination() {
        return WinningCombination;
    }

    public String getCutOverID() {
        return CutOverID;
    }

    public String getDrawDateTime() {
        return DrawDateTime;
    }

    public double getJackpotPrice() {
        return JackpotPrice;
    }

    public double getNextJackpotPrice() {
        return NextJackpotPrice;
    }

    public String getThreeHitsPrize() {
        return ThreeHitsPrize;
    }

    public String getFiveHitsPrize() {
        return FiveHitsPrize;
    }
}
