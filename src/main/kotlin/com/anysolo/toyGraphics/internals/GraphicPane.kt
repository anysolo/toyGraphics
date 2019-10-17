package com.anysolo.toyGraphics.internals

import java.awt.*

import com.anysolo.toyGraphics.Color


internal class GraphicPane(background: Color, buffered: Boolean): Canvas() {
    init {
        this.background = background.jdkColor

        if(buffered)
            createBufferStrategy(1)

        isFocusable = true
        focusTraversalKeysEnabled = false
        requestFocus()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        ignoreRepaint = true
    }
}
