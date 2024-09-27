package com.sztorm.lowallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.*

/** Multiplies this value by the [other] value. **/
inline operator fun Float.times(other: AngleF) = AngleF(this * other.radians)

/**
 * Represents an angle stored in single-precision 32-bit IEEE 754 floating point number.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `float`.
 *
 * @property radians Returns the value of this angle in radians.
 */
@JvmInline
value class AngleF(val radians: Float) : Comparable<AngleF> {
    /** Returns the value of this angle in degrees. **/
    inline val degrees: Float
        get() = radians * DEGREES_IN_RADIAN

    /** Returns `true` if this angle is in `(0°, 90°)` range, `false` otherwise. **/
    inline fun isAcute(): Boolean = radians > 0f && radians < (PI * 0.5).toFloat()

    /** Returns `true` if this angle is in `(90°, 180°)` range, `false` otherwise. **/
    inline fun isObtuse(): Boolean = radians > (PI * 0.5).toFloat() && radians < PI.toFloat()

    /** Returns `true` if this angle is in `(180°, 360°)` range, `false` otherwise. **/
    inline fun isReflex(): Boolean = radians > PI.toFloat() && radians < (2.0 * PI).toFloat()

    /** Returns an angle that is coterminal to this angle and is in `[0°, 360°)` range. **/
    inline fun getMinimalPositiveCoterminal() = mod(FULL)

    /**
     * Returns a value indicating whether this angle is approximately [other] angle given
     * specified [epsilon] tolerance.
     */
    inline fun isApproximately(other: AngleF, epsilon: AngleF = AngleF(0.00001f)): Boolean =
        radians.isApproximately(other.radians, epsilon.radians)

    /**
     * Returns a value indicating whether this angle is approximately coterminal to the [other]
     * angle given specified [epsilon] tolerance.
     */
    inline fun isApproximatelyCoterminalTo(
        other: AngleF, epsilon: AngleF = AngleF(0.00001f)
    ): Boolean = getMinimalPositiveCoterminal()
        .isApproximately(other.getMinimalPositiveCoterminal(), epsilon)

    /**
     * Ensures that this value is not less than the specified [minimum].
     *
     * @return this value if it's greater than or equal to the [minimum] or the [minimum]
     * otherwise.
     */
    inline fun coerceAtLeast(minimum: AngleF) = AngleF(radians.coerceAtLeast(minimum.radians))

    /**
     * Ensures that this value is not greater than the specified [maximum].
     *
     * @return this value if it's less than or equal to the [maximum] or the [maximum] otherwise.
     */
    inline fun coerceAtMost(maximum: AngleF) = AngleF(radians.coerceAtMost(maximum.radians))

    /**
     * Ensures that this value lies in the specified range [minimum]..[maximum].
     *
     * @return this value if it's in the range, or [minimum] if this value is less than [minimum],
     * or [maximum] if this value is greater than [maximum].
     */
    inline fun coerceIn(minimum: AngleF, maximum: AngleF) =
        AngleF(radians.coerceIn(minimum.radians, maximum.radians))

    /**
     * Calculates the remainder of flooring division of this value (dividend) by the other value
     * (divisor).
     *
     * The result is either zero or has the same sign as the _divisor_ and has the absolute value
     * less than the absolute value of the divisor.
     *
     * If the result cannot be represented exactly, it is rounded to the nearest representable
     * number. In this case the absolute value of the result can be less than or _equal to_ the
     * absolute value of the divisor.
     */
    inline fun mod(other: AngleF) = AngleF(radians.mod(other.radians))

    /**
     * Converts this [AngleF] value to unit [ComplexF] with [phaseAngle][ComplexF.phaseAngle]
     * represented by the value of this instance.
     */
    inline fun toComplexF() = ComplexF(cos(radians), sin(radians))

    /**
     * Converts this [AngleF] value to unit [Vector2F] with [x][Vector2F.x] and [y][Vector2F.y]
     * components represented by the value of this instance.
     */
    inline fun toDirectionVectorF() = Vector2F(cos(radians), sin(radians))

    /**
     * Compares this value with the specified value for order. Returns zero if this value is equal
     * to the specified [other] value, a negative number if it's less than [other], or a positive
     * number if it's greater than [other].
     */
    override operator fun compareTo(other: AngleF): Int = radians.compareTo(other.radians)

    /** Returns this angle value. **/
    inline operator fun unaryPlus() = this

    /** Returns this angle value scaled by `-1`. **/
    inline operator fun unaryMinus() = AngleF(-radians)

    /** Adds the [other] value to this value. **/
    inline operator fun plus(other: AngleF) = AngleF(radians + other.radians)

    /** Subtracts the [other] value from this value. **/
    inline operator fun minus(other: AngleF) = AngleF(radians - other.radians)

    /** Multiplies this value by the [other] value. **/
    inline operator fun times(other: Float) = AngleF(radians * other)

    /** Divides this value by the [other] value. **/
    inline operator fun div(other: Float) = AngleF(radians / other)

    companion object {
        /** The number of bits used to represent an instance of [AngleF] in a binary form. **/
        const val SIZE_BITS: Int = 32

        /** The number of bytes used to represent an instance of [AngleF] in a binary form. **/
        const val SIZE_BYTES: Int = 4

        /** The number of degrees in one radian. **/
        const val DEGREES_IN_RADIAN = (180.0 / PI).toFloat()

        /** The number of radians in one degree. **/
        const val RADIANS_IN_DEGREE = (PI / 180.0).toFloat()

        /** An angle equal to `0` radians (`0°`). **/
        inline val ZERO
            @JvmStatic get() = AngleF(0f)

        /** An angle equal to `π/2` radians (`90°`). **/
        inline val RIGHT
            @JvmStatic get() = AngleF((PI * 0.5).toFloat())

        /** An angle equal to `π` radians (`180°`). **/
        inline val STRAIGHT
            @JvmStatic get() = AngleF(PI.toFloat())

        /** An angle equal to `2π` radians (`360°`). **/
        inline val FULL
            @JvmStatic get() = AngleF((2.0 * PI).toFloat())

        /** Creates a new angle using the specified [degrees] value. **/
        @JvmStatic
        inline fun fromDegrees(degrees: Float) = AngleF(degrees * RADIANS_IN_DEGREE)

        /** Creates a new angle using the specified [radians] value. **/
        @JvmStatic
        inline fun fromRadians(radians: Float) = AngleF(radians)

        /** Computes the sine of the given [angle].
         *
         *  Special cases:
         *   - `sin(NaN|+Inf|-Inf)` is `NaN`
         */
        @JvmStatic
        inline fun sin(angle: AngleF): Float = sin(angle.radians)

        /** Computes the cosine of the given [angle].
         *
         *  Special cases:
         *   - `cos(NaN|+Inf|-Inf)` is `NaN`
         */
        @JvmStatic
        inline fun cos(angle: AngleF): Float = cos(angle.radians)

        /** Computes the tangent of the given [angle].
         *
         *  Special cases:
         *   - `tan(NaN|+Inf|-Inf)` is `NaN`
         */
        @JvmStatic
        inline fun tan(angle: AngleF): Float = tan(angle.radians)

        /**
         * Computes the arc sine of the value [x];
         * the returned value is an angle in the range from `-PI/2` to `PI/2` radians.
         *
         * Special cases:
         *    - `asin(x)` is `NaN`, when `abs(x) > 1` or x is `NaN`
         */
        @JvmStatic
        inline fun asin(x: Float) = AngleF(kotlin.math.asin(x))

        /**
         * Computes the arc cosine of the value [x];
         * the returned value is an angle in the range from `0.0` to `PI` radians.
         *
         * Special cases:
         *    - `acos(x)` is `NaN`, when `abs(x) > 1` or x is `NaN`
         */
        @JvmStatic
        inline fun acos(x: Float) = AngleF(kotlin.math.acos(x))

        /**
         * Computes the arc tangent of the value [x];
         * the returned value is an angle in the range from `-PI/2` to `PI/2` radians.
         *
         * Special cases:
         *   - `atan(NaN)` is `NaN`
         */
        @JvmStatic
        inline fun atan(x: Float) = AngleF(kotlin.math.atan(x))

        /**
         * Returns the angle `theta` of the polar coordinates `(r, theta)` that correspond
         * to the rectangular coordinates `(x, y)` by computing the arc tangent of the value
         * [y] / [x]; the returned value is an angle in the range from `-PI` to `PI` radians.
         *
         * Special cases:
         *   - `atan2(0.0, 0.0)` is `0.0`
         *   - `atan2(0.0, x)` is  `0.0` for `x > 0` and `PI` for `x < 0`
         *   - `atan2(-0.0, x)` is `-0.0` for 'x > 0` and `-PI` for `x < 0`
         *   - `atan2(y, +Inf)` is `0.0` for `0 < y < +Inf` and `-0.0` for '-Inf < y < 0`
         *   - `atan2(y, -Inf)` is `PI` for `0 < y < +Inf` and `-PI` for `-Inf < y < 0`
         *   - `atan2(y, 0.0)` is `PI/2` for `y > 0` and `-PI/2` for `y < 0`
         *   - `atan2(+Inf, x)` is `PI/2` for finite `x`y
         *   - `atan2(-Inf, x)` is `-PI/2` for finite `x`
         *   - `atan2(NaN, x)` and `atan2(y, NaN)` is `NaN`
         */
        @JvmStatic
        inline fun atan2(y: Float, x: Float) = AngleF(kotlin.math.atan2(y, x))

        /**
         * Returns the smaller of two values.
         *
         * If either value is `NaN`, then the result is `NaN`.
         */
        @JvmStatic
        inline fun min(a: AngleF, b: AngleF) = AngleF(min(a.radians, b.radians))

        /**
         * Returns the greater of two values.
         *
         * If either value is `NaN`, then the result is `NaN`.
         */
        @JvmStatic
        inline fun max(a: AngleF, b: AngleF) = AngleF(max(a.radians, b.radians))
    }
}