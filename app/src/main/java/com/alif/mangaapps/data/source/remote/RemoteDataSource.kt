package com.alif.mangaapps.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.data.source.DataRepository
import com.alif.mangaapps.data.source.remote.api.ApiConfig
import com.alif.mangaapps.data.source.remote.response.ChapterItems
import com.alif.mangaapps.data.source.remote.response.CoverResponse
import com.alif.mangaapps.data.source.remote.response.MangaResponse
import com.alif.mangaapps.data.source.remote.response.ResultsItem
import com.alif.mangaapps.utils.DataDummy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

    suspend fun getComic(callback: LoadMangaCallback) {
        ApiConfig.getApiServiceManga().getManga().await().results.let { mangaResponse ->
            callback.OnMangaReceived(mangaResponse)
        }
    }

    suspend fun getComicArt(coverId: String, callback: LoadMangaCoverCallback) {
        ApiConfig.getApiServiceManga().getCoverFile(coverId).await().data.attributes.fileName.let { fileName ->
            callback.OnCoverReceived(fileName)
        }
    }

    suspend fun getChapter(mangaId: String, callback: LoadChapterCallback) {
        ApiConfig.getApiServiceManga().getChapter(mangaId).await().results.let { chapterResponse ->
            callback.OnChapterReceived(chapterResponse)
        }
    }




    interface LoadMangaCallback {
        fun OnMangaReceived(mangaResponse: List<ResultsItem>)
    }

    interface LoadChapterCallback {
        fun OnChapterReceived(chapterResponse: List<ChapterItems>)
    }

    interface LoadMangaCoverCallback {
        fun OnCoverReceived(fileName: String)
    }

}