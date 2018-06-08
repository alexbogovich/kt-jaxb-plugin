package io.github.alexbogovich.jaxbplugin

import com.nhaarman.mockito_kotlin.mock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node

@ExtendWith(MockitoExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UtilsKtTest {

    private val node: Node = mock()
    private val nodeNS: Node = mock()
    private val nodeSL: Node = mock()
    private val attr: NamedNodeMap = mock()

    @BeforeEach
    fun setUp() {
        `when`(node.attributes).thenReturn(attr)
    }

    @Test
    fun `SchemaImport should get correct parameters`() {
        `when`(attr.getNamedItem("namespace")).thenReturn(nodeNS)
        `when`(attr.getNamedItem("schemaLocation")).thenReturn(nodeSL)
        `when`(nodeNS.nodeValue).thenReturn("abc")
        `when`(nodeSL.nodeValue).thenReturn("schema")

        getSchemaImport(node).run {
            assertEquals("abc", namespace)
            assertEquals("schema", schemaLocation)
        }
    }

    @Test
    fun `SchemaImport should get only namespace`() {
        `when`(attr.getNamedItem("namespace")).thenReturn(nodeNS)
        `when`(attr.getNamedItem("schemaLocation")).thenReturn(null)
        `when`(nodeNS.nodeValue).thenReturn("abc")

        getSchemaImport(node).run {
            assertEquals("abc", namespace)
            assertEquals("", schemaLocation)
        }
    }
}