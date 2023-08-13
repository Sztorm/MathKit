@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused"
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.withSign

data class RoundedRectangle(
    val center: Vector2F,
    val rotation: ComplexF,
    override inline val width: Float,
    override inline val height: Float,
    override inline val cornerRadius: Float,
) : RoundedRectangleShape {
    override val pointA: Vector2F
    override val pointB: Vector2F
    override val pointC: Vector2F
    override val pointD: Vector2F
    override val pointE: Vector2F
    override val pointF: Vector2F
    override val pointG: Vector2F
    override val pointH: Vector2F
    override val cornerCenterA: Vector2F
    override val cornerCenterB: Vector2F
    override val cornerCenterC: Vector2F
    override val cornerCenterD: Vector2F

    init {
        val center: Vector2F = this.center
        val rotation: ComplexF = this.rotation
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val pA = ComplexF(halfWidthMinusRadius, halfHeight)
        val pB = ComplexF(-halfWidthMinusRadius, halfHeight)
        val pC = ComplexF(-halfWidth, halfHeightMinusRadius)
        val pD = ComplexF(-halfWidth, -halfHeightMinusRadius)
        val pE = ComplexF(-halfWidthMinusRadius, -halfHeight)
        val pF = ComplexF(halfWidthMinusRadius, -halfHeight)
        val pG = ComplexF(halfWidth, -halfHeightMinusRadius)
        val pH = ComplexF(halfWidth, halfHeightMinusRadius)
        val ccA = ComplexF(halfWidthMinusRadius, halfHeightMinusRadius)
        val ccB = ComplexF(-halfWidthMinusRadius, halfHeightMinusRadius)
        val ccC = ComplexF(-halfWidthMinusRadius, -halfHeightMinusRadius)
        val ccD = ComplexF(halfWidthMinusRadius, -halfHeightMinusRadius)
        pointA = center + (rotation * pA).toVector2F()
        pointB = center + (rotation * pB).toVector2F()
        pointC = center + (rotation * pC).toVector2F()
        pointD = center + (rotation * pD).toVector2F()
        pointE = center + (rotation * pE).toVector2F()
        pointF = center + (rotation * pF).toVector2F()
        pointG = center + (rotation * pG).toVector2F()
        pointH = center + (rotation * pH).toVector2F()
        cornerCenterA = center + (rotation * ccA).toVector2F()
        cornerCenterB = center + (rotation * ccB).toVector2F()
        cornerCenterC = center + (rotation * ccC).toVector2F()
        cornerCenterD = center + (rotation * ccD).toVector2F()
    }

    override inline val area: Float
        get() {
            val radius: Float = this.cornerRadius
            val squaredRadius: Float = radius * radius

            return PI.toFloat() * squaredRadius + width * height - 4f * squaredRadius
        }

    override inline val perimeter: Float
        get() {
            val radius: Float = this.cornerRadius

            return (2.0 * PI).toFloat() * radius + 2f * (width + height - 4f * radius)
        }

    fun closestPointTo(point: Vector2F): Vector2F {
        val rotation: ComplexF = this.rotation
        val center: Vector2F = this.center
        val cornerRadius: Float = this.cornerRadius
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p1XAbs: Float = abs(p1X)
        val p1YAbs: Float = abs(p1Y)
        val cornerCenterX: Float = halfWidthMinusRadius.withSign(p1X)
        val cornerCenterY: Float = halfHeightMinusRadius.withSign(p1Y)
        val dx: Float = p1X - cornerCenterX
        val dy: Float = p1Y - cornerCenterY
        val distance: Float = sqrt(dx * dx + dy * dy)
        val isOutOfCorner: Boolean = (p1YAbs > halfHeightMinusRadius) and
                (p1XAbs > halfWidthMinusRadius) and
                (distance > cornerRadius)

        return when {
            isOutOfCorner -> {
                val t: Float = cornerRadius / distance

                center + (rotation * ComplexF(
                    (cornerCenterX + dx * t),
                    (cornerCenterY + dy * t)
                )).toVector2F()
            }

            p1XAbs > halfWidth -> center + (rotation * ComplexF(
                halfWidth.withSign(p1X),
                p1Y
            )).toVector2F()

            p1YAbs > halfHeight -> center + (rotation * ComplexF(
                p1X,
                halfHeight.withSign(p1Y)
            )).toVector2F()

            else -> point
        }
    }

    operator fun contains(point: Vector2F): Boolean {
        val rotation: ComplexF = this.rotation
        val center: Vector2F = this.center
        val cornerRadius: Float = this.cornerRadius
        val halfWidth: Float = width * 0.5f
        val halfHeight: Float = height * 0.5f
        val halfWidthMinusRadius: Float = halfWidth - cornerRadius
        val halfHeightMinusRadius: Float = halfHeight - cornerRadius
        val p1 = ComplexF.conjugate(rotation) *
                ComplexF(point.x - center.x, point.y - center.y)
        val p1X: Float = p1.real
        val p1Y: Float = p1.imaginary
        val p1XAbs: Float = abs(p1X)
        val p1YAbs: Float = abs(p1Y)
        val cornerCenterX: Float = halfWidthMinusRadius.withSign(p1X)
        val cornerCenterY: Float = halfHeightMinusRadius.withSign(p1Y)
        val dx: Float = p1X - cornerCenterX
        val dy: Float = p1Y - cornerCenterY
        val distance: Float = sqrt(dx * dx + dy * dy)

        return (p1YAbs <= halfHeightMinusRadius) or
                (p1XAbs <= halfWidthMinusRadius) or
                (distance <= cornerRadius) and
                (p1XAbs <= halfWidth) and
                (p1YAbs <= halfHeight)
    }
}