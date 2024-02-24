@file:Suppress("unused")

package com.sztorm.lowallocmath

import kotlin.jvm.JvmInline

/**
 * Represents a read-only view over [Vector2IArray] type with methods that avoid boxing. Changes in
 * the original array are reflected in this list.
 */
@JvmInline
value class Vector2IList internal constructor(
    private val array: Vector2IArray
) : List<Vector2I>, RandomAccess {
    override val size: Int get() = array.size

    override fun containsAll(elements: Collection<Vector2I>): Boolean = array.containsAll(elements)

    /**
     * Returns an element at the given [index] without boxing.
     *
     * @exception IndexOutOfBoundsException if the [index] is out of bounds of this list.
     */
    fun elementAt(index: Int): Vector2I = array[index]

    override fun indexOf(element: Vector2I): Int = array.indexOf(element)

    override fun isEmpty(): Boolean = array.isEmpty()

    override fun lastIndexOf(element: Vector2I): Int = array.lastIndexOf(element)

    override fun listIterator(): Vector2IListIterator = ListIteratorImpl(array, index = 0)

    override fun listIterator(index: Int): Vector2IListIterator {
        checkPositionIndex(index, size)

        return ListIteratorImpl(array, index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): Vector2ISubList {
        checkRangeIndexes(fromIndex, toIndex, size)

        return Vector2ISubList(array, fromIndex, toIndex)
    }

    /**
     * Returns 1st *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 1.
     */
    inline operator fun component1(): Vector2I = elementAt(0)

    /**
     * Returns 2nd *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 2.
     */
    inline operator fun component2(): Vector2I = elementAt(1)

    /**
     * Returns 3rd *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 3.
     */
    inline operator fun component3(): Vector2I = elementAt(2)

    /**
     * Returns 4th *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 4.
     */
    inline operator fun component4(): Vector2I = elementAt(3)

    /**
     * Returns 5th *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 5.
     */
    inline operator fun component5(): Vector2I = elementAt(4)

    override fun toString(): String = array.toString()

    override operator fun contains(element: Vector2I): Boolean = array.contains(element)

    override operator fun get(index: Int): Vector2I = array[index]

    override operator fun iterator(): Vector2IIterator = ListIteratorImpl(array, index = 0)

    private class ListIteratorImpl(
        private val array: Vector2IArray,
        private var index: Int
    ) : Vector2IListIterator() {
        override fun hasNext(): Boolean = index < array.size

        override fun hasPrevious(): Boolean = index > 0

        override fun nextIndex(): Int = index

        override fun previousIndex(): Int = index - 1

        override fun nextVector2I(): Vector2I =
            if (!hasNext()) throw NoSuchElementException("$index")
            else array[index++]

        override fun previousVector2I(): Vector2I =
            if (!hasPrevious()) throw NoSuchElementException("$index")
            else array[--index]
    }
}