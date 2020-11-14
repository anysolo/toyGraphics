package com.anysolo.toyGraphics.events


import com.anysolo.toyGraphics.Size
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent


sealed class WindowEvent: Event

data class WindowResizedEvent(val size: Size): WindowEvent()


internal class WindowEventAdapter(private val eventManager: EventManager): ComponentAdapter() {
    override fun componentResized(e: ComponentEvent) {
        eventManager.putEvent(WindowResizedEvent(Size(e.component.width, e.component.height)))
    }
}
