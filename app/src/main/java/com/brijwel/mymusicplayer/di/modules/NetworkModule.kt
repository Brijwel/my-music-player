package com.brijwel.mymusicplayer.di.modules

import com.brijwel.mymusicplayer.BuildConfig
import com.brijwel.mymusicplayer.api.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Brijwel on 07-03-2021.
 */
val networkModule = module {
    single { provideRetrofit() }
    single { provideApiService(get()) }
}

fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        )
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                    } else {
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                    }
                )
                .build()
        )
        .build()


fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)