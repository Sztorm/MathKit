@file:Suppress("ConvertTwoComparisonsToRangeCheck")

package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.lerp
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

class MutableAnnulus : Annulus, MutableTransformable {
    private var _center: Vector2F
    private var _orientation: ComplexF
    private var _outerRadius: Float
    private var _innerRadius: Float

    constructor(center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float) {
        throwWhenConstructorArgumentsAreIllegal(outerRadius, innerRadius)
        _center = center
        _orientation = orientation
        _outerRadius = outerRadius
        _innerRadius = innerRadius
    }

    @Suppress("UNUSED_PARAMETER")
    private constructor(
        center: Vector2F,
        orientation: ComplexF,
        outerRadius: Float,
        innerRadius: Float,
        tag: Any?
    ) {
        _center = center
        _orientation = orientation
        _outerRadius = outerRadius
        _innerRadius = innerRadius
    }

    override val center: Vector2F
        get() = _center

    override val orientation: ComplexF
        get() = _orientation

    override val outerRadius: Float
        get() = _outerRadius

    override val innerRadius: Float
        get() = _innerRadius

    override val area: Float
        get() = PI.toFloat() * (_outerRadius * _outerRadius - _innerRadius * _innerRadius)

    override val perimeter: Float
        get() = (2.0 * PI).toFloat() * (_outerRadius + _innerRadius)

    override val annularRadius: Float
        get() = _outerRadius - _innerRadius

    override val position: Vector2F
        get() = _center

    override fun movedBy(displacement: Vector2F) = MutableAnnulus(
        _center + displacement, _orientation, _outerRadius, _innerRadius, tag = null
    )

    override fun movedTo(position: Vector2F) =
        MutableAnnulus(position, _orientation, _outerRadius, _innerRadius, tag = null)

    override fun moveBy(displacement: Vector2F) {
        _center += displacement
    }

    override fun moveTo(position: Vector2F) {
        _center = position
    }

    override fun rotatedBy(rotation: AngleF) = MutableAnnulus(
        _center,
        _orientation * ComplexF.fromAngle(rotation),
        _outerRadius,
        _innerRadius,
        tag = null
    )

    override fun rotatedBy(rotation: ComplexF) = MutableAnnulus(
        _center, _orientation * rotation, _outerRadius, _innerRadius, tag = null
    )

    override fun rotatedTo(orientation: AngleF) = MutableAnnulus(
        _center, ComplexF.fromAngle(orientation), _outerRadius, _innerRadius, tag = null
    )

    override fun rotatedTo(orientation: ComplexF) =
        MutableAnnulus(_center, orientation, _outerRadius, _innerRadius, tag = null)

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableAnnulus =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableAnnulus {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return MutableAnnulus(
            Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX,
                cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            ),
            _outerRadius,
            _innerRadius,
            tag = null
        )
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableAnnulus =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableAnnulus {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = orientation
        val (cX: Float, cY: Float) = _center
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY
        val centerToPointDist: Float = sqrt(cpDiffX * cpDiffX + cpDiffY * cpDiffY)

        return if (centerToPointDist > 0.00001f) {
            val startRotR: Float = cpDiffX / centerToPointDist
            val startRotI: Float = cpDiffY / centerToPointDist

            MutableAnnulus(
                Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                ComplexF(startRotR, -startRotI) * _orientation * orientation,
                _outerRadius,
                _innerRadius,
                tag = null
            )
        } else {
            MutableAnnulus(_center, orientation, _outerRadius, _innerRadius, tag = null)
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
            val startRotR: Float = cpDiffX / centerToPointDist
            val startRotI: Float = cpDiffY / centerToPointDist

            _center = Vector2F(
                rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
            )
            _orientation = ComplexF(startRotR, -startRotI) * _orientation * orientation
        } else {
            _orientation = orientation
        }
    }

    override fun scaledBy(factor: Float): MutableAnnulus {
        val absFactor: Float = factor.absoluteValue

        return MutableAnnulus(
            _center,
            _orientation * 1f.withSign(factor),
            _outerRadius * absFactor,
            _innerRadius * absFactor,
            tag = null
        )
    }

    override fun dilatedBy(point: Vector2F, factor: Float): MutableAnnulus {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val absFactor: Float = factor.absoluteValue

        return MutableAnnulus(
            center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            orientation * 1f.withSign(factor),
            _outerRadius * absFactor,
            _innerRadius * absFactor,
            tag = null
        )
    }

    override fun scaleBy(factor: Float) {
        val absFactor: Float = factor.absoluteValue
        _orientation *= 1f.withSign(factor)
        _outerRadius *= absFactor
        _innerRadius *= absFactor
    }

    override fun dilateBy(point: Vector2F, factor: Float) {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point
        val absFactor: Float = factor.absoluteValue
        _center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY))
        _orientation *= 1f.withSign(factor)
        _outerRadius *= absFactor
        _innerRadius *= absFactor
    }

    override fun transformedBy(displacement: Vector2F, rotation: AngleF) = MutableAnnulus(
        _center + displacement,
        _orientation * ComplexF.fromAngle(rotation),
        _outerRadius,
        _innerRadius,
        tag = null
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF) = MutableAnnulus(
        _center + displacement,
        _orientation * rotation,
        _outerRadius,
        _innerRadius,
        tag = null
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableAnnulus {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return MutableAnnulus(
            _center + displacement,
            _orientation * ComplexF.fromAngle(rotation) * 1f.withSign(scaleFactor),
            _outerRadius * absScaleFactor,
            _innerRadius * absScaleFactor,
            tag = null
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableAnnulus {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return MutableAnnulus(
            _center + displacement,
            _orientation * rotation * 1f.withSign(scaleFactor),
            _outerRadius * absScaleFactor,
            _innerRadius * absScaleFactor,
            tag = null
        )
    }

    override fun transformedTo(position: Vector2F, orientation: AngleF) = MutableAnnulus(
        position, ComplexF.fromAngle(orientation), _outerRadius, _innerRadius, tag = null
    )

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        MutableAnnulus(position, orientation, _outerRadius, _innerRadius, tag = null)

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformBy(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) {
        _center += displacement
        _orientation *= rotation
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformBy(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        _center += displacement
        _orientation *= rotation * 1f.withSign(scaleFactor)
        _outerRadius *= absScaleFactor
        _innerRadius *= absScaleFactor
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        _center = position
        _orientation = orientation
    }

    private inline fun setInternal(
        center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float
    ) {
        _center = center
        _orientation = orientation
        _outerRadius = outerRadius
        _innerRadius = innerRadius
    }

    fun set(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        outerRadius: Float = this.outerRadius,
        innerRadius: Float = this.innerRadius
    ) {
        throwWhenConstructorArgumentsAreIllegal(outerRadius, innerRadius)
        setInternal(center, orientation, outerRadius, innerRadius)
    }

    override fun interpolated(to: Annulus, by: Float) = MutableAnnulus(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by),
        outerRadius = Float.lerp(_outerRadius, to.outerRadius, by),
        innerRadius = Float.lerp(_innerRadius, to.innerRadius, by),
        tag = null
    )

    fun interpolate(from: Annulus, to: Annulus, by: Float) {
        _center = Vector2F.lerp(from.center, to.center, by)
        _orientation = ComplexF.slerp(from.orientation, to.orientation, by)
        _outerRadius = Float.lerp(from.outerRadius, to.outerRadius, by)
        _innerRadius = Float.lerp(from.innerRadius, to.innerRadius, by)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val outerRadius: Float = _outerRadius
        val innerRadius: Float = _innerRadius
        val (cx: Float, cy: Float) = _center
        val dx: Float = cx - point.x
        val dy: Float = cy - point.y
        val distance: Float = sqrt(dx * dx + dy * dy)

        return when {
            distance < innerRadius -> {
                val t: Float = innerRadius / distance

                Vector2F(cx - dx * t, cy - dy * t)
            }

            distance > outerRadius -> {
                val t: Float = outerRadius / distance

                Vector2F(cx - dx * t, cy - dy * t)
            }

            else -> point
        }
    }

    override fun intersects(annulus: Annulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = _innerRadius

        return (innerRadius <= (otherAnnulusOuterRadius + distance)) &&
                (_outerRadius >= (distance - otherAnnulusOuterRadius)) &&
                (otherAnnulusInnerRadius <= (_outerRadius + distance))
    }

    override fun intersects(circle: Circle): Boolean {
        val distance: Float = _center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (distance >= (_innerRadius - circleRadius)) &&
                (distance <= (_outerRadius + circleRadius))
    }

    override fun intersects(ray: Ray): Boolean {
        val rayOrigin: Vector2F = ray.origin
        val rayDirection: Vector2F = ray.direction
        val annulusCenter: Vector2F = center
        val annulusOuterRadius: Float = outerRadius
        val diff: Vector2F = annulusCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(annulusCenter) <= annulusOuterRadius
    }

    override operator fun contains(point: Vector2F): Boolean {
        val distance: Float = _center.distanceTo(point)

        return distance >= _innerRadius && distance <= _outerRadius
    }

    override operator fun contains(annulus: Annulus): Boolean {
        val distance: Float = _center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = _innerRadius

        return (_outerRadius >= (distance + otherAnnulusOuterRadius)) &&
                ((innerRadius <= (distance - otherAnnulusOuterRadius)) ||
                        (innerRadius <= (distance + otherAnnulusInnerRadius)))
    }

    override operator fun contains(circle: Circle): Boolean {
        val distance: Float = _center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (_outerRadius >= (distance + circleRadius)) &&
                (_innerRadius <= (distance - circleRadius))
    }

    override fun copy(
        center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float
    ) = MutableAnnulus(center, orientation, outerRadius, innerRadius)

    override fun equals(other: Any?): Boolean = other is Annulus &&
            _center == other.center &&
            _orientation == other.orientation &&
            _outerRadius == other.outerRadius &&
            _innerRadius == other.innerRadius

    fun equals(other: MutableAnnulus): Boolean =
        _center == other._center &&
                _orientation == other._orientation &&
                _outerRadius == other._outerRadius &&
                _innerRadius == other._innerRadius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val orientationHash: Int = _orientation.hashCode()
        val outerRadiusHash: Int = _outerRadius.hashCode()
        val innerRadiusHash: Int = _innerRadius.hashCode()

        return centerHash * 29791 + orientationHash * 961 + outerRadiusHash * 31 + innerRadiusHash
    }

    override fun toString() =
        StringBuilder("Annulus(center=").append(_center)
            .append(", orientation=").append(_orientation)
            .append(", outerRadius=").append(_outerRadius)
            .append(", innerRadius=").append(_innerRadius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _orientation

    override operator fun component3(): Float = _outerRadius

    override operator fun component4(): Float = _innerRadius

    companion object {
        private inline fun throwWhenConstructorArgumentsAreIllegal(
            outerRadius: Float, innerRadius: Float
        ) {
            if (outerRadius < 0f) {
                throw IllegalArgumentException(
                    "outerRadius must be greater than or equal to zero."
                )
            }
            if (innerRadius < 0f) {
                throw IllegalArgumentException(
                    "innerRadius must be greater than or equal to zero."
                )
            }
            if (innerRadius > outerRadius) {
                throw IllegalArgumentException("innerRadius must be less than outerRadius.")
            }
        }
    }
}