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
    @MethodSource("sideLengthArgs")
    fun sideLengthReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.sideLength)

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
    @MethodSource("sideCountArgs")
    fun sideCountReturnsCorrectValue(square: Square, expected: Int) =
        assertEquals(expected, square.sideCount)

    @ParameterizedTest
    @MethodSource("interiorAngleArgs")
    fun interiorAngleReturnsCorrectValue(square: Square, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, square.interiorAngle)

    @ParameterizedTest
    @MethodSource("exteriorAngleArgs")
    fun exteriorAngleReturnsCorrectValue(square: Square, expected: Wrapper<AngleF>) =
        assertApproximation(expected.value, square.exteriorAngle)

    @ParameterizedTest
    @MethodSource("inradiusArgs")
    fun inradiusReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.inradius)

    @ParameterizedTest
    @MethodSource("circumradiusArgs")
    fun circumradiusReturnsCorrectValue(square: Square, expected: Float) =
        assertApproximation(expected, square.circumradius)

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        square: Square, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertApproximation(expected.value, square.closestPointTo(point.value))

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(square: Square, point: Wrapper<Vector2F>, expected: Boolean) =
        assertEquals(expected, square.contains(point.value))

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
        fun sideLengthArgs(): List<Arguments> = listOf(
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
        fun widthArgs(): List<Arguments> = sideLengthArgs()

        @JvmStatic
        fun heightArgs(): List<Arguments> = sideLengthArgs()

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
        fun sideCountArgs(): List<Arguments> = listOf(
            Arguments.of(
                Square(center = Vector2F.ZERO, rotation = ComplexF.ONE, sideLength = 3f), 4
            ),
            Arguments.of(
                Square(
                    center = Vector2F.ZERO,
                    rotation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                    sideLength = 3f
                ),
                4
            ),
            Arguments.of(
                Square(
                    center = Vector2F(3f, 1f),
                    rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                4
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
        fun exteriorAngleArgs(): List<Arguments> = interiorAngleArgs()

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

        @Suppress("UNUSED_VARIABLE")
        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val pointA = Vector2F(2.267949f, 3.732051f)
            val pointB = Vector2F(0.2679491f, 0.26794904f)
            val pointC = Vector2F(3.732051f, -1.7320509f)
            val pointD = Vector2F(5.732051f, 1.7320509f)
            val center: Vector2F = (pointA + pointC) * 0.5f
            val square = Square(
                center,
                rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            return listOf(
                Arguments.of(
                    square,
                    Wrapper(Vector2F(3.9823222f, 2.7014322f)),
                    Wrapper(Vector2F(3.9823222f, 2.7014322f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(4.017678f, 2.7626696f)),
                    Wrapper(Vector2F(4.0f, 2.732051f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(2.28089f, 3.6837547f)),
                    Wrapper(Vector2F(2.28089f, 3.6837547f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(2.2550082f, 3.780347f)),
                    Wrapper(Vector2F(2.267949f, 3.732051f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(2.38258f, 4.1108f)),
                    Wrapper(Vector2F(2.267949f, 3.732051f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(1.2985678f, 1.9823223f)),
                    Wrapper(Vector2F(1.2985678f, 1.9823223f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(1.2373304f, 2.0176778f)),
                    Wrapper(Vector2F(1.2679491f, 2.0f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(0.31624532f, 0.28089f)),
                    Wrapper(Vector2F(0.31624532f, 0.28089f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(0.21965289f, 0.2550081f)),
                    Wrapper(Vector2F(0.2679491f, 0.26794904f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(0f, 0.3f)),
                    Wrapper(Vector2F(0.2679491f, 0.26794904f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(2.0176778f, -0.7014322f)),
                    Wrapper(Vector2F(2.0176778f, -0.7014322f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(1.9823223f, -0.76266956f)),
                    Wrapper(Vector2F(2.0f, -0.7320509f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(3.71911f, -1.6837547f)),
                    Wrapper(Vector2F(3.71911f, -1.6837547f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(3.7449918f, -1.7803471f)),
                    Wrapper(Vector2F(3.732051f, -1.7320509f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(3.65837f, -2.28868f)),
                    Wrapper(Vector2F(3.732051f, -1.7320509f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(4.701432f, 0.017677665f)),
                    Wrapper(Vector2F(4.701432f, 0.017677665f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(x = 4.7626696f, y = -0.017677665f)),
                    Wrapper(Vector2F(x = 4.732051f, y = 0.0f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(5.683755f, 1.71911f)),
                    Wrapper(Vector2F(5.683755f, 1.71911f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(6.34639f, 1.57736f)),
                    Wrapper(Vector2F(5.732051f, 1.7320509f))
                ),
                Arguments.of(
                    square,
                    Wrapper(Vector2F(5.780347f, 1.7449918f)),
                    Wrapper(Vector2F(5.732051f, 1.7320509f))
                ),
                Arguments.of(
                    square,
                    Wrapper(center),
                    Wrapper(center)
                ),
            )
        }

        @Suppress("UNUSED_VARIABLE")
        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val pointA = Vector2F(2.267949f, 3.732051f)
            val pointB = Vector2F(0.2679491f, 0.26794904f)
            val pointC = Vector2F(3.732051f, -1.7320509f)
            val pointD = Vector2F(5.732051f, 1.7320509f)
            val center: Vector2F = (pointA + pointC) * 0.5f
            val square = Square(
                center,
                rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            return listOf(
                Arguments.of(square, Wrapper(Vector2F(3.9823222f, 2.7014322f)), true),
                Arguments.of(square, Wrapper(Vector2F(4.017678f, 2.7626696f)), false),
                Arguments.of(square, Wrapper(Vector2F(2.28089f, 3.6837547f)), true),
                Arguments.of(square, Wrapper(Vector2F(2.2550082f, 3.780347f)), false),
                Arguments.of(square, Wrapper(Vector2F(1.2985678f, 1.9823223f)), true),
                Arguments.of(square, Wrapper(Vector2F(1.2373304f, 2.0176778f)), false),
                Arguments.of(square, Wrapper(Vector2F(0.31624532f, 0.28089f)), true),
                Arguments.of(square, Wrapper(Vector2F(0.21965289f, 0.2550081f)), false),
                Arguments.of(square, Wrapper(Vector2F(2.0176778f, -0.7014322f)), true),
                Arguments.of(square, Wrapper(Vector2F(1.9823223f, -0.76266956f)), false),
                Arguments.of(square, Wrapper(Vector2F(3.71911f, -1.6837547f)), true),
                Arguments.of(square, Wrapper(Vector2F(3.7449918f, -1.7803471f)), false),
                Arguments.of(square, Wrapper(Vector2F(4.701432f, 0.017677665f)), true),
                Arguments.of(square, Wrapper(Vector2F(4.7626696f, -0.017677665f)), false),
                Arguments.of(square, Wrapper(Vector2F(5.683755f, 1.71911f)), true),
                Arguments.of(square, Wrapper(Vector2F(5.780347f, 1.7449918f)), false),
                Arguments.of(square, Wrapper(center), true),
            )
        }
    }
}