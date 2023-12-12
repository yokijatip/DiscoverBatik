plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.enigma.discoverbatik"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.enigma.discoverbatik"
        minSdk = 26
        targetSdk = 33
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

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {


    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    //    Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    //    Okhttp
    val okhttpVersion = "4.11.0"
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")

    //    ViewModel
    val viewModelVersion = "2.6.2"
    val activityKtxVersion = "1.7.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$viewModelVersion")
    implementation("androidx.activity:activity-ktx:$activityKtxVersion")

    //    Coroutine
    val coroutineVersion = "1.3.9"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

    //    Datastore
    val dataStoreVersion = "1.0.0"
    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    //    Rounded Image
    val roundedImageVersion = "2.3.0"
    implementation("com.makeramen:roundedimageview:$roundedImageVersion")

    //    Paging 3
    val pagingVersion = "3.1.1"
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")

    //    Glide
    val glideVersion = "4.16.0"
    implementation("com.github.bumptech.glide:glide:$glideVersion")

    //    View Pager 2
    val viewPager2Version = "1.0.0"
    implementation("androidx.viewpager2:viewpager2:$viewPager2Version")

    //    CameraX
    val cameraXversion = "1.2.2"
    implementation ("androidx.camera:camera-core:$cameraXversion")
    implementation ("androidx.camera:camera-camera2:$cameraXversion")
    implementation ("androidx.camera:camera-lifecycle:$cameraXversion")
    implementation ("androidx.camera:camera-video:$cameraXversion")

    implementation ("androidx.camera:camera-view:$cameraXversion")
    implementation ("androidx.camera:camera-extensions:$cameraXversion")




    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}