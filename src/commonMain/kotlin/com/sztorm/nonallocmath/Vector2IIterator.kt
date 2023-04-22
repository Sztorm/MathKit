package com.sztorm.nonallocmath

abstract class Vector2IIterator : Iterator<Vector2I> {
    final override fun next() = nextVector2I()

    /** Returns the next value in the sequence without boxing. **/
    abstract fun nextVector2I(): Vector2I
}