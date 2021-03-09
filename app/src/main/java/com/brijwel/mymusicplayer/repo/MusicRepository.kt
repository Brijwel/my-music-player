package com.brijwel.mymusicplayer.repo

import com.brijwel.mymusicplayer.api.ApiResponse
import com.brijwel.mymusicplayer.api.ApiService

/**
 * Created by Brijwel on 07-03-2021.
 */
class MusicRepository(private val apiService: ApiService) {
    suspend fun getMusics(): ApiResponse = apiService.getMusics()
}