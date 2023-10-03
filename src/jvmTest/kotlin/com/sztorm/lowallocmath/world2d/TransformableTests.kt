package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.utils.Wrapper
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertTrue

class TransformableTests {
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
        fun movedByArgs(): List<Arguments> {
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
            return circleArgs
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
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
            return circleArgs
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = movedByArgs()

        @JvmStatic
        fun moveToArgs(): List<Arguments> = movedToArgs()

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
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
            return circleArgs
        }

        @JvmStatic
        fun rotatedByComplexFArgs(): List<Arguments> {
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
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
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-200f))),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                        radius = 4f
                    )
                ),
            )
            return circleArgs
        }

        @JvmStatic
        fun rotatedToAngleFArgs(): List<Arguments> {
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
            return circleArgs
        }

        @JvmStatic
        fun rotatedToComplexFArgs(): List<Arguments> {
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
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
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-200f))),
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        radius = 4f
                    )
                ),
            )
            return circleArgs
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
            return circleArgs
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = scaledByArgs()

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
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
            return circleArgs
        }

        @JvmStatic
        fun transformedByVector2FComplexFArgs(): List<Arguments> {
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
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
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-200f))),
                    MutableCircle(
                        center = Vector2F(1.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                        radius = 4f
                    )
                ),
            )
            return circleArgs
        }

        @JvmStatic
        fun transformedByVector2FAngleFFloatArgs(): List<Arguments> {
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
            return circleArgs
        }

        @JvmStatic
        fun transformedByVector2FComplexFFloatArgs(): List<Arguments> {
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
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
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-200f))),
                    0.3f,
                    MutableCircle(
                        center = Vector2F(1.5f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-110f)),
                        radius = 1.2f
                    )
                ),
            )
            return circleArgs
        }

        @JvmStatic
        fun transformedToVector2FAngleFArgs(): List<Arguments> {
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
            return circleArgs
        }

        @JvmStatic
        fun transformedToVector2FComplexFArgs(): List<Arguments> {
            val circleArgs = listOf(
                Arguments.of(
                    CircleTests.Companion::areApproximatelyEqual,
                    MutableCircle(
                        center = Vector2F(1f, 2f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(90f)),
                        radius = 4f
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
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
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-200f))),
                    MutableCircle(
                        center = Vector2F(0.5f, 0f),
                        rotation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                        radius = 4f
                    )
                ),
            )
            return circleArgs
        }

        @JvmStatic
        fun transformByVector2FAngleFArgs(): List<Arguments> = transformedByVector2FAngleFArgs()

        @JvmStatic
        fun transformByVector2FComplexFArgs(): List<Arguments> = transformedByVector2FComplexFArgs()

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