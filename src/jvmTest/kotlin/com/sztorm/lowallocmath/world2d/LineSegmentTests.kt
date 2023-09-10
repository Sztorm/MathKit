package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class LineSegmentTests {
    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        lineSegment: LineSegment,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>
    ) {
        assertApproximation(expectedPointA.value, lineSegment.pointA)
        assertApproximation(expectedPointB.value, lineSegment.pointB)
    }

    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(lineSegment: LineSegment, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, lineSegment.center)

    @ParameterizedTest
    @MethodSource("lengthArgs")
    fun lengthReturnsCorrectValue(lineSegment: LineSegment, expected: Float) =
        assertApproximation(expected, lineSegment.length)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        lineSegment: LineSegment, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, lineSegment.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        lineSegment: LineSegment, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, lineSegment.contains(point.value))

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        lineSegment: LineSegment,
        pointA: Wrapper<Vector2F>,
        pointB: Wrapper<Vector2F>,
        expected: LineSegment
    ) = assertEquals(expected, lineSegment.copy(pointA.value, pointB.value))

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(
        lineSegment: MutableLineSegment, other: Any?, expected: Boolean
    ) = assertEquals(expected, lineSegment == other)

    @ParameterizedTest
    @MethodSource("equalsMutableLineSegmentArgs")
    fun equalsReturnsCorrectValue(
        lineSegment: MutableLineSegment, other: MutableLineSegment, expected: Boolean
    ) = assertEquals(expected, lineSegment.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(lineSegment: MutableLineSegment, other: MutableLineSegment) =
        assertEquals(lineSegment.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(lineSegment: MutableLineSegment, expected: String) =
        assertEquals(expected, lineSegment.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        lineSegment: LineSegment,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<Vector2F>
    ) {
        val (actualComponent1, actualComponent2) = lineSegment

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
    }

    companion object {
        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(2f, 5f))
            ),
            Arguments.of(
                LineSegment(Vector2F(3f, 2f), Vector2F(3f, -3f)),
                Wrapper(Vector2F(3f, 2f)),
                Wrapper(Vector2F(3f, -3f))
            ),
            Arguments.of(
                LineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(-2f, -1f))
            ),
        )

        @JvmStatic
        fun centerArgs(): List<Arguments> = listOf(
            Arguments.of(
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                Wrapper(Vector2F(0f, 5f))
            ),
            Arguments.of(
                LineSegment(Vector2F(3f, 2f), Vector2F(3f, -3f)),
                Wrapper(Vector2F(3f, -0.5f))
            ),
            Arguments.of(
                LineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                Wrapper(Vector2F(-3f, 1f))
            ),
        )

        @JvmStatic
        fun lengthArgs(): List<Arguments> = listOf(
            Arguments.of(
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                4f,
            ),
            Arguments.of(
                LineSegment(Vector2F(3f, 2f), Vector2F(3f, -3f)),
                5f
            ),
            Arguments.of(
                LineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                4.472136f
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val lineSegmentA = LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f))
            val segmentAArgs = listOf(
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-1.9f, 5f)),
                    Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-1.9f, 5.1f)),
                    Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-1.9f, 4.9f)),
                    Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(lineSegmentA.pointA),
                    Wrapper(lineSegmentA.pointA)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2f, 5.1f)),
                    Wrapper(lineSegmentA.pointA)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2f, 4.9f)),
                    Wrapper(lineSegmentA.pointA)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2.1f, 5f)),
                    Wrapper(lineSegmentA.pointA)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2.1f, 5.1f)),
                    Wrapper(lineSegmentA.pointA)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(-2.1f, 4.9f)),
                    Wrapper(lineSegmentA.pointA)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(1.9f, 5f)),
                    Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(1.9f, 5.1f)),
                    Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(1.9f, 4.9f)),
                    Wrapper(Vector2F(1.9f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(lineSegmentA.pointB),
                    Wrapper(lineSegmentA.pointB)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2f, 5.1f)),
                    Wrapper(lineSegmentA.pointB)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2f, 4.9f)),
                    Wrapper(lineSegmentA.pointB)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2.1f, 5f)),
                    Wrapper(lineSegmentA.pointB)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2.1f, 5.1f)),
                    Wrapper(lineSegmentA.pointB)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(2.1f, 4.9f)),
                    Wrapper(lineSegmentA.pointB)
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(0f, 5f)),
                    Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(0f, 5.1f)),
                    Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    lineSegmentA,
                    Wrapper(Vector2F(0f, 4.9f)),
                    Wrapper(Vector2F(0f, 5f))
                ),
            )
            val lineSegmentB = LineSegment(Vector2F(3f, 2f), Vector2F(3f, -3f))
            val segmentBArgs = listOf(
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, 1.9f)),
                    Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, 1.9f)),
                    Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, 1.9f)),
                    Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(lineSegmentB.pointA),
                    Wrapper(lineSegmentB.pointA)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, 2f)),
                    Wrapper(lineSegmentB.pointA)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, 2f)),
                    Wrapper(lineSegmentB.pointA)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, 2.1f)),
                    Wrapper(lineSegmentB.pointA)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, 2.1f)),
                    Wrapper(lineSegmentB.pointA)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, 2.1f)),
                    Wrapper(lineSegmentB.pointA)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, -2.9f)),
                    Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -2.9f)),
                    Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -2.9f)),
                    Wrapper(Vector2F(3f, -2.9f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(lineSegmentB.pointB),
                    Wrapper(lineSegmentB.pointB)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -3f)),
                    Wrapper(lineSegmentB.pointB)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -3f)),
                    Wrapper(lineSegmentB.pointB)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, -3.1f)),
                    Wrapper(lineSegmentB.pointB)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -3.1f)),
                    Wrapper(lineSegmentB.pointB)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -3.1f)),
                    Wrapper(lineSegmentB.pointB)
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3f, -0.5f)),
                    Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(2.9f, -0.5f)),
                    Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    lineSegmentB,
                    Wrapper(Vector2F(3.1f, -0.5f)),
                    Wrapper(Vector2F(3f, -0.5f))
                ),
            )
            val lineSegmentC = LineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f))
            val segmentCArgs = listOf(
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.134164f, -0.95527864f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.9552786f, -0.8658359f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.0894427f, -1.0447214f)),
                    Wrapper(lineSegmentC.pointB)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(lineSegmentC.pointB),
                    Wrapper(lineSegmentC.pointB)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.9105573f, -0.95527864f)),
                    Wrapper(lineSegmentC.pointB)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.0447214f, -1.1341641f)),
                    Wrapper(lineSegmentC.pointB)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.9552786f, -1.0894427f)),
                    Wrapper(lineSegmentC.pointB)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-1.8658359f, -1.0447214f)),
                    Wrapper(lineSegmentC.pointB)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.0447216f, 2.865836f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.865836f, 2.9552786f)),
                    Wrapper(Vector2F(-3.9552786f, 2.9105573f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.0894427f, 2.9552786f)),
                    Wrapper(lineSegmentC.pointA)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(lineSegmentC.pointA),
                    Wrapper(lineSegmentC.pointA)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.9105573f, 3.0447214f)),
                    Wrapper(lineSegmentC.pointA)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.134164f, 3.0447214f)),
                    Wrapper(lineSegmentC.pointA)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-4.0447216f, 3.0894427f)),
                    Wrapper(lineSegmentC.pointA)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.9552786f, 3.134164f)),
                    Wrapper(lineSegmentC.pointA)
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.0894427f, 0.95527864f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-3.0f, 1.0f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
                Arguments.of(
                    lineSegmentC,
                    Wrapper(Vector2F(-2.9105573f, 1.0447214f)),
                    Wrapper(Vector2F(-3.0f, 1.0f))
                ),
            )
            val lineSegmentD = LineSegment(Vector2F(4f, 4f), Vector2F(4.000001f, 4f))
            val segmentDArgs = listOf(
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(3.9f, 4f)), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(lineSegmentD.pointA), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(4.1f, 4f)), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(3.9f, 4.1f)), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(4f, 4.1f)), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(4.1f, 4.1f)), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(3.9f, 3.9f)), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(4f, 3.9f)), Wrapper(lineSegmentD.pointA)
                ),
                Arguments.of(
                    lineSegmentD, Wrapper(Vector2F(4.1f, 3.9f)), Wrapper(lineSegmentD.pointA)
                ),
            )
            return segmentAArgs + segmentBArgs + segmentCArgs + segmentDArgs
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val lineSegmentA = LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f))
            val segmentAArgs = listOf(
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-1.9f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-1.9f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-1.9f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(lineSegmentA.pointA), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2.1f, 5f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2.1f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(-2.1f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(1.9f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(1.9f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(1.9f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(lineSegmentA.pointB), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2.1f, 5f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2.1f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(2.1f, 4.9f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(0f, 5f)), true),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(0f, 5.1f)), false),
                Arguments.of(lineSegmentA, Wrapper(Vector2F(0f, 4.9f)), false),
            )
            val lineSegmentB = LineSegment(Vector2F(3f, 2f), Vector2F(3f, -3f))
            val segmentBArgs = listOf(
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, 1.9f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, 1.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, 1.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(lineSegmentB.pointA), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, 2f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, 2f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, 2.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, 2.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, 2.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, -2.9f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -2.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -2.9f)), false),
                Arguments.of(lineSegmentB, Wrapper(lineSegmentB.pointB), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -3f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -3f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, -3.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -3.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -3.1f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3f, -0.5f)), true),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(2.9f, -0.5f)), false),
                Arguments.of(lineSegmentB, Wrapper(Vector2F(3.1f, -0.5f)), false),
            )
            val lineSegmentC = LineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f))
            val segmentCArgs = listOf(
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.134164f, -0.95527864f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.0447214f, -0.91055727f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.9552786f, -0.8658359f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.0894427f, -1.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(lineSegmentC.pointB), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.9105573f, -0.95527864f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.0447214f, -1.1341641f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.9552786f, -1.0894427f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-1.8658359f, -1.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.0447216f, 2.865836f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.9552786f, 2.9105573f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.865836f, 2.9552786f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.0894427f, 2.9552786f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(lineSegmentC.pointA), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.9105573f, 3.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.134164f, 3.0447214f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-4.0447216f, 3.0894427f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.9552786f, 3.134164f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.0894427f, 0.95527864f)), false
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-3.0f, 1.0f)), true
                ),
                Arguments.of(
                    lineSegmentC, Wrapper(Vector2F(-2.9105573f, 1.0447214f)), false
                ),
            )
            val lineSegmentD = LineSegment(Vector2F(4f, 4f), Vector2F(4.000001f, 4f))
            val segmentDArgs = listOf(
                Arguments.of(lineSegmentD, Wrapper(Vector2F(3.9f, 4f)), false),
                Arguments.of(lineSegmentD, Wrapper(lineSegmentD.pointA), true),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4.1f, 4f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(3.9f, 4.1f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4f, 4.1f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4.1f, 4.1f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(3.9f, 3.9f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4f, 3.9f)), false),
                Arguments.of(lineSegmentD, Wrapper(Vector2F(4.1f, 3.9f)), false),
                Arguments.of(lineSegmentD, Wrapper(lineSegmentD.pointB), true),
            )
            return segmentAArgs + segmentBArgs + segmentCArgs + segmentDArgs
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(2f, 5f)),
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f))
            ),
            Arguments.of(
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(-2f, -1f)),
                LineSegment(Vector2F(-2f, 5f), Vector2F(-2f, -1f))
            ),
            Arguments.of(
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(-2f, -1f)),
                LineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f))
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableLineSegmentArgs() + listOf(
            Arguments.of(
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsMutableLineSegmentArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                true
            ),
            Arguments.of(
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2.1f, 5f)),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f))
            ),
            Arguments.of(
                MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f))
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableLineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                "LineSegment(pointA=${Vector2F(-2f, 5f)}, pointB=${Vector2F(2f, 5f)})"
            ),
            Arguments.of(
                MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                "LineSegment(pointA=${Vector2F(-4f, 3f)}, pointB=${Vector2F(-2f, -1f)})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = listOf(
            Arguments.of(
                LineSegment(Vector2F(-2f, 5f), Vector2F(2f, 5f)),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(Vector2F(2f, 5f))
            ),
            Arguments.of(
                LineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(Vector2F(-2f, -1f))
            ),
        )
    }
}