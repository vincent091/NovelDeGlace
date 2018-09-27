package com.lnproduction.noveldeglace.activity

import com.lnproduction.noveldeglace.model.Novel

interface IMainActivity {

    fun getErrorMessage(errorMessage : String)

    fun getNovels(titleNovel : ArrayList<Novel>?)
}