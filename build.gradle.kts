plugins {
    `maven-publish`
    `java-gradle-plugin`
    `kotlin-dsl`
}

group   = "io.github.alexbogovich"
version = "0.2"

repositories {
    jcenter()
}

gradlePlugin {
    (plugins) {
        "kt-jaxb-plugin" {
            id = "io.github.alexbogovich.kt-jaxb-plugin"
            implementationClass = "io.github.alexbogovich.jaxbplugin.JaxbPlugin"
        }
    }
}

dependencies {
    compile("org.jetbrains.kotlinx:kotlinx.dom:0.0.10")
}