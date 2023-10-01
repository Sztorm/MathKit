package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface Transformable : Movable, Rotatable, Scalable {
    override fun movedBy(offset: Vector2F): Transformable

    override fun movedTo(position: Vector2F): Transformable

    override fun rotatedBy(angle: AngleF): Transformable = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): Transformable

    override fun rotatedTo(angle: AngleF): Transformable = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): Transformable

    override fun scaledBy(factor: Float): Transformable

    fun transformedBy(offset: Vector2F, angle: AngleF): Transformable =
        transformedBy(offset, ComplexF.fromAngle(angle))

    fun transformedBy(offset: Vector2F, rotation: ComplexF): Transformable

    fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): Transformable =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Transformable

    fun transformedTo(position: Vector2F, angle: AngleF): Transformable =
        transformedTo(position, ComplexF.fromAngle(angle))

    fun transformedTo(position: Vector2F, rotation: ComplexF): Transformable
}