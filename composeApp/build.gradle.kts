import org.jetbrains.kotlin.gradle.dsl.JvmTarget

// Shared KMP library: Android consumes it from :androidApp, iOS consumes the ComposeApp framework via CocoaPods.
plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.metro)
  kotlin("native.cocoapods")
}

kotlin {
  android {
    namespace = "com.revenuecat.catpaywalls.composeapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    minSdk = libs.versions.android.minSdk.get().toInt()

    compilerOptions {
      jvmTarget.set(JvmTarget.JVM_11)
    }
  }

  // No iosX64(): Compose Multiplatform 1.11+ and the AndroidX KMP libraries no longer publish Apple x86_64
  // variants (deprecated in Kotlin, KT-81596), so Intel simulators cannot be targeted anymore.
  listOf(
    iosArm64(),
    iosSimulatorArm64(),
  ).forEach { iosTarget ->
    iosTarget.binaries.framework {
      baseName = "ComposeApp"
      isStatic = true
    }
  }

  cocoapods {
    summary = "Cat Paywalls KMP App"
    homepage = "https://github.com/revenuecat/cat-paywalls-kmp"
    version = "1.0"
    ios.deploymentTarget = "15.0"
    podfile = project.file("../iosApp/Podfile")
    framework {
      baseName = "ComposeApp"
      isStatic = true
    }
    // No RevenueCat pods: purchases-kmp bundles purchases-ios into this framework, so adding them would ship
    // duplicate RevenueCat classes. Purchases is configured from Kotlin (iosMain/ConfigurePurchases.kt).
  }

  sourceSets {
    commonMain.dependencies {
      // Core modules - use api() for modules with Metro bindings
      implementation(projects.core.model)
      api(projects.core.network)
      api(projects.core.data)
      implementation(projects.core.designsystem)
      implementation(projects.core.navigation)

      // Feature modules
      implementation(projects.feature.home)
      implementation(projects.feature.article)
      implementation(projects.feature.paywalls)
      implementation(projects.feature.account)
      implementation(projects.feature.subscriptions)
      implementation(projects.feature.bookmarks)

      // Compose
      implementation(libs.compose.runtime)
      implementation(libs.compose.foundation)
      implementation(libs.compose.material3)
      implementation(libs.compose.ui)
      implementation(libs.compose.components.resources)

      // Navigation
      implementation(libs.navigation.compose)

      // Coroutines
      implementation(libs.kotlinx.coroutines.core)

      // Serialization
      implementation(libs.kotlinx.serialization.json)

      // RevenueCat
      implementation(libs.purchases.kmp.core)

      // Image loading (Landscapist)
      implementation(libs.landscapist.image)
      implementation(libs.landscapist.placeholder)
      implementation(libs.landscapist.zoomable)

      // Lifecycle (JetBrains KMP)
      implementation(libs.jetbrains.lifecycle.viewmodel)
      implementation(libs.jetbrains.lifecycle.viewmodel.compose)
    }

    androidMain.dependencies {
      implementation(libs.kotlinx.coroutines.android)
      implementation(libs.ktor.client.okhttp)
    }

    iosMain.dependencies {
      implementation(libs.ktor.client.darwin)
    }
  }
}
