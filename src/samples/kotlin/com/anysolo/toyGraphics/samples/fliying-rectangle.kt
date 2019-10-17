package com.anysolo.toyGraphics.samples


import com.anysolo.toyGraphics.Graphics
import com.anysolo.toyGraphics.Pal16
import com.anysolo.toyGraphics.Window

// Import sleep function
import com.anysolo.toyGraphics.sleep


fun main() {
    val wnd = Window(1920, 1080, buffered = true)

    val y = wnd.height/2
    var x = 0

    while(x < wnd.width) {
        val gc = Graphics(wnd)

        gc.setStrokeWidth(3)
        gc.color = Pal16.blue

        gc.clear()
        gc.drawRect(x, y, 50, 10)

        x = x + 1

        gc.close()

        sleep(5)
    }
}
