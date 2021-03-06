plugins {
    `maven-publish`
    `java-gradle-plugin`
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.9.10"
}


group   = "io.github.alexbogovich"
version = "0.3.2"
val ktJaxbPlugin = "kt-jaxb-plugin"
val ktJaxbPluginId = "${project.group}.$ktJaxbPlugin"

repositories {
    jcenter()
}

gradlePlugin {
    (plugins) {
        ktJaxbPlugin {
            id = ktJaxbPluginId
            implementationClass = "${project.group}.jaxbplugin.JaxbPlugin"
            version = "${project.version}"
        }
    }
}

pluginBundle {
    website = "https://github.com/alexbogovich/kt-jaxb-plugin"
    vcsUrl  = "https://github.com/alexbogovich/kt-jaxb-plugin.git"
    (plugins) {
        ktJaxbPlugin {
            id = ktJaxbPluginId
            description = ktJaxbPlugin
            displayName = "Jaxb plugin on kotlin"
            version = "${project.version}"
            tags = listOf("kotlin", "jaxb")
        }
    }
}

dependencies {
    compile("org.jetbrains.kotlinx:kotlinx.dom:0.0.10")
    testCompile("org.mockito:mockito-junit-jupiter:2.18.3")
    testCompile("com.nhaarman:mockito-kotlin:1.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform()
}