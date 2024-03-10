package com.sztorm.lowallocmath.euclidean2d

interface AnnulusShape : Shape {
    val outerRadius: Float
    val innerRadius: Float
    val annularRadius: Float
}