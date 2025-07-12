package com.example.maystech.data.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class District {
    @SerializedName("DistrictID")
    private int id;
    @SerializedName("DistrictName")
    private String name;

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
