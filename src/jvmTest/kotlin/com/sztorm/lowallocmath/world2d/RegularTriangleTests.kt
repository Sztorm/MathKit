package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RegularTriangleTests {
    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        triangle: RegularTriangle,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
    ) {
        assertApproximation(expectedPointA.value, triangle.pointA)
        assertApproximation(expectedPointB.value, triangle.pointB)
        assertApproximation(expectedPointC.value, triangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("sideLengthArgs")
    fun sideLengthReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.sideLength)

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.perimeter)

    @ParameterizedTest
    @MethodSource("sideCountArgs")
    fun sideCountReturnsCorrectValue(triangle: RegularTriangle, expected: Int) =
        assertEquals(expected, triangle.sideCount)

    @ParameterizedTest
    @MethodSource("interiorAngleArgs")
    fun interiorAngleReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, triangle.interiorAngle)

    @ParameterizedTest
    @MethodSource("exteriorAngleArgs")
    fun exteriorAngleReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, triangle.exteriorAngle)

    @ParameterizedTest
    @MethodSource("inradiusArgs")
    fun inradiusReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.inradius)

    @ParameterizedTest
    @MethodSource("circumradiusArgs")
    fun circumradiusReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.circumradius)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        triangle: RegularTriangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, triangle.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        triangle: RegularTriangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertEquals(expected, triangle.contains(point.value))

    companion object {
        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                Wrapper(Vector2F(0f, 3.3333333f)),
                Wrapper(Vector2F(-2.886751f, -1.6666667f)),
                Wrapper(Vector2F(2.886751f, -1.6666667f)),
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                Wrapper(Vector2F(5.464102f, -4f)),
                Wrapper(Vector2F(2f, -2f)),
                Wrapper(Vector2F(2f, -6f)),
            ),
        )

        @JvmStatic
        fun sideLengthArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                5.773503f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                5.773503f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                4f
            ),
        )

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                14.433758f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                14.433758f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                6.928203f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                17.320509f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                17.320509f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                12f
            ),
        )

        @JvmStatic
        fun sideCountArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                3
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                3
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                3
            ),
        )

        @JvmStatic
        fun interiorAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                Wrapper(AngleF.fromDegrees(60f))
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                Wrapper(AngleF.fromDegrees(60f))
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                Wrapper(AngleF.fromDegrees(60f))
            ),
        )

        @JvmStatic
        fun exteriorAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                Wrapper(AngleF.fromDegrees(120f))
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                Wrapper(AngleF.fromDegrees(120f))
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                Wrapper(AngleF.fromDegrees(120f))
            ),
        )

        @JvmStatic
        fun inradiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                1.6666667f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                1.6666667f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                1.1547005f
            ),
        )

        @JvmStatic
        fun circumradiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                RegularTriangle(
                    center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 5.773503f
                ),
                3.3333333f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(-4f, 2f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    sideLength = 5.773503f
                ),
                3.3333333f
            ),
            Arguments.of(
                RegularTriangle(
                    center = Vector2F(3.1547005f, -4f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                    sideLength = 4f
                ),
                2.309401f
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val pointA = Vector2F(5.464102f, -4f)
            val pointB = Vector2F(2f, -2f)
            val pointC = Vector2F(2f, -6f)
            val center: Vector2F = (pointA + pointB + pointC) / 3f
            val triangle = RegularTriangle(
                center,
                rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            return listOf(
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(6f, -3.25f)),
                    Wrapper(Vector2F(5.464102f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(5.5f, -3f)),
                    Wrapper(Vector2F(5.058013f, -3.7655447f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.5f, -2f)),
                    Wrapper(Vector2F(3.125f, -2.649519f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.1f, -1.5f)),
                    Wrapper(Vector2F(2f, -2f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1.5f, -1.75f)),
                    Wrapper(Vector2F(2f, -2f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1f, -2.75f)),
                    Wrapper(Vector2F(2f, -2.75f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1f, -4.5f)),
                    Wrapper(Vector2F(2f, -4.5f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(1f, -6.25f)),
                    Wrapper(Vector2F(2f, -6f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2f, -7f)),
                    Wrapper(Vector2F(2f, -6f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.5f, -6.5f)),
                    Wrapper(Vector2F(2.9084935f, -5.475481f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(5f, -5.75f)),
                    Wrapper(Vector2F(4.3582535f, -4.638462f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(6f, -4.5f)),
                    Wrapper(Vector2F(5.464102f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(5.364102f, -4f)),
                    Wrapper(Vector2F(5.364102f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.682051f, -3.0866027f)),
                    Wrapper(Vector2F(3.682051f, -3.0866027f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.05f, -2.0866024f)),
                    Wrapper(Vector2F(2.05f, -2.0866024f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.1f, -4f)),
                    Wrapper(Vector2F(2.1f, -4f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(2.05f, -5.913398f)),
                    Wrapper(Vector2F(2.05f, -5.913398f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(Vector2F(3.682051f, -4.9133973f)),
                    Wrapper(Vector2F(3.682051f, -4.9133973f))
                ),
                Arguments.of(
                    triangle,
                    Wrapper(center),
                    Wrapper(center)
                ),
            )
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val pointA = Vector2F(5.464102f, -4f)
            val pointB = Vector2F(2f, -2f)
            val pointC = Vector2F(2f, -6f)
            val center: Vector2F = (pointA + pointB + pointC) / 3f
            val triangle = RegularTriangle(
                center,
                rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            return listOf(
                Arguments.of(triangle, Wrapper(Vector2F(5.364102f, -4f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(5.564102f, -4f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(3.682051f, -3.0866027f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(3.7820508f, -2.9133973f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(2.05f, -2.0866024f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(1.95f, -1.9133976f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(2.1f, -4f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(1.9f, -4f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(2.05f, -5.913398f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(1.95f, -6.086602f)), false),
                Arguments.of(triangle, Wrapper(Vector2F(3.682051f, -4.9133973f)), true),
                Arguments.of(triangle, Wrapper(Vector2F(3.7820508f, -5.0866027f)), false),
                Arguments.of(triangle, Wrapper(center), true),
            )
        }
    }
}