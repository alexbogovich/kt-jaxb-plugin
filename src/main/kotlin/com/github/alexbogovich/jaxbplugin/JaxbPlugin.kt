package com.github.alexbogovich.jaxbplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke

open class JaxbPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            tasks {
                group = "jaxb-plugin"
                "process"()
            }
        }
    }
}