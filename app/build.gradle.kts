plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.example.offerwalldemoapp"
    compileSdk = 34

    signingConfigs {
        create("release") {
            keyAlias = "demo-key"
            keyPassword = "demoPass123"
            storeFile = file("../release.jks")
            storePassword = "demoPass123"
        }
    }

    defaultConfig {
        applicationId = "com.greedygame.offerwall.demo"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.006"
        val apkName = "PubScale Offerwall Demo - $versionName($versionCode).apk"
        buildOutputs.all {
            val variantOutputImpl =
                this as com.android.build.gradle.internal.api.BaseVariantOutputImpl
            variantOutputImpl.outputFileName = apkName
        }
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

configurations.configureEach {
    resolutionStrategy {
        force("com.pubscale.caterpillar:analytics:0.20")
    }
}

dependencies {
    implementation(project(":sharedLibs"))
    implementation(project(":navigation"))
}