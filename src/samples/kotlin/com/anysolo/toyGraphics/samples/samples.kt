package com.anysolo.toyGraphics.demo
import com.anysolo.toyGraphics.*


fun drawDots() {
    val wnd = Window(800, 600)
    val gc = Graphics(wnd)

    var x = 0
    var y = 0

    repeat(300) {
        gc.drawDot(x, y)

        x++
        y++
    }
}
