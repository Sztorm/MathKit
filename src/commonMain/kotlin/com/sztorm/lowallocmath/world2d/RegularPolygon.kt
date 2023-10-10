package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.*

fun RegularPolygon(
    center: Vector2F, rotation: ComplexF, sideLength: Float, sideCount: Int
): RegularPolygon = MutableRegularPolygon(center, rotation, sideLength, sideCount)

interface RegularPolygon : RegularShape, Transformable {
    val center: Vector2F
    val points: Vector2FList

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RegularPolygon

    override fun movedTo(position: Vector2F): RegularPolygon

    override fun rotatedBy(angle: AngleF): RegularPolygon = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): RegularPolygon

    override fun rotatedTo(angle: AngleF): RegularPolygon = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): RegularPolygon

    override fun scaledBy(factor: Float): RegularPolygon

    override fun transformedBy(offset: Vector2F, angle: AngleF): RegularPolygon =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RegularPolygon

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): RegularPolygon =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): RegularPolygon

    override fun transformedTo(position: Vector2F, angle: AngleF): RegularPolygon =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): RegularPolygon

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
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

