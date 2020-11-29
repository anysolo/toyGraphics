package com.anysolo.toyGraphics.dataEngine

import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File


typealias InstanceFactory = () -> Writable

data class ClassData(val id: Int, val hid: String, val instanceFactory: InstanceFactory)


interface ClassCatalog {
    fun findClassByHid(hid: String): ClassData
    fun findClassById(id: Int): ClassData
}


class DataEngine(val packages: List<String>): ClassCatalog {
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
            ClassData(
                annotation.hid.hashCode(),
                annotation.hid
            ) { it.getDeclaredConstructor().newInstance() as Writable }
        }

    fun createOutput(filename: String) =
        Output(DataOutputStream(File(filename).outputStream()), this)

    fun openInput(filename: String) =
        Input(DataInputStream(File(filename).inputStream()), this)

    init {
        println(classData)
    }

    override fun findClassByHid(hid: String): ClassData =
        hidToClassData[hid] ?: throw RuntimeException("Cannot find class with hid: $hid")


    override fun findClassById(id: Int): ClassData =
        idToClassData[id] ?: throw RuntimeException("Cannot find class with id: $id")
}
