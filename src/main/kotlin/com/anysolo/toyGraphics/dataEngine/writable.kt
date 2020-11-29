package com.anysolo.toyGraphics.dataEngine


interface Writable {
    fun read(input: Input)
    fun write(output: Output)

    fun getClassMeta(): ClassMeta =
        this.javaClass.getAnnotation(ClassMeta::class.java)

}
