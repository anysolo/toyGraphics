package com.anysolo.toyGraphics.vector


/** Point on the window. */
data class Point(val x: Double, val y: Double) {
    constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())
}

/** 2D vector. */
data class Vector(val x: Double, val y: Double) {
    constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())

    operator fun times(n: Double) = Vector(x * n, y * n)
    operator fun div(n: Double) = Vector(x / n, y / n)
}

operator fun Point.plus(delta: Vector) = Point(x + delta.x, y + delta.y)


/** Rectangular. */
data class Rect(val topLeft: Point, val size: Vector)
