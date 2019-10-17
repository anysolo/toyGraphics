package com.anysolo.toyGraphics

import java.awt.*

import com.anysolo.toyGraphics.internals.GraphicPane
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import kotlin.system.exitProcess


/**
 * The graphic window of your program.
 *
 * The window you draw on. You must create exactly one window in you program.
 *
 * By default all you drawing immediately appears on the window.
 * If you writing a game it will be a lot of flickering. The better way is to pass parameter
 * buffered=true. In this case you draw on a memory buffer. When you done drawing you call method close() from your Graphics object and all your
 * drawing pushed to the screen.
 *
 * Actually in buffered mode Window may use 2 or 3 buffers. Image you created pushes through all the buffers
 * until it reaches the screen. It means you may see the result on the screen with 2 or frames delay.
 */
open class Window(
    /** Width in pixels. */
    val width: Int,

    /** Height in pixels. */
    val height: Int,

    /** Background color. When you call Graphics.clean() it fills at the window with this color. */
    background: Color = Pal16.white,

    /** Buffered mode. Set it to true if you need to draw fast and without flickering. */
    val buffered: Boolean = false,

    /** Call sync() automatically after any drawing operation.
     *
     * It means program does not draw anything else until
     * the current drawing operation is visible on the screen.
     * For buffered window autoSync is disabled by default.
     * */
    val autoSync: Boolean = !buffered
) {
    private val frame = Frame()
    internal val pane: GraphicPane

    init {
        val size = Dimension(width, height)

        frame.isUndecorated = true
        frame.isResizable = false
        frame.size = size

        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(windowEvent: WindowEvent?) {
                exitProcess(0)
            }
        })

        pane = GraphicPane(background, buffered=buffered)
        pane.size = size

        frame.add(pane)
        frame.repaint()
        frame.isVisible = true

        while (!pane.ignoreRepaint) {
            sleep(1)
        }
    }

    /**
    * It means program does not draw anything else until
    * the current drawing operation is visible on the screen.
    * */
    fun sync() = Toolkit.getDefaultToolkit().sync()

    internal fun doAutoSync() {
        if(autoSync)
            sync()
    }

    /**
     * Closes the window.
     *
     * Call this function at the end of your program if you want you program to terminate when you exit from main()
     * If you did not close the window you program will be running until you close the window from OS or stop you program.
     */
    fun close() {
        frame.dispose()
    }
}
