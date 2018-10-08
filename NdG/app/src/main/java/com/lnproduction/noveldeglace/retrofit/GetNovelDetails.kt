package com.lnproduction.noveldeglace.retrofit


import com.lnproduction.noveldeglace.model.NovelDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GetNovelDetails {
    @GET("roman-detail/v1/romanid/{novelId}?_embed")
    fun getDetails(@Path("novelId") novelId: Int): Call<NovelDetail>
}