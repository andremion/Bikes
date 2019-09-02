import org.gradle.api.JavaVersion

object Versions {
    object Compiling {
        val java = JavaVersion.VERSION_1_8
        const val kotlin = "1.3.41"
    }

    object Building {
        const val gradle = "3.4.2"
    }

    object Android {
        const val buildTools = "29.0.0"
        const val minSdk = 16
        const val targetSdk = 28
        const val compileSdk = 28
    }

    object Testing {
        const val junit = "4.12"
        const val mockito = "2.1.0"
        const val runner = "1.2.0"
        const val espresso = "3.2.0"
        const val archComponents = "2.1.0-rc01"
    }

    object Jetpack {
        const val appcompat = "1.0.2"
        const val core = "1.0.2"
        const val fragment = "2.0.0"
        const val navigation = "2.0.0"
        const val viewmodel = "2.1.0-rc01"
        const val livedata = "2.2.0-alpha02"
    }

    object PlayServices {
        const val map = "17.0.0"
    }

    object DependencyInjection {
        const val koin = "2.0.0-rc-1"
    }

    object Utils {
        const val dexter = "5.0.0"
    }

    object Networking {
        const val okHttp = "4.0.1"
        const val retrofit = "2.6.1"
        const val coroutinesAdapter = "0.9.2"
        const val gson = "2.8.5"
    }
}

object Application {
    const val id = "com.andremion.bikes"
    const val versionCode = 1
    const val versionName = "1.0.0"
}


