package com.sztorm.lowallocmath

import kotlin.jvm.JvmInline

/**
 * Represents a read-only view over [Vector2FArray] type with methods that avoid boxing. Changes in
 * the original array are reflected in this list.
 */
@JvmInline
value class Vector2FList internal constructor(
    private val array: Vector2FArray
) : List<Vector2F>, RandomAccess {
    override val size: Int get() = array.size

    override fun containsAll(elements: Collection<Vector2F>): Boolean = array.containsAll(elements)

    /**
     * Returns an element at the given [index] without boxing.
     *
     * @exception IndexOutOfBoundsException if the [index] is out of bounds of this list.
     */
    fun elementAt(index: Int): Vector2F = array[index]

    override fun indexOf(element: Vector2F): Int = array.indexOf(element)

    override fun isEmpty(): Boolean = array.isEmpty()

    override fun lastIndexOf(element: Vector2F): Int = array.lastIndexOf(element)

    override fun listIterator(): Vector2FListIterator = ListIteratorImpl(array, index = 0)

    override fun listIterator(index: Int): Vector2FListIterator {
        checkPositionIndex(index, size)

        return ListIteratorImpl(array, index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): Vector2FSubList {
        checkRangeIndexes(fromIndex, toIndex, size)

        return Vector2FSubList(array, fromIndex, toIndex)
    }

    /**
     * Returns 1st *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 1.
     */
    inline operator fun component1(): Vector2F = elementAt(0)

    /**
     * Returns 2nd *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 2.
     */
    inline operator fun component2(): Vector2F = elementAt(1)

    /**
     * Returns 3rd *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 3.
     */
    inline operator fun component3(): Vector2F = elementAt(2)

    /**
     * Returns 4th *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 4.
     */
    inline operator fun component4(): Vector2F = elementAt(3)

    /**
     * Returns 5th *element* from the list.
     *
     * Throws an [IndexOutOfBoundsException] if the size of this list is less than 5.
     */
    inline operator fun component5(): Vector2F = elementAt(4)

    override fun toString(): String = array.toString()

    override operator fun contains(element: Vector2F): Boolean = array.contains(element)

    override operator fun get(index: Int): Vector2F = array[index]

    override operator fun iterator(): Vector2FIterator = ListIteratorImpl(array, index = 0)

    private class ListIteratorImpl(
        private val array: Vector2FArray,
        private var index: Int
    ) : Vector2FListIterator() {
        override fun hasNext(): Boolean = index < array.size

        override fun hasPrevious(): Boolean = index > 0

        override fun nextIndex(): Int = index

        override fun previousIndex(): Int = index - 1

        override fun nextVector2F(): Vector2F =
            if (!hasNext()) throw NoSuchElementException("$index")
            else array[index++]

        override fun previousVector2F(): Vector2F =
            if (!hasPrevious()) throw NoSuchElementException("$index")
            else array[--index]
    }
}