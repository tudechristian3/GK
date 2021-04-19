package com.goodkredit.myapplication.utilities;

import android.location.Address;

import androidx.collection.LruCache;

import com.goodkredit.myapplication.bean.Merchants;
import com.goodkredit.myapplication.model.GKNegosyPackage;
import com.goodkredit.myapplication.model.GKNegosyoPackagesAndDetails;
import com.goodkredit.myapplication.model.UNORedemption;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantBills;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantLoanTransactions;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantPayments;
import com.goodkredit.myapplication.model.coopassistant.member.CoopAssistantTransactionHistory;
import com.goodkredit.myapplication.model.egame.EGameProducts;
import com.goodkredit.myapplication.model.egame.EGameTransactions;
import com.goodkredit.myapplication.model.gkads.GKAds;
import com.goodkredit.myapplication.model.gkearn.GKEarnConversionPoints;
import com.goodkredit.myapplication.model.gkearn.GKEarnReferralRandom;
import com.goodkredit.myapplication.model.gkearn.GKEarnStockistPackage;
import com.goodkredit.myapplication.model.gkearn.GKEarnTopUpHistory;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniDetails;
import com.goodkredit.myapplication.model.schoolmini.SchoolMiniStudents;
import com.goodkredit.myapplication.model.votes.VotesHistory;
import com.goodkredit.myapplication.model.votes.VotesPending;
import com.goodkredit.myapplication.model.vouchers.AccreditedBanks;
import com.goodkredit.myapplication.model.vouchers.BankDepositHistoryQueue;
import com.goodkredit.myapplication.model.vouchers.SubscriberBankAccounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CacheManager {

    private static CacheManager instance;

    private static final Object singletonLock = new Object();

    private static final int CACHE_SIZE = 500;

    private final LruCache<Integer, GKNegosyoPackagesAndDetails> cacheGKNegosyoPackagesAndDetails = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, GKNegosyPackage> cacheGKNegosyoPackages = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, Address> cacheGKNegosyoResellerAddress = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, Merchants> cacheMerchantsWithDiscount = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, UNORedemption> cacheUNORedemptions = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, SchoolMiniDetails> cacheSchoolDetails = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, SchoolMiniStudents> cacheSchoolStudents = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, GKAds> cacheNewUpdates = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, GKAds> cachePromotions = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, VotesHistory> cacheVotesHistory = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, VotesPending> cacheVotesPending = new LruCache<>(CACHE_SIZE);
    //COOP ASSISTANT
    private final LruCache<Integer, CoopAssistantBills> cacheCoopAssistantBills = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, CoopAssistantPayments> cacheCoopAssistantPayments = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, CoopAssistantLoanTransactions> cacheCoopAssistantLoanTransactions = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, CoopAssistantTransactionHistory> cachecoopAssistantTransactionHistory = new LruCache<>(CACHE_SIZE);

    //GK EARN
    private final LruCache<Integer, GKEarnStockistPackage> cacheGKEarnStockistPackage = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, GKEarnTopUpHistory> cacheGKEarnTopUpHistory = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, GKEarnConversionPoints> cacheGKEarnConversionHistory = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, GKEarnReferralRandom> cacheGKEarnReferralRandom = new LruCache<>(CACHE_SIZE);

    //PAYOUTONE
    private final LruCache<Integer, AccreditedBanks> cacheAccreditedBanks = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, SubscriberBankAccounts> cacheSubscriberBankAccounts = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, BankDepositHistoryQueue> cacheBankDepositHistory = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, BankDepositHistoryQueue> cacheBankDepositQueue = new LruCache<>(CACHE_SIZE);

    //EGAME
    private final LruCache<Integer, EGameProducts> cacheEGameProducts = new LruCache<>(CACHE_SIZE);
    private final LruCache<Integer, EGameTransactions> cacheEGameTransactions = new LruCache<>(CACHE_SIZE);

    public static CacheManager getInstance() {
        synchronized (singletonLock) {
            if (instance == null) {
                instance = new CacheManager();
            }
            return instance;
        }
    }

    //Add new item here
    public void clearCache() {
        cacheGKNegosyoPackagesAndDetails.evictAll();
        cacheGKNegosyoPackages.evictAll();
        cacheGKNegosyoResellerAddress.evictAll();
        cacheMerchantsWithDiscount.evictAll();
        cacheUNORedemptions.evictAll();
        cacheSchoolDetails.evictAll();
        cacheSchoolStudents.evictAll();
        cacheNewUpdates.evictAll();
        cachePromotions.evictAll();
        cacheVotesHistory.evictAll();
        cacheVotesPending.evictAll();
        cacheCoopAssistantBills.evictAll();
        cacheCoopAssistantPayments.evictAll();
        cacheCoopAssistantLoanTransactions.evictAll();
        cacheGKEarnStockistPackage.evictAll();
        cacheGKEarnTopUpHistory.evictAll();
        cacheGKEarnConversionHistory.evictAll();
        cacheAccreditedBanks.evictAll();
        cacheSubscriberBankAccounts.evictAll();
        cacheBankDepositHistory.evictAll();
        cacheBankDepositQueue.evictAll();
        cacheEGameProducts.evictAll();
        cacheEGameTransactions.evictAll();
    }

    //SCHOOL DETAILS
    public void saveSchoolDetails(List<SchoolMiniDetails> schooldetails) {
        cacheSchoolDetails.evictAll();
        for (SchoolMiniDetails schoolMiniDetails : schooldetails) {
            cacheSchoolDetails.put(schoolMiniDetails.getID(), schoolMiniDetails);
        }
    }

    public List<SchoolMiniDetails> getSchoolDetailsList() {
        List<SchoolMiniDetails> schooldetails = new ArrayList<>();

        synchronized (cacheSchoolDetails) {
            Set<Integer> keySet = cacheSchoolDetails.snapshot().keySet();
            for (Integer key : keySet) {
                SchoolMiniDetails schoolMiniDetails = cacheSchoolDetails.get(key);
                schooldetails.add(schoolMiniDetails);
            }
        }
        return schooldetails;
    }

    //SCHOOL STUDENTS
    public void saveSchoolStudents(ArrayList<SchoolMiniStudents> schoolstudents) {
        cacheSchoolStudents.evictAll();
        for (SchoolMiniStudents schoolMiniStudents : schoolstudents) {
            cacheSchoolStudents.put(schoolMiniStudents.getID(), schoolMiniStudents);
        }
    }

    public ArrayList<SchoolMiniStudents> getSchoolStudentsList() {
        ArrayList<SchoolMiniStudents> schoolstudents = new ArrayList<>();

        synchronized (cacheSchoolStudents) {
            Set<Integer> keySet = cacheSchoolStudents.snapshot().keySet();
            for (Integer key : keySet) {
                SchoolMiniStudents schoolMiniStudents = cacheSchoolStudents.get(key);
                schoolstudents.add(schoolMiniStudents);
            }
        }
        return schoolstudents;
    }

    public void saveUNORedemptions(List<UNORedemption> redemptions) {
        cacheUNORedemptions.evictAll();
        for (UNORedemption redemption : redemptions) {
            cacheUNORedemptions.put(redemption.getID(), redemption);
        }
    }

    public List<UNORedemption> getUNORedemptions() {
        List<UNORedemption> redemptions = new ArrayList<>();

        synchronized (cacheUNORedemptions) {
            Set<Integer> keySet = cacheUNORedemptions.snapshot().keySet();
            for (Integer key : keySet) {
                UNORedemption redemption = cacheUNORedemptions.get(key);
                redemptions.add(redemption);
            }
        }
        return redemptions;
    }

    public void saveMerchantsWithDiscount(List<Merchants> merchants) {
        cacheMerchantsWithDiscount.evictAll();
        int x = 0;
        for (Merchants merchant : merchants) {
            cacheMerchantsWithDiscount.put(x++, merchant);
        }
    }

    public List<Merchants> getMerchantsWithDiscount() {
        List<Merchants> merchants = new ArrayList<>();

        synchronized (cacheMerchantsWithDiscount) {
            Set<Integer> keySet = cacheMerchantsWithDiscount.snapshot().keySet();
            for (Integer key : keySet) {
                Merchants merchant = cacheMerchantsWithDiscount.get(key);
                merchants.add(merchant);
            }
        }
        return merchants;
    }

    public void saveGKNegosyPackages(List<GKNegosyPackage> gknp) {
        cacheGKNegosyoPackages.evictAll();
        for (GKNegosyPackage pckage : gknp) {
            cacheGKNegosyoPackages.put(pckage.getID(), pckage);
        }
    }

    public List<GKNegosyPackage> getGKNegosyPackage() {
        List<GKNegosyPackage> gknp = new ArrayList<>();

        synchronized (cacheGKNegosyoPackages) {
            Set<Integer> keySet = cacheGKNegosyoPackages.snapshot().keySet();
            for (Integer key : keySet) {
                GKNegosyPackage pckage = cacheGKNegosyoPackages.get(key);
                gknp.add(pckage);
            }
        }
        return gknp;
    }

    public void saveGKNegosyPackagesAndDetails(GKNegosyoPackagesAndDetails gkNegosyoPackagesAndDetails) {
        cacheGKNegosyoPackagesAndDetails.evictAll();
        cacheGKNegosyoPackagesAndDetails.put(1, gkNegosyoPackagesAndDetails);
    }

    public GKNegosyoPackagesAndDetails getGKNegosyoPackagesAndDetails() {
        GKNegosyoPackagesAndDetails gknPackageAndDetails = null;

        synchronized (cacheGKNegosyoPackagesAndDetails) {
            Set<Integer> keySet = cacheGKNegosyoPackagesAndDetails.snapshot().keySet();
            for (Integer key : keySet) {
                gknPackageAndDetails = cacheGKNegosyoPackagesAndDetails.get(key);
            }
        }
        return gknPackageAndDetails;
    }

    public void saveGKNegosyResellerAddress(Address address) {
        cacheGKNegosyoResellerAddress.evictAll();
        cacheGKNegosyoResellerAddress.put(1, address);
    }

    public Address getGKNegosyoResellerAddress() {
        Address address = null;

        synchronized (cacheGKNegosyoResellerAddress) {
            Set<Integer> keySet = cacheGKNegosyoResellerAddress.snapshot().keySet();
            for (Integer key : keySet) {
                address = cacheGKNegosyoResellerAddress.get(key);
            }
        }
        return address;
    }

    public void saveNewUpdates(List<GKAds> ads) {
        cacheNewUpdates.evictAll();
        for (GKAds ad : ads) {
            cacheNewUpdates.put(ad.getID(), ad);
        }
    }

    public List<GKAds> getNewUpdates() {
        List<GKAds> ads = new ArrayList<>();

        synchronized (cacheNewUpdates) {
            Set<Integer> keySet = cacheNewUpdates.snapshot().keySet();
            for (Integer key : keySet) {
                GKAds ad = cacheNewUpdates.get(key);
                ads.add(ad);
            }
        }
        return ads;
    }

    public void savePromotions(List<GKAds> ads) {
        cachePromotions.evictAll();
        for (GKAds ad : ads) {
            cachePromotions.put(ad.getID(), ad);
        }
    }

    public List<GKAds> getPromotions() {
        List<GKAds> ads = new ArrayList<>();

        synchronized (cachePromotions) {
            Set<Integer> keySet = cachePromotions.snapshot().keySet();
            for (Integer key : keySet) {
                GKAds ad = cachePromotions.get(key);
                ads.add(ad);
            }
        }
        return ads;
    }

    //Votes History
    public void saveVotesHistory(List<VotesHistory> voteshistory){
        cacheVotesHistory.evictAll();
        for(VotesHistory history : voteshistory){
            cacheVotesHistory.put(history.getID(), history);
        }
    }

    public List<VotesHistory> getVotesHistory() {
        List<VotesHistory> voteshistory = new ArrayList<>();

        synchronized (cacheVotesHistory){
            Set<Integer> keySet = cacheVotesHistory.snapshot().keySet();
            for(Integer key : keySet){
                VotesHistory history = cacheVotesHistory.get(key);
                voteshistory.add(history);
            }
        }

        return voteshistory;
    }

    //Votes Pending
    public void saveVotesPending (List<VotesPending> votespending){
        cacheVotesPending.evictAll();
        for (VotesPending pending : votespending){
            cacheVotesPending.put(pending.getID(), pending);
        }
    }

    public List<VotesPending> getVotesPending(){
        List<VotesPending> votespending = new ArrayList<>();

        synchronized (cacheVotesPending) {
            Set<Integer> keySet = cacheVotesPending.snapshot().keySet();
            for(Integer key : keySet) {
                VotesPending pending = cacheVotesPending.get(key);
                votespending.add(pending);
            }
        }

        return votespending;
    }

    //COOP ASSISTANT SOA BILLS
    public void saveCoopAssistantSOABills (List<CoopAssistantBills> coopsoabills){
        for (CoopAssistantBills soabills : coopsoabills){
            cacheCoopAssistantBills.put(soabills.getID(), soabills);
        }
    }

    //COOP ASSISTANT SOA BILLS
    public void clearCoopAssistantSOABills (){
        cacheCoopAssistantBills.evictAll();
    }


    public List<CoopAssistantBills> getCoopAssistantSOABills(){
        List<CoopAssistantBills> coopsoabills = new ArrayList<>();

        synchronized (cacheCoopAssistantBills) {
            Set<Integer> keySet = cacheCoopAssistantBills.snapshot().keySet();
            for(Integer key : keySet) {
                CoopAssistantBills soabills = cacheCoopAssistantBills.get(key);
                coopsoabills.add(soabills);
            }
        }

        return coopsoabills;
    }

    //COOP ASSISTANT PAYMENTS
    public void saveCoopAssistantPayments (List<CoopAssistantPayments> cooppayments){
        cacheCoopAssistantPayments.evictAll();
        for (CoopAssistantPayments payments : cooppayments){
            cacheCoopAssistantPayments.put(payments.getID(), payments);
        }
    }

    public List<CoopAssistantPayments> getCoopAssistantPayments(){
        List<CoopAssistantPayments> cooppayments = new ArrayList<>();

        synchronized (cacheCoopAssistantPayments) {
            Set<Integer> keySet = cacheCoopAssistantPayments.snapshot().keySet();
            for(Integer key : keySet) {
                CoopAssistantPayments payments = cacheCoopAssistantPayments.get(key);
                cooppayments.add(payments);
            }
        }

        return cooppayments;
    }

    //COOP ASSISTANT LOAN TRANSACTIONS
    public void saveCoopAssistantLoanTransactions (List<CoopAssistantLoanTransactions> cooploantransactions){
        cacheCoopAssistantLoanTransactions.evictAll();
        for (CoopAssistantLoanTransactions loanTransactions : cooploantransactions){
            cacheCoopAssistantLoanTransactions.put(loanTransactions.getID(), loanTransactions);
        }
    }

    public List<CoopAssistantLoanTransactions> getCoopAssistantLoanTransactions(){
        List<CoopAssistantLoanTransactions> cooploantransactions = new ArrayList<>();

        synchronized (cacheCoopAssistantLoanTransactions) {
            Set<Integer> keySet = cacheCoopAssistantLoanTransactions.snapshot().keySet();
            for(Integer key : keySet) {
                CoopAssistantLoanTransactions loantransactions = cacheCoopAssistantLoanTransactions.get(key);
                cooploantransactions.add(loantransactions);
            }
        }

        return cooploantransactions;
    }

    //COOP ASSISTANT TRANSACTION HISTORY
    public void saveCoopAssistantTransactionHistory(List<CoopAssistantTransactionHistory> list){
        cachecoopAssistantTransactionHistory.evictAll();
        for (CoopAssistantTransactionHistory coopAssistantTransactionHistory : list){
            cachecoopAssistantTransactionHistory.put(coopAssistantTransactionHistory.getID(), coopAssistantTransactionHistory);
        }
    }

    public List<CoopAssistantTransactionHistory> getCoopAssistantTransactionHistory(){
        List<CoopAssistantTransactionHistory> list = new ArrayList<>();

        synchronized (cachecoopAssistantTransactionHistory) {
            Set<Integer> keySet = cachecoopAssistantTransactionHistory.snapshot().keySet();
            for(Integer key : keySet) {
                CoopAssistantTransactionHistory coopAssistantTransactionHistory = cachecoopAssistantTransactionHistory.get(key);
                list.add(coopAssistantTransactionHistory);
            }
        }

        return list;
    }

    public void removeCoopAssistantTransactionHistory(){
        cachecoopAssistantTransactionHistory.evictAll();
    }

    //GK EARN STOCKIST
    public void saveGKEarnStockistPackage (List<GKEarnStockistPackage> list) {
        cacheGKEarnStockistPackage.evictAll();
        for (GKEarnStockistPackage gkearnstockistpackage : list){
            cacheGKEarnStockistPackage.put(gkearnstockistpackage.getID(), gkearnstockistpackage);
        }
    }

    public List<GKEarnStockistPackage> getGKEarnStockistPackage() {

        List<GKEarnStockistPackage> list = new ArrayList<>();

        synchronized (cacheGKEarnStockistPackage) {
            Set<Integer> keySet = cacheGKEarnStockistPackage.snapshot().keySet();
            for(Integer key : keySet) {
                GKEarnStockistPackage gkearnstockistpackage = cacheGKEarnStockistPackage.get(key);
                list.add(gkearnstockistpackage);
            }
        }

        return list;
    }

    //GKEARN TOP UP HISTORY
    public void saveGKEarnTopUpHistory (List<GKEarnTopUpHistory> gettopuphistory){
        cacheGKEarnTopUpHistory.evictAll();
        for (GKEarnTopUpHistory gkearntopuphistory : gettopuphistory){
            cacheGKEarnTopUpHistory.put(gkearntopuphistory.getID(), gkearntopuphistory);
        }
    }

    public List<GKEarnTopUpHistory> getGKEarnTopUpHistory() {
        List<GKEarnTopUpHistory> gkearntopuphistoryList = new ArrayList<>();

        synchronized (cacheGKEarnTopUpHistory) {
            Set<Integer> keySet = cacheGKEarnTopUpHistory.snapshot().keySet();
            for(Integer key : keySet) {
                GKEarnTopUpHistory gkearntopuphistory = cacheGKEarnTopUpHistory.get(key);
                gkearntopuphistoryList.add(gkearntopuphistory);
            }
        }

        return gkearntopuphistoryList;
    }

    public void removeGKEarnTopUpHistory (){
        cacheGKEarnTopUpHistory.evictAll();
    }

    //ACCREDIT BANKS
    public void saveAccreditedBanks (List<AccreditedBanks> accreditedBanksList){
        cacheAccreditedBanks.evictAll();
        for(AccreditedBanks banks : accreditedBanksList){
            cacheAccreditedBanks.put(banks.getID(), banks);
        }
    }

    public List<AccreditedBanks> getAccreditedBanks() {
        List<AccreditedBanks> accreditedBanksList = new ArrayList<>();
        synchronized (cacheAccreditedBanks){
            Set<Integer> keySet = cacheAccreditedBanks.snapshot().keySet();
            for(Integer key : keySet){
                AccreditedBanks banks = cacheAccreditedBanks.get(key);
                accreditedBanksList.add(banks);
            }
        }

        return accreditedBanksList;
    }

    //SUBSCRIBER BANK ACCOUNTS
    public void saveSubscriberBankAccounts (List<SubscriberBankAccounts> subscriberBankAccountsList){
        cacheSubscriberBankAccounts.evictAll();
        for(SubscriberBankAccounts bankaccounts : subscriberBankAccountsList){
            cacheSubscriberBankAccounts.put(bankaccounts.getID(), bankaccounts);
        }
    }

    public List<SubscriberBankAccounts> getSubscriberBankAccounts(){
        List<SubscriberBankAccounts> subscriberBankAccountsList = new ArrayList<>();
        synchronized (cacheSubscriberBankAccounts){
            Set<Integer> keySet = cacheSubscriberBankAccounts.snapshot().keySet();
            for(Integer key : keySet){
                SubscriberBankAccounts bankaccounts = cacheSubscriberBankAccounts.get(key);
                subscriberBankAccountsList.add(bankaccounts);
            }
        }

        return subscriberBankAccountsList;
    }

    //BANK DEPOSIT HISTORY
    public void saveBankDepositHistory(List<BankDepositHistoryQueue> bankDepositHistoryList){
        cacheBankDepositHistory.evictAll();
        for(BankDepositHistoryQueue bankdeposit : bankDepositHistoryList){
            cacheBankDepositHistory.put(bankdeposit.getID(), bankdeposit);
        }
    }

    public List<BankDepositHistoryQueue> getBankDepositHistory(){
        List<BankDepositHistoryQueue> bankDepositHistoryList = new ArrayList<>();
        synchronized (cacheBankDepositHistory){
            Set<Integer> keySet = cacheBankDepositHistory.snapshot().keySet();
            for(Integer key : keySet){
                BankDepositHistoryQueue bankdeposit = cacheBankDepositHistory.get(key);
                bankDepositHistoryList.add(bankdeposit);
            }
        }

        return bankDepositHistoryList;
    }
    //BANK DEPOSIT QUEUE
    public void saveBankDepositQueue(List<BankDepositHistoryQueue> bankDepositQueueList){
        cacheBankDepositQueue.evictAll();
        for(BankDepositHistoryQueue bankdeposit : bankDepositQueueList){
            cacheBankDepositQueue.put(bankdeposit.getID(), bankdeposit);
        }
    }

    public List<BankDepositHistoryQueue> getBankDepositQueue(){
        List<BankDepositHistoryQueue> bankDepositQueueList = new ArrayList<>();
        synchronized (cacheBankDepositQueue){
            Set<Integer> keySet = cacheBankDepositQueue.snapshot().keySet();
            for(Integer key : keySet){
                BankDepositHistoryQueue bankdeposit = cacheBankDepositQueue.get(key);
                bankDepositQueueList.add(bankdeposit);
            }
        }

        return bankDepositQueueList;
    }

    //EGAME
    public void saveEGameTransactions (List<EGameTransactions> eGameTransactionsList){
        cacheEGameTransactions.evictAll();
        for(EGameTransactions egametransactions : eGameTransactionsList){
            cacheEGameTransactions.put(egametransactions.getID(), egametransactions);
        }
    }

    public void removeEGameTransactions(){
        cacheEGameTransactions.evictAll();
    }

    public List<EGameTransactions> getEGameTransactions(){
        List<EGameTransactions> eGameTransactionsList = new ArrayList<>();
        synchronized (cacheEGameTransactions){
            Set<Integer> keySet = cacheEGameTransactions.snapshot().keySet();
            for(Integer key : keySet){
                EGameTransactions egametransactions = cacheEGameTransactions.get(key);
                eGameTransactionsList.add(egametransactions);
            }
        }

        return eGameTransactionsList;
    }

    //GK EARN (REFERRAL RANDOM)
    public void saveGKEarnReferralRandom(List<GKEarnReferralRandom> gkEarnReferralRandomList){
        cacheGKEarnReferralRandom.evictAll();
        for(GKEarnReferralRandom gkEarnReferralRandom : gkEarnReferralRandomList){
            cacheGKEarnReferralRandom.put(gkEarnReferralRandom.getID(), gkEarnReferralRandom);
        }
    }

    public void removeGKEarnReferralRandom(){
        cacheGKEarnReferralRandom.evictAll();
    }

    public List<GKEarnReferralRandom> getGKEarnReferralRandom(){
        List<GKEarnReferralRandom> gkEarnReferralRandomList = new ArrayList<>();
        synchronized (cacheGKEarnReferralRandom){
            Set<Integer> keySet = cacheGKEarnReferralRandom.snapshot().keySet();
            for(Integer key : keySet){
                GKEarnReferralRandom gkEarnReferralRandom = cacheGKEarnReferralRandom.get(key);
                gkEarnReferralRandomList.add(gkEarnReferralRandom);
            }
        }
        return gkEarnReferralRandomList;
    }

}
