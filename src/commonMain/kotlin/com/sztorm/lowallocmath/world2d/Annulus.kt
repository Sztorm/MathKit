package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

fun Annulus(
    center: Vector2F, orientation: ComplexF, outerRadius: Float, innerRadius: Float
): Annulus = MutableAnnulus(center, orientation, outerRadius, innerRadius)

interface Annulus : AnnulusShape, Transformable {
    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): Annulus

    override fun movedTo(position: Vector2F): Annulus

    override fun rotatedBy(rotation: AngleF): Annulus = rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Annulus

    override fun rotatedTo(orientation: AngleF): Annulus =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Annulus

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Annulus =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Annulus

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Annulus =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Annulus

    override fun scaledBy(factor: Float): Annulus

    override fun dilatedBy(point: Vector2F, factor: Float): Annulus

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Annulus =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Annulus

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Annulus =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Annulus

    override fun transformedTo(position: Vector2F, orientation: AngleF): Annulus =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Annulus

    fun closestPointTo(point: Vector2F): Vector2F

    fun intersects(annulus: AnnulusShape): Boolean

    fun intersects(circle: CircleShape): Boolean

    operator fun contains(point: Vector2F): Boolean

    operator fun contains(annulus: AnnulusShape): Boolean

    operator fun contains(circle: CircleShape): Boolean

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        outerRadius: Float = this.outerRadius,
        innerRadius: Float = this.innerRadius
    ): Annulus

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float

    operator fun component4(): Float
}