package com.alif.mangaapps.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.data.source.remote.RemoteDataSource
import com.alif.mangaapps.data.source.remote.response.ResultsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

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
    }


    override fun getManga(): LiveData<List<MangaEntity>> {
        return remoteDataSource.getManga()
    }

    override fun getMangaDetail(mangaId: String): LiveData<MangaEntity> {
        return remoteDataSource.getMangaDetail(mangaId)
    }


}