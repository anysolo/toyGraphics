package com.anysolo.toyGraphics.dataFile

import java.io.DataOutputStream


class Output(private val stream: DataOutputStream) {
    fun write(data: Int) = stream.writeInt(data)
    fun write(data: String) = stream.writeUTF(data)
}
