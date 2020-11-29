package com.anysolo.toyGraphics.dataFile

import java.io.Closeable
import java.io.DataOutputStream


class OutputFile internal constructor(private val outputStream: DataOutputStream): Closeable {
    val output = Output(outputStream)

    fun save(obj: Savable) {
        obj.save(output)
    }

    override fun close() = outputStream.close()
}
