package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.events.Event
import com.anysolo.toyGraphics.events.EventManager
import com.anysolo.toyGraphics.events.KeyboardEvent
import com.anysolo.toyGraphics.vector.Area
import java.time.Instant
import java.util.*


interface Engine2LevelApi {

}

typealias EventFilter = (e: Event) -> Boolean


interface Engine2GameObjectApi {
    fun subscribe(eventFilter: EventFilter)
    fun unsubscribe()
    fun destroy()
}


data class EventSubscription(val filter: EventFilter, val receiver: GameObject)


class ExitEx: RuntimeException()


class PlatformerEngine(val window: Window, private val level: GameLevel): Engine2LevelApi {
    inner class Engine2GameObjectApiImpl(val gameObject: GameObject): Engine2GameObjectApi {
        override fun subscribe(eventFilter: EventFilter) {
            eventSubscriptions[gameObject] = EventSubscription(eventFilter, gameObject)
        }

        override fun unsubscribe() {
            eventSubscriptions.remove(gameObject)
        }

        override fun destroy() {
            removeObj(gameObject)
        }
    }


    private val eventManager = EventManager(window)
    private val eventSubscriptions = mutableMapOf<GameObject, EventSubscription>()
    private val objects = PriorityQueue<GameObject>()
        { o1: GameObject, o2: GameObject -> o1.zOrder().compareTo(o2.zOrder()) }

    private val collisionDetector: CollisionDetector = ListCollisionDetector()

    private var virtualTime = 0L
    private var frameCounter = 0
    private var lastFrameCounterStartTime = 0L
    private var fps = 0

    fun run() {
        loadFromLevel()
        objects.forEach {it.onStart(Engine2GameObjectApiImpl(it))}

        try {
            while (true) {
                updateVirtualTimeAndFps()
                pumpEvent()
                updateObjects()
                drawFrame()

                Thread.yield()
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
        collisionDetector.addObject(obj)
    }

    private fun removeObj(obj: GameObject) {
        collisionDetector.removeObject(obj)
        objects.remove(obj)
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

            objects.forEach {
                if(it is VisibleObject)
                    it.draw(gc)
            }

            drawFps(gc)
        }
    }

    private fun drawFps(g: Graphics) {
        g.color = Pal16.green
        g.drawText(0, window.height-10, "FPS: $fps")
    }

    private fun updateObjects() {
        objects.forEach { gobj ->
            if(gobj is DynamicObject) {
                val savedPos = gobj.point

                gobj.update(Engine2GameObjectApiImpl(gobj), virtualTime)
                val overlapingObjects = collisionDetector.findObjects(gobj.screenArea).filter { it != gobj }
                if(overlapingObjects.isNotEmpty()) {
                    gobj.point = savedPos
                } else
                    collisionDetector.update(gobj)
            }
        }
    }
}
