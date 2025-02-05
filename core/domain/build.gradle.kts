plugins {
    id("githubrepository.android.library")
}

android {
    namespace = "com.dev.githubrepository.core.domain"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:data-api"))
}