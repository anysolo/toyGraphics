package com.anysolo.toyGraphics.events


import com.anysolo.toyGraphics.Pos
import java.awt.event.MouseEvent as JMouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener


interface MouseEvent: Event {
    val pos: Pos
}

interface MouseEventWithButton: MouseEvent {
    val button: Int
}

data class MouseClickEvent(override val pos: Pos, override val button: Int): MouseEventWithButton
data class MousePressedEvent(override val pos: Pos, override val button: Int): MouseEventWithButton
data class MouseReleasedEvent(override val pos: Pos, override val button: Int): MouseEventWithButton

data class MouseDraggedEvent(override val pos: Pos): MouseEvent
data class MouseMovedEvent(override val pos: Pos): MouseEvent


internal class MouseEventAdapter(private val eventManager: EventManager): MouseListener {
    override fun mouseClicked(e: JMouseEvent) {
        eventManager.putEvent(MouseClickEvent(Pos(e.x, e.y), e.button))
    }

    override fun mousePressed(e: JMouseEvent) {
        eventManager.putEvent(MousePressedEvent(Pos(e.x, e.y), e.button))
    }

    override fun mouseReleased(e: JMouseEvent) {
        eventManager.putEvent(MouseReleasedEvent(Pos(e.x, e.y), e.button))
    }

    override fun mouseEntered(e: JMouseEvent) {}
    override fun mouseExited(e: JMouseEvent) {}
}


internal class MouseMotionEventAdapter(private val eventManager: EventManager): MouseMotionListener  {
    override fun mouseDragged(e: JMouseEvent) {
        eventManager.putEvent(MouseDraggedEvent(Pos(e.x, e.y)))
    }

    override fun mouseMoved(e: JMouseEvent) {

        eventManager.putEvent(MouseMovedEvent(Pos(e.x, e.y)))
    }
}
