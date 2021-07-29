package com.alif.mangaapps.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alif.mangaapps.data.entity.ChapterEntity
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.data.source.remote.RemoteDataSource
import com.alif.mangaapps.data.source.remote.response.*
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
        private const val IMAGE_URL = "https://uploads.mangadex.org/covers/"
    }


    override fun getManga(): LiveData<List<MangaEntity>> {
        val listManga = MutableLiveData<List<MangaEntity>>()

        CoroutineScope(IO).launch {

            remoteDataSource.getManga(object : RemoteDataSource.LoadMangaCallback {

                override fun OnMangaReceived(mangaResponse: List<ResultsItem>) {

                    launch {
                        val mangas = ArrayList<MangaEntity>()

                        for (response in mangaResponse) {
                            val coverPost = response.relationships.size - 1
                            val coverId = response.relationships[coverPost].id

                            remoteDataSource.getCoverArt(coverId, object : RemoteDataSource.LoadMangaCoverCallback {

                                override fun OnCoverReceived(fileName: String) {
                                    val manga = MangaEntity(
                                        response.data.id,
                                        response.data.attributes.title.en,
                                        response.data.attributes.description.en,
                                        response.data.attributes.publicationDemographic,
                                        "${IMAGE_URL}${response.data.id}/${fileName}"
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

    override fun getChapterList(mangaId: String): LiveData<List<ChapterEntity>> {
        val listChapter = MutableLiveData<List<ChapterEntity>>()

        CoroutineScope(IO).launch {
            remoteDataSource.getChapterList(mangaId, object : RemoteDataSource.LoadChapterCallback {
                override fun OnChapterReceived(chapterResponse: List<ChapterItems>) {
                    val chapters = ArrayList<ChapterEntity>()

                    for (chapter in chapterResponse) {
                        val ch = ChapterEntity(
                            chapter.data.id,
                            chapter.data.attributes.chapter,
                            chapter.data.attributes.title,
                            chapter.data.attributes.hash,
                            null
                        )

                        chapters.add(ch)
                    }

                    listChapter.postValue(chapters)
                }

            })
        }
        return listChapter
    }

    override fun getChapterPages(chapterid: String): LiveData<List<String>> {
        val listPages = MutableLiveData<List<String>>()

        CoroutineScope(IO).launch {
            remoteDataSource.getChapterPages(chapterid, object : RemoteDataSource.LoadChapterPageCallback {
                override fun OnPageReceived(pageResponse: PageResponse) {
                    val pages = ArrayList<String>()

                    for (page in pageResponse.data.attributes.data) {
                        val pageItem = "${IMAGE_URL}/data/${pageResponse.data.attributes.hash}/${page}"
                        pages.add(pageItem)
                        Log.d("Repository ChapterPage", pageItem)
                    }
                    listPages.postValue(pages)
                }

            })
        }
        return listPages
    }


}