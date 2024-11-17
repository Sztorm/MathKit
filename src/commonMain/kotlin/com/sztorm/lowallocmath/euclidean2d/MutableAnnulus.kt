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

/** Represents a mutable transformable annulus in a two-dimensional Euclidean space. **/
class MutableAnnulus : Annulus, MutableTransformable {
    private var _center: Vector2F
    private var _orientation: ComplexF
    private var _outerRadius: Float
    private var _innerRadius: Float

    /**
     * Creates a new instance of [MutableAnnulus].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [outerRadius] is less than zero.
     * @throws IllegalArgumentException when [innerRadius] is less than zero.
     * @throws IllegalArgumentException when [outerRadius] is less than [innerRadius].
     */
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

    override val width: Float
        get() = _outerRadius - _innerRadius

    override val outerDiameter: Float
        get() = _outerRadius * 2f

    override val innerDiameter: Float
        get() = _innerRadius * 2f

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

    private inline fun rotatedByImpl(rotation: ComplexF) = MutableAnnulus(
        _center,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _outerRadius,
        _innerRadius,
        tag = null
    )

    override fun rotatedBy(rotation: AngleF) = rotatedByImpl(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF) = rotatedByImpl(rotation)

    private inline fun rotatedToImpl(orientation: ComplexF) = MutableAnnulus(
        _center, orientation.normalizedOrElse(ComplexF.ONE), _outerRadius, _innerRadius, tag = null
    )

    override fun rotatedTo(orientation: AngleF) = rotatedToImpl(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF) = rotatedToImpl(orientation)

    private inline fun rotatedAroundPointByImpl(
        point: Vector2F, rotation: ComplexF
    ): MutableAnnulus {
        val (pX: Float, pY: Float) = point
        val (rotR: Float, rotI: Float) = rotation
        val (cX: Float, cY: Float) = _center
        val (startRotR: Float, startRotI: Float) = _orientation
        val cpDiffX: Float = cX - pX
        val cpDiffY: Float = cY - pY

        return MutableAnnulus(
            center = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX,
                cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            orientation = ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            ).normalizedOrElse(ComplexF.ONE),
            _outerRadius,
            _innerRadius,
            tag = null
        )
    }

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): MutableAnnulus =
        rotatedAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): MutableAnnulus =
        rotatedAroundPointByImpl(point, rotation)

    private inline fun rotatedAroundPointToImpl(
        point: Vector2F, orientation: ComplexF
    ): MutableAnnulus {
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
                center = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                orientation = (ComplexF(startRotR, -startRotI) * _orientation * orientation)
                    .normalizedOrElse(ComplexF.ONE),
                _outerRadius,
                _innerRadius,
                tag = null
            )
        } else {
            MutableAnnulus(
                _center,
                orientation = orientation.normalizedOrElse(ComplexF.ONE),
                _outerRadius,
                _innerRadius,
                tag = null
            )
        }
    }

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): MutableAnnulus =
        rotatedAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): MutableAnnulus =
        rotatedAroundPointToImpl(point, orientation)

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

    private inline fun rotateAroundPointByImpl(point: Vector2F, rotation: ComplexF) {
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

    override fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointByImpl(point, ComplexF.fromAngle(rotation))

    override fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF) =
        rotateAroundPointByImpl(point, rotation)

    private inline fun rotateAroundPointToImpl(point: Vector2F, orientation: ComplexF) {
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

    override fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointToImpl(point, ComplexF.fromAngle(orientation))

    override fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF) =
        rotateAroundPointToImpl(point, orientation)

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

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF
    ) = MutableAnnulus(
        _center + displacement,
        orientation = (_orientation * rotation).normalizedOrElse(ComplexF.ONE),
        _outerRadius,
        _innerRadius,
        tag = null
    )

    override fun transformedBy(displacement: Vector2F, rotation: AngleF) =
        transformedByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF) =
        transformedByImpl(displacement, rotation)

    private inline fun transformedByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableAnnulus {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        return MutableAnnulus(
            center = _center + displacement,
            orientation = (_orientation * rotation)
                .normalizedOrElse(ComplexF.ONE) * 1f.withSign(scaleFactor),
            outerRadius = _outerRadius * absScaleFactor,
            innerRadius = _innerRadius * absScaleFactor,
            tag = null
        )
    }

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ): MutableAnnulus = transformedByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): MutableAnnulus = transformedByImpl(displacement, rotation, scaleFactor)

    private inline fun transformedToImpl(
        position: Vector2F, orientation: ComplexF
    ) = MutableAnnulus(
        center = position,
        orientation = orientation.normalizedOrElse(ComplexF.ONE),
        _outerRadius,
        _innerRadius,
        tag = null
    )

    override fun transformedTo(position: Vector2F, orientation: AngleF) =
        transformedToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        transformedToImpl(position, orientation)

    private inline fun transformByImpl(displacement: Vector2F, rotation: ComplexF) {
        _center += displacement
        _orientation *= rotation
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) =
        transformByImpl(displacement, rotation)

    private inline fun transformByImpl(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ) {
        val absScaleFactor: Float = scaleFactor.absoluteValue

        _center += displacement
        _orientation *= rotation * 1f.withSign(scaleFactor)
        _outerRadius *= absScaleFactor
        _innerRadius *= absScaleFactor
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformByImpl(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) =
        transformByImpl(displacement, rotation, scaleFactor)

    private inline fun transformToImpl(position: Vector2F, orientation: ComplexF) {
        _center = position
        _orientation = orientation
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformToImpl(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) =
        transformToImpl(position, orientation)

    /**
     * Calibrates the properties of this instance. If the [orientation] cannot be normalized, it
     * will take the value of [ONE][ComplexF.ONE].
     *
     * Transformations and operations involving floating point numbers may introduce various
     * inaccuracies that can be countered by this method.
     */
    fun calibrate() {
        _orientation = _orientation.normalizedOrElse(ComplexF.ONE)
    }

    private inline fun setInternal(
        center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float
    ) {
        _center = center
        _orientation = orientation
        _outerRadius = outerRadius
        _innerRadius = innerRadius
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [outerRadius] is less than zero.
     * @throws IllegalArgumentException when [innerRadius] is less than zero.
     * @throws IllegalArgumentException when [outerRadius] is less than [innerRadius].
     */
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
        orientation = ComplexF.slerp(_orientation, to.orientation, by)
            .normalizedOrElse(ComplexF.ONE),
        outerRadius = Float.lerp(_outerRadius, to.outerRadius, by),
        innerRadius = Float.lerp(_innerRadius, to.innerRadius, by),
        tag = null
    )

    /**
     * Sets this annulus with the result of interpolation [from] one annulus [to] another annulus
     * [by] a factor.
     *
     * @param from the annulus from which the interpolation starts.
     * @param to the annulus at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
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

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [outerRadius] is less than zero.
     * @throws IllegalArgumentException when [innerRadius] is less than zero.
     * @throws IllegalArgumentException when [outerRadius] is less than [innerRadius].
     */
    override fun copy(
        center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float
    ) = MutableAnnulus(center, orientation, outerRadius, innerRadius)

    override fun equals(other: Any?): Boolean = other is Annulus &&
            _center == other.center &&
            _orientation == other.orientation &&
            _outerRadius == other.outerRadius &&
            _innerRadius == other.innerRadius

    /** Indicates whether the other [MutableAnnulus] is equal to this one. **/
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
            if (outerRadius < innerRadius) {
                throw IllegalArgumentException(
                    "outerRadius must be greater than or equal to innerRadius."
                )
            }
        }
    }
}