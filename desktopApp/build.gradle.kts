import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

dependencies {
    implementation(projects.shared)
    implementation(libs.ktor.client.okhttp)
    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)
    implementation(libs.koin.core)
    implementation(libs.compose.uiToolingPreview)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_21  // same theـ shared
    }
}

compose.desktop {
    application {
        mainClass = "app.ali.titan.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "app.ali.titan"
            packageVersion = "1.0.0"
        }

        buildTypes.release.proguard {
            isEnabled = true
            optimize = true
        }
    }
}