package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface MutableTransformable : Transformable {
    fun moveBy(displacement: Vector2F)

    fun moveTo(position: Vector2F)

    fun rotateBy(rotation: AngleF)

    fun rotateBy(rotation: ComplexF)

    fun rotateTo(orientation: AngleF)

    fun rotateTo(orientation: ComplexF)

    fun rotateAroundPointBy(point: Vector2F, rotation: AngleF)

    fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF)

    fun rotateAroundPointTo(point: Vector2F, orientation: AngleF)

    fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF)

    fun scaleBy(factor: Float)

    fun dilateBy(point: Vector2F, factor: Float)

    fun transformBy(displacement: Vector2F, rotation: AngleF)

    fun transformBy(displacement: Vector2F, rotation: ComplexF)

    fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float)

    fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float)

    fun transformTo(position: Vector2F, orientation: AngleF)

    fun transformTo(position: Vector2F, orientation: ComplexF)
}