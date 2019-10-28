package com.anysolo.toyGraphics

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.imageio.ImageReader


/**
 * An image from file.
 *
 * You can draw an image from file using this class and Graphics.drawImage()
 * Supported file formats: GIF, PNG, JPEG
 */
class Image internal constructor(internal val jdkImage: BufferedImage) {
    companion object

    /** Width of the image in pixels */
    val width: Int
        get() = jdkImage.width

    /** Height of the image in pixels */
    val height: Int
        get() = jdkImage.height
}

object ImageUtils {
    private fun getReaderForFile(filename: String): ImageReader {
        val stream = ImageIO.createImageInputStream(File(filename))
        val readers = ImageIO.getImageReaders(stream)
        if (!readers.hasNext())
            throw LoadImageError("no image reader found");

        val reader = readers.next() as ImageReader
        reader.input = stream

        return reader
    }

    internal fun readImagesFromFile(filename: String): List<Image> {
        val reader = getReaderForFile(filename)
        val numberOfImages = reader.getNumImages(true)

        return (0 until numberOfImages) . map { Image(reader.read(it)) }
    }
}

fun Image(filename: String): Image {
    val images = ImageUtils.readImagesFromFile(filename)
    if(images.size != 1)
        throw LoadImageError("filename: $filename contains more than one image.")

    return images.first()
}


class ImageSet(val frames: List<Image>) {
    val width: Int
    val height: Int

    init {
        if(frames.isEmpty())
            throw LoadImageError("ImageSet must contain at least one frame")

        width = frames.first().width
        height = frames.first().height
    }

    companion object {
        fun loadFromAnimatedGif(filename: String): ImageSet {
            val images = ImageUtils.readImagesFromFile(filename)
            return ImageSet(images)
        }
    }
}


class LoadImageError(val filename: String):
        ToyGraphicsError("Cannot load image file $filename")
