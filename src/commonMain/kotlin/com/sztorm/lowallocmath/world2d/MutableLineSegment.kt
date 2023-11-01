package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import kotlin.math.abs

class MutableLineSegment(pointA: Vector2F, pointB: Vector2F) : LineSegment, MutableTransformable {
    private var _pointA: Vector2F = pointA
    private var _pointB: Vector2F = pointB

    override val pointA: Vector2F
        get() = _pointA

    override val pointB: Vector2F
        get() = _pointB

    override val center: Vector2F
        get() = (_pointA + _pointB) * 0.5f

    override val length: Float
        get() = _pointA.distanceTo(_pointB)

    override val position: Vector2F
        get() = (_pointA + _pointB) * 0.5f

    override val rotation: ComplexF
        get() = (_pointA - _pointB).normalized.toComplexF()

    override fun movedBy(offset: Vector2F): MutableLineSegment = TODO()

    override fun movedTo(position: Vector2F): MutableLineSegment = TODO()

    override fun moveBy(offset: Vector2F) = TODO()

    override fun moveTo(position: Vector2F) = TODO()

    override fun rotatedBy(angle: AngleF): MutableLineSegment =
        rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): MutableLineSegment = TODO()

    override fun rotatedTo(angle: AngleF): MutableLineSegment =
        rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): MutableLineSegment = TODO()

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableLineSegment =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableLineSegment =
        TODO()

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableLineSegment =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): MutableLineSegment =
        TODO()

    override fun rotateBy(angle: AngleF) = rotateBy(ComplexF.fromAngle(angle))

    override fun rotateBy(rotation: ComplexF) = TODO()

    override fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    override fun rotateTo(rotation: ComplexF) = TODO()

    override fun rotateAroundPointBy(point: Vector2F, angle: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) = TODO()

    override fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF) = TODO()

    override fun scaledBy(factor: Float): MutableLineSegment = TODO()

    override fun scaleBy(factor: Float) = TODO()

    override fun transformedBy(offset: Vector2F, angle: AngleF): MutableLineSegment =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableLineSegment = TODO()

    override fun transformedBy(
        offset: Vector2F, angle: AngleF, factor: Float
    ): MutableLineSegment = transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableLineSegment = TODO()

    override fun transformedTo(position: Vector2F, angle: AngleF): MutableLineSegment =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): MutableLineSegment = TODO()

    override fun transformBy(offset: Vector2F, angle: AngleF) =
        transformBy(offset, ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) = TODO()

    override fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) = TODO()

    override fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    override fun transformTo(position: Vector2F, rotation: ComplexF) = TODO()

    override fun closestPointTo(point: Vector2F): Vector2F {
        val ab: Vector2F = _pointB - _pointA
        val epsilon = 0.00001f

        if ((abs(ab.x) <= epsilon) and (abs(ab.y) <= epsilon)) {
            return _pointA
        }
        val ap: Vector2F = point - _pointA
        val t: Float = (ab dot ap) / (ab dot ab)
        val tClamped: Float = when {
            t < 0f -> 0f
            t > 1f -> 1f
            else -> t
        }
        return _pointA + ab * tClamped
    }

    override operator fun contains(point: Vector2F): Boolean {
        val ab: Vector2F = _pointB - _pointA
        val epsilon = 0.00001f

        if ((abs(ab.x) <= epsilon) and (abs(ab.y) <= epsilon)) {
            return _pointA.isApproximately(point)
        }
        val ap: Vector2F = point - _pointA
        val t: Float = (ab dot ap) / (ab dot ab)

        return if ((t < 0f) or (t > 1f)) false
        else {
            val closestPoint: Vector2F = _pointA + ab * t

            return closestPoint.isApproximately(point)
        }
    }

    override fun pointIterator(): Vector2FIterator = PointIterator(this, index = 0)

    override fun copy(pointA: Vector2F, pointB: Vector2F) = MutableLineSegment(pointA, pointB)

    override fun equals(other: Any?): Boolean = other is LineSegment &&
            _pointA == other.pointA &&
            _pointB == other.pointB

    fun equals(other: MutableLineSegment): Boolean =
        _pointA == other._pointA && _pointB == other._pointB

    override fun hashCode(): Int {
        val pointAHash: Int = _pointA.hashCode()
        val pointBHash: Int = _pointB.hashCode()

        return pointAHash * 31 + pointBHash
    }

    override fun toString() =
        StringBuilder("LineSegment(pointA=").append(_pointA)
            .append(", pointB=").append(_pointB).append(")")
            .toString()

    override operator fun component1(): Vector2F = _pointA

    override operator fun component2(): Vector2F = _pointB

    private class PointIterator(
        private val lineSegment: MutableLineSegment,
        private var index: Int
    ) : Vector2FIterator() {
        override fun hasNext(): Boolean = index < 2

        override fun nextVector2F(): Vector2F = when (index++) {
            0 -> lineSegment._pointA
            1 -> lineSegment._pointB
            else -> throw NoSuchElementException("${index - 1}")
        }
    }
}