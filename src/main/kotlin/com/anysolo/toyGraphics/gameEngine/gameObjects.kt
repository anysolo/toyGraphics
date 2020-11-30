package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.vector.*


interface GameObject {
    val point: Point
    val screenArea: Area
}

abstract class StationaryObject(override val point: Point) : GameObject


interface VisibleObject: GameObject {
    fun draw(gc: Graphics)
}


class VisibleImageObject(point: Point, val image: Image): VisibleObject, StationaryObject(point)  {
    override val screenArea: Area
        get() = Area(point, Vector(image.width, image.height))

    override fun draw(gc: Graphics) {
        gc.drawImage(point.roundToPos(), image)
    }
}


/*
class VisibleAnimationObject(point: Point, val animation: Animation): VisibleObject, StationaryObject(point) {
    override val screenArea: Rect
        get() = Rect(point, Vector(animation.width, animation.height))

    override fun draw(gc: Graphics) {
        gc.drawAnimation(point, animation)
    }

    override fun toData(): JsonData {
        return mapOf("point" to point.toString())
    }
}

interface DynamicObject: GameObject {
    var lastUpdateTime: Long

    fun update(time: Long)
}


interface MovingObject: DynamicObject {
    var acceleration: Vector
    var speed: Vector

    override fun update(time: Long) {
        val deltaTime = time - lastUpdateTime
        speed += acceleration * deltaTime

        point += speed * deltaTime
    }
}

interface MassObject: MovingObject {
    var mass: Double
    var force: Vector

    override fun update(time: Long) {
        acceleration = force / mass

        super.update(time)
    }
}


interface KeyboardEventReceiver {
    fun onKeyboardEvent(event: KeyboardEvent)
}


class WalkingCharacter(override var point: Point, override val screenArea: Rect) : MovingObject, KeyboardEventReceiver {

}
*/
