.. ToyGraphics documentation master file, created by
   sphinx-quickstart on Tue Dec  3 08:48:12 2019.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Game Development for Absolute Beginners
===========================================


Quick Start
Guide
API documentation
Source code on GitHub repository
Starter project
Demo applications

Why?
----------------------

I developed this library as an educational tool for those who just starting to learn Programming.

ToyGraphics library is
----------------------------

* A tool allowing you to write simple graphics and game application.
* Easy enough for an absolute beginner.
* 5 years kid can immediately start programming using Turtle graphics.
* Keeps the focus on programming itself.
* Uses Kotlin – a modern production programming language.
* Together with Kotlin helps to learn object-oriented and functional programming.

ToyGraphics is not
-------------------------

* Tool for developing production-quality, 3D and complex games or graphics.
* Learning a particular platform API: Java AWT, JavaFx.

Use the Starter project to quickly write your first program.

Some short examples
--------------------------

.. code-block:: kotlin

   // An example using coordinate system
   package com.anysolo.toyGraphics.demo
   import com.anysolo.toyGraphics.*

   fun main() {
       val wnd = Window(800, 600)
       val gc = Graphics(wnd)

       gc.drawRect(50, 50, 300, 200)
   }

.. code-block:: kotlin

   // Draw a square using turtle graphics
   package com.anysolo.toyGraphics.demo.turtle
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

   // Draw a square spiral using turtle graphics
   package com.anysolo.toyGraphics.demo.turtle
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

You find more demo applications in toyGraphics-demos Github repository.

.. toctree::
   :maxdepth: 3
   :caption: Contents:

   quickStart
   window
   drawing/turtle.rst




Indices and tables
==================

* :ref:`genindex`
* :ref:`modindex`
* :ref:`search`
