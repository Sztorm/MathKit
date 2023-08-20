@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "unused"
)

package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface Rectangle : RectangleShape {
    val center: Vector2F
    val rotation: ComplexF

    fun closestPointTo(point: Vector2F): Vector2F
    operator fun contains(point: Vector2F): Boolean
}

fun Rectangle(center: Vector2F, rotation: ComplexF, width: Float, height: Float): Rectangle =
    MutableRectangle(center, rotation, width, height)

