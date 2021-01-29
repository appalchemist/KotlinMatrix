package com.dudesolutions.dudelinearalgebra

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import kotlin.math.cos
import kotlin.math.sin

class MatrixUnitTests {

    @Test(expected = IllegalArgumentException::class)
    fun invalidMatrix4() {
        Matrix4(doubleArrayOf(
                1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0
        ))
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidMatrix3() {
        Matrix3(doubleArrayOf(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0
        ))
    }

    @Test
    fun matrixTranslate() {
        val startVector = Vector4(1.0, 0.0, 0.0, 1.0)

        val translateMatrix = Matrix4.MakeTranslate(1.0, 0.0, 0.0)

        val endVector_tx = Vector4(2.0, 0.0, 0.0, 1.0)

        startVector.multiply(translateMatrix)
        assertThat(startVector, `is`(equalTo(endVector_tx)))
    }

    @Test
    fun matrixRotate() {
        val startVector = Vector4(1.0, 0.0, 0.0, 1.0)

        val angle = (Math.PI / 2f)
        val rotateMatrix = Matrix4.MakeRotateY(angle)

        val expectedRotateMatrix = Matrix4(doubleArrayOf(
                cos(angle), 0.0, sin(angle), 0.0,
                0.0, 1.0, 0.0, 0.0,
                -sin(angle), 0.0, cos(angle), 0.0,
                0.0, 0.0, 0.0, 1.0
        ))

        val endVector_rx = Vector4(0.0, 0.0, -1.0, 1.0)

        assertThat(rotateMatrix, `is`(equalTo(expectedRotateMatrix)))

        startVector.multiply(rotateMatrix)
        assertThat(startVector, `is`(equalTo(endVector_rx)))
    }

    @Test
    fun matrixRotateTranslate() {

        val startVector = Vector4(1.0, 0.0, 0.0, 1.0)

        val endVector_rztx = Vector4(1.0, 1.0, 0.0, 1.0)

        val angle = (Math.PI / 2f)
        val rotateZMatrix = Matrix4.MakeRotate(angle, 0.0, 0.0, 1.0)
        val translateMatrix = Matrix4.MakeTranslate(1.0, 0.0, 0.0)

        startVector.multiply(rotateZMatrix)
        startVector.multiply(translateMatrix)

        assertThat(startVector, `is`(equalTo(endVector_rztx)))
    }

    @Test
    fun matrixDeterminant() {
        val startMatrix = Matrix4(doubleArrayOf(
                2.0, -1.0, 3.0, 0.0,
                -3.0, 1.0, 0.0, 4.0,
                -2.0, 1.0, 4.0, 1.0,
                -1.0, 3.0, 0.0, -2.0
        ))

        val expectedDeterminant = -102.0

        assertThat(startMatrix.determinant(), `is`(equalTo(expectedDeterminant)))
    }

    @Test
    fun matrixWithout() {
        val startMatrix4 =  Matrix4(doubleArrayOf(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0
        ))

        val matrixWithout = startMatrix4.matrixWithout(2, 2)
        val expectedMatrix = Matrix3(doubleArrayOf(
                1.0, 2.0, 4.0,
                5.0, 6.0, 8.0,
                13.0, 14.0, 16.0
        ))

        assertThat(matrixWithout, `is`(equalTo(expectedMatrix)))
    }

    @Test
    fun matrixMinors() {
        val startMatrix = Matrix4(doubleArrayOf(
                1.0, 2.0, -1.0, 4.0,
                3.0, 0.0, -2.0, 5.0,
                0.0, 1.0, 0.0, 3.0,
                1.0, 2.0, 5.0, 2.0
        ))

        val expectedMatrix = Matrix4(doubleArrayOf(
                17.0, -51.0, -17.0, 17.0,
                -14.0, -18.0, -2.0, 6.0,
                -52.0, 40.0, 12.0, -36.0,
                -9.0, 3.0, -11.0, -1.0
        ))

        assertThat(startMatrix.minors(), `is`(equalTo(expectedMatrix)))
    }

    @Test
    fun matrixCofactors() {
        val startMatrix = Matrix4(doubleArrayOf(
                1.0, 2.0, -1.0, 4.0,
                3.0, 0.0, -2.0, 5.0,
                0.0, 1.0, 0.0, 3.0,
                1.0, 2.0, 5.0, 2.0
        ))

        val expectedMatrix = Matrix4(doubleArrayOf(
                17.0, 51.0, -17.0, -17.0,
                14.0, -18.0, 2.0, 6.0,
                -52.0, -40.0, 12.0, 36.0,
                9.0, 3.0, 11.0, -1.0
        ))

        assertThat(startMatrix.cofactors(), `is`(equalTo(expectedMatrix)))
    }

    @Test
    fun matrixTranspose() {
        val startMatrix = Matrix4(doubleArrayOf(
                1.0, 2.0, -1.0, 4.0,
                3.0, 0.0, -2.0, 5.0,
                0.0, 1.0, 0.0, 3.0,
                1.0, 2.0, 5.0, 2.0
        ))

        val expectedMatrix = Matrix4(doubleArrayOf(
                1.0, 3.0, 0.0, 1.0,
                2.0, 0.0, 1.0, 2.0,
                -1.0, -2.0, 0.0, 5.0,
                4.0, 5.0, 3.0, 2.0
        ))

        assertThat(startMatrix.transpose(), `is`(equalTo(expectedMatrix)))
    }

    @Test
    fun matrixAdjugate() {
        val startMatrix = Matrix4(doubleArrayOf(
                1.0, 2.0, -1.0, 4.0,
                3.0, 0.0, -2.0, 5.0,
                0.0, 1.0, 0.0, 3.0,
                1.0, 2.0, 5.0, 2.0
        ))

        val expectedMatrix = Matrix4(doubleArrayOf(
                17.0, 14.0, -52.0, 9.0,
                51.0, -18.0, -40.0, 3.0,
                -17.0, 2.0, 12.0, 11.0,
                -17.0, 6.0, 36.0, -1.0
        ))

        assertThat(startMatrix.adjugate(), `is`(equalTo(expectedMatrix)))
    }

    @Test
    fun matrixInverse() {
        val startMatrix = Matrix4(doubleArrayOf(
                1.0, 2.0, -1.0, 4.0,
                3.0, 0.0, -2.0, 5.0,
                0.0, 1.0, 0.0, 3.0,
                1.0, 2.0, 5.0, 2.0
        ))

        val expectedMatrix = Matrix4(doubleArrayOf(
                0.25, 0.205882, -0.7647059, 0.1323529,
                0.75, -0.2647059, -0.5882353, 0.0441176,
               -0.25, 0.0294118, 0.1764706, 0.1617647,
                -0.25, 0.0882353, 0.5294118, -0.0147059
        ))

        assertThat(startMatrix.inverse(), `is`(equalTo(expectedMatrix)))
    }
}