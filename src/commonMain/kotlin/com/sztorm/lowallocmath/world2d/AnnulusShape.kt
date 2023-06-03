package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import kotlin.math.PI

interface AnnulusShape : Shape {
    val center: Vector2F
    val outerRadius: Float
    val innerRadius: Float

    val annularRadius: Float
        get() = outerRadius - innerRadius

    override val area: Float
        get() = PI.toFloat() * (outerRadius * outerRadius - innerRadius * innerRadius)

    override val perimeter: Float
        get() = 2f * PI.toFloat() * (outerRadius + innerRadius)
}