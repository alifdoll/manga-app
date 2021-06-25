package com.alif.mangaapps.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.utils.DataDummy

class MangaViewModel : ViewModel() {

    fun getDummyManga() : ArrayList<MangaEntity> = DataDummy.getDummyManga()
}