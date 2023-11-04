package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F
import com.sztorm.lowallocmath.Vector2FIterator

fun LineSegment(pointA: Vector2F, pointB: Vector2F): LineSegment =
    MutableLineSegment(pointA, pointB)

interface LineSegment : Transformable {
    val pointA: Vector2F
    val pointB: Vector2F

    val center: Vector2F
        get() = (pointA + pointB) * 0.5f

    val length: Float
        get() = pointA.distanceTo(pointB)

    override val position: Vector2F
        get() = center

    override val orientation: ComplexF
        get() = (pointA - pointB).normalized.toComplexF()

    override fun movedBy(offset: Vector2F): LineSegment

    override fun movedTo(position: Vector2F): LineSegment

    override fun rotatedBy(rotation: AngleF): LineSegment = rotatedBy(ComplexF.fromAngle(rotation))

    override fun rotatedBy(rotation: ComplexF): LineSegment

    override fun rotatedTo(orientation: AngleF): LineSegment =
        rotatedTo(ComplexF.fromAngle(orientation))

    override fun rotatedTo(orientation: ComplexF): LineSegment

    override fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): LineSegment =
        rotatedAroundPointBy(point, ComplexF.fromAngle(rotation))

    override fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): LineSegment

    override fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): LineSegment =
        rotatedAroundPointTo(point, ComplexF.fromAngle(orientation))

    override fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): LineSegment

    override fun scaledBy(factor: Float): LineSegment

    override fun transformedBy(offset: Vector2F, rotation: AngleF): LineSegment =
        transformedBy(offset, ComplexF.fromAngle(rotation))

    override fun transformedBy(offset: Vector2F, rotation: ComplexF): LineSegment

    override fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): LineSegment =
        transformedBy(offset, ComplexF.fromAngle(rotation), factor)

    override fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): LineSegment

    override fun transformedTo(position: Vector2F, orientation: AngleF): LineSegment =
        transformedTo(position, ComplexF.fromAngle(orientation))

    override fun transformedTo(position: Vector2F, orientation: ComplexF): LineSegment

    fun closestPointTo(point: Vector2F): Vector2F

    operator fun contains(point: Vector2F): Boolean

    fun pointIterator(): Vector2FIterator

    fun copy(pointA: Vector2F = this.pointA, pointB: Vector2F = this.pointB): LineSegment

    operator fun component1(): Vector2F

    operator fun component2(): Vector2F
}