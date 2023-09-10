package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun LineSegment(pointA: Vector2F, pointB: Vector2F): LineSegment =
    MutableLineSegment(pointA, pointB)

interface LineSegment {
    val pointA: Vector2F
    val pointB: Vector2F
    val center: Vector2F
    val length: Float

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(pointA: Vector2F = this.pointA, pointB: Vector2F = this.pointB): LineSegment

    operator fun component1(): Vector2F

    operator fun component2(): Vector2F
}