@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

inline operator fun Int.times(other: Vector2I) = Vector2I(this * other.x, this * other.y)

@JvmInline
value class Vector2I internal constructor(internal val data: Long) {

    constructor(x: Int, y: Int) : this(data =
        (x.toLong() and 0xFFFFFFFFL) or
        (y.toLong() shl Int.SIZE_BITS))

    val x: Int
        get() = data.toInt()

    val y: Int
        get() = (data ushr Int.SIZE_BITS).toInt()

    inline val xx: Vector2I
        get() {
            val x = this.x
            return Vector2I(x, x)
        }

    inline val yy: Vector2I
        get() {
            val y = this.y
            return Vector2I(y, y)
        }

    inline val xy: Vector2I
        get() = this

    inline val yx: Vector2I
        get() = Vector2I(y, x)

    inline val squaredMagnitude: Float
        get() {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            return x * x + y * y
        }

    inline val magnitude: Float
        get() = sqrt(squaredMagnitude)

    inline fun coerceIn(min: Vector2I, max: Vector2I) =
        Vector2I(x.coerceIn(min.x, max.x), y.coerceIn(min.y, max.y))

    override fun toString(): String = StringBuilder(1 + 11 + 2 + 11 + 1)
        .append('(').append(x).append(", ").append(y).append(')')
        .toString()

    inline infix fun dot(other: Vector2I): Int = x * other.x + y * other.y

    inline operator fun component1(): Int = x

    inline operator fun component2(): Int = y

    inline operator fun get(index: Int): Int = when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Index of $index is out of bounds of 0..1")
    }

    inline operator fun unaryPlus() = this

    inline operator fun unaryMinus() = Vector2I(-x, -y)

    inline operator fun plus(other: Vector2I) = Vector2I(x + other.x, y + other.y)

    inline operator fun minus(other: Vector2I) = Vector2I(x - other.x, y - other.y)

    inline operator fun times(other: Vector2I) = Vector2I(x * other.x, y * other.y)

    inline operator fun times(other: Int) = Vector2I(x * other, y * other)

    inline operator fun div(other: Vector2I) = Vector2I(x / other.x, y / other.y)

    inline operator fun div(other: Int) = Vector2I(x / other, y / other)

    inline fun toVector2F() = Vector2F(x.toFloat(), y.toFloat())

    companion object {
        const val SIZE_BYTES: Int = 8
        const val SIZE_BITS: Int = 64

        /** Value of (0, 0) **/
        inline val ZERO
            @JvmStatic get() = Vector2I(0, 0)

        /** Value of (1, 1) **/
        inline val ONE
            @JvmStatic get() = Vector2I(1, 1)

        @JvmStatic
        inline fun max(a: Vector2I, b: Vector2I) = Vector2I(max(a.x, b.x), max(a.y, b.y))

        @JvmStatic
        inline fun min(a: Vector2I, b: Vector2I) = Vector2I(min(a.x, b.x), min(a.y, b.y))
    }
}