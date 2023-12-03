package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.AngleF
import com.sztorm.lowallocmath.ComplexF
import com.sztorm.lowallocmath.Vector2F

interface Transformable {
    val position: Vector2F
    val orientation: ComplexF

    fun movedBy(offset: Vector2F): Transformable

    fun movedTo(position: Vector2F): Transformable

    fun rotatedBy(rotation: AngleF): Transformable

    fun rotatedBy(rotation: ComplexF): Transformable

    fun rotatedTo(orientation: AngleF): Transformable

    fun rotatedTo(orientation: ComplexF): Transformable

    fun rotatedAroundPointBy(point: Vector2F, rotation: AngleF): Transformable

    fun rotatedAroundPointBy(point: Vector2F, rotation: ComplexF): Transformable

    fun rotatedAroundPointTo(point: Vector2F, orientation: AngleF): Transformable

    fun rotatedAroundPointTo(point: Vector2F, orientation: ComplexF): Transformable

    fun scaledBy(factor: Float): Transformable

    fun dilatedBy(point: Vector2F, factor: Float): Transformable

    fun transformedBy(offset: Vector2F, rotation: AngleF): Transformable

    fun transformedBy(offset: Vector2F, rotation: ComplexF): Transformable

    fun transformedBy(offset: Vector2F, rotation: AngleF, factor: Float): Transformable

    fun transformedBy(offset: Vector2F, rotation: ComplexF, factor: Float): Transformable

    fun transformedTo(position: Vector2F, orientation: AngleF): Transformable

    fun transformedTo(position: Vector2F, orientation: ComplexF): Transformable
}