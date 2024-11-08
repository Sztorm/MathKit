package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.RectangleTests.Companion.mapRectanglesToDefaultRectangles
import com.sztorm.lowallocmath.euclidean2d.RegularPolygonTests.Companion.mapRegularPolygonsToDefaultRegularPolygons
import com.sztorm.lowallocmath.euclidean2d.RegularTriangleTests.Companion.mapRegularTrianglesToDefaultRegularTriangles
import com.sztorm.lowallocmath.euclidean2d.RoundedRectangleTests.Companion.mapRoundedRectanglesToDefaultRoundedRectangles
import com.sztorm.lowallocmath.euclidean2d.SquareTests.Companion.mapSquaresToDefaultSquares
import com.sztorm.lowallocmath.euclidean2d.TriangleTests.Companion.mapTrianglesToDefaultTriangles
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultRay
import com.sztorm.lowallocmath.euclidean2d.utils.assertApproximation
import com.sztorm.lowallocmath.euclidean2d.utils.assertEquals
import com.sztorm.lowallocmath.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class RayTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectRay(
        origin: Wrapper<Vector2F>, direction: Wrapper<Vector2F>
    ) {
        val mutableRay = MutableRay(origin.value, direction.value)
        val ray = Ray(origin.value, direction.value)

        assertEquals(origin.value, mutableRay.origin)
        assertEquals(direction.value, mutableRay.direction)

        assertEquals(origin.value, ray.origin)
        assertEquals(direction.value, ray.direction)
    }

    @ParameterizedTest
    @MethodSource("originArgs")
    fun originReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.origin)
        }

    @ParameterizedTest
    @MethodSource("directionArgs")
    fun directionReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.direction)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(ray: Ray, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.position)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(ray: Ray, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected.value, ray.orientation)
        }

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(ray: Ray, displacement: Wrapper<Vector2F>, expected: Ray) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected, ray.movedBy(displacement.value))
        }

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(ray: Ray, position: Wrapper<Vector2F>, expected: Ray) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected, ray.movedTo(position.value))
        }

    @ParameterizedTest
    @MethodSource("moveByArgs")
    fun moveByMutatesRayCorrectly(
        ray: MutableRay, displacement: Wrapper<Vector2F>, expected: MutableRay
    ) = assertApproximation(expected, ray.apply { moveBy(displacement.value) })

    @ParameterizedTest
    @MethodSource("moveToArgs")
    fun moveToMutatesRayCorrectly(
        ray: MutableRay, position: Wrapper<Vector2F>, expected: MutableRay
    ) = assertApproximation(expected, ray.apply { moveTo(position.value) })

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(ray: Ray, rotation: Wrapper<AngleF>, expected: Ray) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected, ray.rotatedBy(rotation.value))
        }

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        ray: Ray, rotation: Wrapper<ComplexF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(ray: Ray, orientation: Wrapper<AngleF>, expected: Ray) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected, ray.rotatedTo(orientation.value))
        }

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        ray: Ray, orientation: Wrapper<ComplexF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FAngleFArgs")
    fun rotatedAroundPointByVector2FAngleFReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, rotation: Wrapper<AngleF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FComplexFArgs")
    fun rotatedAroundPointByVector2FComplexFReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, rotation: Wrapper<ComplexF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FAngleFArgs")
    fun rotatedAroundPointToVector2FAngleFReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, orientation: Wrapper<AngleF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.rotatedAroundPointTo(point.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FComplexFArgs")
    fun rotatedAroundPointToVector2FComplexFReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.rotatedAroundPointTo(point.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotateByAngleFArgs")
    fun rotateByAngleFMutatesRayCorrectly(
        ray: MutableRay, rotation: Wrapper<AngleF>, expected: MutableRay
    ) = assertApproximation(expected, ray.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateByComplexFArgs")
    fun rotateByComplexFMutatesRayCorrectly(
        ray: MutableRay, rotation: Wrapper<ComplexF>, expected: MutableRay
    ) = assertApproximation(expected, ray.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateToAngleFArgs")
    fun rotateToAngleFMutatesRayCorrectly(
        ray: MutableRay, orientation: Wrapper<AngleF>, expected: MutableRay
    ) = assertApproximation(expected, ray.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateToComplexFArgs")
    fun rotateToComplexFMutatesRayCorrectly(
        ray: MutableRay, orientation: Wrapper<ComplexF>, expected: MutableRay
    ) = assertApproximation(expected, ray.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FAngleFArgs")
    fun rotateAroundPointByVector2FAngleFMutatesRayCorrectly(
        ray: MutableRay, point: Wrapper<Vector2F>, rotation: Wrapper<AngleF>, expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FComplexFArgs")
    fun rotateAroundPointByVector2FComplexFMutatesRayCorrectly(
        ray: MutableRay,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FAngleFArgs")
    fun rotateAroundPointToVector2FAngleFMutatesRayCorrectly(
        ray: MutableRay,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FComplexFArgs")
    fun rotateAroundPointToVector2FComplexFMutatesRayCorrectly(
        ray: MutableRay,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(ray: Ray, factor: Float, expected: Ray) =
        assertImmutabilityOf(ray) {
            assertApproximation(expected, ray.scaledBy(factor))
        }

    @ParameterizedTest
    @MethodSource("dilatedByArgs")
    fun dilatedByReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, factor: Float, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.dilatedBy(point.value, factor))
    }

    @ParameterizedTest
    @MethodSource("scaleByArgs")
    fun scaleByMutatesRayCorrectly(ray: MutableRay, factor: Float, expected: MutableRay) =
        assertApproximation(expected, ray.apply { scaleBy(factor) })

    @ParameterizedTest
    @MethodSource("dilateByArgs")
    fun dilateByMutatesRayCorrectly(
        ray: MutableRay, point: Wrapper<Vector2F>, factor: Float, expected: MutableRay
    ) = assertApproximation(expected, ray.apply { dilateBy(point.value, factor) })

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFArgs")
    fun transformedByVector2FAngleFReturnsCorrectValue(
        ray: Ray, displacement: Wrapper<Vector2F>, rotation: Wrapper<AngleF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFArgs")
    fun transformedByVector2FComplexFReturnsCorrectValue(
        ray: Ray, displacement: Wrapper<Vector2F>, rotation: Wrapper<ComplexF>, expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFFloatArgs")
    fun transformedByVector2FAngleFFloatReturnsCorrectValue(
        ray: Ray,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(
            expected,
            ray.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFFloatArgs")
    fun transformedByVector2FComplexFFloatReturnsCorrectValue(
        ray: Ray,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(
            expected, ray.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FAngleFArgs")
    fun transformedToVector2FAngleFReturnsCorrectValue(
        ray: Ray,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FComplexFArgs")
    fun transformedToVector2FComplexFReturnsCorrectValue(
        ray: Ray,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Ray
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected, ray.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFArgs")
    fun transformByVector2FAngleFMutatesRayCorrectly(
        ray: MutableRay,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFArgs")
    fun transformByVector2FComplexFMutatesRayCorrectly(
        ray: MutableRay,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFFloatArgs")
    fun transformByVector2FAngleFFloatMutatesRayCorrectly(
        ray: MutableRay,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFFloatArgs")
    fun transformByVector2FComplexFFloatMutatesRayCorrectly(
        ray: MutableRay,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: MutableRay
    ) = assertApproximation(
        expected, ray.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FAngleFArgs")
    fun transformToVector2FAngleFMutatesRayCorrectly(
        ray: MutableRay,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableRay
    ) = assertApproximation(expected, ray.apply { transformTo(position.value, orientation.value) })

    @ParameterizedTest
    @MethodSource("transformToVector2FComplexFArgs")
    fun transformToVector2FComplexFMutatesRayCorrectly(
        ray: MutableRay,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableRay
    ) = assertApproximation(expected, ray.apply { transformTo(position.value, orientation.value) })

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesRayCorrectly(ray: MutableRay, expected: MutableRay) =
        assertApproximation(expected, ray.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesRayCorrectly(
        ray: MutableRay,
        origin: Wrapper<Vector2F>,
        direction: Wrapper<Vector2F>,
        expected: MutableRay
    ) = assertEquals(expected, ray.apply { set(origin.value, direction.value) })

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(ray: Ray, to: Ray, by: Float, expected: Ray) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(to) {
                assertApproximation(expected, ray.interpolated(to, by))
            }
        }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesRayCorrectly(
        ray: MutableRay, from: Ray, to: Ray, by: Float, expected: MutableRay
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, ray.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        ray: Ray, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(ray) {
        assertApproximation(expected.value, ray.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, annulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(annulus) {
                assertEquals(expected, ray.intersects(annulus))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsCircleArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, circle: Circle, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(circle) {
                assertEquals(expected, ray.intersects(circle))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsLineSegmentArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, lineSegment: LineSegment, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(lineSegment) {
                assertEquals(expected, ray.intersects(lineSegment))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, otherRay: Ray, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(otherRay) {
                assertEquals(expected, ray.intersects(otherRay))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRectangleArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, rectangle: Rectangle, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(rectangle) {
                assertEquals(expected, ray.intersects(rectangle))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsRegularPolygonArgs")
    fun intersectsReturnsCorrectValue(
        ray: Ray, polygon: RegularPolygon, expected: Boolean
    ) = assertImmutabilityOf(ray) {
        assertImmutabilityOf(polygon) {
            assertEquals(expected, ray.intersects(polygon))
        }
    }

    @ParameterizedTest
    @MethodSource("intersectsRegularTriangleArgs")
    fun intersectsReturnsCorrectValue(
        ray: Ray, triangle: RegularTriangle, expected: Boolean
    ) = assertImmutabilityOf(ray) {
        assertImmutabilityOf(triangle) {
            assertEquals(expected, ray.intersects(triangle))
        }
    }

    @ParameterizedTest
    @MethodSource("intersectsRoundedRectangleArgs")
    fun intersectsReturnsCorrectValue(
        ray: Ray, rectangle: RoundedRectangle, expected: Boolean
    ) = assertImmutabilityOf(ray) {
        assertImmutabilityOf(rectangle) {
            assertEquals(expected, ray.intersects(rectangle))
        }
    }

    @ParameterizedTest
    @MethodSource("intersectsSquareArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, square: Square, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(square) {
                assertEquals(expected, ray.intersects(square))
            }
        }

    @ParameterizedTest
    @MethodSource("intersectsTriangleArgs")
    fun intersectsReturnsCorrectValue(ray: Ray, triangle: Triangle, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(triangle) {
                assertEquals(expected, ray.intersects(triangle))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(ray: Ray, point: Wrapper<Vector2F>, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertEquals(expected, ray.contains(point.value))
        }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        ray: Ray,
        origin: Wrapper<Vector2F>,
        direction: Wrapper<Vector2F>,
        expected: Ray
    ) = assertEquals(expected, ray.copy(origin.value, direction.value))

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(ray: MutableRay, other: Any?, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertEquals(expected, ray == other)
        }

    @ParameterizedTest
    @MethodSource("equalsMutableLineSegmentArgs")
    fun equalsReturnsCorrectValue(ray: MutableRay, other: MutableRay, expected: Boolean) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(other) {
                assertEquals(expected, ray.equals(other))
            }
        }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(ray: MutableRay, other: MutableRay) =
        assertImmutabilityOf(ray) {
            assertImmutabilityOf(other) {
                assertEquals(ray.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(ray: MutableRay, expected: String) =
        assertImmutabilityOf(ray) {
            assertEquals(expected, ray.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        ray: Ray,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<Vector2F>
    ) = assertImmutabilityOf(ray) {
        val (actualComponent1: Vector2F, actualComponent2: Vector2F) = ray

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Ray, b: Ray, tolerance: Float = 0.00001f): Boolean =
            a.origin.isApproximately(b.origin, tolerance) and
                    a.direction.isApproximately(b.direction, tolerance)

        @JvmStatic
        fun areEqual(a: Ray, b: Ray): Boolean =
            (a.origin == b.origin) and (a.direction == b.direction)

        @JvmStatic
        fun List<Arguments>.mapRaysToDefaultRays() = map { args ->
            val argArray = args.get().map {
                if (it is Ray) DefaultRay(it.origin, it.direction)
                else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(AngleF.fromDegrees(0f).toVector2F())
            ),
            Arguments.of(
                Wrapper(Vector2F(3f, 2f)),
                Wrapper(AngleF.fromDegrees(90f).toVector2F())
            ),
            Arguments.of(
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(AngleF.fromDegrees(-45f).toVector2F())
            ),
        )

        @JvmStatic
        fun originArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, 5f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(3f, 2f),
                        direction = AngleF.fromDegrees(90f).toVector2F()
                    ),
                    Wrapper(Vector2F(3f, 2f))
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 3f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    Wrapper(Vector2F(-4f, 3f))
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun directionArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, 5f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    Wrapper(AngleF.fromDegrees(0f).toVector2F())
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(3f, 2f),
                        direction = AngleF.fromDegrees(90f).toVector2F()
                    ),
                    Wrapper(AngleF.fromDegrees(90f).toVector2F())
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 3f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    Wrapper(AngleF.fromDegrees(-45f).toVector2F())
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = originArgs()

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, 5f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(0f)))
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(3f, 2f),
                        direction = AngleF.fromDegrees(90f).toVector2F()
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)))
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 3f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f)))
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
            val mutableRayArgs = moveByArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val mutableRayArgs = moveToArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 2f)),
                MutableRay(
                    origin = Vector2F(-8f, 5f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                MutableRay(
                    origin = Vector2F(-3.5f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
        )

        @JvmStatic
        fun moveToArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 2f)),
                MutableRay(
                    origin = Vector2F(-4f, 2f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                MutableRay(
                    origin = Vector2F(0.5f, 0f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
        )

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
            val mutableRayArgs = rotateByAngleFArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
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
            val mutableRayArgs = rotateToAngleFArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
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
            val mutableRayArgs = rotateAroundPointByVector2FAngleFArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
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
            val mutableRayArgs = rotateAroundPointToVector2FAngleFArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
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
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(115f).toVector2F()
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
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-200f).toVector2F()
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
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(-5.3137083f, -5.8284273f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(13.344805f, -12.058357f),
                    direction = AngleF.fromDegrees(-245f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-245f).toVector2F()
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
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(14.246211f, 5.246211f),
                    direction = AngleF.fromDegrees(-149.03624f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(-4.958605f, 0.988606f),
                    direction = AngleF.fromDegrees(-34.036247f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-200f).toVector2F()
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
            val mutableRayArgs = scaleByArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun dilatedByArgs(): List<Arguments> {
            val mutableRayArgs = dilateByArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                2f,
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                0.3f,
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                1f,
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                -1f,
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(135f).toVector2F()
                )
            ),
        )

        @JvmStatic
        fun dilateByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                2f,
                MutableRay(
                    origin = Vector2F(-14f, 9f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                0.3f,
                MutableRay(
                    origin = Vector2F(3f, -1.2f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                1f,
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(6f, -3f)),
                -1f,
                MutableRay(
                    origin = Vector2F(16f, -9f),
                    direction = AngleF.fromDegrees(135f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 3f)),
                2f,
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
        )

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val mutableRayArgs = transformByVector2FAngleFArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
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
            val mutableRayArgs = transformByVector2FAngleFFloatArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
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
            val mutableRayArgs = transformToVector2FAngleFArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
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
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(-8f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(-3.5f, 3f),
                    direction = AngleF.fromDegrees(-245f).toVector2F()
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
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                2f,
                MutableRay(
                    origin = Vector2F(-8f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                0.3f,
                MutableRay(
                    origin = Vector2F(-3.5f, 3f),
                    direction = AngleF.fromDegrees(-245f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                1f,
                MutableRay(
                    origin = Vector2F(-8f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                -1f,
                MutableRay(
                    origin = Vector2F(-8f, 5f),
                    direction = AngleF.fromDegrees(180f).toVector2F()
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
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableRay(
                    origin = Vector2F(-4f, 2f),
                    direction = AngleF.fromDegrees(45f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableRay(
                    origin = Vector2F(0.5f, 0f),
                    direction = AngleF.fromDegrees(-200f).toVector2F()
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
                MutableRay(
                    origin = Vector2F(-2f, -5f),
                    direction = AngleF.fromDegrees(135f).toVector2F() * 2.5f
                ),
                MutableRay(
                    origin = Vector2F(-2f, -5f),
                    direction = AngleF.fromDegrees(135f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, -5f),
                    direction = AngleF.fromDegrees(135f).toVector2F() * 0.25f
                ),
                MutableRay(
                    origin = Vector2F(-2f, -5f),
                    direction = AngleF.fromDegrees(135f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, -5f),
                    direction = Vector2F.ZERO
                ),
                MutableRay(
                    origin = Vector2F(-2f, -5f),
                    direction = AngleF.ZERO.toVector2F()
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                Wrapper(Vector2F(-2f, 5f)),
                Wrapper(AngleF.fromDegrees(0f).toVector2F()),
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(AngleF.fromDegrees(0f).toVector2F()),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                Wrapper(Vector2F(-4f, 3f)),
                Wrapper(AngleF.fromDegrees(-45f).toVector2F()),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
        )

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableRayArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F.ZERO,
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                ),
                0.5f,
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F.ZERO,
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                0f,
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F.ZERO,
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                0.25f,
                MutableRay(
                    origin = Vector2F(1.25f, 2.25f),
                    direction = AngleF.fromDegrees(56.25f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F.ZERO,
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                0.5f,
                MutableRay(
                    origin = Vector2F(-0.5f, 2.5f),
                    direction = AngleF.fromDegrees(22.5f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F.ZERO,
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                0.75f,
                MutableRay(
                    origin = Vector2F(-2.25f, 2.75f),
                    direction = AngleF.fromDegrees(-11.25f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F.ZERO,
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(3f, 2f),
                    direction = AngleF.fromDegrees(90f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                1f,
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val rayA = MutableRay(
                origin = Vector2F(-2f, 5f),
                direction = AngleF.fromDegrees(0f).toVector2F()
            )
            val rayAArgs = listOf(
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2.1f, 5.1f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2.1f, 5f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2.1f, 4.9f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2f, 5.1f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(rayA.origin), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-2f, 4.9f)), Wrapper(rayA.origin)
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-1.9f, 5.1f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-1.9f, 5f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(-1.9f, 4.9f)), Wrapper(Vector2F(-1.9f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(0f, 5.1f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(0f, 5f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(0f, 4.9f)), Wrapper(Vector2F(0f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(2f, 5.1f)), Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(2f, 5f)), Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(2f, 4.9f)), Wrapper(Vector2F(2f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(4f, 5.1f)), Wrapper(Vector2F(4f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(4f, 5f)), Wrapper(Vector2F(4f, 5f))
                ),
                Arguments.of(
                    rayA, Wrapper(Vector2F(4f, 4.9f)), Wrapper(Vector2F(4f, 5f))
                ),
            )
            val rayB = MutableRay(
                origin = Vector2F(3f, 2f),
                direction = AngleF.fromDegrees(-90f).toVector2F()
            )
            val rayBArgs = listOf(
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, 2.1f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, 2.1f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, 2.1f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, 2f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(rayB.origin), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, 2f)), Wrapper(rayB.origin)
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, 1.9f)), Wrapper(Vector2F(3f, 1.9f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, -0.5f)), Wrapper(Vector2F(3f, -0.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, -3f)), Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, -3f)), Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, -3f)), Wrapper(Vector2F(3f, -3f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(2.9f, -5.5f)), Wrapper(Vector2F(3f, -5.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3f, -5.5f)), Wrapper(Vector2F(3f, -5.5f))
                ),
                Arguments.of(
                    rayB, Wrapper(Vector2F(3.1f, -5.5f)), Wrapper(Vector2F(3f, -5.5f))
                ),
            )
            val rayC = MutableRay(
                origin = Vector2F(-2f, -1f),
                direction = AngleF.fromDegrees(116.56505f).toVector2F()
            )
            val rayCArgs = listOf(
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.0447214f, -1.1341641f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.9552786f, -1.0894427f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.8658359f, -1.0447214f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.0894427f, -1.0447214f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(rayC.origin),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.9105573f, -0.95527864f)),
                    Wrapper(rayC.origin)
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.134164f, -0.95527864f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-1.9552786f, -0.8658359f)),
                    Wrapper(Vector2F(-2.0447214f, -0.91055727f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-3.0894427f, 0.95527864f)),
                    Wrapper(Vector2F(-3f, 1f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(Vector2F(-3f, 1f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-2.9105573f, 1.0447214f)),
                    Wrapper(Vector2F(-3f, 1f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-4.0894427f, 2.9552786f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-3.9105573f, 3.0447214f)),
                    Wrapper(Vector2F(-4f, 3f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-5.0894427f, 4.9552784f)),
                    Wrapper(Vector2F(-5f, 5f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-5f, 5f)),
                    Wrapper(Vector2F(-5f, 5f))
                ),
                Arguments.of(
                    rayC,
                    Wrapper(Vector2F(-4.9105573f, 5.0447216f)),
                    Wrapper(Vector2F(-5f, 5f))
                ),
            )
            val mutableRayArgs = listOf(
                rayAArgs,
                rayBArgs,
                rayCArgs
            ).flatten()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsAnnulusArgs(): List<Arguments> = AnnulusTests.intersectsRayArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun intersectsCircleArgs(): List<Arguments> = CircleTests.intersectsRayArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun intersectsLineSegmentArgs(): List<Arguments> =
            LineSegmentTests.intersectsRayArgs().map {
                val args = it.get()
                Arguments.of(args[1], args[0], args[2])
            }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> {
            fun List<Arguments>.withRayArgsSwapped() = map {
                val args = it.get()
                Arguments.of(args[1], args[0], args[2])
            }

            fun createMutableRayArgs(): List<Arguments> {
                val identicalRaysArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        true
                    ),
                )
                val sameDirectionRaysArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5.1f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 4.9f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 2.1f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 1.9f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(5.1f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(4.9f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -5.1f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -4.9f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -5.1f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -4.9f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -5.1f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, -4.9f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        false
                    ),
                )
                val oppositeDirectionRaysArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, 5f),
                            direction = AngleF.fromDegrees(180f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, 5f),
                            direction = AngleF.fromDegrees(180f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 2.1f),
                            direction = AngleF.fromDegrees(-90f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(5f, 2f),
                            direction = AngleF.fromDegrees(90f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(5f, 1.9f),
                            direction = AngleF.fromDegrees(-90f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -4.9f),
                            direction = AngleF.fromDegrees(-135f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(45f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -5.1f),
                            direction = AngleF.fromDegrees(-135f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.1f, -4.9f),
                            direction = AngleF.fromDegrees(-45f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.9f, -5.1f),
                            direction = AngleF.fromDegrees(-45f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2.000001f, -5.000001f),
                            direction = AngleF.fromDegrees(-45f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, -5f),
                            direction = AngleF.fromDegrees(135f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-1.999999f, -4.999999f),
                            direction = AngleF.fromDegrees(-45f).toVector2F()
                        ),
                        true
                    ),
                )
                val allDirectionRayArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(20f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(60f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(100f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(140f).toVector2F()
                        ),
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(180f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(220f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(260f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(300f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(340f).toVector2F()
                        ),
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 4f),
                            direction = AngleF.fromDegrees(150f).toVector2F()
                        ),
                        MutableRay(
                            origin = Vector2F(-2f, 5f),
                            direction = AngleF.fromDegrees(342f).toVector2F()
                        ),
                        true
                    ),
                )
                val result = listOf(
                    identicalRaysArgs,
                    sameDirectionRaysArgs,
                    oppositeDirectionRaysArgs,
                    allDirectionRayArgs
                ).flatten()

                return result + result.withRayArgsSwapped()
            }

            val mutableRayArgs = createMutableRayArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRectangleArgs(): List<Arguments> {
            val rectangle = MutableRectangle(
                center = Vector2F(-2f, 4f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                width = 4f,
                height = 2f
            )
            val mutableRayMutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 4f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 2f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, -2f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0f, -2f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 0f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5f, 0f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 1f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 2f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, 8f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 8f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 6f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6f, 4f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 6f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 7f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1f, 8f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, 10f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11f, 8f),
                        direction = AngleF.fromDegrees(-172f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11f, 8f),
                        direction = AngleF.fromDegrees(-170f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, -7f),
                        direction = AngleF.fromDegrees(70f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, -7f),
                        direction = AngleF.fromDegrees(72f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.5f, 3.5f),
                        direction = AngleF.fromDegrees(86f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.75f, 4f),
                        direction = AngleF.fromDegrees(-173f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.25f, 3f),
                        direction = AngleF.fromDegrees(-66f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
            )
            val defaultRayMutableRectangleArgs = mutableRayMutableRectangleArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultRectangleArgs = mutableRayMutableRectangleArgs
                .mapRectanglesToDefaultRectangles()
            val defaultRayDefaultRectangleArgs = defaultRayMutableRectangleArgs
                .mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRayMutableRectangleArgs,
                defaultRayMutableRectangleArgs,
                mutableRayDefaultRectangleArgs,
                defaultRayDefaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRegularPolygonArgs(): List<Arguments> {
            fun createMutableRayMutableRegularPolygonArgs(): List<Arguments> {
                val heptagon = MutableRegularPolygon(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    sideLength = 3f,
                    sideCount = 7
                )
                val decagon = MutableRegularPolygon(
                    center = Vector2F(-14f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                    sideLength = 2f,
                    sideCount = 10
                )
                val digonArgs = intersectsLineSegmentArgs()
                    .let { it.take(it.size / 4) }
                    .map {
                        val args = it.get()
                        val ray = args[0] as Ray
                        val lineSegment = args[1] as LineSegment

                        Arguments.of(
                            MutableRay(ray.origin, ray.direction),
                            MutableRegularPolygon(
                                lineSegment.center,
                                lineSegment.orientation,
                                lineSegment.length,
                                sideCount = 2
                            ),
                            args[2]
                        )
                    }
                val regularTriangleArgs = intersectsRegularTriangleArgs()
                    .let { it.take(it.size / 4) }
                    .map {
                        val args = it.get()
                        val ray = args[0] as Ray
                        val triangle = args[1] as RegularTriangle

                        Arguments.of(
                            MutableRay(ray.origin, ray.direction),
                            MutableRegularPolygon(
                                triangle.center,
                                triangle.orientation,
                                triangle.sideLength,
                                sideCount = 3
                            ),
                            args[2]
                        )
                    }
                val squareArgs = intersectsSquareArgs()
                    .let { it.take(it.size / 4) }
                    .map {
                        val args = it.get()
                        val ray = args[0] as Ray
                        val square = args[1] as Square

                        Arguments.of(
                            MutableRay(ray.origin, ray.direction),
                            MutableRegularPolygon(
                                square.center,
                                square.orientation,
                                square.sideLength,
                                sideCount = 4
                            ),
                            args[2]
                        )
                    }
                val heptagonArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-4f, 11.8f),
                            direction = AngleF.fromDegrees(-8.53f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-4f, 11.7f),
                            direction = AngleF.fromDegrees(-8.53f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1.3f, 12f),
                            direction = AngleF.fromDegrees(-59.59f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1.2f, 12f),
                            direction = AngleF.fromDegrees(-59.59f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(3.85f, 9.2f),
                            direction = AngleF.fromDegrees(-111.45f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(3.8f, 9.2f),
                            direction = AngleF.fromDegrees(-111.45f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(3f, 5.65f),
                            direction = AngleF.fromDegrees(-162.88f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(3f, 5.68f),
                            direction = AngleF.fromDegrees(-162.88f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-0.2f, 4.35f),
                            direction = AngleF.fromDegrees(145.61f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-0.2f, 4.38f),
                            direction = AngleF.fromDegrees(145.74f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3f, 5.5f),
                            direction = AngleF.fromDegrees(94.40f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2.9f, 5.5f),
                            direction = AngleF.fromDegrees(94.40f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.8f, 8.8f),
                            direction = AngleF.fromDegrees(43.15f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.6f, 8.8f),
                            direction = AngleF.fromDegrees(43.15f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(4f, 10.6f),
                            direction = AngleF.fromDegrees(171.47f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(4f, 10.5f),
                            direction = AngleF.fromDegrees(171.47f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(4f, 7.4f),
                            direction = AngleF.fromDegrees(120.41f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(3.9f, 7.4f),
                            direction = AngleF.fromDegrees(120.41f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(2.2f, 5f),
                            direction = AngleF.fromDegrees(68.55f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(2.15f, 5f),
                            direction = AngleF.fromDegrees(68.55f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 4.11f),
                            direction = AngleF.fromDegrees(17.12f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-2f, 4.14f),
                            direction = AngleF.fromDegrees(17.12f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.4f, 6.54f),
                            direction = AngleF.fromDegrees(-34.39f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.4f, 6.56f),
                            direction = AngleF.fromDegrees(-34.26f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.3f, 9.4f),
                            direction = AngleF.fromDegrees(-85.6f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.2f, 9.4f),
                            direction = AngleF.fromDegrees(-85.6f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-0.6f, 11.8f),
                            direction = AngleF.fromDegrees(-136.85f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-0.4f, 11.8f),
                            direction = AngleF.fromDegrees(-136.85f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(14.3f, -4f),
                            direction = AngleF.fromDegrees(129.43f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(14.3f, -4f),
                            direction = AngleF.fromDegrees(130.36f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.2f, 8.4f),
                            direction = AngleF.fromDegrees(-87.88f).toVector2F()
                        ),
                        heptagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-3.2f, 8.4f),
                            direction = AngleF.fromDegrees(-82.61f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(1f, 8f),
                            direction = AngleF.fromDegrees(59.04f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-1f, 11.2f),
                            direction = AngleF.fromDegrees(-174.29f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(0f, 6f),
                            direction = AngleF.fromDegrees(-63.43f).toVector2F()
                        ),
                        heptagon,
                        true
                    ),
                )
                val decagonArgs = listOf(
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-15.6f, 4.1f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-15.6f, 4f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-13.4f, 4.4f),
                            direction = AngleF.fromDegrees(-36.03f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-13.5f, 4.4f),
                            direction = AngleF.fromDegrees(-36.03f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.5f, 3.35f),
                            direction = AngleF.fromDegrees(-72.26f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.5f, 3.25f),
                            direction = AngleF.fromDegrees(-72.26f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-10.6f, 1.35f),
                            direction = AngleF.fromDegrees(-108.08f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-10.65f, 1.4f),
                            direction = AngleF.fromDegrees(-108.08f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.2f, -0.8f),
                            direction = AngleF.fromDegrees(-143.62f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.3f, -0.8f),
                            direction = AngleF.fromDegrees(-143.62f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-12.2f, -2.1f),
                            direction = AngleF.fromDegrees(180f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-12.2f, -2.05f),
                            direction = AngleF.fromDegrees(180f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-14.3f, -2.6f),
                            direction = AngleF.fromDegrees(144.09f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-14.1f, -2.7f),
                            direction = AngleF.fromDegrees(144.09f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-16.5f, -1.4f),
                            direction = AngleF.fromDegrees(107.82f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-16.4f, -1.4f),
                            direction = AngleF.fromDegrees(107.82f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-17.4f, 0.7f),
                            direction = AngleF.fromDegrees(72.26f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-17.3f, 0.7f),
                            direction = AngleF.fromDegrees(72.26f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-16.85f, 2.75f),
                            direction = AngleF.fromDegrees(35.94f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-16.8f, 2.75f),
                            direction = AngleF.fromDegrees(35.94f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-12.4f, 4.1f),
                            direction = AngleF.fromDegrees(180f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-12.4f, 4f),
                            direction = AngleF.fromDegrees(180f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.2f, 2.8f),
                            direction = AngleF.fromDegrees(143.97f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.3f, 2.8f),
                            direction = AngleF.fromDegrees(143.97f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-10.7f, 0.85f),
                            direction = AngleF.fromDegrees(107.74f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-10.7f, 0.75f),
                            direction = AngleF.fromDegrees(107.74f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.4f, -1.1f),
                            direction = AngleF.fromDegrees(71.92f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-11.45f, -1.05f),
                            direction = AngleF.fromDegrees(71.92f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-13.1f, -2.2f),
                            direction = AngleF.fromDegrees(36.38f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-13.2f, -2.2f),
                            direction = AngleF.fromDegrees(36.38f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-15.4f, -2.1f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-15.4f, -2.05f),
                            direction = AngleF.fromDegrees(0f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-17.2f, -0.5f),
                            direction = AngleF.fromDegrees(-35.91f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-17f, -0.6f),
                            direction = AngleF.fromDegrees(-35.91f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-17.4f, 1.4f),
                            direction = AngleF.fromDegrees(-72.18f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-17.3f, 1.4f),
                            direction = AngleF.fromDegrees(-72.18f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-16.6f, 3.2f),
                            direction = AngleF.fromDegrees(-107.74f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-16.5f, 3.2f),
                            direction = AngleF.fromDegrees(-107.74f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-14.85f, 4.2f),
                            direction = AngleF.fromDegrees(-144.06f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-14.8f, 4.2f),
                            direction = AngleF.fromDegrees(-144.06f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-20f, -10f),
                            direction = AngleF.fromDegrees(46.22f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-20f, -10f),
                            direction = AngleF.fromDegrees(46.77f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-20f, 18f),
                            direction = AngleF.fromDegrees(-80.96f).toVector2F()
                        ),
                        decagon,
                        false
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-20f, 18f),
                            direction = AngleF.fromDegrees(-80.71f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-13f, 4.05f),
                            direction = AngleF.fromDegrees(53.13f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-13.1f, 0.35f),
                            direction = AngleF.fromDegrees(-37.16f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                    Arguments.of(
                        MutableRay(
                            origin = Vector2F(-16.0f, 1.5f),
                            direction = AngleF.fromDegrees(-131.63f).toVector2F()
                        ),
                        decagon,
                        true
                    ),
                )

                return listOf(
                    digonArgs,
                    regularTriangleArgs,
                    squareArgs,
                    heptagonArgs,
                    decagonArgs
                ).flatten()
            }

            val mutableRayMutableRegularPolygonArgs = createMutableRayMutableRegularPolygonArgs()
            val defaultRayMutableRegularPolygonArgs = mutableRayMutableRegularPolygonArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultRegularPolygonArgs = mutableRayMutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val defaultRayDefaultRegularPolygonArgs = defaultRayMutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()

            return listOf(
                mutableRayMutableRegularPolygonArgs,
                defaultRayMutableRegularPolygonArgs,
                mutableRayDefaultRegularPolygonArgs,
                defaultRayDefaultRegularPolygonArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRegularTriangleArgs(): List<Arguments> {
            val triangle = MutableRegularTriangle(
                center = Vector2F(5f, -7f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                sideLength = 3f
            )
            val mutableRayMutableRegularTriangleArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(6.3f, -5f),
                        direction = AngleF.fromDegrees(-100.3f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(6.2f, -5f),
                        direction = AngleF.fromDegrees(-100.3f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.4f, -7f),
                        direction = AngleF.fromDegrees(19.98f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.5f, -7f),
                        direction = AngleF.fromDegrees(19.98f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.4f, -6f),
                        direction = AngleF.fromDegrees(-40.31f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.5f, -6f),
                        direction = AngleF.fromDegrees(-40.31f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5.3f, -10.5f),
                        direction = AngleF.fromDegrees(79.7f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5.2f, -10.5f),
                        direction = AngleF.fromDegrees(79.7f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7.9f, -5f),
                        direction = AngleF.fromDegrees(-160.02f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(8f, -5f),
                        direction = AngleF.fromDegrees(-160.02f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5.7f, -8.8f),
                        direction = AngleF.fromDegrees(139.69f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5.8f, -8.8f),
                        direction = AngleF.fromDegrees(139.69f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, -4f),
                        direction = AngleF.fromDegrees(-11.31f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, -4f),
                        direction = AngleF.fromDegrees(-12.41f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(12f, 2f),
                        direction = AngleF.fromDegrees(-135.32f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(12f, 2f),
                        direction = AngleF.fromDegrees(-134.68f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4.5f, -6.5f),
                        direction = AngleF.fromDegrees(33.69f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5.5f, -6.5f),
                        direction = AngleF.fromDegrees(-23.20f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, -7.5f),
                        direction = AngleF.fromDegrees(-126.87f).toVector2F()
                    ),
                    triangle,
                    true
                ),
            )
            val defaultRayMutableRegularTriangleArgs = mutableRayMutableRegularTriangleArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultRegularTriangleArgs = mutableRayMutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val defaultRayDefaultRegularTriangleArgs = defaultRayMutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()

            return listOf(
                mutableRayMutableRegularTriangleArgs,
                defaultRayMutableRegularTriangleArgs,
                mutableRayDefaultRegularTriangleArgs,
                defaultRayDefaultRegularTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRoundedRectangleArgs(): List<Arguments> {
            val rectangle = MutableRoundedRectangle(
                center = Vector2F(-3f, -4f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                width = 8f,
                height = 4f,
                cornerRadius = 1f
            )
            val mutableRayMutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -9.2f),
                        direction = AngleF.fromDegrees(29.54f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -9.0f),
                        direction = AngleF.fromDegrees(29.54f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.75f, -2.0f),
                        direction = AngleF.fromDegrees(29.74f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.4f, -2.0f),
                        direction = AngleF.fromDegrees(29.74f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.75f, -9.0f),
                        direction = AngleF.fromDegrees(119.78f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.25f, -9.0f),
                        direction = AngleF.fromDegrees(120.26f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1.9f, -8.0f),
                        direction = AngleF.fromDegrees(120.49f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1.5f, -8.0f),
                        direction = AngleF.fromDegrees(120.26f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -7.75f),
                        direction = AngleF.fromDegrees(-14.04f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4.0f, -7.5f),
                        direction = AngleF.fromDegrees(-14.93f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.15f, -8.0f),
                        direction = AngleF.fromDegrees(80.33f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-0.1f, -8.0f),
                        direction = AngleF.fromDegrees(79.92f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.0f, -0.25f),
                        direction = AngleF.fromDegrees(162.12f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.0f, -0.45f),
                        direction = AngleF.fromDegrees(161.29f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6.0f, -0.5f),
                        direction = AngleF.fromDegrees(-109.18f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.8f, -0.6f),
                        direction = AngleF.fromDegrees(-109.18f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.0f, -5.8f),
                        direction = AngleF.fromDegrees(-150.46f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.0f, -5.6f),
                        direction = AngleF.fromDegrees(-150.46f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.5f, 1.0f),
                        direction = AngleF.fromDegrees(-150.26f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.5f, 0.8f),
                        direction = AngleF.fromDegrees(-150.26f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.9f, 0.0f),
                        direction = AngleF.fromDegrees(-60.22f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.5f, 0.0f),
                        direction = AngleF.fromDegrees(-59.74f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-3.4f, 1.0f),
                        direction = AngleF.fromDegrees(-59.51f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-3.75f, 1.0f),
                        direction = AngleF.fromDegrees(-59.74f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.0f, -8.5f),
                        direction = AngleF.fromDegrees(165.96f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.0f, -8.3f),
                        direction = AngleF.fromDegrees(165.07f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.9f, -3.6f),
                        direction = AngleF.fromDegrees(-99.67f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.7f, -3.5f),
                        direction = AngleF.fromDegrees(-100.08f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.1f, 0.75f),
                        direction = AngleF.fromDegrees(-17.88f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.1f, 0.6f),
                        direction = AngleF.fromDegrees(-18.71f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6.8f, -2.8f),
                        direction = AngleF.fromDegrees(70.82f).toVector2F()
                    ),
                    rectangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-6.6f, -2.9f),
                        direction = AngleF.fromDegrees(70.82f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-3.3f, -0.6f),
                        direction = AngleF.fromDegrees(72.12f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5.0f, -3.0f),
                        direction = AngleF.fromDegrees(-116.57f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2.8f, -4.3f),
                        direction = AngleF.fromDegrees(-22.99f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1.3f, -7.0f),
                        direction = AngleF.fromDegrees(-50.91f).toVector2F()
                    ),
                    rectangle,
                    true
                ),
            )
            val defaultRayMutableRoundedRectangleArgs = mutableRayMutableRoundedRectangleArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultRoundedRectangleArgs = mutableRayMutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val defaultRayDefaultRoundedRectangleArgs = defaultRayMutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()

            return listOf(
                mutableRayMutableRoundedRectangleArgs,
                defaultRayMutableRoundedRectangleArgs,
                mutableRayDefaultRoundedRectangleArgs,
                defaultRayDefaultRoundedRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsSquareArgs(): List<Arguments> {
            val square = MutableSquare(
                center = Vector2F(8f, -2f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                sideLength = 3f
            )
            val mutableRayMutableSquareArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7.5f, -5f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7f, -5f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, -3f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4.5f, -3f),
                        direction = AngleF.fromDegrees(45f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(8.5f, -5f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(9f, -5f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11f, -3f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(11.5f, -3f),
                        direction = AngleF.fromDegrees(135f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(13f, 0.5f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(12.5f, 0.5f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(10f, 2f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(9.5f, 2f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4.5f, -1f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, -1f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7f, 1f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7.5f, 1f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, -4f),
                        direction = AngleF.fromDegrees(-1.5f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2f, -4f),
                        direction = AngleF.ZERO.toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(14f, -5f),
                        direction = AngleF.fromDegrees(139f).toVector2F()
                    ),
                    square,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(14f, -5f),
                        direction = AngleF.fromDegrees(141f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(8.5f, -1.5f),
                        direction = AngleF.fromDegrees(6f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(7f, -2f),
                        direction = AngleF.fromDegrees(117f).toVector2F()
                    ),
                    square,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(8f, -3f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    square,
                    true
                ),
            )
            val defaultRayMutableSquareArgs = mutableRayMutableSquareArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultSquareArgs = mutableRayMutableSquareArgs
                .mapSquaresToDefaultSquares()
            val defaultRayDefaultSquareArgs = defaultRayMutableSquareArgs
                .mapSquaresToDefaultSquares()

            return listOf(
                mutableRayMutableSquareArgs,
                defaultRayMutableSquareArgs,
                mutableRayDefaultSquareArgs,
                defaultRayDefaultSquareArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsTriangleArgs(): List<Arguments> {
            val triangle = MutableTriangle(
                centroid = Vector2F(-0.3333333f, 3f),
                pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)),
                pointDistanceA = 3.8005848f,
                pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(141.54628f)),
                pointDistanceB = 2.538591f,
                pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-138.94519f)),
                pointDistanceC = 2.4037008f
            )
            val mutableRayMutableTriangleArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(3.4f, -2f),
                        direction = AngleF.fromDegrees(108.43f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(3.2f, -2f),
                        direction = AngleF.fromDegrees(108.43f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-8f, 1.9f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-8f, 2.1f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.5f, 0f),
                        direction = AngleF.fromDegrees(30.96f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-7.2f, 0f),
                        direction = AngleF.fromDegrees(30.96f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.4f, 7f),
                        direction = AngleF.fromDegrees(-71.57f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.2f, 7f),
                        direction = AngleF.fromDegrees(-71.57f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4f, 1.9f),
                        direction = AngleF.fromDegrees(180f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(4f, 2.1f),
                        direction = AngleF.fromDegrees(180f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.5f, 6f),
                        direction = AngleF.fromDegrees(-149.04f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(2.8f, 6f),
                        direction = AngleF.fromDegrees(-149.04f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, 1f),
                        direction = AngleF.fromDegrees(132.88f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(5f, 1f),
                        direction = AngleF.fromDegrees(136.97f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5f, -1f),
                        direction = AngleF.fromDegrees(21.8f).toVector2F()
                    ),
                    triangle,
                    false
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-5f, -1f),
                        direction = AngleF.fromDegrees(24.23f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(0.5f, 3.5f),
                        direction = AngleF.fromDegrees(28.3f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-1f, 3f),
                        direction = AngleF.fromDegrees(149.04f).toVector2F()
                    ),
                    triangle,
                    true
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(1f, 2.5f),
                        direction = AngleF.fromDegrees(-102.53f).toVector2F()
                    ),
                    triangle,
                    true
                ),
            )
            val defaultRayMutableTriangleArgs = mutableRayMutableTriangleArgs
                .mapRaysToDefaultRays()
            val mutableRayDefaultTriangleArgs = mutableRayMutableTriangleArgs
                .mapTrianglesToDefaultTriangles()
            val defaultRayDefaultTriangleArgs = defaultRayMutableTriangleArgs
                .mapTrianglesToDefaultTriangles()

            return listOf(
                mutableRayMutableTriangleArgs,
                defaultRayMutableTriangleArgs,
                mutableRayDefaultTriangleArgs,
                defaultRayDefaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val rayA = MutableRay(
                origin = Vector2F(-2f, 5f),
                direction = AngleF.fromDegrees(0f).toVector2F()
            )
            val rayAArgs = listOf(
                Arguments.of(rayA, Wrapper(Vector2F(-2.1f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-2.1f, 5f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-2.1f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-2f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(rayA.origin), true),
                Arguments.of(rayA, Wrapper(Vector2F(-2f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-1.9f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(-1.9f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(-1.9f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(0f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(0f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(0f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(2f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(2f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(2f, 4.9f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(4f, 5.1f)), false),
                Arguments.of(rayA, Wrapper(Vector2F(4f, 5f)), true),
                Arguments.of(rayA, Wrapper(Vector2F(4f, 4.9f)), false),
            )
            val rayB = MutableRay(
                origin = Vector2F(3f, 2f),
                direction = AngleF.fromDegrees(-90f).toVector2F()
            )
            val rayBArgs = listOf(
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, 2.1f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, 2.1f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, 2.1f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, 2f)), false),
                Arguments.of(rayB, Wrapper(rayB.origin), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, 2f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, 1.9f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, 1.9f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, 1.9f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, -0.5f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, -0.5f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, -0.5f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, -3f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, -3f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, -3f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(2.9f, -5.5f)), false),
                Arguments.of(rayB, Wrapper(Vector2F(3f, -5.5f)), true),
                Arguments.of(rayB, Wrapper(Vector2F(3.1f, -5.5f)), false),
            )
            val rayC = MutableRay(
                origin = Vector2F(-2f, -1f),
                direction = AngleF.fromDegrees(116.56505f).toVector2F()
            )
            val rayCArgs = listOf(
                Arguments.of(rayC, Wrapper(Vector2F(-2.0447214f, -1.1341641f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-1.9552786f, -1.0894427f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-1.8658359f, -1.0447214f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-2.0894427f, -1.0447214f)), false),
                Arguments.of(rayC, Wrapper(rayC.origin), true),
                Arguments.of(rayC, Wrapper(Vector2F(-1.9105573f, -0.95527864f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-2.134164f, -0.95527864f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-2.0447214f, -0.91055727f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-1.9552786f, -0.8658359f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-3.0894427f, 0.95527864f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-3f, 1f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-2.9105573f, 1.0447214f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-4.0894427f, 2.9552786f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-4f, 3f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-3.9105573f, 3.0447214f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-5.0894427f, 4.9552784f)), false),
                Arguments.of(rayC, Wrapper(Vector2F(-5f, 5f)), true),
                Arguments.of(rayC, Wrapper(Vector2F(-4.9105573f, 5.0447216f)), false),
            )
            val mutableRayArgs = listOf(
                rayAArgs,
                rayBArgs,
                rayCArgs
            ).flatten()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableRayArgs = setArgs()
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableLineSegmentArgs() + listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                null,
                false
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                DefaultRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                true
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                DefaultRay(
                    origin = Vector2F(-2.1f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableLineSegmentArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                true
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-2.1f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                )
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-2f, 5f),
                    direction = AngleF.fromDegrees(0f).toVector2F()
                ),
                "Ray(" +
                        "origin=${Vector2F(-2f, 5f)}, " +
                        "direction=${AngleF.fromDegrees(0f).toVector2F()})"
            ),
            Arguments.of(
                MutableRay(
                    origin = Vector2F(-4f, 3f),
                    direction = AngleF.fromDegrees(-45f).toVector2F()
                ),
                "Ray(" +
                        "origin=${Vector2F(-4f, 3f)}, " +
                        "direction=${AngleF.fromDegrees(-45f).toVector2F()})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableRayArgs = listOf(
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-2f, 5f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    Wrapper(Vector2F(-2f, 5f)),
                    Wrapper(AngleF.fromDegrees(0f).toVector2F())
                ),
                Arguments.of(
                    MutableRay(
                        origin = Vector2F(-4f, 3f),
                        direction = AngleF.fromDegrees(-45f).toVector2F()
                    ),
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(AngleF.fromDegrees(-45f).toVector2F())
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()

            return listOf(
                mutableRayArgs,
                defaultRayArgs
            ).flatten()
        }
    }
}