package com.sztorm.lowallocmath.world2d

import com.sztorm.lowallocmath.Vector2F

interface Movable {
    val position: Vector2F

    fun movedBy(offset: Vector2F): Movable

    fun movedTo(position: Vector2F): Movable
}