package com.sztorm.nonallocmath

abstract class Vector2FIterator : Iterator<Vector2F> {
    final override fun next() = nextVector2F()

    /** Returns the next value in the sequence without boxing. **/
    abstract fun nextVector2F(): Vector2F
}