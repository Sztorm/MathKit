package com.sztorm.mathkit.euclidean2d

/**
 * Represents a shape of circle in a two-dimensional Euclidean space.
 *
 * Direct implementations of this interface must consider that new interface members may be added
 * in the next versions of the library. Implementations that are safe to use from a compatibility
 * perspective are those that are defined in the library, like [Circle].
 */
interface CircleShape : Shape {
    /** Returns the radius of this circle. **/
    val radius: Float

    /** Returns the diameter of this circle. **/
    val diameter: Float
}