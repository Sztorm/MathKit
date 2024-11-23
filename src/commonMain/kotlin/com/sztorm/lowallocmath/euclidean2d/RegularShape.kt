package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF

/**
 * Represents a regular shape in a two-dimensional Euclidean space.
 *
 * Direct implementations of this interface must consider that new interface members may be added
 * in the next versions of the library. Implementations that are safe to use from a compatibility
 * perspective are those that are defined in the library, like [RegularPolygon].
 */
interface RegularShape : Shape {
    /** Returns the side length of this shape. **/
    val sideLength: Float

    /** Returns the side count of this shape. **/
    val sideCount: Int

    /** Returns the interior angle of this shape. **/
    val interiorAngle: AngleF

    /** Returns the exterior angle of this shape. **/
    val exteriorAngle: AngleF

    /** Returns the inradius of this shape. **/
    val inradius: Float

    /** Returns the circumradius of this shape. **/
    val circumradius: Float
}