package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF

interface MutableRotatable : Rotatable {
    fun rotateBy(angle: AngleF) = rotateBy(ComplexF.fromAngle(angle))

    fun rotateBy(rotation: ComplexF)

    fun rotateTo(angle: AngleF) = rotateTo(ComplexF.fromAngle(angle))

    fun rotateTo(rotation: ComplexF)
}