package nonallocmath

import com.sztorm.nonallocmath.*
import org.junit.jupiter.params.*
import org.junit.jupiter.params.provider.*
import kotlin.test.*

class ComplexFTests {

    @ParameterizedTest
    @MethodSource("complices")
    fun complexContentsAreValid(real: Float, imaginary: Float) =
        assertTrue(equalsBitwise(ComplexF(real, imaginary), real, imaginary))

    @ParameterizedTest
    @MethodSource("complices")
    fun basicComplexPropertiesAreValid(real: Float, imaginary: Float) {
        val value = ComplexF(real, imaginary)
        val (r, i) = value

        assertTrue(
            equalsBitwise(value, real, imaginary) &&
                    value.real.toRawBits() == r.toRawBits() &&
                    value.imaginary.toRawBits() == i.toRawBits()
        )
    }

    @ParameterizedTest
    @MethodSource("squaredMagnitudeArgs")
    fun squaredMagnitudeReturnsCorrectValue(value: Pair<Float, Float>, expected: Float) {
        val actual: Float = ComplexF(value.first, value.second).squaredMagnitude

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun magnitudeReturnsCorrectValue(value: Pair<Float, Float>, expected: Float) {
        val actual: Float = ComplexF(value.first, value.second).magnitude

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun absoluteValueReturnsCorrectValue(value: Pair<Float, Float>, expected: Float) {
        val actual: Float = ComplexF(value.first, value.second).absoluteValue

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("phaseArgs")
    fun phaseReturnsCorrectValue(value: Pair<Float, Float>, expected: Float) {
        val actual: Float = ComplexF(value.first, value.second).phase

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("powComplexArgs")
    fun powComplexReturnsCorrectValue(
        a: Pair<Float, Float>, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val actual = ComplexF(a.first, a.second).pow(ComplexF(b.first, b.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("powFloatArgs")
    fun powFloatReturnsCorrectValue(
        a: Pair<Float, Float>, b: Float, exp: Pair<Float, Float>
    ) {
        val actual = ComplexF(a.first, a.second).pow(b)
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("powIntArgs")
    fun powIntReturnsCorrectValue(
        a: Pair<Float, Float>, b: Int, exp: Pair<Float, Float>
    ) {
        val actual = ComplexF(a.first, a.second).pow(b)
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("fromPolarArgs")
    fun fromPolarReturnsCorrectValue(polar: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.fromPolar(polar.first, polar.second)
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun absReturnsCorrectValue(value: Pair<Float, Float>, expected: Float) {
        val actual: Float = ComplexF.abs(ComplexF(value.first, value.second))

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("conjugateArgs")
    fun conjugateReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.conjugate(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("expArgs")
    fun expReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.exp(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("cosArgs")
    fun cosReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.cos(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("sinArgs")
    fun sinReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.sin(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("tanArgs")
    fun tanReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.tan(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("coshArgs")
    fun coshReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.cosh(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("sinhArgs")
    fun sinhReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.sinh(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("tanhArgs")
    fun tanhReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.tanh(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("sqrtArgs")
    fun sqrtReturnsCorrectValue(value: Pair<Float, Float>, exp: Pair<Float, Float>) {
        val actual = ComplexF.sqrt(ComplexF(value.first, value.second))
        val expected = ComplexF(exp.first, exp.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complices")
    fun unaryPlusOperatorReturnsUnchangedValue(real: Float, imaginary: Float) =
        assertTrue(equalsBitwise(+ComplexF(real, imaginary), real, imaginary))

    @ParameterizedTest
    @MethodSource("complices")
    fun unaryMinusOperatorReturnsOppositeValue(real: Float, imaginary: Float) =
        assertTrue(equalsBitwise(-ComplexF(real, imaginary), -real, -imaginary))

    @ParameterizedTest
    @MethodSource("complexPlusComplexArgs")
    fun complexPlusComplexAddsReturnsSum(
        a: Pair<Float, Float>, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) + ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complexPlusFloatArgs")
    fun complexPlusFloatAddsReturnsSum(
        a: Pair<Float, Float>, b: Float, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) + b

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("floatPlusComplexArgs")
    fun floatPlusComplexAddsReturnsSum(
        a: Float, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = a + ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complexMinusComplexArgs")
    fun complexMinusComplexReturnsDifference(
        a: Pair<Float, Float>, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) - ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complexMinusFloatArgs")
    fun complexMinusFloatReturnsDifference(
        a: Pair<Float, Float>, b: Float, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) - b

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("floatMinusComplexArgs")
    fun floatMinusComplexReturnsDifference(
        a: Float, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = a - ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complexTimesComplexArgs")
    fun complexTimesComplexReturnsProduct(
        a: Pair<Float, Float>, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) * ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complexTimesFloatArgs")
    fun complexTimesFloatReturnsProduct(
        a: Pair<Float, Float>, b: Float, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) * b

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("floatTimesComplexArgs")
    fun floatTimesComplexReturnsProduct(
        a: Float, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = a * ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complexDivComplexArgs")
    fun complexDivComplexReturnsQuotient(
        a: Pair<Float, Float>, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) / ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("complexDivFloatArgs")
    fun complexDivFloatReturnsQuotient(
        a: Pair<Float, Float>, b: Float, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = ComplexF(a.first, a.second) / b

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    @ParameterizedTest
    @MethodSource("floatDivComplexArgs")
    fun floatDivComplexReturnsQuotient(
        a: Float, b: Pair<Float, Float>, exp: Pair<Float, Float>
    ) {
        val expected = ComplexF(exp.first, exp.second)
        val actual = a / ComplexF(b.first, b.second)

        assertTrue(actual.isApproximately(expected, epsilon = 0.001f))
    }

    companion object {
        /**
         * Compares complex with real and imaginary parts bitwise. Useful when comparing NaNs.
         */
        @JvmStatic
        fun equalsBitwise(complex: ComplexF, real: Float, imaginary: Float) =
            complex.real.toRawBits() == real.toRawBits() &&
            complex.imaginary.toRawBits() == imaginary.toRawBits()

        @JvmStatic
        fun complices(): List<Arguments> = listOf(
            Arguments.of(2f, 4f),
            Arguments.of(2f, Float.NaN),
            Arguments.of(Float.NEGATIVE_INFINITY, -1f),
            Arguments.of(-1f, Float.NEGATIVE_INFINITY),
        )

        @JvmStatic
        fun squaredMagnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(3f, 4f), 25f),
            Arguments.of(Pair(3f, -4f), 25f),
        )

        @JvmStatic
        fun magnitudeArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(3f, 4f), 5f),
            Arguments.of(Pair(3f, -4f), 5f),
        )

        @JvmStatic
        fun phaseArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(3f, 4f), 0.9273f),
            Arguments.of(Pair(0f, 2f), 1.5708f),
            Arguments.of(Pair(0f, 0f), 0f),
        )

        @JvmStatic
        fun powComplexArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(-2f, 3f), Pair(-1.3f, 0.5f), Pair(-0.03592f, -0.05314f)),
            Arguments.of(Pair(7f, -1f), Pair(-0.1f, 0f), Pair(0.82226f, 0.01167f)),
            Arguments.of(Pair(-7f, -1f), Pair(0f, 2f), Pair(-289.329f, -280.795f)),
        )

        @JvmStatic
        fun powFloatArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0f), 0f, Pair(1f, 0f)),
            Arguments.of(Pair(3f, -1f), -0.1f, Pair(0.89079f, 0.02867f)),
        )

        @JvmStatic
        fun powIntArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(-2f, 3f), 2, Pair(-5f, -12f)),
            Arguments.of(Pair(7f, -1f), 0, Pair(1f, 0f)),
        )

        @JvmStatic
        fun fromPolarArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(5f, 0.9273f), Pair(3f, 4f)),
            Arguments.of(Pair(5f, -0.9273f), Pair(3f, -4f)),
        )

        @JvmStatic
        fun conjugateArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(3f, 4f), Pair(3f, -4f)),
            Arguments.of(Pair(3f, -4f), Pair(3f, 4f)),
        )

        @JvmStatic
        fun expArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(6.4845f, 3.5425f)),
            Arguments.of(Pair(0f, -4f), Pair(-0.65364f, 0.7568f)),
        )

        @JvmStatic
        fun cosArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(-0.46926f, -0.47383f)),
            Arguments.of(Pair(0f, -4f), Pair(27.3082f, 0f)),
        )

        @JvmStatic
        fun sinArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(1.02535f, -0.21685f)),
            Arguments.of(Pair(0f, -4f), Pair(0f, -27.2899f)),
        )

        @JvmStatic
        fun tanArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(-0.85088f, 1.32129f)),
            Arguments.of(Pair(0f, -4f), Pair(0f, -0.99933f)),
        )

        @JvmStatic
        fun coshArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(3.30164f, 1.73881f)),
            Arguments.of(Pair(0f, -4f), Pair(-0.65364f, 0f)),
        )

        @JvmStatic
        fun sinhArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(3.18287f, 1.80369f)),
            Arguments.of(Pair(0f, -4f), Pair(0f, 0.7568f)),
        )

        @JvmStatic
        fun tanhArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(0.97994f, 0.03022f)),
            Arguments.of(Pair(0f, -4f), Pair(0, -1.15782)),
        )

        @JvmStatic
        fun sqrtArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 0.5f), Pair(1.42505f, 0.17543f)),
            Arguments.of(Pair(0f, -4f), Pair(1.41421f, -1.41421f)),
        )

        @JvmStatic
        fun complexPlusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), Pair(6f, -8f), Pair(8f, -4f)),
            Arguments.of(Pair(0f, -2.3f), Pair(-1.2f, 0f), Pair(-1.2f, -2.3f)),
        )

        @JvmStatic
        fun complexPlusFloatArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), 6f, Pair(8f, 4f)),
            Arguments.of(Pair(0f, -2.3f), -1.2f, Pair(-1.2f, -2.3f)),
        )

        @JvmStatic
        fun floatPlusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(2f, Pair(4f, 6f), Pair(6f, 6f)),
            Arguments.of(0f, Pair(-1.2f, 0f), Pair(-1.2f, 0f)),
        )

        @JvmStatic
        fun complexMinusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), Pair(6f, -8f), Pair(-4f, 12f)),
            Arguments.of(Pair(0f, -2.3f), Pair(-1.2f, 0f), Pair(1.2f, -2.3f)),
        )

        @JvmStatic
        fun complexMinusFloatArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), 6f, Pair(-4f, 4f)),
            Arguments.of(Pair(0f, -2.3f), -1.2f, Pair(1.2f, -2.3f)),
        )

        @JvmStatic
        fun floatMinusComplexArgs(): List<Arguments> = listOf(
            Arguments.of(2f, Pair(4f, 6f), Pair(-2f, -6f)),
            Arguments.of(0f, Pair(-1.2f, 0f), Pair(1.2f, 0f)),
        )

        @JvmStatic
        fun complexTimesComplexArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), Pair(6f, -8f), Pair(44f, 8f)),
            Arguments.of(Pair(0f, -2.3f), Pair(-1.2f, 0f), Pair(0f, 2.76f)),
            Arguments.of(Pair(4.2f, 0f), Pair(0f, 0.8f), Pair(0f, 3.36f)),
        )

        @JvmStatic
        fun complexTimesFloatArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), 6f, Pair(12f, 24f)),
            Arguments.of(Pair(0f, -2.3f), -1.2f, Pair(0f, 2.76f)),
            Arguments.of(Pair(4.2f, 0f), -0.525f, Pair(-2.205f, 0f)),
        )

        @JvmStatic
        fun floatTimesComplexArgs(): List<Arguments> = listOf(
            Arguments.of(2f, Pair(6f, -8f), Pair(12f, -16f)),
            Arguments.of(0f, Pair(-1.2f, 0f), Pair(0f, 0f)),
            Arguments.of(4.2f, Pair(0f, 0.8f), Pair(0f, 3.36f)),
        )

        @JvmStatic
        fun complexDivComplexArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), Pair(6f, -8f), Pair(-0.2f, 0.4f)),
            Arguments.of(Pair(0f, -2.3f), Pair(-1.2f, 0f), Pair(0f, 1.91667f)),
            Arguments.of(Pair(4.2f, 0f), Pair(0f, 0.8f), Pair(0f, -5.25f)),
        )

        @JvmStatic
        fun complexDivFloatArgs(): List<Arguments> = listOf(
            Arguments.of(Pair(2f, 4f), 6f, Pair(0.33333f, 0.66667f)),
            Arguments.of(Pair(0f, -2.3f), -1.2f, Pair(0f, 1.91667f)),
            Arguments.of(Pair(4.2f, 0f), -0.525f, Pair(-8f, 0f)),
        )

        @JvmStatic
        fun floatDivComplexArgs(): List<Arguments> = listOf(
            Arguments.of(2f, Pair(6f, -8f), Pair(0.12f, 0.16f)),
            Arguments.of(0f, Pair(-1.2f, 0f), Pair(0f, 0f)),
            Arguments.of(4.2f, Pair(0f, 0.8f), Pair(0f, -5.25f)),
        )
    }
}