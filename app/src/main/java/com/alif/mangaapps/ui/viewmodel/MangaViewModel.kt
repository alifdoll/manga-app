package com.alif.mangaapps.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alif.mangaapps.data.entity.ChapterEntity
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.data.source.DataRepository
import com.alif.mangaapps.utils.DataDummy

class MangaViewModel(private val mangaDataRepository: DataRepository): ViewModel() {

    fun getDummyManga(): ArrayList<MangaEntity> = DataDummy.getDummyManga()

    fun getManga(): LiveData<List<MangaEntity>> = mangaDataRepository.getManga()

    fun getChapter(mangaId: String): LiveData<List<ChapterEntity>> = mangaDataRepository.getChapterList(mangaId)

    fun getChapterPages(chapterId: String): LiveData<List<String>> = mangaDataRepository.getChapterPages(chapterId)
}