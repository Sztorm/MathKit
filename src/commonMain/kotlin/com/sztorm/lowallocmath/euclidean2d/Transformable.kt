package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

/** Represents properties and functions of a transformable object in Euclidean space. **/
interface Transformable {
    /** Returns the position of this object in reference to the origin of [Vector2F.ZERO]. **/
    val position: Vector2F

    /** Returns the orientation of this object in reference to the origin of [ComplexF.ONE]. **/
    val orientation: ComplexF

    /** Returns a copy of this object that is moved by a [displacement]. **/
    fun movedBy(displacement: Vector2F): Transformable

    /** Returns a copy of this object that is moved to the [position]. **/
    fun movedTo(position: Vector2F): Transformable

    /** Returns a copy of this object that is rotated by a [rotation]. **/
    fun rotatedBy(rotation: AngleF): Transformable

    /** Returns a copy of this object that is rotated by a [rotation]. **/
    fun rotatedBy(rotation: ComplexF): Transformable

    /** Returns a copy of this object that is rotated to the [orientation]. **/
    fun rotatedTo(orientation: AngleF): Transformable

    /** Returns a copy of this object that is rotated to the [orientation]. **/
    fun rotatedTo(orientation: ComplexF): Transformable

    /** Returns a copy of this object that is rotated around the [point] by a [rotation]. **/
    fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Transformable

    /** Returns a copy of this object that is rotated around the [point] by a [rotation]. **/
    fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Transformable

    /** Returns a copy of this object that is rotated around the [point] to the [orientation]. **/
    fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Transformable

    /** Returns a copy of this object that is rotated around the [point] to the [orientation]. **/
    fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Transformable

    /** Returns a copy of this object that is scaled by a [factor]. **/
    fun scaledBy(factor: Float): Transformable

    /** Returns a copy of this object that is dilated around the [point] by a [factor]. **/
    fun dilatedBy(point: Vector2F, factor: Float): Transformable

    /**
     * Returns a copy of this object that is moved by a [displacement] and rotated by a [rotation].
     */
    fun transformedBy(displacement: Vector2F, rotation: AngleF): Transformable

    /**
     * Returns a copy of this object that is moved by a [displacement] and rotated by a [rotation].
     */
    fun transformedBy(displacement: Vector2F, rotation: ComplexF): Transformable

    /**
     * Returns a copy of this object that is moved by a [displacement], rotated by a [rotation],
     * and scaled by a [scaleFactor].
     */
    fun transformedBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float): Transformable

    /**
     * Returns a copy of this object that is moved by a [displacement], rotated by a [rotation],
     * and scaled by a [scaleFactor].
     */
    fun transformedBy(
        displacement: Vector2F, rotation: ComplexF, scaleFactor: Float
    ): Transformable

    /**
     * Returns a copy of this object that is moved to the [position] and rotated to the
     * [orientation].
     */
    fun transformedTo(position: Vector2F, orientation: AngleF): Transformable

    /**
     * Returns a copy of this object that is moved to the [position] and rotated to the
     * [orientation].
     */
    fun transformedTo(position: Vector2F, orientation: ComplexF): Transformable
}