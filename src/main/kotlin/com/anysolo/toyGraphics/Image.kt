package com.anysolo.toyGraphics

import javax.swing.ImageIcon

/**
 * An image from file.
 *
 * You can draw an image from file using this class and Graphics.drawImage()
 * Supported file formats: GIF, PNG, JPEG
 */
class Image(filename: String) {
    internal val jdkImage:ImageIcon = try {
        ImageIcon(filename)
    }
    catch (e: javax.imageio.IIOException) {
        throw ImageError(filename)
    }

    /** Width of the image in pixels */
    val width: Int
        get() = jdkImage.iconWidth

    /** Height of the image in pixels */
    val height: Int
        get() = jdkImage.iconHeight
}


class ImageError(val filename: String):
        ToyGraphicsError("Cannot load image file $filename")
