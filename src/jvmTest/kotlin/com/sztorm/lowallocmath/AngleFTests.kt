package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.PI
import kotlin.math.sign
import kotlin.test.*

class AngleFTests {
    @ParameterizedTest
    @MethodSource("radiansArgs")
    fun radiansReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Float) =
        assertTrue(expected.isApproximately(angle.value.radians))

    @ParameterizedTest
    @MethodSource("degreesArgs")
    fun degreesReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Float) =
        assertTrue(expected.isApproximately(angle.value.degrees))

    @ParameterizedTest
    @MethodSource("isAcuteArgs")
    fun isAcuteReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Boolean) =
        assertEquals(expected, angle.value.isAcute())

    @ParameterizedTest
    @MethodSource("isObtuseArgs")
    fun isObtuseReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Boolean) =
        assertEquals(expected, angle.value.isObtuse())

    @ParameterizedTest
    @MethodSource("isReflexArgs")
    fun isReflexReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Boolean) =
        assertEquals(expected, angle.value.isReflex())

    @ParameterizedTest
    @MethodSource("getMinimalPositiveCoterminalArgs")
    fun getMinimalPositiveCoterminalReturnsCorrectValue(
        angle: Wrapper<AngleF>, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(angle.value.getMinimalPositiveCoterminal()))

    @ParameterizedTest
    @MethodSource("isApproximatelyArgs")
    fun isApproximatelyReturnsCorrectValue(
        angle: Wrapper<AngleF>, other: Wrapper<AngleF>, epsilon: Wrapper<AngleF>, expected: Boolean
    ) = assertEquals(expected, angle.value.isApproximately(other.value, epsilon.value))

    @ParameterizedTest
    @MethodSource("isApproximatelyCoterminalToArgs")
    fun isApproximatelyCoterminalToReturnsCorrectValue(
        angle: Wrapper<AngleF>, other: Wrapper<AngleF>, epsilon: Wrapper<AngleF>, expected: Boolean
    ) = assertEquals(expected, angle.value.isApproximatelyCoterminalTo(other.value, epsilon.value))

    @ParameterizedTest
    @MethodSource("coerceAtLeastArgs")
    fun coerceAtLeastReturnsCorrectValue(
        angle: Wrapper<AngleF>, minimum: Wrapper<AngleF>, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(angle.value.coerceAtLeast(minimum.value)))

    @ParameterizedTest
    @MethodSource("coerceAtMostArgs")
    fun coerceAtMostReturnsCorrectValue(
        angle: Wrapper<AngleF>, maximum: Wrapper<AngleF>, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(angle.value.coerceAtMost(maximum.value)))

    @ParameterizedTest
    @MethodSource("coerceInArgs")
    fun coerceInReturnsCorrectValue(
        angle: Wrapper<AngleF>,
        minimum: Wrapper<AngleF>,
        maximum: Wrapper<AngleF>,
        expected: Wrapper<AngleF>
    ) = assertTrue(
        expected.value.isApproximately(angle.value.coerceIn(minimum.value, maximum.value))
    )

    @ParameterizedTest
    @MethodSource("modArgs")
    fun modReturnsCorrectValue(
        angle: Wrapper<AngleF>, other: Wrapper<AngleF>, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(angle.value.mod(other.value)))

    @ParameterizedTest
    @MethodSource("compareToArgs")
    fun compareToReturnsCorrectValue(
        angle: Wrapper<AngleF>, other: Wrapper<AngleF>, expected: Int
    ) = assertEquals(expected, angle.value.compareTo(other.value))

    @ParameterizedTest
    @MethodSource("plusAngleArgs")
    fun unaryPlusReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Wrapper<AngleF>) =
        assertTrue(equalsBitwise(expected.value, +angle.value))

    @ParameterizedTest
    @MethodSource("minusAngleArgs")
    fun unaryMinusReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Wrapper<AngleF>) =
        assertTrue(equalsBitwise(expected.value, -angle.value))

    @ParameterizedTest
    @MethodSource("anglePlusAngleArgs")
    fun anglePlusAngleReturnsCorrectValue(
        a: Wrapper<AngleF>, b: Wrapper<AngleF>, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(a.value + b.value))

    @ParameterizedTest
    @MethodSource("angleMinusAngleArgs")
    fun angleMinusAngleReturnsCorrectValue(
        a: Wrapper<AngleF>, b: Wrapper<AngleF>, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(a.value - b.value))

    @ParameterizedTest
    @MethodSource("angleTimesFloatArgs")
    fun angleTimesFloatReturnsCorrectValue(
        a: Wrapper<AngleF>, b: Float, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(a.value * b))

    @ParameterizedTest
    @MethodSource("floatTimesAngleArgs")
    fun floatTimesAngleReturnsCorrectValue(
        a: Float, b: Wrapper<AngleF>, expected: Wrapper<AngleF>
    ) = assertTrue(expected.value.isApproximately(a * b.value))

    @ParameterizedTest
    @MethodSource("angleDivFloatArgs")
    fun angleDivFloatReturnsCorrectValue(a: Wrapper<AngleF>, b: Float, expected: Wrapper<AngleF>) =
        assertTrue(expected.value.isApproximately(a.value / b))

    @ParameterizedTest
    @MethodSource("fromDegreesArgs")
    fun fromDegreesReturnsCorrectValue(degrees: Float, expected: Wrapper<AngleF>) =
        assertTrue(expected.value.isApproximately(AngleF.fromDegrees(degrees)))

    @ParameterizedTest
    @MethodSource("fromRadiansArgs")
    fun fromRadiansReturnsCorrectValue(radians: Float, expected: Wrapper<AngleF>) =
        assertTrue(expected.value.isApproximately(AngleF.fromRadians(radians)))

    @ParameterizedTest
    @MethodSource("sinArgs")
    fun sinReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Float) = assertTrue(
        expected.isApproximately(AngleF.sin(angle.value)) ||
                areEquallySpecial(expected, AngleF.sin(angle.value))
    )

    @ParameterizedTest
    @MethodSource("cosArgs")
    fun cosReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Float) = assertTrue(
        expected.isApproximately(AngleF.cos(angle.value)) ||
                areEquallySpecial(expected, AngleF.cos(angle.value))
    )

    @ParameterizedTest
    @MethodSource("tanArgs")
    fun tanReturnsCorrectValue(angle: Wrapper<AngleF>, expected: Float) = assertTrue(
        expected.isApproximately(AngleF.tan(angle.value)) ||
                areEquallySpecial(expected, AngleF.tan(angle.value))
    )

    @ParameterizedTest
    @MethodSource("asinArgs")
    fun asinReturnsCorrectValue(x: Float, expected: Wrapper<AngleF>) = assertTrue(
        expected.value.isApproximately(AngleF.asin(x)) ||
                areEquallySpecial(expected.value.degrees, AngleF.asin(x).degrees)
    )

    @ParameterizedTest
    @MethodSource("acosArgs")
    fun acosReturnsCorrectValue(x: Float, expected: Wrapper<AngleF>) = assertTrue(
        expected.value.isApproximately(AngleF.acos(x)) ||
                areEquallySpecial(expected.value.degrees, AngleF.acos(x).degrees)
    )

    @ParameterizedTest
    @MethodSource("atanArgs")
    fun atanReturnsCorrectValue(x: Float, expected: Wrapper<AngleF>) = assertTrue(
        expected.value.isApproximately(AngleF.atan(x)) ||
                areEquallySpecial(expected.value.degrees, AngleF.atan(x).degrees)
    )

    @ParameterizedTest
    @MethodSource("atan2Args")
    fun atan2ReturnsCorrectValue(y: Float, x: Float, expected: Wrapper<AngleF>) = assertTrue(
        expected.value.isApproximately(AngleF.atan2(y, x)) ||
                areEquallySpecial(expected.value.degrees, AngleF.atan2(y, x).degrees)
    )

    @ParameterizedTest
    @MethodSource("minArgs")
    fun minReturnsCorrectValue(a: Wrapper<AngleF>, b: Wrapper<AngleF>, expected: Wrapper<AngleF>) =
        assertTrue(expected.value.isApproximately(AngleF.min(a.value, b.value)))

    @ParameterizedTest
    @MethodSource("maxArgs")
    fun maxReturnsCorrectValue(a: Wrapper<AngleF>, b: Wrapper<AngleF>, expected: Wrapper<AngleF>) =
        assertTrue(expected.value.isApproximately(AngleF.max(a.value, b.value)))

    companion object {
        /** Compares angles bitwise. Useful when comparing NaNs. **/
        @JvmStatic
        fun equalsBitwise(a: AngleF, b: AngleF) =
            a.radians.toRawBits() == b.radians.toRawBits()

        /**
         * Returns `true` if both a and b are `NaN`s or are infinities with the same sign, `false`
         * otherwise.
         */
        @JvmStatic
        fun areEquallySpecial(a: Float, b: Float): Boolean =
            (a.isNaN() && b.isNaN()) ||
                    ((a.isInfinite() && b.isInfinite()) && (a.sign == b.sign))

        @JvmStatic
        fun radiansArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF.fromRadians(0f)), 0f),
            Arguments.of(Wrapper(AngleF((-0.5 * PI).toFloat())), (-0.5 * PI).toFloat()),
            Arguments.of(Wrapper(AngleF((0.5 * PI).toFloat())), (0.5 * PI).toFloat()),
            Arguments.of(Wrapper(AngleF((3.0 * PI).toFloat())), (3.0 * PI).toFloat()),
        )

        @JvmStatic
        fun degreesArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF(0f)), 0f),
            Arguments.of(Wrapper(AngleF((-0.5 * PI).toFloat())), -90f),
            Arguments.of(Wrapper(AngleF((0.5 * PI).toFloat())), 90f),
            Arguments.of(Wrapper(AngleF((3.0 * PI).toFloat())), 540f),
        )

        @JvmStatic
        fun isAcuteArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF.fromDegrees(-1f)), false),
            Arguments.of(Wrapper(AngleF.fromDegrees(0f)), false),
            Arguments.of(Wrapper(AngleF.fromDegrees(1f)), true),
            Arguments.of(Wrapper(AngleF.fromDegrees(89f)), true),
            Arguments.of(Wrapper(AngleF.fromDegrees(90f)), false),
        )

        @JvmStatic
        fun isObtuseArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF.fromDegrees(-91f)), false),
            Arguments.of(Wrapper(AngleF.fromDegrees(90f)), false),
            Arguments.of(Wrapper(AngleF.fromDegrees(91f)), true),
            Arguments.of(Wrapper(AngleF.fromDegrees(179f)), true),
            Arguments.of(Wrapper(AngleF.fromDegrees(180f)), false),
        )

        @JvmStatic
        fun isReflexArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF.fromDegrees(-181f)), false),
            Arguments.of(Wrapper(AngleF.fromDegrees(180f)), false),
            Arguments.of(Wrapper(AngleF.fromDegrees(181f)), true),
            Arguments.of(Wrapper(AngleF.fromDegrees(359f)), true),
            Arguments.of(Wrapper(AngleF.fromDegrees(360f)), false),
            Arguments.of(Wrapper(AngleF.fromDegrees(0f)), false),
        )

        @JvmStatic
        fun getMinimalPositiveCoterminalArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF.fromDegrees(1f)),
                Wrapper(AngleF.fromDegrees(1f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(359f)),
                Wrapper(AngleF.fromDegrees(359f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(360f)),
                Wrapper(AngleF.fromDegrees(0f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(-1f)),
                Wrapper(AngleF.fromDegrees(359f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(361f)),
                Wrapper(AngleF.fromDegrees(1f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(722f)),
                Wrapper(AngleF.fromDegrees(2f))
            ),
            Arguments.of(
                Wrapper(AngleF.fromDegrees(-722f)),
                Wrapper(AngleF.fromDegrees(358f))
            ),
        )

        @JvmStatic
        fun isApproximatelyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF.fromRadians(2f)),
                Wrapper(AngleF.fromRadians(2f)),
                Wrapper(AngleF.fromRadians(0f)),
                true
            ),
            Arguments.of(
                Wrapper(AngleF.fromRadians(2.001f)),
                Wrapper(AngleF.fromRadians(1.999f)),
                Wrapper(AngleF.fromRadians(0.01f)),
                true
            ),
            Arguments.of(
                Wrapper(AngleF.fromRadians(2.1f)),
                Wrapper(AngleF.fromRadians(1.9f)),
                Wrapper(AngleF.fromRadians(0.01f)),
                false
            ),
        )

        @JvmStatic
        fun isApproximatelyCoterminalToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF.fromRadians(14f)),
                Wrapper(AngleF.fromRadians(1.4336294f)),
                Wrapper(AngleF.fromRadians(0.001f)),
                true
            ),
            Arguments.of(
                Wrapper(AngleF.fromRadians(14.001f)),
                Wrapper(AngleF.fromRadians(1.4336294f)),
                Wrapper(AngleF.fromRadians(0.01f)),
                true
            ),
            Arguments.of(
                Wrapper(AngleF.fromRadians(14.1f)),
                Wrapper(AngleF.fromRadians(1.4336294f)),
                Wrapper(AngleF.fromRadians(0.01f)),
                false
            ),
            Arguments.of(
                Wrapper(AngleF.fromRadians(-12f)),
                Wrapper(AngleF.fromRadians(0.5663706f)),
                Wrapper(AngleF.fromRadians(0.001f)),
                true
            ),
            Arguments.of(
                Wrapper(AngleF.fromRadians(-11.999f)),
                Wrapper(AngleF.fromRadians(0.5663706f)),
                Wrapper(AngleF.fromRadians(0.01f)),
                true
            ),
            Arguments.of(
                Wrapper(AngleF.fromRadians(-11.9f)),
                Wrapper(AngleF.fromRadians(0.5663706f)),
                Wrapper(AngleF.fromRadians(0.01f)),
                false
            ),
        )

        @JvmStatic
        fun coerceAtLeastArgs(): List<Arguments> = maxArgs()

        @JvmStatic
        fun coerceAtMostArgs(): List<Arguments> = minArgs()

        @JvmStatic
        fun coerceInArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(4f)),
                Wrapper(AngleF(5f)),
                Wrapper(AngleF(4f)),
            ),
            Arguments.of(
                Wrapper(AngleF(-3f)),
                Wrapper(AngleF(-5f)),
                Wrapper(AngleF(-4f)),
                Wrapper(AngleF(-4f)),
            ),
            Arguments.of(
                Wrapper(AngleF(2f)),
                Wrapper(AngleF(1f)),
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(2f)),
            ),
        )

        @JvmStatic
        fun modArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF(4f)),
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(1f)),
            ),
            Arguments.of(
                Wrapper(AngleF(-4f)),
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(2f)),
            ),
        )

        @JvmStatic
        fun compareToArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF(2f)), Wrapper(AngleF(3f)), -1),
            Arguments.of(Wrapper(AngleF(3f)), Wrapper(AngleF(2f)), 1),
            Arguments.of(Wrapper(AngleF(2f)), Wrapper(AngleF(2f)), 0),
        )

        @JvmStatic
        fun plusAngleArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF(2f)), Wrapper(AngleF(2f))),
            Arguments.of(Wrapper(AngleF(-2f)), Wrapper(AngleF(-2f))),
        )

        @JvmStatic
        fun minusAngleArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF(2f)), Wrapper(AngleF(-2f))),
            Arguments.of(Wrapper(AngleF(-2f)), Wrapper(AngleF(2f))),
        )

        @JvmStatic
        fun anglePlusAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF(2f)),
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(5f)),
            ),
            Arguments.of(
                Wrapper(AngleF(2f)),
                Wrapper(AngleF(-3f)),
                Wrapper(AngleF(-1f)),
            ),
        )

        @JvmStatic
        fun angleMinusAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF(2f)),
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(-1f)),
            ),
            Arguments.of(
                Wrapper(AngleF(2f)),
                Wrapper(AngleF(-3f)),
                Wrapper(AngleF(5f)),
            ),
        )

        @JvmStatic
        fun angleTimesFloatArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF(4f)), 6f, Wrapper(AngleF(24f))),
            Arguments.of(Wrapper(AngleF(6f)), -4f, Wrapper(AngleF(-24f))),
            Arguments.of(Wrapper(AngleF(-6f)), -4f, Wrapper(AngleF(24f))),
        )

        @JvmStatic
        fun floatTimesAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                6f, Wrapper(AngleF(4f)), Wrapper(AngleF(24f))
            ),
            Arguments.of(
                -4f, Wrapper(AngleF(6f)), Wrapper(AngleF(-24f))
            ),
            Arguments.of(
                -4f, Wrapper(AngleF(-6f)), Wrapper(AngleF(24f))
            ),
        )

        @JvmStatic
        fun angleDivFloatArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF(3f)), 6f, Wrapper(AngleF(0.5f))),
            Arguments.of(Wrapper(AngleF(6f)), -3f, Wrapper(AngleF(-2f))),
            Arguments.of(Wrapper(AngleF(-6f)), -3f, Wrapper(AngleF(2f))),
        )

        @JvmStatic
        fun fromDegreesArgs(): List<Arguments> = listOf(
            Arguments.of(0f, Wrapper(AngleF(0f))),
            Arguments.of(-90f, Wrapper(AngleF((-0.5 * PI).toFloat()))),
            Arguments.of(90f, Wrapper(AngleF((0.5 * PI).toFloat()))),
            Arguments.of(540f, Wrapper(AngleF((3.0 * PI).toFloat()))),
        )

        @JvmStatic
        fun fromRadiansArgs(): List<Arguments> = listOf(
            Arguments.of(0f, Wrapper(AngleF.fromRadians(0f))),
            Arguments.of((-0.5 * PI).toFloat(), Wrapper(AngleF((-0.5 * PI).toFloat()))),
            Arguments.of((0.5 * PI).toFloat(), Wrapper(AngleF((0.5 * PI).toFloat()))),
            Arguments.of((3.0 * PI).toFloat(), Wrapper(AngleF((3.0 * PI).toFloat()))),
        )

        @JvmStatic
        fun sinArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF.fromDegrees(30f)), 0.5f),
            Arguments.of(Wrapper(AngleF.fromDegrees(-45f)), -0.7071068f),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.NaN)), Float.NaN),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.POSITIVE_INFINITY)), Float.NaN),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.NEGATIVE_INFINITY)), Float.NaN),
        )

        @JvmStatic
        fun cosArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF.fromDegrees(30f)), 0.8660254f),
            Arguments.of(Wrapper(AngleF.fromDegrees(-45f)), 0.7071068f),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.NaN)), Float.NaN),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.POSITIVE_INFINITY)), Float.NaN),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.NEGATIVE_INFINITY)), Float.NaN),
        )

        @JvmStatic
        fun tanArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(AngleF.fromDegrees(30f)), 0.5773502f),
            Arguments.of(Wrapper(AngleF.fromDegrees(-45f)), -1f),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.NaN)), Float.NaN),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.POSITIVE_INFINITY)), Float.NaN),
            Arguments.of(Wrapper(AngleF.fromRadians(Float.NEGATIVE_INFINITY)), Float.NaN),
        )

        @JvmStatic
        fun asinArgs(): List<Arguments> = listOf(
            Arguments.of(0.5f, Wrapper(AngleF.fromDegrees(30f))),
            Arguments.of(-0.7071068f, Wrapper(AngleF.fromDegrees(-45f))),
            Arguments.of(Float.NaN, Wrapper(AngleF.fromRadians(Float.NaN))),
            Arguments.of(-1.1f, Wrapper(AngleF.fromRadians(Float.NaN))),
            Arguments.of(1.1f, Wrapper(AngleF.fromRadians(Float.NaN))),
        )

        @JvmStatic
        fun acosArgs(): List<Arguments> = listOf(
            Arguments.of(0.8660254f, Wrapper(AngleF.fromDegrees(30f))),
            Arguments.of(0.7071068f, Wrapper(AngleF.fromDegrees(45f))),
            Arguments.of(Float.NaN, Wrapper(AngleF.fromRadians(Float.NaN))),
            Arguments.of(-1.1f, Wrapper(AngleF.fromRadians(Float.NaN))),
            Arguments.of(1.1f, Wrapper(AngleF.fromRadians(Float.NaN))),
        )

        @JvmStatic
        fun atanArgs(): List<Arguments> = listOf(
            Arguments.of(0.5773502f, Wrapper(AngleF.fromDegrees(30f))),
            Arguments.of(-1f, Wrapper(AngleF.fromDegrees(-45f))),
            Arguments.of(Float.POSITIVE_INFINITY, Wrapper(AngleF.fromDegrees(90f))),
            Arguments.of(Float.NEGATIVE_INFINITY, Wrapper(AngleF.fromDegrees(-90f))),
            Arguments.of(Float.NaN, Wrapper(AngleF.fromRadians(Float.NaN))),
        )

        @JvmStatic
        fun atan2Args(): List<Arguments> = listOf(
            Arguments.of(0f, 0f, Wrapper(AngleF.fromDegrees(0f))),
            Arguments.of(
                0.707107f, 0.707107f, Wrapper(AngleF.fromDegrees(45f))
            ),
            Arguments.of(
                0.707107f, -0.707107f, Wrapper(AngleF.fromDegrees(135f))
            ),
            Arguments.of(
                -0.707107f, 0.707107f, Wrapper(AngleF.fromDegrees(-45f))
            ),
            Arguments.of(
                -0.707107f, -0.707107f, Wrapper(AngleF.fromDegrees(-135f))
            ),
            Arguments.of(0f, 3f, Wrapper(AngleF.fromDegrees(0f))),
            Arguments.of(0f, -3f, Wrapper(AngleF.fromDegrees(180f))),
            Arguments.of(-0f, 3f, Wrapper(AngleF.fromDegrees(-0f))),
            Arguments.of(-0f, -3f, Wrapper(AngleF.fromDegrees(-180f))),
            Arguments.of(
                3f, Float.POSITIVE_INFINITY, Wrapper(AngleF.fromDegrees(0f))
            ),
            Arguments.of(
                -3f, Float.POSITIVE_INFINITY, Wrapper(AngleF.fromDegrees(-0f))
            ),
            Arguments.of(
                3f, Float.NEGATIVE_INFINITY, Wrapper(AngleF.fromDegrees(180f))
            ),
            Arguments.of(
                -3f, Float.NEGATIVE_INFINITY, Wrapper(AngleF.fromDegrees(-180f))
            ),
            Arguments.of(3f, 0f, Wrapper(AngleF.fromDegrees(90f))),
            Arguments.of(-3f, 0f, Wrapper(AngleF.fromDegrees(-90f))),
            Arguments.of(Float.POSITIVE_INFINITY, 3f, Wrapper(AngleF.fromDegrees(90f))),
            Arguments.of(Float.NEGATIVE_INFINITY, 3f, Wrapper(AngleF.fromDegrees(-90f))),
            Arguments.of(Float.NaN, 3f, Wrapper(AngleF.fromRadians(Float.NaN))),
            Arguments.of(3f, Float.NaN, Wrapper(AngleF.fromRadians(Float.NaN))),
        )

        @JvmStatic
        fun minArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(4f)),
                Wrapper(AngleF(3f)),
            ),
            Arguments.of(
                Wrapper(AngleF(-3f)),
                Wrapper(AngleF(-4f)),
                Wrapper(AngleF(-4f)),
            ),
        )

        @JvmStatic
        fun maxArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(AngleF(3f)),
                Wrapper(AngleF(4f)),
                Wrapper(AngleF(4f)),
            ),
            Arguments.of(
                Wrapper(AngleF(-3f)),
                Wrapper(AngleF(-4f)),
                Wrapper(AngleF(-3f)),
            ),
        )
    }
}