package com.anysolo.toyGraphics


/** Point on the window. */
data class Point(val x: Int, val y: Int)

/** Width and height in one object */
data class Size(val width: Int, val height: Int)


/** Rectangular. You can define it either with top left corner and size or top left and right bottom cornet.  */
data class Rect(val point: Point, val size: Size) {
    constructor(x: Int, y: Int, width: Int, height: Int) : this(Point(x, y), Size(width, height))
}

/** Just do nothing for n milliceconds. */
fun sleep(ms: Int) = Thread.sleep(ms.toLong())


fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)
