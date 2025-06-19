import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.hilt.plugin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt)
}

dependencies {
    // detekt formatting
    detektPlugins(libs.detekt.formatting)
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom(files("$rootDir/detekt.yml"))
}

// Stage된 파일만 검사하는 커스텀 태스크
val stagedFiles: String? by project
val stagedFileList =
    stagedFiles
        ?.lines()
        ?.filter { it.isNotBlank() }
        ?.map { file(it) } ?: emptyList()
tasks.register<Detekt>("detektStaged") {
    description = "Run Detekt on staged Kotlin files"
    group = "verification"

    setSource(files(stagedFileList))
    config.setFrom(files("$rootDir/detekt.yml"))
    buildUponDefaultConfig = true

    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(true)
    }
}
