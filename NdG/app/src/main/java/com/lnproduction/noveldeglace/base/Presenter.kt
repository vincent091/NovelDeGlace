package com.lnproduction.noveldeglace.base

internal interface Presenter<in T> {

    fun createView(view: T)

    fun destroyView()
}