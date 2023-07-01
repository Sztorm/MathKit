package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class SquareTests {
    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        square: Square,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
        expectedPointD: Wrapper<Vector2F>
    ) {
        assertApproximation(expectedPointA.value, square.pointA)
        assertApproximation(expectedPointB.value, square.pointB)
        assertApproximation(expectedPointC.value, square.pointC)
        assertApproximation(expectedPointD.value, square.pointD)
    }

    @ParameterizedTest
    @MethodSource("widthArgs")
    fun widthReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.width)

    @ParameterizedTest
    @MethodSource("heightArgs")
    fun heightReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.height)

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.area)

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.perimeter)

    @ParameterizedTest
    @MethodSource("interiorAngleArgs")
    fun interiorAngleReturnsCorrectValue(square: Square, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, square.interiorAngle)

    @ParameterizedTest
    @MethodSource("inradiusArgs")
    fun inradiusReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.inradius)

    @ParameterizedTest
    @MethodSource("circumradiusArgs")
    fun circumradiusReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.circumradius)

    companion object {
        @JvmStatic
        fun pointsArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f),
                Wrapper(Vector2F(1.5f, 1.5f)),
                Wrapper(Vector2F(-1.5f, 1.5f)),
                Wrapper(Vector2F(-1.5f, -1.5f)),
                Wrapper(Vector2F(1.5f, -1.5f)),
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                Wrapper(Vector2F(2.267949f, 3.732051f)),
                Wrapper(Vector2F(0.2679491f, 0.26794904f)),
                Wrapper(Vector2F(3.732051f, -1.7320509f)),
                Wrapper(Vector2F(5.732051f, 1.7320509f)),
            ),
        )

        @JvmStatic
        fun widthArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f), 3f
            ),
            Arguments.of(
                Square(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                    sideLength = 3f
                ),
                3f
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                4f
            ),
        )

        @JvmStatic
        fun heightArgs(): List<Arguments> = widthArgs()

        @JvmStatic
        fun areaArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f), 9f
            ),
            Arguments.of(
                Square(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                    sideLength = 3f
                ),
                9f
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                16f
            ),
        )

        @JvmStatic
        fun perimeterArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f), 12f
            ),
            Arguments.of(
                Square(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                    sideLength = 3f
                ),
                12f
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                16f
            ),
        )

        @JvmStatic
        fun interiorAngleArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f),
                Wrapper(AngleF.fromDegrees(90f))
            ),
            Arguments.of(
                Square(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                    sideLength = 3f
                ),
                Wrapper(AngleF.fromDegrees(90f))
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                Wrapper(AngleF.fromDegrees(90f))
            ),
        )

        @JvmStatic
        fun inradiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f), 1.5f
            ),
            Arguments.of(
                Square(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                    sideLength = 3f
                ),
                1.5f
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                2f
            ),
        )

        @JvmStatic
        fun circumradiusArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f), 2.12132f
            ),
            Arguments.of(
                Square(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                    sideLength = 3f
                ),
                2.12132f
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                2.828427f
            ),
        )
    }
}