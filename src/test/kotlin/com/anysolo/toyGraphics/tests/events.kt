package com.anysolo.toyGraphics.tests

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.events.*


fun main() {
    val wnd = Window(800, 600)
    val eventManager = EventManager(wnd)

    while(true) {
        while(true) {
            val event = eventManager.takeEvent() ?: break
            println("Event: ${event.getClassName()} $event")
        }

        sleep(20)
    }
}
