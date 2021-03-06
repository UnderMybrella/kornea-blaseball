plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.adarshr.test-logger")
}

apply(plugin = "kotlinx-atomicfu")
apply(plugin = "maven-publish")

group = "dev.brella"
version = "2.3.1-alpha"

repositories {
    mavenCentral()
    mavenLocal()
    maven(url = "https://maven.brella.dev")
    maven(url = "https://kotlin.bintray.com/ktor")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
            kotlinOptions.useIR = true
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
//    js(IR) {
//        browser {
//            testTask {
//                useKarma {
//                    useChromeHeadless()
//                    webpackConfig.cssSupport.enabled = true
//                }
//            }
//        }
//    }
//    val hostOs = System.getProperty("os.name")
//    val isMingwX64 = hostOs.startsWith("Windows")
//    val nativeTarget = when {
//        hostOs == "Mac OS X" -> macosX64("native")
//        hostOs == "Linux" -> linuxX64("native")
//        isMingwX64 -> mingwX64("native")
//        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:1.5.0")
                implementation("io.ktor:ktor-client-serialization:1.5.0")

                implementation("dev.brella:kornea-io:5.2.0-alpha")
                implementation("dev.brella:kornea-toolkit:3.3.1-alpha")
                implementation("dev.brella:ktornea-utils:1.2.3-alpha")

                implementation("org.jetbrains.kotlinx:atomicfu:0.16.1")
                api(project(":kornea-blaseball-base"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-apache:1.5.0")
                implementation("io.ktor:ktor-client-okhttp:1.5.0")
                implementation("io.ktor:ktor-client-encoding:1.5.0")
                implementation("io.ktor:ktor-client-core-jvm:1.5.0")
                implementation("dev.brella:ktornea-apache:1.0.0-alpha")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
                implementation("org.junit.jupiter:junit-jupiter-params:5.7.1")
                runtimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")

                implementation("io.ktor:ktor-client-apache:1.5.0")
                implementation("io.ktor:ktor-client-okhttp:1.5.0")
                implementation("io.ktor:ktor-client-encoding:1.5.0")
                implementation("io.ktor:ktor-client-core-jvm:1.5.0")
                implementation("dev.brella:ktornea-apache:1.0.0-alpha")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.3")
            }
        }
//        val jsMain by getting
//        val jsTest by getting {
//            dependencies {
//                implementation(kotlin("test-js"))
//            }
//        }
//        val nativeMain by getting
//        val nativeTest by getting
    }
}

configure<kotlinx.atomicfu.plugin.gradle.AtomicFUPluginExtension> {
    dependenciesVersion = null
}

configure<PublishingExtension> {
    repositories {
        maven(url = "${rootProject.buildDir}/repo")
    }
}