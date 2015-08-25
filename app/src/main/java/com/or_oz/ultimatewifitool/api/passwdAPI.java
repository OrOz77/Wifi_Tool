package com.or_oz.ultimatewifitool.api;

import com.or_oz.ultimatewifitool.model.passwdModel;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by oroz7_000 on 8/24/2015.
 */
//interface for retrofit
public interface passwdAPI {
    @GET("/api/{version}/get_password.{format}?length={length)")
    public void getPass(@Path("version")Double version,@Path("format")String format,
                        @Query("length")int length, Callback<passwdModel> response);
}
