package com.lnproduction.noveldeglace.model

class NovelDetail {

    lateinit var author : String

    lateinit var rythm : String

    lateinit var status : String

    lateinit var synopsis : String

    lateinit var tomeList : ArrayList<Tome>

    var nbChapters : Int = -1
}