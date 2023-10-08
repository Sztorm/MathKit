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
    fun positionReturnsCorrectValue(movable: Movable, expected: Wrapper<Vector2F>) =
        assertApproximation(expected.value, movable.position)

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(
        equalityComparator: (Movable, Movable) -> Boolean,
        movable: Movable,
        offset: Wrapper<Vector2F>,
        expected: Movable
    ) = assertTrue(equalityComparator(expected, movable.movedBy(offset.value)))

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(
        equalityComparator: (Movable, Movable) -> Boolean,
        movable: Movable,
        position: Wrapper<Vector2F>,
        expected: Movable
    ) = assertTrue(equalityComparator(expected, movable.movedTo(position.value)))

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
    fun rotationReturnsCorrectValue(rotatable: Rotatable, expected: Wrapper<ComplexF>) =
        assertApproximation(expected.value, rotatable.rotation)

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        angle: Wrapper<AngleF>,
        expected: Rotatable
    ) = assertTrue(equalityComparator(expected, rotatable.rotatedBy(angle.value)))

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        rotation: Wrapper<ComplexF>,
        expected: Rotatable
    ) = assertTrue(equalityComparator(expected, rotatable.rotatedBy(rotation.value)))

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        angle: Wrapper<AngleF>,
        expected: Rotatable
    ) = assertTrue(equalityComparator(expected, rotatable.rotatedTo(angle.value)))

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        equalityComparator: (Rotatable, Rotatable) -> Boolean,
        rotatable: Rotatable,
        rotation: Wrapper<ComplexF>,
        expected: Rotatable
    ) = assertTrue(equalityComparator(expected, rotatable.rotatedTo(rotation.value)))

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
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(
        equalityComparator: (Scalable, Scalable) -> Boolean,
        scalable: Scalable,
        factor: Float,
        expected: Scalable
    ) = assertTrue(equalityComparator(expected, scalable.scaledBy(factor)))

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
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        offset: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: Transformable
    ) = assertTrue(
        equalityComparator(expected, transformable.transformedBy(offset.value, angle.value))
    )

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFArgs")
    fun transformedByVector2FComplexFReturnsCorrectValue(
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        offset: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Transformable
    ) = assertTrue(
        equalityComparator(expected, transformable.transformedBy(offset.value, rotation.value))
    )

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFFloatArgs")
    fun transformedByVector2FAngleFFloatReturnsCorrectValue(
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        offset: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        factor: Float,
        expected: Transformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.transformedBy(offset.value, angle.value, factor)
        )
    )

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFFloatArgs")
    fun transformedByVector2FComplexFFloatReturnsCorrectValue(
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        offset: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        factor: Float,
        expected: Transformable
    ) = assertTrue(
        equalityComparator(
            expected, transformable.transformedBy(offset.value, rotation.value, factor)
        )
    )

    @ParameterizedTest
    @MethodSource("transformedToVector2FAngleFArgs")
    fun transformedToVector2FAngleFReturnsCorrectValue(
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        position: Wrapper<Vector2F>,
        angle: Wrapper<AngleF>,
        expected: Transformable
    ) = assertTrue(
        equalityComparator(expected, transformable.transformedTo(position.value, angle.value))
    )

    @ParameterizedTest
    @MethodSource("transformedToVector2FComplexFArgs")
    fun transformedToVector2FComplexFReturnsCorrectValue(
        equalityComparator: (Transformable, Transformable) -> Boolean,
        transformable: Transformable,
        position: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Transformable
    ) = assertTrue(
        equalityComparator(expected, transformable.transformedTo(position.value, rotation.value))
    )

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
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(Vector2F(-1f, 2f)),
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    Wrapper(Vector2F(6f, 3f)),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(1f, 2f)),
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    Wrapper(Vector2F(-1f, 7f)),
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f)),
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-2f, 4f)),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(Vector2F(3f, 1f)),
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    Wrapper(Vector2F(8f, -2f)),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = movedByArgs()

        @JvmStatic
        fun moveToArgs(): List<Arguments> = movedToArgs()

        @JvmStatic
        fun rotationArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-45f)),
                        outerRadius = 4f,
                        innerRadius = 2f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                ),
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(6f, 3f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(330f)),
                        outerRadius = 8f,
                        innerRadius = 1f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f))),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(90f))),
                ),
                Arguments.of(
                    MutableCircle(
                        center = Vector2F(-1f, 7f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(244f)),
                        radius = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(244f))),
                ),
            )
            val rectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f))),
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(3f, 1f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(60f)),
                        sideLength = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(60f))),
                ),
                Arguments.of(
                    MutableSquare(
                        center = Vector2F(8f, -2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-135f)),
                        sideLength = 3f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-135f))),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun rotatedByComplexFArgs(): List<Arguments> = rotatedByAngleFArgs().map { args ->
            val argArray = args.get()
            val angle = (argArray[2] as Wrapper<*>).value as AngleF

            Arguments.of(
                argArray[0], argArray[1], Wrapper(ComplexF.fromAngle(angle)), argArray[3]
            )
        }

        @JvmStatic
        fun rotatedToAngleFArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun rotatedToComplexFArgs(): List<Arguments> = rotatedToAngleFArgs().map { args ->
            val argArray = args.get()
            val angle = (argArray[2] as Wrapper<*>).value as AngleF

            Arguments.of(
                argArray[0], argArray[1], Wrapper(ComplexF.fromAngle(angle)), argArray[3]
            )
        }

        @JvmStatic
        fun rotateByAngleFArgs(): List<Arguments> = rotatedByAngleFArgs()

        @JvmStatic
        fun rotateByComplexFArgs(): List<Arguments> = rotatedByComplexFArgs()

        @JvmStatic
        fun rotateToAngleFArgs(): List<Arguments> = rotatedToAngleFArgs()

        @JvmStatic
        fun rotateToComplexFArgs(): List<Arguments> = rotatedToComplexFArgs()

        @JvmStatic
        fun scaledByArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = scaledByArgs()

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun transformedByVector2FComplexFArgs(): List<Arguments> =
            transformedByVector2FAngleFArgs().map { args ->
                val argArray = args.get()
                val angle = (argArray[3] as Wrapper<*>).value as AngleF

                Arguments.of(
                    argArray[0],
                    argArray[1],
                    argArray[2],
                    Wrapper(ComplexF.fromAngle(angle)),
                    argArray[4]
                )
            }

        @JvmStatic
        fun transformedByVector2FAngleFFloatArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun transformedByVector2FComplexFFloatArgs(): List<Arguments> =
            transformedByVector2FAngleFFloatArgs().map { args ->
                val argArray = args.get()
                val angle = (argArray[3] as Wrapper<*>).value as AngleF

                Arguments.of(
                    argArray[0],
                    argArray[1],
                    argArray[2],
                    Wrapper(ComplexF.fromAngle(angle)),
                    argArray[4],
                    argArray[5]
                )
            }

        @JvmStatic
        fun transformedToVector2FAngleFArgs(): List<Arguments> {
            val annulusArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val circleArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            val squareArgs = listOf(
                Arguments.of(
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
                    ),
                ),
                Arguments.of(
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
                    ),
                ),
            )
            return annulusArgs + circleArgs + rectangleArgs + squareArgs
        }

        @JvmStatic
        fun transformedToVector2FComplexFArgs(): List<Arguments> =
            transformedToVector2FAngleFArgs().map { args ->
                val argArray = args.get()
                val angle = (argArray[3] as Wrapper<*>).value as AngleF

                Arguments.of(
                    argArray[0],
                    argArray[1],
                    argArray[2],
                    Wrapper(ComplexF.fromAngle(angle)),
                    argArray[4]
                )
            }

        @JvmStatic
        fun transformByVector2FAngleFArgs(): List<Arguments> = transformedByVector2FAngleFArgs()

        @JvmStatic
        fun transformByVector2FComplexFArgs(): List<Arguments> =
            transformedByVector2FComplexFArgs()

        @JvmStatic
        fun transformByVector2FAngleFFloatArgs(): List<Arguments> =
            transformedByVector2FAngleFFloatArgs()

        @JvmStatic
        fun transformByVector2FComplexFFloatArgs(): List<Arguments> =
            transformedByVector2FComplexFFloatArgs()

        @JvmStatic
        fun transformToVector2FAngleFArgs(): List<Arguments> = transformedToVector2FAngleFArgs()

        @JvmStatic
        fun transformToVector2FComplexFArgs(): List<Arguments> =
            transformedToVector2FComplexFArgs()
    }
}