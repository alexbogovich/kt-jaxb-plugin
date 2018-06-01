package com.github.alexbogovich.jaxbplugin

import kotlinx.dom.parseXml
import kotlinx.dom.search
import org.w3c.dom.Element
import java.io.File

data class SchemaImport(val namespace: String, val schemaLocation: String = "")
data class SchemaInstance(val targetNamespace: String, val imports: List<SchemaImport>, val file: File)
data class SchemaDependancy(val schema: SchemaInstance, val dependsOn: MutableList<SchemaInstance> = mutableListOf())

fun getSchemaImport(element: Element): SchemaImport {
    return SchemaImport(element.getAttribute("namespace"), element.getAttribute("schemaLocation"))
}

fun getSchemaDep(file: File): SchemaInstance {
    return parseXml(file).let {
        val imports = it.search("import").map { e -> getSchemaImport(e) }
        SchemaInstance(it.search("schema").first().getAttribute("targetNamespace"), imports, file)
    }
}