package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.Image


object ImageCache {
    val cache = mutableMapOf<String, Image> ()

    fun lookup(filename: String): Image {
        val searchResult = cache[filename]
        if(searchResult != null)
            return searchResult

        val newImage = Image(filename)
        cache[filename] = newImage

        return newImage
    }

    operator fun invoke(filename: String) = lookup(filename)
}
