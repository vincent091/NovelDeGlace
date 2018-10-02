package com.lnproduction.noveldeglace.viewModel

import com.lnproduction.noveldeglace.activity.INovelsFragment
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.retrofit.GetNovelsList
import com.lnproduction.noveldeglace.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NovelsFragmentPresenter(private val novelsFragment: INovelsFragment) : INovelFragmentPresenter {

    override fun onCreate() {
        getNovelList(-1)
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun getNovelList( categorieId : Int) {
        val service : GetNovelsList = RetrofitInstance.getRetrofitInstance()!!.create(GetNovelsList::class.java)
        val call = service.getNovels()

        call.enqueue(object : Callback<ArrayList<Novel>> {
            override fun onResponse(call: Call<ArrayList<Novel>>, response: Response<ArrayList<Novel>>) {
                val filteredNovelsList : ArrayList<Novel> = ArrayList()
                for (novel : Novel in response.body()!!) {
                    when (categorieId) {
                        -1 -> if (novel.feature_media > 0) {
                            filteredNovelsList.add(novel)
                        }
                        -2 -> if (novel.feature_media == 0) {
                            filteredNovelsList.add(novel)
                        }
                        else -> if (novel.feature_media > 0 && novel.idCategories.get(0) == categorieId) {
                            filteredNovelsList.add(novel)
                        }
                    }
                }
                novelsFragment.getNovels(filteredNovelsList)
            }

            override fun onFailure(call: Call<ArrayList<Novel>>, t: Throwable) {
                novelsFragment.getErrorMessage(t.localizedMessage)
            }
        })
    }
}