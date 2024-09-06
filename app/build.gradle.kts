plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.hiltAndroid)
    id("com.google.gms.google-services")
    id("com.google.protobuf") version "0.9.4"
//    kotlin("plugin.serialization") version "1.9.24"
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization").version("1.9.21")
}

android {
    namespace = "br.sapiens.bellus_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.sapiens.bellus_app"
        minSdk = 27
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"

        freeCompilerArgs += "-Xextended-compiler-checks"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    implementation(libs.firebase.auth.ktx)
//    implementation(platform("com.google.firebase:firebase-bom:32.7.4"))
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))

// Add the dependency for the Firebase SDK for Google Analytics
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-appcheck-playintegrity:18.0.0")
    implementation("com.google.firebase:firebase-appcheck")
    implementation("com.firebaseui:firebase-ui-auth:8.0.0")

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.navigation.compose)
//    implementation(libs.androidx.material3.android)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.camera.core)
    implementation(libs.googleid)
    implementation(libs.androidx.animation.graphics.android)
//    implementation(libs.androidx.tv.material)
    testImplementation("org.testng:testng:6.9.6")
    testImplementation("org.testng:testng:6.9.6")
    val composeBom = platform("androidx.compose:compose-bom:2024.02.02")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Choose one of the following:
    // Material Design 3
    implementation("androidx.compose.material3:material3")
    // or Material Design 2
//    implementation("androidx.compose.material:material")
    // or skip Material Design and build directly on top of foundational components
    implementation("androidx.compose.foundation:foundation")
    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    implementation("androidx.compose.ui:ui")


    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    implementation("androidx.compose.material:material-icons-core")
    // Optional - Add full set of material icons
    implementation("androidx.compose.material:material-icons-extended")
    // Optional - Add window size utils
//    implementation("androidx.compose.material3:material3-window-size-class")

    // Optional - Integration with activities
    implementation("androidx.activity:activity-compose:1.8.2")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // Optional - Integration with RxJava
    implementation("androidx.compose.runtime:runtime-rxjava2")

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.34.0")

    implementation("com.google.android.gms:play-services-location:21.2.0")
    implementation("com.google.android.gms:play-services-auth:21.1.1")
//    implementation("androidx.credentials:credentials:1.3.0-alpha03")
    // https://mvnrepository.com/artifact/androidx.credentials/credentials
    implementation("androidx.credentials:credentials:1.2.2")

//    implementation("androidx.credentials:credentials-play-services-auth:1.3.0-alpha03")
    // https://mvnrepository.com/artifact/androidx.credentials/credentials-play-services-auth
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")

//    implementation("jakarta.validation:jakarta.validation-api:3.1.0-M2")

    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

//    implementation("com.github.Cidaas:cidaas-android-sdk:3.0.8")

    /*
     * DATA STORE
     */
    implementation("androidx.datastore:datastore:1.1.1")

    // https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java
    implementation("com.google.protobuf:protobuf-javalite:4.26.1")

//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    /*
     * KTOR CLIENT
     */
    val ktorVersion = "2.3.12"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
    implementation("io.ktor:ktor-client-auth:$ktorVersion")

    // MOCKITO

//    testImplementation("org.mockito:mockito-core:5.13.0")
//    testImplementation("org.mockito:mockito-inline:5.13.0")
//    // https://mvnrepository.com/artifact/org.mockito.kotlin/mockito-kotlin
//    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
//
//    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.0")

}

kapt {
    correctErrorTypes = true
    javacOptions {
        // Increase the max count of errors from annotation processors.
        // Default is 100.
        option("-Xmaxerrs", 500)
    }
    useBuildCache = false
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:4.26.1"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins.create("java") {
                option("lite")
            }
        }
    }
}

//configurations {
//    all {
//        exclude(group = "com.google.protobuf", module = "protobuf-java")
//    }
//}

//configurations {
//    all {
//        exclude(group = "com.google.protobuf", module = "androidx.datastore")
//    }
//}
