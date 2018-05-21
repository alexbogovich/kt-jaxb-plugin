package com.github.alexbogovich.jaxbplugin

import org.gradle.api.DefaultTask
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
    val xsd = project.objects.property<FileTree>()

    @TaskAction
    fun runJaxbTransormation() {
        println("xsd = ${xsd.get()}")
        xsd.get().visit {
            if (!isDirectory) {
                println("process $relativePath $file")
            }
        }

        bindings.orNull?.run {
            println("bindings = $this")
            visit {
                if (!isDirectory) {
                    println("process $relativePath $file")
                }
            }
        }
    }
}