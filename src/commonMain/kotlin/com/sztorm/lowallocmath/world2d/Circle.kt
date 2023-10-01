package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

fun Circle(center: Vector2F, rotation: ComplexF, radius: Float): Circle =
    MutableCircle(center, rotation, radius)

interface Circle : CircleShape {
    val rotation: ComplexF

    fun closestPointTo(point: Vector2F): Vector2F

    fun intersects(annulus: AnnulusShape): Boolean

    fun intersects(circle: CircleShape): Boolean

    operator fun contains(point: Vector2F): Boolean

    operator fun contains(annulus: AnnulusShape): Boolean

    operator fun contains(circle: CircleShape): Boolean

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        radius: Float = this.radius
    ): Circle

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float
}