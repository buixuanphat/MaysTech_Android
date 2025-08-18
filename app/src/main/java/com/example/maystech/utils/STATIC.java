package com.example.maystech.utils;

import java.text.DecimalFormat;

public class STATIC {
    public static final String KEY_TOKEN = "token";
    public static final String PREF_NAME = "my_pref";
    public static final String KEY_ID = "id";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_WARD = "ward";
    public static final String KEY_DISTRICT = "district";
    public static final String KEY_ADDRESS_DETAILS = "addressDetails";
    public static final String KEY_PROVINCE = "province";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_AVATAR = "avatar";

    public static final String PREPARING = "PREPARING";
    public static final String SHIPPING = "SHIPPING";
    public static final String DELIVERED = "DELIVERED";
    public static final String CANCELLED = "CANCELED";

    public static String formatPrice(double price)
    {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(price);
    }
}
