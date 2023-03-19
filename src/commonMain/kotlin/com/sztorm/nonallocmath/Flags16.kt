@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
value class Flags16(val shortValue: Short) {
    inline val uShortValue: UShort
        get() = shortValue.toUShort()

    inline operator fun get(index: Int): Boolean = ((shortValue.toInt() ushr index) and 1) != 0

    inline infix fun adding(flags: Flags16) =
        Flags16((shortValue.toInt() or flags.shortValue.toInt()).toShort())

    inline infix fun removing(flags: Flags16) =
        Flags16((shortValue.toInt() and flags.shortValue.toInt().inv()).toShort())

    inline infix fun toggling(flags: Flags16) =
        Flags16((shortValue.toInt() xor flags.shortValue.toInt()).toShort())

    inline fun setting(flags: Flags16, to: Boolean) =
        Flags16(((shortValue.toInt() and flags.shortValue.toInt().inv()) or
                (-(if (to) 1 else 0) and flags.shortValue.toInt())).toShort())

    inline fun hasAll(flags: Flags16) =
        (shortValue.toInt() and flags.shortValue.toInt()) == flags.shortValue.toInt()

    inline fun hasAny(flags: Flags16) = (shortValue.toInt() and flags.shortValue.toInt()) != 0

    companion object {
        const val SIZE_BYTES: Int = 2
        const val SIZE_BITS: Int = 16

        @JvmStatic
        inline fun fromUShort(value: UShort) = Flags16(value.toShort())

        @JvmStatic
        inline fun fromShort(value: Short) = Flags16(value)
    }
}