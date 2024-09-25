
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    //change from 1.9.0 to 1.9.21
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
    //id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("com.google.devtools.ksp") version "1.9.21-1.0.16" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false

    //google service for firebase
    id("com.google.gms.google-services") version "4.4.2" apply false
}
