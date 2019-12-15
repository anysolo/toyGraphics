package com.anysolo.toyGraphics.vector

import com.anysolo.toyGraphics.Graphics
import com.anysolo.toyGraphics.Image
import kotlin.math.roundToInt


fun Graphics.drawDot(p: Point) = drawDot(p.x.roundToInt(), p.x.roundToInt())

fun Graphics.drawLine(p1: Point, p2: Point) =
    drawLine(
        p1.x.roundToInt(), p1.y.roundToInt(),
        p2.x.roundToInt(), p2.y.roundToInt()
    )

fun Graphics.drawRect(r: Rect, fill: Boolean = false) =
    drawRect(
        r.topLeft.x.roundToInt(),
        r.topLeft.y.roundToInt(),
        r.size.x.roundToInt(),
        r.size.y.roundToInt(),
        fill
    )

fun Graphics.drawRect(topLeft: Point, size: Vector, fill: Boolean = false) =
    drawRect(Rect(topLeft, size), fill)

fun Graphics.drawImage(p: Point, image: Image) =
    drawImage(p.x.roundToInt(), p.y.roundToInt(), image)

fun Graphics.drawImage(p: Point, image: Image, angle: Double, anchorx: Int = 0, anchory: Int = 0) =
    drawImage(p.x.roundToInt(), p.y.roundToInt(), image, angle, anchorx, anchory)

fun Graphics.drawOval(r: Rect, fill: Boolean = false) =
    drawOval(
        r.topLeft.x.roundToInt(),
        r.topLeft.y.roundToInt(),
        r.size.x.roundToInt(),
        r.size.y.roundToInt(),
        fill
    )

fun Graphics.drawOval(topLeft: Point, size: Vector, fill: Boolean = false) =
    drawOval(Rect(topLeft, size), fill)

fun Graphics.drawText(p: Point, text: String) =
    drawText(p.x.roundToInt(), p.x.roundToInt(), text)
