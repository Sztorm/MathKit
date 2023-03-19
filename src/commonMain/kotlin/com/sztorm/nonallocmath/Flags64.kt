@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
value class Flags64(val longValue: Long) {
    inline val uLongValue: ULong
        get() = longValue.toULong()

    inline operator fun get(index: Int): Boolean = ((longValue ushr index) and 1L) != 0L

    inline infix fun adding(flags: Flags64) = Flags64(longValue or flags.longValue)

    inline infix fun removing(flags: Flags64) = Flags64(longValue and flags.longValue.inv())

    inline infix fun toggling(flags: Flags64) = Flags64(longValue xor flags.longValue)

    inline fun setting(flags: Flags64, to: Boolean) = Flags64(
        (longValue and flags.longValue.inv()) or (-(if (to) 1L else 0L) and flags.longValue)
    )

    inline fun hasAll(flags: Flags64) = (longValue and flags.longValue) == flags.longValue

    inline fun hasAny(flags: Flags64) = (longValue and flags.longValue) != 0L

    companion object {
        const val SIZE_BYTES: Int = 8
        const val SIZE_BITS: Int = 64

        @JvmStatic
        inline fun fromULong(value: ULong) = Flags64(value.toLong())

        @JvmStatic
        inline fun fromLong(value: Long) = Flags64(value)
    }
}