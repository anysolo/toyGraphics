package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 600)
    val gc = Graphics(wnd)

    gc.drawText(100, 100, "Some text")
}
