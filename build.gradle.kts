// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.compose.multiplatform) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.kotlin.multiplatform.library) apply false
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("**/*.kt")
    targetExclude(
      "**/build/**",
      "**/.gradle/**",
      "**/spotless/**",
    )
    ktlint().editorConfigOverride(
      mapOf(
        "ktlint_function_naming_ignore_when_annotated_with" to "Composable",
        "max_line_length" to "120",
      ),
    )
    trimTrailingWhitespace()
    endWithNewline()
    licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
  }
  kotlinGradle {
    target("**/*.kts")
    targetExclude(
      "**/build/**",
      "**/.gradle/**",
    )
    ktlint().editorConfigOverride(
      mapOf(
        "max_line_length" to "120",
      ),
    )
    trimTrailingWhitespace()
    endWithNewline()
  }
}
