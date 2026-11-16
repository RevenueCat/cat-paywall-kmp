plugins {
  id("catpaywalls.kmp.library.compose")
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(projects.core.model)

      api(libs.navigation.compose)
      api(libs.kotlinx.serialization.json)
    }
  }
}
