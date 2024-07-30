package com.sztorm.lowallocmath.euclidean2d

/** Represents a shape of annulus in a two-dimensional Euclidean space. **/
interface AnnulusShape : Shape {
    /** Returns the outer radius of this annulus. **/
    val outerRadius: Float

    /** Returns the inner radius of this annulus. **/
    val innerRadius: Float

    /**
     * Returns the annular radius of this annulus. Annular radius is the difference between the
     * outer radius and the inner radius.
     */
    val annularRadius: Float
}