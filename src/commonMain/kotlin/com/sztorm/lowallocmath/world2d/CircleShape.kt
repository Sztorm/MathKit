package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI

interface CircleShape : Shape {
    val center: Vector2F
    val radius: Float

    override val area: Float
        get() = PI.toFloat() * radius * radius

    override val perimeter: Float
        get() = 2f * PI.toFloat() * radius
}