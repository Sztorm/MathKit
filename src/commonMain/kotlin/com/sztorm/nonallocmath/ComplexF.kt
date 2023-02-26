@file:Suppress("MemberVisibilityCanBePrivate")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.math.*

@JvmInline
value class ComplexF private constructor(private val data: Long) {

    constructor(real: Float, imaginary: Float) : this(data =
        (real.toRawBits().toLong() and 0xFFFFFFFFL) or
        (imaginary.toRawBits().toLong() shl Float.SIZE_BITS))

    val real: Float
        get() = Float.fromBits(data.toInt())

    val imaginary: Float
        get() = Float.fromBits((data ushr Float.SIZE_BITS).toInt())

    override fun toString(): String = (
        if(imaginary < 0)
            StringBuilder(16 + 3 + 16 + 1)
                .append(real).append(" - ").append(imaginary.absoluteValue).append('i')
        else
            StringBuilder(16 + 3 + 16 + 1)
                .append(real).append(" + ").append(imaginary).append('i')
        ).toString()
}