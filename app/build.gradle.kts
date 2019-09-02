plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)
    buildToolsVersion(Versions.Android.buildTools)

    defaultConfig {
        applicationId = Application.id
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
        versionCode = Application.versionCode
        versionName = Application.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            val mapsApiKey = System.getenv("MAPS_API_KEY") ?: property("mapsApiKeyRelease")
            manifestPlaceholders = mapOf("mapsApiKey" to mapsApiKey)
        }
        getByName("debug") {
            val mapsApiKey = System.getenv("MAPS_API_KEY") ?: property("mapsApiKeyDebug")
            manifestPlaceholders = mapOf("mapsApiKey" to mapsApiKey)
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(project(":data"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Compiling.kotlin}")
    implementation("androidx.appcompat:appcompat:${Versions.Jetpack.appcompat}")
    implementation("androidx.core:core-ktx:${Versions.Jetpack.core}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.Jetpack.fragment}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.Jetpack.navigation}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Jetpack.viewmodel}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Jetpack.livedata}")
    implementation("org.koin:koin-android:${Versions.DependencyInjection.koin}")
    implementation("org.koin:koin-android-viewmodel:${Versions.DependencyInjection.koin}")
    implementation("com.google.android.gms:play-services-maps:${Versions.PlayServices.map}")
    implementation("com.google.android.gms:play-services-location:${Versions.PlayServices.map}")
    implementation("com.karumi:dexter:${Versions.Utils.dexter}")

    testImplementation("junit:junit:${Versions.Testing.junit}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.Testing.mockito}")
    testImplementation("androidx.arch.core:core-testing:${Versions.Testing.archComponents}")

    androidTestImplementation("androidx.test:runner:${Versions.Testing.runner}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.Testing.espresso}")
}
