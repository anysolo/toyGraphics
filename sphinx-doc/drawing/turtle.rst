Turtle
************************************

Detailed `API documentation <https://www.anysolo.com/toyGraphicsFiles/toy-graphics/com.anysolo.toy-graphics/-turtle/index.html>`_.

The easiest way to write a program drawing something on a screen is turtle graphics. I saw a five-year-old kid doing it.

You command the imaginary robot called “turtle” using very simple commands: forward, turnLeft, turnRight, backward. The turtle moves drawing line on the screen. You also can use commands: penUp and penDown to move it without drawing.

Turtle graphics was originally created as part of `Logo programming language <https://en.wikipedia.org/wiki/Logo_(programming_language)>`_
in the 1967 year and accepted as a great way to learn Programming.

Using ToyGraphics you can combine good old turtle graphics with a modern programming language (Kotlin).

If you use turtle graphics you may skip “Coordinate system” and “Graphics” in this manual.

Turtle graphics is very easy to use but has its own limitations.
If you need to write a game or use a Cartesian coordinate system you have to use Graphics class.

To use turtle graphics you have to create an object of the Turtle class passing the window as an argument. Like this:

val turtle = Turtle(wnd)
You create several turtles and move them independently.

Here are some complete examples and results on the screen:

.. code-block:: kotlin

    // Draws a square. For absolute beginners.

    package demos.turtle
    import com.anysolo.toyGraphics.*

    fun main() {
        val wnd = Window(800, 600)
        val turtle = Turtle(wnd)

        turtle.forward(100)
        turtle.turnRight(90.0)

        turtle.forward(100)
        turtle.turnRight(90.0)

        turtle.forward(100)
        turtle.turnRight(90.0)

        turtle.forward(100)
        turtle.turnRight(90.0)
    }

.. code-block:: kotlin

    // Draw a square spiral using a loop

    package demos.turtle
    import com.anysolo.toyGraphics.*

    fun main() {
      val wnd = Window(1024, 768)
      val turtle = Turtle(wnd)

      var step = 10

      repeat(50) {
        turtle.forward(step)
        turtle.turnRight(90.0)

        step += 10
      }
    }