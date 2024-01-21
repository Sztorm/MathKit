@file:Suppress("OVERRIDE_BY_INLINE", "PropertyName")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.*

class MutableSquare : Square, MutableTransformable {
    internal var _center: Vector2F
    internal var _orientation: ComplexF
    internal var _sideLength: Float
    internal var _pointA: Vector2F
    internal var _pointB: Vector2F
    internal var _pointC: Vector2F
    internal var _pointD: Vector2F

    constructor(center: Vector2F, orientation: ComplexF, sideLength: Float) {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = orientation
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        sideLength: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        pointD: Vector2F
    ) {
        _center = center
        _orientation = orientation
        _sideLength = sideLength
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _pointD = pointD
    }

    internal constructor(regularPolygon: MutableRegularPolygon) {
        val points: Vector2FArray = regularPolygon._points
        _center = regularPolygon._center
        _orientation = regularPolygon._orientation
        _sideLength = regularPolygon._sideLength
        _pointA = points.elementAt(0)
        _pointB = points.elementAt(1)
        _pointC = points.elementAt(2)
        _pointD = points.elementAt(3)
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val sideLength: Float
        get() = _sideLength

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val pointD: Vector2F
        get() = _pointD

    override val area: Float
        get() = _sideLength * _sideLength

    override val perimeter: Float
        get() = 4f * _sideLength

    override val width: Float
        get() = _sideLength

    override val height: Float
        get() = _sideLength

    override inline val sideCount: Int
        get() = 4

    override inline val interiorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override val inradius: Float
        get() = 0.5f * _sideLength

    override val circumradius: Float
        get() = 0.7071068f * _sideLength

    override val position: Vector2F
        get() = _center

    override fun movedBy(offset: Vector2F) = MutableSquare(
        _center + offset,
        _orientation,
        _sideLength,
        _pointA + offset,
        _pointB + offset,
        _pointC + offset,
        _pointD + offset,
    )

    override fun movedTo(position: Vector2F): MutableSquare {
        val offset: Vector2F = position - _center

        return MutableSquare(
            position,
            _orientation,
            _sideLength,
            _pointA + offset,
            _pointB + offset,
            _pointC + offset,
            _pointD + offset,
        )
    }

    override fun moveBy(offset: Vector2F) {
        _center += offset
        _pointA += offset
        _pointB += offset
        _pointC += offset
        _pointD += offset
    }

    override fun moveTo(position: Vector2F) {
        val offset: Vector2F = position - _center
        _center = position
        _pointA += offset
        _pointB += offset
        _pointC += offset
        _pointD += offset
    }

    override fun rotatedBy(rotation: AngleF) =
        MutableSquare(_center, _orientation * ComplexF.fromAngle(rotation), _sideLength)

    override fun rotatedBy(rotation: ComplexF) =
        MutableSquare(_center, _orientation * rotation, _sideLength)

    override fun rotatedTo(orientation: AngleF) =
        MutableSquare(_center, ComplexF.fromAngle(orientation), _sideLength)

    override fun rotatedTo(orientation: ComplexF) =
        MutableSquare(_center, orientation, _sideLength)

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableSquare =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableSquare {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val addendA: Float = halfSideLength * (targetRotR + targetRotI)
        val addendB: Float = halfSideLength * (targetRotR - targetRotI)

        return MutableSquare(
            center = Vector2F(targetCenterX, targetCenterY),
            orientation = ComplexF(targetRotR, targetRotI),
            _sideLength,
            pointA = Vector2F(targetCenterX + addendB, targetCenterY + addendA),
            pointB = Vector2F(targetCenterX - addendA, targetCenterY + addendB),
            pointC = Vector2F(targetCenterX - addendB, targetCenterY - addendA),
            pointD = Vector2F(targetCenterX + addendA, targetCenterY - addendB),
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableSquare =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableSquare {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val halfSideLength: Float = _sideLength * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendA: Float = halfSideLength * (targetRotR + targetRotI)
            val addendB: Float = halfSideLength * (targetRotR - targetRotI)

            return MutableSquare(
                center = Vector2F(targetCenterX, targetCenterY),
                orientation = targetRot,
                _sideLength,
                pointA = Vector2F(targetCenterX + addendB, targetCenterY + addendA),
                pointB = Vector2F(targetCenterX - addendA, targetCenterY + addendB),
                pointC = Vector2F(targetCenterX - addendB, targetCenterY - addendA),
                pointD = Vector2F(targetCenterX + addendA, targetCenterY - addendB),
            )
        } else {
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)

            return MutableSquare(
                _center,
                orientation,
                _sideLength,
                pointA = Vector2F(cX + addendB, cY + addendA),
                pointB = Vector2F(cX - addendA, cY + addendB),
                pointC = Vector2F(cX - addendB, cY - addendA),
                pointD = Vector2F(cX + addendA, cY - addendB),
            )
        }
    }

    override fun rotateBy(rotation: AngleF) =
        rotateTo(_orientation * ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) = rotateTo(_orientation * rotation)

    override fun rotateTo(orientation: AngleF) = rotateTo(ComplexF.fromAngle(orientation))

    override fun rotateTo(orientation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _orientation = orientation
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val addendA: Float = halfSideLength * (targetRotR + targetRotI)
        val addendB: Float = halfSideLength * (targetRotR - targetRotI)
        _center = Vector2F(targetCenterX, targetCenterY)
        _orientation = ComplexF(targetRotR, targetRotI)
        _pointA = Vector2F(targetCenterX + addendB, targetCenterY + addendA)
        _pointB = Vector2F(targetCenterX - addendA, targetCenterY + addendB)
        _pointC = Vector2F(targetCenterX - addendB, targetCenterY - addendA)
        _pointD = Vector2F(targetCenterX + addendA, targetCenterY - addendB)
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val halfSideLength: Float = _sideLength * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendA: Float = halfSideLength * (targetRotR + targetRotI)
            val addendB: Float = halfSideLength * (targetRotR - targetRotI)
            _center = Vector2F(targetCenterX, targetCenterY)
            _orientation = targetRot
            _pointA = Vector2F(targetCenterX + addendB, targetCenterY + addendA)
            _pointB = Vector2F(targetCenterX - addendA, targetCenterY + addendB)
            _pointC = Vector2F(targetCenterX - addendB, targetCenterY - addendA)
            _pointD = Vector2F(targetCenterX + addendA, targetCenterY - addendB)
        } else {
            val addendA: Float = halfSideLength * (rotR + rotI)
            val addendB: Float = halfSideLength * (rotR - rotI)
            _orientation = orientation
            _pointA = Vector2F(cX + addendB, cY + addendA)
            _pointB = Vector2F(cX - addendA, cY + addendB)
            _pointC = Vector2F(cX - addendB, cY - addendA)
            _pointD = Vector2F(cX + addendA, cY - addendB)
        }
    }

    override fun scaledBy(factor: Float) = MutableSquare(
        _center,
        orientation = _orientation * 1f.withSign(factor),
        sideLength = _sideLength * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): MutableSquare {
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)

        return MutableSquare(
            center = Vector2F(cX, cY),
            orientation = ComplexF(rotR, rotI),
            sideLength,
            pointA = Vector2F(cX + addendB, cY + addendA),
            pointB = Vector2F(cX - addendA, cY + addendB),
            pointC = Vector2F(cX - addendB, cY - addendA),
            pointD = Vector2F(cX + addendA, cY - addendB)
        )
    }

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val f: Float = 1f - factor
        val cX: Float = _center.x * factor + point.x * f
        val cY: Float = _center.y * factor + point.y * f
        val (rotR: Float, rotI: Float) = _orientation * 1f.withSign(factor)
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF) = MutableSquare(
        _center + offset, _orientation * ComplexF.fromAngle(rotation), _sideLength
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) =
        MutableSquare(_center + offset, _orientation * rotation, _sideLength)

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float) = MutableSquare(
        _center + offset,
        _orientation * ComplexF.fromAngle(rotation) * 1f.withSign(factor),
        _sideLength * factor.absoluteValue
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        MutableSquare(
            _center + offset,
            _orientation * rotation * 1f.withSign(factor),
            _sideLength * factor.absoluteValue
        )

    override fun transformedTo(position: Vector2F, orientation: AngleF) =
        MutableSquare(position, ComplexF.fromAngle(orientation), _sideLength)

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        MutableSquare(position, orientation, _sideLength)

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformTo(_center + offset, _orientation * ComplexF.fromAngle(rotation))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) =
        transformTo(_center + offset, _orientation * rotation)

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val cX: Float = _center.x + offset.x
        val cY: Float = _center.y + offset.y
        val r0 = _orientation.real
        val i0 = _orientation.imaginary
        val r1 = rotation.real
        val i1 = rotation.imaginary
        val factorSign: Float = 1f.withSign(factor)
        val rotR: Float = (r0 * r1 - i0 * i1) * factorSign
        val rotI: Float = (i0 * r1 + r0 * i1) * factorSign
        val sideLength: Float = _sideLength * factor.absoluteValue
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _center = Vector2F(cX, cY)
        _orientation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _center = position
        _orientation = orientation
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX
        val p2X: Float =
            if (p1X.absoluteValue > halfSideLength) halfSideLength.withSign(p1X) else p1X
        val p2Y: Float =
            if (p1Y.absoluteValue > halfSideLength) halfSideLength.withSign(p1Y) else p1Y

        return Vector2F(rotR * p2X - rotI * p2Y + cX, rotI * p2X + rotR * p2Y + cY)
    }

    override operator fun contains(point: Vector2F): Boolean {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _orientation
        val halfSideLength: Float = _sideLength * 0.5f
        val (pX: Float, pY: Float) = point
        val cpDiffX: Float = pX - cX
        val cpDiffY: Float = pY - cY
        val p1X: Float = rotR * cpDiffX + rotI * cpDiffY
        val p1Y: Float = rotR * cpDiffY - rotI * cpDiffX

        return (p1X.absoluteValue <= halfSideLength) and (p1Y.absoluteValue <= halfSideLength)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun copy(center: Vector2F, orientation: ComplexF, sideLength: Float) =
        MutableSquare(center, orientation, sideLength)

    override fun equals(other: Any?): Boolean = other is Square &&
            _center == other.center &&
            _orientation == other.orientation &&
            _sideLength == other.sideLength

    fun equals(other: MutableSquare): Boolean =
        _center == other._center &&
                _orientation == other._orientation &&
                _sideLength == other._sideLength

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val sideLengthHash: Int = _sideLength.hashCode()

        return centerHash * 961 + orientationHash * 31 + sideLengthHash
    }

    override fun toString() =
        StringBuilder("Square(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", sideLength=").append(_sideLength).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _sideLength

    private class PointIterator(
        private val square: MutableSquare,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> square._pointA
            1 -> square._pointB
            2 -> square._pointC
            3 -> square._pointD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}