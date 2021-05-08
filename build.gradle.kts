buildscript {
    dependencies {
        classpath("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.16.1")
//        classpath(kotlin("serialization", version = "1.4.30"))
    }
}

plugins {
    kotlin("jvm") version "1.5.0" apply false
    kotlin("multiplatform") version "1.5.0" apply false
    kotlin("plugin.serialization") version "1.5.0" apply false
    id("com.adarshr.test-logger") version "3.0.0" apply false
}