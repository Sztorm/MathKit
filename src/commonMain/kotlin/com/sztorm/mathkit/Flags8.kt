@file:Suppress("OVERRIDE_BY_INLINE")

package com.sztorm.mathkit

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
value class Flags8(val byteValue: Byte) : Collection<Boolean> {
    /** Returns 8-bit unsigned integer representation of this type. **/
    inline val uByteValue: UByte
        get() = byteValue.toUByte()

    /** Returns the number of flags, which is always 8. **/
    override inline val size: Int
        get() = 8

    /** Returns the last valid index for this collection. **/
    inline val lastIndex: Int
        get() = 7

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
            1 -> uByteValue != UByte.MAX_VALUE
            2 -> byteValue.toInt() != 0
            3 -> uByteValue != UByte.MAX_VALUE && byteValue.toInt() != 0
            else -> true
        }
    }

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

    /**
     * Returns a value indicating whether this flags instance has any of the specified [flags].
     */
    inline fun hasAny(flags: Flags8) = (byteValue.toInt() and flags.byteValue.toInt()) != 0

    /**
     * Returns a [String] representation of this flags collection in "Flags8(00000000)" format.
     */
    override fun toString(): String {
        val sb = StringBuilder(7 + 8 + 1)
            .append("Flags8(")

        for (i in 0..7) {
            sb.append(if (this[i]) '1' else '0')
        }
        return sb.append(')').toString()
    }

    /** Returns a value indicating whether the [element] is contained in this collection. **/
    override inline operator fun contains(element: Boolean): Boolean =
        (element && this != NONE) || (!element && this != ALL)

    /**
     * Returns a value indicating whether the bit at [index] is set to 1.
     *
     * [index] should be in range of `[0, 7]`, but the value of [index] is not checked.
     **/
    inline operator fun get(index: Int): Boolean = ((byteValue.toInt() ushr index) and 1) != 0

    /** Returns an iterator of this collection. **/
    override operator fun iterator(): BooleanIterator = Iterator(this)

    companion object {
        /** The number of bits used to represent an instance of [Flags8] in a binary form. **/
        const val SIZE_BITS: Int = 8

        /** The number of bytes used to represent an instance of [Flags8] in a binary form. **/
        const val SIZE_BYTES: Int = 1

        /** Flags whose none of the bits are set to 1. **/
        inline val NONE
            @JvmStatic get() = Flags8(0)

        /** Flags whose all the bits are set to 1. **/
        inline val ALL
            @JvmStatic get() = fromUByte(UByte.MAX_VALUE)

        /** Creates a new [Flags8] instance using the specified unsigned 8-bit integer [value]. **/
        @JvmStatic
        inline fun fromUByte(value: UByte) = Flags8(value.toByte())

        /** Creates a new [Flags8] instance using the specified signed 8-bit integer [value]. **/
        @JvmStatic
        inline fun fromByte(value: Byte) = Flags8(value)
    }

    private class Iterator(private val flags: Flags8) : BooleanIterator() {
        private var index: Int = 0

        override fun hasNext(): Boolean = index < flags.size

        override fun nextBoolean(): Boolean =
            if (!hasNext()) throw NoSuchElementException("$index")
            else flags[index++]
    }
}