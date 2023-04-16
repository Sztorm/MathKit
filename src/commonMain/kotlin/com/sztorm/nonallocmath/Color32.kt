@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
value class Color32 private constructor(private val data: Int) {

    constructor(r: UByte, g: UByte, b: UByte, a: UByte) : this(
        data =
        r.toInt() + (g.toInt() shl 8) + (b.toInt() shl 16) + (a.toInt() shl 24)
    )

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

    inline operator fun component1(): UByte = r

    inline operator fun component2(): UByte = g

    inline operator fun component3(): UByte = b

    inline operator fun component4(): UByte = a

    operator fun get(index: Int): UByte =
        if (index.toUInt() < 4u) (data ushr (index shl 3)).toUByte()
        else throw IndexOutOfBoundsException("Index of $index is out of bounds of 0..3")

    companion object {
        const val SIZE_BYTES: Int = 4
        const val SIZE_BITS: Int = 32

        @JvmStatic
        @Suppress("SpellCheckingInspection")
        fun lerp(a: Color32, b: Color32, t: Float): Color32 {
            val ar: UByte = a.r
            val ag: UByte = a.g
            val ab: UByte = a.b
            val aa: UByte = a.a

            return Color32(
                (ar.toFloat() + (b.r - ar).toFloat() * t).toUInt().toUByte(),
                (ag.toFloat() + (b.g - ag).toFloat() * t).toUInt().toUByte(),
                (ab.toFloat() + (b.b - ab).toFloat() * t).toUInt().toUByte(),
                (aa.toFloat() + (b.a - aa).toFloat() * t).toUInt().toUByte()
            )
        }
    }
}