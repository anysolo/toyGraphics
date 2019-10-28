package com.anysolo.toyGraphics

import java.time.Instant


class AnimationFrames(val frames: List<Image>) {
    val width: Int
    val height: Int

    val numberOfFrames: Int
        get() = frames.size

    constructor(filename: String): this(ImageUtils.readImagesFromFile(filename))

    init {
        if(frames.isEmpty())
            throw ImageError("ImageSet must contain at least one frame")

        width = frames.first().width
        height = frames.first().height
    }

    companion object {
        fun loadFromAnimatedGif(filename: String): AnimationFrames {
            val images = ImageUtils.readImagesFromFile(filename)
            return AnimationFrames(images)
        }
    }
}


class Animation(
    val frames: AnimationFrames,
    usedFrames: List<Int>?,
    val delay: Int = defaultFrameDelay,
    autoStart: Boolean = false,
    loop: Boolean = false
) {
    val usedFrames: List<Int> = usedFrames ?: (0 until frames.numberOfFrames).toList()
    private var currentFrameIndex: Int = 0
    private var lastFrameTime: Long = 0
    var isPlaying: Boolean = autoStart
    var isLoop: Boolean = loop

    constructor(
        frames: AnimationFrames,
        delay: Int = defaultFrameDelay,
        autoStart: Boolean = false,
        loop: Boolean = false
    ): this(frames, null, delay, autoStart, loop)

    constructor(
        filename: String,
        delay: Int = defaultFrameDelay,
        autoStart: Boolean = false,
        loop: Boolean = false
    ): this(AnimationFrames(filename), null, delay, autoStart, loop)

    fun start() {
        isPlaying = true
        currentFrameIndex = 0
    }

    fun stop() {
        isPlaying = false
        currentFrameIndex = 0
    }

    val numberOfFrames: Int
        get() = usedFrames.size

    val height: Int
        get() = frames.height

    val width: Int
        get() = frames.width

    private fun getFrameImage(frameNum: Int): Image = frames.frames[usedFrames[frameNum]]

    init {
        AnimationManager.add(this)
    }

    companion object {
        const val defaultFrameDelay = 100
    }

    fun update(now: Long) {
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


object AnimationManager {
    private val animations = mutableListOf<Animation> ()

    internal fun add(player: Animation) = animations.add(player)

    fun update() {
        val time = Instant.now()

        animations.filter {it.isPlaying} . forEach {
            it.update(time.toEpochMilli())
        }
    }
}


fun Graphics.drawAnimation(x: Int, y: Int, animation: Animation) {
    drawImage(x, y, animation.currentFrameImage)
}
