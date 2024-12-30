package com.sztorm.mathkit.euclidean2d

/** Represents a shape in a two-dimensional Euclidean space. **/
interface Shape {
    /** Returns the area of this shape. **/
    val area: Float

    /** Returns the perimeter of this shape. **/
    val perimeter: Float
}