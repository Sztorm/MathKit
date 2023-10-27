package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableRectangle : Rectangle, MutableTransformable {
    private var _center: Vector2F
    private var _rotation: ComplexF
    private var _width: Float
    private var _height: Float
    private var _pointA: Vector2F
    private var _pointB: Vector2F
    private var _pointC: Vector2F
    private var _pointD: Vector2F

    constructor(center: Vector2F, rotation: ComplexF, width: Float, height: Float) {
        val (cX: Float, cY: Float) = center
        val (rotR: Float, rotI: Float) = rotation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY2: Float = rotI * halfWidth
        val addendSumX1X2: Float = addendX1 + addendX2
        val addendDiffX1X2: Float = addendX1 - addendX2
        val addendSumY1Y2: Float = addendY1 + addendY2
        val addendDiffY1Y2: Float = addendY1 - addendY2
        _center = center
        _rotation = rotation
        _width = width
        _height = height
        _pointA = Vector2F(cX + addendDiffX1X2, cY + addendSumY1Y2)
        _pointB = Vector2F(cX - addendSumX1X2, cY + addendDiffY1Y2)
        _pointC = Vector2F(cX - addendDiffX1X2, cY - addendSumY1Y2)
        _pointD = Vector2F(cX + addendSumX1X2, cY - addendDiffY1Y2)
    }

    private constructor(
        center: Vector2F,
        rotation: ComplexF,
        width: Float,
        height: Float,
        pointA: Vector2F,
        pointB: Vector2F,
        pointC: Vector2F,
        pointD: Vector2F
    ) {
        _center = center
        _rotation = rotation
        _width = width
        _height = height
        _pointA = pointA
        _pointB = pointB
        _pointC = pointC
        _pointD = pointD
    }

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val width: Float
        get() = _width

    override val height: Float
        get() = _height

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val pointC: Vector2F
        get() = _pointC

    override val pointD: Vector2F
        get() = _pointD

    override val area: Float
        get() = _width * _height

    override val perimeter: Float
        get() = 2f * (_width + _height)

    override val position: Vector2F
        get() = _center

    override fun movedBy(offset: Vector2F) = MutableRectangle(
        _center + offset,
        _rotation,
        _width,
        _height,
        _pointA + offset,
        _pointB + offset,
        _pointC + offset,
        _pointD + offset,
    )

    override fun movedTo(position: Vector2F): MutableRectangle {
        val offset: Vector2F = position - _center

        return MutableRectangle(
            position,
            _rotation,
            _width,
            _height,
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

    override fun rotatedBy(angle: AngleF): MutableRectangle =
        MutableRectangle(_center, _rotation * ComplexF.fromAngle(angle), _width, _height)

    override fun rotatedBy(rotation: ComplexF) =
        MutableRectangle(_center, _rotation * rotation, _width, _height)

    override fun rotatedTo(angle: AngleF): MutableRectangle =
        MutableRectangle(_center, ComplexF.fromAngle(angle), _width, _height)

    override fun rotatedTo(rotation: ComplexF) =
        MutableRectangle(_center, rotation, _width, _height)

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableRectangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val addendX1: Float = targetRotR * halfWidth
        val addendX1A: Float = targetCenterX + addendX1
        val addendX1B: Float = targetCenterX - addendX1
        val addendX2: Float = targetRotI * halfHeight
        val addendY1: Float = targetRotR * halfHeight
        val addendY1A: Float = targetCenterY + addendY1
        val addendY1B: Float = targetCenterY - addendY1
        val addendY2: Float = targetRotI * halfWidth

        return MutableRectangle(
            center = Vector2F(targetCenterX, targetCenterY),
            rotation = ComplexF(targetRotR, targetRotI),
            _width,
            _height,
            pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2),
            pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2),
            pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2),
            pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableRectangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): MutableRectangle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot: ComplexF = ComplexF(pointRotR, -pointRotI) * _rotation * rotation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendX1: Float = targetRotR * halfWidth
            val addendX1A: Float = targetCenterX + addendX1
            val addendX1B: Float = targetCenterX - addendX1
            val addendX2: Float = targetRotI * halfHeight
            val addendY1: Float = targetRotR * halfHeight
            val addendY1A: Float = targetCenterY + addendY1
            val addendY1B: Float = targetCenterY - addendY1
            val addendY2: Float = targetRotI * halfWidth

            return MutableRectangle(
                center = Vector2F(targetCenterX, targetCenterY),
                rotation = targetRot,
                _width,
                _height,
                pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2),
                pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2),
                pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2),
                pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2),
            )
        } else {
            val addendX1: Float = rotR * halfWidth
            val addendX1A: Float = cX + addendX1
            val addendX1B: Float = cX - addendX1
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY1A: Float = cY + addendY1
            val addendY1B: Float = cY - addendY1
            val addendY2: Float = rotI * halfWidth

            return MutableRectangle(
                _center,
                rotation,
                _width,
                _height,
                pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2),
                pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2),
                pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2),
                pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2),
            )
        }
    }

    override fun rotateBy(angle: AngleF) = rotateTo(_rotation * ComplexF.fromAngle(angle))

    override fun rotateBy(rotation: ComplexF) = rotateTo(_rotation * rotation)

    override fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    override fun rotateTo(rotation: ComplexF) {
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = rotation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _rotation = rotation
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun rotateAroundPointBy(point: Vector2F, angle: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val targetCenterX: Float = cpDiffX * rotR - cpDiffY * rotI + pX
        val targetCenterY: Float = cpDiffY * rotR + cpDiffX * rotI + pY
        val targetRotR: Float = startRotR * rotR - startRotI * rotI
        val targetRotI: Float = startRotI * rotR + startRotR * rotI
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val addendX1: Float = targetRotR * halfWidth
        val addendX1A: Float = targetCenterX + addendX1
        val addendX1B: Float = targetCenterX - addendX1
        val addendX2: Float = targetRotI * halfHeight
        val addendY1: Float = targetRotR * halfHeight
        val addendY1A: Float = targetCenterY + addendY1
        val addendY1B: Float = targetCenterY - addendY1
        val addendY2: Float = targetRotI * halfWidth
        _center = Vector2F(targetCenterX, targetCenterY)
        _rotation = ComplexF(targetRotR, targetRotI)
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val targetRot: ComplexF = ComplexF(pointRotR, -pointRotI) * _rotation * rotation
            val (targetRotR: Float, targetRotI: Float) = targetRot
            val targetCenterX: Float = rotR * centerToPointDist + pX
            val targetCenterY: Float = rotI * centerToPointDist + pY
            val addendX1: Float = targetRotR * halfWidth
            val addendX1A: Float = targetCenterX + addendX1
            val addendX1B: Float = targetCenterX - addendX1
            val addendX2: Float = targetRotI * halfHeight
            val addendY1: Float = targetRotR * halfHeight
            val addendY1A: Float = targetCenterY + addendY1
            val addendY1B: Float = targetCenterY - addendY1
            val addendY2: Float = targetRotI * halfWidth
            _center = Vector2F(targetCenterX, targetCenterY)
            _rotation = targetRot
            _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
            _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
            _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
            _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
        } else {
            val addendX1: Float = rotR * halfWidth
            val addendX1A: Float = cX + addendX1
            val addendX1B: Float = cX - addendX1
            val addendX2: Float = rotI * halfHeight
            val addendY1: Float = rotR * halfHeight
            val addendY1A: Float = cY + addendY1
            val addendY1B: Float = cY - addendY1
            val addendY2: Float = rotI * halfWidth
            _rotation = rotation
            _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
            _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
            _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
            _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
        }
    }

    override fun scaledBy(factor: Float) =
        MutableRectangle(_center, _rotation, _width * factor, _height * factor)

    override fun scaleBy(factor: Float) {
        val width: Float = _width * factor
        val height: Float = _height * factor
        val (cX: Float, cY: Float) = _center
        val (rotR: Float, rotI: Float) = _rotation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _width = width
        _height = height
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun transformedBy(offset: Vector2F, angle: AngleF) = MutableRectangle(
        _center + offset, _rotation * ComplexF.fromAngle(angle), _width, _height
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) =
        MutableRectangle(_center + offset, _rotation * rotation, _width, _height)

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float) = MutableRectangle(
        _center + offset,
        _rotation * ComplexF.fromAngle(angle),
        _width * factor,
        _height * factor
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        MutableRectangle(
            _center + offset,
            _rotation * rotation,
            _width * factor,
            _height * factor
        )

    override fun transformedTo(position: Vector2F, angle: AngleF) =
        MutableRectangle(position, ComplexF.fromAngle(angle), _width, _height)

    override fun transformedTo(position: Vector2F, rotation: ComplexF) =
        MutableRectangle(position, rotation, _width, _height)

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
        val width = _width * factor
        val height = _height * factor
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _center = Vector2F(cX, cY)
        _rotation = ComplexF(rotR, rotI)
        _width = width
        _height = height
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    override fun transformTo(position: Vector2F, rotation: ComplexF) {
        val (cX: Float, cY: Float) = position
        val (rotR: Float, rotI: Float) = rotation
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val addendX1: Float = rotR * halfWidth
        val addendX1A: Float = cX + addendX1
        val addendX1B: Float = cX - addendX1
        val addendX2: Float = rotI * halfHeight
        val addendY1: Float = rotR * halfHeight
        val addendY1A: Float = cY + addendY1
        val addendY1B: Float = cY - addendY1
        val addendY2: Float = rotI * halfWidth
        _center = position
        _rotation = rotation
        _pointA = Vector2F(addendX1A - addendX2, addendY1A + addendY2)
        _pointB = Vector2F(addendX1B - addendX2, addendY1A - addendY2)
        _pointC = Vector2F(addendX1B + addendX2, addendY1B - addendY2)
        _pointD = Vector2F(addendX1A + addendX2, addendY1B + addendY2)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val center: Vector2F = _center
        val rotation: ComplexF = _rotation
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p2 = ComplexF(
            if (abs(p1X) > halfWidth) halfWidth.withSign(p1X) else p1X,
            if (abs(p1Y) > halfHeight) halfHeight.withSign(p1Y) else p1Y
        )
        return center + (rotation * p2).toVector2F()
    }

    override operator fun contains(point: Vector2F): Boolean {
        val halfWidth: Float = _width * 0.5f
        val halfHeight: Float = _height * 0.5f
        val center: Vector2F = _center
        val p1 = ComplexF.conjugate(_rotation) *
                ComplexF(point.x - center.x, point.y - center.y)

        return (abs(p1.real) <= halfWidth) and (abs(p1.imaginary) <= halfHeight)
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun copy(center: Vector2F, rotation: ComplexF, width: Float, height: Float) =
        MutableRectangle(center, rotation, width, height)

    override fun equals(other: Any?): Boolean = other is Rectangle &&
            _center == other.center &&
            _rotation == other.rotation &&
            _width == other.width &&
            _height == other.height

    fun equals(other: MutableRectangle): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _width == other._width &&
                _height == other._height

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val widthHash: Int = _width.hashCode()
        val heightHash: Int = _height.hashCode()

        return centerHash * 29791 + rotationHash * 961 + widthHash * 31 + heightHash
    }

    override fun toString() =
        StringBuilder("Rectangle(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", width=").append(_width)
            .append(", height=").append(_height).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _width

    override operator fun component4(): Float = _height

    private class PointIterator(
        private val rectangle: MutableRectangle,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 4

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> rectangle._pointA
            1 -> rectangle._pointB
            2 -> rectangle._pointC
            3 -> rectangle._pointD
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}