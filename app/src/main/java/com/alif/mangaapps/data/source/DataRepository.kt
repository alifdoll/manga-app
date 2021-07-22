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
        private const val POSTER_URL = "https://uploads.mangadex.org/covers/"
    }


    override fun getManga(): LiveData<List<MangaEntity>> {
        val listManga = MutableLiveData<List<MangaEntity>>()

        CoroutineScope(IO).launch {

            remoteDataSource.getComic(object : RemoteDataSource.LoadMangaCallback {

                override fun OnMangaReceived(mangaResponse: List<ResultsItem>) {

                    launch {
                        val mangas = ArrayList<MangaEntity>()

                        for (response in mangaResponse) {
                            val coverPost = response.relationships.size - 1
                            val coverId = response.relationships[coverPost].id

                            remoteDataSource.getComicArt(coverId, object : RemoteDataSource.LoadMangaCoverCallback {

                                override fun OnCoverReceived(fileName: String) {
                                    val manga = MangaEntity(
                                        response.data.id,
                                        response.data.attributes.title.en,
                                        response.data.attributes.description.en,
                                        response.data.attributes.publicationDemographic,
                                        "${POSTER_URL}${response.data.id}/${fileName}"
                                    )

                                    mangas.add(manga)
                                }

                            })
                        }

                        listManga.postValue(mangas)
                    }

                }

            })
        }
        return listManga
    }

    override fun getMangaDetail(mangaId: String): LiveData<MangaEntity> {
        return remoteDataSource.getMangaDetail(mangaId)
    }


}