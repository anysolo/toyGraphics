package com.anysolo.toyGraphics.tests


import com.anysolo.toyGraphics.dataFile.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test



@ClassMeta(hid = "MyClass1")
data class MyClass1(val n1: Int, val s1: String) {
    @Serializer
    class MySerializer: ISerializer<MyClass1> {
        override fun save(output: Output, data: MyClass1) {
            output.write(data.n1)
            output.write(data.s1)
        }

        override fun load(input: Input) = MyClass1(
            input.readInt(),
            input.readString()
        )
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
