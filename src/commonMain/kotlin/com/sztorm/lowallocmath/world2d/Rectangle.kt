@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused"
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.*

data class Rectangle(
    val center: Vector2F,
    val rotation: ComplexF,
    override inline val width: Float,
    override inline val height: Float
) : RectangleShape {
    override val pointA: Vector2F
    override val pointB: Vector2F
    override val pointC: Vector2F
    override val pointD: Vector2F

    init {
        val pA = ComplexF(width, height) * 0.5f
        val pB = ComplexF(-pA.real, pA.imaginary)
        val pC = ComplexF(-pA.real, -pA.imaginary)
        val pD = ComplexF(pA.real, -pA.imaginary)
        pointA = center + (rotation * pA).toVector2F()
        pointB = center + (rotation * pB).toVector2F()
        pointC = center + (rotation * pC).toVector2F()
        pointD = center + (rotation * pD).toVector2F()
    }

    override inline val area: Float
        get() = width * height

    override inline val perimeter: Float
        get() {
            val w: Float = width
            val h: Float = height

            return w + w + h + h
        }

    fun closestPointTo(point: Vector2F): Vector2F {
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p2 = ComplexF(
            if (abs(p1X) > halfWidth) halfWidth.withSign(p1X) else p1X,
            if (abs(p1Y) > halfHeight) halfHeight.withSign(p1Y) else p1Y
        )
        return center + (rotation * p2).toVector2F()
    }

    operator fun contains(point: Vector2F): Boolean {
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)

        return (abs(p1.real) <= halfWidth) and (abs(p1.imaginary) <= halfHeight)
    }
}