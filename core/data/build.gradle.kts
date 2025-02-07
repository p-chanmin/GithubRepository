plugins {
    id("githubrepository.android.library")
    id("githubrepository.android.kotlin.serialization")
}

android {
    namespace = "com.dev.githubrepository.core.data"
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.okhttp.logging)
    testImplementation(libs.turbine)

    implementation(project(":core:model"))
    implementation(project(":core:data-api"))
}