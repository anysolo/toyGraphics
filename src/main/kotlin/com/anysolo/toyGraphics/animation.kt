package com.anysolo.toyGraphics

import java.time.Instant

/**
 * A set of animation frames.
 *
 * You can load frames from a sprite sheet image or an animated GIF.
 *
 * Sprite sheet image must be file containing many frames in one image, like a 2D matrix.
 * All frames should have the same width and height. All frames will be numbered from 0,
 * from left to right and from top to bottom.
 *
 * If you use an animated GIF, every frame of the GIF file will be loaded as a separated frames
 * and numbered from 0.
 */
class AnimationFrames(val frames: List<Image>) {
    val width: Int
    val height: Int

    val numberOfFrames: Int
        get() = frames.size

    init {
        if(frames.isEmpty())
            throw ImageError("AnimationFrames object must contain at least one frame")

        width = frames.first().width
        height = frames.first().height
    }

    fun getFrame(index: Int) = frames[index]

    companion object {
    /** Load frames from the an animated GIF file.
     * @param [filename] name of the animated GIF file.
     * */
        fun loadFromAnimatedGif(filename: String): AnimationFrames {
            val images = ImageUtils.readImagesFromFile(filename)
            return AnimationFrames(images)
        }

        /** Load frames from a sprite sheet file.
         * @param [filename] name of the image file. It maybe PNG, GIF or JPEG file.
         * */
        fun loadFromImageSheet(filename: String, columns: Int, rows: Int): AnimationFrames {
            val images = ImageUtils.loadImageSheet(filename, columns, rows)
            return AnimationFrames(images)
        }
    }
}

/**
 * Animation is a set of pictures (activeFrames) showing in a sequence with given delay between them.
 *
 * You create an animation from prepared AnimationFrames object passing it as [frames] parameter.
 * One AnimationFrames object can be used to create as many animation as you want.
 * Each animation cab use it own subset of frames in its own order.
 *
 * To use an animation, you draw it using method "drawAnimation" of Graphics class.
 * When you draw an animation you really draw the current frame of this animation.
 * If the animation is stopped it does not have current frame and drawAnimation() draws nothing.
 *
 * You have to call update() from AnimationManager object to update current frame in each animation.
 *
 * "Delay" is how long, in milliseconds, you want one frame to be on the screen.
 *
 * If you pass loop=true it is going to restart from the first frame after the last one.
 * Without loop=true when the animation goes beyond the last frame it stops. drawAnimation()
 * call for a stopped animation will be ignored.
 *
 * @constructor Create an animation.
 * @param [frames] AnimationFrames object used as source of frames.
 * @param [activeFrames] A list of frames you want to use in your animation.
 * You specify activeFrames by their numbers in sprite sheet.
 * First frame is 0. If you pass null all the activeFrames will be used in original order.
 * @param [delay] How long you want to see one frame on the screen, in milliseconds.
 * @param [loop] Do you want the animation to play in a loop?
 */
class Animation(
    val frames: AnimationFrames,
    activeFrames: List<Int>? = null,
    val delay: Int = defaultFrameDelay,
    val loop: Boolean = false
) {
    var manager: AnimationManager? = null
    val activeFrames: List<Int> = activeFrames ?: (0 until frames.numberOfFrames).toList()

    val height: Int
        get() = frames.height

    val width: Int
        get() = frames.width

    val isRunning: Boolean
        get() = manager != null

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
            val frameNumberInAnimation = manager?.getCurrentFrameNumber(this) ?: return null
            val frameNumberInSheet = activeFrames[frameNumberInAnimation]
            return frames.getFrame(frameNumberInSheet)
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
            if(data.currentFrameIndex == animation.activeFrames.size) {
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
 * If animation is stopped this method does nothing.
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
