@file:Suppress("unused")

package com.sztorm.nonallocmath

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents a collection of sixty-four bit flags.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `long`.
 *
 * @property longValue Returns 64-bit signed integer representation of this type.
 */
@JvmInline
value class Flags64(val longValue: Long) {

    /** Returns 64-bit unsigned integer representation of this type. **/
    inline val uLongValue: ULong
        get() = longValue.toULong()

    /** Returns a value indicating whether the bit at [index] is set to 1.**/
    inline operator fun get(index: Int): Boolean = ((longValue ushr index) and 1L) != 0L

    /** Returns a copy of this flags instance with the specified [flags] added. **/
    inline infix fun adding(flags: Flags64) = Flags64(longValue or flags.longValue)

    /** Returns a copy of this flags instance with the specified [flags] removed. **/
    inline infix fun removing(flags: Flags64) = Flags64(longValue and flags.longValue.inv())

    /** Returns a copy of this flags instance with the specified [flags] toggled. **/
    inline infix fun toggling(flags: Flags64) = Flags64(longValue xor flags.longValue)

    /**
     * Returns a copy of this flags instance with the specified [flags] set to the particular
     * value.
     */
    inline fun setting(flags: Flags64, to: Boolean) = Flags64(
        (longValue and flags.longValue.inv()) or (-(if (to) 1L else 0L) and flags.longValue)
    )

    /** Returns a value indicating whether this flags instance has all the specified [flags]. **/
    inline fun hasAll(flags: Flags64) = (longValue and flags.longValue) == flags.longValue

    /**
     * Returns a value indicating whether this flags instance has any of the specified [flags].
     */
    inline fun hasAny(flags: Flags64) = (longValue and flags.longValue) != 0L

    companion object {

        /** The number of bits used to represent an instance of [Flags64] in a binary form. **/
        const val SIZE_BITS: Int = 64

        /** The number of bytes used to represent an instance of [Flags64] in a binary form. **/
        const val SIZE_BYTES: Int = 8

        /**
         * Creates a new [Flags64] instance using the specified unsigned 64-bit integer [value].
         */
        @JvmStatic
        inline fun fromULong(value: ULong) = Flags64(value.toLong())

        /** Creates a new [Flags64] instance using the specified signed 64-bit integer [value]. **/
        @JvmStatic
        inline fun fromLong(value: Long) = Flags64(value)
    }
}