package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF

interface RegularShape : Shape {
    val sideLength: Float
    val sideCount: Int
    val interiorAngle: AngleF
    val exteriorAngle: AngleF
    val inradius: Float
    val circumradius: Float
}