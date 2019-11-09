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

internal object ImageUtils {
    private fun getReaderForFile(filename: String): ImageReader {
        val file = File(filename)

        if(!file.exists())
            throw ImageFileError(filename, "File does not exists")

        if(!file.isFile)
            throw ImageFileError(filename, "Should a normal file, not a directory or something else.")

        if(!file.canRead())
            throw ImageFileError(filename, "Cannot read the file. Check file permissions.")

        val stream = ImageIO.createImageInputStream(file)
        val readers = ImageIO.getImageReaders(stream)
        if (!readers.hasNext())
            throw ImageFileError(filename, "No image reader found.")

        val reader = readers.next() as ImageReader
        reader.input = stream

        return reader
    }

    internal fun readImagesFromFile(filename: String): List<Image> {
        val reader = getReaderForFile(filename)
        val numberOfImages = reader.getNumImages(true)

        return (0 until numberOfImages) . map { Image(reader.read(it)) }
    }

    internal fun loadImageSheet(filename: String, columns: Int, rows: Int): List<Image> {
        val imageSheet = Image(filename)
        val imageHeight = imageSheet.height / rows
        val imageWidth = imageSheet.width / columns

        val images = mutableListOf<Image>()

        for(row in 0 until rows) {
            val y = row * imageHeight

            for(column in 0 until  columns) {
                val x = column * imageWidth

                images.add(Image(imageSheet.jdkImage.getSubimage(x, y, imageWidth, imageHeight)))
            }
        }

        return images
    }
}

fun Image(filename: String): Image {
    val images = ImageUtils.readImagesFromFile(filename)
    if(images.size != 1)
        throw ImageFileError(
            filename,
            "File contains more than one image. " +
                    "Use Animation or AnimationFrames classes to open animated gif."
        )

    return images.first()
}

open class ImageError(message: String): ToyGraphicsError(message)


class ImageFileError(val filename: String, message: String):
    ImageError("Image error with file: $filename: $message")
