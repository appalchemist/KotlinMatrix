package com.nalbertson.linearalgebra

class Vector2(var x: Double = 0.0,
              var y: Double = 0.0): Vector(doubleArrayOf(x, y)) {

    override fun set(col: Int, value: Double) {
        when (col) {
            0 -> x = value
            1 -> y = value
        }
        super.set(col, value)
    }

    fun getNormal(): Vector2 {
        val len = length()
        return if (len > 0.0)
            Vector2(x / len, y / len)
        else
            Vector2()
    }
}