package com.goodkredit.myapplication.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by User-PC on 1/24/2018.
 */

public class AddressList {
    @SerializedName("Province")
    @Expose
    private String Province;
    @SerializedName("City")
    @Expose
    private String City;
    @SerializedName("Zipcode")
    @Expose
    private String Zipcode;

    public AddressList(String province, String city, String zipcode) {
        Province = province;
        City = city;
        Zipcode = zipcode;
    }

    public String getProvince() {
        return Province;
    }

    public String getCity() {
        return City;
    }

    public String getZipcode() {
        return Zipcode;
    }
}
