package com.anysolo.toyGraphics.vector

import com.anysolo.toyGraphics.Pos
import com.anysolo.toyGraphics.Size
import com.anysolo.toyGraphics.dataEngine.Input
import com.anysolo.toyGraphics.dataEngine.Output
import java.io.Serializable
import kotlin.math.roundToInt


/** Point on the window. */
data class Point(val x: Double, val y: Double): Serializable {
    constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())
    constructor(pos: Pos): this(pos.x.toDouble(), pos.y.toDouble())

    fun roundToPos() = Pos(x.roundToInt(), y.roundToInt())
}

fun Input.readPoint() = Point(readDouble(), readDouble())
fun Output.writePoint(p: Point) {
    writeDouble(p.x)
    writeDouble(p.y)
}


/** 2D vector. */
data class Vector(val x: Double, val y: Double): Serializable {
    constructor(x: Int, y: Int): this(x.toDouble(), y.toDouble())
    constructor(size: Size): this(size.width.toDouble(), size.height.toDouble())

    operator fun plus(v2: Vector) = Vector(x + v2.x, y + v2.y)
    operator fun minus(v2: Vector) = Vector(x - v2.x, y - v2.y)

    operator fun times(n: Double) = Vector(x * n, y * n)
    operator fun times(n: Long) = Vector(x * n, y * n)
    operator fun times(n: Int) = Vector(x * n, y * n)

    operator fun div(n: Double) = Vector(x / n, y / n)
    operator fun div(n: Long) = Vector(x / n, y / n)
    operator fun div(n: Int) = Vector(x / n, y / n)

    operator fun unaryMinus() = Vector(-x, -y)

    fun roundToSize() = Size(x.roundToInt(), y.roundToInt())
}

fun Vector.horizontal() = Vector(x, 0.0)
fun Vector.vertical() = Vector(0.0, y)

fun Pos.toPoint() = Point(x.toDouble(), y.toDouble())
fun Size.toVector() = Vector(width.toDouble(), height.toDouble())

fun Input.readVector() = Vector(readDouble(), readDouble())

fun Output.writeVector(v: Vector) {
    writeDouble(v.x)
    writeDouble(v.y)
}


operator fun Point.plus(delta: Vector) = Point(x + delta.x, y + delta.y)
operator fun Point.minus(delta: Vector) = Point(x - delta.x, y - delta.y)


/** Rectangular. */
data class Area(val topLeft: Point, val size: Vector): Serializable

fun Input.readArea() = Area(readPoint(), readVector())

fun Output.writeArea(a: Area) {
    writePoint(a.topLeft)
    writeVector(a.size)
}
