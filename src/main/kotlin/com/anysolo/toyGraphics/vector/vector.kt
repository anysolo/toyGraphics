package com.anysolo.toyGraphics.vector

import java.io.Serializable



/** Point on the window. */
data class Point(val x: Double, val y: Double): Serializable {
    constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())
}

/** 2D vector. */
data class Vector(val x: Double, val y: Double): Serializable {
    constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())

    operator fun plus(v2: Vector) = Vector(x + v2.x, y + v2.y)
    operator fun minus(v2: Vector) = Vector(x - v2.x, y - v2.y)

    operator fun times(n: Double) = Vector(x * n, y * n)
    operator fun times(n: Long) = Vector(x * n, y * n)
    operator fun times(n: Int) = Vector(x * n, y * n)

    operator fun div(n: Double) = Vector(x / n, y / n)
    operator fun div(n: Long) = Vector(x / n, y / n)
    operator fun div(n: Int) = Vector(x / n, y / n)

    operator fun unaryMinus() = Vector(-x, -y)
}

fun Vector.horizontal() = Vector(x, 0.0)
fun Vector.vertical() = Vector(0.0, y)


operator fun Point.plus(delta: Vector) = Point(x + delta.x, y + delta.y)
operator fun Point.minus(delta: Vector) = Point(x - delta.x, y - delta.y)


/** Rectangular. */
data class Rect(val topLeft: Point, val size: Vector): Serializable
