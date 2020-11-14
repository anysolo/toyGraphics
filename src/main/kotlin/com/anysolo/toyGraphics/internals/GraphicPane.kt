package com.anysolo.toyGraphics.internals

import com.anysolo.toyGraphics.Color
import java.awt.Canvas
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent


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
