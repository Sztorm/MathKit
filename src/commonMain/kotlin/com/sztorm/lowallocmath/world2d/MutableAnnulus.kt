@file:Suppress("ConvertTwoComparisonsToRangeCheck")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.sqrt

class MutableAnnulus(
    center: Vector2F, rotation: ComplexF, outerRadius: Float, innerRadius: Float
) : Annulus {
    private var _center: Vector2F = center
    private var _rotation: ComplexF = rotation
    private var _outerRadius: Float = outerRadius
    private var _innerRadius: Float = innerRadius

    override val center: Vector2F
        get() = _center

    override val rotation: ComplexF
        get() = _rotation

    override val outerRadius: Float
        get() = _outerRadius

    override val innerRadius: Float
        get() = _innerRadius

    override val annularRadius: Float
        get() = _outerRadius - _innerRadius

    override val area: Float
        get() = PI.toFloat() * (_outerRadius * _outerRadius - _innerRadius * _innerRadius)

    override val perimeter: Float
        get() = (2.0 * PI).toFloat() * (_outerRadius + _innerRadius)

    override fun closestPointTo(point: Vector2F): Vector2F {
        val outerRadius: Float = _outerRadius
        val innerRadius: Float = _innerRadius
        val cx: Float = _center.x
        val cy: Float = _center.y
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

    override fun intersects(annulus: AnnulusShape): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = _innerRadius

        return (innerRadius <= (otherAnnulusOuterRadius + distance)) &&
                (_outerRadius >= (distance - otherAnnulusOuterRadius)) &&
                (otherAnnulusInnerRadius <= (_outerRadius + distance))
    }

    override fun intersects(circle: CircleShape): Boolean {
        val distance: Float = _center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (distance >= (_innerRadius - circleRadius)) &&
                (distance <= (_outerRadius + circleRadius))
    }

    override operator fun contains(point: Vector2F): Boolean {
        val distance: Float = _center.distanceTo(point)

        return distance >= _innerRadius && distance <= _outerRadius
    }

    override operator fun contains(annulus: AnnulusShape): Boolean {
        val distance: Float = _center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = _innerRadius

        return (_outerRadius >= (distance + otherAnnulusOuterRadius)) &&
                ((innerRadius <= (distance - otherAnnulusOuterRadius)) ||
                        (innerRadius <= (distance + otherAnnulusInnerRadius)))
    }

    override operator fun contains(circle: CircleShape): Boolean {
        val distance: Float = _center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (_outerRadius >= (distance + circleRadius)) &&
                (_innerRadius <= (distance - circleRadius))
    }

    override fun copy(
        center: Vector2F, rotation: ComplexF, outerRadius: Float, innerRadius: Float
    ) = MutableAnnulus(center, rotation, outerRadius, innerRadius)

    override fun equals(other: Any?): Boolean = other is Annulus &&
            _center == other.center &&
            _rotation == other.rotation &&
            _outerRadius == other.outerRadius &&
            _innerRadius == other.innerRadius

    fun equals(other: MutableAnnulus): Boolean =
        _center == other._center &&
                _rotation == other._rotation &&
                _outerRadius == other._outerRadius &&
                _innerRadius == other._innerRadius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val rotationHash: Int = _rotation.hashCode()
        val outerRadiusHash: Int = _outerRadius.hashCode()
        val innerRadiusHash: Int = _innerRadius.hashCode()

        return centerHash * 29791 + rotationHash * 961 + outerRadiusHash * 31 + innerRadiusHash
    }

    override fun toString() =
        StringBuilder("Annulus(center=").append(_center)
            .append(", rotation=").append(_rotation)
            .append(", outerRadius=").append(_outerRadius)
            .append(", innerRadius=").append(_innerRadius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): ComplexF = _rotation

    override operator fun component3(): Float = _outerRadius

    override operator fun component4(): Float = _innerRadius
}