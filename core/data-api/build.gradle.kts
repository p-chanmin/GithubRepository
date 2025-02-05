plugins {
    id("githubrepository.android.library")
}

android {
    namespace = "com.dev.githubrepository.core.data_api"
}

dependencies {
    implementation(project(":core:model"))
}