package com.sztorm.lowallocmath.euclidean2d

/** Represents a shape of rounded rectangle in a two-dimensional Euclidean space. **/
interface RoundedRectangleShape : Shape {
    /** Returns the width of this rounded rectangle. **/
    val width: Float

    /** Returns the height of this rounded rectangle. **/
    val height: Float

    /** Returns the corner radius of this rounded rectangle. **/
    val cornerRadius: Float
}