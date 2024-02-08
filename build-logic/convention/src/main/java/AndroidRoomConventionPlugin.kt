import androidx.room.gradle.RoomExtension
import com.example.tsundokun.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("%projectDir/schemas")
            }

            dependencies {
                add("implementation", libs.findLibrary("room-runtime").get())
                add("implementation", libs.findLibrary("room-ktx").get())
                add("ksp", libs.findLibrary("room-compiler").get())
            }
        }
    }
}
