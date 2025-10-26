@file:Suppress("NOTHING_TO_INLINE", "OVERRIDE_BY_INLINE")

package com.sztorm.mathkit

import kotlin.jvm.JvmInline
import kotlin.jvm.JvmStatic

/**
 * Represents a collection of sixteen bit flags.
 *
 * On the JVM, non-nullable values of this type are represented as values of the primitive type
 * `short`.
 *
 * @property shortValue Returns 16-bit signed integer representation of this type.
 */
@JvmInline
value class Flags16(val shortValue: Short) : Collection<Boolean> {
    /** Returns 16-bit unsigned integer representation of this type. **/
    inline val uShortValue: UShort
        get() = shortValue.toUShort()

    /** Returns the number of flags, which is always 16. **/
    @Suppress("SameReturnValue")
    override inline val size: Int
        get() = 16

    /** Returns the last valid index for this collection. **/
    inline val lastIndex: Int
        get() = 15

    /** Returns a value indicating whether this collection is empty, which is always false. **/
    @Suppress("SameReturnValue")
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
            1 -> uShortValue != UShort.MAX_VALUE
            2 -> shortValue.toInt() != 0
            3 -> uShortValue != UShort.MAX_VALUE && shortValue.toInt() != 0
            else -> true
        }
    }

    /** Returns a copy of this flags instance with the specified [flags] added. **/
    inline infix fun adding(flags: Flags16) =
        Flags16((shortValue.toInt() or flags.shortValue.toInt()).toShort())

    /** Returns a copy of this flags instance with the specified [flags] removed. **/
    inline infix fun removing(flags: Flags16) =
        Flags16((shortValue.toInt() and flags.shortValue.toInt().inv()).toShort())

    /** Returns a copy of this flags instance with the specified [flags] toggled. **/
    inline infix fun toggling(flags: Flags16) =
        Flags16((shortValue.toInt() xor flags.shortValue.toInt()).toShort())

    /**
     * Returns a copy of this flags instance with the specified [flags] set to the particular
     * value.
     */
    inline fun setting(flags: Flags16, to: Boolean) = Flags16(
        ((shortValue.toInt() and flags.shortValue.toInt().inv()) or
            (-(if (to) 1 else 0) and flags.shortValue.toInt())).toShort()
    )

    /** Returns a value indicating whether this flags instance has all the specified [flags]. **/
    inline fun hasAll(flags: Flags16) =
        (shortValue.toInt() and flags.shortValue.toInt()) == flags.shortValue.toInt()

    /**
     * Returns a value indicating whether this flags instance has any of the specified [flags].
     */
    inline fun hasAny(flags: Flags16) = (shortValue.toInt() and flags.shortValue.toInt()) != 0

    private inline fun StringBuilder.appendFlags(from: Int, to: Int): StringBuilder {
        for (i in from..to) {
            this.append(if (this@Flags16[i]) '1' else '0')
        }
        return this
    }

    /**
     * Returns a [String] representation of this flags collection in "Flags16(00000000 00000000)"
     * format.
     */
    override fun toString(): String = StringBuilder(8 + 8 * 2 + 1 + 1)
        .append("Flags16(")
        .appendFlags(0, 7).append(' ')
        .appendFlags(8, 15).append(')')
        .toString()

    /** Returns a value indicating whether the [element] is contained in this collection. **/
    override inline operator fun contains(element: Boolean): Boolean =
        (element && this != NONE) || (!element && this != ALL)

    /**
     * Returns a value indicating whether the bit at [index] is set to 1.
     *
     * [index] should be in range of `[0, 15]`, but the value of [index] is not checked.
     **/
    inline operator fun get(index: Int): Boolean = ((shortValue.toInt() ushr index) and 1) != 0

    /** Returns an iterator of this collection. **/
    override operator fun iterator(): BooleanIterator = Iterator(this)

    companion object {
        /** The number of bits used to represent an instance of [Flags16] in a binary form. **/
        const val SIZE_BITS: Int = 16

        /** The number of bytes used to represent an instance of [Flags16] in a binary form. **/
        const val SIZE_BYTES: Int = 2

        /** Flags whose none of the bits are set to 1. **/
        inline val NONE
            @JvmStatic get() = Flags16(0)

        /** Flags whose all the bits are set to 1. **/
        inline val ALL
            @JvmStatic get() = fromUShort(UShort.MAX_VALUE)

        /**
         * Creates a new [Flags16] instance using the specified unsigned 16-bit integer [value].
         */
        @JvmStatic
        inline fun fromUShort(value: UShort) = Flags16(value.toShort())

        /** Creates a new [Flags16] instance using the specified signed 16-bit integer [value]. **/
        @JvmStatic
        inline fun fromShort(value: Short) = Flags16(value)
    }

    private class Iterator(private val flags: Flags16) : BooleanIterator() {
        private var index: Int = 0

        override fun hasNext(): Boolean = index < flags.size

        override fun nextBoolean(): Boolean =
            if (!hasNext()) throw NoSuchElementException("$index")
            else flags[index++]
    }
}