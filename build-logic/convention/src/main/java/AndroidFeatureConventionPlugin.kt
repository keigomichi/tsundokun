import com.example.tsundokun.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("tsundokun.android.library")
                apply("tsundokun.android.hilt")
            }

            dependencies{
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())

            }
        }
    }
}