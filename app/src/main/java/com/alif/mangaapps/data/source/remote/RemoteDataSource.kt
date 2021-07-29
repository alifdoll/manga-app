package com.alif.mangaapps.data.source.remote

import com.alif.mangaapps.data.source.remote.api.ApiConfig
import com.alif.mangaapps.data.source.remote.response.ChapterAttributes
import com.alif.mangaapps.data.source.remote.response.ChapterItems
import com.alif.mangaapps.data.source.remote.response.PageResponse
import com.alif.mangaapps.data.source.remote.response.ResultsItem
import retrofit2.await

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }

    suspend fun getManga(callback: LoadMangaCallback) {
        ApiConfig.getApiServiceManga().getManga().await().results.let { mangaResponse ->
            callback.OnMangaReceived(mangaResponse)
        }
    }

    suspend fun getCoverArt(coverId: String, callback: LoadMangaCoverCallback) {
        ApiConfig.getApiServiceManga().getCoverFile(coverId).await().data.attributes.fileName.let { fileName ->
            callback.OnCoverReceived(fileName)
        }
    }

    suspend fun getChapterList(mangaId: String, callback: LoadChapterCallback) {
        ApiConfig.getApiServiceManga().getChapterList(mangaId).await().results.let { chapterResponse ->
            callback.OnChapterReceived(chapterResponse)
        }
    }

    suspend fun getChapterPages(chapterId: String, callback: LoadChapterPageCallback) {
        ApiConfig.getApiServiceManga().getChapterPages(chapterId).await().let { response ->
            callback.OnPageReceived(response)
        }
    }




    interface LoadMangaCallback {
        fun OnMangaReceived(mangaResponse: List<ResultsItem>)
    }

    interface LoadMangaCoverCallback {
        fun OnCoverReceived(fileName: String)
    }

    interface LoadChapterCallback {
        fun OnChapterReceived(chapterResponse: List<ChapterItems>)
    }

    interface LoadChapterPageCallback {
        fun OnPageReceived(pageResponse: PageResponse)
    }



}