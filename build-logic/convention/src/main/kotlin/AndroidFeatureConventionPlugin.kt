import com.dev.philo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("githubrepository.android.library")
                apply("githubrepository.android.compose")
                apply("githubrepository.android.hilt")
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlinx.immutable").get())

                add("implementation", libs.findLibrary("androidx.appcompat").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                add("implementation", libs.findLibrary("androidx.activity.compose").get())

                add("implementation", libs.findLibrary("hilt.navigation.compose").get())
                add("implementation", libs.findLibrary("androidx.compose.navigation").get())
                add("androidTestImplementation", libs.findLibrary("androidx.compose.navigation.test").get())

                add("implementation", project(":core:model"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":core:designsystem"))
                add("implementation", project(":core:navigation"))
            }
        }
    }
}