package com.lnproduction.noveldeglace.viewModel

import com.lnproduction.noveldeglace.activity.IPostFragment
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.retrofit.GetPostsList
import com.lnproduction.noveldeglace.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFragmentPresenter(private val postFragment: IPostFragment): IPostFragmentPresenter {

    override fun onCreate() {
        getPostsList()
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    private fun getPostsList() {
        val service : GetPostsList = RetrofitInstance.getRetrofitInstance()!!.create(GetPostsList::class.java)
        val call = service.getPosts()

        call.enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(call: Call<ArrayList<Post>>, response: Response<ArrayList<Post>>) {
                postFragment.getPosts(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                postFragment.getErrorMessage(t.localizedMessage)
            }
        })
    }
}