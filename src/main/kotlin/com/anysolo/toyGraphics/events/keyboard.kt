package com.anysolo.toyGraphics.events

import java.awt.event.KeyEvent
import java.awt.event.KeyListener


/**
 * Keyboard event.
 *
 * Contains information about what key was pressed or released.
 * If isPressed == true then the key was pressed otherwise released.
 */
data class KeyboardEvent(
    val code: Int,
    val char: Char,
    val isPressed: Boolean,
    val pressedKeyCodes: Set<Int>
): Event


internal class KeyboardEventAdapter(private val eventManager: EventManager): KeyListener {
    val pressedKeyCodes = mutableSetOf<Int> ()

    override fun keyTyped(e: KeyEvent?) {}

    override fun keyPressed(e: KeyEvent) {
        pressedKeyCodes.add(e.keyCode)
        eventManager.putEvent(KeyboardEvent(e.keyCode, e.keyChar, true, pressedKeyCodes.toSet()))
    }

    override fun keyReleased(e: KeyEvent) {
        eventManager.putEvent(KeyboardEvent(e.keyCode, e.keyChar, false, pressedKeyCodes.toSet()))
        pressedKeyCodes.remove(e.keyCode)
    }
}
