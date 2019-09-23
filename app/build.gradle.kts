import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

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

    compileOptions {
        sourceCompatibility = Versions.Compiling.java
        targetCompatibility = Versions.Compiling.java
    }

    kotlinOptions {
        this as KotlinJvmOptions
        jvmTarget = Versions.Compiling.java.toString()
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

    implementation(kotlin("stdlib-jdk8", Versions.Compiling.kotlin))
    implementation("androidx.appcompat:appcompat:${Versions.Jetpack.appcompat}")
    implementation("androidx.core:core-ktx:${Versions.Jetpack.core}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.Jetpack.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.Jetpack.navigation}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Jetpack.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Jetpack.lifecycle}")
    implementation("org.koin:koin-android:${Versions.DependencyInjection.koin}")
    implementation("org.koin:koin-android-viewmodel:${Versions.DependencyInjection.koin}")
    implementation("com.google.android.gms:play-services-maps:${Versions.PlayServices.map}")
    implementation("com.google.android.gms:play-services-location:${Versions.PlayServices.map}")
    implementation("com.karumi:dexter:${Versions.Utils.dexter}")

    testImplementation("junit:junit:${Versions.Testing.junit}")
    testImplementation("io.mockk:mockk:${Versions.Testing.mockk}")
    testImplementation("androidx.arch.core:core-testing:${Versions.Testing.archComponents}")

    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.Testing.espresso}")
}
