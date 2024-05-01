plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.abhiket.qrcodescanner"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.abhiket.qrcodescanner"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.12"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // ANDROID CORE LIBRARY \\
    implementation("androidx.core:core-ktx:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // COMPOSE UI LIBRARY \\
    implementation(platform("androidx.compose:compose-bom:2024.04.01"))
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui")

    // MATERIAL3 LIBRARY \\
    implementation("androidx.compose.material3:material3:1.2.1")

    // Compose PREVIEW \\
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.ui:ui-tooling:1.6.6")

    // CAMERAX LIBRARY \\
    val cameraxVersion = "1.3.3"
    implementation("androidx.camera:camera-core:${cameraxVersion}")
    implementation("androidx.camera:camera-camera2:${cameraxVersion}")
    implementation("androidx.camera:camera-view:${cameraxVersion}")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")

    // GOOGLE ZING FOR QR SCANNER \\
    implementation("com.google.zxing:core:3.5.3")

    // JETPACK COMPOSE NAVIGATION \\
    implementation("androidx.navigation:navigation-compose:2.7.7")
}