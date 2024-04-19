package com.task.testtask.retrofit

import com.task.testtask.model.DataListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getDataList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<DataListModel>>
}

