package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.dataEngine.ClassMeta
import com.anysolo.toyGraphics.dataEngine.Input
import com.anysolo.toyGraphics.dataEngine.Output
import com.anysolo.toyGraphics.dataEngine.Writable
import com.anysolo.toyGraphics.vector.*


interface GameObject: Writable {
    var point: Point
    val screenArea: Area

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


@ClassMeta(hid = "ImageObject")
abstract class ImageObject: VisibleObject {
    override var point: Point = Point(0,0)

    var imageFilename: String? = null
        set(value) {
            assert(value != null)

            field = value
            _image = ImageCache(value!!)
        }

    private var _image: Image? = null

    override val screenArea: Area
        get() {
            if(_image == null)
                return Area(Point(0, 0), Vector(0, 0))

            return Area(point, _image!!.size.toVector())
        }

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
