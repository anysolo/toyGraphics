package com.anysolo.toyGraphics.dataFile

import java.io.Closeable
import java.io.DataOutputStream
import kotlin.reflect.full.createInstance


class OutputFile internal constructor(private val outputStream: DataOutputStream): Closeable {
    val output = Output(outputStream)

    inline fun <reified T> save(obj: T) {
        val serializerCls = T::class.nestedClasses.find { it.java.isAnnotationPresent(Serializer::class.java) }
            ?: throw RuntimeException("Cannot find serializer class in class ${T::class.java.canonicalName}")

        val serializer = serializerCls.createInstance() as ISerializer<T>
        serializer.save(output, obj)
    }

    override fun close() = outputStream.close()
}
