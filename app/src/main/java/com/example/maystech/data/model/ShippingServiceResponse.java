package com.example.maystech.data.model;

import com.google.gson.annotations.SerializedName;

public class ShippingServiceResponse {
    @SerializedName("service_id")
    private int serviceId;

    @SerializedName("short_name")
    private String shortName;

    @SerializedName("service_type_id")
    private int serviceTypeId;

    @SerializedName("config_fee_id")
    private String configFeeId;

    @SerializedName("extra_cost_id")
    private String extraCostId;

    @SerializedName("standard_config_fee_id")
    private String standardConfigFeeId;

    @SerializedName("standard_extra_cost_id")
    private String standardExtraCostId;

    @SerializedName("ecom_config_fee_id")
    private int ecomConfigFeeId;

    @SerializedName("ecom_extra_cost_id")
    private int ecomExtraCostId;

    @SerializedName("ecom_standard_config_fee_id")
    private int ecomStandardConfigFeeId;

    @SerializedName("ecom_standard_extra_cost_id")
    private int ecomStandardExtraCostId;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getConfigFeeId() {
        return configFeeId;
    }

    public void setConfigFeeId(String configFeeId) {
        this.configFeeId = configFeeId;
    }

    public String getExtraCostId() {
        return extraCostId;
    }

    public void setExtraCostId(String extraCostId) {
        this.extraCostId = extraCostId;
    }

    public String getStandardConfigFeeId() {
        return standardConfigFeeId;
    }

    public void setStandardConfigFeeId(String standardConfigFeeId) {
        this.standardConfigFeeId = standardConfigFeeId;
    }

    public String getStandardExtraCostId() {
        return standardExtraCostId;
    }

    public void setStandardExtraCostId(String standardExtraCostId) {
        this.standardExtraCostId = standardExtraCostId;
    }

    public int getEcomConfigFeeId() {
        return ecomConfigFeeId;
    }

    public void setEcomConfigFeeId(int ecomConfigFeeId) {
        this.ecomConfigFeeId = ecomConfigFeeId;
    }

    public int getEcomExtraCostId() {
        return ecomExtraCostId;
    }

    public void setEcomExtraCostId(int ecomExtraCostId) {
        this.ecomExtraCostId = ecomExtraCostId;
    }

    public int getEcomStandardConfigFeeId() {
        return ecomStandardConfigFeeId;
    }

    public void setEcomStandardConfigFeeId(int ecomStandardConfigFeeId) {
        this.ecomStandardConfigFeeId = ecomStandardConfigFeeId;
    }

    public int getEcomStandardExtraCostId() {
        return ecomStandardExtraCostId;
    }

    public void setEcomStandardExtraCostId(int ecomStandardExtraCostId) {
        this.ecomStandardExtraCostId = ecomStandardExtraCostId;
    }
}
