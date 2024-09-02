package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.AnnulusTests.Companion.mapAnnulusesToDefaultAnnuluses
import com.sztorm.lowallocmath.euclidean2d.CircleTests.Companion.mapCirclesToDefaultCircles
import com.sztorm.lowallocmath.euclidean2d.LineSegmentTests.Companion.mapLineSegmentsToDefaultLineSegments
import com.sztorm.lowallocmath.euclidean2d.RayTests.Companion.mapRaysToDefaultRays
import com.sztorm.lowallocmath.euclidean2d.RectangleTests.Companion.mapRectanglesToDefaultRectangles
import com.sztorm.lowallocmath.euclidean2d.RegularPolygonTests.Companion.mapRegularPolygonsToDefaultRegularPolygons
import com.sztorm.lowallocmath.euclidean2d.RegularTriangleTests.Companion.mapRegularTrianglesToDefaultRegularTriangles
import com.sztorm.lowallocmath.euclidean2d.RoundedRectangleTests.Companion.mapRoundedRectanglesToDefaultRoundedRectangles
import com.sztorm.lowallocmath.euclidean2d.SquareTests.Companion.mapSquaresToDefaultSquares
import com.sztorm.lowallocmath.euclidean2d.TriangleTests.Companion.mapTrianglesToDefaultTriangles
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import com.sztorm.lowallocmath.utils.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertTrue

class TransformableTests {
    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        expected: Wrapper<Vector2F>
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Vector2F = transformable.position

        assertApproximation(expected.value, actual)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        displacement: Wrapper<Vector2F>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.movedBy(displacement.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        position: Wrapper<Vector2F>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.movedTo(position.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("moveByArgs")
    fun moveByMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        displacement: Wrapper<Vector2F>,
        expected: MutableTransformable
    ) = assertEquals(
        expected, transformable.apply { moveBy(displacement.value) }, equalityComparator
    )

    @ParameterizedTest
    @MethodSource("moveToArgs")
    fun moveToMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        position: Wrapper<Vector2F>,
        expected: MutableTransformable
    ) = assertEquals(expected, transformable.apply { moveTo(position.value) }, equalityComparator)

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        expected: Wrapper<ComplexF>
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: ComplexF = transformable.orientation

        assertApproximation(expected.value, actual)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        rotation: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.rotatedBy(rotation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        rotation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.rotatedBy(rotation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        orientation: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.rotatedTo(orientation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        orientation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.rotatedTo(orientation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FAngleFArgs")
    fun rotatedAroundPointByVector2FAngleFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.rotatedAroundPointBy(point.value, rotation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FComplexFArgs")
    fun rotatedAroundPointByVector2FComplexFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.rotatedAroundPointBy(point.value, rotation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FAngleFArgs")
    fun rotatedAroundPointToVector2FAngleFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable =
            transformable.rotatedAroundPointTo(point.value, orientation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FComplexFArgs")
    fun rotatedAroundPointToVector2FComplexFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable =
            transformable.rotatedAroundPointTo(point.value, orientation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("rotateByAngleFArgs")
    fun rotateByAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        rotation: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected, transformable.apply { rotateBy(rotation.value) }, equalityComparator
    )

    @ParameterizedTest
    @MethodSource("rotateByComplexFArgs")
    fun rotateByComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        rotation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected, transformable.apply { rotateBy(rotation.value) }, equalityComparator
    )

    @ParameterizedTest
    @MethodSource("rotateToAngleFArgs")
    fun rotateToAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        orientation: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected, transformable.apply { rotateTo(orientation.value) }, equalityComparator
    )

    @ParameterizedTest
    @MethodSource("rotateToComplexFArgs")
    fun rotateToComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        orientation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected, transformable.apply { rotateTo(orientation.value) }, equalityComparator
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FAngleFArgs")
    fun rotateAroundPointByVector2FAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { rotateAroundPointBy(point.value, rotation.value) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FComplexFArgs")
    fun rotateAroundPointByVector2FComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { rotateAroundPointBy(point.value, rotation.value) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FAngleFArgs")
    fun rotateAroundPointToVector2FAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { rotateAroundPointTo(point.value, orientation.value) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FComplexFArgs")
    fun rotateAroundPointToVector2FComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { rotateAroundPointTo(point.value, orientation.value) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        factor: Float,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.scaledBy(factor)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("dilatedByArgs")
    fun dilatedByReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        point: Wrapper<Vector2F>,
        factor: Float,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.dilatedBy(point.value, factor)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("scaleByArgs")
    fun scaleByMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        factor: Float,
        expected: MutableTransformable
    ) = assertEquals(expected, transformable.apply { scaleBy(factor) }, equalityComparator)

    @ParameterizedTest
    @MethodSource("dilateByArgs")
    fun dilateByMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        point: Wrapper<Vector2F>,
        factor: Float,
        expected: MutableTransformable
    ) = assertEquals(
        expected, transformable.apply { dilateBy(point.value, factor) }, equalityComparator
    )

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFArgs")
    fun transformedByVector2FAngleFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedBy(displacement.value, rotation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFArgs")
    fun transformedByVector2FComplexFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedBy(displacement.value, rotation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFFloatArgs")
    fun transformedByVector2FAngleFFloatReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable =
            transformable.transformedBy(displacement.value, rotation.value, scaleFactor)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFFloatArgs")
    fun transformedByVector2FComplexFFloatReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable =
            transformable.transformedBy(displacement.value, rotation.value, scaleFactor)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FAngleFArgs")
    fun transformedToVector2FAngleFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedTo(position.value, orientation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FComplexFArgs")
    fun transformedToVector2FComplexFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedTo(position.value, orientation.value)

        assertEquals(expected, actual, equalityComparator)
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFArgs")
    fun transformByVector2FAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { transformBy(displacement.value, rotation.value) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFArgs")
    fun transformByVector2FComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { transformBy(displacement.value, rotation.value) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFFloatArgs")
    fun transformByVector2FAngleFFloatMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { transformBy(displacement.value, rotation.value, scaleFactor) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFFloatArgs")
    fun transformByVector2FComplexFFloatMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { transformBy(displacement.value, rotation.value, scaleFactor) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FAngleFArgs")
    fun transformToVector2FAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { transformTo(position.value, orientation.value) },
        equalityComparator
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FComplexFArgs")
    fun transformToVector2FComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertEquals(
        expected,
        transformable.apply { transformTo(position.value, orientation.value) },
        equalityComparator
    )

    companion object {
        @JvmStatic
        fun positionArgs(): List<Arguments> {
            val annulusArgs = AnnulusTests.positionArgs().map {
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val circleArgs = CircleTests.positionArgs().map {
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val lineSegmentArgs = LineSegmentTests.positionArgs().map {
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val rayArgs = RayTests.positionArgs().map {
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val rectangleArgs = RectangleTests.positionArgs().map {
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val regularPolygonArgs = RegularPolygonTests.positionArgs().map {
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val regularTriangleArgs = RegularTriangleTests.positionArgs().map {
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val roundedRectangleArgs = RoundedRectangleTests.positionArgs().map {
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val squareArgs = SquareTests.positionArgs().map {
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val triangleArgs = TriangleTests.positionArgs().map {
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            return listOf(
                annulusArgs,
                circleArgs,
                lineSegmentArgs,
                rayArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableAnnulus(
                        center = Vector2F(-0.5f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableLineSegment(Vector2F(-8f, 5f), Vector2F(-6f, 1f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableLineSegment(Vector2F(-3.5f, 3f), Vector2F(-1.5f, -1f)),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRay(Vector2F(-8f, 5f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRay(Vector2F(-3.5f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRectangle(
                        center = Vector2F(-0.5f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRegularPolygon(
                        Vector2F(-4f, 10f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRegularPolygon(
                        Vector2F(0.5f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRegularTriangle(
                        center = Vector2F(5.5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableSquare(
                        center = Vector2F(3.5f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableTriangle(
                        centroid = Vector2F(-0.8333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableAnnulus(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableAnnulus(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableLineSegment(Vector2F(-5f, 4f), Vector2F(-3f, 0f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableLineSegment(Vector2F(-0.5f, 2f), Vector2F(1.5f, -2f)),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRay(Vector2F(-4f, 2f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRay(Vector2F(0.5f, 0f), Vector2F(0.7071068f, -0.7071068f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRectangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRectangle(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRegularPolygon(
                        Vector2F(-4f, 2f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRegularPolygon(
                        Vector2F(0.5f, 0f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRegularTriangle(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRoundedRectangle(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableSquare(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableSquare(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableTriangle(
                        centroid = Vector2F(-4f, 2f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableTriangle(
                        centroid = Vector2F(0.5f, 0f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = movedByArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun moveToArgs(): List<Arguments> = movedToArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val annulusArgs = AnnulusTests.orientationArgs().map {
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val circleArgs = CircleTests.orientationArgs().map {
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val lineSegmentArgs = LineSegmentTests.orientationArgs().map {
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val rayArgs = RayTests.orientationArgs().map {
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val rectangleArgs = RectangleTests.orientationArgs().map {
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val regularPolygonArgs = RegularPolygonTests.orientationArgs().map {
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val regularTriangleArgs = RegularTriangleTests.orientationArgs().map {
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val roundedRectangleArgs = RoundedRectangleTests.orientationArgs().map {
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    *it.get()
                )
            }
            val squareArgs = SquareTests.orientationArgs().map {
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            val triangleArgs = TriangleTests.orientationArgs().map {
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    *it.get()
                )
            }
            return listOf(
                annulusArgs,
                circleArgs,
                lineSegmentArgs,
                rayArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(-5.1213202f, 1.7071068f),
                        Vector2F(-0.87867975f, 0.29289323f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(-2.7443476f, -1.2214055f),
                        Vector2F(-3.2556524f, 3.2214055f)
                    ),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(1f, 0f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(-0.42261827f, 0.9063078f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-3.0641298f, 2.1213202f),
                        originPointB = Vector2F(-0.942809f, -1.4142137f),
                        originPointC = Vector2F(4.0069385f, -0.70710707f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.62761235f, -3.6735532f),
                        originPointB = Vector2F(1.680161f, -0.2568028f),
                        originPointC = Vector2F(-1.0525489f, 3.9303558f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedByComplexFArgs(): List<Arguments> = rotatedByAngleFArgs().map { args ->
            Arguments.of(
                *args.get().copyOf().apply {
                    val angle = (get(3) as Wrapper<*>).value as AngleF
                    set(3, Wrapper(ComplexF.fromAngle(angle)))
                }
            )
        }

        @JvmStatic
        fun rotatedToAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(-1.4188612f, 2.5811388f),
                        Vector2F(-4.5811386f, -0.58113885f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(-5.1012163f, 1.7647803f),
                        Vector2F(-0.89878345f, 0.23521966f)
                    ),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.70710677f, 0.70710677f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(-0.9396926f, 0.34202015f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(2.6352313f, 2.6352308f),
                        originPointB = Vector2F(-1.2227473f, 1.1805834f),
                        originPointC = Vector2F(-1.4124844f, -3.8158147f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-3.5020275f, 1.2746335f),
                        originPointB = Vector2F(-0.5532168f, -1.6071217f),
                        originPointC = Vector2F(4.055244f, 0.33248734f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedToComplexFArgs(): List<Arguments> = rotatedToAngleFArgs().map { args ->
            Arguments.of(
                *args.get().copyOf().apply {
                    val angle = (get(3) as Wrapper<*>).value as AngleF
                    set(3, Wrapper(ComplexF.fromAngle(angle)))
                }
            )
        }

        @JvmStatic
        fun rotatedAroundPointByVector2FAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-2.485281f, -4.4142137f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(10.867748f, -10.092604f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(-5.313708f, -5.8284273f),
                        Vector2F(-1.0710673f, -7.2426405f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(13.344806f, -12.058357f),
                        Vector2F(12.833501f, -7.6155467f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(-5.1213202f, 1.7071068f),
                        Vector2F(-0.87867975f, 0.29289323f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(-2.7443476f, -1.2214055f),
                        Vector2F(-3.2556524f, 3.2214055f)
                    ),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(Vector2F(-5.3137083f, -5.8284273f), Vector2F(1f, 0f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(
                        Vector2F(13.344805f, -12.058357f),
                        Vector2F(-0.42261827f, 0.9063078f)
                    ),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(1f, 0f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(-0.42261827f, 0.9063078f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(0.34314585f, -7.242641f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(12.235828f, -6.3338337f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(-6.020815f, 0.5355339f),
                        ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(7.875934f, -15.38874f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0f, 8f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0f, 8f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(-1.7781744f, 3.3639612f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(3.5194912f, -12.738946f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(0.34314585f, -10.071068f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(14.799253f, -5.138489f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(1.0502529f, -2.2928932f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(7.4509974f, -7.784831f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(0.5788478f, -7.949747f),
                        originPointA = Vector2F(-3.0641294f, 2.1213202f),
                        originPointB = Vector2F(-0.9428089f, -1.4142141f),
                        originPointC = Vector2F(4.0069385f, -0.70710754f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(12.777071f, -5.821378f),
                        originPointA = Vector2F(-0.62761116f, -3.673554f),
                        originPointB = Vector2F(1.6801624f, -0.25680304f),
                        originPointC = Vector2F(-1.0525475f, 3.9303555f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-3.0641298f, 2.1213202f),
                        originPointB = Vector2F(-0.942809f, -1.4142137f),
                        originPointC = Vector2F(4.0069385f, -0.70710707f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.62761235f, -3.6735532f),
                        originPointB = Vector2F(1.680161f, -0.2568028f),
                        originPointC = Vector2F(-1.0525489f, 3.9303558f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedAroundPointByVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointByVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(4) as Wrapper<*>).value as AngleF
                        set(4, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun rotatedAroundPointToVector2FAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(12.082763f, 3.0827627f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-144.46233f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-2.0835419f, -0.057831287f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-29.462322f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(15.189864f, 4.1795816f),
                        Vector2F(10.738524f, 3.748807f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(-4.390715f, 2.2946236f),
                        Vector2F(-2.119083f, -1.5576079f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(-1.4188612f, 2.5811388f),
                        Vector2F(-4.5811386f, -0.58113885f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-3f, 1f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(-5.1012163f, 1.7647803f),
                        Vector2F(-0.89878345f, 0.23521966f)
                    ),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(
                        Vector2F(14.246211f, 5.246211f),
                        Vector2F(-0.85749304f, -0.51449573f)
                    ),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(
                        Vector2F(-4.958605f, 0.988606f),
                        Vector2F(0.82868373f, -0.5597173f)
                    ),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.70710677f, 0.70710677f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(Vector2F(-4f, 3f), Vector2F(-0.9396926f, 0.34202015f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(11f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-6.8698964f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-0.64463043f, -0.58155227f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(108.130104f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(14.860023f, 5.8600225f),
                        ComplexF.fromAngle(AngleF.fromDegrees(46.38954f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(-5.774315f, 1.2855005f),
                        ComplexF.fromAngle(AngleF.fromDegrees(161.38954f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0f, 8f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0f, 8f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(13.106335f, 4.1063347f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-90.710594f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(-3.4437933f, 0.4372599f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(24.289406f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(12.403124f, 3.4031243f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(158.6598f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5092793f, 0.09712434f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-86.340195f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(9.535534f, 0.5355339f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-21.869894f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(1.301537f, -1.2898992f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(93.13011f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(11.190803f, 2.1908038f),
                        originPointA = Vector2F(3.1572943f, -1.9799975f),
                        originPointB = Vector2F(0.87762165f, 1.4555657f),
                        originPointC = Vector2F(-4.0349126f, 0.5244317f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-0.89819396f, -0.4892627f),
                        originPointA = Vector2F(0.46015722f, 3.6982622f),
                        originPointB = Vector2F(-1.6900891f, 0.18024561f),
                        originPointC = Vector2F(1.2299317f, -3.878508f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(2.6352313f, 2.6352308f),
                        originPointB = Vector2F(-1.2227473f, 1.1805834f),
                        originPointC = Vector2F(-1.4124844f, -3.8158147f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-3.5020275f, 1.2746335f),
                        originPointB = Vector2F(-0.5532168f, -1.6071217f),
                        originPointC = Vector2F(4.055244f, 0.33248734f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedAroundPointToVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointToVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(4) as Wrapper<*>).value as AngleF
                        set(4, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun rotateByAngleFArgs(): List<Arguments> = rotatedByAngleFArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun rotateByComplexFArgs(): List<Arguments> = rotatedByComplexFArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun rotateToAngleFArgs(): List<Arguments> = rotatedToAngleFArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun rotateToComplexFArgs(): List<Arguments> = rotatedToComplexFArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun rotateAroundPointByVector2FAngleFArgs(): List<Arguments> =
            rotatedAroundPointByVector2FAngleFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun rotateAroundPointByVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointByVector2FComplexFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun rotateAroundPointToVector2FAngleFArgs(): List<Arguments> =
            rotatedAroundPointToVector2FAngleFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun rotateAroundPointToVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointToVector2FComplexFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun scaledByArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    2f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 8f,
                        innerRadius = 4f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    0.3f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 1.2f,
                        innerRadius = 0.6f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    1f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    -1f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    2f,
                    MutableLineSegment(Vector2F(-5f, 5f), Vector2F(-1f, -3f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    0.3f,
                    MutableLineSegment(
                        Vector2F(-3.3f, 1.6f), Vector2F(-2.6999998f, 0.39999998f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    1f,
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    -1f,
                    MutableLineSegment(Vector2F(-2f, -1f), Vector2F(-4f, 3f)),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    2f,
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    0.3f,
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    1f,
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    -1f,
                    MutableRay(Vector2F(-4f, 3f), Vector2F(-0.7071068f, 0.7071068f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    2f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 6f,
                        height = 10f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    0.3f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 0.9f,
                        height = 1.5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    1f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    -1f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(300f)),
                        width = 3f,
                        height = 5f
                    ),
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    2f,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 6f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    0.3f,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 0.9f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    1f,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    -1f,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(300f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    2f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 6f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    0.3f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 0.9f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    1f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    -1f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(140f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    2f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 16f,
                        height = 8f,
                        cornerRadius = 2f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    0.3f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 2.4f,
                        height = 1.2f,
                        cornerRadius = 0.3f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    1f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    -1f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    2f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 8f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    0.3f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 1.2f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    1f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    -1f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(240f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-1.3333333f, 7.333333f),
                        originPointB = Vector2F(-3.3333333f, -0.6666667f),
                        originPointC = Vector2F(4.666667f, -6.666667f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    0.3f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.2f, 1.1f),
                        originPointB = Vector2F(-0.5f, -0.1f),
                        originPointC = Vector2F(0.7f, -1f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    1f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    -1f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(0.6666667f, -3.6666667f),
                        originPointB = Vector2F(1.6666667f, 0.3333333f),
                        originPointC = Vector2F(-2.3333333f, 3.3333333f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun dilatedByArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableAnnulus(
                        center = Vector2F(-8f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 8f,
                        innerRadius = 4f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableAnnulus(
                        center = Vector2F(3.9f, -1.5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 1.2f,
                        innerRadius = 0.6f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableAnnulus(
                        center = Vector2F(13f, -8f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    2f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 8f,
                        innerRadius = 4f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableLineSegment(Vector2F(-14f, 9f), Vector2F(-10f, 1f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableLineSegment(Vector2F(3f, -1.2f), Vector2F(3.6f, -2.4f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableLineSegment(Vector2F(16f, -9f), Vector2F(14f, -5f)),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-3f, 1f)),
                    2f,
                    MutableLineSegment(Vector2F(-5f, 5f), Vector2F(-1f, -3f)),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableRay(Vector2F(-14f, 9f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableRay(Vector2F(3f, -1.2f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableRay(Vector2F(16f, -9f), Vector2F(-0.7071068f, 0.7071068f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 3f)),
                    2f,
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableRectangle(
                        center = Vector2F(-8f, -1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 6f,
                        height = 10f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableRectangle(
                        center = Vector2F(3.8999999f, -2.6999998f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 0.9f,
                        height = 1.5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableRectangle(
                        center = Vector2F(13f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(300f)),
                        width = 3f,
                        height = 5f
                    ),
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                    2f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 6f,
                        height = 10f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableRegularPolygon(
                        Vector2F(-6f, 19f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 6f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableRegularPolygon(
                        Vector2F(4.2f, 0.3f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 0.9f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableRegularPolygon(
                        Vector2F(12f, -14f),
                        ComplexF.fromAngle(AngleF.fromDegrees(300f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0f, 8f)),
                    2f,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 6f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableRegularTriangle(
                        center = Vector2F(4f, 17f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 6f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableRegularTriangle(
                        center = Vector2F(5.7f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 0.9f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableRegularTriangle(
                        center = Vector2F(7f, -13f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(140f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    2f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 6f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableRoundedRectangle(
                        center = Vector2F(-12f, -5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 16f,
                        height = 8f,
                        cornerRadius = 2f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableRoundedRectangle(
                        center = Vector2F(3.2999997f, -3.3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 2.4f,
                        height = 1.2f,
                        cornerRadius = 0.3f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableRoundedRectangle(
                        center = Vector2F(15f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    2f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 16f,
                        height = 8f,
                        cornerRadius = 2f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableSquare(
                        center = Vector2F(0f, 5f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 8f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableSquare(
                        center = Vector2F(5.1f, -1.8f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 1.2f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableSquare(
                        center = Vector2F(9f, -7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(240f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                    2f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 8f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-8.666666f, -2.3333333f),
                        originPointA = Vector2F(-1.3333333f, 7.333333f),
                        originPointB = Vector2F(-3.3333333f, -0.6666667f),
                        originPointC = Vector2F(4.666667f, -6.666667f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableTriangle(
                        centroid = Vector2F(3.8f, -2.9f),
                        originPointA = Vector2F(-0.2f, 1.1f),
                        originPointB = Vector2F(-0.5f, -0.1f),
                        originPointC = Vector2F(0.7f, -1f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableTriangle(
                        centroid = Vector2F(13.333333f, -3.3333333f),
                        originPointA = Vector2F(0.6666667f, -3.6666667f),
                        originPointB = Vector2F(1.6666667f, 0.3333333f),
                        originPointC = Vector2F(-2.3333333f, 3.3333333f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-1.3333333f, 7.333333f),
                        originPointB = Vector2F(-3.3333333f, -0.6666667f),
                        originPointC = Vector2F(4.666667f, -6.666667f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = scaledByArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun dilateByArgs(): List<Arguments> = dilatedByArgs().mapNotNull { args ->
            val argArray: Array<Any?> = args.get()

            if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                Arguments.of(*argArray.drop(1).toTypedArray())
            } else null
        }

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-0.5f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(-9.121321f, 3.7071068f),
                        Vector2F(-4.8786798f, 2.2928932f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(-2.2443476f, -1.2214055f),
                        Vector2F(-2.7556524f, 3.2214055f)
                    ),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(Vector2F(-8f, 5f), Vector2F(1f, 0f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(
                        Vector2F(-3.5f, 3f), Vector2F(-0.42261827f, 0.9063078f)
                    ),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-0.5f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(-4f, 10f),
                        ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(0.5f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5.5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3.5f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        originPointA = Vector2F(-3.0641298f, 2.1213202f),
                        originPointB = Vector2F(-0.9428091f, -1.4142137f),
                        originPointC = Vector2F(4.0069385f, -0.7071069f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-0.8333333f, -2.6666667f),
                        originPointA = Vector2F(-0.62761223f, -3.6735532f),
                        originPointB = Vector2F(1.6801611f, -0.2568028f),
                        originPointC = Vector2F(-1.0525489f, 3.9303558f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun transformedByVector2FComplexFArgs(): List<Arguments> =
            transformedByVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(4) as Wrapper<*>).value as AngleF
                        set(4, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformedByVector2FAngleFFloatArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 8f,
                        innerRadius = 4f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableAnnulus(
                        center = Vector2F(-0.5f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 1.2f,
                        innerRadius = 0.6f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(180f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableLineSegment(
                        Vector2F(-11.242641f, 4.4142137f),
                        Vector2F(-2.7573595f, 1.5857863f)
                    )
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableLineSegment(
                        Vector2F(-2.4233043f, 0.33357832f),
                        Vector2F(-2.5766957f, 1.6664217f)
                    )
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableLineSegment(
                        Vector2F(-9.121321f, 3.7071068f),
                        Vector2F(-4.8786798f, 2.2928932f)
                    )
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableLineSegment(
                        Vector2F(-4.8786798f, 2.2928932f),
                        Vector2F(-9.121321f, 3.7071068f)
                    )
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRay(Vector2F(-8f, 5f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRay(
                        Vector2F(-3.5f, 3f), Vector2F(-0.42261827f, 0.9063078f)
                    )
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableRay(Vector2F(-8f, 5f), Vector2F(1f, 0f))
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableRay(Vector2F(-8f, 5f), Vector2F(-1f, 0f))
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 6f,
                        height = 10f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRectangle(
                        center = Vector2F(-0.5f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 0.9f,
                        height = 1.5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(345f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRegularPolygon(
                        Vector2F(-4f, 10f),
                        ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        sideLength = 6f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRegularPolygon(
                        Vector2F(0.5f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        sideLength = 0.9f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableRegularPolygon(
                        Vector2F(-4f, 10f),
                        ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableRegularPolygon(
                        Vector2F(-4f, 10f),
                        ComplexF.fromAngle(AngleF.fromDegrees(345f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 6f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRegularTriangle(
                        center = Vector2F(5.5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 0.9f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(185f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 16f,
                        height = 8f,
                        cornerRadius = 2f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 2.4f,
                        height = 1.2f,
                        cornerRadius = 0.3f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 8f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableSquare(
                        center = Vector2F(3.5f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 1.2f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(285f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        originPointA = Vector2F(-6.1282597f, 4.2426405f),
                        originPointB = Vector2F(-1.8856182f, -2.828427f),
                        originPointC = Vector2F(8.013877f, -1.4142137f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableTriangle(
                        centroid = Vector2F(-0.8333333f, -2.6666667f),
                        originPointA = Vector2F(-0.18828374f, -1.1020663f),
                        originPointB = Vector2F(0.50404835f, -0.07704091f),
                        originPointC = Vector2F(-0.31576473f, 1.1791066f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        originPointA = Vector2F(-3.0641298f, 2.1213202f),
                        originPointB = Vector2F(-0.9428091f, -1.4142137f),
                        originPointC = Vector2F(4.0069385f, -0.7071069f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        originPointA = Vector2F(3.0641294f, -2.1213202f),
                        originPointB = Vector2F(0.9428086f, 1.4142137f),
                        originPointC = Vector2F(-4.0069385f, 0.7071064f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun transformedByVector2FComplexFFloatArgs(): List<Arguments> =
            transformedByVector2FAngleFFloatArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(4) as Wrapper<*>).value as AngleF
                        set(4, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformedToVector2FAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    { a: Annulus, b: Annulus -> AnnulusTests.areApproximatelyEqual(a, b) },
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()
            val mutableCircleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
                    CircleTests.Companion::clone,
                    { a: Circle, b: Circle -> CircleTests.areApproximatelyEqual(a, b) },
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
            val defaultCircleArgs = mutableCircleArgs.mapCirclesToDefaultCircles()
            val mutableLineSegmentArgs = listOf(
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableLineSegment(
                        Vector2F(-2.4188612f, 3.5811388f),
                        Vector2F(-5.5811386f, 0.41886115f)
                    ),
                ),
                Arguments.of(
                    LineSegmentTests.Companion::clone,
                    { a: LineSegment, b: LineSegment ->
                        LineSegmentTests.areApproximatelyEqual(a, b)
                    },
                    MutableLineSegment(Vector2F(-4f, 3f), Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableLineSegment(
                        Vector2F(-1.6012166f, 0.76478034f),
                        Vector2F(2.6012166f, -0.76478034f)
                    ),
                ),
            )
            val defaultLineSegmentArgs = mutableLineSegmentArgs
                .mapLineSegmentsToDefaultLineSegments()
            val mutableRayArgs = listOf(
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRay(Vector2F(-4f, 2f), Vector2F(0.70710677f, 0.70710677f)),
                ),
                Arguments.of(
                    RayTests.Companion::clone,
                    { a: Ray, b: Ray -> RayTests.areApproximatelyEqual(a, b) },
                    MutableRay(Vector2F(-4f, 3f), Vector2F(0.7071068f, -0.7071068f)),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRay(Vector2F(0.5f, 0f), Vector2F(-0.9396926f, 0.34202015f)),
                ),
            )
            val defaultRayArgs = mutableRayArgs.mapRaysToDefaultRays()
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    { a: Rectangle, b: Rectangle -> RectangleTests.areApproximatelyEqual(a, b) },
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()
            val mutableRegularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularPolygon(
                        Vector2F(-4f, 2f),
                        ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    { a: RegularPolygon, b: RegularPolygon ->
                        RegularPolygonTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularPolygon(
                        Vector2F(0.5f, 0f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f,
                        sideCount = 7
                    )
                ),
            )
            val defaultRegularPolygonArgs = mutableRegularPolygonArgs
                .mapRegularPolygonsToDefaultRegularPolygons()
            val mutableRegularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    { a: RegularTriangle, b: RegularTriangle ->
                        RegularTriangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f
                    )
                ),
            )
            val defaultRegularTriangleArgs = mutableRegularTriangleArgs
                .mapRegularTrianglesToDefaultRegularTriangles()
            val mutableRoundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    { a: RoundedRectangle, b: RoundedRectangle ->
                        RoundedRectangleTests.areApproximatelyEqual(a, b)
                    },
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val defaultRoundedRectangleArgs = mutableRoundedRectangleArgs
                .mapRoundedRectanglesToDefaultRoundedRectangles()
            val mutableSquareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(-4f, 2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    { a: Square, b: Square -> SquareTests.areApproximatelyEqual(a, b) },
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(0.5f, 0f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 4f
                    )
                ),
            )
            val defaultSquareArgs = mutableSquareArgs.mapSquaresToDefaultSquares()
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-4f, 2f),
                        originPointA = Vector2F(2.635231f, 2.635231f),
                        originPointB = Vector2F(-1.2227478f, 1.1805837f),
                        originPointC = Vector2F(-1.4124842f, -3.8158145f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        originPointA = Vector2F(-0.6666667f, 3.6666667f),
                        originPointB = Vector2F(-1.6666667f, -0.3333333f),
                        originPointC = Vector2F(2.3333333f, -3.3333333f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(0.5f, 0f),
                        originPointA = Vector2F(-3.5020273f, 1.2746339f),
                        originPointB = Vector2F(-0.5532167f, -1.6071215f),
                        originPointC = Vector2F(4.055244f, 0.33248758f)
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs,
                mutableCircleArgs,
                defaultCircleArgs,
                mutableLineSegmentArgs,
                defaultLineSegmentArgs,
                mutableRayArgs,
                defaultRayArgs,
                mutableRectangleArgs,
                defaultRectangleArgs,
                mutableRegularPolygonArgs,
                defaultRegularPolygonArgs,
                mutableRegularTriangleArgs,
                defaultRegularTriangleArgs,
                mutableRoundedRectangleArgs,
                defaultRoundedRectangleArgs,
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun transformedToVector2FComplexFArgs(): List<Arguments> =
            transformedToVector2FAngleFArgs().map { args ->
                Arguments.of(
                    *args.get().copyOf().apply {
                        val angle = (get(4) as Wrapper<*>).value as AngleF
                        set(4, Wrapper(ComplexF.fromAngle(angle)))
                    }
                )
            }

        @JvmStatic
        fun transformByVector2FAngleFArgs(): List<Arguments> =
            transformedByVector2FAngleFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun transformByVector2FComplexFArgs(): List<Arguments> =
            transformedByVector2FComplexFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun transformByVector2FAngleFFloatArgs(): List<Arguments> =
            transformedByVector2FAngleFFloatArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun transformByVector2FComplexFFloatArgs(): List<Arguments> =
            transformedByVector2FComplexFFloatArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun transformToVector2FAngleFArgs(): List<Arguments> =
            transformedToVector2FAngleFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }

        @JvmStatic
        fun transformToVector2FComplexFArgs(): List<Arguments> =
            transformedToVector2FComplexFArgs().mapNotNull { args ->
                val argArray: Array<Any?> = args.get()

                if (argArray.all { it !is Transformable || it is MutableTransformable }) {
                    Arguments.of(*argArray.drop(1).toTypedArray())
                } else null
            }
    }
}