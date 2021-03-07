package com.brijwel.mymusicplayer.di

import com.brijwel.mymusicplayer.MusicApp
import com.brijwel.mymusicplayer.di.modules.networkModule
import com.brijwel.mymusicplayer.di.modules.repositoryModule
import com.brijwel.mymusicplayer.di.modules.serviceModule
import com.brijwel.mymusicplayer.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by Brijwel on 07-03-2021.
 */
object AppModule {
    fun initKoinModule(app: MusicApp) {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(app)
            modules(
                listOf(
                    viewModelModule,
                    serviceModule,
                    repositoryModule,
                    networkModule
                )
            )

        }
    }
}