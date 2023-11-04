package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface Transformable : Movable, Rotatable, Scalable {
    override fun movedBy(offset: Vector2F): Transformable

    override fun movedTo(position: Vector2F): Transformable

    override fun rotatedBy(rotation: AngleF): Transformable =
        rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Transformable

    override fun rotatedTo(orientation: AngleF): Transformable =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Transformable

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Transformable =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Transformable

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Transformable =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Transformable

    override fun scaledBy(factor: Float): Transformable

    override fun dilatedBy(point: Vector2F, factor: Float): Transformable

    fun transformedBy(offset: Vector2F, rotation: AngleF): Transformable =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    fun transformedBy(offset: Vector2F, rotation: ComplexF): Transformable

    fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Transformable =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Transformable

    fun transformedTo(position: Vector2F, orientation: AngleF): Transformable =
        transformedTo(position, ComplexF.fromAngle(orientation))

    fun transformedTo(position: Vector2F, orientation: ComplexF): Transformable
}