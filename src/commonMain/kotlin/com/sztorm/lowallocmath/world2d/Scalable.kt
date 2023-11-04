package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface Scalable {
    fun scaledBy(factor: Float): Scalable

    fun dilatedBy(point: Vector2F, factor: Float): Scalable
}