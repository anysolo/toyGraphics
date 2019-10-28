package com.anysolo.toyGraphics

import java.time.Instant


class Animation(
    val imageSet: ImageSet,
    val frames: List<Int>,
    val delay: Int,
    autoStart: Boolean = false,
    loop: Boolean = false
) {
    private var currentFrameIndex: Int = 0
    private var lastFrameTime: Long = 0
    var isPlaying: Boolean = autoStart
    var isLoop: Boolean = loop

    constructor(
        imageSet: ImageSet,
        delay: Int = defaultFrameDelay,
        autoStart: Boolean = false,
        loop: Boolean = false
    ):
        this(imageSet, (0 until imageSet.numberOfImages).toList(), delay, autoStart, loop)
    {}

    fun start() {
        isPlaying = true
        currentFrameIndex = 0
    }

    fun stop() {
        isPlaying = false
        currentFrameIndex = 0
    }

    val amountOfFrames: Int
        get() = frames.size

    private fun getFrameImage(frameNum: Int): Image = imageSet.frames[frames[frameNum]]

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
            if(currentFrameIndex == amountOfFrames) {
                if(isLoop)
                    currentFrameIndex = 0
                else
                    stop()
            }
        }
    }

    val currentFrameImage: Image
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
