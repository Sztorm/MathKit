package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

fun Ray(origin: Vector2F, direction: Vector2F): Ray = MutableRay(origin, direction)

interface Ray {
    val origin: Vector2F
    val direction: Vector2F

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun copy(origin: Vector2F = this.origin, direction: Vector2F = this.direction): Ray

    operator fun component1(): Vector2F

    operator fun component2(): Vector2F
}