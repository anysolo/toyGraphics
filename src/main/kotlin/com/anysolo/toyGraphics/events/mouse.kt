package com.anysolo.toyGraphics.events


import com.anysolo.toyGraphics.vector.Point
import java.awt.event.MouseEvent as JMouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener


// todo: point. Double vs Int vs generic?
interface MouseEvent: Event {
    val x: Int
    val y: Int

    val point: Point
        get() = Point(x, y)
}

interface MouseEventWithButton: MouseEvent {
    val button: Int
}

data class MouseClickEvent(override val x: Int, override val y: Int, override val button: Int): MouseEventWithButton
data class MousePressedEvent(override val x: Int, override val y: Int, override val button: Int): MouseEventWithButton
data class MouseReleasedEvent(override val x: Int, override val y: Int, override val button: Int): MouseEventWithButton

data class MouseDraggedEvent(override val x: Int, override val y: Int): MouseEvent
data class MouseMovedEvent(override val x: Int, override val y: Int): MouseEvent


internal class MouseEventAdapter(private val eventManager: EventManager): MouseListener {
    override fun mouseClicked(e: JMouseEvent) {
        eventManager.putEvent(MouseClickEvent(e.x, e.y, e.button))
    }

    override fun mousePressed(e: JMouseEvent) {
        eventManager.putEvent(MousePressedEvent(e.x, e.y, e.button))
    }

    override fun mouseReleased(e: JMouseEvent) {
        eventManager.putEvent(MouseReleasedEvent(e.x, e.y, e.button))
    }

    override fun mouseEntered(e: JMouseEvent) {}
    override fun mouseExited(e: JMouseEvent) {}
}


internal class MouseMotionEventAdapter(private val eventManager: EventManager): MouseMotionListener  {
    override fun mouseDragged(e: JMouseEvent) {
        eventManager.putEvent(MouseDraggedEvent(e.x, e.y))
    }

    override fun mouseMoved(e: JMouseEvent) {

        eventManager.putEvent(MouseMovedEvent(e.x, e.y))
    }
}
