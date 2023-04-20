package com.domainbangla.smartpoultry.networking

import com.domainbangla.smartpoultry.model.ResponseData
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiRoutes {
    @GET("get-temp")
    fun getResponseData(): Call<ResponseData>?

    @POST("config")
    @FormUrlEncoded
    fun configure(
        @Field("min_temp") minTemp:Int,
        @Field("max_temp") maxTemp:Int
    ): Call<ResponseData>?
}