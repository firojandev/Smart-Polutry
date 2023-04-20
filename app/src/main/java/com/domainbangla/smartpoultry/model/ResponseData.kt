package com.domainbangla.smartpoultry.model

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("id") val id: Int,
    @SerializedName("current_temp") val currenTemp: Int,
    @SerializedName("fan_status") val fanStatus: Int,
    @SerializedName("min_temp") val minTemp: Int,
    @SerializedName("max_temp") val maxTemp: Int,
    @SerializedName("updated_at") val updatedAt: String,
)
