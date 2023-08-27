package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface RoundedRectangleShape : Shape {
    val width: Float
    val height: Float
    val cornerRadius: Float
    val pointA: Vector2F
    val pointB: Vector2F
    val pointC: Vector2F
    val pointD: Vector2F
    val pointE: Vector2F
    val pointF: Vector2F
    val pointG: Vector2F
    val pointH: Vector2F
    val cornerCenterA: Vector2F
    val cornerCenterB: Vector2F
    val cornerCenterC: Vector2F
    val cornerCenterD: Vector2F
}