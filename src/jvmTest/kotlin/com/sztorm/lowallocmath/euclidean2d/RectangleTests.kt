package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.euclidean2d.utils.DefaultRectangle
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

class RectangleTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectRectangle(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, width: Float, height: Float,
    ) {
        val mutableRectangle = MutableRectangle(center.value, orientation.value, width, height)
        val rectangle = Rectangle(center.value, orientation.value, width, height)

        assertEquals(center.value, mutableRectangle.center)
        assertEquals(orientation.value, mutableRectangle.orientation)
        assertEquals(width, mutableRectangle.width)
        assertEquals(height, mutableRectangle.height)

        assertEquals(center.value, rectangle.center)
        assertEquals(orientation.value, rectangle.orientation)
        assertEquals(width, rectangle.width)
        assertEquals(height, rectangle.height)
    }

    @ParameterizedTest
    @MethodSource("constructorThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableRectangle(center.value, orientation.value, width, height)
        }
        assertThrows(expectedExceptionClass) {
            Rectangle(center.value, orientation.value, width, height)
        }
    }

    @ParameterizedTest
    @MethodSource("centerArgs")
    fun centerReturnsCorrectValue(rectangle: Rectangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.center)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(rectangle: Rectangle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.orientation)
        }

    @ParameterizedTest
    @MethodSource("widthArgs")
    fun widthReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.width)
        }

    @ParameterizedTest
    @MethodSource("heightArgs")
    fun heightReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.height)
        }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        rectangle: Rectangle,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>,
        expectedPointD: Wrapper<Vector2F>
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expectedPointA.value, rectangle.pointA)
        assertApproximation(expectedPointB.value, rectangle.pointB)
        assertApproximation(expectedPointC.value, rectangle.pointC)
        assertApproximation(expectedPointD.value, rectangle.pointD)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(rectangle: Rectangle, expected: Float) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected, rectangle.perimeter)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(rectangle: Rectangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(rectangle) {
            assertApproximation(expected.value, rectangle.position)
        }

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(
        rectangle: Rectangle, displacement: Wrapper<Vector2F>, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.movedBy(displacement.value))
    }

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(
        rectangle: Rectangle, position: Wrapper<Vector2F>, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.movedTo(position.value))
    }

    @ParameterizedTest
    @MethodSource("moveByArgs")
    fun moveByMutatesRectangleCorrectly(
        rectangle: MutableRectangle, displacement: Wrapper<Vector2F>, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { moveBy(displacement.value) })

    @ParameterizedTest
    @MethodSource("moveToArgs")
    fun moveToMutatesRectangleCorrectly(
        rectangle: MutableRectangle, position: Wrapper<Vector2F>, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { moveTo(position.value) })

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(
        rectangle: Rectangle, rotation: Wrapper<AngleF>, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        rectangle: Rectangle, rotation: Wrapper<ComplexF>, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(
        rectangle: Rectangle, orientation: Wrapper<AngleF>, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        rectangle: Rectangle, orientation: Wrapper<ComplexF>, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FAngleFArgs")
    fun rotatedAroundPointByVector2FAngleFReturnsCorrectValue(
        rectangle: Rectangle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FComplexFArgs")
    fun rotatedAroundPointByVector2FComplexFReturnsCorrectValue(
        rectangle: Rectangle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FAngleFArgs")
    fun rotatedAroundPointToVector2FAngleFReturnsCorrectValue(
        rectangle: Rectangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(
            expected, rectangle.rotatedAroundPointTo(point.value, orientation.value)
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FComplexFArgs")
    fun rotatedAroundPointToVector2FComplexFReturnsCorrectValue(
        rectangle: Rectangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(
            expected, rectangle.rotatedAroundPointTo(point.value, orientation.value)
        )
    }

    @ParameterizedTest
    @MethodSource("rotateByAngleFArgs")
    fun rotateByAngleFMutatesRectangleCorrectly(
        rectangle: MutableRectangle, rotation: Wrapper<AngleF>, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateByComplexFArgs")
    fun rotateByComplexFMutatesRectangleCorrectly(
        rectangle: MutableRectangle, rotation: Wrapper<ComplexF>, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateToAngleFArgs")
    fun rotateToAngleFMutatesRectangleCorrectly(
        rectangle: MutableRectangle, orientation: Wrapper<AngleF>, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateToComplexFArgs")
    fun rotateToComplexFMutatesRectangleCorrectly(
        rectangle: MutableRectangle, orientation: Wrapper<ComplexF>, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FAngleFArgs")
    fun rotateAroundPointByVector2FAngleFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected, rectangle.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FComplexFArgs")
    fun rotateAroundPointByVector2FComplexFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected, rectangle.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FAngleFArgs")
    fun rotateAroundPointToVector2FAngleFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected, rectangle.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FComplexFArgs")
    fun rotateAroundPointToVector2FComplexFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected, rectangle.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(
        rectangle: Rectangle, factor: Float, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.scaledBy(factor))
    }

    @ParameterizedTest
    @MethodSource("dilatedByArgs")
    fun dilatedByReturnsCorrectValue(
        rectangle: Rectangle, point: Wrapper<Vector2F>, factor: Float, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.dilatedBy(point.value, factor))
    }

    @ParameterizedTest
    @MethodSource("scaleByArgs")
    fun scaleByMutatesRectangleCorrectly(
        rectangle: MutableRectangle, factor: Float, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { scaleBy(factor) })

    @ParameterizedTest
    @MethodSource("dilateByArgs")
    fun dilateByMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        point: Wrapper<Vector2F>,
        factor: Float,
        expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { dilateBy(point.value, factor) })

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFArgs")
    fun transformedByVector2FAngleFReturnsCorrectValue(
        rectangle: Rectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFArgs")
    fun transformedByVector2FComplexFReturnsCorrectValue(
        rectangle: Rectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFFloatArgs")
    fun transformedByVector2FAngleFFloatReturnsCorrectValue(
        rectangle: Rectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(
            expected, rectangle.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFFloatArgs")
    fun transformedByVector2FComplexFFloatReturnsCorrectValue(
        rectangle: Rectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(
            expected, rectangle.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FAngleFArgs")
    fun transformedToVector2FAngleFReturnsCorrectValue(
        rectangle: Rectangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FComplexFArgs")
    fun transformedToVector2FComplexFReturnsCorrectValue(
        rectangle: Rectangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected, rectangle.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFArgs")
    fun transformByVector2FAngleFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected,
        rectangle.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFArgs")
    fun transformByVector2FComplexFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected,
        rectangle.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFFloatArgs")
    fun transformByVector2FAngleFFloatMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: MutableRectangle
    ) = assertApproximation(
        expected,
        rectangle.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFFloatArgs")
    fun transformByVector2FComplexFFloatMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: MutableRectangle
    ) = assertApproximation(
        expected,
        rectangle.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FAngleFArgs")
    fun transformToVector2FAngleFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected,
        rectangle.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FComplexFArgs")
    fun transformToVector2FComplexFMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableRectangle
    ) = assertApproximation(
        expected,
        rectangle.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesRectangleCorrectly(
        rectangle: MutableRectangle, expected: MutableRectangle
    ) = assertApproximation(expected, rectangle.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expected: MutableRectangle
    ) = assertEquals(
        expected,
        rectangle.apply { set(center.value, orientation.value, width, height) }
    )

    @ParameterizedTest
    @MethodSource("setThrowsExceptionArgs")
    fun setThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val rectangle = MutableRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f
        )
        assertThrows(expectedExceptionClass) {
            rectangle.set(center.value, orientation.value, width, height)
        }
    }

    @ParameterizedTest
    @MethodSource("setDoesNotThrowExceptionArgs")
    fun setDoesNotThrowException(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, width: Float, height: Float,
    ) {
        val rectangle = MutableRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f
        )
        assertDoesNotThrow {
            rectangle.set(center.value, orientation.value, width, height)
        }
    }

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        rectangle: Rectangle, to: Rectangle, by: Float, expected: Rectangle
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, rectangle.interpolated(to, by))
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesRectangleCorrectly(
        rectangle: MutableRectangle,
        from: Rectangle,
        to: Rectangle,
        by: Float,
        expected: MutableRectangle
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            assertApproximation(expected, rectangle.apply { interpolate(from, to, by) })
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        rectangle: Rectangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(rectangle) {
        assertApproximation(expected.value, rectangle.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsRayReturnsCorrectValue(rectangle: Rectangle, ray: Ray, expected: Boolean) =
        assertImmutabilityOf(rectangle) {
            assertImmutabilityOf(ray) {
                assertEquals(expected, rectangle.intersects(ray))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        rectangle: Rectangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertEquals(expected, rectangle.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        rectangle: Rectangle,
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expected: Rectangle
    ) = assertEquals(expected, rectangle.copy(center.value, orientation.value, width, height))

    @ParameterizedTest
    @MethodSource("copyThrowsExceptionArgs")
    fun copyThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        width: Float,
        height: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val rectangle = MutableRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f
        )
        assertThrows(expectedExceptionClass) {
            rectangle.copy(center.value, orientation.value, width, height)
        }
    }

    @ParameterizedTest
    @MethodSource("copyDoesNotThrowExceptionArgs")
    fun copyDoesNotThrowException(
        center: Wrapper<Vector2F>, orientation: Wrapper<ComplexF>, width: Float, height: Float,
    ) {
        val rectangle = MutableRectangle(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            width = 1f,
            height = 0.5f
        )
        assertDoesNotThrow {
            rectangle.copy(center.value, orientation.value, width, height)
        }
    }

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRectangle, other: Any?, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertEquals(expected, rectangle == other)
    }

    @ParameterizedTest
    @MethodSource("equalsMutableRectangleArgs")
    fun equalsReturnsCorrectValue(
        rectangle: MutableRectangle, other: MutableRectangle, expected: Boolean
    ) = assertImmutabilityOf(rectangle) {
        assertImmutabilityOf(other) {
            assertEquals(expected, rectangle.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(rectangle: MutableRectangle, other: MutableRectangle) =
        assertImmutabilityOf(rectangle) {
            assertImmutabilityOf(other) {
                assertEquals(rectangle.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(rectangle: MutableRectangle, expected: String) =
        assertImmutabilityOf(rectangle) {
            assertEquals(expected, rectangle.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        rectangle: Rectangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Float,
        expectedComponent4: Float
    ) = assertImmutabilityOf(rectangle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Float,
            actualComponent4: Float
        ) = rectangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3, actualComponent3)
        assertEquals(expectedComponent4, actualComponent4)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(
            a: Rectangle, b: Rectangle, tolerance: Float = 0.00001f
        ): Boolean = a.center.isApproximately(b.center, tolerance) and
                a.orientation.isApproximately(b.orientation, tolerance) and
                a.width.isApproximately(b.width, tolerance) and
                a.height.isApproximately(b.height, tolerance) and
                a.pointA.isApproximately(b.pointA, tolerance) and
                a.pointB.isApproximately(b.pointB, tolerance) and
                a.pointC.isApproximately(b.pointC, tolerance) and
                a.pointD.isApproximately(b.pointD, tolerance)

        @JvmStatic
        fun areEqual(a: Rectangle, b: Rectangle): Boolean =
            (a.center == b.center) and
                    (a.orientation == b.orientation) and
                    (a.width == b.width) and
                    (a.height == b.height) and
                    (a.pointA == b.pointA) and
                    (a.pointB == b.pointB) and
                    (a.pointC == b.pointC) and
                    (a.pointD == b.pointD)

        @JvmStatic
        fun List<Arguments>.mapRectanglesToDefaultRectangles() = map { args ->
            val argArray = args.get().map {
                if (it is Rectangle) DefaultRectangle(
                    it.center, it.orientation, it.width, it.height
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
                1f,
                2f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                0f,
                2f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                1f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                0f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, -2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f))),
                3f,
                5f
            ),
            Arguments.of(
                Wrapper(Vector2F(-2f, 4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                2f
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
                1f,
                -2f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                -0.5f,
                IllegalArgumentException::class.java
            ),
        )

        @JvmStatic
        fun centerArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-1f, -2f))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-2f, 4f))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(120f)))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f)))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun widthArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    3f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    4f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun heightArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    5f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    2f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(2f, 1f)),
                    Wrapper(Vector2F(-2f, 1f)),
                    Wrapper(Vector2F(-2f, -1f)),
                    Wrapper(Vector2F(2f, -1f))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-1.292893f, 6.12132f)),
                    Wrapper(Vector2F(-4.12132f, 3.292893f)),
                    Wrapper(Vector2F(-2.707107f, 1.87868f)),
                    Wrapper(Vector2F(0.12132f, 4.707107f))
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-1f, -2f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                        width = 3f,
                        height = 5f
                    ),
                    Wrapper(Vector2F(-3.915063f, -1.950962f)),
                    Wrapper(Vector2F(-2.415063f, -4.549038f)),
                    Wrapper(Vector2F(1.915063f, -2.049038f)),
                    Wrapper(Vector2F(0.415063f, 0.549038f))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 4f,
                        height = 2f
                    ),
                    8f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    8f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 1.5f,
                        height = 3f
                    ),
                    4.5f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 4f,
                        height = 2f
                    ),
                    12f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    12f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO,
                        orientation = ComplexF.ONE,
                        width = 1.5f,
                        height = 3f
                    ),
                    9f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
            val mutableRectangleArgs = moveByArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val mutableRectangleArgs = moveToArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = listOf(
            Arguments.of(
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

        @JvmStatic
        fun moveToArgs(): List<Arguments> = listOf(
            Arguments.of(
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

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
            val mutableRectangleArgs = rotateByAngleFArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
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
            val mutableRectangleArgs = rotateToAngleFArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
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
            val mutableRectangleArgs = rotateAroundPointByVector2FAngleFArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
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
            val mutableRectangleArgs = rotateAroundPointToVector2FAngleFArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
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
            val mutableRectangleArgs = scaleByArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun dilatedByArgs(): List<Arguments> {
            val mutableRectangleArgs = dilateByArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = listOf(
            Arguments.of(
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
                )
            ),
            Arguments.of(
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
                )
            ),
        )

        @JvmStatic
        fun dilateByArgs(): List<Arguments> = listOf(
            Arguments.of(
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
                )
            ),
            Arguments.of(
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
                )
            ),
            Arguments.of(
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

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val mutableRectangleArgs = transformByVector2FAngleFArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
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
            val mutableRectangleArgs = transformByVector2FAngleFFloatArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
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
            val mutableRectangleArgs = transformToVector2FAngleFArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
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
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 7f, AngleF.fromDegrees(45f).radians
                    ),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 0.7f, AngleF.fromDegrees(45f).radians
                    ),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.ZERO,
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.ONE,
                    width = 4f,
                    height = 2f
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-2f, 4f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                2f,
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(-2.5f, 1f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                4f,
                1f,
                MutableRectangle(
                    center = Vector2F(-2.5f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 1f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                Wrapper(Vector2F(0f, 8f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                1f,
                3f,
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                )
            ),
        )

        @JvmStatic
        fun setThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun setDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableRectangleArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                0.5f,
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-1f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    width = 3f,
                    height = 5f
                ),
                0f,
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
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
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    width = 1f,
                    height = 0.5f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-1f, -2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(120f)),
                    width = 3f,
                    height = 5f
                ),
                0.5f,
                MutableRectangle(
                    center = Vector2F(-1.5f, 1f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(82.5f)),
                    width = 3.5f,
                    height = 3.5f
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val pointA = Vector2F(2f, 7f)
            val pointB = Vector2F(-2f, 5f)
            val pointC = Vector2F(-1f, 3f)
            val pointD = Vector2F(3f, 5f)
            val a = (pointA.y - pointB.y) / (pointA.x - pointB.x)
            val rectangle = MutableRectangle(
                center = (pointB + pointD) * 0.5f,
                orientation = ComplexF.fromAngle(AngleF.atan(a)),
                width = pointA.distanceTo(pointB),
                height = pointB.distanceTo(pointC)
            )
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(4.5f, 7f)),
                    Wrapper(Vector2F(2.5f, 6f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(2.1f, 7.1f)),
                    Wrapper(Vector2F(2f, 7f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(0.5f, 8f)),
                    Wrapper(Vector2F(1.2f, 6.6f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-5f, 6f)),
                    Wrapper(Vector2F(-2f, 5f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-3.5f, 3f)),
                    Wrapper(Vector2F(-1.5f, 4f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-0.1f, 1f)),
                    Wrapper(Vector2F(-1f, 3f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(1.5f, 3f)),
                    Wrapper(Vector2F(1f, 4f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(5f, 4f)),
                    Wrapper(Vector2F(3f, 5f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(1.2f, 6.5f)),
                    Wrapper(Vector2F(1.2f, 6.5f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-1f, 3.1f)),
                    Wrapper(Vector2F(-1f, 3.1f))
                ),
                Arguments.of(
                    rectangle,
                    Wrapper(Vector2F(-1.4f, 4f)),
                    Wrapper(Vector2F(-1.4f, 4f))
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> = RayTests.intersectsRectangleArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val pointA = Vector2F(2f, 7f)
            val pointB = Vector2F(-2f, 5f)
            val pointC = Vector2F(-1f, 3f)
            val pointD = Vector2F(3f, 5f)
            val a = (pointA.y - pointB.y) / (pointA.x - pointB.x)
            val rectangle = MutableRectangle(
                center = (pointB + pointD) * 0.5f,
                orientation = ComplexF.fromAngle(AngleF.atan(a)),
                width = pointA.distanceTo(pointB),
                height = pointB.distanceTo(pointC)
            )
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.2f, 6.5f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(1.2f, 6.7f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1f, 3.1f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-0.9f, 3f)), false
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.4f, 4f)), true
                ),
                Arguments.of(
                    rectangle, Wrapper(Vector2F(-1.6f, 4f)), false
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, width = 4f, height = 2f
                    ),
                    Wrapper(Vector2F(-1.9f, -0.9f)),
                    true
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F.ZERO, orientation = ComplexF.ONE, width = 4f, height = 2f
                    ),
                    Wrapper(Vector2F(-2.1f, -1f)),
                    false
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(0.02132f, 4.707107f)),
                    true
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(0.22132f, 4.707107f)),
                    false
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableRectangleArgs = setArgs()
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun copyDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableRectangleArgs() + listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                null,
                false
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                DefaultRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                true
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                DefaultRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableRectangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                true
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2.1f
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                )
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                ),
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(-2f, 4f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    width = 4f,
                    height = 2f
                ),
                "Rectangle(" +
                        "center=${Vector2F(-2f, 4f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(45f))}, " +
                        "width=${4f}, " +
                        "height=${2f})"
            ),
            Arguments.of(
                MutableRectangle(
                    center = Vector2F(0f, 8f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    width = 1f,
                    height = 3f
                ),
                "Rectangle(" +
                        "center=${Vector2F(0f, 8f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(30f))}, " +
                        "width=${1f}, " +
                        "height=${3f})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableRectangleArgs = listOf(
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(-2f, 4f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                        width = 4f,
                        height = 2f
                    ),
                    Wrapper(Vector2F(-2f, 4f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(45f))),
                    4f,
                    2f
                ),
                Arguments.of(
                    MutableRectangle(
                        center = Vector2F(0f, 8f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        width = 1f,
                        height = 3f
                    ),
                    Wrapper(Vector2F(0f, 8f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                    1f,
                    3f
                ),
            )
            val defaultRectangleArgs = mutableRectangleArgs.mapRectanglesToDefaultRectangles()

            return listOf(
                mutableRectangleArgs,
                defaultRectangleArgs
            ).flatten()
        }
    }
}