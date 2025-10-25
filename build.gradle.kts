plugins {
    kotlin("multiplatform") version "1.8.0"
    id("org.jetbrains.dokka") version "1.8.10"
    id("org.jetbrains.kotlinx.kover") version "0.9.0"
    id("maven-publish")
}

group = "com.sztorm"
version = "1.1.0"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
    jvm {
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        hostOs.startsWith("Windows") -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter:5.9.2")
                implementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
            }
        }
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}

//./gradlew --continue jvmTest generateTestStatusBadge
tasks.register<GenerateTestsStatusBadge>("generateTestStatusBadge") {
    testsStatusReportInput.set(project.layout.buildDirectory.file("reports/tests/jvmTest/index.html"))
    badgeOutput.set(project.layout.projectDirectory.file("misc/testsStatus.svg"))
    mustRunAfter("jvmTest")
}

//./gradlew --continue koverXmlReport generateCoverageBadge
tasks.register<GenerateCoverageBadge>("generateCoverageBadge") {
    testsStatusReportInput.set(project.layout.buildDirectory.file("reports/tests/jvmTest/index.html"))
    coverageReportInput.set(project.layout.buildDirectory.file("reports/kover/report.xml"))
    badgeOutput.set(project.layout.projectDirectory.file("misc/testCoverage.svg"))
    mustRunAfter("koverXmlReport")
}

//./gradlew --continue generateBadges
tasks.register("generateBadges") {
    dependsOn("jvmTest", "generateTestStatusBadge", "koverXmlReport", "generateCoverageBadge")
    tasks.findByPath("koverXmlReport")!!.mustRunAfter("generateTestStatusBadge")
}

// Build docs
//./gradlew dokkaHtml