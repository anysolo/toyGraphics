package com.anysolo.toyGraphics.config

import kotlin.reflect.full.primaryConstructor

/*
class Config(val filename: String) {
    inline fun <reified T: Any> read(): T {
        val ctor = T::class.primaryConstructor ?: throw RuntimeException("Must have primary constructor")

        ctor.parameters.forEach { p ->
            println(p.name)
        }

    }

}
*/
