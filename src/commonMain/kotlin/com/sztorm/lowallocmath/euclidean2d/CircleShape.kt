package com.sztorm.lowallocmath.euclidean2d

/** Represents a shape of circle in a two-dimensional Euclidean space. **/
interface CircleShape : Shape {
    /** Returns the radius of this circle. **/
    val radius: Float

    /** Returns the diameter of this circle. **/
    val diameter: Float
}