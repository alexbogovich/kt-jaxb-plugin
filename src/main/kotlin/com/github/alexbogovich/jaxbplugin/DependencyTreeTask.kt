package com.github.alexbogovich.jaxbplugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.property
import java.io.File

open class DependencyTreeTask: DefaultTask() {
    @get:InputFiles
    val xsd = project.objects.property<FileTree>()

    @Internal
    val treeRootXsd = project.objects.property<FileCollection>()

    @TaskAction
    fun getRoots() {
        logger.info("Dependency tree start processing")
        logger.info("Tree dir is $xsd")
        val list = xsd.orNull?.files!!
                .filter { !it.isDirectory }
                .flatMap {
                    logger.info("Start process $it")
                    listOf(it)
                }
        logger.info("Result in $list")

        val fileCollection = project.files(list)

        treeRootXsd.set(fileCollection)
    }

    private fun getXsdSchemaData(file: File) {

    }
}