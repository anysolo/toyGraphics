package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(300, 300, background = Pal16.black, buffered = true)
    val image = Image("graphicsFiles/ufo-small.png")

    val x = wnd.width/2 - image.width/2
    val y = wnd.height/2 - image.height/2

    val maxAngle = Math.PI*2
    var angle = 0.0

    while(true) {
        Graphics(wnd).use {g ->
            g.clear()

            val anchorx = x + image.width/2
            val anchory = y + image.height/2

            g.drawImage(x, y, image, angle, anchorx, anchory)

            angle += maxAngle/360
            if(angle > maxAngle)
                angle = 0.0

            sleep(20)
        }
    }
}
