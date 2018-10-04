package com.lnproduction.noveldeglace.viewModel

internal interface Presenter<in T> {

    fun createView(view: T)

    fun destroyView()
}