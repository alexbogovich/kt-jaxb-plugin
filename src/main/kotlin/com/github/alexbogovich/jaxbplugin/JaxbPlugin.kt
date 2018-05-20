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
//                    xsdDir.set(jaxbExtension.xsdDir)
//                    bindings.set(jaxbExtension.bindings)
//                    bindingsDir.set(jaxbExtension.bindingsDir)
                    episodesDir.set(jaxbExtension.episodesDir)
//                    xsdIncludes.set(jaxbExtension.xsdIncludes)
                    xsd.set(jaxbExtension.xsd)
                }
            }
        }
    }
}