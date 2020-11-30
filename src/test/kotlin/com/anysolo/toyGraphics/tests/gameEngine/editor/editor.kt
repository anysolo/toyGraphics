package com.anysolo.toyGraphics.tests.gameEngine.editor

import com.anysolo.toyGraphics.Pal16
import com.anysolo.toyGraphics.dataEngine.ClassMeta
import com.anysolo.toyGraphics.gameEngine.GameLevel
import com.anysolo.toyGraphics.gameEngine.ImageObject
import com.anysolo.toyGraphics.gameEngine.runLevelEditor


@ClassMeta(hid = "brick-h")
class BrickH: ImageObject() {
    init { imageFilename = "graphicsFiles/brick-block-h.jpg" }
}


fun main(args: Array<String>) {
    runLevelEditor(args=args, gameLevel = GameLevel(), background = Pal16.black)
}
