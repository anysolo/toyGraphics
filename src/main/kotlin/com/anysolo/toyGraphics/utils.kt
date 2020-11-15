package com.anysolo.toyGraphics


/** Just do nothing for n milliceconds. */
fun sleep(ms: Int) = Thread.sleep(ms.toLong())


fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)

fun Any.getClassName() = javaClass.simpleName
fun Any.getFullClassName() = javaClass.canonicalName


fun Char.isPrintable(ch: Char) =
    ch.isLetterOrDigit() || ch in " `~@#$%^&*()_+-=|\\:;\"'<,>.?/"
