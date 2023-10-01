package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF

interface Rotatable {
    val rotation: ComplexF

    fun rotatedBy(angle: AngleF): Rotatable = rotatedBy(ComplexF.fromAngle(angle))

    fun rotatedBy(rotation: ComplexF): Rotatable

    fun rotatedTo(angle: AngleF): Rotatable = rotatedTo(ComplexF.fromAngle(angle))

    fun rotatedTo(rotation: ComplexF): Rotatable
}