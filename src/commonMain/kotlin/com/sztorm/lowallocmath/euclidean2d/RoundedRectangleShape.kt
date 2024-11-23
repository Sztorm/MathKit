package com.sztorm.lowallocmath.euclidean2d

/**
 * Represents a shape of rounded rectangle in a two-dimensional Euclidean space.
 *
 * Direct implementations of this interface must consider that new interface members may be added
 * in the next versions of the library. Implementations that are safe to use from a compatibility
 * perspective are those that are defined in the library, like [RoundedRectangle].
 */
interface RoundedRectangleShape : Shape {
    /** Returns the width of this rounded rectangle. **/
    val width: Float

    /** Returns the height of this rounded rectangle. **/
    val height: Float

    /** Returns the corner radius of this rounded rectangle. **/
    val cornerRadius: Float
}