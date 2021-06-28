package com.alif.mangaapps.data.source

import androidx.lifecycle.LiveData
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.data.source.remote.RemoteDataSource

class DataRepository private constructor(
    private val remoteDataSource: RemoteDataSource
): DataSource {

    companion object {
        @Volatile
        private var instance: DataRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): DataRepository =
            instance ?: synchronized(this) {
                instance ?: DataRepository(remoteDataSource).apply {
                    instance = this
                }
            }

        private const val POSTER_URL = "https://uploads.mangadex.org/covers/"
    }


    override fun getHalfManga(): LiveData<List<MangaEntity>> {
        return remoteDataSource.getManga()
    }


}