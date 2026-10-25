plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // TAMBAHKAN PLUGIN INI
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.robaap"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.robaap"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation("io.socket:socket.io-client:2.1.0")

    // --- TAMBAHKAN LIBRARY DI BAWAH INI ---

    // 1. WebRTC
    implementation("org.webrtc:google-webrtc:1.0.32006")

    // 2. Firebase
    // Firebase BoM (Bill of Materials)
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database-ktx")
}