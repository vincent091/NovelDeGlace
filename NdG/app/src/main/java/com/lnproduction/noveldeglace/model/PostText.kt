package com.lnproduction.noveldeglace.model

import com.google.gson.annotations.SerializedName

class PostText {
    @SerializedName("rendered")
    lateinit var postText : String
}