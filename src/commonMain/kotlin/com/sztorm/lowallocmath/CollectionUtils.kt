@file:Suppress("unused")

package com.sztorm.lowallocmath

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

/**
 * Checks bounds [startIndex] inclusive, [endIndex] exclusive.
 *
 * @exception IndexOutOfBoundsException if [startIndex] < 0 or [endIndex] > [size].
 * @exception IllegalArgumentException if [startIndex] > [endIndex].
 */
internal inline fun checkBoundsIndexes(startIndex: Int, endIndex: Int, size: Int) {
    if (startIndex < 0 || endIndex > size) {
        throw IndexOutOfBoundsException(
            "startIndex: $startIndex, endIndex: $endIndex, size: $size"
        )
    }
    if (startIndex > endIndex) {
        throw IllegalArgumentException("startIndex: $startIndex > endIndex: $endIndex")
    }
}