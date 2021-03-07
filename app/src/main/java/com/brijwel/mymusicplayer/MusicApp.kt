package com.brijwel.mymusicplayer

import android.app.Application
import com.brijwel.mymusicplayer.di.AppModule

/**
 * Created by Brijwel on 07-03-2021.
 */
class MusicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.initKoinModule(this)
    }
}