package com.lnproduction.noveldeglace.retrofit

import com.google.gson.GsonBuilder
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.model.NovelDetailDeserializer
import com.lnproduction.noveldeglace.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitInstance {

    companion object {

        private fun createGsonConverter(): Converter.Factory {
            val gsonBuilder = GsonBuilder()
            gsonBuilder.registerTypeAdapter(NovelDetail::class.java, NovelDetailDeserializer())
            val gson = gsonBuilder.create()
            return GsonConverterFactory.create(gson)
        }

        private var retrofit: Retrofit? = null

        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

        /**
         * Create an instance of Retrofit object
         */
        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(createGsonConverter())
                        .client(okHttpClient)
                        .build()
            }
            return retrofit
        }
    }



}