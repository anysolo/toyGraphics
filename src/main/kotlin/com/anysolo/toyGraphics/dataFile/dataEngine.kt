package com.anysolo.toyGraphics.dataFile

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File


data class ClassData(val id: Int, val hid: String)

class DataEngine(val packages: List<String>) {
    val reflections = createReflections()
    val classData: List<ClassData> = createClassData()
    val idToClassData = classData.associateBy { it.id }
    val hidToClassData = classData.associateBy { it.hid }

    private fun collectAllClassUrls(): List<java.net.URL> {
        val urls = mutableListOf<java.net.URL>()
        for(p in packages) {
            urls.addAll(ClasspathHelper.forPackage(p))
        }

        return urls
    }

    private fun createReflections(): Reflections {
        return Reflections(
            ConfigurationBuilder()
                .setUrls(collectAllClassUrls())
                .setScanners(
                    TypeAnnotationsScanner(),
                    SubTypesScanner()
                )
        )
    }

    private fun createClassData(): List<ClassData> =
        reflections.getTypesAnnotatedWith(ClassMeta::class.java).map {
            val annotation = it.getAnnotation(ClassMeta::class.java)
            ClassData(annotation.hid.hashCode(), annotation.hid)
        }

    fun createOutput(filename: String) = OutputFile(DataOutputStream(File(filename).outputStream()))
    fun openInput(filename: String) = InputFile(DataInputStream(File(filename).inputStream()))

    init {
        println(classData)
    }
}
