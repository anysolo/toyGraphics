package com.anysolo.toyGraphics.engine


class GameLevel {
    private val _objects = mutableListOf<GameObject>()

    val objects: List<GameObject> = _objects

    fun addObject(obj: GameObject) {
        _objects.add(obj)
    }

    fun removeObject(obj: GameObject) {
        _objects.remove(obj)
    }
}
