package com.lnproduction.noveldeglace.model

class Tome {

    var tomeId : Int = -1
    lateinit var tomeName : String

    lateinit var chapterList : ArrayList<Chapters>
}