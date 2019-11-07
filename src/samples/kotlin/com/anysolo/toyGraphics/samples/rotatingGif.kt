package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 600, buffered = true)
    val image = Image("graphicsFiles/ufo-small.png")

    val x = wnd.width/2 - image.width/2
    val y = wnd.height/2 - image.height/2

    var angle = 0.0

    while(true) {
        Graphics(wnd).use {g ->
            g.clear()

            val anchorx = x + image.width/2
            val anchory = y + image.height/2

            g.drawImage(x, y, image, Math.toRadians(angle), anchorx, anchory)

            angle += 1.0
            sleep(20)
        }
    }
}
