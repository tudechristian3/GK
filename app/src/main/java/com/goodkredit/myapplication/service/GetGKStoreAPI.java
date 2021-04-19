package com.goodkredit.myapplication.service;


import com.goodkredit.myapplication.responses.GenericResponse;
import com.goodkredit.myapplication.responses.gkstore.CheckGKStoreStatusQueResponse;
import com.goodkredit.myapplication.responses.gkstore.FetchStoreOrderListResponse;
import com.goodkredit.myapplication.responses.gkstore.GetGKStoreCancelOrderResponse;
import com.goodkredit.myapplication.responses.GetServiceChargeResponse;
import com.goodkredit.myapplication.responses.gkstore.GetStoreProductsResponse;
import com.goodkredit.myapplication.responses.gkstore.ProcessGkStoreOrderResponse;
import com.goodkredit.myapplication.responses.whatsnew.BadgeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by GoodApps on 22/01/2018.
 */

public interface GetGKStoreAPI {
    @FormUrlEncoded
    @POST("getStoreProducts")
    Call<GetStoreProductsResponse> getStoreProductsCall(@Field("sessionid") String sessionid,
                                                        @Field("imei") String imei,
                                                        @Field("authcode") String authcode,
                                                        @Field("userid") String userid,
                                                        @Field("borrowerid") String borrowerid,
                                                        @Field("merchantid") String merchantid,
                                                        @Field("storeid") String storeid,
                                                        @Field("limit") int limit
    );

    @FormUrlEncoded
    @POST("processGKStoreOrderV3")
    Call<ProcessGkStoreOrderResponse> processGKStoreOrder(@Field("sessionid") String sessionid,
                                                          @Field("imei") String imei,
                                                          @Field("authcode") String authcode,
                                                          @Field("userid") String userid,
                                                          @Field("borrowerid") String borrowerid,
                                                          @Field("borrowername") String borrowername,
                                                          @Field("merchantid") String merchantid,
                                                          @Field("storeid") String storeid,
                                                          @Field("firstname") String firstname,
                                                          @Field("lastname") String lastname,
                                                          @Field("mobileno") String mobileno,
                                                          @Field("emailaddress") String emailaddress,
                                                          @Field("address") String address,
                                                          @Field("otherdetails") String otherdetails,
                                                          @Field("orderdetails") String orderdetails,
                                                          @Field("vouchersession") String vouchersession,
                                                          @Field("latitude") String latitude,
                                                          @Field("longitude") String longitude,
                                                          @Field("remarks") String remarks,
                                                          @Field("ordertype") String ordertype,
                                                          @Field("servicecode") String servicecode,
                                                          @Field("grossamount") String grossamount,
                                                          @Field("hasdiscount") int hasdiscount,
                                                          @Field("ordertxnno") String ordertxnno
    );

    @FormUrlEncoded
    @POST("fetchStoreOrdersListV2")
    Call<FetchStoreOrderListResponse> getGkStoreHistory(@Field("sessionid") String sessionid,
                                                        @Field("imei") String imei,
                                                        @Field("authcode") String authcode,
                                                        @Field("userid") String userid,
                                                        @Field("borrowerid") String borrowerid,
                                                        @Field("merchantid") String merchantid,
                                                        @Field("storeid") String storeid,
                                                        @Field("limit") int limit,
                                                        @Field("recordyear") String recordyear,
                                                        @Field("recordmonth") String recordmonth
    );

    @FormUrlEncoded
    @POST("getGkStoreCustomerServiceCharge")
    Call<GetServiceChargeResponse> getServiceCharge(@Field("sessionid") String sessionid,
                                                    @Field("imei") String imei,
                                                    @Field("userid") String userid,
                                                    @Field("borrowerid") String borrowerid,
                                                    @Field("authcode") String authcode,
                                                    @Field("amount") double amount,
                                                    @Field("gkstoreid") String gkstoreid

    );

    @FormUrlEncoded
    @POST("gkstoreCancelOrder")
    Call<GetGKStoreCancelOrderResponse> getGkStoreCancelOrder(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("authcode") String authcode,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("ordertxnno") String ordertxnno,
                                                              @Field("servicecode") String servicecode,
                                                              @Field("passedyear") String passedyear,
                                                              @Field("passedmonth") String passedmonth,
                                                              @Field("statusrequest") String statusrequest,
                                                              @Field("declineremarks") String declineremarks,
                                                              @Field("merchantid") String merchantid,
                                                              @Field("storeid") String storeid

    );

    @FormUrlEncoded
    @POST("checkGKStoreStatusQue")
    Call<CheckGKStoreStatusQueResponse> checkGkStoreStatusQue(@Field("sessionid") String sessionid,
                                                              @Field("imei") String imei,
                                                              @Field("userid") String userid,
                                                              @Field("authcode") String authcode,
                                                              @Field("borrowerid") String borrowerid,
                                                              @Field("ordertxnno") String ordertxnno,
                                                              @Field("storeid") String storeid
    );


    @FormUrlEncoded
    @POST("insertOrderLogsQue")
    Call<GenericResponse> insertOrderLogsQue(@Field("sessionid") String sessionid,
                                             @Field("imei") String imei,
                                             @Field("authcode") String authcode,
                                             @Field("userid") String userid,
                                             @Field("borrowerid") String borrowerid,
                                             @Field("borrowername") String borrowername,
                                             @Field("merchantid") String merchantid,
                                             @Field("storeid") String storeid,
                                             @Field("firstname") String firstname,
                                             @Field("lastname") String lastname,
                                             @Field("mobileno") String mobileno,
                                             @Field("emailaddress") String emailaddress,
                                             @Field("address") String address,
                                             @Field("otherdetails") String otherdetails,
                                             @Field("orderdetails") String orderdetails,
                                             @Field("vouchersession") String vouchersession,
                                             @Field("latitude") String latitude,
                                             @Field("longitude") String longitude,
                                             @Field("remarks") String remarks,
                                             @Field("ordertype") String ordertype,
                                             @Field("servicecode") String servicecode,
                                             @Field("grossamount") String grossamount,
                                             @Field("hasdiscount") int hasdiscount,
                                             @Field("isapproved") int isapproved
    );


    @FormUrlEncoded
    @POST("getGkStoreBadge")
    Call<BadgeResponse> getGkStoreBadge(@Field("sessionid") String sessionid,
                                        @Field("imei") String imei,
                                        @Field("userid") String userid,
                                        @Field("authcode") String authcode,
                                        @Field("borrowerid") String borrowerid,
                                        @Field("servicecode") String servicecode
    );

}
