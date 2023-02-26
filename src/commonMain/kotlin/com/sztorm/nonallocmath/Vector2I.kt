@file:Suppress("MemberVisibilityCanBePrivate")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.math.*

@JvmInline
value class Vector2I private constructor(private val data: Long) {

    constructor(x: Int, y: Int) : this(data =
        (x.toLong() and 0xFFFFFFFFL) or
        (y.toLong() shl Int.SIZE_BITS))

    val x: Int
        get() = data.toInt()

    val y: Int
        get() = (data ushr Int.SIZE_BITS).toInt()

    override fun toString(): String = StringBuilder(1 + 11 + 2 + 11 + 1)
        .append('(').append(x).append(", ").append(y).append(')')
        .toString()
}