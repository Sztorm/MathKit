package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface AnnulusShape : Shape {
    val center: Vector2F
    val outerRadius: Float
    val innerRadius: Float
    val annularRadius: Float
}