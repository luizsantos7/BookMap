
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.59" apply false
    id("com.google.devtools.ksp") version "2.3.4" apply false
    kotlin("plugin.serialization") version "2.3.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    alias(libs.plugins.google.gms.google.services) apply false
}

detekt {
    toolVersion = "1.23.6"
    config = files("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
    autoCorrect = true
}

// Configurar para todos os submódulos
subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        config.setFrom(rootProject.files("config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        autoCorrect = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        jvmTarget = "11"  // Deve corresponder ao seu JavaVersion
        reports {
            html.required.set(true)
            xml.required.set(true)
            txt.required.set(true)
            sarif.required.set(true)
        }
    }
}