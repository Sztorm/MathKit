package com.sztorm.mathkit

import com.sztorm.mathkit.utils.Wrapper
import com.sztorm.mathkit.utils.assertApproximation
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Vector2FTests {
    @ParameterizedTest
    @MethodSource("vectorComponentsArgs")
    fun vectorComponentsAreValid(vector: Wrapper<Vector2F>, expectedX: Float, expectedY: Float) {
        assertEquals(expectedX, vector.value.x)
        assertEquals(expectedY, vector.value.y)
        assertEquals(Vector2F(expectedX, expectedY), vector.value.xy)
        assertEquals(Vector2F(expectedY, expectedX), vector.value.yx)
        assertEquals(Vector2F(expectedX, expectedX), vector.value.xx)
        assertEquals(Vector2F(expectedY, expectedY), vector.value.yy)
    }

    @ParameterizedTest
    @MethodSource("squaredLengthArgs")
    fun squaredLengthReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Float) =
        assertApproximation(expected, vector.value.squaredLength)

    @ParameterizedTest
    @MethodSource("lengthArgs")
    fun lengthReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Float) =
        assertApproximation(expected, vector.value.length)

    @ParameterizedTest
    @MethodSource("squaredMagnitudeArgs")
    fun squaredMagnitudeReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Float) =
        assertApproximation(expected, vector.value.squaredMagnitude)

    @ParameterizedTest
    @MethodSource("magnitudeArgs")
    fun magnitudeReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Float) =
        assertApproximation(expected, vector.value.magnitude)

    @ParameterizedTest
    @MethodSource("normalizedArgs")
    fun normalizedReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, vector.value.normalized)

    @ParameterizedTest
    @MethodSource("normalizedOrElseVector2FArgs")
    fun normalizedOrElseReturnsCorrectValue(
        vector: Wrapper<Vector2F>, defaultValue: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, vector.value.normalizedOrElse(defaultValue.value))

    @ParameterizedTest
    @MethodSource("normalizedOrElseFunctionArgs")
    fun normalizedOrElseReturnsCorrectValue(
        vector: Wrapper<Vector2F>, defaultValue: () -> Vector2F, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, vector.value.normalizedOrElse(defaultValue))

    @ParameterizedTest
    @MethodSource("squaredDistanceToArgs")
    fun squaredDistanceToReturnsCorrectValue(
        vector: Wrapper<Vector2F>, other: Wrapper<Vector2F>, expected: Float
    ) = assertApproximation(expected, vector.value.squaredDistanceTo(other.value))

    @ParameterizedTest
    @MethodSource("distanceToArgs")
    fun distanceToReturnsCorrectValue(
        vector: Wrapper<Vector2F>, other: Wrapper<Vector2F>, expected: Float
    ) = assertApproximation(expected, vector.value.distanceTo(other.value))

    @ParameterizedTest
    @MethodSource("coerceInArgs")
    fun coerceInReturnsCorrectValue(
        vector: Wrapper<Vector2F>,
        minimum: Wrapper<Vector2F>,
        maximum: Wrapper<Vector2F>,
        expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, vector.value.coerceIn(minimum.value, maximum.value))

    @ParameterizedTest
    @MethodSource("coerceAtLeastArgs")
    fun coerceAtLeastReturnsCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value.coerceAtLeast(b.value))

    @ParameterizedTest
    @MethodSource("coerceAtMostArgs")
    fun coerceAtMostReturnsCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value.coerceAtMost(b.value))

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: String) =
        assertEquals(expected, vector.value.toString())

    @ParameterizedTest
    @MethodSource("isApproximatelyArgs")
    fun isApproximatelyReturnsCorrectValue(
        vector: Wrapper<Vector2F>, other: Wrapper<Vector2F>, epsilon: Float, expected: Boolean
    ) = assertEquals(expected, vector.value.isApproximately(other.value, epsilon))

    @ParameterizedTest
    @MethodSource("dotArgs")
    fun dotReturnsCorrectValue(a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Float) =
        assertApproximation(expected, a.value dot b.value)

    @ParameterizedTest
    @MethodSource("perpDotArgs")
    fun perpDotReturnsCorrectValue(a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Float) =
        assertApproximation(expected, a.value perpDot b.value)

    @ParameterizedTest
    @MethodSource("multipliedComponentWiseByArgs")
    fun multipliedComponentWiseByReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value multipliedComponentWiseBy b.value)

    @ParameterizedTest
    @MethodSource("dividedComponentWiseByArgs")
    fun dividedComponentWiseByReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value dividedComponentWiseBy b.value)

    @ParameterizedTest
    @MethodSource("angleToArgs")
    fun angleToReturnsCorrectValue(
        vector: Wrapper<Vector2F>, other: Wrapper<Vector2F>, expected: Wrapper<AngleF>
    ) = assertApproximation(expected.value, vector.value.angleTo(other.value))

    @ParameterizedTest
    @MethodSource("rotationToArgs")
    fun rotationToReturnsCorrectValue(
        vector: Wrapper<Vector2F>, other: Wrapper<Vector2F>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, vector.value.rotationTo(other.value))

    @ParameterizedTest
    @MethodSource("toVector2IArgs")
    fun toVector2IReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Wrapper<Vector2I>) =
        assertEquals(expected.value, vector.value.toVector2I())

    @ParameterizedTest
    @MethodSource("toComplexFArgs")
    fun toComplexFReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Wrapper<ComplexF>) =
        assertEquals(expected.value, vector.value.toComplexF())

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        vector: Wrapper<Vector2F>, x: Float, y: Float, expected: Wrapper<Vector2F>
    ) = assertEquals(expected.value, vector.value.copy(x, y))

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        vector: Wrapper<Vector2F>, expectedComponent1: Float, expectedComponent2: Float,
    ) {
        val (actualComponent1: Float, actualComponent2: Float) = vector.value

        assertEquals(expectedComponent1, actualComponent1)
        assertEquals(expectedComponent2, actualComponent2)
    }

    @ParameterizedTest
    @MethodSource("getArgs")
    fun getReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Collection<Float>) {
        val actual: List<Float> = listOf(vector.value[0], vector.value[1])

        assertContentEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("getThrowsExceptionArgs")
    fun getThrowsCorrectException(
        vector: Wrapper<Vector2F>, index: Int, expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) { vector.value[index] }
    }

    @ParameterizedTest
    @MethodSource("plusVector2FArgs")
    fun unaryPlusReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, +vector.value)

    @ParameterizedTest
    @MethodSource("minusVector2FArgs")
    fun unaryMinusReturnsCorrectValue(vector: Wrapper<Vector2F>, expected: Wrapper<Vector2F>) =
        assertEquals(expected.value, -vector.value)

    @ParameterizedTest
    @MethodSource("vector2FPlusVector2FArgs")
    fun vectorPlusVectorReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value + b.value)

    @ParameterizedTest
    @MethodSource("vector2FMinusVector2FArgs")
    fun vectorMinusVectorReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value - b.value)

    @ParameterizedTest
    @MethodSource("vector2FTimesVector2FArgs")
    fun vectorTimesVectorReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value * b.value)

    @ParameterizedTest
    @MethodSource("vector2FTimesFloatArgs")
    fun vectorTimesFloatReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Float, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value * b)

    @ParameterizedTest
    @MethodSource("floatTimesVector2FArgs")
    fun floatTimesVectorReturnCorrectValue(
        a: Float, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a * b.value)

    @ParameterizedTest
    @MethodSource("vector2FTimesComplexFArgs")
    fun vectorTimesComplexReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<ComplexF>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value * b.value)

    @ParameterizedTest
    @MethodSource("vector2FDivVector2FArgs")
    fun vectorDivVectorReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<ComplexF>
    ) = assertApproximation(expected.value, a.value / b.value)

    @ParameterizedTest
    @MethodSource("vector2FDivFloatArgs")
    fun vectorDivFloatReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Float, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value / b)

    @ParameterizedTest
    @MethodSource("floatDivVector2FArgs")
    fun floatDivVectorReturnCorrectValue(
        a: Float, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a / b.value)

    @ParameterizedTest
    @MethodSource("vector2FDivComplexFArgs")
    fun vectorDivComplexReturnCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<ComplexF>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, a.value / b.value)

    @Test
    fun sizeBitsReturnsCorrectValue() = assertEquals(Vector2F.SIZE_BITS, 64)

    @Test
    fun sizeBytesReturnsCorrectValue() = assertEquals(Vector2F.SIZE_BYTES, 8)

    @Test
    fun zeroReturnsCorrectValue() = assertEquals(Vector2F.ZERO, Vector2F(0f, 0f))

    @Test
    fun oneReturnsCorrectValue() = assertEquals(Vector2F.ONE, Vector2F(1f, 1f))

    @Test
    fun positiveInfinityReturnsCorrectValue() = assertEquals(
        Vector2F.POSITIVE_INFINITY, Vector2F(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )

    @Test
    fun negativeInfinityReturnsCorrectValue() = assertEquals(
        Vector2F.NEGATIVE_INFINITY, Vector2F(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY)
    )

    @Suppress("RedundantSuppression", "SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("lerpArgs")
    fun lerpReturnsCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, t: Float, expected: Wrapper<Vector2F>
    ) {
        assertApproximation(expected.value, lerp(a.value, b.value, t))
        assertApproximation(expected.value, Vector2F.lerp(a.value, b.value, t))
    }

    @Suppress("RedundantSuppression", "SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("lerpVectorInterpolatorArgs")
    fun lerpReturnsCorrectValue(
        a: Wrapper<Vector2F>,
        b: Wrapper<Vector2F>,
        t: Wrapper<Vector2F>,
        expected: Wrapper<Vector2F>
    ) {
        assertApproximation(
            expected.value, lerp(a.value, b.value, t.value)
        )
        assertApproximation(
            expected.value, Vector2F.lerp(a.value, b.value, t.value)
        )
    }

    @Suppress("RedundantSuppression", "SpellCheckingInspection")
    @ParameterizedTest
    @MethodSource("inverseLerpArgs")
    fun inverseLerpInReturnsCorrectValue(
        a: Wrapper<Vector2F>,
        b: Wrapper<Vector2F>,
        t: Wrapper<Vector2F>,
        expected: Wrapper<Vector2F>
    ) {
        assertApproximation(
            expected.value, inverseLerp(a.value, b.value, t.value)
        )
        assertApproximation(
            expected.value, Vector2F.inverseLerp(a.value, b.value, t.value)
        )
    }

    @ParameterizedTest
    @MethodSource("maxArgs")
    fun maxReturnsCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) {
        assertApproximation(expected.value, max(a.value, b.value))
        assertApproximation(expected.value, Vector2F.max(a.value, b.value))
    }

    @ParameterizedTest
    @MethodSource("minArgs")
    fun minReturnsCorrectValue(
        a: Wrapper<Vector2F>, b: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) {
        assertApproximation(expected.value, min(a.value, b.value))
        assertApproximation(expected.value, Vector2F.min(a.value, b.value))
    }

    @ParameterizedTest
    @MethodSource("closestPointOnLineSegmentArgs")
    fun closestPointOnLineSegmentReturnsCorrectValue(
        a: Wrapper<Vector2F>,
        b: Wrapper<Vector2F>,
        point: Wrapper<Vector2F>,
        expected: Wrapper<Vector2F>
    ) = assertApproximation(
        expected.value, Vector2F.closestPointOnLineSegment(a.value, b.value, point.value)
    )

    companion object {
        @JvmStatic
        fun vectorComponentsArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2F(2f, 4f)), 2f, 4f),
            Arguments.of(Wrapper(Vector2F(3f, -4f)), 3f, -4f),
            Arguments.of(Wrapper(Vector2F(6f, -2f)), 6f, -2f),
            Arguments.of(
                Wrapper(Vector2F(Float.POSITIVE_INFINITY, -1f)), Float.POSITIVE_INFINITY, -1f
            ),
            Arguments.of(
                Wrapper(Vector2F(2.5f, Float.NaN)), 2.5f, Float.NaN
            ),
        )

        @JvmStatic
        fun squaredLengthArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2F(3f, 4f)), 25f),
            Arguments.of(Wrapper(Vector2F(1f, -2f)), 5f),
            Arguments.of(Wrapper(Vector2F.ZERO), 0f),
        )

        @JvmStatic
        fun lengthArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2F(3f, 4f)), 5f),
            Arguments.of(Wrapper(Vector2F(1f, -2f)), 2.236068f),
            Arguments.of(Wrapper(Vector2F.ZERO), 0f),
        )

        @JvmStatic
        fun squaredMagnitudeArgs(): List<Arguments> = squaredLengthArgs()

        @JvmStatic
        fun magnitudeArgs(): List<Arguments> = lengthArgs()

        @JvmStatic
        fun normalizedArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(0.6f, 0.8f))
            ),
            Arguments.of(
                Wrapper(Vector2F(1f, -2f)), Wrapper(Vector2F(0.4472136f, -0.8944272f))
            ),
            Arguments.of(
                Wrapper(Vector2F(0.000001f, 0.000001f)), Wrapper(Vector2F.ZERO)
            ),
        )

        @JvmStatic
        fun normalizedOrElseVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(Vector2F(1f, 0f)),
                Wrapper(Vector2F(0.6f, 0.8f))
            ),
            Arguments.of(
                Wrapper(Vector2F(1f, -2f)),
                Wrapper(Vector2F(1f, 0f)),
                Wrapper(Vector2F(0.4472136f, -0.8944272f))
            ),
            Arguments.of(
                Wrapper(Vector2F(0.000001f, 0.000001f)),
                Wrapper(Vector2F(1f, 0f)),
                Wrapper(Vector2F(1f, 0f))
            ),
            Arguments.of(
                Wrapper(Vector2F(0.000001f, 0.000001f)),
                Wrapper(Vector2F(0f, 1f)),
                Wrapper(Vector2F(0f, 1f))
            ),
        )

        @JvmStatic
        fun normalizedOrElseFunctionArgs(): List<Arguments> = normalizedOrElseVector2FArgs().map {
            val args = it.get()
            val vector = (args[1] as Wrapper<*>).value as Vector2F
            Arguments.of(args[0], { vector }, args[2])
        }

        @JvmStatic
        fun squaredDistanceToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(0f, 4f)), 9f
            ),
            Arguments.of(
                Wrapper(Vector2F(1f, -2f)), Wrapper(Vector2F(-4f, 0.4f)), 30.76f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO), Wrapper(Vector2F.ZERO), 0f
            ),
        )

        @JvmStatic
        fun distanceToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(0f, 4f)), 3f
            ),
            Arguments.of(
                Wrapper(Vector2F(1f, -2f)), Wrapper(Vector2F(-4f, 0.4f)), 5.54617f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO), Wrapper(Vector2F.ZERO), 0f
            ),
        )

        @JvmStatic
        fun coerceInArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(Vector2F(0f, 5f)),
                Wrapper(Vector2F(2f, 10f)),
                Wrapper(Vector2F(2f, 5f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(-3f, 0.3f)),
                Wrapper(Vector2F(-2f, -0.2f)),
                Wrapper(Vector2F(4f, 0.1f)),
                Wrapper(Vector2F(-2f, 0.1f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(Vector2F(0f, 0f)),
            ),
        )

        @JvmStatic
        fun coerceAtLeastArgs(): List<Arguments> = maxArgs()

        @JvmStatic
        fun coerceAtMostArgs(): List<Arguments> = minArgs()

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)),
                "Vector2F(x=${2f}, y=${4f})"
            ),
            Arguments.of(
                Wrapper(Vector2F(2f, Float.NaN)),
                "Vector2F(x=${2f}, y=${Float.NaN})"
            ),
            Arguments.of(
                Wrapper(Vector2F(Float.NEGATIVE_INFINITY, -1f)),
                "Vector2F(x=${Float.NEGATIVE_INFINITY}, y=${-1f})"
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, Float.POSITIVE_INFINITY)),
                "Vector2F(x=${-1f}, y=${Float.POSITIVE_INFINITY})"
            ),
        )

        @JvmStatic
        fun isApproximatelyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(3f, 4f)), 0f, true
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(3.001f, 3.999f)), 0.01f, true
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(Vector2F(3.000001f, 3.999999f)),
                0.00001f,
                true
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(3.04f, 3.95f)), 0.01f, false
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(Vector2F(3.00004f, 3.99995f)),
                0.00001f,
                false
            ),
        )

        @JvmStatic
        fun dotArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(0f, 4f)), 16f),
            Arguments.of(Wrapper(Vector2F(1f, -2f)), Wrapper(Vector2F(-4f, 0.4f)), -4.8f),
            Arguments.of(Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F.ZERO), 0f),
        )

        @JvmStatic
        fun perpDotArgs(): List<Arguments> = listOf(
            Arguments.of(Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F(0f, 4f)), 12f),
            Arguments.of(Wrapper(Vector2F(1f, -2f)), Wrapper(Vector2F(-4f, 0.4f)), -7.6f),
            Arguments.of(Wrapper(Vector2F(3f, 4f)), Wrapper(Vector2F.ZERO), 0f),
        )

        @JvmStatic
        fun multipliedComponentWiseByArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)),
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(Vector2F(-6f, 4f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, -2f)),
                Wrapper(Vector2F(-4f, 0.4f)),
                Wrapper(Vector2F(4f, -0.8f)),
            ),
        )

        @JvmStatic
        fun dividedComponentWiseByArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)),
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(Vector2F(-0.666667f, 4f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, -2f)),
                Wrapper(Vector2F(-4f, 0.4f)),
                Wrapper(Vector2F(0.25f, -5f)),
            ),
        )

        @JvmStatic
        fun angleToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(179f)).toVector2F() * 0.5f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(1f)).toVector2F() * 4f),
                Wrapper(AngleF.fromDegrees(-178f)),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(1f)).toVector2F() * 0.5f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(179f)).toVector2F() * 4f
                ),
                Wrapper(AngleF.fromDegrees(178f)),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(30f)).toVector2F() * 0.2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.5f
                ),
                Wrapper(AngleF.fromDegrees(-60f)),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(30f)).toVector2F() * 0.5f
                ),
                Wrapper(AngleF.fromDegrees(60f)),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.5f
                ),
                Wrapper(AngleF.fromDegrees(0f)),
            ),
            Arguments.of(
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(AngleF.fromDegrees(0f)),
            ),
        )

        @JvmStatic
        fun rotationToArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(179f)).toVector2F() * 0.5f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(1f)).toVector2F() * 4f),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-178f))),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(1f)).toVector2F() * 0.5f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(179f)).toVector2F() * 4f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(178f))),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(30f)).toVector2F() * 0.2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.5f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f))),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(30f)).toVector2F() * 0.5f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(60f))),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F() * 0.5f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
            ),
            Arguments.of(
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
            ),
        )

        @JvmStatic
        fun toVector2IArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, -4f)), Wrapper(Vector2I(3, -4))
            ),
            Arguments.of(
                Wrapper(Vector2F(0.3f, Float.NaN)), Wrapper(Vector2I(0, 0))
            ),
            Arguments.of(
                Wrapper(Vector2F(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY)),
                Wrapper(Vector2I(Int.MIN_VALUE, Int.MAX_VALUE))
            ),
            Arguments.of(
                Wrapper(Vector2F(-Float.MAX_VALUE, Float.MAX_VALUE)),
                Wrapper(Vector2I(Int.MIN_VALUE, Int.MAX_VALUE))
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO), Wrapper(Vector2I.ZERO)
            ),
        )

        @JvmStatic
        fun toComplexFArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, -4f)), Wrapper(ComplexF(3f, -4f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-Float.MAX_VALUE, Float.MAX_VALUE)),
                Wrapper(ComplexF(-Float.MAX_VALUE, Float.MAX_VALUE))
            ),
            Arguments.of(Wrapper(Vector2F.ZERO), Wrapper(ComplexF.ZERO)),
        )

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), 2f, 4f, Wrapper(Vector2F(2f, 4f))
            ),
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), 1f, 4f, Wrapper(Vector2F(1f, 4f))
            ),
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), 6f, -2f, Wrapper(Vector2F(6f, -2f))
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = vectorComponentsArgs()

        @JvmStatic
        fun getArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), listOf(2f, 4f)
            ),
            Arguments.of(
                Wrapper(Vector2F(2f, Float.NaN)), listOf(2f, Float.NaN)
            ),
            Arguments.of(
                Wrapper(Vector2F(Float.NEGATIVE_INFINITY, -1f)),
                listOf(Float.NEGATIVE_INFINITY, -1f)
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, Float.NEGATIVE_INFINITY)),
                listOf(-1f, Float.NEGATIVE_INFINITY)
            ),
        )

        @JvmStatic
        fun getThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), -1, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), 2, IndexOutOfBoundsException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), 3, IndexOutOfBoundsException::class.java
            ),
        )

        @JvmStatic
        fun plusVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), Wrapper(Vector2F(2f, 4f))
            ),
            Arguments.of(
                Wrapper(Vector2F(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY)),
                Wrapper(Vector2F(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY))
            )
        )

        @JvmStatic
        fun minusVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)), Wrapper(Vector2F(-2f, -4f))
            ),
            Arguments.of(
                Wrapper(Vector2F(Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY)),
                Wrapper(Vector2F(Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY))
            )
        )

        @JvmStatic
        fun vector2FPlusVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)),
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(Vector2F(-1f, 5f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(1f, -2f)),
                Wrapper(Vector2F(-4f, 0.4f)),
                Wrapper(Vector2F(-3f, -1.6f)),
            ),
        )

        @JvmStatic
        fun vector2FMinusVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)),
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(Vector2F(5f, 3f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(1f, -2f)),
                Wrapper(Vector2F(-4f, 0.4f)),
                Wrapper(Vector2F(5f, -2.4f)),
            ),
        )

        @JvmStatic
        fun vector2FTimesVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(90f)).toVector2F() * 5f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(140f)) * 10f),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-110f)).toVector2F() * 0.1f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f)) * 0.2f),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 0.1f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(200f)).toVector2F() * 4f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-110f)) * 0.4f),
            ),
            Arguments.of(
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
            ),
            Arguments.of(
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(ComplexF(0f, 0f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-90f)).toVector2F() * 0.8f
                ),
                Wrapper(ComplexF(0f, 0f)),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-90f)).toVector2F() * 0.8f
                ),
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(ComplexF(0f, 0f)),
            ),
        )

        @JvmStatic
        fun floatTimesVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                2f,
                Wrapper(Vector2F(-3f, 1f)),
                Wrapper(Vector2F(-6f, 2f)),
            ),
            Arguments.of(
                -0.2f,
                Wrapper(Vector2F(-4f, 0.4f)),
                Wrapper(Vector2F(0.8f, -0.08f)),
            ),
        )

        @JvmStatic
        fun vector2FTimesFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-3f, 1f)),
                2f,
                Wrapper(Vector2F(-6f, 2f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(-4f, 0.4f)),
                -0.2f,
                Wrapper(Vector2F(0.8f, -0.08f)),
            ),
        )

        @JvmStatic
        fun vector2FTimesComplexFArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)) * 5f),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(40f)).toVector2F() * 10f
                ),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-110f)) * 0.1f),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-160f)).toVector2F() * 0.2f
                ),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 0.1f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(200f)) * 4f),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(150f)).toVector2F() * 0.4f
                ),
            ),
        )

        @JvmStatic
        fun vector2FDivVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(90f)).toVector2F() * 5f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(140f)) * 0.4f),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-110f)).toVector2F() * 0.1f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f)) * 20f),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 0.1f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(200f)).toVector2F() * 4f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-110f)) * 0.025f),
            ),
            Arguments.of(
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-30f)).toVector2F()),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f))),
            ),
            Arguments.of(
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-90f)).toVector2F() * 0.8f
                ),
                Wrapper(ComplexF(0f, 0f)),
            ),
        )

        @JvmStatic
        fun vector2FDivFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(2f, 4f)),
                -3f,
                Wrapper(Vector2F(-0.666667f, -1.333333f)),
            ),
            Arguments.of(
                Wrapper(Vector2F(-4f, 0.4f)),
                -0.2f,
                Wrapper(Vector2F(20f, -2f)),
            ),
        )

        @JvmStatic
        fun floatDivVector2FArgs(): List<Arguments> = listOf(
            Arguments.of(
                0f,
                Wrapper(Vector2F(2f, 4f)),
                Wrapper(Vector2F(0f, 0f)),
            ),
            Arguments.of(
                2f,
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(90f)).toVector2F() * 5f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(90f)).toVector2F() * 0.4f
                ),
            ),
            Arguments.of(
                2f,
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-110f)).toVector2F() * 0.1f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-110f)).toVector2F() * 20f
                ),
            ),
            Arguments.of(
                0.1f,
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(200f)).toVector2F() * 4f
                ),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(200f)).toVector2F() * 0.025f
                ),
            ),
        )

        @JvmStatic
        fun vector2FDivComplexFArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)) * 5f),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-140f)).toVector2F() * 0.4f
                ),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 2f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-110f)) * 0.1f),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(60f)).toVector2F() * 20f
                ),
            ),
            Arguments.of(
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(-50f)).toVector2F() * 0.1f
                ),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(200f)) * 4f),
                Wrapper(
                    ComplexF.fromAngle(AngleF.fromDegrees(110f)).toVector2F() * 0.025f
                ),
            ),
        )

        @Suppress("RedundantSuppression", "SpellCheckingInspection")
        @JvmStatic
        fun lerpArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(0f, 10f)),
                Wrapper(Vector2F(10f, 20f)),
                0.5f,
                Wrapper(Vector2F(5f, 15f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-2f, -100f)),
                Wrapper(Vector2F(-10f, 100f)),
                0.3333333f,
                Wrapper(Vector2F(-4.666667f, -33.33334f))
            ),
        )

        @Suppress("RedundantSuppression", "SpellCheckingInspection")
        @JvmStatic
        fun lerpVectorInterpolatorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(0f, 10f)),
                Wrapper(Vector2F(10f, 20f)),
                Wrapper(Vector2F(0.5f, 0.1f)),
                Wrapper(Vector2F(5f, 11f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-2f, -100f)),
                Wrapper(Vector2F(-10f, 100f)),
                Wrapper(Vector2F(0.3333333f, 0.1f)),
                Wrapper(Vector2F(-4.666667f, -80f))
            ),
        )

        @Suppress("RedundantSuppression", "SpellCheckingInspection")
        @JvmStatic
        fun inverseLerpArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(0f, 10f)),
                Wrapper(Vector2F(10f, 20f)),
                Wrapper(Vector2F(5f, 11f)),
                Wrapper(Vector2F(0.5f, 0.1f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-2f, -100f)),
                Wrapper(Vector2F(-10f, 100f)),
                Wrapper(Vector2F(-4.666667f, -80f)),
                Wrapper(Vector2F(0.3333333f, 0.1f))
            ),
        )

        @JvmStatic
        fun maxArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(Vector2F(2f, 10f)),
                Wrapper(Vector2F(3f, 10f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-3f, 0.3f)),
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(Vector2F(0f, 0.3f))
            ),
        )

        @JvmStatic
        fun minArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(3f, 4f)),
                Wrapper(Vector2F(2f, 10f)),
                Wrapper(Vector2F(2f, 4f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-3f, 0.3f)),
                Wrapper(Vector2F(0f, 0f)),
                Wrapper(Vector2F(-3f, 0f))
            ),
        )

        @JvmStatic
        fun closestPointOnLineSegmentArgs(): List<Arguments> {
            val a1 = Wrapper(Vector2F(-2f, 5f))
            val a2 = Wrapper(Vector2F(2f, 5f))
            val segmentAArgs = listOf(
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-1.9f, 5f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-1.9f, 5.1f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-1.9f, 4.9f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    a1, a2, a1, a1
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-2f, 5.1f)), a1
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-2f, 4.9f)), a1
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-2.1f, 5f)), a1
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-2.1f, 5.1f)), a1
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(-2.1f, 4.9f)), a1
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(1.9f, 5f)), Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(1.9f, 5.1f)), Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(1.9f, 4.9f)), Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    a1, a2, a2, a2
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(2f, 5.1f)), a2
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(2f, 4.9f)), a2
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(2.1f, 5f)), a2
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(2.1f, 5.1f)), a2
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(2.1f, 4.9f)), a2
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(0f, 5f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(0f, 5.1f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    a1, a2, Wrapper(Vector2F(0f, 4.9f)), Wrapper(Vector2F(0f, 5f))
                ),
            )
            val b1 = Wrapper(Vector2F(3f, 2f))
            val b2 = Wrapper(Vector2F(3f, -3f))
            val segmentBArgs = listOf(
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(2.9f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3.1f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    b1, b2, b1, b1
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(2.9f, 2f)), b1
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3.1f, 2f)), b1
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3f, 2.1f)), b1
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(2.9f, 2.1f)), b1
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3.1f, 2.1f)), b1
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3f, -2.9f)), Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(2.9f, -2.9f)), Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3.1f, -2.9f)), Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    b1, b2, b2, b2
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(2.9f, -3f)), b2
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3.1f, -3f)), b2
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3f, -3.1f)), b2
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(2.9f, -3.1f)), b2
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3.1f, -3.1f)), b2
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(2.9f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    b1, b2, Wrapper(Vector2F(3.1f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
            )
            val c1 = Wrapper(Vector2F(-4f, 3f))
            val c2 = Wrapper(Vector2F(-2f, -1f))
            val segmentCArgs = listOf(
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-2.134164f, -0.95527864f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-1.9552786f, -0.8658359f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-2.0894427f, -1.0447214f)), c2
                ),
                Arguments.of(
                    c1, c2, c2, c2
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-1.9105573f, -0.95527864f)), c2
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-2.0447214f, -1.1341641f)), c2
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-1.9552786f, -1.0894427f)), c2
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-1.8658359f, -1.0447214f)), c2
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-4.0447216f, 2.865836f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-3.865836f, 2.9552786f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-4.0894427f, 2.9552786f)), c1
                ),
                Arguments.of(
                    c1, c2, c1, c1
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-3.9105573f, 3.0447214f)), c1
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-4.134164f, 3.0447214f)), c1
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-4.0447216f, 3.0894427f)), c1
                ),
                Arguments.of(
                    c1, c2, Wrapper(Vector2F(-3.9552786f, 3.134164f)), c1
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-3.0894427f, 0.95527864f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-3.0f, 1.0f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
                Arguments.of(
                    c1,
                    c2,
                    Wrapper(Vector2F(-2.9105573f, 1.0447214f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
            )
            val d1 = Wrapper(Vector2F(4f, 4f))
            val d2 = Wrapper(Vector2F(4.000001f, 4f))
            val segmentDArgs = listOf(
                Arguments.of(d1, d2, Wrapper(Vector2F(3.9f, 4f)), d1),
                Arguments.of(d1, d2, d1, d1),
                Arguments.of(d1, d2, Wrapper(Vector2F(4.1f, 4f)), d1),
                Arguments.of(d1, d2, Wrapper(Vector2F(3.9f, 4.1f)), d1),
                Arguments.of(d1, d2, Wrapper(Vector2F(4f, 4.1f)), d1),
                Arguments.of(d1, d2, Wrapper(Vector2F(4.1f, 4.1f)), d1),
                Arguments.of(d1, d2, Wrapper(Vector2F(3.9f, 3.9f)), d1),
                Arguments.of(d1, d2, Wrapper(Vector2F(4f, 3.9f)), d1),
                Arguments.of(d1, d2, Wrapper(Vector2F(4.1f, 3.9f)), d1),
                Arguments.of(d1, d2, d2, d2),
            )
            return segmentAArgs + segmentBArgs + segmentCArgs + segmentDArgs
        }
    }
}