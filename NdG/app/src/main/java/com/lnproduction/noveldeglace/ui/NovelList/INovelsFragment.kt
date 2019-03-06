package com.lnproduction.noveldeglace.ui.NovelList

import com.lnproduction.noveldeglace.model.Novel

interface INovelsFragment {

    fun getErrorMessage(errorMessage : String)

    fun getNovels(novelLists : ArrayList<Novel>?)
}