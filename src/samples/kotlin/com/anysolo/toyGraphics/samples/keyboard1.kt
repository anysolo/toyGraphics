package com.anysolo.toyGraphics.samples

import com.anysolo.toyGraphics.*


fun main() {
    val wnd = Window(800, 600)
    val keyboard = Keyboard(wnd)

    while(true) {
        do {
            val key = keyboard.getPressedKey()

            if (key != null) {
                println(key)
                println("key: " + key.code)
            }

        } while(key != null)

        println("Waiting...")
        sleep(3000)
    }
}
