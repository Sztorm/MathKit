package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableCircle(
    center: Vector2F, orientation: ComplexF, radius: Float
) : Circle, MutableTransformable {
    private var _center: Vector2F = center
    private var _orientation: ComplexF = orientation
    private var _radius: Float = radius

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

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
        MutableCircle(_center + offset, _orientation, _radius)

    override fun movedTo(position: Vector2F) =
        MutableCircle(position, _orientation, _radius)

    override fun moveBy(offset: Vector2F) {
        _center += offset
    }

    override fun moveTo(position: Vector2F) {
        _center = position
    }

    override fun rotatedBy(rotation: AngleF) =
        MutableCircle(_center, _orientation * ComplexF.fromAngle(rotation), _radius)

    override fun rotatedBy(rotation: ComplexF) =
        MutableCircle(_center, _orientation * rotation, _radius)

    override fun rotatedTo(orientation: AngleF) =
        MutableCircle(_center, ComplexF.fromAngle(orientation), _radius)

    override fun rotatedTo(orientation: ComplexF) = MutableCircle(_center, orientation, _radius)

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableCircle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableCircle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
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

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableCircle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableCircle {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
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
                ComplexF(startRotR, -startRotI) * _orientation * orientation,
                _radius
            )
        } else {
            MutableCircle(_center, orientation, _radius)
        }
    }

    override fun rotateBy(rotation: AngleF) {
        _orientation *= ComplexF.fromAngle(rotation)
    }

    override fun rotateBy(rotation: ComplexF) {
        _orientation *= rotation
    }

    override fun rotateTo(orientation: AngleF) {
        _orientation = ComplexF.fromAngle(orientation)
    }

    override fun rotateTo(orientation: ComplexF) {
        _orientation = orientation
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        _center = Vector2F(
            cpDiffX * rotR - cpDiffY * rotI + pX, cpDiffY * rotR + cpDiffX * rotI + pY
        )
        _orientation = ComplexF(
            startRotR * rotR - startRotI * rotI, startRotI * rotR + startRotR * rotI
        )
    }

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist

            _center = Vector2F(
                rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
            )
            _orientation = ComplexF(pointRotR, -pointRotI) * _orientation * orientation
        } else {
            _orientation = orientation
        }
    }

    override fun scaledBy(factor: Float) = MutableCircle(
        _center,
        _orientation * 1f.withSign(factor),
        _radius * factor.absoluteValue
    )

    override fun dilatedBy(point: Vector2F, factor: Float): MutableCircle {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point

        return MutableCircle(
            center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            orientation * 1f.withSign(factor),
            radius * factor.absoluteValue
        )
    }

    override fun scaleBy(factor: Float) {
        _orientation *= 1f.withSign(factor)
        _radius *= factor.absoluteValue
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        _center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY))
        _orientation *= 1f.withSign(factor)
        _radius *= factor.absoluteValue
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF) = MutableCircle(
        _center + offset,
        _orientation * ComplexF.fromAngle(rotation),
        _radius
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF) =
        MutableCircle(_center + offset, _orientation * rotation, _radius)

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float) = MutableCircle(
        _center + offset,
        _orientation * ComplexF.fromAngle(rotation),
        _radius * factor
    )

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float) =
        MutableCircle(
            _center + offset,
            _orientation * rotation,
            _radius * factor
        )

    override fun transformedTo(position: Vector2F, orientation: AngleF) =
        MutableCircle(position, ComplexF.fromAngle(orientation), _radius)

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        MutableCircle(position, orientation, _radius)

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformBy(offset, ComplexF.fromAngle(rotation))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        _center += offset
        _orientation *= rotation
    }

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        _center += offset
        _orientation *= rotation
        _radius *= factor
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        _center = position
        _orientation = orientation
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

    override fun copy(center: Vector2F, orientation: ComplexF, radius: Float) =
        MutableCircle(center, orientation, radius)

    override fun equals(other: Any?): Boolean = other is Circle &&
            _center == other.center &&
            _orientation == other.orientation &&
            _radius == other.radius

    fun equals(other: MutableCircle): Boolean =
        _center == other._center &&
                _orientation == other._orientation &&
                _radius == other._radius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val radiusHash: Int = _radius.hashCode()

        return centerHash * 961 + orientationHash * 31 + radiusHash
    }

    override fun toString() =
        StringBuilder("Circle(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", radius=").append(_radius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _radius
}