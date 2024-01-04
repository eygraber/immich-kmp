import com.eygraber.conventions.tasks.deleteRootBuildDirWhenCleaning
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
    dependencies {
        classpath(libs.buildscript.android)
        classpath(libs.buildscript.androidCacheFix)
        classpath(libs.buildscript.composeJetbrains)
        classpath(libs.buildscript.detekt)
        classpath(libs.buildscript.kotlin)
    }
}

plugins {
    base
    alias(libs.plugins.conventions)
}

project.deleteRootBuildDirWhenCleaning()

gradleConventionsDefaults {
    android {
        sdkVersions(
            compileSdk = libs.versions.android.sdk.compile,
            targetSdk = libs.versions.android.sdk.target,
            minSdk = libs.versions.android.sdk.min,
        )
    }

    compose {
        multiplatform(
            compilerOverride = libs.compose.compilerJetbrains,
        )
    }

    detekt {
        plugins(libs.detektCompose)
        plugins(libs.detektEygraber.formatting)
        plugins(libs.detektEygraber.style)
    }

    kotlin {
        jvmTargetVersion = JvmTarget.JVM_17
    }
}
