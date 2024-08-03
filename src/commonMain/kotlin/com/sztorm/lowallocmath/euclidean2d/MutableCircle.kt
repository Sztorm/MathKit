package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.lerp
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import kotlin.math.withSign

/** Represents a mutable transformable circle in a two-dimensional Euclidean space. **/
class MutableCircle : Circle, MutableTransformable {
    private var _center: Vector2F
    private var _orientation: ComplexF
    private var _radius: Float

    /**
     * Creates a new instance of [MutableCircle].
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [radius] is less than zero.
     */
    constructor(center: Vector2F, orientation: ComplexF, radius: Float) {
        throwWhenConstructorArgumentIsIllegal(radius)
        _center = center
        _orientation = orientation
        _radius = radius
    }

    @Suppress("UNUSED_PARAMETER")
    private constructor(center: Vector2F, orientation: ComplexF, radius: Float, tag: Any?) {
        _center = center
        _orientation = orientation
        _radius = radius
    }

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

    override fun movedBy(displacement: Vector2F) =
        MutableCircle(_center + displacement, _orientation, _radius, tag = null)

    override fun movedTo(position: Vector2F) =
        MutableCircle(position, _orientation, _radius, tag = null)

    override fun moveBy(displacement: Vector2F) {
        _center += displacement
    }

    override fun moveTo(position: Vector2F) {
        _center = position
    }

    override fun rotatedBy(rotation: AngleF) = MutableCircle(
        _center, _orientation * ComplexF.fromAngle(rotation), _radius, tag = null
    )

    override fun rotatedBy(rotation: ComplexF) =
        MutableCircle(_center, _orientation * rotation, _radius, tag = null)

    override fun rotatedTo(orientation: AngleF) =
        MutableCircle(_center, ComplexF.fromAngle(orientation), _radius, tag = null)

    override fun rotatedTo(orientation: ComplexF) =
        MutableCircle(_center, orientation, _radius, tag = null)

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
            center = Vector2F(
                cpDiffX * rotR - cpDiffY * rotI + pX,
                cpDiffY * rotR + cpDiffX * rotI + pY
            ),
            orientation = ComplexF(
                startRotR * rotR - startRotI * rotI,
                startRotI * rotR + startRotR * rotI
            ),
            _radius,
            tag = null
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
                center = Vector2F(
                    rotR * centerToPointDist + pX, rotI * centerToPointDist + pY
                ),
                orientation = ComplexF(startRotR, -startRotI) * _orientation * orientation,
                _radius,
                tag = null
            )
        } else {
            MutableCircle(_center, orientation, _radius, tag = null)
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
        _radius * factor.absoluteValue,
        tag = null
    )

    override fun dilatedBy(point: Vector2F, factor: Float): MutableCircle {
        val (cX: Float, cY: Float) = _center
        val (pX: Float, pY: Float) = point

        return MutableCircle(
            center = Vector2F(pX + factor * (cX - pX), pY + factor * (cY - pY)),
            _orientation * 1f.withSign(factor),
            _radius * factor.absoluteValue,
            tag = null
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

    override fun transformedBy(displacement: Vector2F, rotation: AngleF) = MutableCircle(
        _center + displacement,
        _orientation * ComplexF.fromAngle(rotation),
        _radius,
        tag = null
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF) = MutableCircle(
        _center + displacement, _orientation * rotation, _radius, tag = null
    )

    override fun transformedBy(
        displacement: Vector2F, rotation: AngleF, scaleFactor: Float
    ) = MutableCircle(
        _center + displacement,
        _orientation * ComplexF.fromAngle(rotation) * 1f.withSign(scaleFactor),
        _radius * scaleFactor.absoluteValue,
        tag = null
    )

    override fun transformedBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) =
        MutableCircle(
            _center + displacement,
            _orientation * rotation * 1f.withSign(scaleFactor),
            _radius * scaleFactor.absoluteValue,
            tag = null
        )

    override fun transformedTo(position: Vector2F, orientation: AngleF) =
        MutableCircle(position, ComplexF.fromAngle(orientation), _radius, tag = null)

    override fun transformedTo(position: Vector2F, orientation: ComplexF) =
        MutableCircle(position, orientation, _radius, tag = null)

    override fun transformBy(displacement: Vector2F, rotation: AngleF) =
        transformBy(displacement, ComplexF.fromAngle(rotation))

    override fun transformBy(displacement: Vector2F, rotation: ComplexF) {
        _center += displacement
        _orientation *= rotation
    }

    override fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float) =
        transformBy(displacement, ComplexF.fromAngle(rotation), scaleFactor)

    override fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float) {
        _center += displacement
        _orientation *= rotation * 1f.withSign(scaleFactor)
        _radius *= scaleFactor.absoluteValue
    }

    override fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    override fun transformTo(position: Vector2F, orientation: ComplexF) {
        _center = position
        _orientation = orientation
    }

    private inline fun setInternal(center: Vector2F, orientation: ComplexF, radius: Float) {
        _center = center
        _orientation = orientation
        _radius = radius
    }

    /**
     * Sets the specified properties of this instance.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [radius] is less than zero.
     */
    fun set(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        radius: Float = this.radius
    ) {
        throwWhenConstructorArgumentIsIllegal(radius)
        setInternal(center, orientation, radius)
    }

    override fun interpolated(to: Circle, by: Float) = MutableCircle(
        center = Vector2F.lerp(_center, to.center, by),
        orientation = ComplexF.slerp(_orientation, to.orientation, by),
        radius = Float.lerp(_radius, to.radius, by),
        tag = null
    )

    /**
     * Sets this circle with the result of interpolation [from] one circle [to] another circle [by]
     * a factor.
     *
     * @param from the circle from which the interpolation starts.
     * @param to the circle at which the interpolation ends.
     * @param by the interpolation factor which is expected to be in the range of `[0, 1]`.
     */
    fun interpolate(from: Circle, to: Circle, by: Float) {
        _center = Vector2F.lerp(from.center, to.center, by)
        _orientation = ComplexF.slerp(from.orientation, to.orientation, by)
        _radius = Float.lerp(from.radius, to.radius, by)
    }

    override fun closestPointTo(point: Vector2F): Vector2F {
        val radius: Float = _radius
        val (cx: Float, cy: Float) = _center
        val dx: Float = point.x - cx
        val dy: Float = point.y - cy
        val distance: Float = sqrt(dx * dx + dy * dy)
        val t: Float = radius / distance

        return if (distance > radius) Vector2F(cx + dx * t, cy + dy * t)
        else point
    }

    override fun intersects(annulus: Annulus): Boolean {
        val distance: Float = _center.distanceTo(annulus.center)
        val radius: Float = _radius

        return (distance >= (annulus.innerRadius - radius)) and
                (distance <= (annulus.outerRadius + radius))
    }

    override fun intersects(circle: Circle): Boolean =
        _center.distanceTo(circle.center) <= _radius + circle.radius

    override fun intersects(ray: Ray): Boolean {
        val rayOrigin: Vector2F = ray.origin
        val rayDirection: Vector2F = ray.direction
        val circleCenter: Vector2F = _center
        val circleRadius: Float = _radius
        val diff: Vector2F = circleCenter - rayOrigin
        val t: Float = diff dot rayDirection
        val closestPointOnRay: Vector2F =
            if (t <= 0f) rayOrigin
            else rayOrigin + rayDirection * t

        return closestPointOnRay.distanceTo(circleCenter) <= circleRadius
    }

    override operator fun contains(point: Vector2F): Boolean = _center.distanceTo(point) <= _radius

    override operator fun contains(annulus: Annulus): Boolean =
        _center.distanceTo(annulus.center) <= _radius - annulus.outerRadius

    override operator fun contains(circle: Circle): Boolean =
        _center.distanceTo(circle.center) <= _radius - circle.radius

    /**
     * Returns a copy of this instance with specified properties changed.
     *
     * @param orientation the value is expected to be [normalized][ComplexF.normalized].
     * @throws IllegalArgumentException when [radius] is less than zero.
     */
    override fun copy(center: Vector2F, orientation: ComplexF, radius: Float) =
        MutableCircle(center, orientation, radius)

    override fun equals(other: Any?): Boolean = other is Circle &&
            _center == other.center &&
            _orientation == other.orientation &&
            _radius == other.radius

    /** Indicates whether the other [MutableCircle] is equal to this one. **/
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

    companion object {
        private inline fun throwWhenConstructorArgumentIsIllegal(radius: Float) {
            if (radius < 0f) {
                throw IllegalArgumentException("radius must be greater than or equal to zero.")
            }
        }
    }
}