package com.anysolo.toyGraphics

import java.awt.Toolkit

object ComputerInfo {
    val screenSize: Size
        get() {
            val d = Toolkit.getDefaultToolkit().screenSize
            return Size(d.width, d.height)
        }
}