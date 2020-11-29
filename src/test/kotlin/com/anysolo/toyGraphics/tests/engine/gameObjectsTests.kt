package com.anysolo.toyGraphics.tests.engine



import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.Test

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File


@Serializable
data class C1(val n1: Int)


class GameObjectsTests {
    @Test
    fun t1() {
        val dataFilename = "test.dat"
        val c1 = C1(123)
        val s = Json.encodeToString(c1)
        println("s")
        println(s)

        File(dataFilename).writeText(s)

        val s1 = File(dataFilename).readText()
        val c11 = Json.decodeFromString<C1>(s1)
        println(c11)

        Assertions.assertEquals(c1, c11)
    }
}
