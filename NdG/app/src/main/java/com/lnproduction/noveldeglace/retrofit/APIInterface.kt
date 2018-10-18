package com.lnproduction.noveldeglace.retrofit

import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {

    @GET("roman-detail/v1/romanid/{novelId}?_embed")
    fun getDetails(@Path("novelId") novelId: Int): Call<NovelDetail>

    @GET("wp/v2/romans-api?per_page=100")
    fun getNovels(): Call<ArrayList<Novel>>

    @GET("wp/v2/posts?per_page=100")
    fun getPosts(): Call<ArrayList<Post>>
}