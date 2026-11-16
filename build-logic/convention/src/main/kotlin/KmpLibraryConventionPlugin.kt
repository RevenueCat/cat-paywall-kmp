/*
 * Copyright (c) 2025 RevenueCat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
        apply("com.android.kotlin.multiplatform.library")
      }

      extensions.configure<KotlinMultiplatformExtension> {
        extensions.configure<KotlinMultiplatformAndroidLibraryTarget>("android") {
          // Derived from the project path so each module keeps the namespace it declared before AGP 9,
          // e.g. :core:data -> com.revenuecat.catpaywalls.core.data
          namespace = "com.revenuecat.catpaywalls${project.path.replace(':', '.')}"
          compileSdk = libs.findVersion("android-compileSdk").get().toString().toInt()
          minSdk = libs.findVersion("android-minSdk").get().toString().toInt()

          compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
          }

          // Host tests are opt-in with the KMP library plugin. Without this, commonTest would no longer run
          // on the JVM (testAndroidHostTest), silently dropping the unit tests that testDebugUnitTest ran.
          withHostTest {}
        }

        // No iosX64(): Compose Multiplatform 1.11+ and the AndroidX KMP libraries no longer publish Apple x86_64
        // variants (deprecated in Kotlin, KT-81596), so Intel simulators cannot be targeted anymore.
        listOf(
          iosArm64(),
          iosSimulatorArm64(),
        ).forEach { iosTarget ->
          iosTarget.binaries.framework {
            baseName = project.name
            isStatic = true
          }
        }

        sourceSets.apply {
          commonMain.dependencies {
            implementation(libs.findLibrary("kotlinx-coroutines-core").get())
          }

          commonTest.dependencies {
            implementation(libs.findLibrary("kotlin-test").get())
            implementation(libs.findLibrary("kotlinx-coroutines-test").get())
            implementation(libs.findLibrary("turbine").get())
          }
        }
      }
    }
  }
}

private val Project.libs
  get() =
    extensions
      .getByType(org.gradle.api.artifacts.VersionCatalogsExtension::class.java)
      .named("libs")
