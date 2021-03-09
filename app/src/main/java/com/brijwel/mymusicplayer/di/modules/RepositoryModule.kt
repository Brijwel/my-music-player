package com.brijwel.mymusicplayer.di.modules

import com.brijwel.mymusicplayer.repo.MusicRepository
import org.koin.dsl.module

/**
 * Created by Brijwel on 07-03-2021.
 */
val repositoryModule = module {
    single { MusicRepository(get()) }
}