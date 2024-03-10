package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultSquare
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class SquareTests {
    @ParameterizedTest
    @MethodSource("constructorThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        sideLength: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableSquare(center.value, orientation.value, sideLength)
        }
    }

    @ParameterizedTest
    @MethodSource("constructorDoesNotThrowExceptionArgs")
    fun constructorDoesNotThrowException(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, sideLength: Float,
    ) {
        assertDoesNotThrow {
            MutableSquare(center.value, orientation.value, sideLength)
        }
    }

    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(square: Square, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(square) {
            assertApproximation(expected.value, square.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(square: Square, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(square) {
            assertApproximation(expected.value, square.orientation)
        }

    @ParameterizedTest
    @MethodSource("sideLengthArgs")
    fun sideLengthReturnsCorrectValue(square: Square, expected: Float) =
        assertImmutabilityOf(square) {
            assertApproximation(expected, square.sideLength)
        }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        square: Square,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
        expectedPointD: Wrapper<Vector2F>
    ) = assertImmutabilityOf(square) {
        assertApproximation(expectedPointA.value, square.pointA)
        assertApproximation(expectedPointB.value, square.pointB)
        assertApproximation(expectedPointC.value, square.pointC)
        assertApproximation(expectedPointD.value, square.pointD)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(square: Square, expected: Float) =
        assertImmutabilityOf(square) {
            assertApproximation(expected, square.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(square: Square, expected: Float) =
        assertImmutabilityOf(square) {
            assertApproximation(expected, square.perimeter)
        }

    @ParameterizedTest
    @MethodSource("widthArgs")
    fun widthReturnsCorrectValue(square: Square, expected: Float) =
        assertImmutabilityOf(square) {
            assertApproximation(expected, square.width)
        }

    @ParameterizedTest
    @MethodSource("heightArgs")
    fun heightReturnsCorrectValue(square: Square, expected: Float) =
        assertImmutabilityOf(square) {
            assertApproximation(expected, square.height)
        }

    @ParameterizedTest
    @MethodSource("sideCountArgs")
    fun sideCountReturnsCorrectValue(square: Square, expected: Int) =
        assertImmutabilityOf(square) {
            assertEquals(expected, square.sideCount)
        }

    @ParameterizedTest
    @MethodSource("interiorAngleArgs")
    fun interiorAngleReturnsCorrectValue(square: Square, expected: Wrapper<AngleF>) =
        assertImmutabilityOf(square) {
            assertApproximation(expected.value, square.interiorAngle)
        }

    @ParameterizedTest
    @MethodSource("exteriorAngleArgs")
    fun exteriorAngleReturnsCorrectValue(square: Square, expected: Wrapper<AngleF>) =
        assertImmutabilityOf(square) {
            assertApproximation(expected.value, square.exteriorAngle)
        }

    @ParameterizedTest
    @MethodSource("inradiusArgs")
    fun inradiusReturnsCorrectValue(square: Square, expected: Float) =
        assertImmutabilityOf(square) {
            assertApproximation(expected, square.inradius)
        }

    @ParameterizedTest
    @MethodSource("circumradiusArgs")
    fun circumradiusReturnsCorrectValue(square: Square, expected: Float) =
        assertImmutabilityOf(square) {
            assertApproximation(expected, square.circumradius)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(square: Square, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(square) {
            assertApproximation(expected.value, square.position)
        }

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesSquareCorrectly(
        square: MutableSquare,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        sideLength: Float,
        expected: MutableSquare
    ) = assertEquals(expected, square.apply { set(center.value, orientation.value, sideLength) })

    @ParameterizedTest
    @MethodSource("setThrowsExceptionArgs")
    fun setThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        sideLength: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val square = MutableSquare(
            center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
        )
        assertThrows(expectedExceptionClass) {
            square.set(center.value, orientation.value, sideLength)
        }
    }

    @ParameterizedTest
    @MethodSource("setDoesNotThrowExceptionArgs")
    fun setDoesNotThrowException(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, sideLength: Float,
    ) {
        val square = MutableSquare(
            center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
        )
        assertDoesNotThrow {
            square.set(center.value, orientation.value, sideLength)
        }
    }

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(square: Square, to: Square, by: Float, expected: Square) =
        assertImmutabilityOf(square) {
            assertImmutabilityOf(to) {
                assertApproximation(expected, square.interpolated(to, by))
            }
        }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesSquareCorrectly(
        square: MutableSquare, from: Square, to: Square, by: Float, expected: MutableSquare
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, square.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        square: Square, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(square) {
        assertApproximation(expected.value, square.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(square: Square, point: Wrapper<Vector2F>, expected: Boolean) =
        assertImmutabilityOf(square) {
            assertEquals(expected, square.contains(point.value))
        }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        square: Square,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        sideLength: Float,
        expected: Square
    ) = assertEquals(expected, square.copy(center.value, orientation.value, sideLength))

    @ParameterizedTest
    @MethodSource("copyThrowsExceptionArgs")
    fun copyThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        sideLength: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val square = MutableSquare(
            center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
        )
        assertThrows(expectedExceptionClass) {
            square.copy(center.value, orientation.value, sideLength)
        }
    }

    @ParameterizedTest
    @MethodSource("copyDoesNotThrowExceptionArgs")
    fun copyDoesNotThrowException(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, sideLength: Float,
    ) {
        val square = MutableSquare(
            center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f
        )
        assertDoesNotThrow {
            square.copy(center.value, orientation.value, sideLength)
        }
    }

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(
        square: MutableSquare, other: Any?, expected: Boolean
    ) = assertImmutabilityOf(square) {
        assertEquals(expected, square == other)
    }

    @ParameterizedTest
    @MethodSource("equalsMutableSquareArgs")
    fun equalsReturnsCorrectValue(
        square: MutableSquare, other: MutableSquare, expected: Boolean
    ) = assertImmutabilityOf(square) {
        assertImmutabilityOf(other) {
            assertEquals(expected, square.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(square: MutableSquare, other: MutableSquare) =
        assertImmutabilityOf(square) {
            assertImmutabilityOf(other) {
                assertEquals(square.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(square: MutableSquare, expected: String) =
        assertImmutabilityOf(square) {
            assertEquals(expected, square.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        square: Square,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float
    ) = assertImmutabilityOf(square) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float
        ) = square

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Square, b: Square, tolerance: Float = 0.00001f): Boolean =
            a.center.isApproximately(b.center, tolerance) and
                    a.orientation.isApproximately(b.orientation, tolerance) and
                    a.sideLength.isApproximately(b.sideLength, tolerance) and
                    a.pointA.isApproximately(b.pointA, tolerance) and
                    a.pointB.isApproximately(b.pointB, tolerance) and
                    a.pointC.isApproximately(b.pointC, tolerance) and
                    a.pointD.isApproximately(b.pointD, tolerance)

        @JvmStatic
        fun clone(square: Square) = square.copy()

        @JvmStatic
        fun List<Arguments>.mapSquaresToDefaultSquares() = map { args ->
            val argArray = args.get().map {
                if (it is Square) DefaultSquare(it.center, it.orientation, it.sideLength)
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -0.1f,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun constructorDoesNotThrowExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                0f
            ),
        )

        @JvmStatic
        fun centerArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f))
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(8f, -2f))
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(60f)))
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-135f)))
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun sideLengthArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    3f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                        sideLength = 3f
                    ),
                    3f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    4f
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    Wrapper(Vector2F(1.5f, 1.5f)),
                    Wrapper(Vector2F(-1.5f, 1.5f)),
                    Wrapper(Vector2F(-1.5f, -1.5f)),
                    Wrapper(Vector2F(1.5f, -1.5f))
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(2.267949f, 3.732051f)),
                    Wrapper(Vector2F(0.2679491f, 0.26794904f)),
                    Wrapper(Vector2F(3.732051f, -1.7320509f)),
                    Wrapper(Vector2F(5.732051f, 1.7320509f))
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    9f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                        sideLength = 3f
                    ),
                    9f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    16f
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    12f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                        sideLength = 3f
                    ),
                    12f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    16f
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun widthArgs(): List<Arguments> = sideLengthArgs()

        @JvmStatic
        fun heightArgs(): List<Arguments> = sideLengthArgs()

        @JvmStatic
        fun sideCountArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    4
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                        sideLength = 3f
                    ),
                    4
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    4
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun interiorAngleArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(90f))
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(90f))
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(90f))
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun exteriorAngleArgs(): List<Arguments> = interiorAngleArgs()

        @JvmStatic
        fun inradiusArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    1.5f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                        sideLength = 3f
                    ),
                    1.5f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    2f
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun circumradiusArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 3f
                    ),
                    2.12132f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.fromAngle(AngleF.fromRadians(-240f)),
                        sideLength = 3f
                    ),
                    2.12132f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    2.828427f
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                Wrapper(Vector2F(3f, 1f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(60f))),
                4f,
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                )
            ),
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                Wrapper(Vector2F(8f, -2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(60f))),
                3f,
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 3f
                )
            ),
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                Wrapper(Vector2F(8f, -2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-135f))),
                3f,
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                )
            ),
        )

        @JvmStatic
        fun setThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun setDoesNotThrowExceptionArgs(): List<Arguments> =
            constructorDoesNotThrowExceptionArgs()

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableSquareArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableSquare(center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                0.5f,
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                )
            ),
            Arguments.of(
                MutableSquare(center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                ),
                0f,
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                )
            ),
            Arguments.of(
                MutableSquare(center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                ),
                1f,
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                )
            ),
            Arguments.of(
                MutableSquare(center = Vector2F.ZERO, orientation = ComplexF.ONE, sideLength = 1f),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                ),
                0.5f,
                MutableSquare(
                    center = Vector2F(5.5f, -0.5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(142.5f)),
                    sideLength = 3.5f
                )
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
            val square = MutableSquare(
                center,
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            val mutableSquareArgs = listOf(
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
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @Suppress("UNUSED_VARIABLE")
        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val pointA = Vector2F(2.267949f, 3.732051f)
            val pointB = Vector2F(0.2679491f, 0.26794904f)
            val pointC = Vector2F(3.732051f, -1.7320509f)
            val pointD = Vector2F(5.732051f, 1.7320509f)
            val center: Vector2F = (pointA + pointC) * 0.5f
            val square = MutableSquare(
                center,
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)) *
                        ComplexF(pointA.x - center.x, pointA.y - center.y).normalized,
                sideLength = pointA.distanceTo(pointB)
            )
            val mutableSquareArgs = listOf(
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
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableSquareArgs = setArgs()
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun copyThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun copyDoesNotThrowExceptionArgs(): List<Arguments> =
            constructorDoesNotThrowExceptionArgs()

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableSquareArgs() + listOf(
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                DefaultSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                true
            ),
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                DefaultSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableSquareArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                true
            ),
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                )
            ),
            Arguments.of(
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                ),
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableSquare(
                    center = Vector2F(3f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                    sideLength = 4f
                ),
                "Square(" +
                        "center=${Vector2F(3f, 1f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(60f))}, " +
                        "sideLength=${4f})"
            ),
            Arguments.of(
                MutableSquare(
                    center = Vector2F(8f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                    sideLength = 3f
                ),
                "Square(" +
                        "center=${Vector2F(8f, -2f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(-135f))}, " +
                        "sideLength=${3f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableSquareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(60f))),
                    4f
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(8f, -2f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-135f))),
                    3f
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs
            ).flatten()
        }
    }
}