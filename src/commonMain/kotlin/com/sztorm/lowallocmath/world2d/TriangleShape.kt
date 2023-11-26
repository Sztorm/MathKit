package com.sztorm.lowallocmath.world2d

interface TriangleShape : Shape {
    val sideLengthAB: Float
    val sideLengthBC: Float
    val sideLengthCA: Float
}