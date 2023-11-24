package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface MutableTransformable : Transformable {
    fun moveBy(offset: Vector2F)

    fun moveTo(position: Vector2F)

    fun rotateBy(rotation: AngleF) = rotateBy(ComplexF.fromAngle(rotation))

    fun rotateBy(rotation: ComplexF)

    fun rotateTo(orientation: AngleF) = rotateTo(ComplexF.fromAngle(orientation))

    fun rotateTo(orientation: ComplexF)

    fun rotateAroundPointBy(point: Vector2F, rotation: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(rotation))

    fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF)

    fun rotateAroundPointTo(point: Vector2F, orientation: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(orientation))

    fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF)

    fun scaleBy(factor: Float)

    fun dilateBy(point: Vector2F, factor: Float)

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