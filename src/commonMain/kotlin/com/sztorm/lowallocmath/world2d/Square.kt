package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun Square(center: Vector2F, orientation: ComplexF, sideLength: Float): Square =
    MutableSquare(center, orientation, sideLength)

interface Square : RectangleShape, RegularShape, Transformable {
    val center: Vector2F

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): Square

    override fun movedTo(position: Vector2F): Square

    override fun rotatedBy(rotation: AngleF): Square = rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): Square

    override fun rotatedTo(orientation: AngleF): Square =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): Square

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Square =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Square

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Square =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Square

    override fun scaledBy(factor: Float): Square

    override fun dilatedBy(point: Vector2F, factor: Float): Square

    override fun transformedBy(offset: Vector2F, rotation: AngleF): Square =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Square

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Square =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Square

    override fun transformedTo(position: Vector2F, orientation: AngleF): Square =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): Square

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
    ): Square

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float
}