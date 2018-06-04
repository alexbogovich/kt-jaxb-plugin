package io.github.alexbogovich.jaxbplugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property
import java.io.File

open class DependencyTreeTask : DefaultTask() {
    @get:InputFiles
    val xsd = project.objects.property<FileTree>()

    @Internal
    val treeRootXsd = project.objects.property<FileCollection>()

    @TaskAction
    fun dependencyTreeTask() {
        logger.info("Dependency tree start processing")
        logger.info("Initial file tree is $xsd")
        val map = xsd.orNull?.files!!
                .filter { !it.isDirectory }
                .map { file: File -> getSchemaDep(file) }
                .map { instance: SchemaInstance ->  instance.targetNamespace to SchemaDependency(instance) }
                .toMap()

        logger.info("Schema dependency instant's for process are $map")

        val files = map.values
                .map { dep ->
                    dep.schema.imports.forEach { import ->
                        map[import.namespace]?.dependsOn?.add(dep.schema)
                    }
                    dep
                }
                .filter { dep -> dep.dependsOn.isEmpty() }
                .map { dep -> dep.schema.file }

        logger.info("Lowest nodes is $files")
        treeRootXsd.set(project.files(files))
    }
}