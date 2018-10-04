package com.lnproduction.noveldeglace.activity

interface LoginView {

    fun showProgress()

    fun hideProgress()

    fun onLoginSuccessful()

    fun showLoginError(errorMessage: String?)

    fun showPasswordError(errorMessage: String?)

    fun requestLoginFocus()

    fun requestPasswordFocus()
}