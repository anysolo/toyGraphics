package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 200, buffered = true)

    val animationFrames = SpriteSheet.loadFromAnimatedGif("graphicsFiles/zombie.gif")
    val animation = Sprite(animationFrames, delay = 100, autoStart = true, loop = true)

    var x = wnd.width - 100
    val y = wnd.height - animation.height

    while(x > 0) {
        Graphics(wnd).use { gc ->
            gc.clear()
            gc.drawAnimation(x, y, animation)
        }

        x --

        sleep(10)
        AnimationManager.update()
    }
}
