package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 200, buffered = true)

    val animationManager = AnimationManager()
    val animationFrames = AnimationFrameSheet.loadFromAnimatedGif("graphicsFiles/zombie.gif")
    val animation = Animation(animationManager, animationFrames, delay = 100, loop = true)

    var x = wnd.width - 100
    val y = wnd.height - animation.height

    while(x > 0) {
        Graphics(wnd).use { gc ->
            gc.clear()
            gc.drawAnimation(x, y, animation)
        }

        x --

        sleep(10)
        animationManager.update()
    }
}
