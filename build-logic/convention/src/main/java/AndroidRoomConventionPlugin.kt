/*
roomのバージョンを上げるとcompileSdk = 34以上じゃないとエラーが出るため、
一旦様子見でコメントアウトしている
https://androidx.tech/artifacts/room/androidx.room.gradle.plugin/
 */
//class AndroidRoomConventionPlugin : Plugin<Project> {
//    override fun apply(target: Project) {
//        with(target) {
//            with(pluginManager) {
//                apply("androidx.room")
//                apply("com.google.devtools.ksp")
//            }
//
//            extensions.configure<RoomExtension> {
//                schemaDirectory("%projectDir/schemas")
//            }
//
//            dependencies {
//                add("implementation", libs.findLibrary("room-runtime").get())
//                add("implementation", libs.findLibrary("room-ktx").get())
//                add("ksp", libs.findLibrary("room-compiler").get())
//            }
//        }
//    }
//}
