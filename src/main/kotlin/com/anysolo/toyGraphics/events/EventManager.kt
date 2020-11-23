package com.anysolo.toyGraphics.events

import com.anysolo.toyGraphics.Window
import java.util.*


interface Event


fun Char.isPrintable(ch: Char) =
    ch.isLetterOrDigit() || ch in " `~@#$%^&*()_+-=|\\:;\"'<,>.?/"


class EventManager(val window: Window) {
    private var eventQueue: Queue<Event> = ArrayDeque()

    fun takeEvent(): Event? {
        if(eventQueue.isEmpty())
            return null

        val event = eventQueue.first()
        eventQueue.remove()
        return event
    }

    fun putEvent(event: Event) {
        eventQueue.add(event)
    }

    init {
        window.pane.addKeyListener(KeyboardEventAdapter(this))
        window.pane.addMouseListener(MouseEventAdapter(this))
        window.pane.addMouseMotionListener(MouseMotionEventAdapter(this))
        window.pane.addComponentListener(WindowEventAdapter(this))
    }
}
