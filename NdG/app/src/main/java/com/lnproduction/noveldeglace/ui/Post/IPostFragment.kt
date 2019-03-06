package com.lnproduction.noveldeglace.ui.Post

import com.lnproduction.noveldeglace.model.Post

interface IPostFragment {

    fun getErrorMessage(errorMessage: String)

    fun getPosts(postsList: ArrayList<Post>?)

}