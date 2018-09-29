package com.lnproduction.noveldeglace.model

import com.google.gson.annotations.SerializedName

class Post {

    @SerializedName("id")
    var novelId : Int = -1

    @SerializedName("date_gmt")
    lateinit var dateGMT : String

    @SerializedName("title")
    lateinit var postTitle : Title

    @SerializedName(value = "excerpt")
    lateinit var postText : PostText

}