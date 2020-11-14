package com.anysolo.toyGraphics.events

import com.anysolo.toyGraphics.vector.Point
import java.awt.event.MouseEvent as JMouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener


sealed class MouseEvent(val x: Int, val y: Int): Event {
    override fun toString(): String =
        javaClass.simpleName + "(x=$x, y=$y)"

    val point: Point
        get() = Point(x, y)
}

class MouseClickEvent(x: Int, y: Int): MouseEvent(x, y)
class MousePressedEvent(x: Int, y: Int): MouseEvent(x, y)
class MouseReleasedEvent(x: Int, y: Int): MouseEvent(x, y)

class MouseDraggedEvent(x: Int, y: Int): MouseEvent(x, y)
class MouseMovedEvent(x: Int, y: Int): MouseEvent(x, y)


internal class MouseEventAdapter(private val eventManager: EventManager): MouseListener {
    override fun mouseClicked(e: JMouseEvent) {
        eventManager.putEvent(MouseClickEvent(e.x, e.y))
    }

    override fun mousePressed(e: JMouseEvent) {
        eventManager.putEvent(MousePressedEvent(e.x, e.y))
    }

    override fun mouseReleased(e: JMouseEvent) {
        eventManager.putEvent(MouseReleasedEvent(e.x, e.y))
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
