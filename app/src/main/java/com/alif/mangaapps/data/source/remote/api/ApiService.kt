package com.alif.mangaapps.data.source.remote.api

import com.alif.mangaapps.data.entity.MangaEntity
import com.alif.mangaapps.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("manga")
    fun getManga(): Call<MangaResponse>

    @GET("manga/{mangaId}")
    fun getMangaDetail(
        @Path("mangaId") mangaId: String
    ): Call<ResultsItem>

    @GET("cover/{coverId}")
    fun getCoverFile(
        @Path("coverId") coverId: String
    ): Call<CoverResponse>

    @GET("manga/{mangaId}/feed?translatedLanguage[]=en&order[chapter]=asc")
    fun getChapterList(
        @Path("mangaId") mangaId: String
    ): Call<ChapterResponse>

    @GET("chapter/{chapterId}")
    fun getChapterPages(
        @Path("chapterId") chapterId: String
    ): Call<PageResponse>


}