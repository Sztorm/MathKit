package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun RegularTriangle(center: Vector2F, rotation: ComplexF, sideLength: Float): RegularTriangle =
    MutableRegularTriangle(center, rotation, sideLength)

interface RegularTriangle : TriangleShape, RegularShape, Transformable {
    val center: Vector2F

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): RegularTriangle

    override fun movedTo(position: Vector2F): RegularTriangle

    override fun rotatedBy(angle: AngleF): RegularTriangle = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): RegularTriangle

    override fun rotatedTo(angle: AngleF): RegularTriangle = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): RegularTriangle

    override fun scaledBy(factor: Float): RegularTriangle

    override fun transformedBy(offset: Vector2F, angle: AngleF): RegularTriangle =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): RegularTriangle

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): RegularTriangle =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(
        offset: Vector2F, rotation: ComplexF, factor: Float
    ): RegularTriangle

    override fun transformedTo(position: Vector2F, angle: AngleF): RegularTriangle =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): RegularTriangle

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        sideLength: Float = this.sideLength,
    ): RegularTriangle

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float
}