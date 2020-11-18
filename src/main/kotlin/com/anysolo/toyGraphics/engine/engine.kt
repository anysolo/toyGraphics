package com.anysolo.toyGraphics.engine

import com.anysolo.toyGraphics.Graphics
import java.time.Instant


class PlatformerEngine(val level: GameLevel) {
    fun getVirtualTime() = Instant.now().toEpochMilli()

    // todo: Separate update from drawing
    fun update(gc: Graphics) {
        val t = getVirtualTime()

        /*
        objects.filter { it is DynamicObject } . forEach {
            if(it is DynamicObject)
                it.update(t)
        }

        objects. forEach {
            if(it is VisibleObject)
                it.draw(gc)
        }
*/
    }
}

