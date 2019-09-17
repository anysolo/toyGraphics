package com.anysolo.toyGraphics

import kotlin.math.roundToInt


/** Allows to use turtle graphics like in Logo language.
 *
 * This is the simplest way to start working with computer graphics.
 * You command the turtle to turn left or right and to go forward or backward.
 * It draws on the window while it moving. You also can lift the turtle's pen up and
 * move without drawing.
 * */
class Turtle(window: Window) {
  private var angle: Double = 0.0

  private var position = Point(
        (window.width.toDouble() / 2).roundToInt(),
        (window.height.toDouble() / 2).roundToInt()
  )

  private var penIsDown = true
  private val gc = Graphics(window)

  fun forward(distance: Int) {
    val radianAngle = Math.toRadians(angle)

    val newPos = Point(
          (position.x + Math.sin(radianAngle) * distance).roundToInt(),
          (position.y + Math.cos(radianAngle) * distance).roundToInt()
    )

    if(penIsDown)
      gc.drawLine(position.x, position.y, newPos.x, newPos.y)

    position = newPos
  }

  fun backward(distance: Int) = forward(-distance)

  fun turnRight(angle: Double)  { this.angle += angle % 360 }
  fun turnLeft(angle: Double)   { this.angle -= angle % 360 }

  /** Lift the pen up. Turtle is going to move without drawing */
  fun penUp()   { penIsDown = false }

  /** Put the pen down. Turtle is going to draw line wherever is goes. */
  fun penDown() { penIsDown = true }

  /** set the color of the pen using Color class. */
  fun setColor(color: Color) {gc.color = color}

  /** Set one of 16 colors using integer number 0 .. 15 */
  fun setColor(color: Int) { gc.color = Pal16[color] }
}
