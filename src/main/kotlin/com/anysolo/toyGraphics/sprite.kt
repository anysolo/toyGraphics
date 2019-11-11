package com.anysolo.toyGraphics

import com.sun.org.apache.xpath.internal.operations.Bool
import java.time.Instant

/**
 * A set of animation frames.
 *
 * You can load frames from a sprite sheet image or an animated GIF.
 * Sprite sheet image must be file containing many frames in one image, like a 2D matrix.
 */
class AnimationFrameSheet(val frames: List<Image>) {
    val width: Int
    val height: Int

    val numberOfFrames: Int
        get() = frames.size

    init {
        if(frames.isEmpty())
            throw ImageError("ImageSet must contain at least one frame")

        width = frames.first().width
        height = frames.first().height
    }

    fun getFrame(index: Int) = frames[index]

    companion object {
    /** Load frames from the an animated GIF file.
     * @param [filename] name of the animated GIF file.
     * */
        fun loadFromAnimatedGif(filename: String): AnimationFrameSheet {
            val images = ImageUtils.readImagesFromFile(filename)
            return AnimationFrameSheet(images)
        }

        fun loadFromImageSheet(filename: String, columns: Int, rows: Int): AnimationFrameSheet {
            val images = ImageUtils.loadImageSheet(filename, columns, rows)
            return AnimationFrameSheet(images)
        }
    }
}

/**
 * A sprite is a set of pictures (frameNumbers) showing in a sequence.
 *
 * You can create a sprite from a [frameSheet].
 * One sprite sheet can be used to create as many spites as you want. Each sprite cab use it own subset of frame
 * in its own order.
 *
 * To use sprite, you draw it using method "drawSprite" of Graphics class.
 * Each call of this method chooses the appropriate frame to draw,
 * depending on the time and frame delay you set.
 *
 * "Delay" is how long, in milliseconds, you want one frame to be on the screen.
 * By default, an animation is stopped after you create the sprite, and you have to start it manually.
 * It also does not repeat.
 *
 * If you pass loop=true it is going to restart from the first frame after the last one.
 *
 * @constructor Create a sprite using existing [frameSheet] object.
 * @param [frameSheet] The sprite sheet used in this sprite.
 * @param [frameNumbers] A list of frameNumbers you want to use in your animation.
 * You specify frameNumbers by their numbers in sprite sheet.
 * First frame is 0. If you pass null all the frameNumbers will be used in original order.
 * @param [delay] How long you want to see one frame on the screen, in milliseconds.
 * @param [loop] Do you want the animation of the sprite to play in a loop?
 */
class Animation(
        var manager: AnimationManager?,
        val frameSheet: AnimationFrameSheet,
        frameNumbers: List<Int>? = null,
        val delay: Int = defaultFrameDelay,
        val loop: Boolean = false
) {
    init {
        manager?.add(this)
    }

    val frameNumbers: List<Int> = frameNumbers ?: (0 until frameSheet.numberOfFrames).toList()

    val numberOfFrames: Int
        get() = frameNumbers.size

    val height: Int
        get() = frameSheet.height

    val width: Int
        get() = frameSheet.width

    val isPlaying: Boolean
        get() = manager != null

//    private fun getFrameImage(frameNum: Int): Image = spriteSheet.frameNumbers[frameNumbers[frameNum]]

    companion object {
        /** This frame delay will be used if you do not specify delay or pass null */
        const val defaultFrameDelay = 100
    }

    fun start(manager: AnimationManager) {
        if(this.manager != null)
            stop()

        this.manager = manager
        manager.add(this)
    }

    fun stop() {
        manager?.let {
            it.remove(this)
            manager = null
        }
    }

    internal val currentFrameImage: Image?
        get() {
            val frameNumber = manager?.getCurrentFrameNumber(this)
            return if(frameNumber != null)frameSheet.getFrame(frameNumber) else null
        }
}

/** Sprite manager switches frames in all sprites when you call its update method. */
internal data class SpriteData(var currentFrameIndex: Int, var lastFrameTime: Long)

class AnimationManager {
    private val sprites = mutableMapOf<Animation, SpriteData> ()

    internal fun add(sprite: Animation) {
        sprites[sprite] = SpriteData(currentFrameIndex = 0, lastFrameTime = 0)
    }

    internal fun remove(animation: Animation) {
        sprites.remove(animation)
    }

    internal fun getCurrentFrameNumber(animation: Animation): Int? =
        sprites[animation]?.currentFrameIndex

    /** Goes through all animations and switch frames if needed.
     *
     * You must call this method between drawing frames. Usually the best moment to call it
     * right after sleep an the end of the game loop.
     */
    fun update() {
        val currentTime = Instant.now().toEpochMilli()

        val forRemoval = mutableListOf<Animation>()

        sprites.forEach { sprite, spriteData ->
            if(updateSprite(sprite, spriteData, currentTime))
                forRemoval.add(sprite)
        }

        forRemoval.forEach { sprites.remove(it) }
    }

    private fun updateSprite(animation: Animation, data: SpriteData, currentTime: Long): Boolean {
        if(currentTime - data.lastFrameTime >= animation.delay) {
            data.lastFrameTime = currentTime

            data.currentFrameIndex++
            if(data.currentFrameIndex == animation.numberOfFrames) {
                if(animation.loop)
                    data.currentFrameIndex = 0
                else
                    return true
            }
        }

        return false
    }
}


/** Draws current frame from animation.
 *
 * If animation is stopped this method will be always drawing the same frame.
 * */
fun Graphics.drawAnimation(x: Int, y: Int, animation: Animation) {
    animation.currentFrameImage?.let { drawImage(x, y, it) }
}

/**
 * Draws rotated animation.
 *
 * Draws an animation rotated on [angle] measured in radians. angle=Math.PI will be a half of the full circle.
 * The animation rotates coordinates around an anchor point. You can set the anchor point using
 * [anchorx] and [anchory]. By default the anchor point is 0,0, meaning the animation rotates around its top left
 * corner.
 **/
fun Graphics.drawAnimation(x: Int, y: Int, animation: Animation, angle: Double, anchorx: Int = 0, anchory: Int = 0) {
    animation.currentFrameImage?.let { drawImage(x, y, it, angle, anchorx, anchory) }
}
