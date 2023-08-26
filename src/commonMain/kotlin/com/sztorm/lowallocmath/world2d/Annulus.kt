package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

fun Annulus(center: Vector2F, outerRadius: Float, innerRadius: Float): Annulus =
    MutableAnnulus(center, outerRadius, innerRadius)

interface Annulus : AnnulusShape {
    fun closestPointTo(point: Vector2F): Vector2F

    fun intersects(annulus: AnnulusShape): Boolean

    fun intersects(circle: CircleShape): Boolean

    operator fun contains(point: Vector2F): Boolean

    operator fun contains(annulus: AnnulusShape): Boolean

    operator fun contains(circle: CircleShape): Boolean

    fun copy(
        center: Vector2F = this.center,
        outerRadius: Float = this.outerRadius,
        innerRadius: Float = this.innerRadius
    ): Annulus

    operator fun component1(): Vector2F

    operator fun component2(): Float

    operator fun component3(): Float
}