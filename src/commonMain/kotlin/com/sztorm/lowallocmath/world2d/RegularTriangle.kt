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

data class RegularTriangle(
    val center: Vector2F,
    val rotation: ComplexF,
    override inline val sideLength: Float
) : TriangleShape, RegularShape {
    override val pointA: Vector2F
    override val pointB: Vector2F
    override val pointC: Vector2F

    init {
        val halfSideLength: Float = sideLength * 0.5f
        val inradius: Float = this.inradius
        val circumradius: Float = inradius + inradius
        val pA = ComplexF(0f, circumradius)
        val pB = ComplexF(-halfSideLength, -inradius)
        val pC = ComplexF(halfSideLength, -inradius)
        pointA = center + (rotation * pA).toVector2F()
        pointB = center + (rotation * pB).toVector2F()
        pointC = center + (rotation * pC).toVector2F()
    }

    override inline val area: Float
        get() = 0.4330127f * sideLength * sideLength

    override inline val perimeter: Float
        get() = 3f * sideLength

    override inline val interiorAngle: AngleF
        get() = AngleF((PI / 3.0).toFloat())

    inline val inradius: Float
        get() = 0.28867513f * sideLength

    inline val circumradius: Float
        get() = 0.5773503f * sideLength
}