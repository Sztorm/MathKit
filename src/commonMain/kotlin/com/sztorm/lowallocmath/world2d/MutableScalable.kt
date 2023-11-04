package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface MutableScalable : Scalable {
    fun scaleBy(factor: Float)

    fun dilateBy(point: Vector2F, factor: Float)
}