package com.brijwel.mymusicplayer.api

import retrofit2.http.GET

/**
 * Created by Brijwel on 07-03-2021.
 */
interface ApiService {
    @GET("catalog.json")
    suspend fun getMusics(): ApiResponse
}