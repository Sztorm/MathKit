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

    val diameter: Float
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

    fun intersects(circle: Circle): Boolean =
        center.distanceTo(circle.center) <= radius + circle.radius

    operator fun contains(point: Vector2F): Boolean = center.distanceTo(point) <= radius

    operator fun contains(circle: Circle): Boolean =
        center.distanceTo(circle.center) <= radius - circle.radius
}