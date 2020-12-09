package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.dataEngine.Input
import com.anysolo.toyGraphics.dataEngine.Output
import com.anysolo.toyGraphics.dataEngine.Writable
import com.anysolo.toyGraphics.events.Event
import com.anysolo.toyGraphics.vector.*


interface GameObject: Writable {
    var point: Point
    val size: Vector

    val screenArea: Area
        get() = Area(point, size)

    fun zOrder() = 1

    fun onStart(engineApi: Engine2GameObjectApi) {}
    fun onEvent(event: Event) {}

    fun subscribe(eventFilter: EventFilter, engineApi: Engine2GameObjectApi) =
        engineApi.subscribe(eventFilter)

    fun unsubscribe(engineApi: Engine2GameObjectApi) =
        engineApi.unsubscribe()

    override fun read(input: Input) {
        point = input.readPoint()
    }

    override fun write(output: Output) {
        output.writePoint(point)
    }
}

//abstract class StationaryObject(override val point: Point) : GameObject


interface VisibleObject: GameObject {
    fun draw(gc: Graphics)
}


interface DynamicObject: GameObject {
    fun update(engineApi: Engine2GameObjectApi, time: Long)
}


interface ArcadeMovingObject: DynamicObject, VisibleObject {
    var speed: Vector
}


abstract class AbstractVisibleObject: VisibleObject {
    override var point: Point = Point(0,0)
}


abstract class AbstractArcadeMovingObject: ArcadeMovingObject {
    override var speed: Vector = Vector(0, 0)
    override var point: Point = Point(0,0)
    var lastUpdateTimeMs = 0L

    override fun update(engineApi: Engine2GameObjectApi, time: Long) {
        if(lastUpdateTimeMs != 0L) {
            val deltaTime = (time - lastUpdateTimeMs) / 1000.0
            point += speed * deltaTime
        }

        lastUpdateTimeMs = time
    }
}


abstract class ImageObject: AbstractVisibleObject (){
    var imageFilename: String? = null
        set(value) {
            assert(value != null)

            field = value
            _image = ImageCache(value!!)
        }

    private var _image: Image? = null

    override val size: Vector
        get() = if(_image == null) Vector(0, 0) else _image!!.size.toVector()

    override fun read(input: Input) {
        super.read(input)
        imageFilename = input.readString()
    }

    override fun write(output: Output) {
        super.write(output)
        output.writeString(imageFilename!!)
    }

    override fun draw(gc: Graphics) {
        _image ?. let {
            gc.drawImage(point.roundToPos(), it)
        }
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
