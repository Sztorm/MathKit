package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface Rotatable {
    val rotation: ComplexF

    fun rotatedBy(angle: AngleF): Rotatable = rotatedBy(ComplexF.fromAngle(angle))

    fun rotatedBy(rotation: ComplexF): Rotatable

    fun rotatedTo(angle: AngleF): Rotatable = rotatedTo(ComplexF.fromAngle(angle))

    fun rotatedTo(rotation: ComplexF): Rotatable

    fun rotatedAroundPointBy(point: Vector2F, angle: AngleF): Rotatable =
        rotatedAroundPointBy(point, ComplexF.fromAngle(angle))

    fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Rotatable

    fun rotatedAroundPointTo(point: Vector2F, angle: AngleF): Rotatable =
        rotatedAroundPointTo(point, ComplexF.fromAngle(angle))

    fun rotatedAroundPointTo(point: Vector2F, rotation: ComplexF): Rotatable
}