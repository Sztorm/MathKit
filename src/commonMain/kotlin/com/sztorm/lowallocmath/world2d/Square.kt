@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused"
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI

data class Square(
    val center: Vector2F,
    val rotation: ComplexF,
    override inline val sideLength: Float
) : RectangleShape, RegularShape {

    override val pointA: Vector2F
    override val pointB: Vector2F
    override val pointC: Vector2F
    override val pointD: Vector2F

    init {
        val halfSideLength: Float = sideLength * 0.5f
        val center: Vector2F = this.center
        val rotation: ComplexF = this.rotation
        val pA = ComplexF(halfSideLength, halfSideLength)
        val pB = ComplexF(-halfSideLength, halfSideLength)
        val pC = ComplexF(-halfSideLength, -halfSideLength)
        val pD = ComplexF(halfSideLength, -halfSideLength)
        pointA = center + (rotation * pA).toVector2F()
        pointB = center + (rotation * pB).toVector2F()
        pointC = center + (rotation * pC).toVector2F()
        pointD = center + (rotation * pD).toVector2F()
    }

    override inline val width: Float
        get() = sideLength

    override inline val height: Float
        get() = sideLength

    override inline val area: Float
        get() = sideLength * sideLength

    override inline val perimeter: Float
        get() = 4f * sideLength

    override inline val interiorAngle: AngleF
        get() = AngleF((PI / 2.0).toFloat())

    override inline val inradius: Float
        get() = 0.5f * sideLength

    override inline val circumradius: Float
        get() = 0.7071068f * sideLength
}