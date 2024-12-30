package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.AngleF
import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import com.sztorm.mathkit.euclidean2d.utils.DefaultTriangle
import com.sztorm.mathkit.euclidean2d.utils.assertApproximation
import com.sztorm.mathkit.euclidean2d.utils.assertEquals
import com.sztorm.mathkit.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.mathkit.utils.Wrapper
import com.sztorm.mathkit.utils.assertApproximation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class TriangleTests {
    @ParameterizedTest
    @MethodSource("constructorVector2FComplexFVector2Fx3Args")
    fun constructorCreatesCorrectTriangle(
        centroid: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        originPointA: Wrapper<Vector2F>,
        originPointB: Wrapper<Vector2F>,
        originPointC: Wrapper<Vector2F>,
    ) {
        val mutableTriangle = MutableTriangle(
            centroid.value,
            orientation.value,
            originPointA.value,
            originPointB.value,
            originPointC.value
        )
        val triangle = Triangle(
            centroid.value,
            orientation.value,
            originPointA.value,
            originPointB.value,
            originPointC.value
        )
        assertEquals(centroid.value, mutableTriangle.centroid)
        assertEquals(orientation.value, mutableTriangle.orientation)
        assertEquals(originPointA.value, mutableTriangle.originPointA)
        assertEquals(originPointB.value, mutableTriangle.originPointB)
        assertEquals(originPointC.value, mutableTriangle.originPointC)

        assertEquals(centroid.value, triangle.centroid)
        assertEquals(orientation.value, triangle.orientation)
        assertEquals(originPointA.value, triangle.originPointA)
        assertEquals(originPointB.value, triangle.originPointB)
        assertEquals(originPointC.value, triangle.originPointC)

        assertApproximation(triangle.pointA, mutableTriangle.pointA)
        assertApproximation(triangle.pointB, mutableTriangle.pointB)
        assertApproximation(triangle.pointC, mutableTriangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("constructorVector2Fx3Args")
    fun constructorCreatesCorrectTriangle(
        pointA: Wrapper<Vector2F>, pointB: Wrapper<Vector2F>, pointC: Wrapper<Vector2F>
    ) {
        val mutableTriangle = MutableTriangle(pointA.value, pointB.value, pointC.value)
        val triangle = Triangle(pointA.value, pointB.value, pointC.value)

        assertApproximation(pointA.value, mutableTriangle.pointA)
        assertApproximation(pointB.value, mutableTriangle.pointB)
        assertApproximation(pointC.value, mutableTriangle.pointC)

        assertApproximation(pointA.value, triangle.pointA)
        assertApproximation(pointB.value, triangle.pointB)
        assertApproximation(pointC.value, triangle.pointC)

        assertApproximation(triangle.centroid, mutableTriangle.centroid)
        assertApproximation(triangle.orientation, mutableTriangle.orientation)
        assertApproximation(triangle.originPointA, mutableTriangle.originPointA)
        assertApproximation(triangle.originPointB, mutableTriangle.originPointB)
        assertApproximation(triangle.originPointC, mutableTriangle.originPointC)
    }

    @ParameterizedTest
    @MethodSource("centroidArgs")
    fun centroidReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.centroid)
        }

    @ParameterizedTest
    @MethodSource("orientationArgs")
    fun orientationReturnsCorrectValue(triangle: Triangle, expected: Wrapper<ComplexF>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orientation)
        }

    @ParameterizedTest
    @MethodSource("originPointsArgs")
    fun originPointsReturnCorrectValues(
        triangle: Triangle,
        expectedOriginPointA: Wrapper<Vector2F>,
        expectedOriginPointB: Wrapper<Vector2F>,
        expectedOriginPointC: Wrapper<Vector2F>
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedOriginPointA.value, triangle.originPointA)
        assertApproximation(expectedOriginPointB.value, triangle.originPointB)
        assertApproximation(expectedOriginPointC.value, triangle.originPointC)
    }

    @ParameterizedTest
    @MethodSource("pointsArgs")
    fun pointsReturnCorrectValues(
        triangle: Triangle,
        expectedPointA: Wrapper<Vector2F>,
        expectedPointB: Wrapper<Vector2F>,
        expectedPointC: Wrapper<Vector2F>
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedPointA.value, triangle.pointA)
        assertApproximation(expectedPointB.value, triangle.pointB)
        assertApproximation(expectedPointC.value, triangle.pointC)
    }

    @ParameterizedTest
    @MethodSource("areaArgs")
    fun areaReturnsCorrectValue(triangle: Triangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.area)
        }

    @ParameterizedTest
    @MethodSource("perimeterArgs")
    fun perimeterReturnsCorrectValue(triangle: Triangle, expected: Float) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.perimeter)
        }

    @ParameterizedTest
    @MethodSource("sideLengthsArgs")
    fun sideLengthsReturnsCorrectValues(
        triangle: Triangle,
        expectedSideLengthAB: Float,
        expectedSideLengthBC: Float,
        expectedSideLengthCA: Float,
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expectedSideLengthAB, triangle.sideLengthAB)
        assertApproximation(expectedSideLengthBC, triangle.sideLengthBC)
        assertApproximation(expectedSideLengthCA, triangle.sideLengthAC)
    }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.position)
        }

    @ParameterizedTest
    @MethodSource("incenterArgs")
    fun incenterReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.incenter)
        }

    @ParameterizedTest
    @MethodSource("circumcenterArgs")
    fun circumcenterReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.circumcenter)
        }

    @ParameterizedTest
    @MethodSource("orthocenterArgs")
    fun orthocenterReturnsCorrectValue(triangle: Triangle, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected.value, triangle.orthocenter)
        }

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(
        triangle: Triangle, displacement: Wrapper<Vector2F>, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.movedBy(displacement.value))
    }

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(
        triangle: Triangle, position: Wrapper<Vector2F>, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.movedTo(position.value))
    }

    @ParameterizedTest
    @MethodSource("moveByArgs")
    fun moveByMutatesTriangleCorrectly(
        triangle: MutableTriangle, displacement: Wrapper<Vector2F>, expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { moveBy(displacement.value) })

    @ParameterizedTest
    @MethodSource("moveToArgs")
    fun moveToMutatesTriangleCorrectly(
        triangle: MutableTriangle, position: Wrapper<Vector2F>, expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { moveTo(position.value) })

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(
        triangle: Triangle, rotation: Wrapper<AngleF>, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        triangle: Triangle, rotation: Wrapper<ComplexF>, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(
        triangle: Triangle, orientation: Wrapper<AngleF>, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        triangle: Triangle, orientation: Wrapper<ComplexF>, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FAngleFArgs")
    fun rotatedAroundPointByVector2FAngleFReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, rotation: Wrapper<AngleF>, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FComplexFArgs")
    fun rotatedAroundPointByVector2FComplexFReturnsCorrectValue(
        triangle: Triangle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FAngleFArgs")
    fun rotatedAroundPointToVector2FAngleFReturnsCorrectValue(
        triangle: Triangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(
            expected, triangle.rotatedAroundPointTo(point.value, orientation.value)
        )
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FComplexFArgs")
    fun rotatedAroundPointToVector2FComplexFReturnsCorrectValue(
        triangle: Triangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(
            expected, triangle.rotatedAroundPointTo(point.value, orientation.value)
        )
    }

    @ParameterizedTest
    @MethodSource("rotateByAngleFArgs")
    fun rotateByAngleFMutatesTriangleCorrectly(
        triangle: MutableTriangle, rotation: Wrapper<AngleF>, expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateByComplexFArgs")
    fun rotateByComplexFMutatesTriangleCorrectly(
        triangle: MutableTriangle, rotation: Wrapper<ComplexF>, expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateToAngleFArgs")
    fun rotateToAngleFMutatesTriangleCorrectly(
        triangle: MutableTriangle, orientation: Wrapper<AngleF>, expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateToComplexFArgs")
    fun rotateToComplexFMutatesTriangleCorrectly(
        triangle: MutableTriangle, orientation: Wrapper<ComplexF>, expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FAngleFArgs")
    fun rotateAroundPointByVector2FAngleFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FComplexFArgs")
    fun rotateAroundPointByVector2FComplexFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FAngleFArgs")
    fun rotateAroundPointToVector2FAngleFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FComplexFArgs")
    fun rotateAroundPointToVector2FComplexFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(triangle: Triangle, factor: Float, expected: Triangle) =
        assertImmutabilityOf(triangle) {
            assertApproximation(expected, triangle.scaledBy(factor))
        }

    @ParameterizedTest
    @MethodSource("dilatedByArgs")
    fun dilatedByReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, factor: Float, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.dilatedBy(point.value, factor))
    }

    @ParameterizedTest
    @MethodSource("scaleByArgs")
    fun scaleByMutatesTriangleCorrectly(
        triangle: MutableTriangle, factor: Float, expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { scaleBy(factor) })

    @ParameterizedTest
    @MethodSource("dilateByArgs")
    fun dilateByMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        point: Wrapper<Vector2F>,
        factor: Float,
        expected: MutableTriangle
    ) = assertApproximation(expected, triangle.apply { dilateBy(point.value, factor) })

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFArgs")
    fun transformedByVector2FAngleFReturnsCorrectValue(
        triangle: Triangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFArgs")
    fun transformedByVector2FComplexFReturnsCorrectValue(
        triangle: Triangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFFloatArgs")
    fun transformedByVector2FAngleFFloatReturnsCorrectValue(
        triangle: Triangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(
            expected, triangle.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFFloatArgs")
    fun transformedByVector2FComplexFFloatReturnsCorrectValue(
        triangle: Triangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(
            expected, triangle.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FAngleFArgs")
    fun transformedToVector2FAngleFReturnsCorrectValue(
        triangle: Triangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FComplexFArgs")
    fun transformedToVector2FComplexFReturnsCorrectValue(
        triangle: Triangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected, triangle.transformedTo(position.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFArgs")
    fun transformByVector2FAngleFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFArgs")
    fun transformByVector2FComplexFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFFloatArgs")
    fun transformByVector2FAngleFFloatMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFFloatArgs")
    fun transformByVector2FComplexFFloatMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FAngleFArgs")
    fun transformToVector2FAngleFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FComplexFArgs")
    fun transformToVector2FComplexFMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableTriangle
    ) = assertApproximation(
        expected, triangle.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesTriangleCorrectly(triangle: MutableTriangle, expected: MutableTriangle) =
        assertApproximation(expected, triangle.apply { calibrate() })

    @ParameterizedTest
    @MethodSource("setArgs")
    fun setMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        centroid: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        originPointA: Wrapper<Vector2F>,
        originPointB: Wrapper<Vector2F>,
        originPointC: Wrapper<Vector2F>,
        expected: MutableTriangle
    ) = assertEquals(
        expected,
        triangle.apply {
            set(
                centroid.value,
                orientation.value,
                originPointA.value,
                originPointB.value,
                originPointC.value
            )
        }
    )

    @ParameterizedTest
    @MethodSource("interpolatedArgs")
    fun interpolatedReturnsCorrectValue(
        triangle: Triangle, to: Triangle, by: Float, expected: Triangle
    ) = assertImmutabilityOf(triangle) {
        assertImmutabilityOf(to) {
            val actual: Triangle = triangle.interpolated(to, by)
            assertApproximation(actual.pointA, expected.pointA)
            assertApproximation(actual.pointB, expected.pointB)
            assertApproximation(actual.pointC, expected.pointC)
        }
    }

    @ParameterizedTest
    @MethodSource("interpolateArgs")
    fun interpolateMutatesTriangleCorrectly(
        triangle: MutableTriangle,
        from: Triangle,
        to: Triangle,
        by: Float,
        expected: MutableTriangle
    ) = assertImmutabilityOf(from) {
        assertImmutabilityOf(to) {
            val actual: MutableTriangle = triangle.apply { interpolate(from, to, by) }
            assertApproximation(actual.pointA, expected.pointA)
            assertApproximation(actual.pointB, expected.pointB)
            assertApproximation(actual.pointC, expected.pointC)
        }
    }

    @ParameterizedTest
    @MethodSource("closestPointToArgs")
    fun closestPointToReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, expected: Wrapper<Vector2F>
    ) = assertImmutabilityOf(triangle) {
        assertApproximation(expected.value, triangle.closestPointTo(point.value))
    }

    @ParameterizedTest
    @MethodSource("intersectsRayArgs")
    fun intersectsRayReturnsCorrectValue(triangle: Triangle, ray: Ray, expected: Boolean) =
        assertImmutabilityOf(triangle) {
            assertImmutabilityOf(ray) {
                assertEquals(expected, triangle.intersects(ray))
            }
        }

    @ParameterizedTest
    @MethodSource("containsVector2FArgs")
    fun containsReturnsCorrectValue(
        triangle: Triangle, point: Wrapper<Vector2F>, expected: Boolean
    ) = assertImmutabilityOf(triangle) {
        assertEquals(expected, triangle.contains(point.value))
    }

    @ParameterizedTest
    @MethodSource("copyArgs")
    fun copyReturnsCorrectValue(
        triangle: Triangle,
        centroid: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        originPointA: Wrapper<Vector2F>,
        originPointB: Wrapper<Vector2F>,
        originPointC: Wrapper<Vector2F>,
        expected: Triangle
    ) = assertEquals(
        expected,
        triangle.copy(
            centroid.value,
            orientation.value,
            originPointA.value,
            originPointB.value,
            originPointC.value
        )
    )

    @ParameterizedTest
    @MethodSource("equalsAnyArgs")
    fun equalsReturnsCorrectValue(triangle: MutableTriangle, other: Any?, expected: Boolean) =
        assertImmutabilityOf(triangle) {
            assertEquals(expected, triangle == other)
        }

    @ParameterizedTest
    @MethodSource("equalsMutableTriangleArgs")
    fun equalsReturnsCorrectValue(
        triangle: MutableTriangle, other: MutableTriangle, expected: Boolean
    ) = assertImmutabilityOf(triangle) {
        assertImmutabilityOf(other) {
            assertEquals(expected, triangle.equals(other))
        }
    }

    @ParameterizedTest
    @MethodSource("hashCodeArgs")
    fun hashCodeReturnsCorrectValue(triangle: MutableTriangle, other: MutableTriangle) =
        assertImmutabilityOf(triangle) {
            assertImmutabilityOf(other) {
                assertEquals(triangle.hashCode(), other.hashCode())
            }
        }

    @ParameterizedTest
    @MethodSource("toStringArgs")
    fun toStringReturnsCorrectValue(triangle: MutableTriangle, expected: String) =
        assertImmutabilityOf(triangle) {
            assertEquals(expected, triangle.toString())
        }

    @ParameterizedTest
    @MethodSource("componentsArgs")
    fun componentsReturnCorrectValues(
        triangle: Triangle,
        expectedComponent1: Wrapper<Vector2F>,
        expectedComponent2: Wrapper<ComplexF>,
        expectedComponent3: Wrapper<Vector2F>,
        expectedComponent4: Wrapper<Vector2F>,
        expectedComponent5: Wrapper<Vector2F>,
    ) = assertImmutabilityOf(triangle) {
        val (
            actualComponent1: Vector2F,
            actualComponent2: ComplexF,
            actualComponent3: Vector2F,
            actualComponent4: Vector2F,
            actualComponent5: Vector2F
        ) = triangle

        assertEquals(expectedComponent1.value, actualComponent1)
        assertEquals(expectedComponent2.value, actualComponent2)
        assertEquals(expectedComponent3.value, actualComponent3)
        assertEquals(expectedComponent4.value, actualComponent4)
        assertEquals(expectedComponent5.value, actualComponent5)
    }

    companion object {
        @JvmStatic
        fun areApproximatelyEqual(a: Triangle, b: Triangle, tolerance: Float = 0.00001f): Boolean =
            a.centroid.isApproximately(b.centroid, tolerance) and
                    a.orientation.isApproximately(b.orientation, tolerance) and
                    a.originPointA.isApproximately(b.originPointA, tolerance) and
                    a.originPointB.isApproximately(b.originPointB, tolerance) and
                    a.originPointC.isApproximately(b.originPointC, tolerance) and
                    a.pointA.isApproximately(b.pointA, tolerance) and
                    a.pointB.isApproximately(b.pointB, tolerance) and
                    a.pointC.isApproximately(b.pointC, tolerance)

        @JvmStatic
        fun areEqual(a: Triangle, b: Triangle): Boolean =
            (a.centroid == b.centroid) and
                    (a.orientation == b.orientation) and
                    (a.originPointA == b.originPointA) and
                    (a.originPointB == b.originPointB) and
                    (a.originPointC == b.originPointC) and
                    (a.pointA == b.pointA) and
                    (a.pointB == b.pointB) and
                    (a.pointC == b.pointC)

        @JvmStatic
        fun List<Arguments>.mapTrianglesToDefaultTriangles() = map { args ->
            val argArray = args.get().map {
                if (it is Triangle) DefaultTriangle(
                    it.centroid, it.orientation, it.originPointA, it.originPointB, it.originPointC
                ) else it
            }.toTypedArray()

            Arguments.of(*argArray)
        }

        @JvmStatic
        fun constructorVector2FComplexFVector2Fx3Args(
        ): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-0.3333333f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                Wrapper(Vector2F(-3.6754265f, 0.9673083f)),
                Wrapper(Vector2F(1.520726f, -2.0326917f)),
                Wrapper(Vector2F(2.1547005f, 1.0653841f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-125f))),
                Wrapper(Vector2F(-2.6211731f, -2.6492152f)),
                Wrapper(Vector2F(1.2290114f, -1.1740615f)),
                Wrapper(Vector2F(1.3921616f, 3.8232758f))
            ),
            Arguments.of(
                Wrapper(Vector2F(8f, -4.8453f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(80f))),
                Wrapper(Vector2F(2.2743154f, 0.4010229f)),
                Wrapper(Vector2F(-0.7898607f, -2.1701279f)),
                Wrapper(Vector2F(-1.4844537f, 1.7691035f))
            ),
        )

        @JvmStatic
        fun constructorVector2Fx3Args(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(Vector2F(2f, 2f)),
                Wrapper(Vector2F(1f, 5f))
            ),
            Arguments.of(
                Wrapper(Vector2F(-2f, 1f)),
                Wrapper(Vector2F(-3f, -3f)),
                Wrapper(Vector2F(1f, -6f))
            ),
            Arguments.of(
                Wrapper(Vector2F(8f, -2.535898f)),
                Wrapper(Vector2F(10f, -6f)),
                Wrapper(Vector2F(6f, -6f))
            ),
        )

        @JvmStatic
        fun centroidArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(Vector2F(-0.3333333f, 3f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun orientationArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-125f)))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(80f)))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun originPointsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(Vector2F(-3.6754265f, 0.9673083f)),
                    Wrapper(Vector2F(1.520726f, -2.0326917f)),
                    Wrapper(Vector2F(2.1547005f, 1.0653841f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(Vector2F(-2.6211731f, -2.6492152f)),
                    Wrapper(Vector2F(1.2290114f, -1.1740615f)),
                    Wrapper(Vector2F(1.3921616f, 3.8232758f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(Vector2F(2.2743154f, 0.4010229f)),
                    Wrapper(Vector2F(-0.7898607f, -2.1701279f)),
                    Wrapper(Vector2F(-1.4844537f, 1.7691035f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun pointsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(Vector2F(2f, 2f)),
                    Wrapper(Vector2F(1f, 5f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(Vector2F(-2f, 1f)),
                    Wrapper(Vector2F(-3f, -3f)),
                    Wrapper(Vector2F(1f, -6f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(Vector2F(8f, -2.535898f)),
                    Wrapper(Vector2F(10f, -6f)),
                    Wrapper(Vector2F(6f, -6f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun areaArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    9f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    9.5f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    6.928203f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun perimeterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    14.99323f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    16.73888f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    12f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun sideLengthsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    6f,
                    3.1622777f,
                    5.8309517f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    4.1231055f,
                    5.0f,
                    7.615773f
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    4f,
                    4f,
                    4f
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centroidArgs()

        @JvmStatic
        fun incenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(Vector2F(0.3343371f, 3.200542f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(Vector2F(-1.7160178f, -2.544134f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun circumcenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(Vector2F(-1f, 2.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(Vector2F(0.9736842f, -1.868421f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun orthocenterArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(Vector2F(1f, 3.6666667f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(Vector2F(-5.947368f, -4.263158f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(Vector2F(8f, -4.8453f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
            val mutableTriangleArgs = moveByArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val mutableTriangleArgs = moveToArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                MutableTriangle(
                    centroid = Vector2F(-5.333333f, -0.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                MutableTriangle(
                    centroid = Vector2F(-0.8333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
        )

        @JvmStatic
        fun moveToArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                MutableTriangle(
                    centroid = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                MutableTriangle(
                    centroid = Vector2F(0.5f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
        )

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
            val mutableTriangleArgs = rotateByAngleFArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
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
            val mutableTriangleArgs = rotateToAngleFArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
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
            val mutableTriangleArgs = rotateAroundPointByVector2FAngleFArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
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
            val mutableTriangleArgs = rotateAroundPointToVector2FAngleFArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
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
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(35f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
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
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
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
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(0.5788478f, -7.949747f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(12.777071f, -5.821378f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(35f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(35f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
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
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(11.190803f, 2.1908038f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(102.6026f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(-0.89819396f, -0.4892627f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-142.3974f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
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
            val mutableTriangleArgs = scaleByArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun dilatedByArgs(): List<Arguments> {
            val mutableTriangleArgs = dilateByArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                2f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-5.242346f, -5.2984304f),
                    originPointB = Vector2F(2.4580228f, -2.348123f),
                    originPointC = Vector2F(2.7843232f, 7.6465516f)
                ),
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                0.3f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-0.7863519f, -0.7947646f),
                    originPointB = Vector2F(0.36870342f, -0.35221845f),
                    originPointC = Vector2F(0.4176485f, 1.1469827f)
                ),
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                1f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                -1f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(2.6211731f, 2.6492152f),
                    originPointB = Vector2F(-1.2290114f, 1.1740615f),
                    originPointC = Vector2F(-1.3921616f, -3.8232758f)
                )
            ),
        )

        @JvmStatic
        fun dilateByArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                2f,
                MutableTriangle(
                    centroid = Vector2F(-8.666666f, -2.3333333f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-5.242346f, -5.2984304f),
                    originPointB = Vector2F(2.4580228f, -2.348123f),
                    originPointC = Vector2F(2.7843232f, 7.6465516f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                0.3f,
                MutableTriangle(
                    centroid = Vector2F(3.8f, -2.9f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-0.7863519f, -0.7947646f),
                    originPointB = Vector2F(0.36870342f, -0.35221845f),
                    originPointC = Vector2F(0.4176485f, 1.1469827f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                1f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(6f, -3f)),
                -1f,
                MutableTriangle(
                    centroid = Vector2F(13.333333f, -3.3333333f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(2.6211731f, 2.6492152f),
                    originPointB = Vector2F(-1.2290114f, 1.1740615f),
                    originPointC = Vector2F(-1.3921616f, -3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                2f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-5.242346f, -5.2984304f),
                    originPointB = Vector2F(2.4580228f, -2.348123f),
                    originPointC = Vector2F(2.7843232f, 7.6465516f)
                )
            ),
        )

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val mutableTriangleArgs = transformByVector2FAngleFArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
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
            val mutableTriangleArgs = transformByVector2FAngleFFloatArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
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
            val mutableTriangleArgs = transformToVector2FAngleFArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
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
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(-5.333333f, -0.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(-0.8333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(35f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
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
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                2f,
                MutableTriangle(
                    centroid = Vector2F(-5.333333f, -0.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                    originPointA = Vector2F(-5.242346f, -5.2984304f),
                    originPointB = Vector2F(2.4580228f, -2.348123f),
                    originPointC = Vector2F(2.7843232f, 7.6465516f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                0.3f,
                MutableTriangle(
                    centroid = Vector2F(-0.8333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(35f)),
                    originPointA = Vector2F(-0.7863519f, -0.7947646f),
                    originPointB = Vector2F(0.36870342f, -0.35221845f),
                    originPointC = Vector2F(0.4176485f, 1.1469827f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                1f,
                MutableTriangle(
                    centroid = Vector2F(-5.333333f, -0.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                -1f,
                MutableTriangle(
                    centroid = Vector2F(-5.333333f, -0.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-80f)),
                    originPointA = Vector2F(2.6211731f, 2.6492152f),
                    originPointB = Vector2F(-1.2290114f, 1.1740615f),
                    originPointC = Vector2F(-1.3921616f, -3.8232758f)
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
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(-4f, 2f)),
                Wrapper(AngleF.fromDegrees(45f)),
                MutableTriangle(
                    centroid = Vector2F(-4f, 2f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(45f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                Wrapper(Vector2F(0.5f, 0f)),
                Wrapper(AngleF.fromDegrees(-200f)),
                MutableTriangle(
                    centroid = Vector2F(0.5f, 0f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-200f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
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
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 7.1f, AngleF.fromDegrees(30f).radians
                    ),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 0.71f, AngleF.fromDegrees(30f).radians
                    ),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.ZERO,
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.ONE,
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                )
            ),
        )

        @JvmStatic
        fun setArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                Wrapper(Vector2F(-0.3333333f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                Wrapper(Vector2F(-3.6754265f, 0.9673083f)),
                Wrapper(Vector2F(1.520726f, -2.0326917f)),
                Wrapper(Vector2F(2.1547005f, 1.0653841f)),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                Wrapper(Vector2F(-3.6754265f, 0.9673083f)),
                Wrapper(Vector2F(1.520726f, -2.0326917f)),
                Wrapper(Vector2F(1.3921616f, 3.8232758f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-125f))),
                Wrapper(Vector2F(-2.6211731f, -2.6492152f)),
                Wrapper(Vector2F(1.2290114f, -1.1740615f)),
                Wrapper(Vector2F(1.3921616f, 3.8232758f)),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
            ),
        )

        @JvmStatic
        fun interpolatedArgs(): List<Arguments> {
            val mutableTriangleArgs = interpolateArgs().map {
                Arguments.of(*it.get().drop(1).toTypedArray())
            }
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun interpolateArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    originPointA = Vector2F(1f, 0f),
                    originPointB = Vector2F(-1f, 0f),
                    originPointC = Vector2F(0f, 1f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                0.5f,
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    originPointA = Vector2F(1f, 0f),
                    originPointB = Vector2F(-1f, 0f),
                    originPointC = Vector2F(0f, 1f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                0f,
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    originPointA = Vector2F(1f, 0f),
                    originPointB = Vector2F(-1f, 0f),
                    originPointC = Vector2F(0f, 1f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                0.25f,
                MutableTriangle(
                    Vector2F(-4.3240943f, 2.1412225f),
                    Vector2F(0.66140705f, -0.22897983f),
                    Vector2F(1.9126875f, 2.8377573f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    originPointA = Vector2F(1f, 0f),
                    originPointB = Vector2F(-1f, 0f),
                    originPointC = Vector2F(0f, 1f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                0.5f,
                MutableTriangle(
                    Vector2F(-4.017435f, 2.173356f),
                    Vector2F(-0.85369134f, -1.7579086f),
                    Vector2F(2.3711262f, 0.08455205f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    originPointA = Vector2F(1f, 0f),
                    originPointB = Vector2F(-1f, 0f),
                    originPointC = Vector2F(0f, 1f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                0.75f,
                MutableTriangle(
                    Vector2F(-3.179938f, 1.8533859f),
                    Vector2F(-2.175759f, -2.6148026f),
                    Vector2F(2.1056974f, -2.988583f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F.ZERO,
                    orientation = ComplexF.ONE,
                    originPointA = Vector2F(1f, 0f),
                    originPointB = Vector2F(-1f, 0f),
                    originPointC = Vector2F(0f, 1f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                1f,
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
        )

        @JvmStatic
        fun closestPointToArgs(): List<Arguments> {
            val triangleA = MutableTriangle(
                centroid = Vector2F(-0.3333333f, 3f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                originPointA = Vector2F(-3.6754265f, 0.9673083f),
                originPointB = Vector2F(1.520726f, -2.0326917f),
                originPointC = Vector2F(2.1547005f, 1.0653841f)
            )
            val triangleAArgs = listOf(
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-3.903629f, 2.0266933f)),
                    Wrapper(Vector2F(-3.903629f, 2.0266933f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-4f, 2f)),
                    Wrapper(Vector2F(-4f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-4.0963717f, 1.9733067f)),
                    Wrapper(Vector2F(-4f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.9188758f, 2.058471f)),
                    Wrapper(Vector2F(1.9188758f, 2.058471f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(2f, 2f)),
                    Wrapper(Vector2F(2f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(2.081124f, 1.941529f)),
                    Wrapper(Vector2F(2f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(0.9653053f, 4.9062114f)),
                    Wrapper(Vector2F(0.9653053f, 4.9062114f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1f, 5f)),
                    Wrapper(Vector2F(1f, 5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.0346946f, 5.093788f)),
                    Wrapper(Vector2F(1f, 5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1f, 2.1f)),
                    Wrapper(Vector2F(-1f, 2.1f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1f, 2f)),
                    Wrapper(Vector2F(-1f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1f, 1.9f)),
                    Wrapper(Vector2F(-1f, 2f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.4051317f, 3.468377f)),
                    Wrapper(Vector2F(1.4051317f, 3.468377f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.5f, 3.5f)),
                    Wrapper(Vector2F(1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(1.5948683f, 3.531623f)),
                    Wrapper(Vector2F(1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.4485505f, 3.4142506f)),
                    Wrapper(Vector2F(-1.4485505f, 3.4142506f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.5f, 3.5f)),
                    Wrapper(Vector2F(-1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA,
                    Wrapper(Vector2F(-1.5514495f, 3.5857494f)),
                    Wrapper(Vector2F(-1.5f, 3.5f))
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.centroid), Wrapper(triangleA.centroid)
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.orthocenter), Wrapper(triangleA.orthocenter)
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.incenter), Wrapper(triangleA.incenter)
                ),
                Arguments.of(
                    triangleA, Wrapper(triangleA.circumcenter), Wrapper(triangleA.circumcenter)
                ),
            )
            val triangleB = MutableTriangle(
                centroid = Vector2F(-1.3333333f, -2.6666667f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                originPointA = Vector2F(-2.6211731f, -2.6492152f),
                originPointB = Vector2F(1.2290114f, -1.1740615f),
                originPointC = Vector2F(1.3921616f, 3.8232758f)
            )
            val triangleBArgs = listOf(
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1.9920129f, 0.9003196f)),
                    Wrapper(Vector2F(-1.9920129f, 0.9003196f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2f, 1f)),
                    Wrapper(Vector2F(-2f, 1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.007987f, 1.0996807f)),
                    Wrapper(Vector2F(-2f, 1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.9057631f, -2.966542f)),
                    Wrapper(Vector2F(-2.9057631f, -2.966542f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-3f, -3f)),
                    Wrapper(Vector2F(-3f, -3f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-3.0942369f, -3.033458f)),
                    Wrapper(Vector2F(-3f, -3f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(0.9382081f, -5.921376f)),
                    Wrapper(Vector2F(0.9382081f, -5.921376f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(1f, -6f)),
                    Wrapper(Vector2F(1f, -6f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(1.0617917f, -6.078624f)),
                    Wrapper(Vector2F(1f, -6f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.4029858f, -1.0242536f)),
                    Wrapper(Vector2F(-2.4029858f, -1.0242536f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.5f, -1f)),
                    Wrapper(Vector2F(-2.5f, -1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-2.5970142f, -0.97574645f)),
                    Wrapper(Vector2F(-2.5f, -1f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.94f, -4.42f)),
                    Wrapper(Vector2F(-0.94f, -4.42f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1f, -4.5f)),
                    Wrapper(Vector2F(-1f, -4.5f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-1.06f, -4.58f)),
                    Wrapper(Vector2F(-1f, -4.5f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.59191453f, -2.539392f)),
                    Wrapper(Vector2F(-0.59191453f, -2.539392f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.5f, -2.5f)),
                    Wrapper(Vector2F(-0.5f, -2.5f))
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(Vector2F(-0.4080855f, -2.460608f)),
                    Wrapper(Vector2F(-0.5f, -2.5f))
                ),
                Arguments.of(
                    triangleB, Wrapper(triangleB.centroid), Wrapper(triangleB.centroid)
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(triangleB.orthocenter),
                    Wrapper(Vector2F(-3f, -3f))
                ),
                Arguments.of(
                    triangleB, Wrapper(triangleB.incenter), Wrapper(triangleB.incenter)
                ),
                Arguments.of(
                    triangleB,
                    Wrapper(triangleB.circumcenter),
                    Wrapper(Vector2F(-0.5f, -2.5f))
                ),
            )
            val triangleC = MutableTriangle(
                centroid = Vector2F(8f, -4.8453f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                originPointA = Vector2F(2.2743154f, 0.4010229f),
                originPointB = Vector2F(-0.7898607f, -2.1701279f),
                originPointC = Vector2F(-1.4844537f, 1.7691035f)
            )
            val triangleCArgs = listOf(
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -2.6358979f)),
                    Wrapper(Vector2F(8f, -2.6358979f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -2.535898f)),
                    Wrapper(Vector2F(8f, -2.535898f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -2.435898f)),
                    Wrapper(Vector2F(8f, -2.535898f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9.913398f, -5.95f)),
                    Wrapper(Vector2F(9.913398f, -5.95f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(10f, -6f)),
                    Wrapper(Vector2F(10f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(10.086602f, -6.05f)),
                    Wrapper(Vector2F(10f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6.086602f, -5.95f)),
                    Wrapper(Vector2F(6.086602f, -5.95f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6f, -6f)),
                    Wrapper(Vector2F(6f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(5.913398f, -6.05f)),
                    Wrapper(Vector2F(6f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8.913398f, -4.3179493f)),
                    Wrapper(Vector2F(8.913398f, -4.3179493f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9f, -4.267949f)),
                    Wrapper(Vector2F(9f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(9.086602f, -4.217949f)),
                    Wrapper(Vector2F(9f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -5.9f)),
                    Wrapper(Vector2F(8f, -5.9f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -6f)),
                    Wrapper(Vector2F(8f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(8f, -6.1f)),
                    Wrapper(Vector2F(8f, -6f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(7.0866027f, -4.3179493f)),
                    Wrapper(Vector2F(7.0866027f, -4.3179493f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(7f, -4.267949f)),
                    Wrapper(Vector2F(7f, -4.267949f))
                ),
                Arguments.of(
                    triangleC,
                    Wrapper(Vector2F(6.9133973f, -4.217949f)),
                    Wrapper(Vector2F(7f, -4.267949f))
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.centroid), Wrapper(triangleC.centroid)
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.orthocenter), Wrapper(triangleC.orthocenter)
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.incenter), Wrapper(triangleC.incenter)
                ),
                Arguments.of(
                    triangleC, Wrapper(triangleC.circumcenter), Wrapper(triangleC.circumcenter)
                ),
            )
            val mutableTriangleArgs = listOf(
                triangleAArgs,
                triangleBArgs,
                triangleCArgs
            ).flatten()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> = RayTests.intersectsTriangleArgs().map {
            val args = it.get()
            Arguments.of(args[1], args[0], args[2])
        }

        @JvmStatic
        fun containsVector2FArgs(): List<Arguments> {
            val triangleA = MutableTriangle(
                centroid = Vector2F(-0.3333333f, 3f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                originPointA = Vector2F(-3.6754265f, 0.9673083f),
                originPointB = Vector2F(1.520726f, -2.0326917f),
                originPointC = Vector2F(2.1547005f, 1.0653841f)
            )
            val triangleAArgs = listOf(
                Arguments.of(triangleA, Wrapper(Vector2F(-3.903629f, 2.0266933f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(-4.0963717f, 1.9733067f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(1.9188758f, 2.058471f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(2.081124f, 1.941529f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(0.9653053f, 4.9062114f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(1.0346946f, 5.093788f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.0f, 2.1f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.0f, 1.9f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(1.4051317f, 3.468377f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(1.5948683f, 3.531623f)), false),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.4485505f, 3.4142506f)), true),
                Arguments.of(triangleA, Wrapper(Vector2F(-1.5514495f, 3.5857494f)), false),
                Arguments.of(triangleA, Wrapper(triangleA.centroid), true),
                Arguments.of(triangleA, Wrapper(triangleA.orthocenter), true),
                Arguments.of(triangleA, Wrapper(triangleA.incenter), true),
                Arguments.of(triangleA, Wrapper(triangleA.circumcenter), true),
            )
            val triangleB = MutableTriangle(
                centroid = Vector2F(-1.3333333f, -2.6666667f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                originPointA = Vector2F(-2.6211731f, -2.6492152f),
                originPointB = Vector2F(1.2290114f, -1.1740615f),
                originPointC = Vector2F(1.3921616f, 3.8232758f)
            )
            val triangleBArgs = listOf(
                Arguments.of(triangleB, Wrapper(Vector2F(-1.9920129f, 0.9003196f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.007987f, 1.0996807f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.9057631f, -2.966542f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-3.0942369f, -3.033458f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(0.9382081f, -5.921376f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(1.0617917f, -6.078624f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.4029858f, -1.0242536f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-2.5970142f, -0.97574645f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-0.94f, -4.42f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-1.06f, -4.58f)), false),
                Arguments.of(triangleB, Wrapper(Vector2F(-0.59191453f, -2.539392f)), true),
                Arguments.of(triangleB, Wrapper(Vector2F(-0.4080855f, -2.460608f)), false),
                Arguments.of(triangleB, Wrapper(triangleB.centroid), true),
                Arguments.of(triangleB, Wrapper(triangleB.orthocenter), false),
                Arguments.of(triangleB, Wrapper(triangleB.incenter), true),
                Arguments.of(triangleB, Wrapper(triangleB.circumcenter), false),
            )
            val triangleC = MutableTriangle(
                centroid = Vector2F(8f, -4.8453f),
                orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                originPointA = Vector2F(2.2743154f, 0.4010229f),
                originPointB = Vector2F(-0.7898607f, -2.1701279f),
                originPointC = Vector2F(-1.4844537f, 1.7691035f)
            )
            val triangleCArgs = listOf(
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -2.6358979f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -2.435898f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(9.913398f, -5.95f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(10.086602f, -6.05f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(6.086602f, -5.95f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(5.913398f, -6.05f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(8.913398f, -4.3179493f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(9.086602f, -4.217949f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -5.9f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(8.0f, -6.1f)), false),
                Arguments.of(triangleC, Wrapper(Vector2F(7.0866027f, -4.3179493f)), true),
                Arguments.of(triangleC, Wrapper(Vector2F(6.9133973f, -4.217949f)), false),
                Arguments.of(triangleC, Wrapper(triangleC.centroid), true),
                Arguments.of(triangleC, Wrapper(triangleC.orthocenter), true),
                Arguments.of(triangleC, Wrapper(triangleC.incenter), true),
                Arguments.of(triangleC, Wrapper(triangleC.circumcenter), true),
            )
            val mutableTriangleArgs = listOf(
                triangleAArgs,
                triangleBArgs,
                triangleCArgs
            ).flatten()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun copyArgs(): List<Arguments> {
            val mutableTriangleArgs = setArgs()
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }

        @JvmStatic
        fun equalsAnyArgs(): List<Arguments> = equalsMutableTriangleArgs() + listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                null,
                false
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                DefaultTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                true
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                DefaultTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1f, 1.0653841f)
                ),
                false
            ),
        )

        @JvmStatic
        fun equalsMutableTriangleArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                true
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1f, 1.0653841f)
                ),
                false
            ),
        )

        @JvmStatic
        fun hashCodeArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                )
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                )
            ),
        )

        @JvmStatic
        fun toStringArgs(): List<Arguments> = listOf(
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-0.3333333f, 3f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                    originPointA = Vector2F(-3.6754265f, 0.9673083f),
                    originPointB = Vector2F(1.520726f, -2.0326917f),
                    originPointC = Vector2F(2.1547005f, 1.0653841f)
                ),
                "Triangle(" +
                        "centroid=${Vector2F(-0.3333333f, 3f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(30f))}, " +
                        "originPointA=${Vector2F(-3.6754265f, 0.9673083f)}, " +
                        "originPointB=${Vector2F(1.520726f, -2.0326917f)}, " +
                        "originPointC=${Vector2F(2.1547005f, 1.0653841f)})"
            ),
            Arguments.of(
                MutableTriangle(
                    centroid = Vector2F(-1.3333333f, -2.6666667f),
                    orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                    originPointA = Vector2F(-2.6211731f, -2.6492152f),
                    originPointB = Vector2F(1.2290114f, -1.1740615f),
                    originPointC = Vector2F(1.3921616f, 3.8232758f)
                ),
                "Triangle(" +
                        "centroid=${Vector2F(-1.3333333f, -2.6666667f)}, " +
                        "orientation=${ComplexF.fromAngle(AngleF.fromDegrees(-125f))}, " +
                        "originPointA=${Vector2F(-2.6211731f, -2.6492152f)}, " +
                        "originPointB=${Vector2F(1.2290114f, -1.1740615f)}, " +
                        "originPointC=${Vector2F(1.3921616f, 3.8232758f)})"
            ),
        )

        @JvmStatic
        fun componentsArgs(): List<Arguments> {
            val mutableTriangleArgs = listOf(
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-0.3333333f, 3f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(30f)),
                        originPointA = Vector2F(-3.6754265f, 0.9673083f),
                        originPointB = Vector2F(1.520726f, -2.0326917f),
                        originPointC = Vector2F(2.1547005f, 1.0653841f)
                    ),
                    Wrapper(Vector2F(-0.3333333f, 3f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(30f))),
                    Wrapper(Vector2F(-3.6754265f, 0.9673083f)),
                    Wrapper(Vector2F(1.520726f, -2.0326917f)),
                    Wrapper(Vector2F(2.1547005f, 1.0653841f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(-1.3333333f, -2.6666667f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(-125f)),
                        originPointA = Vector2F(-2.6211731f, -2.6492152f),
                        originPointB = Vector2F(1.2290114f, -1.1740615f),
                        originPointC = Vector2F(1.3921616f, 3.8232758f)
                    ),
                    Wrapper(Vector2F(-1.3333333f, -2.6666667f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-125f))),
                    Wrapper(Vector2F(-2.6211731f, -2.6492152f)),
                    Wrapper(Vector2F(1.2290114f, -1.1740615f)),
                    Wrapper(Vector2F(1.3921616f, 3.8232758f))
                ),
                Arguments.of(
                    MutableTriangle(
                        centroid = Vector2F(8f, -4.8453f),
                        orientation = ComplexF.fromAngle(AngleF.fromDegrees(80f)),
                        originPointA = Vector2F(2.2743154f, 0.4010229f),
                        originPointB = Vector2F(-0.7898607f, -2.1701279f),
                        originPointC = Vector2F(-1.4844537f, 1.7691035f)
                    ),
                    Wrapper(Vector2F(8f, -4.8453f)),
                    Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(80f))),
                    Wrapper(Vector2F(2.2743154f, 0.4010229f)),
                    Wrapper(Vector2F(-0.7898607f, -2.1701279f)),
                    Wrapper(Vector2F(-1.4844537f, 1.7691035f))
                ),
            )
            val defaultTriangleArgs = mutableTriangleArgs.mapTrianglesToDefaultTriangles()

            return listOf(
                mutableTriangleArgs,
                defaultTriangleArgs
            ).flatten()
        }
    }
}