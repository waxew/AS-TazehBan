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

// AS Team: namespace identity update. Remaining source package migration is handled separately.
android {
    namespace = "com.asteam.tazehban"
}
