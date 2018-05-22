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

                val treeTask = "dependencyTree"(DependencyTreeTask::class) {
                    xsd.set(jaxbExtension.xsd)
                }

                "jaxb"(JaxbTask::class) {
                    dependsOn(treeTask)

                    episodesDir.set(jaxbExtension.episodesDir)
                    bindings.set(jaxbExtension.bindings)
                    xsd.set(treeTask.treeRootXsd)
                }
            }
        }
    }
}