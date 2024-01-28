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
    @MethodSource("phaseAngleArgs")
    fun phaseAngleReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<AngleF>) =
        assertApproximation(
            expected.value, complex.value.phaseAngle, tolerance = AngleF.fromRadians(0.001f)
        )

    @ParameterizedTest
    @MethodSource("conjugateArgs")
    fun conjugateReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, complex.value.conjugate)

    @ParameterizedTest
    @MethodSource("normalizedArgs")
    fun normalizedReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, complex.value.normalized)

    @ParameterizedTest
    @MethodSource("normalizedOrElseArgs")
    fun normalizedOrElseReturnsCorrectValue(
        complex: Wrapper<ComplexF>, defaultValue: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, complex.value.normalizedOrElse(defaultValue.value))

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
    @MethodSource("dotArgs")
    fun dotReturnsCorrectValue(a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, expected: Float) =
        assertApproximation(expected, a.value dot b.value)

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
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        complex: Wrapper<ComplexF>, real: Float, imaginary: Float, expected: Wrapper<ComplexF>
    ) = assertEquals(expected.value, complex.value.copy(real, imaginary))

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
    @MethodSource("companionConjugateArgs")
    fun companionConjugateReturnsCorrectValue(
        complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, ComplexF.conjugate(complex.value))

    @ParameterizedTest
    @MethodSource("expArgs")
    fun expReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.exp(complex.value), tolerance = 0.001f)

    @ParameterizedTest
    @MethodSource("lnArgs")
    fun lnReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.ln(complex.value))

    @ParameterizedTest
    @MethodSource("logArgs")
    fun logReturnsCorrectValue(
        complex: Wrapper<ComplexF>, base: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, ComplexF.log(complex.value, base))

    @ParameterizedTest
    @MethodSource("log10Args")
    fun log10ReturnsCorrectValue(complex: Wrapper<ComplexF>, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, ComplexF.log10(complex.value))

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

    @Suppress("SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("nlerpArgs")
    fun nlerpReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, t: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, ComplexF.nlerp(a.value, b.value, t))

    @Suppress("SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("slerpArgs")
    fun slerpReturnsCorrectValue(
        a: Wrapper<ComplexF>, b: Wrapper<ComplexF>, t: Float, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, ComplexF.slerp(a.value, b.value, t))

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
        fun phaseAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)), Wrapper(AngleF.fromRadians(0.9273f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, 2f)), Wrapper(AngleF.fromRadians(1.5708f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, 0f)), Wrapper(AngleF.fromRadians(0f))
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
        fun normalizedOrElseArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(1f, 0f)),
                Wrapper(ComplexF(0.6f, 0.8f))
            ),
            Arguments.of(
                Wrapper(ComplexF(1f, -2f)),
                Wrapper(ComplexF(1f, 0f)),
                Wrapper(ComplexF(0.4472136f, -0.8944272f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0.000001f, 0.000001f)),
                Wrapper(ComplexF(1f, 0f)),
                Wrapper(ComplexF(1f, 0f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0.000001f, 0.000001f)),
                Wrapper(ComplexF(0f, 1f)),
                Wrapper(ComplexF(0f, 1f))
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
        fun dotArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF(0f, 4f)),
                16f
            ),
            Arguments.of(
                Wrapper(ComplexF(1f, -2f)),
                Wrapper(ComplexF(-4f, 0.4f)),
                -4.8f
            ),
            Arguments.of(
                Wrapper(ComplexF(3f, 4f)),
                Wrapper(ComplexF.ZERO),
                0f
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
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                2f,
                4f,
                Wrapper(ComplexF(2f, 4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                1f,
                4f,
                Wrapper(ComplexF(1f, 4f))
            ),
            Arguments.of(
                Wrapper(ComplexF(2f, 4f)),
                6f,
                -2f,
                Wrapper(ComplexF(6f, -2f))
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
        fun companionConjugateArgs(): List<Arguments> = conjugateArgs()

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
        fun lnArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(0.7234595f, 0.2449787f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(1.3862944f, -1.5707963f))
            ),
        )

        @JvmStatic
        fun logArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)), 3,
                Wrapper(ComplexF(0.658521f, 0.222989f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)), 1.4f,
                Wrapper(ComplexF(4.12009f, -4.66843f))
            ),
        )

        @JvmStatic
        fun log10Args(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(ComplexF(2f, 0.5f)),
                Wrapper(ComplexF(0.314194f, 0.106393f))
            ),
            Arguments.of(
                Wrapper(ComplexF(0f, -4f)),
                Wrapper(ComplexF(0.6020599f, -0.6821882f))
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

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun nlerpArgs(): List<Arguments> {
            fun createArgumentList(
                aDegreesArray: FloatArray,
                bDegreesArray: FloatArray,
                tArray: FloatArray,
                expectedDegreesArray: FloatArray
            ): List<Arguments> {
                require(aDegreesArray.size == bDegreesArray.size)
                require(aDegreesArray.size == tArray.size)
                require(aDegreesArray.size == expectedDegreesArray.size)

                return Array<Arguments>(aDegreesArray.size) { i ->
                    Arguments.of(
                        Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(aDegreesArray[i]))),
                        Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(bDegreesArray[i]))),
                        tArray[i],
                        Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(expectedDegreesArray[i])))
                    )
                }.asList()
            }

            fun swapped(args: Arguments) = args.get().let { argArray ->
                Arguments.of(
                    argArray[1],
                    argArray[0],
                    1f - argArray[2] as Float,
                    argArray[3]
                )
            }

            val argsT0 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(  0f,    0f),
                    floatArrayOf(0.5f, -0.5f),
                    floatArrayOf(  0f,    0f),
                    floatArrayOf(  0f,    0f)
                ),
                createArgumentList(
                    floatArrayOf( 0f, 20f, 40f, 60f,  80f, 100f, 120f, 140f, 160f),
                    floatArrayOf(20f, 40f, 60f, 80f, 100f, 120f, 140f, 160f, 180f),
                    floatArrayOf( 0f,  0f,  0f,  0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf( 0f, 20f, 40f, 60f,  80f, 100f, 120f, 140f, 160f)
                ),
                createArgumentList(
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f),
                    floatArrayOf(  0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(  0f,   0f,   0f,   0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf(   0f,    0f,    0f,    0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f),
                    floatArrayOf(40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f),
                    floatArrayOf( 0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf( 0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(  0f,   0f,    0f,    0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  60f, 120f, 180f, 240f, 300f),
                    floatArrayOf(60f, 120f, 180f, 240f, 300f, 360f),
                    floatArrayOf( 0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf( 0f,  60f, 120f, 180f, 240f, 300f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(  0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f),
                    floatArrayOf(90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f),
                    floatArrayOf( 0f,   0f,   0f,   0f,   0f,    0f,    0f,    0f),
                    floatArrayOf( 0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f),
                    floatArrayOf(  0f,   0f,   0f,    0f,    0f,    0f),
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(     0f,       0f,        0f,        0f),
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(      0f,        0f,         0f,         0f),
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(      0f,       0f,        0f,        0f),
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(     0f,        0f,         0f,         0f),
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f)
                ),
                // @formatter:on
            ).flatten()
            val argsT0Swapped = argsT0.map(::swapped)
            val argsT25 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(    0f,      0f),
                    floatArrayOf(  0.5f,   -0.5f),
                    floatArrayOf( 0.25f,   0.25f),
                    floatArrayOf(0.125f, -0.125f)
                ),
                createArgumentList(
                    floatArrayOf(     0f,      20f,      40f,      60f,      80f,      100f),
                    floatArrayOf(    20f,      40f,      60f,      80f,     100f,      120f),
                    floatArrayOf(  0.25f,    0.25f,    0.25f,    0.25f,    0.25f,     0.25f),
                    floatArrayOf(4.9616f, 24.9616f, 44.9616f, 64.9616f, 84.9616f, 104.9616f)
                ),
                createArgumentList(
                    floatArrayOf(     120f,      140f,      160f,      180f,      200f,      220f),
                    floatArrayOf(     140f,      160f,      180f,      200f,      220f,      240f),
                    floatArrayOf(    0.25f,     0.25f,     0.25f,     0.25f,     0.25f,     0.25f),
                    floatArrayOf(124.9616f, 144.9616f, 164.9616f, 184.9616f, 204.9616f, 224.9616f)
                ),
                createArgumentList(
                    floatArrayOf(     240f,      260f,      280f,      300f,      320f,      340f),
                    floatArrayOf(     260f,      280f,      300f,      320f,      340f,      360f),
                    floatArrayOf(    0.25f,     0.25f,     0.25f,     0.25f,     0.25f,     0.25f),
                    floatArrayOf(244.9616f, 264.9616f, 284.9616f, 304.9616f, 324.9616f, 344.9616f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,      -20f,      -40f,      -60f,      -80f,      -100f),
                    floatArrayOf(    -20f,      -40f,      -60f,      -80f,     -100f,      -120f),
                    floatArrayOf(   0.25f,     0.25f,     0.25f,     0.25f,     0.25f,      0.25f),
                    floatArrayOf(-4.9616f, -24.9616f, -44.9616f, -64.9616f, -84.9616f, -104.9616f)
                ),
                createArgumentList(
                    floatArrayOf(     -120f,      -140f,      -160f,      -180f,      -200f,      -220f),
                    floatArrayOf(     -140f,      -160f,      -180f,      -200f,      -220f,      -240f),
                    floatArrayOf(     0.25f,      0.25f,      0.25f,      0.25f,      0.25f,      0.25f),
                    floatArrayOf(-124.9616f, -144.9616f, -164.9616f, -184.9616f, -204.9616f, -224.9616f)
                ),
                createArgumentList(
                    floatArrayOf(     -240f,      -260f,      -280f,      -300f,      -320f,      -340f),
                    floatArrayOf(     -260f,      -280f,      -300f,      -320f,      -340f,      -360f),
                    floatArrayOf(     0.25f,      0.25f,      0.25f,      0.25f,      0.25f,      0.25f),
                    floatArrayOf(-244.9616f, -264.9616f, -284.9616f, -304.9616f, -324.9616f, -344.9616f)
                ),
                createArgumentList(
                    floatArrayOf(     0f,      40f,      80f,      120f,      160f),
                    floatArrayOf(    40f,      80f,     120f,      160f,      200f),
                    floatArrayOf(  0.25f,    0.25f,    0.25f,     0.25f,     0.25f),
                    floatArrayOf(9.6858f, 49.6858f, 89.6858f, 129.6858f, 169.6858f)
                ),
                createArgumentList(
                    floatArrayOf(     200f,      240f,      280f,      320f),
                    floatArrayOf(     240f,      280f,      320f,      360f),
                    floatArrayOf(    0.25f,     0.25f,     0.25f,     0.25f),
                    floatArrayOf(209.6858f, 249.6858f, 289.6858f, 329.6858f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,      -40f,      -80f,      -120f,      -160f),
                    floatArrayOf(    -40f,      -80f,     -120f,      -160f,      -200f),
                    floatArrayOf(   0.25f,     0.25f,     0.25f,      0.25f,      0.25f),
                    floatArrayOf(-9.6858f, -49.6858f, -89.6858f, -129.6858f, -169.6858f)
                ),
                createArgumentList(
                    floatArrayOf(     -200f,      -240f,      -280f,      -320f),
                    floatArrayOf(     -240f,      -280f,      -320f,      -360f),
                    floatArrayOf(     0.25f,      0.25f,      0.25f,      0.25f),
                    floatArrayOf(-209.6858f, -249.6858f, -289.6858f, -329.6858f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,      60f,      120f,      180f,      240f,      300f),
                    floatArrayOf(     60f,     120f,      180f,      240f,      300f,      360f),
                    floatArrayOf(   0.25f,    0.25f,     0.25f,     0.25f,     0.25f,     0.25f),
                    floatArrayOf(13.8978f, 73.8978f, 133.8978f, 193.8978f, 253.8978f, 313.8978f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,      -60f,      -120f,      -180f,      -240f,      -300f),
                    floatArrayOf(     -60f,     -120f,      -180f,      -240f,      -300f,      -360f),
                    floatArrayOf(    0.25f,     0.25f,      0.25f,      0.25f,      0.25f,      0.25f),
                    floatArrayOf(-13.8978f, -73.8978f, -133.8978f, -193.8978f, -253.8978f, -313.8978f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,       90f,      180f,      270f),
                    floatArrayOf(     90f,      180f,      270f,      360f),
                    floatArrayOf(   0.25f,     0.25f,     0.25f,     0.25f),
                    floatArrayOf(18.4349f, 108.4349f, 198.4349f, 288.4349f)
                ),
                createArgumentList(
                    floatArrayOf(      -0f,       -90f,     -180f,       -270f),
                    floatArrayOf(     -90f,      -180f,     -270f,       -360f),
                    floatArrayOf(    0.25f,      0.25f,     0.25f,       0.25f),
                    floatArrayOf(-18.4349f, -108.4349f, -198.4349f, -288.4349f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,      120f,      240f,        0f,      -120f,      -240f),
                    floatArrayOf(    120f,      240f,      360f,     -120f,      -240f,      -360f),
                    floatArrayOf(   0.25f,     0.25f,     0.25f,     0.25f,      0.25f,      0.25f),
                    floatArrayOf(19.1066f, 139.1066f, 259.1066f, -19.1066f, -139.1066f, -259.1066f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(  0.25f,    0.25f,     0.25f,     0.25f),
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(   0.25f,     0.25f,      0.25f,      0.25f),
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(   0.25f,    0.25f,     0.25f,     0.25f),
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(  0.25f,     0.25f,      0.25f,      0.25f),
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f)
                ),
                // @formatter:on
            ).flatten()
            val argsT25Swapped = argsT25.map(::swapped)
            val argsT50 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(   0f,     0f),
                    floatArrayOf( 0.5f,  -0.5f),
                    floatArrayOf( 0.5f,   0.5f),
                    floatArrayOf(0.25f, -0.25f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  20f,  40f,  60f,  80f, 100f, 120f, 140f, 160f),
                    floatArrayOf( 20f,  40f,  60f,  80f, 100f, 120f, 140f, 160f, 180f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf( 10f,  30f,  50f,  70f,  90f, 110f, 130f, 150f, 170f)
                ),
                createArgumentList(
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf(190f, 210f, 230f, 250f, 270f, 290f, 310f, 330f, 350f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-10f, -30f, -50f, -70f,  -90f, -110f, -130f, -150f, -170f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf( 0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-190f, -210f, -230f, -250f, -270f, -290f, -310f, -330f, -350f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f),
                    floatArrayOf( 40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf( 20f,  60f, 100f, 140f, 180f, 220f, 260f, 300f, 340f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(0.5f, 0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-20f, -60f, -100f, -140f, -180f, -220f, -260f, -300f, -340f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  60f, 120f, 180f, 240f, 300f),
                    floatArrayOf( 60f, 120f, 180f, 240f, 300f, 360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf( 30f,  90f, 150f, 210f, 270f, 330f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-30f,  -90f, -150f, -210f, -270f,  -330f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f),
                    floatArrayOf( 90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf( 45f, 135f, 225f, 315f, -45f, -135f, -225f, -315f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf( 60f, 180f, 300f,  -60f, -180f, -300f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,       90f,      180f,      270f),
                    floatArrayOf(     180f,      270f,      360f,       90f),
                    floatArrayOf(0.499999f, 0.499999f, 0.499999f, 0.499999f),
                    floatArrayOf(       0f,       90f,      180f,      270f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,      -90f,     -180f,     -270f),
                    floatArrayOf(    -180f,     -270f,     -360f,      -90f),
                    floatArrayOf(0.499999f, 0.499999f, 0.499999f, 0.499999f),
                    floatArrayOf(       0f,      -90f,     -180f,     -270f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,       90f,      180f,      270f),
                    floatArrayOf(     180f,      270f,      360f,       90f),
                    floatArrayOf(0.500001f, 0.500001f, 0.500001f, 0.500001f),
                    floatArrayOf(     180f,      270f,      360f,       90f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,      -90f,     -180f,     -270f),
                    floatArrayOf(    -180f,     -270f,     -360f,      -90f),
                    floatArrayOf(0.500001f, 0.500001f, 0.500001f, 0.500001f),
                    floatArrayOf(    -180f,     -270f,     -360f,      -90f)
                ),
                // @formatter:on
            ).flatten()
            val argsT50Swapped = argsT50.map(::swapped)
            val argsT75 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(    0f,      0f),
                    floatArrayOf(  0.5f,   -0.5f),
                    floatArrayOf( 0.75f,   0.75f),
                    floatArrayOf(0.375f, -0.375f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,      20f,      40f,      60f,      80f,      100f),
                    floatArrayOf(     20f,      40f,      60f,      80f,     100f,      120f),
                    floatArrayOf(   0.75f,    0.75f,    0.75f,    0.75f,    0.75f,     0.75f),
                    floatArrayOf(15.0383f, 35.0383f, 55.0383f, 75.0383f, 95.0383f, 115.0383f)
                ),
                createArgumentList(
                    floatArrayOf(     120f,      140f,      160f,      180f,      200f,      220f),
                    floatArrayOf(     140f,      160f,      180f,      200f,      220f,      240f),
                    floatArrayOf(    0.75f,     0.75f,     0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(135.0383f, 155.0383f, 175.0383f, 195.0383f, 215.0383f, 235.0383f)
                ),
                createArgumentList(
                    floatArrayOf(     240f,      260f,      280f,      300f,      320f,      340f),
                    floatArrayOf(     260f,      280f,      300f,      320f,      340f,      360f),
                    floatArrayOf(    0.75f,     0.75f,     0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(255.0383f, 275.0383f, 295.0383f, 315.0383f, 335.0383f, 355.0383f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,      -20f,      -40f,      -60f,      -80f,      -100f),
                    floatArrayOf(     -20f,      -40f,      -60f,      -80f,     -100f,      -120f),
                    floatArrayOf(    0.75f,     0.75f,     0.75f,     0.75f,     0.75f,      0.75f),
                    floatArrayOf(-15.0383f, -35.0383f, -55.0383f, -75.0383f, -95.0383f, -115.0383f)
                ),
                createArgumentList(
                    floatArrayOf(     -120f,      -140f,      -160f,      -180f,      -200f,      -220f),
                    floatArrayOf(     -140f,      -160f,      -180f,      -200f,      -220f,      -240f),
                    floatArrayOf(     0.75f,      0.75f,      0.75f,      0.75f,      0.75f,      0.75f),
                    floatArrayOf(-135.0383f, -155.0383f, -175.0383f, -195.0383f, -215.0383f, -235.0383f)
                ),
                createArgumentList(
                    floatArrayOf(     -240f,      -260f,      -280f,      -300f,      -320f,      -340f),
                    floatArrayOf(     -260f,      -280f,      -300f,      -320f,      -340f,      -360f),
                    floatArrayOf(     0.75f,      0.75f,      0.75f,      0.75f,      0.75f,      0.75f),
                    floatArrayOf(-255.0383f, -275.0383f, -295.0383f, -315.0383f, -335.0383f, -355.0383f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,      40f,       80f,      120f,      160f),
                    floatArrayOf(     40f,      80f,      120f,      160f,      200f),
                    floatArrayOf(   0.75f,    0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(30.3141f, 70.3141f, 110.3141f, 150.3141f, 190.3141f)
                ),
                createArgumentList(
                    floatArrayOf(     200f,      240f,      280f,      320f),
                    floatArrayOf(     240f,      280f,      320f,      360f),
                    floatArrayOf(    0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(230.3141f, 270.3141f, 310.3141f, 350.3141f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,      -40f,       -80f,      -120f,      -160f),
                    floatArrayOf(     -40f,      -80f,      -120f,      -160f,      -200f),
                    floatArrayOf(    0.75f,     0.75f,      0.75f,      0.75f,      0.75f),
                    floatArrayOf(-30.3141f, -70.3141f, -110.3141f, -150.3141f, -190.3141f)
                ),
                createArgumentList(
                    floatArrayOf(     -200f,      -240f,      -280f,      -320f),
                    floatArrayOf(     -240f,      -280f,      -320f,      -360f),
                    floatArrayOf(     0.75f,      0.75f,      0.75f,      0.75f),
                    floatArrayOf(-230.3141f, -270.3141f, -310.3141f, -350.3141f)
                ),
                createArgumentList(
                    floatArrayOf(      0f,       60f,      120f,      180f,      240f,      300f),
                    floatArrayOf(     60f,      120f,      180f,      240f,      300f,      360f),
                    floatArrayOf(   0.75f,     0.75f,     0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(46.1021f, 106.1021f, 166.1021f, 226.1021f, 286.1021f, 346.1021f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,       -60f,      -120f,      -180f,      -240f,      -300f),
                    floatArrayOf(     -60f,      -120f,      -180f,      -240f,      -300f,      -360f),
                    floatArrayOf(    0.75f,      0.75f,      0.75f,      0.75f,      0.75f,      0.75f),
                    floatArrayOf(-46.1021f, -106.1021f, -166.1021f, -226.1021f, -286.1021f, -346.1021f)
                ),
                createArgumentList(
                    floatArrayOf(     0f,      90f,     180f,     270f),
                    floatArrayOf(    90f,     180f,     270f,     360f),
                    floatArrayOf(  0.75f,    0.75f,    0.75f,    0.75f),
                    floatArrayOf(71.565f, 161.565f, 251.565f, 341.565f)
                ),
                createArgumentList(
                    floatArrayOf(     -0f,      -90f,     -180f,     -270f),
                    floatArrayOf(    -90f,     -180f,     -270f,     -360f),
                    floatArrayOf(   0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(-71.565f, -161.565f, -251.565f, -341.565f)
                ),
                createArgumentList(
                    floatArrayOf(       0f,      120f,      240f,         0f,      -120f,      -240f),
                    floatArrayOf(     120f,      240f,      360f,      -120f,      -240f,      -360f),
                    floatArrayOf(    0.75f,     0.75f,     0.75f,      0.75f,      0.75f,      0.75f),
                    floatArrayOf(100.8933f, 220.8933f, 340.8933f, -100.8933f, -220.8933f, -340.8933f)
                ),
                createArgumentList(
                    floatArrayOf(  0.0001f,  90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(     180f,      270f,      360f,       90f),
                    floatArrayOf(    0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(179.9999f, 269.9999f, 359.9999f,  89.9999f)
                ),
                createArgumentList(
                    floatArrayOf(  -0.0001f,  -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(     -180f,      -270f,      -360f,       -90f),
                    floatArrayOf(     0.75f,      0.75f,      0.75f,      0.75f),
                    floatArrayOf(-179.9999f, -269.9999f, -359.9999f,  -89.9999f)
                ),
                createArgumentList(
                    floatArrayOf(  -0.0001f,  89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(      180f,      270f,      360f,       90f),
                    floatArrayOf(     0.75f,     0.75f,     0.75f,     0.75f),
                    floatArrayOf(-179.9999f, -89.9999f,   0.0001f,  90.0001f)
                ),
                createArgumentList(
                    floatArrayOf( 0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(   0.75f,     0.75f,      0.75f,      0.75f),
                    floatArrayOf(179.9999f, 89.9999f,   -0.0001f,  -90.0001f)
                ),
                // @formatter:on
            ).flatten()
            val argsT75Swapped = argsT75.map(::swapped)
            val argsT100 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(  0f,    0f),
                    floatArrayOf(0.5f, -0.5f),
                    floatArrayOf(  1f,    1f),
                    floatArrayOf(0.5f, -0.5f)
                ),
                createArgumentList(
                    floatArrayOf( 0f, 20f, 40f, 60f,  80f, 100f, 120f, 140f, 160f),
                    floatArrayOf(20f, 40f, 60f, 80f, 100f, 120f, 140f, 160f, 180f),
                    floatArrayOf( 1f,  1f,  1f,  1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(20f, 40f, 60f, 80f, 100f, 120f, 140f, 160f, 180f)
                ),
                createArgumentList(
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f),
                    floatArrayOf(  1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(  1f,   1f,   1f,   1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf(   1f,    1f,    1f,    1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f),
                    floatArrayOf(40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f),
                    floatArrayOf( 1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(  1f,   1f,    1f,    1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  60f, 120f, 180f, 240f, 300f),
                    floatArrayOf(60f, 120f, 180f, 240f, 300f, 360f),
                    floatArrayOf( 1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(60f, 120f, 180f, 240f, 300f, 360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(  1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f),
                    floatArrayOf(90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f),
                    floatArrayOf( 1f,   1f,   1f,   1f,   1f,    1f,    1f,    1f),
                    floatArrayOf(90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f),
                    floatArrayOf(  1f,   1f,   1f,    1f,    1f,    1f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(     1f,       1f,        1f,        1f),
                    floatArrayOf(   180f,     270f,      360f,       90f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(      1f,        1f,         1f,         1f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(      1f,       1f,        1f,        1f),
                    floatArrayOf(    180f,     270f,      360f,       90f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(     1f,        1f,         1f,         1f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f)
                ),
                // @formatter:on
            ).flatten()
            val argsT100Swapped = argsT100.map(::swapped)

            return listOf(
                argsT0,
                argsT0Swapped,
                argsT25,
                argsT25Swapped,
                argsT50,
                argsT50Swapped,
                argsT75,
                argsT75Swapped,
                argsT100,
                argsT100Swapped
            ).flatten()
        }

        @Suppress("SpellCheckingInspection")
        @JvmStatic
        fun slerpArgs(): List<Arguments> {
            fun createArgumentList(
                aDegreesArray: FloatArray,
                bDegreesArray: FloatArray,
                tArray: FloatArray,
                expectedDegreesArray: FloatArray
            ): List<Arguments> {
                require(aDegreesArray.size == bDegreesArray.size)
                require(aDegreesArray.size == tArray.size)
                require(aDegreesArray.size == expectedDegreesArray.size)

                return Array<Arguments>(aDegreesArray.size) { i ->
                    Arguments.of(
                        Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(aDegreesArray[i]))),
                        Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(bDegreesArray[i]))),
                        tArray[i],
                        Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(expectedDegreesArray[i])))
                    )
                }.asList()
            }

            fun swapped(args: Arguments) = args.get().let { argArray ->
                Arguments.of(
                    argArray[1],
                    argArray[0],
                    1f - argArray[2] as Float,
                    argArray[3]
                )
            }

            val argsT0 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(  0f,    0f),
                    floatArrayOf(0.5f, -0.5f),
                    floatArrayOf(  0f,    0f),
                    floatArrayOf(  0f,    0f)
                ),
                createArgumentList(
                    floatArrayOf( 0f, 20f, 40f, 60f,  80f, 100f, 120f, 140f, 160f),
                    floatArrayOf(20f, 40f, 60f, 80f, 100f, 120f, 140f, 160f, 180f),
                    floatArrayOf( 0f,  0f,  0f,  0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf( 0f, 20f, 40f, 60f,  80f, 100f, 120f, 140f, 160f)
                ),
                createArgumentList(
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f),
                    floatArrayOf(  0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(  0f,   0f,   0f,   0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf(   0f,    0f,    0f,    0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f),
                    floatArrayOf(40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f),
                    floatArrayOf( 0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf( 0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(  0f,   0f,    0f,    0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  60f, 120f, 180f, 240f, 300f),
                    floatArrayOf(60f, 120f, 180f, 240f, 300f, 360f),
                    floatArrayOf( 0f,   0f,   0f,   0f,   0f,   0f),
                    floatArrayOf( 0f,  60f, 120f, 180f, 240f, 300f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(  0f,    0f,    0f,    0f,    0f,    0f),
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f),
                    floatArrayOf(90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f),
                    floatArrayOf( 0f,   0f,   0f,   0f,   0f,    0f,    0f,    0f),
                    floatArrayOf( 0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f),
                    floatArrayOf(  0f,   0f,   0f,    0f,    0f,    0f),
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(     0f,       0f,        0f,        0f),
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(      0f,        0f,         0f,         0f),
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(      0f,       0f,        0f,        0f),
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(     0f,        0f,         0f,         0f),
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f)
                ),
                // @formatter:on
            ).flatten()
            val argsT0Swapped = argsT0.map(::swapped)
            val argsT25 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(    0f,      0f),
                    floatArrayOf(  0.5f,   -0.5f),
                    floatArrayOf( 0.25f,   0.25f),
                    floatArrayOf(0.125f, -0.125f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,   20f,   40f,   60f,   80f,  100f,  120f,  140f,  160f),
                    floatArrayOf(  20f,   40f,   60f,   80f,  100f,  120f,  140f,  160f,  180f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf(   5f,   25f,   45f,   65f,   85f,  105f,  125f,  145f,  165f)
                ),
                createArgumentList(
                    floatArrayOf( 180f,  200f,  220f,  240f,  260f,  280f,  300f,  320f,  340f),
                    floatArrayOf( 200f,  220f,  240f,  260f,  280f,  300f,  320f,  340f,  360f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf( 185f,  205f,  225f,  245f,  265f,  285f,  305f,  325f,  345f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  -20f,  -40f,  -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf( -20f,  -40f,  -60f,  -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf(  -5f,  -25f,  -45f,  -65f,  -85f, -105f, -125f, -145f, -165f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf(-185f, -205f, -225f, -245f, -265f, -285f, -305f, -325f, -345f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,   40f,   80f,  120f,  160f,  200f,  240f,  280f,  320f),
                    floatArrayOf(  40f,   80f,  120f,  160f,  200f,  240f,  280f,  320f,  360f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf(  10f,   50f,   90f,  130f,  170f,  210f,  250f,  290f,  330f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf( -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf( -10f,  -50f,  -90f, -130f, -170f, -210f, -250f, -290f, -330f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,   60f,  120f,  180f,  240f,  300f),
                    floatArrayOf(  60f,  120f,  180f,  240f,  300f,  360f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf(  15f,   75f,  135f,  195f,  255f,  315f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf( -60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(0.25f, 0.25f, 0.25f, 0.25f, 0.25f, 0.25f),
                    floatArrayOf( -15f,  -75f, -135f, -195f, -255f, -315f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,    90f,   180f,   270f,    -0f,    -90f,   -180f,   -270f),
                    floatArrayOf(  90f,   180f,   270f,   360f,   -90f,   -180f,   -270f,   -360f),
                    floatArrayOf(0.25f,  0.25f,  0.25f,  0.25f,  0.25f,   0.25f,   0.25f,   0.25f),
                    floatArrayOf(22.5f, 112.5f, 202.5f, 292.5f, -22.5f, -112.5f, -202.5f, -292.5f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  120f,  240f,     0f,  -120f,  -240f),
                    floatArrayOf( 120f,  240f,  360f,  -120f,  -240f,  -360f),
                    floatArrayOf(0.25f, 0.25f, 0.25f,  0.25f,  0.25f,  0.25f),
                    floatArrayOf(  30f,  150f,  270f,   -30f,  -150f,  -270f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(  0.25f,    0.25f,     0.25f,     0.25f),
                    floatArrayOf(    45f,     135f,      225f,      315f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(   0.25f,     0.25f,      0.25f,      0.25f),
                    floatArrayOf(    -45f,     -135f,      -225f,      -315f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(   0.25f,    0.25f,     0.25f,     0.25f),
                    floatArrayOf(    -45f,      45f,      135f,      225f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(  0.25f,     0.25f,      0.25f,      0.25f),
                    floatArrayOf(    45f,      -45f,      -135f,      -225f)
                ),
                // @formatter:on
            ).flatten()
            val argsT25Swapped = argsT25.map(::swapped)
            val argsT50 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(   0f,     0f),
                    floatArrayOf( 0.5f,  -0.5f),
                    floatArrayOf( 0.5f,   0.5f),
                    floatArrayOf(0.25f, -0.25f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  20f,  40f,  60f,  80f, 100f, 120f, 140f, 160f),
                    floatArrayOf( 20f,  40f,  60f,  80f, 100f, 120f, 140f, 160f, 180f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf( 10f,  30f,  50f,  70f,  90f, 110f, 130f, 150f, 170f)
                ),
                createArgumentList(
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf(190f, 210f, 230f, 250f, 270f, 290f, 310f, 330f, 350f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-10f, -30f, -50f, -70f,  -90f, -110f, -130f, -150f, -170f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf( 0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-190f, -210f, -230f, -250f, -270f, -290f, -310f, -330f, -350f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f),
                    floatArrayOf( 40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf( 20f,  60f, 100f, 140f, 180f, 220f, 260f, 300f, 340f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(0.5f, 0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-20f, -60f, -100f, -140f, -180f, -220f, -260f, -300f, -340f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  60f, 120f, 180f, 240f, 300f),
                    floatArrayOf( 60f, 120f, 180f, 240f, 300f, 360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f),
                    floatArrayOf( 30f,  90f, 150f, 210f, 270f, 330f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(0.5f,  0.5f,  0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf(-30f,  -90f, -150f, -210f, -270f,  -330f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f),
                    floatArrayOf( 90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f, 0.5f, 0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf( 45f, 135f, 225f, 315f, -45f, -135f, -225f, -315f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f),
                    floatArrayOf(0.5f, 0.5f, 0.5f,  0.5f,  0.5f,  0.5f),
                    floatArrayOf( 60f, 180f, 300f,  -60f, -180f, -300f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(   0.5f,     0.5f,      0.5f,      0.5f),
                    floatArrayOf(    90f,     180f,      270f,        0f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(    0.5f,      0.5f,       0.5f,       0.5f),
                    floatArrayOf(    -90f,     -180f,      -270f,         0f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(    0.5f,     0.5f,      0.5f,      0.5f),
                    floatArrayOf(    -90f,       0f,       90f,      180f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(   0.5f,      0.5f,       0.5f,       0.5f),
                    floatArrayOf(    90f,        0f,       -90f,      -180f)
                ),
                // @formatter:on
            ).flatten()
            val argsT50Swapped = argsT50.map(::swapped)
            val argsT75 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(    0f,      0f),
                    floatArrayOf(  0.5f,   -0.5f),
                    floatArrayOf( 0.75f,   0.75f),
                    floatArrayOf(0.375f, -0.375f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,   20f,   40f,   60f,   80f,  100f,  120f,  140f,  160f),
                    floatArrayOf(  20f,   40f,   60f,   80f,  100f,  120f,  140f,  160f,  180f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf(  15f,   35f,   55f,   75f,   95f,  115f,  135f,  155f,  175f)
                ),
                createArgumentList(
                    floatArrayOf( 180f,  200f,  220f,  240f,  260f,  280f,  300f,  320f,  340f),
                    floatArrayOf( 200f,  220f,  240f,  260f,  280f,  300f,  320f,  340f,  360f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf( 195f,  215f,  235f,  255f,  275f,  295f,  315f,  335f,  355f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  -20f,  -40f,  -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf( -20f,  -40f,  -60f,  -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf( -15f,  -35f,  -55f,  -75f,  -95f, -115f, -135f, -155f, -175f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf(-195f, -215f, -235f, -255f, -275f, -295f, -315f, -335f, -355f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,   40f,   80f,  120f,  160f,  200f,  240f,  280f,  320f),
                    floatArrayOf(  40f,   80f,  120f,  160f,  200f,  240f,  280f,  320f,  360f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf(  30f,   70f,  110f,  150f,  190f,  230f,  270f,  310f,  350f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf( -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf( -30f,  -70f, -110f, -150f, -190f, -230f, -270f, -310f, -350f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,   60f,  120f,  180f,  240f,  300f),
                    floatArrayOf(  60f,  120f,  180f,  240f,  300f,  360f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf(  45f,  105f,  165f,  225f,  285f,  345f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf( -60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(0.75f, 0.75f, 0.75f, 0.75f, 0.75f, 0.75f),
                    floatArrayOf( -45f, -105f, -165f, -225f, -285f, -345f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,    90f,   180f,   270f,    -0f,    -90f,   -180f,   -270f),
                    floatArrayOf(  90f,   180f,   270f,   360f,   -90f,   -180f,   -270f,   -360f),
                    floatArrayOf(0.75f,  0.75f,  0.75f,  0.75f,  0.75f,   0.75f,    0.75f,  0.75f),
                    floatArrayOf(67.5f, 157.5f, 247.5f, 337.5f, -67.5f, -157.5f, -247.5f, -337.5f)
                ),
                createArgumentList(
                    floatArrayOf(   0f,  120f,  240f,     0f,  -120f,  -240f),
                    floatArrayOf( 120f,  240f,  360f,  -120f,  -240f,  -360f),
                    floatArrayOf(0.75f, 0.75f, 0.75f,  0.75f,  0.75f,  0.75f),
                    floatArrayOf(  90f,  210f,  330f,   -90f,  -210f,  -330f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(  0.75f,    0.75f,     0.75f,     0.75f),
                    floatArrayOf(   135f,     225f,      315f,       45f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(   0.75f,     0.75f,      0.75f,      0.75f),
                    floatArrayOf(   -135f,     -225f,      -315f,       -45f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(   0.75f,    0.75f,     0.75f,     0.75f),
                    floatArrayOf(   -135f,     -45f,       45f,      135f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(  0.75f,     0.75f,      0.75f,      0.75f),
                    floatArrayOf(   135f,       45f,       -45f,      -135f)
                ),
                // @formatter:on
            ).flatten()
            val argsT75Swapped = argsT75.map(::swapped)
            val argsT100 = listOf(
                // @formatter:off
                createArgumentList(
                    floatArrayOf(  0f,    0f),
                    floatArrayOf(0.5f, -0.5f),
                    floatArrayOf(  1f,    1f),
                    floatArrayOf(0.5f, -0.5f)
                ),
                createArgumentList(
                    floatArrayOf( 0f, 20f, 40f, 60f,  80f, 100f, 120f, 140f, 160f),
                    floatArrayOf(20f, 40f, 60f, 80f, 100f, 120f, 140f, 160f, 180f),
                    floatArrayOf( 1f,  1f,  1f,  1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(20f, 40f, 60f, 80f, 100f, 120f, 140f, 160f, 180f)
                ),
                createArgumentList(
                    floatArrayOf(180f, 200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f),
                    floatArrayOf(  1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(200f, 220f, 240f, 260f, 280f, 300f, 320f, 340f, 360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -20f, -40f, -60f,  -80f, -100f, -120f, -140f, -160f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f),
                    floatArrayOf(  1f,   1f,   1f,   1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-20f, -40f, -60f, -80f, -100f, -120f, -140f, -160f, -180f)
                ),
                createArgumentList(
                    floatArrayOf(-180f, -200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f),
                    floatArrayOf(   1f,    1f,    1f,    1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-200f, -220f, -240f, -260f, -280f, -300f, -320f, -340f, -360f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f),
                    floatArrayOf(40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f),
                    floatArrayOf( 1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(40f,  80f, 120f, 160f, 200f, 240f, 280f, 320f, 360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, -40f,  -80f, -120f, -160f, -200f, -240f, -280f, -320f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f),
                    floatArrayOf(  1f,   1f,    1f,    1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-40f, -80f, -120f, -160f, -200f, -240f, -280f, -320f, -360f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  60f, 120f, 180f, 240f, 300f),
                    floatArrayOf(60f, 120f, 180f, 240f, 300f, 360f),
                    floatArrayOf( 1f,   1f,   1f,   1f,   1f,   1f),
                    floatArrayOf(60f, 120f, 180f, 240f, 300f, 360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f,  -60f, -120f, -180f, -240f, -300f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f),
                    floatArrayOf(  1f,    1f,    1f,    1f,    1f,    1f),
                    floatArrayOf(-60f, -120f, -180f, -240f, -300f, -360f)
                ),
                createArgumentList(
                    floatArrayOf( 0f,  90f, 180f, 270f,  -0f,  -90f, -180f, -270f),
                    floatArrayOf(90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f),
                    floatArrayOf( 1f,   1f,   1f,   1f,   1f,    1f,    1f,    1f),
                    floatArrayOf(90f, 180f, 270f, 360f, -90f, -180f, -270f, -360f)
                ),
                createArgumentList(
                    floatArrayOf(  0f, 120f, 240f,    0f, -120f, -240f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f),
                    floatArrayOf(  1f,   1f,   1f,    1f,    1f,    1f),
                    floatArrayOf(120f, 240f, 360f, -120f, -240f, -360f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, 90.0001f, 180.0001f, 270.0001f),
                    floatArrayOf(   180f,     270f,      360f,       90f),
                    floatArrayOf(     1f,       1f,        1f,        1f),
                    floatArrayOf(   180f,     270f,      360f,       90f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, -90.0001f, -180.0001f, -270.0001f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(      1f,        1f,         1f,         1f),
                    floatArrayOf(   -180f,     -270f,      -360f,       -90f)
                ),
                createArgumentList(
                    floatArrayOf(-0.0001f, 89.9999f, 179.9999f, 269.9999f),
                    floatArrayOf(    180f,     270f,      360f,       90f),
                    floatArrayOf(      1f,       1f,        1f,        1f),
                    floatArrayOf(    180f,     270f,      360f,       90f)
                ),
                createArgumentList(
                    floatArrayOf(0.0001f, -89.9999f, -179.9999f, -269.9999f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f),
                    floatArrayOf(     1f,        1f,         1f,         1f),
                    floatArrayOf(  -180f,     -270f,      -360f,       -90f)
                ),
                // @formatter:on
            ).flatten()
            val argsT100Swapped = argsT100.map(::swapped)

            return listOf(
                argsT0,
                argsT0Swapped,
                argsT25,
                argsT25Swapped,
                argsT50,
                argsT50Swapped,
                argsT75,
                argsT75Swapped,
                argsT100,
                argsT100Swapped
            ).flatten()
        }

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