package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

class MutableRay(origin: Vector2F, direction: Vector2F) : Ray, MutableTransformable {
    private var _origin: Vector2F = origin
    private var _direction: Vector2F = direction

    override val origin: Vector2F
        get() = _origin

    override val direction: Vector2F
        get() = _direction

    override val position: Vector2F
        get() = _origin

    override val rotation: ComplexF
        get() = _direction.toComplexF()

    override fun movedBy(offset: Vector2F): MutableRay = TODO()

    override fun movedTo(position: Vector2F): MutableRay = TODO()

    override fun moveBy(offset: Vector2F) = TODO()

    override fun moveTo(position: Vector2F) = TODO()

    override fun rotatedBy(angle: AngleF): MutableRay =
        rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): MutableRay = TODO()

    override fun rotatedTo(angle: AngleF): MutableRay =
        rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): MutableRay = TODO()

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableRay =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableRay =
        TODO()

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableRay =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): MutableRay =
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

    override fun scaledBy(factor: Float): MutableRay = TODO()

    override fun scaleBy(factor: Float) = TODO()

    override fun transformedBy(offset: Vector2F, angle: AngleF): MutableRay =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableRay = TODO()

    override fun transformedBy(
        offset: Vector2F, angle: AngleF, factor: Float
    ): MutableRay = transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): MutableRay = TODO()

    override fun transformedTo(position: Vector2F, angle: AngleF): MutableRay =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): MutableRay = TODO()

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
        val op: Vector2F = point - _origin
        val t: Float = op dot _direction

        return if (t <= 0f) _origin
        else _origin + _direction * t
    }

    override operator fun contains(point: Vector2F): Boolean {
        val op: Vector2F = point - _origin
        val t: Float = op dot _direction
        val closestPoint: Vector2F =
            if (t <= 0f) _origin
            else _origin + _direction * t

        return closestPoint.isApproximately(point)
    }

    override fun copy(origin: Vector2F, direction: Vector2F) =
        MutableRay(origin, direction)

    override fun equals(other: Any?): Boolean = other is Ray &&
            _origin == other.origin &&
            _direction == other.direction

    fun equals(other: MutableRay): Boolean =
        _origin == other._origin && _direction == other._direction

    override fun hashCode(): Int {
        val originHash: Int = _origin.hashCode()
        val directionHash: Int = _direction.hashCode()

        return originHash * 31 + directionHash
    }

    override fun toString() =
        StringBuilder("Ray(origin=").append(_origin)
            .append(", direction=").append(_direction).append(")")
            .toString()

    override operator fun component1(): Vector2F = _origin

    override operator fun component2(): Vector2F = _direction
}