@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

@JvmInline
value class Flags8(val byteValue: Byte) {
    inline val uByteValue: UByte
        get() = byteValue.toUByte()

    inline operator fun get(index: Int): Boolean = ((byteValue.toInt() ushr index) and 1) != 0

    inline infix fun adding(flags: Flags8) =
        Flags8((byteValue.toInt() or flags.byteValue.toInt()).toByte())

    inline infix fun removing(flags: Flags8) =
        Flags8((byteValue.toInt() and flags.byteValue.toInt().inv()).toByte())

    inline infix fun toggling(flags: Flags8) =
        Flags8((byteValue.toInt() xor flags.byteValue.toInt()).toByte())

    inline fun setting(flags: Flags8, to: Boolean) =
        Flags8(((byteValue.toInt() and flags.byteValue.toInt().inv()) or
                (-(if (to) 1 else 0) and flags.byteValue.toInt())).toByte())

    inline fun hasAll(flags: Flags8) =
        (byteValue.toInt() and flags.byteValue.toInt()) == flags.byteValue.toInt()

    inline fun hasAny(flags: Flags8) = (byteValue.toInt() and flags.byteValue.toInt()) != 0

    companion object {
        const val SIZE_BYTES: Int = 1
        const val SIZE_BITS: Int = 8

        @JvmStatic
        inline fun fromUByte(value: UByte) = Flags8(value.toByte())

        @JvmStatic
        inline fun fromByte(value: Byte) = Flags8(value)
    }
}