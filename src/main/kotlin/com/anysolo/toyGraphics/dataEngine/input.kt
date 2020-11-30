package com.anysolo.toyGraphics.dataEngine

import java.io.Closeable
import java.io.DataInputStream


class Input internal constructor (private val stream: DataInputStream, val classCatalog: ClassCatalog): Closeable {
    inline fun <reified T: Writable> readValue(): T {
        val obj: T = T::class.java.getDeclaredConstructor().newInstance()
        obj.read(this)

        return obj
    }

    inline fun <reified T: Writable> readObject(): T {
        val classId = readInt()
        val classData = classCatalog.findClassById(classId)
        val obj = classData.instanceFactory()
        obj.read(this)

        return obj as T
    }

    override fun close() = stream.close()

    fun readInt(): Int = stream.readInt()
    fun readDouble(): Double = stream.readDouble()
    fun readString(): String = stream.readUTF()
}
