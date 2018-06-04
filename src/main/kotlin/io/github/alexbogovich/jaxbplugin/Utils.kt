package io.github.alexbogovich.jaxbplugin

import kotlinx.dom.asList
import kotlinx.dom.parseXml
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

data class SchemaImport(val namespace: String, val schemaLocation: String = "")
data class SchemaInstance(val targetNamespace: String, val imports: List<SchemaImport>, val file: File)
data class SchemaDependency(val schema: SchemaInstance, val dependsOn: MutableList<SchemaInstance> = mutableListOf())


fun getSchemaImport(node: Node): SchemaImport {
    return SchemaImport(node.attributes.getNamedItem("namespace").nodeValue,
            node.attributes.getNamedItem("schemaLocation").nodeValue)
}


fun getSchemaDep(file: File): SchemaInstance {
    val newInstance = DocumentBuilderFactory.newInstance()!!
    newInstance.isNamespaceAware = true
    return parseXml(file, newInstance.newDocumentBuilder()).let {
        val imports = it.documentElement
                .getElementsByTagNameNS("*", "import")
                .asList()
                .map { node: Node -> getSchemaImport(node) }

        val targetNamespace = it.documentElement.attributes.getNamedItem("targetNamespace").nodeValue
        SchemaInstance(targetNamespace, imports, file)
    }
}