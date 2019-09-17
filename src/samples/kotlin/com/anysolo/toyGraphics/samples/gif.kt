package com.anysolo.toyGraphics.demo

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(2000, 1200, buffered = true)

    val image = Image("graphicsFiles/zombie.gif")

    var x = wnd.width - 100
    val y = wnd.height - image.height

    while(x > 0) {
        Graphics(wnd).use { gc ->
            gc.clear()
            gc.drawImage(x, y, image)
        }

        sleep(50)
        x -= 2
    }
}
