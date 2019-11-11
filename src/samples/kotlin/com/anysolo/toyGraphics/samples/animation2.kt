package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 100, buffered = true, background = Pal16.black)

    val spriteSheet = AnimationFrameSheet.loadFromImageSheet("graphicsFiles/jumpman.png", 4, 3)
    val animationManager = AnimationManager()
    val spriteRunningLeft = Animation(null, spriteSheet, frameNumbers = listOf(0, 1), delay = 100, loop = true)
    val spriteRunningRight = Animation(null, spriteSheet, frameNumbers = listOf(4, 5), delay = 100, loop = true)
    val spriteJumping = Animation(null, spriteSheet, frameNumbers = (8..11).toList(), delay = 100, loop = true)
    val spriteStanding = Animation(null, spriteSheet, frameNumbers = listOf(8), delay = 100, loop = true)

    var x = wnd.width/2
    val y = wnd.height - spriteSheet.height-2
    var currentSprite: Animation = spriteStanding

    val keyboard = Keyboard(wnd)

    while(true) {
        val key = keyboard.getPressedKey()

        if(key != null) {
            var newAnimation: Animation? = null

            when(key.code) {
                KeyCodes.LEFT -> newAnimation = spriteRunningLeft
                KeyCodes.RIGHT -> newAnimation = spriteRunningRight
                KeyCodes.SPACE -> newAnimation = spriteJumping
                KeyCodes.ENTER -> newAnimation = spriteStanding
            }

            if(newAnimation != null) {
                currentSprite.stop()
                currentSprite.start(animationManager)
                currentSprite = newAnimation
            }
        }

        when(currentSprite) {
            spriteRunningLeft -> if(x > 1) x -= 2
            spriteRunningRight -> if(x < wnd.width-1) x += 2
        }

        Graphics(wnd).use { gc ->
            gc.clear()

            gc.drawAnimation(x, y, currentSprite)
        }

        sleep(20)
        animationManager.update()
    }
}
