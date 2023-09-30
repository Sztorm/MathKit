package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun Triangle(pointA: Vector2F, pointB: Vector2F, pointC: Vector2F): Triangle =
    MutableTriangle(pointA, pointB, pointC)

interface Triangle : TriangleShape {
    fun pointIterator(): Vector2FIterator

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun copy(
        pointA: Vector2F = this.pointA,
        pointB: Vector2F = this.pointB,
        pointC: Vector2F = this.pointC
    ): Triangle

    operator fun component1(): Vector2F

    operator fun component2(): Vector2F

    operator fun component3(): Vector2F
}