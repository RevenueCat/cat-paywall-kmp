plugins {
  id("catpaywalls.kmp.feature")
}

kotlin {
  sourceSets {
    commonTest.dependencies {
      implementation(projects.core.data)
    }
  }
}
