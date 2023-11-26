package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun Rectangle(center: Vector2F, orientation: ComplexF, width: Float, height: Float): Rectangle =
    MutableRectangle(center, orientation, width, height)

interface Rectangle : RectangleShape, Transformable {
    val center: Vector2F
    val pointA: Vector2F
    val pointB: Vector2F
    val pointC: Vector2F
    val pointD: Vector2F

    override fun movedBy(offset: Vector2F): Rectangle

    override fun movedTo(position: Vector2F): Rectangle

    override fun rotatedBy(rotation: AngleF): Rectangle = rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Rectangle

    override fun rotatedTo(orientation: AngleF): Rectangle =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Rectangle

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Rectangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Rectangle

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Rectangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Rectangle

    override fun scaledBy(factor: Float): Rectangle

    override fun dilatedBy(point: Vector2F, factor: Float): Rectangle

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Rectangle =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Rectangle

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Rectangle =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Rectangle

    override fun transformedTo(position: Vector2F, orientation: AngleF): Rectangle =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Rectangle

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        width: Float = this.width,
        height: Float = this.height
    ): Rectangle

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float

    operator fun component4(): Float
}