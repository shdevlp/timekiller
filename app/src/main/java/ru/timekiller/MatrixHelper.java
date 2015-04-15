package ru.timekiller;

import android.opengl.Matrix;

/**
 * Created by Дмитрий on 14.04.2015.
 * Операции с матрицами
 */
public class MatrixHelper {
    private float[] _modelMatrix      = new float[16]; //Модельная матрица
    private float[] _viewMatrix       = new float[16]; //Видовая
    private float[] _projectionMatrix = new float[16]; //Проекция
    private float[] _mvp              = new float[16]; //Все вместе

    /**
     * Установка проекционной, видовой, модельной матриц
     */
    public MatrixHelper() {
        final float aspect = (float) GlobalVars.width / (float) GlobalVars.height;
        Matrix.perspectiveM(_projectionMatrix, 0, 45.0f, aspect, 0.1f, 100.0f);

        final float eyeX = 10.0f;
        final float eyeY = 3.0f;
        final float eyeZ = 10.0f;

        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = 0.0f;

        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(_viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
        Matrix.setIdentityM(_modelMatrix, 0);
    }

    public float[] getModelMatrix() {
        return _modelMatrix;
    }

    public float[] getViewMatrix() {
        return _viewMatrix;
    }

    public float[] getProjectionMatrix() {
        return _projectionMatrix;
    }

    public float[] getMvp() {
        return _mvp;
    }
    public void multiMvp() {
        Matrix.multiplyMM(_mvp, 0, _viewMatrix, 0, _modelMatrix, 0);
        Matrix.multiplyMM(_mvp, 0, _projectionMatrix, 0, _mvp, 0);
    }
}
