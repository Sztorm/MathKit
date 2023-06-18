package com.sztorm.lowallocmath

import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.PI
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
    @MethodSource("fromDegreesArgs")
    fun fromDegreesReturnsCorrectValue(degrees: Float, expected: Wrapper<AngleF>) =
        assertTrue(expected.value.isApproximately(AngleF.fromDegrees(degrees)))

    @ParameterizedTest
    @MethodSource("fromRadiansArgs")
    fun fromRadiansReturnsCorrectValue(radians: Float, expected: Wrapper<AngleF>) =
        assertTrue(expected.value.isApproximately(AngleF.fromRadians(radians)))

    companion object {
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
    }
}