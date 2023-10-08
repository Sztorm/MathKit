package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun Square(center: Vector2F, rotation: ComplexF, sideLength: Float): Square =
    MutableSquare(center, rotation, sideLength)

interface Square : RectangleShape, RegularShape, Transformable {
    val center: Vector2F

    override val position: Vector2F
        get() = center

    override fun movedBy(offset: Vector2F): Square

    override fun movedTo(position: Vector2F): Square

    override fun rotatedBy(angle: AngleF): Square = rotatedBy(ComplexF.fromAngle(angle))

    override fun rotatedBy(rotation: ComplexF): Square

    override fun rotatedTo(angle: AngleF): Square = rotatedTo(ComplexF.fromAngle(angle))

    override fun rotatedTo(rotation: ComplexF): Square

    override fun scaledBy(factor: Float): Square

    override fun transformedBy(offset: Vector2F, angle: AngleF): Square =
        transformedBy(offset, ComplexF.fromAngle(angle))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): Square

    override fun transformedBy(offset: Vector2F, angle: AngleF, factor: Float): Square =
        transformedBy(offset, ComplexF.fromAngle(angle), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Square

    override fun transformedTo(position: Vector2F, angle: AngleF): Square =
        transformedTo(position, ComplexF.fromAngle(angle))

    override fun transformedTo(position: Vector2F, rotation: ComplexF): Square

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(
        center: Vector2F = this.center,
        rotation: ComplexF = this.rotation,
        sideLength: Float = this.sideLength,
    ): Square

    operator fun component1(): Vector2F

    operator fun component2(): ComplexF

    operator fun component3(): Float
}