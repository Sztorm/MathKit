package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import com.sztorm.lowallocmath.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertTrue

class TransformableTests {
    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(
        cloner: (Movable) -> Movable,
        equalityComparator: (Movable, Movable) -> Boolean,
        movable: Movable,
        expected: Wrapper<Vector2F>
    ) {
        val clone: Movable = cloner(movable)
        val actual: Vector2F = movable.position

        assertApproximation(expected.value, actual)
        assertTrue(equalityComparator(clone, movable), "Movable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(
        cloner: (Movable) -> Movable,
        equalityComparator: (Movable, Movable) -> Boolean,
        movable: Movable,
        offset: Wrapper<Vector2F>,
        expected: Movable
    ) {
        val clone: Movable = cloner(movable)
        val actual: Movable = movable.movedBy(offset.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, movable), "Movable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(
        cloner: (Movable) -> Movable,
        equalityComparator: (Movable, Movable) -> Boolean,
        movable: Movable,
        position: Wrapper<Vector2F>,
        expected: Movable
    ) {
        val clone: Movable = cloner(movable)
        val actual: Movable = movable.movedTo(position.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, movable), "Movable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("moveByArgs")
    fun moveByMutatesMovableCorrectly(
        equalityComparator: (MutableMovable, MutableMovable) -> Boolean,
        movable: MutableMovable,
        offset: Wrapper<Vector2F>,
        expected: MutableMovable
    ) = assertTrue(equalityComparator(expected, movable.apply { moveBy(offset.value) }))

    @ParameterizedTest
    @MethodSource("moveToArgs")
    fun moveToMutatesMovableCorrectly(
        equalityComparator: (MutableMovable, MutableMovable) -> Boolean,
        movable: MutableMovable,
        position: Wrapper<Vector2F>,
        expected: MutableMovable
    ) = assertTrue(equalityComparator(expected, movable.apply { moveTo(position.value) }))

    @ParameterizedTest
    @MethodSource("rotationArgs")
    fun rotationReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        expected: Wrapper<ComplexF>
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: ComplexF = rotatable.rotation

        assertApproximation(expected.value, actual)
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        angle: Wrapper<AngleF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedBy(angle.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        rotation: Wrapper<ComplexF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedBy(rotation.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        angle: Wrapper<AngleF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedTo(angle.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        rotation: Wrapper<ComplexF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedTo(rotation.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FAngleFArgs")
    fun rotatedAroundPointByVector2FAngleFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        point: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedAroundPointBy(point.value, angle.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FComplexFArgs")
    fun rotatedAroundPointByVector2FComplexFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedAroundPointBy(point.value, rotation.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FAngleFArgs")
    fun rotatedAroundPointToVector2FAngleFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        point: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedAroundPointTo(point.value, angle.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FComplexFArgs")
    fun rotatedAroundPointToVector2FComplexFReturnsCorrectValue(
        cloner: (Rotatable) -> Rotatable,
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Rotatable
    ) {
        val clone: Rotatable = cloner(rotatable)
        val actual: Rotatable = rotatable.rotatedAroundPointTo(point.value, rotation.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, rotatable), "Rotatable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("rotateByAngleFArgs")
    fun rotateByAngleFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        angle: Wrapper<AngleF>,
        expected: MutableRotatable
    ) = assertTrue(equalityComparator(expected, rotatable.apply { rotateBy(angle.value) }))

    @ParameterizedTest
    @MethodSource("rotateByComplexFArgs")
    fun rotateByComplexFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        rotation: Wrapper<ComplexF>,
        expected: MutableRotatable
    ) = assertTrue(equalityComparator(expected, rotatable.apply { rotateBy(rotation.value) }))

    @ParameterizedTest
    @MethodSource("rotateToAngleFArgs")
    fun rotateToAngleFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        angle: Wrapper<AngleF>,
        expected: MutableRotatable
    ) = assertTrue(equalityComparator(expected, rotatable.apply { rotateTo(angle.value) }))

    @ParameterizedTest
    @MethodSource("rotateToComplexFArgs")
    fun rotateToComplexFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        rotation: Wrapper<ComplexF>,
        expected: MutableRotatable
    ) = assertTrue(equalityComparator(expected, rotatable.apply { rotateTo(rotation.value) }))

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FAngleFArgs")
    fun rotateAroundPointByVector2FAngleFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        point: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: MutableRotatable
    ) = assertTrue(
        equalityComparator(
            expected, rotatable.apply { rotateAroundPointBy(point.value, angle.value) }
        )
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FComplexFArgs")
    fun rotateAroundPointByVector2FComplexFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableRotatable
    ) = assertTrue(
        equalityComparator(
            expected, rotatable.apply { rotateAroundPointBy(point.value, rotation.value) }
        )
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FAngleFArgs")
    fun rotateAroundPointToVector2FAngleFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        point: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: MutableRotatable
    ) = assertTrue(
        equalityComparator(
            expected, rotatable.apply { rotateAroundPointTo(point.value, angle.value) }
        )
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FComplexFArgs")
    fun rotateAroundPointToVector2FComplexFMutatesRotatableCorrectly(
        equalityComparator: (MutableRotatable, MutableRotatable) -> Boolean,
        rotatable: MutableRotatable,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableRotatable
    ) = assertTrue(
        equalityComparator(
            expected, rotatable.apply { rotateAroundPointTo(point.value, rotation.value) }
        )
    )

    @ParameterizedTest
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(
        cloner: (Scalable) -> Scalable,
        equalityComparator: (Scalable, Scalable) -> Boolean,
        scalable: Scalable,
        factor: Float,
        expected: Scalable
    ) {
        val clone: Scalable = cloner(scalable)
        val actual: Scalable = scalable.scaledBy(factor)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(equalityComparator(clone, scalable), "Scalable must not be mutated.")
    }

    @ParameterizedTest
    @MethodSource("scaleByArgs")
    fun scaleByMutatesScalableCorrectly(
        equalityComparator: (MutableScalable, MutableScalable) -> Boolean,
        scalable: MutableScalable,
        factor: Float,
        expected: MutableScalable
    ) = assertTrue(equalityComparator(expected, scalable.apply { scaleBy(factor) }))

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFArgs")
    fun transformedByVector2FAngleFReturnsCorrectValue(
        cloner: (Transformable) -> Transformable,
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        offset: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedBy(offset.value, angle.value)

        assertTrue(equalityComparator(expected, actual))
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
        offset: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedBy(offset.value, rotation.value)

        assertTrue(equalityComparator(expected, actual))
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
        offset: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        factor: Float,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedBy(offset.value, angle.value, factor)

        assertTrue(equalityComparator(expected, actual))
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
        offset: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        factor: Float,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable =
            transformable.transformedBy(offset.value, rotation.value, factor)

        assertTrue(equalityComparator(expected, actual))
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
        angle: Wrapper<AngleF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedTo(position.value, angle.value)

        assertTrue(equalityComparator(expected, actual))
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
        rotation: Wrapper<ComplexF>,
        expected: Transformable
    ) {
        val clone: Transformable = cloner(transformable)
        val actual: Transformable = transformable.transformedTo(position.value, rotation.value)

        assertTrue(equalityComparator(expected, actual))
        assertTrue(
            equalityComparator(clone, transformable), "Transformable must not be mutated."
        )
    }

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFArgs")
    fun transformByVector2FAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        offset: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.apply { transformBy(offset.value, angle.value) }
        )
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFArgs")
    fun transformByVector2FComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        offset: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.apply { transformBy(offset.value, rotation.value) }
        )
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFFloatArgs")
    fun transformByVector2FAngleFFloatMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        offset: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        factor: Float,
        expected: MutableTransformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.apply { transformBy(offset.value, angle.value, factor) }
        )
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFFloatArgs")
    fun transformByVector2FComplexFFloatMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        offset: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        factor: Float,
        expected: MutableTransformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.apply { transformBy(offset.value, rotation.value, factor) }
        )
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FAngleFArgs")
    fun transformToVector2FAngleFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        position: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: MutableTransformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.apply { transformTo(position.value, angle.value) }
        )
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FComplexFArgs")
    fun transformToVector2FComplexFMutatesTransformableCorrectly(
        equalityComparator: (MutableTransformable, MutableTransformable) -> Boolean,
        transformable: MutableTransformable,
        position: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableTransformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.apply { transformTo(position.value, rotation.value) }
        )
    )

    companion object {
        @JvmStatic
        fun positionArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f))
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, 3f))
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(1f, 2f))
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    Wrapper(Vector2F(-1f, 7f))
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f))
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-2f, 4f))
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(Vector2F(0f, 8f))
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
                    MutableRegularPolygon(
                        Vector2F(14f, 1f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                        sideLength = 2f,
                        sideCount = 10
                    ),
                    Wrapper(Vector2F(14f, 1f))
                ),
            )
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f))
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3.1547005f, -4f))
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f))
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    Wrapper(Vector2F(6f, -4f))
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f))
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(8f, -2f))
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f))
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(Vector2F(-0.3333333f, 3f))
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
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
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableAnnulus(
                        center = Vector2F(-0.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableCircle(
                        center = Vector2F(-3f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableCircle(
                        center = Vector2F(1.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRectangle(
                        center = Vector2F(-0.5f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRegularTriangle(
                        center = Vector2F(5.5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableSquare(
                        center = Vector2F(3.5f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableTriangle(
                        Vector2F(-6f, 3f), Vector2F(-7f, -1f), Vector2F(-3f, -4f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableTriangle(
                        Vector2F(-1.5f, 1f),
                        Vector2F(-2.5f, -3f),
                        Vector2F(1.5f, -6f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableAnnulus(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableAnnulus(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableCircle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableCircle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRectangle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRectangle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRegularTriangle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableRoundedRectangle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableSquare(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableSquare(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    MutableTriangle(
                        Vector2F(-4.666667f, 5.666667f),
                        Vector2F(-5.666667f, 1.6666667f),
                        Vector2F(-1.6666667f, -1.3333333f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    MutableTriangle(
                        Vector2F(-0.1666667f, 3.666667f),
                        Vector2F(-1.1666667f, -0.3333333f),
                        Vector2F(2.8333333f, -3.3333333f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
            ).flatten()
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = movedByArgs().map { args ->
            Arguments.of(*args.get().drop(1).toTypedArray())
        }

        @JvmStatic
        fun moveToArgs(): List<Arguments> = movedToArgs().map { args ->
            Arguments.of(*args.get().drop(1).toTypedArray())
        }

        @JvmStatic
        fun rotationArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f)))
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f)))
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f)))
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f)))
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f)))
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f)))
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
                    MutableRegularPolygon(
                        Vector2F(0f, 8f),
                        ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        sideLength = 3f,
                        sideCount = 7
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f)))
                ),
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
                    MutableRegularPolygon(
                        Vector2F(14f, 1f),
                        ComplexF.fromAngle(AngleF.fromDegrees(-72f)),
                        sideLength = 2f,
                        sideCount = 10
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-72f)))
                ),
            )
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-40f)))
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(3.1547005f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90f)),
                        sideLength = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-90f)))
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-60f)))
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(6f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 4f,
                        cornerRadius = 1.5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f)))
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(60f)))
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-135f)))
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(100.30485f)))
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-4f, 2f), Vector2F(2f, 2f), Vector2F(1f, 5f)
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-164.74489f)))
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
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
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 4f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        Vector2F(-4.397463f, -0.54534626f),
                        Vector2F(-2.2761421f, -4.08088f),
                        Vector2F(2.6736052f, -3.3737736f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        Vector2F(-1.9609454f, -6.3402195f),
                        Vector2F(0.34682798f, -2.923469f),
                        Vector2F(-2.385882f, 1.2636895f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
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
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 4f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        Vector2F(1.301898f, -0.03143549f),
                        Vector2F(-2.5560806f, -1.4860829f),
                        Vector2F(-2.7458177f, -6.482481f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        Vector2F(-4.8353605f, -1.3920327f),
                        Vector2F(-1.88655f, -4.273788f),
                        Vector2F(2.7219107f, -2.334179f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
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
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-2.485281f, -4.4142137f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(10.867748f, -10.092604f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableCircle(
                        center = Vector2F(-1.0710678f, -3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableCircle(
                        center = Vector2F(8.988362f, -9.408564f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(0.34314585f, -7.242641f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(12.235828f, -6.3338337f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            )
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(-1.7781744f, 3.3639612f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(3.5194912f, -12.738946f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(0.34314585f, -10.071068f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(14.799253f, -5.138489f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs
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
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(12.082763f, 3.0827627f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-144.46233f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-2.0835419f, -0.057831287f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-29.462322f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableCircle(
                        center = Vector2F(11f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableCircle(
                        center = Vector2F(-0.64463043f, -0.58155227f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(115f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(1f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(1f, 2f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(11f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-6.8698964f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-0.64463043f, -0.58155227f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(108.130104f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(13.106335f, 4.1063347f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-90.710594f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(-3.4437933f, 0.4372599f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(24.289406f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(5f, 7f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(12.403124f, 3.4031243f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(158.6598f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, -3f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5092793f, 0.09712434f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-86.340195f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-3f, -4f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs
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
        fun rotateByAngleFArgs(): List<Arguments> = rotatedByAngleFArgs().map { args ->
            Arguments.of(*args.get().drop(1).toTypedArray())
        }

        @JvmStatic
        fun rotateByComplexFArgs(): List<Arguments> = rotatedByComplexFArgs().map { args ->
            Arguments.of(*args.get().drop(1).toTypedArray())
        }

        @JvmStatic
        fun rotateToAngleFArgs(): List<Arguments> = rotatedToAngleFArgs().map { args ->
            Arguments.of(*args.get().drop(1).toTypedArray())
        }

        @JvmStatic
        fun rotateToComplexFArgs(): List<Arguments> = rotatedToComplexFArgs().map { args ->
            Arguments.of(*args.get().drop(1).toTypedArray())
        }

        @JvmStatic
        fun rotateAroundPointByVector2FAngleFArgs(): List<Arguments> =
            rotatedAroundPointByVector2FAngleFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun rotateAroundPointByVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointByVector2FComplexFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun rotateAroundPointToVector2FAngleFArgs(): List<Arguments> =
            rotatedAroundPointToVector2FAngleFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun rotateAroundPointToVector2FComplexFArgs(): List<Arguments> =
            rotatedAroundPointToVector2FComplexFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun scaledByArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    2f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 8f,
                        innerRadius = 4f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    0.3f,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 1.2f,
                        innerRadius = 0.6f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    2f,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 8f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    0.3f,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 1.2f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    2f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 6f,
                        height = 10f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    0.3f,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 0.9f,
                        height = 1.5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            )
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    2f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 6f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    0.3f,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 0.9f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    2f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 16f,
                        height = 8f,
                        cornerRadius = 2f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    0.3f,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 2.4f,
                        height = 1.2f,
                        cornerRadius = 0.3f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    2f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 8f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    0.3f,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 1.2f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    2f,
                    MutableTriangle(
                        Vector2F(-2.6666667f, 4.6666665f),
                        Vector2F(-4.666667f, -3.3333335f),
                        Vector2F(3.3333333f, -9.333334f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    0.3f,
                    MutableTriangle(
                        Vector2F(-1.5333333f, -1.5666666f),
                        Vector2F(-1.8333333f, -2.7666667f),
                        Vector2F(-0.63333327f, -3.6666665f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
            ).flatten()
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = scaledByArgs().map { args ->
            Arguments.of(*args.get().drop(1).toTypedArray())
        }

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(-0.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableCircle(
                        center = Vector2F(-3f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableCircle(
                        center = Vector2F(1.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(-0.5f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(5.5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(3.5f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 4f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        Vector2F(-8.397463f, 1.4546535f),
                        Vector2F(-6.276142f, -2.0808804f),
                        Vector2F(-1.3263946f, -1.3737737f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        Vector2F(-1.4609454f, -6.3402195f),
                        Vector2F(0.846828f, -2.923469f),
                        Vector2F(-1.885882f, 1.2636895f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
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
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableAnnulus(
                        center = Vector2F(-5f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(0f)),
                        outerRadius = 8f,
                        innerRadius = 4f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableAnnulus(
                        center = Vector2F(-0.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-245f)),
                        outerRadius = 1.2f,
                        innerRadius = 0.6f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableCircle(
                        center = Vector2F(-3f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(135f)),
                        radius = 8f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableCircle(
                        center = Vector2F(1.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                        radius = 1.2f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRectangle(
                        center = Vector2F(-5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(165f)),
                        width = 6f,
                        height = 10f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRectangle(
                        center = Vector2F(-0.5f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                        width = 0.9f,
                        height = 1.5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            )
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRegularTriangle(
                        center = Vector2F(1f, 9f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(5f)),
                        sideLength = 6f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRegularTriangle(
                        center = Vector2F(5.5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-240f)),
                        sideLength = 0.9f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableRoundedRectangle(
                        center = Vector2F(-7f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-15f)),
                        width = 16f,
                        height = 8f,
                        cornerRadius = 2f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableRoundedRectangle(
                        center = Vector2F(-2.5f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-260f)),
                        width = 2.4f,
                        height = 1.2f,
                        cornerRadius = 0.3f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableSquare(
                        center = Vector2F(-1f, 3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(105f)),
                        sideLength = 8f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableSquare(
                        center = Vector2F(3.5f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-140f)),
                        sideLength = 1.2f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    2f,
                    MutableTriangle(
                        Vector2F(-11.461593f, 3.5759735f),
                        Vector2F(-7.218951f, -3.4950943f),
                        Vector2F(2.680544f, -2.0808809f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    0.3f,
                    MutableTriangle(
                        Vector2F(-1.0216169f, -3.7687325f),
                        Vector2F(-0.32928485f, -2.7437072f),
                        Vector2F(-1.1490979f, -1.4875597f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
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
            val annulusArgs = listOf(
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableAnnulus(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
                Arguments.of(
                    AnnulusTests.Companion::clone,
                    AnnulusTests.Companion::areApproximatelyEqual,
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableAnnulus(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    )
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableCircle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        radius = 4f
                    )
                ),
                Arguments.of(
                    CircleTests.Companion::clone,
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableCircle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        radius = 4f
                    )
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRectangle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 3f,
                        height = 5f
                    )
                ),
                Arguments.of(
                    RectangleTests.Companion::clone,
                    RectangleTests.Companion::areApproximatelyEqual,
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRectangle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 3f,
                        height = 5f
                    )
                ),
            )
            val regularPolygonArgs = listOf(
                Arguments.of(
                    RegularPolygonTests.Companion::clone,
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
                    RegularPolygonTests.Companion::areApproximatelyEqual,
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
            val regularTriangleArgs = listOf(
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRegularTriangle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 3f
                    )
                ),
                Arguments.of(
                    RegularTriangleTests.Companion::clone,
                    RegularTriangleTests.Companion::areApproximatelyEqual,
                    MutableRegularTriangle(
                        center = Vector2F(5f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-40f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRegularTriangle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 3f
                    )
                ),
            )
            val roundedRectangleArgs = listOf(
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableRoundedRectangle(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
                Arguments.of(
                    RoundedRectangleTests.Companion::clone,
                    RoundedRectangleTests.Companion::areApproximatelyEqual,
                    MutableRoundedRectangle(
                        center = Vector2F(-3f, -4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-60f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableRoundedRectangle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        width = 8f,
                        height = 4f,
                        cornerRadius = 1f
                    )
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableSquare(
                        center = Vector2F(-4f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        sideLength = 4f
                    )
                ),
                Arguments.of(
                    SquareTests.Companion::clone,
                    SquareTests.Companion::areApproximatelyEqual,
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableSquare(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        sideLength = 4f
                    )
                ),
            )
            val triangleArgs = listOf(
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(AngleF.fromDegrees(45f)),
                    MutableTriangle(
                        Vector2F(-1.364769f, 4.635231f),
                        Vector2F(-5.222748f, 3.1805837f),
                        Vector2F(-5.412484f, -1.8158145f)
                    )
                ),
                Arguments.of(
                    TriangleTests.Companion::clone,
                    TriangleTests.Companion::areApproximatelyEqual,
                    MutableTriangle(
                        Vector2F(-2f, 1f), Vector2F(-3f, -3f), Vector2F(1f, -6f)
                    ),
                    Wrapper(Vector2F(0.5f, 0f)),
                    Wrapper(AngleF.fromDegrees(-200f)),
                    MutableTriangle(
                        Vector2F(-3.0020273f, 1.2746338f),
                        Vector2F(-0.053216696f, -1.6071216f),
                        Vector2F(4.555244f, 0.33248746f)
                    )
                ),
            )
            return listOf(
                annulusArgs,
                circleArgs,
                rectangleArgs,
                regularPolygonArgs,
                regularTriangleArgs,
                roundedRectangleArgs,
                squareArgs,
                triangleArgs
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
            transformedByVector2FAngleFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun transformByVector2FComplexFArgs(): List<Arguments> =
            transformedByVector2FComplexFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun transformByVector2FAngleFFloatArgs(): List<Arguments> =
            transformedByVector2FAngleFFloatArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun transformByVector2FComplexFFloatArgs(): List<Arguments> =
            transformedByVector2FComplexFFloatArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun transformToVector2FAngleFArgs(): List<Arguments> =
            transformedToVector2FAngleFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }

        @JvmStatic
        fun transformToVector2FComplexFArgs(): List<Arguments> =
            transformedToVector2FComplexFArgs().map { args ->
                Arguments.of(*args.get().drop(1).toTypedArray())
            }
    }
}