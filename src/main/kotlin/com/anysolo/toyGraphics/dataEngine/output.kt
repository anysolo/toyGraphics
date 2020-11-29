package com.anysolo.toyGraphics.dataEngine

import java.io.Closeable
import java.io.DataOutputStream


class Output internal constructor(private val stream: DataOutputStream, private val classCatalog: ClassCatalog): Closeable {
    fun writeInt(data: Int) = stream.writeInt(data)
    fun writeDouble(data: Double) = stream.writeDouble(data)
    fun writeString(data: String) = stream.writeUTF(data)

    fun writeValue(obj: Writable) {
        obj.write(this)
    }

    fun writeObject(obj: Writable) {
        val classMeta = obj.getClassMeta()
        val classData = classCatalog.findClassByHid(classMeta.hid)
        writeInt(classData.id)
        obj.write(this)
    }

    override fun close() = stream.close()
}
