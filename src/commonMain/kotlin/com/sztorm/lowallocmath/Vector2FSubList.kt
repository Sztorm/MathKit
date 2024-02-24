package com.sztorm.lowallocmath

/**
 * Represents a read-only view of the portion of [Vector2FList]. Changes in the original list are
 * reflected in this list.
 */
class Vector2FSubList internal constructor(
    private val array: Vector2FArray,
    private val fromIndex: Int,
    toIndex: Int
) : List<Vector2F>, RandomAccess {
    private val _size: Int = toIndex - fromIndex

    override val size: Int
        get() = _size

    override fun containsAll(elements: Collection<Vector2F>): Boolean {
        for (v in elements) {
            if (!contains(v)) {
                return false
            }
        }
        return true
    }

    /**
     * Returns an element at the given [index] without boxing.
     *
     * @exception IndexOutOfBoundsException if the [index] is out of bounds of this list.
     */
    fun elementAt(index: Int): Vector2F {
        checkElementIndex(index, _size)

        return array[index + fromIndex]
    }

    override fun indexOf(element: Vector2F): Int {
        val toIndex = fromIndex + _size

        for (index in fromIndex until toIndex) {
            if (element == array[index]) {
                return index - fromIndex
            }
        }
        return -1
    }

    override fun isEmpty(): Boolean = _size == 0

    override fun lastIndexOf(element: Vector2F): Int {
        var index: Int = fromIndex + _size

        while (--index >= fromIndex) {
            if (element == array[index]) {
                return index - fromIndex
            }
        }
        return -1
    }

    override fun listIterator(): Vector2FListIterator =
        ListIteratorImpl(array, fromIndex, _size, index = 0)

    override fun listIterator(index: Int): Vector2FListIterator {
        checkPositionIndex(index, _size)

        return ListIteratorImpl(array, fromIndex, _size, index)
    }

    override fun subList(fromIndex: Int, toIndex: Int): Vector2FSubList {
        checkRangeIndexes(fromIndex, toIndex, _size)
        val thisFromIndex: Int = this.fromIndex

        return Vector2FSubList(
            array, thisFromIndex + fromIndex, thisFromIndex + toIndex
        )
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

    override fun toString(): String = this.joinToString(
        prefix = "[",
        postfix = "]",
        separator = ", "
    ) { it.toString() }

    override operator fun contains(element: Vector2F): Boolean = indexOf(element) >= 0

    override operator fun get(index: Int): Vector2F {
        checkElementIndex(index, _size)

        return array[index + fromIndex]
    }

    override operator fun iterator(): Vector2FListIterator =
        ListIteratorImpl(array, fromIndex, _size, index = 0)

    private class ListIteratorImpl(
        private val array: Vector2FArray,
        private val fromIndex: Int,
        private val size: Int,
        private var index: Int
    ) : Vector2FListIterator() {
        override fun hasNext(): Boolean = index < size

        override fun hasPrevious(): Boolean = index > 0

        override fun nextIndex(): Int = index

        override fun previousIndex(): Int = index - 1

        override fun nextVector2F(): Vector2F =
            if (!hasNext()) throw NoSuchElementException("$index")
            else array[index++ + fromIndex]

        override fun previousVector2F(): Vector2F =
            if (!hasPrevious()) throw NoSuchElementException("$index")
            else array[--index + fromIndex]
    }
}