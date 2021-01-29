package com.nalbertson.linearalgebra

import java.lang.IllegalArgumentException
import java.util.*
import kotlin.math.sqrt

internal class Matrix(val matrix: DoubleArray) {

    val length: Int

    init {
        val n = sqrt(matrix.size.toFloat())
        if (n % 1 != 0f) {
            throw IllegalArgumentException("Only square matrices are supported (NxN matrices)")
        } else {
            length = n.toInt()
        }
    }

    fun getCell(row: Int, col: Int): Double = matrix[length * row + col]

    fun setCell(row: Int, col: Int, value: Double) {
        matrix[length * row + col] = value
    }

    fun determinant(): Double {
        var determinant = getCell(0,0)
        if (length > 3) {
            val innerMatrices = mutableListOf<Matrix>()
            for (i in 0 until length) {
                val array = DoubleArray((length - 1) * (length - 1))
                var index = 0
                for (row in 1 until length) {
                    for (col in 0 until length) {
                        if (col == i) continue
                        array[index] = getCell(row, col)
                        index++
                    }
                }
                innerMatrices.add(Matrix(array))
            }

            for (i in 0 until innerMatrices.size) {
                val matrix = innerMatrices[i]
                when {
                    i == 0 -> determinant *= matrix.determinant()
                    i % 2 == 0 -> determinant += getCell(0, i) * matrix.determinant()
                    else -> determinant -= getCell(0, i) * matrix.determinant()
                }
            }

        } else {
            determinant *= (getCell(1, 1) * getCell(2,2) - getCell(1, 2) * getCell(2, 1))
            determinant -= getCell(0,1) * (getCell(1, 0) * getCell(2,2) - getCell(1, 2) * getCell(2, 0))
            determinant += getCell(0,2) * (getCell(1, 0) * getCell(2,1) - getCell(1, 1) * getCell(2, 0))
        }

        return determinant
    }

    fun matrixWithout(row: Int, col: Int): DoubleArray {
        val array = DoubleArray((length - 1) * (length - 1))

        var index = 0
        for (i in 0 until length) {
            if (i == row) {
                continue
            }
            for (j in 0 until length) {
                if (j == col) {
                    continue
                }
                array[index] = getCell(i, j)
                index++
            }
        }

        return array
    }

    fun minors(): DoubleArray {
        val array = DoubleArray(matrix.size)
        for (row in 0 until length) {
            for (col in 0 until length) {
                array[length * row + col] = Matrix(matrixWithout(row, col)).determinant()
            }
        }
        return array
    }

    fun cofactors(): DoubleArray {
        val cofactorMatrix = Matrix(this.minors())
        for (i in 0 until length) {
            for (j in 0 until length) {
                val cofactor = (Math.pow(-1.0, (i + j).toDouble()).toFloat()) * cofactorMatrix.getCell(i, j)
                cofactorMatrix.setCell(i, j, cofactor)
            }
        }
        return cofactorMatrix.matrix
    }

    fun transpose(): DoubleArray {
        val array = DoubleArray(matrix.size)
        for (row in 0 until length) {
            for (col in 0 until length) {
                array[length * row + col] = getCell(col, row)
            }
        }
        return array
    }

    fun adjugate(): DoubleArray = Matrix(this.cofactors()).transpose()

    fun inverse(): DoubleArray {
        val array = DoubleArray(matrix.size)
        val adjugate = adjugate()
        val det = determinant()
        for (i in 0 until adjugate.size) {
            array[i] = adjugate[i] / det
        }

        return array
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix) {
            for (i in 0 until other.matrix.size) {
                val difference = Math.abs(this.matrix[i] - other.matrix[i])
                if (difference > 0.00001) return false
            }
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(matrix)
    }

    override fun toString(): String {
        var string = ""
        for (i in 0 until matrix.size) {
            val point = matrix[i]
            string += "$point, "
            if ((i+1) % length == 0) {
                string += "\n"
            }
        }
        return string
    }
}