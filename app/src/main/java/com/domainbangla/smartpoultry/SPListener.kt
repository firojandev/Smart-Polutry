package com.domainbangla.smartpoultry

import com.domainbangla.smartpoultry.model.ResponseData

interface SPListener {
    fun onResponse(responseData: ResponseData)
}