package com.alif.mangaapps.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.data.source.DataRepository
import com.alif.mangaapps.data.source.remote.api.ApiConfig
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
        private const val POSTER_URL = "https://uploads.mangadex.org/covers/"
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

    fun getManga(): LiveData<List<MangaEntity>> {
        val listManga = MutableLiveData<List<MangaEntity>>()

        val client = ApiConfig.getApiServiceManga().getManga()
        client.enqueue(object: Callback<MangaResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<MangaResponse>, response: Response<MangaResponse>) {
                if(response.isSuccessful) {
                    val mangas = ArrayList<MangaEntity>()
                    val result = response.body()?.results

                    for(mangaResponse in result!!) {
                        val manga = MangaEntity(
                            mangaResponse.data.id,
                            mangaResponse.data.attributes.title.en,
                            mangaResponse.data.attributes.description.en,
                            mangaResponse.data.attributes.publicationDemographic,
                            ""
                        )
                        getCoverArt(manga, mangaResponse.relationships[2].id)
                        Log.d("Debug remoteDataSource getmanga", manga.coverArt!!)
                        mangas.add(manga)
                    }
                    listManga.postValue(mangas)
                }
            }

            override fun onFailure(call: Call<MangaResponse>, t: Throwable) {
                Log.e("Fail", "onFailure: ${t.message.toString()}")
            }

        })
        return listManga
    }

    fun getMangaDetail(mangaId: String): LiveData<MangaEntity> {
        val manga = MutableLiveData<MangaEntity>()

        val client = ApiConfig.getApiServiceManga().getMangaDetail(mangaId)
        client.enqueue(object : Callback<ResultsItem> {
            override fun onResponse(call: Call<ResultsItem>, response: Response<ResultsItem>) {
                val result = response.body()!!

                val mangaResponse = MangaEntity(
                    result.data.id,
                    result.data.attributes.title.en,
                    result.data.attributes.description.en,
                    result.data.attributes.publicationDemographic,
                    ""
                )
                getCoverArt(mangaResponse, result.relationships[2].id)
                Log.d("Debug remote getdetail", mangaResponse.coverArt!!)
                Log.d("Debug remote getdetail", "coba")
                manga.postValue(mangaResponse)
            }

            override fun onFailure(call: Call<ResultsItem>, t: Throwable) {
                Log.e("Fail", "onFailure: ${t.message.toString()}")
            }

        })
        return manga
    }

    private fun getCoverArt(manga: MangaEntity, coverId: String){
        val client = ApiConfig.getApiServiceManga().getCoverFile(coverId)
        client.enqueue(object: Callback<CoverResponse> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<CoverResponse>, response: Response<CoverResponse>) {
                if(response.isSuccessful) {
                    val result = response.body()?.data?.attributes?.fileName.toString()
                    manga.coverArt = "${POSTER_URL}${manga.id}/${result}"
                }
            }

            override fun onFailure(call: Call<CoverResponse>, t: Throwable) {
                Log.e("Fail", "onFailure: ${t.message.toString()}")
            }

        })
    }


    interface LoadMangaCallback {
        fun OnMangaReceived(mangaResponse: List<ResultsItem>)
    }

    interface LoadMangaCoverCallback {
        fun OnCoverReceived(fileName: String)
    }

}