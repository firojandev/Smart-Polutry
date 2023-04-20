package com.domainbangla.smartpoultry.networking

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.domainbangla.smartpoultry.SPListener
import com.domainbangla.smartpoultry.SpConstant
import com.domainbangla.smartpoultry.model.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ApiService {
    const val TAG = "ApiService"
    fun getResponse(context: Context,spListener: SPListener) {
        var retrofitClient: RetrofitClient? = RetrofitClient(SpConstant.API_URL)
        val service: ApiRoutes = retrofitClient!!.client!!.create(ApiRoutes::class.java)
        service.getResponseData()
            ?.enqueue(object :
                Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>,
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            spListener.onResponse(data)
                        }else{
                            Log.e(TAG, "Response null!")
                        }
                    } else {
                        Log.e(TAG, "Response is successful!")
                    }
                }
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                }

            })
    }

    fun configure(context: Context,minTemp:Int,maxTemp:Int) {
        var retrofitClient: RetrofitClient? = RetrofitClient(SpConstant.API_URL)
        val service: ApiRoutes = retrofitClient!!.client!!.create(ApiRoutes::class.java)
        service.configure(minTemp,maxTemp)
            ?.enqueue(object :
                Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>,
                ) {
                    if (response.isSuccessful) {
                       showMessage(context,"Saved successfully")
                    } else {
                        showMessage(context,"Failed to save")
                    }
                }
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    showMessage(context,"Failed to save:${t.message.toString()}")
                }

            })
    }

    fun showMessage(context: Context,msg:String){
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }

}