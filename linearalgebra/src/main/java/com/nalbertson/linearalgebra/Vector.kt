package com.nalbertson.linearalgebra

import kotlin.math.sqrt

open class Vector(val vector: DoubleArray) {

    fun get(col: Int): Double = vector[col]

    open fun set(col: Int, value: Double) {
        vector[col] = value
    }

    fun length(): Double {
        var length = 0.0
        for (i in vector.indices) {
            length += vector[i] * vector[i]
        }
        return sqrt(length)
    }

    fun normalize() {
        val len = length()
        for (i in vector.indices) {
            vector[i] /= len
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is Vector) {
            for (i in vector.indices) {
                val difference = Math.abs(vector[i] - other.vector[i])
                if (difference > 0.00001) return false
            }
            return true
        }
        return false
    }

    override fun toString(): String {
        var string = ""
        for (element in vector) {
            val point = element
            string += "$point, "
        }
        return string
    }

    override fun hashCode(): Int {
        return vector.contentHashCode()
    }
}