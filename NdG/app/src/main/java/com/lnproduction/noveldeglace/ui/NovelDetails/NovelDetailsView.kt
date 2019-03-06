package com.lnproduction.noveldeglace.ui.NovelDetails

import com.lnproduction.noveldeglace.model.NovelDetail

interface NovelDetailsView {

    fun getErrorMessage(errorMessage : String)

    fun getNovelDetails(novelDetail : NovelDetail)
}