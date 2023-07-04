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
import kotlin.math.absoluteValue
import kotlin.math.withSign

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

    override inline val sideCount: Int
        get() = 4

    override inline val interiorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override inline val exteriorAngle: AngleF
        get() = AngleF((0.5 * PI).toFloat())

    override inline val inradius: Float
        get() = 0.5f * sideLength

    override inline val circumradius: Float
        get() = 0.7071068f * sideLength

    fun closestPointTo(point: Vector2F): Vector2F {
        val halfSideLength: Float = sideLength * 0.5f
        val center: Vector2F = this.center
        val rotation: ComplexF = this.rotation
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p2 = ComplexF(
            if (p1X.absoluteValue > halfSideLength) halfSideLength.withSign(p1X) else p1X,
            if (p1Y.absoluteValue > halfSideLength) halfSideLength.withSign(p1Y) else p1Y
        )
        return center + (rotation * p2).toVector2F()
    }

    operator fun contains(point: Vector2F): Boolean {
        val halfSideLength: Float = sideLength * 0.5f
        val center: Vector2F = this.center
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)

        return (p1.real.absoluteValue <= halfSideLength) and
                (p1.imaginary.absoluteValue <= halfSideLength)
    }
}