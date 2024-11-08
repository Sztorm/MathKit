package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
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
                squareArgs,
                triangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableTriangle(
                        centroid = Vector2F(-0.8333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableTriangle(
                        centroid = Vector2F(-4f, 2f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableTriangle(
                        centroid = Vector2F(0.5f, 0f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                squareArgs,
                triangleArgs
            ).flatten()
        }

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(145.3049f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-99.69515f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(0.5788478f, -7.949747f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(145.3049f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(12.777071f, -5.821378f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-99.69515f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(145.3049f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-99.69515f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(11.190803f, 2.1908038f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-32.092594f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-0.89819396f, -0.4892627f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(82.90741f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 7.45356f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 3.3993466f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 8.137704f
                    ),
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    0.3f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 1.118034f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 0.509902f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 1.2206556f
                    ),
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    1f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    -1f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-79.69515f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableSquareArgs,
                defaultSquareArgs,
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun dilatedByArgs(): List<Arguments> {
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-8.666666f, -2.3333333f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 7.45356f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 3.3993466f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 8.137704f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    0.3f,
                    MutableTriangle(
                        centroid = Vector2F(3.8f, -2.9f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 1.118034f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 0.509902f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 1.2206556f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    1f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    -1f,
                    MutableTriangle(
                        centroid = Vector2F(13.333333f, -3.3333333f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-79.69515f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 7.45356f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 3.3993466f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 8.137704f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(145.3049f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(-0.8333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-99.69515f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(145.3049f)),
                        pointDistanceA = 7.45356f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 3.3993466f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 8.137704f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableTriangle(
                        centroid = Vector2F(-0.8333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-99.69515f)),
                        pointDistanceA = 1.118034f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 0.509902f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 1.2206556f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    1f,
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(145.3049f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    -1f,
                    MutableTriangle(
                        centroid = Vector2F(-5.333333f, -0.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-34.69515f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        centroid = Vector2F(-4f, 2f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    { a: Triangle, b: Triangle -> TriangleTests.areApproximatelyEqual(a, b) },
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        centroid = Vector2F(0.5f, 0f),
                        pathRotorA = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        pointDistanceA = 3.72678f,
                        pathRotorAB = ComplexF.fromAngle(AngleF.fromDegrees(91.00509f)),
                        pointDistanceB = 1.6996733f,
                        pathRotorAC = ComplexF.fromAngle(AngleF.fromDegrees(-155.31284f)),
                        pointDistanceC = 4.068852f
                    )
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
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