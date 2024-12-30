package com.sztorm.mathkit.euclidean2d

/**
 * Represents a shape of triangle in a two-dimensional Euclidean space.
 *
 * Direct implementations of this interface must consider that new interface members may be added
 * in the next versions of the library. Implementations that are safe to use from a compatibility
 * perspective are those that are defined in the library, like [Triangle].
 */
interface TriangleShape : Shape {
    /** Returns the side length between points _A_ and _B_ of this triangle. **/
    val sideLengthAB: Float

    /** Returns the side length between points _B_ and _C_ of this triangle. **/
    val sideLengthBC: Float

    /** Returns the side length between points _A_ and _C_ of this triangle. **/
    val sideLengthAC: Float
}