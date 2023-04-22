@file:Suppress(
    "MemberVisibilityCanBePrivate",
    "OVERRIDE_BY_INLINE",
    "unused",
)

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
value class Flags64(val longValue: Long) : Collection<Boolean> {

    /** Returns 64-bit unsigned integer representation of this type. **/
    inline val uLongValue: ULong
        get() = longValue.toULong()

    /** Returns the number of flags, which is always 64. **/
    override inline val size: Int
        get() = 64

    /** Returns the last valid index for this collection. **/
    inline val lastIndex: Int
        get() = 63

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
            1 -> uLongValue != ULong.MAX_VALUE
            2 -> longValue != 0L
            3 -> uLongValue != ULong.MAX_VALUE && longValue != 0L
            else -> true
        }
    }

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

    /** Returns a value indicating whether the [element] is contained in this collection. **/
    override inline operator fun contains(element: Boolean): Boolean =
        (element && this != NONE) || (!element && this != ALL)

    /** Returns a value indicating whether the bit at [index] is set to 1. **/
    inline operator fun get(index: Int): Boolean = ((longValue ushr index) and 1L) != 0L

    /** Returns an iterator of this collection. **/
    override operator fun iterator(): BooleanIterator = BooleanIteratorOfFlags64(this)

    companion object {

        /** The number of bits used to represent an instance of [Flags64] in a binary form. **/
        const val SIZE_BITS: Int = 64

        /** The number of bytes used to represent an instance of [Flags64] in a binary form. **/
        const val SIZE_BYTES: Int = 8

        /** Flags whose none of the bits are set to 1. **/
        inline val NONE
            @JvmStatic get() = Flags64(0L)

        /** Flags whose all the bits are set to 1. **/
        inline val ALL
            @JvmStatic get() = fromULong(ULong.MAX_VALUE)

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

private class BooleanIteratorOfFlags64(private val flags: Flags64) : BooleanIterator() {
    private var index: Int = 0

    override fun hasNext(): Boolean = index < flags.size

    override fun nextBoolean(): Boolean =
        if (!hasNext()) throw NoSuchElementException("$index")
        else flags[index++]
}