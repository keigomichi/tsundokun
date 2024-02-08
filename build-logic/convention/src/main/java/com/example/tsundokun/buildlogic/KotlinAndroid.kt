package com.example.tsundokun.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>
){
    commonExtension.apply {
        compileSdk = 34
        defaultConfig{
            minSdk = 24
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}