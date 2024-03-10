package com.sztorm.lowallocmath.euclidean2d

interface TriangleShape : Shape {
    val sideLengthAB: Float
    val sideLengthBC: Float
    val sideLengthCA: Float
}