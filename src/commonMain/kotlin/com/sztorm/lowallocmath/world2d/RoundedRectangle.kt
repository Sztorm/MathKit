@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused"
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI

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
}