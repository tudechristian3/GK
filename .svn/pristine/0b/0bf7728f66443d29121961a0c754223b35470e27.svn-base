package com.goodkredit.myapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportFAQ {
    @SerializedName("HelpTopic")
    @Expose
    private String HelpTopic;
    @SerializedName("FAQID")
    @Expose
    private String FAQID;
    @SerializedName("Question")
    @Expose
    private String Question;
    @SerializedName("Answer")
    @Expose
    private String Answer;
    @SerializedName("Order")
    @Expose
    private int Order;

    public SupportFAQ(String helpTopic, String FAQID, String question, String answer, int order) {
        HelpTopic = helpTopic;
        this.FAQID = FAQID;
        Question = question;
        Answer = answer;
        Order = order;
    }

    public String getHelpTopic() {
        return HelpTopic;
    }

    public String getFAQID() {
        return FAQID;
    }

    public String getQuestion() {
        return Question;
    }

    public String getAnswer() {
        return Answer;
    }

    public int getOrder() {
        return Order;
    }
}
