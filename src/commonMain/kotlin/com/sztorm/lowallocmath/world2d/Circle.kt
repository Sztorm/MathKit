@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused",
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.sqrt

data class Circle(
    override inline val center: Vector2F,
    override inline val radius: Float
) : CircleShape {
    override inline val area: Float
        get() = PI.toFloat() * radius * radius

    override inline val perimeter: Float
        get() = 2f * PI.toFloat() * radius

    override inline val diameter: Float
        get() = 2f * radius

    fun closestPointTo(point: Vector2F): Vector2F {
        val cx: Float = center.x
        val cy: Float = center.y
        val dx: Float = point.x - cx
        val dy: Float = point.y - cy
        val distance: Float = sqrt(dx * dx + dy * dy)
        val t = radius / distance

        return if (distance > radius) Vector2F(cx + dx * t, cy + dy * t)
        else point
    }

    inline fun <reified TAnnulus : AnnulusShape> intersects(annulus: TAnnulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val radius: Float = this.radius

        return (distance >= (annulus.innerRadius - radius)) and
                (distance <= (annulus.outerRadius + radius))
    }

    inline fun <reified TCircle : CircleShape> intersects(circle: TCircle): Boolean =
        center.distanceTo(circle.center) <= radius + circle.radius

    operator fun contains(point: Vector2F): Boolean = center.distanceTo(point) <= radius

    inline operator fun <reified TAnnulus : AnnulusShape> contains(annulus: TAnnulus): Boolean =
        center.distanceTo(annulus.center) <= radius - annulus.outerRadius

    inline operator fun <reified TCircle : CircleShape> contains(circle: TCircle): Boolean =
        center.distanceTo(circle.center) <= radius - circle.radius
}