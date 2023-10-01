package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface MutableTransformable : Transformable, MutableMovable, MutableRotatable, MutableScalable {
    fun transformBy(offset: Vector2F, angle: AngleF) =
        transformBy(offset, ComplexF.fromAngle(angle))

    fun transformBy(offset: Vector2F, rotation: ComplexF)

    fun transformBy(offset: Vector2F, angle: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(angle), factor)

    fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float)

    fun transformTo(position: Vector2F, angle: AngleF) =
        transformTo(position, ComplexF.fromAngle(angle))

    fun transformTo(position: Vector2F, rotation: ComplexF)
}