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


    suspend fun getAllMangas(callback: LoadMangaCallback) {
        ApiConfig.getApiServiceManga().getManga().await().results.let { listManga ->
            callback.onMangaReceived(listManga)
        }
    }


    @SuppressLint("LongLogTag")
    suspend fun getMangaCover(coverId: String, callback: LoadDetailCallback) {
        ApiConfig.getApiServiceManga().getCoverFile(coverId).await().data.attributes.fileName.let { fileName ->
            callback.onFileNameReceived(fileName)
        }
    }

    fun getManga(): LiveData<List<MangaEntity>> {
        val listManga = MutableLiveData<List<MangaEntity>>()

        val client = ApiConfig.getApiServiceManga().getManga()
        client.enqueue(object: Callback<MangaResponse> {
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
                            DataDummy.getDummyCover()
                        )
                        getCoverArt(manga, mangaResponse.relationships[2].id)
                        Log.d("Debug coba", manga.coverArt)
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

    fun getCoverArt(manga: MangaEntity, coverId: String) {
        val client = ApiConfig.getApiServiceManga().getCoverFile(coverId)
        client.enqueue(object: Callback<CoverResponse> {
            override fun onResponse(call: Call<CoverResponse>, response: Response<CoverResponse>) {
                if(response.isSuccessful) {
                    val result = response.body()?.data?.attributes?.fileName.toString()
                    manga.coverArt = "${POSTER_URL}${manga.id}/${result}"
//                    Log.d("Debug coba,", result)
                } else {
                    Log.d("Debug coba", "GAGAL")
                }
            }

            override fun onFailure(call: Call<CoverResponse>, t: Throwable) {
                Log.e("Fail", "onFailure: ${t.message.toString()}")
            }

        })
    }

    interface LoadMangaCallback {
        fun onMangaReceived(mangaResponse: List<ResultsItem>)
    }

    interface LoadDetailCallback {
         fun onFileNameReceived(fileName: String)
    }

}