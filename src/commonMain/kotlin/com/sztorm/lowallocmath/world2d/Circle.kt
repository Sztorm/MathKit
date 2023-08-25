package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

fun Circle(center: Vector2F, radius: Float): Circle = MutableCircle(center, radius)

interface Circle : CircleShape {
    fun closestPointTo(point: Vector2F): Vector2F

    fun intersects(annulus: AnnulusShape): Boolean

    fun intersects(circle: CircleShape): Boolean

    operator fun contains(point: Vector2F): Boolean

    operator fun contains(annulus: AnnulusShape): Boolean

    operator fun contains(circle: CircleShape): Boolean

    fun copy(center: Vector2F = this.center, radius: Float = this.radius): Circle

    operator fun component1(): Vector2F

    operator fun component2(): Float
}