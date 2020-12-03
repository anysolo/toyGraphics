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
        val size = input.readInt()
        _objects.clear()

        repeat(size) {
            _objects.add(input.readObject<GameObject>())
        }
    }

    override fun write(output: Output) {
        output.writeInt(_objects.size)
        _objects.forEach {output.writeObject(it)}
    }
}
