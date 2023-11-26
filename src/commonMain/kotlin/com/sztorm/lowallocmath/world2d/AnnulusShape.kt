package com.sztorm.lowallocmath.world2d

interface AnnulusShape : Shape {
    val outerRadius: Float
    val innerRadius: Float
    val annularRadius: Float
}