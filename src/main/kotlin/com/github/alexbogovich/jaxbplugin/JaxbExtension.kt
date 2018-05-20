package com.github.alexbogovich.jaxbplugin

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.kotlin.dsl.property

open class JaxbExtension(project: Project) {
    // TODO create proper type
    val xsd = project.objects.property<FileTree>()
//    val xsdDir = project.objects.property<String>()
//    val xsdIncludes = project.objects.property<String>()
    val episodesDir = project.objects.property<String>()
//    val bindingsDir = project.objects.property<String>()
//    val bindings = project.objects.property<String>()
}