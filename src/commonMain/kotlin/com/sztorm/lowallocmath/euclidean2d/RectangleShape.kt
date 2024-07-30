package com.sztorm.lowallocmath.euclidean2d

/** Represents a shape of rectangle in a two-dimensional Euclidean space. **/
interface RectangleShape : Shape {
    /** Returns the width of this rectangle. **/
    val width: Float

    /** Returns the height of this rectangle. **/
    val height: Float
}