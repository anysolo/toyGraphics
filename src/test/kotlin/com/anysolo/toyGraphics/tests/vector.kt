package com.anysolo.toyGraphics.tests

import com.anysolo.toyGraphics.vector.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals


class VectorTests {
    @Test
    fun `V1 + V2`() {
        assertEquals(
            Vector(220, 110),
            Vector(200, 100) + Vector(20, 10)
        )
    }

    @Test
    fun `V1 - V2`() {
        assertEquals(
            Vector(180, 90),
            Vector(200, 100) - Vector(20, 10)
        )
    }

    @Test
    fun `V1 * n`() {
        assertEquals(
            Vector(600, 300),
            Vector(200, 100) * 3
        )
    }

    @Test
    fun `V1 div  n`() {
        assertEquals(
            Vector(50, 25),
            Vector(200, 100) / 4
        )
    }

    @Test
    fun `-V`() {
        assertEquals(
            Vector(-20, -10),
            -Vector(20, 10)
        )
    }
}
