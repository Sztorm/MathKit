package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.sqrt

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

    override fun movedBy(offset: Vector2F) = MutableRay(_origin + offset, _direction)

    override fun movedTo(position: Vector2F) = MutableRay(position, _direction)

    override fun moveBy(offset: Vector2F) {
        _origin += offset
    }

    override fun moveTo(position: Vector2F) {
        _origin = position
    }

    override fun rotatedBy(angle: AngleF): MutableRay =
        rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): MutableRay {
        val dirX: Float = _direction.x
        val dirY: Float = _direction.y
        val rotR: Float = rotation.real
        val rotI: Float = rotation.imaginary

        return MutableRay(
            _origin,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun rotatedTo(angle: AngleF): MutableRay =
        MutableRay(_origin, direction = ComplexF.fromAngle(angle).toVector2F())

    override fun rotatedTo(rotation: ComplexF): MutableRay =
        MutableRay(_origin, direction = rotation.toVector2F())

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableRay =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableRay {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _origin
        val (startRotR: Float, startRotI: Float) = _direction
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return MutableRay(
            origin = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX, cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            direction = Vector2F(
                startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
            )
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableRay =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): MutableRay {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _origin
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val startRotR: Float = _direction.x
            val startRotI: Float = _direction.y
            val r0: Float = pointRotR * startRotR + pointRotI * startRotI
            val i0: Float = pointRotR * startRotI - pointRotI * startRotR

            return MutableRay(
                origin = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                direction = Vector2F(r0 * rotR - i0 * rotI, i0 * rotR + r0 * rotI)
            )
        } else {
            return MutableRay(_origin, direction = rotation.toVector2F())
        }
    }

    override fun rotateBy(angle: AngleF) = rotateBy(ComplexF.fromAngle(angle))

    override fun rotateBy(rotation: ComplexF) {
        val dirX: Float = _direction.x
        val dirY: Float = _direction.y
        val rotR: Float = rotation.real
        val rotI: Float = rotation.imaginary

        _direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
    }

    override fun rotateTo(angle: AngleF) {
        _direction = ComplexF.fromAngle(angle).toVector2F()
    }

    override fun rotateTo(rotation: ComplexF) {
        _direction = rotation.toVector2F()
    }

    override fun rotateAroundPointBy(point: Vector2F, angle: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _origin
        val (startRotR: Float, startRotI: Float) = _direction
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        _origin = Vector2F(
            cpDiffX * rotR - cpDiffY * rotI + pX, cpDiffY * rotR + cpDiffX * rotI + pY
        )
        _direction = Vector2F(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _origin
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val startRotR: Float = _direction.x
            val startRotI: Float = _direction.y
            val r0: Float = pointRotR * startRotR + pointRotI * startRotI
            val i0: Float = pointRotR * startRotI - pointRotI * startRotR

            _origin = Vector2F(
                rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
            )
            _direction = Vector2F(r0 * rotR - i0 * rotI, i0 * rotR + r0 * rotI)
        } else {
            _direction = rotation.toVector2F()
        }
    }

    override fun scaledBy(factor: Float) = MutableRay(_origin, _direction)

    override fun scaleBy(factor: Float) {}

    override fun transformedBy(offset: Vector2F, angle: AngleF): MutableRay =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableRay {
        val dirX: Float = _direction.x
        val dirY: Float = _direction.y
        val rotR: Float = rotation.real
        val rotI: Float = rotation.imaginary

        return MutableRay(
            origin = _origin + offset,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): MutableRay =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): MutableRay =
        transformedBy(offset, rotation)

    override fun transformedTo(position: Vector2F, angle: AngleF): MutableRay =
        MutableRay(position, direction = ComplexF.fromAngle(angle).toVector2F())

    override fun transformedTo(position: Vector2F, rotation: ComplexF) =
        MutableRay(position, direction = rotation.toVector2F())

    override fun transformBy(offset: Vector2F, angle: AngleF) =
        transformBy(offset, ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        val dirX: Float = _direction.x
        val dirY: Float = _direction.y
        val rotR: Float = rotation.real
        val rotI: Float = rotation.imaginary

        _origin += offset
        _direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
    }

    override fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        transformBy(offset, rotation)

    override fun transformTo(position: Vector2F, angle: AngleF) {
        _origin = position
        _direction = ComplexF.fromAngle(angle).toVector2F()
    }

    override fun transformTo(position: Vector2F, rotation: ComplexF) {
        _origin = position
        _direction = rotation.toVector2F()
    }

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