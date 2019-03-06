package com.lnproduction.noveldeglace.ui.novelDetails

import com.lnproduction.noveldeglace.model.NovelDetail

interface NovelDetailsView {

    fun getErrorMessage(errorMessage : String)

    fun getNovelDetails(novelDetail : NovelDetail)
}