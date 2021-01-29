package com.dudesolutions.dudelinearalgebra

import java.util.*


class Matrix3(private val array: DoubleArray = doubleArrayOf(
        1.0, 0.0, 0.0,
        0.0, 1.0, 0.0,
        0.0, 0.0, 1.0)) {

    private val matrix: Matrix = Matrix(array)

    init {
        if (array.size != 9) throw IllegalArgumentException("Matrix3 was initialized with an invalid length.")
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix3) {
            return matrix == other.matrix
        }
        return false
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(array)
    }

    override fun toString(): String = matrix.toString()
}