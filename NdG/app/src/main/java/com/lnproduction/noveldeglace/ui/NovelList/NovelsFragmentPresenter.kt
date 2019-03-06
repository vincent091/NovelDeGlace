package com.lnproduction.noveldeglace.ui.novelList

import com.google.gson.Gson
import com.lnproduction.noveldeglace.base.BasePresenter
import com.lnproduction.noveldeglace.model.Novel
import com.lnproduction.noveldeglace.di.retrofit.APIInterface
import com.lnproduction.noveldeglace.di.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class NovelsFragmentPresenter() : BasePresenter<NovelsFragment>() {

    fun getNovelList( categorieId : Int) {
        val service = RetrofitInstance.getRetrofitInstance(view?.context)!!.create(APIInterface::class.java)
        val call = service.getNovels()

        call.enqueue(object : Callback<ArrayList<Novel>> {
            override fun onResponse(call: Call<ArrayList<Novel>>, response: Response<ArrayList<Novel>>) {
                if(response.isSuccessful){
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
                    view?.getNovels(filteredNovelsList)
                }
                else
                    view?.getErrorMessage(response.errorBody().toString())
            }

            override fun onFailure(call: Call<ArrayList<Novel>>, t: Throwable) {
                view?.getErrorMessage(t.localizedMessage)
            }
        })
    }

    fun filterByRadioButton(checkedRadioButtonIndex: Int) {
        when {
            checkedRadioButtonIndex == 0 -> getNovelList(-1)
            checkedRadioButtonIndex == 1 -> getNovelList(73)
            checkedRadioButtonIndex == 2 -> getNovelList(74)
            checkedRadioButtonIndex == 3 -> getNovelList(265)
            checkedRadioButtonIndex == 4 -> getNovelList(-2)
        }

    }

    fun serializeResponse(item : Novel) : String{
        val json = Gson()
        return json.toJson(item)
    }
}