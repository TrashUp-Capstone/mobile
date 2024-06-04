// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin) apply false
}

tasks.register<Exec>("acceptLicenses") {
    description = "Accept Android SDK licenses"
    commandLine = listOf(
        "${System.getenv("ANDROID_HOME")}/cmdline-tools/latest/bin/sdkmanager",
        "--licenses"
    )
}