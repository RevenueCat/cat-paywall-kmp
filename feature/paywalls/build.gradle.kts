plugins {
  id("catpaywalls.kmp.feature")
}

kotlin {
  sourceSets {
    commonMain.dependencies {
      implementation(libs.purchases.kmp.ui)
    }
  }
}
