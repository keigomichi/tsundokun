package com.example.tsundokun.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.2"
        }

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }

        dependencies{
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

            add("implementation", libs.findLibrary("ui").get())
            add("implementation", libs.findLibrary("ui-graphics").get())
            add("implementation", libs.findLibrary("ui-tooling").get())
            add("implementation", libs.findLibrary("ui-tooling-preview").get())
            add("implementation", libs.findLibrary("material3").get())
            add("implementation", libs.findLibrary("navigation.compose").get())

            add("testImplementation", libs.findLibrary("ui-test-manifest").get())
            add("testImplementation", libs.findLibrary("junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx.test.ext.junit").get())
            add("androidTestImplementation", libs.findLibrary("espresso-core").get())
            add("androidTestImplementation", libs.findLibrary("ui-test-junit4").get())

            add("debugImplementation", libs.findLibrary("ui-tooling").get())
            add("debugImplementation", libs.findLibrary("ui-test-manifest").get())
        }
    }
}