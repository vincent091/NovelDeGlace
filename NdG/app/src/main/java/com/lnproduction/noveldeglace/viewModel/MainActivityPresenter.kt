package com.lnproduction.noveldeglace.viewModel

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.lnproduction.noveldeglace.activity.MainActivity
import com.lnproduction.noveldeglace.model.Users
import com.lnproduction.noveldeglace.utils.Constants


class MainActivityPresenter: BasePresenter<MainActivity>() {

    private lateinit var users : Users

    fun setNavigationDrawer(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(view)
        val userDetatails = preferences.getString(Constants.SHARED_PREFS_USER, "")
        if(userDetatails.isNullOrEmpty()){
            view?.setupNavigationDrawer(null)
        }else{
            val gson = Gson()
            users = gson.fromJson(userDetatails,Users::class.java)
            view?.setupNavigationDrawer(users)
        }
    }

    fun logout(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(view)
        preferences.edit().remove(Constants.SHARED_PREFS_USER).apply()
        setNavigationDrawer()
    }

}