@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
value class Vector2F private constructor(private val data: Long) {

    constructor(x: Float, y: Float) : this(data =
        (x.toRawBits().toLong() and 0xFFFFFFFFL) or
        (y.toRawBits().toLong() shl Float.SIZE_BITS))

    val x: Float
        get() = Float.fromBits(data.toInt())

    val y: Float
        get() = Float.fromBits((data ushr Float.SIZE_BITS).toInt())

    inline val xx: Vector2F
        get() {
            val x = this.x
            return Vector2F(x, x)
        }

    inline val yy: Vector2F
        get() {
            val y = this.y
            return Vector2F(y, y)
        }

    inline val xy: Vector2F
        get() = this

    inline val yx: Vector2F
        get() = Vector2F(y, x)

    override fun toString(): String = StringBuilder(1 + 16 + 2 + 16 + 1)
        .append('(').append(x).append(", ").append(y).append(')')
        .toString()

    inline fun isApproximately(other: Vector2F, epsilon: Float = 0.0001f): Boolean =
        x.isApproximately(other.x, epsilon) && y.isApproximately(other.y, epsilon)

    inline operator fun get(index: Int): Float = when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Index of $index is out of bounds of 0..1")
    }

    inline operator fun unaryPlus() = this

    inline operator fun unaryMinus() = Vector2F(-x, -y)

    inline operator fun plus(other: Vector2F) = Vector2F(x + other.x, y + other.y)

    inline operator fun minus(other: Vector2F) = Vector2F(x - other.x, y - other.y)

    inline operator fun times(other: Vector2F) = Vector2F(x * other.x, y * other.y)

    inline operator fun times(other: Float) = Vector2F(x * other, y * other)

    inline operator fun div(other: Vector2F) = Vector2F(x / other.x, y / other.y)

    inline operator fun div(other: Float) = Vector2F(x / other, y / other)

    companion object {
        const val SIZE_BYTES: Int = 8
        const val SIZE_BITS: Int = 64

        /** Value of (0, 0) **/
        inline val ZERO
            @JvmStatic get() = Vector2F(0f, 0f)

        /** Value of (1, 1) **/
        inline val ONE
            @JvmStatic get() = Vector2F(1f, 1f)

        /** Value of (∞, ∞) **/
        inline val POSITIVE_INFINITY
            @JvmStatic get() = Vector2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)

        /** Value of (-∞, -∞) **/
        inline val NEGATIVE_INFINITY
            @JvmStatic get() = Vector2F(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY)

        @JvmStatic
        inline operator fun Float.times(other: Vector2F) = Vector2F(this * other.x, this * other.y)
    }
}

