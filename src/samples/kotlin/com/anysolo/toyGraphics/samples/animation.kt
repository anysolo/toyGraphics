package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 200, buffered = true)

    val imageSet = ImageSet.loadFromAnimatedGif("graphicsFiles/zombie.gif")
    val animation = Animation(imageSet, autoStart = true, loop = true)

    var x = wnd.width - 100
    val y = wnd.height - imageSet.height

    while(x > 0) {
        Graphics(wnd).use { gc ->
            gc.clear()
            gc.drawAnimation(x, y, animation)
        }

        x -= 2

        sleep(50)
        AnimationManager.update()
    }
}
