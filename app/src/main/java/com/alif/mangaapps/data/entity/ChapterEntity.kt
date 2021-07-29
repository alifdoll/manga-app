package com.alif.mangaapps.data.entity

data class ChapterEntity(
    val id: String,
    val number: String,
    val title: String,
    val hash: String,
    val pages: List<String>?
)
