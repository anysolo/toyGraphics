package com.anysolo.toyGraphics.dataFile


interface ISerializer<T> {
    fun save(output: Output, data: T)
    fun load(input: Input): T
}
