package com.anysolo.toyGraphics.tests


import com.anysolo.toyGraphics.dataFile.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test



@ClassMeta(hid = "MyClass1")
data class MyClass1(val n1: Int, val s1: String): Savable {
    constructor(input: Input): this(input.readInt(), input.readString())

    override fun save(output: Output) {
        output.write(n1)
        output.write(s1)
    }
}


class DataFileTests {
    @Test
    fun `simple data class save and load`() {
        val dataFilename = "test.dat"
        val dataEngine = DataEngine(listOf("com.anysolo.toyGraphics"))

        val o1 = MyClass1(7, "-7-")

        dataEngine.createOutput(dataFilename).use { output ->
            output.save(o1)
        }

        val o11 = dataEngine.openInput(dataFilename).use { input ->
            input.load<MyClass1>()
        }

        assertEquals(o1, o11)
    }
}
