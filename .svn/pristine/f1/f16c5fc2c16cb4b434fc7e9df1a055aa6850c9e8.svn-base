package com.goodkredit.myapplication.utilities;

import android.content.Context;

import com.goodkredit.myapplication.common.CommonVariables;
import com.goodkredit.myapplication.service.AccountAPI;
import com.goodkredit.myapplication.service.BulletinAPI;
import com.goodkredit.myapplication.service.CSBRewardsAPI;
import com.goodkredit.myapplication.service.CommonApi;
import com.goodkredit.myapplication.service.ConsummationTransactionDetailsAPI;
import com.goodkredit.myapplication.service.DiscountAPI;
import com.goodkredit.myapplication.service.DropOffAPI;
import com.goodkredit.myapplication.service.GKEarnAPI;
import com.goodkredit.myapplication.service.GKNegosyoAPI;
import com.goodkredit.myapplication.service.GKServicesAPI;
import com.goodkredit.myapplication.service.GenerateQrAPI;
import com.goodkredit.myapplication.service.GetBillsPaymentLogsAPI;
import com.goodkredit.myapplication.service.GetBorrowingsAPI;
import com.goodkredit.myapplication.service.GetGKStoreAPI;
import com.goodkredit.myapplication.service.GetMerchantPromosAPI;
import com.goodkredit.myapplication.service.GetPaymentPartnerBranchesAPI;
import com.goodkredit.myapplication.service.GetPaymentPartnersAPI;
import com.goodkredit.myapplication.service.GetPrepaidLogsAPI;
import com.goodkredit.myapplication.service.GetPurchasesAPI;
import com.goodkredit.myapplication.service.GetReceivedVouchersAPI;
import com.goodkredit.myapplication.service.GetVirtualVoucherProductAPI;
import com.goodkredit.myapplication.service.GetVoucherDetailsAPI;
import com.goodkredit.myapplication.service.MDPAPI;
import com.goodkredit.myapplication.service.MedPadalaAPI;
import com.goodkredit.myapplication.service.NotificationAPI;
import com.goodkredit.myapplication.service.ParamountAPI;
import com.goodkredit.myapplication.service.PayByQRCodeApi;
import com.goodkredit.myapplication.service.PaymentAPI;
import com.goodkredit.myapplication.service.PrePurchaseAPI;
import com.goodkredit.myapplication.service.ProjectcoopAPI;
import com.goodkredit.myapplication.service.PromoQRCodeAPI;
import com.goodkredit.myapplication.service.PublicSponsorAPI;
import com.goodkredit.myapplication.service.RFIDAPI;
import com.goodkredit.myapplication.service.ReCaptchaAPI;
import com.goodkredit.myapplication.service.ReferralAPI;
import com.goodkredit.myapplication.service.RequestVoucherGenerationAPI;
import com.goodkredit.myapplication.service.SchoolAPI;
import com.goodkredit.myapplication.service.SendEmailForBillsPaymentAPI;
import com.goodkredit.myapplication.service.SkycableAPI;
import com.goodkredit.myapplication.service.SoaApi;
import com.goodkredit.myapplication.service.SubscriberAPI;
import com.goodkredit.myapplication.service.SupportAPI;
import com.goodkredit.myapplication.service.UnoRewardsAPI;
import com.goodkredit.myapplication.service.V2TransactionsApi;
import com.goodkredit.myapplication.service.WhatsNewAPI;
import com.goodkredit.myapplication.service.bills.BillsAPI;
import com.goodkredit.myapplication.service.coopassistant.CoopAssistantAPI;
import com.goodkredit.myapplication.service.fairchild.FairChildAPI;
import com.goodkredit.myapplication.service.freesms.FreeSMSAPI;
import com.goodkredit.myapplication.service.prepaidload.PrepaidLoadAPI;
import com.goodkredit.myapplication.service.transfervoucher.TransferVoucherAPI;
import com.goodkredit.myapplication.service.votes.VotesAPI;
import com.goodkredit.myapplication.service.voucher.VoucherAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Ban_Lenovo on 7/15/2017.
 */

public class RetrofitBuild {

    //dev
    public static final String ROOT_URL = CommonVariables.link + "api/";

    public static Retrofit retrofitBuilder;
    public static Retrofit retrofitBuilder2;
    public static Retrofit retrofitBuilder3;

    private static SoaApi soaApiService;
    private static CommonApi commonApiService;
    private static ConsummationTransactionDetailsAPI getTransactionDetailsService;
    private static GetVoucherDetailsAPI getVoucherDetailsService;
    private static GetPrepaidLogsAPI getPrepaidLogsService;
    private static GetBillsPaymentLogsAPI getBillsPaymentLogsService;
    private static GetReceivedVouchersAPI getReceivedVouchersService;
    private static GetBorrowingsAPI getBorrowingsService;
    private static GetPurchasesAPI getPurchasesService;
    private static SendEmailForBillsPaymentAPI sendEmailForBillsPaymentService;
    private static GetVirtualVoucherProductAPI getVirtualVoucherProductService;

    private static RequestVoucherGenerationAPI cancelRequestVoucherViaEghlPaymentService;
    private static RequestVoucherGenerationAPI requestVoucherGenerationService;
    private static RequestVoucherGenerationAPI checkEGHLPaymentTransactionStatusService;
    private static RequestVoucherGenerationAPI calculateEGHLServiceChargeService;

    private static GetPaymentPartnersAPI getPaymentPartnersService;
    private static GetPaymentPartnerBranchesAPI getPaymentPartnerBranchesService;
    private static CSBRewardsAPI rewardsAPIService;
    private static GetMerchantPromosAPI getMerchantPromoService;
    private static GetGKStoreAPI getGKStoreService;
    private static ParamountAPI getParamountPolicyTypeService;
    private static ParamountAPI getVehicleMakerService;
    private static ParamountAPI getVehicleSeriesService;
    private static ParamountAPI getProvincesService;
    private static ParamountAPI getCitiesService;
    private static ParamountAPI addParamountQueueService;
    private static ParamountAPI getParamountChargeService;
    private static ParamountAPI checkTransactionStatusService;
    private static ParamountAPI getParamountHistoryService;
    private static ParamountAPI getParamountPaymentVouchersService;
    private static ParamountAPI sendEmailForParamountService;

    private static PrepaidLoadAPI prepaidLoadAPIService;

    //GET VOUCHER
    private static VoucherAPI getVoucherV3Service;

    //TRANSFERVOUCHER
    private static TransferVoucherAPI transferVoucherV2Service;

    //DROPOFF
    private static DropOffAPI getDropOffPendingOrdersService;
    private static DropOffAPI cancelDropOffOrderService;

    //PROJECTCOOP
    private static ProjectcoopAPI getGameProductsService;
    private static ProjectcoopAPI getGameP2SResultsService;
    private static ProjectcoopAPI insertEntryNumberP2SService;
    private static ProjectcoopAPI getGameServiceChargeService;
    private static ProjectcoopAPI getGamePreviousEntriesService;
    private static ProjectcoopAPI getGameSupportFAQService;
    private static ProjectcoopAPI getGameSupportThreadService;
    private static ProjectcoopAPI getGameSupportConversationService;
    private static ProjectcoopAPI sendProjectCoopSupportMessageService;
    private static ProjectcoopAPI getGameP2SCutoverDetailsService;
    private static ProjectcoopAPI validateEntryP2SCutoverService;

    //SKYCABLE
    private static SkycableAPI getSkycablePpvCatalogsService;
    private static SkycableAPI getSkycablePPVCustomerServiceChargeService;
    private static SkycableAPI subscribeSkycablePpvService;
    private static SkycableAPI linkSkycableAccountService;
    private static SkycableAPI registerNewSkycableAccountService;
    private static SkycableAPI getSkycablePpvHistoryService;
    private static SkycableAPI getSkycableSOAService;
    private static SkycableAPI callLinkSkycableAccountService;
    private static SkycableAPI getSkycableNewApplicationRegistrationsService;
    private static SkycableAPI getSkycableDictionariesService;
    private static SkycableAPI skycableProcessVoucherConsummationService;
    private static SkycableAPI getSkycableSupportThreadService;
    private static SkycableAPI getSkycableSupportFAQService;
    private static SkycableAPI sendSkycableSupportMessageService;
    private static SkycableAPI getSkycableSupportConversationService;
    private static SkycableAPI getSkycableConfigService;
    private static SkycableAPI checkIfConfigIsAvailableService;
    private static CommonApi getS3keyService;

    private static PrePurchaseAPI prePurchaseService;

    private static V2TransactionsApi transactionsApi;

    private static PayByQRCodeApi qrCodeApiService;
    private static PromoQRCodeAPI promoQRApiService;

    private static MedPadalaAPI medPadalaAPIService;

    private static NotificationAPI notificationAPIService;

    private static SubscriberAPI subscriberAPIService;

    private static GKServicesAPI gkServicesAPIService;
    private static PublicSponsorAPI publicSponsorAPI;

    private static DiscountAPI getDiscountService;

    private static PaymentAPI paymentAPIService;

    private static GKNegosyoAPI gkNegosyoAPIService;

    private static UnoRewardsAPI unoRewardsAPIService;

    private static ReferralAPI referralAPIService;

    private static SchoolAPI schoolAPIService;

    private static BulletinAPI bulletinAPIService;

    private static SupportAPI supportAPIService;

    private static DropOffAPI dropOffAPIService;
    private static MDPAPI mdpAPIService;
    private static WhatsNewAPI whatsNewAPIService;
    private static ReCaptchaAPI reCaptchaAPIService;

    //FREE SMS
    private static FreeSMSAPI freeSMSAPIService;

    //BILLS
    private static BillsAPI billsAPIService;

    //VOTES
    private static VotesAPI votesAPIService;

    //FAIR CHILD
    private static FairChildAPI fairChildAPIService;

    //ACCOUNT API
    private static AccountAPI accountAPIService;

    //COOP ASSISTANT
    private static CoopAssistantAPI coopAssistantAPIService;

    //GK EARN
    private static GKEarnAPI gkEarnAPIService;

    //RFID
    private static RFIDAPI rfidAPIService;

    //Qr
    private static GenerateQrAPI getGenerateQr;

    public static Retrofit getRetrofitBuilder(final Context context) {
        if (retrofitBuilder == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//            httpClient.readTimeout(600, TimeUnit.SECONDS);
//            httpClient.connectTimeout(600, TimeUnit.SECONDS);
            if (CommonVariables.isHttpLogs) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(interceptor);
            }

            retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitBuilder;
    }


    public static Retrofit getRetrofitBuilderCSB(final Context context) {
        if (retrofitBuilder == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);

            if (CommonVariables.isHttpLogs) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(interceptor);
            }

            retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitBuilder;
    }

    public static Retrofit getRetrofitBuilderSession(final Context context) {
        if (retrofitBuilder2 == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            if (CommonVariables.isHttpLogs) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                httpClient.addInterceptor(interceptor);
            }

            retrofitBuilder2 = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .client(httpClient.build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }

        return retrofitBuilder2;
    }


    public static SoaApi getSoaApiService(Context context) {
        if (soaApiService == null) {
            soaApiService = getRetrofitBuilder(context).create(SoaApi.class);
        }
        return soaApiService;
    }

    public static CommonApi getCommonApiService(Context context) {
        if (commonApiService == null) {
            commonApiService = getRetrofitBuilderSession(context).create(CommonApi.class);
        }
        return commonApiService;
    }

    public static ConsummationTransactionDetailsAPI getTransactionDetailsService(Context context) {
        if (getTransactionDetailsService == null) {
            getTransactionDetailsService = getRetrofitBuilder(context).create(ConsummationTransactionDetailsAPI.class);
        }
        return getTransactionDetailsService;
    }

    public static GetVoucherDetailsAPI getVoucherDetailsService(Context context) {
        if (getVoucherDetailsService == null) {
            getVoucherDetailsService = getRetrofitBuilderSession(context).create(GetVoucherDetailsAPI.class);
        }
        return getVoucherDetailsService;
    }

    public static GetPrepaidLogsAPI getPrepaidLogsService(Context context) {
        if (getPrepaidLogsService == null) {
            getPrepaidLogsService = getRetrofitBuilder(context).create(GetPrepaidLogsAPI.class);
        }
        return getPrepaidLogsService;
    }

    public static GetBillsPaymentLogsAPI getBillsPaymentLogsService(Context context) {
        if (getBillsPaymentLogsService == null) {
            getBillsPaymentLogsService = getRetrofitBuilder(context).create(GetBillsPaymentLogsAPI.class);
        }
        return getBillsPaymentLogsService;
    }

    public static GetReceivedVouchersAPI getReceivedVouchersService(Context context) {
        if (getReceivedVouchersService == null) {
            getReceivedVouchersService = getRetrofitBuilderSession(context).create(GetReceivedVouchersAPI.class);
        }
        return getReceivedVouchersService;
    }

    public static GetBorrowingsAPI getBorrowingsService(Context context) {
        if (getBorrowingsService == null) {
            getBorrowingsService = getRetrofitBuilderSession(context).create(GetBorrowingsAPI.class);
        }
        return getBorrowingsService;
    }

    public static GetPurchasesAPI getPurchasesService(Context context) {
        if (getPurchasesService == null) {
            getPurchasesService = getRetrofitBuilder(context).create(GetPurchasesAPI.class);
        }
        return getPurchasesService;
    }

    public static V2TransactionsApi getTransactionsApi(Context context) {
        if (transactionsApi == null) {
            transactionsApi = getRetrofitBuilder(context).create(V2TransactionsApi.class);
        }
        return transactionsApi;
    }

    public static SendEmailForBillsPaymentAPI sendEmailForBillsPaymentService(Context context) {
        if (sendEmailForBillsPaymentService == null) {
            sendEmailForBillsPaymentService = getRetrofitBuilder(context).create(SendEmailForBillsPaymentAPI.class);
        }
        return sendEmailForBillsPaymentService;
    }

    public static GetMerchantPromosAPI getMerchantPromoService(Context context) {
        if (getMerchantPromoService == null) {
            getMerchantPromoService = getRetrofitBuilder(context).create(GetMerchantPromosAPI.class);
        }
        return getMerchantPromoService;
    }

    public static GetVirtualVoucherProductAPI getVirtualVoucherProductService(Context context) {
        if (getVirtualVoucherProductService == null) {
            getVirtualVoucherProductService = getRetrofitBuilder(context).create(GetVirtualVoucherProductAPI.class);
        }
        return getVirtualVoucherProductService;
    }

    public static RequestVoucherGenerationAPI cancelRequestVoucherViaEghlPaymentService(Context context) {
        if (cancelRequestVoucherViaEghlPaymentService == null) {
            cancelRequestVoucherViaEghlPaymentService = getRetrofitBuilder(context).create(RequestVoucherGenerationAPI.class);
        }
        return cancelRequestVoucherViaEghlPaymentService;
    }

    public static RequestVoucherGenerationAPI requestVoucherGenerationService(Context context) {
        if (requestVoucherGenerationService == null) {
            requestVoucherGenerationService = getRetrofitBuilder(context).create(RequestVoucherGenerationAPI.class);
        }
        return requestVoucherGenerationService;
    }

    public static RequestVoucherGenerationAPI checkEGHLPaymentTransactionStatusService(Context context) {
        if (checkEGHLPaymentTransactionStatusService == null) {
            checkEGHLPaymentTransactionStatusService = getRetrofitBuilder(context).create(RequestVoucherGenerationAPI.class);
        }
        return checkEGHLPaymentTransactionStatusService;
    }

    public static RequestVoucherGenerationAPI calculateEGHLServiceChargeService(Context context) {
        if (calculateEGHLServiceChargeService == null) {
            calculateEGHLServiceChargeService = getRetrofitBuilder(context).create(RequestVoucherGenerationAPI.class);
        }
        return calculateEGHLServiceChargeService;
    }

    public static GetPaymentPartnersAPI getPaymentPartnersService(Context context) {
        if (getPaymentPartnersService == null) {
            getPaymentPartnersService = getRetrofitBuilder(context).create(GetPaymentPartnersAPI.class);
        }
        return getPaymentPartnersService;
    }

    public static GetPaymentPartnerBranchesAPI getPaymentPartnerBranchesService(Context context) {
        if (getPaymentPartnerBranchesService == null) {
            getPaymentPartnerBranchesService = getRetrofitBuilder(context).create(GetPaymentPartnerBranchesAPI.class);
        }
        return getPaymentPartnerBranchesService;
    }

    public static PayByQRCodeApi getQrCodeApiService(Context context) {
        if (qrCodeApiService == null) {
            qrCodeApiService = getRetrofitBuilder(context).create(PayByQRCodeApi.class);
        }
        return qrCodeApiService;
    }

    public static CSBRewardsAPI getRewardsAPIService(Context context) {
        if (rewardsAPIService == null) {
            rewardsAPIService = getRetrofitBuilderCSB(context).create(CSBRewardsAPI.class);
        }
        return rewardsAPIService;
    }

    public static ParamountAPI getParamountPolicyTypeService(Context context) {
        if (getParamountPolicyTypeService == null) {
            getParamountPolicyTypeService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getParamountPolicyTypeService;
    }

    public static ParamountAPI getVehicleMakerService(Context context) {
        if (getVehicleMakerService == null) {
            getVehicleMakerService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getVehicleMakerService;
    }

    public static ParamountAPI getVehicleSeriesService(Context context) {
        if (getVehicleSeriesService == null) {
            getVehicleSeriesService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getVehicleSeriesService;
    }

    public static ParamountAPI getProvincesService(Context context) {
        if (getProvincesService == null) {
            getProvincesService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getProvincesService;
    }

    public static ParamountAPI getCitiesService(Context context) {
        if (getCitiesService == null) {
            getCitiesService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getCitiesService;
    }

    public static ParamountAPI addParamountQueueService(Context context) {
        if (addParamountQueueService == null) {
            addParamountQueueService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return addParamountQueueService;
    }

    public static ParamountAPI getParamountChargeService(Context context) {
        if (getParamountChargeService == null) {
            getParamountChargeService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getParamountChargeService;
    }

    public static PrePurchaseAPI prePurchaseService(Context context) {
        if (prePurchaseService == null) {
            prePurchaseService = getRetrofitBuilderSession(context).create(PrePurchaseAPI.class);
        }
        return prePurchaseService;
    }

    //SKYCABLE
    public static SkycableAPI getSkycablePpvCatalogsService(Context context) {
        if (getSkycablePpvCatalogsService == null) {
            getSkycablePpvCatalogsService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycablePpvCatalogsService;
    }

    public static SkycableAPI getSkycablePPVCustomerServiceChargeService(Context context) {
        if (getSkycablePPVCustomerServiceChargeService == null) {
            getSkycablePPVCustomerServiceChargeService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycablePPVCustomerServiceChargeService;
    }

    public static SkycableAPI subscribeSkycablePpvService(Context context) {
        if (subscribeSkycablePpvService == null) {
            subscribeSkycablePpvService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return subscribeSkycablePpvService;
    }

    public static SkycableAPI linkSkycableAccountService(Context context) {
        if (linkSkycableAccountService == null) {
            linkSkycableAccountService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return linkSkycableAccountService;
    }

    public static SkycableAPI registerNewSkycableAccountService(Context context) {
        if (registerNewSkycableAccountService == null) {
            registerNewSkycableAccountService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return registerNewSkycableAccountService;
    }

    public static SkycableAPI getSkycablePpvHistoryService(Context context) {
        if (getSkycablePpvHistoryService == null) {
            getSkycablePpvHistoryService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycablePpvHistoryService;
    }

    public static SkycableAPI callLinkSkycableAccountService(Context context) {
        if (callLinkSkycableAccountService == null) {
            callLinkSkycableAccountService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return callLinkSkycableAccountService;
    }

    public static SkycableAPI getSkycableSOAService(Context context) {
        if (getSkycableSOAService == null) {
            getSkycableSOAService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycableSOAService;
    }

    public static SkycableAPI getSkycableNewApplicationRegistrationsService(Context context) {
        if (getSkycableNewApplicationRegistrationsService == null) {
            getSkycableNewApplicationRegistrationsService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycableNewApplicationRegistrationsService;
    }

    public static SkycableAPI getSkycableDictionariesService(Context context) {
        if (getSkycableDictionariesService == null) {
            getSkycableDictionariesService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycableDictionariesService;
    }

    public static SkycableAPI skycableProcessVoucherConsummationService(Context context) {
        if (skycableProcessVoucherConsummationService == null) {
            skycableProcessVoucherConsummationService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return skycableProcessVoucherConsummationService;
    }

    public static SkycableAPI getSkycableSupportThreadService(Context context) {
        if (getSkycableSupportThreadService == null) {
            getSkycableSupportThreadService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycableSupportThreadService;
    }

    public static SkycableAPI getSkycableSupportFAQService(Context context) {
        if (getSkycableSupportFAQService == null) {
            getSkycableSupportFAQService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycableSupportFAQService;
    }

    public static SkycableAPI sendSkycableSupportMessageService(Context context) {
        if (sendSkycableSupportMessageService == null) {
            sendSkycableSupportMessageService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return sendSkycableSupportMessageService;
    }

    public static SkycableAPI getSkycableSupportConversationService(Context context) {
        if (getSkycableSupportConversationService == null) {
            getSkycableSupportConversationService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycableSupportConversationService;
    }

    public static SkycableAPI getSkycableConfigService(Context context) {
        if (getSkycableConfigService == null) {
            getSkycableConfigService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return getSkycableConfigService;
    }

    public static SkycableAPI checkIfConfigIsAvailableService(Context context) {
        if (checkIfConfigIsAvailableService == null) {
            checkIfConfigIsAvailableService = getRetrofitBuilder(context).create(SkycableAPI.class);
        }
        return checkIfConfigIsAvailableService;
    }

    public static CommonApi getS3keyService(Context context) {
        if (getS3keyService == null) {
            getS3keyService = getRetrofitBuilder(context).create(CommonApi.class);
        }
        return getS3keyService;
    }

    //PREPAID LOAD
    public static PrepaidLoadAPI getPrepaidLoadService(Context context) {
        if (prepaidLoadAPIService == null) {
            prepaidLoadAPIService = getRetrofitBuilder(context).create(PrepaidLoadAPI.class);
        }
        return prepaidLoadAPIService;
    }

    public static VoucherAPI getVoucherV3Service(Context context) {
        if (getVoucherV3Service == null) {
            getVoucherV3Service = getRetrofitBuilder(context).create(VoucherAPI.class);
        }
        return getVoucherV3Service;
    }

    //TRANSFERVOUCHER
    public static TransferVoucherAPI transferVoucherV2Service(Context context) {
        if (transferVoucherV2Service == null) {
            transferVoucherV2Service = getRetrofitBuilder(context).create(TransferVoucherAPI.class);
        }
        return transferVoucherV2Service;
    }

    //DROP OFF
    public static DropOffAPI getDropOffPendingOrdersService(Context context) {
        if (getDropOffPendingOrdersService == null) {
            getDropOffPendingOrdersService = getRetrofitBuilder(context).create(DropOffAPI.class);
        }
        return getDropOffPendingOrdersService;
    }

    public static DropOffAPI cancelDropOffOrderService(Context context) {
        if (cancelDropOffOrderService == null) {
            cancelDropOffOrderService = getRetrofitBuilder(context).create(DropOffAPI.class);
        }
        return cancelDropOffOrderService;
    }

    //PROJECT COOP
    public static ProjectcoopAPI getGameProductsService(Context context) {
        if (getGameProductsService == null) {
            getGameProductsService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGameProductsService;
    }

    public static ProjectcoopAPI getGameP2SResultsService(Context context) {
        if (getGameP2SResultsService == null) {
            getGameP2SResultsService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGameP2SResultsService;
    }

    public static ProjectcoopAPI insertEntryNumberP2SService(Context context) {
        if (insertEntryNumberP2SService == null) {
            insertEntryNumberP2SService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return insertEntryNumberP2SService;
    }

    public static ProjectcoopAPI getGameServiceChargeService(Context context) {
        if (getGameServiceChargeService == null) {
            getGameServiceChargeService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGameServiceChargeService;
    }

    public static ProjectcoopAPI getGamePreviousEntriesService(Context context) {
        if (getGamePreviousEntriesService == null) {
            getGamePreviousEntriesService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGamePreviousEntriesService;
    }

    public static ProjectcoopAPI getGameSupportFAQService(Context context) {
        if (getGameSupportFAQService == null) {
            getGameSupportFAQService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGameSupportFAQService;
    }

    public static ProjectcoopAPI getGameSupportThreadService(Context context) {
        if (getGameSupportThreadService == null) {
            getGameSupportThreadService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGameSupportThreadService;
    }

    public static ProjectcoopAPI getGameSupportConversationService(Context context) {
        if (getGameSupportConversationService == null) {
            getGameSupportConversationService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGameSupportConversationService;
    }

    public static ProjectcoopAPI sendProjectCoopSupportMessageService(Context context) {
        if (sendProjectCoopSupportMessageService == null) {
            sendProjectCoopSupportMessageService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return sendProjectCoopSupportMessageService;
    }

    public static ProjectcoopAPI getGameP2SCutoverDetailsService(Context context) {
        if (getGameP2SCutoverDetailsService == null) {
            getGameP2SCutoverDetailsService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return getGameP2SCutoverDetailsService;
    }

    public static ProjectcoopAPI validateEntryP2SCutoverService(Context context) {
        if (validateEntryP2SCutoverService == null) {
            validateEntryP2SCutoverService = getRetrofitBuilder(context).create(ProjectcoopAPI.class);
        }
        return validateEntryP2SCutoverService;
    }

    //PARAMOUNT
    public static ParamountAPI checkTransactionStatusService(Context context) {
        if (checkTransactionStatusService == null) {
            checkTransactionStatusService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return checkTransactionStatusService;
    }

    public static ParamountAPI getParamountHistoryService(Context context) {
        if (getParamountHistoryService == null) {
            getParamountHistoryService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getParamountHistoryService;
    }

    public static ParamountAPI getParamountPaymentVouchersService(Context context) {
        if (getParamountPaymentVouchersService == null) {
            getParamountPaymentVouchersService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return getParamountPaymentVouchersService;
    }

    public static ParamountAPI sendEmailForParamountService(Context context) {
        if (sendEmailForParamountService == null) {
            sendEmailForParamountService = getRetrofitBuilder(context).create(ParamountAPI.class);
        }
        return sendEmailForParamountService;
    }

    public static PromoQRCodeAPI getPromoQRApiService(Context context) {
        if (promoQRApiService == null) {
            promoQRApiService = getRetrofitBuilder(context).create(PromoQRCodeAPI.class);
        }
        return promoQRApiService;
    }

    public static MedPadalaAPI getMedPadalaApiService(Context context) {
        if (medPadalaAPIService == null) {
            medPadalaAPIService = getRetrofitBuilder(context).create(MedPadalaAPI.class);
        }
        return medPadalaAPIService;
    }

    public static NotificationAPI getNotificationAPIService(Context context) {
        if (notificationAPIService == null) {
            notificationAPIService = getRetrofitBuilderSession(context).create(NotificationAPI.class);
        }
        return notificationAPIService;
    }

    public static SubscriberAPI getSubscriberAPIService(Context context) {
        if (subscriberAPIService == null) {
            subscriberAPIService = getRetrofitBuilder(context).create(SubscriberAPI.class);
        }
        return subscriberAPIService;
    }

    public static GetGKStoreAPI getGKStoreService(Context context) {
        if (getGKStoreService == null) {
            getGKStoreService = getRetrofitBuilder(context).create(GetGKStoreAPI.class);
        }
        return getGKStoreService;
    }

    public static GenerateQrAPI getGenerateQr(Context context) {
        if (getGenerateQr == null) {
            getGenerateQr = getRetrofitBuilder(context).create(GenerateQrAPI.class);
        }
        return getGenerateQr;
    }

    public static GKServicesAPI getGKSerivcesAPIService(Context context) {
        if (gkServicesAPIService == null) {
            gkServicesAPIService = getRetrofitBuilder(context).create(GKServicesAPI.class);
        }
        return gkServicesAPIService;
    }

    public static PublicSponsorAPI getPublicSponsorAPI(Context context) {
        if (publicSponsorAPI == null) {
            publicSponsorAPI = getRetrofitBuilder(context).create(PublicSponsorAPI.class);
        }
        return publicSponsorAPI;
    }

    public static DiscountAPI getDiscountService(Context context) {
        if (getDiscountService == null) {
            getDiscountService = getRetrofitBuilder(context).create(DiscountAPI.class);
        }
        return getDiscountService;
    }

    public static PaymentAPI getPaymentAPIService(Context context) {
        if (paymentAPIService == null) {
            paymentAPIService = getRetrofitBuilder(context).create(PaymentAPI.class);
        }
        return paymentAPIService;
    }

    public static GKNegosyoAPI getGKNegosyoAPIService(Context context) {
        if (gkNegosyoAPIService == null) {
            gkNegosyoAPIService = getRetrofitBuilder(context).create(GKNegosyoAPI.class);
        }
        return gkNegosyoAPIService;
    }

    public static UnoRewardsAPI getUnoRewardsAPIService(Context context) {
        if (unoRewardsAPIService == null) {
            unoRewardsAPIService = getRetrofitBuilder(context).create(UnoRewardsAPI.class);
        }
        return unoRewardsAPIService;
    }

    public static ReferralAPI getReferralAPIService(Context context) {
        if (referralAPIService == null) {
            referralAPIService = getRetrofitBuilder(context).create(ReferralAPI.class);
        }
        return referralAPIService;
    }

    public static SchoolAPI getSchoolAPIService(Context context) {
        if (schoolAPIService == null) {
            schoolAPIService = getRetrofitBuilder(context).create(SchoolAPI.class);
        }
        return schoolAPIService;
    }

    public static VotesAPI getVotesAPIService(Context context) {
        if (votesAPIService == null) {
            votesAPIService = getRetrofitBuilder(context).create(VotesAPI.class);
        }
        return votesAPIService;
    }

    public static FairChildAPI getFairChildAPIService(Context context) {
        if (fairChildAPIService == null) {
            fairChildAPIService = getRetrofitBuilder(context).create(FairChildAPI.class);
        }
        return fairChildAPIService;
    }

    public static AccountAPI getAccountAPIService (Context context) {
        if (accountAPIService == null) {
            accountAPIService = getRetrofitBuilder(context).create(AccountAPI.class);
        }
        return accountAPIService;
    }

    public static BulletinAPI getBulletinAPIService(Context context) {
        if (bulletinAPIService == null) {
            bulletinAPIService = getRetrofitBuilder(context).create(BulletinAPI.class);
        }
        return bulletinAPIService;
    }

    public static SupportAPI getSupportAPIService(Context context) {
        if (supportAPIService == null) {
            supportAPIService = getRetrofitBuilder(context).create(SupportAPI.class);
        }
        return supportAPIService;
    }

    public static DropOffAPI getDropOffAPIService(Context context) {
        if (dropOffAPIService == null) {
            dropOffAPIService = getRetrofitBuilder(context).create(DropOffAPI.class);
        }
        return dropOffAPIService;
    }

    public static MDPAPI getMDPAPIService(Context context) {
        if (mdpAPIService == null) {
            mdpAPIService = getRetrofitBuilder(context).create(MDPAPI.class);
        }
        return mdpAPIService;
    }

    public static WhatsNewAPI getWhatsNewAPIService(Context context) {
        if (whatsNewAPIService == null) {
            whatsNewAPIService = getRetrofitBuilder(context).create(WhatsNewAPI.class);
        }
        return whatsNewAPIService;
    }


    private static final String RECAPTCHA_VERIFICATION_URL = "https://www.google.com/recaptcha/api/";

    public static Retrofit getRetrofitBuilderReCaptcha(final Context context) {
        if (retrofitBuilder3 == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor);

            retrofitBuilder3 = new Retrofit.Builder()
                    .baseUrl(RECAPTCHA_VERIFICATION_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitBuilder3;
    }

    public static ReCaptchaAPI getReCaptchaAPIService(Context context) {
        if (reCaptchaAPIService == null) {
            reCaptchaAPIService = getRetrofitBuilderReCaptcha(context).create(ReCaptchaAPI.class);
        }
        return reCaptchaAPIService;
    }

    public static FreeSMSAPI getFreeSMSAPIService(Context context){
        if(freeSMSAPIService == null){
            freeSMSAPIService = getRetrofitBuilder(context).create(FreeSMSAPI.class);
        }
        return freeSMSAPIService;
    }

    public static BillsAPI getBillsAPIService(Context context){
        if(billsAPIService == null){
            billsAPIService = getRetrofitBuilder(context).create(BillsAPI.class);
        }
        return billsAPIService;
    }

    public static CoopAssistantAPI getCoopAssistantAPI(Context context){
        if(coopAssistantAPIService == null){
            coopAssistantAPIService = getRetrofitBuilder(context).create(CoopAssistantAPI.class);
        }
        return coopAssistantAPIService;
    }

    public static RFIDAPI getRFIDAPIService(Context context){
        if(rfidAPIService == null){
            rfidAPIService = getRetrofitBuilder(context).create(RFIDAPI.class);
        }
        return rfidAPIService;
    }

    public static GKEarnAPI getgkEarnAPIService(Context context){
        if(gkEarnAPIService == null){
            gkEarnAPIService = getRetrofitBuilder(context).create(GKEarnAPI.class);
        }
        return gkEarnAPIService;
    }
}
