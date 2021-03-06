package com.lnproduction.noveldeglace.ui.novelDetails

import android.content.Intent
import com.google.gson.Gson
import com.lnproduction.noveldeglace.ui.chapterNovel.WebViewActivity
import com.lnproduction.noveldeglace.base.BasePresenter
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.model.NovelDetail
import com.lnproduction.noveldeglace.di.retrofit.APIInterface
import com.lnproduction.noveldeglace.di.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovelPresenter() : BasePresenter<NovelFragment>() {

    lateinit var novel : Novel

    fun getNovelDetails(json : String)
    {
        val gson = Gson()
        novel = gson.fromJson(json,Novel::class.java)
        val service = RetrofitInstance.getRetrofitInstance(view?.context)!!.create(APIInterface::class.java)
        val call = service.getDetails(novel.novelId)

        call.enqueue( object : Callback<NovelDetail> {
            override fun onFailure(call: Call<NovelDetail>, t: Throwable) {
                view?.getErrorMessage(t.localizedMessage)
            }

            override fun onResponse(call: Call<NovelDetail>, response: Response<NovelDetail>) {
                if(response.isSuccessful)
                    view?.getNovelDetails(response.body()!!)
                else
                    view?.getErrorMessage(response.errorBody().toString())
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