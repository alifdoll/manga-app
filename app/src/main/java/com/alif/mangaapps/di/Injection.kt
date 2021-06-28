package com.alif.mangaapps.di

import android.content.Context
import com.alif.mangaapps.data.source.DataRepository
import com.alif.mangaapps.data.source.remote.RemoteDataSource

object Injection {

    fun provideRepository(context: Context): DataRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return DataRepository.getInstance(remoteDataSource)
    }
}