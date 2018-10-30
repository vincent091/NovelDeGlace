package com.lnproduction.noveldeglace.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.activity.LoginActivity
import com.lnproduction.noveldeglace.model.LoginCredentials
import com.lnproduction.noveldeglace.model.LoginValidator
import com.lnproduction.noveldeglace.model.Users
import com.lnproduction.noveldeglace.retrofit.APIInterface
import com.lnproduction.noveldeglace.retrofit.RetrofitInstance
import com.lnproduction.noveldeglace.utils.Constants
import com.lnproduction.noveldeglace.utils.ResourceProvider
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPresenter(private val resourceProvider: ResourceProvider, private val loginValidator: LoginValidator) : BasePresenter<LoginActivity>() {

    @SuppressLint("CheckResult")
    fun attemptLogin(loginCredentials: LoginCredentials) {
        if (!validateInputs(loginCredentials)) {
            return
        }

        view?.showProgress()

        val basic = Credentials.basic(loginCredentials.login,loginCredentials.password)
        val service = RetrofitInstance.getRetrofitInstance(view)?.create(APIInterface::class.java)
        val call = service?.getUserInfo(basic)

        call?.enqueue( object : Callback<Users> {
            override fun onFailure(call: Call<Users>, t: Throwable) {

            }
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if(response.isSuccessful) {
                    view?.showProgress(false)
                    val gson = Gson()
                    val userJson = gson.toJson(response.body())
                    val returnIntent = Intent()
                    updateUserInfos(userJson)
                    view?.setResult(Activity.RESULT_OK, returnIntent)
                    view?.finish()
                }
                else
                    view?.showProgress(false)
                    view?.showPasswordError(resourceProvider.getString(R.string.wrong_identifiant))
            }

        })
    }

    private fun updateUserInfos(userJson: String?) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(view)
        val edit = preferences.edit()
        edit.putString(Constants.SHARED_PREFS_USER,userJson)
        edit.apply()
    }

    private fun validateInputs(loginCredentials: LoginCredentials): Boolean {
        val validateLogin = validateLogin(loginCredentials)
        return validatePassword(loginCredentials) && validateLogin //XXX validateLogin is not inlined to avoid short circuit check
    }

    private fun validateLogin(loginCredentials: LoginCredentials): Boolean =
            if (loginValidator.validateLogin(loginCredentials.login)) {
                view?.showLoginError(null)
                true
            } else {
                view?.showLoginError(resourceProvider.getString(R.string.error_field_required))
                view?.requestLoginFocus()
                false
            }

    private fun validatePassword(loginCredentials: LoginCredentials): Boolean =
            if (loginValidator.validatePassword(loginCredentials.password)) {
                view?.showPasswordError(null)
                true
            } else {
                view?.showPasswordError(resourceProvider.getString(R.string.error_invalid_password))
                view?.requestPasswordFocus()
                false
            }
}