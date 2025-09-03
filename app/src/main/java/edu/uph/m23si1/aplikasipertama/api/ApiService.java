package edu.uph.m23si1.aplikasipertama.api;

import java.util.List;

import edu.uph.m23si1.aplikasipertama.api.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import edu.uph.m23si1.aplikasipertama.model.Kota;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/api/provinces.json")
    Call<ApiResponse> getProvinces();

    @GET("/api/regencies/{province_id}.json")
    Call<ApiResponse> getRegencies(@Path("province_id") String provinceId);
}

