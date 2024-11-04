package com.sztorm.lowallocmath.euclidean2d

/** Represents a shape of triangle in a two-dimensional Euclidean space. **/
interface TriangleShape : Shape {
    /** Returns the side length between points _A_ and _B_ of this triangle. **/
    val sideLengthAB: Float

    /** Returns the side length between points _B_ and _C_ of this triangle. **/
    val sideLengthBC: Float

    /** Returns the side length between points _A_ and _C_ of this triangle. **/
    val sideLengthAC: Float
}