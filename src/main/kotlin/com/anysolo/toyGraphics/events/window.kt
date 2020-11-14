package com.anysolo.toyGraphics.events


import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent


sealed class WindowEvent: Event

data class WindowResizedEvent(val width: Int, val height: Int): WindowEvent()


internal class WindowEventAdapter(private val eventManager: EventManager): ComponentAdapter() {
    override fun componentResized(e: ComponentEvent) {
        eventManager.putEvent(WindowResizedEvent(e.component.width, e.component.height))
    }
}
