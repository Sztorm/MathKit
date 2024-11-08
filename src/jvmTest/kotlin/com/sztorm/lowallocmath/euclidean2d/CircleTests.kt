package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.RayTests.Companion.mapRaysToDefaultRays
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultCircle
import com.sztorm.lowallocmath.euclidean2d.utils.assertApproximation
import com.sztorm.lowallocmath.euclidean2d.utils.assertEquals
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.isApproximately
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class CircleTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectCircle(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, radius: Float,
    ) {
        val mutableCircle = MutableCircle(center.value, orientation.value, radius)
        val circle = Circle(center.value, orientation.value, radius)

        assertEquals(center.value, mutableCircle.center)
        assertEquals(orientation.value, mutableCircle.orientation)
        assertEquals(radius, mutableCircle.radius)

        assertEquals(center.value, circle.center)
        assertEquals(orientation.value, circle.orientation)
        assertEquals(radius, circle.radius)
    }

    @ParameterizedTest
    @MethodSource("constructorThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        radius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableCircle(center.value, orientation.value, radius)
        }
        assertThrows(expectedExceptionClass) {
            Circle(center.value, orientation.value, radius)
        }
    }

    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(circle: Circle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(circle) {
            assertApproximation(expected.value, circle.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(circle: Circle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(circle) {
            assertApproximation(expected.value, circle.orientation)
        }

    @ParameterizedTest
    @MethodSource("radiusArgs")
    fun radiusReturnsCorrectValue(circle: Circle, expected: Float) =
        assertImmutabilityOf(circle) {
            assertApproximation(expected, circle.radius)
        }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(circle: Circle, expected: Float) =
        assertImmutabilityOf(circle) {
            assertApproximation(expected, circle.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(circle: Circle, expected: Float) =
        assertImmutabilityOf(circle) {
            assertApproximation(expected, circle.perimeter)
        }

    @ParameterizedTest
    @MethodSource("diameterArgs")
    fun diameterReturnsCorrectValue(circle: Circle, expected: Float) =
        assertImmutabilityOf(circle) {
            assertApproximation(expected, circle.diameter)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(circle: Circle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(circle) {
            assertApproximation(expected.value, circle.position)
        }

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(
        circle: Circle, displacement: Wrapper<Vector2F>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.movedBy(displacement.value))
    }

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(
        circle: Circle, position: Wrapper<Vector2F>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.movedTo(position.value))
    }

    @ParameterizedTest
    @MethodSource("moveByArgs")
    fun moveByMutatesCircleCorrectly(
        circle: MutableCircle, displacement: Wrapper<Vector2F>, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { moveBy(displacement.value) })

    @ParameterizedTest
    @MethodSource("moveToArgs")
    fun moveToMutatesCircleCorrectly(
        circle: MutableCircle, position: Wrapper<Vector2F>, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { moveTo(position.value) })

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(
        circle: Circle, rotation: Wrapper<AngleF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        circle: Circle, rotation: Wrapper<ComplexF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(
        circle: Circle, orientation: Wrapper<AngleF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        circle: Circle, orientation: Wrapper<ComplexF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FAngleFArgs")
    fun rotatedAroundPointByVector2FAngleFReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, rotation: Wrapper<AngleF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FComplexFArgs")
    fun rotatedAroundPointByVector2FComplexFReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, rotation: Wrapper<ComplexF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FAngleFArgs")
    fun rotatedAroundPointToVector2FAngleFReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, orientation: Wrapper<AngleF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedAroundPointTo(point.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FComplexFArgs")
    fun rotatedAroundPointToVector2FComplexFReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.rotatedAroundPointTo(point.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotateByAngleFArgs")
    fun rotateByAngleFMutatesCircleCorrectly(
        circle: MutableCircle, rotation: Wrapper<AngleF>, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateByComplexFArgs")
    fun rotateByComplexFMutatesCircleCorrectly(
        circle: MutableCircle, rotation: Wrapper<ComplexF>, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateToAngleFArgs")
    fun rotateToAngleFMutatesCircleCorrectly(
        circle: MutableCircle, orientation: Wrapper<AngleF>, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateToComplexFArgs")
    fun rotateToComplexFMutatesCircleCorrectly(
        circle: MutableCircle, orientation: Wrapper<ComplexF>, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FAngleFArgs")
    fun rotateAroundPointByVector2FAngleFMutatesCircleCorrectly(
        circle: MutableCircle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FComplexFArgs")
    fun rotateAroundPointByVector2FComplexFMutatesCircleCorrectly(
        circle: MutableCircle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FAngleFArgs")
    fun rotateAroundPointToVector2FAngleFMutatesCircleCorrectly(
        circle: MutableCircle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FComplexFArgs")
    fun rotateAroundPointToVector2FComplexFMutatesCircleCorrectly(
        circle: MutableCircle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(
        circle: Circle, factor: Float, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.scaledBy(factor))
    }

    @ParameterizedTest
    @MethodSource("dilatedByArgs")
    fun dilatedByReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, factor: Float, expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.dilatedBy(point.value, factor))
    }

    @ParameterizedTest
    @MethodSource("scaleByArgs")
    fun scaleByMutatesCircleCorrectly(
        circle: MutableCircle, factor: Float, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { scaleBy(factor) })

    @ParameterizedTest
    @MethodSource("dilateByArgs")
    fun dilateByMutatesCircleCorrectly(
        circle: MutableCircle, point: Wrapper<Vector2F>, factor: Float, expected: MutableCircle
    ) = assertApproximation(expected, circle.apply { dilateBy(point.value, factor) })

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFArgs")
    fun transformedByVector2FAngleFReturnsCorrectValue(
        circle: Circle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFArgs")
    fun transformedByVector2FComplexFReturnsCorrectValue(
        circle: Circle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFFloatArgs")
    fun transformedByVector2FAngleFFloatReturnsCorrectValue(
        circle: Circle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(
            expected, circle.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFFloatArgs")
    fun transformedByVector2FComplexFFloatReturnsCorrectValue(
        circle: Circle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(
            expected, circle.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FAngleFArgs")
    fun transformedToVector2FAngleFReturnsCorrectValue(
        circle: Circle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FComplexFArgs")
    fun transformedToVector2FComplexFReturnsCorrectValue(
        circle: Circle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Circle
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected, circle.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFArgs")
    fun transformByVector2FAngleFMutatesCircleCorrectly(
        circle: MutableCircle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFArgs")
    fun transformByVector2FComplexFMutatesCircleCorrectly(
        circle: MutableCircle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFFloatArgs")
    fun transformByVector2FAngleFFloatMutatesCircleCorrectly(
        circle: MutableCircle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFFloatArgs")
    fun transformByVector2FComplexFFloatMutatesCircleCorrectly(
        circle: MutableCircle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FAngleFArgs")
    fun transformToVector2FAngleFMutatesCircleCorrectly(
        circle: MutableCircle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FComplexFArgs")
    fun transformToVector2FComplexFMutatesCircleCorrectly(
        circle: MutableCircle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableCircle
    ) = assertApproximation(
        expected, circle.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesCircleCorrectly(circle: MutableCircle, expected: MutableCircle) =
        assertApproximation(expected, circle.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesCircleCorrectly(
        circle: MutableCircle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        radius: Float,
        expected: MutableCircle
    ) = assertEquals(expected, circle.apply { set(center.value, orientation.value, radius) })

    @ParameterizedTest
    @MethodSource("setThrowsExceptionArgs")
    fun setThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        radius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val circle = MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f)

        assertThrows(expectedExceptionClass) {
            circle.set(center.value, orientation.value, radius)
        }
    }

    @ParameterizedTest
    @MethodSource("setDoesNotThrowExceptionArgs")
    fun setDoesNotThrowException(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, radius: Float,
    ) {
        val circle = MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f)

        assertDoesNotThrow {
            circle.set(center.value, orientation.value, radius)
        }
    }

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(circle: Circle, to: Circle, by: Float, expected: Circle) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(to) {
                assertApproximation(expected, circle.interpolated(to, by))
            }
        }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesCircleCorrectly(
        circle: MutableCircle, from: Circle, to: Circle, by: Float, expected: MutableCircle
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, circle.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        circle: Circle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(circle) {
        assertApproximation(expected.value, circle.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(circle: Circle, annulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(annulus) {
                assertEquals(expected, circle.intersects(annulus))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(circle: Circle, otherCircle: Circle, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(otherCircle) {
                assertEquals(expected, circle.intersects(otherCircle))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsReturnsCorrectValue(circle: Circle, ray: Ray, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(ray) {
                assertEquals(expected, circle.intersects(ray))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(circle: Circle, point: Wrapper<Vector2F>, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertEquals(expected, circle.contains(point.value))
        }

    @ParameterizedTest
    @MethodSource("containsAnnulusArgs")
    fun containsReturnsCorrectValue(circle: Circle, annulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(annulus) {
                assertEquals(expected, circle.contains(annulus))
            }
        }

    @ParameterizedTest
    @MethodSource("containsCircleArgs")
    fun containsReturnsCorrectValue(circle: Circle, otherCircle: Circle, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(otherCircle) {
                assertEquals(expected, circle.contains(otherCircle))
            }
        }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        circle: Circle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        radius: Float,
        expected: Circle
    ) = assertEquals(expected, circle.copy(center.value, orientation.value, radius))

    @ParameterizedTest
    @MethodSource("copyThrowsExceptionArgs")
    fun copyThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        radius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val circle = MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f)

        assertThrows(expectedExceptionClass) {
            circle.copy(center.value, orientation.value, radius)
        }
    }

    @ParameterizedTest
    @MethodSource("copyDoesNotThrowExceptionArgs")
    fun copyDoesNotThrowException(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, radius: Float,
    ) {
        val circle = MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f)

        assertDoesNotThrow {
            circle.copy(center.value, orientation.value, radius)
        }
    }

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(circle: MutableCircle, other: Any?, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertEquals(expected, circle == other)
        }

    @ParameterizedTest
    @MethodSource("equalsMutableCircleArgs")
    fun equalsReturnsCorrectValue(circle: MutableCircle, other: MutableCircle, expected: Boolean) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(other) {
                assertEquals(expected, circle.equals(other))
            }
        }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(circle: MutableCircle, other: MutableCircle) =
        assertImmutabilityOf(circle) {
            assertImmutabilityOf(other) {
                assertEquals(circle.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(circle: MutableCircle, expected: String) =
        assertImmutabilityOf(circle) {
            assertEquals(expected, circle.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        circle: Circle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float
    ) = assertImmutabilityOf(circle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float
        ) = circle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Circle, b: Circle, tolerance: Float = 0.00001f): Boolean =
            a.center.isApproximately(b.center, tolerance) and
                    a.orientation.isApproximately(b.orientation, tolerance) and
                    a.radius.isApproximately(b.radius, tolerance)

        @JvmStatic
        fun areEqual(a: Circle, b: Circle): Boolean =
            (a.center == b.center) and
                    (a.orientation == b.orientation) and
                    (a.radius == b.radius)

        @JvmStatic
        fun List<Arguments>.mapCirclesToDefaultCircles() = map { args ->
            val argArray = args.get().map {
                if (it is Circle) DefaultCircle(it.center, it.orientation, it.radius)
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorArgs(): List<Arguments> = listOf(
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
            Arguments.of(
                Wrapper(Vector2F(1f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                4f
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, 7f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f))),
                5f
            ),
        )

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
        fun centerArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(1f, 2f))
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    Wrapper(Vector2F(-1f, 7f))
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)))
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f)))
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun radiusArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    4f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    5f
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    50.2655f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    50.2655f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 2.5f
                    ),
                    19.635f
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    25.1327f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    25.1327f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 2.5f
                    ),
                    15.708f
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun diameterArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    8f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    8f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 2.5f
                    ),
                    5f
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
            val mutableCircleArgs = moveByArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val mutableCircleArgs = moveToArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                MutableCircle(
                    center = Vector2F(-3f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                MutableCircle(
                    center = Vector2F(1.5f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun moveToArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                MutableCircle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                MutableCircle(
                    center = Vector2F(0.5f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
            val mutableCircleArgs = rotateByAngleFArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedByComplexFArgs(): List<Arguments> = rotatedByAngleFArgs().map { args ->
            Arguments.of(
                *args.get().copyOf().apply {
                    val angle = (get(1) as Wrapper<*>).value as AngleF
                    set(1, Wrapper(ComplexF.fromAngle(angle)))
                }
            )
        }

        @JvmStatic
        fun rotatedToAngleFArgs(): List<Arguments> {
            val mutableCircleArgs = rotateToAngleFArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedToComplexFArgs(): List<Arguments> = rotatedToAngleFArgs().map { args ->
            Arguments.of(
                *args.get().copyOf().apply {
                    val angle = (get(1) as Wrapper<*>).value as AngleF
                    set(1, Wrapper(ComplexF.fromAngle(angle)))
                }
            )
        }

        @JvmStatic
        fun rotatedAroundPointByVector2FAngleFArgs(): List<Arguments> {
            val mutableCircleArgs = rotateAroundPointByVector2FAngleFArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedAroundPointByVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointByVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun rotatedAroundPointToVector2FAngleFArgs(): List<Arguments> {
            val mutableCircleArgs = rotateAroundPointToVector2FAngleFArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedAroundPointToVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointToVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun rotateByAngleFArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun rotateByComplexFArgs(): List<Arguments> = rotateByAngleFArgs().map { args ->
            Arguments.of(
                *args.get().copyOf().apply {
                    val angle = (get(1) as Wrapper<*>).value as AngleF
                    set(1, Wrapper(ComplexF.fromAngle(angle)))
                }
            )
        }

        @JvmStatic
        fun rotateToAngleFArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun rotateToComplexFArgs(): List<Arguments> = rotateToAngleFArgs().map { args ->
            Arguments.of(
                *args.get().copyOf().apply {
                    val angle = (get(1) as Wrapper<*>).value as AngleF
                    set(1, Wrapper(ComplexF.fromAngle(angle)))
                }
            )
        }

        @JvmStatic
        fun rotateAroundPointByVector2FAngleFArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(-1.0710678f, -3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(8.988362f, -9.408564f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(1f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(1f, 2f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun rotateAroundPointByVector2FComplexFArgs(): List<Arguments> =
            rotateAroundPointByVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun rotateAroundPointToVector2FAngleFArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(11f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(-0.64463043f, -0.58155227f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(115f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(1f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(1f, 2f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun rotateAroundPointToVector2FComplexFArgs(): List<Arguments> =
            rotateAroundPointToVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun scaledByArgs(): List<Arguments> {
            val mutableCircleArgs = scaleByArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun dilatedByArgs(): List<Arguments> {
            val mutableCircleArgs = dilateByArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                2f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 8f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                0.3f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 1.2f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                1f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                -1f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(270f)),
                    radius = 4f
                ),
            ),
        )

        @JvmStatic
        fun dilateByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                2f,
                MutableCircle(
                    center = Vector2F(-4f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 8f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                0.3f,
                MutableCircle(
                    center = Vector2F(4.5f, -1.5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 1.2f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                1f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(6f, -3f)),
                -1f,
                MutableCircle(
                    center = Vector2F(11f, -8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(270f)),
                    radius = 4f
                ),
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(1f, 2f)),
                2f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 8f
                )
            ),
        )

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val mutableCircleArgs = transformByVector2FAngleFArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun transformedByVector2FComplexFArgs(): List<Arguments> =
            transformedByVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformedByVector2FAngleFFloatArgs(): List<Arguments> {
            val mutableCircleArgs = transformByVector2FAngleFFloatArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun transformedByVector2FComplexFFloatArgs(): List<Arguments> =
            transformedByVector2FAngleFFloatArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformedToVector2FAngleFArgs(): List<Arguments> {
            val mutableCircleArgs = transformToVector2FAngleFArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun transformedToVector2FComplexFArgs(): List<Arguments> =
            transformedToVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformByVector2FAngleFArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(-3f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(1.5f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun transformByVector2FComplexFArgs(): List<Arguments> =
            transformByVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformByVector2FAngleFFloatArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                2f,
                MutableCircle(
                    center = Vector2F(-3f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    radius = 8f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                0.3f,
                MutableCircle(
                    center = Vector2F(1.5f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                    radius = 1.2f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                1f,
                MutableCircle(
                    center = Vector2F(-3f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                -1f,
                MutableCircle(
                    center = Vector2F(-3f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(315f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun transformByVector2FComplexFFloatArgs(): List<Arguments> =
            transformByVector2FAngleFFloatArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformToVector2FAngleFArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableCircle(
                    center = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableCircle(
                    center = Vector2F(0.5f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun transformToVector2FComplexFArgs(): List<Arguments> =
            transformToVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(2) as Wrapper<*>).value as AngleF
                        set(2, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun calibrateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 4f, AngleF.fromDegrees(-78f).radians
                    ),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 0.4f, AngleF.fromDegrees(-78f).radians
                    ),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.ZERO,
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.ONE,
                    radius = 4f
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(2f, 0f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-78f))),
                4f,
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(2f, 0f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-78f))),
                5f,
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 5f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 4f
                ),
                Wrapper(Vector2F(-1f, 7f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f))),
                5f,
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                )
            ),
        )

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableCircleArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                0.5f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                0f,
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                1f,
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                )
            ),
            Arguments.of(
                MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 1f),
                MutableCircle(
                    center = Vector2F(1f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                0.5f,
                MutableCircle(
                    center = Vector2F(0f, 4.5f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(167f)),
                    radius = 4.5f
                )
            ),
        )

        @JvmStatic
        fun setThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun setDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    Wrapper(Vector2F.ZERO),
                    Wrapper(Vector2F.ZERO)
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    Wrapper(Vector2F(-2.1f, 0f)),
                    Wrapper(Vector2F(-2f, 0f))
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    Wrapper(Vector2F(-2f, 4f)),
                    Wrapper(Vector2F(-0.828429f, 2.828429f))
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    Wrapper(Vector2F(6f, -4f)),
                    Wrapper(Vector2F(4.828429f, -2.828429f))
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    Wrapper(Vector2F(0f, 2f)),
                    Wrapper(Vector2F(0f, 2f))
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsAnnulusArgs(): List<Arguments> = AnnulusTests.intersectsCircleArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-4f, 4f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 3f
                    ),
                    false
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-4f, 4f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    true
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-4f, 4f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    MutableCircle(
                        center = Vector2F(3.99f, 4f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    true
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> {
            val circle = MutableCircle(
                center = Vector2F(-4f, 4f), orientation = ComplexF.ONE, radius = 4f
            )
            val mutableCircleMutableRayArgs = listOf(
                Arguments.of(
                    circle,
                    MutableRay(
                        origin = Vector2F(-8.1f, 0f),
                        direction = AngleF.fromDegrees(90f).toVector2F()
                    ),
                    false
                ),
                Arguments.of(
                    circle,
                    MutableRay(
                        origin = Vector2F(-8.1f, 0f),
                        direction = AngleF.fromDegrees(80f).toVector2F()
                    ),
                    true
                ),
                Arguments.of(
                    circle,
                    MutableRay(
                        origin = Vector2F(-4f, 7.9f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    true
                ),
                Arguments.of(
                    circle,
                    MutableRay(
                        origin = Vector2F(-4f, 8.1f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    false
                ),
                Arguments.of(
                    circle,
                    MutableRay(
                        origin = Vector2F(-4f, 7.9f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    true
                ),
                Arguments.of(
                    circle,
                    MutableRay(
                        origin = Vector2F(-4f, -2f),
                        direction = AngleF.fromDegrees(40f).toVector2F()
                    ),
                    false
                ),
                Arguments.of(
                    circle,
                    MutableRay(
                        origin = Vector2F(-4f, -2f),
                        direction = AngleF.fromDegrees(50f).toVector2F()
                    ),
                    true
                ),
            )
            val defaultCircleMutableRayArgs = mutableCircleMutableRayArgs
                .mapCirclesToDefaultCircles()
            val mutableCircleDefaultRayArgs = mutableCircleMutableRayArgs
                .mapRaysToDefaultRays()
            val defaultCircleDefaultRayArgs = defaultCircleMutableRayArgs
                .mapRaysToDefaultRays()

            return listOf(
                mutableCircleMutableRayArgs,
                defaultCircleMutableRayArgs,
                mutableCircleDefaultRayArgs,
                defaultCircleDefaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    Wrapper(Vector2F.ZERO),
                    true
                ),
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    Wrapper(Vector2F(3.99f, 0f)),
                    true
                ),
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    Wrapper(Vector2F(4.01f, 0f)),
                    false
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun containsAnnulusArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    Annulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 3.99f,
                        innerRadius = 2f
                    ),
                    true
                ),
                Arguments.of(
                    MutableCircle(center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f),
                    Annulus(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        outerRadius = 4.01f,
                        innerRadius = 2f
                    ),
                    false
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    Annulus(
                        center = Vector2F(4f, 0f),
                        orientation = ComplexF.ONE,
                        outerRadius = 1.99f,
                        innerRadius = 1f
                    ),
                    true
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    Annulus(
                        center = Vector2F(4f, 0f),
                        orientation = ComplexF.ONE,
                        outerRadius = 2.01f,
                        innerRadius = 1f
                    ),
                    false
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun containsCircleArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f
                    ),
                    MutableCircle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 3.99f
                    ),
                    true
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4f
                    ),
                    MutableCircle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, radius = 4.01f
                    ),
                    false
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    MutableCircle(
                        center = Vector2F(4f, 0f), orientation = ComplexF.ONE, radius = 1.99f
                    ),
                    true
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f), orientation = ComplexF.ONE, radius = 4f
                    ),
                    MutableCircle(
                        center = Vector2F(4f, 0f), orientation = ComplexF.ONE, radius = 2.01f
                    ),
                    false
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableCircleArgs = setArgs()
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun copyDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableCircleArgs() + listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                DefaultCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                true
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                DefaultCircle(
                    center = Vector2F(2f, 0.1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableCircleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                true
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0.1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                )
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableCircle(
                    center = Vector2F(2f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                    radius = 4f
                ),
                "Circle(" +
                        "center=${Vector2F(2f, 0f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(-78f))}, " +
                        "radius=${4f})"
            ),
            Arguments.of(
                MutableCircle(
                    center = Vector2F(-1f, 7f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                    radius = 5f
                ),
                "Circle(" +
                        "center=${Vector2F(-1f, 7f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(244f))}, " +
                        "radius=${5f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableCircleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(2f, 0f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-78f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(2f, 0f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-78f))),
                    4f
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    Wrapper(Vector2F(-1f, 7f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f))),
                    5f
                ),
            )
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()

            return listOf(
                mutableCircleArgs,
                defaultCircleArgs
            ).flatten()
        }
    }
}