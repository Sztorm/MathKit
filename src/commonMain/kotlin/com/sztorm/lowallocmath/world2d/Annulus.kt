@file:Suppress(
    "ConvertTwoComparisonsToRangeCheck",
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused",
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.sqrt

data class Annulus(
    override inline val center: Vector2F,
    override inline val outerRadius: Float,
    override inline val innerRadius: Float
) : AnnulusShape {
    override val annularRadius: Float
        inline get() = outerRadius - innerRadius

    override val area: Float
        inline get() = PI.toFloat() * (outerRadius * outerRadius - innerRadius * innerRadius)

    override val perimeter: Float
        inline get() = 2f * PI.toFloat() * (outerRadius + innerRadius)

    fun closestPointTo(point: Vector2F): Vector2F {
        val cx: Float = center.x
        val cy: Float = center.y
        val dx: Float = cx - point.x
        val dy: Float = cy - point.y
        val distance: Float = sqrt(dx * dx + dy * dy)

        return when {
            distance < innerRadius -> Vector2F(
                cx - (dx / distance) * innerRadius,
                cy - (dy / distance) * innerRadius
            )

            distance > outerRadius -> Vector2F(
                cx - (dx / distance) * outerRadius,
                cy - (dy / distance) * outerRadius
            )

            else -> point
        }
    }

    inline fun <reified TAnnulus : AnnulusShape> intersects(annulus: TAnnulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = innerRadius

        return (innerRadius <= (otherAnnulusOuterRadius + distance)) &&
               (outerRadius >= (distance - otherAnnulusOuterRadius)) &&
               (otherAnnulusInnerRadius <= (outerRadius + distance))
    }

    inline fun <reified TCircle : CircleShape> intersects(circle: TCircle): Boolean {
        val distance: Float = center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (distance >= (innerRadius - circleRadius)) &&
               (distance <= (outerRadius + circleRadius))
    }

    operator fun contains(point: Vector2F): Boolean {
        val distance: Float = center.distanceTo(point)

        return distance >= innerRadius && distance <= outerRadius
    }

    inline operator fun <reified TAnnulus : AnnulusShape> contains(annulus: TAnnulus): Boolean {
        val distance: Float = center.distanceTo(annulus.center)
        val otherAnnulusOuterRadius: Float = annulus.outerRadius
        val otherAnnulusInnerRadius: Float = annulus.innerRadius
        val innerRadius: Float = innerRadius

        return (outerRadius >= (distance + otherAnnulusOuterRadius)) &&
               ((innerRadius <= (distance - otherAnnulusOuterRadius)) ||
               (innerRadius <= (distance + otherAnnulusInnerRadius)))
    }

    inline operator fun <reified TCircle : CircleShape> contains(circle: TCircle): Boolean {
        val distance: Float = center.distanceTo(circle.center)
        val circleRadius: Float = circle.radius

        return (outerRadius >= (distance + circleRadius)) &&
               (innerRadius <= (distance - circleRadius))
    }
}