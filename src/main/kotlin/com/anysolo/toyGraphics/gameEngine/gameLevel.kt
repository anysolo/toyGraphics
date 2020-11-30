package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.dataEngine.Input
import com.anysolo.toyGraphics.dataEngine.Output
import com.anysolo.toyGraphics.dataEngine.Writable


class GameLevel: Writable {
    private val _objects = mutableListOf<GameObject>()

    val objects: List<GameObject> = _objects

    fun addObject(obj: GameObject) {
        _objects.add(obj)
    }

    fun removeObject(obj: GameObject) {
        _objects.remove(obj)
    }

    override fun read(input: Input) {
        TODO("Not yet implemented")
    }

    override fun write(output: Output) {
        TODO("Not yet implemented")
    }
}
