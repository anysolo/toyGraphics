package com.anysolo.toyGraphics.internals

import java.awt.*

import com.anysolo.toyGraphics.Color


internal class GraphicPane(background: Color): Canvas() {
    init {
        this.background = background.jdkColor

        isFocusable = true
        focusTraversalKeysEnabled = false
        requestFocus()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        ignoreRepaint = true
    }
}
