package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface RectangleShape : Shape {
    val width: Float
    val height: Float
    val pointA: Vector2F
    val pointB: Vector2F
    val pointC: Vector2F
    val pointD: Vector2F

    override val area: Float
        get() = width * height

    override val perimeter: Float
        get() {
            val w: Float = width
            val h: Float = height

            return w + w + h + h
        }
}