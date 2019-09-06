buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.Building.gradle}")
        classpath(kotlin("gradle-plugin", Versions.Compiling.kotlin))
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Jetpack.navigation}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.name.contains("kotlinx-coroutines")) {
                useVersion(Versions.Compiling.coroutines)
                because("Different lib versions from different sources")
            }
        }
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
