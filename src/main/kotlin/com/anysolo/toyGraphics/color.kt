package com.anysolo.toyGraphics

import java.awt.Color as JdkColor


/** Rgb color.
 *  You can create any color from red, green and blue values.
 *  RGB values should from in 0..255 range.
 */
class Color(r: Int, g: Int, b: Int) {
    internal val jdkColor: JdkColor = JdkColor(r, g, b)

    internal constructor(jdkColor: JdkColor) : this(jdkColor.red, jdkColor.green, jdkColor.blue)
}


/**
 * 16 colors palette from old computers.
 *
 * This is the easiest way to work with colors.
 * You can address these colors by index: ``Pal16[0]`` to ``Pal16[15]``, or you can address
 * them by names like: Pal16.green`
 */
object Pal16{
    val black = Color(0, 0, 0)
    val blue = Color(0, 0, 0xAA)
    val green = Color(0, 0xAA, 0)
    val cyan = Color(0, 0xAA, 0xAA)
    val red = Color(0xAA, 0, 0)
    val magenta = Color(0xAA, 0, 0xAA)
    val brown = Color(0xAA, 0x55, 0)
    val lightGray = Color(0xAA, 0xAA, 0xAA)
    val darkGray = Color(0x55, 0x55, 0x55)
    val brightBlue = Color(0x55, 0x55, 0xFF)
    val brightGreen = Color(0x55, 0xFF, 0x55)
    val brightCyan = Color(0x55, 0xFF, 0xFF)
    val brightRed = Color(0xFF, 0x55, 0x55)
    val brightMagenta = Color(0xFF, 0x55, 0xFF)
    val brightYellow = Color(0xFF, 0xFF, 0x55)
    val white = Color(0xFF, 0xFF, 0xFF)

    /** An array containg all 16 color objects. */
    val all = arrayOf(
        black,
        blue,
        green,
        cyan,
        red,
        magenta,
        brown,
        lightGray,
        darkGray,
        brightBlue,
        brightGreen,
        brightCyan,
        brightRed,
        brightMagenta,
        brightYellow,
        white
    )

    val size: Int
        get() = all.size

    operator fun get(index: Int): Color {
        assert(index in 0 until size)
        return all[index]
    }
}
