package com.lnproduction.noveldeglace.ui.Login

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import com.lnproduction.noveldeglace.R
import com.lnproduction.noveldeglace.base.BaseActivity
import com.lnproduction.noveldeglace.model.LoginCredentials
import com.lnproduction.noveldeglace.model.LoginValidator
import com.lnproduction.noveldeglace.utils.ResourceProvider
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Based on Google Login Screen example
 */
class LoginActivity : BaseActivity(), LoginView {

    //TODO @Inject
    private lateinit var loginPresenter: LoginPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        password.setOnEditorActionListener { _, id, _ ->
            when (id) {
                EditorInfo.IME_ACTION_DONE -> {
                    onSignInClick()
                    true
                }
                else -> false
            }
        }

        loginPresenter = LoginPresenter(ResourceProvider(resources), LoginValidator())
        loginPresenter.createView(this)

        email_sign_in_button.setOnClickListener {
            onSignInClick()
        }
    }

    override fun onDestroy() {
        loginPresenter.destroyView()
        super.onDestroy()
    }

    private fun onSignInClick() {
        loginPresenter.attemptLogin(
                LoginCredentials(
                        login = email.text.toString(),
                        password = password.text.toString()
                )
        )
    }

    override fun showProgress() {
        showProgress(true)
    }

    override fun hideProgress() {
        showProgress(false)
    }

    override fun showLoginError(errorMessage: String?) {
        email.error = errorMessage
    }

    override fun showPasswordError(errorMessage: String?) {
        password.error = errorMessage
    }

    override fun requestLoginFocus() {
        email.requestFocus()
    }

    override fun requestPasswordFocus() {
        password.requestFocus()
    }

    internal fun showProgress(progressVisible: Boolean) {
        val animationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)

        login_form.visibility = if (progressVisible) View.GONE else View.VISIBLE
        login_form.animate().setDuration(animationDuration.toLong()).alpha((if (progressVisible) 0 else 1).toFloat())

        login_progress.visibility = if (progressVisible) View.VISIBLE else View.GONE
        login_progress.animate().setDuration(animationDuration.toLong()).alpha((if (progressVisible) 1 else 0).toFloat())
    }
}