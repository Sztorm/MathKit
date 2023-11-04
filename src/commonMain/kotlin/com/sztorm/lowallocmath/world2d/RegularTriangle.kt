package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun RegularTriangle(center: Vector2F, orientation: ComplexF, sideLength: Float): RegularTriangle =
    MutableRegularTriangle(center, orientation, sideLength)

interface RegularTriangle : TriangleShape, RegularShape, Transformable {
    val center: Vector2F

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RegularTriangle

    override fun movedTo(position: Vector2F): RegularTriangle

    override fun rotatedBy(rotation: AngleF): RegularTriangle =
        rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RegularTriangle

    override fun rotatedTo(orientation: AngleF): RegularTriangle =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RegularTriangle

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RegularTriangle =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RegularTriangle

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RegularTriangle =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RegularTriangle

    override fun scaledBy(factor: Float): RegularTriangle

    override fun transformedBy(offset: Vector2F, rotation: AngleF): RegularTriangle =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RegularTriangle

    override fun transformedBy(
        offset: Vector2F, rotation: AngleF, factor: Float
    ): RegularTriangle = transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): RegularTriangle

    override fun transformedTo(position: Vector2F, orientation: AngleF): RegularTriangle =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RegularTriangle

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
    ): RegularTriangle

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float
}