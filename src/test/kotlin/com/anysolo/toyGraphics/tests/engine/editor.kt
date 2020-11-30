package com.anysolo.toyGraphics.tests.engine

import com.anysolo.toyGraphics.Image
import com.anysolo.toyGraphics.Pal16
import com.anysolo.toyGraphics.gameEngine.LevelEditor
import com.anysolo.toyGraphics.gameEngine.VisibleImageObject


fun main() {
    val levelEditor = LevelEditor(background = Pal16.black)

    val brickHBlock = Image("graphicsFiles/brick-block-h.jpg")

    levelEditor.register("brick-h") { pos ->
        VisibleImageObject(pos, brickHBlock)
    }

    levelEditor.edit()
}
