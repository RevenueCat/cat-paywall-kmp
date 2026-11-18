plugins {
  id("catpaywalls.kmp.library.compose")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.compose.material.icons.extended)
    }
  }
}
