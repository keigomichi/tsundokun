import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.example.tsundokun.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.firebase.crashlytics.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin{
    plugins{
        register("androidApplicationCompose") {
            id = "tsundokun.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication"){
            id = "tsundokun.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose"){
            id = "tsundokun.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary"){
            id = "tsundokun.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature"){
            id = "tsundokun.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidHilt"){
            id = "tsundokun.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("androidRoom"){
            id = "tsundokun.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidTest"){
            id = "tsundokun.android.test"
            implementationClass = "AndroidTestConventionPlugin"
        }
    }
}
