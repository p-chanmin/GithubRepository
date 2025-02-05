plugins {
    id("githubrepository.android.feature")
}

android {
    namespace = "com.dev.githubrepository.feature.main"
}

dependencies {
    implementation(project(":feature:search"))
    implementation(project(":feature:repository"))
}