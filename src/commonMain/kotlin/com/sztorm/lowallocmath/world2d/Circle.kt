package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

fun Circle(center: Vector2F, rotation: ComplexF, radius: Float): Circle =
    MutableCircle(center, rotation, radius)

interface Circle : CircleShape, Transformable {
    override fun movedBy(offset: Vector2F): Circle

    override fun movedTo(position: Vector2F): Circle

    override fun rotatedBy(angle: AngleF): Circle = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): Circle

    override fun rotatedTo(angle: AngleF): Circle = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): Circle

    override fun scaledBy(factor: Float): Circle

    override fun transformedBy(offset: Vector2F, angle: AngleF): Circle =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Circle

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): Circle =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Circle

    override fun transformedTo(position: Vector2F, angle: AngleF): Circle =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): Circle

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