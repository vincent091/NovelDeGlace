package com.lnproduction.noveldeglace.viewModel

import com.lnproduction.noveldeglace.activity.IMainActivity
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.retrofit.GetNovelsList
import com.lnproduction.noveldeglace.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(private val mainActivity: IMainActivity) : IMainActivityPresenter {

    override fun onCreate() {
        getNovelList()
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    private fun getNovelList() {
        val service : GetNovelsList = RetrofitInstance.getRetrofitInstance()!!.create(GetNovelsList::class.java)
        val call = service.getNovels()

        call.enqueue(object : Callback<ArrayList<Novel>> {
            override fun onResponse(call: Call<ArrayList<Novel>>, response: Response<ArrayList<Novel>>) {
                mainActivity.getNovels(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Novel>>, t: Throwable) {
                mainActivity.getErrorMessage(t.localizedMessage)
            }
        })
    }
}