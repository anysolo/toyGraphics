package com.anysolo.toyGraphics.dataFile

import java.io.DataInputStream


class Input(private val stream: DataInputStream) {
    fun readInt(): Int = stream.readInt()
    fun readString(): String = stream.readUTF()
}
