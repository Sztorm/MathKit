package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableRay(origin: Vector2F, direction: Vector2F) : Ray, MutableTransformable {
    private var _origin: Vector2F = origin
    private var _direction: Vector2F = direction

    override val origin: Vector2F
        get() = _origin

    override val direction: Vector2F
        get() = _direction

    override val position: Vector2F
        get() = _origin

    override val orientation: ComplexF
        get() = _direction.toComplexF()

    override fun movedBy(offset: Vector2F) = MutableRay(_origin + offset, _direction)

    override fun movedTo(position: Vector2F) = MutableRay(position, _direction)

    override fun moveBy(offset: Vector2F) {
        _origin += offset
    }

    override fun moveTo(position: Vector2F) {
        _origin = position
    }

    override fun rotatedBy(rotation: AngleF): MutableRay =
        rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): MutableRay {
        val (dirX: Float, dirY: Float) = _direction
        val (rotR: Float, rotI: Float) = rotation

        return MutableRay(
            _origin,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun rotatedTo(orientation: AngleF): MutableRay =
        MutableRay(_origin, direction = ComplexF.fromAngle(orientation).toVector2F())

    override fun rotatedTo(orientation: ComplexF): MutableRay =
        MutableRay(_origin, direction = orientation.toVector2F())

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableRay =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

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

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableRay =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableRay {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _origin
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val (startRotR: Float, startRotI: Float) = _direction
            val r0: Float = pointRotR * startRotR + pointRotI * startRotI
            val i0: Float = pointRotR * startRotI - pointRotI * startRotR

            return MutableRay(
                origin = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                direction = Vector2F(r0 * rotR - i0 * rotI, i0 * rotR + r0 * rotI)
            )
        } else {
            return MutableRay(_origin, direction = orientation.toVector2F())
        }
    }

    override fun rotateBy(rotation: AngleF) = rotateBy(ComplexF.fromAngle(rotation))

    override fun rotateBy(rotation: ComplexF) {
        val (dirX: Float, dirY: Float) = _direction
        val (rotR: Float, rotI: Float) = rotation

        _direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
    }

    override fun rotateTo(orientation: AngleF) {
        _direction = ComplexF.fromAngle(orientation).toVector2F()
    }

    override fun rotateTo(orientation: ComplexF) {
        _direction = orientation.toVector2F()
    }

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

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

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _origin
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        if (centerToPointDist > 0.00001f) {
            val pointRotR: Float = cpDiffX / centerToPointDist
            val pointRotI: Float = cpDiffY / centerToPointDist
            val (startRotR: Float, startRotI: Float) = _direction
            val r0: Float = pointRotR * startRotR + pointRotI * startRotI
            val i0: Float = pointRotR * startRotI - pointRotI * startRotR

            _origin = Vector2F(
                rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
            )
            _direction = Vector2F(r0 * rotR - i0 * rotI, i0 * rotR + r0 * rotI)
        } else {
            _direction = orientation.toVector2F()
        }
    }

    override fun scaledBy(factor: Float) =
        MutableRay(_origin, _direction * 1f.withSign(factor))

    override fun dilatedBy(point: Vector2F, factor: Float): MutableRay {
        val (cX: Float, cY: Float) = _origin
        val (pX: Float, pY: Float) = point

        return MutableRay(
            origin = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            direction * 1f.withSign(factor)
        )
    }

    override fun scaleBy(factor: Float) {
        _direction *= 1f.withSign(factor)
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _origin
        val (pX: Float, pY: Float) = point
        _origin = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY))
        _direction *= 1f.withSign(factor)
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF): MutableRay =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): MutableRay {
        val (dirX: Float, dirY: Float) = _direction
        val (rotR: Float, rotI: Float) = rotation

        return MutableRay(
            origin = _origin + offset,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): MutableRay =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): MutableRay {
        val (dirX: Float, dirY: Float) = _direction
        val factorSign: Float = 1f.withSign(factor)
        val rotR: Float = rotation.real * factorSign
        val rotI: Float = rotation.imaginary * factorSign

        return MutableRay(
            origin = _origin + offset,
            direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF): MutableRay =
        MutableRay(position, direction = ComplexF.fromAngle(orientation).toVector2F())

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        MutableRay(position, direction = orientation.toVector2F())

    override fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformBy(offset, ComplexF.fromAngle(rotation))

    override fun transformBy(offset: Vector2F, rotation: ComplexF) {
        val (dirX: Float, dirY: Float) = _direction
        val (rotR: Float, rotI: Float) = rotation

        _origin += offset
        _direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
    }

    override fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float) {
        val (dirX: Float, dirY: Float) = _direction
        val factorSign: Float = 1f.withSign(factor)
        val rotR: Float = rotation.real * factorSign
        val rotI: Float = rotation.imaginary * factorSign

        _origin += offset
        _direction = Vector2F(dirX * rotR - dirY * rotI, dirY * rotR + dirX * rotI)
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) {
        _origin = position
        _direction = ComplexF.fromAngle(orientation).toVector2F()
    }

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        _origin = position
        _direction = orientation.toVector2F()
    }

    private inline fun setInternal(origin: Vector2F, direction: Vector2F) {
        _origin = origin
        _direction = direction
    }

    fun set(origin: Vector2F = this.origin, direction: Vector2F = this.direction) =
        setInternal(origin, direction)

    override fun interpolated(to: Ray, by: Float) = MutableRay(
        origin = Vector2F.lerp(_origin, to.origin, by),
        direction = ComplexF
            .slerp(_direction.toComplexF(), to.direction.toComplexF(), by)
            .toVector2F()
    )

    fun interpolate(from: Ray, to: Ray, by: Float) {
        _origin = Vector2F.lerp(from.origin, to.origin, by)
        _direction = ComplexF
            .slerp(from.direction.toComplexF(), to.direction.toComplexF(), by)
            .toVector2F()
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val origin: Vector2F = _origin
        val direction: Vector2F = _direction
        val op: Vector2F = point - origin
        val t: Float = op dot direction

        return if (t <= 0f) origin
        else origin + direction * t
    }

    override fun intersects(annulus: Annulus): Boolean {
        val rayOrigin: Vector2F = _origin
        val rayDirection: Vector2F = _direction
        val annulusCenter: Vector2F = annulus.center
        val annulusOuterRadius: Float = annulus.outerRadius
        val diff: Vector2F = annulusCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(annulusCenter) <= annulusOuterRadius
    }

    override fun intersects(circle: Circle): Boolean {
        val rayOrigin: Vector2F = _origin
        val rayDirection: Vector2F = _direction
        val circleCenter: Vector2F = circle.center
        val circleRadius: Float = circle.radius
        val diff: Vector2F = circleCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(circleCenter) <= circleRadius
    }

    override fun intersects(ray: Ray): Boolean {
        val (origAX: Float, origAY: Float) = _origin
        val (dirAX: Float, dirAY: Float) = _direction
        val (origBX: Float, origBY: Float) = ray.origin
        val (dirBX: Float, dirBY: Float) = ray.direction
        val dx: Float = origBX - origAX
        val dy: Float = origBY - origAY
        val dirBCrossDirA: Float = dirBX * dirAY - dirBY * dirAX
        val areDirsTheSameOrOpposite: Boolean = dirBCrossDirA.absoluteValue < 0.00001f

        if (areDirsTheSameOrOpposite) {
            val length: Float = sqrt(dx * dx + dy * dy)

            if (length < 0.00001f) {
                return true
            }
            val dirBDotDirA: Float = dirBX * dirAX + dirBY * dirAY
            val oneOverLength: Float = 1f / length
            val dxn: Float = dx * oneOverLength
            val dyn: Float = dy * oneOverLength
            val areDirsOpposite: Boolean = dirBDotDirA < 0
            val det: Float =
                if (areDirsOpposite) dirAX * dxn + dirAY * dyn - 1f
                else dirAX * dyn - dirAY * dxn

            return det.absoluteValue < 0.000001f
        }
        val nomX: Float = (dy * dirBX - dx * dirBY)
        val nomY: Float = (dy * dirAX - dx * dirAY)

        return (nomX * dirBCrossDirA >= 0f) and (nomY * dirBCrossDirA >= 0f)
    }

    override operator fun contains(point: Vector2F): Boolean {
        val origin: Vector2F = _origin
        val direction: Vector2F = _direction
        val op: Vector2F = point - origin
        val t: Float = op dot direction
        val closestPoint: Vector2F =
            if (t <= 0f) origin
            else origin + direction * t

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