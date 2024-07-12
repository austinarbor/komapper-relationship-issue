import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.springBoot)
    alias(libs.plugins.kotlin.spring)
}

group = "com.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(springLibs.spring.springBootStarterJdbc)
    implementation(komapperLibs.komapperSpringBootStarterJdbc)
    implementation(komapperLibs.komapperDialectPostgresqlJdbc)
    implementation(komapperLibs.komapperSpringJdbc)
    implementation(springLibs.flywaydb.flywayDatabasePostgresql)

    runtimeOnly(komapperLibs.komapperSlf4j)
    runtimeOnly(springLibs.postgresql.postgresql)

    ksp(komapperLibs.komapperProcessor)

    testImplementation(libs.junit.jupiter)
    testImplementation(springLibs.spring.springBootStarterTest)
    testImplementation(libs.assertj)
}

tasks {
    withType<KotlinCompile>().configureEach {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-opt-in=org.komapper.annotation.KomapperExperimentalAssociation",
                "-Xcontext-receivers",
            )
        }
    }
    test {
        useJUnitPlatform()
    }
}
kotlin {
    jvmToolchain(21)
}
