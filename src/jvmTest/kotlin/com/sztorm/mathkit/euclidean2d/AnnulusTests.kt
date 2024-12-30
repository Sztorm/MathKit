package com.sztorm.mathkit.euclidean2d

import com.sztorm.mathkit.AngleF
import com.sztorm.mathkit.ComplexF
import com.sztorm.mathkit.Vector2F
import com.sztorm.mathkit.euclidean2d.CircleTests.Companion.mapCirclesToDefaultCircles
import com.sztorm.mathkit.euclidean2d.RayTests.Companion.mapRaysToDefaultRays
import com.sztorm.mathkit.euclidean2d.utils.DefaultAnnulus
import com.sztorm.mathkit.euclidean2d.utils.assertApproximation
import com.sztorm.mathkit.euclidean2d.utils.assertEquals
import com.sztorm.mathkit.euclidean2d.utils.assertImmutabilityOf
import com.sztorm.mathkit.isApproximately
import com.sztorm.mathkit.utils.Wrapper
import com.sztorm.mathkit.utils.assertApproximation
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class AnnulusTests {
    @ParameterizedTest
    @MethodSource("constructorArgs")
    fun constructorCreatesCorrectAnnulus(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float
    ) {
        val mutableAnnulus = MutableAnnulus(
            center.value, orientation.value, outerRadius, innerRadius
        )
        val annulus = Annulus(center.value, orientation.value, outerRadius, innerRadius)

        assertEquals(center.value, mutableAnnulus.center)
        assertEquals(orientation.value, mutableAnnulus.orientation)
        assertEquals(outerRadius, mutableAnnulus.outerRadius)
        assertEquals(innerRadius, mutableAnnulus.innerRadius)

        assertEquals(center.value, annulus.center)
        assertEquals(orientation.value, annulus.orientation)
        assertEquals(outerRadius, annulus.outerRadius)
        assertEquals(innerRadius, annulus.innerRadius)
    }

    @ParameterizedTest
    @MethodSource("constructorThrowsExceptionArgs")
    fun constructorThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        assertThrows(expectedExceptionClass) {
            MutableAnnulus(center.value, orientation.value, outerRadius, innerRadius)
        }
        assertThrows(expectedExceptionClass) {
            Annulus(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

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
    @MethodSource("widthArgs")
    fun widthReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.width)
        }

    @ParameterizedTest
    @MethodSource("outerDiameterArgs")
    fun outerDiameterReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.outerDiameter)
        }

    @ParameterizedTest
    @MethodSource("innerDiameterArgs")
    fun innerDiameterReturnsCorrectValue(annulus: Annulus, expected: Float) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.innerDiameter)
        }

    @ParameterizedTest
    @MethodSource("positionArgs")
    fun positionReturnsCorrectValue(annulus: Annulus, expected: Wrapper<Vector2F>) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected.value, annulus.position)
        }

    @ParameterizedTest
    @MethodSource("movedByArgs")
    fun movedByReturnsCorrectValue(
        annulus: Annulus, displacement: Wrapper<Vector2F>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.movedBy(displacement.value))
    }

    @ParameterizedTest
    @MethodSource("movedToArgs")
    fun movedToReturnsCorrectValue(
        annulus: Annulus, position: Wrapper<Vector2F>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.movedTo(position.value))
    }

    @ParameterizedTest
    @MethodSource("moveByArgs")
    fun moveByMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, displacement: Wrapper<Vector2F>, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { moveBy(displacement.value) })

    @ParameterizedTest
    @MethodSource("moveToArgs")
    fun moveToMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, position: Wrapper<Vector2F>, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { moveTo(position.value) })

    @ParameterizedTest
    @MethodSource("rotatedByAngleFArgs")
    fun rotatedByAngleFReturnsCorrectValue(
        annulus: Annulus, rotation: Wrapper<AngleF>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedByComplexFArgs")
    fun rotatedByComplexFReturnsCorrectValue(
        annulus: Annulus, rotation: Wrapper<ComplexF>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedBy(rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToAngleFArgs")
    fun rotatedToAngleFReturnsCorrectValue(
        annulus: Annulus, orientation: Wrapper<AngleF>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedToComplexFArgs")
    fun rotatedToComplexFReturnsCorrectValue(
        annulus: Annulus, orientation: Wrapper<ComplexF>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedTo(orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FAngleFArgs")
    fun rotatedAroundPointByVector2FAngleFReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, rotation: Wrapper<AngleF>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointByVector2FComplexFArgs")
    fun rotatedAroundPointByVector2FComplexFReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, rotation: Wrapper<ComplexF>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedAroundPointBy(point.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FAngleFArgs")
    fun rotatedAroundPointToVector2FAngleFReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, orientation: Wrapper<AngleF>, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedAroundPointTo(point.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotatedAroundPointToVector2FComplexFArgs")
    fun rotatedAroundPointToVector2FComplexFReturnsCorrectValue(
        annulus: Annulus,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.rotatedAroundPointTo(point.value, orientation.value))
    }

    @ParameterizedTest
    @MethodSource("rotateByAngleFArgs")
    fun rotateByAngleFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, rotation: Wrapper<AngleF>, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateByComplexFArgs")
    fun rotateByComplexFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, rotation: Wrapper<ComplexF>, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { rotateBy(rotation.value) })

    @ParameterizedTest
    @MethodSource("rotateToAngleFArgs")
    fun rotateToAngleFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, orientation: Wrapper<AngleF>, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateToComplexFArgs")
    fun rotateToComplexFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, orientation: Wrapper<ComplexF>, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { rotateTo(orientation.value) })

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FAngleFArgs")
    fun rotateAroundPointByVector2FAngleFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointByVector2FComplexFArgs")
    fun rotateAroundPointByVector2FComplexFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        point: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { rotateAroundPointBy(point.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FAngleFArgs")
    fun rotateAroundPointToVector2FAngleFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("rotateAroundPointToVector2FComplexFArgs")
    fun rotateAroundPointToVector2FComplexFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        point: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { rotateAroundPointTo(point.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("scaledByArgs")
    fun scaledByReturnsCorrectValue(annulus: Annulus, factor: Float, expected: Annulus) =
        assertImmutabilityOf(annulus) {
            assertApproximation(expected, annulus.scaledBy(factor))
        }

    @ParameterizedTest
    @MethodSource("dilatedByArgs")
    fun dilatedByReturnsCorrectValue(
        annulus: Annulus, point: Wrapper<Vector2F>, factor: Float, expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.dilatedBy(point.value, factor))
    }

    @ParameterizedTest
    @MethodSource("scaleByArgs")
    fun scaleByMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, factor: Float, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { scaleBy(factor) })

    @ParameterizedTest
    @MethodSource("dilateByArgs")
    fun dilateByMutatesAnnulusCorrectly(
        annulus: MutableAnnulus, point: Wrapper<Vector2F>, factor: Float, expected: MutableAnnulus
    ) = assertApproximation(expected, annulus.apply { dilateBy(point.value, factor) })

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFArgs")
    fun transformedByVector2FAngleFReturnsCorrectValue(
        annulus: Annulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFArgs")
    fun transformedByVector2FComplexFReturnsCorrectValue(
        annulus: Annulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(expected, annulus.transformedBy(displacement.value, rotation.value))
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FAngleFFloatArgs")
    fun transformedByVector2FAngleFFloatReturnsCorrectValue(
        annulus: Annulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(
            expected, annulus.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedByVector2FComplexFFloatArgs")
    fun transformedByVector2FComplexFFloatReturnsCorrectValue(
        annulus: Annulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(
            expected, annulus.transformedBy(displacement.value, rotation.value, scaleFactor)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FAngleFArgs")
    fun transformedToVector2FAngleFReturnsCorrectValue(
        annulus: Annulus,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(
            expected, annulus.transformedTo(position.value, orientation.value)
        )
    }

    @ParameterizedTest
    @MethodSource("transformedToVector2FComplexFArgs")
    fun transformedToVector2FComplexFReturnsCorrectValue(
        annulus: Annulus,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: Annulus
    ) = assertImmutabilityOf(annulus) {
        assertApproximation(
            expected, annulus.transformedTo(position.value, orientation.value)
        )
    }

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFArgs")
    fun transformByVector2FAngleFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFArgs")
    fun transformByVector2FComplexFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { transformBy(displacement.value, rotation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FAngleFFloatArgs")
    fun transformByVector2FAngleFFloatMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<AngleF>,
        scaleFactor: Float,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformByVector2FComplexFFloatArgs")
    fun transformByVector2FComplexFFloatMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        displacement: Wrapper<Vector2F>,
        rotation: Wrapper<ComplexF>,
        scaleFactor: Float,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { transformBy(displacement.value, rotation.value, scaleFactor) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FAngleFArgs")
    fun transformToVector2FAngleFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<AngleF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("transformToVector2FComplexFArgs")
    fun transformToVector2FComplexFMutatesAnnulusCorrectly(
        annulus: MutableAnnulus,
        position: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        expected: MutableAnnulus
    ) = assertApproximation(
        expected, annulus.apply { transformTo(position.value, orientation.value) }
    )

    @ParameterizedTest
    @MethodSource("calibrateArgs")
    fun calibrateMutatesAnnulusCorrectly(annulus: MutableAnnulus, expected: MutableAnnulus) =
        assertApproximation(expected, annulus.apply { calibrate() })

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
    @MethodSource("setThrowsExceptionArgs")
    fun setThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertThrows(expectedExceptionClass) {
            annulus.set(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("setDoesNotThrowExceptionArgs")
    fun setDoesNotThrowException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertDoesNotThrow {
            annulus.set(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

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
    @MethodSource("intersectsAnnulusArgs")
    fun intersectsReturnsCorrectValue(annulus: Annulus, otherAnnulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(otherAnnulus) {
                assertEquals(expected, annulus.intersects(otherAnnulus))
            }
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
    @MethodSource("intersectsRayArgs")
    fun intersectsReturnsCorrectValue(annulus: Annulus, ray: Ray, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(ray) {
                assertEquals(expected, annulus.intersects(ray))
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
    @MethodSource("containsAnnulusArgs")
    fun containsReturnsCorrectValue(annulus: Annulus, otherAnnulus: Annulus, expected: Boolean) =
        assertImmutabilityOf(annulus) {
            assertImmutabilityOf(otherAnnulus) {
                assertEquals(expected, annulus.contains(otherAnnulus))
            }
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
    @MethodSource("copyThrowsExceptionArgs")
    fun copyThrowsCorrectException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float,
        expectedExceptionClass: Class<Throwable>
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertThrows(expectedExceptionClass) {
            annulus.copy(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

    @ParameterizedTest
    @MethodSource("copyDoesNotThrowExceptionArgs")
    fun copyDoesNotThrowException(
        center: Wrapper<Vector2F>,
        orientation: Wrapper<ComplexF>,
        outerRadius: Float,
        innerRadius: Float
    ) {
        val annulus = MutableAnnulus(
            center = Vector2F.ZERO,
            orientation = ComplexF.ONE,
            outerRadius = 1f,
            innerRadius = 0.5f
        )
        assertDoesNotThrow {
            annulus.copy(center.value, orientation.value, outerRadius, innerRadius)
        }
    }

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
        fun areEqual(a: Annulus, b: Annulus): Boolean =
            (a.center == b.center) and
                    (a.orientation == b.orientation) and
                    (a.outerRadius == b.outerRadius) and
                    (a.innerRadius == b.innerRadius)

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
        fun constructorArgs(): List<Arguments> = listOf(
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                1f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                2f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                0f,
                0f
            ),
            Arguments.of(
                Wrapper(Vector2F(-1f, 2f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(-45f))),
                4f,
                2f
            ),
            Arguments.of(
                Wrapper(Vector2F(6f, 3f)),
                Wrapper(ComplexF.fromAngle(AngleF.fromDegrees(330f))),
                8f,
                1f
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
                2f,
                -1f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                -1f,
                -0.5f,
                IllegalArgumentException::class.java
            ),
            Arguments.of(
                Wrapper(Vector2F.ZERO),
                Wrapper(ComplexF.ONE),
                2f,
                2.1f,
                IllegalArgumentException::class.java
            ),
        )

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
        fun widthArgs(): List<Arguments> {
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
        fun outerDiameterArgs(): List<Arguments> = outerRadiusArgs().map {
            val args = it.get()
            Arguments.of(args[0], args[1] as Float * 2f)
        }

        @JvmStatic
        fun innerDiameterArgs(): List<Arguments> = innerRadiusArgs().map {
            val args = it.get()
            Arguments.of(args[0], args[1] as Float * 2f)
        }

        @JvmStatic
        fun positionArgs(): List<Arguments> = centerArgs()

        @JvmStatic
        fun movedByArgs(): List<Arguments> {
            val mutableAnnulusArgs = moveByArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun movedToArgs(): List<Arguments> {
            val mutableAnnulusArgs = moveToArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun moveByArgs(): List<Arguments> = listOf(
            Arguments.of(
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

        @JvmStatic
        fun moveToArgs(): List<Arguments> = listOf(
            Arguments.of(
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

        @JvmStatic
        fun rotatedByAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = rotateByAngleFArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
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
            val mutableAnnulusArgs = rotateToAngleFArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
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
            val mutableAnnulusArgs = rotateAroundPointByVector2FAngleFArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
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
            val mutableAnnulusArgs = rotateAroundPointToVector2FAngleFArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
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
            val mutableAnnulusArgs = scaleByArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun dilatedByArgs(): List<Arguments> {
            val mutableAnnulusArgs = dilateByArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun scaleByArgs(): List<Arguments> = listOf(
            Arguments.of(
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
                )
            ),
            Arguments.of(
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
                )
            ),
        )

        @JvmStatic
        fun dilateByArgs(): List<Arguments> = listOf(
            Arguments.of(
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
                )
            ),
            Arguments.of(
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
                )
            ),
            Arguments.of(
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

        @JvmStatic
        fun transformedByVector2FAngleFArgs(): List<Arguments> {
            val mutableAnnulusArgs = transformByVector2FAngleFArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
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
            val mutableAnnulusArgs = transformByVector2FAngleFFloatArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
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
            val mutableAnnulusArgs = transformToVector2FAngleFArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
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
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 3f, AngleF.fromDegrees(-45f).radians
                    ),
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
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.fromPolar(
                        magnitude = 0.3f, AngleF.fromDegrees(-45f).radians
                    ),
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
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ZERO,
                    outerRadius = 4f,
                    innerRadius = 2f
                ),
                MutableAnnulus(
                    center = Vector2F(-1f, 2f),
                    orientation = ComplexF.ONE,
                    outerRadius = 4f,
                    innerRadius = 2f
                )
            ),
        )

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
        fun setThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun setDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

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
        fun intersectsAnnulusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
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
                    MutableAnnulus(
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
                    MutableAnnulus(
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
                    MutableAnnulus(
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
                    MutableAnnulus(
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
                    MutableAnnulus(
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
        fun intersectsCircleArgs(): List<Arguments> {
            val mutableAnnulusMutableCircleArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
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
                    MutableCircle(
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
                    MutableCircle(
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
                    MutableCircle(
                        center = Vector2F(-6f, 2f),
                        orientation = ComplexF.ONE,
                        radius = 0.99f
                    ),
                    false
                ),
            )
            val defaultAnnulusMutableCircleArgs = mutableAnnulusMutableCircleArgs
                .mapAnnulusesToDefaultAnnuluses()
            val mutableAnnulusDefaultCircleArgs = mutableAnnulusMutableCircleArgs
                .mapCirclesToDefaultCircles()
            val defaultAnnulusDefaultCircleArgs = defaultAnnulusMutableCircleArgs
                .mapCirclesToDefaultCircles()

            return listOf(
                mutableAnnulusMutableCircleArgs,
                defaultAnnulusMutableCircleArgs,
                mutableAnnulusDefaultCircleArgs,
                defaultAnnulusDefaultCircleArgs
            ).flatten()
        }

        @JvmStatic
        fun intersectsRayArgs(): List<Arguments> {
            val annulus = MutableAnnulus(
                center = Vector2F(-4f, 4f),
                orientation = ComplexF.ONE,
                outerRadius = 4f,
                innerRadius = 2f
            )
            val mutableAnnulusMutableRayArgs = listOf(
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-8.1f, 0f),
                        direction = AngleF.fromDegrees(90f).toVector2F()
                    ),
                    false
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-8.1f, 0f),
                        direction = AngleF.fromDegrees(80f).toVector2F()
                    ),
                    true
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, 7.9f),
                        direction = AngleF.fromDegrees(0f).toVector2F()
                    ),
                    true
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, 8.1f),
                        direction = AngleF.fromDegrees(90f).toVector2F()
                    ),
                    false
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, 7.9f),
                        direction = AngleF.fromDegrees(-135f).toVector2F()
                    ),
                    true
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, -2f),
                        direction = AngleF.fromDegrees(40f).toVector2F()
                    ),
                    false
                ),
                Arguments.of(
                    annulus,
                    MutableRay(
                        origin = Vector2F(-4f, -2f),
                        direction = AngleF.fromDegrees(50f).toVector2F()
                    ),
                    true
                ),
            )
            val defaultAnnulusMutableRayArgs = mutableAnnulusMutableRayArgs
                .mapAnnulusesToDefaultAnnuluses()
            val mutableAnnulusDefaultRayArgs = mutableAnnulusMutableRayArgs
                .mapRaysToDefaultRays()
            val defaultAnnulusDefaultRayArgs = defaultAnnulusMutableRayArgs
                .mapRaysToDefaultRays()

            return listOf(
                mutableAnnulusMutableRayArgs,
                defaultAnnulusMutableRayArgs,
                mutableAnnulusDefaultRayArgs,
                defaultAnnulusDefaultRayArgs
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
        fun containsAnnulusArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableAnnulus(
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
                    MutableAnnulus(
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
                    MutableAnnulus(
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
                    MutableAnnulus(
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
                    MutableAnnulus(
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
        fun containsCircleArgs(): List<Arguments> {
            val mutableAnnulusArgs = listOf(
                Arguments.of(
                    MutableAnnulus(
                        center = Vector2F(-1f, 2f),
                        outerRadius = 4f,
                        orientation = ComplexF.ONE,
                        innerRadius = 2f
                    ),
                    MutableCircle(
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
                    MutableCircle(
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
        fun copyArgs(): List<Arguments> {
            val mutableAnnulusArgs = setArgs()
            val defaultAnnulusArgs = mutableAnnulusArgs.mapAnnulusesToDefaultAnnuluses()

            return listOf(
                mutableAnnulusArgs,
                defaultAnnulusArgs
            ).flatten()
        }

        @JvmStatic
        fun copyThrowsExceptionArgs(): List<Arguments> = constructorThrowsExceptionArgs()

        @JvmStatic
        fun copyDoesNotThrowExceptionArgs(): List<Arguments> = constructorArgs()

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