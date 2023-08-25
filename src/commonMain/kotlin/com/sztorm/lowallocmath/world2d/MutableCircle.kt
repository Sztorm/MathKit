package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.sqrt

class MutableCircle(center: Vector2F, radius: Float) : Circle {
    private var _center: Vector2F = center
    private var _radius: Float = radius

    override val center: Vector2F
        get() = _center

    override val radius: Float
        get() = _radius

    override val area: Float
        get() = PI.toFloat() * _radius * _radius

    override val perimeter: Float
        get() = 2f * PI.toFloat() * _radius

    override val diameter: Float
        get() = 2f * _radius

    override fun closestPointTo(point: Vector2F): Vector2F {
        val radius: Float = _radius
        val cx: Float = _center.x
        val cy: Float = _center.y
        val dx: Float = point.x - cx
        val dy: Float = point.y - cy
        val distance: Float = sqrt(dx * dx + dy * dy)
        val t = radius / distance

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

    override fun copy(center: Vector2F, radius: Float) = MutableCircle(center, radius)

    override fun equals(other: Any?): Boolean = other is Circle &&
            _center == other.center &&
            _radius == other.radius

    fun equals(other: MutableCircle): Boolean = _center == other._center && _radius == other.radius

    override fun hashCode(): Int {
        val centerHash: Int = _center.hashCode()
        val radiusHash: Int = _radius.hashCode()

        return centerHash * 31 + radiusHash
    }

    override fun toString() =
        StringBuilder("Circle(center=").append(_center)
            .append(", radius=").append(_radius).append(")")
            .toString()

    override operator fun component1(): Vector2F = _center

    override operator fun component2(): Float = _radius
}