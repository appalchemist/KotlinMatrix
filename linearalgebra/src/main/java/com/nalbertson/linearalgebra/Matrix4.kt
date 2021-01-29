package com.nalbertson.linearalgebra

import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class Matrix4(private val array: DoubleArray = doubleArrayOf(
        1.0, 0.0, 0.0, 0.0,
        0.0, 1.0, 0.0, 0.0,
        0.0, 0.0, 1.0, 0.0,
        0.0, 0.0, 0.0, 1.0)) {

    private val matrix: Matrix = Matrix(array)

    init {
        if (array.size != 16) throw IllegalArgumentException("Matrix4 was initialized with an invalid length.")
    }

    fun getLength(): Int = matrix.length

    fun getCell(row: Int, col: Int) = matrix.getCell(row, col)

    fun setCell(row: Int, col: Int, value: Double) {
        matrix.setCell(row, col, value)
    }

    fun determinant() = matrix.determinant()

    fun matrixWithout(row: Int, col: Int) = Matrix3(matrix.matrixWithout(row, col))

    fun minors() = Matrix4(matrix.minors())

    fun cofactors() = Matrix4(matrix.cofactors())

    fun transpose() = Matrix4(matrix.transpose())

    fun adjugate() = Matrix4(matrix.adjugate())

    fun inverse() = Matrix4(matrix.inverse())

    fun getProduct(matrix: Matrix4): Matrix4 {
        val product = Matrix4.ZERO
        with(this.matrix) {
            for (row in 0 until length) {
                for (col in 0 until length) {
                    var sum = 0.0
                    for (i in 0 until length) {
                        sum += this.getCell(row, i) * matrix.getCell(i, col)
                    }
                    product.setCell(row, col, sum)
                }
            }
        }
        return product
    }

    fun multiply(matrix: Matrix4) {
        val multMatrix = Matrix4(array.clone())
        with(this.matrix) {
            for (r in 0 until length) {
                for (c in 0 until length) {
                    var sum = 0.0
                    for (i in 0 until length) {
                        sum += matrix.getCell(r, i) * multMatrix.getCell(i, c)
                    }
                    setCell(r, c, sum)
                }
            }
        }
    }

    private fun applyTransform(matrix: Matrix4) {
        val multMatrix = Matrix4(array.clone())
        for (r in 0 until matrix.getLength()) {
            for (c in 0 until matrix.getLength()) {
                var sum = 0.0
                for (i in 0 until matrix.getLength()) {
                    sum += matrix.getCell(r, i) * multMatrix.getCell(i, c)
                }
                setCell(r, c, sum)
            }
        }
    }

    fun rotate(rad: Double, rotX: Double, rotY: Double, rotZ: Double) {
        val rotateMatrix = MakeRotate(rad, rotX, rotY, rotZ)
        applyTransform(rotateMatrix)
    }

    fun scale(x: Double, y: Double, z: Double) {
        val scaleMatrix = MakeScale(x, y, z)
        applyTransform(scaleMatrix)
    }

    fun translate(x: Double, y: Double, z: Double) {
        val translateMatrix = MakeTranslate(x, y, z)
        applyTransform(translateMatrix)
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix4) {
            return matrix == other.matrix
        }
        return false
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(array)
    }

    override fun toString(): String = matrix.toString()

    companion object {
        val ZERO = Matrix4(DoubleArray(16))

        fun MakeRotateZ(rot: Double) = Matrix4(doubleArrayOf(
                    cos(rot), -sin(rot), 0.0, 0.0,
                    sin(rot), cos(rot), 0.0, 0.0,
                    0.0, 0.0, 1.0, 0.0,
                    0.0, 0.0, 0.0, 1.0
            ))

        fun MakeRotateY(rot: Double) =  Matrix4(doubleArrayOf(
                cos(rot), 0.0, sin(rot), 0.0,
                0.0, 1.0, 0.0, 0.0,
                -sin(rot), 0.0, cos(rot), 0.0,
                0.0, 0.0, 0.0, 1.0
            ))

        fun MakeRotate(rot: Double, rotX: Double, rotY: Double, rotZ: Double): Matrix4 {
            val axis:Vector3 = Vector3(rotX, rotY, rotZ).getNormal()
            val x = axis.get(0)
            val y = axis.get(1)
            val z = axis.get(2)
            val s = sin(rot)
            val c = cos(rot)
            val oc = 1 - c
            return Matrix4(doubleArrayOf(
                    oc * x * x + c,     oc * x * y - z * s, oc * x * z + y * s, 0.0,
                    oc * y * x + z * s, oc * y * y + c,     oc * y * z - x * s, 0.0,
                    oc * z * x - y * s, oc * z * y + x * s, oc * z * z + c,     0.0,
                    0.0,                0.0,                0.0,                1.0
            ))
        }

        fun MakeTranslate(x: Double, y: Double, z: Double): Matrix4 = Matrix4(doubleArrayOf(
                1.0, 0.0, 0.0, x,
                0.0, 1.0, 0.0, y,
                0.0, 0.0, 1.0, z,
                0.0, 0.0, 0.0, 1.0
        ))

        fun MakeScale(x: Double, y: Double, z: Double): Matrix4 = Matrix4(doubleArrayOf(
                x, 0.0, 0.0, 0.0,
                0.0, y, 0.0, 0.0,
                0.0, 0.0, z, 0.0,
                0.0, 0.0, 0.0, 1.0
        ))
    }
}

