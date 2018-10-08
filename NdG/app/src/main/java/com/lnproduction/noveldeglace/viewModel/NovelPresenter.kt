package com.lnproduction.noveldeglace.viewModel

import com.lnproduction.noveldeglace.activity.NovelFragment
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.retrofit.GetNovelDetails
import com.lnproduction.noveldeglace.retrofit.RetrofitInstance
import com.lnproduction.noveldeglace.utils.SchedulersFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovelPresenter(private val schedulersFactory: SchedulersFactory) : BasePresenter<NovelFragment>() {

    fun getNovelDetails(novelId : Int){
        val service : GetNovelDetails = RetrofitInstance.getRetrofitInstance()!!.create(GetNovelDetails::class.java)
        val call = service.getDetails(novelId)

        call.enqueue( object : Callback<NovelDetail> {
            override fun onFailure(call: Call<NovelDetail>, t: Throwable) {
                view?.getErrorMessage(t.localizedMessage)
            }

            override fun onResponse(call: Call<NovelDetail>, response: Response<NovelDetail>) {
                view?.getNovelDetails(response.body()!!)
            }

        })
    }
}