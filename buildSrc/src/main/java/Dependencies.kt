/**
 * Created by Brijwel on 06-03-2021.
 */

object Dependencies {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.4.0"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0"
    const val gradle = "com.android.tools.build:gradle:4.0.1"
    const val materialDesign = "com.google.android.material:material:1.1.0"
    const val coil = "io.coil-kt:coil:0.9.5"
}

object Testing {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.9"
    const val room = "androidx.room:room-testing:2.2.5"
    const val jUnit = "junit:junit:4.13"
    const val extJUnit = "androidx.test.ext:junit:1.1.1"
    const val espresso = "androidx.test.espresso:espresso-core:3.2.0"
    const val okHttp = "com.squareup.okhttp3:mockwebserver:4.4.0"
    const val core = "androidx.arch.core:core-testing:2.1.0"
}

object Lifecycle {
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
}

object Moshi {
    const val moshi = "com.squareup.moshi:moshi-kotlin:1.9.3"
    const val codeGen = "com.squareup.moshi:moshi-kotlin-codegen:1.9.3"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofitLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.7.1"
    const val moshiRetrofitConverter = "com.squareup.retrofit2:converter-moshi:2.7.2"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
}

object Android {
    const val appcompat = "androidx.appcompat:appcompat:1.1.0"
    const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
    const val coreKtx = "androidx.core:core-ktx:1.2.0"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
}

object Koin {
    const val koin = "org.koin:koin-android:2.0.1"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:2.0.1"
    const val koinScope = "org.koin:koin-androidx-scope:2.0.1"
}

object Timber {
    const val timber = "com.jakewharton.timber:timber:4.7.1"
}

object Navigation {
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:2.2.2"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:2.2.2"
}

object Gson {
    const val gson = "com.google.code.gson:gson:2.8.6"
}

object WaveformSeekBar {
    const val waveformSeekBar = "com.github.massoudss:waveformSeekBar:2.3.0"
}

object ExoPlayer {
    const val exoplayerCore = "com.google.android.exoplayer:exoplayer-core:2.11.8"
    const val exoplayerUi = "com.google.android.exoplayer:exoplayer-ui:2.11.8"
    const val exoplayerMediaSession = "com.google.android.exoplayer:extension-mediasession:2.11.8"
}
