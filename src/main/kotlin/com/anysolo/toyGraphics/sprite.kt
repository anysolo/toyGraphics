package com.anysolo.toyGraphics

import java.time.Instant

/**
 * A set of animation frames.
 *
 * You can load animation frames into this object from a sprite sheet image or an animated GIF.
 * Sprite sheet is an image file containing many frames in one image, like a 2D matrix.
 */
class SpriteSheet(val frames: List<Image>) {
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

    companion object {
    /** Load frames from the an animated GIF file.
     * @param [filename] name of the animated GIF file.
     * */
        fun loadFromAnimatedGif(filename: String): SpriteSheet {
            val images = ImageUtils.readImagesFromFile(filename)
            return SpriteSheet(images)
        }

        fun loadFromImageSheet(filename: String, columns: Int, rows: Int): SpriteSheet {
            val images = ImageUtils.loadImageSheet(filename, columns, rows)
            return SpriteSheet(images)
        }
    }
}

/**
 * A sprite is a set of pictures (frames) showing in a sequence.
 *
 * You can create an animation object from an animated GIF file or AnimationFrames object.
 * Using AnimationFrames allows you to create multiple animations using only one set of frames,
 * which preserves memory.
 *
 * To use animation, you draw it using method "drawAnimation" of Graphics class.
 * Each call of this method chooses the appropriate frame to draw,
 * depending on the time and frame delay you set.
 *
 * "Delay" is how long, in milliseconds, you want one frame to be on the screen.
 * By default, an animation is stopped after you create it, and you have to start it manually.
 * It also does not repeat.
 *
 * You can pass autoStart=true when you create an Animation object to make it start automatically.
 * If you pass loop=true it is going to restart from the first frame after the last one.
 *
 * @constructor Create an animation using existing [spriteSheet] object.
 * @param [spriteSheet] The existing AnimationFrames object used a base for this animation
 * @param [frames] A list of frames you want to use in your animation.
 * You specify active frames by their numbers in the original set frames
 * (AnimationFrames or animated gif file). First frame is 0. If you pass null all the frame will be used.
 * @param [delay] How long you want to see one frame on the screen, in milliseconds.
 * @param [autoStart] Do you want to start the animation automatically when it created?
 * @param [loop] Do you want the animation to play in a loop?
 */
class Sprite(
    val spriteSheet: SpriteSheet,
    frames: List<Int>? = null,
    val delay: Int = defaultFrameDelay,
    autoStart: Boolean = false,
    loop: Boolean = false
) {
    val frames: List<Int> = frames ?: (0 until spriteSheet.numberOfFrames).toList()
    private var currentFrameIndex: Int = 0
    private var lastFrameTime: Long = 0
    var isPlaying: Boolean = autoStart
    var isLoop: Boolean = loop

    /** Start switching frames from the first frame. */
    fun start() {
        isPlaying = true
        currentFrameIndex = 0
    }

    /** Stop switching frames. */
    fun stop() {
        isPlaying = false
        currentFrameIndex = 0
    }

    val numberOfFrames: Int
        get() = frames.size

    val height: Int
        get() = spriteSheet.height

    val width: Int
        get() = spriteSheet.width

    private fun getFrameImage(frameNum: Int): Image = spriteSheet.frames[frames[frameNum]]

    init {
        AnimationManager.add(this)
    }

    companion object {
        /** This frame delay will be used if you do not specify delay or pass null */
        const val defaultFrameDelay = 100
    }

    internal fun update(now: Long) {
        if(now - lastFrameTime >= delay) {
            lastFrameTime = now

            currentFrameIndex++
            if(currentFrameIndex == numberOfFrames) {
                if(isLoop)
                    currentFrameIndex = 0
                else
                    stop()
            }
        }
    }

    internal val currentFrameImage: Image
        get() = getFrameImage(currentFrameIndex)
}

/** AnimationManager switches frames in all animations when you call its update method. */
object AnimationManager {
    private val animations = mutableListOf<Sprite> ()

    internal fun add(player: Sprite) = animations.add(player)

    /** Goes through all animations and switch frames if needed.
     *
     * You must call this method between drawing frames. Usually the best moment to call it
     * right after sleep an the end of the game loop.
     */
    fun update() {
        val time = Instant.now()

        animations.filter {it.isPlaying} . forEach {
            it.update(time.toEpochMilli())
        }
    }
}

/** Draws current frame from animation.
 *
 * If animation is stopped this method will be always drawing the same frame.
 * */
fun Graphics.drawAnimation(x: Int, y: Int, sprite: Sprite) {
    drawImage(x, y, sprite.currentFrameImage)
}

/**
 * Draws rotated animation.
 *
 * Draws an animation rotated on [angle] measured in radians. angle=Math.PI will be a half of the full circle.
 * The animation rotates coordinates around an anchor point. You can set the anchor point using
 * [anchorx] and [anchory]. By default the anchor point is 0,0, meaning the animation rotates around its top left
 * corner.
 **/
fun Graphics.drawAnimation(x: Int, y: Int, sprite: Sprite, angle: Double, anchorx: Int = 0, anchory: Int = 0) {
    drawImage(x, y, sprite.currentFrameImage, angle, anchorx, anchory)
}
