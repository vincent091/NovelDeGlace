package com.lnproduction.noveldeglace.ui.main

import android.preference.PreferenceManager
import com.google.gson.Gson
import com.lnproduction.noveldeglace.model.Users
import com.lnproduction.noveldeglace.utils.Constants
import com.lnproduction.noveldeglace.base.BasePresenter


class MainActivityPresenter: BasePresenter<MainActivity>() {

    private lateinit var users : Users

    fun setNavigationDrawer(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(view)
        val userDetails = preferences.getString(Constants.SHARED_PREFS_USER, "")
        if(userDetails.isNullOrEmpty()){
            view?.setupNavigationDrawer(null)
        }else{
            val gson = Gson()
            users = gson.fromJson(userDetails,Users::class.java)
            view?.setupNavigationDrawer(users)
        }
    }

    fun logout(){
        val preferences = PreferenceManager.getDefaultSharedPreferences(view)
        preferences.edit().remove(Constants.SHARED_PREFS_USER).apply()
        setNavigationDrawer()
    }

    fun isUserConnected():Boolean{
        val preferences = PreferenceManager.getDefaultSharedPreferences(view)
        val userDetails = preferences.getString(Constants.SHARED_PREFS_USER, "")
        return !userDetails.isNullOrEmpty()
    }

}