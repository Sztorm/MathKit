@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

inline operator fun Float.times(other: Vector2F) = Vector2F(this * other.x, this * other.y)

@JvmInline
value class Vector2F internal constructor(internal val data: Long) {

    constructor(x: Float, y: Float) : this(
        (x.toRawBits().toLong() and 0xFFFFFFFFL) or
                (y.toRawBits().toLong() shl Float.SIZE_BITS)
    )

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

    inline val squaredMagnitude: Float
        get() {
            val x = this.x
            val y = this.y
            return x * x + y * y
        }

    inline val magnitude: Float
        get() = sqrt(squaredMagnitude)

    inline val normalized: Vector2F
        get() {
            val magnitude = this.magnitude

            return if (magnitude > 0.00001f) this / magnitude
            else ZERO
        }

    inline fun squaredDistanceTo(other: Vector2F): Float {
        val dX = other.x - this.x
        val dY = other.y - this.y

        return dX * dX + dY * dY
    }

    inline fun distanceTo(other: Vector2F): Float = sqrt(squaredDistanceTo(other))

    inline fun coerceIn(min: Vector2F, max: Vector2F) =
        Vector2F(x.coerceIn(min.x, max.x), y.coerceIn(min.y, max.y))

    override fun toString(): String = StringBuilder(1 + 16 + 2 + 16 + 1)
        .append('(').append(x).append(", ").append(y).append(')')
        .toString()

    inline fun isApproximately(other: Vector2F, epsilon: Float = 0.00001f): Boolean =
        x.isApproximately(other.x, epsilon) && y.isApproximately(other.y, epsilon)

    inline infix fun dot(other: Vector2F): Float = x * other.x + y * other.y

    inline operator fun component1(): Float = x

    inline operator fun component2(): Float = y

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

    /**
     * Converts this [Vector2F] value to [Vector2I].
     *
     * For each component:
     *
     * The fractional part, if any, is rounded down towards zero. Returns zero if this [Float]
     * value is NaN, [Int.MIN_VALUE] if it's less than [Int.MIN_VALUE], [Int.MAX_VALUE] if it's
     * bigger than [Int.MAX_VALUE].
     */
    inline fun toVector2I() = Vector2I(x.toInt(), y.toInt())

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
        inline fun lerp(a: Vector2F, b: Vector2F, t: Float) =
            Vector2F(Float.lerp(a.x, b.x, t), Float.lerp(a.y, b.y, t))

        @JvmStatic
        inline fun lerp(a: Vector2F, b: Vector2F, t: Vector2F) =
            Vector2F(Float.lerp(a.x, b.x, t.x), Float.lerp(a.y, b.y, t.y))

        @JvmStatic
        inline fun inverseLerp(a: Vector2F, b: Vector2F, t: Vector2F) =
            Vector2F(Float.inverseLerp(a.x, b.x, t.x), Float.inverseLerp(a.y, b.y, t.y))

        @JvmStatic
        inline fun max(a: Vector2F, b: Vector2F) = Vector2F(max(a.x, b.x), max(a.y, b.y))

        @JvmStatic
        inline fun min(a: Vector2F, b: Vector2F) = Vector2F(min(a.x, b.x), min(a.y, b.y))
    }
}

