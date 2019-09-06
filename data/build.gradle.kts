import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(Versions.Android.compileSdk)
    buildToolsVersion(Versions.Android.buildTools)

    defaultConfig {
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
        }
    }
}

private val apiUrl = System.getenv("API_URL") ?: property("apiUrl")
android.buildTypes.forEach { type ->
    type.buildConfigField("String", "API_URL", "\"$apiUrl\"")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(kotlin("stdlib-jdk8", Versions.Compiling.kotlin))
    implementation("androidx.appcompat:appcompat:${Versions.Jetpack.appcompat}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.Networking.okHttp}")
    implementation("com.squareup.retrofit2:retrofit:${Versions.Networking.retrofit}")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.Networking.coroutinesAdapter}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.Networking.retrofit}")
    implementation("com.google.code.gson:gson:${Versions.Networking.gson}")

    testImplementation("junit:junit:${Versions.Testing.junit}")

    androidTestImplementation("androidx.test:runner:${Versions.Testing.runner}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.Testing.espresso}")
}
