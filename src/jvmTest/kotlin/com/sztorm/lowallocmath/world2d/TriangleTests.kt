package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TriangleTests {
    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        triangle: TriangleShape,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>
    ) {
        assertApproximation(expectedPointA.value, triangle.pointA)
        assertApproximation(expectedPointB.value, triangle.pointB)
        assertApproximation(expectedPointC.value, triangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(triangle: TriangleShape, expected: Float) =
        assertApproximation(expected, triangle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(triangle: TriangleShape, expected: Float) =
        assertApproximation(expected, triangle.perimeter)

    @ParameterizedTest
    @MethodSource("centroidArgs")
    fun centroidReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.centroid)

    @ParameterizedTest
    @MethodSource("orthocenterArgs")
    fun orthocenterReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.orthocenter)

    @ParameterizedTest
    @MethodSource("incenterArgs")
    fun incenterReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.incenter)

    @ParameterizedTest
    @MethodSource("circumcenterArgs")
    fun circumcenterReturnsCorrectValue(triangle: TriangleShape, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, triangle.circumcenter)

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        triangle: Triangle,
        pointA: Wrapper<Vector2F>,
        pointB: Wrapper<Vector2F>,
        pointC: Wrapper<Vector2F>,
        expected: Triangle
    ) = assertEquals(expected, triangle.copy(pointA.value, pointB.value, pointC.value))

    @ParameterizedTest
    @MethodSource("equalsArgs")
    fun equalsReturnsCorrectValue(triangle: MutableTriangle, other: Any?, expected: Boolean) =
        assertEquals(expected, triangle == other)

    @ParameterizedTest
    @MethodSource("equalsMutableTriangleArgs")
    fun equalsReturnsCorrectValue(
        triangle: MutableTriangle, other: MutableTriangle, expected: Boolean
    ) = assertEquals(expected, triangle.equals(other))

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(triangle: MutableTriangle, other: MutableTriangle) =
        assertEquals(triangle.hashCode(), other.hashCode())

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(triangle: MutableTriangle, expected: String) =
        assertEquals(expected, triangle.toString())

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        triangle: Triangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<Vector2F>,
        expectedComponent3: Wrapper<Vector2F>
    ) {
        val (actualComponent1, actualComponent2, actualComponent3) = triangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3.value, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(2f, 2f)),
                Wrapper(Vector2F(1f, 5f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -2.535898f)),
                Wrapper(Vector2F(10f, -6f)),
                Wrapper(Vector2F(6f, -6f))
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                9f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                9.5f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                6.928203f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                14.99323f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                16.73888f
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                12f
            ),
        )

        @JvmStatic
        fun centroidArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-0.3333333f, 3f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun orthocenterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(1f, 3.6666667f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-5.947368f, -4.263158f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun incenterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(0.3343371f, 3.200542f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(-1.7160178f, -2.544134f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun circumcenterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-1f, 2.6666667f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                Wrapper(Vector2F(0.9736842f, -1.868421f))
            ),
            Arguments.of(
                Triangle(
                    Vector2F(8f, -2.535898f),
                    Vector2F(10f, -6f),
                    Vector2F(6f, -6f)
                ),
                Wrapper(Vector2F(8f, -4.8453f))
            ),
        )

        @JvmStatic
        fun copyArgs(): List<Arguments> = listOf(
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(2f, 2f)),
                Wrapper(Vector2F(1f, 5f)),
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f))
            ),
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f)),
                Triangle(
                    Vector2F(-4f, 2f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                )
            ),
            Arguments.of(
                Triangle(Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)),
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f)),
                Triangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                )
            ),
        )

        @JvmStatic
        fun equalsArgs(): List<Arguments> = equalsMutableTriangleArgs() + listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                null,
                false
            ),
        )

        @JvmStatic
        fun equalsMutableTriangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                true
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1.1f, 5f)
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                ),
                "Triangle(" +
                        "pointA=${Vector2F(-4f, 2f)}, " +
                        "pointB=${Vector2F(2f, 2f)}, " +
                        "pointC=${Vector2F(1f, 5f)})"
            ),
            Arguments.of(
                MutableTriangle(
                    Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                ),
                "Triangle(" +
                        "pointA=${Vector2F(-2f, 1f)}, " +
                        "pointB=${Vector2F(-3f, -3f)}, " +
                        "pointC=${Vector2F(1f, -6f)})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> = pointsArgs()
    }
}