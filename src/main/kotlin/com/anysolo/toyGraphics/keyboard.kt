package com.anysolo.toyGraphics

import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*


private val modifierCodes = listOf(KeyEvent.VK_SHIFT, KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_META)


/**
 * What modifiers keys were pressed in the moment of when a normal key was pressed.
 */
data class KeyModifiers(
    val shift: Boolean = false, val alt: Boolean = false,
    val ctrl: Boolean = false, val meta: Boolean = false
)


/** Contains information about one pressed key */
class Key(
    /**
     * Integer code of the key.
     *
     * Each key on the keyboard can be identified by a unique Int code.
     * Key code does not change when you press modifiers keys like shift, control, alt and so on.
     */
    val code: Int,

    val char: Char,

    /**
     * Modifiers pressed at the same moment the key was pressed
     */
    val modifiers: KeyModifiers = KeyModifiers()
) {
    override fun toString() = "code: $code $modifiers"

    val isShift
        get() = modifiers.shift

    val isAlt
        get() = modifiers.alt

    val isCtrl
        get() = modifiers.ctrl

    val isMeta
        get() = modifiers.meta
}


object KeyCodes {
    val LEFT = KeyEvent.VK_LEFT
    val RIGHT = KeyEvent.VK_RIGHT
    val UP = KeyEvent.VK_UP
    val DOWN = KeyEvent.VK_DOWN

    val ENTER = KeyEvent.VK_ENTER
    val HOME = KeyEvent.VK_HOME
    val END = KeyEvent.VK_END
    val INSERT = KeyEvent.VK_INSERT
    val DELETE = KeyEvent.VK_DELETE

    val PAGE_UP = KeyEvent.VK_PAGE_UP
    val PAGE_DOWN = KeyEvent.VK_PAGE_DOWN

    val F1 = KeyEvent.VK_F1
    val F2 = KeyEvent.VK_F2
    val F3 = KeyEvent.VK_F3
    val F4 = KeyEvent.VK_F4
    val F5 = KeyEvent.VK_F5
    val F6 = KeyEvent.VK_F6
    val F7 = KeyEvent.VK_F7
    val F8 = KeyEvent.VK_F8
    val F9 = KeyEvent.VK_F9
    val F10 = KeyEvent.VK_F10
    val F11 = KeyEvent.VK_F11
    val F12 = KeyEvent.VK_F12

    val ESCAPE = KeyEvent.VK_ESCAPE
    val SPACE = KeyEvent.VK_SPACE
    val BACKSPACE = KeyEvent.VK_BACK_SPACE
    val TAB = KeyEvent.VK_TAB

    val ALT = KeyEvent.VK_ALT
    val CTRL = KeyEvent.VK_CONTROL
    val SHIFT = KeyEvent.VK_SHIFT
    val META = KeyEvent.VK_META

    val arrows = setOf(KeyCodes.LEFT, KeyCodes.RIGHT, KeyCodes.UP, KeyCodes.DOWN)
}


/** Use this class to work with keyboard
 *
 * keyboard collects Key objects. You receive these object
 * calling getPressedKey() method.
 * You cannot detect when a key was released.
 * Ctrl, Alt, Shift, Meta keys will be treated as modifiers for normal keys.
 * */
class Keyboard(val window: Window) {
    internal inner class KeyAdapter: KeyListener {
        override fun keyTyped(e: KeyEvent?) {}

        override fun keyPressed(e: KeyEvent) {
            val keyCode = e.keyCode

            if(!isModifierCode(keyCode))
                _keys.add(makeKey(e))
        }

        override fun keyReleased(e: KeyEvent) {}

        private fun isModifierCode(code: Int) = code in modifierCodes

        private fun makeKey(e: KeyEvent) = Key(
            e.keyCode, e.keyChar, KeyModifiers(e.isShiftDown, e.isAltDown, e.isControlDown, e.isMetaDown)
        )
    }

    private var _keys = ArrayList<Key>()

    init {
        window.pane.addKeyListener(KeyAdapter())
    }

    /** Returns the pressed key. Pressed keys are collected in the queue.
     *  This function returns a pressed key from keyboard queue.
     *  If the queue is empty it returns null
     *  You must create the keyboard with evenMode=false to use this function.
     *
     * Key object contains the code of the key and all key modifiers (shift, ctrl, alt).
     * */
    fun getPressedKey(): Key? =
        if(! _keys.isEmpty()) {
            val key = _keys.first()
            _keys.remove(key)
            key
        }
        else
            null
}
