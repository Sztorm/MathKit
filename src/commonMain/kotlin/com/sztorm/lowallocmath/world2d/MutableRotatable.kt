package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface MutableRotatable : Rotatable {
    fun rotateBy(angle: AngleF) = rotateBy(ComplexF.fromAngle(angle))

    fun rotateBy(rotation: ComplexF)

    fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    fun rotateTo(rotation: ComplexF)

    fun rotateAroundPointBy(point: Vector2F, angle: AngleF) =
        rotateAroundPointBy(point, ComplexF.fromAngle(angle))

    fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF)

    fun rotateAroundPointTo(point: Vector2F, angle: AngleF) =
        rotateAroundPointTo(point, ComplexF.fromAngle(angle))

    fun rotateAroundPointTo(point: Vector2F, rotation: ComplexF)
}