package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface MutableTransformable : Transformable, MutableMovable, MutableRotatable, MutableScalable {
    fun transformBy(offset: Vector2F, rotation: AngleF) =
        transformBy(offset, ComplexF.fromAngle(rotation))

    fun transformBy(offset: Vector2F, rotation: ComplexF)

    fun transformBy(offset: Vector2F, rotation: AngleF, factor: Float) =
        transformBy(offset, ComplexF.fromAngle(rotation), factor)

    fun transformBy(offset: Vector2F, rotation: ComplexF, factor: Float)

    fun transformTo(position: Vector2F, orientation: AngleF) =
        transformTo(position, ComplexF.fromAngle(orientation))

    fun transformTo(position: Vector2F, orientation: ComplexF)
}