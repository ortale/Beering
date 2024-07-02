// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
}
extra.apply {
    set("FLAVOR_DIMENSION", "mode")
    set("DEV_API_URL", "http://192.168.1.115:5002")
    set("DEV_APP_LABEL", "Beering-Dev")
    set("DEV_APP_ICON", "@mipmap/ic_launcher")

    set("PROD_API_URL", "http://159.65.21.221:5002")
    set("PROD_APP_LABEL", "Beering")
    set("PROD_APP_ICON", "@mipmap/ic_launcher")
}