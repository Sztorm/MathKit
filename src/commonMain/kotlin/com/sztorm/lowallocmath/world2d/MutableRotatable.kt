package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface MutableRotatable : Rotatable {
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
}