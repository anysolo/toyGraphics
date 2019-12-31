package com.anysolo.toyGraphics.tests

import com.anysolo.toyGraphics.Color
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class ColorTests {
    @Test
    fun testToString() {
        assertEquals("Color(10, 20, 30)", Color(10, 20, 30).toString())
    }

    @Test
    fun comparsion() {
        val c1 = Color(10, 20, 30)
        val c12 = Color(10, 20, 30)
        val c2 = Color(15, 20, 30)

        assertTrue(c1.equals(c12))
        assertTrue(!c1.equals(c2))
    }
}
