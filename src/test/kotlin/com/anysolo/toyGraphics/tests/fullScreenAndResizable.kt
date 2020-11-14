package com.anysolo.toyGraphics.tests

import com.anysolo.toyGraphics.*
import kotlin.random.Random


fun makeRandomPos(windowSize: Size) = Pos(
    Random.nextInt(windowSize.width),
    Random.nextInt(windowSize.height)
)

fun main() {
    val wnd = Window(ComputerInfo.screenSize, buffered = false, background = Pal16.black)

    while(true) {
        Graphics(wnd).use { gc ->
            gc.color = Pal16[Random.nextInt(0, 15)]
            gc.drawLine(makeRandomPos(wnd.size), makeRandomPos(wnd.size))
            println(wnd.size)
            sleep(1000)
        }
    }
}