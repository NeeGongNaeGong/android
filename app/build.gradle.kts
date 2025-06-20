import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.google.services)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.room)
    alias(libs.plugins.detekt)
}

val properties =
    Properties().apply {
        load(File(rootProject.rootDir, "local.properties").inputStream())
    }

android {
    namespace = "com.ssafy.neegongnaegong"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ssafy.neegongnaegong"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
        buildConfigField("String", "GOOGLE_CLIENT_ID", properties.getProperty("GOOGLE_CLIENT_ID"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        compose = true
        buildConfig = true
    }
    room {
        // 스키마 파일들을 저장할 디렉터리를 지정합니다.
        // "$projectDir/schemas"는 'app/schemas' 경로를 의미하며 가장 표준적인 방식입니다.
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.runtime.compose.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // hilt
    implementation(libs.hilt)
    implementation(libs.hilt.compose)
    ksp(libs.hilt.compiler)

    // network
    implementation(libs.bundles.network)

    // material icon
    implementation(libs.androidx.material.icons.extended)

    // google login
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    // with life cycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // test
    testImplementation(libs.kotlinx.coroutines.test)
    implementation(libs.androidx.lifecycle.runtime.compose.android)

    // data store
    implementation(libs.androidx.datastore.preferences)

    // compose navigation
    implementation(libs.androidx.navigation.compose)

    // serialization
    implementation(libs.kotlinx.serialization.json)

    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // Kotlinx-collections-immutable (Persistent List, Immutable List)
    implementation(libs.kotlinx.collections.immutable)

    // landscapist(image loader, glide)
    implementation(libs.landscapist.glide)

    // paging3
    implementation(libs.paging3)
    implementation(libs.paging3.compose)
    // fcm
    implementation(libs.firebase.messaging.ktx)

    // Room
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
}

// ./gradlew assembleRelease -PenableComposeCompilerMetrics=true -PenableComposeCompilerReports=true 를 이용해서 성능 및 보고서 생성 가능
// 최상위의 build에 생성됨
extensions.configure<ComposeCompilerGradlePluginExtension> {
    fun Provider<String>.onlyIfTrue() = flatMap { provider { it.takeIf(String::toBoolean) } }

    fun Provider<*>.relativeToRootProject(dir: String) =
        map {
            isolated.rootProject.projectDirectory
                .dir("build")
                .dir(projectDir.toRelativeString(rootDir))
        }.map { it.dir(dir) }

    // 만약 gradlew를 통해 enableComposeCompilerMetrics라는 옵션이 들어오거면 메트릭 정보를 수집하여 저장함
    project.providers.gradleProperty("enableComposeCompilerMetrics").onlyIfTrue()
        .relativeToRootProject("compose-metrics")
        .let(metricsDestination::set)

    // 만약 gradlew를 통해 enableComposeCompilerReports 옵션이 들어오거면 리컴포지션 비용 등이 있는 리포트 파일들을 생성하여 저장함
    project.providers.gradleProperty("enableComposeCompilerReports").onlyIfTrue()
        .relativeToRootProject("compose-reports")
        .let(reportsDestination::set)
}
