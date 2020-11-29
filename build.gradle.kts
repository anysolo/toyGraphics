import org.gradle.api.publish.maven.*

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.jfrog.bintray.gradle.BintrayExtension
import org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention
import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig

import org.jetbrains.dokka.gradle.DokkaTask

import groovy.lang.GroovyObject
import java.util.*


buildscript {
    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.1")
        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4+")
    }
}

plugins {
    val kotlinVersion = "1.4.20"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    maven
    `maven-publish`
    signing
    id("org.jetbrains.dokka") version "0.9.18"
    id("com.jfrog.bintray") version "1.8.4"
    id("com.jfrog.artifactory") version "4.9.5"
}

repositories {
    jcenter()
    mavenCentral()
}

val myVersion = "dev-v2-0.1.1"

group = "com.anysolo"
version = myVersion

val samples by configurations.creating {
    this.extendsFrom(configurations["compile"])
    this.extendsFrom(configurations["runtime"])
}

sourceSets.create("samples") {
    java.srcDir("src/samples/kotlin")
    
    compileClasspath += sourceSets["main"].output
    compileClasspath += configurations["samples"]
    runtimeClasspath += output
    runtimeClasspath += configurations["runtime"]
    runtimeClasspath += configurations["samples"]
}

dependencies {
    //val jacksonVer = "2.11.3"
    val kotlinVersion = "1.4.20"

    compile(gradleApi())
    compile(kotlin("stdlib"))
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")

    compile("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
/*
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVer")
    implementation("com.fasterxml.jackson.core:annotations:$jacksonVer")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVer")
*/

    samples(kotlin("stdlib"))
    samples(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}


val dokka by tasks.getting(DokkaTask::class) {
    outputFormat = "html"
    outputDirectory = "$buildDir/dokka-html"
    reportUndocumented = false
    includeNonPublic = false
    samples = listOf("src/samples/kotlin/com/anysolo/toyGraphics/samples")

    packageOptions {
        prefix = "com.anysolo.toyGraphics.internals"
        suppress = true
    }

    packageOptions {
        prefix = "com.anysolo.toyGraphics.samples"
        suppress = true
    }
}

val kdocZip by tasks.creating(Zip::class) {
    archiveClassifier.set("kdoc")
    from("$buildDir/dokka-html")
    dependsOn(dokka)
}

val dokkaJavaDoc by tasks.creating(DokkaTask::class) {
    outputFormat = "javadoc"
    outputDirectory = "$buildDir/dokka-javadoc"
}

val javadocJar by tasks.creating(Jar::class) {
    archiveClassifier.set("javadoc")
    from("$buildDir/dokka-javadoc")
    dependsOn(dokkaJavaDoc)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            artifact(kdocZip)
//            artifact(javadocJar)
            artifact(sourcesJar)
        }
    }
}

val localProperties = Properties()
val localPropertiesFile: File = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

fun findProperty(propertyName: String): String? = localProperties.getProperty(propertyName) as String?


bintray {
    user = findProperty("bintray_username")
    key = findProperty("bintray_key")
    publish = true
    setPublications("mavenJava")

    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "edu"
        name = "toyGraphics"
        userOrg = "anysolo"
        websiteUrl = "https://github.com/anysolo/toyGraphics"
        vcsUrl = "https://github.com/anysolo/toyGraphics.git"
        githubRepo = "anysolo/toyGraphics"
        description = "Library allowing absolute beginners to work with graphics and write games"
        setLabels("kotlin")
        setLicenses("GPL-3.0")
        desc = description
    })
}


fun Project.artifactory(configure: ArtifactoryPluginConvention.() -> Unit): Unit =
        configure(project.convention.getPluginByName<ArtifactoryPluginConvention>("artifactory"))


artifactory {
    setContextUrl(findProperty("artifactory_contextUrl"))

    publish(delegateClosureOf<PublisherConfig> {
        repository(delegateClosureOf<GroovyObject> {
            setProperty("repoKey", findProperty("artifactory_repo_key"))
            setProperty("username", findProperty("artifactory_username"))
            setProperty("password", findProperty("artifactory_password"))
            setProperty("maven", true)
        })
        defaults(delegateClosureOf<GroovyObject> {
            invokeMethod("publications", "mavenJava")
            setProperty("publishArtifacts", true)
            setProperty("publishPom", true)
        })
    })
}
