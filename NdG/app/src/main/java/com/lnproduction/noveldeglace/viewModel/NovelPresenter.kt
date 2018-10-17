package com.lnproduction.noveldeglace.viewModel

import android.content.Intent
import com.google.gson.Gson
import com.lnproduction.noveldeglace.activity.NovelFragment
import com.lnproduction.noveldeglace.activity.WebViewActivity
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.retrofit.GetNovelDetails
import com.lnproduction.noveldeglace.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovelPresenter() : BasePresenter<NovelFragment>() {

    lateinit var novel : Novel

    fun getNovelDetails(json : String)
    {
        val gson = Gson()
        novel = gson.fromJson(json,Novel::class.java)
        val service : GetNovelDetails = RetrofitInstance.getRetrofitInstance()!!.create(GetNovelDetails::class.java)
        val call = service.getDetails(novel.novelId)

        call.enqueue( object : Callback<NovelDetail> {
            override fun onFailure(call: Call<NovelDetail>, t: Throwable) {
                view?.getErrorMessage(t.localizedMessage)
            }

            override fun onResponse(call: Call<NovelDetail>, response: Response<NovelDetail>) {
                view?.getNovelDetails(response.body()!!)
            }

        })
    }

    fun openWebView(url : String)
    {
        val intent = Intent(view?.context, WebViewActivity::class.java)
        intent.putExtra("chapter_url",url)
        view?.startActivity(intent)
    }
}