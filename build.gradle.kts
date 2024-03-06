// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.gradle)
        classpath(libs.firebase.crashlytics.gradle)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.androidLibrary) apply false
}