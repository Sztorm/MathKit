@file:Suppress("MemberVisibilityCanBePrivate")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.math.*

@JvmInline
value class Vector2F private constructor(private val data: Long) {

    constructor(x: Float, y: Float) : this(data =
        (x.toRawBits().toLong() and 0xFFFFFFFFL) or
        (y.toRawBits().toLong() shl Float.SIZE_BITS))

    val x: Float
        get() = Float.fromBits(data.toInt())

    val y: Float
        get() = Float.fromBits((data ushr Float.SIZE_BITS).toInt())

    override fun toString(): String = StringBuilder(1 + 16 + 2 + 16 + 1)
        .append('(').append(x).append(", ").append(y).append(')')
        .toString()
}