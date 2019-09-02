buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.Building.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Compiling.kotlin}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Jetpack.navigation}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
