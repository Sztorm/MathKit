package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class ComplexFTests {
    @ParameterizedTest
    @MethodSource("complices")
    fun basicPropertiesAreValid(complex: Wrapper<ComplexF>) {
        val value: ComplexF = complex.value
        val (real, imaginary) = value

        assertEquals(value.real.toRawBits(), real.toRawBits())
        assertEquals(value.imaginary.toRawBits(), imaginary.toRawBits())
    }

    @ParameterizedTest
    @MethodSource("squaredMagnitudeArgs")
    fun squaredMagnitudeReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Float) =
        assertApproximation(expected, complex.value.squaredMagnitude, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun magnitudeReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Float) =
        assertApproximation(expected, complex.value.magnitude, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun absoluteValueReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Float) =
        assertApproximation(expected, complex.value.absoluteValue, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("phaseArgs")
    fun phaseReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Float) =
        assertApproximation(expected, complex.value.phase, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("normalizedArgs")
    fun normalizedReturnsCorrectValue(vector: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, vector.value.normalized)

    @ParameterizedTest
    @MethodSource("toVector2FArgs")
    fun toVector2FReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, complex.value.toVector2F())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: String) =
        assertEquals(expected, complex.value.toString())

    @ParameterizedTest
    @MethodSource("isApproximatelyArgs")
    fun isApproximatelyReturnsCorrectValue(
        complex: Wrapper<ComplexF>, other: Wrapper<ComplexF>, epsilon: Float, expected: Boolean
    ) = assertEquals(expected, complex.value.isApproximately(other.value, epsilon))

    @ParameterizedTest
    @MethodSource("powComplexArgs")
    fun powComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value.pow(b.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("powFloatArgs")
    fun powFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value.pow(b), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("powIntArgs")
    fun powIntReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Int, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value.pow(b), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("fromPolarArgs")
    fun fromPolarReturnsCorrectValue(magnitude: Float, phase: Float, expected: Wrapper<ComplexF>) =
        assertApproximation(
            expected.value, ComplexF.fromPolar(magnitude, phase), tolerance = 0.001f
        )

    @ParameterizedTest
    @MethodSource("fromAngleArgs")
    fun fromAngleReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.fromAngle(angle.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun absReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Float) =
        assertApproximation(expected, ComplexF.abs(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("conjugateArgs")
    fun conjugateReturnsCorrectValue(
        complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, ComplexF.conjugate(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("expArgs")
    fun expReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.exp(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("cosArgs")
    fun cosReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.cos(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("sinArgs")
    fun sinReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.sin(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("tanArgs")
    fun tanReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.tan(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("coshArgs")
    fun coshReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.cosh(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("sinhArgs")
    fun sinhReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.sinh(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("tanhArgs")
    fun tanhReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.tanh(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("sqrtArgs")
    fun sqrtReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.sqrt(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("plusComplexArgs")
    fun unaryPlusOperatorReturnsCorrectValue(
        complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(equalsBitwise(expected.value, +complex.value))

    @ParameterizedTest
    @MethodSource("minusComplexArgs")
    fun unaryMinusOperatorReturnsCorrectValue(
        complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertTrue(equalsBitwise(expected.value, -complex.value))

    @ParameterizedTest
    @MethodSource("complexPlusComplexArgs")
    fun complexPlusComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value + b.value, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("complexPlusFloatArgs")
    fun complexPlusFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value + b, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("floatPlusComplexArgs")
    fun floatPlusComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a + b.value, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("complexMinusComplexArgs")
    fun complexMinusComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value - b.value, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("complexMinusFloatArgs")
    fun complexMinusFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value - b, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("floatMinusComplexArgs")
    fun floatMinusComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a - b.value, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("complexTimesComplexArgs")
    fun complexTimesComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value * b.value, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("complexTimesFloatArgs")
    fun complexTimesFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value * b, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("floatTimesComplexArgs")
    fun floatTimesComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a * b.value, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("complexDivComplexArgs")
    fun complexDivComplexReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value / b.value, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("complexDivFloatArgs")
    fun complexDivFloatReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value / b, tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("floatDivComplexArgs")
    fun floatDivComplexReturnsCorrectValue(
        a: Float, b: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a / b.value, tolerance = 0.001f)

    companion object {
        /** Compares complices bitwise. Useful when comparing NaNs. **/
        @JvmStatic
        fun equalsBitwise(a: ComplexF, b: ComplexF) =
            a.real.toRawBits() == b.real.toRawBits() &&
                    a.imaginary.toRawBits() == b.imaginary.toRawBits()

        @JvmStatic
        fun complices(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(2f, 4f))),
            Arguments.of(Wrapper(ComplexF(2f, Float.NaN))),
            Arguments.of(Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f))),
            Arguments.of(Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY))),
        )

        @JvmStatic
        fun squaredMagnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(3f, 4f)), 25f),
            Arguments.of(Wrapper(ComplexF(3f, -4f)), 25f),
        )

        @JvmStatic
        fun magnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(3f, 4f)), 5f),
            Arguments.of(Wrapper(ComplexF(3f, -4f)), 5f),
        )

        @JvmStatic
        fun phaseArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(ComplexF(3f, 4f)), 0.9273f),
            Arguments.of(Wrapper(ComplexF(0f, 2f)), 1.5708f),
            Arguments.of(Wrapper(ComplexF(0f, 0f)), 0f),
        )

        @JvmStatic
        fun normalizedArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(0.6f, 0.8f))
            ),
            Arguments.of(
                Wrapper(ComplexF(1f, -2f)),
                Wrapper(ComplexF(0.4472136f, -0.8944272f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0.000001f, 0.000001f)),
                Wrapper(ComplexF.ZERO)
            ),
        )

        @JvmStatic
        fun toVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, -4f)), Wrapper(Vector2F(3f, -4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(-Float.MAX_VALUE, Float.MAX_VALUE)),
                Wrapper(Vector2F(-Float.MAX_VALUE, Float.MAX_VALUE))
            ),
            Arguments.of(Wrapper(ComplexF.ZERO), Wrapper(Vector2F.ZERO)),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                "${2f} + ${4f}i"
            ),
            Arguments.of(
                Wrapper(ComplexF(2f, -4f)),
                "${2f} - ${4f}i"
            ),
            Arguments.of(
                Wrapper(ComplexF(-2f, 0f)),
                "${-2f}"
            ),
        )

        @JvmStatic
        fun isApproximatelyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(3f, 4f)),
                0f,
                true
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(3.001f, 3.999f)),
                0.01f,
                true
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(3.000001f, 3.999999f)),
                0.00001f,
                true
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(3.04f, 3.95f)),
                0.01f,
                false
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(3.00004f, 3.99995f)),
                0.00001f,
                false
            ),
        )

        @JvmStatic
        fun powComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(-2f, 3f)),
                Wrapper(ComplexF(-1.3f, 0.5f)),
                Wrapper(ComplexF(-0.03592f, -0.05314f))
            ),
            Arguments.of(
                Wrapper(ComplexF(7f, -1f)),
                Wrapper(ComplexF(-0.1f, 0f)),
                Wrapper(ComplexF(0.82226f, 0.01167f))
            ),
            Arguments.of(
                Wrapper(ComplexF(-7f, -1f)),
                Wrapper(ComplexF(0f, 2f)),
                Wrapper(ComplexF(-289.329f, -280.795f))
            ),
        )

        @JvmStatic
        fun powFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0f)), 0f,
                Wrapper(ComplexF(1f, 0f))
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, -1f)), -0.1f,
                Wrapper(ComplexF(0.89079f, 0.02867f))
            ),
        )

        @JvmStatic
        fun powIntArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(-2f, 3f)), 2,
                Wrapper(ComplexF(-5f, -12f))
            ),
            Arguments.of(
                Wrapper(ComplexF(7f, -1f)), 0,
                Wrapper(ComplexF(1f, 0f))
            ),
        )

        @JvmStatic
        fun fromPolarArgs(): List<Arguments> = listOf(
            Arguments.of(
                5f, 0.9273f,
                Wrapper(ComplexF(3f, 4f))
            ),
            Arguments.of(
                5f, -0.9273f,
                Wrapper(ComplexF(3f, -4f))
            ),
        )

        @JvmStatic
        fun fromAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF.fromDegrees(45f)),
                Wrapper(ComplexF(0.7071068f, 0.7071068f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(-30f)),
                Wrapper(ComplexF(0.8660254f, -0.5f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(-390f)),
                Wrapper(ComplexF(0.8660254f, -0.5f))
            ),
        )

        @JvmStatic
        fun conjugateArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(3f, -4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, -4f)),
                Wrapper(ComplexF(3f, 4f))
            ),
        )

        @JvmStatic
        fun expArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(6.4845f, 3.5425f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(-0.65364f, 0.7568f))
            ),
        )

        @JvmStatic
        fun cosArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(-0.46926f, -0.47383f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(27.3082f, 0f))
            ),
        )

        @JvmStatic
        fun sinArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(1.02535f, -0.21685f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, -27.2899f))
            ),
        )

        @JvmStatic
        fun tanArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(-0.85088f, 1.32129f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, -0.99933f))
            ),
        )

        @JvmStatic
        fun coshArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(3.30164f, 1.73881f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(-0.65364f, 0f))
            ),
        )

        @JvmStatic
        fun sinhArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(3.18287f, 1.80369f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, 0.7568f))
            ),
        )

        @JvmStatic
        fun tanhArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(0.97994f, 0.03022f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0f, -1.15782f))
            ),
        )

        @JvmStatic
        fun sqrtArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(1.42505f, 0.17543f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(1.41421f, -1.41421f))
            ),
        )

        @JvmStatic
        fun plusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(2f, 4f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f)),
                Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY)),
                Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY)),
            ),
        )

        @JvmStatic
        fun minusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(-2f, -4f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(Float.POSITIVE_INFINITY, -1f)),
                Wrapper(ComplexF(Float.NEGATIVE_INFINITY, 1f)),
            ),
            Arguments.of(
                Wrapper(ComplexF(-1f, Float.NEGATIVE_INFINITY)),
                Wrapper(ComplexF(1f, Float.POSITIVE_INFINITY)),
            ),
        )

        @JvmStatic
        fun complexPlusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(8f, -4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(-1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun complexPlusFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(8f, 4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(-1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun floatPlusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(4f, 6f)),
                Wrapper(ComplexF(6f, 6f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(-1.2f, 0f))
            ),
        )

        @JvmStatic
        fun complexMinusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(-4f, 12f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun complexMinusFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(-4f, 4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(1.2f, -2.3f))
            ),
        )

        @JvmStatic
        fun floatMinusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(4f, 6f)),
                Wrapper(ComplexF(-2f, -6f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(1.2f, 0f))
            ),
        )

        @JvmStatic
        fun complexTimesComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(44f, 8f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 2.76f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)),
                Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, 3.36f))
            ),
        )

        @JvmStatic
        fun complexTimesFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(12f, 24f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(0f, 2.76f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)), -0.525f,
                Wrapper(ComplexF(-2.205f, 0f))
            ),
        )

        @JvmStatic
        fun floatTimesComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(12f, -16f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 0f))
            ),
            Arguments.of(
                4.2f, Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, 3.36f))
            ),
        )

        @JvmStatic
        fun complexDivComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(-0.2f, 0.4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)),
                Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 1.91667f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)),
                Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, -5.25f))
            ),
        )

        @JvmStatic
        fun complexDivFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)), 6f,
                Wrapper(ComplexF(0.33333f, 0.66667f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -2.3f)), -1.2f,
                Wrapper(ComplexF(0f, 1.91667f))
            ),
            Arguments.of(
                Wrapper(ComplexF(4.2f, 0f)), -0.525f,
                Wrapper(ComplexF(-8f, 0f))
            ),
        )

        @JvmStatic
        fun floatDivComplexArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f, Wrapper(ComplexF(6f, -8f)),
                Wrapper(ComplexF(0.12f, 0.16f))
            ),
            Arguments.of(
                0f, Wrapper(ComplexF(-1.2f, 0f)),
                Wrapper(ComplexF(0f, 0f))
            ),
            Arguments.of(
                4.2f, Wrapper(ComplexF(0f, 0.8f)),
                Wrapper(ComplexF(0f, -5.25f))
            ),
        )
    }
}