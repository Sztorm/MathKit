package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.world2d.utils.DefaultAnnulus
import com.sztorm.lowallocmath.world2d.utils.assertImmutabilityOf
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class AnnulusTests {
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
    @MethodSource("annularRadiusArgs")
    fun annularRadiusReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.annularRadius)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(annulus: Annulus, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected.value, annulus.position)
        }

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
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(annulus: Annulus, circle: Circle, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(circle) {
                assertEquals(expected, annulus.intersects(circle))
            }
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
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(annulus) {
        assertEquals(expected, annulus.contains(point.value))
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
    @MethodSource("containsAnnulusArgs")
    fun containsReturnsCorrectValue(annulus: Annulus, otherAnnulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(otherAnnulus) {
                assertEquals(expected, annulus.contains(otherAnnulus))
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
        fun annularRadiusArgs(): List<Arguments> {
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
        fun positionArgs(): List<Arguments> = centerArgs()

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
        fun intersectsCircleArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    Circle(
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
                    Circle(
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
                    Circle(
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
                    Circle(
                        center = Vector2F(-6f, 2f),
                        orientation = ComplexF.ONE,
                        radius = 0.99f
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
        fun intersectsAnnulusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    Annulus(
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
                    Annulus(
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
                    Annulus(
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
                    Annulus(
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
                    Annulus(
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
                    Annulus(
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
        fun containsCircleArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    Circle(
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
                    Circle(
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
        fun containsAnnulusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    Annulus(
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
                    Annulus(
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
                    Annulus(
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
                    Annulus(
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
                    Annulus(
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
        fun copyArgs(): List<Arguments> {
            val mutableAnnulusArgs = setArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

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