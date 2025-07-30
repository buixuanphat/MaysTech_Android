package com.example.maystech.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingFeeRequest {
    @SerializedName("from_district_id")
    private int fromDistrictId;

    @SerializedName("from_ward_code")
    private String fromWardCode;

    @SerializedName("service_id")
    private int serviceId;

    @SerializedName("service_type_id")
    private Integer serviceTypeId; // nullable

    @SerializedName("to_district_id")
    private int toDistrictId;

    @SerializedName("to_ward_code")
    private String toWardCode;

    @SerializedName("height")
    private int height;

    @SerializedName("length")
    private int length;

    @SerializedName("weight")
    private int weight;

    @SerializedName("width")
    private int width;

    @SerializedName("insurance_value")
    private int insuranceValue;

    @SerializedName("cod_failed_amount")
    private int codFailedAmount;

    @SerializedName("coupon")
    private String coupon; // nullable

    @SerializedName("items")
    private List<Object> items;

    public int getFromDistrictId() {
        return fromDistrictId;
    }

    public void setFromDistrictId(int fromDistrictId) {
        this.fromDistrictId = fromDistrictId;
    }

    public String getFromWardCode() {
        return fromWardCode;
    }

    public void setFromWardCode(String fromWardCode) {
        this.fromWardCode = fromWardCode;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public int getToDistrictId() {
        return toDistrictId;
    }

    public void setToDistrictId(int toDistrictId) {
        this.toDistrictId = toDistrictId;
    }

    public String getToWardCode() {
        return toWardCode;
    }

    public void setToWardCode(String toWardCode) {
        this.toWardCode = toWardCode;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(int insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public int getCodFailedAmount() {
        return codFailedAmount;
    }

    public void setCodFailedAmount(int codFailedAmount) {
        this.codFailedAmount = codFailedAmount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
