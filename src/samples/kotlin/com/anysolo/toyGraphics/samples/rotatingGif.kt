package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 600, buffered = true)
    val image = Image("graphicsFiles/ufo-small.png")

    var angle = 0.0
    while(true) {
        Graphics(wnd).use {g ->
            g.clear()
            g.rotate(Math.toRadians(angle))
            g.drawImage(wnd.width/2, wnd.height/2, image)
            angle += 1.0
            sleep(20)
        }
    }
}
