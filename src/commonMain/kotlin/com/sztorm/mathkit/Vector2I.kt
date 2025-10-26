@file:Suppress("NOTHING_TO_INLINE")

package com.sztorm.mathkit

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/** Multiplies this vector by the [other] scalar. **/
inline operator fun Int.times(other: Vector2I) = Vector2I(this * other.x, this * other.y)

/**
 * Represents a vector of two 32-bit signed integers.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `long`.
 */
@JvmInline
value class Vector2I internal constructor(internal val data: Long) {
    /** Constructs a new vector with given [x] and [y] components. **/
    constructor(x: Int, y: Int) : this(
        data = (x.toLong() and 0xFFFFFFFFL) or (y.toLong() shl Int.SIZE_BITS)
    )

    /** First component of the vector. **/
    val x: Int
        get() = data.toInt()

    /** Second component of the vector. **/
    val y: Int
        get() = (data ushr Int.SIZE_BITS).toInt()

    /** Returns a vector composed of two [x] components of this vector. **/
    inline val xx: Vector2I
        get() {
            val x = this.x
            return Vector2I(x, x)
        }

    /** Returns a vector composed of two [y] components of this vector. **/
    inline val yy: Vector2I
        get() {
            val y = this.y
            return Vector2I(y, y)
        }

    /** Returns a vector composed of [x] and [y] components of this vector, respectively. **/
    inline val xy: Vector2I
        get() = this

    /** Returns a vector composed of [y] and [x] components of this vector, respectively. **/
    inline val yx: Vector2I
        get() = Vector2I(y, x)

    /** Returns the squared length of this vector. **/
    inline val squaredLength: Float
        get() {
            val x = this.x.toFloat()
            val y = this.y.toFloat()
            return x * x + y * y
        }

    /** Returns the length of this vector. **/
    inline val length: Float
        get() = sqrt(squaredLength)

    /**
     * Returns the squared magnitude of this vector.
     *
     * This property is equal to [squaredLength].
     */
    inline val squaredMagnitude: Float
        get() = squaredLength

    /**
     * Returns the magnitude of this vector.
     *
     * This property is equal to [length].
     */
    inline val magnitude: Float
        get() = length

    /** Returns the squared distance from this vector to the [other]. **/
    inline fun squaredDistanceTo(other: Vector2I): Float {
        val dX = (other.x - this.x).toFloat()
        val dY = (other.y - this.y).toFloat()

        return dX * dX + dY * dY
    }

    /** Returns the distance from this vector to the [other]. **/
    inline fun distanceTo(other: Vector2I): Float = sqrt(squaredDistanceTo(other))

    /**
     * Returns a vector composed of [x] and [y] components coerced to specified range of
     * [minimum]..[maximum].
     */
    inline fun coerceIn(minimum: Vector2I, maximum: Vector2I) =
        Vector2I(x.coerceIn(minimum.x, maximum.x), y.coerceIn(minimum.y, maximum.y))

    /**
     * Returns a vector composed of [x] and [y] components that are not less than the specified
     * [minimum] components.
     */
    inline fun coerceAtLeast(minimum: Vector2I) = Vector2I(max(x, minimum.x), max(y, minimum.y))

    /**
     * Returns a vector composed of [x] and [y] components that are not greater than the specified
     * [maximum] components.
     */
    inline fun coerceAtMost(maximum: Vector2I) = Vector2I(min(x, maximum.x), min(y, maximum.y))

    /** Returns the dot product of this and [other] vector. **/
    inline infix fun dot(other: Vector2I): Long =
        x.toLong() * other.x.toLong() + y.toLong() * other.y.toLong()

    /** Returns a [String] representation of this vector in "Vector2I(x=[x], y=[y])" format. **/
    override fun toString(): String = StringBuilder(11 + 11 + 4 + 11 + 1)
        .append("Vector2I(x=").append(x).append(", y=").append(y).append(')')
        .toString()

    /**
     * Converts this [Vector2I] value to [Vector2F].
     *
     * For each component:
     *
     * The resulting value is the closest [Float] to this [Int] component value. In case when this
     * [Int] component value is exactly between two [Float]s, the one with zero at least
     * significant bit of mantissa is selected.
     */
    inline fun toVector2F() = Vector2F(x.toFloat(), y.toFloat())

    /** Returns a copy of this instance with specified properties altered. **/
    inline fun copy(x: Int = this.x, y: Int = this.y) = Vector2I(x, y)

    /** First component of the vector. **/
    inline operator fun component1(): Int = x

    /** Second component of the vector. **/
    inline operator fun component2(): Int = y

    /**
     * Returns a component specified by an [index].
     *
     * @throws [IndexOutOfBoundsException] when [index] is out of range of `[0, 1]`.
     */
    inline operator fun get(index: Int): Int = when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Index of $index is out of bounds of [0, 1].")
    }

    /** Returns this vector. **/
    inline operator fun unaryPlus() = this

    /** Returns the negative of this vector. **/
    inline operator fun unaryMinus() = Vector2I(-x, -y)

    /** Adds the [other] vector to this vector. **/
    inline operator fun plus(other: Vector2I) = Vector2I(x + other.x, y + other.y)

    /** Subtracts the [other] vector from this vector. **/
    inline operator fun minus(other: Vector2I) = Vector2I(x - other.x, y - other.y)

    /** Multiplies this vector by the [other] vector component-wise. **/
    inline operator fun times(other: Vector2I) = Vector2I(x * other.x, y * other.y)

    /** Multiplies this vector by the [other] scalar. **/
    inline operator fun times(other: Int) = Vector2I(x * other, y * other)

    /** Divides this vector by the [other] vector component-wise. **/
    inline operator fun div(other: Vector2I) = Vector2I(x / other.x, y / other.y)

    /** Divides this vector by the [other] scalar. **/
    inline operator fun div(other: Int) = Vector2I(x / other, y / other)

    companion object {
        /** The number of bits used to represent an instance of [Vector2I] in a binary form. **/
        const val SIZE_BITS: Int = 64

        /** The number of bytes used to represent an instance of [Vector2I] in a binary form. **/
        const val SIZE_BYTES: Int = 8

        /** Value of (0, 0) **/
        inline val ZERO
            @JvmStatic get() = Vector2I(0, 0)

        /** Value of (1, 1) **/
        inline val ONE
            @JvmStatic get() = Vector2I(1, 1)

        /** Returns a vector that is made from the largest components of two vectors. **/
        @JvmStatic
        inline fun max(a: Vector2I, b: Vector2I) = Vector2I(max(a.x, b.x), max(a.y, b.y))

        /** Returns a vector that is made from the smallest components of two vectors. **/
        @JvmStatic
        inline fun min(a: Vector2I, b: Vector2I) = Vector2I(min(a.x, b.x), min(a.y, b.y))
    }
}