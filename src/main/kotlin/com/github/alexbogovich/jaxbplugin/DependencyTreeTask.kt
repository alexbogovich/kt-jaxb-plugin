package com.github.alexbogovich.jaxbplugin

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
    fun getRoots() {
        logger.info("Dependency tree start processing")
        logger.info("Tree dir is $xsd")
        val map = xsd.orNull?.files!!
                .filter { !it.isDirectory }
//                .flatMap {
//                    logger.info("Start process $it")
//                    listOf(it)
//                }
                .let { logger.info(it.toString()); it }
                .map { file: File ->  getSchemaDep(file) }
                .let { logger.info(it.toString()); it }
                .map { instance: SchemaInstance ->  instance.targetNamespace to instance }
                .let { logger.info(it.toString()); it }
                .toMap()

        logger.info("map in $map")

        val files = map.values
                .asSequence()
                .map { SchemaDependancy(it) }
                .map { dep ->
                    dep.schema.imports.forEach { import ->
                        map[import.namespace]?.let { instance ->
                            dep.dependsOn.add(instance)
                        }
                    }
                    dep
                }
                .filter { dep -> dep.dependsOn.isEmpty() }
                .map { dep -> dep.schema }
                .map { schema -> schema.file }
                .toList()



        logger.info("Result in $files")

        val fileCollection = project.files(files)

        treeRootXsd.set(fileCollection)
    }
}