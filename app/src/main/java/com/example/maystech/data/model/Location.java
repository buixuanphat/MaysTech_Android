package com.example.maystech.data.model;

import androidx.annotation.NonNull;

public class Location {
    private String name;
    private int code;

    public Location(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public Location() {
    }

    @NonNull
    @Override
    public String toString() {
        return  name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
