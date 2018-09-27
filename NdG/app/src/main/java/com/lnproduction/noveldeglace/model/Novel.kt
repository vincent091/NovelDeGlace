package com.lnproduction.noveldeglace.model

import com.google.gson.annotations.SerializedName

class Novel {

    @SerializedName("title")
    lateinit var novelTitle : Title

    @SerializedName("link")
    lateinit var novelLink : String

    @SerializedName("id")
    var novelId : Int = -1
}