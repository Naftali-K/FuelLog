plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.roomPlugin)
//    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.fuellog"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.fuellog"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

//    kotlin {
//        compilerOptions {
//            optIn.add("kotlin.RequiresOptIn")
//        }
//    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.swiperefreshlayout)

    // Room dependencies
    implementation(libs.roomRuntime)
    ksp(libs.roomCompiler)
    implementation(libs.lifeCycleViewModel) // https://developer.android.com/jetpack/androidx/releases/lifecycle
}