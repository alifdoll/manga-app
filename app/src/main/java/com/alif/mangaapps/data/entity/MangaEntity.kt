package com.alif.mangaapps.data.entity

data class MangaEntity(
    var id: String,
    var title: String,
    var desc: String,
    var demograph: String,
    var coverArt: String,
    var favorites: Boolean = false
)
