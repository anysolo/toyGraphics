package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.Color
import com.sksamuel.hoplite.ConfigLoader


private class CmdLineError(val error: String): RuntimeException(error)


private fun printHelp() {
    println("Usage: <command> <level-filename> <config-filename>")
    println("Where:")
    println("  command: 'e' - for edit existing level or 'c' filename - to create a new level")
    println("  <level-filename> - the file containing game level")
    println("  <gameObjectPackages-filename> - the file containing packages with game object classes. One line for for one package")
}


fun runLevelEditor(args: Array<String>, gameLevel: GameLevel, background: Color) {
    try {
        if (args.size != 3)
            throw CmdLineError("You must provide two arguments")

        val cmd = args[0]

        if (cmd !in listOf("e", "c"))
            throw CmdLineError("Unknown command: $cmd")

        val levelFilename = args[1]
        val configFilename = args[2]

        val levelEditor = LevelEditor(
            gameLevel = gameLevel,
            config = EditorConfig.load(configFilename),
            background = background,
            filename = levelFilename,
            isNewFile = cmd == "c"
        )

        levelEditor.edit()
    } catch (e: CmdLineError) {
        println("Cmd error: ${e.error}")
        printHelp()
    }
}
