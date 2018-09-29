package com.lnproduction.noveldeglace.retrofit

import com.lnproduction.noveldeglace.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface GetPostsList {

    @GET("posts?per_page=100")
    fun getPosts(): Call<ArrayList<Post>>
}