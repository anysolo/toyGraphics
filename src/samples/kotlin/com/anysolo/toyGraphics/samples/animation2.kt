package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 100, buffered = true, background = Pal16.black)

    val spriteSheet = SpriteSheet.loadFromImageSheet("graphicsFiles/jampman.png", 4, 3)
    val spriteRunningLeft = Sprite(spriteSheet, frames = listOf(0, 1), delay = 100, autoStart = true, loop = true)
    val spriteRunningRight = Sprite(spriteSheet, frames = listOf(4, 5), delay = 100, autoStart = true, loop = true)
    val spriteJumping = Sprite(spriteSheet, frames = (8..11).toList(), delay = 100, autoStart = true, loop = true)

    var x = wnd.width/2
    val y = wnd.height - spriteSheet.height-2
    var currentSprite: Sprite = spriteJumping

    val keyboard = Keyboard(wnd)

    while(true) {
        val key = keyboard.getPressedKey()
        if(key != null) {
            when(key.code) {
                KeyCodes.LEFT -> currentSprite = spriteRunningLeft
                KeyCodes.RIGHT -> currentSprite = spriteRunningRight
                KeyCodes.SPACE -> currentSprite = spriteJumping
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
        AnimationManager.update()
    }
}
