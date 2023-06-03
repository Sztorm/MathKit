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
    override val area: Float
        inline get() = PI.toFloat() * radius * radius

    override val perimeter: Float
        inline get() = 2f * PI.toFloat() * radius

    override val diameter: Float
        inline get() = 2f * radius

    fun closestPointTo(point: Vector2F): Vector2F {
        val cx: Float = center.x
        val cy: Float = center.y
        val dx: Float = cx - point.x
        val dy: Float = cy - point.y
        val distance: Float = sqrt(dx * dx + dy * dy)

        return if (distance > radius) Vector2F(
            cx - (dx / distance) * radius,
            cy - (dy / distance) * radius
        )
        else point
    }

    inline fun <reified TAnnulus : AnnulusShape> intersects(annulus: TAnnulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val radius: Float = radius

        return (distance >= (annulus.innerRadius - radius)) &&
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