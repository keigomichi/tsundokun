plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.tsundokun.core.model"
    compileSdk = 33

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
