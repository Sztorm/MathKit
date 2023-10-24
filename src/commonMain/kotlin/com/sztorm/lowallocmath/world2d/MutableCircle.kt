package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.sqrt

class MutableCircle(
    center: Vector2F, rotation: ComplexF, radius: Float
) : Circle, MutableTransformable {
    private var _center: Vector2F = center
    private var _rotation: ComplexF = rotation
    private var _radius: Float = radius

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val radius: Float
        get() = _radius

    override val area: Float
        get() = PI.toFloat() * _radius * _radius

    override val perimeter: Float
        get() = 2f * PI.toFloat() * _radius

    override val diameter: Float
        get() = 2f * _radius

    override val position: Vector2F
        get() = _center

    override fun movedBy(offset: Vector2F) =
        MutableCircle(_center + offset, _rotation, _radius)

    override fun movedTo(position: Vector2F) =
        MutableCircle(position, _rotation, _radius)

    override fun moveBy(offset: Vector2F) {
        _center += offset
    }

    override fun moveTo(position: Vector2F) {
        _center = position
    }

    override fun rotatedBy(angle: AngleF) =
        MutableCircle(_center, _rotation * ComplexF.fromAngle(angle), _radius)

    override fun rotatedBy(rotation: ComplexF) =
        MutableCircle(_center, _rotation * rotation, _radius)

    override fun rotatedTo(angle: AngleF) =
        MutableCircle(_center, ComplexF.fromAngle(angle), _radius)

    override fun rotatedTo(rotation: ComplexF) = MutableCircle(_center, rotation, _radius)

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): MutableCircle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableCircle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _rotation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return MutableCircle(
            Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX,
                cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            ),
            _radius
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): MutableCircle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): MutableCircle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val startRotR: Float = cpDiffX / centerToPointDist
            val startRotI: Float = cpDiffY / centerToPointDist

            MutableCircle(
                Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                ComplexF(startRotR, -startRotI) * _rotation * rotation,
                _radius
            )
        } else {
            MutableCircle(_center, rotation, _radius)
        }
    }

    override fun rotateBy(angle: AngleF) {
        _rotation *= ComplexF.fromAngle(angle)
    }

    override fun rotateBy(rotation: ComplexF) {
        _rotation *= rotation
    }

    override fun rotateTo(angle: AngleF) {
        _rotation = ComplexF.fromAngle(angle)
    }

    override fun rotateTo(rotation: ComplexF) {
        _rotation = rotation
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

        _center = Vector2F(
            cpDiffX * rotR - cpDiffY * rotI + pX, cpDiffY * rotR + cpDiffX * rotI + pY
        )
        _rotation = ComplexF(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val startRotR: Float = cpDiffX / centerToPointDist
            val startRotI: Float = cpDiffY / centerToPointDist

            _center = Vector2F(
                rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
            )
            _rotation = ComplexF(startRotR, -startRotI) * _rotation * rotation
        } else {
            _rotation = rotation
        }
    }

    override fun scaledBy(factor: Float) =
        MutableCircle(_center, _rotation, _radius * factor)

    override fun scaleBy(factor: Float) {
        _radius *= factor
    }

    override fun transformedBy(offset: Vector2F, angle: AngleF) = MutableCircle(
        _center + offset,
        _rotation * ComplexF.fromAngle(angle),
        _radius
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) =
        MutableCircle(_center + offset, _rotation * rotation, _radius)

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float) = MutableCircle(
        _center + offset,
        _rotation * ComplexF.fromAngle(angle),
        _radius * factor
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        MutableCircle(
            _center + offset,
            _rotation * rotation,
            _radius * factor
        )

    override fun transformedTo(position: Vector2F, angle: AngleF) =
        MutableCircle(position, ComplexF.fromAngle(angle), _radius)

    override fun transformedTo(position: Vector2F, rotation: ComplexF) =
        MutableCircle(position, rotation, _radius)

    override fun transformBy(offset: Vector2F, angle: AngleF) =
        transformBy(offset, ComplexF.fromAngle(angle))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        _center += offset
        _rotation *= rotation
    }

    override fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        _center += offset
        _rotation *= rotation
        _radius *= factor
    }

    override fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    override fun transformTo(position: Vector2F, rotation: ComplexF) {
        _center = position
        _rotation = rotation
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val radius: Float = _radius
        val cx: Float = _center.x
        val cy: Float = _center.y
        val dx: Float = point.x - cx
        val dy: Float = point.y - cy
        val distance: Float = sqrt(dx * dx + dy * dy)
        val t: Float = radius / distance

        return if (distance > radius) Vector2F(cx + dx * t, cy + dy * t)
        else point
    }

    override fun intersects(annulus: AnnulusShape): Boolean {
        val distance: Float = _center.distanceTo(annulus.center)
        val radius: Float = _radius

        return (distance >= (annulus.innerRadius - radius)) and
                (distance <= (annulus.outerRadius + radius))
    }

    override fun intersects(circle: CircleShape): Boolean =
        _center.distanceTo(circle.center) <= _radius + circle.radius

    override operator fun contains(point: Vector2F): Boolean = _center.distanceTo(point) <= _radius

    override operator fun contains(annulus: AnnulusShape): Boolean =
        _center.distanceTo(annulus.center) <= _radius - annulus.outerRadius

    override operator fun contains(circle: CircleShape): Boolean =
        _center.distanceTo(circle.center) <= _radius - circle.radius

    override fun copy(center: Vector2F, rotation: ComplexF, radius: Float) =
        MutableCircle(center, rotation, radius)

    override fun equals(other: Any?): Boolean = other is Circle &&
            _center == other.center &&
            _rotation == other.rotation &&
            _radius == other.radius

    fun equals(other: MutableCircle): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _radius == other._radius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val radiusHash: Int = _radius.hashCode()

        return centerHash * 961 + rotationHash * 31 + radiusHash
    }

    override fun toString() =
        StringBuilder("Circle(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", radius=").append(_radius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _radius
}