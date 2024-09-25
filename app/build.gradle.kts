
plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.anytimememo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.anytimememo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        // Set JVM target from 1.8 to 19
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        //change from 1.5.1 to 1.4.1
        //https://developer.android.com/jetpack/androidx/releases/compose-kotlin
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures{
        viewBinding = true
    }
    //https://github.com/auth0/Auth0.Android/issues/598
    //package conflict when merge bug
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "**/*"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.room.common)
    implementation(libs.firebase.database.ktx)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.material.icons.extended)
    // Navigation with Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    // Modal Drawer Layout
    implementation (libs.androidx.drawerlayout)

    // Coroutines
    implementation (libs.kotlinx.coroutines.android)

    //location
    implementation (libs.play.services.location)

    //splash screen
    dependencies {
        implementation (libs.androidx.appcompat.v161)
    }
    dependencies {
        implementation(libs.androidx.core.splashscreen)
    }

    dependencies {
        implementation (libs.kotlinx.coroutines.core) // or the latest version
        implementation (libs.kotlinx.coroutines.android.v171) // if using Android
    }

    //dagger
    //https://stackoverflow.com/questions/77104555/whats-the-difference-between-these-gradle-deps-for-and-dagger-hilt-compilers
    dependencies {
        //val room_version = "2.6.1"

        ksp (libs.dagger.compiler.v248) // Dagger compiler
        ksp (libs.dagger.hilt.compiler)   // Hilt compiler

        implementation(libs.androidx.room.runtime)
        implementation (libs.androidx.room.ktx)
        annotationProcessor(libs.androidx.room.compiler)
        ksp(libs.androidx.room.compiler)
        //implementation(libs.hilt.android)
        //ksp (libs.hilt.android.compiler.v2391)
        implementation(libs.hilt.android.v249)

    }
    dependencies {
        implementation(libs.hilt.navigation.compose)
    }

    //Firebase
    dependencies{
        implementation(libs.firebase.auth.ktx)
        implementation(platform(libs.firebase.bom))
    }
}