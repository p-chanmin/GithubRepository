plugins {
    id("githubrepository.android.application")
}

android {
    namespace = "com.dev.githubrepository"

    defaultConfig {
        applicationId = "com.dev.githubrepository"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":feature:main"))
}