package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.Color
import com.anysolo.toyGraphics.Graphics
import com.anysolo.toyGraphics.Size
import com.anysolo.toyGraphics.Window
import com.anysolo.toyGraphics.events.Event
import com.anysolo.toyGraphics.events.EventManager
import java.time.Instant
import java.util.*


interface Engine2LevelApi {

}

typealias EventFilter = (e: Event) -> Boolean


interface Engine2GameObjectApi {
    fun subscribe(eventFilter: EventFilter, receiver: GameObject)
    fun unsubscribe(gameObject: GameObject)
}


data class EventSubscription(val filter: EventFilter, val receiver: GameObject)


class PlatformerEngine(windowSize: Size, backgroundColor: Color, val level: GameLevel): Engine2LevelApi, Engine2GameObjectApi {
    private val window = Window(size = windowSize, background = backgroundColor)
    private val eventManager = EventManager(window)
    private val eventSubscriptions = mutableMapOf<GameObject, EventSubscription>()
    private val objects = PriorityQueue<GameObject>()
        { o1: GameObject, o2: GameObject -> o1.zOrder().compareTo(o2.zOrder()) }

    private var virtualTime = 0L

    override fun subscribe(eventFilter: EventFilter, receiver: GameObject) {
        eventSubscriptions[receiver] = EventSubscription(eventFilter, receiver)
    }

    override fun unsubscribe(gameObject: GameObject) {
        eventSubscriptions.remove(gameObject)
    }

    fun run() {
        while (true) {
            virtualTime = Instant.now().toEpochMilli()

            pumpEvent()
            updateObjects()
            drawFrame()
        }
    }

    private fun pumpEvent() {
        while (true) {
            val event = eventManager.takeEvent() ?: break

            eventSubscriptions.values.forEach {
                if(it.filter(event))
                    it.receiver.onEvent(event)
            }
        }
    }

    private fun drawFrame() {
        Graphics(window).use { gc ->
            objects.forEach {
                if(it is VisibleObject)
                    it.draw(gc)
            }
        }
    }

    private fun updateObjects() {
        objects.filterIsInstance<DynamicObject>(). forEach {
            if(it is DynamicObject)
                it.update(this, virtualTime)
        }
    }
}
