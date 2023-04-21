@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents a collection of thirty-two bit flags.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `int`.
 *
 * @property intValue Returns 32-bit signed integer representation of this type.
 */
@JvmInline
value class Flags32(val intValue: Int) {

    /** Returns 32-bit unsigned integer representation of this type. **/
    inline val uIntValue: UInt
        get() = intValue.toUInt()

    /** Returns a value indicating whether the bit at [index] is set to 1.**/
    inline operator fun get(index: Int): Boolean = ((intValue ushr index) and 1) != 0

    /** Returns a copy of this flags instance with the specified [flags] added. **/
    inline infix fun adding(flags: Flags32) = Flags32(intValue or flags.intValue)

    /** Returns a copy of this flags instance with the specified [flags] removed. **/
    inline infix fun removing(flags: Flags32) = Flags32(intValue and flags.intValue.inv())

    /** Returns a copy of this flags instance with the specified [flags] toggled. **/
    inline infix fun toggling(flags: Flags32) = Flags32(intValue xor flags.intValue)

    /**
     * Returns a copy of this flags instance with the specified [flags] set to the particular
     * value.
     */
    inline fun setting(flags: Flags32, to: Boolean) =
        Flags32((intValue and flags.intValue.inv()) or (-(if (to) 1 else 0) and flags.intValue))

    /** Returns a value indicating whether this flags instance has all the specified [flags]. **/
    inline fun hasAll(flags: Flags32) = (intValue and flags.intValue) == flags.intValue

    /**
     * Returns a value indicating whether this flags instance has any of the specified [flags].
     */
    inline fun hasAny(flags: Flags32) = (intValue and flags.intValue) != 0

    companion object {

        /** The number of bits used to represent an instance of [Flags32] in a binary form. **/
        const val SIZE_BITS: Int = 32

        /** The number of bytes used to represent an instance of [Flags32] in a binary form. **/
        const val SIZE_BYTES: Int = 4

        /**
         * Creates a new [Flags32] instance using the specified unsigned 32-bit integer [value].
         */
        @JvmStatic
        inline fun fromUInt(value: UInt) = Flags32(value.toInt())

        /** Creates a new [Flags32] instance using the specified signed 32-bit integer [value]. **/
        @JvmStatic
        inline fun fromInt(value: Int) = Flags32(value)
    }
}