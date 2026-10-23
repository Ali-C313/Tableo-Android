import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.google.services)
}

// API-Key für Google Maps steht in local.properties (MAPS_API_KEY=...), damit er nicht auf GitHub landet
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}

android {
    namespace = "com.example.tableo"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        // gleiche ID wie bisher, damit die vorhandene google-services.json passt
        applicationId = "com.example.abschluss_android_2026_ali313"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["MAPS_API_KEY"] = localProperties.getProperty("MAPS_API_KEY", "")
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Icons
    implementation(libs.androidx.compose.material.icons.extended)

    // Navigation mit typisierten Routen
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization)

    // Retrofit und Moshi für die Wechselkurs-API
    implementation(libs.retrofit)
    implementation(libs.converterMoshi)
    implementation(libs.moshi)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)

    // Coil für Bilder aus URLs
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // DataStore für lokale Einstellungen
    implementation(libs.androidx.datastore.preferences)

    // Firebase: Anmeldung, Datenbank, Fotos
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.kotlinx.coroutines.play.services)

    // Standort und Google Maps für Lieferung und Live-Tracking
    implementation(libs.play.services.location)
    implementation(libs.maps.compose)
}
