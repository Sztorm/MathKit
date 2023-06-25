package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface TriangleShape : Shape {
    val pointA: Vector2F
    val pointB: Vector2F
    val pointC: Vector2F
}