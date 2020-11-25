package com.anysolo.toyGraphics.dataFile

import java.io.Closeable
import java.io.DataInputStream
import kotlin.reflect.full.createInstance


class InputFile internal constructor(private val inputStream: DataInputStream): Closeable {
    val input = Input(inputStream)

    inline fun <reified T> load(): T {
        val serializerCls = T::class.nestedClasses.find { it.java.isAnnotationPresent(Serializer::class.java) }
            ?: throw RuntimeException("Cannot find serializer class in class ${T::class.java.canonicalName}")

        val serializer = serializerCls.createInstance() as ISerializer<T>
        return serializer.load(input)
    }

    override fun close() = inputStream.close()
}
