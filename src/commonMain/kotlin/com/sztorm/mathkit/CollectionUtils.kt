@file:Suppress("NOTHING_TO_INLINE")

package com.sztorm.mathkit

/** @exception IndexOutOfBoundsException if index is out of [0, [size]) range. **/
internal inline fun checkElementIndex(index: Int, size: Int) {
    if (index.toUInt() >= size.toUInt()) {
        throw IndexOutOfBoundsException("index: $index, size: $size")
    }
}

/** @exception IndexOutOfBoundsException if index is out of [0, [size]] range. **/
internal inline fun checkPositionIndex(index: Int, size: Int) {
    if (index.toUInt() > size.toUInt()) {
        throw IndexOutOfBoundsException("index: $index, size: $size")
    }
}

/**
 * Checks range [fromIndex] inclusive, [toIndex] exclusive.
 *
 * @exception IndexOutOfBoundsException if [fromIndex] < 0 or [toIndex] > [size].
 * @exception IllegalArgumentException if [fromIndex] > [toIndex].
 */
internal inline fun checkRangeIndexes(fromIndex: Int, toIndex: Int, size: Int) {
    if (fromIndex < 0 || toIndex > size) {
        throw IndexOutOfBoundsException("fromIndex: $fromIndex, toIndex: $toIndex, size: $size")
    }
    if (fromIndex > toIndex) {
        throw IllegalArgumentException("fromIndex: $fromIndex > toIndex: $toIndex")
    }
}