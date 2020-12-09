package com.anysolo.toyGraphics.tests

import com.anysolo.toyGraphics.vector.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue


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
    fun `V1 div n`() {
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


class AreaTests() {
    @Test
    fun `one area inside another`() {
        assertTrue(
            Area(Point(100.0, 100.0), Vector(100,100))
                overlaps
            Area(Point(150.0, 150.0), Vector(10.0, 10.0))
        )
    }

    @Test
    fun `both x and y not overlap`() {
        val size = Vector(10.0, 10.0)

        assertFalse(
            Area(Point(100.0, 100.0), size)
                overlaps
            Area(Point(200.0, 200.0), size)
        )

        assertFalse(
            Area(Point(200.0, 200.0), size)
                overlaps
            Area(Point(100.0, 100.0), size)
        )
    }

    @Test
    fun `x or y does not overlap`() {
        val size = Vector(10.0, 10.0)

        assertFalse(
            Area(Point(100.0, 100.0), size)
                overlaps
            Area(Point(100.0, 200.0), size)
        )

        assertFalse(
            Area(Point(100.0, 100.0), size)
                overlaps
            Area(Point(200.0, 100.0), size)
        )

    }

    @Test
    fun `x or y touched but does not overlap`() {
        val size = Vector(10.0, 10.0)

        assertFalse(
            Area(Point(100.0, 100.0), size)
                overlaps
            Area(Point(100.0, 110.0), size)
        )

        assertFalse(
            Area(Point(100.0, 100.0), size)
                overlaps
            Area(Point(110.0, 100.0), size)
        )
    }

    @Test
    fun `x or y barely overlap`() {
        val size = Vector(10.0, 10.0)

        assertTrue(
            Area(Point(100.0, 100.0), size)
                overlaps
            Area(Point(100.0, 109.9), size)
        )

        assertTrue(
            Area(Point(100.0, 100.0), size)
                overlaps
            Area(Point(109.9, 100.0), size)
        )
    }
}
