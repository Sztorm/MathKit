package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

fun Ray(origin: Vector2F, direction: Vector2F): Ray = MutableRay(origin, direction)

interface Ray : Transformable {
    val origin: Vector2F
    val direction: Vector2F

    override val position: Vector2F
        get() = origin

    override val rotation: ComplexF
        get() = direction.toComplexF()

    override fun movedBy(offset: Vector2F): Ray

    override fun movedTo(position: Vector2F): Ray

    override fun rotatedBy(angle: AngleF): Ray = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): Ray

    override fun rotatedTo(angle: AngleF): Ray = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): Ray

    override fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): Ray =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Ray

    override fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): Ray =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    override fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): Ray

    override fun scaledBy(factor: Float): Ray

    override fun transformedBy(offset: Vector2F, angle: AngleF): Ray =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Ray

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): Ray =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Ray

    override fun transformedTo(position: Vector2F, angle: AngleF): Ray =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): Ray

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun copy(origin: Vector2F = this.origin, direction: Vector2F = this.direction): Ray

    operator fun component1(): Vector2F

    operator fun component2(): Vector2F
}