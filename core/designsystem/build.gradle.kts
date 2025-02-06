plugins {
    id("githubrepository.android.library")
    id("githubrepository.android.compose")
}

android {
    namespace = "com.dev.githubrepository.core.designsystem"
}

dependencies {
    implementation(libs.coil.compose)
}