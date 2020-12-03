package com.anysolo.toyGraphics.tests.gameEngine.editor

import com.anysolo.toyGraphics.Graphics
import com.anysolo.toyGraphics.Pal16
import com.anysolo.toyGraphics.dataEngine.ClassMeta
import com.anysolo.toyGraphics.drawOval
import com.anysolo.toyGraphics.gameEngine.*
import com.anysolo.toyGraphics.vector.Vector
import kotlin.random.Random


@ClassMeta(hid = "brick-h")
class BrickH: ImageObject() {
    init { imageFilename = "graphicsFiles/brick-block-h.jpg" }
}


@ClassMeta(hid = "bullet")
class Bullet: AbstractArcadeMovingObject() {
    override fun onStart(engineApi: Engine2GameObjectApi) {
        super.onStart(engineApi)
        speed = Vector(Random.nextDouble(50.0), Random.nextDouble(50.0))
    }

    override val size: Vector
        get() = Vector(6, 6)

    override fun draw(gc: Graphics) {
        gc.color = Pal16.red
        gc.drawOval(point.roundToPos(), size.roundToSize(), fill = true)
    }
}


fun main(args: Array<String>) {
    runLevelEditor(args=args, gameLevel = GameLevel(), background = Pal16.black)
}
