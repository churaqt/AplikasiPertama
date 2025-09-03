package edu.uph.m23si1.aplikasipertama.model;
import retrofit2.http.GET;

public class Kota {
    private String code;
    private String name;

    public Kota(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() { return name; }

    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return name;
    }
}
