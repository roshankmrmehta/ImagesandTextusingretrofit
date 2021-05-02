package com.example.imagesandtextusingretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("images/")
    Call<List<ImagesResponse>> getAllImages();
}
