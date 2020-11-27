package com.anysolo.toyGraphics.tests.engine


import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.Test
import java.io.FileInputStream
import java.io.FileOutputStream


data class C1(val n1: Int = 0)

class GameObjectsTests {
    @Test
    fun t1() {
        val kryo = Kryo()
        kryo.register(C1::class.java)

        val c1 = C1(123)

        Output(FileOutputStream("test.dat")).use { output ->
            kryo.writeObject(output, c1)
        }

        Input(FileInputStream("test.dat")).use { input ->
            val c11 = kryo.readObject(input, C1::class.java)
            Assertions.assertEquals(c1, c11)
        }
    }
}
