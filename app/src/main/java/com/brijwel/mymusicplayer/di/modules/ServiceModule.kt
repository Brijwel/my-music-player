package com.brijwel.mymusicplayer.di.modules

import android.content.Context
import com.brijwel.mymusicplayer.R
import com.brijwel.mymusicplayer.exoplayer.MusicServiceConnection
import com.brijwel.mymusicplayer.exoplayer.MusicSource
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Created by Brijwel on 07-03-2021.
 */
val serviceModule = module {
    single { provideMusicServiceConnection(androidContext()) }
    single { provideAudioAttributes() }
    single { provideExoPlayer(androidContext(), get()) }
    single { provideDataSourceFactory(androidContext()) }
    single { MusicSource(get()) }
}

fun provideMusicServiceConnection(
    context: Context
) = MusicServiceConnection(context)


fun provideAudioAttributes() = AudioAttributes.Builder()
    .setContentType(C.CONTENT_TYPE_MUSIC)
    .setUsage(C.USAGE_MEDIA)
    .build()

fun provideExoPlayer(
    context: Context,
    audioAttributes: AudioAttributes
) = SimpleExoPlayer.Builder(context).build().apply {
    setAudioAttributes(audioAttributes, true)
    setHandleAudioBecomingNoisy(true)
}

fun provideDataSourceFactory(
    context: Context
) = DefaultDataSourceFactory(
    context,
    Util.getUserAgent(context, context.resources.getString(R.string.app_name))
)