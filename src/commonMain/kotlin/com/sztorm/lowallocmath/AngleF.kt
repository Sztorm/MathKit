@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.lowallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.PI

/**
 * Represents an angle stored in single-precision 32-bit IEEE 754 floating point number.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `float`.
 *
 * @property radians Returns the value of this angle in radians.
 */
@JvmInline
value class AngleF(val radians: Float) {
    /** Returns the value of this angle in degrees. **/
    inline val degrees: Float
        get() = radians * DEGREES_IN_RADIAN

    /** Returns `true` if this angle is in (0°, 90°) range, `false` otherwise. **/
    fun isAcute(): Boolean = radians > 0f && radians < (PI * 0.5).toFloat()

    /** Returns `true` if this angle is in (90°, 180°) range, `false` otherwise. **/
    fun isObtuse(): Boolean = radians > (PI * 0.5).toFloat() && radians < PI.toFloat()

    /** Returns `true` if this angle is in (180°, 360°) range, `false` otherwise. **/
    fun isReflex(): Boolean = radians > PI.toFloat() && radians < (2.0 * PI).toFloat()

    /** Returns an angle that is coterminal to this angle and is in [0°, 360°) range. **/
    fun getMinimalPositiveCoterminal() =
        AngleF(
            ((radians % (2.0 * PI).toFloat()) + (2.0 * PI).toFloat()) % (2.0 * PI).toFloat()
        )

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

    companion object {
        /** The number of bits used to represent an instance of [AngleF] in a binary form. **/
        const val SIZE_BITS: Int = 32

        /** The number of bytes used to represent an instance of [AngleF] in a binary form. **/
        const val SIZE_BYTES: Int = 4

        /** The number of degrees in one radian. **/
        const val DEGREES_IN_RADIAN = (180.0 / PI).toFloat()

        /** The number of radians in one degree. **/
        const val RADIANS_IN_DEGREE = (PI / 180.0).toFloat()

        /** An angle equal to 0 radians (0°). **/
        inline val ZERO
            get() = AngleF(0f)

        /** An angle equal to π/2 radians (90°). **/
        inline val RIGHT
            get() = AngleF((PI * 0.5).toFloat())

        /** An angle equal to π radians (180°). **/
        inline val STRAIGHT
            get() = AngleF(PI.toFloat())

        /** An angle equal to 2π radians (360°). **/
        inline val FULL
            get() = AngleF((2.0 * PI).toFloat())

        /** Creates a new angle using the specified [degrees] value. **/
        @JvmStatic
        inline fun fromDegrees(degrees: Float) = AngleF(degrees * RADIANS_IN_DEGREE)

        /** Creates a new angle using the specified [radians] value. **/
        @JvmStatic
        inline fun fromRadians(radians: Float) = AngleF(radians)
    }
}