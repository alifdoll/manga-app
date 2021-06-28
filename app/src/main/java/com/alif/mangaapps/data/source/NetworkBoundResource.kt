package com.alif.mangaapps.data.source

import androidx.lifecycle.MediatorLiveData
import com.alif.mangaapps.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>{
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

//        val dbSource = loadFromDb()
    }

}