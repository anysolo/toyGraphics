package com.anysolo.toyGraphics.gameEngine

import com.anysolo.toyGraphics.*
import com.anysolo.toyGraphics.dataEngine.DataEngine
import com.anysolo.toyGraphics.events.*
import com.anysolo.toyGraphics.vector.*


typealias ActionAfterStringInput = (str: String) -> Unit


class EditorError(val error: String): RuntimeException(error)


interface EditorMode {
    fun draw(gc: Graphics)
    fun processKeyboard(event: KeyboardEvent): Boolean
    fun finish()

    fun isRunning(): Boolean
}


abstract class AbstractEditorMode: EditorMode {
    private var _isRunning = true

    override fun isRunning() = _isRunning

    override fun finish() {
        _isRunning = false
    }
}


class StringEditorMode(val inputLinePos: Pos, val actionAfterStringInput: ActionAfterStringInput): AbstractEditorMode() {
    private val inputStringColor = Pal16.white
    var inputString = ""

    override fun draw(gc: Graphics) {
        gc.color = inputStringColor
        gc.drawText(inputLinePos, inputString)
    }

    override fun processKeyboard(event: KeyboardEvent): Boolean {
        if(!event.isPressed)
            return false

        var stopProcessing = false

        val ch = event.char

        when {
            event.code == KeyCodes.ENTER -> finish()

            ch.isLetterOrDigit() || ch in "_-" -> {
                inputString += ch
                stopProcessing = true
            }

            event.code == KeyCodes.BACKSPACE -> {
                if(!inputString.isEmpty()) {
                    inputString = inputString.dropLast(1)
                    stopProcessing = true
                }
            }
        }

        return stopProcessing
    }

    override fun finish() {
        actionAfterStringInput(inputString)
        super.finish()
    }
}


class LevelDrawer(val level: GameLevel, val gc: Graphics) {
    fun draw() {
        for(obj in level.objects) {
            if(obj is VisibleObject)
                obj.draw(gc)
        }
    }
}


class LevelEditor(val gameLevel: GameLevel, gameObjectPackages: List<String>, val background: Color, val filename: String, val isNewFile: Boolean) {
    val dataEngine = DataEngine(gameObjectPackages)
    private val wnd = Window(1024, 768, background = background, buffered = true)
    private var cursorPos = Pos(wnd.width/2, wnd.height/2)
    private val cursorSize = Size(6, 6)
    private val eventManager = EventManager(wnd)
    private val gridStep = Vector(8.0, 8.0)

    private var messageString = ""
    private val statusLinePos = Pos(0, wnd.height-10)

    private val inputLinePos = statusLinePos - Size(0, 15)

    private val cursorColor = Pal16.white
    private val statusLineColor = Pal16.white

    private var mode: EditorMode? = null

    private var currentBlockHid = ""
        set(value) {
            if(!dataEngine.hasHid(value))
                throw EditorError("There is not registration for object factory. Hid: $value")

            field = value
        }

    fun isModeRunning(): Boolean = mode?.isRunning() == true

    fun edit() {
        if(!isNewFile)
            load()

        while (true) {
            drawEverything()
            processEvents()
            sleep(2)
        }
    }

    private fun processEvents() {
        while (true) {
            val event = eventManager.takeEvent() ?: break

            when (event) {
                is KeyboardEvent -> processKeyboard(event)
                is MouseEvent -> processMouse(event)
            }
        }
    }

    private fun processMouse(event: MouseEvent) {
        when(event) {
            is MouseMovedEvent -> cursorPos = event.pos
            is MouseClickEvent -> println(event)
        }
    }

    fun load() {
        dataEngine.openInput(filename).use { input ->
            gameLevel.read(input)
        }
    }

    fun save() {
        dataEngine.createOutput(filename).use { output ->
            output.writeValue(gameLevel)
        }
    }

    private fun createObject(hid: String): GameObject {
        val obj = dataEngine.createInstanceByHid(hid) as? GameObject
            ?: throw RuntimeException("Class with hid: $hid is not GameObject")

        obj.point = cursorPos.toPoint()

        gameLevel.addObject(obj)

        return obj
    }

    private fun drawEverything() {
        Graphics(wnd).use {gc ->
            gc.clear()

            val levelDrawer = LevelDrawer(gameLevel, gc)
            levelDrawer.draw()

            drawCursor(gc)

            drawStatusLine(gc)

            if(isModeRunning())
                mode?.draw(gc)
        }
    }

    private fun drawCursor(gc: Graphics) {
        gc.color = cursorColor
        gc.drawLine(cursorPos - cursorSize.vertical(), cursorPos + cursorSize.vertical())
        gc.drawLine(cursorPos - cursorSize.horizontal(), cursorPos + cursorSize.horizontal())
    }

    private fun drawStatusLine(gc: Graphics) {
        val statusLineItems = mutableListOf<String>()

        if(currentBlockHid != "")
            statusLineItems.add("currentBlock: '$currentBlockHid'")

        if(messageString != "")
            statusLineItems.add(messageString)

        if(!statusLineItems.isEmpty()) {
            gc.color = statusLineColor
            gc.drawText(statusLinePos, statusLineItems.joinToString(", "))
        }
    }

    private fun processKeyboard(event: KeyboardEvent) {
        if(isModeRunning() && mode?.processKeyboard(event) == true)
            return

        if(!event.isPressed)
            return

        val ch = event.char

        if(ch.isLetterOrDigit()) {
            when(ch.toUpperCase()) {
                'O' -> mode = StringEditorMode(inputLinePos) {
                    try {
                        currentBlockHid = it
                    } catch (e: EditorError) {
                        messageString = "error: ${e.error}"
                    }
                }

                'S' -> {
                    save()
                }

                'L' -> {
                    load()
                }

                'P' -> {
                    playTheLevel()
                }
            }
        }
        else {
            if(
                KeyCodes.CTRL in event.pressedKeyCodes &&
                !event.pressedKeyCodes.intersect(KeyCodes.arrows).isEmpty()
            ) {
                val newObj = createObject(currentBlockHid)

                when (event.code) {
                    KeyCodes.LEFT -> cursorPos -= newObj.screenArea.size.horizontal().roundToSize()
                    KeyCodes.RIGHT -> cursorPos += newObj.screenArea.size.horizontal().roundToSize()
                    KeyCodes.UP -> cursorPos -= newObj.screenArea.size.vertical().roundToSize()
                    KeyCodes.DOWN -> cursorPos += newObj.screenArea.size.vertical().roundToSize()
                }
            } else {
                val step = if (KeyCodes.ALT in event.pressedKeyCodes) Vector(1.0, 1.0) else gridStep

                when (event.code) {
                    KeyCodes.LEFT -> cursorPos -= step.horizontal().roundToSize()
                    KeyCodes.RIGHT -> cursorPos += step.horizontal().roundToSize()
                    KeyCodes.UP -> cursorPos -= step.vertical().roundToSize()
                    KeyCodes.DOWN -> cursorPos += step.vertical().roundToSize()
                }
            }
        }
    }

    fun playTheLevel() {
        val gameEngine = PlatformerEngine(wnd, gameLevel)
        gameEngine.run()
        load()
    }
}
