package com.lnproduction.noveldeglace.model

import com.google.gson.annotations.SerializedName

class Users {

    @SerializedName("id")
    var id : Int = -1

    @SerializedName("name")
    lateinit var userName : String

    @SerializedName("url")
    lateinit var userUrl : String

    @SerializedName("description")
    lateinit var userDescription : String

    @SerializedName("link")
    lateinit var userLink : String

}
