package com.github.alexbogovich.jaxbplugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property

open class JaxbTask: DefaultTask() {
    @get:Input
    @Optional
    val episodesDir = project.objects.property<String>()

    @get:InputFiles
    @Optional
    val bindings = project.objects.property<FileTree>()

    @get:InputFiles
    val xsd = project.objects.property<FileCollection>()

    @TaskAction
    fun runJaxbTransormation() {
        logger.info("Start jaxb")
        xsd.orNull?.files!!.forEach {
            logger.info("process ${it.absoluteFile}")
        }

        bindings.orNull?.run {
            logger.info("bindings = $this")
            visit {
                if (!isDirectory) {
                    logger.info("process $relativePath $file")
                }
            }
        }
    }
}