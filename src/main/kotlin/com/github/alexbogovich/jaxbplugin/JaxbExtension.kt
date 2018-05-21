package com.github.alexbogovich.jaxbplugin

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.kotlin.dsl.property

open class JaxbExtension(project: Project) {
    val xsd = project.objects.property<FileTree>()
    val bindings = project.objects.property<FileTree>()
    val episodesDir = project.objects.property<String>()
}