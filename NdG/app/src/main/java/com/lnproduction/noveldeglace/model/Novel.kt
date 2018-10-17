package com.lnproduction.noveldeglace.model

import com.google.gson.annotations.SerializedName

class Novel {

    @SerializedName("title")
    lateinit var novelTitle : Title

    @SerializedName("link")
    lateinit var novelLink : String

    @SerializedName("id")
    var novelId : Int = -1

    @SerializedName("roman_url_img")
    lateinit var imgNovel : String

    @SerializedName("categorie_roman")
    lateinit var idCategories : ArrayList<Int>

    @SerializedName("genre_roman")
    lateinit var idGenre : ArrayList<Int>

    @SerializedName("featured_media")
    var feature_media : Int = -1

    var backgroundColor : Int = -1

    var textColor : Int = -1

}