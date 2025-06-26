plugins {
    id("com.android.application")
    id("com.google.gms.google-services") // Plugin de Google Services para Firebase
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.appsplashlogin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appsplashlogin"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    // Importa el Firebase BoM para manejar versiones compatibles automáticamente
    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))

    // Dependencias de Firebase sin versión explícita (versiones gestionadas por el BoM)
    implementation("com.google.firebase:firebase-auth-ktx")       // Firebase Authentication con extensiones Kotlin
    implementation("com.google.firebase:firebase-analytics-ktx")  // Firebase Analytics (opcional)

    // Otras dependencias de Android
    implementation("androidx.activity:activity-ktx:1.8.1")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.activity)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
