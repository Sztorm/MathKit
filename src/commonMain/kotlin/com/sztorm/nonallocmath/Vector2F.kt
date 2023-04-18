@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/** Multiplies this vector by the scalar. **/
inline operator fun Float.times(other: Vector2F) = Vector2F(this * other.x, this * other.y)

/**
 * Represents a vector of two single-precision 32-bit IEEE 754 floating point numbers.
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `long`.
 */
@JvmInline
value class Vector2F internal constructor(internal val data: Long) {

    /** Constructs a new vector with given [x] and [y] components. **/
    constructor(x: Float, y: Float) : this(
        (x.toRawBits().toLong() and 0xFFFFFFFFL) or
                (y.toRawBits().toLong() shl Float.SIZE_BITS)
    )

    /** First component of the vector. **/
    val x: Float
        get() = Float.fromBits(data.toInt())

    /** Second component of the vector. **/
    val y: Float
        get() = Float.fromBits((data ushr Float.SIZE_BITS).toInt())

    /** Returns vector composed of two [x] components of this vector. **/
    inline val xx: Vector2F
        get() {
            val x = this.x
            return Vector2F(x, x)
        }

    /** Returns vector composed of two [y] components of this vector. **/
    inline val yy: Vector2F
        get() {
            val y = this.y
            return Vector2F(y, y)
        }

    /** Returns vector composed of [x] and [y] components respectively. **/
    inline val xy: Vector2F
        get() = this

    /** Returns vector composed of [y] and [x] components respectively. **/
    inline val yx: Vector2F
        get() = Vector2F(y, x)

    /** Returns squared magnitude of this vector. **/
    inline val squaredMagnitude: Float
        get() {
            val x = this.x
            val y = this.y
            return x * x + y * y
        }

    /** Returns magnitude (length) of this vector. **/
    inline val magnitude: Float
        get() = sqrt(squaredMagnitude)

    /**
     * Returns normalized copy of this vector if this vector [magnitude] is large enough to safely
     * normalize. Else returns [ZERO].
     */
    inline val normalized: Vector2F
        get() {
            val magnitude = this.magnitude

            return if (magnitude > 0.00001f) this / magnitude
            else ZERO
        }

    /** Returns squared distance from this vector to the [other]. **/
    inline fun squaredDistanceTo(other: Vector2F): Float {
        val dX = other.x - this.x
        val dY = other.y - this.y

        return dX * dX + dY * dY
    }

    /** Returns distance from this vector to the [other]. **/
    inline fun distanceTo(other: Vector2F): Float = sqrt(squaredDistanceTo(other))

    /**
     * Returns vector composed of [x] and [y] components coerced to specified range of
     * [minimum]..[maximum].
     */
    inline fun coerceIn(minimum: Vector2F, maximum: Vector2F) =
        Vector2F(x.coerceIn(minimum.x, maximum.x), y.coerceIn(minimum.y, maximum.y))

    /**
     * Returns vector composed of [x] and [y] components that are not less than the specified
     * [minimum] components.
     */
    inline fun coerceAtLeast(minimum: Vector2F) = Vector2F(max(x, minimum.x), max(y, minimum.y))

    /**
     * Returns vector composed of [x] and [y] components that are not greater than the specified
     * [maximum] components.
     */
    inline fun coerceAtMost(maximum: Vector2F) = Vector2F(min(x, maximum.x), min(y, maximum.y))

    /** Returns [String] in ([x],[y]) format. **/
    override fun toString(): String = StringBuilder(1 + 16 + 2 + 16 + 1)
        .append('(').append(x).append(", ").append(y).append(')')
        .toString()

    /**
     * Returns value indicating whether this vector is approximately [other] vector given specified
     * [epsilon] tolerance.
     * */
    inline fun isApproximately(other: Vector2F, epsilon: Float = 0.00001f): Boolean =
        x.isApproximately(other.x, epsilon) && y.isApproximately(other.y, epsilon)

    /** Returns dot product of this and [other] vector. **/
    inline infix fun dot(other: Vector2F): Float = x * other.x + y * other.y

    /** First component of the vector. **/
    inline operator fun component1(): Float = x

    /** Second component of the vector. **/
    inline operator fun component2(): Float = y

    /**
     * Returns component specified by [index].
     *
     * @throws [IndexOutOfBoundsException] when [index] is out of range of 0..1.
     */
    inline operator fun get(index: Int): Float = when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Index of $index is out of range of 0..1")
    }

    /** Returns this vector. **/
    inline operator fun unaryPlus() = this

    /** Returns the negative of this vector. **/
    inline operator fun unaryMinus() = Vector2F(-x, -y)

    /** Adds the other vector to this vector. **/
    inline operator fun plus(other: Vector2F) = Vector2F(x + other.x, y + other.y)

    /** Subtracts the other vector from this vector. **/
    inline operator fun minus(other: Vector2F) = Vector2F(x - other.x, y - other.y)

    /** Multiplies this vector by the other vector component-wise. **/
    inline operator fun times(other: Vector2F) = Vector2F(x * other.x, y * other.y)

    /** Multiplies this vector by the scalar. **/
    inline operator fun times(other: Float) = Vector2F(x * other, y * other)

    /** Divides this vector by the other vector component-wise. **/
    inline operator fun div(other: Vector2F) = Vector2F(x / other.x, y / other.y)

    /** Divides this vector by the scalar. **/
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
        /** The number of bits used to represent an instance of [Vector2F] in a binary form. **/
        const val SIZE_BYTES: Int = 8

        /** The number of bytes used to represent an instance of [Vector2F] in a binary form. **/
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

        /**
         * Returns linearly interpolated vector between [a] and [b] vectors.
         *
         * @param [a] Source vector.
         * @param [b] Destination vector.
         * @param [t] Interpolator value with an expected range of 0..1.
         */
        @JvmStatic
        @Suppress("SpellCheckingInspection")
        inline fun lerp(a: Vector2F, b: Vector2F, t: Float) =
            Vector2F(Float.lerp(a.x, b.x, t), Float.lerp(a.y, b.y, t))

        /**
         * Returns component-wise linearly interpolated vector between [a] and [b] vectors.
         *
         * @param [a] Source vector.
         * @param [b] Destination vector.
         * @param [t] Interpolator vector with an expected range of (0, 0)..(1, 1).
         */
        @JvmStatic
        @Suppress("SpellCheckingInspection")
        inline fun lerp(a: Vector2F, b: Vector2F, t: Vector2F) =
            Vector2F(Float.lerp(a.x, b.x, t.x), Float.lerp(a.y, b.y, t.y))

        /**
         * Returns interpolator value of the linearly interpolated vector [t] between [a] and [b]
         * vectors.
         *
         * @param [a] Source vector.
         * @param [b] Destination vector.
         * @param [t] Interpolated vector.
         */
        @JvmStatic
        @Suppress("SpellCheckingInspection")
        inline fun inverseLerp(a: Vector2F, b: Vector2F, t: Vector2F) =
            Vector2F(Float.inverseLerp(a.x, b.x, t.x), Float.inverseLerp(a.y, b.y, t.y))

        /** Returns a vector that is made from the largest components of two vectors. **/
        @JvmStatic
        inline fun max(a: Vector2F, b: Vector2F) = Vector2F(max(a.x, b.x), max(a.y, b.y))

        /** Returns a vector that is made from the smallest components of two vectors. **/
        @JvmStatic
        inline fun min(a: Vector2F, b: Vector2F) = Vector2F(min(a.x, b.x), min(a.y, b.y))
    }
}

