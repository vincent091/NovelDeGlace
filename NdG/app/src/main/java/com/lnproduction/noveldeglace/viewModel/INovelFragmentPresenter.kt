package com.lnproduction.noveldeglace.viewModel

interface INovelFragmentPresenter {

    fun onCreate()

    fun onResume()

    fun onPause()

    fun getNovelList(categorieId: Int)
}