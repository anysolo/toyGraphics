package com.anysolo.toyGraphics.tests.dataEngine


import com.anysolo.toyGraphics.dataEngine.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test



@ClassMeta(hid = "MyClass1")
data class MyClass1(var n1: Int = 0, var s1: String = ""): Writable {

    override fun read(input: Input) {
        n1 = input.readInt()
        s1 = input.readString()
    }

    override fun write(output: Output) {
        output.writeInt(n1)
        output.writeString(s1)
    }
}


class NonPolymorphTests {
    @Test
    fun `simple data class save and load`() {
        val dataFilename = "test.dat"
        val dataEngine = DataEngine(listOf("com.anysolo.toyGraphics"))

        val o1 = MyClass1(7, "-7-")

        dataEngine.createOutput(dataFilename).use { output ->
            output.writeValue(o1)
        }

        val o11 = dataEngine.openInput(dataFilename).use { input ->
            input.readValue<MyClass1>()
        }

        assertEquals(o1, o11)
    }
}
