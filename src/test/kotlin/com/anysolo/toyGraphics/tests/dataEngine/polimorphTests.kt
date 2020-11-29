package com.anysolo.toyGraphics.tests.dataEngine

import com.anysolo.toyGraphics.dataEngine.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


interface IScreenObject: Writable {
    var x: Int
    var y: Int

    abstract fun textDump(): String
}


@ClassMeta(hid = "ScreenObject")
abstract class ScreenObject: IScreenObject {
    override var x: Int = 0
    override var y: Int = 0

    override fun read(input: Input) {
        x = input.readInt()
        y = input.readInt()
    }

    override fun write(output: Output) {
        output.writeInt(x)
        output.writeInt(y)
    }
}


@ClassMeta(hid = "ImageObject")
class ImageObject : ScreenObject() {
    var imageFilename : String = ""

    override fun textDump(): String = "x=$x, y=$y, image=$imageFilename"

    override fun read(input: Input) {
        super.read(input)
        imageFilename = input.readString()
    }

    override fun write(output: Output) {
        super.write(output)
        output.writeString(imageFilename)
    }
}


@ClassMeta(hid = "MyRectObject")
class MyRectObject : ScreenObject() {
    var size : Int = 10

    override fun textDump(): String = "x=$x, y=$y, size=$size"

    override fun read(input: Input) {
        super.read(input)
        size = input.readInt()
    }

    override fun write(output: Output) {
        super.write(output)
        output.writeInt(size)
    }
}


class PolymorphTests {
    @Test
    fun test1() {
        val dataFilename = "test.dat"
        val dataEngine = DataEngine(listOf("com.anysolo.toyGraphics.tests.dataEngine"))

        val o1 = ImageObject()
        o1.x = 100
        o1.y = 200
        o1.imageFilename = "image1.png"

        val o2 = MyRectObject()
        o2.x = 400
        o2.y = 500
        o2.size = 50

        dataEngine.createOutput(dataFilename).use { output ->
            output.writeObject(o1)
            output.writeObject(o2)
        }

        dataEngine.openInput(dataFilename).use { input ->
            val o11 = input.readObject<IScreenObject>()
            val o22 = input.readObject<IScreenObject>()

            assertEquals(o1.textDump(), o11.textDump())
            assertEquals(o2.textDump(), o22.textDump())
        }
    }
}
