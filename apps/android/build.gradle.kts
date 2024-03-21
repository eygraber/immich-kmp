plugins {
  kotlin("android")
  id("com.android.application")
  id("com.eygraber.conventions-kotlin-library")
  id("com.eygraber.conventions-detekt")
  id("com.eygraber.conventions-compose-jetbrains")
}

android {
  namespace = "app.immich.kmp"

  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  defaultConfig {
    applicationId = "app.immich.kmp"
    targetSdk = libs.versions.android.sdk.target.get().toInt()
    minSdk = libs.versions.android.sdk.min.get().toInt()

    versionCode = 1
    versionName = "0.0.1"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    multiDexEnabled = true
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles.clear()
      proguardFiles += project.file("proguard-rules.pro")
    }

    named("debug") {
      applicationIdSuffix = ".debug"

      versionNameSuffix = "-DEBUG"

      isMinifyEnabled = false
    }
  }

  lint {
    checkDependencies = true
    checkReleaseBuilds = false
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
  }

  packaging {
    resources.pickFirsts += "META-INF/*"
  }

  dependencies {
    coreLibraryDesugaring(libs.android.desugar)

    implementation(projects.apps.shared)
    implementation(projects.theme)
    implementation(projects.virtue.app)

    implementation(libs.androidx.appCompat)
  }
}
