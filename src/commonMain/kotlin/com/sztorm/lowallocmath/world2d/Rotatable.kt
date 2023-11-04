package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface Rotatable {
    val orientation: ComplexF

    fun rotatedBy(rotation: AngleF): Rotatable = rotatedBy(ComplexF.fromAngle(rotation))

    fun rotatedBy(rotation: ComplexF): Rotatable

    fun rotatedTo(orientation: AngleF): Rotatable = rotatedTo(ComplexF.fromAngle(orientation))

    fun rotatedTo(orientation: ComplexF): Rotatable

    fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Rotatable =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Rotatable

    fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Rotatable =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Rotatable
}