package com.example.simpletranslator.testUtils

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

fun loadTestResource(resourceName: String): String {
    val path = Paths.get(ClassLoader.getSystemClassLoader().getResource(resourceName).toURI())
    return String(Files.readAllBytes(path), Charset.forName("UTF8"))
}
