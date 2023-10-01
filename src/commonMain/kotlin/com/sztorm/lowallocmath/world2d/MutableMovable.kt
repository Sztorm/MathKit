package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface MutableMovable : Movable {
    fun moveBy(offset: Vector2F)

    fun moveTo(position: Vector2F)
}