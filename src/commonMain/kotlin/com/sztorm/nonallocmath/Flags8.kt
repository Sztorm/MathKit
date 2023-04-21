@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents a collection of eight bit flags.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `byte`.
 *
 * @property byteValue Returns 8-bit signed integer representation of this type.
 */
@JvmInline
value class Flags8(val byteValue: Byte) {

    /** Returns 8-bit unsigned integer representation of this type. **/
    inline val uByteValue: UByte
        get() = byteValue.toUByte()

    /** Returns a value indicating whether the bit at [index] is set to 1.**/
    inline operator fun get(index: Int): Boolean = ((byteValue.toInt() ushr index) and 1) != 0

    /** Returns a copy of this flags instance with the specified [flags] added. **/
    inline infix fun adding(flags: Flags8) =
        Flags8((byteValue.toInt() or flags.byteValue.toInt()).toByte())

    /** Returns a copy of this flags instance with the specified [flags] removed. **/
    inline infix fun removing(flags: Flags8) =
        Flags8((byteValue.toInt() and flags.byteValue.toInt().inv()).toByte())

    /** Returns a copy of this flags instance with the specified [flags] toggled. **/
    inline infix fun toggling(flags: Flags8) =
        Flags8((byteValue.toInt() xor flags.byteValue.toInt()).toByte())

    /**
     * Returns a copy of this flags instance with the specified [flags] set to the particular
     * value.
     */
    inline fun setting(flags: Flags8, to: Boolean) = Flags8(
        ((byteValue.toInt() and flags.byteValue.toInt().inv()) or
                (-(if (to) 1 else 0) and flags.byteValue.toInt())).toByte()
    )

    /** Returns a value indicating whether this flags instance has all the specified [flags]. **/
    inline fun hasAll(flags: Flags8) =
        (byteValue.toInt() and flags.byteValue.toInt()) == flags.byteValue.toInt()

    /** Returns a value indicating whether this flags instance has any of the specified [flags]. **/
    inline fun hasAny(flags: Flags8) = (byteValue.toInt() and flags.byteValue.toInt()) != 0

    companion object {

        /** The number of bits used to represent an instance of [Flags8] in a binary form. **/
        const val SIZE_BITS: Int = 8

        /** The number of bytes used to represent an instance of [Flags8] in a binary form. **/
        const val SIZE_BYTES: Int = 1

        /** Creates a new [Flags8] instance using the specified unsigned 8-bit integer [value]. **/
        @JvmStatic
        inline fun fromUByte(value: UByte) = Flags8(value.toByte())

        /** Creates a new [Flags8] instance using the specified signed 8-bit integer [value]. **/
        @JvmStatic
        inline fun fromByte(value: Byte) = Flags8(value)
    }
}