package com.nalbertson.linearalgebra

class Vector4(var x: Double = 0.0,
              var y: Double = 0.0,
              var z: Double = 0.0,
              var a: Double = 0.0): Vector(doubleArrayOf(x, y, z, a)) {

    override fun set(col: Int, value: Double) {
        when (col) {
            0 -> x = value
            1 -> y = value
            2 -> z = value
            3 -> a = value
        }
        super.set(col, value)
    }

    fun getNormal(): Vector4 {
        val len = length()
        return if (len > 0.0)
            Vector4(x / len, y / len, z / len, a / len)
        else
            Vector4()
    }

    fun multiply(matrix: Matrix4) {
        val multVector = Vector4(x, y, z, a)
        for (r in 0 until matrix.getLength()) {
            var sum = 0.0
            for (i in 0 until matrix.getLength()) {
                sum += matrix.getCell(r, i) * multVector.get(i)
            }
            set(r, sum)
        }
    }
}