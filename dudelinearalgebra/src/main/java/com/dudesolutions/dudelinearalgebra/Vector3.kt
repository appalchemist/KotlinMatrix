package com.dudesolutions.dudelinearalgebra


class Vector3(var x: Double = 0.0,
              var y: Double = 0.0,
              var z: Double = 0.0): Vector(doubleArrayOf(x, y, z)) {

    override fun set(col: Int, value: Double) {
        when (col) {
            0 -> x = value
            1 -> y = value
            2 -> z = value
        }
        super.set(col, value)
    }

    fun getNormal(): Vector3 {
        val len = length()
        return if (len > 0.0)
            Vector3(x / len, y / len, z / len)
        else
            Vector3()
    }
}