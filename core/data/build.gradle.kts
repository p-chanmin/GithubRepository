plugins {
    id("githubrepository.android.library")
    id("githubrepository.android.kotlin.serialization")
}

android {
    namespace = "com.dev.githubrepository.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data-api"))
}