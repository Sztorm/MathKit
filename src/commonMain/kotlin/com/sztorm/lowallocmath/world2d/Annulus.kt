@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

data class Annulus(val center: Vector2F, val outerRadius: Float, val innerRadius: Float) {
    val annularRadius: Float
        get() = outerRadius - innerRadius
}