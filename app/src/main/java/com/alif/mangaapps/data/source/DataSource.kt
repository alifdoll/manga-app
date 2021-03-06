package com.alif.mangaapps.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alif.mangaapps.data.entity.ChapterEntity
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.vo.Resource

interface DataSource {

    fun getManga(): LiveData<List<MangaEntity>>

    fun getChapterList(mangaId: String): LiveData<List<ChapterEntity>>

    fun getChapterPages(chapterid: String): LiveData<List<String>>
}