package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.vector.*


interface CollisionDetector {
    fun addObject(obj: GameObject)
    fun removeObject(obj: GameObject)
    fun update(obj: GameObject)
    fun findObjects(area: Area): List<GameObject>
    fun gatherCollisionData(movingObject: GameObject): CollisionData?
}


data class CollisionData(val objects: List<GameObject>, val isHorizontal: Boolean, val isVertical: Boolean)


class ListCollisionDetector: CollisionDetector {
    private val objects: MutableList<GameObject> = ArrayList ()

    override fun addObject(obj: GameObject)                 {objects.add(obj)}
    override fun removeObject(obj: GameObject)              {objects.remove(obj)}
    override fun update(obj: GameObject)                    {}
    override fun findObjects(area: Area): List<GameObject>  = objects.filter { it.screenArea.overlaps(area) }

    override fun gatherCollisionData(movingObject: GameObject): CollisionData? {
        val overlappingObjects = findObjects(movingObject.screenArea).filter { it != movingObject}
        if(overlappingObjects.isEmpty())
            return null

        var isHorizontal = false
        var isVertical = false

        overlappingObjects.forEach {
            val mp1 = movingObject.screenArea.topLeft
            val mp2 = mp1 + movingObject.screenArea.size
            val ap1 = it.screenArea.topLeft
            val ap2 = it.screenArea.topLeft + it.size

            if(
                (mp1.x < ap2.x && mp1.x >= ap1.x) ||
                (mp2.x >= ap1.x && mp2.x < ap2.x)
            )
                isHorizontal = true

            if(
                (mp1.y < ap2.y && mp1.y >= ap1.y) ||
                (mp2.y >= ap1.y && mp2.y < ap2.y)
            )
                isVertical = true

            if(!isHorizontal && !isVertical) {
                println("Bred")
            }
        }

        return CollisionData(objects = overlappingObjects, isHorizontal = isHorizontal, isVertical = isVertical)
    }
}
