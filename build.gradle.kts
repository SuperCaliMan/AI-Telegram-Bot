import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("maven-publish")
    id("java")
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "com.serverless"
version = "dev"

description = """Transcription bot"""

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


repositories {
    mavenCentral()
    google()
    maven { url = uri("https://jitpack.io") }
}

// If requiring AWS JDK, uncomment the dependencyManagement to use the bill of materials
//   https://aws.amazon.com/blogs/developer/managing-dependencies-with-aws-sdk-for-java-bill-of-materials-module-bom/
//dependencyManagement {
//    imports {
//        mavenBom 'com.amazonaws:aws-java-sdk-bom:1.11.688'
//    }
//}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.3")
    implementation("com.amazonaws:aws-lambda-java-log4j2:1.6.0")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")

    implementation("com.aallam.openai:openai-client:3.7.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("io.ktor:ktor-client-cio:2.3.10")
    implementation("io.ktor:ktor-client-logging:2.3.10")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.10")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.10")
    implementation("io.ktor:ktor-client-auth:2.3.10")

}

tasks.withType<ShadowJar> {
    archiveFileName.set("telegram-bot_${project.version}.jar")
}

tasks.register("deploy", Exec::class) {
    dependsOn("shadowJar")
    commandLine("sls", "deploy")
}


tasks.getByName("build").finalizedBy(tasks.getByName("shadowJar"))

tasks.shadowJar {
    transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)
}

tasks.getByName("build").finalizedBy(tasks.getByName("shadowJar"))

