package edu.uph.m23si1.aplikasipertama.model;

import retrofit2.http.GET;

public class Provinsi {
    private String code, name;

    public Provinsi(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

