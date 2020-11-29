package com.anysolo.toyGraphics.dataFile

import java.io.Closeable
import java.io.DataInputStream
import kotlin.reflect.full.createType


class InputFile internal constructor(private val inputStream: DataInputStream): Closeable {
    companion object {
        val inputType = Input::class.createType()
    }

    val input = Input(inputStream)

    inline fun <reified T> load(): T {
        val constructors = T::class.constructors
        val conctor = constructors.find { c ->
            c.parameters.size == 1 && c.parameters.first().type == inputType
        } ?: throw RuntimeException("Cannot find constructor(Input) in class ${T::class.java.canonicalName}")

        return conctor.call(input)
    }

    override fun close() = inputStream.close()
}
