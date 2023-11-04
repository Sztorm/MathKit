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

    override val orientation: ComplexF
        get() = direction.toComplexF()

    override fun movedBy(offset: Vector2F): Ray

    override fun movedTo(position: Vector2F): Ray

    override fun rotatedBy(rotation: AngleF): Ray = rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Ray

    override fun rotatedTo(orientation: AngleF): Ray = rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Ray

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Ray =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Ray

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Ray =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Ray

    override fun scaledBy(factor: Float): Ray

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Ray =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Ray

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Ray =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Ray

    override fun transformedTo(position: Vector2F, orientation: AngleF): Ray =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Ray

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun copy(origin: Vector2F = this.origin, direction: Vector2F = this.direction): Ray

    operator fun component1(): Vector2F

    operator fun component2(): Vector2F
}