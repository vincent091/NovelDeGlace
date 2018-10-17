package com.lnproduction.noveldeglace.model

import com.google.gson.*
import java.lang.reflect.Type

class NovelDetailDeserializer : JsonDeserializer<NovelDetail> {

    private val KEY_AUTHOR = "auteur"
    private val KEY_RYTHM = "rythme"
    private val KEY_STATUS = "statut_roman"
    private val KEY_SYNOPSIS = "synopsis"
    private val KEY_EMBEDDED = "_embedded"
    private val KEY_TOMES = "tomes"
    private val KEY_TOME_ID = "id"
    private val KEY_TOME_NAME = "name"
    private val KEY_CHAPTERS = "chapitres"
    private val KEY_CHAPTER_TOME_ID = "chapitre-tome"
    private val KEY_CHAPTER_NAME = "chapitre-nom"
    private val KEY_CHAPTER_URL = "chapitre-url"


    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): NovelDetail {
        val jsonObject: JsonObject = json!!.getAsJsonObject();
        val novelDetail = NovelDetail()
        novelDetail.author = jsonObject.get(KEY_AUTHOR).asString
        novelDetail.rythm = jsonObject.get(KEY_RYTHM).asString
        novelDetail.status = jsonObject.get(KEY_STATUS).asString
        novelDetail.synopsis = jsonObject.get(KEY_SYNOPSIS).asString

        val embeddedObject = jsonObject.getAsJsonObject(KEY_EMBEDDED)
        val chaptersObjectArray: JsonArray = embeddedObject.getAsJsonArray(KEY_CHAPTERS).get(0) as JsonArray
        val chapterList : ArrayList<Chapters> = ArrayList()
        for(chaptersObject : JsonElement in chaptersObjectArray){
            val chapters = Chapters()
            chapters.chapterTomeId = (chaptersObject as JsonObject).get(KEY_CHAPTER_TOME_ID).asInt
            chapters.chapterName = chaptersObject.get(KEY_CHAPTER_NAME).asString
            chapters.chapterUrl = chaptersObject.get(KEY_CHAPTER_URL).asString
            chapterList.add(chapters)
        }
        novelDetail.nbChapters = chapterList.size
        val tomesObjectArray: JsonArray = embeddedObject.getAsJsonArray(KEY_TOMES).get(0) as JsonArray
        val tomeLists: ArrayList<Tome> = ArrayList()
        for (tomeObject: JsonElement in tomesObjectArray) {
            val tome = Tome()
            tome.tomeId = (tomeObject as JsonObject).get(KEY_TOME_ID).asInt
            tome.tomeName = tomeObject.get(KEY_TOME_NAME).asString
            tome.chapterList = chapterList.filter { chapters: Chapters -> chapters.chapterTomeId == tome.tomeId } as ArrayList<Chapters>
            tomeLists.add(tome)
        }
        novelDetail.tomeList = tomeLists

        return novelDetail
    }
}