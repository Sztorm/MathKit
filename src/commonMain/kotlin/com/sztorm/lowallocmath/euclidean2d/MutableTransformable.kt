package com.sztorm.lowallocmath.euclidean2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

/** Represents properties and functions of a mutable transformable object in Euclidean space. **/
interface MutableTransformable : Transformable {
    /** Moves this object by a [displacement]. **/
    fun moveBy(displacement: Vector2F)

    /** Moves this object to the [position]. **/
    fun moveTo(position: Vector2F)

    /** Rotates this object by a [rotation]. **/
    fun rotateBy(rotation: AngleF)

    /** Rotates this object by a [rotation]. **/
    fun rotateBy(rotation: ComplexF)

    /** Rotates this object to the [orientation]. **/
    fun rotateTo(orientation: AngleF)

    /** Rotates this object to the [orientation]. **/
    fun rotateTo(orientation: ComplexF)

    /** Rotates this object around the [point] by a [rotation]. **/
    fun rotateAroundPointBy(point: Vector2F, rotation: AngleF)

    /** Rotates this object around the [point] by a [rotation]. **/
    fun rotateAroundPointBy(point: Vector2F, rotation: ComplexF)

    /** Rotates this object around the [point] to the [orientation]. **/
    fun rotateAroundPointTo(point: Vector2F, orientation: AngleF)

    /** Rotates this object around the [point] to the [orientation]. **/
    fun rotateAroundPointTo(point: Vector2F, orientation: ComplexF)

    /** Scales this object by a [factor]. **/
    fun scaleBy(factor: Float)

    /** Dilates this object around the [point] by a [factor]. **/
    fun dilateBy(point: Vector2F, factor: Float)

    /** Transforms this object by moving by a [displacement] and rotating by a [rotation]. **/
    fun transformBy(displacement: Vector2F, rotation: AngleF)

    /** Transforms this object by moving by a [displacement] and rotating by a [rotation]. **/
    fun transformBy(displacement: Vector2F, rotation: ComplexF)

    /**
     * Transforms this object by moving by a [displacement], rotating by a [rotation], and scaling
     * by a [scaleFactor].
     */
    fun transformBy(displacement: Vector2F, rotation: AngleF, scaleFactor: Float)

    /**
     * Transforms this object by moving by a [displacement], rotating by a [rotation], and scaling
     * by a [scaleFactor].
     */
    fun transformBy(displacement: Vector2F, rotation: ComplexF, scaleFactor: Float)

    /** Transforms this object by moving to the [position] and rotating to the [orientation]. **/
    fun transformTo(position: Vector2F, orientation: AngleF)

    /** Transforms this object by moving to the [position] and rotating to the [orientation]. **/
    fun transformTo(position: Vector2F, orientation: ComplexF)
}