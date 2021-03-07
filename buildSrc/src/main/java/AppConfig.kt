/**
 * Created by Brijwel on 05-03-2021.
 */
object AppConfig {
    const val APPLICATION_ID = "com.brijwel.mymusicplayer"

    const val BUILD_TOOLS_VERSION = "30.0.3"
    const val COMPILE_SDK_VERSION = 30
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 30

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
    val isShrinkResources: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
    override val isShrinkResources = true
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = true
    override val isShrinkResources = true
}
