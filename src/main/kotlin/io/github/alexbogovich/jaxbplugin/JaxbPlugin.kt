package io.github.alexbogovich.jaxbplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

open class JaxbPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.run {

            val jaxbConfiguration = configurations.maybeCreate("jaxb")
                    .setVisible(true)
                    .setTransitive(true)
                    .setDescription("jaxb")


            val jaxbExtension = extensions.create("jaxbConfig", JaxbExtension::class.java, project)

            tasks {
                group = "kt-jaxb-plugin"

                val treeTask = "dependencyTree"(DependencyTreeTask::class) {
                    xsd.set(jaxbExtension.xsd)
                }

                "jaxb"(JaxbTask::class) {
                    dependsOn(treeTask)

                    if (jaxbExtension.episodesDir.isPresent) {
                        episodesDir.set(jaxbExtension.episodesDir)
                    }
                    if (jaxbExtension.catalog.isPresent) {
                        catalog.set(jaxbExtension.catalog)
                    }

                    bindings.set(jaxbExtension.bindings)
                    xsd.set(treeTask.treeRootXsd)
                    config.setFrom(jaxbConfiguration)
                    destdir.set(jaxbExtension.destdir)
                    args.set(jaxbExtension.args)
                }
            }
        }
    }
}