package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*

fun RegularPolygon(
    center: Vector2F, orientation: ComplexF, sideLength: Float, sideCount: Int
): RegularPolygon = MutableRegularPolygon(center, orientation, sideLength, sideCount)

interface RegularPolygon : RegularShape, Transformable {
    val center: Vector2F
    val points: Vector2FList

    override fun movedBy(offset: Vector2F): RegularPolygon

    override fun movedTo(position: Vector2F): RegularPolygon

    override fun rotatedBy(rotation: AngleF): RegularPolygon =
        rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): RegularPolygon

    override fun rotatedTo(orientation: AngleF): RegularPolygon =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): RegularPolygon

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): RegularPolygon =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): RegularPolygon

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): RegularPolygon =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): RegularPolygon

    override fun scaledBy(factor: Float): RegularPolygon

    override fun dilatedBy(point: Vector2F, factor: Float): RegularPolygon

    override fun transformedBy(offset: Vector2F, rotation: AngleF): RegularPolygon =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RegularPolygon

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): RegularPolygon =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): RegularPolygon

    override fun transformedTo(position: Vector2F, orientation: AngleF): RegularPolygon =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): RegularPolygon

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        orientation: ComplexF = this.orientation,
        sideLength: Float = this.sideLength,
        sideCount: Int = this.sideCount
    ): RegularPolygon

    fun toRegularTriangleOrNull(): RegularTriangle?

    fun toSquareOrNull(): Square?

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float

    operator fun component4(): Int
}

