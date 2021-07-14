package com.alif.mangaapps.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.vo.Resource

interface DataSource {

    fun getManga(): LiveData<List<MangaEntity>>

    fun getMangaDetail(mangaId: String): LiveData<MangaEntity>
}