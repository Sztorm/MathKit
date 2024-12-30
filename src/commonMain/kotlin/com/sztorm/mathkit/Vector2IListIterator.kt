package com.sztorm.mathkit

/** An iterator over a [Vector2I] collection that supports indexed access. **/
abstract class Vector2IListIterator : Vector2IIterator(), ListIterator<Vector2I> {
    final override fun previous() = previousVector2I()

    /**
     * Returns the previous element in the iteration without boxing, and moves the cursor position
     * backwards.
     */
    abstract fun previousVector2I(): Vector2I
}