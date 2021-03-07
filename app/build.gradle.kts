plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")

}

android {
    compileSdkVersion(AppConfig.COMPILE_SDK_VERSION)
    buildToolsVersion(AppConfig.BUILD_TOOLS_VERSION)

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdkVersion(AppConfig.MIN_SDK_VERSION)
        targetSdkVersion(AppConfig.TARGET_SDK_VERSION)
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
    }
    buildFeatures.dataBinding = true
    buildFeatures.viewBinding = true

    buildTypes {
        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://storage.googleapis.com/uamp/\""
            )
        }

        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"https://storage.googleapis.com/uamp/\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }
}
dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(Dependencies.kotlin)

    // Coroutines
    implementation(Coroutines.core)
    implementation(Coroutines.android)

    // Android
    implementation(Android.appcompat)
    implementation(Android.activityKtx)
    implementation(Android.coreKtx)
    implementation(Android.constraintLayout)
    implementation(Android.swipeRefreshLayout)

    // Architecture Components
    implementation(Lifecycle.viewModel)
    implementation(Lifecycle.liveData)


    // Material Design
    implementation(Dependencies.materialDesign)

    // Coil-kt
    implementation(Dependencies.coil)

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.moshiRetrofitConverter)
    // Logging Interceptor
    implementation(Retrofit.retrofitLoggingInterceptor)

    //navigation
    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)

    //Gson
    implementation(Gson.gson)

    // Moshi
    implementation(Moshi.moshi)
    implementation(Moshi.codeGen)
    kapt(Moshi.codeGen)

    // Koin
    implementation(Koin.koin)
    implementation(Koin.koinViewModel)
    implementation(Koin.koinScope)

    // Timber
    implementation(Timber.timber)

    // Testing
    testImplementation(Testing.core)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.room)
    testImplementation(Testing.okHttp)
    testImplementation(Testing.jUnit)

    // Android Testing
    androidTestImplementation(Testing.extJUnit)
    androidTestImplementation(Testing.espresso)

    //wave form seek bar
    implementation(WaveformSeekBar.waveformSeekBar)

    //exoplayer
    api(ExoPlayer.exoplayerCore)
    api(ExoPlayer.exoplayerUi)
    api(ExoPlayer.exoplayerMediaSession)

}