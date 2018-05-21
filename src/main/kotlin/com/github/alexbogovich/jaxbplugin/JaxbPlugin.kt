package com.github.alexbogovich.jaxbplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

open class JaxbPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            val jaxbExtension = extensions.create("jaxbConfig", JaxbExtension::class.java, project)

            tasks {
                group = "jaxb-plugin"

                "jaxb"(JaxbTask::class) {
                    episodesDir.set(jaxbExtension.episodesDir)
                    bindings.set(jaxbExtension.bindings)
                    xsd.set(jaxbExtension.xsd)
                }
            }
        }
    }
}