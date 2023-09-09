package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun RegularTriangle(center: Vector2F, rotation: ComplexF, sideLength: Float): RegularTriangle =
    MutableRegularTriangle(center, rotation, sideLength)

interface RegularTriangle : TriangleShape, RegularShape {
    val center: Vector2F
    val rotation: ComplexF

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        sideLength: Float = this.sideLength,
    ): RegularTriangle

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float
}