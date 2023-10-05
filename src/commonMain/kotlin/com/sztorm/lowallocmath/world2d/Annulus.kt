package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

fun Annulus(
    center: Vector2F, rotation: ComplexF, outerRadius: Float, innerRadius: Float
): Annulus = MutableAnnulus(center, rotation, outerRadius, innerRadius)

interface Annulus : AnnulusShape, Transformable {
    override fun movedBy(offset: Vector2F): Annulus

    override fun movedTo(position: Vector2F): Annulus

    override fun rotatedBy(angle: AngleF): Annulus = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): Annulus

    override fun rotatedTo(angle: AngleF): Annulus = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): Annulus

    override fun scaledBy(factor: Float): Annulus

    override fun transformedBy(offset: Vector2F, angle: AngleF): Annulus =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Annulus

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): Annulus =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Annulus

    override fun transformedTo(position: Vector2F, angle: AngleF): Annulus =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): Annulus

    fun closestPointTo(point: Vector2F): Vector2F

    fun intersects(annulus: AnnulusShape): Boolean

    fun intersects(circle: CircleShape): Boolean

    operator fun contains(point: Vector2F): Boolean

    operator fun contains(annulus: AnnulusShape): Boolean

    operator fun contains(circle: CircleShape): Boolean

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        outerRadius: Float = this.outerRadius,
        innerRadius: Float = this.innerRadius
    ): Annulus

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float

    operator fun component4(): Float
}