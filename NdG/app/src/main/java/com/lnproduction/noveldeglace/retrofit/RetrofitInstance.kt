package com.lnproduction.noveldeglace.retrofit

import com.lnproduction.noveldeglace.utils.Constants
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



class RetrofitInstance {

    companion object {

        private var retrofit: Retrofit? = null

        /**
         * Create an instance of Retrofit object
         */
        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
    }



}