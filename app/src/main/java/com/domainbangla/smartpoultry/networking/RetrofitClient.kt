package com.domainbangla.smartpoultry.networking

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

class RetrofitClient (Base_URL:String) {
    private var retrofit: Retrofit? = null
    var Base_URL = Base_URL

    val client: Retrofit?
        get() {
            if (retrofit == null) {

                val client = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.MINUTES)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(Base_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

}