package com.github.alexbogovich.jaxbplugin

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.property

open class JaxbTask: DefaultTask() {

//    @get:Input
//    @Optional
//    val xsdDir = project.objects.property<String>()
//
//    @get:Input
//    @Optional
//    val xsdIncludes = project.objects.property<String>()

    @get:Input
    @Optional
    val episodesDir = project.objects.property<String>()

//    @get:Input
//    @Optional
//    val bindingsDir = project.objects.property<String>()
//
//    @get:Input
//    @Optional
//    val bindings = project.objects.property<String>()

    @get:InputFiles
    val xsd = project.objects.property<FileTree>()

    @TaskAction
    fun runJaxbTransormation() {
//        println("run task with xsdDir = ${xsdDir.getOrElse("def1")}" +
//                " xsdIncludes = ${xsdIncludes.getOrElse("def2")} " +
//                " episodesDir = ${episodesDir.getOrElse("def3")}" +
//                " bindingsDir = ${bindingsDir.getOrElse("def4")}" +
//                " bindings = ${bindings.getOrElse("def5")}")

        println("episodesDir = ${episodesDir.getOrElse("def3")}")
        println("xsd = ${xsd.get()}")

        xsd.get().visit {
            println("process $relativePath $file")
        }
    }
}