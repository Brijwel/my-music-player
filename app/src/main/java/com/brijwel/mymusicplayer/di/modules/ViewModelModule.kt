package com.brijwel.mymusicplayer.di.modules

import com.brijwel.mymusicplayer.ui.MusicListViewModel
import com.brijwel.mymusicplayer.ui.NowPlayingMusicViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Brijwel on 07-03-2021.
 */
val viewModelModule = module {
    viewModel { MusicListViewModel(get(), get()) }
    viewModel { NowPlayingMusicViewModel(get()) }
}