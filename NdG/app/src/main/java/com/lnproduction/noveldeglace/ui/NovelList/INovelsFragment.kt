package com.lnproduction.noveldeglace.ui.novelList

import com.lnproduction.noveldeglace.model.Novel

interface INovelsFragment {

    fun getErrorMessage(errorMessage : String)

    fun getNovels(novelLists : ArrayList<Novel>?)
}