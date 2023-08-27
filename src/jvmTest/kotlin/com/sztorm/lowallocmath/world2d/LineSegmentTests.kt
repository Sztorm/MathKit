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