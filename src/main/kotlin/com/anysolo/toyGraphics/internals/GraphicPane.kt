package com.anysolo.toyGraphics.internals

import java.awt.*
import javax.swing.JFrame

import com.anysolo.toyGraphics.Color


internal class GraphicPane(jframe: JFrame, background: Color, buffered: Boolean): Canvas() {
    init {
        this.background = background.jdkColor
        jframe.contentPane.add(this, BorderLayout.CENTER)
        jframe.isVisible = true

        if(buffered)
            createBufferStrategy(1)

        focusTraversalKeysEnabled = false
        requestFocus()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        ignoreRepaint = true
    }
}
