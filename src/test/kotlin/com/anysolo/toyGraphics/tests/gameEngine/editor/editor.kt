package com.anysolo.toyGraphics.tests.gameEngine.editor

import com.anysolo.toyGraphics.Pal16
import com.anysolo.toyGraphics.dataEngine.ClassMeta
import com.anysolo.toyGraphics.dataEngine.DataEngine
import com.anysolo.toyGraphics.gameEngine.LevelEditor
import com.anysolo.toyGraphics.gameEngine.ImageObject


@ClassMeta(hid = "brick-h")
class BrickH: ImageObject() {
    init { imageFilename = "graphicsFiles/brick-block-h.jpg" }
}


fun main() {
    val dataEngine = DataEngine(listOf("com.anysolo.toyGraphics.tests.gameEngine.editor"))
    val levelEditor = LevelEditor(dataEngine, background = Pal16.black)

    levelEditor.edit()
}
