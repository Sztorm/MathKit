@file:Suppress("MemberVisibilityCanBePrivate")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.math.*

@JvmInline
value class Color32 private constructor(private val data: Int) {

    constructor(r: UByte, g: UByte, b: UByte, a: UByte) : this(data =
        r.toInt() + (g.toInt() shl 8) + (b.toInt() shl 16) + (a.toInt() shl 24))

    val r: UByte
        get() = data.toUByte()

    val g: UByte
        get() = (data ushr 8).toUByte()

    val b: UByte
        get() = (data ushr 16).toUByte()

    val a: UByte
        get() = (data ushr 24).toUByte()

    override fun toString(): String = StringBuilder(4 + 3 + 5 + 3 + 5 + 3 + 5 + 3 + 1)
        .append("(r: ").append(r)
        .append(", g: ").append(g)
        .append(", b: ").append(b)
        .append(", a: ").append(a)
        .append(')')
        .toString()
}