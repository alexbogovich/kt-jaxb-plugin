package io.github.alexbogovich.jaxbplugin

import org.gradle.api.Project
import org.gradle.api.file.FileTree
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.property
import java.io.File

open class JaxbExtension(project: Project) {
    val xsd = project.objects.property<FileTree>()
    val bindings = project.objects.property<FileTree>()
    val episodesDir = project.objects.property<String>()
    val catalog = project.objects.property<File>()
    val destdir = project.objects.property<File>()
    val args = project.objects.property<List<String>>()
}