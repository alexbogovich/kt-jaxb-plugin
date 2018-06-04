package io.github.alexbogovich.jaxbplugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileCollection
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.withGroovyBuilder
import java.io.File

open class JaxbTask : DefaultTask() {
    @get:Input
    @Optional
    val episodesDir = project.objects.property<String>()

    @get:InputFiles
    @Optional
    val bindings = project.objects.property<FileTree>()

    @get:InputFiles
    val xsd = project.objects.property<FileCollection>()

    @get:InputFile
    val catalog = project.objects.property<File>()

    @get:OutputDirectory
    val destdir = project.objects.property<File>()

    @get:Input
    val args = project.objects.property<List<String>>()

    val config = project.files()

    @TaskAction
    fun jaxbTask() {
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


        System.setProperty("javax.xml.accessExternalSchema", "file")

        ant.withGroovyBuilder {
            "taskdef"("name" to "xjc",
                    "classname" to "org.jvnet.jaxb2_commons.xjc.XJC2Task",
                    "classpath" to config.asPath)

            "xjc"("destdir" to destdir.get().absolutePath,
                    "extension" to true,
                    "removeOldOutput" to "yes",
                    "header" to true,
                    "catalog" to catalog.get().absolutePath) {
                xsd.get().addToAntBuilder(ant, "schema", FileCollection.AntType.FileSet)
                bindings.get().addToAntBuilder(ant, "binding", FileCollection.AntType.FileSet)

                args.get().forEach {
                    "arg"("value" to it)
                }
            }
        }
    }
}