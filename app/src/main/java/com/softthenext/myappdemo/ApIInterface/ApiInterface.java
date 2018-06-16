package com.softthenext.myappdemo.ApIInterface;

import com.softthenext.myappdemo.Model.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Dilip Ghawade
 * Organization Name SoftTheNext
 * on Date 13/06/2018.
 */
public interface ApiInterface {

    @GET("d5y1e")
    Call<List<ProductModel>> getCarlists();
}
