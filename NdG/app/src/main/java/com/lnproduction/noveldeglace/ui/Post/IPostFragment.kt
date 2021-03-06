package com.lnproduction.noveldeglace.ui.post

import com.lnproduction.noveldeglace.model.Post

interface IPostFragment {

    fun getErrorMessage(errorMessage: String)

    fun getPosts(postsList: ArrayList<Post>?)

}