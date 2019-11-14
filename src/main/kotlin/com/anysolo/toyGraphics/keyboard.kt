package com.anysolo.toyGraphics

import java.awt.event.KeyEvent
import java.awt.event.KeyListener


private val modifierCodes = listOf(KeyEvent.VK_SHIFT, KeyEvent.VK_ALT, KeyEvent.VK_CONTROL, KeyEvent.VK_META)



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
}

/**
 * Keyboard event.
 *
 * Contains information about what key was pressed or released.
 * Treat modifier keys (Ctrl, Shift, Alt and so on) as normal keys.
 * If isPressed == true then the key was pressed otherwise released.
 */
data class KeyboardEvent(val code: Int, val isPressed: Boolean)


/** Use this class to work with keyboard
 *
 * You can work with keyboard in two modes. If [eventMode] == false
 * the keyboard will be collection Key objects. You should receive these object
 * calling getPressedKey() method.
 * This mode is easier but you cannot detect
 * when a key was released. Also Ctrl, Alt, Shift, Meta keys will be treated as modifiers
 * for normal keys. For instance, you cannot detect when Ctrl was pressed.
 *
 * If [eventMode] == true the keyboard will be collecting KeyboardEvent objects. You should
 * get those objects calling getEvent() method.
 * In this mode modifier keys will be treated as normal keys. You also will receive event for both
 * pressed and released keys.
 * */
class Keyboard(val window: Window, eventMode: Boolean = false) {
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
            e.keyCode, KeyModifiers(e.isShiftDown, e.isAltDown, e.isControlDown, e.isMetaDown)
        )
    }

    internal inner class KeyboardEventAdapter: KeyListener {
        override fun keyTyped(e: KeyEvent?) {}

        override fun keyPressed(e: KeyEvent) {
            _events.add(KeyboardEvent(e.keyCode, true))
        }

        override fun keyReleased(e: KeyEvent) {
            _events.add(KeyboardEvent(e.keyCode, false))
        }
    }

    private var _keys = ArrayList<Key>()
    private var _events = ArrayList<KeyboardEvent>()

    private val keyEventAdapter = if(eventMode) KeyboardEventAdapter() else KeyAdapter()

    init {
        window.pane.addKeyListener(keyEventAdapter)
    }

    /** Return the pressed key pressed. Pressed keys collected in queue.
     *  This function returns the key which was pressed first.
     *  If the queue is empty returns null
     *
     * Returns object of Key class containing the code of the key and all key modifiers (shift, ctrl, alt).
     * */
    fun getPressedKey(): Key? =
        if(! _keys.isEmpty()) {
            val key = _keys.first()
            _keys.remove(key)
            key
        }
        else
            null

    /**
     * Return a keyboard event or null.
     */
    fun getEvent(): KeyboardEvent? =
        if(! _events.isEmpty()) {
            val e = _events.first()
            _events.remove(e)
            e
        }
        else
            null
}
