package com.sztorm.lowallocmath.euclidean2d

/** Represents a shape of annulus in a two-dimensional Euclidean space. **/
interface AnnulusShape : Shape {
    /** Returns the outer radius of this annulus. **/
    val outerRadius: Float

    /** Returns the inner radius of this annulus. **/
    val innerRadius: Float

    /**
     * Returns the width of this annulus. Width is the difference between the outer radius and the
     * inner radius.
     */
    val width: Float

    /** Returns the outer diameter of this annulus. **/
    val outerDiameter: Float

    /** Returns the inner diameter of this annulus. **/
    val innerDiameter: Float
}