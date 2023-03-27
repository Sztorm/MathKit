@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic
import kotlin.math.*

inline val Float.i
    get() = ComplexF(0f, this)

inline fun Float.toComplexF() = ComplexF(this, 0f)

inline operator fun Float.plus(other: ComplexF) =
    ComplexF(this + other.real, other.imaginary)

inline operator fun Float.minus(other: ComplexF) =
    ComplexF(this - other.real, -other.imaginary)

inline operator fun Float.times(other: ComplexF) =
    ComplexF(this * other.real, this * other.imaginary)

operator fun Float.div(other: ComplexF): ComplexF {
    val r1 = other.real
    val i1 = other.imaginary

    return if (i1.absoluteValue < r1.absoluteValue) {
        val i1OverR1 = i1 / r1
        val divisor = r1 + i1 * i1OverR1

        ComplexF(this / divisor, (-this * i1OverR1) / divisor)
    } else {
        val r1OverI1 = r1 / i1
        val divisor = i1 + r1 * r1OverI1

        ComplexF((this * r1OverI1) / divisor, -this / divisor)
    }
}

@JvmInline
value class ComplexF private constructor(private val data: Long) {

    constructor(real: Float, imaginary: Float) : this(
        (real.toRawBits().toLong() and 0xFFFFFFFFL) or
                (imaginary.toRawBits().toLong() shl Float.SIZE_BITS)
    )

    val real: Float
        get() = Float.fromBits(data.toInt())

    val imaginary: Float
        get() = Float.fromBits((data ushr Float.SIZE_BITS).toInt())

    inline val squaredMagnitude: Float
        get() {
            val r = this.real
            val i = this.imaginary
            return r * r + i * i
        }

    inline val magnitude: Float
        get() = sqrt(squaredMagnitude)

    inline val absoluteValue: Float
        get() = magnitude

    inline val phase: Float
        get() = atan2(imaginary, real)

    override fun toString(): String = (
            if (imaginary < 0)
                StringBuilder(16 + 3 + 16 + 1)
                    .append(real).append(" - ").append(imaginary.absoluteValue).append('i')
            else
                StringBuilder(16 + 3 + 16 + 1)
                    .append(real).append(" + ").append(imaginary).append('i')
            ).toString()

    inline fun isApproximately(other: ComplexF, epsilon: Float = 0.00001f): Boolean =
        real.isApproximately(other.real, epsilon) &&
        imaginary.isApproximately(other.imaginary, epsilon)

    fun pow(x: ComplexF): ComplexF {
        if (x == ZERO) {
            return ONE
        }
        if (this == ZERO) {
            return ZERO
        }
        val r: Float = real
        val i: Float = imaginary
        val xR: Float = x.real
        val xI: Float = x.imaginary
        val mag: Float = absoluteValue
        val phase: Float = atan2(i, r)
        val rho: Float = xR * phase + xI * ln(mag)
        val t = mag.pow(xR) * E.toFloat().pow(-xI * phase)

        return ComplexF(t * cos(rho), t * sin(rho))
    }

    fun pow(x: Float): ComplexF {
        if (x == 0f) {
            return ONE
        }
        if (this == ZERO) {
            return ZERO
        }
        val r: Float = real
        val i: Float = imaginary
        val mag: Float = sqrt(r * r + i * i)
        val phase: Float = atan2(i, r)
        val rho: Float = x * phase
        val t = mag.pow(x)

        return ComplexF(t * cos(rho), t * sin(rho))
    }

    fun pow(n: Int): ComplexF {
        if (n == 0) {
            return ONE
        }
        if (this == ZERO) {
            return ZERO
        }
        val r: Float = real
        val i: Float = imaginary
        val mag: Float = absoluteValue
        val phase: Float = atan2(i, r)
        val rho: Float = n * phase
        val t = mag.pow(n)

        return ComplexF(t * cos(rho), t * sin(rho))
    }

    inline operator fun component1(): Float = real

    inline operator fun component2(): Float = imaginary

    inline operator fun unaryPlus() = this

    inline operator fun unaryMinus() = ComplexF(-real, -imaginary)

    inline operator fun plus(other: ComplexF) =
        ComplexF(real + other.real, imaginary + other.imaginary)

    inline operator fun plus(other: Float) = ComplexF(real + other, imaginary)

    inline operator fun minus(other: ComplexF) =
        ComplexF(real - other.real, imaginary - other.imaginary)

    inline operator fun minus(other: Float) = ComplexF(real - other, imaginary)

    inline operator fun times(other: ComplexF): ComplexF {
        val r0 = real
        val i0 = imaginary
        val r1 = other.real
        val i1 = other.imaginary

        return ComplexF(r0 * r1 - i0 * i1, i0 * r1 + r0 * i1)
    }

    inline operator fun times(other: Float) = ComplexF(real * other, imaginary * other)

    operator fun div(other: ComplexF): ComplexF {
        val r0 = real
        val i0 = imaginary
        val r1 = other.real
        val i1 = other.imaginary

        return if (i1.absoluteValue < r1.absoluteValue) {
            val i1OverR1 = i1 / r1
            val divisor = r1 + i1 * i1OverR1

            ComplexF((r0 + i0 * i1OverR1) / divisor, (i0 - r0 * i1OverR1) / divisor)
        } else {
            val r1OverI1 = r1 / i1
            val divisor = i1 + r1 * r1OverI1

            ComplexF((i0 + r0 * r1OverI1) / divisor, (i0 * r1OverI1 - r0) / divisor)
        }
    }

    inline operator fun div(other: Float) = ComplexF(real / other, imaginary / other)

    companion object {
        const val SIZE_BYTES: Int = 8
        const val SIZE_BITS: Int = 64

        /** Value of 0 **/
        inline val ZERO
            @JvmStatic get() = ComplexF(0f, 0f)

        /** Value of 1 **/
        inline val ONE
            @JvmStatic get() = ComplexF(1f, 0f)

        /** Value of i **/
        inline val IMAGINARY_ONE
            @JvmStatic get() = ComplexF(0f, 1f)

        @JvmStatic
        inline fun fromPolar(magnitude: Float, phase: Float) =
            ComplexF(magnitude * cos(phase), magnitude * sin(phase))

        @JvmStatic
        inline fun abs(value: ComplexF) = value.absoluteValue

        @JvmStatic
        inline fun conjugate(value: ComplexF) = ComplexF(value.real, -value.imaginary)

        @JvmStatic
        fun exp(value: ComplexF): ComplexF {
            val rExp: Float = exp(value.real)
            val i: Float = value.imaginary

            return ComplexF(rExp * cos(i), rExp * sin(i))
        }

        @JvmStatic
        fun cos(value: ComplexF): ComplexF {
            val r: Float = value.real
            val iExp: Float = exp(value.imaginary)
            val iExpReciprocal: Float = 1f / iExp
            val sinh: Float = (iExp - iExpReciprocal) * 0.5f
            val cosh: Float = (iExp + iExpReciprocal) * 0.5f

            return ComplexF(cos(r) * cosh, -sin(r) * sinh)
        }

        @JvmStatic
        fun sin(value: ComplexF): ComplexF {
            val r: Float = value.real
            val iExp: Float = exp(value.imaginary)
            val iExpReciprocal: Float = 1f / iExp
            val sinh: Float = (iExp - iExpReciprocal) * 0.5f
            val cosh: Float = (iExp + iExpReciprocal) * 0.5f

            return ComplexF(sin(r) * cosh, cos(r) * sinh)
        }

        @JvmStatic
        fun tan(value: ComplexF): ComplexF {
            val r: Float = value.real
            val iExp: Float = exp(value.imaginary)
            val iExpReciprocal: Float = 1f / iExp
            val sinh: Float = (iExp - iExpReciprocal) * 0.5f
            val cosh: Float = (iExp + iExpReciprocal) * 0.5f
            val cosR: Float = cos(r)
            val sinR: Float = sin(r)
            val sin = ComplexF(sinR * cosh, cosR * sinh)
            val cos = ComplexF(cosR * cosh, -sinR * sinh)

            return sin / cos
        }

        @JvmStatic
        inline fun cosh(value: ComplexF): ComplexF = cos(ComplexF(-value.imaginary, value.real))

        @JvmStatic
        inline fun sinh(value: ComplexF): ComplexF {
            val sin: ComplexF = sin(ComplexF(-value.imaginary, value.real))
            return ComplexF(sin.imaginary, -sin.real)
        }

        @JvmStatic
        inline fun tanh(value: ComplexF): ComplexF {
            val tan: ComplexF = tan(ComplexF(-value.imaginary, value.real))
            return ComplexF(tan.imaginary, -tan.real)
        }

        @JvmStatic
        fun sqrt(value: ComplexF): ComplexF {
            if (value == ZERO) {
                return ZERO
            }
            val r: Float = value.real
            val i: Float = value.imaginary

            if (i == 0f) {
                return ComplexF(sqrt(r), 0f)
            }
            val t: Float = sqrt((r.absoluteValue + value.absoluteValue) / 2)

            return if (r >= 0) {
                ComplexF(t, i / (2 * t))
            } else {
                ComplexF(i.absoluteValue / (2 * t), 1f.withSign(i) * t)
            }
        }
    }
}