package com.lnproduction.noveldeglace.retrofit

import com.lnproduction.noveldeglace.model.Novel
import retrofit2.Call
import retrofit2.http.GET

interface GetNovelsList {

    @GET("romans-api?per-page=100")
    fun getNovels(): Call<ArrayList<Novel>>
}