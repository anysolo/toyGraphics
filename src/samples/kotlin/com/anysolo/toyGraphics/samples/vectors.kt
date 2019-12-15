package com.anysolo.toyGraphics.demo

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.vector.*


fun main() {
    val wnd = Window(1280, 720)
    val g = Graphics(wnd)

    val objectSize = Vector(75, 25)
    var delta = Vector(20, 10)

    var p1 = Point(0, 0)

    repeat(20) {
        g.drawRect(p1, objectSize)

        p1 += delta
        delta *= 1.2
    }
}
