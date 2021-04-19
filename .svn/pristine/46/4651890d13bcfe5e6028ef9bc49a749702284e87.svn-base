package com.goodkredit.myapplication.utilities;

import android.app.Notification;
import android.content.Context;

import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.service.newAPICalls.CoopAssistantV2API;
import com.goodkredit.myapplication.service.newAPICalls.EGameAPI;
import com.goodkredit.myapplication.service.newAPICalls.AccountV2API;

import com.goodkredit.myapplication.service.newAPICalls.CommonV2API;
import com.goodkredit.myapplication.service.newAPICalls.DropOffV2API;
import com.goodkredit.myapplication.service.newAPICalls.FreeSMSV2API;
import com.goodkredit.myapplication.service.newAPICalls.GKEarnV2API;
import com.goodkredit.myapplication.service.newAPICalls.GKNegosyoV2API;
import com.goodkredit.myapplication.service.newAPICalls.GKStoreV2API;
import com.goodkredit.myapplication.service.newAPICalls.GkServiceV2API;
import com.goodkredit.myapplication.service.newAPICalls.LoadMessengerV2API;
import com.goodkredit.myapplication.service.newAPICalls.MerchantV2API;
import com.goodkredit.myapplication.service.newAPICalls.NotificationsV2API;
import com.goodkredit.myapplication.service.newAPICalls.PayByQRCodeV2API;
import com.goodkredit.myapplication.service.newAPICalls.PaymentV2API;
import com.goodkredit.myapplication.service.newAPICalls.PrepaidLoadV2API;
import com.goodkredit.myapplication.service.newAPICalls.PromoQRCodeV2API;
import com.goodkredit.myapplication.service.newAPICalls.PublicSponsorV2API;
import com.goodkredit.myapplication.service.newAPICalls.SchoolV2API;
import com.goodkredit.myapplication.service.newAPICalls.SoaV2API;
import com.goodkredit.myapplication.service.newAPICalls.SubscriberV2API;
import com.goodkredit.myapplication.service.newAPICalls.TransactionsV2API;
import com.goodkredit.myapplication.service.newAPICalls.VotesV2API;
import com.goodkredit.myapplication.service.newAPICalls.VoucherV2API;
import com.goodkredit.myapplication.service.newAPICalls.WhatsNewV2API;
import com.goodkredit.myapplication.service.newAPICalls.TransferVoucherV2API;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RONELDAYANAN-PC on October 2019.
 */

public class RetrofitBuilder {


    /*|************************SECURITY UPDATE AS OF OCT 14,2019***********************************************|*/
    //dev
    private static final String NEW_ROOT_URL = CommonVariables.securedLink + "api/";

    private static Retrofit retrofitBuilderNewRootURL;


    //static interfaces
    private static AccountV2API accountV2API;
    private static SubscriberV2API subscriberAPIV2Service;
    private static TransactionsV2API transactionsV2API;
    private static CommonV2API commonV2API;
    private static PublicSponsorV2API publicSponsorV2API;
    private static SoaV2API soaV2API;
    private static GkServiceV2API gkServiceV2API;
    private static NotificationsV2API notificationsV2API;
    private static WhatsNewV2API whatsNewV2API;
    private static PayByQRCodeV2API payByQRCodeV2API;
    private static VoucherV2API voucherV2API;
    private static PaymentV2API paymentV2API;
    private static PromoQRCodeV2API promoQRCodeV2API;
    private static GKNegosyoV2API gkNegosyoV2API;
    private static DropOffV2API dropOffV2API;
    private static FreeSMSV2API freeSMSV2API;
    private static MerchantV2API merchantV2API;
    private static EGameAPI eGameAPI;
    private static PrepaidLoadV2API prepaidLoadV2API;
    private static CoopAssistantV2API coopAssistantV2API;
    private static VotesV2API votesV2API;
    private static GKStoreV2API gKStoreV2API;
    private static SchoolV2API schoolV2API;
    private static TransferVoucherV2API transferVoucherV2API;
    private static GKEarnV2API gkEarnV2API;
    private static LoadMessengerV2API loadMessengerV2API;

    //get instance
    private static Retrofit getRetrofitBuilderNewRootURL(final Context context) {
        if (retrofitBuilderNewRootURL == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            if (CommonVariables.isHttpLogs) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(interceptor);
            }

          httpClient.readTimeout(120, TimeUnit.SECONDS);
          httpClient.connectTimeout(120,TimeUnit.SECONDS);

            retrofitBuilderNewRootURL = new Retrofit.Builder()
                    .baseUrl(NEW_ROOT_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitBuilderNewRootURL;
    }


    //Methods
    public static AccountV2API getAccountV2API(Context context) {
        if (accountV2API == null) {
            accountV2API = getRetrofitBuilderNewRootURL(context).create(AccountV2API.class);
        }
        return accountV2API;
    }

    public static SubscriberV2API getSubscriberV2APIService(Context context) {
        if (subscriberAPIV2Service == null) {
            subscriberAPIV2Service = getRetrofitBuilderNewRootURL(context).create(SubscriberV2API.class);
        }
        return subscriberAPIV2Service;
    }

    public static TransactionsV2API getTransactionsV2APIService(Context context) {
        if (transactionsV2API == null) {
            transactionsV2API = getRetrofitBuilderNewRootURL(context).create(TransactionsV2API.class);
        }
        return transactionsV2API;
    }

    public static PublicSponsorV2API getPublicSponsorV2API(Context context) {
        if (publicSponsorV2API == null) {
            publicSponsorV2API = getRetrofitBuilderNewRootURL(context).create(PublicSponsorV2API.class);
        }
        return publicSponsorV2API;
    }

    public static SoaV2API getSoaV2API(Context context) {
        if (soaV2API == null) {
            soaV2API = getRetrofitBuilderNewRootURL(context).create(SoaV2API.class);
        }
        return soaV2API;
    }

    public static GkServiceV2API getGkServiceV2API(Context context) {
        if (gkServiceV2API == null) {
            gkServiceV2API = getRetrofitBuilderNewRootURL(context).create(GkServiceV2API.class);
        }
        return gkServiceV2API;
    }
    public static NotificationsV2API getNotificationsV2API(Context context) {
        if (notificationsV2API == null) {
            notificationsV2API = getRetrofitBuilderNewRootURL(context).create(NotificationsV2API.class);
        }
        return notificationsV2API;
    }

    public static WhatsNewV2API getWhatsNewV2API(Context context) {
        if (whatsNewV2API == null) {
            whatsNewV2API = getRetrofitBuilderNewRootURL(context).create(WhatsNewV2API.class);
        }
        return whatsNewV2API;
    }

    public static PayByQRCodeV2API getPayByQRCodeV2API(Context context) {
        if (payByQRCodeV2API == null) {
            payByQRCodeV2API = getRetrofitBuilderNewRootURL(context).create(PayByQRCodeV2API.class);
        }
        return payByQRCodeV2API;
    }

    public static CommonV2API getCommonV2API(Context context) {
        if (commonV2API == null) {
            commonV2API = getRetrofitBuilderNewRootURL(context).create(CommonV2API.class);
        }
        return commonV2API;
    }
    public static VoucherV2API getVoucherV2API(Context context) {
        if (voucherV2API == null) {
            voucherV2API = getRetrofitBuilderNewRootURL(context).create(VoucherV2API.class);
        }
        return voucherV2API;
    }
    public static PaymentV2API getPaymentV2API(Context context) {
        if (paymentV2API == null) {
            paymentV2API = getRetrofitBuilderNewRootURL(context).create(PaymentV2API.class);
        }
        return paymentV2API;
    }

    public static PromoQRCodeV2API getPromoQRCodeV2API(Context context) {
        if (promoQRCodeV2API == null) {
            promoQRCodeV2API = getRetrofitBuilderNewRootURL(context).create(PromoQRCodeV2API.class);
        }
        return promoQRCodeV2API;
    }
    public static GKNegosyoV2API getGkNegosyoV2API(Context context) {
        if (gkNegosyoV2API == null) {
            gkNegosyoV2API = getRetrofitBuilderNewRootURL(context).create(GKNegosyoV2API.class);
        }
        return gkNegosyoV2API;
    }
    public static DropOffV2API getDropOffV2API(Context context) {
        if (dropOffV2API == null) {
            dropOffV2API = getRetrofitBuilderNewRootURL(context).create(DropOffV2API.class);
        }
        return dropOffV2API;
    }

    public static FreeSMSV2API getFreeSMSV2API(Context context) {
        if (freeSMSV2API == null) {
            freeSMSV2API = getRetrofitBuilderNewRootURL(context).create(FreeSMSV2API.class);
        }
        return freeSMSV2API;
    }
    public static MerchantV2API getMerchantV2API(Context context) {
        if (merchantV2API == null) {
            merchantV2API = getRetrofitBuilderNewRootURL(context).create(MerchantV2API.class);
        }
        return merchantV2API;
    }
    public static PrepaidLoadV2API getPrepaidLoadV2API(Context context) {
        if (prepaidLoadV2API == null) {
            prepaidLoadV2API = getRetrofitBuilderNewRootURL(context).create(PrepaidLoadV2API.class);
        }
        return prepaidLoadV2API;
    }
    public static EGameAPI geteGameAPI (Context context){
        if(eGameAPI == null){
            eGameAPI = getRetrofitBuilderNewRootURL(context).create(EGameAPI.class);
        }
        return eGameAPI;
    }

    public static CoopAssistantV2API getCoopAssistantV2API (Context context){
        if(coopAssistantV2API == null){
            coopAssistantV2API = getRetrofitBuilderNewRootURL(context).create(CoopAssistantV2API.class);
        }
        return coopAssistantV2API;
    }
    public static VotesV2API getVotesV2API (Context context){
        if(votesV2API == null){
            votesV2API = getRetrofitBuilderNewRootURL(context).create(VotesV2API.class);
        }
        return votesV2API;
    }

    public static GKStoreV2API getGKStoreV2API (Context context){
        if(gKStoreV2API == null){
            gKStoreV2API = getRetrofitBuilderNewRootURL(context).create(GKStoreV2API.class);
        }
        return gKStoreV2API;
    }

    public static SchoolV2API getSchoolV2API (Context context){
        if(schoolV2API == null){
            schoolV2API = getRetrofitBuilderNewRootURL(context).create(SchoolV2API.class);
        }
        return schoolV2API;
    }

    public static TransferVoucherV2API getTransferVoucherV2API (Context context){
        if(transferVoucherV2API == null){
            transferVoucherV2API = getRetrofitBuilderNewRootURL(context).create(TransferVoucherV2API.class);
        }
        return transferVoucherV2API;
    }
    public static GKEarnV2API getGkEarnV2API (Context context){
        if(gkEarnV2API == null){
            gkEarnV2API = getRetrofitBuilderNewRootURL(context).create(GKEarnV2API.class);
        }
        return gkEarnV2API;
    }

    public static LoadMessengerV2API getLoadMessengerV2API(Context context){
        if(loadMessengerV2API == null){
            loadMessengerV2API = getRetrofitBuilderNewRootURL(context).create(LoadMessengerV2API.class);
        }
        return loadMessengerV2API;
    }

}
