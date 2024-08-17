package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.CircleTests.Companion.mapCirclesToDefaultCircles
import com.sztorm.lowallocmath.euclidean2d.RayTests.Companion.mapRaysToDefaultRays
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultAnnulus
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.utils.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class AnnulusTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectAnnulus(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float
    ) {
        val mutableAnnulus = MutableAnnulus(
            center.value, orientation.value, outerRadius, innerRadius
        )
        val annulus = Annulus(center.value, orientation.value, outerRadius, innerRadius)

        assertEquals(center.value, mutableAnnulus.center)
        assertEquals(orientation.value, mutableAnnulus.orientation)
        assertEquals(outerRadius, mutableAnnulus.outerRadius)
        assertEquals(innerRadius, mutableAnnulus.innerRadius)

        assertEquals(center.value, annulus.center)
        assertEquals(orientation.value, annulus.orientation)
        assertEquals(outerRadius, annulus.outerRadius)
        assertEquals(innerRadius, annulus.innerRadius)
    }

    @ParameterizedTest
    @MethodSource("constructorThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableAnnulus(center.value, orientation.value, outerRadius, innerRadius)
        }
        assertThrows(expectedExceptionClass) {
            Annulus(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(annulus: Annulus, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected.value, annulus.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(annulus: Annulus, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected.value, annulus.orientation)
        }

    @ParameterizedTest
    @MethodSource("outerRadiusArgs")
    fun outerRadiusReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.outerRadius)
        }

    @ParameterizedTest
    @MethodSource("innerRadiusArgs")
    fun innerRadiusReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.innerRadius)
        }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.perimeter)
        }

    @ParameterizedTest
    @MethodSource("widthArgs")
    fun widthReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.width)
        }

    @ParameterizedTest
    @MethodSource("outerDiameterArgs")
    fun outerDiameterReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.outerDiameter)
        }

    @ParameterizedTest
    @MethodSource("innerDiameterArgs")
    fun innerDiameterReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.innerDiameter)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(annulus: Annulus, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected.value, annulus.position)
        }

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesAnnulusCorrectly(annulus: MutableAnnulus, expected: MutableAnnulus) =
        assertApproximation(expected, annulus.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expected: MutableAnnulus
    ) = assertEquals(
        expected,
        annulus.apply { set(center.value, orientation.value, outerRadius, innerRadius) }
    )

    @ParameterizedTest
    @MethodSource("setThrowsExceptionArgs")
    fun setThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertThrows(expectedExceptionClass) {
            annulus.set(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("setDoesNotThrowExceptionArgs")
    fun setDoesNotThrowException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertDoesNotThrow {
            annulus.set(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        annulus: Annulus, to: Annulus, by: Float, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, annulus.interpolated(to, by))
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, from: Annulus, to: Annulus, by: Float, expected: MutableAnnulus
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, annulus.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected.value, annulus.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(annulus: Annulus, otherAnnulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(otherAnnulus) {
                assertEquals(expected, annulus.intersects(otherAnnulus))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(annulus: Annulus, circle: Circle, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(circle) {
                assertEquals(expected, annulus.intersects(circle))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsReturnsCorrectValue(annulus: Annulus, ray: Ray, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(ray) {
                assertEquals(expected, annulus.intersects(ray))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(annulus) {
        assertEquals(expected, annulus.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("containsAnnulusArgs")
    fun containsReturnsCorrectValue(annulus: Annulus, otherAnnulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(otherAnnulus) {
                assertEquals(expected, annulus.contains(otherAnnulus))
            }
        }

    @ParameterizedTest
    @MethodSource("containsCircleArgs")
    fun containsReturnsCorrectValue(annulus: Annulus, circle: Circle, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(circle) {
                assertEquals(expected, annulus.contains(circle))
            }
        }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        annulus: Annulus,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expected: Annulus
    ) = assertEquals(
        expected, annulus.copy(center.value, orientation.value, outerRadius, innerRadius)
    )

    @ParameterizedTest
    @MethodSource("copyThrowsExceptionArgs")
    fun copyThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertThrows(expectedExceptionClass) {
            annulus.copy(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("copyDoesNotThrowExceptionArgs")
    fun copyDoesNotThrowException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertDoesNotThrow {
            annulus.copy(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(annulus: MutableAnnulus, other: Any?, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertEquals(expected, annulus == other)
        }

    @ParameterizedTest
    @MethodSource("equalsMutableAnnulusArgs")
    fun equalsReturnsCorrectValue(
        annulus: MutableAnnulus, other: MutableAnnulus, expected: Boolean
    ) = assertImmutabilityOf(annulus) {
        assertImmutabilityOf(other) {
            assertEquals(expected, annulus.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(annulus: MutableAnnulus, other: MutableAnnulus) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(other) {
                assertEquals(annulus.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(annulus: MutableAnnulus, expected: String) =
        assertImmutabilityOf(annulus) {
            assertEquals(expected, annulus.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        annulus: Annulus,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Float
    ) = assertImmutabilityOf(annulus) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float,
            actualComponent4: Float
        ) = annulus

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Annulus, b: Annulus, tolerance: Float = 0.00001f): Boolean =
            a.center.isApproximately(b.center, tolerance) and
                    a.orientation.isApproximately(b.orientation, tolerance) and
                    a.outerRadius.isApproximately(b.outerRadius, tolerance) and
                    a.innerRadius.isApproximately(b.innerRadius, tolerance)

        @JvmStatic
        fun areEqual(a: Annulus, b: Annulus): Boolean =
            (a.center == b.center) and
                    (a.orientation == b.orientation) and
                    (a.outerRadius == b.outerRadius) and
                    (a.innerRadius == b.innerRadius)

        @JvmStatic
        fun clone(annulus: Annulus) = annulus.copy()

        @JvmStatic
        fun List<Arguments>.mapAnnulusesToDefaultAnnuluses() = map { args ->
            val argArray = args.get().map {
                if (it is Annulus) DefaultAnnulus(
                    it.center, it.orientation, it.outerRadius, it.innerRadius
                )
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                1f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                2f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                0f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                2f
            ),
            Arguments.of(
                Wrapper(Vector2F(6f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f))),
                8f,
                1f
            ),
        )

        @JvmStatic
        fun constructorThrowsExceptionArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                2f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                -1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                -0.5f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                2.1f,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun centerArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f))
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, 3f))
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f)))
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f)))
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun outerRadiusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    4f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    8f
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun innerRadiusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    2f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    1f
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    37.6991f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    37.6991f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 2.5f,
                        innerRadius = 1.5f
                    ),
                    12.5664f
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    37.6991f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    37.6991f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 2.5f,
                        innerRadius = 1.5f
                    ),
                    25.1327f
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun widthArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    2f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    2f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 2.5f,
                        innerRadius = 1.5f
                    ),
                    1f
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun outerDiameterArgs(): List<Arguments> = outerRadiusArgs().map {
            val args = it.get()
            Arguments.of(args[0], args[1] as Float * 2f)
        }

        @JvmStatic
        fun innerDiameterArgs(): List<Arguments> = innerRadiusArgs().map {
            val args = it.get()
            Arguments.of(args[0], args[1] as Float * 2f)
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun calibrateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 3f, AngleF.fromDegrees(-45f).radians
                    ),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 0.3f, AngleF.fromDegrees(-45f).radians
                    ),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ZERO,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(-1f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                2f,
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(6f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                1f,
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 1f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                Wrapper(Vector2F(6f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f))),
                8f,
                1f,
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                )
            ),
        )

        @JvmStatic
        fun setThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun setDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableAnnulusArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 1f,
                    innerRadius = 0.5f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                0.5f,
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 1f,
                    innerRadius = 0.5f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                0f,
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 1f,
                    innerRadius = 0.5f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                1f,
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    outerRadius = 1f,
                    innerRadius = 0.5f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                0.5f,
                MutableAnnulus(
                    center = Vector2F(2.5f, 2.5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-37.5f)),
                    outerRadius = 6f,
                    innerRadius = 1.5f
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-2.9f, 2f)),
                    Wrapper(Vector2F(-3f, 2f))
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-3.1f, 2f)),
                    Wrapper(Vector2F(-3.1f, 2f))
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4.9f, 2f)),
                    Wrapper(Vector2F(-4.9f, 2f))
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-5.1f, 2f)),
                    Wrapper(Vector2F(-5f, 2f))
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.ONE,
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-5f, 6f)),
                    Wrapper(Vector2F(-3.828429f, 4.828429f))
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsAnnulusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-2f, 2f),
                        outerRadius = 1.01f,
                        orientation = ComplexF.ONE,
                        innerRadius = 0.5f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-2f, 2f),
                        outerRadius = 0.99f,
                        orientation = ComplexF.ONE,
                        innerRadius = 0.5f
                    ),
                    false
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-6f, 2f),
                        outerRadius = 1.01f,
                        orientation = ComplexF.ONE,
                        innerRadius = 0.5f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-6f, 2f),
                        outerRadius = 0.99f,
                        orientation = ComplexF.ONE,
                        innerRadius = 0.5f
                    ),
                    false
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(0f, 2f),
                        outerRadius = 7f,
                        orientation = ComplexF.ONE,
                        innerRadius = 4.9f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(0f, 2f),
                        outerRadius = 7f,
                        orientation = ComplexF.ONE,
                        innerRadius = 5.1f
                    ),
                    false
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> {
            val mutableAnnulusMutableCircleArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
                        center = Vector2F(-2f, 2f),
                        orientation = ComplexF.ONE,
                        radius = 1.01f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
                        center = Vector2F(-2f, 2f),
                        orientation = ComplexF.ONE,
                        radius = 0.99f
                    ),
                    false
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
                        center = Vector2F(-6f, 2f),
                        orientation = ComplexF.ONE,
                        radius = 1.01f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
                        center = Vector2F(-6f, 2f),
                        orientation = ComplexF.ONE,
                        radius = 0.99f
                    ),
                    false
                ),
            )
            val defaultAnnulusMutableCircleArgs = mutableAnnulusMutableCircleArgs
                .mapAnnulusesToDefaultAnnuluses()
            val mutableAnnulusDefaultCircleArgs = mutableAnnulusMutableCircleArgs
                .mapCirclesToDefaultCircles()
            val defaultAnnulusDefaultCircleArgs = defaultAnnulusMutableCircleArgs
                .mapCirclesToDefaultCircles()

            return listOf(
                mutableAnnulusMutableCircleArgs,
                defaultAnnulusMutableCircleArgs,
                mutableAnnulusDefaultCircleArgs,
                defaultAnnulusDefaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> {
            val annulus = MutableAnnulus(
                center = Vector2F(-4f, 4f),
                orientation = ComplexF.ONE,
                outerRadius = 4f,
                innerRadius = 2f
            )
            val mutableAnnulusMutableRayArgs = listOf(
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-8.1f, 0f), direction = Vector2F(0f, 1f)
                    ),
                    false
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-8.1f, 0f),
                        direction = Vector2F(0.17365f, 0.98481f)
                    ),
                    true
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, 7.9f), direction = Vector2F(1f, 0f)
                    ),
                    true
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, 8.1f), direction = Vector2F(1f, 0f)
                    ),
                    false
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, 7.9f),
                        direction = Vector2F(-0.70711f, -0.70711f)
                    ),
                    true
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, -2f),
                        direction = Vector2F(0.76604f, 0.64279f)
                    ),
                    false
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, -2f),
                        direction = Vector2F(0.64279f, 0.76604f)
                    ),
                    true
                ),
            )
            val defaultAnnulusMutableRayArgs = mutableAnnulusMutableRayArgs
                .mapAnnulusesToDefaultAnnuluses()
            val mutableAnnulusDefaultRayArgs = mutableAnnulusMutableRayArgs
                .mapRaysToDefaultRays()
            val defaultAnnulusDefaultRayArgs = defaultAnnulusMutableRayArgs
                .mapRaysToDefaultRays()

            return listOf(
                mutableAnnulusMutableRayArgs,
                defaultAnnulusMutableRayArgs,
                mutableAnnulusDefaultRayArgs,
                defaultAnnulusDefaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-3.01f, 2f)),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-2.99f, 2f)),
                    false
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        outerRadius = 2.5f,
                        orientation = ComplexF.ONE,
                        innerRadius = 1.5f
                    ),
                    Wrapper(Vector2F(0f, 2.49f)),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F.ZERO,
                        outerRadius = 2.5f,
                        orientation = ComplexF.ONE,
                        innerRadius = 1.5f
                    ),
                    Wrapper(Vector2F(0f, 2.51f)),
                    false
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun containsAnnulusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-1f, -1f),
                        outerRadius = 0.99f,
                        orientation = ComplexF.ONE,
                        innerRadius = 0.5f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-1f, -1f),
                        outerRadius = 1.01f,
                        orientation = ComplexF.ONE,
                        innerRadius = 0.5f
                    ),
                    false
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 3.99f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2.01f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4.01f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2.01f
                    ),
                    false
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 3.99f,
                        orientation = ComplexF.ONE,
                        innerRadius = 1.99f
                    ),
                    false
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun containsCircleArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
                        center = Vector2F(-1f, -1f),
                        orientation = ComplexF.ONE,
                        radius = 0.99f
                    ),
                    true
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
                        center = Vector2F(-1f, -1f),
                        orientation = ComplexF.ONE,
                        radius = 1.01f
                    ),
                    false
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableAnnulusArgs = setArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun copyThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun copyDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableAnnulusArgs() + listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                DefaultAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                true
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                DefaultAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 3.9f,
                    innerRadius = 2f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableAnnulusArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                true
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 3.9f,
                    innerRadius = 2f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                )
            )
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                "Annulus(" +
                        "center=${Vector2F(-1f, 2f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(-45f))}, " +
                        "outerRadius=${4f}, " +
                        "innerRadius=${2f})"
            ),
            Arguments.of(
                MutableAnnulus(
                    center = Vector2F(6f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                    outerRadius = 8f,
                    innerRadius = 1f
                ),
                "Annulus(" +
                        "center=${Vector2F(6f, 3f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(330f))}, " +
                        "outerRadius=${8f}, " +
                        "innerRadius=${1f})"
            )
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                    4f,
                    2f
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, 3f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f))),
                    8f,
                    1f
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }
    }
}