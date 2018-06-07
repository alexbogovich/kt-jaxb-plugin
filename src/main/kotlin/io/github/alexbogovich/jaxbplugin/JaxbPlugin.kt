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

            jaxbConfiguration.withDependencies {
                add(dependencies.create("org.glassfish.jaxb","jaxb-xjc","2.2.11"))
                add(dependencies.create("org.glassfish.jaxb","jaxb-runtime","2.2.11"))
                add(dependencies.create("org.jvnet.jaxb2_commons","jaxb2-basics-ant","1.11.1"))
                add(dependencies.create("org.jvnet.jaxb2_commons","jaxb2-basics","1.11.1"))
                add(dependencies.create("org.jvnet.jaxb2_commons","jaxb2-basics-annotate","1.0.4"))
                add(dependencies.create("org.slf4j","slf4j-simple","1.7.25"))
                add(dependencies.create("javax.xml.bind", "jaxb-api", "2.3.0"))
                add(dependencies.create("javax.activation", "activation", "1.1.1"))
            }


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