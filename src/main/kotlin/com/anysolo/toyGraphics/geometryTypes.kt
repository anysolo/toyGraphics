package com.anysolo.toyGraphics


import java.io.Serializable


/** Pos on the window. */
data class Pos(val x: Int, val y: Int): Serializable {
    operator fun plus(delta: Size) = Pos(x + delta.width, y + delta.height)
    operator fun minus(delta: Size) = Pos(x - delta.width, y - delta.height)
}


/** 2D Size. */
data class Size(val width: Int, val height: Int): Serializable {
    fun horizontal() = Size(0, height)
    fun vertical() = Size(0, height)
}


/** Rectangular. */
data class Rect(val topLeft: Pos, val size: Size): Serializable


fun Graphics.drawDot(p: Pos) = drawDot(p.x, p.x)

fun Graphics.drawLine(p1: Pos, p2: Pos) =
    drawLine(p1.x, p1.y, p2.x, p2.y)

fun Graphics.drawRect(r: Rect, fill: Boolean = false) =
    drawRect(
        r.topLeft.x, r.topLeft.y,
        r.size.width, r.size.height,
        fill
    )

fun Graphics.drawRect(topLeft: Pos, size: Size, fill: Boolean = false) =
    drawRect(Rect(topLeft, size), fill)

fun Graphics.drawImage(p: Pos, image: Image) =
    drawImage(p.x, p.y, image)

fun Graphics.drawImage(p: Pos, image: Image, angle: Double, anchorx: Int = 0, anchory: Int = 0) =
    drawImage(p.x, p.y, image, angle, anchorx, anchory)

fun Graphics.drawOval(r: Rect, fill: Boolean = false) =
    drawOval(
        r.topLeft.x, r.topLeft.y,
        r.size.width, r.size.height,
        fill
    )

fun Graphics.drawOval(topLeft: Pos, size: Size, fill: Boolean = false) =
    drawOval(Rect(topLeft, size), fill)

fun Graphics.drawText(p: Pos, text: String) =
    drawText(p.x, p.x, text)
