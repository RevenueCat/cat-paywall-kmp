import java.util.Properties

// Android entry point. AGP 9 no longer allows com.android.application in a Kotlin Multiplatform module,
// so the app lives here and consumes the shared :composeApp library.
plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.compiler)
  // Needed for createGraph<AppGraph>() in CatArticlesApplication, which Metro's compiler plugin rewrites.
  alias(libs.plugins.metro)
}

val localProperties = Properties().apply {
  val file = rootProject.file("local.properties")
  if (file.exists()) file.inputStream().use { load(it) }
}

android {
  namespace = "com.revenuecat.catpaywalls"
  compileSdk = libs.versions.android.compileSdk.get().toInt()

  defaultConfig {
    applicationId = "com.revenuecat.catpaywalls"
    minSdk = libs.versions.android.minSdk.get().toInt()
    targetSdk = libs.versions.android.targetSdk.get().toInt()
    versionCode = 1
    versionName = "1.0"

    // RevenueCat Test Store API key (loaded from local.properties)
    buildConfigField(
      "String",
      "REVENUECAT_TEST_API_KEY",
      "\"${localProperties.getProperty("revenuecat.test.api.key", "")}\"",
    )
  }

  buildFeatures {
    compose = true
    buildConfig = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(projects.composeApp)
  implementation(projects.core.data)

  implementation(libs.purchases.kmp.core)
  implementation(libs.androidx.activity.compose)

  implementation(libs.compose.runtime)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui)
}
