plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val javaVersion = 17

allprojects {
    group = "dev.encode42.wrench"
    version = "3.0.0-SNAPSHOT"
    description = "Single-instance Discord bot built with modularity in mind. Includes automation and utilities to enhance your server with ease."

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://m2.dv8tion.net/releases")
    }

    tasks.withType<JavaCompile> {
        apply(plugin = "com.github.johnrengelman.shadow")

        options.encoding = Charsets.UTF_8.name()
        options.release.set(javaVersion)

        java {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(javaVersion))
            }
        }
    }
}

dependencies {
    implementation(group = "dev.encode42.copper", name = "copper-jda", version = "2.1.0-SNAPSHOT")
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.11")
    implementation(group = "com.github.Kaktushose", name = "jda-commands", version = "v.2.2.0")
}

application {
    mainClass.set("dev.encode42.wrench.Main")
}
