package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator
import com.sztorm.lowallocmath.Vector2FList

fun RegularPolygon(
    center: Vector2F, rotation: ComplexF, sideLength: Float, sideCount: Int
): RegularPolygon = MutableRegularPolygon(center, rotation, sideLength, sideCount)

interface RegularPolygon : RegularShape {
    val center: Vector2F
    val rotation: ComplexF
    val points: Vector2FList

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        sideLength: Float = this.sideLength,
        sideCount: Int = this.sideCount
    ): RegularPolygon

    fun toRegularTriangleOrNull(): RegularTriangle?

    fun toSquareOrNull(): Square?

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float

    operator fun component4(): Int
}

