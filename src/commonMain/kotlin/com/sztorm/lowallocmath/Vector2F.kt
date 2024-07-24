package com.sztorm.lowallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.*

/** Multiplies this vector by the [other] scalar. **/
inline operator fun Float.times(other: Vector2F) = Vector2F(this * other.x, this * other.y)

/**
 * Represents a vector of two single-precision 32-bit IEEE 754 floating point numbers.
 *
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

    /** Returns a vector composed of two [x] components of this vector. **/
    inline val xx: Vector2F
        get() {
            val x = this.x
            return Vector2F(x, x)
        }

    /** Returns a vector composed of two [y] components of this vector. **/
    inline val yy: Vector2F
        get() {
            val y = this.y
            return Vector2F(y, y)
        }

    /** Returns a vector composed of [x] and [y] components of this vector, respectively. **/
    inline val xy: Vector2F
        get() = this

    /** Returns vector composed of [y] and [x] components of this vector, respectively. **/
    inline val yx: Vector2F
        get() = Vector2F(y, x)

    /** Returns the squared length of this vector. **/
    inline val squaredLength: Float
        get() {
            val x: Float = this.x
            val y: Float = this.y
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

    /**
     * Returns a normalized copy of this vector if this vector [magnitude] is large enough to
     * safely normalize. Else returns [ZERO].
     */
    inline val normalized: Vector2F
        get() {
            val length: Float = this.length

            return if (length > 0.00001f) this / length
            else ZERO
        }

    /**
     * Returns a normalized copy of this vector if this vector [magnitude] is large enough to
     * safely normalize. Else returns [defaultValue].
     *
     * @param [defaultValue] The returned value if this vector could not be safely normalized.
     */
    inline fun normalizedOrElse(defaultValue: Vector2F): Vector2F {
        val length: Float = this.length

        return if (length > 0.00001f) this / length
        else defaultValue
    }

    /**
     * Returns a normalized copy of this vector if this vector [magnitude] is large enough to
     * safely normalize. Else returns the result of calling the [defaultValue] function.
     *
     * @param [defaultValue] The function whose result is returned if the vector could not be
     * safely normalized.
     */
    inline fun normalizedOrElse(defaultValue: () -> Vector2F): Vector2F {
        val length: Float = this.length

        return if (length > 0.00001f) this / length
        else defaultValue()
    }

    /** Returns the squared distance from this vector to the [other]. **/
    inline fun squaredDistanceTo(other: Vector2F): Float {
        val dX = other.x - this.x
        val dY = other.y - this.y

        return dX * dX + dY * dY
    }

    /** Returns the distance from this vector to the [other]. **/
    inline fun distanceTo(other: Vector2F): Float = sqrt(squaredDistanceTo(other))

    /**
     * Returns a vector composed of [x] and [y] components coerced to specified range of
     * [minimum]..[maximum].
     */
    inline fun coerceIn(minimum: Vector2F, maximum: Vector2F) =
        Vector2F(x.coerceIn(minimum.x, maximum.x), y.coerceIn(minimum.y, maximum.y))

    /**
     * Returns a vector composed of [x] and [y] components that are not less than the specified
     * [minimum] components.
     */
    inline fun coerceAtLeast(minimum: Vector2F) = Vector2F(max(x, minimum.x), max(y, minimum.y))

    /**
     * Returns a vector composed of [x] and [y] components that are not greater than the specified
     * [maximum] components.
     */
    inline fun coerceAtMost(maximum: Vector2F) = Vector2F(min(x, maximum.x), min(y, maximum.y))

    /** Returns a [String] representation of this vector in "Vector2F(x=[x], y=[y])" format. **/
    override fun toString(): String = StringBuilder(11 + 16 + 4 + 16 + 1)
        .append("Vector2F(x=").append(x).append(", y=").append(y).append(')')
        .toString()

    /**
     * Returns a value indicating whether this vector is approximately the [other] vector given
     * the specified [epsilon] tolerance.
     */
    inline fun isApproximately(other: Vector2F, epsilon: Float = 0.00001f): Boolean =
        x.isApproximately(other.x, epsilon) and y.isApproximately(other.y, epsilon)

    /**
     * Returns the dot product of this and the [other] vector.
     *
     * Selected identities that the [dot] satisfies:
     * 1. _a [dot] b = b [dot] a_
     * 2. _a [dot] b = a.[magnitude] * b.[magnitude] * cos(theta)_
     * 3. _(a [dot] b)^2 + (a [perpDot] b)^2 = a.[magnitude]^2 * b.[magnitude]^2_
     *
     * where _theta_ is the angle from vector _a_ to vector _b_.
     */
    inline infix fun dot(other: Vector2F): Float = x * other.x + y * other.y

    /**
     * Returns the perp dot product of this and the [other] vector.
     *
     * Selected identities that the [perpDot] satisfies:
     * 1. _a [perpDot] b = -b [perpDot] a_
     * 2. _a [perpDot] b = a.[magnitude] * b.[magnitude] * sin(theta)_
     * 3. _(a [perpDot] b)^2 + (a [dot] b)^2 = a.[magnitude]^2 * b.[magnitude]^2_
     *
     * where _theta_ is the angle from vector _a_ to vector _b_.
     */
    inline infix fun perpDot(other: Vector2F): Float = x * other.y - y * other.x

    /** Multiplies this vector by the [other] vector component-wise. **/
    inline infix fun multipliedComponentWiseBy(other: Vector2F) =
        Vector2F(x * other.x, y * other.y)

    /** Divides this vector by the [other] vector component-wise. **/
    inline infix fun dividedComponentWiseBy(other: Vector2F) =
        Vector2F(x / other.x, y / other.y)

    /**
     * Returns the geometric product, which is a result of multiplying this vector by the [other]
     * vector.
     *
     * Geometric product can be interpreted as a scaled rotation from this to the [other]
     * vector. The scale is a product of magnitudes of this and the [other] vector.
     */
    infix fun geometric(other: Vector2F): ComplexF {
        val (aX: Float, aY: Float) = this
        val (bX: Float, bY: Float) = other
        val dotProduct: Float = aX * bX + aY * bY
        val perpDotProduct: Float = aX * bY - aY * bX

        return ComplexF(dotProduct, perpDotProduct)
    }

    /**
     * Returns the smallest angle between this vector and the [other] vector. Angle is signed when
     * the shortest angular path from this vector to the [other] vector is clockwise.
     */
    fun angleTo(other: Vector2F): AngleF {
        val (aX: Float, aY: Float) = this
        val (bX: Float, bY: Float) = other
        val perpDotProduct: Float = aX * bY - aY * bX
        val dotProduct: Float = aX * bX + aY * bY

        return AngleF(atan2(perpDotProduct, dotProduct))
    }

    /**
     * Returns a normalized complex number which represent rotation from this vector to the
     * [other] vector.
     */
    inline fun rotationTo(other: Vector2F): ComplexF {
        val (aX: Float, aY: Float) = this
        val (bX: Float, bY: Float) = other
        val perpDotProduct: Float = aX * bY - aY * bX
        val dotProduct: Float = aX * bX + aY * bY
        val aMagnitudeSqr: Float = aX * aX + aY * aY
        val bMagnitudeSqr: Float = bX * bX + bY * bY
        val abMagnitudeReciprocal: Float = 1f / sqrt(aMagnitudeSqr * bMagnitudeSqr)

        return ComplexF(
            dotProduct * abMagnitudeReciprocal,
            perpDotProduct * abMagnitudeReciprocal
        )
    }

    /**
     * Converts this [Vector2F] value to [Vector2I].
     *
     * For each component:
     *
     * The fractional part, if any, is rounded down towards zero. Returns zero if this [Float]
     * component value is NaN, [Int.MIN_VALUE] if it's less than [Int.MIN_VALUE], [Int.MAX_VALUE]
     * if it's bigger than [Int.MAX_VALUE].
     */
    inline fun toVector2I() = Vector2I(x.toInt(), y.toInt())

    /**
     * Converts this [Vector2F] value to [ComplexF], where [x] and [y] represents the
     * [real][ComplexF.real] and [imaginary][ComplexF.imaginary] parts of a complex number.
     *
     * Note that this type of conversion is not mathematically correct.
     */
    fun toComplexF() = ComplexF(data)

    /** Returns a copy of this instance with specified properties altered. **/
    inline fun copy(x: Float = this.x, y: Float = this.y) = Vector2F(x, y)

    /** First component of the vector. **/
    inline operator fun component1(): Float = x

    /** Second component of the vector. **/
    inline operator fun component2(): Float = y

    /**
     * Returns a component specified by an [index].
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

    /** Adds the [other] vector to this vector. **/
    inline operator fun plus(other: Vector2F) = Vector2F(x + other.x, y + other.y)

    /** Subtracts the [other] vector from this vector. **/
    inline operator fun minus(other: Vector2F) = Vector2F(x - other.x, y - other.y)

    /** Multiplies this vector by the [other] vector component-wise. **/
    inline operator fun times(other: Vector2F) = Vector2F(x * other.x, y * other.y)

    /**
     * Multiplies this vector by the [other] scalar.
     *
     * The result [magnitude] is this.[magnitude] * [other].
     */
    inline operator fun times(other: Float) = Vector2F(x * other, y * other)

    /**
     * Multiplies this vector by the [other] complex number.
     *
     * The result [magnitude] is this.[magnitude] * [other].[magnitude][ComplexF.magnitude].
     *
     * The result [phase][ComplexF.phase] is this.[toComplexF].[phase][ComplexF.phase] +
     * [other].[phase][ComplexF.phase].
     */
    inline operator fun times(other: ComplexF): Vector2F {
        val (x: Float, y: Float) = this
        val (r: Float, i: Float) = other

        return Vector2F(x * r - y * i, x * i + y * r)
    }

    /** Divides this vector by the [other] vector component-wise. **/
    inline operator fun div(other: Vector2F) = Vector2F(x / other.x, y / other.y)

    /**
     * Divides this vector by the [other] scalar.
     *
     * The result [magnitude] is this.[magnitude] / [other].
     */
    inline operator fun div(other: Float) = Vector2F(x / other, y / other)

    /**
     * Divides this vector by the [other] complex number.
     *
     * The result [magnitude] is this.[magnitude] / [other].[magnitude][ComplexF.magnitude].
     *
     * The result [phase][ComplexF.phase] is this.[toComplexF].[phase][ComplexF.phase] -
     * [other].[phase][ComplexF.phase].
     */
    inline operator fun div(other: ComplexF): Vector2F {
        val (x: Float, y: Float) = this
        val (r: Float, i: Float) = other
        val factor: Float = 1f / (r * r + i * i)

        return Vector2F((x * r + y * i) * factor, (y * r - x * i) * factor)
    }

    companion object {
        /** The number of bits used to represent an instance of [Vector2F] in a binary form. **/
        const val SIZE_BITS: Int = 64

        /** The number of bytes used to represent an instance of [Vector2F] in a binary form. **/
        const val SIZE_BYTES: Int = 8

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
         * Returns a linearly interpolated vector between [a] and [b] vectors.
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
         * Returns a component-wise linearly interpolated vector between [a] and [b] vectors.
         *
         * @param [a] Source vector.
         * @param [b] Destination vector.
         * @param [t] Interpolator vector with expected ranges of (0..1, 0..1).
         */
        @JvmStatic
        @Suppress("SpellCheckingInspection")
        inline fun lerp(a: Vector2F, b: Vector2F, t: Vector2F) =
            Vector2F(Float.lerp(a.x, b.x, t.x), Float.lerp(a.y, b.y, t.y))

        /**
         * Returns an interpolator value of the linearly interpolated vector [t] between [a] and
         * [b] vectors.
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

        /**
         * Returns the closest point to the [point] on a line segment composed of the [a] and [b]
         * points.
         */
        @JvmStatic
        fun closestPointOnLineSegment(a: Vector2F, b: Vector2F, point: Vector2F): Vector2F {
            val ab: Vector2F = b - a
            val epsilon = 0.00001f

            if ((abs(ab.x) <= epsilon) and (abs(ab.y) <= epsilon)) {
                return a
            }
            val ap: Vector2F = point - a
            val t: Float = (ab dot ap) / (ab dot ab)
            val tClamped: Float = when {
                t < 0f -> 0f
                t > 1f -> 1f
                else -> t
            }
            return a + ab * tClamped
        }
    }
}

