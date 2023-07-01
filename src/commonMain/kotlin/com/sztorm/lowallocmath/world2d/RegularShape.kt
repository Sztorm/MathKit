package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF

interface RegularShape : Shape {
    val sideLength: Float
    val interiorAngle: AngleF
    val inradius: Float
    val circumradius: Float
}