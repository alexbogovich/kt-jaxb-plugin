plugins {
    `maven-publish`
    `java-gradle-plugin`
    `kotlin-dsl`
}

group   = "com.github.alexbogovich"
version = "0.1"

repositories {
    jcenter()
}

gradlePlugin {
    (plugins) {
        "kt-jaxb-plugin" {
            id = "com.github.alexbogovich.kt-jaxb-plugin"
            implementationClass = "com.github.alexbogovich.jaxbplugin.JaxbPlugin"
        }
    }
}