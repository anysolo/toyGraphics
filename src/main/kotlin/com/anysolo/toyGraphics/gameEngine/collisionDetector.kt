package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.vector.*


interface CollisionDetector {
    fun addObject(obj: GameObject)
    fun removeObject(obj: GameObject)
    fun update(obj: GameObject)
    fun findObjects(area: Area): List<GameObject>
}


class ListCollisionDetector: CollisionDetector {
    private val objects: MutableList<GameObject> = ArrayList ()

    override fun addObject(obj: GameObject)                 {objects.add(obj)}
    override fun removeObject(obj: GameObject)              {objects.remove(obj)}
    override fun update(obj: GameObject)                    {}
    override fun findObjects(area: Area): List<GameObject>  = objects.filter { it.screenArea.overlaps(area) }
}
