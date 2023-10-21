@file:Suppress("OVERRIDE_BY_INLINE", "PropertyName")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.withSign

class MutableSquare : Square, MutableTransformable {
    internal var _center: Vector2F
    internal var _rotation: ComplexF
    internal var _sideLength: Float
    internal var _pointA: Vector2F
    internal var _pointB: Vector2F
    internal var _pointC: Vector2F
    internal var _pointD: Vector2F

    constructor(center: Vector2F, rotation: ComplexF, sideLength: Float) {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = rotation
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _center = center
        _rotation = rotation
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    private constructor(
        center: Vector2F,
        rotation: ComplexF,
        sideLength: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        pointD: Vector2F
    ) {
        _center = center
        _rotation = rotation
        _sideLength = sideLength
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _pointD = pointD
    }

    internal constructor(regularPolygon: MutableRegularPolygon) {
        val points: Vector2FArray = regularPolygon._points
        _center = regularPolygon._center
        _rotation = regularPolygon._rotation
        _sideLength = regularPolygon._sideLength
        _pointA = points.elementAt(0)
        _pointB = points.elementAt(1)
        _pointC = points.elementAt(2)
        _pointD = points.elementAt(3)
    }

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

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

    override val width: Float
        get() = _sideLength

    override val height: Float
        get() = _sideLength

    override val area: Float
        get() = _sideLength * _sideLength

    override val perimeter: Float
        get() = 4f * _sideLength

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
        _rotation,
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
            _rotation,
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

    override fun rotatedBy(angle: AngleF) =
        MutableSquare(_center, _rotation * ComplexF.fromAngle(angle), _sideLength)

    override fun rotatedBy(rotation: ComplexF) =
        MutableSquare(_center, _rotation * rotation, _sideLength)

    override fun rotatedTo(angle: AngleF) =
        MutableSquare(_center, ComplexF.fromAngle(angle), _sideLength)

    override fun rotatedTo(rotation: ComplexF) =
        MutableSquare(_center, rotation, _sideLength)

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableSquare =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableSquare =
        TODO()

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableSquare =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): MutableSquare =
        TODO()

    override fun rotateBy(angle: AngleF) = rotateTo(_rotation * ComplexF.fromAngle(angle))

    override fun rotateBy(rotation: ComplexF) = rotateTo(_rotation * rotation)

    override fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    override fun rotateTo(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation
        val halfSideLength: Float = _sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _rotation = rotation
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun rotateAroundPointBy(point: Vector2F, angle: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        TODO()

    override fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF) =
        TODO()

    override fun scaledBy(factor: Float) =
        MutableSquare(_center, _rotation, _sideLength * factor)

    override fun scaleBy(factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _rotation
        val sideLength: Float = _sideLength * factor
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun transformedBy(offset: Vector2F, angle: AngleF) = MutableSquare(
        _center + offset, _rotation * ComplexF.fromAngle(angle), _sideLength
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) =
        MutableSquare(_center + offset, _rotation * rotation, _sideLength)

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float) = MutableSquare(
        _center + offset,
        _rotation * ComplexF.fromAngle(angle),
        _sideLength * factor
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        MutableSquare(
            _center + offset,
            _rotation * rotation,
            _sideLength * factor
        )

    override fun transformedTo(position: Vector2F, angle: AngleF) =
        MutableSquare(position, ComplexF.fromAngle(angle), _sideLength)

    override fun transformedTo(position: Vector2F, rotation: ComplexF) =
        MutableSquare(position, rotation, _sideLength)

    override fun transformBy(offset: Vector2F, angle: AngleF) =
        transformTo(_center + offset, _rotation * ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) =
        transformTo(_center + offset, _rotation * rotation)

    override fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val cX: Float = _center.x + offset.x
        val cY: Float = _center.y + offset.y
        val r0 = _rotation.real
        val i0 = _rotation.imaginary
        val r1 = rotation.real
        val i1 = rotation.imaginary
        val rotR: Float = r0 * r1 - i0 * i1
        val rotI: Float = i0 * r1 + r0 * i1
        val sideLength: Float = _sideLength * factor
        val halfSideLength: Float = sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _center = Vector2F(cX, cY)
        _rotation = ComplexF(rotR, rotI)
        _sideLength = sideLength
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    override fun transformTo(position: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = rotation
        val halfSideLength: Float = _sideLength * 0.5f
        val addendA: Float = halfSideLength * (rotR + rotI)
        val addendB: Float = halfSideLength * (rotR - rotI)
        _center = position
        _rotation = rotation
        _pointA = Vector2F(cX + addendB, cY + addendA)
        _pointB = Vector2F(cX - addendA, cY + addendB)
        _pointC = Vector2F(cX - addendB, cY - addendA)
        _pointD = Vector2F(cX + addendA, cY - addendB)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val halfSideLength: Float = _sideLength * 0.5f
        val center: Vector2F = _center
        val rotation: ComplexF = _rotation
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p2 = ComplexF(
            if (p1X.absoluteValue > halfSideLength) halfSideLength.withSign(p1X) else p1X,
            if (p1Y.absoluteValue > halfSideLength) halfSideLength.withSign(p1Y) else p1Y
        )
        return center + (rotation * p2).toVector2F()
    }

    override operator fun contains(point: Vector2F): Boolean {
        val halfSideLength: Float = _sideLength * 0.5f
        val center: Vector2F = _center
        val p1 = ComplexF.conjugate(_rotation) *
                ComplexF(point.x - center.x, point.y - center.y)

        return (p1.real.absoluteValue <= halfSideLength) and
                (p1.imaginary.absoluteValue <= halfSideLength)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun copy(center: Vector2F, rotation: ComplexF, sideLength: Float) =
        MutableSquare(center, rotation, sideLength)

    override fun equals(other: Any?): Boolean = other is Square &&
            _center == other.center &&
            _rotation == other.rotation &&
            _sideLength == other.sideLength

    fun equals(other: MutableSquare): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _sideLength == other._sideLength

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val sideLengthHash: Int = _sideLength.hashCode()

        return centerHash * 961 + rotationHash * 31 + sideLengthHash
    }

    override fun toString() =
        StringBuilder("Square(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", sideLength=").append(_sideLength).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

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