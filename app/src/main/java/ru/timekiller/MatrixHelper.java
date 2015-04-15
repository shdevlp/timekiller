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
        Matrix.orthoM(_projectionMatrix, 0, GlobalVars.left, GlobalVars.right,
                GlobalVars.bottom, GlobalVars.top, GlobalVars.near, GlobalVars.far);
        restoreViewMatrix();
        Matrix.setIdentityM(_modelMatrix, 0);
    }

    /**
     * Восстановление видовой матрицы
     */
    public void restoreViewMatrix() {
        Matrix.setLookAtM(_viewMatrix, 0, GlobalVars.eyeX, GlobalVars.eyeY, GlobalVars.eyeZ,
                GlobalVars.lookX, GlobalVars.lookY, GlobalVars.lookZ,
                GlobalVars.upX, GlobalVars.upY, GlobalVars.upZ);
    }

    /**
     * Перемещение по осям
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        Matrix.translateM(_viewMatrix, 0, x, y, z);
    }

    public void loadIdentity() {
        restoreViewMatrix();
        Matrix.setIdentityM(_modelMatrix, 0);
    }

    public float[] getMvp() {
        multiMvp();
        return _mvp;
    }

    private void multiMvp() {
        Matrix.multiplyMM(_mvp, 0, _viewMatrix, 0, _modelMatrix, 0);
        Matrix.multiplyMM(_mvp, 0, _projectionMatrix, 0, _mvp, 0);
    }
}
