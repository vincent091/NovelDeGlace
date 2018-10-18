package com.lnproduction.noveldeglace.viewModel

import com.lnproduction.noveldeglace.activity.PostFragment
import com.lnproduction.noveldeglace.model.Post
import com.lnproduction.noveldeglace.retrofit.APIInterface
import com.lnproduction.noveldeglace.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostFragmentPresenter() : BasePresenter<PostFragment>() {

     fun getPostsList() {
        val service = RetrofitInstance.getRetrofitInstance(view?.context)!!.create(APIInterface::class.java)
        val call = service.getPosts()

        call.enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(call: Call<ArrayList<Post>>, response: Response<ArrayList<Post>>) {
                view?.getPosts(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {
                view?.getErrorMessage(t.localizedMessage)
            }
        })
    }
}