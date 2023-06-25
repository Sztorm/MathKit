package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

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
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.perimeter)

    @ParameterizedTest
    @MethodSource("interiorAngleArgs")
    fun interiorAngleReturnsCorrectValue(triangle: RegularTriangle, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, triangle.interiorAngle)

    @ParameterizedTest
    @MethodSource("inradiusArgs")
    fun inradiusReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.inradius)

    @ParameterizedTest
    @MethodSource("circumradiusArgs")
    fun circumradiusReturnsCorrectValue(triangle: RegularTriangle, expected: Float) =
        assertApproximation(expected, triangle.circumradius)

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
    }
}