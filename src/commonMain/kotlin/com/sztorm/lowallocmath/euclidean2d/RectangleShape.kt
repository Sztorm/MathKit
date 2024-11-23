package com.sztorm.lowallocmath.euclidean2d

/**
 * Represents a shape of rectangle in a two-dimensional Euclidean space.
 *
 * Direct implementations of this interface must consider that new interface members may be added
 * in the next versions of the library. Implementations that are safe to use from a compatibility
 * perspective are those that are defined in the library, like [Rectangle].
 */
interface RectangleShape : Shape {
    /** Returns the width of this rectangle. **/
    val width: Float

    /** Returns the height of this rectangle. **/
    val height: Float
}