@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.tsundokun.android.application)
    alias(libs.plugins.tsundokun.android.application.compose)
    alias(libs.plugins.tsundokun.android.hilt)
    alias(libs.plugins.tsundokun.android.room)
    alias(libs.plugins.tsundokun.android.firebase)
    alias(libs.plugins.spotless)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.example.tsundokun"

    defaultConfig {
        applicationId = "com.example.tsundokun"
        minSdk = 24
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
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

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("$buildDir/**/*.kt")
        targetExclude("bin/**/*.kt")

        ktlint("0.49.1")
    }
}

dependencies {
    implementation(project(":core:model"))

    // Jetpack Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.coil.kt)
    implementation(libs.coil.compose)
    implementation(libs.jsoup)

    // Compose Destinations
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    // Supabase
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.postgrest.kt)

    // Ktor
    implementation(libs.ktor.client.cio)

    // Fido
    implementation("com.google.android.gms:play-services-fido:20.1.0")

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)

}