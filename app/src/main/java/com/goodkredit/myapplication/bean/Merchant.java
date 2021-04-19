package com.goodkredit.myapplication.bean;

import java.io.Serializable;

/**
 * Created by Ban_Lenovo on 1/4/2017.
 */

public class Merchant implements Serializable {

    private String merchantID;
    private String branchID;
    private String merchantName;
    private String branchName;
    private String merchantLogo;
    private String streetAddress;
    private String province;
    private String country;
    private String telephone;
    private String mobile;
    private String numOfOutlets;
    private String city;
    private String longitude;
    private String latitude;
    private String isFeatured;
    private String featuredPath;
    private String merchantGroup;
    private String zip;
    private String email;
    private String natureofbusiness;

    public Merchant(String merchantName, String merchantID, String branchName, String branchID, String merchantLogo) {
        this.merchantName = merchantName;
        this.merchantID = merchantID;
        this.branchID = branchID;
        this.branchName = branchName;
        this.merchantLogo = merchantLogo;
    }

    public Merchant(String merchantID, String branchID) {
        this.merchantID = merchantID;
        this.branchID = branchID;
    }

    public Merchant(String merchantID, String branchID, String merchantName, String branchName, String merchantLogo, String streetAddress, String province, String country, String telephone, String mobile, String numOfOutlets, String city) {
        this.merchantID = merchantID;
        this.branchID = branchID;
        this.merchantName = merchantName;
        this.branchName = branchName;
        this.merchantLogo = merchantLogo;
        this.streetAddress = streetAddress;
        this.province = province;
        this.country = country;
        this.telephone = telephone;
        this.mobile = mobile;
        this.numOfOutlets = numOfOutlets;
        this.city = city;
    }

    public Merchant(String merchantID, String branchID, String merchantName, String branchName, String merchantLogo, String streetAddress, String province, String country, String telephone, String mobile, String numOfOutlets, String city, String longitude, String latitude, String isFeatured, String featuredPath, String merchantGroup) {
        this.merchantID = merchantID;
        this.branchID = branchID;
        this.merchantName = merchantName;
        this.branchName = branchName;
        this.merchantLogo = merchantLogo;
        this.streetAddress = streetAddress;
        this.province = province;
        this.country = country;
        this.telephone = telephone;
        this.mobile = mobile;
        this.numOfOutlets = numOfOutlets;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isFeatured = isFeatured;
        this.featuredPath = featuredPath;
        this.merchantGroup = merchantGroup;
    }

    public Merchant(String merchantID, String branchID, String merchantName, String branchName, String merchantLogo,
                    String streetAddress, String province, String country, String telephone,
                    String mobile, String numOfOutlets, String city,
                    String longitude, String latitude, String isFeatured, String featuredPath,
                    String merchantGroup, String zip, String email, String natureofbusiness) {

        this.merchantID = merchantID;
        this.branchID = branchID;
        this.merchantName = merchantName;
        this.branchName = branchName;
        this.merchantLogo = merchantLogo;
        this.streetAddress = streetAddress;
        this.province = province;
        this.country = country;
        this.telephone = telephone;
        this.mobile = mobile;
        this.numOfOutlets = numOfOutlets;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isFeatured = isFeatured;
        this.featuredPath = featuredPath;
        this.merchantGroup = merchantGroup;
        this.zip = zip;
        this.email = email;
        this.natureofbusiness = natureofbusiness;
    }

    public String getMerchantGroup() {
        return merchantGroup;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(String isFeatured) {
        this.isFeatured = isFeatured;
    }

    public String getFeaturedPath() {
        return featuredPath;
    }

    public void setFeaturedPath(String featuredPath) {
        this.featuredPath = featuredPath;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNumOfOutlets() {
        return numOfOutlets;
    }

    public void setNumOfOutlets(String numOfOutlets) {
        this.numOfOutlets = numOfOutlets;
    }

    public String getMerchatInitials() {
        String[] temp = getMerchantName().split("\\s");
        String result = "";
        for (int x = 0; x < temp.length; x++) {
            if (temp[x] != null && !temp[x].equals("") && !temp[x].equals(" ")) {
                result += (temp[x].charAt(0));
            }
        }
        if (result.length() >= 2) {
            result = result.substring(0, 2);
        }
        return result;
    }

    public String getMerchantLogo() {
        return merchantLogo;
    }

    public void setMerchantLogo(String mechantLogo) {
        this.merchantLogo = mechantLogo;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNatureofBusiness() {
        return natureofbusiness;
    }

    public void setNatureofBusiness(String natureofbusiness) {
        this.natureofbusiness = natureofbusiness;
    }
}
