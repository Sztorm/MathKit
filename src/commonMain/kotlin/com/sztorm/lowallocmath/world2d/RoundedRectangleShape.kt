package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI

interface RoundedRectangleShape : Shape {
    val width: Float
    val height: Float
    val cornerRadius: Float
    val pointA: Vector2F
    val pointB: Vector2F
    val pointC: Vector2F
    val pointD: Vector2F
    val pointE: Vector2F
    val pointF: Vector2F
    val pointG: Vector2F
    val pointH: Vector2F
    val cornerCenterA: Vector2F
    val cornerCenterB: Vector2F
    val cornerCenterC: Vector2F
    val cornerCenterD: Vector2F

    override val area: Float
        get() {
            val radius: Float = this.cornerRadius
            val squaredRadius: Float = radius * radius

            return PI.toFloat() * squaredRadius + width * height - 4f * squaredRadius
        }

    override val perimeter: Float
        get() {
            val radius: Float = this.cornerRadius

            return (2.0 * PI).toFloat() * radius + 2f * (width + height - 4f * radius)
        }
}