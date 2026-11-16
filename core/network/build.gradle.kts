plugins {
  id("catpaywalls.kmp.library")
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.metro)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.model)

      api(libs.ktor.client.core)
      api(libs.ktor.client.content.negotiation)
      api(libs.ktor.serialization.kotlinx.json)
      api(libs.ktor.client.logging)
      api(libs.kotlinx.serialization.json)
    }

    androidMain.dependencies {
      implementation(libs.ktor.client.okhttp)
    }

    iosMain.dependencies {
      implementation(libs.ktor.client.darwin)
    }
  }
}
