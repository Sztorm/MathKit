package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface CircleShape : Shape {
    val center: Vector2F
    val radius: Float
    val diameter: Float
}