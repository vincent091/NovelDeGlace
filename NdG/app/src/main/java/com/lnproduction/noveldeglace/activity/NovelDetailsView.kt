package com.lnproduction.noveldeglace.activity

import com.lnproduction.noveldeglace.model.NovelDetail

interface NovelDetailsView {

    fun getErrorMessage(errorMessage : String)

    fun getNovelDetails(novelDetail : NovelDetail)
}