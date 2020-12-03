package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.events.Event
import com.anysolo.toyGraphics.events.EventManager
import com.anysolo.toyGraphics.events.KeyboardEvent
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


class ExitEx: RuntimeException()


class PlatformerEngine(val window: Window, private val level: GameLevel): Engine2LevelApi, Engine2GameObjectApi {
    private val eventManager = EventManager(window)
    private val eventSubscriptions = mutableMapOf<GameObject, EventSubscription>()
    private val objects = PriorityQueue<GameObject>()
        { o1: GameObject, o2: GameObject -> o1.zOrder().compareTo(o2.zOrder()) }

    private var virtualTime = 0L
    private var frameCounter = 0
    private var lastFrameCounterStartTime = 0L
    private var fps = 0

    override fun subscribe(eventFilter: EventFilter, receiver: GameObject) {
        eventSubscriptions[receiver] = EventSubscription(eventFilter, receiver)
    }

    override fun unsubscribe(gameObject: GameObject) {
        eventSubscriptions.remove(gameObject)
    }

    fun run() {
        loadFromLevel()
        objects.forEach {it.onStart(this)}

        try {
            while (true) {
                updateVirtualTimeAndFps()
                pumpEvent()
                updateObjects()
                drawFrame()

                sleep(1)
            }
        } catch (e: ExitEx) {}
    }

    private fun updateVirtualTimeAndFps() {
        virtualTime = Instant.now().toEpochMilli()

        if(virtualTime - lastFrameCounterStartTime > 1000) {
            fps = frameCounter
            frameCounter = 0
            lastFrameCounterStartTime = virtualTime
        }
        else
            frameCounter++
    }

    private fun loadFromLevel() {
        level.objects.forEach() {
            addObject(it)
        }
    }

    private fun addObject(obj: GameObject) {
        objects.add(obj)
    }

    private fun pumpEvent() {
        while (true) {
            val event = eventManager.takeEvent() ?: break

            if(!processEvent(event)) {
                eventSubscriptions.values.forEach {
                    if (it.filter(event))
                        it.receiver.onEvent(event)
                }
            }
        }
    }

    private fun processEvent(event: Event): Boolean {
        if(event is KeyboardEvent) {
            if(event.isPressed) {
                when(event.code) {
                    KeyCodes.F10 -> throw ExitEx()
                }
            }
        }

        return false
    }

    private fun drawFrame() {
        Graphics(window).use { gc ->
            gc.clear()

            drawFps(gc)

            objects.forEach {
                if(it is VisibleObject)
                    it.draw(gc)
            }
        }
    }

    private fun drawFps(g: Graphics) {
        g.color = Pal16.green
        g.drawText(0, window.height-10, "FPS: $fps")
    }

    private fun updateObjects() {
        objects.forEach {
            if(it is DynamicObject)
                it.update(this, virtualTime)
        }
    }
}
