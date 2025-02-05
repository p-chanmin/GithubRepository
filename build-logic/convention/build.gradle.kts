import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.dev.githubrepository.buildlogic"

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
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "githubrepository.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidHilt") {
            id = "githubrepository.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibrary") {
            id = "githubrepository.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidFeature") {
            id = "githubrepository.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }

        register("androidCompose") {
            id = "githubrepository.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("androidKotlinSerialization") {
            id = "githubrepository.android.kotlin.serialization"
            implementationClass = "AndroidKotlinSerializationConventionPlugin"
        }
    }
}