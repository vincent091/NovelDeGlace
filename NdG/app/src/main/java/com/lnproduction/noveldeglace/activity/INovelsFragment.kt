package com.lnproduction.noveldeglace.activity

import com.lnproduction.noveldeglace.model.Novel

interface INovelsFragment {

    fun getErrorMessage(errorMessage : String)

    fun getNovels(novelLists : ArrayList<Novel>?)
}