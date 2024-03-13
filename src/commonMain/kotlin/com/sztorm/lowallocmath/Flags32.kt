@file:Suppress("OVERRIDE_BY_INLINE")

package com.sztorm.lowallocmath

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
value class Flags32(val intValue: Int) : Collection<Boolean> {
    /** Returns 32-bit unsigned integer representation of this type. **/
    inline val uIntValue: UInt
        get() = intValue.toUInt()

    /** Returns the number of flags, which is always 32. **/
    override inline val size: Int
        get() = 32

    /** Returns the last valid index for this collection. **/
    inline val lastIndex: Int
        get() = 31

    /** Returns a value indicating whether this collection is empty, which is always false. **/
    override inline fun isEmpty() = false

    /**
     * Returns a value indicating whether all specified [elements] are contained in this
     * collection.
     */
    override fun containsAll(elements: Collection<Boolean>): Boolean {
        val it = elements.iterator()
        // 0 -> elements collection is empty
        // 1 -> all elements are false
        // 2 -> all elements are true
        // 3 -> elements contain true and false
        var indicator = 0

        while (it.hasNext() && indicator != 0b11) {
            indicator =
                if (it.next()) indicator or 0b10
                else indicator or 0b01
        }
        return when (indicator) {
            1 -> uIntValue != UInt.MAX_VALUE
            2 -> intValue != 0
            3 -> uIntValue != UInt.MAX_VALUE && intValue != 0
            else -> true
        }
    }

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

    private inline fun StringBuilder.appendFlags(from: Int, to: Int): StringBuilder {
        for (i in from..to) {
            this.append(if (this@Flags32[i]) '1' else '0')
        }
        return this
    }

    /**
     * Returns a [String] representation of this flags collection in
     * "Flags32(00000000 00000000 00000000 00000000)" format.
     */
    override fun toString(): String = StringBuilder(8 + 8 * 4 + 3 * 1 + 1)
        .append("Flags32(")
        .appendFlags(0, 7).append(' ')
        .appendFlags(8, 15).append(' ')
        .appendFlags(16, 23).append(' ')
        .appendFlags(24, 31).append(')')
        .toString()

    /** Returns a value indicating whether the [element] is contained in this collection. **/
    override inline operator fun contains(element: Boolean): Boolean =
        (element && this != NONE) || (!element && this != ALL)

    /**
     * Returns a value indicating whether the bit at [index] is set to 1.
     *
     * [index] should be in range of 0..31, but the value of [index] is not checked.
     **/
    inline operator fun get(index: Int): Boolean = ((intValue ushr index) and 1) != 0

    /** Returns an iterator of this collection. **/
    override operator fun iterator(): BooleanIterator = Iterator(this)

    companion object {
        /** The number of bits used to represent an instance of [Flags32] in a binary form. **/
        const val SIZE_BITS: Int = 32

        /** The number of bytes used to represent an instance of [Flags32] in a binary form. **/
        const val SIZE_BYTES: Int = 4

        /** Flags whose none of the bits are set to 1. **/
        inline val NONE
            @JvmStatic get() = Flags32(0)

        /** Flags whose all the bits are set to 1. **/
        inline val ALL
            @JvmStatic get() = fromUInt(UInt.MAX_VALUE)

        /**
         * Creates a new [Flags32] instance using the specified unsigned 32-bit integer [value].
         */
        @JvmStatic
        inline fun fromUInt(value: UInt) = Flags32(value.toInt())

        /** Creates a new [Flags32] instance using the specified signed 32-bit integer [value]. **/
        @JvmStatic
        inline fun fromInt(value: Int) = Flags32(value)
    }

    private class Iterator(private val flags: Flags32) : BooleanIterator() {
        private var index: Int = 0

        override fun hasNext(): Boolean = index < flags.size

        override fun nextBoolean(): Boolean =
            if (!hasNext()) throw NoSuchElementException("$index")
            else flags[index++]
    }
}