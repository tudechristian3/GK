package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDtr;
import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniConfigResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniDetailsResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniDtrResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniGradesResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniPaymentLogsResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniPaymentResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniStudentsResponse;
import com.goodkredit.myapplication.responses.schoolmini.SchoolMiniTuitionFeeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SchoolAPI {
    @FormUrlEncoded
    @POST("getStudentAccounts")
    Call<SchoolMiniStudentsResponse> getStudentAccounts(@Field("sessionid") String sessionid,
                                                        @Field("imei") String imei,
                                                        @Field("userid") String userid,
                                                        @Field("borrowerid") String borrowerid,
                                                        @Field("authcode") String authcode,
                                                        @Field("schoolid") String schoolid,
                                                        @Field("mobileno") String mobileno);


    @FormUrlEncoded
    @POST("getSchoolDetails")
    Call<SchoolMiniDetailsResponse> getSchoolDetails(@Field("sessionid") String sessionid,
                                                     @Field("imei") String imei,
                                                     @Field("userid") String userid,
                                                     @Field("borrowerid") String borrowerid,
                                                     @Field("authcode") String authcode,
                                                     @Field("schoolid") String schoolid);

    @FormUrlEncoded
    @POST("getStudentGrades")
    Call<SchoolMiniGradesResponse> getStudentGrades(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("schoolid") String schoolid,
                                                    @Field("studentid") String studentid);

    @FormUrlEncoded
    @POST("getStudentTuitionFee")
    Call<SchoolMiniTuitionFeeResponse> getStudentTuitionFee(@Field("sessionid") String sessionid,
                                                        @Field("imei") String imei,
                                                        @Field("userid") String userid,
                                                        @Field("borrowerid") String borrowerid,
                                                        @Field("authcode") String authcode,
                                                        @Field("schoolid") String schoolid,
                                                        @Field("studentid") String studentid);

    @FormUrlEncoded
    @POST("payStudentTuitionFee")
    Call<SchoolMiniPaymentResponse> payStudentTuitionFee(@Field("sessionid") String sessionid,
                                                         @Field("imei") String imei,
                                                         @Field("userid") String userid,
                                                         @Field("borrowerid") String borrowerid,
                                                         @Field("authcode") String authcode,
                                                         @Field("schoolid") String schoolid,
                                                         @Field("studentid") String studentid,
                                                         @Field("soaid") String soaid,
                                                         @Field("amountpurchase") String amountpurchase,
                                                         @Field("paymenttype") String paymenttype,
                                                         @Field("amountdetails") String amountdetails,
                                                         @Field("remarks") String remarks);

    @FormUrlEncoded
    @POST("processStudentPaymentConsummation")
    Call<GenericResponse> processStudentPaymentConsummation(@Field("sessionid") String sessionid,
                                                         @Field("imei") String imei,
                                                         @Field("userid") String userid,
                                                         @Field("authcode") String authcode,
                                                         @Field("borrowerid") String borrowerid,
                                                         @Field("vouchersessionid") String vouchersessionid,
                                                         @Field("merchantid") String merchantid,
                                                         @Field("soaid") String soaid,
                                                         @Field("studentid") String studentid,
                                                         @Field("schoolid") String schoolid,
                                                         @Field("merchantreferenceno") String merchantreferenceno,
                                                         @Field("amount") String amount,
                                                         @Field("paymenttype") String paymenttype,
                                                         @Field("hasdiscount") int hasdiscount,
                                                         @Field("servicecode") String servicecode,
                                                         @Field("grossamount") String grossamount,
                                                         @Field("remarks") String remarks);

    @FormUrlEncoded
    @POST("cancelSchoolPaymentPartnerRequest")
    Call<GenericResponse> cancelSchoolPaymentPartnerRequest(@Field("sessionid") String sessionid,
                                               @Field("imei") String imei,
                                               @Field("userid") String userid,
                                               @Field("borrowerid") String borrowerid,
                                               @Field("authcode") String authcode,
                                               @Field("schoolid") String schoolid,
                                               @Field("soaid") String soaid,
                                               @Field("billingid") String billingid,
                                               @Field("studentid") String studentid);

    @FormUrlEncoded
    @POST("getSchoolStudentPaymentLogs")
    Call<SchoolMiniPaymentLogsResponse> getSchoolStudentPaymentLogs(@Field("sessionid") String sessionid,
                                                                          @Field("imei") String imei,
                                                                          @Field("userid") String userid,
                                                                          @Field("borrowerid") String borrowerid,
                                                                          @Field("customermobileno") String customermobileno,
                                                                          @Field("authcode") String authcode,
                                                                          @Field("limit") int limit,
                                                                          @Field("year") String year);

    @FormUrlEncoded
    @POST("getMiniAppConfig")
    Call<SchoolMiniConfigResponse> getMiniAppConfig(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("servicecode") String servicecode);

    @FormUrlEncoded
    @POST("checkTuitionFeePaymentStatus")
    Call<SchoolMiniPaymentResponse> checkTuitionFeePaymentStatus(@Field("sessionid") String sessionid,
                                                                 @Field("imei") String imei,
                                                                 @Field("userid") String userid,
                                                                 @Field("borrowerid") String borrowerid,
                                                                 @Field("authcode") String authcode,
                                                                 @Field("schoolid") String schoolid,
                                                                 @Field("studentid") String studentid,
                                                                 @Field("soaid") String soaid);

    @FormUrlEncoded
    @POST("getStudentDtrRecord")
    Call<SchoolMiniDtrResponse> getStudentDtrRecord(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("authcode") String authcode,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("schoolid") String schoolid,
                                                    @Field("studentid") String studentid,
                                                    @Field("limit") int limit,
                                                    @Field("recordmonth") String month,
                                                    @Field("recordyear") String year);
}
