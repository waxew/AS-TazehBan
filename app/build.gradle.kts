import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.Properties

plugins {
    id("git-info")
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.compose)
    alias(libs.plugins.screenshot)
}

val buildFossProperty = "buildFoss"
val buildFoss = project.hasProperty(buildFossProperty)

val keystorePropertiesFile: File = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
var areKeystorePropertiesLoaded = false
try {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
    areKeystorePropertiesLoaded = true
} catch (_: FileNotFoundException){
    println("File keystore.properties not found!")
}

android {
    // AS Team package migration
    namespace = "com.asteam.tazehban"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.asteam.tazehban"
        minSdk = 26
        targetSdk = 37
        versionCode = 62
        versionName = "2.12.0"

        base.archivesName.set("AS-TazehBan-$versionName")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    if (areKeystorePropertiesLoaded) {
        signingConfigs {
            create("release") {
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (areKeystorePropertiesLoaded) {
                signingConfig = signingConfigs.getByName("release")
            }
        }
    }
}
