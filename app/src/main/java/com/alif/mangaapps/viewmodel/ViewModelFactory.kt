package com.alif.mangaapps.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alif.mangaapps.data.source.DataRepository
import com.alif.mangaapps.di.Injection
import com.alif.mangaapps.ui.viewmodel.MangaViewModel

class ViewModelFactory private constructor(private val dataRepository: DataRepository): ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply { instance = this }
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MangaViewModel::class.java) -> {
                MangaViewModel(dataRepository) as T
            }

            else -> throw Throwable("Unknown Viewmodel class: " + modelClass.name)
        }
    }
}