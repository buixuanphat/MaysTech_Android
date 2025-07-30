package com.example.maystech.data.model;

import com.google.gson.annotations.SerializedName;

public class ShippingFeeResponse {
    @SerializedName("total")
    private int total;

    @SerializedName("service_fee")
    private int serviceFee;

    @SerializedName("insurance_fee")
    private int insuranceFee;

    @SerializedName("pick_station_fee")
    private int pickStationFee;

    @SerializedName("coupon_value")
    private int couponValue;

    @SerializedName("r2s_fee")
    private int r2sFee;

    @SerializedName("return_again")
    private int returnAgain;

    @SerializedName("document_return")
    private int documentReturn;

    @SerializedName("double_check")
    private int doubleCheck;

    @SerializedName("cod_fee")
    private int codFee;

    @SerializedName("pick_remote_areas_fee")
    private int pickRemoteAreasFee;

    @SerializedName("deliver_remote_areas_fee")
    private int deliverRemoteAreasFee;

    @SerializedName("cod_failed_fee")
    private int codFailedFee;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(int serviceFee) {
        this.serviceFee = serviceFee;
    }

    public int getInsuranceFee() {
        return insuranceFee;
    }

    public void setInsuranceFee(int insuranceFee) {
        this.insuranceFee = insuranceFee;
    }

    public int getPickStationFee() {
        return pickStationFee;
    }

    public void setPickStationFee(int pickStationFee) {
        this.pickStationFee = pickStationFee;
    }

    public int getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(int couponValue) {
        this.couponValue = couponValue;
    }

    public int getR2sFee() {
        return r2sFee;
    }

    public void setR2sFee(int r2sFee) {
        this.r2sFee = r2sFee;
    }

    public int getReturnAgain() {
        return returnAgain;
    }

    public void setReturnAgain(int returnAgain) {
        this.returnAgain = returnAgain;
    }

    public int getDocumentReturn() {
        return documentReturn;
    }

    public void setDocumentReturn(int documentReturn) {
        this.documentReturn = documentReturn;
    }

    public int getDoubleCheck() {
        return doubleCheck;
    }

    public void setDoubleCheck(int doubleCheck) {
        this.doubleCheck = doubleCheck;
    }

    public int getCodFee() {
        return codFee;
    }

    public void setCodFee(int codFee) {
        this.codFee = codFee;
    }

    public int getPickRemoteAreasFee() {
        return pickRemoteAreasFee;
    }

    public void setPickRemoteAreasFee(int pickRemoteAreasFee) {
        this.pickRemoteAreasFee = pickRemoteAreasFee;
    }

    public int getDeliverRemoteAreasFee() {
        return deliverRemoteAreasFee;
    }

    public void setDeliverRemoteAreasFee(int deliverRemoteAreasFee) {
        this.deliverRemoteAreasFee = deliverRemoteAreasFee;
    }

    public int getCodFailedFee() {
        return codFailedFee;
    }

    public void setCodFailedFee(int codFailedFee) {
        this.codFailedFee = codFailedFee;
    }
}
