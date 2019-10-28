package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(400, 200)

    val image = Image("graphicsFiles/bricks.jpg")

    val g = Graphics(wnd)
    g.drawImage(0, 0, image)
}
