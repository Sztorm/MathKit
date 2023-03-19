@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
value class Flags32(val intValue: Int) {
    inline val uIntValue: UInt
        get() = intValue.toUInt()

    inline operator fun get(index: Int): Boolean = ((intValue ushr index) and 1) != 0

    inline infix fun adding(flags: Flags32) = Flags32(intValue or flags.intValue)

    inline infix fun removing(flags: Flags32) = Flags32(intValue and flags.intValue.inv())

    inline infix fun toggling(flags: Flags32) = Flags32(intValue xor flags.intValue)

    inline fun setting(flags: Flags32, to: Boolean) =
        Flags32((intValue and flags.intValue.inv()) or (-(if (to) 1 else 0) and flags.intValue))

    inline fun hasAll(flags: Flags32) = (intValue and flags.intValue) == flags.intValue

    inline fun hasAny(flags: Flags32) = (intValue and flags.intValue) != 0

    companion object {
        const val SIZE_BYTES: Int = 4
        const val SIZE_BITS: Int = 32

        @JvmStatic
        inline fun fromUInt(value: UInt) = Flags32(value.toInt())

        @JvmStatic
        inline fun fromInt(value: Int) = Flags32(value)
    }
}