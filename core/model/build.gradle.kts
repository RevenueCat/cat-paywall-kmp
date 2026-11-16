plugins {
  id("catpaywalls.kmp.library")
  alias(libs.plugins.kotlin.serialization)
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      api(libs.kotlinx.serialization.json)
      compileOnly("androidx.compose.runtime:runtime-annotation:1.12.1")
    }
  }
}
