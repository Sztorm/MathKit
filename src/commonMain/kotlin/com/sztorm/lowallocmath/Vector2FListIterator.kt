package com.sztorm.lowallocmath


/** An iterator over a [Vector2F] collection that supports indexed access. **/
abstract class Vector2FListIterator : Vector2FIterator(), ListIterator<Vector2F> {
    final override fun previous() = previousVector2F()

    /**
     * Returns the previous element in the iteration without boxing, and moves the cursor position
     * backwards.
     */
    abstract fun previousVector2F(): Vector2F
}